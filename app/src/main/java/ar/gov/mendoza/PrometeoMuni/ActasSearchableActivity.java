package ar.gov.mendoza.PrometeoMuni;

import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;
import com.zebra.android.zebrautilitiesmza.util.ImageHelper;
import com.zebra.android.zebrautilitiesmza.util.UIHelper;
import com.zebra.android.zebrautilitiesmza.util.ZebraPrinterTask;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.device.ZebraIllegalArgumentException;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

//RESTAURAR2016 import ar.gov.mendoza.deviceactas.R;
import ar.gov.mendoza.PrometeoMuni.core.domain.ActaConstatacion;
import ar.gov.mendoza.PrometeoMuni.core.util.Tools;
import ar.gov.mendoza.PrometeoMuni.abstracts.HandlerSearchableListViewInterface;
import ar.gov.mendoza.PrometeoMuni.abstracts.SearchableActivity;
import ar.gov.mendoza.PrometeoMuni.dao.ActaConstatacionDao;
import ar.gov.mendoza.PrometeoMuni.rules.ActaConstatacionRules;
import ar.gov.mendoza.PrometeoMuni.rules.InformeActaRules;
import ar.gov.mendoza.PrometeoMuni.screens.actas.PrintActaHelper;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;
import ar.gov.mendoza.PrometeoMuni.sync.ActaConstatacionSync;
import ar.gov.mendoza.PrometeoMuni.utils.Utilities;

public class ActasSearchableActivity extends SearchableActivity<ActaConstatacion> {
 
