package com.kcwongjoe.joelib.math.sgd.gd;

/**
 * Adagrad
 * <p>
 * reference: http://jmlr.org/papers/v12/duchi11a.html
 **/
public class Adagrad implements GradientDescentMethod {
    private double eps;

    private double[] currentGt = null;

    // region Constructor

    /**
     * Contructor
     */
    public Adagrad() {
        setEps(1e-8);
    }

    /**
     * Constructor
     *
     * @param eps eps
     */
    public Adagrad(double eps) {
        setEps(eps);
    }

    // endregion

    // region getter and setter

    /**
     * Get eps
     *
     * @return Return eps
     */
    public double getEps() {
        return eps;
    }

    /**
     * Set eps
     *
     * @param eps eps. Must be &gt; 0
     */
    public void setEps(double eps) {
        if (eps <= 0)
            throw new IllegalArgumentException("eps (" + eps + ") must be > 0");

        this.eps = eps;
    }

    // endregion

    @Override
    public String toString() {
        return "Adagrad: eps = " + this.eps;
    }

    @Override
    public void Reset(int length) {
        this.currentGt = new double[length];
        for (int i = 0; i < this.currentGt.length; i++) {
            this.currentGt[i] = 0;
        }
    }

    // region calculateTheta

    @Override
    public double calculateTheta(int index, double learningRate, double theta, double derivativeTheta){
        return calculateTheta(index, learningRate, theta, derivativeTheta, true);
    }

    @Override
    public double calculateTheta(int index, double learningRate, double theta, double derivativeTheta, boolean updateInternalCoefficient){

        if (currentGt == null)
            throw new ArithmeticException("Please reset().");

        double gt = this.currentGt[index] + (derivativeTheta * derivativeTheta);
        double newTheta = theta - learningRate * derivativeTheta / (Math.sqrt(gt) + this.eps);

        if (updateInternalCoefficient) {
            this.currentGt[index] = gt;
        }

        return newTheta;
    }

    @Override
    public double[] calculateTheta(double learningRate, double[] theta, double[] derivativeTheta){

        double[] newTheta = new double[theta.length];
        for (int i = 0; i < theta.length; i++) {
            newTheta[i] = calculateTheta(i, learningRate, theta[i], derivativeTheta[i]);
        }

        return newTheta;
    }


    // endregion
}
