package com.github.tsingjyujing.tof.engine

import breeze.linalg.DenseMatrix
import com.eclipsesource.v8.V8Array
import com.github.tsingjyujing.tof.tensorflow.{IModel, MatrixConverter}

/**
  * Dense matrix model
  *
  * @param initScript
  */
class DenseMatrixModel(initScript: String) extends
    Engine(initScript) with
    IModel[DenseMatrix[Double], DenseMatrix[Double]] {

    getEngineCore.executeScript("let predict = Function(\"return this\")().predict;")
    getEngineCore.executeScript("let isLoaded = Function(\"return this\")().isLoaded;")

    // Waiting for model loaded.
    while (! {
        try {
            getEngineCore.executeBooleanFunction("isLoaded", null)
        } catch {
            case ex: Throwable =>
                ex.printStackTrace()
                false
        }
    }) {
        Thread.sleep(200)
    }

    val matrixConverter = new MatrixConverter(getEngineCore)

    /**
      * Predict the input data
      *
      * @param in
      * @return
      */
    override def predict(in: DenseMatrix[Double]): DenseMatrix[Double] = {
        val result = getEngineCore.executeArrayFunction(
            "predict",
            new V8Array(getEngineCore).push(
                matrixConverter.denseMatrixToV8Object(in)
            )
        )
        matrixConverter.v8ArrayToDenseMatrix(
            result
        )
    }
}
