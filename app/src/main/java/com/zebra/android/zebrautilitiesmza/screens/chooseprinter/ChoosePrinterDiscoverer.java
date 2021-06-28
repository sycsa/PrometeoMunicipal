 package com.zebra.android.zebrautilitiesmza.screens.chooseprinter;
 
 import com.zebra.sdk.comm.ConnectionException;
 import com.zebra.sdk.printer.discovery.DiscoveredPrinter;
 import com.zebra.sdk.printer.discovery.DiscoveryException;
 import com.zebra.sdk.printer.discovery.DiscoveryHandler;
 import java.util.Observable;
 
 public abstract class ChoosePrinterDiscoverer
 extends Observable
 implements DiscoveryHandler
 {
   public void discoveryError(String message)
   {
     setChanged();
	 notifyObservers(message);
   }
   
   public void foundPrinter(DiscoveredPrinter printer)
   {
     setChanged();
	 notifyObservers(printer);
   }
   
   public abstract void startDiscovery()
     throws DiscoveryException, ConnectionException, InterruptedException;
 }
