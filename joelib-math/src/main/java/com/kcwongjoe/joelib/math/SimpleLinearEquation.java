package com.kcwongjoe.joelib.math;

import com.kcwongjoe.joelib.JGeneralUtils;

/**
 * Simple linear regression y = ax + b by OLS fitting
 **/
public class SimpleLinearEquation {

    public double slope = Double.NaN;
    public double intercept = Double.NaN;

    /**
     * Constructor
     */
    public SimpleLinearEquation() {

    }

    /**
     * Esimated parameters by Ordinary Least Square. x = new double[]{ 0 ....
     * y.length-1 } To calculate y = f(x), x will be the index of y.
     *
     * @param y y
     */
    public void OLSFit(double[] y) {
        // Throw Exception
        if (y == null)
            throw new NullPointerException("y can't be null.");

        double[] x = JGeneralUtils.createSequence(0.0, y.length - 1.0);
        OLSFit(x, y, true);
    }

    /**
     * Esimated parameters by Ordinary Least Square. x = new double[]{ 0 ....
     * y.length-1 } To calculate y = f(x), x will be the index of y.
     *
     * @param y y
     * @param intercept Intercept
     */
    public void OLSFit(double[] y, boolean intercept) {
        // Throw Exception
        if (y == null)
            throw new NullPointerException("y can't be null.");

        double[] x = JGeneralUtils.createSequence(0.0, y.length - 1.0);
        OLSFit(x, y, intercept);
    }

    /**
     * Esimated parameters by Ordinary Least Square
     *
     * @param x x
     * @param y y
     */
    public void OLSFit(double[] x, double[] y) {
        OLSFit(x, y, true);
    }

    /**
     * Esimated parameters by Ordinary Least Square
     *
     * @param x         x
     * @param y         y
     * @param intercept Set as true if fitting with intercept term.
     */
    public void OLSFit(double[] x, double[] y, boolean intercept) {

        // Throw Exception
        if (x == null)
            throw new NullPointerException("x can't be null.");
        if (y == null)
            throw new NullPointerException("y can't be null.");
        if (x.length != y.length)
            throw new IllegalArgumentException(
                    "Length of x (" + x.length + ") must be equal to length of y (" + y.length + ")");

        if (y.length > 1) {
            // Calculate Sx, Sy, Sxx, Sxy
            double n = 0;
            double Sx = 0;
            double Sy = 0;
            double Sxx = 0;
            double Sxy = 0;
            for (int i = 0; i < x.length; i++) {
                if (Double.isFinite(x[i]) && Double.isFinite(y[i])) {
                    Sx += x[i];
                    Sy += y[i];
                    Sxx += x[i] * x[i];
                    Sxy += x[i] * y[i];
                    n++;
                }
            }

            // Update
            if (n > 0) {
                if (intercept) {
                    this.slope = (n * Sxy - Sx * Sy) / (n * Sxx - Sx * Sx);
                    this.intercept = CumulativeAverage.calculate(y) - this.slope * CumulativeAverage.calculate(x);
                } else {
                    this.slope = Sxy / Sxx;
                    this.intercept = 0;
                }

            }
        }
    }

    /**
     * Calculate y = intercept + slope * x
     *
     * @param x x
     * @return Return y
     */
    public double calculate(double x) {
        // Throw Exception
        if (Double.isNaN(this.slope) || Double.isNaN(this.intercept))
            throw new ArithmeticException("Parameters was not estimated. Please use OLSFit()");

        return this.intercept + this.slope * x;
    }

    /**
     * Calculate y = intercept + slope * x
     *
     * @param x x
     * @return Return y
     */
    public double[] calculate(double[] x) {
        // Throw Exception
        if (x == null)
            return null;

        double[] y = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            y[i] = calculate(x[i]);
        }

        return y;
    }
}
