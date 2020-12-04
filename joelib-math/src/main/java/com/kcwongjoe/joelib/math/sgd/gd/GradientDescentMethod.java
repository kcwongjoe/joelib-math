package com.kcwongjoe.joelib.math.sgd.gd;

/**
 * Interface for Gradient Descent Method
 **/
public interface GradientDescentMethod {

    /**
     * Reset internal coefficient
     *
     * @param length Number of parameters
     */
    void Reset(int length);

    /**
     * Calculate Theta
     *
     * @param index           Iteration
     * @param learningRate    Learning Rate
     * @param theta           Theta
     * @param derivativeTheta The derivative of Theta
     * @return Return the new theta
     */
    double calculateTheta(int index, double learningRate, double theta, double derivativeTheta);

    /**
     * Calculate Theta
     *
     * @param index                     Iteration
     * @param learningRate              Learning Rate
     * @param theta                     Theta
     * @param derivativeTheta           The derivative of Theta
     * @param updateInternalCoefficient Update the internal coefficient?
     * @return Return the new theta
     */
    double calculateTheta(int index, double learningRate, double theta, double derivativeTheta, boolean updateInternalCoefficient);

    /**
     * Calculate Thetas
     *
     * @param learningRate  Learning Rate
     * @param theta Theta
     * @param derivativeTheta The derivative of Theta
     * @return Return the new theta
     */
    double[] calculateTheta(double learningRate, double[] theta, double[] derivativeTheta);
}
