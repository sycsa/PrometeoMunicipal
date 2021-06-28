package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import ar.gov.mendoza.PrometeoMuni.core.domain.Color;
import ar.gov.mendoza.PrometeoMuni.dao.ColorDao;

/*
	 * Bussines Rules for Countries
	 */
public class ColorRules {

	private Context context;
	
	public ColorRules (Context context) {
		this.context = context;
	}
	/*
	 * Get All Items of Countries from Databases
	 */
	public List<Color> getAll()
	{
		List<Color> lstColores = new ArrayList<Color>();
		ColorDao colorDao = new ColorDao();
		String[] aSort = {"NOMBRE" }; 
		lstColores= colorDao.getAll(aSort);
		
		return lstColores;
		
	}
	
}
