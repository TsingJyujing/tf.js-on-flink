package com.github.tsingjyujing.tof;

import com.eclipsesource.v8.V8;
import com.github.tsingjyujing.tof.util.FileUtil;
import com.github.tsingjyujing.tof.util.V8PrintFunction;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * To run JavaScript on JVM
 */
public class JavaScriptRunner {

    /**
     * First arg should be filename
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        runScript(
                FileUtil.readRawTextFile(
                        args[0]
                )
        );
    }

    /**
     * Create an engine and run specified script
     *
     * @param script javascript script
     * @throws IOException
     */
    public static void runScript(String script) throws IOException, InterruptedException {
        final V8 runtime = V8.createV8Runtime();
        runtime.registerJavaMethod(new V8PrintFunction(), "stdout_print");
        runtime.registerJavaMethod(new V8PrintFunction(System.err), "stderr_print");
        runtime.executeScript(FileUtil.readTextResource(
                "v8-polyfill.js"
        ));
        runtime.executeScript(script);
        Thread.sleep(1000);
        runtime.release();
    }
}
