package com.kcwongjoe.joelib.math.smoothfilter;

/**
 * Created by JOE Wong on 6/10/2020
 **/
public interface SmoothFilter {

    /**
     * Smooth. It does not handle nan data.
     * @param x Values to be smoothed
     * @return Return the smoothed values
     */
    double[] smooth(double[] x);

    /**
     * Smooth
     * @param x Values to be smoothed
     * @param handleNaN Set it as true to handle nan data.
     * @return Return the smoothed values.
     */
    double[] smooth(double[] x, boolean handleNaN);
}
