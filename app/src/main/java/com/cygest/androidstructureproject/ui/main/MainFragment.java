package com.cygest.androidstructureproject.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cygest.androidstructureproject.R;
import com.cygest.androidstructureproject.databinding.FragmentUserListBinding;
import com.cygest.androidstructureproject.viewmodels.UserViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainFragment extends Fragment {

    public static final String TAG = MainFragment.class.getSimpleName();

    public FragmentUserListBinding binding;
    private UserViewModel userViewModel;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private MainAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        createAdapter();

        /* Observer for all users in db */
        observeUsers();

        /* Observer for user selected in recyclerview */
        observeUser();

        /* Observer for number of users in db */
        observeCountOfUsers();
    }

    /**
     * Called to create adapter and build click event.
     * Set user selected on UserViewModel
     */
    private void createAdapter() {
        adapter = new MainAdapter(userEntity -> userViewModel.getCurrentUser().setValue(userEntity));
    }

    /**
     * RxJava flowable + Paging data.
     * When
     */
    private void observeUsers() {
        disposables.add(userViewModel.getWithPaging().subscribe(
            userEntityPagingData -> adapter.submitData(getLifecycle(), userEntityPagingData),
            throwable -> Log.e(TAG, throwable.getMessage())
        ));
    }

    /**
     * When user click on item in recycler view, we set user to user in UserViewModel.
     * Then, this observer is called.
     */
    private void observeUser() {
        userViewModel.getCurrentUser().observe(getViewLifecycleOwner(),
            userEntity -> Snackbar.make(
                requireView(),
                getString(R.string.userChoosing, userEntity.getLogin()),
                BaseTransientBottomBar.LENGTH_LONG
            ).show());
    }

    /**
     * This observer will be executed only if we are in main fragment.
     * As opposed to the one present in the activity which will be executed as long as the activity exists.
     */
    private void observeCountOfUsers() {
        userViewModel.count().observe(getViewLifecycleOwner(), integer -> {
            if (integer == 0) {
                Snackbar.make(requireView(), getString(R.string.noUsersInDb), BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Override because we need to clean view binding when view is destroy
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * Override because we need to clear disposables when we destroy this fragment.
     * Otherwise, we would have memory leaks.
     */
    @Override
    public void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }
}
