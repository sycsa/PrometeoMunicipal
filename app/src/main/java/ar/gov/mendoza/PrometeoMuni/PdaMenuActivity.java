package ar.gov.mendoza.PrometeoMuni;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

import com.zebra.android.zebrautilitiesmza.ZebraUtilitiesApplication;
import com.zebra.android.zebrautilitiesmza.screens.chooseprinter.ChoosePrinterController;
import com.zebra.android.zebrautilitiesmza.util.ImageHelper;
import com.zebra.android.zebrautilitiesmza.util.UIHelper;
import com.zebra.android.zebrautilitiesmza.util.ZebraPrinterTask;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.device.ZebraIllegalArgumentException;

import ar.gov.mendoza.PrometeoMuni.core.domain.ActaConstatacion;
import ar.gov.mendoza.PrometeoMuni.core.util.Tools;
import ar.gov.mendoza.PrometeoMuni.rules.ActaConstatacionRules;
import ar.gov.mendoza.PrometeoMuni.rules.DeviceActasRules;
import ar.gov.mendoza.PrometeoMuni.rules.InformeActaRules;
import ar.gov.mendoza.PrometeoMuni.screens.actas.CreateActaHelper;
import ar.gov.mendoza.PrometeoMuni.screens.actas.VerificacionActaActivity;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;
import ar.gov.mendoza.PrometeoMuni.sync.ActaConstatacionSync;
import ar.gov.mendoza.PrometeoMuni.sync.DeviceActasSync;
import ar.gov.mendoza.PrometeoMuni.sync.dto.ActualizacionDTO;
import ar.gov.mendoza.PrometeoMuni.utils.Utilities;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PdaMenuActivity extends MendozaActivity {
	

	
	static String SIMPLE_DB_NAME = "DeviceActas.db";
	
	
	
	LoadViewTask loadViewTask;
	LoadActualizacionesTask actualizacionTask;
	BackupDatabaseTask backupDatabaseTask;
    ProgressDialog pleaseWaitDialog;
    private Boolean WorkingInPrintAsyncTask = false;
	protected ProgressDialog progressDialog;
	
	//handler for Broadcast
	BroadcastReceiver mBroadcaster;
	private IntentFilter receiveFilter;
	
	 @Override
	 protected void onResume() 
	 {
	        super.onResume();
	        registerReceiver(mBroadcaster,receiveFilter);
	 }
	 @Override 
	 protected void onPause()
	 {
	        super.onPause();
	        unregisterReceiver(mBroadcaster);
	 }   
	
	 
	 


	private static final String TAG = "PdaMenuActivity";
	
	static final int SEARCH_ACTA = 1;
	static final int SEARCH_INFORME_ACTA = 2;
	/** Reference of SomeExpensiveObject.  This object is expensive to create. */
//	 private CacheObjectForMenu cacheObjectForMenu;
//	 
//	 @Override
//	    public Object onRetainCustomNonConfigurationInstance() {
//	    	return cacheObjectForMenu;
//	    }
//	    /**
//	     *  The method that creates an instance of SomeExpensiveObject class.
//	     */
//	    public CacheObjectForMenu doSomeExpensiveOperation() {
//	     return new CacheObjectForMenu();
//	    }
	    
	    
		/* Initialize ListActas*/
//		 private ListView myActasListView;
//		 private GenericListAdapter<ActaConstatacion> myActasAdapter;	    

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Tools.Log(Log.ERROR, TAG, "onActivityResult");
		String sTag ="";
		
		ConfigTextLabels();
		
		switch (requestCode) {
		case SEARCH_ACTA:
			if (resultCode == Activity.RESULT_CANCELED) {
				// Search person mode was canceled.
				//Utilities.ShowToast(QuizMenuActivity.this, "Busqueda de Infraccion Cancelada");
			} else if (resultCode == Activity.RESULT_OK) {
			
				Bundle appDataReturned = data.getBundleExtra(SearchManager.APP_DATA);
				
				ActaConstatacion acta = (ActaConstatacion) appDataReturned.getSerializable("ItemObject");
				
				acta = Utilities.CargarInformacionAdicional(acta);
				doPrintActaRequest(acta);
			}
		default:
		}
	}
	
	private void doPrintActaRequest(final ActaConstatacion pActa) {
   	 new ZebraPrinterTask<Object, Object, Object>(PdaMenuActivity.this)
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
	             {
	               CreateActaHelper.printCreateActaCPCL(pActa,this.printer);
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
            UIHelper.showErrorOnGuiThread(PdaMenuActivity.this, e.getLocalizedMessage(), null);
          }
     

          @Override
          public void onPreExecute()
          {
  			WorkingInPrintAsyncTask = true;
  			pleaseWaitDialog = ProgressDialog.show(PdaMenuActivity.this,"Impresion de Acta", "Iniciando conexion con Impresora", true, false);
       	super.onPreExecute();
          }
          @Override
          public void onPostExecute(Object object)
          {
       		WorkingInPrintAsyncTask = false; 
   			super.onPostExecute(object);
   			
   			pleaseWaitDialog.dismiss();
   			pleaseWaitDialog = null;
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
	private void setText(int pId, CharSequence pTexto)
	{
		Object control = findViewById(pId);
		if (control!=null && control instanceof TextView ) {
			
			((TextView) control).setText(pTexto);
		}
		
	}
	
	private void setVisibility(int pId, boolean pVisible)
	{
		Object control = findViewById(pId);
		if (control!=null) {
			
			if(pVisible==true)
				((TextView) control).setVisibility(View.VISIBLE);
			else
				((TextView) control).setVisibility(View.GONE);
		}
		
	}
	
	private void ConfigTextLabels()
	{
		try
		{
			
	    	String sNumeroSerie = GlobalVar.getInstance().getNumeroSerie();
			sNumeroSerie = Tools.formatearIzquierda(sNumeroSerie, 4, '0');
			
	        setText(R.id.TextView_Numero_Serie, "Dispositivo : " + sNumeroSerie);
	        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);
	        String ImpresoraConfigurada = "Impresora : " + sharedPref.getString("CURRENTLY_SELECTED_PRINTER", "(Ninguna)");
	        setText(R.id.TextView_ImpresoraConfigurada, ImpresoraConfigurada);
	        String sPrecioNafta = "Valor UF : " + Tools.DecimalFormat(GlobalVar.getInstance().getSuportTable().getImporteNP());
	        setText(R.id.TextView_PrecioNafta,sPrecioNafta);
	        String sImsi = GlobalVar.getInstance().getImsi();
	        setText(R.id.TextView_Imsi,"Chip : " + (sImsi==null?"No":sImsi));
	        String sCoordenadas = "Lat." + Tools.DecimalFormat(GlobalVar.getInstance().getLatitud(),"#.0000") + " Lon." + Tools.DecimalFormat(GlobalVar.getInstance().getLongitud(),"#.0000") + " Prov." + GlobalVar.getInstance().getProvider(); 
	        setText(R.id.TextView_Coordenadas,sCoordenadas);
	        
	        String sZona = GlobalVar.getInstance().getSuportTable().getIdZona();
	        if (sZona!=null)
	        {
	        	setText(R.id.TextView_ZonaConfigurada,"Zona : "+ sZona );
	        	setVisibility(R.id.TextView_ZonaConfigurada, true);
	        }
	        else
	        	setVisibility(R.id.TextView_ZonaConfigurada, false);
		}
		catch(Exception ex)
		{
			String message = ex.getMessage();
			message ="";
		}
	}
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
//        cacheObjectForMenu = (CacheObjectForMenu) getLastCustomNonConfigurationInstance();
//        if(cacheObjectForMenu == null) {
//        	cacheObjectForMenu = doSomeExpensiveOperation();  
//        }
        setContentView(R.layout.menu);
        ConfigTextLabels();
        
        
        ListView menuList = findViewById(R.id.ListView_Menu);
        //myActasListView = (ListView) findViewById(R.id.ListView_Actas);
        
//        final ActaConstatacion[] itemsA = new ActaConstatacion[cacheObjectForMenu.lstActasConstatacion.size()];
//        cacheObjectForMenu.lstActasConstatacion.toArray(itemsA);
//		myActasAdapter = new GenericListAdapter<ActaConstatacion>(this,android.R.layout.simple_spinner_item,itemsA);
//		myActasListView.setAdapter(myActasAdapter);
        
		
        String[] items = { getResources().getString(R.string.menu_item_play),
              /*getResources().getString(R.string.menu_item_scores),*/
        		/*
                getResources().getString(R.string.menu_item_settings),*/
                getResources().getString(R.string.menu_item_listado_actas),
                /*getResources().getString(R.string.menu_item_help) ,*/
                getResources().getString(R.string.menu_item_verificacion_actas),
                getResources().getString(R.string.menu_item_configurar_impresora),
                getResources().getString(R.string.menu_item_sincronizar_acta),
                getResources().getString(R.string.menu_item_informe_acta),
                //getResources().getString(R.string.menu_item_backup_database),
                //getResources().getString(R.string.menu_item_actualizar_parametros),
                /*
                getResources().getString(R.string.menu_item_list_paises) ,
                getResources().getString(R.string.menu_item_list_provincias) ,
                getResources().getString(R.string.menu_item_list_departamentos) ,
                getResources().getString(R.string.menu_item_list_localidades) ,*/
                };
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, R.layout.menu_item, items);
        menuList.setAdapter(adapt);


        
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {

                // Note: if the list was built "by hand" the id could be used.
                // As-is, though, each item has the same id

                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString();

                if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_configurar_impresora))) {
                    // Launch the Printer Config
                	
                  //startActivity(new Intent(QuizMenuActivity.this, QuizCreateActaActivity.class));
                    
          	      Intent localIntent = new Intent(PdaMenuActivity.this, ChoosePrinterController.class);
        	      //startActivity(localIntent);
        	      startActivityForResult(localIntent, 0);
        	      
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_play))) {
                    // Launch the Game Activity
                	//startService(new Intent(getBaseContext(), RadarService.class));
                    startActivity(new Intent(PdaMenuActivity.this, CreateActaActivity.class));//GameActivity.class));
                    
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_help))) {
                    // Launch the Help Activity
                    startActivity(new Intent(PdaMenuActivity.this, HelpActivity.class));
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_verificacion_actas))) {
                    // Launch the Settings Activity
                	
                    startActivity(new Intent(PdaMenuActivity.this, VerificacionActaActivity.class));
                    
                } /*
                	else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_verificacion_policial))) {
                    startActivity(new Intent(QuizMenuActivity.this, VerificacionPolicialActivity.class));
                }*/ else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_actualizar_parametros))) {
                    // ACTUALIZAR EL DISPOSITIVO
                	
                    doActualizarParametros();
                	
                
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_sincronizar_acta))) {
                    // SINCRONIZAR ACTAS
                	 doSincronizarActas();
                    
                    
                   
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_settings))) {
                    // Launch the Settings Activity
                    startActivity(new Intent(PdaMenuActivity.this, SettingsActivity.class));
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_scores))) {
                    // Launch the Scores Activity
                    startActivity(new Intent(PdaMenuActivity.this, ScoresActivity.class));
                }else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_list_paises))) {
                    
                 Intent intent = new Intent(Intent.ACTION_SEARCH);
   				 Bundle appSearchData = new Bundle();
   				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_OBJETO_BUSQUEDA, "PAIS");
   				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_LABEL_SEARCH, "Busqueda por Nombre");
   				 appSearchData.putInt(PersonaSearchableActivity.APP_DATA_HINT_SEARCH, R.string.searchable_hint_default);
   				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_HEADER_LIST, "Paises");
   				 String initialQuery = "%";//editText_NumeroDocumento.getText().toString();
   				 
   		    	 intent.putExtra(SearchManager.APP_DATA, appSearchData);
   		    	 
   		         if (!TextUtils.isEmpty(initialQuery)) {
   		             intent.putExtra(SearchManager.QUERY, initialQuery);
   		         }
   				 intent.setClass(PdaMenuActivity.this,ListPaisesActivity.class);
                	// Launch the ListPaises Activity
                    //startActivity(new Intent(QuizMenuActivity.this, ListPaisesActivity.class));
   				startActivity(intent);
                    
                    
                }else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_list_provincias))) {
                    // Launch the ListPaises Activity
                    //startActivity(new Intent(QuizMenuActivity.this, ListProvinciasActivity.class));
                    Intent intent = new Intent(Intent.ACTION_SEARCH);
      				 Bundle appSearchData = new Bundle();
      				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_OBJETO_BUSQUEDA, "PROVINCIA");
      				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_LABEL_SEARCH, "Busqueda por Nombre");
      				 appSearchData.putInt(PersonaSearchableActivity.APP_DATA_HINT_SEARCH, R.string.searchable_hint_default);
      				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_HEADER_LIST, "Provincias");
      				 String initialQuery = "%";//editText_NumeroDocumento.getText().toString();
      				 
      		    	 intent.putExtra(SearchManager.APP_DATA, appSearchData);
      		    	 
      		         if (!TextUtils.isEmpty(initialQuery)) {
      		             intent.putExtra(SearchManager.QUERY, initialQuery);
      		         }
      				 intent.setClass(PdaMenuActivity.this,ListProvinciasActivity.class);
                   	// Launch the ListPaises Activity
                       //startActivity(new Intent(QuizMenuActivity.this, ListPaisesActivity.class));
      				startActivity(intent);
                }else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_list_departamentos))) {
                    // Launch the ListPaises Activity
                    //startActivity(new Intent(QuizMenuActivity.this, ListDepartamentosActivity.class));
                	
                    Intent intent = new Intent(Intent.ACTION_SEARCH);
      				 Bundle appSearchData = new Bundle();
      				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_OBJETO_BUSQUEDA, "DEPARTAMENTO");
      				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_LABEL_SEARCH, "Busqueda por Nombre");
      				 appSearchData.putInt(PersonaSearchableActivity.APP_DATA_HINT_SEARCH, R.string.searchable_hint_default);
      				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_HEADER_LIST, "Departamentos");
      				 String initialQuery = "%";//editText_NumeroDocumento.getText().toString();
      				 
      		    	 intent.putExtra(SearchManager.APP_DATA, appSearchData);
      		    	 
      		         if (!TextUtils.isEmpty(initialQuery)) {
      		             intent.putExtra(SearchManager.QUERY, initialQuery);
      		         }
      				 intent.setClass(PdaMenuActivity.this,ListDepartamentosActivity.class);
                   	// Launch the ListPaises Activity
                       //startActivity(new Intent(QuizMenuActivity.this, ListPaisesActivity.class));
      				startActivity(intent);
      				
      				
                }else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_list_localidades))) {
                    // Launch the ListPaises Activity
                    //startActivity(new Intent(QuizMenuActivity.this, ListLocalidadesActivity.class));
                	
                    Intent intent = new Intent(Intent.ACTION_SEARCH);
      				 Bundle appSearchData = new Bundle();
      				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_OBJETO_BUSQUEDA, "LOCALIDAD");
      				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_LABEL_SEARCH, "Busqueda por Nombre");
      				 appSearchData.putInt(PersonaSearchableActivity.APP_DATA_HINT_SEARCH, R.string.searchable_hint_default);
      				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_HEADER_LIST, "Localidades");
      				 String initialQuery = "%";//editText_NumeroDocumento.getText().toString();
      				 
      		    	 intent.putExtra(SearchManager.APP_DATA, appSearchData);
      		    	 
      		         if (!TextUtils.isEmpty(initialQuery)) {
      		             intent.putExtra(SearchManager.QUERY, initialQuery);
      		         }
      				 intent.setClass(PdaMenuActivity.this,ListLocalidadesActivity.class);
                   	// Launch the ListPaises Activity
                       //startActivity(new Intent(QuizMenuActivity.this, ListPaisesActivity.class));
      				startActivity(intent);
                }
                else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_listado_actas))) {
                    // Launch the ListPaises Activity
                    //startActivity(new Intent(QuizMenuActivity.this, ListLocalidadesActivity.class));
                	
                    Intent intent = new Intent(Intent.ACTION_SEARCH);
      				 Bundle appSearchData = new Bundle();
      				 appSearchData.putString(ActasSearchableActivity.APP_DATA_OBJETO_BUSQUEDA, "ACTA_CONSTATACION");
      				 appSearchData.putString(ActasSearchableActivity.APP_DATA_LABEL_SEARCH, "Busqueda por Numero de Acta");
      				 appSearchData.putInt(ActasSearchableActivity.APP_DATA_HINT_SEARCH, R.string.searchable_hint_default);
      				 appSearchData.putString(ActasSearchableActivity.APP_DATA_HEADER_LIST, "Actas de Constatacion");
      				 String initialQuery = "%";//editText_NumeroDocumento.getText().toString();
      				 
      		    	 intent.putExtra(SearchManager.APP_DATA, appSearchData);
      		    	 
      		         if (!TextUtils.isEmpty(initialQuery)) {
      		             intent.putExtra(SearchManager.QUERY, initialQuery);
      		         }
      				 intent.setClass(PdaMenuActivity.this,ActasSearchableActivity.class);
                   	// Launch the ListPaises Activity
                       //startActivity(new Intent(QuizMenuActivity.this, ListPaisesActivity.class));
      				//startActivity(intent);
      				startActivityForResult(intent, PdaMenuActivity.SEARCH_ACTA);
                }
                else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_backup_database))) {
                	Utilities.ShowToast(PdaMenuActivity.this, "Confeccionando Backup de Base de Datos");
                	exportDB();
                }
                else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_informe_acta))) {
                    // Launch the ListPaises Activity
                    //startActivity(new Intent(QuizMenuActivity.this, ListLocalidadesActivity.class));
                	
                    Intent intent = new Intent(Intent.ACTION_SEARCH);
      				 Bundle appSearchData = new Bundle();
      				 appSearchData.putString(ActasSearchableActivity.APP_DATA_OBJETO_BUSQUEDA, "USUARIO");
      				 appSearchData.putString(ActasSearchableActivity.APP_DATA_LABEL_SEARCH, "Busqueda por Usuario");
      				 appSearchData.putInt(ActasSearchableActivity.APP_DATA_HINT_SEARCH, R.string.searchable_hint_default);
      				 appSearchData.putString(ActasSearchableActivity.APP_DATA_HEADER_LIST, "Informe de Actas Realizadas");
      				 String initialQuery = "%";//editText_NumeroDocumento.getText().toString();
      				 
      		    	 intent.putExtra(SearchManager.APP_DATA, appSearchData);
      		    	 
      		         if (!TextUtils.isEmpty(initialQuery)) {
      		             intent.putExtra(SearchManager.QUERY, initialQuery);
      		         }
      				 intent.setClass(PdaMenuActivity.this,InformeActasSearchableActivity.class);
                   	// Launch the ListPaises Activity
                       //startActivity(new Intent(QuizMenuActivity.this, ListPaisesActivity.class));
      				//startActivity(intent);
      				startActivityForResult(intent, PdaMenuActivity.SEARCH_ACTA);
                }
            }
        });
        
        ListUtils.setDynamicHeight(menuList);
       // ListUtils.setDynamicHeight(myActasListView);
    
        receiveFilter = new IntentFilter(ar.gov.mendoza.PrometeoMuni.services.LocationService.BROADCAST_ACTION);
		mBroadcaster = new BroadcastReceiver()
							{
								@Override
								public void onReceive(Context context, Intent intent) 
								{
					
									String AccionRecibida =  intent.getAction();
							    	Bundle bundle = intent.getExtras();
							    	Double Latitud = bundle.getDouble("Latitud");
							    	Double Longitud = bundle.getDouble("Longitud");
							    	String Provider = bundle.getString("Provider");
							    	
							    	Log.w("AccionRecibida",AccionRecibida);
							    	Log.w("Provider",Provider);
							    	Log.w("Latitud",Latitud.toString());
							    	Log.w("Longitud",Longitud.toString());
							    	
							    	ConfigTextLabels();
//							    	textLatitud.setText(Latitud.toString());
//							    	textLongitud.setText(Longitud.toString());
//							    	textProvider.setText(Provider);
							    	
								}
								
							};	
    
    }
    
    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = MeasureSpec.makeMeasureSpec(mListView.getWidth(), MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }
    public void doActualizarParametros()
   	{
   	    
    	
    	//UIHelper.showAlertOnGuiThread(this, "No Implementada Todavia", "Actualizacion", null);
    	if (actualizacionTask == null || actualizacionTask.getStatus() == AsyncTask.Status.FINISHED || actualizacionTask.isCancelled()) 
    	{
         actualizacionTask = new LoadActualizacionesTask();    
    	 actualizacionTask.execute();
    	}
   	}
    public void doSincronizarActas()
	{
    	// para no permitir ejecutar mas de una vez ants de que termine la solicitud anterior
    	if (loadViewTask == null || loadViewTask.getStatus() == AsyncTask.Status.FINISHED || loadViewTask.isCancelled()) 
    	{
    	
		    	ActaConstatacionRules actasRules = new  ActaConstatacionRules(this);
		    	
		    	final List<ActaConstatacion> lstItems = actasRules.getActasParaSincronizar();
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
            progressDialog = new ProgressDialog(PdaMenuActivity.this);  
            //Set the progress dialog to display a horizontal progress bar  
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
            //Set the dialog title to 'Loading...'  
            progressDialog.setTitle("Sincronizando Actas...");  
            //Set the dialog message to 'Loading application View, please wait...'  
            progressDialog.setMessage("Sincronizando Actas, Espere un momento por favor...");  
            //This dialog can't be canceled by pressing the back key  
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancelar o Posponer", new DialogInterface.OnClickListener() 
            {
                public void onClick(DialogInterface dialog, int which) 
                {
                	Toast.makeText(PdaMenuActivity.this, "Cancelando Operaciones de sincronizacion de Actas", Toast.LENGTH_LONG).show();
                  	loadViewTask.cancel(true);
                    return;
                }
            });	
            progressDialog.setOnCancelListener(new OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    // TODO Auto-generated method stub
                    Toast.makeText(PdaMenuActivity.this, "Cancelando Operaciones de sincronizacion de Actas", Toast.LENGTH_LONG).show();
                	loadViewTask.cancel(true);
                }
            });

            
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
                    	
                       if( isCancelled())
                       {
                          //Log.d(LOG_TAG, "doInBackGround. Task cancelled");
                          return null;
                       }
                       ActaConstatacionSync actaSync = new ActaConstatacionSync(GlobalStateApp.getInstance().getApplicationContext());
                       String sMensaje;
                       String sNumeroActa= currentActa.getNumeroActa();
                       sMensaje  = "Iniciando Proceso de Acta Numero " + sNumeroActa  + " (" + Tools.DateValueOf( Tools.Today(),"dd/MM/yyyy HH:mm:ss") + ")";
                       boolean sincronizada = actaSync.sincronizarActa(currentActa);
                       if (sincronizada==true)// de false es temporal soolo para poder envir la foto
                       {
                    	   sMensaje  ="El Acta Numero " + sNumeroActa  + " ha sido sincronizada correctamente!";
                    	   //procesar las imagenes
                    	   //si se graban las imagenes, 
                    	   //poner el acta como sincronizada
                    	   String numeroActa = currentActa.getNumeroActa();
                    	  /*NO MANDAREMOS FOTOS EN ESTA VERSION */
                    	   
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
				                    		   String namefoto = fileFoto.getName();
				                    		   if(actaSync.sincronizarFotoActa(numeroActa,namefoto,bitmap)==true)
					                    	   {
				                    			   icantidadFotosProcesadas++;
					                    		   sMensaje  =sMensaje + "\n Foto Procesada (" + (j + 1) + ") " + foto ;
					                    		   
					                    	   }
				                    	   }
			                    	   }
			                    	   else
			                    	   {
			                    		   //si no se puede leer o no existe  es por que paso algo en la PDA de todas maneras queremos que se sincronize el acta
			                    		   icantidadFotosProcesadas++;
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
                        	   ActaConstatacionRules actaRules = new ActaConstatacionRules(PdaMenuActivity.this);
                        	   actaRules.marcarActaSincronizada(currentActa.getIdActaConstatacion());
                               //actaServices.actaDelete(acta.Id_Acta_Constatacion);
                               //Si se elimina le cambio el estado a "Transmitida" en la table T_INFORMES_ACTAS
                        	   
                        	   InformeActaRules informeActaRules = new InformeActaRules(PdaMenuActivity.this);
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
                         	  if(xFotos!=null)
                         	  {
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
           UIHelper.showAlertOnGuiThread(PdaMenuActivity.this, "Cantidad de Actas Sincronizadas " + cantidadTareasSincronizadas + "/" + cantidadTareasProcesadas, "Sincronizacion Finalizada", null);
            
        }  
    
        /**
         * This method is called when the AsyncTask is canceled. When this method
         * is called, controls are reset to be used again. 
         */
        @Override
        protected void onCancelled(){
          //Log.d(LOG_TAG, "onCancelled");
          
          super.onCancelled();   
          //pbM.setVisibility( View.INVISIBLE);
          //teSecondsProgressedM.setVisibility( View.INVISIBLE);
          //teSecondsProgressedM.setText("");
          //pbM.setProgress(0);
        }
    
    }  

    private class BackupDatabaseTask extends AsyncTask<Void, Object, Void>  
    {
    	
        //Before running code in separate thread  
        @Override  
        protected void onPreExecute()  
        {  
            progressDialog = new ProgressDialog(PdaMenuActivity.this);  
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setProgressNumberFormat(null);
            progressDialog.setProgressPercentFormat(null);
            progressDialog.setTitle("GenerandoBackup Dispositivo...");  
            progressDialog.setMessage("Procesando Pedido de Backup, Espere un momento por favor...");  
            progressDialog.setCancelable(false);  
            progressDialog.setIndeterminate(true);//antes era false  
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
                	File sd =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS); //Environment.getExternalStorageDirectory();
                  	File data = Environment.getDataDirectory();
                   FileChannel source=null;
                   FileChannel destination=null;
                   String currentDBPath = "/data/"+ "ar.gov.mendoza.deviceactas" +"/databases/" + SIMPLE_DB_NAME;
                   String backupDBPath = SIMPLE_DB_NAME;
                   File currentDB = new File(data, currentDBPath);
                   File backupDB = new File(sd, backupDBPath);
                   try {
                        source = new FileInputStream(currentDB).getChannel();
                        destination = new FileOutputStream(backupDB).getChannel();
                        destination.transferFrom(source, 0, source.size());
                        
                        source.close();
                        destination.close();
                    } catch(IOException e) {
                    	e.printStackTrace();
                    }

                }  
            }  
            catch (Exception e)  
            {  
                //e.printStackTrace();  
            }  
            return null;  
        }
        
        //Update the progress  
        @Override  
        protected void onProgressUpdate(Object... values)  
        {  
            //set the current progress of the progress dialog  
           // progressDialog.setProgress((Integer) values[0]);  
            progressDialog.setMessage("Procesando Pedido, Espere un momento por favor...\n" + values[1].toString());  
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
            UIHelper.showAlertOnGuiThread(PdaMenuActivity.this, "backup realizado correctamente " , "Backup Finalizada", null);
            
        }  

        
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
            //Create a new progress dialog  
            progressDialog = new ProgressDialog(PdaMenuActivity.this);  
            //Set the progress dialog to display a horizontal progress bar  
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setProgressNumberFormat(null);
            progressDialog.setProgressPercentFormat(null);
            //Set the dialog title to 'Loading...'  
            progressDialog.setTitle("Actualizando Dispositivo...");  
            //Set the dialog message to 'Loading application View, please wait...'  
            progressDialog.setMessage("Buscando Actualizaciones, Espere un momento por favor...");  
            //This dialog can't be canceled by pressing the back key  
            progressDialog.setCancelable(false);  
            //This dialog isn't indeterminate  
            progressDialog.setIndeterminate(true);//antes era false  
            //The maximum number of items is 100  
            //progressDialog.setMax(cantidadTareas);  
            //Set the current progress to zero  
            //progressDialog.setProgress(0);  
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

                    DeviceActasSync actualizacionSync = new DeviceActasSync(GlobalStateApp.getInstance().getApplicationContext());
                    
                    String sMensaje ="";
                    sMensaje  ="Buscando Actualizaciones ...";

                    List<ActualizacionDTO> lst = actualizacionSync.BuscarActualizaciones("SQL");
                    aActualizacionDTO = new ActualizacionDTO[lst.size()];
                    lst.toArray(aActualizacionDTO);
                    cantidadTareas = aActualizacionDTO.length;
                    if (cantidadTareas==0)
                    {
                    	publishProgress(cantidadTareasProcesadas,sMensaje);
                    	 this.wait(850); 
                    }
                    
                    DeviceActasRules deviceActaRules = new DeviceActasRules(GlobalStateApp.getInstance().getApplicationContext());
                    
                    while(cantidadTareasProcesadas < cantidadTareas)  
                    {  
                       ActualizacionDTO currentActualizacion = aActualizacionDTO[cantidadTareasProcesadas];

                    	   sMensaje  ="Procesando Actualizacion Nro: " + (cantidadTareasProcesadas+1);
                    	   publishProgress(cantidadTareasProcesadas,sMensaje);
                          
                           
                           // aplicaciones Ejecutadas en el dispositivo
                    	   if (deviceActaRules.aplicarActualizacion(currentActualizacion)==true)
                           {
                        	   cantidadTareasAplicadas++;
                           }
                           

                           //Wait 850 milliseconds  
                           this.wait(850);  
                           //Increment the counter  
                           cantidadTareasProcesadas++;  
                           //Set the current progress.  
                           //This value is going to be passed to the onProgressUpdate() method.  
                             
                    }  
                    this.wait(850); 
                }  
            }  
            catch (InterruptedException e)  
            {  
                //e.printStackTrace();  
            }  
            return null;  
        }  
  
        //Update the progress  
        @Override  
        protected void onProgressUpdate(Object... values)  
        {  
            //set the current progress of the progress dialog  
           // progressDialog.setProgress((Integer) values[0]);  
            progressDialog.setMessage("Actualizando Dispositivo, Espere un momento por favor...\n" + values[1].toString());  
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
            try
            {
            Utilities.ConfigGlobalVars(PdaMenuActivity.this);
            ConfigTextLabels();
            }
            catch(Exception ex)
            {
            	
            }
            UIHelper.showAlertOnGuiThread(PdaMenuActivity.this, "Actualizaciones realizadas correctamente " + cantidadTareasAplicadas + "/" + cantidadTareas, "Actualizacion Finalizada", null);
            
        }  
    }  

    private void exportDB(){
    	
       	if (backupDatabaseTask == null || backupDatabaseTask.getStatus() == AsyncTask.Status.FINISHED || backupDatabaseTask.isCancelled()) 
    	{
       		backupDatabaseTask = new BackupDatabaseTask();    
       		backupDatabaseTask.execute();
    	}
    	
    }

}