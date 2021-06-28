 package com.zebra.android.zebrautilitiesmza.util;
 
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;

import com.zebra.android.zebrautilitiesmza.ZebraActivityHelper;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.device.ZebraIllegalArgumentException;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;
import com.zebra.sdk.printer.discovery.DiscoveredPrinter;
import com.zebra.sdk.util.internal.Sleeper;

 import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
public abstract class ZebraPrinterTask<T, S, A>
implements ZebraPrinterTaskI<T, S, A>
{
	   private static ExecutorService taskQueue = null;
	   protected ZebraPrinter printer = null;
	   protected Connection printerConnection = null;
	   private AsyncTask<T, S, A> taskToRun = null;
	   
	   static {
	        ZebraPrinterTask.taskQueue = Executors.newSingleThreadExecutor();
	    }

	   
	   public ZebraPrinterTask(final Activity activity, final T... parametros)
	   {
		 super();
	     this.printerConnection = null;
	     this.printer = null;
	     this.taskToRun = null;
	     
	     final DiscoveredPrinter currentlySelectedPrinter = ZebraUtilitiesOptions.getSelectedPrinter();
	 
	     activity.runOnUiThread(new Runnable()
								     { @Override
								       public void run()
								       {
								         if (NetworkHelper.blueToothAndRadioIsOff())
								         {
								           ZebraPrinterTask.this.showPleaseTurnRadioOnDialog(activity, currentlySelectedPrinter, parametros);
								           return;
								         }
								         else
								           ZebraPrinterTask.this.scheduleTask(activity, currentlySelectedPrinter, parametros);
								       }
								     }
	    		 			    );
	   }

	   private void scheduleTask(final Activity activity, final DiscoveredPrinter currentlySelectedPrinter, final T... parametros)
	   {
	     taskQueue.execute(new Runnable()
	     { @Override
	       public void run()
	       {
	         activity.runOnUiThread(new Runnable()
	         {
	           public void run() //1
	           {
	             ZebraPrinterTask.this.taskToRun = new AsyncTask<T,S,A>()
				             { 
	            	 			 
					              protected void onCancelled()
					              {
					                ZebraPrinterTask.this.onPostExecute(null);
					                super.onCancelled();
					              } 
					              
								  protected A doInBackground(final T... parametrosI)
				                  {
				                    return ZebraPrinterTask.this.doInBackground(currentlySelectedPrinter, parametrosI);
				                  }
				                  protected void onPostExecute(final A result)
				                  {
				                    ZebraPrinterTask.this.onPostExecute(result);
				                  }
				                  protected void onPreExecute()
				                  {
				                	  ZebraPrinterTask.this.onPreExecute();
				                  }
				                  protected void onProgressUpdate(final S... values)
				                  {
				                	  ZebraPrinterTask.this.onProgressUpdate(values);
				                  }
				                  
			                };
	             
	          ZebraPrinterTask.this.taskToRun.execute(parametros);
	         } //end run() 1
	         });
	         
	         
	 
	       long currentTime = System.currentTimeMillis();
	       long timeKeeper = currentTime;
	         do
	         {
	           if ((ZebraPrinterTask.this.taskToRun != null) && (ZebraPrinterTask.this.taskToRun.getStatus() == Status.FINISHED))
	           {
	             return;
	           }
	           Sleeper.sleep(500L);
	           timeKeeper += 500L;
	         } while (timeKeeper < 60000L + currentTime);
	         
	         ZebraPrinterTask.this.taskToRun.cancel(true);
	         
	       }//end run()
	     
	     });
	   }

	   
	   public A doInBackground(final DiscoveredPrinter currentlySelectedPrinter,final T... parametros)
	   {
			  A retVal = null;

	     if (NetworkHelper.blueToothAndRadioIsOff())
	     {
	    	 	handleError(new IOException("Bluetooth radio is turned off"));
				return null;
	     }
	     else
	     {
	    	 
	        try {
	        	 
	               this.connectToPrinter(currentlySelectedPrinter);
	               final A doWorkRetorno = this.doWork(parametros);
	               disconnectFromPrinter();
	               if(doWorkRetorno!=null)
	               {	
	            	   	Class<?> clase = doWorkRetorno.getClass();
	               		String sClase = clase.toString();
	               }
	               return doWorkRetorno;
	            } 
	        	catch (ConnectionException conex) 
	            {
	               this.handleError(conex);
	               retVal = null;
	            } 
	        	catch (ZebraPrinterLanguageUnknownException unkEx) 
	            {
	               this.handleError(unkEx);
	               retVal = null;
	            } 
	        	catch (ZebraIllegalArgumentException argEx) 
	        	{
	               this.handleError(argEx);
	               retVal = null;
	               
	            } 
	        	catch (IOException ioEx) 
	        	{
	               this.handleError(ioEx);
	               retVal = null;
	            }
	    	
	    	 return retVal;
		}
	   }

	   
	   private void connectToPrinter(DiscoveredPrinter currentlySelectedPrinter) throws ConnectionException, ZebraPrinterLanguageUnknownException
	   {
		  
	     this.printerConnection = PrinterConnectionFactory.createPrinterConnection(currentlySelectedPrinter);
	     
	     //if (ZebraUtilitiesOptions.getPrinterLanguage().toUpperCase().equals("ZPL"))
	     {
	     //  this.printer = ZebraPrinterFactory.getInstance(PrinterLanguage.ZPL, this.printerConnection);
	     //  return;
	     }
	     
	     //if (ZebraUtilitiesOptions.getPrinterLanguage().toUpperCase().equals("CPCL"))
	     {
	       this.printer = ZebraPrinterFactory.getInstance(PrinterLanguage.CPCL, this.printerConnection);
	       return;
	     }
	     /*
	      * this.printer = ZebraPrinterFactory.getInstance(this.printerConnection);
	     */
	   }

		private void disconnectFromPrinter() 
		{
		    if (this.printerConnection == null) 
		    {
		        return;
		    }
		    try 
		    {
		        this.printerConnection.close();
		    }
		    catch (ConnectionException e) 
		    {
		        this.handleError(e);
		    }
		}

	   public void onPostExecute(final A result)
	   {
	     notifyTaskFinished();
	   }
	   public void onPreExecute()
	   {
	     notifyTaskStarted();
	   }
	   public void onProgressUpdate(final S ... result)
	   {
		   // no hacer nada
	   }

	   private void showPleaseTurnRadioOnDialog(final Activity activity, final DiscoveredPrinter currentlySelectedPrinter, final T... parametros)
	   {
	     Builder builder = new Builder(activity);
	     builder.setMessage("Do you want to turn Bluetooth on?");
		 builder.setTitle("Bluetooth is disabled.");
		 builder.setCancelable(false);
		 builder.setNegativeButton("Cancel", new OnClickListener()
			     {
			       public void onClick(DialogInterface dialog, int which)
			       {
			         ZebraPrinterTask.this.scheduleTask(activity, currentlySelectedPrinter, parametros);
			       }
			     });
	     builder.setPositiveButton("Turn on Bluetooth", new OnClickListener()
	     {
		       public void onClick(DialogInterface dialog, int which)
		       {
		         final ProgressDialog pd = ProgressDialog.show(activity, null, "Enabling Bluetooth...", true, false);
		         //begin Thread
		         new Thread(new Runnable()
		         {
		           public void run()
		           {
		             NetworkHelper.turnOnBluetoothRadio();
		             activity.runOnUiThread(new Runnable()
		             {
		               public void run()
		               {
		                 pd.dismiss();
		            	 ZebraPrinterTask.this.scheduleTask(activity, currentlySelectedPrinter, parametros);
		               }
		             });
		           }
		         }).start();
		       //end Thread
		       }
	     });
	     AlertDialog alert = builder.create();
	     alert.show();
	   }
	   

		private void notifyTaskFinished() 
		{
		    ZebraActivityHelper.stopSpinner();
		}
		private void notifyTaskStarted() 
		{
		    ZebraActivityHelper.startSpinner();
		}

		

}
