package ar.gov.mendoza.PrometeoMuni.db;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ar.gov.mendoza.PrometeoMuni.dao.SeccionalDao;
import ar.gov.mendoza.PrometeoMuni.dao.SuportTableDao;

/**
 * Helper class uses to connect to database. Perform query/insert/update/delete.
 * 
 * @author huyletran84@gmail.com
 */
public class DatabaseConnection {
	/** The name of the database file on the device. */
	private static final String DATABASE_NAME = "DeviceActas.db";//"Actas2011.2.28.1";//"";
	/** The version of the database this code works with. */
	public static final int DATABASE_CURRENT_VERSION = 1;
	/** Variable to hold the database instance. */
	private SQLiteDatabase mDB;
	/** Database open/upgrade helper. */
	private DatabaseHelper mOpenHelper;
	/** The context within which to work. */
	private final Context mContext;

	

	/**
	 * Construct database service with context of the application.
	 * 
	 * @param context
	 *            the Context within which to work
	 */
	public DatabaseConnection(final Context context) {
		mContext = context;
		
		mOpenHelper = new DatabaseHelper(mContext);
	}

	/**
	 * Get context.
	 */
	public final Context getContext() {
		return mContext;
	}

	/**
	 * Get database connection.
	 */
	public final SQLiteDatabase getDatabase() {
		return mDB;
	}

	/** Open the database. */
	public final void open() {
		if (mOpenHelper == null) {
			return;
		}
		mDB = mOpenHelper.getWritableDatabase();
	}

	/** Close the database. */
	public final void close() {
		if (mDB != null) {
			mDB.close();
		}
	}
	
	/**
	 * A helper class is used to manage database.
	 */
	public static final class DatabaseHelper extends SQLiteAssetHelper { // SQLiteOpenHelper {

		/**
		 * Constructor.
		 * 
		 * @param context
		 *            the context keeps this connection.
		 */
		public DatabaseHelper(final Context context) {
			//antes de llamar a la base de datos  deberiamos de copiarla desde la primer version original
			super(context, DATABASE_NAME, null, DATABASE_CURRENT_VERSION);
			//setForcedUpgradeVersion(2);
		}


		/*
		@Override
		public void onCreate(final SQLiteDatabase db) {
		
			try
			{
				new SeccionalDao().createTable(db);
			}catch(Exception ex)
			{
				
			}

		}			new UsuarioDao().createTable(db);
			new ActaConstatacionDao().createTable(db);
			*/
			/* Cargar paises */
			/*PaisDao paisDao = new PaisDao();
			paisDao.createTable(db);
			paisDao.Initialize(db);
			*/
			/* Cargar Provincias */
			/*
			ProvinciaDao provinciaDao = new ProvinciaDao();
			provinciaDao .createTable(db);
			provinciaDao.Initialize(db);
			*/
			
			/* Cargar Departamentos */
			/*
			DepartamentoDao departamentoDao = new DepartamentoDao();
			departamentoDao.createTable(db);
			departamentoDao.Initialize(db);
			*/
			/* Cargar Localidades */
			/*
			LocalidadDao localidadDao = new LocalidadDao();
			localidadDao.createTable(db);
			localidadDao.Initialize(db);
			*/
			/* Carga de Equipos Habilitados */
			/*
			 EquipoDao equipoDao = new EquipoDao();
			 equipoDao.createTable(db);
			 equipoDao.Initialize(db);
			*/
		//}

		@Override
		public void onUpgrade(final SQLiteDatabase db, final int oldVersion,final int newVersion) {
			
			if(newVersion==1)
			{
				try
				{
					SuportTableDao suportTableDao = new SuportTableDao();
					ContentValues values = new ContentValues();
					values.put("HABILITAR_BTN_CODIGO_QR","N");
					suportTableDao.update("1", values);
					
				}
				catch(Exception ex)
				{}
			}	
			
			try
			{
				new SeccionalDao().createTable(db);
			}catch(Exception ex)
			{
				
			}
			//Solo para probar cambiar el wslocation para desarrollo
			/*try
			{
				SuportTableDao suportTableDao = new SuportTableDao();
				ContentValues values = new ContentValues();
				values.put("WSLOCATION","http://desa.serviciosyconsultoria.com/PDAWebService/servicio/");
				suportTableDao.update("1", values);
				
			}
			catch(Exception ex)
			{}*/
			//Solo para probar cambiar el wslocation para produccion
			/*
			try
			{
				SuportTableDao suportTableDao = new SuportTableDao();
				ContentValues values = new ContentValues();
				values.put("WSLOCATION","https://sistemas.seguridad.mendoza.gov.ar/PDAWebService/servicio/");
				suportTableDao.update("1", values);
				
			}
			catch(Exception ex)
			{}
			*/
			// Not need to do anything for the first version.
/*			if(newVersion==2 && oldVersion<=1)
			{
				String versionPDA = "CATS50-0003";
				
			   	VersionDBDao  versionDBDao = new VersionDBDao();
			   	try
			   	{
			   	versionDBDao.createTable(db);
			   	} catch(Exception ex)
			   	{
			   		
			   	}
			   	
			   	String comentarios ="Agregado de Tabla para ZONA, actualizacion de Suport table para guardar varios datos";
			   	VersionDB versionDB = new VersionDB(oldVersion, newVersion, comentarios, versionPDA);
			   	versionDBDao.insert(versionDB);
			   	
				//String sql ="CREATE TABLE T_ACTUALIZACION_DB (`_id`	INTEGER PRIMARY KEY,`TIPO`	TEXT DEFAULT 'SQL',`COMANDO` TEXT, `VERSION_PDA` TEXT, `FECHA_REGISTRO`	INTEGER,`FECHA_EJECUCION` INTEGER,`REALIZADO` DEFAULT 'N')";
			   	ActualizacionDBDao actualizacionDBDao = new ActualizacionDBDao();
			   	try
			   	{
				actualizacionDBDao.createTable(db);
			   	}
			   	catch(Exception ex)
			   	{
			   		
			   	}

				
				
			}
*/		}
	}
}
