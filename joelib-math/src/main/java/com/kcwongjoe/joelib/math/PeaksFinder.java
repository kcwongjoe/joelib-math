package com.kcwongjoe.joelib.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.kcwongjoe.joelib.JGeneralUtils;

/**
 * A finder to find peaks.
 * If a flatness peak was identified, the first point would be used. For example: peak location = 2 for x = [1,5,6,6,6,3]
 **/
public class PeaksFinder {

    private double minimumPeaksDistance = 0;
    private double minimumPeakSlope = 0;

    /**
     * Constructor
     */
    public PeaksFinder() {

    }

    /**
     * Constructor
     * @param minimumPeaksDistance Minimum Peaks Distance
     * @param minimumPeakSlope Minimum Peaks Slope
     */
    public PeaksFinder(double minimumPeaksDistance, double minimumPeakSlope) {
        this.minimumPeaksDistance = minimumPeaksDistance;
        this.minimumPeakSlope = minimumPeakSlope;
    }

    // region getter and setter

    public double getMinimumPeaksDistance() {
        return minimumPeaksDistance;
    }

    public void setMinimumPeaksDistance(double minimumPeaksDistance) {
        this.minimumPeaksDistance = minimumPeaksDistance;
    }

    public double getMinimumPeakSlope() {
        return minimumPeakSlope;
    }

    public void setMinimumPeakSlope(double minimumPeakSlope) {
        this.minimumPeakSlope = minimumPeakSlope;
    }

    // endregion

    /**
     * Find possible peaks index
     *
     * @param y y
     * @return
     */
    private int[] findPossiblePeak(double[] y) {
        // Find possible peak
        ArrayList<Integer> peakIndex = new ArrayList<Integer>();
        for (int i = 1; i < y.length - 1; i++) {
            if (Double.isFinite(i) && Double.isFinite(i - 1) && Double.isFinite(i + 1)) { // Check value valid
                if (y[i - 1] < y[i] && y[i] >= y[i + 1]) {
                    peakIndex.add(i);
                }
            }
        }
        return JGeneralUtils.list2IntArr(peakIndex);
    }

    /**
     * Bisection search the closest index of the target x
     *
     * @param x           data
     * @param leftIndex   The range of index, min
     * @param rightIndex  The range of index, max
     * @param targetX     target
     * @param useLeftSide Since x[index] may not exactly equal to targetX, Use left side data as the index? Set as false to use right side.
     * @return Return the closest index
     */
    private int searchIndex(double[] x, int leftIndex, int rightIndex, double targetX, boolean useLeftSide) {
        int resultIndex = 0;
        boolean stop = false;
        while (rightIndex - leftIndex > 1 && !stop) {
            // Calculate center index
            int center = (rightIndex - leftIndex) / 2 + leftIndex;

            // Update left and right
            if (x[center] > targetX) {
                rightIndex = center;
            } else if (x[center] < targetX) {
                leftIndex = center;
            } else {
                resultIndex = center;
                stop = true;
            }

            if (useLeftSide) {
                resultIndex = leftIndex;
            } else {
                resultIndex = rightIndex;
            }
        }

        return resultIndex;
    }

    /**
     * If peaks are located in a window, choose the highest peak
     *
     * @param x           data
     * @param y           data
     * @param peakIndices peak indices to be check
     * @param windowSize  window size
     * @return
     */
    private int[] removeDuplicatePeak(double[] x, double[] y, ArrayList<Integer> peakIndices, double windowSize) {

        // Remove peak within window
        ArrayList<Integer> result = new ArrayList<Integer>(peakIndices);
        ArrayList<Integer> saveBuffer = new ArrayList<Integer>();
        boolean stop;
        while (true) {

            // Reset
            stop = true;
            saveBuffer = new ArrayList<Integer>();

            // Sort
            Collections.sort(result);

            // Add index
            for (int i = 0; i < result.size(); i++) {

                // Get index
                int curIndex = result.get(i);
                int saveIndex = curIndex;

                if (i < result.size()) {

                    // Get index
                    int preIndex = -1;
                    if (i > 0)
                        preIndex = result.get(i - 1);

                    int nextIndex = -1;
                    if (i < result.size() - 1)
                        nextIndex = result.get(i + 1);

                    // Check with previous index
                    if (preIndex != -1 && x[curIndex] - x[preIndex] < windowSize) {
                        // Check
                        if (y[preIndex] >= y[saveIndex]) {
                            saveIndex = preIndex;
                        }
                        stop = false;
                    }

                    // Check in next index
                    if (nextIndex != -1 && x[nextIndex] - x[curIndex] < windowSize) {
                        // Check
                        if (y[nextIndex] > y[saveIndex]) {
                            saveIndex = nextIndex;
                        }
                        stop = false;
                    }
                }

                // Add
                if (!saveBuffer.contains(saveIndex))
                    saveBuffer.add(saveIndex);
            }

            // Update
            result = saveBuffer;

            // Stop
            if (stop) {
                break;
            }
        }

        return JGeneralUtils.list2IntArr(result);
    }

