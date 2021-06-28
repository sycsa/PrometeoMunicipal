package ar.gov.mendoza.PrometeoMuni.core.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ar.gov.mendoza.PrometeoMuni.core.domain.ActaConstatacion;
import ar.gov.mendoza.PrometeoMuni.core.providers.ActaConstatacionProvider;

//import com.cids.siga.core.base.Enumeraciones.EstadoNovedadMovilEnum;
//import com.cids.siga.core.domain.Novedad;
//import com.cids.siga.core.providers.NovedadProvider;
//import com.cids.siga.core.providers.ParametroGeneralProvider;

public class DaoActaConstatacion implements IDao<ActaConstatacion, Integer> {

	private DatabaseHelper helper;
	
	public DaoActaConstatacion(Context context) {
		this.helper = new DatabaseHelper(context);
	}	
	
	public long insert(ActaConstatacion entity) {
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ContentValues values = new ContentValues();
		/*
		values.put(NovedadProvider.EMPRESA, entity.getEmpresa());
		values.put(NovedadProvider.TIPO_NOVEDAD, entity.getTipoNovedad().toString());
		values.put(NovedadProvider.CODIGO_OBJETO, entity.getCodigoObjeto());
		values.put(NovedadProvider.DETALLE_PENALIDAD, entity.getDetallePenalidad());
		values.put(NovedadProvider.TIPO_DEFECTO, entity.getTipoDefecto());
		values.put(NovedadProvider.TIEMPO_ESPERA, entity.getTiempoEspera());
		values.put(NovedadProvider.TIEMPO_EN_HORAS, entity.isTiempoEnHoras());
		values.put(NovedadProvider.ESTADO, entity.getEstado().toString());
		values.put(NovedadProvider.OBSERVACION, entity.getObservacion());
		values.put(NovedadProvider.LATITUD, entity.getLatitud());
		values.put(NovedadProvider.LONGITUD, entity.getLongitud());
		values.put(NovedadProvider.UBICACION, entity.getUbicacion());
		values.put(NovedadProvider.FOTO, entity.getFoto());
		values.put(NovedadProvider.FECHA_ALTA, entity.getFechaAlta());
		values.put(NovedadProvider.USUARIO_ID, entity.getUsuarioId());
		values.put(NovedadProvider.USUARIO_NOMBRE, entity.getUsuarioNombre());
		values.put(NovedadProvider.INSPECCION_ID, entity.getInspeccionId());
		values.put(NovedadProvider.ESTADO_NOVEDAD_MOVIL, entity.getEstadoNovedadMovil().toString());
		*/
		SQLiteDatabase db = helper.getWritableDatabase();
		
		long id = db.insert(ActaConstatacionProvider.TABLE, null, values);
		
		db.close();
		
		return id;
	}

	public void update(ActaConstatacion entity) {
		// TODO Auto-generated method stub
	}

	public void delete(Integer id) {
		SQLiteDatabase db = helper.getReadableDatabase();
		db.delete(ActaConstatacionProvider.TABLE, ActaConstatacionProvider.ID_ACTA_CONSTATACION + "=?",  new String[] { String.valueOf(id) });

	}

	public ActaConstatacion getById(Integer id) {

		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(ActaConstatacionProvider.TABLE, null, ActaConstatacionProvider.ID_ACTA_CONSTATACION + "=?", new String[] { String.valueOf(id) }, null, null, null);
		
		ActaConstatacion novedad = null;

		if(cursor.moveToFirst()) { 
			novedad = cast(cursor);
		}
		
		cursor.close();
		db.close();
		
		return novedad;
	}
	
	public ActaConstatacion getByIdSistema(Integer id) {

		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(ActaConstatacionProvider.TABLE, null, ActaConstatacionProvider.ID_JUZGADO + "=?", new String[] { String.valueOf(id) }, null, null, null);
		
		ActaConstatacion novedad = null;

		if(cursor.moveToFirst()) { 
			novedad = cast(cursor);
		}
		
		cursor.close();
		db.close();
		
		return novedad;
	}

	public ArrayList<ActaConstatacion> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
		
