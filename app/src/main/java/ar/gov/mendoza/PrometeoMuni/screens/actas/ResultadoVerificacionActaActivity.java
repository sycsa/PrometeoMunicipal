package ar.gov.mendoza.PrometeoMuni.screens.actas;

import android.support.v7.app.AppCompatActivity;//.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import ar.gov.mendoza.PrometeoMuni.R;

public class ResultadoVerificacionActaActivity extends  AppCompatActivity{ //ActionBarActivity {//FragmentActivity {//
	public final static String APP_DATA = "app_data";
	
	public static String APP_DATA_RESULTADO = "RESULTADO" ;
	public static String APP_DATA_LICENCIA = "LICENCIA" ;
	
	private TextView textView_Resultado;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resultado_verificacion_policial);

		textView_Resultado = findViewById(R.id.TextView_Resultado);
		// ver que viene con el bundled para ponerlo en el TextView 
		handleIntent(getIntent());
	}

	public void handleIntent(Intent intent) {
		 
		
		
		Bundle appData = intent.getBundleExtra(ResultadoVerificacionActaActivity.APP_DATA);
		if (appData != null) 
		{
		    String datosLicencia = appData.getString(ResultadoVerificacionActaActivity.APP_DATA_LICENCIA,"");
		    String datosResultado=  appData.getString(ResultadoVerificacionActaActivity.APP_DATA_RESULTADO,"");
		    String mensajeAMostrar ="";
		    if (datosLicencia!=null && !datosLicencia.equals(""))
		    {
		    	mensajeAMostrar = datosLicencia;
		    }
		    if (datosResultado!=null && !datosResultado.equals(""))
		    {
		    	mensajeAMostrar += datosResultado;
		    }
		    textView_Resultado.setText(mensajeAMostrar);
		}
		
		
	    
	 }
}
