package com.kcwongjoe.joelib.math.sgd.lr_decay;

/**
 * Learning Rate Decay Method interface
 **/
public interface LearningRateDecayMethod {

    /**
     * Reset
     */
    void reset();

    /**
     * Get learning rate
     *
     * @param initLearningRate    The initial learning rate when iteration = 0
     * @param currentLearningRate The current learning rate when iteration = 0
     * @param iteration           The current iteration
     * @param loss                The loss on the previous training
     * @return Return the current learning rate
     */
    double getLearningrate(double initLearningRate, double currentLearningRate, int iteration, double loss);
}
