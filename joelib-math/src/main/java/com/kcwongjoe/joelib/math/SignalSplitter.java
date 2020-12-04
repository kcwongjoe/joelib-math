package com.kcwongjoe.joelib.math;

import com.kcwongjoe.joelib.math.smoothfilter.SmoothFilter;
import com.kcwongjoe.joelib.JMathUtils;

/**
 * A Splitter to split data into signal + noise + bias. Some analysis tools is
 * also provide.
 **/
public class SignalSplitter {

    private SmoothFilter filter;
    private double[] signal = null;
    private double[] noise = null;
    private double bias = 0;


    public SignalSplitter(SmoothFilter filter) {
        this.filter = filter;
    }

    //region getter and setter

    /**
     * Get signal
     *
     * @return Return the signal
     */
    public double[] getSignal() {
        return this.signal;
    }

    /**
     * Get noise
     *
     * @return Return the noise
     */
    public double[] getNoise() {
        return this.noise;
    }

    /**
     * Get bias
     * @return Return the bias
     */
    public double getBias(){
        return this.bias;
    }

    // endregion

    // region split

    /**
     * Split x into signal + noise + bias
     * @param x x
     */
    public void split(double[] x) {
        // Throw Exception
        if (x == null)
            throw new NullPointerException("x can't be null.");

        double[] smoothedX = filter.smooth(x);
        this.noise = JMathUtils.minus(x, smoothedX);
        this.bias = JMathUtils.min(smoothedX);
        this.signal = JMathUtils.minus(smoothedX, this.bias);
    }

    //endregion

    // region Analysis

    // region Mean

    /**
     * Calculate the mean of the signal
     *
     * @return Return the mean of the signal
     */
    public double signalMean() {
        if (this.signal == null)
            return Double.NaN;

        return CumulativeAverage.calculate(this.signal);
    }

    /**
     * Calculate the mean of the noise
     *
     * @return Return the mean of the noise
     */
    public double noiseMean() {
        if (this.signal == null)
            return 0;

        return CumulativeAverage.calculate(this.noise);
    }

    // endregion

    // region Stdev

    /**
     * Calculate the stdev of the signal
     *
     * @return Return the stdev of the signal
     */
    public double signalStdev() {
        if (this.signal == null)
            return 0;

        return JMathUtils.stdev(this.signal);
    }

    /**
     * Calculate the stdev of the noise
     *
     * @return Return the stdev of the noise
     */
    public double noiseStdev() {
        if (this.noise == null)
            return Double.NaN;

        return JMathUtils.stdev(this.noise);
    }

    // endregion

    /**
     * Calculate the signal to noise ratio, SNR = P_signal / P_noise = E[signal^2] / variance_noise
     *
     * @return Return the SNR
     */
    public double SNR() {
        if (this.signal == null || this.noise == null)
            return 0;

        // Calculate E[signal^2]
        CumulativeAverage average = new CumulativeAverage();
        for (int i = 0; i < this.signal.length; i++) {
            if (Double.isFinite(this.signal[i])) {
                average.add(this.signal[i] * this.signal[i]);
            }
        }

        // Get Noise stdev
        double noiseStdev = noiseStdev();

        // SNR
        double SNR = 0;
        if (noiseStdev > 0)
            SNR = average.getMean() / (noiseStdev * noiseStdev);

        return SNR;
    }

    // endregion
}
