package ar.gov.mendoza.PrometeoMuni.rules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.zebra.android.zebrautilitiesmza.util.ImageHelper;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;


import ar.gov.mendoza.PrometeoMuni.core.domain.ActaConstatacion;
import ar.gov.mendoza.PrometeoMuni.core.domain.AndroidWidgetControl;
import ar.gov.mendoza.PrometeoMuni.core.domain.Equipo;
import ar.gov.mendoza.PrometeoMuni.core.domain.InfraccionClasificada;
import ar.gov.mendoza.PrometeoMuni.core.domain.InfraccionNomenclada;
import ar.gov.mendoza.PrometeoMuni.core.domain.TipoDocumento;
import ar.gov.mendoza.PrometeoMuni.core.util.Tools;
import ar.gov.mendoza.PrometeoMuni.dao.ActaConstatacionDao;
import ar.gov.mendoza.PrometeoMuni.dao.EquipoDao;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;
import ar.gov.mendoza.PrometeoMuni.sync.ActaConstatacionSync;
import ar.gov.mendoza.PrometeoMuni.utils.Filter;
import ar.gov.mendoza.PrometeoMuni.utils.FilterList;
import ar.gov.mendoza.PrometeoMuni.utils.Utilities;

	/*
	 * Regla de Negocio para Actas de Constatacion
	 */
public class ActaConstatacionRules {

	private Context context;
	
	public ActaConstatacionRules (Context context) {
		this.context = context;
	}
	
	/*
	 * Get All Items of Countries from Databases
	 */
	public List<ActaConstatacion> getAll()
	{
		List<ActaConstatacion> lstActas = new ArrayList<ActaConstatacion>();
		ActaConstatacionDao actaDao = new ActaConstatacionDao();
		String[] aSort =  {"NUMERO_ACTA" }; 
		lstActas= actaDao.getAll(aSort);
		
		return lstActas;
		
	}
	/*
	 * Get All Items of Countries from Databases
	 */
	public List<ActaConstatacion> getActasParaSincronizar()
	{
		List<ActaConstatacion> lstActas = new ArrayList<ActaConstatacion>();
		ActaConstatacionDao actaDao = new ActaConstatacionDao();
		String[] aSort =  {"NUMERO_ACTA" }; 
		lstActas= actaDao.getAll(aSort);
		
		 Filter<ActaConstatacion, String> filter = new Filter<ActaConstatacion,String>() {
	            public boolean isMatched(ActaConstatacion object, String elFiltro) {
	                return object.getSincronizada().equals(elFiltro);
	            }
	        };
	        
	        List<ActaConstatacion> sortEmpList =  new FilterList<String>().filterList(lstActas, filter,"N");
	       return sortEmpList;
		
	}
	
