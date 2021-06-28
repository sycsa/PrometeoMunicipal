package ar.gov.mendoza.PrometeoMuni.screens.verificaciones.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import ar.gov.mendoza.PrometeoMuni.R;
import ar.gov.mendoza.PrometeoMuni.core.domain.TipoBusqueda;
import ar.gov.mendoza.PrometeoMuni.adapters.GenericSpinAdapter;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;
import ar.gov.mendoza.PrometeoMuni.sync.VerificacionPolicialSync;
import ar.gov.mendoza.PrometeoMuni.sync.dto.RestriccionDTO;

public class VerificacionGenerica extends Fragment {

	ProgressDialog pleaseWaitDialog;
    private Boolean WorkingInVerificacionAsyncTask = false;
    VerificacionPolicialRequestTask verificacionPolicialRequest;

    
	//protected String LABEL = "Buscar Por";
	protected String TIPO_OBJETO = VerificacionPolicialSync.PERSONAS; // AUTOMOTORES  MOTOVEHICULOS  
	protected String TIPO_BUSQUEDA = VerificacionPolicialSync.NUMERO_DOCUMENTO; // DOMINIO  NUMERO_MOTOR  NUMERO CHASIS
	protected EditText editText_Valor_A_Buscar ;
	protected Spinner myTipoBusquedaSpinner;
	protected TextView textView_Resultado;
	
	private GenericSpinAdapter<TipoBusqueda> myTipoBusquedaAdapter;
	private boolean isSpinnerInitialTipoBusqueda = true;
	private void initTipoBusquedaSpinner()
	{
		final TipoBusqueda[] items;
		
		List<TipoBusqueda> lstTipoBusqueda= new ArrayList<TipoBusqueda>();
		if(TIPO_OBJETO.equals("PERSONAS"))
		{ 
			TipoBusqueda pTipoBusqueda = new TipoBusqueda(VerificacionPolicialSync.NUMERO_DOCUMENTO,"NUMERO DOCUMENTO");
			lstTipoBusqueda.add(pTipoBusqueda);
			

		}
		else
		{
			TipoBusqueda pTipoBusqueda = new TipoBusqueda(VerificacionPolicialSync.DOMINIO,"DOMINIO");
			lstTipoBusqueda.add(pTipoBusqueda);
			pTipoBusqueda = new TipoBusqueda(VerificacionPolicialSync.NUMERO_MOTOR,"NUMERO MOTOR");
			lstTipoBusqueda.add(pTipoBusqueda);
			pTipoBusqueda = new TipoBusqueda(VerificacionPolicialSync.NUMERO_CHASIS,"NUMERO CHASIS");
			lstTipoBusqueda.add(pTipoBusqueda);
		}
		TipoBusqueda pTipoBusqueda = new TipoBusqueda("","Seleccione que desea Buscar");
		lstTipoBusqueda.add(pTipoBusqueda);
		
		items = new TipoBusqueda[lstTipoBusqueda.size()];
		lstTipoBusqueda.toArray(items);
		
		myTipoBusquedaAdapter = new GenericSpinAdapter<TipoBusqueda>(getActivity(),android.R.layout.simple_spinner_item,items);
		myTipoBusquedaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		myTipoBusquedaSpinner.setAdapter(myTipoBusquedaAdapter);
		//myTipoBusquedaSpinner.setSelection(myTipoBusquedaSpinner.getCount());
	}
	
	public Handler receptorVerificacionPolicialTask = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	      
	    	RestriccionDTO restricion = (RestriccionDTO) msg.obj;
	    	if(restricion==null)
	    		textView_Resultado.setText("No se pudo Obtener la información Solicitada!");
	    	else
	    		textView_Resultado.setText(restricion.getResultado());
//	    	webView.setContentDescription("Descripcion del Contenido");
//	    	webView.loadData("<html><body>" + restricion.getResultado() + "</body></html>",
//	                		 "text/html", 
//	                		 "UTF-8");
	    }
	  };
	  
//		private TextChangedListener editTextChanged() {
//		return new TextChangedListener() {
//			
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				
//			}
//			
//			@Override
//			public void afterTextChanged(Editable s) {
//				
//			}
//		};	  
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    	 View rootView = inflater.inflate(R.layout.fragment_verificacion_generica, null);
		 // The key to making data survive
         // runtime configuration changes.
         setRetainInstance(true);
