package ar.gov.mendoza.PrometeoMuni;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConsultasRestriccionesActivity extends ItemVerificacionPolicialActivity {
	   
	   private EditText  txtNroDocumento=null;
	   private TextView  txtResultado=null;
	   private Button btnBuscar;
	   int counter = 3;
	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consultas_restricciones);
		
		
        txtNroDocumento = findViewById(R.id.editTextNroDocumento);
        txtResultado = findViewById(R.id.TextView_ResultText);
        btnBuscar = findViewById(R.id.btnBuscarRestricciones);
        
        OnClickListener buttonListener = new OnClickListener() {
          
        @Override
        public void onClick(View v) {

        	
        	
            }
        };
        btnBuscar.setOnClickListener(buttonListener);
        
        
	}
}
