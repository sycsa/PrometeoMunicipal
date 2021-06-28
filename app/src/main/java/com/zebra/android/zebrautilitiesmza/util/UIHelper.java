 package com.zebra.android.zebrautilitiesmza.util;
 
 import android.app.Activity;
 import android.app.AlertDialog;
 import android.app.AlertDialog.Builder;
 import android.content.Context;
 import android.content.DialogInterface;
 import android.content.DialogInterface.OnClickListener;

 public class UIHelper
 {
	   public static void showAlertOnGuiThread(final Activity activity, final String message, final String title, final Runnable r)
	   {
	     if (!activity.isFinishing()) {
	       activity.runOnUiThread(new Runnable()
	       {
	         public void run()
	         {
	           UIHelper.showAlert(activity, message, title, r);
	         }
	       });
	     }
	   }
   
   public static void showErrorOnGuiThread(final Activity activity, final String message, final Runnable r)
   {
     if (!activity.isFinishing()) {
       activity.runOnUiThread(new Runnable()
       {
         public void run()
         {
           UIHelper.showAlert(activity, message, "Error ..!", r);
         }
       });
     }
   }
   public static void showAlertCancelable(Context context, String message, String title,final Runnable r)
   {
     Builder builder = new Builder(context);
     
     int image = 2130837505;					//setIcon(image).
     builder.setMessage(message).setTitle(title).setCancelable(true).setPositiveButton("Aceptar", new OnClickListener()
		     {
		       public void onClick(DialogInterface dialog, int id)
		       {
		         dialog.dismiss();
		         if (r != null) {
		           r.run();
		         }
		       }
		     }
       ).setNegativeButton("Cancelar", new OnClickListener()
		     {
		       public void onClick(DialogInterface dialog, int id)
		       {
		         dialog.dismiss();
		       }
		     }
       );
     AlertDialog  alert = builder.create();
     alert.show();
   }
   
   public static void showAlertCancelableExtended(Context context, String message, String title,final Runnable r,final Runnable rExtended)
   {
     Builder builder = new Builder(context);
     
     int image = 2130837505;					//setIcon(image).
     builder.setMessage(message).setTitle(title).setCancelable(true).setPositiveButton("Aceptar", new OnClickListener()
		     {
		       public void onClick(DialogInterface dialog, int id)
		       {
		         dialog.dismiss();
		         if (r != null) {
		           r.run();
		         }
		       }
		     }
       ).setNegativeButton("Cancelar", new OnClickListener()
		     {
		       public void onClick(DialogInterface dialog, int id)
		       {
		         dialog.dismiss();
		         if (rExtended != null) {
		        	 rExtended.run();
			         }

		       }
		     }
       );
     AlertDialog  alert = builder.create();
     alert.show();
   }
   public static void showAlert(Context context, String message, String title,final Runnable r)
   {
     Builder builder = new Builder(context);
     
     int image = 2130837505;					//setIcon(image).
     builder.setMessage(message).setTitle(title).setCancelable(false).setPositiveButton("OK", new OnClickListener()
		     {
		       public void onClick(DialogInterface dialog, int id)
		       {
		         dialog.dismiss();
		         if (r != null) {
		           r.run();
		         }
		       }
		     }
       );
     AlertDialog  alert = builder.create();
     alert.show();
   }

 }