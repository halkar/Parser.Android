package au.shamsutdinov.artur.parser;

import android.support.annotation.NonNull;
import android.util.Pair;

import java.util.Arrays;
import java.util.List;

import au.shamsutdinov.artur.parser.interfaces.ElementParser;
import au.shamsutdinov.artur.parser.interfaces.Parser;
import au.shamsutdinov.artur.parser.interfaces.Serializer;
import rx.Observable;
import rx.functions.Func2;

public class MessageParser implements Parser {
    private final ElementParser[] parsers;
    private final Serializer serializer;

    public MessageParser(ElementParser[] parsers, Serializer serializer) {
        this.parsers = parsers;
        Arrays.sort(this.parsers, (lhs, rhs) -> lhs.getOrder().compareTo(rhs.getOrder()));
        this.serializer = serializer;
    }


    @Override
    public Observable<String> parse(String text) {
        ValueHolder dummy = new ValueHolder(text);
        return Observable.from(parsers)
                .map(parser -> new Pair<>(parser, dummy))
                .flatMap(this::parseInternal, (parser, results) -> new Pair<>(parser.first.getName(), results))
                .filter(pair -> pair.getSecond().size() > 0)
                .toMap(Pair::getFirst, Pair::getSecond)
                .map(serializer::toString);
    }

    @NonNull
    private Observable<? extends List<Object>> parseInternal(Pair<ElementParser, ValueHolder> pair) {
        ElementParser parser = pair.first;
        ValueHolder holder = pair.second;
        //parsing input value
        Observable<List<Object>> result = parser.parse(holder.value).toList();
        //removing all entries from the string to prevent nested elements
        holder.value = parser.remove(holder.value);
        return result;
    }

    public static class Pair<K, V> {

        private final K first;
        private final V second;

        public Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }

        public K getFirst() {
            return first;
        }

        public V getSecond() {
            return second;
        }

    }

    public static class ValueHolder {

        private String value;

        public ValueHolder(String value) {
            this.value = value;
        }
    }
}
