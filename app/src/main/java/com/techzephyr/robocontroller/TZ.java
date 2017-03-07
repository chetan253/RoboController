package com.techzephyr.robocontroller;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class TZ extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tz);
		new Handler().postDelayed(new Runnable() {
		    @Override
		    public void run() {
		        Intent startActivity = new Intent(TZ.this, MainActivity.class);
		        startActivity(startActivity);
		        finish();
		    }
		}, 2000);
}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();
	}
}