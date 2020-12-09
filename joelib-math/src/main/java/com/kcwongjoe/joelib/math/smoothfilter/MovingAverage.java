package com.kcwongjoe.joelib.math.smoothfilter;

import com.kcwongjoe.joelib.JGeneralUtils;

/**
 * Moving average
 */
public class MovingAverage implements SmoothFilter {

    public static final int FORWARD = 0;
    public static final int CENTRAL = 1;
    public static final int BACKWARD = 2;
    public static final String[] DIRECTION_STRING = new String[]{"Forward", "Central", "backward"};

    private int direction = FORWARD;

    /**
     * Boundary values will not be averaged if set as False.
     * <p>
     * For example:
     * <p>
     * averageBoundary = false, x = {3,3,3,3,3,3,3,3}, windowSize = 3, result =
     * {3,3,3,3,3,3,NaN,NaN}
     * <p>
     * averageBoundary = true, x = {3,3,3,3,3,3,6,9}, windowSize = 3, result =
     * {3,3,3,3,4,6,5,9}
     */
    public boolean averageBoundary = false;

    private int windowSize;

    /**
     * Constructor
     * 
     * @param windowSize Window Size. It must be &lt; 0.
     */
    public MovingAverage(int windowSize) {
        setWindowSize(windowSize);
    }

    // region Getter and Setter

    /**
     * Set the window size
     * 
     * @param windowSize Window Size. It must be &lt; 0.
     */
    public void setWindowSize(int windowSize) {
        if (windowSize <= 0)
            throw new IllegalArgumentException("Window size (" + windowSize + ") must be > 0.");

        this.windowSize = windowSize;
    }

    /**
     * Get the window size.
     * 
     * @return Return the window size.
     */
    public int getWindowSize() {
        return this.windowSize;
    }

    /**
     * Set direction
     * 
     * @param direction FORWARD, CENTRAL or BACKWARD
     */
    public void setDirection(int direction) {
        if (direction == FORWARD) {
            this.direction = direction;
        } else if (direction == CENTRAL) {
            this.direction = direction;
        } else if (direction == BACKWARD) {
            this.direction = direction;
        } else {
            throw new IllegalArgumentException("Direction must be FORWARD, CENTRAL or BACKWARD");
        }
    }

    /**
     * Return the direction
     * 
     * @return Return the direction
     */
    public int getDirection() {
        return direction;
    }

    // endregion Getter and Setter

    // region Smooth

    @Override
    public double[] smooth(double[] x) {
        return smoothCore(x, false);
    }

    @Override
    public double[] smooth(double[] x, boolean handleNaN) {
        return smoothCore(x, handleNaN);
    }

    private double[] smoothCore(double[] x, boolean smoothWithNan) {
        // Exception
        if (x == null)
            throw new NullPointerException("x is null.");
        if (x.length == 0)
            throw new IllegalArgumentException("The size of x can't be zero-length.");

        // Initialize
        double[] result = JGeneralUtils.nans(x.length);
        if (x.length < windowSize)
            return result;

        // Get start index
        int startIndex = 0;
        if (direction == FORWARD) {
            startIndex = 0;
        } else if (direction == CENTRAL) {
            startIndex = -windowSize / 2;
        } else if (direction == BACKWARD) {
            startIndex = -windowSize + 1;
        }

        // Average
        boolean previousContainNaN = false; // if previous contain NaN, use simple average
        for (int i = 0; i < x.length; i++) {

            // Calculate end index
            int endIndex = startIndex + windowSize - 1;
            if (endIndex >= 0) {
                // Moving average

                // Check average method
                boolean simpleAverage = false;
                if (startIndex >= 0 && endIndex < x.length) {
                    // Within range
                    if (i > 0 && startIndex > 0 && !Double.isNaN(result[i - 1]) && !Double.isNaN(x[endIndex])
                            && !Double.isNaN(x[startIndex - 1]) && !previousContainNaN) {
                        // Cumulative Average
                        result[i] = result[i - 1] + (x[endIndex] - x[startIndex - 1]) / ((double) windowSize);
                    } else {
                        if (!(Double.isNaN(x[endIndex]) && !smoothWithNan)) {
                            simpleAverage = true;
                        }
                    }
                } else if (averageBoundary) {
                    // Boundary
                    simpleAverage = true;
                }

                // Simple Average
                if (simpleAverage) {
                    int simpleAverageN = 0;
                    double simpleAverageResult = 0;
                    boolean saveResult = true;

                    // Sum
                    for (int ai = startIndex; ai <= endIndex; ai++) {
                        if (ai >= 0 && ai < x.length){
                            if (!Double.isNaN(x[ai])) {
                                simpleAverageResult += x[ai];
                                simpleAverageN++;
                            } else if (!smoothWithNan) {
                                // Found NaN, don't implement averaging
                                saveResult = false;
                                break;
                            } else {
                                previousContainNaN = true;
                            }
                        }
                    }

                    // Save result
                    if (saveResult && simpleAverageN > 0) {
                        result[i] = simpleAverageResult / ((double) simpleAverageN);
                    }
                } else {
                    previousContainNaN = false;
                }
            }

            // Move the window
            startIndex++;
        }

        return result;
    }

    // endregion Smooth

    @Override
    public String toString(){
        return "MovingAverage(window size = " + this.windowSize + ", direction = " + DIRECTION_STRING[this.direction] + ", average boundary = " + this.averageBoundary + ")";
    }

}
