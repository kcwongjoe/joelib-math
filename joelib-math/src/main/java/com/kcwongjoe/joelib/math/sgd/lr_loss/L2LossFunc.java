package com.kcwongjoe.joelib.math.sgd.lr_loss;

/**
 * L2 Loss Function - Least Square Errors = ((y - f(x))^2) / 2
 **/

public class L2LossFunc implements LossFunction {

    @Override
    public double loss(double y, double fx) {
        double yMinusfx = y - fx;

        return (yMinusfx * yMinusfx) / 2;
    }

    @Override
    public double derivativeLoss(double y, double fx, double dfdtheta){
        return -(y - fx) * dfdtheta;
    }
}
