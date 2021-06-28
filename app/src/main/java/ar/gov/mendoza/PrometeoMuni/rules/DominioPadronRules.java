package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import ar.gov.mendoza.PrometeoMuni.core.domain.DominioPadron;
import ar.gov.mendoza.PrometeoMuni.dao.DominioPadronDao;

/*
	 * Bussines Rules for Countries
	 */
public class DominioPadronRules {

	private Context context;
	
	public DominioPadronRules (Context context) {
		this.context = context;
	}
	
	/*
	 * Get All Items of Domain from Databases
	 */
	public List<DominioPadron> getAll()
	{
		List<DominioPadron> lstDominios = new ArrayList<DominioPadron>();
		DominioPadronDao dominioDao = new DominioPadronDao();
		String[] aSort = {"NOMBRE" }; 
		lstDominios= dominioDao.getAll(aSort);
		
		return lstDominios;
	}
	
	/*
	 * Get All Items of PersonaPadron from Databases
	 */
	public DominioPadron getByDominio (String pDominio)
	{
		List<DominioPadron> lstDominioPadron = new ArrayList<DominioPadron>();
		DominioPadronDao dominioPadronDao = new DominioPadronDao();
		ContentValues cvalues = new ContentValues();
		
		cvalues.put("DOMINIO", pDominio);
		
		lstDominioPadron  = dominioPadronDao.getByFilter(cvalues);
		
		if(lstDominioPadron.isEmpty())
			return null;
		else
			return lstDominioPadron.get(0);
		
	}
	

}
