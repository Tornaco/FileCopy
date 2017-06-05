package com.prac;

/**
 * Created @2017/6/3 18:18
 */
public interface ProgressListener {
    void onStart();

    void onProgressUpdate(int progress);

    void onComplete();

    void onError();
}
