package ar.gov.mendoza.PrometeoMuni.screens.actas.fragments;

import java.util.ArrayList;
import java.util.List;

import com.zebra.android.zebrautilitiesmza.util.UIHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import ar.gov.mendoza.PrometeoMuni.R;
import ar.gov.mendoza.PrometeoMuni.core.domain.TipoBusqueda;
import ar.gov.mendoza.PrometeoMuni.adapters.GenericListAdapter;
import ar.gov.mendoza.PrometeoMuni.adapters.GenericSpinAdapter;
import ar.gov.mendoza.PrometeoMuni.screens.verificaciones.ResultadoVerificacionPolicialActivity;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;
import ar.gov.mendoza.PrometeoMuni.sync.ActaConstatacionSync;
import ar.gov.mendoza.PrometeoMuni.sync.dto.ActaConstatacionDTO;
import ar.gov.mendoza.PrometeoMuni.utils.StringBuilderForPrint;

public class VerificacionActasGenerica extends Fragment {

    public static final String DEBUG_TAG = "DeviceActas.VerificacionActasGenerica ! Fragment Log";
    ActaRequestTask actaRequest;
    
    
    //Licencia
	public Handler receptorVerificacionActaTask = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      
    	 ActaConstatacionDTO actaValida = (ActaConstatacionDTO) msg.obj;
    	 String sResultado ="";
    	 String sDatosLicencia ="";
    	 
    	 if (actaValida==null)
    	 {
    		 UIHelper.showAlert(getActivity(), "No se obtener la informacion solicitada", "Verificacion de Acta Remota", null);
    		 return;
    	 }
    	 StringBuilderForPrint strb = new StringBuilderForPrint();
    	 if(actaValida!=null)
    	 {
	    	 /*strb.append("Licencia:" + licencia.getNumeroLicencia())
	    	 .append("Vencida:" + licencia.getLicenciaVencida())
	    	 .append("Fecha Vencimiento:" + licencia.getFechaVencimiento())
	    	 .append("Titular" + licencia.getApellido() + ", " + licencia.getNombre())
	    	 .append("Numero Documento" +  licencia.getNumeroDocumento());
	    	 */
    		 strb.append(actaValida.toString());
	    	 sDatosLicencia = strb.toString();
    	 }
    	 
		Intent intent = new Intent();
	    Bundle appResultData = new Bundle();
	     
	    if(sDatosLicencia.equals("") && sResultado.equals(""))
	    {
	    	sDatosLicencia = "No se encontraron datos del Acta";
	    }
	    appResultData.putString(ResultadoVerificacionPolicialActivity.APP_DATA_LICENCIA,sDatosLicencia);   
	    appResultData.putString(ResultadoVerificacionPolicialActivity.APP_DATA_RESULTADO,sResultado); 
	        
	    intent.putExtra(ResultadoVerificacionPolicialActivity.APP_DATA,appResultData);
		intent.setClass(getActivity(),ResultadoVerificacionPolicialActivity.class);
         
