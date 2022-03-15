package com.cygest.androidstructureproject.db.dao;

import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cygest.androidstructureproject.db.entities.UserEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

@Dao
public interface UserDAO {

    @Insert
    long insert(UserEntity userEntity);

    @Update
    void update(UserEntity userEntity);

    @Delete
    void delete(UserEntity userEntity);

    @Query("select * from users where id = :id")
    Maybe<UserEntity> get(int id);

    @Query("select * from users")
    PagingSource<Integer, UserEntity> getWithPaging();

    @Query("select count(1) from users")
    Flowable<Integer> count();
}
