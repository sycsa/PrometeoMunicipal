package ar.gov.mendoza.PrometeoMuni;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.zebra.android.zebrautilitiesmza.util.UIHelper;
import com.zebra.android.zebrautilitiesmza.util.ZebraPrinterTask;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.device.ZebraIllegalArgumentException;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import ar.gov.mendoza.PrometeoMuni.core.domain.Usuario;
import ar.gov.mendoza.PrometeoMuni.core.exceptions.DeviceActasOrmException;
//import ar.gov.mendoza.deviceactas.core.providers.UsuarioProvider;
import ar.gov.mendoza.PrometeoMuni.core.rules.UsuarioRules;
import ar.gov.mendoza.PrometeoMuni.core.util.Tools;
import ar.gov.mendoza.PrometeoMuni.libraries.SoporteSistemas;
import ar.gov.mendoza.PrometeoMuni.rules.*;
import ar.gov.mendoza.PrometeoMuni.screens.actas.CreateActaHelper;
import ar.gov.mendoza.PrometeoMuni.services.LocationService;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;
import ar.gov.mendoza.PrometeoMuni.sync.ActaConstatacionSync;
import ar.gov.mendoza.PrometeoMuni.sync.dto.DtoUsuarioValidado;
import ar.gov.mendoza.PrometeoMuni.utils.Utilities;
import net.petitviolet.fixedtimesscheduledexecutorservice.FixedTimesScheduledExecutorService;


public class LoginActivity extends MendozaActivity {
	private ScheduledExecutorService scheduler;   
	private FixedTimesScheduledExecutorService mService;	
	//handler for Broadcast
	BroadcastReceiver mBroadcaster;
	private IntentFilter receiveFilter;
	
	
	 // VARIABLE LOCALES
		private double latitud;
		private double longitud;
		
		private Boolean WorkingInLoginAsyncTask = false;
		private ProgressDialog progress;
		QuizLoginTask loginTask;
	
		private TextView textView_NumeroSerie=null;
		private TextView textView_Version=null;
		private TextView textView_PoliciaCaminera;
		
	   private EditText  username=null;
	   private EditText  password=null;
	   private TextView attempts;
	   private Button login;
	   int counter = 3;

	   String user;
    /** Called when the activity is first created. */
	   
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
	   
