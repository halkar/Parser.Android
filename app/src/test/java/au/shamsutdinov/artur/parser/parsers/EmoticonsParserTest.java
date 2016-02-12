package au.shamsutdinov.artur.parser.parsers;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import au.shamsutdinov.artur.parser.interfaces.ElementParser;
import rx.observers.TestSubscriber;

@RunWith(Parameterized.class)
public class EmoticonsParserTest {

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Good morning! (megusta) (coffee)", new Object[]{"megusta", "coffee"}},
                {"@bob @john (success) such a cool feature; https://twitter.com/jdorfman/status/430511497475670016", new Object[]{"success"}}
        });
    }

    private final String text;
    private final Object[] result;

    public EmoticonsParserTest(String text, Object[] result) {
        this.text = text;
        this.result = result;
    }

    @Test
    public void simpleTest() throws JSONException {
        ElementParser parser = new EmoticonsParser();
        TestSubscriber<Object> testSubscriber = new TestSubscriber<>();
        parser.parse(text).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertReceivedOnNext(Arrays.asList(result));
    }
}
