package edu.astate.cs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;


public class PlayOptions extends Activity {

	Boolean continueMusic = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_options);
		
		Intent i = getIntent();
		i.getBooleanExtra("boolean", continueMusic);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play_options, menu);
		return true;
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		if(Home.mIsBound == true)
		{		
			continueMusic = false;
	    	Home.mServ.resumeMusic();
		}
		
	}
	
	@Override
    public void onPause ()
    {
		super.onPause();
    	if(!continueMusic)
    	{
    		Home.mServ.pauseMusic();
    	}
    }
    
    @Override
    protected void onStop ()
    {
    	super.onStop();
    	if(!continueMusic)
    	{
    		Home.mServ.pauseMusic();
    	}
    }
    
    @Override
    protected void onRestart () 
    {
    	super.onRestart();
    	if(Home.mIsBound == true)
    	{
    		continueMusic = false;
        	Home.mServ.resumeMusic();
    	}
    	
    }

    public void challengeClick (View view)
	{
		Intent intent = new Intent (this, Challenge.class);
		if (Home.mIsBound)
			continueMusic = true;
		else
			continueMusic = false;
        intent.putExtra("boolean", continueMusic);
		startActivity(intent);
	}
	
	public void timeBombClick (View view)
	{
		Intent intent = new Intent (this, TimeBomb.class);
		if (Home.mIsBound)
			continueMusic = true;
		else
			continueMusic = false;
        intent.putExtra("boolean", continueMusic);
		startActivity(intent);
	}	
	
	public void freePlayClick (View view)
	{
		Intent intent = new Intent (this, FreePlay.class);
		if (Home.mIsBound)
			continueMusic = true;
		else
			continueMusic = false;
        intent.putExtra("boolean", continueMusic);
		startActivity(intent);
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
	    if(keyCode == KeyEvent.KEYCODE_BACK) {
	    	
	            Intent Act2Intent = new Intent(this, Home.class);
	            if (Home.mIsBound)
	    			continueMusic = true;
	            else
	            	continueMusic = false;
	            Act2Intent.putExtra("boolean", continueMusic);
	            startActivity(Act2Intent);  
	    		
	            finish();
	            return true;
	    }
	    return false;
	}
	
	
	
}
