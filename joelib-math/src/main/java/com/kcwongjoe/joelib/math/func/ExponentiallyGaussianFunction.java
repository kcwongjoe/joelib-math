package com.kcwongjoe.joelib.math.func;

import com.kcwongjoe.joelib.math.JMathUtilsEx;

/**
 * Exponentially Gaussian Function
 **/
public class ExponentiallyGaussianFunction {

    public double h;
    public double u;
    public double sigma;
    public double tau;

    private double sqrtPiOrder2;
    private double OneOverSqrt2;

    /**
     * Constructor
     * @param h h
     * @param u u
     * @param sigma sigma
     * @param tau tau
     */
    public ExponentiallyGaussianFunction(double h, double u, double sigma, double tau) {
        this.h = h;
        this.u = u;
        this.sigma = sigma;
        this.tau = tau;

        // Init constant
        this.sqrtPiOrder2 = Math.sqrt(JMathUtilsEx.PI_ORDER_2);
        this.OneOverSqrt2 = 1 / Math.sqrt(2);
    }

    // region calculate

    /**
     * Calculate
     *
     * @param x x
     * @return Return f(x)
     */
    public double calculate(double x) {

        double factor = h * sigma / tau * sqrtPiOrder2;

        double sigmaOverTau = sigma / tau;
        double expTerm = Math.exp(sigmaOverTau * sigmaOverTau / 2 - (x - u) / tau);
        double erfcTerm = GaussErrorFunction.erfc(OneOverSqrt2 * (sigma / tau - (x - u) / sigma));

        return factor * expTerm * erfcTerm;
    }

    /**
     * Calculate
     *
     * @param x x
     * @return Return f(x)
     */
    public double[] calculate(double[] x) {
        double[] result = new double[x.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = calculate(x[i]);
        }

        return result;
    }

    //endregion

    // region derivativeParameters

    /**
     * Calculate the derivative of the paratmeters on x
     *
     * @param x x
     * @return Return the derivative of a, b and c as new double[]{ df(x)/da, df(x)/db, df(x)/dc}
     */
    public double[] derivativeParameters(double x) {
        double factor = h * sigma / tau;

        double sigmaOverTau = sigma / tau;
        double expTerm = Math.exp(sigmaOverTau * sigmaOverTau / 2 - (x - u) / tau);
        double erfcTerm = GaussErrorFunction.erfc(OneOverSqrt2 * (sigma / tau - (x - u) / sigma));
        double dErfcTerm = GaussErrorFunction.derivative(OneOverSqrt2 * (sigma / tau - (x - u) / sigma));

        double dh = sigmaOverTau * sqrtPiOrder2 * expTerm * erfcTerm;
        double du = factor * expTerm * sqrtPiOrder2 * (dErfcTerm * OneOverSqrt2 / sigma + erfcTerm / tau);
        double dSigma = expTerm * sqrtPiOrder2 * (OneOverSqrt2 * factor * dErfcTerm * (1 / tau + (x - u) / (sigma * sigma)) + erfcTerm * (h / tau + sigma / (tau * tau)));
        double dTau = -expTerm * sqrtPiOrder2 / (tau * tau) * (factor * dErfcTerm * OneOverSqrt2 * sigma + erfcTerm * (h * sigma + factor * (sigma * sigma / (2 * tau) - x + u)));

        return new double[]{dh, du, dSigma, dTau};
    }

    /**
     * Calculate the derivative of the paratmeters on x
     *
     * @param x x
     * @return Return the derivative of a, b and c as new double[data][]{ df(x)/da, df(x)/db, df(x)/dc}
     */
    public double[][] derivativeParameters(double[] x) {
        double[][] result = new double[x.length][];

        for (int i = 0; i < result.length; i++) {
            result[i] = derivativeParameters(x[i]);
        }

        return result;
    }

    //endregion
}
