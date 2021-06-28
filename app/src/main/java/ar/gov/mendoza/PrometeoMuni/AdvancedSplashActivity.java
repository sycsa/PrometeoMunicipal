package ar.gov.mendoza.PrometeoMuni;


//RESTAURAR2016 import ar.gov.mendoza.deviceactas.R;
import android.app.Activity;
import android.app.ProgressDialog;
        import android.content.Context;
import android.content.Intent;
        import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
        import android.provider.Settings;
        import android.provider.Settings.SettingNotFoundException;
        import android.view.MotionEvent;
        import android.widget.ImageView;

import ar.gov.mendoza.PrometeoMuni.core.util.Tools;
        import ar.gov.mendoza.PrometeoMuni.dao.SeccionalDao;
        import ar.gov.mendoza.PrometeoMuni.db.DatabaseConnection;
        import ar.gov.mendoza.PrometeoMuni.services.ActualizacionService;
import ar.gov.mendoza.PrometeoMuni.services.RadarService;
import ar.gov.mendoza.PrometeoMuni.services.SetupService;
import ar.gov.mendoza.PrometeoMuni.settings.AirplaneModeService;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;
        import ar.gov.mendoza.PrometeoMuni.utils.Utilities;

        import android.util.Log;

public class AdvancedSplashActivity extends Activity {


	
private void adjustAirplaneModeOff()
{
     AirplaneModeService airPlaneOff = new AirplaneModeService();
     try
     {
     airPlaneOff.run(this);
     }
     catch(Exception ex)
     {
    	 String sMessage = ex.getMessage();
    	 sMessage = "";
    	 
     }
	
}
private void adjustBright() throws SettingNotFoundException {
   
    int brightnessMode = Settings.System.getInt(getContentResolver(),
            Settings.System.SCREEN_BRIGHTNESS_MODE);
    if (brightnessMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
        Settings.System.putInt(getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }
    
    
    int brightpref = 255/3;
    try
    {
    	brightpref = GlobalVar.getInstance().getSuportTable().getBrilloPantalla();
    }
    catch(Exception e)
    {
    	
    }
    
    Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightpref);
//    WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//    layoutParams.screenBrightness = 0.5F;
//    getWindow().setAttributes(layoutParams);
}
	
