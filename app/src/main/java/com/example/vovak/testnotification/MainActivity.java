package com.example.vovak.testnotification;



import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.vovak.testnotification.database.Information;
import com.example.vovak.testnotification.fragment.FragmentView;
import com.example.vovak.testnotification.fragment.OnChangeFragmentListener;

import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements OnChangeFragmentListener {
    public final static String NOTIFICATION_ACTION = "notification_action";
    private List<Information> mInformationList;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        mInformationList = getDataBaseInformation();
        viewFragment(mInformationList.get(mInformationList.size()-1).getId());
    }


    @Override
    public void onAddFragment() {
            Log.d("LOG","ADD");
            int i = mInformationList.size();
            addInformation(i);
            viewFragment(getLastId());
    }

    @Override
    public void onRemoveFragment() {
            Log.d("LOG","REMOVE");
            int i = mInformationList.size() - 1;
            removeInformation(i);
            viewFragment(getLastId());
    }

    private void viewFragment(int i){
        FragmentView fragmentView = FragmentView.newInstance(i);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_conteiner,fragmentView);
        transaction.commit();
    }

    private List<Information> getDataBaseInformation(){
        realm.beginTransaction();
        if(realm.where(Information.class).findAll().isEmpty()) {
            Information information = realm.createObject(Information.class);
            information.setId(1);
        }
            mInformationList = realm.where(Information.class).findAll();
        realm.commitTransaction();
        return mInformationList;
    }

    private void addInformation(final int i){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                Information information = realm.createObject(Information.class);
                information.setId(i + 1);
            }
        });
    }

    private void removeInformation(final int i){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                List<Information> listInformation = realm.where(Information.class).findAll();
                if(listInformation.size()>1){
                    Information information = listInformation.get(i);
                    assert information != null;
                    information.deleteFromRealm();
                }
            }
        });
    }

    private int getLastId(){
        int id;
        realm.beginTransaction();
        List<Information> information= realm.where(Information.class).findAll();
        id = information.get(information.size()-1).getId();
        realm.commitTransaction();
        mInformationList = information;
        return id;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if(requestCode == 111){
            Log.d("TAG", "onActivityResult: "+requestCode);
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(realm!=null && !realm.isClosed())
        realm.close();
    }
}
