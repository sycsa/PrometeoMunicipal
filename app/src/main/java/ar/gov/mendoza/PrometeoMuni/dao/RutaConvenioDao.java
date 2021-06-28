package ar.gov.mendoza.PrometeoMuni.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;

import ar.gov.mendoza.PrometeoMuni.core.domain.RutaConvenio;


public class RutaConvenioDao extends BaseDao<RutaConvenio>
{

	public RutaConvenioDao(){//Class<Provincia> class1) {
		super(RutaConvenio.class);//class1);
	}
	
	
	public Boolean EstaEnRangoConvenio(String pConvenio ,Integer pIdRuta,String pKm)
	{
		
		Boolean bResultado = false;
		
		
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder(); 
		queryBuilder.setTables("T_RUTA_CONVENIO"); 
		queryBuilder.appendWhere("CONVENIO = '" + pConvenio + "' ");
		queryBuilder.appendWhere(" AND ID_RUTA = " +  pIdRuta);
		queryBuilder.appendWhere(" AND " + pKm + " BETWEEN CAST(KM_INI as integer)  AND CAST(KM_FIN as integer) ");
        String[] asColumnsToReturn = {
                "_id",
                "ID_RUTA",
                "CONVENIO",
                "KM_INI",
                "KM_FIN"
        };
		
		
		String strSortOrder = "_id"; 
		Cursor cursor = queryBuilder.query(mDB, asColumnsToReturn,null, null, null, null,strSortOrder); 
		if(cursor != null && cursor.getCount() > 0)
		{
			while (cursor.moveToNext())
			{
				// si encuentra tan solo un registro...esta en convenio
				bResultado = true;
			}
			
		}
		
		return bResultado;
		
	}
	
	
}