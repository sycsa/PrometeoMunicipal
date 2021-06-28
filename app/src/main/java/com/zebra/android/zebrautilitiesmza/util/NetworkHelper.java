 package com.zebra.android.zebrautilitiesmza.util;
 
 import android.bluetooth.BluetoothAdapter;
 import android.os.Build;
 import com.zebra.sdk.printer.discovery.DiscoveredPrinter;
 import com.zebra.sdk.printer.discovery.DiscoveredPrinterBluetooth;
 import com.zebra.sdk.util.internal.Sleeper;
 
 public class NetworkHelper
 {
   public static boolean blueToothAndRadioIsOff()
   {
     DiscoveredPrinter currentlySelectedPrinter = ZebraUtilitiesOptions.getSelectedPrinter();
     boolean bluetoothPrinterSelected = currentlySelectedPrinter instanceof DiscoveredPrinterBluetooth;
     return (bluetoothPrinterSelected) && (!isBluetoothRadioEnabled());
   }
   
   public static boolean isBluetoothRadioEnabled()
   {
     BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
     if (btAdapter == null) {
       return false;
     }
     return btAdapter.isEnabled();
   }
   
   public static boolean isRunningInEmulator()
   {
     return (Build.PRODUCT.equalsIgnoreCase("sdk")) || (Build.PRODUCT.equalsIgnoreCase("google_sdk"));
   }
   
   public static void turnOnBluetoothRadio()
   {
     BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
     if (btAdapter == null)
     {
    	 return;
     }
       btAdapter.enable();
     int  timeoutCounter = 30000;
     
     while(!NetworkHelper.isBluetoothRadioEnabled())
     {
       if ((timeoutCounter < 0)) 
       {
         return;
       }
       
       Sleeper.sleep(500L);
	   timeoutCounter -= 500;
     }
   }
   
   public static void waitForBluetoothDiscoveryToFinish()
   {
     BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
     
     if (btAdapter == null) 
     {
    	 return;	
     }

     int timeoutCounter = 30000;
     while (btAdapter.isDiscovering())
     {
       if ((timeoutCounter < 0)) 
       {
    	   return;
       }
       
       Sleeper.sleep(500L);
	   timeoutCounter -= 500;
     }
   }
 }
