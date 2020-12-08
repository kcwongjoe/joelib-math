package com.kcwongjoe.joelib.math;

import org.junit.Test;
import static org.junit.Assert.*;

import com.kcwongjoe.joelib.math.smoothfilter.MovingAverage;

public class MovingAverageTest {

    @Test
    public void testSmooth() {

        MovingAverage movingAverage = new MovingAverage(3);

        double[] x = new double[] { 4, 8, 6, -1, -2, -3, -1, 3, 4, 5 };

        double[] forwardResult = new double[] { 6, 13.0 / 3.0, 1, -2, -2, -1 / 3.0, 2, 4, Double.NaN, Double.NaN };
        assertArrayEquals("Fail on forward.", forwardResult, movingAverage.smooth(x), 0.001);

        movingAverage.setDirection(MovingAverage.CENTRAL);
        double[] centralResult = new double[] { Double.NaN, 6, 13.0 / 3.0, 1, -2, -2, -1 / 3.0, 2, 4, Double.NaN };
        assertArrayEquals("Fail on central.", centralResult, movingAverage.smooth(x), 0.001);

        movingAverage.setDirection(MovingAverage.BACKWARD);
        double[] backwardResult = new double[] { Double.NaN, Double.NaN, 6.0, 13.0 / 3.0, 1, -2, -2, -1 / 3.0, 2, 4 };
        assertArrayEquals("Fail on backward.", backwardResult, movingAverage.smooth(x), 0.001);

        // Average boundary
        movingAverage.averageBoundary = true;

        movingAverage.setDirection(MovingAverage.FORWARD);
        double[] forwardBoundaryResult = new double[] { 6, 13.0 / 3.0, 1, -2, -2, -1 / 3.0, 2, 4, 4.5, 5};
        assertArrayEquals("Fail on forward with boundary average.", forwardBoundaryResult, movingAverage.smooth(x), 0.001);

        movingAverage.setDirection(MovingAverage.CENTRAL);
        double[] centralBoundaryResult = new double[] { 6, 6, 13.0 / 3.0, 1, -2, -2, -1 / 3.0, 2, 4, 4.5 };
        assertArrayEquals("Fail on central.", centralBoundaryResult, movingAverage.smooth(x), 0.001);

        movingAverage.setDirection(MovingAverage.BACKWARD);
        double[] backwardBoundaryResult = new double[] { 4, 6, 6, 13.0 / 3.0, 1, -2, -2, -1 / 3.0, 2, 4 };
        assertArrayEquals("Fail on backward.", backwardBoundaryResult, movingAverage.smooth(x), 0.001);
    }

    @Test
    public void testSmoothWithNaN() {

        MovingAverage movingAverage = new MovingAverage(3);

        double[] x = new double[] { 4, 8, 6, Double.NaN, Double.NaN, Double.NaN, Double.NaN, -1, -2, Double.NaN, -1, 3, 4, 5 };

        double[] forwardResult = new double[] { 6, 7, 6, Double.NaN, Double.NaN, -1, -1.5, -1.5, -1.5, 1, 2, 4, Double.NaN, Double.NaN };
        assertArrayEquals("Fail on forward.", forwardResult, movingAverage.smoothWithNaN(x), 0.001);

        movingAverage.setDirection(MovingAverage.CENTRAL);
        double[] centralResult = new double[] { Double.NaN, 6, 7, 6, Double.NaN, Double.NaN, -1, -1.5, -1.5, -1.5, 1, 2, 4, Double.NaN };
        assertArrayEquals("Fail on central.", centralResult, movingAverage.smoothWithNaN(x), 0.001);

        movingAverage.setDirection(MovingAverage.BACKWARD);
        double[] backwardResult = new double[] { Double.NaN, Double.NaN, 6, 7, 6, Double.NaN, Double.NaN, -1, -1.5, -1.5, -1.5, 1, 2, 4 };
        assertArrayEquals("Fail on backward.", backwardResult, movingAverage.smoothWithNaN(x), 0.001);
    }
}