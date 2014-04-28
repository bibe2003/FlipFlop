package edu.astate.cs;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ToggleButton;

public class Options extends Activity {

	private boolean continueMusic = false;
	private ToggleButton tg;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        
        tg = (ToggleButton) findViewById(R.id.optionsToggle1);
        tg.setChecked(true);
        
        Intent intent = getIntent(); //get intent from button press
		intent.getBooleanExtra("boolean", continueMusic);
    }
	
	
	@Override
	protected void onResume()
	{
		super.onResume();
		if(Home.mIsBound == true)
		{
			tg.setChecked(true);
			continueMusic = false;
	    	Home.mServ.resumeMusic();
		}
		else
			tg.setChecked(false);
		
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
	public void soundToggleClick (View view)
    {
        // boolean on = ((ToggleButton) view).isChecked();

        if (tg.isChecked() == true) {
            // resume music from where you paused it
    		Home.mServ.resumeMusic();
    		Home.mIsBound = true;
        } else {
        	// pause media player from main activity
        	Home.mServ.pauseMusic();
        	Home.mIsBound = false;
        }
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
