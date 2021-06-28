package ar.gov.mendoza.PrometeoMuni.core.providers;

import android.net.Uri;

public class VersionProvider extends BaseProvider {
		
	public static final Uri CONTENT_URI = Uri.parse("content://ar.gov.mendoza.deviceactas.core.providers.VersionProvider");
	
	/*
	 * TABLA
	 */
	public static final String TABLE = "T_VERSION";

	/*
	 * CAMPOS COLUMNAS
	 */
	public static final String ID_VERSION = "_id";//"ID_ACTA_CONSTATACION";
	public static final String NOMBRE = "NOMBRE";

	
	public static final String SQLCreateTable = "CREATE TABLE "
    + TABLE + "(" + ID_VERSION + " TEXT PRIMARY KEY ," 
								           + NOMBRE + " TEXT "
								           + ")";
	
	public static final String SQLDropTable ="DROP TABLE IF EXISTS " + TABLE;


	@Override
	public boolean onCreate() {
		table = TABLE;
		return super.onCreate();
	}
}
