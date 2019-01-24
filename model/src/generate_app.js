import "@babel/polyfill";
import * as tf from '@tensorflow/tfjs';
import fs from "fs";

global.fetch = require("node-fetch");

const modelPath = "http://fileserver.shinonomelab.com/tof/models/wine/v1.0.0/model.json";

tf.loadModel(
    modelPath
).then(
    modelGet => {
        fs.readFile("src/app_template.js", (err, data) => {
            if (err) {
                console.error("Error while reading template.");
                console.error(err);
            } else {
                const fileText = data.toString().replace(
                    "__MODEL_JSON__",
                    modelGet.toJSON()
                ).replace(
                    "__WEIGHTS__",
                    modelGet.getWeights().map(w => {
                        const array = [];
                        w.dataSync().forEach(x => array.push(x));
                        return `tf.tensor(${JSON.stringify(array)},${JSON.stringify(w.shape)})`
                    }).join(",\n")
                );
                fs.writeFile("src/app.js", fileText, err => {
                    if (err) {
                        console.error("Error while writing application.");
                        console.error(err);
                    }
                })
            }
        })
    }
).catch(
    ex => {
        console.error("Error while loading model.");
        console.error(ex)
    }
);