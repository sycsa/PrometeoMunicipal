package ar.gov.mendoza.PrometeoMuni.screens.verificaciones;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;//ActionBarActivity;
import android.os.Bundle;

import ar.gov.mendoza.PrometeoMuni.R;
import ar.gov.mendoza.PrometeoMuni.screens.verificaciones.fragments.VerificacionAutomotor;
import ar.gov.mendoza.PrometeoMuni.screens.verificaciones.fragments.VerificacionMotoVehiculo;
import ar.gov.mendoza.PrometeoMuni.screens.verificaciones.fragments.VerificacionPersona;

public class VerificacionPolicialActivity extends AppCompatActivity{ //ActionBarActivity {//FragmentActivity {//
	 
	private FragmentTabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verificacion_policial);

//		 TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);  
//	        tabHost.setup();  
//	          
//	        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Persona").setContent(R.id.tab1));  
//	        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Automotor").setContent(R.id.tab2));
//	        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("MotoVehiculo").setContent(R.id.tab2));

	     tabHost = findViewById(R.id.mi_tabhost);
	     tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
	     tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Persona"),
	                           VerificacionPersona.class, null);
	     tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Autos"),
	                           VerificacionAutomotor.class, null);
	     tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Motos"),
	                           VerificacionMotoVehiculo.class, null);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.verificacion_policial, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_imprimir) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}
