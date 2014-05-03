package com.pulsedroid.pulsetimer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.pulsedroid.pulsetimer.R;

public class TimerActivity extends Activity   {
	
	public static int hours;
	public static int minutes;
	public static int seconds;
	
	public static TextView countdownmessage;
	public static Button finishButton;

	public static boolean timerisDone;
	private Timer countdown = null;
	private static int HELLO_ID = MainActivity.NOTIF;
	
	
	public void onCreate(Bundle savedInstanceState) {
		
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_timer);
	        countdownmessage = (TextView) findViewById(R.id.countdown_text);
	        finishButton= (Button) findViewById(R.id.finish_button);
	        
	         hours = getIntent().getIntExtra("hours", 0);
	         minutes = getIntent().getIntExtra("minutes", 0);
	         seconds = getIntent().getIntExtra("seconds", 0);
	        
	        long  time = ((hours * 60 * 60 * 1000) + (minutes * 60 * 1000) + (seconds * 1000));

	        countdown = new Timer(time,1000, this);
	        timerisDone = false;
	        
	        countdown.start();
	         
	        finishButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if(!timerisDone)
						confirmCancel();
					else
						end();
				}	
	        });
		}

	// If back button = Cancel
	
	@Override
	public void onBackPressed() {
		if(!timerisDone){
			confirmCancel();
		}
		else{
			end();
		}
	}
	
	// Confirmation before cancel
	
	public void confirmCancel(){
		AlertDialog.Builder confirmation = new AlertDialog.Builder(this);
	    confirmation.setMessage("Are you sure?")
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                    end();
	               }
	           })
	           .setNegativeButton("No", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                    dialog.cancel();
	               }
	           });
	    AlertDialog alert = confirmation.create();
	    alert.show();
	}
	
	// End countdown and their notifications
	
	private void end(){
		countdown.cancel();
		
		if(timerisDone) {
			Timer.ring.stop();
			Timer.vibrate.cancel();
		} else {
			Toast.makeText(TimerActivity.this, "Timer cancelled", Toast.LENGTH_SHORT).show();
		}
		
		MainActivity.mNotificationManager.cancel(HELLO_ID);
		finish();
	}
}

