package com.github.tsingjyujing.tof.engine;

import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8;

/**
 * JavaScript Engine
 */
public class Engine {

    public V8 getEngineCore() {
        return engineCore.getRuntime();
    }

    private final NodeJS engineCore = NodeJS.createNodeJS();

    public Engine() throws Exception {
    }

    public Engine(String initScript) throws Exception {
        engineCore.getRuntime().executeScript(
                initScript
        );

    }
}
