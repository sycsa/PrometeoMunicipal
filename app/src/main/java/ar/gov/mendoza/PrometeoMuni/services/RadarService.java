package ar.gov.mendoza.PrometeoMuni.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import ar.gov.mendoza.PrometeoMuni.rules.DeviceActasRules;

public class RadarService extends Service {

	
    private static final String TAG = "RadarService";

    private NotificationManager notifier = null;
    private boolean isRunning  = false;
    
	/** indicates how to behave if the service is killed */
	   int mStartMode = START_NOT_STICKY;
	   
	   /** interface for clients that bind */
	   IBinder mBinder;     
	   
	   /** indicates whether onRebind should be used */
	   boolean mAllowRebind;

	   /** Called when the service is being created. */
	   @Override
	   public void onCreate() {
	     
		   Log.i(TAG, "RadarService onCreate");

	        isRunning = true;
	        
	        notifier = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	   }

	   @Override
	   public void onStart(Intent intent, int startId)
	   {
		   super.onStart(intent, startId);
		   Log.i(TAG, "RadarService onStart , Level 3 o L4");
		   
		   doServiceStart(intent, startId);
	   }
	   
	   public void doServiceStart(Intent intent , int StartId)
	   {
		   performOnBackgroundThread(new Runnable() {
				@Override
				public void run() {
					
					
					DeviceActasRules deviceRules = new DeviceActasRules(getApplicationContext());
					deviceRules.actualizarPosicion();
	                
					/*
					Notification notify = new Notification(android.R.drawable.stat_notify_more,"Policia Caminera",System.currentTimeMillis());
					
					notify.flags |= Notification.FLAG_AUTO_CANCEL;
					
					Intent toLaunch = new Intent(getApplicationContext(),NullActivity.class);// ServiceControl.class);
					PendingIntent intentBack = PendingIntent.getActivity(getApplicationContext(), 0, toLaunch,0);
					
					notify.setLatestEventInfo(getApplicationContext(), "Policia Caminera"," " + System.currentTimeMillis(), intentBack);
					
					notifier.notify(0,notify);
					*/
					//Stop service once it finishes its task
	                stopSelf();
					
				}
			});
	   
	   }
	   /** The service is starting, due to a call to startService() */
	   @Override
	   public int onStartCommand(Intent intent, int flags, int startId) {
		   Log.i(TAG, "RadarService onStartCommand");
		   // Let it continue running until it is stopped.
		      Toast.makeText(this, "Iniciando Policia Caminera...", Toast.LENGTH_SHORT).show();
		      
		      if(flags!=0){
		    	  Log.w(TAG, "Redelivered  or retrying service start: "+flags); 
		      }

		      doServiceStart(intent, startId);
		      
		    
		      
	      return mStartMode;
	   }

	   
	   
	   public static Thread performOnBackgroundThread(final Runnable runnable) {
	        final Thread t = new Thread() {
	            @Override
	            public void run() {
	                try {
	                    runnable.run();
	                } finally {

	                }
	            }
	        };
	        t.start();
	        return t;
	    }
	   
	   /** A client is binding to the service with bindService() */
	   @Override
       public IBinder onBind(Intent intent) {
			// TODO Auto-generated method stub
			return mBinder;
	   }
	   
	   
	   /** Called when all clients have unbound with unbindService() */
	   @Override
	   public boolean onUnbind(Intent intent) {
	      return mAllowRebind;
	   }

	   /** Called when a client is binding to the service with bindService()*/
	   @Override
	   public void onRebind(Intent intent) {

	   }

	   /** Called when The service is no longer used and is being destroyed */
	   @Override
	   public void onDestroy() {
		    super.onDestroy();
		      Toast.makeText(this, "Listo ...", Toast.LENGTH_SHORT).show();
	   }
	
	
}
