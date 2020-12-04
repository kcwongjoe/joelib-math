package com.kcwongjoe.joelib.math.sgd.lr_loss;

/**
 * L1 Loss Function - Least Absolute Deviations = |y - f(x)|
 **/
public class L1LossFunc implements LossFunction {

    @Override
    public double loss(double y, double fx) {
        return Math.abs(y - fx);
    }

    @Override
    public double derivativeLoss(double y, double fx, double dfdtheta) {
        /**
         * d|x|/dx  = 1 if x > 0, -1 if x <0. Not exist on x = 0,
         * So if x = 0, we set gradient as 0.
         */

        double yMinusfx = y - fx;

        if (yMinusfx > 0)
            return -dfdtheta;
        else if (yMinusfx < 0)
            return dfdtheta;

        return 0;
    }
}
