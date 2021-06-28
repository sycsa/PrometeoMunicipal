package ar.gov.mendoza.PrometeoMuni;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ar.gov.mendoza.PrometeoMuni.core.domain.Licencia;
import ar.gov.mendoza.PrometeoMuni.abstracts.HandlerSearchableListViewInterface;
import ar.gov.mendoza.PrometeoMuni.abstracts.SearchableActivity;
import ar.gov.mendoza.PrometeoMuni.dao.LicenciaDao;
import ar.gov.mendoza.PrometeoMuni.utils.Utilities;

public class LicenciaSearchableActivity extends SearchableActivity<Licencia> {
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.titleHeader ="Listado de Licencias Encontradas";
		this.genericDao = new LicenciaDao();
		this.FIELD_SEARCH_DEFAULT ="MATRICULA";
		this.handlerSelectItemClick = new HandlerSearchableListViewInterface() {
			
			@Override
			public void onSelectedItem(Object objItem,View view) {
		        Licencia per =(Licencia) objItem;
		         
				Utilities.ShowToast(LicenciaSearchableActivity.this,"Recibiendo el Item Seleccionado" + per.getItemName());
				Intent intent = new Intent();
		        Bundle appData = new Bundle();
		        
		        appData.putSerializable("ItemObject",per); 
		        
		        intent.putExtra(SearchManager.APP_DATA,appData);
		        setResult(RESULT_OK, intent);
		        finish();
				
			}
		};
		super.onCreate(savedInstanceState);
		
	}

	
}
