package ar.gov.mendoza.PrometeoMuni.sync;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;

import ar.gov.mendoza.PrometeoMuni.core.exceptions.DeviceActasSynchronizationException;

//import com.cids.siga.core.base.Enumeraciones.EstadoNovedadMovilEnum;
//import com.cids.siga.core.data.DaoUti;
import ar.gov.mendoza.PrometeoMuni.core.util.Tools;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;
import ar.gov.mendoza.PrometeoMuni.sync.dto.DtoInicioInspeccion;
import ar.gov.mendoza.PrometeoMuni.sync.dto.DtoUsuarioValidado;
import ar.gov.mendoza.PrometeoMuni.sync.dto.LicenciaDTO;
import ar.gov.mendoza.PrometeoMuni.sync.dto.LicenciaValidaSyncDTO;
import ar.gov.mendoza.PrometeoMuni.sync.dto.RestriccionDTO;

//import com.cids.siga.sync.dto.DtoInicioInspeccion;
//import com.cids.siga.sync.dto.DtoInspeccion;


public class SyncLicencia extends SyncBase {	  
		
	public SyncLicencia(Context context) {
		 super(context, "SyncLicencia");
	}

//	private void saveUtis(DtoInspeccion dto) {
//		ContentResolver resolver = context.getContentResolver();
//		Iterator<Uti> it = dto.getUti().iterator();
//		
//		resolver.delete(UtiProvider.CONTENT_URI, null, null);
//		
//		while(it.hasNext()) 
//		{
//			Uti uti = (Uti)it.next();
//			
//			ContentValues cv = new ContentValues();
//			cv.put(UtiProvider._ID, uti.getId());
//			cv.put(UtiProvider.NOMBRE, uti.getNombre());
//			cv.put(UtiProvider.COORDENADAS, uti.getCoordenadas());
//			resolver.insert(UtiProvider.CONTENT_URI, cv);
//		}
//	}
	public LicenciaValidaSyncDTO validarNumeroLicencia(String pNumeroLicencia)
	{
		String idUsuario = GlobalVar.getInstance().getIdUsuario();
		String login = GlobalVar.getInstance().getLogin();
		String imei  = GlobalVar.getInstance().getImei();
		String lat = Tools.DecimalFormat(GlobalVar.getInstance().getLatitud(),"0.0") ;
		String lon = Tools.DecimalFormat(GlobalVar.getInstance().getLongitud(),"0.0") ;
		
		LicenciaValidaSyncDTO licenciaValidaSyncDTO=null;
		
		setServiceMethodName("ConsultaDatosdeLicencia");//"BusquedaPorLicenciaUPConVP");
		StringBuilder strb = new StringBuilder();
        strb.append("<root>");
        strb.append("<values>");
		
        strb.append("<nroLicencia>");
        strb.append(pNumeroLicencia.trim());
        strb.append("</nroLicencia>");
        strb.append("<idUsuario>");
        strb.append(idUsuario.trim());
        strb.append("</idUsuario>");
        strb.append("<IMEI>");
        strb.append(imei.trim());
        strb.append("</IMEI>");
        strb.append("<Lat>");
        strb.append(lat.trim());
        strb.append("</Lat>");
        strb.append("<Lon>");
        strb.append(lon.trim());
        strb.append("</Lon>");

        strb.append("<Login>");
        strb.append(login.trim());
        strb.append("</Login>");

        strb.append("</values>");
        strb.append("</root>");
        
        String sxml = strb.toString();	
        String parametro = Proteger(sxml);
        HashMap<String, Object> values = new HashMap<String, Object> ();
		values.put("param", parametro);
	try
	{	
		 String xmlEnc = super.callService(values);
	     if (xmlEnc.equals(""))
         {
             return null;
         }
         else
         {
             xmlEnc = xmlEnc.replace(" ", "+");
         }
		
	     		 
		 ArrayList<HashMap<String, Object>> valuesARetornar = new ArrayList<HashMap<String,Object>>();
		 
		 String xmlRespuesta = ProcesarRetorno(xmlEnc);
		 String patron = "//root/filas/fila" ;
		 valuesARetornar = CargarValores(xmlRespuesta,patron);
		  
		 if (!valuesARetornar.isEmpty())
		 {  licenciaValidaSyncDTO = new LicenciaValidaSyncDTO();
			HashMap<String,Object> valores   = valuesARetornar.get(0);
			licenciaValidaSyncDTO.licencia = new LicenciaDTO();
			licenciaValidaSyncDTO.licencia.setNumeroLicencia(valores.get("NUMERO_LICENCIA").toString());
			licenciaValidaSyncDTO.licencia.setSistemaUnicoProvincial(valores.get("SISTEMA_UNICO_PROVINCIAL").toString());
			licenciaValidaSyncDTO.licencia.setClaseLicencia(valores.get("CLASE_LICENCIA").toString());
			licenciaValidaSyncDTO.licencia.setNumeroDocumento(valores.get("NRO_DOCUMENTO").toString());
			licenciaValidaSyncDTO.licencia.setIdSexo(valores.get("ID_SEXO").toString());
			licenciaValidaSyncDTO.licencia.setApellido(valores.get("APELLIDO").toString());
			licenciaValidaSyncDTO.licencia.setNombre(valores.get("NOMBRE").toString());
			licenciaValidaSyncDTO.licencia.setFechaVencimiento(valores.get("FECHA_VENCIMIENTO").toString());
			licenciaValidaSyncDTO.licencia.setRetenida(valores.get("RETENIDA").toString());
			licenciaValidaSyncDTO.licencia.setPaisLicencia(valores.get("PAIS_LICENCIA").toString());
			licenciaValidaSyncDTO.licencia.setnPaisLicencia(valores.get("N_PAIS_LICENCIA").toString());
			licenciaValidaSyncDTO.licencia.setProvinciaLicencia(valores.get("PROVINCIA_LICENCIA").toString());
			licenciaValidaSyncDTO.licencia.setnProvinciaLicencia(valores.get("N_PROVINCIA_LICENCIA").toString());
			licenciaValidaSyncDTO.licencia.setDepartamentoLicencia(valores.get("DEPARTAMENTO_LICENCIA").toString());
			licenciaValidaSyncDTO.licencia.setnDepartamentoLicencia(valores.get("N_DEPARTAMENTO_LICENCIA").toString());

			licenciaValidaSyncDTO.licencia.setLocalidadLicencia(valores.get("LOCALIDAD_LICENCIA").toString());
			licenciaValidaSyncDTO.licencia.setnLocalidadLicencia(valores.get("N_LOCALIDAD_LICENCIA").toString());

			licenciaValidaSyncDTO.licencia.setIdPersona(valores.get("ID_PERSONA").toString());
			licenciaValidaSyncDTO.licencia.setEstadoPersona(valores.get("ESTADO_PERSONA").toString());

			licenciaValidaSyncDTO.licencia.setPedidoCaptura(valores.get("PEDIDO_CAPTURA").toString());

			licenciaValidaSyncDTO.licencia.setBarrioTitular(valores.get("BARRIO_TITULAR").toString());
			licenciaValidaSyncDTO.licencia.setCalleTitular(valores.get("CALLE_TITULAR").toString());
			licenciaValidaSyncDTO.licencia.setAlturaTitular(valores.get("ALTURA_TITULAR").toString());
			licenciaValidaSyncDTO.licencia.setPisoTitular(valores.get("PISO_TITULAR").toString());
			licenciaValidaSyncDTO.licencia.setDeptoTitular(valores.get("DEPTO_TITULAR").toString());
			licenciaValidaSyncDTO.licencia.setCodigoPostalTitular(valores.get("CODIGO_POSTAL_TITULAR").toString());
			licenciaValidaSyncDTO.licencia.setIdTipoDocumento(valores.get("ID_TIPO_DOCUMENTO").toString());
			licenciaValidaSyncDTO.licencia.setBarrioTitular(valores.get("BARRIO_TITULAR").toString());
            try {
            	licenciaValidaSyncDTO.licencia.setLicenciaAnulada(valores.get("LICENCIA_ANULADA").toString());
            	licenciaValidaSyncDTO.licencia.setMotivoAnulacionLicencia(valores.get("MOTIVO_ANULACION_LICENCIA").toString());
            	licenciaValidaSyncDTO.licencia.setFechaAnulacionLicencia(valores.get("FECHA_ANULACION_LICENCIA").toString());
            }
            catch (Exception ex){ }

            licenciaValidaSyncDTO.licencia.setPaisTitular(valores.get("PAIS_TITULAR").toString());
            licenciaValidaSyncDTO.licencia.setProvinciaTitular(valores.get("PROVINCIA_TITULAR").toString());
            licenciaValidaSyncDTO.licencia.setDepartamentoTitular(valores.get("DEPARTAMENTO_TITULAR").toString());
            licenciaValidaSyncDTO.licencia.setLocalidadTitular(valores.get("LOCALIDAD_TITULAR").toString());
            
            try
            {
            	licenciaValidaSyncDTO.licencia.setLicenciaVencida(valores.get("LICENCIA_VENCIDA").toString());
            }
            catch (Exception ex){ }

            
            try
            {
            	licenciaValidaSyncDTO.licencia.setRestricciones(valores.get("RESTRICCIONES").toString());
            }
            catch (Exception ex){ }
			
			
			
			 valuesARetornar = new ArrayList<HashMap<String,Object>>();
			 patron = "//root/filas/restriccion" ;
			 valuesARetornar = CargarValores(xmlRespuesta,patron);
			 if (!valuesARetornar.isEmpty())
			 {
				 valores   = valuesARetornar.get(0);
				 RestriccionDTO restriccion = new RestriccionDTO();
				 restriccion.setHayDatos(valores.get("HAY_DATOS").toString());
				 restriccion.setResultado(valores.get("RESULTADO").toString());
				 licenciaValidaSyncDTO.restriciones.add(restriccion);
			 }
		 
		 }

		 
		 return licenciaValidaSyncDTO;
	} 
	catch (DeviceActasSynchronizationException e) {
		e.printStackTrace();
	}
	
	
		
		// en caso de que no se encuentre datos  devolveremos null
	return null;
		
	}
	public DtoUsuarioValidado validarUsuarioEnRepat(String userName, String passWord)
	{
		DtoUsuarioValidado dtoUsuarioValidado = new DtoUsuarioValidado();
		setServiceMethodName("validarUsuarioEnRepat");
		
        StringBuilder strb = new StringBuilder();
        strb.append("<root>");
        strb.append("<values>");

        strb.append("<usuario>");
        strb.append(userName.trim());
        strb.append("</usuario>");

        strb.append("<password>");
        strb.append(passWord.trim());
        strb.append("</password>");

        strb.append("</values>");
        strb.append("</root>");
        
        String sxml = strb.toString();	
        String parametro = Proteger(sxml);
        HashMap<String, Object> values = new HashMap<String, Object> ();
		values.put("param", parametro);
	try
	{	
		 String xmlEnc = super.callService(values);
	     if (xmlEnc.equals(""))
         {
             return dtoUsuarioValidado;
         }
         else
         {
             xmlEnc = xmlEnc.replace(" ", "+");
         }
		
	     		 
		 ArrayList<HashMap<String, Object>> valuesARetornar = new ArrayList<HashMap<String,Object>>();
		 
		 String xmlRespuesta = ProcesarRetorno(xmlEnc);
		 String patron = "//root/filas/fila" ;
		 valuesARetornar = CargarValores(xmlRespuesta,patron);
		 if (!valuesARetornar.isEmpty())
		 {
			HashMap<String,Object> valores   = valuesARetornar.get(0);
			dtoUsuarioValidado.idUsuario = valores.get("ID_USUARIO").toString();//Integer.parseInt(valores.get("ID_USUARIO").toString());
			dtoUsuarioValidado.login = valores.get("LOGIN").toString();
			dtoUsuarioValidado.clave = valores.get("CLAVE").toString();
			dtoUsuarioValidado.habilitado = valores.get("HABILITADO").toString();
			dtoUsuarioValidado.passIngresadoEncrip = valores.get("PassIngresadoEncrip").toString();
			dtoUsuarioValidado.apellidoNombre = valores.get("ApellidoNombre").toString();
		 }
	} 
	catch (DeviceActasSynchronizationException e) {
		e.printStackTrace();
	}
	
	return dtoUsuarioValidado;
		
	}
	public String validarNumeroActa(String numeroSerie,double latitud,double longitud) throws SecurityException, NoSuchMethodException {
		
		 setServiceMethodName("validarNumeroActa");
		 String param = "";
		
		 String numeroActa = "";
		
		 StringBuilder strb = new StringBuilder();
         strb.append("<root>");
         strb.append("<values>");

         strb.append("<numeroSerie>");
         strb.append(numeroSerie.trim());
         strb.append("</numeroSerie>");

         strb.append("</values>");
         strb.append("</root>");
         
         String sxml = strb.toString();	
		
         String parametro = Proteger(sxml);
         
		HashMap<String, Object> values = new HashMap<String, Object> ();
		values.put("param", parametro);

		try {
			    String xmlEnc = super.callService(values);
		        if (xmlEnc.equals(""))
	            {
	                return numeroActa;
	            }
	            else
	            {
	                xmlEnc = xmlEnc.replace(" ", "+");
	            }
		 
		 String ValorInicial ="";
		 
		 ArrayList<HashMap<String, Object>> valuesARetornar = new ArrayList<HashMap<String,Object>>();
		 
		 String xmlRespuesta = ProcesarRetorno(xmlEnc);
		 String patron = "//root/filas/fila" ;
		 valuesARetornar = CargarValores(xmlRespuesta,patron);
		 if (!valuesARetornar.isEmpty())
			 numeroActa = (String) valuesARetornar.get(0).get("NUMERO_ACTA");
		 else
			 numeroActa ="";
		 
		        
		} 
		catch (DeviceActasSynchronizationException e) {
			//e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		return numeroActa;
	}
	
	
	public boolean CerrarInspeccion() {		
		setServiceMethodName("CerrarInspeccion");		
		boolean success = true;
		HashMap<String, Object> values = new HashMap<String, Object> ();
	
		values.put("idInspeccion",getInspeccion());
		try 
			{
				String result = super.callService(values);
				if (result.length()!=0)
				{
					success=false;
				}
				
			} 
		catch (DeviceActasSynchronizationException e) 
			{			
				success = false;
				e.printStackTrace();
			}
		return success;
	}
	
	private int getInspeccion()
	{
//		DaoUti duti= new DaoUti(context);		
//		Uti uti= duti.getUTI();		
//		return uti.getIdInspeccion();
		return 0;
	}

	private void saveContratos (DtoInicioInspeccion dto) {
		
//		if(dto.getContratoId() == 0)
//			return;
//				
//		ContentResolver resolver = context.getContentResolver();
//		resolver.delete(ContratoProvider.CONTENT_URI, null, null);
//		
//		if(dto.getContratoId() != 0) 
//		{
//			ContentValues cv = new ContentValues();
//			cv.put(ContratoProvider._ID, dto.getContratoId());
//			cv.put(ContratoProvider.EMPRESA_ID, dto.getEmpresaId());
//			cv.put(ContratoProvider.EMPRESA_RAZON_SOCIAL, dto.getEmpresaRazonSocial());
//			
//			resolver.insert(ContratoProvider.CONTENT_URI, cv);
//		}
	}
	
	private void saveUTI (DtoInicioInspeccion dto) {
		
//		if(dto.getUTIId() == 0)
//			return;
//				
//		ContentResolver resolver = context.getContentResolver();
//		resolver.delete(UtiProvider.CONTENT_URI, null, null);
//		
//		if(dto.getUTIId() != 0) 
//		{
//			ContentValues cv = new ContentValues();
//			cv.put(UtiProvider._ID, dto.getUTIId());
//			cv.put(UtiProvider.NOMBRE, dto.getUTINombre());
//			cv.put(UtiProvider.ID_INSPECCION, dto.getInspeccionId());
//			cv.put(UtiProvider.AUDITOR, dto.getAuditor());
//			cv.put(UtiProvider.COORDENADAS, dto.getUTICoordenadas());
//			
//			resolver.insert(UtiProvider.CONTENT_URI, cv);
//		}
	}
	private void saveDetallesPenalidad (DtoInicioInspeccion dto) {
		
//		if(dto.getDetallesPenalidad() == null)
//			return;
//		
//		ContentResolver resolver = context.getContentResolver();
//		Iterator<DetallePenalidad> it = dto.getDetallesPenalidad().iterator();
//		
//		resolver.delete(DetallePenalidadProvider.CONTENT_URI, null, null);
//		
//		while(it.hasNext()) 
//		{
//			DetallePenalidad dp = (DetallePenalidad)it.next();
//			
//			ContentValues cv = new ContentValues();
//			cv.put(DetallePenalidadProvider._ID, dp.getId());
//			cv.put(DetallePenalidadProvider.CONTRATO_ID, dto.getContratoId());
//			cv.put(DetallePenalidadProvider.PENALIDAD_ID, dp.getPenalidadId());
//			cv.put(DetallePenalidadProvider.PENALIDAD_PADRE_ID, dp.getPenalidadPadreId());
//			cv.put(DetallePenalidadProvider.PENALIDAD_NOMBRE, dp.getNombre());
//			cv.put(DetallePenalidadProvider.TIEMPO_ESPERA, dp.getTiempoEspera());
//			cv.put(DetallePenalidadProvider.SOLICITAR_CODIGO, dp.isSolicitarCodigo());
//			cv.put(DetallePenalidadProvider.TIEMPO_EN_HORAS, dp.isTiempoEnHoras());
//			cv.put(DetallePenalidadProvider.PENALIDAD_CODIGO, dp.getCodigo());
//			
//			resolver.insert(DetallePenalidadProvider.CONTENT_URI, cv);
//		}
	}
	
	private void saveTiposDefecto (DtoInicioInspeccion dto) {
		
//		if(dto.getDefectos() == null)
//			return;
//		
//		ContentResolver resolver = context.getContentResolver();
//		Iterator<TipoDefecto> it = dto.getDefectos().iterator();
//		
//		resolver.delete(TipoDefectoProvider.CONTENT_URI, null, null);
//		
//		while(it.hasNext()) 
//		{
//			TipoDefecto td = (TipoDefecto)it.next();
//			
//			ContentValues cv = new ContentValues();
//			cv.put(TipoDefectoProvider._ID, td.getId());
//			cv.put(TipoDefectoProvider.CODIGO, td.getCodigo());
//			cv.put(TipoDefectoProvider.DEFECTO_PADRE_ID, td.getTipoDefectoPadre());
//			cv.put(TipoDefectoProvider.DESCRIPCION, td.getDescripcion());
//			cv.put(TipoDefectoProvider.NOMBRE, td.getNombre());
//			cv.put(TipoDefectoProvider.SOLICITAR_CODIGO, td.isSolicitarCodigo());
//			
//			resolver.insert(TipoDefectoProvider.CONTENT_URI, cv);
//		}
	}
	
	private void saveNovedades (DtoInicioInspeccion dto) {
		
//		if(dto.getNovedades() == null)
//			return;
//		
//		ContentResolver resolver = context.getContentResolver();
//		Iterator<Novedad> it = dto.getNovedades().iterator();
//		
//		resolver.delete(NovedadProvider.CONTENT_URI, null, null);
//		
//		while(it.hasNext()) 
//		{
//			Novedad td = (Novedad)it.next();
//			
//			ContentValues cv = new ContentValues();
//			cv.put(NovedadProvider.ESTADO_NOVEDAD_MOVIL, EstadoNovedadMovilEnum.EXISTENTE.toString());
//			cv.put(NovedadProvider._ID, td.getId());
//			cv.put(NovedadProvider._ID_SISTEMA, td.getId());
//			
//			cv.put(NovedadProvider.INSPECCION_ID, td.getInspeccionId());
//			cv.put(NovedadProvider.USUARIO_ID, td.getUsuarioId());
//			cv.put(NovedadProvider.USUARIO_NOMBRE, td.getUsuarioNombre());
//			cv.put(NovedadProvider.EMPRESA, td.getEmpresa());
//			cv.put(NovedadProvider.NUMERO_ACTA, td.getNroActa());
//			cv.put(NovedadProvider.FOTO, td.getFoto());
//			cv.put(NovedadProvider.FECHA_ALTA, td.getFechaAlta().toString());
//			cv.put(NovedadProvider.ESTADO, td.getEstado().toString());
//			cv.put(NovedadProvider.TIPO_NOVEDAD, td.getTipoNovedad().toString());
//			cv.put(NovedadProvider.DETALLE_PENALIDAD, td.getDetallePenalidad());
//			cv.put(NovedadProvider.TIPO_DEFECTO, td.getTipoDefecto());
//			cv.put(NovedadProvider.CODIGO_OBJETO, td.getCodigoObjeto());
//			cv.put(NovedadProvider.TIEMPO_ESPERA, td.getTiempoEspera());
//			cv.put(NovedadProvider.TIEMPO_EN_HORAS, td.isTiempoEnHoras());
//			cv.put(NovedadProvider.OBSERVACION, td.getObservacion());
//			cv.put(NovedadProvider.UBICACION, td.getUbicacion());
//			cv.put(NovedadProvider.LATITUD, td.getLatitud());
//			cv.put(NovedadProvider.LONGITUD, td.getLongitud());
//	       // public string Coordenadas { get; set; }
//	        //public int NumeroComunicacion { get; set; }
//			resolver.insert(NovedadProvider.CONTENT_URI, cv);
//		}
	}
	
	private void saveParametros (DtoInicioInspeccion dto) {
		
//		if(dto.getParametros() == null)
//			return;
//		
//		ContentResolver resolver = context.getContentResolver();
//		Iterator<ParametroGeneral> it = dto.getParametros().iterator();
//		
//		resolver.delete(ParametroGeneralProvider.CONTENT_URI, null, null);
//		
//		while(it.hasNext()) 
//		{
//			ParametroGeneral parametro = (ParametroGeneral)it.next();
//			
//			ContentValues cv = new ContentValues();
//			cv.put(ParametroGeneralProvider._ID, parametro.getId());
//			cv.put(ParametroGeneralProvider.NOMBRE, parametro.getNombre());
//			cv.put(ParametroGeneralProvider.VALOR, parametro.getValor());
//			
//			resolver.insert(ParametroGeneralProvider.CONTENT_URI, cv);
//		}
	}
	
}