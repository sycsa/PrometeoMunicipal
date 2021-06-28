package ar.gov.mendoza.PrometeoMuni.core.providers;

import android.net.Uri;

public class ProvinciaProvider extends BaseProvider {
		
	public static final Uri CONTENT_URI = Uri.parse("content://ar.gov.mendoza.deviceactas.core.providers.ProvinciaProvider");
	
	/*
	 * TABLA
	 */
	public static final String TABLE = "T_PROVINCIA";

	/*
	 * CAMPOS COLUMNAS
	 */
	public static final String ID_PROVINCIA = "_id";//"ID_ACTA_CONSTATACION";
	public static final String NOMBRE = "NOMBRE";
	public static final String ID_PAIS = "ID_PAIS";

	
	public static final String SQLCreateTable = "CREATE TABLE "
    + TABLE + "(" + ID_PROVINCIA + " TEXT PRIMARY KEY ," 
								           + NOMBRE + " TEXT, "
								           + ID_PAIS + " TEXT "
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
