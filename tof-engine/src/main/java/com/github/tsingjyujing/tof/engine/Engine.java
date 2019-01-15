package com.github.tsingjyujing.tof.engine;

import com.eclipsesource.v8.V8;
import com.github.tsingjyujing.tof.util.FileUtil;
import com.github.tsingjyujing.tof.util.V8PrintFunction;

import java.io.IOException;

/**
 * JavaScript Engine
 */
public class Engine {

    public V8 getEngineCore() {
        return engineCore;
    }

    private final V8 engineCore = createV8Core();

    public Engine() throws Exception {
    }

    public Engine(String initScript) throws Exception {
        engineCore.executeScript(
                initScript
        );
    }


    /**
     * Create a new core
     *
     * @return
     * @throws IOException
     */
    private static V8 createV8Core() throws IOException {
        final V8 v8 = V8.createV8Runtime();
        v8.registerJavaMethod(new V8PrintFunction(), "stdout_print");
        v8.registerJavaMethod(new V8PrintFunction(System.err), "stderr_print");
        v8.executeScript(FileUtil.readTextResource(
                "v8-polyfill.js"
        ));
        return v8;
    }

}
