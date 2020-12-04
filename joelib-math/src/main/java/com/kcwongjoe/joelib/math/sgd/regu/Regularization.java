package com.kcwongjoe.joelib.math.sgd.regu;

/**
 * Regularization interface for Stochastic Gradient Descent
 **/
public interface Regularization {

    double lamba = 0;
    double[] normalizeTheta = null;

    /**
     * The normalization can help to reduce the huge impact of outer weight.
     *
     * @param thetaCeil The maximum value of the parameters. Set it as null to disabled.
     */
    void setThetaNormalization(double[] thetaCeil);

    /**
     * Calculate the term of regularization in loss function
     *
     * @param theta The function parameters
     * @return Return the result of the loss function
     */
    double loss(double[] theta);

    /**
     * Calculate the term of regularization in derivative of loss function
     *
     * @param theta The function parameters
     * @return Retrun the result of the derivative of loss function
     */
    double derivativeLoss(double[] theta);
}
