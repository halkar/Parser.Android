package au.shamsutdinov.artur.parser.interfaces;

import rx.Observable;

public interface Parser {
    Observable<Object> parse(String text);
}
