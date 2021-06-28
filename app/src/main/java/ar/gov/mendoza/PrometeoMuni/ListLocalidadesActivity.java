package ar.gov.mendoza.PrometeoMuni;

import android.os.Bundle;

import ar.gov.mendoza.PrometeoMuni.core.domain.Localidad;
import ar.gov.mendoza.PrometeoMuni.abstracts.SearchableActivity;
import ar.gov.mendoza.PrometeoMuni.dao.LocalidadDao;

public class ListLocalidadesActivity extends SearchableActivity<Localidad>//ListActivity {
{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		this.titleHeader ="Listado de Localidades";
		this.genericDao = new LocalidadDao();
		this.FIELD_SEARCH_DEFAULT ="NOMBRE";
		this.SHOW_ALL_ITEMS_DEFAULT = true;
		this.LIKEABLE_SEARCH_DEFAULT = true;
		this.OBJETO_BUSQUEDA_DEFAULT ="LOCALIDADES";
		
		//requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
		/*setContentView(R.layout.list_generico);
		// MAKE QUERY TO CONTACT CONTENTPROVIDER
		String[] projections = new String[] { LocalidadProvider.ID_LOCALIDAD,LocalidadProvider.NOMBRE,LocalidadProvider.ID_DEPARTAMENTO};
		Cursor c = getContentResolver().query(LocalidadProvider.CONTENT_URI,	projections, null, null, null);
		startManagingCursor(c);
		// THE DESIRED COLUMNS TO BE BOUND
		String[] columns = new String[] {LocalidadProvider.ID_LOCALIDAD,LocalidadProvider.NOMBRE,LocalidadProvider.ID_DEPARTAMENTO};
		// THE XML DEFINED VIEWS FOR EACH FIELD TO BE BOUND TO
		int[] to = new int[] { R.id.id_entry, R.id.name_entry,R.id.type_entry};//, R.id.number_entry,R.id.number_type_entry };
		// CREATE ADAPTER WITH CURSOR POINTING TO DESIRED DATA
		SimpleCursorAdapter cAdapter = new SimpleCursorAdapter(this,R.layout.list_entry, c, columns, to);
		// SET THIS ADAPTER AS YOUR LIST ACTIVITY'S ADAPTER
		 this.setListAdapter(cAdapter);*/
		
	}
}
