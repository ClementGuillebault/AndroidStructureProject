package com.cygest.androidstructureproject.repository;

import android.app.Application;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.cygest.androidstructureproject.db.DataBase;
import com.cygest.androidstructureproject.db.dao.UserDAO;
import com.cygest.androidstructureproject.db.entities.UserEntity;
import com.cygest.androidstructureproject.network.ApiService;
import com.cygest.androidstructureproject.network.Network;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Repository {

    /* Http request */
    public ApiService apiService;

    /* Db */
    public UserDAO userDAO;

    public Repository(Application application) {
        this.apiService = Network.getInstance();
        this.userDAO = DataBase.getInstance(application).userDAO();
    }

    /* CRUD example */

    public long insert(UserEntity userEntity) {
        return userDAO.insert(userEntity);
    }

    public void update(UserEntity userEntity) {
        userDAO.update(userEntity);
    }

    public void delete(UserEntity userEntity) {
        userDAO.delete(userEntity);
    }

    /* /CRUD */

    /**
     *
     * @return
     */
    public Flowable<PagingData<UserEntity>> getWithPaging() {
        return PagingRx.getFlowable(new Pager<>(new PagingConfig(20, 5, true, 20, 30), () -> userDAO.getWithPaging()));
    }

    public Flowable<Integer> count() {
        return userDAO.count().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Maybe<UserEntity> get(int id) {
        return userDAO.get(id);
    }
}
