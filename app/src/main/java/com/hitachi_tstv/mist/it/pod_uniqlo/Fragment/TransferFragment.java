package com.hitachi_tstv.mist.it.pod_uniqlo.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hitachi_tstv.mist.it.pod_uniqlo.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransferFragment extends Fragment {


    public TransferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transfer, container, false);
    }

}
