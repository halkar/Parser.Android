package au.shamsutdinov.artur.parser.parsers;

import java.util.regex.Pattern;

import au.shamsutdinov.artur.parser.interfaces.Parser;
import rx.Observable;
import rx.functions.Func1;

public class MentionsParser implements Parser {
    private final RegexParser baseParser = new RegexParser(Pattern.compile("@(\\w+)"));

    @Override
    public Observable<Object> parse(String text) {
        return baseParser.parse(text).map(new Func1<String, Object>() {
            @Override
            public Object call(String s) {
                return s;
            }
        });
    }
}
