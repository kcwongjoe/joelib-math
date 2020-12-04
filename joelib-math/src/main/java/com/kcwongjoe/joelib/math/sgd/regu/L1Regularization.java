package com.kcwongjoe.joelib.math.sgd.regu;

import com.kcwongjoe.joelib.JGeneralUtils;
import com.kcwongjoe.joelib.math.CumulativeAverage;

/**
 * L1 Regularization - lamba / n * (|a0| + |a1| + ... + |an|)
 **/
public class L1Regularization implements Regularization {

    public double lamba = 0;
    public double[] normalizeTheta = null;

    // region Constructor

    /**
     * Constructor
     */
    public L1Regularization() {
        this.lamba = 0.3;
    }

    /**
     * Constructor
     * @param lamba Lamba
     */
    public L1Regularization(double lamba) {
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
        return "L1 regularization: lamba = " + this.lamba + ", normalizeTheta = " + JGeneralUtils.toString(this.normalizeTheta);
    }

    @Override
    public double loss(double[] theta) {
        // Throw Exception
        if (this.normalizeTheta != null && theta.length != this.normalizeTheta.length)
            throw new IllegalArgumentException("The length of parameters (" + theta.length + ") must be = " + this.normalizeTheta.length + " or setParametersNormalization() as null. ");

        // Calculate
        CumulativeAverage average = new CumulativeAverage();
        for (int i = 0; i < theta.length; i++) {
            if (this.normalizeTheta == null) {
                average.add(this.lamba * Math.abs(theta[i]));
            } else {
                average.add(this.lamba * Math.abs(theta[i] / this.normalizeTheta[i]));
            }
        }
        return this.lamba * average.getMean();
    }

    @Override
    public double derivativeLoss(double[] theta) {

        // Calculate
        CumulativeAverage average = new CumulativeAverage();
        for (int i = 0; i < theta.length; i++) {
            if (this.normalizeTheta == null) {
                average.add(theta[i]);
            } else {
                average.add(this.normalizeTheta[i]);
            }
        }

        if (average.getMean() > 0)
            return lamba;
        else if (average.getMean() < 0)
            return -lamba;
        else
            return 0;
    }
}
