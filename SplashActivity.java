package com.mycompany.myapp;

import android.app.*;
import android.content.*;
import android.content.res.*;
import android.os.*;

public class SplashActivity extends Activity {

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashfile);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent intent=new Intent(SplashActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				}
			},3000);

    }
}
