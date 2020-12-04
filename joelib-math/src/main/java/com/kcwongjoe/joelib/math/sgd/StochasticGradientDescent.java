package com.kcwongjoe.joelib.math.sgd;

import com.kcwongjoe.joelib.JGeneralUtils;
import com.kcwongjoe.joelib.math.CumulativeAverage;
import com.kcwongjoe.joelib.math.sgd.gd.Adam;
import com.kcwongjoe.joelib.math.sgd.gd.GradientDescentMethod;
import com.kcwongjoe.joelib.math.sgd.lr_decay.LearningRateDecayMethod;
import com.kcwongjoe.joelib.math.sgd.lr_loss.L2LossFunc;
import com.kcwongjoe.joelib.math.sgd.lr_loss.LossFunction;
import com.kcwongjoe.joelib.math.sgd.regu.Regularization;

/**
 * Stochastic Gradient Descent
 **/
public class StochasticGradientDescent {

    private static final int STOP_ABSOLUTE_LOSS = 0;
    private static final int STOP_RELATIVE_LOSS = 1;

    private ObjectiveFunction objectiveFunction;
    private double[] initParameters = null;

    /**
     * parameters range double[para][2] { {min,max}, {min, max} }
     */
    private double[][] parameterRange = null;

    /**
     * Maximum number of epochs. Default as 50
     */
    private int maxEpochs;

    /**
     * Start learning rate. Default as 0.1
     */
    private double initLearningRate;

    /**
     * Gradient Descent method. Default as Adam
     */
    private GradientDescentMethod gradientDescentMethod;

    /**
     * Learning rate decay method. Set it as null to disabled.
     */
    private LearningRateDecayMethod learningRateDecayMethod = null;

    /**
     * Loss Function. Default as L2 Loss.
     */
    private LossFunction lossFunction;

    /**
     * Regularization. Set it to null to disabled.
     */
    private Regularization regularization = null;

    private int stopLossType = STOP_RELATIVE_LOSS;
    private double stopLossThreshold = 0.01;

    /**
     * Print detail during solving.
     */
    public boolean verbose = false;

    /**
     * Constructor
     * @param objectiveFunction Objective Function
     */
    public StochasticGradientDescent(ObjectiveFunction objectiveFunction) {
        // Throw Exception
        if (objectiveFunction == null)
            throw new NullPointerException("objectiveFunction can't not be null");

        // Set
        this.objectiveFunction = objectiveFunction;

        if (this.objectiveFunction.getParameter() != null) {
            this.initParameters = this.objectiveFunction.getParameter().clone();
            initParameterRange();
        }


        // Initialize
        init();
    }

    private void init() {
        setInitLearningRate(0.1);
        setGradientDescentMethod(new Adam());
        setLossFunction(new L2LossFunc());
        setMaxEpochs(50);
        setStopRelativeLoss(1e-9);
    }

    private void initParameterRange() {
        if (this.initParameters != null && (this.parameterRange == null || this.parameterRange.length != this.initParameters.length)) {
            this.parameterRange = new double[this.initParameters.length][];
            for (int i = 0; i < this.parameterRange.length; i++) {
                this.parameterRange[i] = new double[]{Double.NaN, Double.NaN};
            }
        }
    }

    // region Getter and Setter

    // region initParameters

    public void setInitParameters(double[] initParameters) {
        if (this.objectiveFunction.getParameter() != null && initParameters.length != this.objectiveFunction.getParameter().length)
            throw new IllegalArgumentException("Length of initParameters (" + initParameters.length + ") must be = " + this.objectiveFunction.getParameter().length);

        this.initParameters = initParameters;
        initParameterRange();
    }

    public double[] getInitParameters() {
        return this.initParameters;
    }

    //endregion

    // region Parameters range

    public double[][] getParameterRange() {
        return parameterRange;
    }

    public void setParameterRange(double[][] parameterRange) {
        this.parameterRange = parameterRange;
    }

    // endregion

    // region maxEpochs

