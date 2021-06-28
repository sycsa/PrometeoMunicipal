 package com.zebra.android.zebrautilitiesmza.util;
 
 import com.zebra.android.zebrautilitiesmza.ZebraUtilitiesApplication;
import com.zebra.sdk.comm.BluetoothConnection;
 import com.zebra.sdk.comm.Connection;
 import com.zebra.sdk.comm.ConnectionException;
 import com.zebra.sdk.printer.discovery.BluetoothDiscoverer;
 import com.zebra.sdk.printer.discovery.DiscoveredPrinter;
 import com.zebra.sdk.printer.discovery.DiscoveredPrinterBluetooth;
 import com.zebra.sdk.printer.discovery.DiscoveryHandler;
 import com.zebra.sdk.util.internal.Sleeper;

 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;

public class PrinterConnectionFactoryBluetooth
implements DiscoveryHandler
{
	   private String macAddressToConnectTo = null;
	   private String friendlyNameOrMac;
	   private boolean discoveringPrinters = false;

	   
	   public Connection getConnection(DiscoveredPrinterBluetooth bluetoothPrinter)
	     throws ConnectionException
	   {
	     this.friendlyNameOrMac = bluetoothPrinter.address;
	     
	     if (isValidMacAddress(this.friendlyNameOrMac))
	     {
	    	 final BluetoothConnection printerConnection = new BluetoothConnection(this.friendlyNameOrMac);
	         return printerConnection;
	     }
	     NetworkHelper.waitForBluetoothDiscoveryToFinish();
	     BluetoothDiscoverer.findPrinters(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT, this);
	     this.discoveringPrinters = true;
	     while (this.discoveringPrinters) 
	     {
            Sleeper.sleep(100L);
         }
	     if (this.macAddressToConnectTo != null) 
	     {
	            this.replaceKnownPrinterWithDiscoveredPrinter();
	            final BluetoothConnection printerConnection = new BluetoothConnection(this.macAddressToConnectTo);
	            return printerConnection;
	     }

	     throw new ConnectionException("Printer with friendly name " + this.friendlyNameOrMac + " not found.");
	   }
	   
	   private void replaceKnownPrinterWithDiscoveredPrinter()
	   {
	     DiscoveredPrinterBluetooth modifiedPrinter = new DiscoveredPrinterBluetooth(this.macAddressToConnectTo, this.friendlyNameOrMac);
	     ZebraUtilitiesOptions.setSelectedPrinter(modifiedPrinter);
	     List<DiscoveredPrinter> currentKnownPrinters = ZebraUtilitiesOptions.getKnownPrinters();
	     List<DiscoveredPrinter> newListOfKnownPrinters = new ArrayList<DiscoveredPrinter>();
	     
	     for (final DiscoveredPrinter aPrinter : currentKnownPrinters) 
	     {
	            if (aPrinter.address.equals(this.friendlyNameOrMac)) 
	            {
	                newListOfKnownPrinters.add(modifiedPrinter);
	            }
	            else 
	            {
	                newListOfKnownPrinters.add(aPrinter);
	            }
	     }
	     ZebraUtilitiesOptions.setKnownPrinters(newListOfKnownPrinters);
	   }
   
			public static boolean isValidMacAddress(String macAddress)
			{
			  try
			  {
			    String macWithoutColons = macAddress.replaceAll(":", "");
			    int i = macWithoutColons.length();
			    boolean b = false;
			    if (i == 12) 
			    {
			      Long.parseLong(macWithoutColons, 16);
			      b=true;
			    }
			    return b;
			  }
			  catch (NumberFormatException e) 
			  {
				return false;				  
			  }

			}

		    @Override
			public void discoveryError(String message)
			{
			  this.discoveringPrinters = false;
			}
		    @Override
			public void discoveryFinished()
			{
			  this.discoveringPrinters = false;
			}
		    @Override
			public void foundPrinter(DiscoveredPrinter printer)
			{
			  DiscoveredPrinterBluetooth discoveredPrinterBluetooth = (DiscoveredPrinterBluetooth)printer;
			  if ((discoveredPrinterBluetooth.friendlyName != null) && (discoveredPrinterBluetooth.friendlyName.toLowerCase().equals(this.friendlyNameOrMac.toLowerCase())))
			  {
			    this.macAddressToConnectTo = printer.address;
			    this.discoveringPrinters = false;
			  }
			}
}