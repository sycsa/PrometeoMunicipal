
package ar.gov.mendoza.PrometeoMuni.task;

import android.os.AsyncTask;
import android.widget.Toast;
import ar.gov.mendoza.PrometeoMuni.LoginActivity;

public class LoginTask extends AsyncTask<LoginActivity, String, String> {

	private LoginActivity context;
	
	@Override
	protected String doInBackground(LoginActivity... params) {
		
		context = params[0];
		publishProgress("Iniciando Sesión...");
		
//		SyncInspeccion service = new SyncInspeccion(context);
//		
//		try {
//			return service.validateLogin(
//					context.getNombreUsuario(), 
//					context.getClaveUsuario(),  
//					context.getControlUsuario(),
//					context.getLatitud(),
//					context.getLongitud());
//		} 
//		catch (SecurityException e) {			
//			return e.getMessage();
//		} 
//		catch (NoSuchMethodException e) {
//			return e.getMessage();
//		}
		return "";
	}
	
	@Override
	protected void onProgressUpdate(String... values) {
		
		if(values != null && values[0] != null) {
			context.getProgressDialog().setMessage(values[0]);
			
			if(!context.getProgressDialog().isShowing())
				context.getProgressDialog().show();
		}
		
		super.onProgressUpdate(values);
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		
		if(result == null) {
			context.getProgressDialog().dismiss();
			context.finish();
			
		//	Intent intent = new Intent (context, NovedadPendienteActivity.class);
		//	context.startActivity(intent);						
		}
		else {
			context.getProgressDialog().dismiss();
			Toast.makeText(context, result, Toast.LENGTH_LONG).show();
		}
	}
}
