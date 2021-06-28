package ar.gov.mendoza.PrometeoMuni;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import com.zebra.android.zebrautilitiesmza.util.ImageHelper;
import com.zebra.android.zebrautilitiesmza.util.UIHelper;
import com.zebra.android.zebrautilitiesmza.util.ZebraPrinterTask;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.device.ZebraIllegalArgumentException;

import android.view.MotionEvent;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import ar.gov.mendoza.PrometeoMuni.core.domain.ActaConstatacion;
import ar.gov.mendoza.PrometeoMuni.core.domain.InformeActa;
import ar.gov.mendoza.PrometeoMuni.core.util.Tools;
import ar.gov.mendoza.PrometeoMuni.abstracts.HandlerSearchableListViewInterface;
import ar.gov.mendoza.PrometeoMuni.abstracts.SearchableActivity;
import ar.gov.mendoza.PrometeoMuni.adapters.GenericListAdapter;
import ar.gov.mendoza.PrometeoMuni.dao.InformeActaDao;
import ar.gov.mendoza.PrometeoMuni.rules.ActaConstatacionRules;
import ar.gov.mendoza.PrometeoMuni.rules.InformeActaRules;
import ar.gov.mendoza.PrometeoMuni.screens.actas.PrintActaHelper;
import ar.gov.mendoza.PrometeoMuni.screens.actas.PrintInformeActaHelper;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;
import ar.gov.mendoza.PrometeoMuni.sync.ActaConstatacionSync;
import ar.gov.mendoza.PrometeoMuni.utils.Utilities;

public class InformeActasSearchableActivity extends SearchableActivity<InformeActa> {
 
	LoadViewTask loadViewTask;
    ProgressDialog pleaseWaitDialog;
    private Boolean WorkingInPrintAsyncTask = false; 
    Button button_FechaDesde;
    Button button_HoraDesde;
    Button button_FechaHasta;
    Button button_HoraHasta;
    Button button_Buscar;
    Button button_Imprimir;
    //DIALOG IDS
    static final int FROM_DATE_DIALOG_ID=0;
    static final int FROM_HOUR_DIALOG_ID=1;
    static final int TO_DATE_DIALOG_ID=2;
    static final int TO_HOUR_DIALOG_ID=3;
    
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
//		ActaConstatacion acta = (ActaConstatacion) v.getTag();
//		if (acta.getIdActaConstatacion()==null || acta.getIdActaConstatacion()==-1)
//			return;
//		
//		Utilities.ShowToast(this, "Numero de Acta Seleccionada " +  acta.getNumeroActa() );
//		acta = Utilities.CargarInformacionAdicional(acta);
//		
//		Tools.PersitirToXml(acta);
		
