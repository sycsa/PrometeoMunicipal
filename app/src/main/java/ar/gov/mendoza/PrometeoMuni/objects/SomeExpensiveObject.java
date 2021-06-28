package ar.gov.mendoza.PrometeoMuni.objects;

import java.util.ArrayList;
import java.util.List;

import ar.gov.mendoza.PrometeoMuni.core.domain.Color;
import ar.gov.mendoza.PrometeoMuni.core.domain.Departamento;
import ar.gov.mendoza.PrometeoMuni.core.domain.Entidad;
import ar.gov.mendoza.PrometeoMuni.core.domain.Genero;
import ar.gov.mendoza.PrometeoMuni.core.domain.InfraccionNomenclada;
import ar.gov.mendoza.PrometeoMuni.core.domain.Localidad;
import ar.gov.mendoza.PrometeoMuni.core.domain.Marca;
import ar.gov.mendoza.PrometeoMuni.core.domain.Pais;
import ar.gov.mendoza.PrometeoMuni.core.domain.Provincia;
import ar.gov.mendoza.PrometeoMuni.core.domain.Ruta;
import ar.gov.mendoza.PrometeoMuni.core.domain.Seccional;
import ar.gov.mendoza.PrometeoMuni.core.domain.TipoDocumento;
import ar.gov.mendoza.PrometeoMuni.core.domain.TipoRuta;
import ar.gov.mendoza.PrometeoMuni.core.domain.TipoVehiculo;
import ar.gov.mendoza.PrometeoMuni.rules.ColorRules;
import ar.gov.mendoza.PrometeoMuni.rules.DepartamentoRules;
import ar.gov.mendoza.PrometeoMuni.rules.EntidadRules;
import ar.gov.mendoza.PrometeoMuni.rules.GeneroRules;
import ar.gov.mendoza.PrometeoMuni.rules.MarcaRules;
import ar.gov.mendoza.PrometeoMuni.rules.PaisRules;
import ar.gov.mendoza.PrometeoMuni.rules.SeccionalRules;
import ar.gov.mendoza.PrometeoMuni.rules.TipoDocumentoRules;
import ar.gov.mendoza.PrometeoMuni.rules.TipoRutaRules;
import ar.gov.mendoza.PrometeoMuni.rules.TipoVehiculoRules;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;

public class SomeExpensiveObject {
	/**
	  *  The constructor of SomeExpensiveObject does nothing, 
	  *  but sleeps for 5 seconds before returning the call.  
	  *  This signifies that its really expensive to create an
	  *  instance of this class.  
	  */
	
	

	public List<TipoRuta> lstTiposRuta;
	public List<Ruta> lstRutas;
	public List<Entidad>  lstEntidades;
	public List<Seccional>  lstSeccionales;

	public List<Departamento> lstDepartamentosInfraccion;
	public List<Localidad> lstLocalidadesInfraccion;

	public List<Pais> lstPaisesLicencia;
	public List<Provincia> lstProvinciasLicencia;
	public List<Departamento> lstDepartamentosLicencia;
	public List<Localidad> lstLocalidadesLicencia;

	public List<Pais> lstPaisesDocumento;
	public List<Provincia> lstProvinciasDocumento;
	public List<Departamento> lstDepartamentosDocumento;
	public List<Localidad> lstLocalidadesDocumento;
	
	public List<Pais> lstPaisesDocumentoTitular;
	public List<Provincia> lstProvinciasDocumentoTitular;
	public List<Departamento> lstDepartamentosDocumentoTitular;
	public List<Localidad> lstLocalidadesDocumentoTitular;

	public List<Pais> lstPaisesDocumentoTestigo;
	public List<Provincia> lstProvinciasDocumentoTestigo;
	public List<Departamento> lstDepartamentosDocumentoTestigo;
	public List<Localidad> lstLocalidadesDocumentoTestigo;
	
	public InfraccionNomenclada infraccionNomenclada1;
	public InfraccionNomenclada infraccionNomenclada2;
	
	public List<TipoDocumento> lstTiposDocumento;
	public List<Genero> lstGeneros;
	public List<TipoVehiculo> lstTiposVehiculo;
	
	public List<Marca> lstMarcas;
	public List<Color> lstColores;
	
	private List<Color> getListColoresInitialized()
	{
		
		ColorRules coloresRules = new ColorRules(GlobalStateApp.getInstance().getApplicationContext());
		List<Color> list = coloresRules.getAll();//Pais.All();
		
		Color pColor = new Color(null,"Seleccione un Color");
		list.add(pColor);
		pColor = new Color(-1,"NO APARECE EN LA LISTA (Lo Escribire Manualmente)");
		list.add(0,pColor);
		
		return list;
	}

	private List<Marca> getListMarcasInitialized()
	{
		
		MarcaRules marcasRules = new MarcaRules(GlobalStateApp.getInstance().getApplicationContext());
		List<Marca> list = marcasRules.getAll();//Pais.All();
		
		Marca pMarca = new Marca(null,"Seleccione una Marca");
		list.add(pMarca);
		pMarca = new Marca(-1,"NO APARECE EN LA LISTA (Lo Escribire Manualmente)");
		list.add(0,pMarca);

		return list;
	}
	
