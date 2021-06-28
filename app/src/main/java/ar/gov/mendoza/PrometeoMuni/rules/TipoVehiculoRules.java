package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import ar.gov.mendoza.PrometeoMuni.dao.TipoVehiculoDao;
import ar.gov.mendoza.PrometeoMuni.core.domain.TipoVehiculo;

	/*
	 * Business Rules for Genders
	 */
public class TipoVehiculoRules {

	private Context context;
	
	public TipoVehiculoRules (Context context) {
		this.context = context;
	}
	/*
	 * Get All Items of Countries from Databases
	 */
	public List<TipoVehiculo> getAll()
	{
		List<TipoVehiculo> lstTiposVehiculo = new ArrayList<TipoVehiculo>();
		TipoVehiculoDao tipoVehiculoDao = new TipoVehiculoDao();
		String[] aSort = {"_id" }; 
		
		lstTiposVehiculo = tipoVehiculoDao.getAll(aSort);
		
		return lstTiposVehiculo;
		
	}
	public TipoVehiculo getTipoVehiculoById(String pIdTipoVehiculo)
	{
		TipoVehiculo tipoVehiculo = new TipoVehiculo();
		TipoVehiculoDao tipoVehiculoDao = new TipoVehiculoDao();
		
		tipoVehiculo= tipoVehiculoDao.getItem(pIdTipoVehiculo);
		 
		return tipoVehiculo;
	}
	
	/*
	public TipoVehiculo getTipoVehiculoById(String pIdTipoVehiculo)
	{
		TipoVehiculo tipoVehiculo = new TipoVehiculo();
		tipoVehiculo .setId(pIdTipoVehiculo);
		if(pIdTipoVehiculo.equals("A"))
		{
			tipoVehiculo .setNombre("Automotor");
		}
		else if(pIdTipoVehiculo.equals("M"))
		{
			tipoVehiculo .setNombre("Motocicleta");
		}
		else if(pIdTipoVehiculo.equals("P"))
		{
			tipoVehiculo .setNombre("Peaton");
		}
		else if(pIdTipoVehiculo.equals("B"))
		{
			tipoVehiculo .setNombre("Bicicleta");
		}

		return tipoVehiculo ;
	}
	*/
}