	public void cambiarEstadoNovedad(ActaConstatacion novedad) {
		try
		{
		ContentValues values = new ContentValues();
		//values.put(ActaConstatacionProvider.PISO, novedad.getEstado().toString());
		/*
		if(novedad.getFechaEnvio()==null)
		{
			values.put(NovedadProvider.FECHA_SINCRONIZACION, (String)null);
		}
		else
		{
			values.put(NovedadProvider.FECHA_SINCRONIZACION, novedad.getFechaEnvio().toString());
		}
		*/
		/*
		values.put(NovedadProvider.ESTADO_NOVEDAD_MOVIL, novedad.getEstadoNovedadMovil().toString());
		values.put(NovedadProvider.FOTO, novedad.getFoto());
		values.put(NovedadProvider.NUMERO_ACTA, novedad.getNroActa());
		values.put(NovedadProvider._ID_SISTEMA, novedad.getIdSistema());
		*/
		SQLiteDatabase db = helper.getWritableDatabase();
		
		//busco por idsistema primero
		/*
		int i = db.update(ActaConstatacionProvider.TABLE, values, ActaConstatacionProvider.ID_JUZGADO+ "=?", new String [] { String.valueOf(novedad.getIdSistema()) });		
		if (i==0)
		{
			i = db.update(NovedadProvider.TABLE, values, NovedadProvider._ID + "=?", new String [] { String.valueOf(novedad.getId()) });		
		}
		*/
		db.close();
		}
		catch(Exception e)
		{
			
		}
	}
	
	/*public Novedad getNovedadByIdSistema(int idSistema) {
		
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(NovedadProvider.TABLE, null, NovedadProvider._ID_SISTEMA + "=?", new String [] { String.valueOf(idSistema) }, null, null, null);
		
		Novedad novedad = null;

		if(cursor.moveToFirst()) { 
			novedad = cast(cursor);
		}
		
		cursor.close();
		db.close();
		
		return novedad;
	}
	*/
	
	/*public List<Novedad> buscarNovedadAnterior(Novedad novedad) {
		
		String where = "strftime('%m', " + NovedadProvider.FECHA_ALTA + ") = strftime('%m', DATETIME('now')) ";
		where += "AND (? IS NULL OR " + NovedadProvider.CODIGO_OBJETO + "=?) ";
		
		String[] parametros = new String[4];
		parametros[0] = novedad.getTipoNovedad().toString();
		parametros[2] = novedad.getCodigoObjeto();
		parametros[3] = novedad.getCodigoObjeto();
		
		switch(novedad.getTipoNovedad()) 
		{
			case NOVEDAD_DEFECTO: 
				parametros[1] = novedad.getTipoDefecto();
				where += "AND (" + NovedadProvider.TIPO_NOVEDAD + "=? AND " + NovedadProvider.TIPO_DEFECTO + "=?)";
				break;
				
			case NOVEDAD_DEFICIENCIA: 
				parametros[1] = novedad.getDetallePenalidad();
				where += "AND (" + NovedadProvider.TIPO_NOVEDAD + "=? AND " + NovedadProvider.DETALLE_PENALIDAD + "=?)";
				break;
		}
		
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(NovedadProvider.TABLE, null, where, parametros, null, null, NovedadProvider.FECHA_ALTA + " ASC");
		
		List<Novedad> novedades = castAll(cursor);
		
		cursor.close();
		db.close();
		
		return novedades;
	}*/
	
	/*public List<Novedad> getNovedadPendientes() {
		
		SQLiteDatabase db = helper.getReadableDatabase();
		//Cursor cursor = db.query(NovedadProvider.TABLE, null, 
		//		"(julianday(DATETIME('now')) - julianday(" + NovedadProvider.FECHA_ALTA + ")) <= ?",
		//		new String[] { "30" }, 
		//		null, null, NovedadProvider.ESTADO + " ASC");
		
		Cursor cursor = db.query(NovedadProvider.TABLE, null, 
				null,null,null,null,null);
				
		List<Novedad> novedades = castAll(cursor);
		
		cursor.close();
		db.close();
		
		return novedades;
	}*/
	/*
	public List<Novedad> getNovedadPendientesEnvio() {
		
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(NovedadProvider.TABLE, null, 
				"" + NovedadProvider.ESTADO_NOVEDAD_MOVIL + "= ? or " +  NovedadProvider.ESTADO_NOVEDAD_MOVIL + "= ?",
				new String[] { EstadoNovedadMovilEnum.MODIFICADA.toString(),EstadoNovedadMovilEnum.NUEVA.toString() }, 
				null, null, null);
				
		List<Novedad> novedades = castAll(cursor);
		cursor.close();
		db.close();
		return novedades;
	}
*/
	/*public double getRadioNovedadesPendientes() {
		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.query(ParametroGeneralProvider.TABLE, 
				new String[] { ParametroGeneralProvider.VALOR }, 
				ParametroGeneralProvider.NOMBRE + "=?", 
				new String[] { "RadioDeNovedad" }, null, null, null);
		
		double distancia = 100;
		
		if(cursor.moveToFirst()) {
			distancia = cursor.getDouble(0);
		}
		
		cursor.close();
		db.close();
		
		return distancia;
	}*/
	
