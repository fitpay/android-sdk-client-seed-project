package com.example;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.fitpay.android.utils.Command;
import com.fitpay.android.utils.Listener;
import com.fitpay.android.utils.NotificationManager;
import com.fitpay.android.utils.RxBus;

import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private CustomListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text);
        listener = new CustomListener();

        NotificationManager.getInstance().addListener(listener, AndroidSchedulers.mainThread());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RxBus.getInstance().post("New text");
            }
        }, 2000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        NotificationManager.getInstance().removeListener(listener);
    }

    private class CustomListener extends Listener {
        CustomListener() {
            super();
            mCommands.put(String.class, new Command() {
                @Override
                public void execute(Object data) {
                    textView.setText((String) data);
                }
            });
        }
    }
}