	private List<TipoRuta> getListTiposRutaInitialized()
	{
		
		TipoRutaRules tipoRutaRules = new TipoRutaRules(GlobalStateApp.getInstance().getApplicationContext());
		List<TipoRuta> list = tipoRutaRules.getAll();//Pais.All();
		
		TipoRuta pTipoRuta = new TipoRuta("","Seleccione un Item");
		list.add(pTipoRuta);
		
		return list;
	}

	private List<Departamento> getListDepartamentosInfraccionInitialized()
	{
		
		DepartamentoRules departamentoRules = new DepartamentoRules(GlobalStateApp.getInstance().getApplicationContext());
		List<Departamento> list = departamentoRules.getByProvincia("12");//Pais.All();
		
		Departamento pDepartamento = new Departamento(-1,"Seleccione un Item","");
		list.add(pDepartamento);
		
		return list;
	}

	private List<Ruta> getListRutasInitialized()
	{

		List<Ruta> list = new ArrayList<Ruta>();
		Ruta pRuta = new Ruta("","Seleccione un Item","");
		list.add(pRuta);
		return list;
	}
	private List<Localidad> getListLocalidadesInfraccionInitialized()
	{

		List<Localidad> list = new ArrayList<Localidad>();
		Localidad pLocalidad = new Localidad(-1,"Seleccione un Item","");
		list.add(pLocalidad);
		return list;
	}


	private List<Genero> getListGenerosInitialized()
	{			
		GeneroRules generoRules = new GeneroRules(GlobalStateApp.getInstance().getApplicationContext());
		List<Genero> lstGeneros = generoRules.getAll();
		Genero pGenero = new Genero("","Seleccione un Genero");
		lstGeneros.add(pGenero);
		return lstGeneros;
	}

	private List<TipoVehiculo> getListTiposVehiculoInitialized()
	{			
		TipoVehiculoRules tipoVehiculoRules = new TipoVehiculoRules(GlobalStateApp.getInstance().getApplicationContext());
		List<TipoVehiculo> lsttipoVehiculo = tipoVehiculoRules.getAll();
		TipoVehiculo pTipoVehiculo = new TipoVehiculo("","Seleccione un Tipo de Vehiculo");
		lsttipoVehiculo.add(pTipoVehiculo);
		return lsttipoVehiculo;
	}
	private List<TipoDocumento> getListTiposDocumentoInitialized()
	{			
		TipoDocumentoRules tipoDocumentoRules = new TipoDocumentoRules(GlobalStateApp.getInstance().getApplicationContext());
		List<TipoDocumento> lstTiposDocumento = tipoDocumentoRules.getAll();
		TipoDocumento pObjeto = new TipoDocumento("","Seleccione un Tipo de Documento");
		lstTiposDocumento.add(pObjeto);

		return lstTiposDocumento;
	}
	private List<Pais> getListPaisesInitialized()
	{
		
		PaisRules rulesPais = new PaisRules(GlobalStateApp.getInstance().getApplicationContext());
		List<Pais> lstPaises = rulesPais.getAll();
		Pais pPais = new Pais("1","ARGENTINA");
		lstPaises.add(pPais);
		  pPais = new Pais("-1","NO APARECE EN LA LISTA (Lo Escribire Manualmente)");
		lstPaises.add(0,pPais);
		return lstPaises;
	}
	
	private List<Entidad> getListEntidadesInitialized()
	{
		
		EntidadRules rulesEntidad = new EntidadRules(GlobalStateApp.getInstance().getApplicationContext());
		List<Entidad> lstEntidades = rulesEntidad.getAll();
		Entidad pEntidad = new Entidad(-1,"Seleccione un juzgado");
		lstEntidades.add(pEntidad);
		
		return lstEntidades;
	}
	private List<Seccional> getListSeccionalesInitialized()
	{
		
		SeccionalRules rulesSeccional= new SeccionalRules(GlobalStateApp.getInstance().getApplicationContext());
		List<Seccional> lstSeccional = rulesSeccional.getAll();
		Seccional pSeccional= new Seccional("-1","Seleccione una secretaria");
		lstSeccional.add(pSeccional);
		
		return lstSeccional;
	}	
	private List<Provincia> getListProvinciasInitialized()
	{
		List<Provincia> lstProvincias= new ArrayList<Provincia>();
		Provincia pProvincia = new Provincia("12","MENDOZA(init)", "1");
		lstProvincias.add(pProvincia);
		pProvincia = new Provincia("-1","NO APARECE EN LA LISTA (Lo Escribire Manualmente)", "-1");
		lstProvincias.add(0,pProvincia);
		
		return lstProvincias;
		
		
	}
	private List<Departamento> getListDepartamentosInitialized()
	{
		List<Departamento> lstDepartamentos= new ArrayList<Departamento>();
		Departamento pDepartamento = new Departamento(null,"Seleccione un Departamento", "12");
		lstDepartamentos.add(pDepartamento);
		pDepartamento = new Departamento(-1,"NO APARECE EN LA LISTA (Lo Escribire Manualmente)", "-1");
		lstDepartamentos.add(0,pDepartamento);
		
		return lstDepartamentos;
	}
	private List<Localidad> getListLocalidadesInitialized()
	{
		
		List<Localidad> lstLocalidades = new ArrayList<Localidad>();
		Localidad pLocalidad = new Localidad(null,"Seleccione una Localidad", "");
		lstLocalidades.add(pLocalidad);
		pLocalidad = new Localidad(-1,"NO APARECE EN LA LISTA (Lo Escribire Manualmente)","-1");
		lstLocalidades.add(0,pLocalidad);
		return lstLocalidades;
	}