	/*public double getRadioParaValidar() {
		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.query(ParametroGeneralProvider.TABLE, 
				new String[] { ParametroGeneralProvider.VALOR }, 
				ParametroGeneralProvider.NOMBRE + "=?", 
				new String[] { "RadioParaValidar" }, null, null, null);
		
		double distancia = 5;
		
		if(cursor.moveToFirst()) {
			distancia = cursor.getDouble(0);
		}
		
		cursor.close();
		db.close();
		
		return distancia;
	}
	*/
	public static ArrayList<ActaConstatacion> castAll(Cursor cursor) {
		
		ArrayList<ActaConstatacion> novedades = new ArrayList<ActaConstatacion>();
		
		while(cursor.moveToNext()) {
			novedades.add(cast(cursor));
		}
		
		return novedades;
	}
	
	public static ActaConstatacion cast(Cursor cursor) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ActaConstatacion novedad = null;
		
		novedad = new ActaConstatacion();
		/*
		novedad.setId(cursor.getInt(cursor.getColumnIndex(NovedadProvider._ID)));
		novedad.setIdSistema(cursor.getInt(cursor.getColumnIndex(NovedadProvider._ID_SISTEMA)));
		novedad.setEmpresa(cursor.getInt(cursor.getColumnIndex(NovedadProvider.EMPRESA)));
		novedad.setTipoNovedad(cursor.getString(cursor.getColumnIndex(NovedadProvider.TIPO_NOVEDAD)));
		novedad.setCodigoObjeto(cursor.getString(cursor.getColumnIndex(NovedadProvider.CODIGO_OBJETO)));
		novedad.setDetallePenalidad(cursor.getString(cursor.getColumnIndex(NovedadProvider.DETALLE_PENALIDAD)));
		novedad.setTipoDefecto(cursor.getString(cursor.getColumnIndex(NovedadProvider.TIPO_DEFECTO)));
		novedad.setTiempoEspera(cursor.getInt(cursor.getColumnIndex(NovedadProvider.TIEMPO_ESPERA)));
		novedad.setTiempoEnHoras(cursor.getInt(cursor.getColumnIndex(NovedadProvider.TIEMPO_EN_HORAS)) == 1);
		novedad.setEstado(cursor.getString(cursor.getColumnIndex(NovedadProvider.ESTADO)));
		novedad.setObservacion(cursor.getString(cursor.getColumnIndex(NovedadProvider.OBSERVACION)));
		novedad.setLatitud(cursor.getDouble(cursor.getColumnIndex(NovedadProvider.LATITUD)));
		novedad.setLongitud(cursor.getDouble(cursor.getColumnIndex(NovedadProvider.LONGITUD)));
		novedad.setUbicacion(cursor.getString(cursor.getColumnIndex(NovedadProvider.UBICACION)));
		novedad.setFoto(cursor.getString(cursor.getColumnIndex(NovedadProvider.FOTO)));
		novedad.setUsuarioNombre(cursor.getString(cursor.getColumnIndex(NovedadProvider.USUARIO_NOMBRE)));
		novedad.setUsuarioId(cursor.getInt(cursor.getColumnIndex(NovedadProvider.USUARIO_ID)));
		novedad.setInspeccionId(cursor.getInt(cursor.getColumnIndex(NovedadProvider.INSPECCION_ID)));
		novedad.setNroActa(cursor.getInt(cursor.getColumnIndex(NovedadProvider.NUMERO_ACTA)));
		novedad.setEstadoNovedadMovil(cursor.getString(cursor.getColumnIndex(NovedadProvider.ESTADO_NOVEDAD_MOVIL)) );
		try {
			String fechaSincronizacion = cursor.getString(cursor.getColumnIndex(NovedadProvider.FECHA_SINCRONIZACION));
			if(fechaSincronizacion != null)
				novedad.setFechaEnvio(dateFormatter.parse(fechaSincronizacion));
			
			String fechaAlta = cursor.getString(cursor.getColumnIndex(NovedadProvider.FECHA_ALTA));
			if(fechaAlta != null)
				//novedad.setFechaAlta(dateFormatter.parse(fechaAlta));
				novedad.setFechaAlta(fechaAlta);
		}
		catch (ParseException e) { 
			//Calendar c=  Calendar.getInstance();
			//novedad.setFechaAlta(c.getTime());
		}*/
		
		return novedad;
	}
}
