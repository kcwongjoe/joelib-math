package com.kcwongjoe.joelib.math.sgd;

/**
 * Objective Function for Stochastic Gradient Descent
 **/
public interface ObjectiveFunction {

    /**
     * Set the function parameters
     * @param parameters parameters
     */
    void setParameter(double[] parameters);

    /**
     * Get the function parameters
     * @return Return the function parameters
     */
    double[] getParameter();

    /**
     * Function
     * @param x x
     * @return Return f(x)
     */
    double[] function(double[] x);

    /**
     * df(x)/dtheta
     * @param x x
     * @return Return df(x)/dtheta in new double[sample][parameter]
     */
    double[][] derivativeFunctionParameters(double[] x);
}
