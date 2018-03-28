package com.example.vovak.testnotification.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vovak on 27.03.2018.
 */

public class Information extends RealmObject {

    private int id;
    private String titleNotification;
    private String textNotification;



    public void setTitleNotification(String titleNotification) {
        this.titleNotification = titleNotification;
    }

    public void setTextNotification(String textNotification) {
        this.textNotification = textNotification;
    }

    public String getTitleNotification() {
        return titleNotification;
    }

    public String getTextNotification() {
        return textNotification;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
