package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import ar.gov.mendoza.PrometeoMuni.core.domain.ActaConstatacion;
import ar.gov.mendoza.PrometeoMuni.core.domain.SuportTable;
import ar.gov.mendoza.PrometeoMuni.dao.ActaConstatacionDao;
import ar.gov.mendoza.PrometeoMuni.dao.SuportTableDao;

/*
	 * Bussines Rules for Countries
	 */
public class SuportTableRules {

	private Context context;
	
	public SuportTableRules (Context context) {
		this.context = context;
	}
	/*
	 * Get All Items of Countries from Databases
	 */
	public SuportTable getSuportTable()
	{
		SuportTableDao suportTableDao = new SuportTableDao();
		return suportTableDao.getSuportTable();
	}

	

	public void setZona(String pZona)
	{
		
		ContentValues cv = new ContentValues();
		cv.put("ID_ZONA", pZona);
		SuportTableDao suportTableDao = new SuportTableDao();
		suportTableDao.update("1", cv);
	}
	public void setupVariables()
	{
		// UltimaActa
		// Actas Pendientes
	    ActaConstatacionDao actaDao = new ActaConstatacionDao();
	     long ultimo_nro_acta = actaDao.getNextId() -1;
	     List<ActaConstatacion> lstActas = actaDao.getAll();
		 int actasNoSincronizadas = lstActas.size();
		
		ContentValues cv = new ContentValues();
		cv.put("ACTAS_NO_SINCRONIZADAS", actasNoSincronizadas);
		cv.put("ULTIMO_NUMERO_ACTA", ultimo_nro_acta);
		SuportTableDao suportTableDao = new SuportTableDao();
		suportTableDao.update("1", cv);
	}
	
}
