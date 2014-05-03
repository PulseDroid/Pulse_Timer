package com.pulsedroid.pulsetimer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class TimerDonePopup extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog alert = showTimerFinishedDialog();
		
        alert.show();
	}
        // Show the done Dialog
	
	private AlertDialog showTimerFinishedDialog() {
		
		final AlertDialog alertDialog = new AlertDialog.Builder(this)
			.setTitle("PulseTimer")
			.setMessage("Time's up!")
		
			.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() { 
				public void onClick(DialogInterface dialog, int whichButton) { 
					TimerActivity.finishButton.performClick();
					finish();
				}              
			})
			.setNegativeButton("Create new", new DialogInterface.OnClickListener() { 
				public void onClick(DialogInterface dialog, int whichButton) { 
					TimerActivity.finishButton.performClick();
					Intent newTimer = new Intent(TimerDonePopup.this, MainActivity.class);
					startActivity(newTimer);
					finish();
				}              
			})
		.create();
		
	return alertDialog;
	}
}
