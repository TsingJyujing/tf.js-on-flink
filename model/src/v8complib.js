// V8 engine compatible library

import * as tf from '@tensorflow/tfjs';

/**
 *
 * @param jsonString String like [[1,2,3,4]]
 */
function createMatrix(jsonString, row=-1, col=-1) {
    const value = JSON.parse(
        jsonString
    );

    const row = value.length;
    const col = value.map(
            row = > row.length
    ).reduce(
            (a, b) = > Math.max(a, b)
    );
    tf.tensor2d(, [4, 1]);
}