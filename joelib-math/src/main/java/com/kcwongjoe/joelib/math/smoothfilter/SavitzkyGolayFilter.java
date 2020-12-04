package com.kcwongjoe.joelib.math.smoothfilter;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.QRDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;
import java.util.Arrays;

import com.kcwongjoe.joelib.JGeneralUtils;

/**
 * Created by JOE Wong on 6/4/2020
 **/
public class SavitzkyGolayFilter implements SmoothFilter{

    private double[][] coefficients;

    private int order;
    private int windowSize;


    /**
     * Constructor
     *
     * @param order      Order of the polynomial
     * @param windowSize The frame length. It must be odd number.
     * @throws IllegalArgumentException If <code>order</code> &lt; 1.
     * @throws IllegalArgumentException If <code>order</code> &lt; <code>winSize</code>.
     * @throws IllegalArgumentException If <code>winSize</code> is even number.
     */
    public SavitzkyGolayFilter(int order, int windowSize) {
        this.coefficients = calculateCoefficients(order, windowSize);

        this.order = order;
        this.windowSize = windowSize;


    }

    // region getter and setter

    /**
     * Get the order of the filter
     *
     * @return Return the order of filter
     */
    public int getOrder() {
        return order;
    }

    /**
     * Set the order of the filter
     *
     * @param order The order of the filter to be set
     */
    public void setOrder(int order) {
        if (order <= 0)
            throw new IllegalArgumentException("order (" + order + ") must be > 0.");

        this.coefficients = calculateCoefficients(order, this.windowSize);
        this.order = order;

    }

    /**
     * Get the window size
     *
     * @return Return the window size.
     */
    public int getWindowSize() {
        return windowSize;
    }

    /**
     * Set the window size
     *
     * @param size Window size. It must be odd number.
     */
    public void setWindowSize(int size) {
        if (size <= 0)
            throw new IllegalArgumentException("windowSize (" + size + ") must be > 0.");
        if (size % 2 != 1)
            throw new IllegalArgumentException("windowSize (" + size + ") must be odd number.");

        this.coefficients = calculateCoefficients(this.order, size);
        this.windowSize = size;
    }

    // endregion

    /**
     * Computer the coefficient of the Savitzky-Golay filter. The middle rows is the center coefficients.
     *
     * @param order      Order of the polynomial
     * @param windowSize The frame length
     * @return Return the Savitzky-Golay filter coefficients
     * @throws IllegalArgumentException If <code>order</code> &lt; 1.
     * @throws IllegalArgumentException If <code>order</code> &lt; <code>winSize</code>.
     * @throws IllegalArgumentException If <code>winSize</code> is even number.
     */
    private double[][] calculateCoefficients(int order, int windowSize) {
        if (order < 1)
            throw new IllegalArgumentException("order must be >= 1.");
        if (order >= windowSize)
            throw new IllegalArgumentException("order must be < winSize.");
        if (windowSize % 2 != 1)
            throw new IllegalArgumentException("winSize must be odd number.");

        // Generate the Vandermonde matrix
        double[][] SArr = new double[windowSize][order + 1];
        int sframe = (int) ((windowSize - 1) / 2.0);
        int arrIndex = 0;
        for (int i = -sframe; i <= sframe; i++) {
            SArr[arrIndex][0] = 1;
            for (int j = 1; j < order + 1; j++) {
                SArr[arrIndex][j] = Math.pow(i, j);
            }
            arrIndex++;
        }
        RealMatrix S = new Array2DRowRealMatrix(SArr, false);

        //Compute QR decomposition
        QRDecomposition QR = new QRDecomposition(S);

        //Get Q
        RealMatrix Q = QR.getQ().getSubMatrix(JGeneralUtils.createSequence(0, windowSize - 1), JGeneralUtils.createSequence(0, order));

        //Computer B
        RealMatrix B = Q.multiply(Q.transpose());

        return B.getData();
    }

