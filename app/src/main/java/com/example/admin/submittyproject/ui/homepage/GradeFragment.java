package com.example.admin.submittyproject.ui.homepage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.submittyproject.R;

public class GradeFragment extends android.support.v4.app.Fragment {
    public GradeFragment() {
        // Required empty public constructor
    }

    public static GradeFragment getInstane(){ return new GradeFragment(); }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grade, container, false);
    }
}
