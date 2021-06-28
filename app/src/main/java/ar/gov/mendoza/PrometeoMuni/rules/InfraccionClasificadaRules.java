package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import ar.gov.mendoza.PrometeoMuni.core.domain.InfraccionClasificada;
import ar.gov.mendoza.PrometeoMuni.dao.InfraccionClasificadaDao;

/*
	 * Regla de Negocio para Actas de Constatacion
	 */
public class InfraccionClasificadaRules {

	private Context context;
	public InfraccionClasificadaRules (Context context) {
		this.context = context;
	}
	
	public InfraccionClasificada getInfraccionById(Integer pIdInfraccion)
	{
		InfraccionClasificada infraccion = new InfraccionClasificada();
		InfraccionClasificadaDao infraccionDao = new InfraccionClasificadaDao();
		
		infraccion = infraccionDao.getItem(pIdInfraccion.toString());
		 
		return infraccion;
		
		
	}

	public InfraccionClasificada getInfraccionByIdClasificacion(Integer pIdInfraccion,String pClasificacion)
	{
		List<InfraccionClasificada> lstInfraccion = new  ArrayList<InfraccionClasificada>();
		InfraccionClasificadaDao infraccionDao = new InfraccionClasificadaDao();
		
		ContentValues pContentValues = new ContentValues();
		pContentValues.put("_id", pIdInfraccion);
		pContentValues.put("CLASIFICACION", pClasificacion);
		
		lstInfraccion = infraccionDao.getByFilter(pContentValues);
		
		 if(lstInfraccion.size()>0)
			 return lstInfraccion.get(0);	 
		 else
			 return null;
		
		
	}

}
