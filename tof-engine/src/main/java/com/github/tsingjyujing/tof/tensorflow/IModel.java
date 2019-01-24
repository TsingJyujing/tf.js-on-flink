package com.github.tsingjyujing.tof.tensorflow;

/**
 * Model interface
 *
 * @param <InputType>  input type of the model
 * @param <OutputType> output type of the model
 */
public interface IModel<InputType, OutputType> {

    /**
     * Predict the input data
     *
     * @param in
     * @return
     */
    OutputType predict(InputType in);

}
