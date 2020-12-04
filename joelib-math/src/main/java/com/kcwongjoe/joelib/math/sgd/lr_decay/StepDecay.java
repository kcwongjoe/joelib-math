package com.kcwongjoe.joelib.math.sgd.lr_decay;

/**
 * Step decay
 *
 * <p>Multiple <code>decayLearnRate</code> per <code>decayEpochs</code> epochs
 **/
public class StepDecay implements LearningRateDecayMethod {
    private double decayLearnRate = 0.96;
    private double decayEpochs = 10;

    // region Constructor

    /**
     * Constructor
     */
    public StepDecay() {

    }

    /**
     * Constructor
     *
     * @param decayLearnRate The ratio of decay learning rate, default:0.96
     * @param decayEpochs    The number of iteration
     */
    public StepDecay(double decayLearnRate, int decayEpochs) {
        this.decayLearnRate = decayLearnRate;
        this.decayEpochs = decayEpochs;
    }

    // endregion

    // region getter and setter

    // region decayLearnRate

    /**
     * Get Decay learning rate
     * @return Return decay learning rate
     */
    public double getDecayLearnRate() {
        return decayLearnRate;
    }

    /**
     * Set decay learning rate
     * @param decayLearnRate decay learning rate
     */
    public void setDecayLearnRate(double decayLearnRate) {
        this.decayLearnRate = decayLearnRate;
    }

    // endregion

    // region decayEpochs

    /**
     * Get decay epochs
     * @return Return decay epochs
     */
    public double getDecayEpochs() {
        return decayEpochs;
    }

    /**
     * Set decay epochs
     * @param decayEpochs decay epochs
     */
    public void setDecayEpochs(double decayEpochs) {
        this.decayEpochs = decayEpochs;
    }

    //endregion

    // endregion

    @Override
    public String toString(){
        return "Step Decay: decayLearnRate = " + this.decayLearnRate + ", decayEpochs = " + this.decayEpochs;
    }

    @Override
    public void reset(){

    }

    @Override
    public double getLearningrate(double initLearningRate, double currentLearningRate, int iteration, double loss) {
        return initLearningRate * Math.pow(this.decayLearnRate, iteration / this.decayEpochs);
    }

}
