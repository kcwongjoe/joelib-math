package com.kcwongjoe.joelib.math;

import java.util.Random;

/**
 * Math Utils
 **/
public class JMathUtilsEx {

    public static final double SQRT_2 = 1.414213562373095;
    public static final double PI_ORDER_2 = 1.570796326794897;

    //region Sinc

    /**
     * Sinc function
     *
     * @param x x
     * @return y y
     */
    public static double[] sinc(double[] x) {
        double[] result = new double[x.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = sinc(x[i]);
        }

        return result;
    }

    /**
     * Sinc function. If x = 0, Return 1 rather than Inf
     *
     * @param x x
     * @return y y
     */
    public static double sinc(double x) {
        if (x == 0)
            return 1;
        else
            return Math.sin(x) / x;
    }

    /**
     * Generate a impluse response by sinc
     * @param x x
     * @param center Center of the response
     * @param width Width of the response
     * @return Return the impluse response
     */
    public static double[] impulseResponse(double[] x, double center, double width) {

        double scale = Math.PI / (width / 2);

        double[] result = new double[x.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = sinc((x[i] - center) * scale);
        }

        return result;
    }

    //endregion

    //region Generate Random values

    //region rand

    /**
     * Generates <code>n</code> random points in the interval [<code>start</code>,<code>end</code>].
     *
     * @param start Lower margin of the array
     * @param end   Upper margin of the array
     * @param n     Number of point of the array
     * @return A random array
     * @throws IllegalArgumentException If <code>start</code> or <code>end</code> is invalid.
     * @throws IllegalArgumentException If <code>start &gt; end</code>.
     * @throws IllegalArgumentException If <code>n &lt;= 0</code>.
     */
    public static double[] rand(double start, double end, int n) {
        //Throw Exception
        if (!Double.isFinite(start))
            throw new IllegalArgumentException("start (" + start + ") is invalid.");
        if (!Double.isFinite(end))
            throw new IllegalArgumentException("end (" + end + ") is invalid.");
        if (start > end)
            throw new IllegalArgumentException("start (" + start + ") must be > end (" + end + ").");
        if (n <= 0)
            throw new IllegalArgumentException("n (" + n + ") must be > 0.");

        return rand(start, end, n, null);
    }

    /**
     * Generates <code>n</code> random points  in the interval [<code>start</code>,<code>end</code>].
     *
     * @param start Lower margin of the array
     * @param end   Upper margin of the array
     * @param n     Number of point of the array
     * @param seed  The seed for random generator. Set it as <code>null</code> without seed.
     * @return A random array
     * @throws IllegalArgumentException If <code>start</code> or <code>end</code> is invalid.
     * @throws IllegalArgumentException If <code>start &gt; end</code>.
     * @throws IllegalArgumentException If <code>n &lt;= 0</code>.
     */
    public static double[] rand(double start, double end, int n, Long seed) {
        //Throw Exception
        if (!Double.isFinite(start))
            throw new IllegalArgumentException("start (" + start + ") is invalid.");
        if (!Double.isFinite(end))
            throw new IllegalArgumentException("end (" + end + ") is invalid.");
        if (start > end)
            throw new IllegalArgumentException("start (" + start + ") must be > end (" + end + ").");
        if (n <= 0)
            throw new IllegalArgumentException("n (" + n + ") must be > 0.");


        //Construct the random generator
        Random rand;
        if (seed == null) {
            rand = new Random();
        } else {
            rand = new Random(seed);
        }

        //Generate random data
        double range = end - start;
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = rand.nextDouble() * range + start;
        }

        return result;
    }

    //endregion

    //region normrnd

    /**
     * Generates <code>n</code> random points from the normal distribution with mean <code>mu</code>
     * and standard deviation <code>sigma</code>.
     *
     * @param mu    mean
     * @param sigma standard deviation
     * @param n     Number of point of the array
     * @return A random array
     * @throws IllegalArgumentException If <code>mu</code> or <code>sigma</code> is invalid.
     * @throws IllegalArgumentException If <code>n &lt;= 0</code>.
     */
    public static double[] normrnd(double mu, double sigma, int n) {
        //Throw Exception
        if (!Double.isFinite(mu))
            throw new IllegalArgumentException("mu (" + mu + ") is invalid.");
        if (!Double.isFinite(sigma))
            throw new IllegalArgumentException("sigma (" + sigma + ") is invalid.");
        if (n <= 0)
            throw new IllegalArgumentException("n (" + n + ") must be > 0.");

        return normrnd(mu, sigma, n, null);
    }

    /**
     * Generates <code>n</code> random points from the normal distribution with mean <code>mu</code>
     * and standard deviation <code>sigma</code>.
     *
     * @param mu    mean
     * @param sigma standard deviation
     * @param n     Number of point of the array
     * @param seed  The seed for random generator. Set it as <code>null</code> without seed.
     * @return A random array
     * @throws IllegalArgumentException If <code>mu</code> or <code>sigma</code> is invalid.
     * @throws IllegalArgumentException If <code>n &lt;= 0</code>.
     */
    public static double[] normrnd(double mu, double sigma, int n, Long seed) {
        //Throw Exception
        if (!Double.isFinite(mu))
            throw new IllegalArgumentException("mu (" + mu + ") is invalid.");
        if (!Double.isFinite(sigma))
            throw new IllegalArgumentException("sigma (" + sigma + ") is invalid.");
        if (n <= 0)
            throw new IllegalArgumentException("n (" + n + ") must be > 0.");


        //Construct the random generator
        Random rand;
        if (seed == null) {
            rand = new Random();
        } else {
            rand = new Random(seed);
        }

        //Generate random data
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = rand.nextGaussian() * sigma + mu;
        }

        return result;
    }

    //endregion


    //endregion


}
