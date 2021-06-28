package ar.gov.mendoza.PrometeoMuni.core.providers;

import android.net.Uri;

public class LocalidadProvider extends BaseProvider {
		
	public static final Uri CONTENT_URI = Uri.parse("content://ar.gov.mendoza.deviceactas.core.providers.LocalidadProvider");
	
	/*
	 * TABLA
	 */
	public static final String TABLE = "T_LOCALIDAD";

	/*
	 * CAMPOS COLUMNAS
	 */
	public static final String ID_LOCALIDAD = "_id";//"ID_DEPARTAMENTO";
	public static final String NOMBRE = "NOMBRE";
	public static final String ID_DEPARTAMENTO = "ID_DEPARTAMENTO";//"ID_DEPARTAMENTO";
	

	
	public static final String SQLCreateTable = "CREATE TABLE "
    + TABLE + "(" + ID_LOCALIDAD + " TEXT PRIMARY KEY ," 
								           + NOMBRE + " TEXT, "
								           + ID_DEPARTAMENTO + " TEXT "
								           + ")";
	
	public static final String SQLDropTable ="DROP TABLE IF EXISTS " + TABLE;

	
	/*
	public void Add(Pais pais)
	{
		ContentValues values = new ContentValues();
		values.put(ID_PAIS, pais.getId());
		values.put(NOMBRE, pais.getNombre());
		this.insert(CONTENT_URI, values);
	}
	*/
	@Override
	public boolean onCreate() {
		table = TABLE;
		return super.onCreate();
	}
}
