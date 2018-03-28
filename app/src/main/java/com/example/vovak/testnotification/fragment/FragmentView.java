package com.example.vovak.testnotification.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.vovak.testnotification.R;
import com.example.vovak.testnotification.notification.Notification;

/**
 * Created by vovak on 27.03.2018.
 */

public class FragmentView extends Fragment {
    public static final String ARGUMENT_ID = "argument_id_for_fragment";
    public OnChangeFragmentListener onChangeFragmentListener;

    private Context mContext;
    private Button mSetNotification;
    private FloatingActionButton mButtonAdd,mButtonRemove;
    private TextView mTextView;
    private Integer id;
    private Notification notification;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = (Integer) getArguments().getSerializable(ARGUMENT_ID);
    }

    public static FragmentView newInstance(int id) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_ID,id);
        FragmentView fragment = new FragmentView();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_view_fragment,container,false);

        mButtonAdd = view.findViewById(R.id.buttom_add);
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChangeFragmentListener.onAddFragment();
            }
        });

        mButtonRemove = view.findViewById(R.id.buttom_remove);
        if(id==1)
            mButtonRemove.setVisibility(View.INVISIBLE);
        mButtonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChangeFragmentListener.onRemoveFragment();
                if(notification!=null && id!=null){
                    notification.deleteNotification(id);
                }
            }
        });

        mSetNotification = view.findViewById(R.id.buttom_set_notification);
        mSetNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               notification = new Notification(mContext,id);
            }
        });

        mTextView = view.findViewById(R.id.text);
        mTextView.setText(String.valueOf(id));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        try {
            onChangeFragmentListener = (OnChangeFragmentListener) context;
        }catch (ClassCastException e){
            Log.d("LOG","Exception = "+e);
        }
    }
}
