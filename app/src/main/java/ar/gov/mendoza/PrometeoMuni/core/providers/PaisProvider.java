package ar.gov.mendoza.PrometeoMuni.core.providers;

import android.content.ContentValues;
import android.net.Uri;

import ar.gov.mendoza.PrometeoMuni.core.domain.Pais;

public class PaisProvider extends BaseProvider {
		
	public static final Uri CONTENT_URI = Uri.parse("content://ar.gov.mendoza.deviceactas.core.providers.PaisProvider");
	
	/*
	 * TABLA
	 */
	public static final String TABLE = "T_PAIS";

	/*
	 * CAMPOS COLUMNAS
	 */
	public static final String ID_PAIS = "_id";//"ID_ACTA_CONSTATACION";
	public static final String NOMBRE = "NOMBRE";

	
	public static final String SQLCreateTable = "CREATE TABLE "
    + TABLE + "(" + ID_PAIS + " TEXT PRIMARY KEY ," 
								           + NOMBRE + " TEXT "
								           + ")";
	
	public static final String SQLDropTable ="DROP TABLE IF EXISTS " + TABLE;

	
	
	public void Add(Pais pais)
	{
		//SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ID_PAIS, pais.getId());
		values.put(NOMBRE, pais.getNombre());
		//db.insert(TABLE,null, values);
		//db.close();
		//this.query(uri, projection, selection, selectionArgs, sortOrder);
		this.insert(CONTENT_URI, values);
		
		
	}
	
	@Override
	public boolean onCreate() {
		table = TABLE;
		return super.onCreate();
	}
}
