package ar.gov.mendoza.PrometeoMuni.singleton;

import java.util.Date;

import com.zebra.android.zebrautilitiesmza.ZebraUtilitiesApplication;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import ar.gov.mendoza.PrometeoMuni.db.*;
import ar.gov.mendoza.PrometeoMuni.sessions.SessionManager;
/**
 * @author huyletran84@gmail.com
 */
public class GlobalStateApp extends Application {

	   private static GlobalStateApp singleton;
	   public static GlobalStateApp getInstance() {
	      return singleton;
	    }
	public static Context mContext;
	public static DatabaseConnection mDatabaseConnection;
	public static Date FechaInicioSesion;
	
	public static SessionManager session;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
		singleton = this;
		ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT = mContext;
		
		// abriremos la conexion 	
//		mDatabaseConnection = new DatabaseConnection(getApplicationContext());
//		mDatabaseConnection.open();
		
		session = new SessionManager(mContext);

		GlobalVar.InitInstance();
	
		
	}
	
	

	@Override
	public void onTerminate() {
		super.onTerminate();
		mDatabaseConnection.close();
	}
	
	// Create an AsyncTask to copy the database in a background thread while
	// displaying a ProgressDialog.
	private class LoadDatabaseTask extends AsyncTask<Context, Void, Void> {
	    Context mContext;
	    ProgressDialog mDialog;

	    // Provide a constructor so we can get a Context to use to create
	    // the ProgressDialog.
	   public LoadDatabaseTask(Context context) {
	        super();
	        mContext = context;
	    }

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        mDialog = new ProgressDialog(mContext);
	        mDialog.setMessage("Loading database...");//mContext.getString(
	        mDialog.show();
	    }

	    @Override
	    protected Void doInBackground(Context... contexts) {
	        // Copy database.
	        //new MyAssetHelper(contexts[0]).getReadableDatabase();
	    	mDatabaseConnection = new DatabaseConnection(getApplicationContext());
			mDatabaseConnection.open();
	        return null;
	    }

	    @Override
	    protected void onPostExecute(Void result) {
	        super.onPostExecute(result);
	        mDialog.dismiss();
	    }
	}

	
}