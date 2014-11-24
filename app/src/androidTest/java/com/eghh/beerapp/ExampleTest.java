package com.eghh.beerapp;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;

import com.eghh.beerapp.common.utilities.DataBaseHelper;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ExampleTest extends InstrumentationTestCase {
    //fail test
    public void test() throws Exception {
//        DataBaseHelper dbh = new DataBaseHelper();
        final int expected = 1;
        final int reality = 5;
        assertEquals(expected, reality-4);
    }
}