    /**
     * Apply Savitzky-Golay filter on <code>x</code> with <code>order</code> of polynomial on <code>winSize</code> length of window.
     * If order = windowSize-1, the filter produces no smoothing. Return NaN if <code>x.length</code> &lt; window size
     *
     * @param x data which is without <code>Double.NaN</code>
     * @return Return the smoothed X.
     * @throws NullPointerException     If <code>x</code> or <code>coef</code> is <code>null</code>.
     * @throws IllegalArgumentException If length of <code>x</code> is 0.
     */
    public double[] smooth(double[] x) {
        if (x == null)
            throw new NullPointerException("x is null.");
        if (x.length == 0)
            throw new IllegalArgumentException("The size of x can't be zero-length.");

        //Get information
        int halfWindowSize = (int) ((windowSize - 1) / 2.0);

        if (x.length < windowSize)
            return JGeneralUtils.nans(x.length);

        double[] result = new double[x.length];

        //Calculate ybegin
        for (int i = 0; i < halfWindowSize; i++) {
            result[i] = 0;
            for (int xi = 0; xi < windowSize; xi++) {
                result[i] += coefficients[i][xi] * x[xi];
            }
        }

        //Calculate ycenter
        for (int i = halfWindowSize; i < x.length - halfWindowSize; i++) {
            result[i] = 0;
            for (int xi = 0; xi < windowSize; xi++) {
                result[i] += coefficients[halfWindowSize][xi] * x[i + xi - halfWindowSize];
            }
        }

        //Calculate yend
        int endIndex = 1;
        for (int i = x.length - halfWindowSize; i < x.length; i++) {
            result[i] = 0;
            for (int xi = 0; xi < windowSize; xi++) {
                result[i] += coefficients[halfWindowSize + endIndex][xi] * x[x.length - windowSize + xi];
            }
            endIndex++;
        }

        return result;
    }

    /**
     * Apply Savitzky-Golay filter on <code>x</code> with <code>order</code> of polynomial on <code>winSize</code> length of window.
     * If order = windowSize-1, the filter produces no smoothing.
     *
     * @param x data.
     * @return Return the smoothed X.
     * @throws NullPointerException     If <code>x</code> or <code>coef</code> is <code>null</code>.
     * @throws IllegalArgumentException If length of <code>x</code> is 0.
     */
    public double[] smoothWithNaN(double[] x) {
        if (x == null)
            throw new NullPointerException("x is null.");
        if (x.length == 0)
            throw new IllegalArgumentException("The size of x can't be zero-length.");

        //Search NaN Location in x
        ArrayList<Integer> startLoc = new ArrayList<Integer>();
        ArrayList<Integer> endLoc = new ArrayList<Integer>();
        boolean hasValue = false;
        for (int i = 0; i < x.length; i++) {
            if (i != x.length - 1 && Double.isFinite(x[i]) && !hasValue) {
                hasValue = true;
                startLoc.add(i);
            } else if (!Double.isFinite(x[i]) && hasValue) {
                hasValue = false;
                endLoc.add(i);
            } else if (i == x.length - 1 && Double.isFinite(x[i]) && hasValue) {
                hasValue = false;
                endLoc.add(i + 1);
            }
        }

        //Separate x and apply Savitzky-Golay filter
        double[] result = JGeneralUtils.nans(x.length);
        for (int i = 0; i < startLoc.size(); i++) {

            //Get the Subset
            double[] xsubSet = Arrays.copyOfRange(x, startLoc.get(i), endLoc.get(i));

            //Apply Savitzky-Golay filter
            double[] smoothedSubSet = smooth(xsubSet);

            //Copy to the result
            int xSubSetIndex = 0;
            for (int j = startLoc.get(i); j < endLoc.get(i); j++) {
                result[j] = smoothedSubSet[xSubSetIndex];
                xSubSetIndex++;
            }
        }

        return result;
    }
}
