package ar.gov.mendoza.PrometeoMuni.services;

import java.util.List;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import ar.gov.mendoza.PrometeoMuni.rules.DeviceActasRules;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;
import ar.gov.mendoza.PrometeoMuni.sync.DeviceActasRestSync;
import ar.gov.mendoza.PrometeoMuni.sync.dto.ActualizacionDTO;
import ar.gov.mendoza.PrometeoMuni.utils.Utilities;

public class ActualizacionService extends Service {

	
    private static final String TAG = "ActualizacionService";

    private boolean isRunning  = false;
    
	/** indicates how to behave if the service is killed */
	   int mStartMode = Service.START_NOT_STICKY;
	   
	   /** interface for clients that bind */
	   IBinder mBinder;     
	   
	   /** indicates whether onRebind should be used */
	   boolean mAllowRebind;

	   /** Called when the service is being created. */
	   @Override
	   public void onCreate() {
	     
		   Log.i(TAG, "SetupService onCreate");

	        isRunning = true;
	   }

	   @Override
	   public void onStart(Intent intent, int startId)
	   {
		   super.onStart(intent, startId);
		   Log.i(TAG, "ActualizacionService onStart , Level 3 o L4");
		   
		   doServiceStart(intent, startId);
	   }
	   
	   public void doServiceStart(Intent intent , int StartId)
	   {
		   
		   performOnBackgroundThread(new Runnable() {
				@Override
				public void run() {
					
					
					doActualizarParametros();
            		//se pueden tambien hacer las actualizaciones correspondientes
	                //Stop service once it finishes its task
	                stopSelf();
					
				}
			});
		   
	   }
	   
	   LoadActualizacionesTask actualizacionTask;
	   protected ProgressDialog progressDialog;
		
	   public void doActualizarParametros()
	   	{
	   	    
	    	
	    	//UIHelper.showAlertOnGuiThread(this, "No Implementada Todavia", "Actualizacion", null);
	    	if (actualizacionTask == null || actualizacionTask.getStatus() == AsyncTask.Status.FINISHED || actualizacionTask.isCancelled()) 
	    	{
	         actualizacionTask = new LoadActualizacionesTask();    
	    	 actualizacionTask.execute();
	    	}
	   	}
	   /** The service is starting, due to a call to startService() */
	   @Override
	   public int onStartCommand(Intent intent, int flags, int startId) {
		   Log.i(TAG, "ActualizacionService onStartCommand L5 o greater");
		   // Let it continue running until it is stopped.
		      //Toast.makeText(this, "Buscando Actualizaciones ...", Toast.LENGTH_SHORT).show();
		      
		      if(flags!=0){
		    	  Log.w(TAG, "Redelivered  or retrying service start: "+flags); 
		      }

		      doServiceStart(intent, startId);
		   int i =  Service.START_REDELIVER_INTENT;
	      return mStartMode;
	   }

	   
	   public static Thread performOnBackgroundThread(final Runnable runnable) {
	        final Thread t = new Thread() {
	            @Override
	            public void run() {
	                try {
	                    runnable.run();
	                } finally {

	                }
	            }
	        };
	        t.start();
	        return t;
	    }
	   
	   
	   /** A client is binding to the service with bindService() */
	   @Override
       public IBinder onBind(Intent intent) {
			// TODO Auto-generated method stub
			return mBinder;
	   }
	   
	   
	   /** Called when all clients have unbound with unbindService() */
	   @Override
	   public boolean onUnbind(Intent intent) {
	      return mAllowRebind;
	   }

	   /** Called when a client is binding to the service with bindService()*/
	   @Override
	   public void onRebind(Intent intent) {

	   }

	   /** Called when The service is no longer used and is being destroyed */
	   @Override
	   public void onDestroy() {
		    super.onDestroy();
		      //Toast.makeText(this, "Actualizacion Finalizada ...", Toast.LENGTH_SHORT).show();
	   }
	   

	    //To use the AsyncTask, it must be subclassed  
	    private class LoadActualizacionesTask extends AsyncTask<Void, Object, Void>  
	    {  
	    	private Integer cantidadTareas;
	    	private ActualizacionDTO[] aActualizacionDTO;
	    	private Integer cantidadTareasProcesadas = 0;
	    	private Integer cantidadTareasAplicadas = 0;

