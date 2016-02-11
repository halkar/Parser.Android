package au.shamsutdinov.artur.parser.parsers;

import java.util.regex.Pattern;

import au.shamsutdinov.artur.parser.interfaces.ElementParser;
import rx.Observable;
import rx.functions.Func1;

public class EmoticonsParser implements ElementParser {
    private final RegexParser baseParser = new RegexParser(Pattern.compile("\\(([a-zA-Z0-9]{1,15})\\)"));

    @Override
    public Observable<Object> parse(String text) {
        return baseParser.parse(text).map(s -> s);
    }
}