    /**
     * The thread to process splash screen events
     */
    private Thread mSplashThread;  
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advanced_splash);
        

        
        
        // Start animating the image
	    final ImageView splashImageView = findViewById(R.id.SplashImageView);
	    splashImageView.setBackgroundResource(R.drawable.flag);
	    final AnimationDrawable frameAnimation = (AnimationDrawable)splashImageView.getBackground();
	    splashImageView.post(new Runnable(){
			@Override
			public void run() {
				frameAnimation.start();				
			}	    	
	    });
	    
        final AdvancedSplashActivity sPlashScreen = this;   
        mSplashThread =  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this) {

                    	
                 		Context ctx = getApplicationContext();
      
                 		
                 		String mDatabasePath = getApplicationContext().getApplicationInfo().dataDir + "/databases";
                		String mName = "";
                		java.io.File file = new java.io.File (mDatabasePath + "/" + mName);

                		GlobalStateApp.mDatabaseConnection= new DatabaseConnection(getApplicationContext());
            	    	GlobalStateApp.mDatabaseConnection.open();

            	    	try
            	    	{
	            	    	SQLiteDatabase dbTemporal = GlobalStateApp.mDatabaseConnection.getDatabase();
	            			try
	            			{
	            				new SeccionalDao().createTable(dbTemporal);
	            			}catch(Exception ex)
	            			{
	            				Log.d("SPLASH", ex.getMessage());
	            			}
	            			/*try
	            			{
	                			dbTemporal.execSQL("ALTER TABLE T_SECCIONAL ADD ID_ENTIDAD INTEGER;");
	            			}catch(Exception ex)
	            			{
	            				Log.d("SPLASH", ex.getMessage());
	            			}
	            			
	            			try
	            			{
	                			dbTemporal.execSQL("ALTER TABLE T_ACTA_CONSTATACION ADD ANIO INTEGER;");
	            			}catch(Exception ex)
	            			{
	            				Log.d("SPLASH", ex.getMessage());
	            			}
	            			
	            			try
	            			{
	                			dbTemporal.execSQL("ALTER TABLE SUPORT_TABLE ADD USAR_WEBSERVICE_POLICIA TEXT;");
	                			dbTemporal.execSQL("ALTER TABLE SUPORT_TABLE ADD WEBSERVICE_POLICIA TEXT;");
	            			}catch(Exception ex)
	            			{
	            				Log.d("SPLASH", ex.getMessage());
	            			}

	            			try
	            			{
	            				//dbTemporal.execSQL("UPDATE SUPORT_TABLE SET CALIDAD_FOTO = 100;");
	                			dbTemporal.execSQL("ALTER TABLE SUPORT_TABLE ADD CALIDAD_FOTO INTEGER;");
	                			dbTemporal.execSQL("UPDATE SUPORT_TABLE SET CALIDAD_FOTO = 50;");
	                		}catch(Exception ex)
	            			{
	            				Log.d("SPLASH", ex.getMessage());
	            			}
	            			//
	            			//;
	            			try
	            			{
	                			dbTemporal.execSQL("ALTER TABLE T_DOMINIOS ADD ANIO INTEGER;");
	            			}catch(Exception ex)
	            			{
	            				Log.d("SPLASH", ex.getMessage());
	            			}
	            			try
	            			{
	                			dbTemporal.execSQL("INSERT INTO T_SECCIONAL (_id,NOMBRE,DESCRIPCION,ID_ENTIDAD)values(1,'1ra Seccional - Luj�n, Godoy Cruz','1ra Seccional - Luj�n, Godoy Cruz',700);");
	            			}catch(Exception ex)
	            			{
	            				Log.d("SPLASH", ex.getMessage());
	            			}

	            			try
	            			{
	                			dbTemporal.execSQL("INSERT INTO T_FECHAS_CONFIGURADAS (FECHA,FECHA_STRING,TIPO,DESCRIPCION) VALUES (20180521,'21/05/2018','FERIADO','FERIADO DE PRUEBA');");
	            			}catch(Exception ex)
	            			{
	            				Log.d("SPLASH", ex.getMessage());
	            			}

	            			
	            			try
	            			{
		            			dbTemporal.execSQL("ALTER TABLE T_ACTA_CONSTATACION ADD DEPARTAMENTO_INFRACCION TEXT");
		            			dbTemporal.execSQL("ALTER TABLE T_ACTA_CONSTATACION ADD ID_DEPARTAMENTO_INFRACCION TEXT");
		            			dbTemporal.execSQL("ALTER TABLE T_ACTA_CONSTATACION ADD ID_SECCIONAL TEXT");
		            			dbTemporal.execSQL("ALTER TABLE T_ACTA_CONSTATACION ADD DEJA_COPIA TEXT");
		            			dbTemporal.execSQL("ALTER TABLE T_ACTA_CONSTATACION ADD LOCALIDAD_INFRACCION TEXT");
		            			dbTemporal.execSQL("ALTER TABLE T_ACTA_CONSTATACION ADD ID_LOCALIDAD_INFRACCION TEXT");
	            			
	            			
	            			}catch(Exception ex)
	            			{
	            				Log.d("SPLASH", ex.getMessage());
	            			}*/

	            			
            	    	}catch(Exception ex)
            	    	{
            	    		Log.d("SPLASH", ex.getMessage());
            	    	}
            			
            	    	
            			try
            			{		String sLatitud = GlobalVar.getInstance().getLatitud().toString();
            					String sLongitud = GlobalVar.getInstance().getLongitud().toString();
            				//	Utilities.ShowToast(AdvancedSplashActivity.this, "(1) Latidud|Longitud:" + sLatitud + "|" + sLongitud );
            					
            					double dLatitud = GlobalVar.getInstance().getLatitud();
            					double dLongitud = GlobalVar.getInstance().getLongitud();
            					sLatitud = Tools.DecimalFormat(dLatitud,"0.0000000");
            					sLongitud = Tools.DecimalFormat(dLongitud,"0.0000000");
            				//	Utilities.ShowToast(AdvancedSplashActivity.this, "(2) Latidud|Longitud:" + sLatitud + "|" + sLongitud );
            					
            			} catch(Exception ex)
            			{
            				Log.d("SPLASH", ex.getMessage());
            			}
            			//SQLiteDatabase dbOtraTemporal = GlobalStateApp.mDatabaseConnection.getDatabase();
            			/*
            			 String pValue = "ff�";
            					pValue  = android.text.Html.escapeHtml(pValue);
            					Log.d("valor traducido de � ", pValue);
            			*/		
            	    	/*
            	    	try
            			{
            				SuportTableDao suportTableDao = new SuportTableDao();
            				ContentValues values = new ContentValues();
            				values.put("WSLOCATION","http://desa.serviciosyconsultoria.com/PDAWebService/servicio/");
            				suportTableDao.update("1", values);
            				
            			}
            			catch(Exception ex)
            			{} 
            	    	*/
                 		Utilities.ConfigGlobalVars(ctx);
                 		
                 		/* Servicio Asincronos */
                 		startService(new Intent(getBaseContext(), SetupService.class));
                 		
                 		startService(new Intent(getBaseContext(), RadarService.class));
                 		
                 		startService(new Intent(getBaseContext(), ActualizacionService.class));
                 		
                 		
                 		/*  Probaremos */
                 		
                 		
                 		
                 		try {
                            adjustBright();
                        } catch (SettingNotFoundException e) {
                        }
                 		 catch (Exception e) {
						}
                 		
                    }
                }
                catch(Exception ex){ 
                	String sError = ex.getMessage();
                	sError = sError + "";  
                }

                finish();

                // Run next activity
                Intent intent = new Intent();
                intent.setClass(sPlashScreen,LoginActivity.class); 
                startActivity(intent);
                //stop();          
                Thread.currentThread().interrupt();
            }
        };
        
        
        
        
        
        
        mSplashThread.start();    
        startAnimating();
    }
    
    /**
     * Processes splash screen touch events
     */
    @Override
    public boolean onTouchEvent(MotionEvent evt)
    {
        if(evt.getAction() == MotionEvent.ACTION_DOWN)
        {
            synchronized(mSplashThread){
                mSplashThread.notifyAll();
            }
        }
       return true;
    }

    /**
     * Helper method to start the animation on the splash screen
     */
    private void startAnimating() {
       
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop the animation
       
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Start animating at the beginning so we get the full splash screen experience
        //startAnimating();
    }



 // Create an AsyncTask to copy the database in a background thread while
 // displaying a ProgressDialog.
 private class LoadDatabaseTask extends AsyncTask<Context, Void, Void> {
     Context mContext;
     ProgressDialog mDialog;

     // Provide a constructor so we can get a Context to use to create
     // the ProgressDialog.
     void LoadDatabasesTask(Context context) {
        // super();
         mContext = context;
     }

     @Override
     protected void onPreExecute() {
         super.onPreExecute();
         mDialog = new ProgressDialog(mContext);
         mDialog.setMessage("Inicializando la Base de Datos");//mContext.getString("Loading database...")
         mDialog.show();
     }

     @Override
     protected Void doInBackground(Context... contexts) {
         // Copy database.
    	 
    	// REVISAR DE DONDE SE EJECUTA PRIMER LA LECTURA DE LA BASE DE DATOS PARA VER QUE PONER AQUI
        // new MyAssetHelper(contexts[0]).getReadableDatabase();
         return null;
     }

     @Override
     protected void onPostExecute(Void result) {
         super.onPostExecute(result);
         mDialog.dismiss();
         
         
     }
 }
}
