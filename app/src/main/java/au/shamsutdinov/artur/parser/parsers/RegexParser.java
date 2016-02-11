package au.shamsutdinov.artur.parser.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

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

}
