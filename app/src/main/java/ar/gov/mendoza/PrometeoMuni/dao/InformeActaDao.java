package ar.gov.mendoza.PrometeoMuni.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;

import ar.gov.mendoza.PrometeoMuni.core.domain.InformeActa;
import ar.gov.mendoza.PrometeoMuni.core.util.Tools;


public class InformeActaDao extends BaseDao<InformeActa>
{

	public InformeActaDao(){//Class<Pais> class1) {
		super(InformeActa.class);//class1);
	}
	
	public List<InformeActa> getInformeActaByUsuarioyFecha(String pUsuario,Long pFechaHoraDesde,Long pFechaHoraHasta)
	{
		List<InformeActa> lstEntidades = new ArrayList<InformeActa>();
		
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder(); 
		queryBuilder.setTables("T_INFORME_ACTAS"); 
		queryBuilder.appendWhere("USUARIO LIKE '" + pUsuario + "'");
		queryBuilder.appendWhere(" AND FECHA_Y_HORA BETWEEN " + pFechaHoraDesde + " AND " + pFechaHoraHasta);

        String[] asColumnsToReturn = {
                "_id",
                "USUARIO",
                "FECHA_Y_HORA",
                "NUMERO_REFERENCIA",
                "ESTADO"
        };
		String strSortOrder = "FECHA_Y_HORA"; 
		Cursor cursor = queryBuilder.query(mDB, asColumnsToReturn,null, null, null, null,strSortOrder); 
		if(cursor != null && cursor.getCount() > 0)
		{
			while (cursor.moveToNext())
			{
					InformeActa informe = new InformeActa();
					
					informe.setId(cursor.getInt(cursor.getColumnIndex("_id")));
					informe.setUsuario(cursor.getString(cursor.getColumnIndex("USUARIO")));
					Long dateLong = cursor.getLong(cursor.getColumnIndex("FECHA_Y_HORA"));
					Date date = Tools.ConvertLongToDate(dateLong);
					informe.setFechayHora(date);
					informe.setNumeroReferencia(cursor.getString(cursor.getColumnIndex("NUMERO_REFERENCIA")));
					informe.setEstado(cursor.getString(cursor.getColumnIndex("ESTADO")));
					
					lstEntidades.add(informe);
			}
			
		}
		
		return lstEntidades;
		
	}
}

