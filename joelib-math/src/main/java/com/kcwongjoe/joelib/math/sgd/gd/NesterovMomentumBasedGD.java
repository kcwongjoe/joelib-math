package com.kcwongjoe.joelib.math.sgd.gd;

/**
 * Nesterov momentum-based gradient descent
 * Created by JOE Wong on 6/8/2020
 **/
public class NesterovMomentumBasedGD implements GradientDescentMethod {

    private double momentumCoefficient;

    private double[] currentMomentumCoefficient = null;

    // region Constructor

    /**
     * Contructor
     * Set momentumCoefficient = 0.2
     */
    public NesterovMomentumBasedGD() {
        setMomentumCoefficient(0.2);
    }

    /**
     * Constructor
     *
     * @param momentum_coefficient Momentum Coefficient
     */
    public NesterovMomentumBasedGD(double momentum_coefficient) {
        setMomentumCoefficient(momentum_coefficient);
    }

    // endregion

    // region getter and setter

    /**
     * Get the Momentum Coefficient
     * @return Return the Momentum Coefficient
     */
    public double getMomentumCoefficient() {
        return momentumCoefficient;
    }

    /**
     * Set the Momentum Coefficient
     * @param momentumCoefficient Momentum Coefficient. It must be &gt;= 0
     */
    public void setMomentumCoefficient(double momentumCoefficient) {
        if (momentumCoefficient < 0)
            throw new IllegalArgumentException("momentumCoefficient (" + momentumCoefficient + ") must be >= 0");

        this.momentumCoefficient = momentumCoefficient;
    }

    // endregion

    @Override
    public String toString(){
        return "Nesterov Momentum Based Gradient Descent: momentumCoefficient = " + this.momentumCoefficient;
    }

    @Override
    public void Reset(int length) {
        this.currentMomentumCoefficient = new double[length];
        for (int i = 0; i < this.currentMomentumCoefficient.length; i++) {
            this.currentMomentumCoefficient[i] = 0;
        }
    }

    // region calculateTheta

    @Override
    public double calculateTheta(int index, double learningRate, double theta, double derivativeTheta){
        return calculateTheta(index, learningRate, theta, derivativeTheta, true);
    }


    @Override
    public double calculateTheta(int index, double learningRate, double theta, double derivativeTheta, boolean updateInternalCoefficient){

        if (currentMomentumCoefficient == null)
            throw new ArithmeticException("Please reset().");

        double coefficient = this.momentumCoefficient * this.currentMomentumCoefficient[index] - learningRate * derivativeTheta;
        double newTheta = theta - this.momentumCoefficient * this.currentMomentumCoefficient[index] + (1.0 + this.momentumCoefficient) * coefficient;

        if (updateInternalCoefficient) {
            this.currentMomentumCoefficient[index] = coefficient;
        }

        return newTheta;
    }

    @Override
    public double[] calculateTheta(double learningRate, double[] theta, double[] derivativeTheta) {

        double[] newTheta = new double[theta.length];
        for (int i = 0; i < theta.length; i++) {
            newTheta[i] = calculateTheta(i, learningRate, theta[i], derivativeTheta[i]);
        }

        return newTheta;
    }


    // endregion

}
