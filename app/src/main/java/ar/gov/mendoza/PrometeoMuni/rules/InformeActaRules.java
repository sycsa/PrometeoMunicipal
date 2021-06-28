package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;

import ar.gov.mendoza.PrometeoMuni.core.domain.ActaConstatacion;
import ar.gov.mendoza.PrometeoMuni.core.domain.InformeActa;
import ar.gov.mendoza.PrometeoMuni.core.util.Tools;
import ar.gov.mendoza.PrometeoMuni.dao.InformeActaDao;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;

/*
	 * Bussines Rules for Countries
	 */
public class InformeActaRules {

	private Context context;
	
	public InformeActaRules (Context context) {
		this.context = context;
	}
	/*
	 * Get All Items of Countries from Databases
	 */
	public List<InformeActa> getAll()
	{
		List<InformeActa> lstInformeActas = new ArrayList<InformeActa>();
		InformeActaDao informeActaDao = new InformeActaDao();
		String[] aSort = {"_id" }; 
		lstInformeActas= informeActaDao.getAll(aSort);
		
		return lstInformeActas;
		
	}
	
	public Boolean marcarSincronizadoInformeActa(Integer pId)
	{
		Boolean bResultado = false;
		
		InformeActaDao informeActaDao = new InformeActaDao();
		InformeActa informeActa = informeActaDao.getItem(pId.toString());
		
		if (informeActa==null) return false;
		
		informeActa.setEstado("Transmitido");
		
		int rowsAffected = informeActaDao.update(informeActa);
		if(rowsAffected>0)
			bResultado= true;
		
		
		return bResultado;
	}
	public Boolean grabarInformeActa(ActaConstatacion pActa)
	{
		Boolean bResultado = false;
		
		InformeActa informeActa = new InformeActa();
		
		String  login = GlobalVar.getInstance().getLogin();
		Date hoy = Tools.Today();
		String numeroReferencia = pActa.getNumeroDocumento();
		
		/*
		  if (numeroReferencia.length()>4)
		 
		{
			numeroReferencia = numeroReferencia.substring(numeroReferencia.length()-4,numeroReferencia.length());
		}
		else
		{
			numeroReferencia =Tools.formatearIzquierda(numeroReferencia, 4, '0');
		}
		*/
		
		numeroReferencia = pActa.getNumeroActa().trim();//pActa.getCodigoBarra().substring(6, 16+6).trim().toString();
		
		informeActa.setId(pActa.getIdActaConstatacion());
		informeActa.setUsuario(login);
		informeActa.setEstado("Pendiente");
		informeActa.setFechayHora(hoy);
		informeActa.setNumeroReferencia(numeroReferencia);
		
		InformeActaDao informeActaDao = new InformeActaDao();
		long id = informeActaDao.insert(informeActa);
		if (id>0)
			bResultado = true;
		
		return bResultado;
	}
	
	
}
