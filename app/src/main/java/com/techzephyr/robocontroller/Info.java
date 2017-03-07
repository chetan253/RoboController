package com.techzephyr.robocontroller;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.widget.TextView;

public class Info extends Activity {
	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		tv = (TextView)findViewById(R.id.infotxt);
		tv.setText("Developer:\nChetan patil\n\nConcept :\nSanket L Humane\n\nProgramming assistance :\nRohit Korlekar\n\nSpecial Thanks to:\nYash Manian & Saiprasad Balasubramanian");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info, menu);
		return true;
	}

}
