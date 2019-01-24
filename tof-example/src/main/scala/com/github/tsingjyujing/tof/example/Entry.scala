package com.github.tsingjyujing.tof.example

import com.github.tsingjyujing.tof.engine.DenseMatrixModel
import com.github.tsingjyujing.tof.util.FileUtil
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.ProcessAllWindowFunction
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow
import org.apache.flink.util.Collector

import scala.io.Source

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

        env.setParallelism(1)

        val data = Source.fromFile("model/data/wine.csv").getLines().flatMap(line => try {
            val arr = line.replace("\n", "").split(",")
            val label = arr(0).toInt - 1
            val data = arr.tail.map(_.toDouble)
            Some((label, data))
        } catch {
            case ex: Throwable => None
        }).toIndexedSeq

        env.fromCollection(
            data
        ).map(new RichMapFunction[(Int, Array[Double]), Boolean] {
            lazy val model: DenseMatrixModel = new DenseMatrixModel(
                FileUtil.readTextResource("model/ml.bundle.js")
            )

            override def map(value: (Int, Array[Double])): Boolean = {
                val label = value._1
                val data = value._2
                val result = model.predict(
                    model.matrixConverter.arraysToDenseMatrix(
                        Array(data), 1, data.length
                    )
                )
                val predictLabel = result.data.zipWithIndex.maxBy(_._1)._2
                predictLabel == label
            }
        }).countWindowAll(10).process(
            new ProcessAllWindowFunction[Boolean, String, GlobalWindow]() {
                override def process(context: Context, elements: Iterable[Boolean], out: Collector[String]): Unit = out.collect({
                    val acc = elements.count(x => x) * 100.0 / elements.size
                    s"Accuracy of batch 10 elements: ${acc}%"
                })
            }
        ).print()

        env.execute("TensorFlow Example")
    }
}
