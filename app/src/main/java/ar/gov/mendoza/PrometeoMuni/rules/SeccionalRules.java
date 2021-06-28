package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import ar.gov.mendoza.PrometeoMuni.core.domain.Seccional;
import ar.gov.mendoza.PrometeoMuni.dao.SeccionalDao;

	/*
	 * Business Rules for Entity
	 */
public class SeccionalRules {

	private Context context;
	
	public SeccionalRules (Context context) {
		this.context = context;
	}
	/*
	 * Get All Items of Entities from Databases
	 */
	public List<Seccional> getAll()
	{
		List<Seccional> lstPaises = new ArrayList<Seccional>();
		SeccionalDao seccionalDao = new SeccionalDao();
		String[] aSort = {"_id" };
		lstPaises  = seccionalDao.getAll(aSort);
		
		return lstPaises;
	}
	public Seccional getSeccionalById(String pIdSeccional)
	{
		Seccional entidad = new Seccional();
		SeccionalDao entidadDao = new SeccionalDao();
		
		entidad = entidadDao.getItem(pIdSeccional.toString());
		 
		return entidad;
	}
	
	
	
}
