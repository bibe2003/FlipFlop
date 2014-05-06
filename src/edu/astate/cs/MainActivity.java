package edu.astate.cs;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {
	 
	private boolean continueMusic = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    //Delay Code after 5 seconds
	    new Handler().postDelayed(new Runnable() {
	        @Override
	        public void run() {
	            Intent i = new Intent(MainActivity.this, Home.class);
	            i.putExtra("boolean", continueMusic);
	            startActivity(i);
	            }
	        }, 5000);   
	    
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
