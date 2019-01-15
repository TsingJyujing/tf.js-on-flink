package com.github.tsingjyujing.tof;

import com.github.tsingjyujing.tof.util.FileUtil;
import org.junit.Test;

import java.io.IOException;

public class JavaScriptRunnerTest {

    @Test
    public void runScriptTest() throws Exception {
        JavaScriptRunner.runScript(
                FileUtil.readRawTextFile(
                        "../model/target/ml.bundle.js"
                )
        );
    }

}