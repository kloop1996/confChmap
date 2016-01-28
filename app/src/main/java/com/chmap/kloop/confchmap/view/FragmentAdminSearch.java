package com.chmap.kloop.confchmap.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chmap.kloop.confchmap.R;

/**
 * Created by android on 09.04.2015.
 */
public class FragmentAdminSearch extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.fragment_admin, container, false);
        return rootView;
    }

    @Override
    public void onClick(View v) {

    }
}
