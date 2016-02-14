package au.shamsutdinov.artur.parser.interfaces;

import rx.Observable;

/**
 * Interface encapsulating parser for one kind of elements.
 */
public interface ElementParser {
    Observable<Object> parse(String text);
    String remove(String text);
    /**
     * Getter used for parsers prioritization.
     * @return priority. Smaller value - higher priority.
     */
    Integer getOrder();
    String getName();
}
