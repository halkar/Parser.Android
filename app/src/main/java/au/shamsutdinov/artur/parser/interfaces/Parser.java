package au.shamsutdinov.artur.parser.interfaces;

import rx.Observable;

/**
 * Interface encapsulating message processing.
 */
public interface Parser {
    /**
     * Parse message text
     * @param text message
     * @return JSON string representation of parsed data.
     */
    Observable<String> parse(String text);
}
