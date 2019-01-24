package com.github.tsingjyujing.tof.tensorflow

import breeze.linalg.DenseMatrix
import com.eclipsesource.v8.{V8, V8Array}

/**
  * Convert data from DMatrix to V8Object
  */
class MatrixConverter(engine: V8) {

    /**
      * Convert arrays to DenseMatrix
      *
      * @param matrixInfo
      * @param rowSize
      * @param colSize
      * @return
      */
    def arraysToDenseMatrix(matrixInfo: Array[Array[Double]], rowSize: Int = -1, colSize: Int = -1): DenseMatrix[Double] = {
        val shape: (Int, Int) = if (rowSize <= 0 || colSize <= 0) {
            (matrixInfo.length, matrixInfo.map(_.length).max)
        } else {
            (rowSize, colSize)
        }
        val mat = new DenseMatrix[Double](shape._1, shape._2)
        matrixInfo.zipWithIndex.foreach(
            rowInfo => {
                val rowId = rowInfo._2
                rowInfo._1.zipWithIndex.foreach(
                    colInfo => {
                        mat(rowId, colInfo._2) = colInfo._1
                    }
                )
            }
        )
        mat
    }

    def v8ArrayToDenseMatrix(matrixInfo:V8Array,rowSize: Int = -1, colSize: Int = -1):DenseMatrix[Double] = {
        val shape: (Int, Int) = if (rowSize <= 0 || colSize <= 0) {
            (matrixInfo.length, {
                (0 until matrixInfo.length()).map(i=>{
                    matrixInfo.getArray(i).length()
                }).max
            })
        } else {
            (rowSize, colSize)
        }
        val mat = new DenseMatrix[Double](shape._1, shape._2)
        (0 until matrixInfo.length()).map(i=>{
            matrixInfo.getArray(i)
        }).zipWithIndex.foreach(
            rowInfo => {
                val rowId: Int = rowInfo._2
                val rowData: V8Array = rowInfo._1
                (0 until rowData.length()).foreach(
                    colId=>{
                        mat(rowId,colId) = rowData.getDouble(colId).asInstanceOf[Double]
                    }
                )
            }
        )
        mat
    }

    /**
      * Convert dense matrix into JavaScript array format
      *
      * @param data dense matrix
      * @return
      */
    def denseMatrixToV8Object(data: DenseMatrix[Double]): V8Array = {
        val array = new V8Array(engine)
        (0 until data.rows).foreach(rowId => {
            val row = new V8Array(engine)
            (0 until data.cols).foreach(colId => {
                row.push(data(rowId, colId))
            })
            array.push(row)
        })
        array
    }
}
