package com.cygest.androidstructureproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.cygest.androidstructureproject.databinding.ActivityMainBinding;
import com.cygest.androidstructureproject.tools.INavigate;
import com.cygest.androidstructureproject.tools.Utility;
import com.cygest.androidstructureproject.ui.main.MainFragment;
import com.cygest.androidstructureproject.viewmodels.UserViewModel;
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity implements INavigate {

    public static final String TAG = MainActivity.class.getSimpleName();

    private UserViewModel userViewModel;
    private TextView menuItemCount;
    private Toolbar toolbar;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        toolbar = binding.getRoot().findViewById(R.id.menu);
        setSupportActionBar(toolbar);

        /* Owner is activity which allows to share the ViewModel with Fragments */
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        observeNumberOfUser();

        observeUser();

        navigateTo(new MainFragment(), false, TAG);
    }

    /**
     * To show how observer work in both activity and fragment
     */
    private void observeUser() {
        userViewModel.getCurrentUser().observe(this, userEntity -> {
            if (toolbar != null) {
                toolbar.setTitle(userEntity.getLogin());
            }
        });
    }

    /**
     * This observer will be executed as long as the activity exists.
     */
    private void observeNumberOfUser() {
        userViewModel.count().observe(this, integer -> menuItemCount.setText(String.valueOf(integer)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        menuItemCount = menu.findItem(R.id.nbOperationTotal).getActionView().findViewById(R.id.numberOfUsers);

        /* Example using FontAwesome to add icon in menu */
        menu.findItem(R.id.example1)
            .setIcon(Utility.iconToDrawable(this, FontAwesome.Icon.faw_trash_alt1, 44, R.color.teal_700));

        menu.findItem(R.id.example2)
            .setIcon(Utility.iconToDrawable(this, FontAwesome.Icon.faw_download, 44, R.color.purple_200));


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.example1) {
            disposables.add(userViewModel.get(1).subscribe(
                userEntity -> Toast.makeText(this, userEntity.getVar_to_ignore(), Toast.LENGTH_LONG).show(),
                throwable -> Log.e(TAG, throwable.getMessage()),
                () -> Toast.makeText(this, getString(R.string.noUsersInDb), Toast.LENGTH_LONG).show())
            );
            return true;
        }

        if (item.getItemId() == R.id.example2) {
            disposables.add(userViewModel.get(2)
                .doOnSuccess(userEntity -> Toast.makeText(this, userEntity.getVar_to_ignore(), Toast.LENGTH_LONG).show())
                .doOnError(throwable -> Log.e(TAG, throwable.getMessage()))
                .doOnTerminate(() -> Toast.makeText(this, getString(R.string.noUsersInDb), Toast.LENGTH_LONG).show())
                .subscribe());

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackStack, String tag) {
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, tag);
            transaction.setReorderingAllowed(true);

            if (addToBackStack) {
                transaction.addToBackStack(tag);
            }

            transaction.commitAllowingStateLoss();
        }
        catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }
}