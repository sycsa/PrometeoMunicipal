package ar.gov.mendoza.PrometeoMuni.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import ar.gov.mendoza.PrometeoMuni.core.domain.Equipo;
import ar.gov.mendoza.PrometeoMuni.core.domain.RutaEntidad;

public class RutaEntidadDao extends BaseDao<RutaEntidad>
{

	public RutaEntidadDao() {
		super(RutaEntidad.class);
	}
	public void Initialize(SQLiteDatabase db)
	{  
	}
	
	
	
	public Equipo getByImei(String imei) {
		Cursor cursor = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" select * ");
		sql.append(" from " + getTableName(Equipo.class));
		sql.append(" where IMEI = ?");
		
		String[] args = new String[] {imei};
		
		cursor = mDB.rawQuery(sql.toString(), args);

		Equipo equipo = new Equipo();
		
		if(cursor != null && cursor.getCount() > 0 && cursor.moveToFirst())
		{
		  equipo.setIdEquipo(cursor.getInt(cursor.getColumnIndex("_id")));
		  equipo.setImei(cursor.getString(cursor.getColumnIndex("IMEI")));
		  equipo.setNumeroSerie(cursor.getString(cursor.getColumnIndex("NUMERO_SERIE")));
		}
		
		return equipo;
	}
	
}
