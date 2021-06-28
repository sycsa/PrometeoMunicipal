package ar.gov.mendoza.PrometeoMuni.core.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ar.gov.mendoza.PrometeoMuni.core.domain.ActaConstatacion;
import ar.gov.mendoza.PrometeoMuni.core.domain.Usuario;
import ar.gov.mendoza.PrometeoMuni.core.providers.ActaConstatacionProvider;
import ar.gov.mendoza.PrometeoMuni.core.providers.UsuarioProvider;

//import com.cids.siga.core.base.Enumeraciones.EstadoNovedadMovilEnum;
//import com.cids.siga.core.domain.Novedad;
//import com.cids.siga.core.providers.NovedadProvider;
//import com.cids.siga.core.providers.ParametroGeneralProvider;

public class DaoUsuario implements IDao<Usuario, Integer> {

	private DatabaseHelper helper;
	
	public DaoUsuario(Context context) {
		this.helper = new DatabaseHelper(context);
	}	
	
	public long insert(Usuario entity) {
		
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

	public void update(Usuario entity) {
		// TODO Auto-generated method stub
	}

	public void delete(Integer id) {
		SQLiteDatabase db = helper.getReadableDatabase();
		db.delete(ActaConstatacionProvider.TABLE, ActaConstatacionProvider.ID_ACTA_CONSTATACION + "=?",  new String[] { String.valueOf(id) });

	}

	public Usuario getById(Integer id) {

		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(ActaConstatacionProvider.TABLE, null, ActaConstatacionProvider.ID_ACTA_CONSTATACION + "=?", new String[] { String.valueOf(id) }, null, null, null);
		
		Usuario novedad = null;

		if(cursor.moveToFirst()) { 
			novedad = cast(cursor);
		}
		
		cursor.close();
		db.close();
		
		return novedad;
	}
	
	public long RegistrarUsuario(String id_Usuario, String userName, String userNameEncriptado, String passWordEncriptado, String ApellidoNombre, String Bloqueado)
	{
		SQLiteDatabase db = helper.getReadableDatabase();
		ContentValues values = new ContentValues();
		//ID_USU_BASE, LOGIN, UserName, PassWord 
		values.put(UsuarioProvider.ID_USU_BASE, id_Usuario);
		values.put(UsuarioProvider.LOGIN, userName);
		values.put(UsuarioProvider.USER_NAME, userNameEncriptado);
		values.put(UsuarioProvider.PASSWORD, passWordEncriptado);
		values.put(UsuarioProvider.APELLIDO_NOMBRE, ApellidoNombre);
		values.put(UsuarioProvider.BLOQUEADO, Bloqueado);
		
		
		long id = db.insert(UsuarioProvider.TABLE, null, values);
		db.close();
		
		return id;
	}
	
	public long UpdateUsuario(String id_Usuario, String userName, String userNameEncriptado, String passWordEncriptado, String ApellidoNombre, String Bloqueado)
	{
		SQLiteDatabase db = helper.getReadableDatabase();
		ContentValues values = new ContentValues();
		//ID_USU_BASE, LOGIN, UserName, PassWord 
		values.put(UsuarioProvider.ID_USU_BASE, id_Usuario);
		values.put(UsuarioProvider.LOGIN, userName);
		values.put(UsuarioProvider.USER_NAME, userNameEncriptado);
		values.put(UsuarioProvider.PASSWORD, passWordEncriptado);
		values.put(UsuarioProvider.APELLIDO_NOMBRE, ApellidoNombre);
		values.put(UsuarioProvider.BLOQUEADO, Bloqueado);
		
		long id = db.update(UsuarioProvider.TABLE,  values,UsuarioProvider.ID_USU_BASE + "=?",new String[] { String.valueOf(id_Usuario) });
		db.close();
		
		return id;
	}
	public Usuario getByCredenciales(String pUserName, String pPassWord) {

		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(UsuarioProvider.TABLE, null, UsuarioProvider.USER_NAME + "=? and " + UsuarioProvider.PASSWORD + "=? ", new String[] { pUserName,pPassWord }, null, null, null);
		
		Usuario usuario = null;

		if(cursor.moveToFirst()) { 
			usuario = cast(cursor);
		}
		
		cursor.close();
		db.close();
		
		return usuario;
	}
	
	public Usuario getByIdSistemaRePAT(String id) {

		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(UsuarioProvider.TABLE, null, UsuarioProvider.ID_USU_BASE + "=?", new String[] { String.valueOf(id) }, null, null, null);
		
		Usuario usuario = null;

		if(cursor.moveToFirst()) { 
			usuario = cast(cursor);
		}
		
		cursor.close();
		db.close();
		
		return usuario;
	}

	public ArrayList<Usuario> getAll() {
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
	public static ArrayList<Usuario> castAll(Cursor cursor) {
		
		ArrayList<Usuario> novedades = new ArrayList<Usuario>();
		
		while(cursor.moveToNext()) {
			novedades.add(cast(cursor));
		}
		
		return novedades;
	}
	
	public static Usuario cast(Cursor cursor) {
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Usuario usuario = null;
		
		usuario = new Usuario();		
		
		usuario.setIdUsuario(cursor.getInt(cursor.getColumnIndex(UsuarioProvider.ID_USUARIO)));
		usuario.setBloqueado(cursor.getString(cursor.getColumnIndex(UsuarioProvider.BLOQUEADO)));
		usuario.setApellidoNombre(cursor.getString(cursor.getColumnIndex(UsuarioProvider.APELLIDO_NOMBRE)));
		usuario.setIdUsuBase(cursor.getString(cursor.getColumnIndex(UsuarioProvider.ID_USU_BASE)));
		usuario.setLogin(cursor.getString(cursor.getColumnIndex(UsuarioProvider.LOGIN)));
		usuario.setUserName(cursor.getString(cursor.getColumnIndex(UsuarioProvider.USER_NAME)));
		usuario.setPassWord(cursor.getString(cursor.getColumnIndex(UsuarioProvider.PASSWORD)));
		
		try {
			String fechaDesde = cursor.getString(cursor.getColumnIndex(UsuarioProvider.FECHA_DESDE));
			if(fechaDesde != null)
				usuario.setFechaDesde(dateFormatter.parse(fechaDesde));
			
			String fechaAlta = cursor.getString(cursor.getColumnIndex(UsuarioProvider.FECHA_HASTA));
			if(fechaAlta != null)
				usuario.setFechaHasta(dateFormatter.parse(fechaAlta));
				
		}
		catch (ParseException e) {
		}
		
		return usuario;
	}
}
