package au.shamsutdinov.artur.parser.parsers;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import au.shamsutdinov.artur.parser.JsonSerializer;
import au.shamsutdinov.artur.parser.MessageParser;
import au.shamsutdinov.artur.parser.interfaces.ElementParser;
import au.shamsutdinov.artur.parser.interfaces.Parser;
import au.shamsutdinov.artur.parser.interfaces.TitleRetriever;
import rx.Observable;
import rx.observers.TestSubscriber;

@RunWith(Parameterized.class)
public class ParserTest {
    @Parameterized.Parameters(name = "{index}: {0}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Good morning! (megusta) (coffee)", "{\"emoticons\":[\"megusta\",\"coffee\"]}"},
                {"@bob @john (success) such a cool feature; https://twitter.com/jdorfman/status/430511497475670016", "{\"emoticons\":[\"success\"],\"links\":[{\"title\":\"Title\",\"url\":\"https://twitter.com/jdorfman/status/430511497475670016\"}],\"mention\":[\"bob\",\"john\"]}"},
                {"Olympics are starting soon; http://www.nbcolympics.com", "{\"links\":[{\"title\":\"Title\",\"url\":\"http://www.nbcolympics.com\"}]}"},
                {"http://login@pass:testurl.com", "{\"links\":[{\"title\":\"Title\",\"url\":\"http://login@pass:testurl.com\"}]}"}
        });
    }

    private final String text;
    private final String result;

    public ParserTest(String text, String result) {
        this.text = text;
        this.result = result;
    }

    @Test
    public void simpleTest() throws JSONException {
        ElementParser[] parsers = {new EmoticonsParser(), new LinksParser(new MockTitleRetriever()), new MentionsParser()};
        Parser parser = new MessageParser(parsers, new JsonSerializer());
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        parser.parse(text).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertReceivedOnNext(Arrays.asList(result));
    }

    public static class MockTitleRetriever implements TitleRetriever {
        @Override
        public Observable<String> getTitle(String urlString) {
            return Observable.just("Title");
        }
    }
}
