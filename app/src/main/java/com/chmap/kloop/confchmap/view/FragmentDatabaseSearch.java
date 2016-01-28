package com.chmap.kloop.confchmap.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chmap.kloop.confchmap.R;

/**
 * Created by kloop on 26.08.2015.
 */
public class FragmentDatabaseSearch extends Fragment implements View.OnClickListener {
    @Override
    public void onClick(View view) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = (LinearLayout) inflater.inflate(R.layout.fragment_database_search, container, false);

        return view;
    }
}
