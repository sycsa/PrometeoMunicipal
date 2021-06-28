 package com.zebra.android.zebrautilitiesmza.screens.chooseprinter;
 
 import com.zebra.android.zebrautilitiesmza.ZebraUtilitiesApplication;
import com.zebra.sdk.comm.ConnectionException;
 import com.zebra.sdk.printer.discovery.BluetoothDiscoverer;
 import com.zebra.sdk.printer.discovery.DiscoveryException;
 
 public class ChoosePrinterBluetoothDiscoverer
   extends ChoosePrinterDiscoverer
 {
   public void discoveryError(String message)
   {
     super.discoveryError(message);
     setChanged();
     notifyObservers(Integer.valueOf(1));
   }
   
   public void startDiscovery()
     throws DiscoveryException, ConnectionException, InterruptedException
   {
     BluetoothDiscoverer.findPrinters(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT, this);
   }
   
   public void discoveryFinished()
   {
     setChanged();
     notifyObservers(Integer.valueOf(1));
   }
 }