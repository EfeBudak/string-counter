package com.pasha.efebudak.stringcounter;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by efebudak on 02/11/2016.
 */

public class WebInteractor {

    private static final String BASE_URL = "http://www.reddit.com";

    private static WebInteractor sWebInteractor;

    private Call<ResponseBody> mSupportResponseBodyCall;
    private StringCounterCallback mStringCounterCallbackTenthChar;
    private StringCounterCallback mStringCounterCallbackEveryTenthChar;
    private StringCounterCallback mStringCounterCallbackWordCount;
    private WebInteractorListener mWebInteractorListener;

    private WebInteractor() {

        final Retrofit sRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(new OkHttpClient())
                .build();

        initStringCounterCallbacks();

        final RedditService redditService = sRetrofit.create(RedditService.class);

        mSupportResponseBodyCall = redditService.getAnddevResponseBodyCall();
    }

    private void initStringCounterCallbacks() {

        mStringCounterCallbackTenthChar = new StringCounterCallback(RequestType.TENTH_CHAR);
        mStringCounterCallbackEveryTenthChar = new StringCounterCallback(RequestType.EVERY_TENTH_CHAR);
        mStringCounterCallbackWordCount = new StringCounterCallback(RequestType.WORD_COUNT);
    }

    public static WebInteractor getInstance() {

        if (sWebInteractor == null) {
            sWebInteractor = new WebInteractor();
        }

        return sWebInteractor;
    }

    public void setWebInteractorListener(final WebInteractorListener webInteractorListener) {
        mWebInteractorListener = webInteractorListener;
    }

    public void runTenthCharRequest() {

        enqueueSupportResponseBodyCall(mStringCounterCallbackTenthChar);
    }

    public void runEveryTenthCharRequest() {

        enqueueSupportResponseBodyCall(mStringCounterCallbackEveryTenthChar);
    }

    public void runWordCountRequest() {

        enqueueSupportResponseBodyCall(mStringCounterCallbackWordCount);
    }

    private void enqueueSupportResponseBodyCall(StringCounterCallback StringCounterCallback) {

        mSupportResponseBodyCall.clone().enqueue(StringCounterCallback);
    }

    private enum RequestType {TENTH_CHAR, EVERY_TENTH_CHAR, WORD_COUNT}

    private class StringCounterCallback implements Callback<ResponseBody> {

        private RequestType mRequestType;

        private StringCounterCallback(RequestType requestType) {

            mRequestType = requestType;
        }

        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            String responseBodyString = null;
            try {
                responseBodyString = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            switch (mRequestType) {

                case TENTH_CHAR:

                    mWebInteractorListener.onFindTenthCharResponse(
                            StringCounterUtils.findTenthChar(responseBodyString));
                    break;

                case EVERY_TENTH_CHAR:

                    mWebInteractorListener.onFindEveryTenthCharResponse(
                            StringCounterUtils.findEveryTenthChar(responseBodyString));
                    break;

                case WORD_COUNT:

                    StringCounterUtils.findWordCount(
                            responseBodyString,
                            new StringCounterUtils.CountWordsListener() {

                                @Override
                                public void onCountingFinish(HashMap<String, Integer> resultWordCounts) {
                                    mWebInteractorListener.onWordCountResponse(resultWordCounts);
                                }
                            });
                    break;

                default:
                    break;
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
        }
    }

    public interface WebInteractorListener {

        void onFindTenthCharResponse(String resultString);

        void onFindEveryTenthCharResponse(String resultString);

        void onWordCountResponse(HashMap<String, Integer> resultWordCounts);

    }

}
