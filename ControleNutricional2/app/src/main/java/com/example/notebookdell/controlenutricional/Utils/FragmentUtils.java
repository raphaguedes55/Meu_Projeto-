package com.example.notebookdell.controlenutricional.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.notebookdell.controlenutricional.R;

public class FragmentUtils {

    public static void replace(FragmentActivity activity, Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPrincipal, fragment).commit();
    }
}