	public ActaConstatacion getActaById(Integer pId)
	{
		ActaConstatacion acta = new ActaConstatacion();
		ActaConstatacionDao actaDao = new ActaConstatacionDao();
		acta = actaDao.getItem(pId.toString());
		return acta;
	}
	public Boolean deleteNullItems()
	{
		ActaConstatacionDao actaDao = new ActaConstatacionDao();
		actaDao.deleteAll();
		return true;
	}
	/*
	 * 
	 */
	public List<AndroidWidgetControl> validarActa(ActaConstatacion pActa)
	{
		List<AndroidWidgetControl> lstRetorno = new ArrayList<AndroidWidgetControl>();
		String controlNombre = "";
		String sContent ="";
		String sMensaje ="";

		if (pActa.getIdDepartamentoInfraccion()==null ||  pActa.getIdDepartamentoInfraccion()==-1 || pActa.getIdDepartamentoInfraccion().equals("-1"))
		{
			sMensaje = "Debe Seleccionar un Departamento";
			sContent = sContent + sMensaje + "\n";
			lstRetorno.add(new AndroidWidgetControl("Spinner_DepartamentoInfraccion",sMensaje));
		}

		if (pActa.getIdLocalidadInfraccion() == null || pActa.getIdLocalidadInfraccion()==-1 || pActa.getIdLocalidadInfraccion().equals("-1"))
		{	sMensaje ="Debe Seleccionar una Localidad";
			sContent = sContent + sMensaje + "\n";
			lstRetorno.add(new AndroidWidgetControl("Spinner_LocalidadInfraccion",sMensaje));
		}

		if (pActa.getIdTipoRuta()==null ||  pActa.getIdTipoRuta().equals("") || pActa.getIdTipoRuta().equals("-1"))
		{
			sMensaje = "Debe Seleccionar un Lugar de Constatacion";
			sContent = sContent + sMensaje + "\n";
			lstRetorno.add(new AndroidWidgetControl("Spinner_TipoRuta",sMensaje));
		}

		// recordar que si el spinner no tiene nada cargado no podra ser cargado con el error
		if (pActa.getIdNumeroRuta() == null || pActa.getIdNumeroRuta().equals("") || pActa.getIdNumeroRuta().equals("-1"))
		{	sMensaje ="Debe Seleccionar la segunda Opcion de Lugar de Constatacion";
			sContent = sContent + sMensaje + "\n";
			lstRetorno.add(new AndroidWidgetControl("Spinner_Ruta",sMensaje));
		}

		if (pActa.getKm()==null || pActa.getKm().equals(""))
		{   sMensaje = "Debe Ingresar el Altura/KM";
			sContent = sContent + sMensaje + "\n";
			lstRetorno.add(new AndroidWidgetControl("EditText_Km",sMensaje));
		}
		//if (pActa.getDescripcionUbicacion()==null || pActa.getDescripcionUbicacion().equals(""))
		//	sContent = sContent + "Debe Ingresar la Descripcion de la Ubicacion" + "\n";


		if(pActa.getIdJuzgado()==null || pActa.getIdJuzgado().equals("") || pActa.getIdJuzgado().equals(-1))
		{   sMensaje = "Debe seleccionar un juzgado";
			sContent = sContent + sMensaje + "\n";
			lstRetorno.add(new AndroidWidgetControl("Spinner_Juzgado",sMensaje));
		}
		if(pActa.getIdSeccional()==null || pActa.getIdSeccional().equals(""))
		{   sMensaje = "Debe seleccionar una seccional";
			sContent = sContent + sMensaje + "\n";
			lstRetorno.add(new AndroidWidgetControl("Spinner_Seccional",sMensaje));
		}

		if (pActa.getTieneLicencia().equals("S"))
		{
				if (pActa.getNumeroLicencia()==null || pActa.getNumeroLicencia().equals(""))
				{
					sMensaje = "Debe Ingresar el Numero de Licencia";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_NumeroLicencia",sMensaje));
				}

				if (pActa.getClaseLicencia()==null || pActa.getClaseLicencia().equals(""))
				{   sMensaje = "Debe Ingresar la Categoria de la Licencia";
					sContent = sContent + sMensaje  + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_ClaseLicencia",sMensaje));
				}

				if (pActa.getFechaVencimientoLicencia()==null || pActa.getFechaVencimientoLicencia().equals(""))
				{   sMensaje = "Debe Ingresar la Fecha del Vencimiento de la Licencia";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_FVL_Info",sMensaje));
				}

				/* VALIDACION DATOS DEMOGRAFICOS DE LA LICENCIA */
				if (pActa.getIdPaisLicencia() == null || pActa.getIdPaisLicencia().equals(""))
				{   sMensaje = "Debe Seleccionar o Escribir el Pais de la Licencia";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("Spinner_PaisLicencia",sMensaje));

				}
				else if (pActa.getIdPaisLicencia().equals("-1") && (pActa.getPaisLicencia()==null || pActa.getPaisLicencia().equals("")) )
				{
					sMensaje = "Debe Escribir el Pais de la Licencia" ;
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_PaisLicencia",sMensaje));
				}
				/*------------------------------*/
				if (pActa.getIdProvinciaLicencia() == null || pActa.getIdProvinciaLicencia().equals(""))
				{
					sMensaje = "Debe Seleccionar o Escribir la Provincia de la Licencia";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("Spinner_ProvinciaLicencia",sMensaje));
				}
				else if (pActa.getIdProvinciaLicencia().equals("-1") && (pActa.getProvinciaLicencia()==null || pActa.getProvinciaLicencia().equals("")) )
				{
					sMensaje = "Debe Escribir la Provincia de la Licencia";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_ProvinciaLicencia",sMensaje));
				}
				/*------------------------------*/
				/*
				if (pActa.getIdDepartamentoLicencia() == null || pActa.getIdDepartamentoLicencia().equals(""))
				{
					sContent = sContent + "Debe Seleccionar o Escribir el Departamento de la Licencia" + "\n";
				}
				else if (pActa.getIdDepartamentoLicencia().equals(-1) && (pActa.getDepartamentoLicencia()==null || pActa.getDepartamentoLicencia().equals("")) )
				{
					sContent = sContent + "Debe Escribir el Departamento de la Licencia" + "\n";
				}
				*/
				/*------------------------------*/
				/*
				if (pActa.getIdLocalidadLicencia() == null || pActa.getIdLocalidadLicencia().equals(""))
				{
					sContent = sContent + "Debe Seleccionar o Escribir la Localidad de la Licencia" + "\n";
				}
				else if (pActa.getIdLocalidadLicencia().equals(-1) && (pActa.getLocalidadLicencia()==null || pActa.getLocalidadLicencia().equals("")) )
				{
					sContent = sContent + "Debe Escribir la Localidad de la Licencia" + "\n";
				}
				*/
		}

		if(pActa.getSinConductor()==null || pActa.getSinConductor().equals("N"))
		{
			if (pActa.getTipoDocumento()==null || pActa.getTipoDocumento().equals(""))
			{
				sMensaje ="Debe Ingresar el Tipo de Documento del Infractor";
				sContent = sContent + sMensaje + "\n";
				lstRetorno.add(new AndroidWidgetControl("Spinner_TipoDocumento",sMensaje));
			}
			
	        /*
			if (pActa.getIdSexo()==null || pActa.getIdSexo().equals(""))
				sContent = sContent + "Debe Ingresar el Sexo del Infractor" + "\n";
			*/
			if (pActa.getNumeroDocumento()==null || pActa.getNumeroDocumento().equals(""))
			{
				sMensaje = "Debe Ingresar el Numero de Documento del Infractor";
				sContent = sContent + sMensaje + "\n";
				lstRetorno.add(new AndroidWidgetControl("EditText_NumeroDocumento",sMensaje));
			}
			else
			{	if (pActa.getTipoDocumento()!=null && !pActa.getTipoDocumento().equals(""))
				{   	String sMensajeSalida =doValidarFormatoDocumento(pActa.getoTipoDocumento(),pActa.getNumeroDocumento());
						if(!sMensajeSalida.equals(""))
						{
							sMensaje = sMensajeSalida + " (Conductor)";
							sContent = sContent + sMensaje + "\n";
							lstRetorno.add(new AndroidWidgetControl("EditText_NumeroDocumento",sMensaje));
						}

				}
			}

			/*
			if (pActa.getFechaNacimiento()==null || pActa.getFechaNacimiento().equals(""))
				sContent = sContent + "Debe Ingresar la Fecha Nacimiento del Infractor" + "\n";
			*/

			if (pActa.getApellido()==null || pActa.getApellido().equals(""))
			{
				sMensaje ="Debe Ingresar el Apellido del Infractor";
				sContent = sContent + sMensaje + "\n";
				lstRetorno.add(new AndroidWidgetControl("EditText_Apellido",sMensaje));
			}

			if (pActa.getNombre()==null || pActa.getNombre().equals("")){
				sMensaje = "Debe Ingresar el Nombre del Infractor";
				sContent = sContent + sMensaje + "\n";
				lstRetorno.add(new AndroidWidgetControl("EditText_Nombre",sMensaje));
			}



			if ((pActa.getEsCallePublica()==null || pActa.getEsCallePublica()==false) &&  (pActa.getCalle()==null ||pActa.getCalle().equals("")))
			{   sMensaje = "Debe Ingresar la calle" ;
				sContent = sContent + sMensaje + "\n";
				lstRetorno.add(new AndroidWidgetControl("EditText_Calle",sMensaje));
			}

			if ((pActa.getEsAlturaSinNumero()==null || pActa.getEsAlturaSinNumero()==false) &&  ( pActa.getAltura()==null || pActa.getAltura().equals("")))
			{   sMensaje ="Debe Ingresar la altura";
				sContent = sContent + sMensaje + "\n";
				lstRetorno.add(new AndroidWidgetControl("EditText_Altura",sMensaje));
			}
			/*
			if (pActa.getBarrio()==null || pActa.getBarrio().equals(""))
				sContent = sContent + "Debe Ingresar el barrio" + "\n";
			*/
		}
		/* TITULAR */

		//if (pActa.getConductorEsTitular()==null || pActa.getConductorEsTitular().equals("N"))
		if(pActa.getSinTitular()==null || pActa.getSinTitular().equals("N"))
		{
				if (pActa.getTipoDocumentoTitular()==null || pActa.getTipoDocumentoTitular().equals(""))
				{	sMensaje = "Debe Ingresar el Tipo de Documento del Titular";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("Spinner_TipoDocumentoTitular",sMensaje));
				}
				
		        /*
				if (pActa.getIdSexoTitular()==null || pActa.getIdSexoTitular().equals(""))
					sContent = sContent + "Debe Ingresar el Sexo del Titular" + "\n";
				*/
				if (pActa.getNumeroDocumentoTitular()==null || pActa.getNumeroDocumentoTitular().equals(""))
				{	sMensaje = "Debe Ingresar el Numero de Documento del Titular" ;
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_NumeroDocumentoTitular",sMensaje));
				}
				else
				{	if (pActa.getTipoDocumentoTitular()!=null && !pActa.getTipoDocumentoTitular().equals(""))
					{   	String sMensajeSalida =doValidarFormatoDocumento(pActa.getoTipoDocumentoTitular(),pActa.getNumeroDocumentoTitular());
							if(!sMensajeSalida.equals(""))
							{   sMensaje = sMensajeSalida + " (Titular)";
								sContent = sContent + sMensaje +"\n";
								lstRetorno.add(new AndroidWidgetControl("EditText_NumeroDocumentoTitular",sMensaje));
							}
					}
				}
				
				/*
				if (pActa.getFechaNacimientoTitular()==null || pActa.getFechaNacimientoTitular().equals(""))
					sContent = sContent + "Debe Ingresar la Fecha Nacimiento del Titular" + "\n";
				*/

				if (pActa.getApellidoTitular()==null || pActa.getApellidoTitular().equals(""))
				{	sMensaje = "Debe Ingresar el Apellido del Titular";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_ApellidoTitular",sMensaje));
				}

				if (pActa.getNombreTitular()==null || pActa.getNombreTitular().equals(""))
				{	sMensaje = "Debe Ingresar el Nombre del Titular";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_NombreTitular",sMensaje));
				}


				if ((pActa.getEsCallePublicaTitular()==null || pActa.getEsCallePublicaTitular()==false) &&  (pActa.getCalleTitular()==null ||pActa.getCalleTitular().equals("")))
				{	sMensaje ="Debe Ingresar la calle del Titular";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_CalleTitular",sMensaje));
				}

				if ((pActa.getEsAlturaSinNumeroTitular()==null || pActa.getEsAlturaSinNumeroTitular()==false) &&  ( pActa.getAlturaTitular()==null || pActa.getAlturaTitular().equals("")))
				{	sMensaje ="Debe Ingresar la altura del Domicilio del Titular" ;
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_AlturaTitular",sMensaje));
				}
				/*
				if (pActa.getBarrioTitular()==null || pActa.getBarrioTitular().equals(""))
					sContent = sContent + "Debe Ingresar el barrio del Domicilio del Titular" + "\n";
				*/

				if (pActa.getIdPaisDomicilioTitular() == null || pActa.getIdPaisDomicilioTitular().equals(""))
				{	sMensaje ="Debe Seleccionar o Escribir el Pais del Domicilio del Titular";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("Spinner_PaisDocumentoTitular",sMensaje));
				}
				else if (pActa.getIdPaisDomicilioTitular().equals("-1") && (pActa.getPaisDomicilioTitular()==null || pActa.getPaisDomicilioTitular().equals("")) )
				{	sMensaje = "Debe Escribir el Pais del Domicilio del Titular";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_PaisDocumentoTitular",sMensaje));
				}
				/*------------------------------*/
				if (pActa.getIdProvinciaDomicilioTitular() == null || pActa.getIdProvinciaDomicilioTitular().equals(""))
				{	sMensaje = "Debe Seleccionar o Escribir la Provincia del Domicilio del Titular";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("Spinner_ProvinciaDocumentoTitular",sMensaje));
				}
				else if (pActa.getIdProvinciaDomicilioTitular().equals("-1") && (pActa.getProvinciaDomicilioTitular()==null || pActa.getProvinciaDomicilioTitular().equals("")) )
				{   sMensaje = "Debe Escribir la Provincia del Domicilio del Titular";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_ProvinciaDocumentoTitular",sMensaje));
				}
				/*------------------------------*/
				if (pActa.getIdDepartamentoDomicilioTitular() == null || pActa.getIdDepartamentoDomicilioTitular().equals(""))
				{	sMensaje = "Debe Seleccionar o Escribir el Departamento del Domicilio del Titular";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("Spinner_DepartamentoDocumentoTitular",sMensaje));
				}
				else if (pActa.getIdDepartamentoDomicilioTitular().equals(-1) && (pActa.getDepartamentoDomicilioTitular()==null || pActa.getDepartamentoDomicilioTitular().equals("")) )
				{	sMensaje = "Debe Escribir el Departamento del Domicilio del Titular";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_DepartamentoDocumentoTitular",sMensaje));
				}
				/*------------------------------*/
				if (pActa.getIdLocalidadDomicilioTitular() == null || pActa.getIdLocalidadDomicilioTitular().equals(""))
				{	sMensaje = "Debe Seleccionar o Escribir la Localidad del Domicilio del Titular";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("Spinner_LocalidadDocumentoTitular",sMensaje));
				}
				else if (pActa.getIdLocalidadDomicilioTitular().equals(-1) && (pActa.getLocalidadDomicilioTitular()==null || pActa.getLocalidadDomicilioTitular().equals("")))
				{	sMensaje = "Debe Escribir la Localidad del Domicilio del Titular";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_LocalidadDocumentoTitular",sMensaje));
				}

				if(doIsObligatorioCodigoPostal()==true)
					if (pActa.getCodigoPostalTitular()	==null || pActa.getCodigoPostalTitular().equals(""))
					{	sMensaje = "Debe Ingresar el Codigo Postal del Domicilio del Titular";
						sContent = sContent + sMensaje + "\n";
						lstRetorno.add(new AndroidWidgetControl("EditText_CodigoPostalTitular",sMensaje));
					}
	    }

		/* END TITULAR */
		/* TESTIGO */
		//if (pActa.getConductorEsTitular()==null || pActa.getConductorEsTitular().equals("N"))
		if (pActa.getSinTestigo()==null || pActa.getSinTestigo().equals("N"))
		{
				if (pActa.getTipoDocumentoTestigo()==null || pActa.getTipoDocumentoTestigo().equals(""))
				{	sMensaje = "Debe Ingresar el Tipo de Documento del Testigo";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("Spinner_TipoDocumentoTestigo",sMensaje));
				}
				
				/*
				if (pActa.getIdSexoTestigo()==null || pActa.getIdSexoTestigo().equals(""))
					sContent = sContent + "Debe Ingresar el Sexo del Titular" + "\n";
				*/
				if (pActa.getNumeroDocumentoTestigo()==null || pActa.getNumeroDocumentoTestigo().equals(""))
				{	sMensaje = "Debe Ingresar el Numero de Documento del Testigo";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_NumeroDocumentoTestigo",sMensaje));
				}
				else
				{	if (pActa.getTipoDocumentoTestigo()!=null && !pActa.getTipoDocumentoTestigo().equals(""))
					{   	String sMensajeSalida =doValidarFormatoDocumento(pActa.getoTipoDocumentoTestigo(),pActa.getNumeroDocumentoTestigo());
							if(!sMensajeSalida.equals(""))
							{	sMensaje = sMensajeSalida + " (Testigo)";
								sContent = sContent + sMensaje + "\n";
								lstRetorno.add(new AndroidWidgetControl("EditText_NumeroDocumentoTestigo",sMensaje));
							}
					}
				}
				
				/*
				if (pActa.getFechaNacimientoTitular()==null || pActa.getFechaNacimientoTitular().equals(""))
					sContent = sContent + "Debe Ingresar la Fecha Nacimiento del Titular" + "\n";
				*/

				if (pActa.getApellidoTestigo()==null || pActa.getApellidoTestigo().equals(""))
				{	sMensaje = "Debe Ingresar el Apellido del Testigo";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_ApellidoTestigo",sMensaje));
				}

				if (pActa.getNombreTestigo()==null || pActa.getNombreTestigo().equals(""))
				{	sMensaje = "Debe Ingresar el Nombre del Testigo";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_NombreTestigo",sMensaje));
				}


				if ((pActa.getEsCallePublicaTestigo()==null || pActa.getEsCallePublicaTestigo()==false) &&  (pActa.getCalleTestigo()==null ||pActa.getCalleTestigo().equals("")))
				{	sMensaje = "Debe Ingresar la calle del Testigo";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_CalleTestigo",sMensaje));
				}

				if ((pActa.getEsAlturaSinNumeroTestigo()==null || pActa.getEsAlturaSinNumeroTestigo()==false) &&  ( pActa.getAlturaTestigo()==null || pActa.getAlturaTestigo().equals("")))
				{	sMensaje = "Debe Ingresar la altura del Domicilio del Testigo";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_AlturaTestigo",sMensaje));
				}
				/*
				if (pActa.getBarrioTestigo()==null || pActa.getBarrioTestigo().equals(""))
					sContent = sContent + "Debe Ingresar el barrio del Domicilio del Testigo" + "\n";
		        */

				if (pActa.getIdPaisDomicilioTestigo() == null || pActa.getIdPaisDomicilioTestigo().equals(""))
				{	sMensaje = "Debe Seleccionar o Escribir el Pais del Domicilio del Testigo";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("Spinner_PaisDocumentoTestigo",sMensaje));
				}
				else if (pActa.getIdPaisDomicilioTestigo().equals("-1") && (pActa.getPaisDomicilioTestigo()==null || pActa.getPaisDomicilioTestigo().equals("")) )
				{   sMensaje = "Debe Escribir el Pais del Domicilio del Testigo";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_PaisDocumentoTestigo",sMensaje));
				}
				/*------------------------------*/
				if (pActa.getIdProvinciaDomicilioTestigo() == null || pActa.getIdProvinciaDomicilioTestigo().equals(""))
				{	sMensaje = "Debe Seleccionar o Escribir la Provincia del Domicilio del Testigo";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("Spinner_ProvinciaDocumentoTestigo",sMensaje));
				}
				else if (pActa.getIdProvinciaDomicilioTestigo().equals("-1") && (pActa.getProvinciaDomicilioTestigo()==null || pActa.getProvinciaDomicilioTestigo().equals("")) )
				{	sMensaje = "Debe Escribir la Provincia del Domicilio del Testigo" ;
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_ProvinciaDocumentoTestigo",sMensaje));
				}
				/*------------------------------*/
				if (pActa.getIdDepartamentoDomicilioTestigo() == null || pActa.getIdDepartamentoDomicilioTestigo().equals(""))
				{	sMensaje = "Debe Seleccionar o Escribir el Departamento del Domicilio del Testigo";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("Spinner_DepartamentoDocumentoTestigo",sMensaje));
				}
				else if (pActa.getIdDepartamentoDomicilioTestigo().equals(-1) && (pActa.getDepartamentoDomicilioTestigo()==null || pActa.getDepartamentoDomicilioTestigo().equals("")) )
				{	sMensaje = "Debe Escribir el Departamento del Domicilio del Testigo";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_DepartamentoDocumentoTestigo",sMensaje));
				}
				/*------------------------------*/
				if (pActa.getIdLocalidadDomicilioTestigo() == null || pActa.getIdLocalidadDomicilioTestigo().equals(""))
				{	sMensaje = "Debe Seleccionar o Escribir la Localidad del Domicilio del Testigo";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("Spinner_LocalidadDocumentoTestigo",sMensaje));
				}
				else if (pActa.getIdLocalidadDomicilioTestigo().equals(-1) && (pActa.getLocalidadDomicilioTestigo()==null || pActa.getLocalidadDomicilioTestigo().equals("")))
				{	sMensaje ="Debe Escribir la Localidad del Domicilio del Testigo";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_LocalidadDocumentoTestigo",sMensaje));
				}

				if(doIsObligatorioCodigoPostal()==true)
					if (pActa.getCodigoPostalTestigo()	==null || pActa.getCodigoPostalTestigo().equals(""))
					{	sMensaje = "Debe Ingresar el Codigo Postal del Domicilio del Testigo";
						sContent = sContent + sMensaje + "\n";
						lstRetorno.add(new AndroidWidgetControl("EditText_CodigoPostalTestigo",sMensaje));
					}
	    }
		/* END TESTIGO */

		/*--------------------------------*/
		/* VALIDACION DATOS DEMOGRAFICOS DEL DOMICILIO DEL INFRACTOR */
		if(pActa.getSinConductor()== null || pActa.getSinConductor().equals("N"))
		{
			if (pActa.getIdPaisDomicilio() == null || pActa.getIdPaisDomicilio().equals(""))
			{	sMensaje = "Debe Seleccionar o Escribir el Pais del Domicilio";
				sContent = sContent + sMensaje + "\n";
				lstRetorno.add(new AndroidWidgetControl("Spinner_PaisDocumento",sMensaje));
			}
			else if (pActa.getIdPaisDomicilio().equals("-1") && (pActa.getPaisDomicilio()==null || pActa.getPaisDomicilio().equals("")) )
			{	sMensaje = "Debe Escribir el Pais del Domicilio";
				sContent = sContent + sMensaje + "\n";
				lstRetorno.add(new AndroidWidgetControl("EditText_PaisDocumento",sMensaje));
			}
			/*------------------------------*/
			if (pActa.getIdProvinciaDomicilio() == null || pActa.getIdProvinciaDomicilio().equals(""))
			{	sMensaje = "Debe Seleccionar o Escribir la Provincia del Domicilio";
				sContent = sContent + sMensaje + "\n";
				lstRetorno.add(new AndroidWidgetControl("Spinner_ProvinciaDocumento",sMensaje));
			}
			else if (pActa.getIdProvinciaDomicilio().equals("-1") && (pActa.getProvinciaDomicilio()==null || pActa.getProvinciaDomicilio().equals("")) )
			{	sMensaje = "Debe Escribir la Provincia del Domicilio";
				sContent = sContent + sMensaje + "\n";
				lstRetorno.add(new AndroidWidgetControl("EditText_ProvinciaDocumento",sMensaje));
			}
			/*------------------------------*/
			if (pActa.getIdDepartamentoDomicilio() == null || pActa.getIdDepartamentoDomicilio().equals(""))
			{	sMensaje = "Debe Seleccionar o Escribir el Departamento del Domicilio";
				sContent = sContent + sMensaje + "\n";
				lstRetorno.add(new AndroidWidgetControl("Spinner_DepartamentoDocumento",sMensaje));
			}
			else if (pActa.getIdDepartamentoDomicilio().equals(-1) && (pActa.getDepartamentoDomicilio()==null || pActa.getDepartamentoDomicilio().equals("")) )
			{	sMensaje = "Debe Escribir el Departamento del Domicilio";
				sContent = sContent + sMensaje + "\n";
				lstRetorno.add(new AndroidWidgetControl("EditText_DepartamentoDocumento",sMensaje));
			}
			/*------------------------------*/
			if (pActa.getIdLocalidadDomicilio() == null || pActa.getIdLocalidadDomicilio().equals(""))
			{	sMensaje = "Debe Seleccionar o Escribir la Localidad del Domicilio";
				sContent = sContent + sMensaje + "\n";
				lstRetorno.add(new AndroidWidgetControl("Spinner_LocalidadDocumento",sMensaje));
			}
			else if (pActa.getIdLocalidadDomicilio().equals(-1) && (pActa.getLocalidadDomicilio()==null || pActa.getLocalidadDomicilio().equals("")))
			{	sMensaje = "Debe Escribir la Localidad del Domicilio";
				sContent = sContent + sMensaje + "\n";
				lstRetorno.add(new AndroidWidgetControl("EditText_LocalidadDocumento",sMensaje));
			}

			if(doIsObligatorioCodigoPostal()==true)
				if (pActa.getCodigoPostal()	==null || pActa.getCodigoPostal().equals(""))
				{	sMensaje = "Debe Ingresar el Codigo Postal del Domicilio";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_CodigoPostal",sMensaje));
				}
		}

		/* validaremos el tipo de vehiculo */
		if (pActa.getoTipoVehiculo()==null || pActa.getIdTipoVehiculo().equals(""))
		{   	sMensaje = "Debe seleccionar un tipo de Vehiculo";
			    sContent = sContent + sMensaje + "\n";
				lstRetorno.add(new AndroidWidgetControl("Spinner_TipoVehiculo",sMensaje));
		}
		else
		{
			if (pActa.getoTipoVehiculo().getRequiereDominio().equals("S"))		/* podria falta validacion para tipo vehiculo y tipo patente  A o M  antes */
			{
				if (pActa.getDominio()==null || pActa.getDominio().trim().equals(""))
				{	sMensaje = "Debe Ingresar el Dominio/Patente del Vehiculo";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_Dominio",sMensaje));
				}
				else
				{
					String sDominio = pActa.getDominio();
					String sMensajeSalida =doValidarFormatoDominio(pActa.getIdTipoPatente(),pActa.getIdTipoVehiculo(),sDominio);
						if (!sMensajeSalida.equals(""))
						{	sMensaje = sMensajeSalida;
							sContent = sContent + sMensaje + "\n";
							lstRetorno.add(new AndroidWidgetControl("EditText_Dominio",sMensaje));
						}
				}

				if (pActa.getIdMarca() == null || pActa.getIdMarca().equals(""))
				{
					sMensaje = "Debe Seleccionar o Escribir la Marca del Vehiculo";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("Spinner_Marca",sMensaje));
				}
				else if (pActa.getIdMarca().equals(-1) && (pActa.getMarca()==null || pActa.getMarca().equals("")))
				{
					sMensaje = "Debe Escribir la Marca del Vehiculo";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_Marca",sMensaje));
				}

				if (pActa.getModeloVehiculo()==null || pActa.getModeloVehiculo().equals(""))
				{
					sMensaje = "Debe Ingresar el Modelo del Vehiculo";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("TextView_Modelo",sMensaje));
				}

				if (pActa.getAnioModeloVehiculo()==null || pActa.getAnioModeloVehiculo().equals(""))
				{
					sMensaje = "Debe Ingresar el Año del Modelo del Vehiculo";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_AnioModeloVehiculo",sMensaje));
				}

				if (pActa.getIdColor() == null || pActa.getIdColor().equals(""))
				{
					sMensaje = "Debe Seleccionar o Escribir el Color del Vehiculo";
					sContent = sContent + sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("Spinner_Color",sMensaje));
				}
				else if (pActa.getIdColor().equals(-1) && (pActa.getColor()==null || pActa.getColor().trim().equals("")))
				{
					sMensaje = "Debe Escribir el Color del Vehiculo";
					sContent = sContent +  sMensaje + "\n";
					lstRetorno.add(new AndroidWidgetControl("EditText_Color",sMensaje));
				}
			}
		}

        List<InfraccionNomenclada> listaInfracciones = new ArrayList<>();
		if (pActa.getoInfraccionNomenclada1()!=null)  listaInfracciones.add(pActa.getoInfraccionNomenclada1());
        if (pActa.getoInfraccionNomenclada2()!=null)  listaInfracciones.add(pActa.getoInfraccionNomenclada2());
        if (pActa.getoInfraccionNomenclada3()!=null)  listaInfracciones.add(pActa.getoInfraccionNomenclada3());
        if (pActa.getoInfraccionNomenclada4()!=null)  listaInfracciones.add(pActa.getoInfraccionNomenclada4());
        if (pActa.getoInfraccionNomenclada5()!=null)  listaInfracciones.add(pActa.getoInfraccionNomenclada5());

        int iCantidadInfracciones = listaInfracciones.size();
        if (iCantidadInfracciones <= 0)
        {
            sMensaje = "Debe Ingresar al menos una Infraccion";
            sContent = sContent + sMensaje + "\n";
            lstRetorno.add(new AndroidWidgetControl("EditText_CODInfraccion_1",sMensaje));
        }
        int iCantidadInfraccionesLeves = 0;
        for(InfraccionNomenclada e : listaInfracciones){
            if(e.getTipoFalta().equals("LEVE")) iCantidadInfraccionesLeves++;
        }
        if (iCantidadInfraccionesLeves > 1 )
        {
            sMensaje = "No se permite generar un Acta con mas de una Infraccion LEVE";
            sContent = sContent + sMensaje + "\n";
            lstRetorno.add(new AndroidWidgetControl("EditText_CODInfraccion_1",sMensaje));
        }

		for(InfraccionNomenclada e : listaInfracciones) {
			if ((e.getId().equals(158) || e.getId() == 158) && pActa.getGrad_alcohol().equals("")){
				sMensaje = "Debe ingresar el dosaje de alcohol en sangre registrado";
				sContent = sContent + sMensaje + "\n";
				lstRetorno.add(new AndroidWidgetControl("editText_AlcoholEnSangre",sMensaje));
			}
		}

		String FotosObligatorias = GlobalVar.getInstance().getSuportTable().getFotosObligatoriasEnActas();
		if(FotosObligatorias!=null && FotosObligatorias.trim().toUpperCase().equals("S"))
		{	
				if(pActa.getFotos()==null || pActa.getFotos().equals(""))
				{
					sMensaje = "Debe Tomar como minimo la Foto de la Licencia de Conducir y la de Documento de Identidad" ;
					sContent = sContent + sMensaje + "\n";
					
				}
				else
				{
					String pFotos= pActa.getFotos();
					String[] imagenes  =  pFotos.split("\\|");
					if (imagenes.length<2)
					{	sMensaje = "Debe Tomar como minimo la Foto de la Licencia de Conducir y la de Documento de Identidad";
						sContent = sContent + sMensaje + "\n";
					}
					else
					{ if (imagenes.length==2)
						{
							Boolean existeFotoLicencia = pFotos.contains("licencia");
							Boolean existeFotoDocumento = pFotos.contains("documento");
							if(!existeFotoLicencia)
							{   sMensaje = "Debe Tomar la Foto de la Licencia de Conducir";
								sContent = sContent + sMensaje + "\n";
							}
							if(!existeFotoDocumento)
							{
								sMensaje = "Debe Tomar la Foto del Documento de Identidad";
								sContent = sContent + sMensaje + "\n";
							}
							
						}
						
					}
					 
					
				}
		}
		
		
	    if(sContent.equals("")) 
	    {	return lstRetorno;// o lstRetorno.size()==0 
	    // todos los datos requeridos son validos
	    }
	  
	    sContent = "\n" + sContent;
	  
	  
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle("Validar Actas...");
		alertDialog.setMessage("Los Siguientes Items son Datos Obligatorios" + sContent);
		alertDialog.setButton("Aceptar", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
		}
		});
		//alertDialog.setIcon(R.drawable.icon);
		alertDialog.show();
		
		return lstRetorno;
	}
	public Boolean marcarActaSincronizada(Integer idActaConstatacion)
	{
		Boolean resultado = false;
		ActaConstatacionDao actaDao = new ActaConstatacionDao();
		ContentValues cv = new ContentValues();
		cv.put("SINCRONIZADA", "S");
		
		/*  esta parte del codigo es para ver si la podemos guardar como sincronizada
		int rowsAffected =	actaDao.update(idActaConstatacion.toString(),cv);
		if(rowsAffected>0)
			resultado= true;*/

		int rowsAffected =	actaDao.delete(idActaConstatacion.toString());
		if(rowsAffected>0)
			return true;
		
		return resultado;
	}
	
	public List<ActaConstatacion> grabarActa(ActaConstatacion pActaConstatacion)
	{	
		List<ActaConstatacion> lstActasCreadas = new ArrayList<ActaConstatacion>();
	
		
		int iCantidadActas =0;
		
		String _numeroActaPrincipal = pActaConstatacion.getNumeroActa();
		/*
		List<InfraccionNomenclada> lstInfracciones = new ArrayList<InfraccionNomenclada>();
		if(pActaConstatacion.getoInfraccionNomenclada1()!=null)
		{
			InfraccionNomenclada inf = pActaConstatacion.getoInfraccionNomenclada1();
			inf.setObservaciones(pActaConstatacion.getObservaciones());
			lstInfracciones.add(inf);
		}
		
		if(pActaConstatacion.getoInfraccionNomenclada2()!=null)
		{
			InfraccionNomenclada inf = pActaConstatacion.getoInfraccionNomenclada2();
			inf.setObservaciones(pActaConstatacion.getObservaciones2());
			lstInfracciones.add(inf);
		}		
		*/
		
		//for(InfraccionNomenclada infraccion :lstInfracciones)
		{
			iCantidadActas++;
				ActaConstatacionDao actaDao = new ActaConstatacionDao();
				ActaConstatacion _acta = (ActaConstatacion) pActaConstatacion.clone();
				
				String _numeroActa =  this.getNextNumeroActa();
				_acta.setNumeroActa(_numeroActa);
				
				String sFotos =_acta.getFotos();
				if (sFotos !=null && !sFotos.contains(_numeroActa) && !_numeroActa.equals(_numeroActaPrincipal))
				{  // hay que cambiar/copiar las fotos de la primer acta para la segunda (si es que tiene fotos)
					
				  /* Incio de copia de Fotos del Acta original para la duplicada */
				  String[] xFotos;
				  String[] xFotos2;
               	  if(sFotos!=null && sFotos.length()>0)
               	  {
               		  xFotos = sFotos.split("\\|");
               		  String FotosAReemplazar =_acta.getFotos();
               		  FotosAReemplazar = FotosAReemplazar.replace(_numeroActaPrincipal, _numeroActa);
               		  _acta.setFotos(FotosAReemplazar);// las fotos con el nuevo nro de Acta
               		  xFotos2 = FotosAReemplazar.split("\\|");
               	  }
               	  else
               	  {
               		  xFotos = null;
               		  xFotos2 = null;
               	  }
                  if (xFotos != null && xFotos.length>0)
                  {	
                	  
                	  
               	   	  for (int j = 0; j < xFotos.length; j++)
                      {
	                	   String foto =xFotos[j]; // licencia documento otros
	                	   String foto2= xFotos2[j];
	                	   File fileFoto = new File(foto);
	                	   //existe y se puede leer
	                	   if (fileFoto !=null && fileFoto.exists()==true && fileFoto.canRead()==true)
	                	   {
	                		  
	                		  Boolean bBitmapObtenido = false;
	                    	   Bitmap bitmap;
	                    	   try
	                    	   {
	                    		   bitmap = ImageHelper.getBitmap(foto);
	                    		   bBitmapObtenido = true;
	                    	   }
	                    	   catch(IOException ioe)
	                    	   {
	                    		   bitmap = null;   
	                    	   }
	                    	   
	                    	   if(bBitmapObtenido==true && bitmap!=null)
	                    	   {
	                    		
	                    		try {
	                    		   //copiar la imagen
	                    		   InputStream in = new FileInputStream(foto);
	                               OutputStream out = new FileOutputStream(foto2);
	                    
	                               // Copy the bits from instream to outstream
	                               byte[] buf = new byte[1024];
	                               int len;
	                                
	                               while ((len = in.read(buf)) > 0) {
	                                   out.write(buf, 0, len);
	                               }
	                                
	                               in.close();
	                               out.close();
	                               
	                    		} 
	                    		  catch (FileNotFoundException e) {	}
	                    		  catch (IOException e) {	}
	                               
	                    	   }
	                	   }
	                  }
                  }
					/* Fin de copia de Fotos del Acta original*/
					
				}
				
				//_acta.setoInfraccionNomenclada1(infraccion);
				//_acta.setoInfraccionNomenclada2(null);
				//_acta.setObservaciones(infraccion.getObservaciones());
				//_acta.setObservaciones2(null);
				
				Double monto= 0.0;
				//, _acta.getIdInfraccion1(), _acta.getIdInfraccion2()
				List<Integer> lstIdInfracciones = new ArrayList<Integer>();

				if (_acta.getIdInfraccion1()!=null && _acta.getIdInfraccion1()>0) lstIdInfracciones.add(_acta.getIdInfraccion1());
				if (_acta.getIdInfraccion2()!=null && _acta.getIdInfraccion2()>0) lstIdInfracciones.add(_acta.getIdInfraccion2());
				if (_acta.getIdInfraccion3()!=null && _acta.getIdInfraccion3()>0) lstIdInfracciones.add(_acta.getIdInfraccion3());
				if (_acta.getIdInfraccion4()!=null && _acta.getIdInfraccion4()>0) lstIdInfracciones.add(_acta.getIdInfraccion4());
				if (_acta.getIdInfraccion5()!=null && _acta.getIdInfraccion5()>0) lstIdInfracciones.add(_acta.getIdInfraccion5());
				if (_acta.getIdInfraccion6()!=null && _acta.getIdInfraccion6()>0) lstIdInfracciones.add(_acta.getIdInfraccion6());

				monto = Utilities.ConfigMontoTotalMendozaConDescuento(GlobalStateApp.getInstance().getApplicationContext(),lstIdInfracciones);
				_acta.setMontoCupon(monto);

				//String sMonto = String.format("%10.2", monto) ;
				SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
				String sFechaVencimiento = formatter.format(_acta.getFechaVencimientoCupon());
				//RECORDAR QUE LOS CAMPOS CALCULADOS LO DEBEMOS PONER LUEGO DE LAS VALDIACIONES PARA QUE NO PRODUZCAN ERROR
				
				//String codigoBarra ="" ;//Tools.actaCodeGetChaco(_acta.getNumeroActa().toString(), _acta.getNumeroDocumento().toString(), sFechaVencimiento, _acta.getMontoCuponString());//(actaID, pNroDocumento, pFechaVencimiento_ddmmyyyy, pMontoMulta0_00);
				//_acta.setCodigoBarra(codigoBarra);

				String a = _acta.getNumeroActa();
				int largo = a.length();
				String nroActa =a;

				
				long idCreatedItem = actaDao.insert(_acta);
				//_acta.setIdActaConstatacion(idCreatedItem);
				if(idCreatedItem>0)
				{
					_acta.setIdActaConstatacion(Integer.parseInt(String.valueOf(idCreatedItem)));
					
					//una vez grabada el acta  grabmos informes
					InformeActaRules informeActaRules = new InformeActaRules(context);
					informeActaRules.grabarInformeActa(_acta);
					
					lstActasCreadas.add(_acta);
				}
		}
		return lstActasCreadas;//resultado;
	}

	public Boolean imprimirActa(ActaConstatacion pActa)
	{
		Boolean bResultadoImpresion= false;
		
		
		return bResultadoImpresion;
	}
	public String getNextNumeroActa()
	{	String letraSerie ="";
		String numeroSerie = "";
		String sNextNumeroActa ="000000000000";
		String imei = GlobalVar.getInstance().getImei();
	
		
		EquipoDao equipoDao = new EquipoDao();
 	    Equipo equipo  = equipoDao.getByImei(imei);
 	    numeroSerie = equipo.getNumeroSerie();
 	    letraSerie=equipo.getLetraSerie();
 	    int valor = Integer.parseInt(numeroSerie); 
	    numeroSerie =  Tools.formatearIzquierda(valor, 4,'0');
		
 	    ActaConstatacionDao actaDao = new ActaConstatacionDao();
		
 	    String sNextId = String.valueOf(actaDao.getNextId());
 	    valor = Integer.parseInt(sNextId);
 	    //sNextId =  Tools.formatearIzquierda(valor, 7,'0');
 	   sNextId =  Tools.formatearIzquierda(valor, 10,'0');
 	    
 	    //sNextNumeroActa = numeroSerie + sNextId;
 	  sNextNumeroActa = letraSerie + sNextId;
 	  return sNextNumeroActa;
 	    //Integer dv = Tools.GeneraDigitoVerificadorChaco(sNextNumeroActa);
		//return sNextNumeroActa + dv.toString();
	}
	public void sincronizarUltimaActa()
	{
		ActaConstatacionSync synActa = new ActaConstatacionSync(this.context);
 		
 	    String sNumeroActa ="";
 	    String imei = GlobalVar.getInstance().getImei();
 	    
 	    // buscar localmente nro de serie en base al imei del dispositivo
 	    String numeroSerie = "";
 	    String letraSerie = "";
 	    EquipoDao equipoDao = new EquipoDao();
 	    Equipo equipo  = equipoDao.getByImei(imei);
 	    numeroSerie = equipo.getNumeroSerie();
 	    letraSerie = equipo.getLetraSerie();
 	    
 	    try
 	    {
     	    sNumeroActa = synActa.validarNumeroActa();
     	    long numeroActaREPAT = Integer.parseInt(sNumeroActa);// no tengo digito verificador aqui -1)); //desde la 4 posicion hasta el largo menos -1 que es el digito verificador
     	    ActaConstatacionDao actaDao = new ActaConstatacionDao();
     	    
     	 
	  		 long value = actaDao.getNextId();
	  		 if (value < numeroActaREPAT)
	  		 {
	     	    actaDao.configSecuence(numeroActaREPAT);
	  		 }
	  		 //actaDao.configSecuence(367);
     	    /*
     	    ContentProvider cp = this.context.getContentResolver().acquireContentProviderClient(ActaConstatacionProvider.CONTENT_URI).getLocalContentProvider();
       	    ActaConstatacionProvider  a =(ActaConstatacionProvider) cp; 
     	    a.setSecuenceValue(numeroActaREPAT);*/
     	    
 	    } 
 	    catch (Exception e) 
 	    {
 	       
 	    }
 	    finally
 	    {
 	    	synActa=null;
 	    }

	}

	public  String doValidarFormatoDocumento(TipoDocumento pTipoDocumento, String pNumeroDocumento)
    {
    	String sMensajeSalida= "";
    
    	if (pTipoDocumento.getId().equals("DNI") || pTipoDocumento.getId().equals("CF") )
    	{
    		if (!Tools.isNumber(pNumeroDocumento.trim()))
            {
    			sMensajeSalida ="El numero de Documento no es correcto";
    			return sMensajeSalida;
            }
            
    	}
    	Integer CantMinCaract = Integer.parseInt(pTipoDocumento.getCantidadMinimaCaracteres());
    	Integer CantMaxCaract = Integer.parseInt(pTipoDocumento.getCantidadMaximaCaracteres());
    	Integer Largo =pNumeroDocumento.length();
    	 switch (pTipoDocumento.getId())
         {
             case "DNI": //Documento Nacional de identidad
                 if (pNumeroDocumento.length() < CantMinCaract)
                 {

                	 sMensajeSalida ="La longitud del número de Documento no es correcta, debe ser de por lo menos " + pTipoDocumento.getCantidadMinimaCaracteres() + " caracteres.";
                     return sMensajeSalida;
                 }
                 if (CantMaxCaract != 0 && pNumeroDocumento.length() > CantMaxCaract)
                 {

                	 sMensajeSalida ="La longitud del número de Documento no es correcta, debe tener un maximo de " + CantMaxCaract.toString() + " caracteres.";
                     return sMensajeSalida;
                 }
                 break;

             case "CF": //Cedula Federal
                 if (pNumeroDocumento.length() < CantMinCaract)
                 {

                	 sMensajeSalida ="La longitud del número de la Cedula Federal no es correcta, debe ser de por lo menos " + CantMinCaract.toString() + " caracteres.";
                	 return sMensajeSalida;
                 }
                 if (CantMaxCaract != 0 && pNumeroDocumento.length() > CantMaxCaract)
                 {

                	 sMensajeSalida ="La longitud del número de la Cedula Federal no es correcta, debe tener un maximo de " + CantMaxCaract.toString() + " caracteres.";
                     return sMensajeSalida;
                 }
                 break;

             case "LEN": //Libreta de enrolamiento
                 if (Largo < CantMinCaract)
                 {

                	 sMensajeSalida ="La longitud del número de la Libreta de Errolamiento no es correcta, debe ser de por lo menos " + CantMinCaract.toString() + " caracteres.";
                     return sMensajeSalida;
                 }
                 if (CantMaxCaract != 0 && Largo > CantMaxCaract)
                 {

                	 sMensajeSalida ="La longitud del número de la Libreta de Enrrolamiento no es correcta, debe tener un maximo de " + CantMaxCaract.toString() + " caracteres.";
                     return sMensajeSalida;
                 }
                 break;

             case "LC": //Libreta civica
                 if (Largo < CantMinCaract)
                 {

                	 sMensajeSalida ="La longitud del número de la Libreta Cívica no es correcta, debe ser de por lo menos " + CantMinCaract.toString() + " caracteres.";
                     return sMensajeSalida;
                 }
                 if (CantMaxCaract != 0 && Largo > CantMaxCaract)
                 {


                	 sMensajeSalida ="La longitud del número de la Libreta Cívica no es correcta, debe tener un maximo de " + CantMaxCaract.toString() + " caracteres.";
                     return sMensajeSalida;
                 }
                 break;

             case "CM": //Certificado migratorio
                 if (Largo < CantMinCaract)
                 {

                	 sMensajeSalida ="La longitud del número del Certificado Migratorio no es correcta, debe ser de por lo menos " + CantMinCaract.toString() + " caracteres.";
                     return sMensajeSalida;
                 }
                 if (CantMaxCaract != 0 && Largo > CantMaxCaract)
                 {

                	 sMensajeSalida ="La longitud del número del Certificado Migratorio no es correcta, debe tener un maximo de " + CantMaxCaract.toString() + " caracteres.";
                     return sMensajeSalida;
                 }
                 break;

             case "MI": //Matricula individual
                 if (Largo < CantMinCaract)
                 {

                	 sMensajeSalida ="La longitud del número de la Matricula Individual no es correcta, debe ser de por lo menos " + CantMinCaract.toString() + " caracteres.";
                	 return sMensajeSalida;
                 }
                 if (CantMaxCaract != 0 && Largo > CantMaxCaract)
                 {

                	 sMensajeSalida ="La longitud del número de la Matricula Individual no es correcta, debe tener un maximo de " + CantMaxCaract.toString() + " caracteres.";
                     return sMensajeSalida;
                 }
                 break;

             case "NE": //Numeros especiales
                 if (Largo < CantMinCaract)
                 {

                	 sMensajeSalida ="La longitud del número del Numero Especial no es correcta, debe ser de por lo menos " + CantMinCaract.toString() + " caracteres.";
                	 return sMensajeSalida;
                 }
                 if (CantMaxCaract != 0 && Largo > CantMaxCaract)
                 {

                	 sMensajeSalida ="La longitud del número del Numero Especial no es correcta, debe tener un maximo de " + CantMaxCaract.toString() + " caracteres.";
                     return sMensajeSalida;
                 }
                 break;

             case "PSP": //Pasaporte
                 if (Largo < CantMinCaract)
                 {

                	 sMensajeSalida ="La longitud del número de Pasaporte no es correcta, debe ser de por lo menos " + CantMinCaract.toString() + " caracteres.";
                     return sMensajeSalida;
                 }
                 if (CantMaxCaract != 0 && Largo > CantMaxCaract)
                 {

                	 sMensajeSalida ="La longitud del número de Pasaporte no es correcta, debe tener un maximo de " + CantMaxCaract.toString() + " caracteres.";
                     return sMensajeSalida;
                 }
                 break;

             case "CID": //Cedula de identidad
                 if (Largo < CantMinCaract)
                 {

                	 sMensajeSalida ="La longitud del número de la Cédula de Identidad no es correcta, debe ser de por lo menos " + CantMinCaract.toString() + " caracteres.";
                     return sMensajeSalida;
                 }
                 if (CantMaxCaract != 0 && Largo > CantMaxCaract)
                 {

                	 sMensajeSalida ="La longitud del número de la Cédula de Identidad no es correcta, debe tener un maximo de " + CantMaxCaract.toString() + " caracteres.";
                     return sMensajeSalida;
                 }
                 break;
         }
    	return sMensajeSalida;
    }
    
    public Boolean doIsObligatorioCodigoPostal()
    {
    	Boolean obligatorio= false;
    	
    	return obligatorio;
    }
    /*
     * Verificar el Formato del Dominio segun el Tipo de Vehiculo y el Tipo de Patente (nacional internacional) 
     */
    public  String doValidarFormatoDominio(String pTipoPatente, String pTipoVehiculo,String pDominioIngresado)
    {
    	String sMensajeSalida= "";
    	if (pTipoVehiculo.toUpperCase().equals("AUTOMOTOR") && pTipoPatente.toUpperCase().equals("NACIONAL"))
        { 
            // verificar que se Escriba correctamente el Dominio del auto
            /*regex myRegex = new Regex(@"[A-Z]{3}d{3}", RegexOptions.IgnoreCase);
            Match myMatch = myRegex.Match(pDominioIngresado);
            */
    		//Boolean bMatches = pDominioIngresado.toUpperCase().matches("[A-Z]{3}\\d{3}");
    		Boolean bMatches = pDominioIngresado.toUpperCase().matches("[A-Z]{3}\\d{3}");
    		Boolean bMatchesMercosur = pDominioIngresado.toUpperCase().matches("[A-Z]{2}\\d{3}[A-Z]{2}");
            if (bMatches || bMatchesMercosur)
            {
                return "";
            }
            else
            {

            	sMensajeSalida ="El Formato del Dominio del Automotor no es válido\nEl Valor debe contener tres (3) letras seguidas de tres (3) numeros o dos (2) letras seguidas de tres (3) y luego dos (letras) numeros ej.  NNN999 o NN999NN";
                return sMensajeSalida;
            }

        }

        if (pTipoVehiculo.toUpperCase().equals("MOTOVEHICULO") && pTipoPatente.toUpperCase().equals("NACIONAL"))
        {
            // verificar que se Escriba correctamente el Dominio de la moto
            /*Regex myRegex = new Regex(@"\d{3}[A-Z]{3}", RegexOptions.IgnoreCase);
            Match myMatch = myRegex.Match(pDominioIngresado);*/
            Boolean bMatches = pDominioIngresado.toUpperCase().matches("\\d{3}[A-Z]{3}");
    		Boolean bMatchesMercosur = pDominioIngresado.toUpperCase().matches("[A-Z]{1}\\d{3}[A-Z]{3}");

            if (bMatches || bMatchesMercosur)
            {
                return "";
            }
            else
            {
            	sMensajeSalida ="El Formato del Dominio del MotoVehiculo no es válido\nEl Valor debe contener tres (3) numeros seguido de tres (3) letras o 1 (una) letra  seguido de tres (3) numeros y de tres (3) letras ej.  999NNN o N999NNN";
                return sMensajeSalida;
            }

        }
        
    	return sMensajeSalida;
    }
    
    
    public  Boolean doValidarTipoInfraccion(InfraccionNomenclada pIdInfraccion1,InfraccionNomenclada pIdInfraccion2 )
    {
    	
    	 if (pIdInfraccion1!=null && pIdInfraccion2!=null)
    		 	return pIdInfraccion1.getTipoInfraccionId()==pIdInfraccion2.getTipoInfraccionId();
    		 else
    			 return true;
    	
    }

    public  Boolean doValidarInfraccionNoPosseLicencia(Integer pIdInfraccion1,Integer pIdInfraccion2 )
    {
    	Boolean validado= false;
    	
    	String CodigoAValidar  = "NO_POSEE_LICENCIA";
    	
    	InfraccionClasificadaRules infraccionRules = new InfraccionClasificadaRules(context);
    	
    	InfraccionClasificada infraccion1=null;
    	InfraccionClasificada infraccion2=null;
    	
    	if(pIdInfraccion1!=null)
    		infraccion1 = infraccionRules.getInfraccionByIdClasificacion(pIdInfraccion1,CodigoAValidar);
    	
    	if(pIdInfraccion2!=null)
    		infraccion2 = infraccionRules.getInfraccionByIdClasificacion(pIdInfraccion2,CodigoAValidar);

        validado = infraccion1 != null || infraccion2 != null;
    	return validado;
    }

		public Boolean omitirCodigoBarra(ActaConstatacion p_Acta) {

			Boolean omitir = false;

			InfraccionNomencladaRules infraccionRules = new InfraccionNomencladaRules(this.context);
			ArrayList<InfraccionNomenclada> infracciones = new ArrayList<>();

			try
			{
				infracciones.add(infraccionRules.getInfraccionByCodigo(p_Acta.getCodigoInfraccion1()));
				infracciones.add(infraccionRules.getInfraccionByCodigo(p_Acta.getCodigoInfraccion2()));
				infracciones.add(infraccionRules.getInfraccionByCodigo(p_Acta.getCodigoInfraccion3()));
				infracciones.add(infraccionRules.getInfraccionByCodigo(p_Acta.getCodigoInfraccion4()));
				infracciones.add(infraccionRules.getInfraccionByCodigo(p_Acta.getCodigoInfraccion5()));
				infracciones.add(infraccionRules.getInfraccionByCodigo(p_Acta.getCodigoInfraccion6()));
			}
			catch (Exception e){   }

			for (int i=0; i<infracciones.size(); i++)
			{
				if (infracciones.get(i).getOmitirCodBarra().equals("S"))
				{
					omitir = true;
				}
			}

			return omitir;

		}




}
