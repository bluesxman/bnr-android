package com.smackwerks.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by jwn on 3/26/2017.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
