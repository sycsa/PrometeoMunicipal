package ar.gov.mendoza.PrometeoMuni.sync;

import java.lang.reflect.Type;
import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import ar.gov.mendoza.PrometeoMuni.core.exceptions.DeviceActasSynchronizationException;

//import com.cids.siga.core.base.Enumeraciones.EstadoNovedadMovilEnum;
//import com.cids.siga.core.data.DaoUti;
import ar.gov.mendoza.PrometeoMuni.sync.dto.DtoInicioInspeccion;

//import com.cids.siga.sync.dto.DtoInicioInspeccion;
//import com.cids.siga.sync.dto.DtoInspeccion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class SyncInspeccion extends SyncBase {	  
		
	public SyncInspeccion(Context context) {
		 super(context, "GetUtiControl");
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

	public String validateLogin(String usuario, String clave, int control,double latitud,double longitud) throws SecurityException, NoSuchMethodException {
		
		setServiceMethodName("IniciarInspeccion");
		
		HashMap<String, Object> values = new HashMap<String, Object> ();
		values.put("usuario", usuario);
		values.put("clave", clave);
		values.put("control", control);
		values.put("latitud",  String.valueOf(latitud));
		values.put("longitud",  String.valueOf(longitud));
		
		values.put("imei", 0);
		
		String error = null;
		DtoInicioInspeccion dto =new DtoInicioInspeccion();
		try {
			String result = super.callService(values);
			
			GsonBuilder b = new GsonBuilder();	    
		    Gson gson = b.create();
		    
		    Type type = new TypeToken<DtoInicioInspeccion>(){}.getType();
		     dto = gson.fromJson(result, type);
		    
		    if(dto != null && dto.getError() != null) {
		    	error = dto.getError(); 
		    }
		    else {
		    	saveContratos(dto);
			    saveDetallesPenalidad(dto);
			    saveParametros(dto);
			    saveTiposDefecto(dto);
			    saveNovedades(dto);
			    saveUTI(dto);
			    
			    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
				SharedPreferences.Editor editor = preferences.edit();
				
	/*			editor.putString(Preferencias.PREFERENCES_SESION_USUARIO, dto.getUsuarioNombre());
				editor.putInt(Preferencias.PREFERENCES_SESION_USUARIO_ID, dto.getUsuarioId());
				editor.putInt(Preferencias.PREFERENCES_SESION_INSPECCION_ID, dto.getInspeccionId());
*/				editor.commit();
		    }
		} 
		catch (DeviceActasSynchronizationException e) {
			dto.setError(e.getMessage());
			e.printStackTrace();
		}
		
		return error;
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