    /**
     * Find peaks location from <code>x</code>. If a flatness peak was identified, the first point would be used. For example: peak location = 2 for x = [1,5,6,6,6,3]
     *
     * @param y          Array to be searched
     * @param windowSize Window size.
     * @param thresholdSlope  The slope threshold to identify as peak. If windowSize == 0, it is the threshold of the peak value.
     * @return The index of the peaks. <code>null</code> return if <code>x</code> is <code>null</code>. If <code>x.length <= 3</code>, <code>int[0]</code> return.
     */
    private int[] findInWindow(double[] x, double[] y, int[] possiblePeakIndices, double windowSize, double thresholdSlope) {

        // init
        thresholdSlope = Math.abs(thresholdSlope);

        // Find possible peak
        ArrayList<Integer> peakIndices = new ArrayList<Integer>();
        for (int i = 0; i < possiblePeakIndices.length; i++) {

            int possiblePeakIndex = possiblePeakIndices[i];

            if (windowSize < 1) {
                // No Window
                peakIndices.add(possiblePeakIndex);
            } else {
                // Use Window

                // Get window index
                //   Find start index
                int startIndex = searchIndex(x, 0, possiblePeakIndex, x[possiblePeakIndex] - windowSize, true);
                int lastIndex = searchIndex(x, possiblePeakIndex, x.length - 1, x[possiblePeakIndex] + windowSize, false);

                // Get left and right value
                double leftValue;
                if (possiblePeakIndex - startIndex == 1) {
                    leftValue = y[possiblePeakIndex] - y[startIndex];
                } else {
                    // Fit line
                    SimpleLinearEquation leftLine = new SimpleLinearEquation();
                    leftLine.OLSFit(Arrays.copyOfRange(y, startIndex, possiblePeakIndex));
                    leftValue = (y[possiblePeakIndex] - leftLine.calculate(0)) / (x[possiblePeakIndex] - x[startIndex]);
                }

                double rightValue;
                if (lastIndex - possiblePeakIndex == 1) {
                    rightValue = y[possiblePeakIndex] - y[lastIndex];
                } else {
                    // Fit line
                    SimpleLinearEquation rightLine = new SimpleLinearEquation();
                    rightLine.OLSFit(Arrays.copyOfRange(y, possiblePeakIndex + 1, lastIndex + 1));
                    rightValue = -(y[possiblePeakIndex] - rightLine.calculate(lastIndex)) / (x[possiblePeakIndex] - x[lastIndex]);
                }

                // Check
                if (leftValue >= thresholdSlope && rightValue >= thresholdSlope) {
                    peakIndices.add(possiblePeakIndex);
                }
            }
        }
        int[] result = JGeneralUtils.list2IntArr(peakIndices);

        // Remove duplicate peaks
        if (windowSize > 1) {
            result = removeDuplicatePeak(x, y, peakIndices, windowSize);
        }

        return result;
    }

    /**
     * Find peaks from data y.
     *
     * @param x x
     * @param y y
     * @return Return the peaks
     */
    public int[] find(double[] x, double[] y) {
        if (x == null || y == null) return null;
        if (x.length <= 3 || y.length <= 3) return new int[0];

        int[] possiblePeakIndices = findPossiblePeak(y);

        // Reducing window to search peak
        ArrayList<Integer> result = new ArrayList<Integer>();
        double curWindowSize = (x[x.length - 1] - x[0]) / 2.0;
        while (true) {

            // find
            int[] findIndex = findInWindow(x, y, possiblePeakIndices, curWindowSize, this.minimumPeakSlope);

            // Add
            for (int i = 0; i < findIndex.length; i++) {
                if (!result.contains(findIndex[i]))
                    result.add(findIndex[i]);
            }

            // Update window size
            if (curWindowSize == this.minimumPeaksDistance) {
                break;
            } else {
                curWindowSize /= 2;
                if (curWindowSize < this.minimumPeaksDistance)
                    curWindowSize = this.minimumPeaksDistance;
            }
        }

        // Remove duplicate peaks
        int[] finalResult = removeDuplicatePeak(x, y, result, this.minimumPeaksDistance);

        return finalResult;
    }

}
