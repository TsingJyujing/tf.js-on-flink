package com.github.tsingjyujing.tof.util;

import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

import java.io.PrintStream;

/**
 * Print function, output data to specified PrintStream
 */
public class V8PrintFunction implements JavaVoidCallback {

    private final PrintStream printStream;

    /**
     * Initialize by default PrintStream (to stdout)
     */
    public V8PrintFunction() {
        this(System.out);
    }

    /**
     * Specified PrintStream
     *
     * @param printStream PrintStream
     */
    public V8PrintFunction(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void invoke(V8Object receiver, V8Array parameters) {
        final int N = parameters.length();
        for (int i = 0; i < N; i++) {
            printStream.print(parameters.get(i));
        }
        printStream.print("\n");
    }
}
