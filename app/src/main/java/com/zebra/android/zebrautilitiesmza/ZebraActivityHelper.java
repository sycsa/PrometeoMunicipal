 package com.zebra.android.zebrautilitiesmza;
 
 import android.app.Activity;
import android.content.Intent;
 import android.view.Menu;
import android.view.MenuItem;

 import java.util.ArrayList;
import java.util.List;

import com.zebra.android.zebrautilitiesmza.util.ZebraUtilitiesOptions;
 
 public class ZebraActivityHelper
 {
   private static boolean IS_BUSY = false;
   private static List<Activity> activeScreens = new ArrayList<Activity>();
   private int contentLayout;
   private Activity myActivity;
   private String title;
   //private UsbHandler usbHandler;
   
   public ZebraActivityHelper(Activity anActivity, String title, int contentLayout)
   {
          this.myActivity = anActivity;
		  this.title = title;
		  this.contentLayout = contentLayout;
   }
   
   public void onCreate()
   {
	   		//this.myActivity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		    this.myActivity.setContentView(this.contentLayout);
			this.updateTitleBar();
			activeScreens.add(this.myActivity);
//			UsbHandler localUsbHandler = null;
//			if(VERSION.SDK_INT>=12)
//			{
//				localUsbHandler = new UsbHandler(this);
//			}
//			this.usbHandler = localUsbHandler;
   }
   
   public void onDestroy()
   {
     activeScreens.remove(this.myActivity);
   }
   
   public static void startSpinner()
   {
     IS_BUSY = true;
     for (Activity a : activeScreens)
     {
//       ProgressBar mainProgressBar = (ProgressBar)a.findViewById(R.id.main_progress_bar);//2131296278);
//       mainProgressBar.setVisibility(0);
     }
   }
   
   public static void stopSpinner()
   {
     IS_BUSY = false;
     for (Activity a : activeScreens)
     {
//       ProgressBar mainProgressBar = (ProgressBar)a.findViewById(R.id.main_progress_bar);//2131296278);
//       mainProgressBar.setVisibility(4);
     }
   }
   
   void updateTitleBar()
   {
    // this.myActivity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title );//2130903043);
	 /*TextView titleBarLeftText = (TextView)this.myActivity.findViewById(R.id.left_text);//2131296276);
     
     TextView titleBarRightText = (TextView)this.myActivity.findViewById(R.id.right_text);//2131296277);
     ProgressBar mainProgressBar = (ProgressBar)this.myActivity.findViewById(R.id.main_progress_bar);//2131296278);
     titleBarLeftText.setTextColor(-1);
	 titleBarRightText.setTextColor(-1);
     int i=0;
     if (IS_BUSY) 
     {
       i = 0;
     }
	 else
	 { 
	   i=4;				  
	 }
       mainProgressBar.setVisibility(i);
	   titleBarLeftText.setText(this.title);
	   titleBarRightText.setText(ZebraUtilitiesOptions.getSelectedPrinterDisplayString());
	   */
   }
   
   public void onResume()
   {
//     if (this.usbHandler != null) 
//     {
//       this.usbHandler.enable();
//     }
     updateTitleBar();
   }
   
   public void onPause()
   {
//     if (this.usbHandler != null) {
//       this.usbHandler.disable();
//     }
   }
   
   public void onNewIntent(Intent intent)
   {
     this.myActivity.setIntent(intent);
   }
   
   public void onPrepareOptionsMenu(Menu menu)
   {
     MenuItem menuItem = menu.findItem(2131296357);
     if (menuItem == null) {
       return;
     }
     if (ZebraUtilitiesOptions.isBluetoothPrinterSelected())
     {
       menuItem.setVisible(false);
     }
     else
     {
     menuItem.setVisible(true);
     }
   }
   
   public boolean onCreateOptionsMenu(Menu menu)
   {
     return ZebraMenuCreator.createOptionsMenu(this.myActivity, 2131230720, menu);
   }
   
   public boolean optionsItemSelected(MenuItem item)
   {
     return ZebraMenuCreator.optionsItemSelected(this.myActivity, item);
   }
   
   public Activity getActivity()
   {
     return this.myActivity;
   }
 }