package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import ar.gov.mendoza.PrometeoMuni.core.domain.Marca;
import ar.gov.mendoza.PrometeoMuni.dao.MarcaDao;

/*
	 * Bussines Rules for Countries
	 */
public class MarcaRules {

	private Context context;
	
	public MarcaRules (Context context) {
		this.context = context;
	}
	/*
	 * Get All Items of Countries from Databases
	 */
	public List<Marca> getAll()
	{
		List<Marca> lstMarcas = new ArrayList<Marca>();
		MarcaDao marcaDao = new MarcaDao();
		String[] aSort = {"NOMBRE" }; 
		lstMarcas  = marcaDao.getAll(aSort);
		
		return lstMarcas;
		
	}
	
}
