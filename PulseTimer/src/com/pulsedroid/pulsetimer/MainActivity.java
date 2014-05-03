package com.pulsedroid.pulsetimer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import com.pulsedroid.pulsetimer.R;

public class MainActivity extends SlidingActivity {
	
	private EditText hoursEdit;
	private EditText minutesEdit;
	private EditText secondsEdit;

	private int hours;
	private int minutes;
	private int seconds;

	public static NotificationManager mNotificationManager;
	public static int NOTIF = 1;
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      
      // About sliding menu
      
      setBehindContentView(R.layout.activity_menu);
     
      SlidingMenu sm = getSlidingMenu();
      sm.setMode(SlidingMenu.LEFT);
      sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
      sm.setBehindOffset(150);
      sm.setShadowWidthRes(R.dimen.shadow_width);
      sm.setShadowDrawable(R.drawable.shadow);
      
      // Time input
        
      hoursEdit = (EditText) findViewById(R.id.hoursBox);
      minutesEdit = (EditText) findViewById(R.id.minutesBox);
      secondsEdit = (EditText) findViewById(R.id.secondsBox);
      Button startButton = (Button) findViewById(R.id.startButton);
      
      // Initiate Start button
       
      startButton.setOnClickListener(new View.OnClickListener() {
    	  		
    	  		// When user leaves spaces blank = 0
    	  
    	  public void onClick(View v) {
    		  if(!(hoursEdit.getText().toString().equals("") 
    				  && minutesEdit.getText().toString().equals("")
    				  && secondsEdit.getText().toString().equals(""))) {
    			    
    			  Intent startCountdown = new Intent(MainActivity.this, TimerActivity.class);
		   
    			  if(hoursEdit.getText().toString().equals(""))
    				  hoursEdit.setText("0");
		    
    			  if(minutesEdit.getText().toString().equals(""))
    				  minutesEdit.setText("0");
		    
    			  if(secondsEdit.getText().toString().equals(""))
    				  secondsEdit.setText("0");
				    		
    			  hours = Integer.parseInt(hoursEdit.getText().toString());
    			  minutes = Integer.parseInt(minutesEdit.getText().toString());	
    			  seconds = Integer.parseInt(secondsEdit.getText().toString());
		    
    			  startCountdown.putExtra("hours", hours);
    			  startCountdown.putExtra("minutes", minutes);
    			  startCountdown.putExtra("seconds", seconds);
    			  
    			  // Notification 
    			  
    			  String ns = Context.NOTIFICATION_SERVICE;
    			  mNotificationManager = (NotificationManager) getSystemService(ns);
    			  CharSequence tickerText = "Timer active";
    			  int icon = R.drawable.ic_launcher;
        
    			  long when = System.currentTimeMillis();

    			  Notification notification = new Notification(icon, tickerText, when);
        
    			  CharSequence contentTitle = "PulseTimer";
    			  CharSequence contentText = "Click to view";
    			  Intent notificationIntent = new Intent(MainActivity.this, TimerActivity.class);
    			  PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent, NOTIF);
    			  notification.setLatestEventInfo(MainActivity.this, contentTitle, contentText, pIntent);
    			  notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        
    			  mNotificationManager.notify(NOTIF, notification);
        
    			  startActivity(startCountdown);
    		  }
    	  }
    	 });
      
    }
    
    protected void onResume(){
  
    	super.onResume();
    	
    	hoursEdit.setText("");
    	minutesEdit.setText("");
    	secondsEdit.setText("");
    }
}
