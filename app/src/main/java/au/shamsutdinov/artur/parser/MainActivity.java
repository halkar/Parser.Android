package au.shamsutdinov.artur.parser;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import au.shamsutdinov.artur.parser.interfaces.ElementParser;
import au.shamsutdinov.artur.parser.parsers.EmoticonsParser;
import au.shamsutdinov.artur.parser.parsers.LinksParser;
import au.shamsutdinov.artur.parser.parsers.MentionsParser;
import au.shamsutdinov.artur.parser.parsers.WebsiteTitleRetriever;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements Observer<String> {

    private EditText messageText;
    private TextView resultText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageText = (EditText) this.findViewById(R.id.messageText);
        resultText = (TextView) this.findViewById(R.id.resultText);
        progressBar = (ProgressBar) this.findViewById(R.id.progress);
    }

    public void parse(View view) {
        ElementParser[] parsers = {new EmoticonsParser(), new LinksParser(new WebsiteTitleRetriever()), new MentionsParser()};
        MessageParser parser = new MessageParser(parsers, new JsonSerializer());
        String text = messageText.getText().toString();
        hideSoftKeyboard(MainActivity.this, messageText);
        progressBar.setVisibility(View.VISIBLE);
        parser.parse(text)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    public static void hideSoftKeyboard (Activity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    @Override
    public void onCompleted() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onError(Throwable e) {
        TextView resultText = (TextView) this.findViewById(R.id.resultText);
        resultText.setText(e.getMessage());
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onNext(String result) {;
        resultText.setText(result);
    }
}
