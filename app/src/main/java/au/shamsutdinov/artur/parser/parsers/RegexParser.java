package au.shamsutdinov.artur.parser.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Base parser for all other parsers.
 * Input strings are parsed using regular expression.
 */
public class RegexParser {

    private final Pattern pattern;
    private final Func1<Matcher, String> selector;

    public RegexParser(Pattern pattern) {
        this(pattern, matcher -> matcher.group(1));
    }

    public RegexParser(Pattern pattern, Func1<Matcher, String> selector) {
        this.pattern = pattern;
        this.selector = selector;
    }

    /**
     * Extract values from the string and return them as observable.
     * @param text message to parse.
     * @return observable result.
     */
    public Observable<String> parse(final String text) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Matcher matcher = pattern.matcher(text);
                while (matcher.find()) {
                    subscriber.onNext(selector.call(matcher));
                }
                subscriber.onCompleted();
            }
        });
    }

    /**
     * Remove all found entries from the message.
     */
    public String remove(String text) {
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll("");
    }
}