//         TextView textViewLabel =  (TextView) rootView.findViewById(R.id.TextView_LabelBuscarPor);
//         textViewLabel.setText(LABEL);
         
          editText_Valor_A_Buscar = rootView.findViewById(R.id.EditText_Valor_A_Buscar);
          editText_Valor_A_Buscar.addTextChangedListener( new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				textView_Resultado.setText("");
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
          
          myTipoBusquedaSpinner = rootView.findViewById(R.id.Spinner_TipoBusqueda);
          textView_Resultado = rootView.findViewById(R.id.TextView_Resultado);
         initTipoBusquedaSpinner();
          
          
         Button buttonBuscar = rootView.findViewById(R.id.Button_BuscarRestricciones);
         buttonBuscar.setOnClickListener(new OnClickListener() {
 			
 			@Override
 			public void onClick(View v)	 {
 				 String valor = editText_Valor_A_Buscar.getText().toString();
 				 String tipoBusqueda = ((TipoBusqueda) myTipoBusquedaSpinner.getSelectedItem()).getId();
 				 doBuscarRestriccion(TIPO_OBJETO ,tipoBusqueda,valor);	
 			}
 		});
        
        
         
         return rootView;
    }
    
   
    protected void doBuscarRestriccion(String pTipoObjetoBusqueda,String pTipoBusqueda,String pValor)
    {
        // make sure we don't collide with another pending update
        if (verificacionPolicialRequest == null || verificacionPolicialRequest.getStatus() == AsyncTask.Status.FINISHED || verificacionPolicialRequest.isCancelled()) {
        	verificacionPolicialRequest = new VerificacionPolicialRequestTask(pTipoObjetoBusqueda,pTipoBusqueda);
        	verificacionPolicialRequest.setPostExecuteHandler(receptorVerificacionPolicialTask);
        	verificacionPolicialRequest.execute(pValor);
        } else {
            Log.w("AsyncTask", "Warning: verificacionPolicialRequestTask already going");
        }

    	
    }
    private class VerificacionPolicialRequestTask extends AsyncTask<String, Object, RestriccionDTO> 
    {
    	private Handler postExecuteHandler;
        final private String tipoObjetoBusqueda ;
        final private String tipoBusqueda ;
    	
        public void setPostExecuteHandler(Handler postExecuteHandler) {
            this.postExecuteHandler = postExecuteHandler;
        }
        
    	public VerificacionPolicialRequestTask (String pTipoObjetoBusqueda,String pTipoBusqueda)
        {
    		this.tipoObjetoBusqueda= pTipoObjetoBusqueda;
    		this.tipoBusqueda = pTipoBusqueda;
        }
    	
    	@Override
        protected void onPostExecute(RestriccionDTO result) {
            //VerificacionGenerica.this.setProgressBarIndeterminateVisibility(false);
            WorkingInVerificacionAsyncTask = false; 
			super.onPostExecute(result);
			
			pleaseWaitDialog.dismiss();
			pleaseWaitDialog = null;
			
			if (postExecuteHandler != null) {
	            Message msg = Message.obtain();
	            msg.obj = result;
	            postExecuteHandler.sendMessage(msg);
	        }
	  	
        }

        @Override
        protected void onPreExecute() {
            //QuizCreateActaActivity.this.setProgressBarIndeterminateVisibility(true);
        	WorkingInVerificacionAsyncTask = true;
   			pleaseWaitDialog = ProgressDialog.show(getActivity(),"Verificacion Policial", "Iniciando conexion con Re.P.A.T.", true, false);
        	super.onPreExecute();
            
        		
        }

        @Override
        protected RestriccionDTO doInBackground(String... params) {
        	RestriccionDTO resultado= null;
            try {
                String pDatoBusqueda = params[0];

                
                Context context = GlobalStateApp.getInstance().getApplicationContext();
                VerificacionPolicialSync  verificacionPolicialSync = new VerificacionPolicialSync(context);
                
                resultado = verificacionPolicialSync.BuscarRestricciones(tipoObjetoBusqueda, tipoBusqueda, pDatoBusqueda);
                // resultado = syncLicencia.validarNumeroLicencia(numeroLicencia);
                
                return resultado;
            } 
            catch(Exception ex)
            {
            	String message = ex.getMessage();
            	//UIHelper.showAlert(VerificacionGenerica.this, message, "Error Obteniendo datos de la Licencia", null);
            	resultado = null;
            }
            /*
            catch (MalformedURLException e) {
                Log.e(DEBUG_TAG, "Failed to add friend", e);
            } catch (IOException e) {
                Log.e(DEBUG_TAG, "Failed to add friend", e);
            }*/

            return null;
        }

    }
    
}
