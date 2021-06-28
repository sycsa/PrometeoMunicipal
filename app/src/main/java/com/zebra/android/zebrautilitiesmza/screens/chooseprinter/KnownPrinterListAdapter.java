 package com.zebra.android.zebrautilitiesmza.screens.chooseprinter;
 
 import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import ar.gov.mendoza.PrometeoMuni.R;
 
 class KnownPrinterListAdapter
   extends BasePrinterListAdapter
 {
   public KnownPrinterListAdapter(Context context, ChoosePrinterModel model)
   {
	    this.mInflater = LayoutInflater.from(context);
	    this.discoveredPinters = model.getKnownPrintersList();
	    this.bluetoothIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.bt);//2130837511
	    this.networkIcon = BitmapFactory.decodeResource(context.getResources(),R.drawable.network );//2130837534
   }
 }



