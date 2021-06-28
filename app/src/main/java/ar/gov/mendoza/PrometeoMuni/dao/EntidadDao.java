package ar.gov.mendoza.PrometeoMuni.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import ar.gov.mendoza.PrometeoMuni.core.domain.Entidad;

public class EntidadDao extends BaseDao<Entidad>
{

	public EntidadDao() {
		super(Entidad.class);
	}
	public void Initialize(SQLiteDatabase db)
	{  
	}
	
	public List<Entidad> getEntidadesSegunConfiguracion(String pIdRuta,String pKm,String pParidad,String pMesActual)
	{
		List<Entidad> lstEntidades = new ArrayList<Entidad>();
		
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder(); 
		queryBuilder.setTables("T_ENTIDAD,T_RUTA_ENTIDAD"); 
		queryBuilder.appendWhere("T_ENTIDAD._id = T_RUTA_ENTIDAD.ID_ENTIDAD");
		queryBuilder.appendWhere(" AND T_RUTA_ENTIDAD.ID_RUTA = " +  pIdRuta);
		queryBuilder.appendWhere(" AND " + pKm + " BETWEEN CAST(T_RUTA_ENTIDAD.KM_INI as integer)  AND CAST(T_RUTA_ENTIDAD.KM_FIN as integer) ");
		//queryBuilder.appendWhere(" AND (T_RUTA_ENTIDAD.PARIDAD = '" + pParidad + "' OR T_RUTA_ENTIDAD.PARIDAD IS NULL OR (instr(T_RUTA_ENTIDAD.MESES,'" + pMesActual + "')> 0)) ");
		queryBuilder.appendWhere(" AND (T_RUTA_ENTIDAD.PARIDAD = '" + pParidad + "' OR T_RUTA_ENTIDAD.PARIDAD IS NULL OR T_RUTA_ENTIDAD.PARIDAD = '3') ");
        String[] asColumnsToReturn = {
                "T_ENTIDAD._id",
                "T_ENTIDAD.NOMBRE",
                "T_RUTA_ENTIDAD.MESES",
                "T_RUTA_ENTIDAD.PARIDAD"
        };
		String strSortOrder = "T_ENTIDAD.NOMBRE"; 
		Cursor cursor = queryBuilder.query(mDB, asColumnsToReturn,null, null, null, null,strSortOrder); 
		if(cursor != null && cursor.getCount() > 0)
		{
			while (cursor.moveToNext())
			{
				// como la funcion instr del sqlite no funciona en la version de android se hace la verificacion via java   
				//OR (instr(T_RUTA_ENTIDAD.MESES,'" + pMesActual + "')> 0)
				String sMeses = cursor.getString(cursor.getColumnIndex("MESES"));
				String sParidad = cursor.getString(cursor.getColumnIndex("PARIDAD"));
				if ( sParidad==null || (sParidad!=null && sParidad.equals(pParidad)) || (sParidad!=null && sMeses.length()>0 && sParidad.equals("3") && sMeses.indexOf(pMesActual) != -1))
				{
					Entidad entidad = new Entidad();
					entidad.setId(cursor.getInt(cursor.getColumnIndex("_id")));
					entidad.setNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
					lstEntidades.add(entidad);
				}
			}
			
		}
		
		return lstEntidades;
		
	}

}
