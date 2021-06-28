 package com.zebra.android.zebrautilitiesmza.screens.chooseprinter;
 
 import android.content.Context;
 import android.graphics.BitmapFactory;
 import android.view.LayoutInflater;
 
 //import com.zebra.android.zebrautilitiesmza.R;
 import ar.gov.mendoza.PrometeoMuni.R;
 
 class DiscoveredPrinterListAdapter extends BasePrinterListAdapter
{
  public DiscoveredPrinterListAdapter(Context context, ChoosePrinterModel model)
  {
	this.mInflater = LayoutInflater.from(context);
	this.discoveredPinters = model.getPrinterDisplayList();
	this.bluetoothIcon = BitmapFactory.decodeResource(context.getResources(),R.drawable.bt); //2130837511);
	this.networkIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.network);//2130837534);
  }
}