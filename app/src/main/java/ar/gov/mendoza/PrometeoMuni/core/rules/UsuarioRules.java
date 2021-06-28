package ar.gov.mendoza.PrometeoMuni.core.rules;

import android.content.Context;

import ar.gov.mendoza.PrometeoMuni.core.data.DaoUsuario;
import ar.gov.mendoza.PrometeoMuni.core.data.IDao;
import ar.gov.mendoza.PrometeoMuni.core.domain.Usuario;
import ar.gov.mendoza.PrometeoMuni.core.exceptions.DeviceActasOrmException;
import ar.gov.mendoza.PrometeoMuni.core.exceptions.DeviceActasValidationException;

public class UsuarioRules {
	
	private DaoUsuario dao;
	private Context context;
	
	public UsuarioRules (Context context) {
		this.dao = new DaoUsuario(context);
		this.context = context;
	}

	protected IDao<Usuario, Integer> getDaoInstance() {
		return dao;
	}
	
	public Usuario getById(int id) {
		return getDaoInstance().getById(id);
	}
	
	public void insert(Usuario entity) throws DeviceActasValidationException {

		validarNovedad(entity);
		getDaoInstance().insert(entity);
	}
	
	
	private void validarNovedad(Usuario entity) throws DeviceActasValidationException {

		// TODO Agregar validacion para que la novedad no se repita dentro de un radio de 100 mts.
	}
	
	public long RegistrarUsuario(String id_Usuario, String userName, String userNameEncriptado, String passWordEncriptado, String ApellidoNombre, String Bloqueado)
	{
		// preguntaremos si el id_Usuario  que viene de RePAT  ya existe en la base local, si existe la actualizamos
			
			Usuario usuario = dao.getByIdSistemaRePAT(id_Usuario);
			if (usuario==null)
				return dao.RegistrarUsuario(id_Usuario, userName, userNameEncriptado, passWordEncriptado, ApellidoNombre,Bloqueado);
			else
				return dao.UpdateUsuario(id_Usuario, userName, userNameEncriptado, passWordEncriptado, ApellidoNombre,Bloqueado);
		
	}
	public Usuario getUsuario(String pUserName , String pPassWord) throws DeviceActasOrmException {
		Usuario usuario = null;
		usuario = dao.getByCredenciales(pUserName, pPassWord);
		if (usuario==null)
			usuario = new Usuario();
		return usuario;
	}
	
	/*
	public List<Novedad> getNovedadesPendientesEnRadio(double latitud, double longitud) throws SigaOrmException {
		
		List<Novedad> list = dao.getNovedadPendientes();
		List<Novedad> pendientes = new ArrayList<Novedad>();

		Punto latLanActual = new Punto(latitud, longitud);		
		Iterator<Novedad> it = list.iterator();
		
		double radioEnMetros = dao.getRadioNovedadesPendientes();
		
		while(it.hasNext()) {
			
			Novedad novedad = it.next();			
			Punto latLanNovedad = new Punto(novedad.getLatitud(), novedad.getLongitud());
			
			double distancia = MapUtilities.calcularDistanciaLinealEnMetros(latLanActual, latLanNovedad);
			
			if(distancia <= radioEnMetros) {
				pendientes.add(novedad);
			}
		}
		
		return pendientes;
	}
	*/
	/*
	public Boolean dentroDeRadio(double ltnovedad, double lgnovedad,double ltInspector, double lgInspector) throws SigaOrmException {
		
		double radioEnMetros = dao.getRadioNovedadesPendientes();
		
		Punto latLanNovedad = new Punto(ltnovedad, lgnovedad);	
		Punto latLanInspector = new Punto(ltInspector, lgInspector);
		
		double distancia = MapUtilities.calcularDistanciaLinealEnMetros(latLanNovedad, latLanInspector);			
		return distancia <= radioEnMetros;
	}
	*/
	/*
	public Boolean dentroDeRadioParaValidar(double ltnovedad, double lgnovedad,double ltInspector, double lgInspector) throws SigaOrmException {
		
		double radioEnMetros = dao.getRadioParaValidar();
		
		Punto latLanNovedad = new Punto(ltnovedad, lgnovedad);	
		Punto latLanInspector = new Punto(ltInspector, lgInspector);
		
		double distancia = MapUtilities.calcularDistanciaLinealEnMetros(latLanNovedad, latLanInspector);			
		return distancia <= radioEnMetros;
	}
	*/
	/*
	public List<Novedad> getNovedadesPendientes() { 
		return  dao.getNovedadPendientes();
	}
	
	*/
	/*
	public Uti getUti() { 
		DaoUti duti= new DaoUti(context);
		return  duti.getUTI();	
	}
	*/
	/*
	public Novedad getNovedadByIdSistema(int idSistema) {
		return ((DaoActaConstatacion)getDaoInstance()).getNovedadByIdSistema(idSistema);
	}
	
	public void cambiarEstadoNovedad(Novedad novedad) {
		((DaoActaConstatacion)getDaoInstance()).cambiarEstadoNovedad(novedad);
	}*/
}