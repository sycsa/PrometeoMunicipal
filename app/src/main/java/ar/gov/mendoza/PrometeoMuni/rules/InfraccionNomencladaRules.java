package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import ar.gov.mendoza.PrometeoMuni.core.domain.InfraccionNomenclada;
import ar.gov.mendoza.PrometeoMuni.dao.InfraccionNomencladaDao;

/*
	 * Regla de Negocio para Actas de Constatacion
	 */
public class InfraccionNomencladaRules {

	private Context context;
	public InfraccionNomencladaRules (Context context) {
		this.context = context;
	}
	
	public InfraccionNomenclada getInfraccionById(Integer pIdInfraccion)
	{
		InfraccionNomenclada infraccion = new InfraccionNomenclada();
		InfraccionNomencladaDao infraccionDao = new InfraccionNomencladaDao();
		
		infraccion = infraccionDao.getItem(pIdInfraccion.toString());
		 
		return infraccion;
	}

	public InfraccionNomenclada getInfraccionByCodigo(String pCodigoInfraccion)
	{
		List<InfraccionNomenclada> lstInfraccion = new  ArrayList<InfraccionNomenclada>();
		InfraccionNomencladaDao infraccionDao = new InfraccionNomencladaDao();
		
		ContentValues pContentValues = new ContentValues();
		pContentValues.put("CODIGO", pCodigoInfraccion);
		
		lstInfraccion = infraccionDao.getByFilter(pContentValues);
		
		 if(lstInfraccion.size()>0)
			 return lstInfraccion.get(0);	 
		 else
			 return null;
		
		
	}
}
