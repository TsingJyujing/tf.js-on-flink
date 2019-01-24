package com.github.tsingjyujing.tof.util;

import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

/**
 * Sleep function
 */
public class V8SleepFunction implements JavaVoidCallback {

    @Override
    public void invoke(V8Object receiver, V8Array parameters) {
        try {
            Thread.sleep(Math.round(parameters.getDouble(0)));
        } catch (InterruptedException e) {
            // fixme print details to log
        }
    }
}
