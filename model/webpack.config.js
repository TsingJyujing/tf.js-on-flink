const path = require("path");
const webpack = require('webpack');

module.exports = {
    entry: './src/app.js',
    output: {
        path: path.resolve(__dirname, 'target'),
        filename: 'ml.bundle.js'
    },
    performance: {
        hints: false
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: [
                            'es2015'
                        ],
                        plugins: [
                            'transform-es2015-typeof-symbol',
                            "transform-runtime"
                        ]
                    }
                }
            }
        ]
    },
    mode: 'production',
    // externals: {
    //     'node-fetch': 'fetch'
    // }
}
