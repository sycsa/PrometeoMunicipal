 package com.zebra.android.zebrautilitiesmza;
 
 import android.app.Activity;
 import android.content.Intent;
 import android.os.Bundle;
 import android.view.Menu;
 import android.view.MenuItem;
 
 public class ZebraActivityBase 
   extends Activity
 {
   protected ZebraActivityHelper activityHelper;
   
   public ZebraActivityBase(String title, int contentLayout)
   {
     this.activityHelper = new ZebraActivityHelper(this, title, contentLayout);
   }
   
   protected void onCreate(Bundle savedInstanceState)
   {
	   super.onCreate(savedInstanceState);
	   this.activityHelper.onCreate();
   }
   
   protected void onResume()
   {
	   super.onResume();
	   this.activityHelper.onResume();
   }
   
   protected void onPause()
   {
	   super.onPause();
	   this.activityHelper.onPause();
   }
   
   protected void onNewIntent(Intent intent)
   {
	   super.onNewIntent(intent);
	   this.activityHelper.onNewIntent(intent);
   }
   
   protected void onDestroy()
   {
	   super.onDestroy();
	   this.activityHelper.onDestroy();
   }
   
   public boolean onCreateOptionsMenu(Menu menu)
   {
     return this.activityHelper.onCreateOptionsMenu(menu);
   }
   
   public boolean onPrepareOptionsMenu(Menu menu)
   {
     this.activityHelper.onPrepareOptionsMenu(menu);
	 return super.onPrepareOptionsMenu(menu);
   }
   
   public boolean onOptionsItemSelected(MenuItem item)
   {
     return this.activityHelper.optionsItemSelected(item);
   }
 }


