package com.pasha.efebudak.stringcounter;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by efebudak on 02/11/2016.
 */

public class StringCounterUtils {

    private static final int TEN = 10;

    public static String findTenthChar(final String text) {

        if (isTextSufficient(text)) {
            return "";
        } else {
            return String.valueOf(text.charAt(TEN - 1));
        }
    }

    public static String findEveryTenthChar(final String text) {

        if (isTextSufficient(text)) {
            return "";
        } else {

            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 1, totalTenthChar = text.length() / TEN; i <= totalTenthChar; i++) {

                stringBuilder.append(text.charAt(i * TEN - 1));
            }

            return stringBuilder.toString();
        }
    }

    public static void findWordCount(
            final String text,
            final CountWordsListener countWordListener) {

        if (isTextSufficient(text)) {
            countWordListener.onCountingFinish(new HashMap<String, Integer>());
        } else {
            new CountWords(countWordListener).execute(text);
        }
    }

    public static String convertHashMapToString(final HashMap<String, Integer> hashMap) {

        StringBuilder stringBuilder = new StringBuilder();
        Iterator iterator = hashMap.entrySet().iterator();

        Map.Entry entry;
        String keyValueSeparator = ": ";
        String newLine = "\n";
        while (iterator.hasNext()) {

            entry = (Map.Entry) iterator.next();
            stringBuilder
                    .append(entry.getKey())
                    .append(keyValueSeparator)
                    .append(entry.getValue())
                    .append(newLine);
        }

        return stringBuilder.toString();
    }

    private static boolean isTextSufficient(final String text) {
        return (text == null || TextUtils.isEmpty(text) || text.length() < TEN);
    }

    private static class CountWords extends AsyncTask<String, Void, HashMap<String, Integer>> {

        private CountWordsListener mCountWordsListener;

        public CountWords(CountWordsListener countWordsListener) {
            mCountWordsListener = countWordsListener;
        }

        protected HashMap<String, Integer> doInBackground(String... strings) {

            String text = strings[0];
            text = text.replaceAll("[\r\n\t]+", " ");
            String[] splitString = text.split("\\s+");

            HashMap<String, Integer> hashMapWordCount = new HashMap<>();
            String lowerCaseString;
            for (String aSplitString : splitString) {

                lowerCaseString = aSplitString.toLowerCase();
                if (hashMapWordCount.containsKey(lowerCaseString)) {
                    hashMapWordCount.put(lowerCaseString, hashMapWordCount.get(lowerCaseString) + 1);
                } else {
                    hashMapWordCount.put(lowerCaseString, 1);
                }
            }

            return hashMapWordCount;
        }

        protected void onPostExecute(HashMap<String, Integer> resultString) {
            mCountWordsListener.onCountingFinish(resultString);
        }

    }

    public interface CountWordsListener {
        void onCountingFinish(HashMap<String, Integer> hashMap);
    }
}
