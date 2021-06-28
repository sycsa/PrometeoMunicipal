/*   1:    */ package com.zebra.android.zebrautilitiesmza.screens.chooseprinter;

import com.zebra.android.zebrautilitiesmza.util.NetworkHelper;

/*   3:    */ import android.app.Activity;
/*   4:    */ import android.app.AlertDialog;
/*   5:    */ import android.app.AlertDialog.Builder;
/*   6:    */ import android.app.ProgressDialog;
/*   7:    */ import android.content.DialogInterface;
/*   8:    */ import android.content.DialogInterface.OnClickListener;
/*   9:    */ import android.content.Intent;
/*  10:    */ import android.net.wifi.WifiManager;

			  public class RadioStateManager
			  {
				public static void manageState(final ChoosePrinterController controller)
				{
				  boolean isBTEnabled = NetworkHelper.isBluetoothRadioEnabled();
				  WifiManager wifiManager = (WifiManager)controller.getSystemService("wifi");
				  boolean isWifiEnabled = wifiManager.isWifiEnabled();

				   if ((isBTEnabled) && (!isWifiEnabled))
				   {
					   controller.sequenceDiscoveryOperations(DiscoveryState.BT_STARTED);
					/*   
					AlertDialog.Builder builder = new AlertDialog.Builder(controller);

					builder.setMessage("Desea habilitar el servicio de Wi-Fi?");//("Do you want to turn Wi-Fi on?");
					builder.setTitle("Wi-Fi esta deshabilitado.");
					builder.setCancelable(false);
					builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int which)
							{
								controller.sequenceDiscoveryOperations(DiscoveryState.BT_STARTED);// this.val$controller.sequenceDiscoveryOperations(DiscoveryState.BT_STARTED);
							}
						});
					//builder.setPositiveButton("Turn on Wi-Fi", new DialogInterface.OnClickListener()
					builder.setPositiveButton("Iniciar el servicio de Wi-Fi", new DialogInterface.OnClickListener()
					{
							public void onClick(DialogInterface dialog, int which)
							{
								RadioStateManager.turnOnWifiRadio(controller);
							}
					}
					);
					AlertDialog  alert = builder.create();
					alert.show();*/
      			  }
				  else if ((!isBTEnabled))// && (isWifiEnabled))
				  {
						Builder builder = new Builder(controller);
						builder.setMessage("Se habilitara el servicio de Bluetooth");//("Do you want to turn Bluetooth on?");
						builder.setTitle("Bluetooth esta deshabilitado.");
						builder.setCancelable(false);
	/*					builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int which)
							{
								//no hacer nada ya que wifi no vamos a tener NO SE ENCONTRARA NADA
								//controller.sequenceDiscoveryOperations(DiscoveryState.NETWORK_STARTED);
								//this.val$controller.sequenceDiscoveryOperations(DiscoveryState.NETWORK_STARTED);
							}
						});*/
						//builder.setPositiveButton("Turn on Bluetooth", new DialogInterface.OnClickListener()
						builder.setPositiveButton("Iniciar el servicio de Bluetooth", new OnClickListener()
							{
								public void onClick(DialogInterface dialog, int which)
								{
									RadioStateManager.enableBluetoothRadio(controller);
									// RadioStateManager.enableBluetoothRadio(this.val$controller);
								}
							});
						
						AlertDialog alert = builder.create();
						alert.show();
					}
					/*else if ((!isBTEnabled) && (!isWifiEnabled))
					{
					 AlertDialog.Builder builder = new AlertDialog.Builder(controller);

					  //builder.setTitle("Bluetooth and Wi-Fi are disabled.");
					  builder.setTitle("Bluetooth y Wi-Fi estan deshabilitados.");
					  //builder.setItems(new String[] { "Turn on Bluetooth only", "Turn on Wi-Fi only", "Turn both on", "Leave both off" }, new DialogInterface.OnClickListener()
					  builder.setItems(new String[] { "Inicia solo Bluetooth ", "Iniciar solo Wi-Fi only", "Iniciar Ambos", "Dejarlos deshabilitados" }, new DialogInterface.OnClickListener()
					  {
						  public void onClick(DialogInterface dialog, int which)
						  {
							  switch (which)
							  {
							  case 0: 
								RadioStateManager.enableBluetoothRadio(controller);
								return;
							  case 1: 
								RadioStateManager.turnOnWifiRadio(controller);
							  case 2: 
								  //final ProgressDialog pd = ProgressDialog.show(controller, null, "Enabling Bluetooth...", true, false);
								  final ProgressDialog pd = ProgressDialog.show(controller, null, "Habilitando Bluetooth...", true, false);
				                  (new Thread(new Runnable() {
				                      public void run() 
				                      {
				                         NetworkHelper.turnOnBluetoothRadio();
				                         controller.runOnUiThread(new Runnable() 
					                         {
					                            public void run() 
					                            {
					                               pd.dismiss();
					                               RadioStateManager.turnOnWifiRadio(controller);
					                            }
					                         });
				                      }
				                   })).start();
				                   
								return;
							  default: 
									controller.sequenceDiscoveryOperations(DiscoveryState.TEAR_DOWN_DISCO);
									controller.finish(); 
							  }
						 }
					  });
					  AlertDialog alert = builder.create();
					  alert.show();
					}*/
					else 
					{
						controller.sequenceDiscoveryOperations(DiscoveryState.BT_STARTED);   
				    }
			}
   
			private static void enableBluetoothRadio(final ChoosePrinterController controller)
			{
				 //final ProgressDialog pd = ProgressDialog.show(controller, null, "Enabling Bluetooth...", true, false);
				final ProgressDialog pd = ProgressDialog.show(controller, null, "Habilitando Bluetooth...", true, false);
				 (new Thread(new Runnable()
				 {
					 public void run()
					 {
						 NetworkHelper.turnOnBluetoothRadio();
						 controller.runOnUiThread(new Runnable()
						 {
						  public void run()
						  {
							  controller.sequenceDiscoveryOperations(DiscoveryState.BT_STARTED);
							  pd.dismiss();
						  }
						 });
				 	}
				 })).start();
			}
   
			private static void turnOnWifiRadio(Activity activity)
			{
			     Intent enableBtIntent = new Intent("android.net.wifi.PICK_WIFI_NETWORK");
			     activity.startActivityForResult(enableBtIntent, 42);
			}
}
