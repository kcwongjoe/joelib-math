package com.kcwongjoe.joelib.math;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class CumulativeAverageTest {



    @Test public void testCalculate() {

        // test int array
        int[] intArr = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        assertEquals("calculate() fail with integer array.", 5.5, CumulativeAverage.calculate(intArr), 0.00001);

        // test int array
        float[] floatArr = new float[] { 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f };
        assertEquals("calculate() fail with float array.", 5.5, CumulativeAverage.calculate(floatArr), 0.00001);

        // test double array
        double[] doubleArr = new double[] { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0 };
        assertEquals("calculate() fail with double array.", 5.5, CumulativeAverage.calculate(doubleArr), 0.00001);

        // test Double array
        Double[] doubleClassArr = new Double[] { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0 };
        assertEquals("calculate() fail with Double array.", 5.5, CumulativeAverage.calculate(doubleClassArr), 0.00001);

        // test null Double array
        Double[] doubleNullClassArr = new Double[] {null, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, null, 7.0, 8.0, 9.0, 10.0, null};
        assertEquals("calculate() fail with Double array contains null.", 5.5, CumulativeAverage.calculate(doubleNullClassArr), 0.00001);

        // test double array list
        ArrayList<Double> doubleArrList = new ArrayList<>();
        doubleArrList.add(1.0);
        doubleArrList.add(2.0);
        doubleArrList.add(3.0);
        doubleArrList.add(4.0);
        doubleArrList.add(5.0);
        doubleArrList.add(6.0);
        doubleArrList.add(7.0);
        doubleArrList.add(8.0);
        doubleArrList.add(9.0);
        doubleArrList.add(10.0);

        assertEquals("calculate() fail with double array list.", 5.5, CumulativeAverage.calculate(doubleArrList), 0.00001);

        // test null Double array list
        ArrayList<Double> doubleArrNullList = new ArrayList<>();
        doubleArrNullList.add(null);
        doubleArrNullList.add(1.0);
        doubleArrNullList.add(2.0);
        doubleArrNullList.add(3.0);
        doubleArrNullList.add(4.0);
        doubleArrNullList.add(5.0);
        doubleArrNullList.add(6.0);
        doubleArrNullList.add(null);
        doubleArrNullList.add(7.0);
        doubleArrNullList.add(8.0);
        doubleArrNullList.add(9.0);
        doubleArrNullList.add(10.0);
        doubleArrNullList.add(null);
        assertEquals("calculate() fail with double array list contains null.", 5.5, CumulativeAverage.calculate(doubleArrNullList), 0.00001);
    }

    @Test public void testNonFiniteValues() {

        // Test NaN
        double[] nullArr = new double[] {Double.NaN, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, Double.NaN, 7.0, 8.0, 9.0, 10.0, Double.NaN};       
        assertEquals("Test fail with NaN.", 5.5, CumulativeAverage.calculate(nullArr), 0.00001);

        // Test Infinite
        double[] infiniteArr = new double[] {Double.POSITIVE_INFINITY, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, Double.NEGATIVE_INFINITY, 7.0, 8.0, 9.0, 10.0, Double.POSITIVE_INFINITY};       
        assertEquals("Test fail with NaN.", 5.5, CumulativeAverage.calculate(infiniteArr), 0.00001);
    }
}
