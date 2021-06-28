 package com.zebra.android.zebrautilitiesmza.statusdialog;
 
 import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import ar.gov.mendoza.PrometeoMuni.R;

import com.zebra.android.zebrautilitiesmza.ZebraActivityBase;
import com.zebra.android.zebrautilitiesmza.util.UIHelper;
import com.zebra.android.zebrautilitiesmza.util.ZebraPrinterTask;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.device.ZebraIllegalArgumentException;
import com.zebra.sdk.printer.PrinterStatus;
import com.zebra.sdk.printer.PrinterStatusMessages;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;
import com.zebra.sdk.printer.ZebraPrinterLinkOs;

 import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
 
public class StatusDialog
extends ZebraActivityBase
 {
   private StatusDialogListAdapter adapter;
   private RelativeLayout statusCell;
   private ArrayList<String> statusMessage = new ArrayList<String>();
   private Button statusRefreshButton;
   
   public StatusDialog()
   {
     super("Printer Status", R.layout.status_dialog);//2130903063
   }
   

   
   public void onCreate(Bundle savedInstanceState)
   {
     super.onCreate(savedInstanceState);
     ListView listView = findViewById(R.id.statusMessageList);//2131296349
 
     this.adapter = new StatusDialogListAdapter(this, this.statusMessage);
     listView.setAdapter(this.adapter);
     //listView.setSelector(17170445);
     this.statusRefreshButton = findViewById(R.id.statusRefreshButton);//2131296346
     this.statusCell = findViewById(R.id.statusRelativeLayout);//2131296343
     this.statusCell.setOnClickListener(new OnClickListener()
		     {
		       public void onClick(View v)
		       {
		         TextView printerStatusText = StatusDialog.this.findViewById(R.id.statusOfPrinter);//2131296345
		         printerStatusText.setVisibility(4);
		         StatusDialog.this.updateStatus();
		       }
		     });
     
     updateStatus();
   }
   
   public boolean onCreateOptionsMenu(Menu menu)
   {
     return true;
   }
   
   private void updateStatus()
   {
     this.statusCell.setClickable(false);
     LinearLayout statusMessagesLayout = findViewById(R.id.statusMessageLayout);//2131296347
     statusMessagesLayout.setVisibility(4);
     this.statusMessage.clear();
     this.adapter.notifyDataSetChanged();
     this.statusRefreshButton.setVisibility(4);
     TextView statusLabelText = findViewById(R.id.statusLabel);//2131296344
     statusLabelText.setText("Updating Status...");
     
     new ZebraPrinterTask<Object,Integer,String[]>(this)
     {
       public String[] doWork(final Object[] params)
       throws ConnectionException, ZebraIllegalArgumentException
       {
    	   PrinterStatus printerStatus;
    	   
           try 
           {
               ZebraPrinter printer = ZebraPrinterFactory.getInstance(this.printerConnection);
               ZebraPrinterLinkOs linkOsPrinter = ZebraPrinterFactory.createLinkOsPrinter(printer);
               if (linkOsPrinter != null) 
               {
                   printerStatus =  linkOsPrinter.getCurrentStatus();
               }
               else
            	   printerStatus = printer.getCurrentStatus();
           }
           catch (ZebraPrinterLanguageUnknownException e) 
           {
               e.printStackTrace();
               printerStatus = null;
           }
		  
		  String[] printerStatusString={};
		  printerStatusString = new PrinterStatusMessages(printerStatus).getStatusMessage();
		  
		  int n = printerStatusString.length; //i
		  for (int n2=0;n2<n;++n2)
           {
             String string = printerStatusString[n2];
             if (string.equals("HEAD OPEN")) {
             StatusDialog.this.statusMessage.add("The printer head is open");
             }
             else if (string.equals("HEAD TOO HOT")) {
                 StatusDialog.this.statusMessage.add("The printer head is too hot");
             }
             else if (string.equals("PAPER OUT")) {
            	 StatusDialog.this.statusMessage.add("The printer paper is out");  
             }
             else if (string.equals("RIBBON OUT")) {
            	 StatusDialog.this.statusMessage.add("The printer ribbon out");  
             }
             else if (string.equals("RECEIVE BUFFER FULL")) {
                 StatusDialog.this.statusMessage.add("The printer receive buffer is full");
             }
             else if (string.equals("PAUSE")) {
                 StatusDialog.this.statusMessage.add("The printer is paused");
             }
             
           } 

           String[] tmp = new String[StatusDialog.this.statusMessage.size()];
           int i = 0;
           
           while(i < tmp.length)
           {
        	   tmp[i] = StatusDialog.this.statusMessage.get(i);
        	   ++i;
           }		
           return tmp;
        
       }
       
       public void onPostExecute(final String[] printerStatusErrors)
       {
         super.onPostExecute(printerStatusErrors);
         TextView printerStatusText = StatusDialog.this.findViewById(R.id.statusOfPrinter);//2131296345
         if (printerStatusErrors != null)
         {
           TextView numErrorLabel = StatusDialog.this.findViewById(R.id.numberOfErrorsLabel);//2131296348
           
           numErrorLabel.setTextColor(-12303292);
           numErrorLabel.setText("Errors (" + printerStatusErrors.length + ")");
           LinearLayout statusMessagesLayout = StatusDialog.this.findViewById(R.id.statusMessageLayout);//2131296347
           if (printerStatusErrors.length == 0)
           {
             printerStatusText.setText("Ready");
           }
           else
           {	
        	   printerStatusText.setText("Not Ready");
			   statusMessagesLayout.setVisibility(0);
           }   
             TextView statusLabelText = StatusDialog.this.findViewById(R.id.statusLabel);//2131296344
             statusLabelText.setText("Printer Status");
             StatusDialog.this.adapter.notifyDataSetChanged();
           
         }
         else
         {
        	  TextView statusLabelText = StatusDialog.this.findViewById(R.id.statusLabel);//2131296344);
              statusLabelText.setText("Status Unknown");
         }
           printerStatusText.setVisibility(0);
           LinearLayout printStatus = StatusDialog.this.findViewById(R.id.statusLayout);//2131296350
           
           StatusDialog.this.statusRefreshButton.setVisibility(0);
           printStatus.setVisibility(0);
           TextView updateTime = StatusDialog.this.findViewById(R.id.lastUpdateTime);//2131296351
           
           Calendar cal = Calendar.getInstance();
           SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
           updateTime.setText(sdf.format(cal.getTime()));
           StatusDialog.this.statusCell.setClickable(true);
       }
       
       public void handleError(final Exception e)
       {
         UIHelper.showErrorOnGuiThread(StatusDialog.this, e.getLocalizedMessage(), null);
       }
     };
   }
 }
