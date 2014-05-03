package com.pulsedroid.pulsetimer;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.Vibrator;

public class Timer extends CountDownTimer{
	
	public static Ringtone ring;
	public static Vibrator vibrate;
	private static int NOTIF2 = MainActivity.NOTIF;
	private Context mContext;
	private PowerManager power;
	
	 
	public Timer(long millisInFuture, long countDownInterval, Context mContext) {
			super(millisInFuture, countDownInterval);
			this.mContext = mContext;
	}
	
	@Override
	public void onFinish() {
		
		// Let the user know it's done
		
		Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		vibrate = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
		ring = RingtoneManager.getRingtone(mContext, notification);
		power = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = power.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "PulseTimer");
		final long[] pattern = {1,300,75,300,75,300,75,300,3000,300,75,300,75,300,75,300};
		
		// Finished Dialog
		
		Intent TimerFinishedDialog = new Intent(mContext,TimerDonePopup.class);
		TimerFinishedDialog.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		TimerFinishedDialog.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		TimerActivity.countdownmessage.setText("Done!");
		TimerActivity.finishButton.setText("Yay");
		TimerActivity.timerisDone = true;
		
		wl.acquire();
		
		mContext.getApplicationContext().startActivity(TimerFinishedDialog);
		MainActivity.mNotificationManager.cancel(NOTIF2);
		
		vibrate.vibrate(pattern, -1);
		ring.play();
		
		wl.release();
	}
	
	@Override
	public void onTick(long millisUntilFinished) {
		

		long remainingMs = millisUntilFinished;
		
		// Convert time to be Seconds / Minutes / Hours
		
		Integer h = new Integer((int) remainingMs/3600000);
		long remaining = remainingMs - (h*3600000);
		Integer m = new Integer((int) remaining/60000);
		remaining = remaining - (m*60000);
		Integer s = new Integer((int) (remaining /1000));
		
		String remainingH;
		String remainingM;
		String remainingS;
		
		if(h.equals(new Integer(0)))
			 remainingH = "00";
		else
			if(h<10)
				remainingH = "0"+h.toString();
			else
			remainingH = h.toString();
		
		if(m.equals(new Integer(0)))
			 remainingM = "00";
		else{
			if(m < 10)
				remainingM = "0"+m.toString();
			else
				remainingM = m.toString();
		}
		
		if(s<10)
			remainingS = "0"+s.toString();
		else
			remainingS = s.toString();
		
			
		if(remainingH.equals("00"))
			TimerActivity.countdownmessage.setText(remainingM+":"+remainingS);
		else
			TimerActivity.countdownmessage.setText(remainingH+":"+remainingM+":"+remainingS);
	}
	
}

