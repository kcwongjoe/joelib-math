package com.kcwongjoe.joelib.math;

import java.util.List;

/**
 * Cumulative Average
 **/
public class CumulativeAverage {

    private double average = 0;
    private int n = 0;

    /**
     * Constructor
     */
    public CumulativeAverage() {

    }

    /**
     * Add value and return the mean. Non-finite values will be ignored.
     *
     * @param value Value
     * @return Return the mean
     */

    public double add(double value) {

        if (Double.isFinite(value)) {
            // Update
            if (n == 0)
                this.average = value;
            else {
                double n1 = this.n + 1;
                this.average = this.average * n / n1 + value / n1;
            }

            this.n++;
        }
        return this.average;
    }

    /**
     * Clear
     */
    public void clear() {
        this.average = 0;
        this.n = 0;
    }

    /**
     * Get the mean
     *
     * @return Return the average
     */
    public double getMean() {
        return this.average;
    }

    /**
     * Get the number of values to be calculated the mean.
     *
     * @return Return the number of values to be averaged.
     */
    public int getN() {
        return this.n;
    }

    /**
     * Calculate the mean
     *
     * @param x data
     * @return Return the mean of <code>x</code>. 0 will be return if x is <code>null</code> or length == 0
     */
    public static double calculate(float[] x) {
        if (x == null || x.length == 0) {
            return 0;
        }

        // Average
        CumulativeAverage average = new CumulativeAverage();
        for (int i = 0; i < x.length; i++) {
            average.add(x[i]);
        }

        return average.getMean();
    }

    /**
     * Calculate the mean
     *
     * @param x data
     * @return Return the mean of <code>x</code>. 0 will be return if x is <code>null</code> or length == 0
     */
    public static double calculate(int[] x) {
        if (x == null || x.length == 0) {
            return 0;
        }

        // Average
        CumulativeAverage average = new CumulativeAverage();
        for (int i = 0; i < x.length; i++) {
            average.add(x[i]);
        }

        return average.getMean();
    }


    /**
     * Calculate the mean
     *
     * @param x data
     * @return Return the mean of <code>x</code>. 0 will be return if x is <code>null</code> or length == 0
     */
    public static double calculate(double[] x) {
        if (x == null || x.length == 0) {
            return 0;
        }

        // Average
        CumulativeAverage average = new CumulativeAverage();
        for (int i = 0; i < x.length; i++) {
            average.add(x[i]);
        }

        return average.getMean();
    }

    /**
     * Calculate the mean
     *
     * @param <T> Numerical
     * @param x data
     * @return Return the mean of <code>x</code>. 0 will be return if x is <code>null</code> or length == 0
     */
    public static <T extends Number> double calculate(T[] x) {

        if (x == null || x.length == 0) {
            return 0;
        }

        // Average
        CumulativeAverage average = new CumulativeAverage();
        for (int i = 0; i < x.length; i++) {
            if (x[i] != null)
                average.add(x[i].doubleValue());
        }

        return average.getMean();
    }

    /**
     * Calculate the mean
     *
     * @param <T> Numerical
     * @param x Data
     * @return Return the mean of <code>x</code>. 0 will be return if x is <code>null</code> or length == 0
     */
    public static <T extends Number> double calculate(List<T> x) {
        if (x == null || x.size() == 0) {
            return 0;
        }

        // Average
        CumulativeAverage average = new CumulativeAverage();
        for (int i = 0; i < x.size(); i++) {
            if (x.get(i) != null)
                average.add(x.get(i).doubleValue());
        }

        return average.getMean();
    }
}
