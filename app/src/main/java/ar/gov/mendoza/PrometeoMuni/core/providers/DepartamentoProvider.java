package ar.gov.mendoza.PrometeoMuni.core.providers;

import android.net.Uri;

public class DepartamentoProvider extends BaseProvider {
		
	public static final Uri CONTENT_URI = Uri.parse("content://ar.gov.mendoza.deviceactas.core.providers.DepartamentoProvider");
	
	/*
	 * TABLA
	 */
	public static final String TABLE = "T_DEPARTAMENTO";

	/*
	 * CAMPOS COLUMNAS
	 */
	public static final String ID_DEPARTAMENTO = "_id";//"ID_DEPARTAMENTO";
	public static final String NOMBRE = "NOMBRE";
	public static final String ID_PROVINCIA = "ID_PROVINCIA";

	
	public static final String SQLCreateTable = "CREATE TABLE "
    + TABLE + "(" + ID_DEPARTAMENTO + " TEXT PRIMARY KEY ," 
								           + NOMBRE + " TEXT, "
								           + ID_PROVINCIA + " TEXT "
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
