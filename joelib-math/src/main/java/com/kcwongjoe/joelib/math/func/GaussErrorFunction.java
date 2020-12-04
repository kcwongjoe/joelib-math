package com.kcwongjoe.joelib.math.func;

/**
 * Gauss error functio, erf(x) = 2 / sqrt(pi) * Integration(0,z,exp(-t^2) dt).
 * <p>
 * It is solved by the taylor series.
 */
public class GaussErrorFunction {

    private double epsilon = 1e-15;
    private int maxIterations = 10000;

    /**
     * Constructor
     */
    public GaussErrorFunction() {

    }

    /**
     * Constructor
     * 
     * @param epsilon       Stop if (nth series term) / result &lt; epsilon
     * @param maxIterations Maximum number of iteration
     */
    public GaussErrorFunction(double epsilon, int maxIterations) {
        setEpsilon(epsilon);
        setMaxIterations(maxIterations);
    }

    // region Getter and Setter

    /**
     * Set Epsilon of the taylor series
     * 
     * @param epsilon Stop if (nth series term) / result &lt; epsilon
     */
    public void setEpsilon(double epsilon) {
        if (epsilon <= 0)
            epsilon = 2 * Double.MIN_VALUE;

        this.epsilon = epsilon;
    }

    /**
     * Return the epsilon
     * 
     * @return Return the epsilon
     */
    public double getEpsilon() {
        return this.epsilon;
    }

    /**
     * Set Maximum iterations of the taylor series
     * 
     * @param maxIterations Maximum number of iteration
     */
    public void setMaxIterations(int maxIterations) {
        if (maxIterations <= 0)
            throw new IllegalArgumentException("maxIterations (" + maxIterations + ") must be > 0.");

        this.maxIterations = maxIterations;
    }

    /**
     * Return the Maximum iterations of the taylor series
     * 
     * @return Return the Maximum iterations of the taylor series
     */
    public double getMaxIterations() {
        return this.maxIterations;
    }

    // endregion Getter and Setter

    /**
     * Calculate the erf(x)
     * 
     * @param x x
     * @return Return erf(x)
     */
    public double calculate(double x) {
        double result = x;

        double z2k = 1;
        int n = 1;
        double partialTerm = x;
        int iter = 0;

        double x2 = x * x;

        // Calculate taylor series
        while (Math.abs(partialTerm / result) > this.epsilon && iter < this.maxIterations) {

            z2k *= -x2 / n;
            partialTerm = x / (2 * n + 1) * z2k;
            result += partialTerm;

            n++;
            iter++;
        }

        result *= 2 / Math.sqrt(Math.PI);

        return result;
    }

    /**
     * Calcualte the complementary error function
     *
     * @param x x
     * @return Retrun the erfc(x)
     */
    public double calculateComplementary(double x){
        return 1 - calculate(x);
    }

    /**
     * Derivative of erf(x)
     * @param x X
     * @return Return the Derivative of erf(x)
     */
    public static double derivative(double x){
        return 2 / Math.sqrt(Math.PI) * Math.exp(-x * x);
    }

    /**
     * Gauss error function
     *
     * @param x x
     * @return Return erf(x)
     */
    public static double erf(double x) {
        return erf(x, 1e-15, 10000);
    }

    /**
     * Gauss error function
     *
     * @param x             x
     * @param epsilon       Stop if (nth series term) / result &lt; epsilon
     * @param maxIterations Maximum number of iteration
     * @return Retrun erf(x)
     */
    public static double erf(double x, double epsilon, int maxIterations) {

        GaussErrorFunction erf = new GaussErrorFunction(epsilon, maxIterations);
        return erf.calculate(x);
    }

    /**
     * The complementary error function
     *
     * @param x x
     * @return Retrun the erfc(x)
     */
    public static double erfc(double x) {
        return 1 - erf(x);
    }

    /**
     * The complementary error function
     *
     * @param x x
     * @param epsilon       Stop if (nth series term) / result &lt; epsilon
     * @param maxIterations Maximum number of iteration
     * @return Retrun the erfc(x)
     */
    public static double erfc(double x, double epsilon, int maxIterations) {
        return 1 - erf(x, epsilon, maxIterations);
    }
}