    /**
     * Set the maximum number of epochs
     *
     * @param epochs epochs
     */
    public void setMaxEpochs(int epochs) {
        if (epochs <= 0)
            throw new IllegalArgumentException("epochs(" + epochs + ") must be > 0.");

        this.maxEpochs = epochs;
    }

    /**
     * Get the maximum number of epochs
     *
     * @return Return the maximum number of epochs
     */
    public int getMaxEpochs() {
        return this.maxEpochs;
    }

    //endregion

    // region initLearningRate

    /**
     * Set the start learning rate
     *
     * @param learningRate Start learning rate
     */
    public void setInitLearningRate(double learningRate) {
        if (learningRate <= 0 || learningRate >= 1)
            throw new IllegalArgumentException("learningRate(" + learningRate + ") must be between 0 and 1.");

        this.initLearningRate = learningRate;
    }

    /**
     * Get the start learning rate
     *
     * @return Return start learning rate
     */
    public double getInitLearningRate() {
        return this.initLearningRate;
    }

    // endregion

    // region learning rate optimizer method

    /**
     * Set Gradient Descent Method
     *
     * @param method Gradient descent method.
     */
    public void setGradientDescentMethod(GradientDescentMethod method) {
        if (method == null)
            throw new NullPointerException("method can't be null.");
        this.gradientDescentMethod = method;
    }

    /**
     * Set learning rate decay method. Default as disabled.
     *
     * @param method Set as <code>null</code> to disable the decay method
     */
    public void setLearningRateDecayMethod(LearningRateDecayMethod method) {
        this.learningRateDecayMethod = method;
    }

    /**
     * Get learning rate decay method.
     *
     * @return LearningRateDecayMethod
     */
    public LearningRateDecayMethod getLearningRateDecayMethod() {
        return this.learningRateDecayMethod;
    }

    //endregion

    // region regularization

    /**
     * Set regularization.  Default as disabled.
     *
     * @param regularization Set it to null to disabled the regularization
     */
    public void setRegularization(Regularization regularization) {
        this.regularization = regularization;
    }

    /**
     * Get regularization.
     *
     * @return Return the regularization.
     */
    public Regularization getRegularization() {
        return this.regularization;
    }

    // endregion

    // region loss function

    /**
     * Get the loss function
     *
     * @return Return the loss funcion
     */
    public LossFunction getLossFunction() {
        return lossFunction;
    }

    /**
     * Set the loss function
     *
     * @param lossFunction Loss fucntion
     */
    public void setLossFunction(LossFunction lossFunction) {
        if (lossFunction == null)
            throw new NullPointerException("lossFucntion can't be null.");

        this.lossFunction = lossFunction;
    }

    // endregion

    // region stop loss threshold

    /**
     * Set the absolute loss threshold
     * Iteration will be stop if the average loss is &lt; threshold
     *
     * @param threshold Threshold
     */
    public void setStopAbsoluteLoss(double threshold) {
        this.stopLossType = STOP_ABSOLUTE_LOSS;
        this.stopLossThreshold = threshold;
    }

    /**
     * Set the relative loss threshold
     * Iteration will be stop if the average loss improvement &lt; threshold in %;
     *
     * @param threshold Threshold
     */
    public void setStopRelativeLoss(double threshold) {
        this.stopLossType = STOP_RELATIVE_LOSS;
        this.stopLossThreshold = threshold;
    }

    // endregion


    //endregion

