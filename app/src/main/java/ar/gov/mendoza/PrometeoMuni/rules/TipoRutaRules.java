package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import ar.gov.mendoza.PrometeoMuni.core.domain.TipoRuta;
import ar.gov.mendoza.PrometeoMuni.dao.TipoRutaDao;

/*
	 * Business Rules for Countries
	 */
public class TipoRutaRules {

	private Context context;
	
	public TipoRutaRules (Context context) {
		this.context = context;
	}
	/*
	 * Get All Items of TipoRuta from Databases
	 */
	public List<TipoRuta> getAll()
	{
		List<TipoRuta> lstTiposRuta= new ArrayList<TipoRuta>();
		TipoRutaDao tipoRutaDao = new TipoRutaDao();
		String[] aSort = {"NOMBRE" }; 
		lstTiposRuta = tipoRutaDao.getAll(aSort);
		
		return lstTiposRuta;
		
	}
	
}