    	//doPrintActaRequest(acta);
	}
	protected void doPrintActaRequest(final ActaConstatacion pActa) {
    	
	    
    	new ZebraPrinterTask<Object, Object, Object>(InformeActasSearchableActivity.this)
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
        		   printActaHelper.printCreateActaCPCL(pActa,true, false);
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
         		  UIHelper.showErrorOnGuiThread(InformeActasSearchableActivity.this, "No se pudo conectar con la Impresora\nVerifique que la Impresora este Encendida", null);
         	  else
         	  {
         		  UIHelper.showErrorOnGuiThread(InformeActasSearchableActivity.this, e.getLocalizedMessage(), null);
         	  }         
         			  //QuizCreateActaActivity.this.finish();
           }
           
           @Override
           public void onPreExecute()
           {
   			WorkingInPrintAsyncTask = true;
   			pleaseWaitDialog = ProgressDialog.show(InformeActasSearchableActivity.this,"Impresion de Acta", "Iniciando conexion con Impresora", true, false);
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
	public void doMySearch(String pQuery) {		
		Long iFechaHoraDesde;
		Long iFechaHoraHasta;
		
		String sFechaDesde;
		String sFechaHasta;
		
		button_FechaDesde = findViewById(R.id.Button_FechaDesde);
		button_FechaHasta = findViewById(R.id.Button_FechaHasta);
		button_HoraDesde = findViewById(R.id.Button_HoraDesde);
		button_HoraHasta = findViewById(R.id.Button_HoraHasta);
		
		if(button_FechaDesde==null || button_HoraDesde ==null|| button_FechaHasta ==null || button_HoraHasta==null)
			return;
		
		sFechaDesde = button_FechaDesde.getText().toString() + " " + button_HoraDesde.getText().toString();
		sFechaHasta = button_FechaHasta.getText().toString() + " " + button_HoraHasta.getText().toString();
		
		iFechaHoraDesde = Tools.ConvertDateToLong(Tools.ConvertStringToDate(sFechaDesde, "dd/MM/yyyy HH:mm"));
		iFechaHoraHasta = Tools.ConvertDateToLong(Tools.ConvertStringToDate(sFechaHasta, "dd/MM/yyyy HH:mm"));
		
		String  sUsuario = pQuery;
		
		if (TextUtils.isEmpty(pQuery)) sUsuario ="%";
		
		if (!TextUtils.isEmpty(pQuery)  )
		{
		 
	      textView_PatronBusqueda.setVisibility(View.VISIBLE);
	      textView_PatronBusqueda.setText("Buscando por el patron de busqueda : " + pQuery);
	      	
      		InformeActaDao informeDao = (InformeActaDao) genericDao;
			lstItems =  informeDao.getInformeActaByUsuarioyFecha(sUsuario, iFechaHoraDesde, iFechaHoraHasta);
      		
      		
      		if (lstItems.size()>0)
      		{
      			Utilities.ShowToast(GlobalStateApp.mContext, "Items Encontrado encontrados " + lstItems.size());
      		}
      		else
      		{	
      			     InformeActa newObject = genericDao.getNewObject();
      			     newObject.setItemId("-1");
      			     newObject.setItemName("No se encontraron items ...");
      				 lstItems.add(newObject);
      		}
      		
      		GenericListAdapter<InformeActa> myTAdapter = new GenericListAdapter<InformeActa>(this,getLayoutItems(),lstItems);
            setListAdapter(myTAdapter);		
	    	  
	      
		}
		else
		{
			textView_PatronBusqueda.setVisibility(View.GONE);
		}
			
	      
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.titleHeader ="Resumen de Actas Realizadas";
		this.genericDao = new InformeActaDao();
		this.FIELD_SEARCH_DEFAULT ="USUARIO";
		this.SHOW_ALL_ITEMS_DEFAULT = true;
		this.LIKEABLE_SEARCH_DEFAULT = true;
		this.SELECTOR_LIST_ITEM = R.drawable.selector;
		//this.CHOICE_MODE_LIST_VIEW = ListView.CHOICE_MODE_MULTIPLE;
		//this.idLayoutItems = R.layout.list_item_with_two_text_button;
		this.idLayoutActivity = R.layout.informes_acta;
		
		
		this.handlerSelectItemClick = new HandlerSearchableListViewInterface() {
			
			@Override
			public void onSelectedItem(Object objItem,View view) {
			}
			
		};
		super.onCreate(savedInstanceState);
		
		
		TextView textView_PatronBusqueda = findViewById(R.id.TextView_PatronBusqueda);
		textView_PatronBusqueda.setVisibility(View.GONE);
		button_FechaDesde = findViewById(R.id.Button_FechaDesde);
		button_FechaDesde.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)	 {
				showDialog(FROM_DATE_DIALOG_ID);
			}
		});

		//showDialog(EXPIRE_DATE_DIALOG_ID);
		button_HoraDesde = findViewById(R.id.Button_HoraDesde);
		button_HoraDesde.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)	 {
				showDialog(FROM_HOUR_DIALOG_ID);
			}
		});

		button_FechaHasta = findViewById(R.id.Button_FechaHasta);
		button_FechaHasta.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)	 {
				showDialog(TO_DATE_DIALOG_ID);
			}
		});

		button_HoraHasta = findViewById(R.id.Button_HoraHasta);
		button_HoraHasta.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)	 {
				showDialog(TO_HOUR_DIALOG_ID);
			}
		});

		button_Buscar = findViewById(R.id.Button_Buscar);
		button_Buscar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)	 {
				doBuscar();
				//aqui hacemos la busqueda
			}
		});
		button_Buscar.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                  InputMethodManager inputMethodManager = (InputMethodManager)  InformeActasSearchableActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(InformeActasSearchableActivity.this.getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        });
		
		button_Imprimir= findViewById(R.id.Button_Imprimir);
		button_Imprimir.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)	 {
				doImprimirInformeActas();

			}
		});
		
		setCurrentTimeOnView();
	}
	
	private int hour;
	private int minute;
	private String fecha;
	
	
	@Override
	public boolean onQueryTextSubmit(String arg0) {
		doBuscar();
		return false;
	}
	
	private void doBuscar()
	{
		
		LinearLayout ll = (LinearLayout)searchView.getChildAt(0);
		LinearLayout ll2 = (LinearLayout)ll.getChildAt(2);
	    LinearLayout ll3 = (LinearLayout)ll2.getChildAt(1);
	    SearchView.SearchAutoComplete autoComplete = (SearchView.SearchAutoComplete) ll3.getChildAt(0);
	    
	    
	    
	    Utilities.ShowToast(this, autoComplete.getText().toString());
	    String pQuery = autoComplete.getText().toString();
	    doMySearch(pQuery);
		
	}
	
	private static String pad(int c) {
		if (c >= 10)
		   return String.valueOf(c);
		else
		   return "0" + c;
	}
	// display current time
		public void setCurrentTimeOnView() {
	 

	 
			final Calendar c = Calendar.getInstance();
			hour = c.get(Calendar.HOUR_OF_DAY);
			minute = c.get(Calendar.MINUTE);
			Integer mes =c.get(Calendar.MONTH) + 1;
			fecha = pad(c.get(Calendar.DAY_OF_MONTH)) + "/" + pad(mes) +"/" +c.get(Calendar.YEAR) ;
			
			button_FechaDesde.setText(fecha);
			button_FechaHasta.setText(fecha);
			
			// set current time into textview
			button_HoraDesde.setText(
	                    new StringBuilder().append("00")
	                                       .append(":").append("00"));
	
			button_HoraHasta.setText(
                    new StringBuilder().append(pad(hour))
                                       .append(":").append(pad(minute)));

			
			
			// set current time into timepicker
			//timePicker1.setCurrentHour(hour);
			//timePicker1.setCurrentMinute(minute);
	 
		}
	@Override
	protected Dialog onCreateDialog(int id,final Bundle arg) {
		switch (id) {
		
		case FROM_HOUR_DIALOG_ID:
			// set time picker as current time
			return new TimePickerDialog(this,FromtimePickerListener, hour, minute,false);
		case TO_HOUR_DIALOG_ID:
			// set time picker as current time
			return new TimePickerDialog(this,TotimePickerListener, hour, minute,false);
 
		case FROM_DATE_DIALOG_ID:
			Calendar calendario = Calendar.getInstance();
			DatePickerDialog dateFVLDialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) 
						{
							Time dateOfExpired = new Time();
							dateOfExpired.set(dayOfMonth, monthOfYear, year);
							long dtDob = dateOfExpired.toMillis(true);
							CharSequence DateFLV = DateFormat.format("dd/MM/yyyy",dtDob);
							button_FechaDesde.setText(DateFLV);
						}
					}, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH),calendario.get(Calendar.DAY_OF_MONTH));
			return dateFVLDialog;	

		case TO_DATE_DIALOG_ID:
			Calendar calendario2 = Calendar.getInstance();
			DatePickerDialog dateTFVLDialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) 
						{
							Time dateOfExpired = new Time();
							dateOfExpired.set(dayOfMonth, monthOfYear, year);
							long dtDob = dateOfExpired.toMillis(true);
							CharSequence DateFLV = DateFormat.format("dd/MM/yyyy",dtDob);
							button_FechaHasta.setText(DateFLV);
						}
					}, calendario2.get(Calendar.YEAR), calendario2.get(Calendar.MONTH),calendario2.get(Calendar.DAY_OF_MONTH));
			return dateTFVLDialog;	
		}
		return null;
	}
	
	private TimePickerDialog.OnTimeSetListener FromtimePickerListener = 
            new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;
 
			// set current time into textview
			button_HoraDesde.setText(new StringBuilder().append(pad(hour))
					.append(":").append(pad(minute)));
 
			// set current time into timepicker
			//timePicker1.setCurrentHour(hour);
			//timePicker1.setCurrentMinute(minute);
 
		}
	};
	private TimePickerDialog.OnTimeSetListener TotimePickerListener = 
            new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;
 
			// set current time into textview
			button_HoraHasta.setText(new StringBuilder().append(pad(hour))
					.append(":").append(pad(minute)));
 
			// set current time into timepicker
			//timePicker1.setCurrentHour(hour);
			//timePicker1.setCurrentMinute(minute);
 
		}
	};
	public void doImprimirInformeActas()
	{
		if (lstItems==null || lstItems.size()==0) // por lo general siempre hay una acta en al lista de items-..
		{
			UIHelper.showAlertOnGuiThread(this, "No hay Actas para Sincronizar", "Sin Actas", null);
			return;
		}
		else
		{
		InformeActa informeActa = lstItems.get(0);
			if (informeActa.getNumeroReferencia()==null || informeActa.getNumeroReferencia() =="-1")
			{
				UIHelper.showAlertOnGuiThread(this, "No hay Actas para Informar", "Policia Caminera", null);
				return;
			}
		}
			
		
		
		UIHelper.showAlertCancelable(this, "Desea Imprimir?", "Policia Caminera", new Runnable() {
			
			@Override
			public void run() {
				final InformeActa[] InformeActasConstatacion = new InformeActa[lstItems.size()];
				lstItems.toArray(InformeActasConstatacion);
				final String sFechaDesde = button_FechaDesde.getText().toString() + " " + button_HoraDesde.getText().toString();
				final String sFechaHasta = button_FechaHasta.getText().toString() + " " + button_HoraHasta.getText().toString();
				 
				 

				
				new ZebraPrinterTask<Object, Object, Object>(InformeActasSearchableActivity.this)
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
		        		   PrintInformeActaHelper printActaHelper =  new PrintInformeActaHelper(this.printerConnection, this.printer);
		        		   printActaHelper.printCreateInformeActaCPCL(sFechaDesde,sFechaHasta ,InformeActasConstatacion);
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
		         		  UIHelper.showErrorOnGuiThread(InformeActasSearchableActivity.this, "No se pudo conectar con la Impresora\nVerifique que la Impresora este Encendida", null);
		         	  else
		         	  {
		         		  UIHelper.showErrorOnGuiThread(InformeActasSearchableActivity.this, e.getLocalizedMessage(), null);
		         	  }         
		         			  //QuizCreateActaActivity.this.finish();
		           }
		           
		           @Override
		           public void onPreExecute()
		           {
		   			WorkingInPrintAsyncTask = true;
		   			pleaseWaitDialog = ProgressDialog.show(InformeActasSearchableActivity.this,"Impresion de Informe de Actas", "Iniciando conexion con Impresora", true, false);
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
				
				//loadViewTask = new LoadViewTask(actasConstatacions);    
			    //loadViewTask.execute();
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
            progressDialog = new ProgressDialog(InformeActasSearchableActivity.this);  
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
                        	   ActaConstatacionRules actaRules = new ActaConstatacionRules(InformeActasSearchableActivity.this);
                        	   actaRules.marcarActaSincronizada(currentActa.getIdActaConstatacion());
                               //actaServices.actaDelete(acta.Id_Acta_Constatacion);
                               //Si se elimina le cambio el estado a "Transmitida" en la table T_INFORMES_ACTAS
                        	   
                        	   InformeActaRules informeActaRules = new InformeActaRules(InformeActasSearchableActivity.this);
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
           UIHelper.showAlertOnGuiThread(InformeActasSearchableActivity.this, "Cantidad de Actas Sincronizadas " + cantidadTareasSincronizadas + "/" + cantidadTareasProcesadas, "Sincronizacion Finalizada", null);
            
        }  
    }  

	
}