		startActivity(intent);

    }
  };
	
	 private final class ListOnItemClickListener implements OnItemClickListener 
	 {

	        public void onItemClick(AdapterView<?> lv, View v, int position, long id) {
	            onListItemClick((ListView) lv, v, position, id);
	        }
	 }
	 
	 protected void onListItemClick(ListView lv, View v, int position, long id) {
		   //Log.d("click", "Position click " + position);
	        ActaConstatacionDTO item = (ActaConstatacionDTO) lv.getAdapter().getItem(position);
	        //Toast.makeText(getActivity(), item.getItemName() + " selected", Toast.LENGTH_LONG).show();
	        // buscamos el acta completa y la mostramos en una nueva pantallas/activity
	        doVerificarIdActaRemota(item.getNumeroActa());//item.getIdActaConstatacion().toString());
	        
	 }
	 
		private void doVerificarIdActaRemota(String idActaConstatacion)
		{
	        // make sure we don't collide with another pending update
	        if (actaRequest == null || actaRequest.getStatus() == AsyncTask.Status.FINISHED || actaRequest.isCancelled()) {
	        	actaRequest = new ActaRequestTask();
	        	actaRequest.setPostExecuteHandler(receptorVerificacionActaTask);
	        	actaRequest.execute(idActaConstatacion);
	        } else {
	            Log.w(DEBUG_TAG, "Warning: actaRequestTask already going");
	        }
		}
	 
	ProgressDialog pleaseWaitDialog;
    private Boolean WorkingInVerificacionAsyncTask = false;
    VerificacionActasRequestTask verificacionPolicialRequest;

    
	//protected String LABEL = "Buscar Por";
	protected String TIPO_OBJETO = ActaConstatacionSync.ACTA_CONSTATACION; // AUTOMOTORES  MOTOVEHICULOS  
	protected String TIPO_BUSQUEDA = ActaConstatacionSync.NUMERO_ACTA; // DOMINIO  NUMERO_MOTOR  NUMERO CHASIS
	protected EditText editText_Valor_A_Buscar ;
	protected Spinner myTipoBusquedaSpinner;
	protected TextView textView_Resultado;
	protected ListView listView_Resultado;// agregado para mostrar las actas encontradas
	
	private GenericSpinAdapter<TipoBusqueda> myTipoBusquedaAdapter;
	private boolean isSpinnerInitialTipoBusqueda = true;

	private void initTipoBusquedaSpinner()
	{
		final TipoBusqueda[] items;
		
		List<TipoBusqueda> lstTipoBusqueda= new ArrayList<TipoBusqueda>();
		if(TIPO_OBJETO.equals(ActaConstatacionSync.ACTA_CONSTATACION))
		{ 
			TipoBusqueda pTipoBusqueda = new TipoBusqueda(ActaConstatacionSync.NUMERO_ACTA,"NUMERO ACTA");
			lstTipoBusqueda.add(pTipoBusqueda);
			
			pTipoBusqueda = new TipoBusqueda(ActaConstatacionSync.NUMERO_DOCUMENTO,"NUMERO DOCUMENTO");
			lstTipoBusqueda.add(pTipoBusqueda);

		}
		else
		{ 	return;
			/*TipoBusqueda pTipoBusqueda = new TipoBusqueda(VerificacionPolicialSync.DOMINIO,"DOMINIO");
			lstTipoBusqueda.add(pTipoBusqueda);
			pTipoBusqueda = new TipoBusqueda(VerificacionPolicialSync.NUMERO_MOTOR,"NUMERO MOTOR");
			lstTipoBusqueda.add(pTipoBusqueda);
			pTipoBusqueda = new TipoBusqueda(VerificacionPolicialSync.NUMERO_CHASIS,"NUMERO CHASIS");
			lstTipoBusqueda.add(pTipoBusqueda);
			*/			
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
	      
	    	List<ActaConstatacionDTO>  actasEncontradas = (List<ActaConstatacionDTO>) msg.obj;
	    	if(actasEncontradas!=null && actasEncontradas.size()>0)
	    	{
	    		textView_Resultado.setText("Actas encontradas (" + actasEncontradas.size() +")");
	    		listView_Resultado.setVisibility(View.VISIBLE);
	    		GenericListAdapter<ActaConstatacionDTO> myTAdapter = new GenericListAdapter<ActaConstatacionDTO>(getActivity(),R.layout.list_item_with_two_text,actasEncontradas);
	    		listView_Resultado.setAdapter(myTAdapter);		
	    	}
	    	else
	    	{
	    		textView_Resultado.setText("No se pudo Obtener la información Solicitada!");
	    	    listView_Resultado.setVisibility(View.GONE);
	    		
	    	}
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	
    	listView_Resultado = view.findViewById(R.id.ListView_Resultado);
          
     //   listView_Resultado.setOnItemClickListener(new ListOnItemClickListener());
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    	 View rootView = inflater.inflate(R.layout.fragment_verificacion_acta_remota, null);
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
          listView_Resultado = rootView.findViewById(R.id.ListView_Resultado);
          
          listView_Resultado.setOnItemClickListener(new ListOnItemClickListener());
          
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

        if (verificacionPolicialRequest == null || verificacionPolicialRequest.getStatus() == AsyncTask.Status.FINISHED || verificacionPolicialRequest.isCancelled()) {
        	verificacionPolicialRequest = new VerificacionActasRequestTask(pTipoObjetoBusqueda,pTipoBusqueda);
        	verificacionPolicialRequest.setPostExecuteHandler(receptorVerificacionPolicialTask);
        	verificacionPolicialRequest.execute(pValor);
        } else {
            Log.w("AsyncTask", "Warning: verificacionActasRequestTask already going");
        }

    	
    }
    private class VerificacionActasRequestTask extends AsyncTask<String, Object, List<ActaConstatacionDTO>> 
    {
    	private Handler postExecuteHandler;
        final private String tipoObjetoBusqueda ;
        final private String tipoBusqueda ;
    	
        public void setPostExecuteHandler(Handler postExecuteHandler) {
            this.postExecuteHandler = postExecuteHandler;
        }
        
    	public VerificacionActasRequestTask (String pTipoObjetoBusqueda,String pTipoBusqueda)
        {
    		this.tipoObjetoBusqueda= pTipoObjetoBusqueda;
    		this.tipoBusqueda = pTipoBusqueda;
        }
    	
    	@Override
        protected void onPostExecute(List<ActaConstatacionDTO> result) {
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
   			pleaseWaitDialog = ProgressDialog.show(getActivity(),"Verificacion Actas", "Iniciando conexion con Sistema", true, false);
        	super.onPreExecute();
            
        		
        }

        @Override
        protected List<ActaConstatacionDTO> doInBackground(String... params) {
        	List<ActaConstatacionDTO> resultado= null;
            try {
                String pDatoBusqueda = params[0];

                
                Context context = GlobalStateApp.getInstance().getApplicationContext();
                ActaConstatacionSync verificacionActasSync = new ActaConstatacionSync(context);
                
                resultado = verificacionActasSync.BuscarActaConstatacion(tipoObjetoBusqueda, tipoBusqueda, pDatoBusqueda);
                // resultado = syncLicencia.validarNumeroLicencia(numeroLicencia);
                
                return resultado;
            } 
            catch(Exception ex)
            {
            	String message = ex.getMessage();
            	//UIHelper.showAlert(VerificacionGenerica.this, message, "Error Obteniendo datos de la Licencia", null);
            	resultado = null;
            }

            return null;
        }

    }

    
 private class ActaRequestTask extends AsyncTask<String, Object, ActaConstatacionDTO> {
    	
    	private Handler postExecuteHandler;
    	
        public void setPostExecuteHandler(Handler postExecuteHandler) {
            this.postExecuteHandler = postExecuteHandler;
        }  
    	
    	@Override
        protected void onPostExecute(ActaConstatacionDTO result) {
            getActivity().setProgressBarIndeterminateVisibility(false);
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
            getActivity().setProgressBarIndeterminateVisibility(true);
        	WorkingInVerificacionAsyncTask = true;
   			pleaseWaitDialog = ProgressDialog.show(getActivity(),"Verificacion de Acta", "Iniciando conexion con Sistema", true, false);
        	super.onPreExecute();
            
        }

        @Override
        protected ActaConstatacionDTO doInBackground(String... params) {
            Boolean succeeded = false;
            try {
                String idActaConstatacion = params[0];

               
                Context context = GlobalStateApp.getInstance().getApplicationContext();
                ActaConstatacionSync actasSync = new ActaConstatacionSync(context);
                ActaConstatacionDTO resultado = actasSync.getActa(idActaConstatacion);
                
                return resultado;
            } 
            catch(Exception ex)
            {
            	String message = ex.getMessage();
            	UIHelper.showAlert(getActivity(), message, "Error Obteniendo datos del Acta", null);
            	succeeded = false;
            }

            return null;
        }

    }
    
}
