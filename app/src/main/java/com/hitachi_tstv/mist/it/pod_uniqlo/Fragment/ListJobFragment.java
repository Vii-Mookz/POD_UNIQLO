package com.hitachi_tstv.mist.it.pod_uniqlo.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hitachi_tstv.mist.it.pod_uniqlo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListJobFragment extends Fragment {


    @BindView(R.id.tripListviewTrip)
    ListView tripListviewTrip;
    Unbinder unbinder;

    String TAG = ListJobFragment.class.getSimpleName();
    String[] DOStrings, sourceCodeStrings,locationStrings,loginStrings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_job, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
