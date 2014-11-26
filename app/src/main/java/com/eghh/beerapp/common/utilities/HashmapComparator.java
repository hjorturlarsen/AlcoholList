package com.eghh.beerapp.common.utilities;

import android.widget.Switch;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Comparator is a helper class used to order beer list in ascending order
 * using the Collections.sort method
 */
public class HashmapComparator implements Comparator<HashMap<String, Object>>
{
    private final String key;

    public HashmapComparator(String key)
    {
        this.key = key;
    }

    // Compares two integer values (ratings) and determines where in the list to place the beer
    public int compare(HashMap<String, Object> first, HashMap<String, Object> second)
    {
        String firstValue = (String) first.get(key);
        String secondValue = (String) second.get(key);
        int fv = Integer.parseInt(firstValue);
        int sv = Integer.parseInt(secondValue);
        if (sv > fv)
            return 1;
        else if (fv == sv)
            return 0;
        else
            return -1;
        //return firstValue.compareTo(secondValue);
    }
}
