package au.shamsutdinov.artur.parser.interfaces;

import rx.Observable;

/**
 * Interface encapsulating logic of associating link with its title.
 */
public interface TitleRetriever {
    Observable<String> getTitle(final String urlString);
}
