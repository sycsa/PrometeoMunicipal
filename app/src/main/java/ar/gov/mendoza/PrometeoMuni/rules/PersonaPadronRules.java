package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import ar.gov.mendoza.PrometeoMuni.dao.PersonaPadronDao;
import ar.gov.mendoza.PrometeoMuni.core.domain.PersonaPadron;

	/*
	 * Bussines Rules for Countries
	 */
public class PersonaPadronRules {

	private Context context;
	
	public PersonaPadronRules (Context context) {
		this.context = context;
	}
	
	/*
	 * Get All Items of Domain from Databases
	 */
	public List<PersonaPadron> getAll()
	{
		List<PersonaPadron> lstPersonas = new ArrayList<PersonaPadron>();
		PersonaPadronDao personaDao = new PersonaPadronDao();
		String[] aSort = {"NOMBRE" }; 
		lstPersonas= personaDao.getAll(aSort);
		
		return lstPersonas;
	}
	
	/*
	 * Get All Items of PersonaPadron from Databases
	 */
	public PersonaPadron getByNumeroDocumento (String pNumeroDocumento)
	{
		List<PersonaPadron> lstPersonasPadron = new ArrayList<PersonaPadron>();
		PersonaPadronDao personaPadronDao = new PersonaPadronDao();
		ContentValues cvalues = new ContentValues();
		
		cvalues.put("NUMERO_DOCUMENTO", pNumeroDocumento);
		
		lstPersonasPadron  = personaPadronDao.getByFilter(cvalues);
		
		if (lstPersonasPadron.isEmpty())
			return null;
		else
			return lstPersonasPadron.get(0);
		
	}
	

}
