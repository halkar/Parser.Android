package au.shamsutdinov.artur.parser.interfaces;

import rx.Observable;

public interface ElementParser {
    Observable<Object> parse(String text);
    String remove(String text);
    Integer getOrder();
    String getName();
}
