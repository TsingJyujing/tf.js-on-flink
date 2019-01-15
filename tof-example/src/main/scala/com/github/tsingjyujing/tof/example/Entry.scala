package com.github.tsingjyujing.tof.example

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.functions.source.{FromElementsFunction, SourceFunction}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
  * Entry of Flink program
  */
object Entry {
    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        val configFromArg = ParameterTool.fromArgs(args)
        env.getConfig.setGlobalJobParameters(
            configFromArg
        )


        env.execute("TensorFlow Example")
    }
}
