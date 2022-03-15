package com.cygest.androidstructureproject.db.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {
    @PrimaryKey
    private int id;

    /* default value will block null value in db */
    private String login = "";
    private String pwd = "";
    private String address = "";

    @Ignore
    private String var_to_ignore;

    @Ignore
    public UserEntity() { }

    public UserEntity(int id, String login, String pwd, String address) {
        this.id = id;
        this.login = login;
        this.pwd = pwd;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVar_to_ignore() {
        return var_to_ignore;
    }

    public void setVar_to_ignore(String var_to_ignore) {
        this.var_to_ignore = var_to_ignore;
    }
}
