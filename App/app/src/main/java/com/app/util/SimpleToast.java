package com.app.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hhh.appstore.R;

public class SimpleToast {

    static SimpleToast S;

    public static SimpleToast get(){
        if(S == null){
            S = new SimpleToast();
        }
        return S;
    }

    private Toast toast;

    private Context context;

    public void init(Context context){
        this.context = context;
    }

    public void show(String message){
        show(message, Gravity.CENTER, 0, 0);
    }

    public void show(String message, int gravity, int xOffset, int yOffset){
        if(context == null){
            return;
        }
        if(toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(context, null, Toast.LENGTH_LONG);
        View view = LayoutInflater.from(context).inflate(R.layout.simple_toast, null);
        toast.setView(view);
        // toast.setGravity(gravity, (int) (xOffset * toast.getView().getResources().getDisplayMetrics().density), (int) (yOffset * toast.getView().getResources().getDisplayMetrics().density));
        ((TextView)toast.getView().findViewById(R.id.tv_content)).setText(message);
        toast.show();
    }

    public void show(int stringId){
        if(context == null){
            return;
        }
        show(context.getResources().getString(stringId));
    }

}
