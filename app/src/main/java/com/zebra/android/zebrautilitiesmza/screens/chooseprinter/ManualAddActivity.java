 package com.zebra.android.zebrautilitiesmza.screens.chooseprinter;
 
 import android.app.Activity;
 import android.os.Bundle;
 import android.view.View;
 import android.view.View.OnClickListener;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.LinearLayout;
 import android.widget.RadioButton;
 import android.widget.RadioGroup;
 import android.widget.RadioGroup.OnCheckedChangeListener;

import com.zebra.android.zebrautilitiesmza.util.UIHelper;
import com.zebra.android.zebrautilitiesmza.util.ZebraUtilitiesOptions;
import com.zebra.sdk.printer.discovery.DiscoveredPrinter;
 import com.zebra.sdk.printer.discovery.DiscoveredPrinterBluetooth;
 import com.zebra.sdk.printer.discovery.DiscoveredPrinterNetwork;
 
 public class ManualAddActivity
   extends Activity
 {
   protected void onCreate(Bundle savedInstanceState)
   {
     super.onCreate(savedInstanceState);
     setTitle("Manually Add Printer");
     setContentView(2130903040);
     
     final EditText ipAddressText = findViewById(2131296265);
     ipAddressText.setText(ZebraUtilitiesOptions.getManualAddIpDns());
     
     final EditText portText = findViewById(2131296267);
     portText.setText(ZebraUtilitiesOptions.getManualAddPort());
     
     final EditText macAddressText = findViewById(2131296262);
     macAddressText.setText(ZebraUtilitiesOptions.getManualAddBtAddress());
     
     final LinearLayout ipManualAddLayout = findViewById(2131296263);
     final LinearLayout btManualAddLayout = findViewById(2131296260);
     final RadioGroup radioGroup = findViewById(2131296257);
     
     radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
     {
       public void onCheckedChanged(RadioGroup group, int checkedId)
       {
         if (checkedId == 2131296258)
         {
           btManualAddLayout.setVisibility(0);
           ipManualAddLayout.setVisibility(8);
         }
         else
         {
           btManualAddLayout.setVisibility(8);
           ipManualAddLayout.setVisibility(0);
         }
       }
     });
     
     int i=0;
     if (ZebraUtilitiesOptions.getIsBtManualAddSelected()) 
     {
       i = 2131296258;
     }
     else
     {
       i = 2131296259;
     }
       radioGroup.check(i);
	   Button addButton = findViewById(2131296268);
       addButton.setOnClickListener(new OnClickListener()
       {
         public void onClick(View v)
         {
           String ipOrDns = ipAddressText.getText().toString();
           String portString = portText.getText().toString();
           String bluetoothAddress = macAddressText.getText().toString();
           try
           {
             DiscoveredPrinter printer = ManualAddActivity.this.getPrinterToAdd(radioGroup, ipOrDns, portString, bluetoothAddress);
             ChoosePrinterModel.getInstance().addToKnownPrinters(printer);
             ManualAddActivity.this.setResult(1);
             ManualAddActivity.this.saveState();
             ManualAddActivity.this.finish(); 
           }
           catch (PrinterConnectionIllegalArgumentException e)
           {
             UIHelper.showAlertOnGuiThread(ManualAddActivity.this, e.getMessage(), "Error: Illegal Arguments", null);
           }
         }
       }); 

   }
   
   private DiscoveredPrinter getPrinterToAdd(RadioGroup radioGroup, String ipOrDns, String portString, String bluetoothAddress)
     throws PrinterConnectionIllegalArgumentException
   {
     if (radioGroup.getCheckedRadioButtonId() == 2131296258)
     {
       if (bluetoothAddress.length() > 0)
       {
         DiscoveredPrinterBluetooth tmp = new DiscoveredPrinterBluetooth(bluetoothAddress, bluetoothAddress);
         DiscoveredPrinter localDiscoveredPrinter1 = ChoosePrinterModel.getInstance().checkDiscoListForManuallyAddedBluetoothPrinter(tmp);
         return localDiscoveredPrinter1;
       }
       else
       {
       throw new PrinterConnectionIllegalArgumentException("Friendly name or MAC address cannot be blank");
       }
     }
     if (ipOrDns.length() == 0) {
       throw new PrinterConnectionIllegalArgumentException("IP address or DNS name cannot be blank");
     }
     
     
       try
       {
         int portAsInt = Integer.parseInt(portString);
         if (portAsInt < 1) 
         {
        	 throw new PrinterConnectionIllegalArgumentException("Port number must be an integer between 1-65535");
         }
         if(portAsInt <= 65535)
         {
        	 DiscoveredPrinter printer = new DiscoveredPrinterNetwork(ipOrDns, portAsInt);	 
        	 return printer;
         }
         else
         {
         throw new PrinterConnectionIllegalArgumentException("Port number must be an integer between 1-65535");
         }
         	
       }
       catch (NumberFormatException e)
       {
         throw new PrinterConnectionIllegalArgumentException("Port number must be an integer between 1-65535");
       }
     
     
   }
   
   private void saveState()
   {
     EditText ipAddressText = findViewById(2131296265);
     ZebraUtilitiesOptions.setManualAddIpDns(ipAddressText.getText().toString());
     
     EditText portText = findViewById(2131296267);
     ZebraUtilitiesOptions.setManualAddPort(portText.getText().toString());

     EditText macAddressText = findViewById(2131296262);
     ZebraUtilitiesOptions.setManualAddBtAddress(macAddressText.getText().toString());
     RadioButton bluetoothRadio = findViewById(2131296258);
     ZebraUtilitiesOptions.setManualIsBtSelected(Boolean.valueOf(bluetoothRadio.isChecked()));
   }
 }