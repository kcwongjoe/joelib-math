package com.kcwongjoe.joelib.math.sgd.gd;

/**
 * Gradient descent
 * <p>
 * Created by JOE Wong on 6/8/2020
 **/
public class StandardGradientDescent implements GradientDescentMethod {

    // region Constructor

    /**
     * Constructor
     */
    public StandardGradientDescent() {

    }

    // endregion

    @Override
    public void Reset(int length) {

    }

    // region calculateTheta

    @Override
    public double calculateTheta(int index, double learningRate, double theta, double derivativeTheta) {
        return calculateTheta(index, learningRate, theta, derivativeTheta, true);
    }

    @Override
    public double calculateTheta(int index, double learningRate, double theta, double derivativeTheta, boolean updateInternalCoefficient){

        // Update theta
        return theta - learningRate * derivativeTheta;
    }

    @Override
    public double[] calculateTheta(double learningRate, double[] theta, double[] derivativeTheta){

        double[] newTheta = new double[theta.length];
        for (int i = 0; i < theta.length; i++) {
            newTheta[i] = calculateTheta(i, learningRate, theta[i], derivativeTheta[i]);
        }

        return newTheta;
    }


    // endregion
}

