package au.shamsutdinov.artur.parser.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.shamsutdinov.artur.parser.data.Link;
import au.shamsutdinov.artur.parser.interfaces.ElementParser;
import au.shamsutdinov.artur.parser.interfaces.TitleRetriever;
import rx.Observable;

public class LinksParser implements ElementParser {
    private final RegexParser parser = new RegexParser(
            Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)"),
            Matcher::group);
    private final TitleRetriever titleRetriever;

    public LinksParser(TitleRetriever titleRetriever) {
        this.titleRetriever = titleRetriever;
    }

    public Observable<Object> parse(final String text) {
        return parser.parse(text)
                .flatMap(titleRetriever::getTitle, Link::new);
    }
}