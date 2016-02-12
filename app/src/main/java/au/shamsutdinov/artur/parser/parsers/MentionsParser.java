package au.shamsutdinov.artur.parser.parsers;

import java.util.regex.Pattern;

import au.shamsutdinov.artur.parser.interfaces.ElementParser;
import rx.Observable;
import rx.functions.Func1;

public class MentionsParser implements ElementParser {
    private final RegexParser baseParser = new RegexParser(Pattern.compile("@(\\w+)"));

    @Override
    public Observable<Object> parse(String text) {
        return baseParser.parse(text).map(s -> s);
    }

    @Override
    public String remove(String text) {
        return baseParser.remove(text);
    }

    @Override
    public Integer getOrder() {
        return 100;
    }

    @Override
    public String getName() {
        return "mention";
    }
}
