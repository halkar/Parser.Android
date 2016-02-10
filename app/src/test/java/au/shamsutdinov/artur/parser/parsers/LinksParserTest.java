package au.shamsutdinov.artur.parser.parsers;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import au.shamsutdinov.artur.parser.data.Link;
import au.shamsutdinov.artur.parser.interfaces.TitleRetriever;
import rx.Observable;
import rx.observers.TestSubscriber;

@RunWith(Parameterized.class)
public class LinksParserTest {
    @Parameterized.Parameters(name = "{index}: {0}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"@bob @john (success) such a cool feature; https://twitter.com/jdorfman/status/430511497475670016",
                        new Link[]{new Link("https://twitter.com/jdorfman/status/430511497475670016", "Title")}},
                {"Olympics are starting soon; http://www.nbcolympics.com",
                        new Link[]{new Link("http://www.nbcolympics.com", "Title")}},
        });
    }

    private final String text;
    private final Object[] result;

    public LinksParserTest(String text, Object[] result) {
        this.text = text;
        this.result = result;
    }

    @Test
    public void simpleTest() throws JSONException {
        MockTitleRetriever titleRetriever = new MockTitleRetriever();
        LinksParser parser = new LinksParser(titleRetriever);
        TestSubscriber<Object> testSubscriber = new TestSubscriber<>();
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
