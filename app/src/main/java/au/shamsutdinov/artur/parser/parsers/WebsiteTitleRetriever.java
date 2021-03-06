package au.shamsutdinov.artur.parser.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.shamsutdinov.artur.parser.interfaces.TitleRetriever;
import rx.Observable;
import rx.Subscriber;

/**
 * Observable wrapper for HTTP request.
 */
public class WebsiteTitleRetriever implements TitleRetriever {
    private final Pattern regex = Pattern.compile("<title[^>]*>(.*?)</title>");

    public Observable<String> getTitle(final String urlString) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder str = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        str.append(line);
                    }
                    in.close();
                    String html = str.toString();
                    Matcher matcher = regex.matcher(html);
                    //extract title
                    if (matcher.find()) {
                        subscriber.onNext(matcher.group(1));
                    } else {
                        subscriber.onNext(null);
                    }
                    subscriber.onCompleted();
                } catch (MalformedURLException e) {
                    subscriber.onError(e);
                } catch (IOException e) {
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                }
            }
        });
    }
}
