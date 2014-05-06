package edu.astate.cs;


import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.TextView;

public class TimeBomb extends Activity {
	private CountDownTimer timebombCountdown;
	public TextView timerText;
	private final long startTime = 4 * 1000; 
	private final long interval = 1 * 1000;
	private boolean continueMusic = false;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_bomb);

		Intent i = getIntent();
		i.getBooleanExtra("boolean", continueMusic);
		
		//use to set up countdown timer
		timerText = (TextView) this.findViewById(R.id.tbCountdown);
		timebombCountdown = new MyCountDownTimer(startTime,interval);
		timerText.setText(timerText.getText() + String.valueOf(startTime/1000));
				
		timebombCountdown.start();
		


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.time_bomb, menu);
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
    
	public class MyCountDownTimer extends CountDownTimer //use for actual timer
    {
        public MyCountDownTimer (long startTime, long interval)
        {
            super(startTime, interval); //pass to superclasses' constructor
        }


        @Override
        //Alert user to start game upon timer finish
        public void onFinish(){
            // override onFinish() to do nothing
         	
         }


        @Override
        //used to update timer value
        public void onTick (long millisUntilFinished)
        {
        		timerText.setText("" + millisUntilFinished / 1000);
            	if ((millisUntilFinished / 1000) == 1)
            	{
            		finishIt();
            	}
        }
        
     // function finishIt() to be able to start new intent earlier
        public void finishIt()
        {
        	// display 1 then wait half a second and start new activity
        	timerText.setText("1");
        	
        	//use handler to start new activity to capture flips
        	new Handler().postDelayed(new Runnable() {
    	        @Override
    	        public void run() {
    	            Intent Act2Intent = new Intent(TimeBomb.this, Start.class); 
    	            
            		if (Home.mIsBound)
     	    			continueMusic = true;
     	            else
     	            	continueMusic = false;
    	            
    	            Act2Intent.putExtra("boolean", continueMusic);
    	            startActivity(Act2Intent);  
    	     
    	            }
    	        }, 500); 
        }

    }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
	    if(keyCode == KeyEvent.KEYCODE_BACK) {
	    	
	            Intent Act2Intent = new Intent(this, PlayOptions.class);
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

