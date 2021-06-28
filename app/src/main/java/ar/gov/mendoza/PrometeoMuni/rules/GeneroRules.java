package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import ar.gov.mendoza.PrometeoMuni.core.domain.Genero;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;

/*
	 * Business Rules for Genders
	 */
public class GeneroRules {

	private Context context;
	
	public GeneroRules (Context context) {
		this.context = context;
	}
	/*
	 * Get All Items of Countries from Databases
	 */
	public List<Genero> getAll()
	{
		List<Genero> lstGeneros = new ArrayList<Genero>();
		Genero genero ;
		genero = new Genero("01","Masculino");
		lstGeneros.add(genero);
		genero = new Genero("02","Femenino");
	    lstGeneros.add(genero);
		
		return lstGeneros;
	}
	
	public Genero getGeneroById(String pIdGenero)
	{
		Genero genero = new Genero();
		genero.setId(pIdGenero);
		if(pIdGenero.equals("01"))
		{
			genero.setNombre(GlobalVar.getInstance().getSuportTable().getMaleName());
		}
		else
		{
			genero.setNombre(GlobalVar.getInstance().getSuportTable().getFemaleName());
		}
		return genero;
	}
	
}
