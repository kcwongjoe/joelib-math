package com.kcwongjoe.joelib.math.sgd.lr_loss;

/**
 * Loss fucntion for Stochastic Gradient Descent
 **/
public interface LossFunction {

    /**
     * Calculate the loss
     *
     * @param y The true value on x
     * @param fx The predict value on x
     * @return Return the loss
     */
    double loss(double y, double fx);

    /**
     * Calculate the derivative of loss function
     * @param y The true value on x
     * @param fx The predict value on x
     * @param dfdtheta df(x)/dtheta on x where theta is the parameter in the funciton.
     * @return Return the derivative of loss
     */
    double derivativeLoss(double y, double fx, double dfdtheta);
}
