package ar.gov.mendoza.PrometeoMuni.sync;

/*import com.cids.siga.core.data.DaoNovedad;
import com.cids.siga.core.data.DaoUti;
import com.cids.siga.core.domain.Novedad;
import com.cids.siga.core.domain.Uti;*/
//import com.cids.siga.core.providers.NovedadProvider;

import android.content.Context;

public class SyncNovedad extends SyncBase {

	public SyncNovedad(Context context) {
		 super(context, null);
	}
	
	public boolean sincronizarNovedadesWeb(Boolean cierreInspeccion) {		
		setServiceMethodName("Actualizar");		
		
		boolean success = true;
		
		return success;
	}
	
	
	
	

	

}