	        //Before running code in separate thread  
	        @Override  
	        protected void onPreExecute()  
	        {
	        	/*
	            progressDialog = new ProgressDialog(getApplicationContext());  
	            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	            progressDialog.setProgressNumberFormat(null);
	            progressDialog.setProgressPercentFormat(null);
	            progressDialog.setTitle("Actualizando Dispositivo...");  
	            progressDialog.setMessage("Buscando Actualizaciones, Espere un momento por favor...");  
	            progressDialog.setCancelable(false);  
	            progressDialog.setIndeterminate(true);//antes era false  
	            progressDialog.show();
	            */  
	        }  
	  
	        @Override  
	        protected Void doInBackground(Void... params)  
	        {  
	            try  
	            {  
	                synchronized (this)  
	                {  
	                    cantidadTareasProcesadas = 0;
	                    DeviceActasRestSync actualizacionSync = new DeviceActasRestSync(GlobalStateApp.getInstance().getApplicationContext());
	                    String sMensaje ="";
	                    sMensaje  ="Buscando Actualizaciones ...";
	                    List<ActualizacionDTO> lst = actualizacionSync.BuscarActualizaciones("SQL");
	                    aActualizacionDTO = new ActualizacionDTO[lst.size()];
	                    lst.toArray(aActualizacionDTO);
	                    cantidadTareas = aActualizacionDTO.length;
	                    if (cantidadTareas==0)
	                    {  sMensaje ="";
	                    	publishProgress(cantidadTareasProcesadas,sMensaje);
	                    	 this.wait(850); 
	                    }
	                    
	                    DeviceActasRules deviceActaRules = new DeviceActasRules(GlobalStateApp.getInstance().getApplicationContext());
	                    String idsActualizacionRepat ="";
	                    while(cantidadTareasProcesadas < cantidadTareas)  
	                    {  
	                    	   ActualizacionDTO currentActualizacion = aActualizacionDTO[cantidadTareasProcesadas];
	                    	   idsActualizacionRepat = idsActualizacionRepat + (idsActualizacionRepat.equals("")?"":";") + currentActualizacion.getId();
	                    	   sMensaje  ="Procesando Actualizacion Nro: " + (cantidadTareasProcesadas+1);
	                    	   publishProgress(cantidadTareasProcesadas,sMensaje);
	                    	   if (deviceActaRules.aplicarActualizacion(currentActualizacion)==true)
	                           {
	                        	   cantidadTareasAplicadas++;
	                           }
	                           this.wait(850);  
	                           cantidadTareasProcesadas++;  
	                    }  
	                    if(idsActualizacionRepat!=null && !idsActualizacionRepat.equals(""))
						{   // llamamos todavia al servicio web anterior hasta que lo definamos con Maxi
							//DeviceActasSync actualizacionOldSync = new DeviceActasSync(GlobalStateApp.getInstance().getApplicationContext());
							//actualizacionOldSync.ConfirmarActualizaciones(idsActualizacionRepat);
							actualizacionSync = new DeviceActasRestSync(GlobalStateApp.getInstance().getApplicationContext());
							actualizacionSync.ConfirmarActualizaciones(idsActualizacionRepat);
						}

	                    
	                    this.wait(850); 
	                }  
	            }  
	            catch (InterruptedException e)  
	            {  
	            }  
	            return null;  
	        }  
	  
	        //Update the progress  
	        @Override  
	        protected void onProgressUpdate(Object... values)  
	        {  
	            //progressDialog.setMessage("Actualizando Dispositivo, Espere un momento por favor...\n" + values[1].toString());
	        	Toast.makeText(getApplicationContext(), "Espere un momento por favor...\n" + values[1].toString(), Toast.LENGTH_SHORT).show();
	        }  
	  
	        @Override  
	        protected void onPostExecute(Void result)  
	        {  
	            //progressDialog.dismiss();  
	            try
	            {
	            Utilities.ConfigGlobalVars(getApplicationContext());
	            }
	            catch(Exception ex)
	            {
	            	
	            }
	            Toast.makeText(getApplicationContext(), "Actualizaciones realizadas correctamente " + cantidadTareasAplicadas + "/" + cantidadTareas, Toast.LENGTH_SHORT).show();
	            //UIHelper.showAlert(getApplicationContext(), "Actualizaciones realizadas correctamente " + cantidadTareasAplicadas + "/" + cantidadTareas, "Actualizacion Finalizada", null);
	            
	        }  
	    }  


	
	
}
