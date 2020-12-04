package com.kcwongjoe.joelib.math.func;

/**
 * Gaussian function f(x) = a * exp( -(x - b)^2 / (2 * c^2) )
 */
public class GaussianFunction {

    public double a;
    public double b;
    public double c;


    /**
     * f(x) = a * exp( -(x - b)^2 / (2 * c^2) )
     *
     * @param a Constant a
     * @param b Constant b
     * @param c Constant c
     */
    public GaussianFunction(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    // region calculate

    /**
     * Calculate gaussian function with variable x
     *
     * @param x x
     * @return Return f(x)
     */
    public double calculate(double x) {
        double xb = x - b;
        return a * Math.exp(-xb * xb / (2 * c * c));
    }

    /**
     * Calculate gaussian function with variable x
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
        return derivativeParameters(x, Double.NaN);
    }

    /**
     * Calculate the derivative of the paratmeters on x
     *
     * @param x x
     * @return Return the derivative of a, b and c as new double[data][]{ df(x)/da, df(x)/db, df(x)/dc}
     */
    public double[][] derivativeParameters(double[] x){
        double[][] result = new double[x.length][];

        for (int i=0;i<result.length;i++){
            result[i] = derivativeParameters(x[i]);
        }

        return result;
    }

    /**
     * Calculate the derivative of the paratmeters on x
     *
     * @param x  x
     * @param fx f(x), If fx is NaN, the method will calculate fx.
     * @return Return the derivative of a, b and c as new double[]{ df(x)/da, df(x)/db, df(x)/dc}
     */
    public double[] derivativeParameters(double x, double fx) {

        if (Double.isNaN(fx))
            fx = calculate(x);

        double xb = x - b;

        double da = fx / a;
        double db = 1 / (c * c) * (x - b) * fx;
        double dc = (xb * xb) * (1 / (c * c * c)) * fx;

        return new double[]{da, db, dc};
    }

    /**
     * Calculate the derivative of the paratmeters on x
     *
     * @param x x
     * @param fx f(x)
     * @return Return the derivative of a, b and c as new double[data][]{ df(x)/da, df(x)/db, df(x)/dc}
     */
    public double[][] derivativeParameters(double[] x, double[] fx){
        double[][] result = new double[x.length][];

        for (int i=0;i<result.length;i++){
            result[i] = derivativeParameters(x[i], fx[i]);
        }

        return result;
    }

    //endregion
}
