package com.github.tsingjyujing.tof;

import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8Array;
import com.github.tsingjyujing.tof.util.FileUtil;

import java.io.IOException;

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
    public static void runScript(String script) {
        final NodeJS node = NodeJS.createNodeJS();
        try {
            node.getRuntime().executeScript(script);
            node.getRuntime().executeScript("const predict = Function(\"return this\")().predict;const isLoaded = Function(\"return this\")().isLoaded;");
            Thread.sleep(5000);
            V8Array data = new V8Array(node.getRuntime());
            double[] dataRaw = new double[]{
                    14.23, 1.71, 2.43, 15.6, 127, 2.8, 3.06, .28, 2.29, 5.64, 1.04, 3.92, 1065
            };
            for (double x : dataRaw) {
                data.push(x);
            }
            System.out.println(node.getRuntime().executeBooleanFunction("isLoaded",null));
            V8Array result = node.getRuntime().executeArrayFunction("predict", new V8Array(node.getRuntime()).push(new V8Array(node.getRuntime()).push(data)));
            System.out.println(result);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            node.release();
        }
    }
}