	public  void reInitializeList(String pTipoLista,String pAreaDemografica)
	{
		if(pTipoLista.equals("LICENCIA"))
		{
			switch (pAreaDemografica) {
			case "PROVINCIA":
				 lstProvinciasLicencia = getListProvinciasInitialized();
				break;
			case "DEPARTAMENTO":
				 lstDepartamentosLicencia = getListDepartamentosInitialized();
				break;
			case "LOCALIDAD":
				 lstLocalidadesLicencia = getListLocalidadesInitialized();
				break;

			default:
				break;
			}
			
		}
		if(pTipoLista.equals("DOCUMENTO"))
		{
			switch (pAreaDemografica) {
			case "PROVINCIA":
				 lstProvinciasDocumento= getListProvinciasInitialized();
				break;
			case "DEPARTAMENTO":
				 lstDepartamentosDocumento= getListDepartamentosInitialized();
				break;
			case "LOCALIDAD":
				 lstLocalidadesDocumento = getListLocalidadesInitialized();
				break;


			default:
				break;
			}
			
		}
		if(pTipoLista.equals("DOCUMENTO_TITULAR"))
		{
			switch (pAreaDemografica) {
			case "PROVINCIA":
				 lstProvinciasDocumentoTitular= getListProvinciasInitialized();
				break;
			case "DEPARTAMENTO":
				 lstDepartamentosDocumentoTitular= getListDepartamentosInitialized();
				break;
			case "LOCALIDAD":
				 lstLocalidadesDocumentoTitular= getListLocalidadesInitialized();
				break;


			default:
				break;
			}
		}
		if(pTipoLista.equals("DOCUMENTO_TESTIGO"))
		{
			switch (pAreaDemografica) {
			case "PROVINCIA":
				 lstProvinciasDocumentoTestigo= getListProvinciasInitialized();
				break;
			case "DEPARTAMENTO":
				 lstDepartamentosDocumentoTestigo= getListDepartamentosInitialized();
				break;
			case "LOCALIDAD":
				 lstLocalidadesDocumentoTestigo= getListLocalidadesInitialized();
				break;


			default:
				break;
			}
		}		
		 
	}
	
	 public SomeExpensiveObject() {
	  
		try {
			
			lstTiposRuta = this.getListTiposRutaInitialized();
			lstRutas = this.getListRutasInitialized();
			
			lstDepartamentosInfraccion = this.getListDepartamentosInfraccionInitialized();
			lstLocalidadesInfraccion = this.getListLocalidadesInfraccionInitialized();
			
			lstLocalidadesDocumento = this.getListLocalidadesInitialized();
			lstLocalidadesLicencia = this.getListLocalidadesInitialized();
			lstLocalidadesDocumentoTitular = this.getListLocalidadesInitialized();
			lstLocalidadesDocumentoTestigo = this.getListLocalidadesInitialized();

			lstDepartamentosDocumento = this.getListDepartamentosInitialized();
			lstDepartamentosLicencia = this.getListDepartamentosInitialized();
			lstDepartamentosDocumentoTitular = this.getListDepartamentosInitialized();
			lstDepartamentosDocumentoTestigo = this.getListDepartamentosInitialized();
			
			lstProvinciasDocumento= this.getListProvinciasInitialized();
			lstProvinciasLicencia = this.getListProvinciasInitialized();
			lstProvinciasDocumentoTitular = this.getListProvinciasInitialized();
			lstProvinciasDocumentoTestigo = this.getListProvinciasInitialized();
			
			lstPaisesDocumento =  this.getListPaisesInitialized();
			lstPaisesLicencia = this.getListPaisesInitialized();
			lstPaisesDocumentoTitular = this.getListPaisesInitialized();
			lstPaisesDocumentoTestigo = this.getListPaisesInitialized();			
			
			lstEntidades = this.getListEntidadesInitialized();
			lstSeccionales = this.getListSeccionalesInitialized();

			lstTiposDocumento = this.getListTiposDocumentoInitialized();
			
			lstGeneros = this.getListGenerosInitialized();
			
			lstTiposVehiculo = this.getListTiposVehiculoInitialized();
			
			
			lstMarcas  = this.getListMarcasInitialized();
			
			lstColores = this.getListColoresInitialized();
			
			//Thread.sleep(500);
		}
		 /*  catch (InterruptedException e) {
			//e.printStackTrace();
		}*/ 
		catch(Exception exc)
		{
			
		}
	 }

}
