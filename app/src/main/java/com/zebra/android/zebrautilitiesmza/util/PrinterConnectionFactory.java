 package com.zebra.android.zebrautilitiesmza.util;
 
 import android.os.Looper;
 import com.zebra.sdk.comm.Connection;
 import com.zebra.sdk.comm.ConnectionException;
 import com.zebra.sdk.comm.TcpConnection;
 import com.zebra.sdk.printer.discovery.DiscoveredPrinter;
 import com.zebra.sdk.printer.discovery.DiscoveredPrinterBluetooth;
 import com.zebra.sdk.printer.discovery.DiscoveredPrinterNetwork;
 import java.util.Map;
 
public class PrinterConnectionFactory
{
	   private static PrinterConnectionFactory printerConnectionFactoryInstance;
	   
	   protected Connection createPrinterConnectionInternal(DiscoveredPrinter currentlySelectedPrinter)
	     throws ConnectionException
	   {
		     if (currentlySelectedPrinter == null) 
		     {
			       throw new ConnectionException("No printer selected. Please select a printer from the \"Choose Printer\" menu.");
			 }
		     else
		     {
				int portToUse = 0;
  		        Connection printerConnection=null;
  		        
//			     if ((currentlySelectedPrinter instanceof DiscoveredPrinterNetwork))
//			     {
//			       DiscoveredPrinterNetwork networkPrinter = (DiscoveredPrinterNetwork)currentlySelectedPrinter;
//			       
//			       String defaultPort = ZebraUtilitiesOptions.getDefaultPrinterPort();
//			       if (defaultPort != null && !defaultPort.equals(""))
//			       {
//			    	  portToUse =  Integer.parseInt(defaultPort); 
//			       }
//			       else
//			       {
//			    	 portToUse = 9100;
//			         try
//			         {
//			           int i = Integer.parseInt((String)networkPrinter.getDiscoveryDataMap().get("PORT"));
//							  portToUse = i;
//			         }
//			         catch (NumberFormatException e)
//			         {
//			             portToUse = 9100;
//			         }
//			      }
//			      
//			      printerConnection = new TcpConnection(networkPrinter.address, portToUse);
//			     }
//			     else 
			     if ((currentlySelectedPrinter instanceof DiscoveredPrinterBluetooth))
			     {
			    	 DiscoveredPrinterBluetooth bluetoothPrinter = (DiscoveredPrinterBluetooth)currentlySelectedPrinter;
			    	 printerConnection = (new PrinterConnectionFactoryBluetooth()).getConnection(bluetoothPrinter);
			     }
//			     else 
//			     {
//			    	 if(!(currentlySelectedPrinter instanceof DiscoveredPrinterUsb)) 
//			    	 {
//			               throw new ConnectionException("Invalid printer type");
//			         }
//			    	 printerConnection = ZebraUtilitiesOptions.usbConnection;
//			     }	 
			    	 
			     
			     boolean reuse = false;
			     if (Looper.myLooper() == null) 
			     {
			         Looper.prepare();
			     }
			     else
			     {
			    	 reuse = true;
			     }
			     
			     if(printerConnection!=null) //added by me
			    	 printerConnection.open();
			     
			     
			     if (!reuse) 
			     {
			        Looper.myLooper().quit();
			     }
			       
			  return printerConnection;
			     
	     }//end else
	   }
	   
	   public static Connection createPrinterConnection(DiscoveredPrinter currentlySelectedPrinter)
	   throws ConnectionException
	   {
	     if (printerConnectionFactoryInstance == null) {
	       printerConnectionFactoryInstance = new PrinterConnectionFactory();
	     }
	     return printerConnectionFactoryInstance.createPrinterConnectionInternal(currentlySelectedPrinter);
	   }
}