package ar.gov.mendoza.PrometeoMuni;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ar.gov.mendoza.PrometeoMuni.core.domain.PersonaPadron;
import ar.gov.mendoza.PrometeoMuni.abstracts.HandlerSearchableListViewInterface;
import ar.gov.mendoza.PrometeoMuni.abstracts.SearchableActivity;
import ar.gov.mendoza.PrometeoMuni.dao.PersonaPadronDao;

public class PersonaSearchableActivity extends SearchableActivity<PersonaPadron> {
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.titleHeader ="Listado de Personas Encontradas";
		this.genericDao = new PersonaPadronDao();
		this.FIELD_SEARCH_DEFAULT ="NUMERO_DOCUMENTO";
		this.handlerSelectItemClick = new HandlerSearchableListViewInterface() {
			
			@Override
			public void onSelectedItem(Object objItem,View view) {
				
				
		        PersonaPadron per =(PersonaPadron) objItem;
		         
				//Utilities.ShowToast(PersonaSearchableActivity.this,"Recibiendo el Item Seleccionado" + per.getItemName());
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
