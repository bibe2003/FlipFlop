package edu.astate.cs;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;


public class Home extends Activity {
	
	 public static boolean mIsBound = false;
	 static boolean continueMusic = false;
	 static MusicService mServ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		continueMusic = getIntent().getExtras().getBoolean("boolean");
	
		if (continueMusic == true)
		{
			doBindService(); // bind music service
			
			Intent music = new Intent(); // explicit intent
			music.setClass(this,MusicService.class);
			startService(music);
		}
			
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		if(mIsBound == true)
		{
			continueMusic = false;
    		// mServ.resumeMusic();
		}
		
	}

	@Override
    public void onDestroy () //when main is destroyed, kill the music as well
    {
        if(mIsBound == true)
        {
        	doUnbindService();
        }
    }
	
	@Override
    public void onPause ()
    {
		super.onPause();
    	if(mIsBound == true && !continueMusic)
    	{
    		mServ.pauseMusic();
    	}
    }
    
    @Override
    protected void onStop ()
    {
    	super.onStop();
    	if(mIsBound && !continueMusic)
    	{
    		mServ.pauseMusic();
    	}
    }
    
    @Override
    protected void onRestart () 
    {
    	super.onRestart();
		if (mIsBound == true)
		{
			continueMusic = false;
    		mServ.resumeMusic();
		}
    	
    }
	
	public void instructions(View view) {
		if (mIsBound)
		{
			continueMusic = true;
		}
		else
		{
			continueMusic = false;
		}
		Intent viewIntent = new Intent(this, Instructions.class);
		viewIntent.putExtra("boolean", continueMusic);
		startActivity(viewIntent);
	}
	
	public void options(View view) {
		if (mIsBound)
		{
			continueMusic = true;
		}
		else
		{
			continueMusic = false;
		}
		Intent viewIntent = new Intent(this, Options.class);
		viewIntent.putExtra("boolean", continueMusic);
		startActivity(viewIntent);
	}
	
	public void disclaimer(View view) {
		if (mIsBound)
		{
			continueMusic = true;
		}
		else
		{
			continueMusic = false;
		}
		Intent viewIntent = new Intent(this, Disclaimer.class);
		viewIntent.putExtra("boolean", continueMusic);
		startActivity(viewIntent);
	}
	
	@Override
	public void onBackPressed() {
	}
	
	/*
	 * For music service
	 */
	private ServiceConnection Scon = new ServiceConnection() {

		public void onServiceConnected(ComponentName name, IBinder binder) {
			mServ = ((MusicService.ServiceBinder) binder).getService();
		}

		public void onServiceDisconnected(ComponentName name) {
			mServ = null;
		}
	};

		void doBindService(){
	 		bindService(new Intent(this,MusicService.class),
					Scon,Context.BIND_AUTO_CREATE);
			mIsBound = true;
		}

		void doUnbindService()
		{
			if(mIsBound)
			{
				unbindService(Scon);
	      		mIsBound = false;
	      		continueMusic = false;
			}
		}
    
}