	LoadViewTask loadViewTask;
    ProgressDialog pleaseWaitDialog;
    private Boolean WorkingInPrintAsyncTask = false; 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.adminactasoptions, menu);
		super.onCreateOptionsMenu(menu);
		return true;
	}
	
	//@Override
    //   
	//
	 @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
		
	    switch (item.getItemId()) {
	    // action with ID action_settings was selected
	    case R.id.action_acta_sincronizar:
	      //doSincronizarActas();
	    	//Toast.makeText(this, "Sincronizar", Toast.LENGTH_SHORT).show();
	      
	      break;
	    default:
	      break;
	    }

	    return super.onOptionsItemSelected(item);
	  }
	 
	public void ItemButtonOnClickHandler(View v) {
		//AtomPayment itemToRemove = (AtomPayment)v.getTag();
		//adapter.remove(itemToRemove);
		ActaConstatacion acta = (ActaConstatacion) v.getTag();
		if (acta.getIdActaConstatacion()==null || acta.getIdActaConstatacion()==-1)
			return;
		
		Utilities.ShowToast(this, "Numero de Acta Seleccionada " +  acta.getNumeroActa() );
		acta = Utilities.CargarInformacionAdicional(acta);
		
		//Tools.PersitirToXml(acta);
		Gson gson = new Gson(); 
		String valor ="";
		valor = gson.toJson(acta);
		String otroValor = valor;
		Utilities.PersitirToFichero(valor);
    	doPrintActaRequest(acta);
		
		
	}
	protected void doPrintActaRequest(final ActaConstatacion pActa) {

		final ActaConstatacionRules rules = new ActaConstatacionRules(this);

		new ZebraPrinterTask<Object, Object, Object>(ActasSearchableActivity.this)
         {
    	    
           @Override
           public Object doWork(final Object[] params)
             throws ConnectionException, ZebraIllegalArgumentException
	           {
        	    /*
	             ((TrafficTicketViolatorData)TrafficTicket.violatorArrayList.get(TrafficTicket.indexOfViolator)).plateNumber = TrafficTicket.this.licensePlate;
				 ((TrafficTicketViolatorData)TrafficTicket.violatorArrayList.get(TrafficTicket.indexOfViolator)).violation = ((String)spinner.getSelectedItem());
				 */
	            // if (this.printer.getPrinterControlLanguage() == PrinterLanguage.CPCL)
        	   try
	             {
	              // CreateActaHelper.printCreateActaCPCL((ActaConstatacion)pActa,this.printer);
        		   PrintActaHelper printActaHelper =  new PrintActaHelper(this.printerConnection, this.printer);
        		   printActaHelper.printCreateActaCPCL(pActa,true,  rules.omitirCodigoBarra(pActa));
	             }
        	    catch(Exception ex)
        	   {
        		   handleError(ex);
        	   }
	            // else
	             {
	             //  TrafficTicketHelper.printTrafficTicketZPL((TrafficTicketViolatorData)TrafficTicket.violatorArrayList.get(TrafficTicket.indexOfViolator), TrafficTicket.sig, this.printer);
	             }
	             return null;
	           }
           @Override
           public void handleError(final Exception e)
           {
            
        	   if (e instanceof ConnectionException)
         		  UIHelper.showErrorOnGuiThread(ActasSearchableActivity.this, "No se pudo conectar con la Impresora\nVerifique que la Impresora este Encendida", null);
         	  else
         	  {
         		  UIHelper.showErrorOnGuiThread(ActasSearchableActivity.this, e.getLocalizedMessage(), null);
         	  }         
         			  //QuizCreateActaActivity.this.finish();
           }
           
           @Override
           public void onPreExecute()
           {
   			WorkingInPrintAsyncTask = true;
   			pleaseWaitDialog = ProgressDialog.show(ActasSearchableActivity.this,"Impresion de Acta", "Iniciando conexion con Impresora", true, false);
        	super.onPreExecute();
           }
           @Override
           public void onPostExecute(Object object)
           {
        		WorkingInPrintAsyncTask = false; 
    			super.onPostExecute(object);
    			
    			pleaseWaitDialog.dismiss();
    			pleaseWaitDialog = null;
    			//ActasSearchableActivity.this.finish();
           }
         };
        // make sure we don't collide with another pending update
    	/*
        if (printActaRequest == null || printActaRequest.getStatus() == AsyncTask.Status.FINISHED || printActaRequest.isCancelled()) {
        	printActaRequest = new PrintActaRequestTask(this);
        	printActaRequest.execute(pActa);
        } else {
            Log.w(DEBUG_TAG, "Advertencia: La Solicitud de Impresion del Acta : " + pActa.getNumeroActa() + " ya esta siendo procesada");
        }
        */
    }
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.titleHeader ="Listado de Actas Encontradas";
		this.genericDao = new ActaConstatacionDao();
		this.FIELD_SEARCH_DEFAULT ="NUMERO_ACTA";
		this.SHOW_ALL_ITEMS_DEFAULT = true;
		this.LIKEABLE_SEARCH_DEFAULT = true;
		this.SELECTOR_LIST_ITEM = R.drawable.selector;
		//this.CHOICE_MODE_LIST_VIEW = ListView.CHOICE_MODE_MULTIPLE;
		this.idLayoutItems = R.layout.list_item_with_two_text_button;
		
		
		this.handlerSelectItemClick = new HandlerSearchableListViewInterface() {
			
			@Override
			public void onSelectedItem(Object objItem,View view) {
			}
			
		};
		super.onCreate(savedInstanceState);
		
	}

	public void doSincronizarActas()
	{
		if (lstItems==null || lstItems.size()==0) // por lo general siempre hay una acta en al lista de items-..
		{
			UIHelper.showAlertOnGuiThread(this, "No hay Actas para Sincronizar", "Sin Actas", null);
			return;
		}
		else
		{
			ActaConstatacion acta = lstItems.get(0);
			if (acta.getIdActaConstatacion()==null || acta.getIdActaConstatacion() ==-1)
			{
				UIHelper.showAlertOnGuiThread(this, "No hay Actas para Sincronizar", "Policia Caminera", null);
				return;
			}
		}
			
		
		
		UIHelper.showAlertCancelable(this, "Desea Sincronizar todas las Actas", "Policia Caminera", new Runnable() {
			
			@Override
			public void run() {
				ActaConstatacion[] actasConstatacions = new ActaConstatacion[lstItems.size()];
				lstItems.toArray(actasConstatacions);
				loadViewTask = new LoadViewTask(actasConstatacions);    
			    loadViewTask.execute();
			}
		});

	}
	  //To use the AsyncTask, it must be subclassed  
    private class LoadViewTask extends AsyncTask<Void, Object, Void>  
    {  
    	final private Integer cantidadTareas;
    	final private ActaConstatacion[] actasConstatacion;
    	private Integer cantidadTareasProcesadas = 0;
    	private Integer cantidadTareasSincronizadas = 0;
    	public LoadViewTask(ActaConstatacion[] actasConstatacion)
    	{ 
    		this.actasConstatacion = actasConstatacion;
    		cantidadTareas = this.actasConstatacion.length;
    	}
        //Before running code in separate thread  
        @Override  
        protected void onPreExecute()  
        {  
            //Create a new progress dialog  
            progressDialog = new ProgressDialog(ActasSearchableActivity.this);  
            //Set the progress dialog to display a horizontal progress bar  
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
            //Set the dialog title to 'Loading...'  
            progressDialog.setTitle("Sincronizando Actas...");  
            //Set the dialog message to 'Loading application View, please wait...'  
            progressDialog.setMessage("Sincronizando Actas, Espere un momento por favor...");  
            //This dialog can't be canceled by pressing the back key  
            progressDialog.setCancelable(false);  
            //This dialog isn't indeterminate  
            progressDialog.setIndeterminate(false);  
            //The maximum number of items is 100  
            progressDialog.setMax(cantidadTareas);  
            //Set the current progress to zero  
            progressDialog.setProgress(0);  
            //Display the progress dialog  
            progressDialog.show();  
        }  
  
        //The code to be executed in a background thread.  
        @Override  
        protected Void doInBackground(Void... params)  
        {  
            /* This is just a code that delays the thread execution 4 times, 
             * during 850 milliseconds and updates the current progress. This 
             * is where the code that is going to be executed on a background 
             * thread must be placed. 
             */  
            try  
            {  
                //Get the current thread's token  
                synchronized (this)  
                {  
                    //Initialize an integer (that will act as a counter) to zero  
                    
                    //While the counter is smaller than four
                    cantidadTareasProcesadas = 0;
                  
        	        
                    while(cantidadTareasProcesadas < cantidadTareas)  
                    {  
                       ActaConstatacion currentActa = actasConstatacion[cantidadTareasProcesadas];
                    	
                       ActaConstatacionSync actaSync = new ActaConstatacionSync(GlobalStateApp.getInstance().getApplicationContext());
                       String sMensaje ="";
                       String sNumeroActa= currentActa.getNumeroActa();
                       sMensaje  ="Iniciando Proceso de Acta Numero " + sNumeroActa  + " (" + Tools.DateValueOf( Tools.Today(),"dd/MM/yyyy HH:mm:ss") + ")";
                       if (actaSync.sincronizarActa(currentActa)==true)
                       {
                    	   sMensaje  ="El Acta Numero " + sNumeroActa  + " ha sido sincronizada correctamente!";
                    	   //procesar las imagenes
                    	   //si se graban las imagenes, 
                    	   //poner el acta como sincronizada
                    	   String numeroActa = currentActa.getNumeroActa();
                    	   String[] xFotos;
                    	  if(currentActa.getFotos()!=null &&currentActa.getFotos().length()>0)
                    	  {
                    		  xFotos = currentActa.getFotos().split("\\|");
                    	  }
                    	  else
                    	  {
                    		  xFotos = null;
                    	  }
                    	  
                          
                           Boolean resultadoB = true;
                           if (xFotos != null && xFotos.length>0)
                           {	
                        	   int icantidadFotos = xFotos.length;
                        	   int icantidadFotosProcesadas = 0;
                        	   sMensaje  =sMensaje + "\nCantidad de Fotos que contiene el Acta " + icantidadFotos;
                        	   for (int j = 0; j < xFotos.length; j++)
                               {
                    	   
			                    	   String foto =xFotos[j]; // licencia documento otros
			                    	   File fileFoto = new File(foto);
			                    	   //existe y se puede leer
			                    	   if (fileFoto !=null && fileFoto.exists()==true && fileFoto.canRead()==true)
			                    	   {
			                    		  
			                    		  Boolean bBitmapObtenido = false;
				                    	   Bitmap bitmap;
				                    	   try
				                    	   {
				                    		   bitmap = ImageHelper.getBitmap(foto);
				                    		   bBitmapObtenido = true;
				                    	   }
				                    	   catch(IOException ioe)
				                    	   {
				                    		   bitmap = null;   
				                    	   }
				                    	   
				                    	   if(bBitmapObtenido==true && bitmap!=null)
				                    	   {
				                    		   if(actaSync.sincronizarFotoActa(numeroActa,foto,bitmap)==true)
					                    	   {
				                    			   icantidadFotosProcesadas++;
					                    		   sMensaje  =sMensaje + "\n Foto Procesada (" + (j + 1) + ") " + foto ;
					                    		   
					                    	   }
				                    	   }
			                    	   }
			                 }
                        	   
                        	  if (icantidadFotos>icantidadFotosProcesadas)
                        	  {
                        		  sMensaje  =sMensaje + "\nHubo problemas el la sincronizacion de las Fotos \nIntente mas tarde nuevamente";
                        		  resultadoB = false;
                        	  }
                        	  else
                        	  {
                        		  // se procesaron todas las fotos de esta acta
                        		  resultadoB = true;
                        		  cantidadTareasSincronizadas++;
                        	  }
                        	   
                           }
                           else
                           {
                        	   resultadoB = true;
                        	   cantidadTareasSincronizadas++;
                           }

                           
                           if (resultadoB == true)
                           {
                        	   ActaConstatacionRules actaRules = new ActaConstatacionRules(ActasSearchableActivity.this);
                        	   actaRules.marcarActaSincronizada(currentActa.getIdActaConstatacion());
                               //actaServices.actaDelete(acta.Id_Acta_Constatacion);
                               //Si se elimina le cambio el estado a "Transmitida" en la table T_INFORMES_ACTAS
                        	   
                        	   InformeActaRules informeActaRules = new InformeActaRules(ActasSearchableActivity.this);
                        	   informeActaRules.marcarSincronizadoInformeActa(currentActa.getIdActaConstatacion());
                               /*
                                InformeActasService informesService = new InformeActasService();
                               informesService.updateInformeService(actaId);
                               */
                           	  String[] xToDeleteFotos;
                         	  if(currentActa.getFotos()!=null &&currentActa.getFotos().length()>0)
                         	  {
                         		 xToDeleteFotos = currentActa.getFotos().split("\\|");
                         	  }
                         	  else
                         	  {
                         		 xToDeleteFotos = null;
                         	  }
	                       	   for (int j = 0; j < xFotos.length; j++)
	                           {
                	   
		                    	   String foto =xToDeleteFotos[j]; // licencia documento otros
		                    	   File fileFoto = new File(foto);
		                    	   //existe y se puede leer
		                    	   if (fileFoto !=null && fileFoto.exists()==true && fileFoto.canRead()==true)
		                    	   {
		                    		   try
		                    		   {
		                    			   fileFoto.delete();
		                    		   }
		                    		   catch(Exception ex)
		                    		   {
		                    			   
		                    		   }
		                    	   
		                    	   }
	                           }
                           }
                       }
                       else
                       {
                    	   sMensaje  ="El Acta Numero " + sNumeroActa  + " no pudo ser sincronizada!";
                       }
                       //Wait 850 milliseconds  
                        this.wait(850);  
                        //Increment the counter  
                        cantidadTareasProcesadas++;  
                        //Set the current progress.  
                        //This value is going to be passed to the onProgressUpdate() method.  
                        publishProgress(cantidadTareasProcesadas,sMensaje);  
                    }  
                    this.wait(850); 
                }  
            }  
            catch (InterruptedException e)  
            {  
                e.printStackTrace();  
            }  
            return null;  
        }  
  
        //Update the progress  
        @Override  
        protected void onProgressUpdate(Object... values)  
        {  
            //set the current progress of the progress dialog  
            progressDialog.setProgress((Integer) values[0]);  
            progressDialog.setMessage("Sincronizando Actas, Espere un momento por favor...\n" + values[1].toString());  
        }  
  
        //after executing the code in the thread  
        @Override  
        protected void onPostExecute(Void result)  
        {  
            //close the progress dialog  
            progressDialog.dismiss();  
            //cantidadTareasProcesadas;
            //initialize the View  
            //setContentView(R.layout.main);  
           UIHelper.showAlertOnGuiThread(ActasSearchableActivity.this, "Cantidad de Actas Sincronizadas " + cantidadTareasSincronizadas + "/" + cantidadTareasProcesadas, "Sincronizacion Finalizada", null);
            
        }  
    }  

	
}
