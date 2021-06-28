package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import ar.gov.mendoza.PrometeoMuni.core.domain.TipoDocumento;
import ar.gov.mendoza.PrometeoMuni.dao.TipoDocumentoDao;

/*
	 * Business Rules for Genders
	 */
public class TipoDocumentoRules {

	private Context context;
	
	public TipoDocumentoRules (Context context) {
		this.context = context;
	}
	/*
	 * Get All Items of Document Types from Databases
	 */
	public List<TipoDocumento> getAll()
	{
		List<TipoDocumento> lstTiposDocumento = new ArrayList<TipoDocumento>();
		TipoDocumentoDao tipoDocumentoDao = new TipoDocumentoDao();
		lstTiposDocumento  = tipoDocumentoDao.getAll();
		
		return lstTiposDocumento;
		
	}
	
}
