package com.kcwongjoe.joelib.math.sgd.lr_decay;

import java.util.ArrayList;

/**
 * Learning rate decay when the loss is increasing.
 **/
public class JumpDecay implements LearningRateDecayMethod {

    private double decayRate = 0.7;
    private int decayStep = 5;

    private ArrayList<Double> lossBuffer;
    private int reduceIterationHistory = 0;

    /**
     * Constructor
     */
    public JumpDecay(){
        reset();
    }

    // region getter and setter

    /**
     * Get decay rate
     * @return Return the decay rate
     */
    public double getDecayRate() {
        return decayRate;
    }

    /**
     * Set Decay rate
     * @param decayRate Decay rate
     */
    public void setDecayRate(double decayRate) {
        this.decayRate = decayRate;
    }

    /**
     * Get decay step
     * @return Get decay step
     */
    public int getDecayStep() {
        return decayStep;
    }

    /**
     * Set decay step
     * @param decayStep Decay step
     */
    public void setDecayStep(int decayStep) {
        this.decayStep = decayStep;
    }

    // endregion

    @Override
    public String toString(){
        return "Jump Decay: Decay Rate = " + this.decayRate;
    }

    @Override
    public void reset(){
        this.lossBuffer = new ArrayList<Double>();
        this.reduceIterationHistory = 0;
    }

    @Override
    public double getLearningrate(double initLearningRate, double currentLearningRate, int iteration, double loss) {
        if (iteration - this.reduceIterationHistory > this.decayStep){
            // Reduce
            if (loss > lossBuffer.get(lossBuffer.size() - 1))
                currentLearningRate = currentLearningRate * this.decayRate;

            this.reduceIterationHistory = iteration;
        }

        // Add
        lossBuffer.add(loss);

        return currentLearningRate;
    }
}
