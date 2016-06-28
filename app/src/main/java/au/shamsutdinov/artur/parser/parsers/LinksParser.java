package au.shamsutdinov.artur.parser.parsers;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.shamsutdinov.artur.parser.data.Link;
import au.shamsutdinov.artur.parser.interfaces.ElementParser;
import au.shamsutdinov.artur.parser.interfaces.TitleRetriever;
import rx.Observable;

/**
 * Parsing links like "http://google.com".
 */
public class LinksParser implements ElementParser {
    private final RegexParser baseParser = new RegexParser(
            Patterns.WEB_URL,
            Matcher::group);
    private final TitleRetriever titleRetriever;

    public LinksParser(TitleRetriever titleRetriever) {
        this.titleRetriever = titleRetriever;
    }

    public Observable<Object> parse(final String text) {
        return baseParser.parse(text).flatMap(titleRetriever::getTitle, Link::new);
    }

    @Override
    public String remove(String text) {
        return baseParser.remove(text);
    }

    @Override
    public Integer getOrder() {
        return 1;
    }

    @Override
    public String getName() {
        return "links";
    }
}
