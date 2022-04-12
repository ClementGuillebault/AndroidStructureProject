package com.cygest.androidstructureproject.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagingData;

import com.cygest.androidstructureproject.db.entities.UserEntity;
import com.cygest.androidstructureproject.repository.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserViewModel extends AndroidViewModel {

    private final Repository repository;

    private MutableLiveData<UserEntity> currentUser;


    public UserViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
    }

    /**
     * Called to get a list of user from db.
     * @return LiveData<List<UserEntity>>
     */
    public MutableLiveData<UserEntity> getCurrentUser() {
        if (currentUser == null) {
            currentUser = new MutableLiveData<>();
        }

        return currentUser;
    }

    public Maybe<UserEntity> get(int id) {
        return repository.get(id)
            .map(userEntity -> {
                userEntity.setVar_to_ignore("What you want");
                return userEntity; })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }


    public Flowable<PagingData<UserEntity>> getWithPaging() {
        return repository.getWithPaging().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * Called to get number of user in db.
     * This feature is not very useful. It's just an example of LiveDataReactiveStreams with rxjava and a flowable
     * @return LiveData<Integer> number of user in table
     */
    public LiveData<Integer> count() {
        return LiveDataReactiveStreams.fromPublisher(repository.count());
    }
}
