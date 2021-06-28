 package com.zebra.android.zebrautilitiesmza;
 
 import com.zebra.android.zebrautilitiesmza.screens.chooseprinter.ChoosePrinterController;
import com.zebra.android.zebrautilitiesmza.statusdialog.StatusDialog;
import com.zebra.android.zebrautilitiesmza.util.UIHelper;
import com.zebra.android.zebrautilitiesmza.util.ZebraUtilitiesOptions;

import android.app.Activity;
 import android.content.Intent;
 import android.view.Menu;
 import android.view.MenuInflater;
 import android.view.MenuItem;
 import android.view.Window;
 import android.widget.TextView;
 
 public class ZebraMenuCreator
 {
   public static boolean createOptionsMenu(Activity activity, int menuID, Menu menu)
   {
     MenuInflater inflater = activity.getMenuInflater();
     inflater.inflate(menuID, menu);
     return true;
   }
   
   public static boolean optionsItemSelected(Activity activity, MenuItem item)
   {

     Intent localIntent;
     int getItemId =item.getItemId();
     switch (getItemId)
     {
     case 2131296355: 
         localIntent = new Intent(activity, ChoosePrinterController.class);
         break;
       case 2131296356:
          localIntent= new Intent(activity, StatusDialog.class);
          break;
       /*case 2131296357:
		   boolean printerIsSelected = ZebraUtilitiesOptions.isPrinterSelected().booleanValue();
		   boolean isBluetoothPrinter = ZebraUtilitiesOptions.isBluetoothPrinterSelected();
	       if (!printerIsSelected)
	       {
	         UIHelper.showErrorOnGuiThread(activity, "No printer selected. Please select a printer from the \"Choose Printer\" menu.", null);
	         return true;
	       }
	       if (isBluetoothPrinter)
	       {
	         UIHelper.showErrorOnGuiThread(activity, "Bluetooth printers do not support the Web page feature.", null);
	         return true;
	       }
		  localIntent = new Intent(activity, PrinterWebpage.class);
    	  break;*/
       /*case 2131296358:
           localIntent = new Intent(activity, OptionsScreen.class);
           break;*/
        /*case 2131296359:
           localIntent = new Intent(activity, AboutActivity.class);
           break;*/    	  
        default: 
        	return false;
     }
       activity.startActivity(localIntent);
       return true;
   }
   
   public static void createTitleBar(Activity activity, String title, String printer)
   {
	   /* revisar luego si va a funcionar esto 
     activity.getWindow().setFeatureInt(7, 2130903043);
     TextView titleBarLeftText = (TextView)activity.findViewById(2131296276);
     
     TextView titleBarRightText = (TextView)activity.findViewById(2131296277);
     titleBarLeftText.setTextColor(-1);
     titleBarRightText.setTextColor(-1);
     titleBarLeftText.setText(title);
     titleBarRightText.setText(printer);
     */
   }
 }
