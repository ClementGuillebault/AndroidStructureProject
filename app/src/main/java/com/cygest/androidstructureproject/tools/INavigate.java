package com.cygest.androidstructureproject.tools;

import androidx.fragment.app.Fragment;

public interface INavigate {
    void navigateTo(Fragment fragment, boolean addToBackStack, String tag);
}
