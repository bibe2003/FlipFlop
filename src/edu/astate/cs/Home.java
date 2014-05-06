package edu.astate.cs;

import java.util.List;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;


public class Home extends Activity {
	
	 public static boolean mIsBound = false;
	 static boolean continueMusic = false;
	 public static MusicService mServ;
	 private static  FreePlayOpenHelper fpDB; //use to add and get scores from each game mode's database
	 private static TimeBombOpenHelper tbDB;
	 private static ChallengeOpenHelper chDB;

	 private Intent music;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		continueMusic = getIntent().getExtras().getBoolean("boolean");
	
		if (continueMusic == true)
		{
			doBindService(); // bind music service
			
			music = new Intent(); // explicit intent
			music.setClass(this,MusicService.class);
			startService(music);
		}
		
		fpDB = new FreePlayOpenHelper(this); //create instance of fp database
		tbDB = new TimeBombOpenHelper(this);
		chDB = new ChallengeOpenHelper(this);

		
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

			}
		}



	@Override
    public void onDestroy () //when main is destroyed, kill the music as well
    {
		super.onDestroy();
        if(mIsBound == true)
        {
        	doUnbindService();
        }
        
       
        fpDB.close();
        tbDB.close();
        chDB.close(); //close each database after application closes
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
	
	public void scoresClick (View view)
	{
		if (mIsBound)
		{
			continueMusic = true;
		}
		else
		{
			continueMusic = false;
		}
		Intent viewIntent = new Intent(this, ViewScores.class);
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
					Scon,Context.BIND_ADJUST_WITH_ACTIVITY);
			mIsBound = true;
		}

		public void doUnbindService()
		{
			if(mIsBound)
			{
				unbindService(Scon);
				stopService(music);
	      		mIsBound = false;
	      		continueMusic = false;
			}
		}
		
	//set method for freeplay database	
	public static void addFPScore (int score)
	{
		fpDB.addScore(score); //insert score into database
	}
	
	
	//get method for freeplay scores
	public static List<String> getFPScores ()
	{
		return fpDB.getAllScores(); //return scores from the database
	}
	
	//set method for Challenge database	
	public static void addCHScore (int score)
	{
		chDB.addScore(score); //insert score into database
	}
	
	
	//get method for challenge scores
	public static List<String> getCHScores ()
	{
		return chDB.getAllScores(); //return scores from the database
	}
	
	
	//set method for timebomb database	
	public static void addTBScore (int score)
	{
		tbDB.addScore(score); //insert score into database
	}
	
	
	//get method for timebomb scores
	public static List<String> getTBScores ()
	{
		return tbDB.getAllScores(); //return scores from the database
	}
		
	
	
}
