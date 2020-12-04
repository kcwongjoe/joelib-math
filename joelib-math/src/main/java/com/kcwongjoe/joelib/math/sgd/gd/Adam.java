package com.kcwongjoe.joelib.math.sgd.gd;

/**
 * Adam
 * <p>
 * reference: https://arxiv.org/abs/1412.6980
 **/
public class Adam implements GradientDescentMethod {

    private double beta1;
    private double beta2;
    private double eps;

    private double[] currentmt = null;
    private double[] currentvt = null;
    private double[] currentIteration = null;

    // region Constructor

    /**
     * Contructor
     * Set beta1 = 0.9, beta2 = 0.9, eps = 1e-8
     */
    public Adam() {
        setBeta1(0.9);
        setBeta2(0.9);
        setEps(1e-8);
    }

    /**
     * Constructor
     *
     * @param beta1 Beta1
     * @param beta2 Beta2
     * @param eps eps
     */
    public Adam(double beta1, double beta2, double eps) {
        setBeta1(beta1);
        setBeta2(beta2);
        setEps(eps);
    }

    // endregion

    // region getter and setter

    /**
     * Get beta1
     *
     * @return Return beta1
     */
    public double getBeta1() {
        return beta1;
    }

    /**
     * Set beta1
     *
     * @param beta1 beta1, Must be between 0 and 1
     */
    public void setBeta1(double beta1) {
        if (beta1 < 0 || beta1 > 1)
            throw new IllegalArgumentException("beta1 (" + beta1 + ") must be between 0 and 1.");

        this.beta1 = beta1;
    }

    /**
     * Get beta2
     *
     * @return Return beta2
     */
    public double getBeta2() {
        return beta2;
    }

    /**
     * Set beta2
     *
     * @param beta2 beta2, Must be between 0 and 1
     */
    public void setBeta2(double beta2) {
        if (beta2 < 0 || beta2 > 1)
            throw new IllegalArgumentException("beta2 (" + beta2 + ") must be between 0 and 1.");

        this.beta2 = beta2;
    }

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
        return "Adam: beta1 = " + this.beta1 + ", beta2 = " + this.beta2 + ", eps = " + this.eps;
    }


    @Override
    public void Reset(int length) {
        this.currentmt = new double[length];
        this.currentvt = new double[length];
        this.currentIteration = new double[length];
        for (int i = 0; i < this.currentmt.length; i++) {
            this.currentmt[i] = 0;
            this.currentvt[i] = 0;
            this.currentIteration[i] = 0;
        }
    }

    // region calculateTheta

    @Override
    public double calculateTheta(int index, double learningRate, double theta, double derivativeTheta) {
        return calculateTheta(index, learningRate, theta, derivativeTheta, true);
    }

    @Override
    public double calculateTheta(int index, double learningRate, double theta, double derivativeTheta, boolean updateInternalCoefficient){

        if (currentmt == null || currentvt == null)
            throw new ArithmeticException("Please reset().");

        //  Update moment estimate
        double mt = this.beta1 * this.currentmt[index] + (1.0 - this.beta1) * derivativeTheta;
        double vt = this.beta2 * this.currentvt[index] + (1.0 - this.beta2) * (derivativeTheta * derivativeTheta);

        //  Compute bias-corrected  moment estimate
        if (this.currentIteration[index] == 0)
            this.currentIteration[index] = 1;
        double mtHead = mt / (1.0 - Math.pow(this.beta1, this.currentIteration[index]));
        double vtHead = vt / (1.0 - Math.pow(this.beta2, this.currentIteration[index]));

        //  Update
        double newTheta = theta - learningRate * mtHead / (Math.sqrt(vtHead) + this.eps);

        if (updateInternalCoefficient) {
            this.currentmt[index] = mt;
            this.currentvt[index] = vt;
            this.currentIteration[index]++;
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


}
