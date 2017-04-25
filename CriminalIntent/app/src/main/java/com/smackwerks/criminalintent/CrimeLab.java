package com.smackwerks.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jwn on 3/26/2017.
 */

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimes;
    private Map<UUID, Integer> indexes;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        indexes = new HashMap<>();

        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            addCrime(crime);
        }
    }

    private void addCrime(Crime crime) {
        int idx = mCrimes.size();
        mCrimes.add(crime);
        indexes.put(crime.getId(), idx);
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        return mCrimes.get(getPosition(id));
    }

    public int getPosition(UUID id) {
        return indexes.get(id);
    }
}
