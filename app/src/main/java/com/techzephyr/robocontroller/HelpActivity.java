package com.techzephyr.robocontroller;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.widget.TextView;

public class HelpActivity extends Activity {
TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		tv = (TextView)findViewById(R.id.helptext);
		tv.setTextColor(Color.parseColor("#33B5E5"));
		tv.setText("Help :"+"\n"+"This Section describes the values of signals that are transmitted to arduino for controlling Robot"+"\n"+"w ==> FORWARD"
		+"\n"+"a ==> LEFT"+"\n"+"d ==> RIGHT"+"\n"+"s ==> BACK"+"\n"+"Note that the Application transmits the ASCII value of the alphabets mentioned above.");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
		return true;
	}

}
