package ar.gov.mendoza.PrometeoMuni.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;

/**
 * Define the basic method to work with database: insert, update, delete, get...
 * 
 * @author huyletran84@gmail.com
 */


public class DeviceActasDao {

	/**
	 * The application context.
	 */
	protected Context mContext;
	/**
	 * Keep the static connection to database.
	 */
	protected SQLiteDatabase mDB;
	/**
	 * The default constructor.
	 */
	public DeviceActasDao() {
		mContext = GlobalStateApp.mContext;
		if (GlobalStateApp.mDatabaseConnection != null) {
			mDB = GlobalStateApp.mDatabaseConnection.getDatabase();
		}
	}

	
    public Boolean execSQL(String pStatement)
    {
    	Boolean bResultado = false;
    	try
    	{
    		mDB.execSQL(pStatement);
    		bResultado = true;
    	}
    	catch(Exception ex)
    	{
    	}
    	return bResultado;
    }
    
	
	

	
}
