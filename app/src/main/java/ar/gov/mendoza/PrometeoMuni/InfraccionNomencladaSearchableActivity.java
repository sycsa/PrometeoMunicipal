package ar.gov.mendoza.PrometeoMuni;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import ar.gov.mendoza.PrometeoMuni.core.domain.InfraccionNomenclada;
import ar.gov.mendoza.PrometeoMuni.abstracts.HandlerSearchableListViewInterface;
import ar.gov.mendoza.PrometeoMuni.abstracts.SearchableActivity;
import ar.gov.mendoza.PrometeoMuni.dao.InfraccionNomencladaDao;

import android.view.View;
public class InfraccionNomencladaSearchableActivity extends SearchableActivity<InfraccionNomenclada> {
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.titleHeader ="Listado de Infracciones Nomencladas Encontradas";
		this.genericDao = new InfraccionNomencladaDao();
		this.FIELD_SEARCH_DEFAULT ="DESCRIPCION_CORTA";
		this.FIELD_SEARCH2_DEFAULT ="CODIGO";
		this.LIKEABLE_SEARCH_DEFAULT = true;
		this.LIKEABLE_OR = true;
		this.PERMITIR_BUSQUEDA_SIN_DATOS = true;
		this.idLayoutItems = R.layout.list_item_with_two_text;
		this.handlerSelectItemClick = new HandlerSearchableListViewInterface() {
			
			@Override
			public void onSelectedItem(Object objItem,View view) {
				InfraccionNomenclada infraccionNomenclada =(InfraccionNomenclada) objItem;
		         
				//Utilities.ShowToast(InfraccionNomencladaSearchableActivity.this,"Recibiendo el Item Seleccionado" + infraccionNomenclada.getItemName());
				Intent intent = new Intent();
		        Bundle appData = new Bundle();
		        
		        appData.putSerializable("ItemObject",infraccionNomenclada); 
		        
		        intent.putExtra(SearchManager.APP_DATA,appData);
		        setResult(RESULT_OK, intent);
		        finish();
				
			}
		};
		super.onCreate(savedInstanceState);
		
	}
	

	
}
