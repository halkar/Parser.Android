package au.shamsutdinov.artur.parser.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.shamsutdinov.artur.parser.data.Link;
import au.shamsutdinov.artur.parser.interfaces.Parser;
import au.shamsutdinov.artur.parser.interfaces.TitleRetriever;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

public class LinksParser implements Parser {
    private final RegexParser parser = new RegexParser(
            Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)"),
            new Func1<Matcher, String>() {
                @Override
                public String call(Matcher matcher) {
                    return matcher.group();
                }
            });
    private final TitleRetriever titleRetriever;

    public LinksParser(TitleRetriever titleRetriever) {
        this.titleRetriever = titleRetriever;
    }

    public Observable<Object> parse(final String text) {
        return parser.parse(text)
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String url) {
                        return titleRetriever.getTitle(url);
                    }
                }, new Func2<String, String, Object>() {
                    @Override
                    public Object call(String url, String title) {
                        return new Link(url, title);
                    }
                });
    }
}