    @Override
    public void onCreate(Bundle savedInstanceState) {

    	    scheduler = Executors.newSingleThreadScheduledExecutor();

    		scheduler.scheduleAtFixedRate
    		      (new Runnable() {
    		         public void run() {
    		        	 Utilities.ShowToast(LoginActivity.this, "Ejecutandose el Proceso");
    		         }
    		      }, 0, 1, TimeUnit.MINUTES);
    	/*
        mService = new FixedTimesScheduledExecutorService();
        final Runnable biggerTask = new Runnable(){
			@Override
			public void run() {
				try {
				    Utilities.ShowToast(LoginActivity.this, "Ejecutandose el Proceso");

					Thread.sleep(50000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}	    	
	    };
        
	    mService.scheduleAtFixedRate(biggerTask, 20, 0, 50, TimeUnit.MILLISECONDS,new FixedTimesTaskListener() {
			
			@Override
			public void onComplete() {
				Utilities.ShowToast(LoginActivity.this, "Terminando el Proceso");
				
			}
		} );
	    */
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	super.onCreate(savedInstanceState);
        
        Intent intent = new Intent(this,LocationService.class);
	    startService(intent);
        
        
		receiveFilter = new IntentFilter(LocationService.BROADCAST_ACTION);
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
							    	
//							    	textLatitud.setText(Latitud.toString());
//							    	textLongitud.setText(Longitud.toString());
//							    	textProvider.setText(Provider);
							    	
								}
								
							};
        
        setContentView(R.layout.advanced_login);
        
        progress = new ProgressDialog(this);
        
        
        username = findViewById(R.id.editText1);
        
        //username.setText("");
        TextKeyListener.clear( username.getText());
        
        password = findViewById(R.id.editText2);
        
        //password.setText("");
        TextKeyListener.clear( password.getText());
        
        attempts = findViewById(R.id.textView5);
        attempts.setText(Integer.toString(counter));
        attempts.setVisibility(View.GONE);
        
        login = findViewById(R.id.button1);

        textView_NumeroSerie = findViewById(R.id.TextView_Numero_Serie);
        textView_Version = findViewById(R.id.textView3);
        textView_PoliciaCaminera = findViewById(R.id.TextView_PoliciaCaminera);

        String versionPDA = "";
        try {
        	versionPDA = GlobalVar.getInstance().getVersionSoftwarePDA();
        } catch(Exception ex ){}
        textView_Version.setText("Version : " + versionPDA);
        
    	EquipoRules equipoRules = new EquipoRules(this);

    	DepartamentoRules departamentoRules = new DepartamentoRules(this);

    	/*Se hace unicamente para pruebas en Municipalidad de Rivadavia*/
		String sMunicipio = "MUNICIPALIDAD DE RIVADAVIA";
    	/*try {
			sMunicipio = "MUNICIPALIDAD DE " + departamentoRules.getDepartamentoById(equipoRules.getMunicipio()).getNombre();
		} catch (Exception e){
			sMunicipio = "NO CONFIGURADA";
		}*/

    	textView_PoliciaCaminera.setText(sMunicipio);

    	String sNumeroSerie = equipoRules.getNumeroSerie();
    	if(sNumeroSerie==null)
    		sNumeroSerie = "No Configurada";
    		else
    	sNumeroSerie = Tools.formatearIzquierda(sNumeroSerie, 4, '0');

    	String sTextNumeroSerie ="DISPOSITIVO : " + sNumeroSerie;
        textView_NumeroSerie.setText(sTextNumeroSerie);
        
        OnClickListener buttonListener = new OnClickListener() {
            boolean clicked = false;
            int numClicks = 0;

            @Override
            public void onClick(View v) {
            	
                if (GlobalVar.getInstance().getIdMovil()==null || GlobalVar.getInstance().getIdMovil()==0)
         		{
         			UIHelper.showAlert(LoginActivity.this, "El Dispositivo no esta Habilitado", "Policia Mendoza", null);
         			return;
         		}
            	
            	login(v);
                /*if(numClicks > counter) {
                    login.setText("Se alcanzo el limite de intentos");
                    login.setEnabled(false);
                }*/
                numClicks++;
            }
        };
        login.setOnClickListener(buttonListener);


    }
    public void loginfailed()
    {
    	if (user.equals(null) || user == null){
			UIHelper.showAlert(LoginActivity.this,
					          "El usuario no se encuentra habilitado. Por favor contactar al area de sistemas",
					              "Usuario Inhabilitado", null);
		}
    	else{
			UIHelper.showAlert(LoginActivity.this,"Credenciales incorrectas. Por favor ingrese nuevamente", "Error", null);

		}
    	attempts.setBackgroundColor(Color.RED);
        counter--;
        attempts.setText(Integer.toString(counter));
        /*
         * if(counter==0){
           login.setEnabled(false);
        }*/
    	
    }
    public void loginsuccessful()
    {
    	//Toast.makeText(this, "Ingresando..." ).show();
        finish();
        Intent intent = new Intent();
        intent.setClass(this, PdaMenuActivity.class);// MainActivity
        startActivity(intent);
    }
    
    public void TestImpresion(View view) {
	//doPrintActaRequest();
    	
    	
    }
    
    protected void doPrintActaRequest() {
    	
        
    	new ZebraPrinterTask<Object, Object, Object>(LoginActivity.this)
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
        		   
	               //CreateActaHelper.printCreateActaCPCL((ActaConstatacion)pActa,this.printer);
        		   CreateActaHelper.printTestImpresionCPCL(this.printerConnection, this.printer);

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
             UIHelper.showErrorOnGuiThread(LoginActivity.this, e.getLocalizedMessage(), null);
            
           }
           
           @Override
           public void onPreExecute()
           {
   			//WorkingInPrintAsyncTask = true;
   			pleaseWaitDialog = ProgressDialog.show(LoginActivity.this,"Impresion de Acta", "Iniciando conexion con Impresora", true, false);
        	super.onPreExecute();
           }
           @Override
           public void onPostExecute(Object object)
           {
        		//WorkingInPrintAsyncTask = false; 
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
    public void login(View view){
    	
    	if (WorkingInLoginAsyncTask ==true)
    	{	Toast.makeText(getApplicationContext(), "Una solicitud de validacion esta siendo realizada! espere un momento" ,Toast.LENGTH_SHORT).show();
    		return;
    	}
    	String _userName = username.getText().toString().trim();
    	String _password = password.getText().toString().trim();
    	if(_userName.equals("") || _password.equals(""))
    	{
    		Toast.makeText(getApplicationContext(), "Nombre de Usuario y/o Contrase�a invalida..." ,Toast.LENGTH_SHORT).show();
    		return;
    	}
    	Log.d(DEBUG_TAG, "Login Click -- button");
    	loginTask = new QuizLoginTask();
    	loginTask.execute(_userName,_password);
    	
    	return;
  
  }
    
    public ProgressDialog getProgressDialog() {
    	return progress;
    }
    // @Override
    // public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.main, menu);
      //  return true;
    // }
    
    ProgressDialog pleaseWaitDialog;
    /***
     * Logueo Asincrono
     * parametros
     * Object      usuario y contrase�a
     * String 	   parametros del progreso de la operacion asincrona
     * Boolean     resultado que se devuelve al terminar la operacion
     * @author 20231670956
     *
     */
    private class QuizLoginTask extends AsyncTask<Object, String, Boolean> {
    	private static final String DEBUG_TAG = "QuizLoginActivity$QuizLoginTask";
    	

    	
		@Override
		protected void onPreExecute() {
			WorkingInLoginAsyncTask = true;
			pleaseWaitDialog = ProgressDialog.show(LoginActivity.this,"Ingreso al Sistema", "Validando credenciales", true, false);
			/*pleaseWaitDialog.setOnCancelListener(new OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					Log.d(DEBUG_TAG, "onCancel -- dialog");
					QuizLoginTask.this.cancel(true);
				}
			}); */
		}
		@Override
		protected Boolean doInBackground(Object... params)
		{
            
			String id_Usuario = "";
            String usuarioRepat = "";
            String claveRepat = "";
            String bloqueado = "";
            String claveIngresadaEncriptada = "";
            String apellidonombre = ""; // agregado
			
			publishProgress("Iniciando Proceso de Validacion");
//			SystemClock.sleep(5000);
			if(isCancelled()) return false;
			
			String userName = (String)params[0];
			String passWord = (String)params[1];
			GlobalVar.getInstance().setLogin(userName);
			
			boolean hayconectividad = true;// da error lo siguiente SyncActa.checkConnectivity(QuizLoginActivity.this);
			
			if (hayconectividad==true)
			{
				ActaConstatacionSync syncActa = new ActaConstatacionSync(LoginActivity.this);
				
				DtoUsuarioValidado dto = new DtoUsuarioValidado();
				/* RESTAURAR */
				try
				{
					dto = syncActa.validarUsuarioEnRepat(userName, passWord);
					if (dto.idUsuario !=null && dto.idUsuario.trim().length()>0)
					{
						dto.login = userName;
						SoporteSistemas sop3 = new SoporteSistemas();
					    String passWordEncriptado3 =sop3.Proteger(passWord);
						dto.clave =passWordEncriptado3;
						dto.passIngresadoEncrip = passWordEncriptado3;
					}
					
				} catch (Exception ex)
				{
					// si la validacion produce error  generamos el dto a no null;
					dto  = new DtoUsuarioValidado();
				}
				/**/
				publishProgress("Finalizando Proceso de Validacion");
				//SystemClock.sleep(5000);
				
				if(isCancelled()) return false;
				
				if(dto.idUsuario !=null && dto.idUsuario.trim().length()>0)
				{	
					publishProgress("Usuario validado (Web)!!!");
					GlobalVar.getInstance().setIdUsuario(dto.idUsuario);
				}
				else
				   publishProgress("Usuario NO validado (Web)!!!");
				
	            id_Usuario = dto.idUsuario; //(int)usuarioEnRepat.Rows[0]["ID_USUARIO"];
	            usuarioRepat = dto.login ==null?"":dto.login;//(string)usuarioEnRepat.Rows[0]["LOGIN"];
	            //Global.GlobalVar.OperadorPDA = usuarioRepat; // agregado
	            claveRepat = dto.clave;//(string)usuarioEnRepat.Rows[0]["CLAVE"];
	            bloqueado = dto.habilitado;// (string)usuarioEnRepat.Rows[0]["BLOQUEADO"];
	            claveIngresadaEncriptada = dto.passIngresadoEncrip;//(string)usuarioEnRepat.Rows[0]["PassIngresadoEncrip"];
	            
	            if (dto.habilitado.equals("NO") || dto.habilitado == "NO"){
	            	user = usuarioRepat;
	            	return false;
				}
			}

			else // sin conectividad
			{
				publishProgress("Conectividad No Detectada (Desconexion)!!!");
			}//FIN    if (hayconectividad==true)
            
			SoporteSistemas sop = new SoporteSistemas();
		    String userNameEncriptado=sop.Proteger(userName);
            String passWordEncriptado=sop.Proteger(passWord);
            
            UsuarioRules rnUsuario = new UsuarioRules(LoginActivity.this);
            
        	Usuario usuarioLocal;	
        	try 
        	{
				usuarioLocal = rnUsuario.getUsuario(userNameEncriptado,passWordEncriptado);
			}
        	catch (DeviceActasOrmException e) 
        	{
				return false;
			}
        	
        	if (usuarioRepat.trim().equals("") && usuarioLocal.getIdUsuario()<= 0)
        	{ // NO SE PUDO CONECTAR A REPAT Y NO SE ENCONTRO LOS DATOS EN BASE LOCAL
        		publishProgress("Las Credenciales no puedieron ser verificadas (NWNL)");
        		return false;
        	}
        	if (usuarioRepat.trim().equals("") && usuarioLocal.getIdUsuario()> 0)
        	{ // NO SE PUDO CONECTAR A REPAT Y SE ENCONTRO LOS DATOS EN BASE LOCAL
        		GlobalVar.getInstance().setIdUsuario(usuarioLocal.getIdUsuBase());
        		publishProgress("Las Credenciales han sido ser verificadas (L)");
        		return true;
        	}
        	
        	if ((!claveIngresadaEncriptada.equals(claveRepat))== true)
        	{
        		publishProgress("Las Credenciales no pudieron ser verificadas (NW)");
        		return false;
        	}
        	else
			{ // contrase�as validadas remotamente 

        		publishProgress("Las Credenciales han sido verificadas (W)");
        		//registra o actualiza segun los datos en base de datos local
       			rnUsuario.RegistrarUsuario(id_Usuario, userName, userNameEncriptado, passWordEncriptado, apellidonombre, bloqueado);
        		if (bloqueado == "S")
	            {
        			publishProgress("Usuario Inhabilitado. ");
	                return false;
	            }
	       	    /*  RESTAURAR
				SyncActa synActa; 
                try
         	    {
                	publishProgress("Obteniendo Ultimo Nro de Acta (WEB)!!!");
	        		synActa = new SyncActa(QuizLoginActivity.this);
	         	    String sNumeroActa ="";
             	    sNumeroActa = synActa.validarNumeroActa("0150", 0.0, 0.0);
             	    long numeroActaREPAT = Integer.parseInt(sNumeroActa.substring(4,sNumeroActa.length()-1)); //desde la 4 posicion hasta el largo menos -1 que es el digito verificador
             	    publishProgress("Actualizando Ultimo Nro de Acta a " + numeroActaREPAT + "(WEB)!!!");
             	    ContentProvider cp = getContentResolver().acquireContentProviderClient(ActaConstatacionProvider.CONTENT_URI).getLocalContentProvider();
             	    ActaConstatacionProvider  a =(ActaConstatacionProvider) cp; 
             	    a.setSecuenceValue(numeroActaREPAT);
         	    } 
         	    catch (Exception e) 
         	    {
         	    	publishProgress("No se pudo Obtener el Ultimo Nro de Acta (WEB)!!!");
         	    }
         	    finally
         	    {
         	    	synActa =null;
         	    }
                */
        		  
                return true;
            }

		}

		@Override
		protected void onProgressUpdate(String... values) {
			if(values != null && values[0] != null) {
				///context.getProgressDialog().setMessage(values[0]);
				pleaseWaitDialog.setMessage(values[0]);
				pleaseWaitDialog.incrementProgressBy(50);
				if(!pleaseWaitDialog.isShowing())
					pleaseWaitDialog.show();
				//if(!context.getProgressDialog().isShowing())
				//	context.getProgressDialog().show();
			}
			super.onProgressUpdate(values);
			
		}

		@Override protected void onPostExecute(Boolean result) {
			WorkingInLoginAsyncTask = false; 
			super.onPostExecute(result);
			
			pleaseWaitDialog.dismiss();
			pleaseWaitDialog = null;
			
			//if(result == null) {
			if(result == true) {
				//context.getProgressDialog().dismiss();
				//context.finish();
				//pleaseWaitDialog.dismiss();
				//QuizLoginActivity.this.finish();
				loginsuccessful();
				
			//	Intent intent = new Intent (context, NovedadPendienteActivity.class);
			//	context.startActivity(intent);						
			}
			else {
				loginfailed();
				//context.getProgressDialog().dismiss();
				//pleaseWaitDialog.dismiss();
				//Toast.makeText(context, result, Toast.LENGTH_LONG).show();
				//Toast.makeText(QuizLoginActivity.this, "Credenciales Invalidas", Toast.LENGTH_LONG).show();
			}
			
		
		}
		
    	@Override
		protected void onCancelled() {
    		WorkingInLoginAsyncTask = false;
			Log.i(DEBUG_TAG, "onCancelled");
			//handleNoQuestions();
			pleaseWaitDialog.dismiss();
		}
    	
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//    	Intent localIntent;
//    	switch(item.getItemId())
//        { 
//            case R.id.action_acta_imprimir://action_settings:
//                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();;
//                localIntent= new Intent(this, StatusDialog.class);
//                this.startActivity(localIntent);
//                break;
//            case R.id.action_printer_settings://.action_search:
//                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
//                
//                localIntent= new Intent(this, StatusDialog.class);
//                localIntent= new Intent(this, TrafficTicket.class);
//                localIntent = new Intent(this, ChoosePrinterController.class);
//      	        this.startActivity(localIntent);
//                break;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
     
        return true;
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.crearactaoptions, menu);
		return true;
	}
}