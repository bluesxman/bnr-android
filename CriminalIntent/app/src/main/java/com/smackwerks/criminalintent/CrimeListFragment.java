package com.smackwerks.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jwn on 3/26/2017.
 */

public class CrimeListFragment extends Fragment {
    private enum ItemType {CRIME, CALL_POLICE,};

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

    private abstract class AbstractCrimeHolder extends RecyclerView.ViewHolder {
        public AbstractCrimeHolder(LayoutInflater inflater, ViewGroup parent, int layoutId) {
            super(inflater.inflate(layoutId, parent, false));
        }

        public abstract void bind(Crime crime);
    }

    private class CrimeHolder extends AbstractCrimeHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Crime mCrime;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater, parent, R.layout.list_item_crime);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(crime.getTitle());
            mDateTextView.setText(crime.getDate().toString());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
        }

    }

    private class CallPoliceHolder extends AbstractCrimeHolder {
        public CallPoliceHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater, parent, R.layout.list_item_police);
        }

        @Override
        public void bind(Crime crime) {

        }
    }


    private class CrimeAdapter extends RecyclerView.Adapter<AbstractCrimeHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public AbstractCrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            switch (ItemType.values()[viewType]) {
                case CALL_POLICE:
                    return new CallPoliceHolder(layoutInflater, parent);
                case CRIME:
                    return new CrimeHolder(layoutInflater, parent);
                default:
                    return new CrimeHolder(layoutInflater, parent);
            }
        }

        @Override
        public void onBindViewHolder(AbstractCrimeHolder holder, int position) {
            holder.bind(mCrimes.get(position));
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (mCrimes.get(position).isRequiresPolice()) {
                return ItemType.CALL_POLICE.ordinal();
            } else {
                return ItemType.CRIME.ordinal();
            }
        }
    }

}
