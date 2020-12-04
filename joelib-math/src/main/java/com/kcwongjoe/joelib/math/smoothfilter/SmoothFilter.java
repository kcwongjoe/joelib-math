package com.kcwongjoe.joelib.math.smoothfilter;

/**
 * Created by JOE Wong on 6/10/2020
 **/
public interface SmoothFilter {

    /**
     * Smooth
     * @param x Values to be smoothed
     * @return Return the smoothed values
     */
    double[] smooth(double[] x);

    /**
     * Smooth with NaN data.
     * @param x Values to be smoothed
     * @return Return the smoothed values.
     */
    double[] smoothWithNaN(double[] x);
}
