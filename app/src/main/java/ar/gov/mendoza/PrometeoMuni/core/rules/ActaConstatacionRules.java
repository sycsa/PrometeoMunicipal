package ar.gov.mendoza.PrometeoMuni.core.rules;

import android.content.Context;

import ar.gov.mendoza.PrometeoMuni.core.data.DaoActaConstatacion;
import ar.gov.mendoza.PrometeoMuni.core.data.IDao;
import ar.gov.mendoza.PrometeoMuni.core.domain.ActaConstatacion;
import ar.gov.mendoza.PrometeoMuni.core.exceptions.DeviceActasValidationException;

public class ActaConstatacionRules {
	
	private DaoActaConstatacion dao;
	private Context context;
	
	public ActaConstatacionRules (Context context) {
		this.dao = new DaoActaConstatacion(context);
		this.context = context;
	}

	protected IDao<ActaConstatacion, Integer> getDaoInstance() {
		return dao;
	}
	
	public ActaConstatacion getById(int id) {
		return getDaoInstance().getById(id);
	}
	
	public void insert(ActaConstatacion entity) throws DeviceActasValidationException {

		validarNovedad(entity);
		getDaoInstance().insert(entity);
	}
	
	private void validarNovedad(ActaConstatacion entity) throws DeviceActasValidationException {

		// TODO Agregar validacion para que la novedad no se repita dentro de un radio de 100 mts.
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