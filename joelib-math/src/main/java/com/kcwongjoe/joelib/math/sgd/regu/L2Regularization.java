package com.kcwongjoe.joelib.math.sgd.regu;

import com.kcwongjoe.joelib.JGeneralUtils;
import com.kcwongjoe.joelib.math.CumulativeAverage;

/**
 * L2 Regularization - lamba / n * (a0^2 + a1^2 + ... + an^2)
 **/
public class L2Regularization implements Regularization {

    public double lamba = 0;
    private double[] normalizeTheta = null;

    // region Constructor

    /**
     * Constructor
     */
    public L2Regularization(){
        this.lamba = 0.3;
    }

    /**
     * Constructor
     * @param lamba Lamba must be &gt;= 0
     */
    public L2Regularization(double lamba) {
        if (lamba < 0)
            throw new IllegalArgumentException("lamba (" + lamba + ") must be >= 0.");

        this.lamba = lamba;
    }

    // endregion

    // region getter and setter

    @Override
    public void setThetaNormalization(double[] thetaCeil) {
        this.normalizeTheta = thetaCeil;
    }

    public double[] getThetaNormalization(){
        return this.normalizeTheta;
    }

    // endregion

    @Override
    public String toString(){
        return "L2 regularization: lamba = " + this.lamba + ", normalizeTheta = " + JGeneralUtils.toString(this.normalizeTheta);
    }

    @Override
    public double loss(double[] theta) {
        // Throw Exception
        if (theta.length != this.normalizeTheta.length)
            throw new IllegalArgumentException("The length of parameters (" + theta.length + ") must be = " + this.normalizeTheta.length + " or setParametersNormalization() as null. ");

        // Calculate
        CumulativeAverage average = new CumulativeAverage();
        for (int i = 0; i < theta.length; i++) {
            if (this.normalizeTheta == null) {
                average.add(theta[i] * theta[i]);
            } else {
                double value = theta[i] / this.normalizeTheta[i];
                average.add(value * value);
            }
        }
        return this.lamba * average.getMean();
    }

    @Override
    public double derivativeLoss(double[] theta) {
        // Throw Exception
        if (this.normalizeTheta != null && theta.length != this.normalizeTheta.length)
            throw new IllegalArgumentException("The length of parameters (" + theta.length + ") must be = " + this.normalizeTheta.length + " or setParametersNormalization() as null. ");

        // Calculate
        CumulativeAverage average = new CumulativeAverage();
        for (int i = 0; i < theta.length; i++) {
            if (this.normalizeTheta == null) {
                average.add(theta[i]);
            } else {
                average.add(theta[i] / this.normalizeTheta[i]);
            }
        }
        return 2 * this.lamba * average.getMean();
    }
}
