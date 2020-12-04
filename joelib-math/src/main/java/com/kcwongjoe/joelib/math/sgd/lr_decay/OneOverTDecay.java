package com.kcwongjoe.joelib.math.sgd.lr_decay;

/**
 * 1/t decay
 * <p>
 * learning rate = alpha / (1 + k * iteration)
 **/
public class OneOverTDecay implements LearningRateDecayMethod {

    private double alpha = 0.96;
    private double k = 0.01;

    // region Constructor

    /**
     * Constructor
     */
    public OneOverTDecay() {

    }

    /**
     * Constructor
     *
     * @param alpha The ratio of decay learning rate, between 0 and 1, default:0.96
     * @param k     Factor for decay by iteration, default:0.01
     */
    public OneOverTDecay(double alpha, double k) {
        this.alpha = alpha;
        this.k = k;
    }

    // endregion

    // region getter and setter

    // region Alpha

    /**
     * Get alpha
     * @return Return alpha
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * Set alpha
     * @param alpha alpha
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    // endregion

    // region k

    /**
     * Get k
     * @return Return k
     */
    public double getK() {
        return k;
    }

    /**
     * Set k
     * @param k k
     */
    public void setK(double k) {
        this.k = k;
    }

    // endregion

    // endregion

    @Override
    public String toString(){
        return "1/t Decay: alpha = " + this.alpha + ", k = " + this.k;
    }

    @Override
    public void reset(){

    }

    @Override
    public double getLearningrate(double initLearningRate, double currentLearningRate, int iteration, double loss) {
        return initLearningRate * alpha / (1 + iteration * k);
    }


}
