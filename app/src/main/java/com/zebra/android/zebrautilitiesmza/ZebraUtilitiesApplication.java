 package com.zebra.android.zebrautilitiesmza;
	 
 //import com.zebra.android.zebrautilitiesmza.R;
 import android.app.Activity;
 import android.app.ProgressDialog;
 import android.content.Context;
 import android.content.Intent;
 
 //import android.hardware.usb.UsbManager;
 import android.nfc.NdefMessage;
 import android.nfc.NdefRecord;
 import android.os.AsyncTask;
 import android.os.Build.VERSION;
 import android.os.Bundle;
 import android.os.Parcelable;
 import android.preference.PreferenceManager;
 import android.view.View;
 import android.view.View.OnClickListener;
 import android.widget.ImageButton;
 import android.widget.Toast;

import com.zebra.android.zebrautilitiesmza.util.ZebraUtilitiesOptions;
import com.zebra.sdk.comm.UsbConnection;
import com.zebra.sdk.printer.discovery.DiscoveredPrinter;
import com.zebra.sdk.printer.discovery.DiscoveryException;
import com.zebra.sdk.printer.discovery.DiscoveryHandler;
import com.zebra.sdk.printer.discovery.UrlPrinterDiscoverer;


public class ZebraUtilitiesApplication
//extends ZebraActivityBase
{
	public static Activity ZEBRA_UTIL_APP_ACTIVITY;
	public static Context ZEBRA_UTIL_APP_CONTEXT;
	
	public ZebraUtilitiesApplication()
	{
	//  super("Zebra Utilities",R.layout.main);// 2130903049);
	}
  
//    public void onCreate(Bundle savedInstanceState)
//    {
//      ZEBRA_UTIL_APP_ACTIVITY = this;
// 	  ZEBRA_UTIL_APP_CONTEXT = getApplicationContext();
// 	  PreferenceManager.setDefaultValues(this, R.xml.preferences,false);//2130968577, false);
//	  super.onCreate(savedInstanceState);
//	  createHomeScreenClickListeners();
//    }
//	
//    @Override 
//    protected void onResume()
//    {
//        tryToProcessNfcTagData();
// 		tryToProcessUsbConnect();
// 		super.onResume();
//    }
        
//    void tryToProcessNfcTagData()
//    {
//      if (android.os.Build.VERSION.SDK_INT >= 14)
//      {
//        Parcelable[] scannedTags = getIntent().getParcelableArrayExtra("android.nfc.extra.NDEF_MESSAGES");
//        if ((scannedTags != null) && (scannedTags.length > 0))
//        {
//          NdefMessage msg = (NdefMessage)scannedTags[0];
//          byte[] payload = msg.getRecords()[0].getPayload();
//          String scannedData = new String(payload);
//          new ProcessNfcTask(this, scannedData, this.activityHelper).execute(new Void[] { null });
// 		  getIntent().removeExtra("android.nfc.extra.NDEF_MESSAGES");
//        }
//      }
//
//    }
    
    private void tryToProcessUsbConnect()
    {
    	/*
      if (android.os.Build.VERSION.SDK_INT >= 12)
      {
        Parcelable device = getIntent().getParcelableExtra("device");
        if ((device != null) && ((device instanceof android.hardware.usb.UsbDevice)))
        {//import android.hardware.usb.UsbDevice;
          UsbManager localUsbManager = (UsbManager)getSystemService("usb");
          android.hardware.usb.UsbDevice localUsbDevice = (android.hardware.usb.UsbDevice)device;
 		  com.zebra.android.zebrautilitiesmza.util.ZebraUtilitiesOptions.usbConnection = new UsbConnection(localUsbManager, localUsbDevice);
 		  this.activityHelper.updateTitleBar();
 		  getIntent().removeExtra("device");
        }
      }*/
    }
    /*
    @Override
    protected void onNewIntent(Intent intent)
    {
      super.onNewIntent(intent);
 	  tryToProcessNfcTagData();
    }
    
    private void createHomeScreenClickListeners()
    {
      int levelCode = android.os.Build.VERSION.SDK_INT;
      ImageButton pdfFilesButton = (ImageButton)findViewById(2131296296);
      pdfFilesButton.setOnClickListener(new View.OnClickListener()
		      {
		        public void onClick(View v)
		        {
		          Intent i = new Intent(ZebraUtilitiesApplication.this, FilesList.class);
		          ZebraUtilitiesApplication.this.startActivity(i);
		        }
		      }
       );
      
      
	 ImageButton filesListButton = (ImageButton)findViewById(R.id.filesButton);//2131296297); 
     filesListButton.setOnClickListener(new View.OnClickListener()
		    {
		      public void onClick(View v)
		      {
		        Intent i = new Intent(ZebraUtilitiesApplication.this, FilesList.class);
		        ZebraUtilitiesApplication.this.startActivity(i);
		      }
     });
	    
 	 ImageButton photoIdButton = (ImageButton)findViewById(2131296299);
     photoIdButton.setOnClickListener(new View.OnClickListener()
 			  {
 			    public void onClick(View v)
 			    {
 			      Intent i = new Intent(ZebraUtilitiesApplication.this, PhotoId.class);
 			      ZebraUtilitiesApplication.this.startActivity(i);
 			    }
 			  }
     );
 			  
 	  ImageButton assetTagButton = (ImageButton)findViewById(2131296300);
	  assetTagButton.setOnClickListener(new View.OnClickListener()
 			   {
 			     public void onClick(View v)
 			     {
 			       Intent i = new Intent(ZebraUtilitiesApplication.this, AssetTag.class);
 			       ZebraUtilitiesApplication.this.startActivity(i);
 			     }
 			   }
	  );
 	  
	  ImageButton trafficTicketButton = (ImageButton)findViewById(2131296301); 
	  trafficTicketButton.setOnClickListener(new View.OnClickListener()
 			   {
 			     public void onClick(View v)
 			     {
 			       Intent i = new Intent(ZebraUtilitiesApplication.this, TrafficTicket.class);
 			       ZebraUtilitiesApplication.this.startActivity(i);
 			     }
 			   });

 	   ImageButton  webFilesButton = (ImageButton)findViewById(2131296302);
       webFilesButton.setOnClickListener(new View.OnClickListener()
		      {
		        public void onClick(View v)
		        {
		          Intent i = new Intent(ZebraUtilitiesApplication.this, WebFiles.class);
		          ZebraUtilitiesApplication.this.startActivity(i);
		        }
		      }
       );
    }
    */
    //clase interna
    /*
    class ProcessNfcTask extends AsyncTask<Void, Void, Void>
    {
      private ZebraActivityHelper activityHelper;
      private Activity hostActivity;
      private String nfcData;
      
      public ProcessNfcTask(Activity hostActivity, String nfcData, ZebraActivityHelper activityHelper)
      {
    	super();
        this.hostActivity = hostActivity;
        this.nfcData = nfcData;
        this.activityHelper = activityHelper;
      }
      
//      public void execute()
//      {
//        new AsyncTask<Void,Void,Void>()
//        {
          private volatile boolean printerFound = false;
          private volatile boolean discoveryComplete = false;
          ProgressDialog dialog;
          
          @Override
          protected void onPreExecute()
          {
            super.onPreExecute();
            this.dialog = new ProgressDialog(ZebraUtilitiesApplication.ProcessNfcTask.this.hostActivity, 0);
            this.dialog.setMessage("Processing NFC Scan");
            this.dialog.show();
          }
          
          @Override
          protected Void doInBackground(Void... params)
          {
            try
            {
              UrlPrinterDiscoverer.findPrinters(ZebraUtilitiesApplication.ProcessNfcTask.this.nfcData, new DiscoveryHandler()
              {
            	  @Override
	        	  public void discoveryError(String message)
	              {
	                //ZebraUtilitiesApplication.ProcessNfcTask.access$302(ZebraUtilitiesApplication.ProcessNfcTask.this, true);
            		ZebraUtilitiesApplication.ProcessNfcTask.this.discoveryComplete = true;  	
	              }
            	  public void discoveryFinished()
                  {
                   // ZebraUtilitiesApplication.ProcessNfcTask.access$302(ZebraUtilitiesApplication.ProcessNfcTask.this, true);
            		  ZebraUtilitiesApplication.ProcessNfcTask.this.discoveryComplete = true;  
                  }
                public void foundPrinter(DiscoveredPrinter printer)
                {
 					if (!ZebraUtilitiesApplication.ProcessNfcTask.this.printerFound)
 					{
 						ZebraUtilitiesApplication.ProcessNfcTask.this.printerFound = true;
 						ChoosePrinterModel.getInstance().addToKnownPrinters(printer);
 					}
                //  if (!ZebraUtilitiesApplication.ProcessNfcTask.this.printerFound)
                //  {
                //    ZebraUtilitiesApplication.ProcessNfcTask.access$202(ZebraUtilitiesApplication.ProcessNfcTask.this, true);
 				//	  ChoosePrinterModel.getInstance().addToKnownPrinters(printer);
                //  }
 					
                }
                
                
                
               
              });
              while ((!this.discoveryComplete) && (!this.printerFound)) 
              {
	                try
	                {
	                  Thread.sleep(100L);
	                }
	                catch (InterruptedException localInterruptedException) {}
              }
            }
            catch (DiscoveryException e)
            {
                e.printStackTrace();
                this.discoveryComplete = true;
            }
            return null;
          }
          
          @Override
          protected void onPostExecute(Void result)
          {
            super.onPostExecute(result);
            this.dialog.dismiss();
            if (!this.printerFound)
            {
              Toast.makeText(ZebraUtilitiesApplication.ProcessNfcTask.this.hostActivity, "Unable to find specified NFC printer", 0).show();
              return;
            }
            ZebraUtilitiesApplication.ProcessNfcTask.this.activityHelper.updateTitleBar();
          }

		     
//		 }.execute(new Void[] { null }); 
//
//      }
    //fin clase interna
     
     */

}


