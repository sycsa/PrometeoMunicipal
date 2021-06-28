package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import ar.gov.mendoza.PrometeoMuni.core.domain.Equipo;
import ar.gov.mendoza.PrometeoMuni.dao.EquipoDao;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;

/*
	 * Bussines Rules for Countries
	 */
public class EquipoRules {

	private Context context;
	
	public EquipoRules (Context context) {
		this.context = context;
	}
	
	/*
	 * Get All Items of Countries from Databases
	 */
	public List<Equipo> getAll()
	{
		List<Equipo> lstEquipos= new ArrayList<Equipo>();
		EquipoDao equipoDao = new EquipoDao();
		String[] aSort = {"NOMBRE" }; 
		lstEquipos= equipoDao.getAll(aSort);
		
		return lstEquipos;
	}
	
	public Equipo getByImei(String pImei)
	{
		//pImei = "355177060242523"; // valores para usar en pruebas
		
		Equipo equipo = new Equipo();
		EquipoDao equipoDao = new EquipoDao();
		equipo = equipoDao.getByImei(pImei);

		//equipo.setIdEquipo(997); //valores por defecto para pruebas
		return equipo;
		
	}
	
	public String getNumeroSerie()
	{
		//pImei = "355177060242523"; // valores para usar en pruebas
		
		 String imei = GlobalVar.getInstance().getImei();
 	    String numeroSerie = "";
 	    EquipoDao equipoDao = new EquipoDao();
 	    Equipo equipo  = equipoDao.getByImei(imei);
 	    numeroSerie = equipo.getNumeroSerie();
	 	    
		return numeroSerie;
		
	}
	public String getLetraSerie()
	{
		//pImei = "355177060242523"; // valores para usar en pruebas
		
		 String imei = GlobalVar.getInstance().getImei();
 	    String letraSerie = "";
 	    EquipoDao equipoDao = new EquipoDao();
 	    Equipo equipo  = equipoDao.getByImei(imei);
 	    letraSerie = equipo.getLetraSerie();
	 	    
		return letraSerie;
		
	}

	public int getMunicipio(){
		String imei = GlobalVar.getInstance().getImei();
		EquipoDao equipoDao = new EquipoDao();
		Equipo equipo  = equipoDao.getByImei(imei);
		int idMunicipio = equipo.getIdDepartamento();

		return idMunicipio;
	}

	public void setIdEquipoPrueba(String pImei)
	{
		
		ContentValues cv = new ContentValues();
		cv.put("IMEI", pImei);
		cv.put("HABILITADO", "S");
		cv.put("NUMERODESERIE", 999);
		cv.put("LETRADESERIE", "Z");
		EquipoDao suportTableDao = new EquipoDao();
		suportTableDao.update("999", cv);
		

	}
}
