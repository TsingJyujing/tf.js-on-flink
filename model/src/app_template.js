import "@babel/polyfill";
import * as tf from '@tensorflow/tfjs';

/**
 * Convert an array to tensorflow matrix
 * @param {Array} value array like [[1,2,3,4]] 
 * @param {Number} row row count of the matrix, if <=0 means auto
 * @param {Number} col column count of the matrix, if <=0 means auto
 */
function arrayToMatrix(value, row = -1, col = -1) {
    if (row <= 0) {
        row = value.length;
    }
    if (col <= 0) {
        col = value.map(
            row => row.length
        ).reduce(
            (a, b) => Math.max(a, b)
        );
    }
    return tf.tensor2d(value, [row, col]);
}

let modelLoaded = undefined;

const GLOBAL = Function("return this")();

GLOBAL.isLoaded = function() {
    return typeof modelLoaded !== "undefined";
}

/**
 * Predict Function
 * @param {*} input_data Input data (maybe a matrix)
 */
GLOBAL.predict = function (input_data) {
    if (!isLoaded()) {
        console.log("Model not prepared yet.")
        throw Error("Model not prepared yet.");
    }
    const array = [];
    modelLoaded.predict(
        arrayToMatrix(
            input_data
        )
    ).dataSync().forEach(
        x => {
            array.push(x)
        }
    );
    return [array];
}

tf.models.modelFromJSON(
    __MODEL_JSON__
).then(
    model => {
        model.setWeights([
            __WEIGHTS__
        ]);
        modelLoaded = model;
        console.log("Model loaded successfully.")
    }
).catch(
    err => {
        console.error("Error while loading model:");
        console.error(err);
    }
)

