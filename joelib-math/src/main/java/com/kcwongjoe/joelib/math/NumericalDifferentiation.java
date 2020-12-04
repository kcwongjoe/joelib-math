package com.kcwongjoe.joelib.math;

/**
 * Numerical Differentiation
 **/
public class NumericalDifferentiation {

    public static final int FORWARD = 0;
    public static final int CENTER = 1;
    public static final int BACKWARD = 2;

    private int method;
    private int derivative;
    private int order;

    public NumericalDifferentiation(int method, int derivative, int order) {
        setMethod(method);
        setDerivative(derivative);
        setOrder(order);
    }

    // region Getter and Setter

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        if (method != FORWARD && method != CENTER && method != BACKWARD)
            throw new IllegalArgumentException("method (" + method + ") is invalid.");

        this.method = method;
    }

    public int getDerivative() {
        return derivative;
    }

    public void setDerivative(int derivative) {
        if (derivative < 1 || derivative > 4)
            throw new IllegalArgumentException("derivative (" + derivative + ") must be between 1 and 4.");

        this.derivative = derivative;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        if (order != 1 && order != 2)
            throw new IllegalArgumentException("order (" + order + ") must be 1 or 2.");

        this.order = order;
    }

    // endregion

    public double[] diff(double[] y, double stepX) {
        if (method == FORWARD) {
            // Forward
            if (derivative == 1) {
                // 1st Derivative
                if (order == 1) {
                    return forward1Deriv1Order(y, stepX, true);
                } else if (order == 2) {
                    return forward1Deriv2Order(y, stepX, true);
                }
            } else if (derivative == 2) {
                // 2nd Derivative
                if (order == 1) {
                    return forward2Deriv1Order(y, stepX, true);
                } else if (order == 2) {
                    return forward2Deriv2Order(y, stepX, true);
                }
            } else if (derivative == 3) {
                // 3rd Derivative
                if (order == 1) {
                    return forward3Deriv1Order(y, stepX, true);
                } else if (order == 2) {
                    return forward3Deriv2Order(y, stepX, true);
                }
            } else if (derivative == 4) {
                // 4th Derivative
                if (order == 1) {
                    return forward4Deriv1Order(y, stepX, true);
                } else if (order == 2) {
                    return forward4Deriv2Order(y, stepX, true);
                }
            }
        } else if (method == CENTER) {
            // Center
            if (derivative == 1) {
                // 1st Derivative
                if (order == 1) {
                    return center1Deriv1Order(y, stepX, true);
                } else if (order == 2) {
                    return center1Deriv2Order(y, stepX, true);
                }
            } else if (derivative == 2) {
                // 2nd Derivative
                if (order == 1) {
                    return center2Deriv1Order(y, stepX, true);
                } else if (order == 2) {
                    return center2Deriv2Order(y, stepX, true);
                }
            } else if (derivative == 3) {
                // 3rd Derivative
                if (order == 1) {
                    return center3Deriv1Order(y, stepX, true);
                } else if (order == 2) {
                    return center3Deriv2Order(y, stepX, true);
                }
            } else if (derivative == 4) {
                // 4th Derivative
                if (order == 1) {
                    return center4Deriv1Order(y, stepX, true);
                } else if (order == 2) {
                    return center4Deriv2Order(y, stepX, true);
                }
            }
        } else if (method == BACKWARD) {
            // backward
            if (derivative == 1) {
                // 1st Derivative
                if (order == 1) {
                    return backward1Deriv1Order(y, stepX, true);
                } else if (order == 2) {
                    return backward1Deriv2Order(y, stepX, true);
                }
            } else if (derivative == 2) {
                // 2nd Derivative
                if (order == 1) {
                    return backward2Deriv1Order(y, stepX, true);
                } else if (order == 2) {
                    return backward2Deriv2Order(y, stepX, true);
                }
            } else if (derivative == 3) {
                // 3rd Derivative
                if (order == 1) {
                    return backward3Deriv1Order(y, stepX, true);
                } else if (order == 2) {
                    return backward3Deriv2Order(y, stepX, true);
                }
            } else if (derivative == 4) {
                // 4th Derivative
                if (order == 1) {
                    return backward4Deriv1Order(y, stepX, true);
                } else if (order == 2) {
                    return backward4Deriv2Order(y, stepX, true);
                }
            }
        }

        return null;
    }

    // region Forward

    // region First Derivative

    public static double[] forward1Deriv1Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        for (int i = 0; i < y.length; i++) {
            if (i == y.length - 1)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (y[i + 1] - y[i]) / stepX;
        }

        // Fill
        if (fillWithNextSlope)
            diffY[y.length - 1] = diffY[y.length - 2];

        return diffY;
    }

    public static double[] forward1Deriv2Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX2 = 2.0 * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i >= y.length - 2)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (-y[i + 2] + 4.0 * y[i + 1] - 3.0 * y[i]) / stepX2;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[y.length - 2] = diffY[y.length - 3];
            diffY[y.length - 1] = diffY[y.length - 3];
        }

        return diffY;
    }

    // endregion

    // region Second Derivative

    public static double[] forward2Deriv1Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX2 = stepX * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i >= y.length - 2)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (y[i + 2] - 2.0 * y[i + 1] + y[i]) / stepX2;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[y.length - 2] = diffY[y.length - 3];
            diffY[y.length - 1] = diffY[y.length - 3];
        }

        return diffY;
    }

    public static double[] forward2Deriv2Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX2 = stepX * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i >= y.length - 3)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (-y[i + 3] + 4.0 * y[i + 2] - 5.0 * y[i + 1] + 2.0 * y[i]) / stepX2;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[y.length - 3] = diffY[y.length - 4];
            diffY[y.length - 2] = diffY[y.length - 4];
            diffY[y.length - 1] = diffY[y.length - 4];
        }

        return diffY;
    }

    // endregion

    // region Third Derivative

    public static double[] forward3Deriv1Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX3 = stepX * stepX * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i >= y.length - 3)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (y[i + 3] - 3.0 * y[i + 2] + 3.0 * y[i + 1] - y[i]) / stepX3;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[y.length - 3] = diffY[y.length - 4];
            diffY[y.length - 2] = diffY[y.length - 4];
            diffY[y.length - 1] = diffY[y.length - 4];
        }

        return diffY;
    }

    public static double[] forward3Deriv2Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX23 = 2 * stepX * stepX * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i >= y.length - 4)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (-3.0 * y[i + 4] + 14.0 * y[i + 3] - 24.0 * y[i + 2] + 18.0 * y[i + 1] - 5.0 * y[i]) / stepX23;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[y.length - 4] = diffY[y.length - 5];
            diffY[y.length - 3] = diffY[y.length - 5];
            diffY[y.length - 2] = diffY[y.length - 5];
            diffY[y.length - 1] = diffY[y.length - 5];
        }

        return diffY;
    }

    // endregion

    // region Fourth Derivative

    public static double[] forward4Deriv1Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX4 = stepX * stepX * stepX * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i >= y.length - 4)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (y[i + 4] - 4.0 * y[i + 3] + 6.0 * y[i + 2] - 4.0 * y[i + 1] + y[i]) / stepX4;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[y.length - 4] = diffY[y.length - 5];
            diffY[y.length - 3] = diffY[y.length - 5];
            diffY[y.length - 2] = diffY[y.length - 5];
            diffY[y.length - 1] = diffY[y.length - 5];
        }

        return diffY;
    }

    public static double[] forward4Deriv2Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX4 = 2 * stepX * stepX * stepX * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i >= y.length - 5)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (-2.0 * y[i + 5] + 11.0 * y[i + 4] - 24.0 * y[i + 3] + 26.0 * y[i + 2] - 14.0 * y[i + 1] + 3.0 * y[i]) / stepX4;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[y.length - 5] = diffY[y.length - 6];
            diffY[y.length - 4] = diffY[y.length - 6];
            diffY[y.length - 3] = diffY[y.length - 6];
            diffY[y.length - 2] = diffY[y.length - 6];
            diffY[y.length - 1] = diffY[y.length - 6];
        }

        return diffY;
    }

    // endregion

    // endregion

    // region Center

    // region First Derivative

    public static double[] center1Deriv1Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        for (int i = 0; i < y.length; i++) {
            if (i == 0 || i == y.length - 1)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (y[i + 1] - y[i - 1]) / stepX;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[0] = diffY[1];
            diffY[y.length - 1] = diffY[y.length - 2];
        }


        return diffY;
    }

    public static double[] center1Deriv2Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX12 = 12.0 * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i >= y.length - 2 || i < 2)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (-y[i + 2] + 8.0 * y[i + 1] - 8.0 * y[i - 1] + y[i - 2]) / stepX12;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[y.length - 2] = diffY[y.length - 3];
            diffY[y.length - 1] = diffY[y.length - 3];
            diffY[0] = diffY[2];
            diffY[1] = diffY[2];
        }

        return diffY;
    }

    // endregion

    // region Second Derivative

    public static double[] center2Deriv1Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX2 = stepX * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i == 0 || i == y.length - 1)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (y[i + 1] - 2.0 * y[i] + y[i - 1]) / stepX2;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[0] = diffY[1];
            diffY[y.length - 1] = diffY[y.length - 2];
        }

        return diffY;
    }

    public static double[] center2Deriv2Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX212 = 12 * stepX * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i < 2 || i >= y.length - 2)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (-y[i + 2] + 16.0 * y[i + 1] - 30.0 * y[i] + 16.0 * y[i - 1] - y[i - 2]) / stepX212;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[y.length - 2] = diffY[y.length - 3];
            diffY[y.length - 1] = diffY[y.length - 3];
            diffY[0] = diffY[2];
            diffY[1] = diffY[2];
        }

        return diffY;
    }

    // endregion

    // region Third Derivative

    public static double[] center3Deriv1Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX32 = stepX * stepX * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i < 2 || i >= y.length - 2)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (y[i + 2] - 2.0 * y[i + 1] + 2.0 * y[i - 1] - y[i - 2]) / stepX32;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[y.length - 2] = diffY[y.length - 3];
            diffY[y.length - 1] = diffY[y.length - 3];
            diffY[0] = diffY[2];
            diffY[1] = diffY[2];
        }

        return diffY;
    }

    public static double[] center3Deriv2Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX83 = 8 * stepX * stepX * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i < 3 || i >= y.length - 3)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (-y[i + 3] + 8.0 * y[i + 2] - 13.0 * y[i + 1] + 13.0 * y[i - 1] - 8.0 * y[i - 2] + y[i - 3]) / stepX83;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[y.length - 3] = diffY[y.length - 4];
            diffY[y.length - 2] = diffY[y.length - 4];
            diffY[y.length - 1] = diffY[y.length - 4];
            diffY[0] = diffY[3];
            diffY[1] = diffY[3];
            diffY[2] = diffY[3];
        }

        return diffY;
    }

    // endregion

    // region Fourth Derivative

    public static double[] center4Deriv1Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX4 = stepX * stepX * stepX * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i < 2 || i >= y.length - 2)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (y[i + 2] - 4.0 * y[i + 1] + 6.0 * y[i] - 4.0 * y[i - 1] + y[i - 2]) / stepX4;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[y.length - 2] = diffY[y.length - 3];
            diffY[y.length - 1] = diffY[y.length - 3];
            diffY[0] = diffY[2];
            diffY[1] = diffY[2];
        }

        return diffY;
    }

    public static double[] center4Deriv2Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX64 = 6 * stepX * stepX * stepX * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i < 3 || i >= y.length - 3)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (-y[i + 3] + 12.0 * y[i + 2] + 39.0 * y[i + 1] + 56.0 * y[i] - 39.0 * y[i - 1] + 12.0 * y[i - 2] + y[i - 3]) / stepX64;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[y.length - 3] = diffY[y.length - 4];
            diffY[y.length - 2] = diffY[y.length - 4];
            diffY[y.length - 1] = diffY[y.length - 4];
            diffY[0] = diffY[3];
            diffY[1] = diffY[3];
            diffY[2] = diffY[3];
        }

        return diffY;
    }

    // endregion

    // endregion

    // region Backward

    // region First Derivative

    public static double[] backward1Deriv1Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        for (int i = 0; i < y.length; i++) {
            if (i == 0)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (y[i] - y[i - 1]) / stepX;
        }

        // Fill
        if (fillWithNextSlope)
            diffY[0] = diffY[1];

        return diffY;
    }

    public static double[] backward1Deriv2Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX2 = 2.0 * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i < 2)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (3.0 * y[i] - 4.0 * y[i - 1] + y[i - 2]) / stepX2;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[0] = diffY[2];
            diffY[1] = diffY[2];
        }

        return diffY;
    }

    // endregion

    // region Second Derivative

    public static double[] backward2Deriv1Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX2 = stepX * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i < 2)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (y[i] - 2.0 * y[i - 1] + y[i - 2]) / stepX2;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[0] = diffY[2];
            diffY[1] = diffY[2];
        }

        return diffY;
    }

    public static double[] backward2Deriv2Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX2 = stepX * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i < 3)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (2.0 * y[i] - 5.0 * y[i - 1] + 4.0 * y[i - 2] - y[i - 3]) / stepX2;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[0] = diffY[3];
            diffY[1] = diffY[3];
            diffY[2] = diffY[3];
        }

        return diffY;
    }

    // endregion

    // region Third Derivative

    public static double[] backward3Deriv1Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX3 = stepX * stepX * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i < 3)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (y[i] - 3.0 * y[i - 1] + 3.0 * y[i - 2] - y[i - 3]) / stepX3;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[0] = diffY[3];
            diffY[1] = diffY[3];
            diffY[2] = diffY[3];
        }

        return diffY;
    }

    public static double[] backward3Deriv2Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX23 = 2 * stepX * stepX * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i < 4)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (5.0 * y[i] - 18.0 * y[i - 1] + 24.0 * y[i - 2] - 14.0 * y[i - 3] + 3.0 * y[i - 4]) / stepX23;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[0] = diffY[4];
            diffY[1] = diffY[4];
            diffY[2] = diffY[4];
            diffY[3] = diffY[4];
        }

        return diffY;
    }

    // endregion

    // region Fourth Derivative

    public static double[] backward4Deriv1Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX4 = stepX * stepX * stepX * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i < 4)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (y[i] - 4.0 * y[i - 1] + 6.0 * y[i - 2] - 4.0 * y[i - 3] + y[i - 4]) / stepX4;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[0] = diffY[4];
            diffY[1] = diffY[4];
            diffY[2] = diffY[4];
            diffY[3] = diffY[4];
        }

        return diffY;
    }

    public static double[] backward4Deriv2Order(double[] y, double stepX, boolean fillWithNextSlope) {

        // Diff
        double[] diffY = new double[y.length];
        double stepX4 = 2 * stepX * stepX * stepX * stepX;
        for (int i = 0; i < y.length; i++) {
            if (i < 5)
                diffY[i] = Double.NaN;
            else
                diffY[i] = (3.0 * y[i] - 14.0 * y[i - 1] + 26.0 * y[i - 2] - 24.0 * y[i - 3] + 11.0 * y[i - 4] - 2.0 * y[i - 5]) / stepX4;
        }

        // Fill
        if (fillWithNextSlope) {
            diffY[0] = diffY[5];
            diffY[1] = diffY[5];
            diffY[2] = diffY[5];
            diffY[3] = diffY[5];
            diffY[4] = diffY[5];
        }

        return diffY;
    }

    // endregion

    // endregion
}
