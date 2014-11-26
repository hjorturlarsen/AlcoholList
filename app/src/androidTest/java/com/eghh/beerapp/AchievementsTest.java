package com.eghh.beerapp;

import android.app.Application;
import android.provider.ContactsContract;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContext;

import com.eghh.beerapp.common.utilities.DataBaseHelper;
import com.eghh.beerapp.common.utilities.DatabaseImages;

import java.io.IOException;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class AchievementsTest extends AndroidTestCase {
    MockContext mCox = new MockContext();
    private static final String TEST_DB_FILE = "TESTARI";
    private DataBaseHelper dbh;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), TEST_DB_FILE);

        this.dbh = new DataBaseHelper(context);
    }
    //fail test

    public void achievementsTest() throws Exception {
        int achievementCounter = DataBaseHelper.getAchievements().size();
        assertEquals(achievementCounter, 0);
        this.dbh.setAchievements();
        assertEquals(achievementCounter, 0);
        DataBaseHelper.getAchievements().add("1");
        int sss = DataBaseHelper.getAchievements().size();
        assertEquals(sss, 1);
        DataBaseHelper.getAchievements().clear();
        assertEquals(DataBaseHelper.getAchievements().size(), 0);
        for (int i = 0; i < 5; i++){
            DataBaseHelper.getAchievements().add("String" + i);
        }
        int achSize = DataBaseHelper.getAchievements().size();
        assertEquals(DataBaseHelper.getAchievements().size(), 5);
        assertEquals(DataBaseHelper.getAchievements().get(0), "String0");
        assertEquals(DataBaseHelper.getAchievements().get(1), "String1");
        assertEquals(DataBaseHelper.getAchievements().get(2), "String2");
        assertEquals(DataBaseHelper.getAchievements().get(3), "String3");
        assertEquals(DataBaseHelper.getAchievements().get(4), "String4");
    }
}