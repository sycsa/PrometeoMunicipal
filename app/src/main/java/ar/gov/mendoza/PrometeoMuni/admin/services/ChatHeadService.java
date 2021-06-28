package ar.gov.mendoza.PrometeoMuni.admin.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;


public class ChatHeadService extends Service {

	  private WindowManager windowManager;
	  private ImageView chatHead;

	  @Override public IBinder onBind(Intent intent) {
	    // Not used
	    return null;
	  }

	  @Override public void onCreate() {
	    super.onCreate();

	    windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

	    View disableStatusBar = new View(this);
	     WindowManager.LayoutParams handleParams = new WindowManager.LayoutParams(
	         WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT,//<height of the status bar>
	         // This allows the view to be displayed over the status bar
	         WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
	         // this is to keep button presses going to the background window
	         WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
	         // this is to enable the notification to recieve touch events
	         WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
	         // Draws over status bar
	         WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
	         PixelFormat.TRANSLUCENT);
	     handleParams.gravity = Gravity.TOP;
	     
	     windowManager.addView(disableStatusBar, handleParams);
	   /* chatHead = new ImageView(this);
	    chatHead.setImageResource(R.drawable.network);

	    WindowManager.LayoutParams params = new WindowManager.LayoutParams(
	        WindowManager.LayoutParams.WRAP_CONTENT,
	        WindowManager.LayoutParams.WRAP_CONTENT,
	        WindowManager.LayoutParams.TYPE_PHONE,
	        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
	        PixelFormat.TRANSLUCENT);

	    
	    params.gravity = Gravity.TOP | Gravity.LEFT;
	    params.x = 0;
	    params.y = 100;

	    windowManager.addView(chatHead, params);*/
	  }

	  @Override
	  public void onDestroy() {
	    super.onDestroy();
	    if (chatHead != null) windowManager.removeView(chatHead);
	  }
	}