    /**
     * Optimize the parameters in the objective function.
     *
     * @param x Training data x
     * @param y Training data y
     * @return Return the optimized parameters
     */
    public double[] solve(double[] x, double[] y) {
        // Throw Exception
        if (x.length != y.length)
            throw new IllegalArgumentException("The length of x (" + x.length + ") must be equal to the length of y (" + y.length + ").");

        // Initialize
        int numOfSample = x.length;
        int numOfParameters = this.objectiveFunction.getParameter().length;
        this.gradientDescentMethod.Reset(numOfParameters);
        if (this.learningRateDecayMethod != null) this.learningRateDecayMethod.reset();
        double currentLearningRate = this.initLearningRate;

        // Initialize theta
        double[] theta;
        if (this.initParameters != null)
            theta = this.initParameters.clone();
        else {
            theta = new double[numOfParameters];
            for (int i = 0; i < numOfParameters; i++) {
                theta[i] = 1;
            }
        }

        // Iteration
        boolean stopIteration = false;
        int iter = 0;
        double previousLoss = -1;
        while (!stopIteration && iter < this.maxEpochs) {
            if (verbose)
                System.out.print("Iteration = " + iter);

            // Learning Rate
            if (this.learningRateDecayMethod != null) {
                currentLearningRate = this.learningRateDecayMethod.getLearningrate(this.initLearningRate, currentLearningRate, iter, previousLoss);
            }
            if (verbose)
                System.out.print(" learning rate = " + currentLearningRate);

            // Set current theta to objective function
            this.objectiveFunction.setParameter(theta);

            // Calculate f(x) and df(x)/da
            double[] fx = this.objectiveFunction.function(x);
            double[][] dfxd0 = this.objectiveFunction.derivativeFunctionParameters(x);

            // Calculate derivative theta
            double[][] derivativeThetaAll = new double[numOfSample][];  // [sample][parameter]
            for (int si = 0; si < numOfSample; si++) {
                derivativeThetaAll[si] = new double[numOfParameters];
                for (int pi = 0; pi < numOfParameters; pi++) {
                    derivativeThetaAll[si][pi] = this.lossFunction.derivativeLoss(y[si], fx[si], dfxd0[si][pi]);
                }
            }

            // Calculate average derivative theta and regularization
            double[] derivativeTheta = new double[numOfParameters];
            for (int pi = 0; pi < numOfParameters; pi++) {

                // Average
                CumulativeAverage average = new CumulativeAverage();
                for (int si = 0; si < numOfSample; si++) {
                    average.add(derivativeThetaAll[si][pi]);
                }
                derivativeTheta[pi] = average.getMean();

                // Regularization
                if (this.regularization != null)
                    derivativeTheta[pi] += this.regularization.derivativeLoss(theta);
            }

            // Calculate Gradient descient
            double[] newTheta = this.gradientDescentMethod.calculateTheta(currentLearningRate, theta, derivativeTheta);

            // Fix parameter range
            if (this.parameterRange != null) {
                for (int pi = 0; pi < newTheta.length; pi++) {
                    if (!Double.isNaN(this.parameterRange[pi][0]) && newTheta[pi] < this.parameterRange[pi][0]) {
                        newTheta[pi] = this.parameterRange[pi][0];
                    } else if (!Double.isNaN(this.parameterRange[pi][1]) && newTheta[pi] > this.parameterRange[pi][1]) {
                        newTheta[pi] = this.parameterRange[pi][1];
                    }
                }
            }

            // Calculate Loss
            this.objectiveFunction.setParameter(newTheta);
            CumulativeAverage lossAverage = new CumulativeAverage();
            double[] predictY = this.objectiveFunction.function(x);
            for (int si = 0; si < numOfSample; si++) {
                lossAverage.add(this.lossFunction.loss(y[si], predictY[si]));
            }
            double loss = lossAverage.getMean();
            if (verbose)
                System.out.print(" loss = " + loss);

            // Stop

            //    Get loss threshold
            double lossThreshold = this.stopLossThreshold;
            if (this.stopLossType == STOP_RELATIVE_LOSS) {
                lossThreshold = loss * this.stopLossThreshold;
            }

            //    Check
            double lossDiff = previousLoss - loss;
            if (lossDiff > 0 && lossDiff < lossThreshold)
                stopIteration = true;

            //    Update
            previousLoss = loss;

            // Update theta
            if (verbose)
                System.out.println(" theta:" + JGeneralUtils.toString(theta) + " => " + JGeneralUtils.toString(newTheta));
            theta = newTheta;

            // Update Iteration
            iter++;
        }

        return theta;
    }


}
