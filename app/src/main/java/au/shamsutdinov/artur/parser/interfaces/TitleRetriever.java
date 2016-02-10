package au.shamsutdinov.artur.parser.interfaces;

import rx.Observable;

public interface TitleRetriever {
    Observable<String> getTitle(final String urlString);
}
