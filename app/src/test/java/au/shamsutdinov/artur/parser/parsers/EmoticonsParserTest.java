package au.shamsutdinov.artur.parser.parsers;

import org.json.JSONException;
import org.junit.Test;

import java.util.Arrays;

import rx.observers.TestSubscriber;


public class EmoticonsParserTest {
    @Test
    public void simpleTest() throws JSONException {
        EmoticonsParser parser = new EmoticonsParser();
        TestSubscriber<Object> testSubscriber = new TestSubscriber<>();
        parser.parse("Good morning! (megusta) (coffee)").subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertReceivedOnNext(Arrays.asList((Object)"megusta", "coffee"));
    }
}
