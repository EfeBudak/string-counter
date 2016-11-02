package com.pasha.efebudak.stringcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements WebInteractor.WebInteractorListener {

    private static final String KEY_TENTH_CHAR = "keyTenthChar";
    private static final String KEY_EVERY_TENTH_CHAR = "keyEveryTenthChar";
    private static final String KEY_WORD_COUNTS_STRING = "keyWordCountsString";
    private static final String KEY_WORD_COUNTS_MAP = "keyWordCountsMap";

    private WebInteractor mWebInteractor;
    private HashMap<String, Integer> mHashMapWordCounts;

    @BindView(R.id.activity_main_text_view_tenth_char)
    TextView mTextViewTenthChar;
    @BindView(R.id.activity_main_text_view_every_tenth_char)
    TextView mTextViewEveryTenthChar;
    @BindView(R.id.activity_main_text_view_word_counter)
    TextView mTextViewWordCounter;
    @BindView(R.id.activity_main_button_run_requests)
    Button mButtonRunRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mWebInteractor = WebInteractor.getInstance();
        mWebInteractor.setWebInteractorListener(this);

        mButtonRunRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runStringRequest();
            }
        });

        if (savedInstanceState != null) {

            mTextViewTenthChar.setText(savedInstanceState.getString(KEY_TENTH_CHAR));
            mTextViewEveryTenthChar.setText(savedInstanceState.getString(KEY_EVERY_TENTH_CHAR));
            mTextViewWordCounter.setText(savedInstanceState.getString(KEY_WORD_COUNTS_STRING));
            mHashMapWordCounts = (HashMap<String, Integer>) savedInstanceState.getSerializable(KEY_WORD_COUNTS_MAP);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(KEY_TENTH_CHAR, mTextViewTenthChar.getText().toString());
        outState.putString(KEY_EVERY_TENTH_CHAR, mTextViewEveryTenthChar.getText().toString());
        outState.putString(KEY_WORD_COUNTS_STRING, mTextViewWordCounter.getText().toString());
        outState.putSerializable(KEY_WORD_COUNTS_MAP, mHashMapWordCounts);

    }

    private void runStringRequest() {

        mWebInteractor.runTenthCharRequest();
        mWebInteractor.runEveryTenthCharRequest();
        mWebInteractor.runWordCountRequest();
    }

    @Override
    public void onFindTenthCharResponse(String resultString) {
        mTextViewTenthChar.setText(resultString);
    }

    @Override
    public void onFindEveryTenthCharResponse(String resultString) {
        mTextViewEveryTenthChar.setText(resultString);
    }

    @Override
    public void onWordCountResponse(HashMap<String, Integer> resultWordCounts) {

        mHashMapWordCounts = resultWordCounts;
        mTextViewWordCounter.setText(StringCounterUtils.convertHashMapToString(resultWordCounts));
    }
}
