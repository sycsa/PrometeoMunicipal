package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;

import ar.gov.mendoza.PrometeoMuni.core.domain.Entidad;
import ar.gov.mendoza.PrometeoMuni.dao.EntidadDao;

/*
	 * Business Rules for Entity
	 */
public class EntidadRules {

	private Context context;
	
	public EntidadRules (Context context) {
		this.context = context;
	}
	/*
	 * Get All Items of Entities from Databases
	 */
	public List<Entidad> getAll()
	{
		List<Entidad> lstPaises = new ArrayList<Entidad>();
		EntidadDao entidadDao = new EntidadDao();
		String[] aSort = {"NOMBRE" }; 
		lstPaises  = entidadDao.getAll(aSort);
		
		return lstPaises;
	}
	public Entidad getEntidadById(Integer pIdEntidad)
	{
		Entidad entidad = new Entidad();
		EntidadDao entidadDao = new EntidadDao();
		
		entidad = entidadDao.getItem(pIdEntidad.toString());
		 
		return entidad;
	}
	
	/*
	 * Get All Items of Entities from Databases Based on  Route and KM
	 */
	public List<Entidad> getByRutaKM (String pIdRuta,String pKm)
	{
		List<Entidad> lstEntidades = new ArrayList<Entidad>();
		
		String pParidad = "0";
		String pMesActual = "00";
		
		Calendar now = Calendar.getInstance();
		int iMesActual =now.get(Calendar.MONTH) + 1;
		
		pMesActual = String.valueOf(iMesActual);
		if (iMesActual<=9)
			pMesActual ="0" + iMesActual; 
		
		if ((iMesActual % 2)==0)
			pParidad = "1";
		else
			pParidad = "0";
		
		EntidadDao entidadDao = new EntidadDao();
		
		lstEntidades =	entidadDao.getEntidadesSegunConfiguracion(pIdRuta,pKm,pParidad,pMesActual);

		
		return lstEntidades;
		
	}

	public Integer getPosicionById(Integer juzgado)
	{
		List<Entidad> listadoJuzgados = getAll();
		//Entidad entidad = getEntidadById(juzgado);
		//listadoJuzgados.indexOf(entidad);
		Integer i = 0;
		while (!listadoJuzgados.get(i).getId().equals(juzgado)) {
			i++;
		}
		return i;
	}

	
}
