
package ar.gov.mendoza.PrometeoMuni.abstracts;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ar.gov.mendoza.PrometeoMuni.R;
import ar.gov.mendoza.PrometeoMuni.R.id;
import ar.gov.mendoza.PrometeoMuni.R.layout;
import ar.gov.mendoza.PrometeoMuni.R.string;
import ar.gov.mendoza.PrometeoMuni.core.domain.ISeleccionable;
import ar.gov.mendoza.PrometeoMuni.adapters.GenericListAdapter;
import ar.gov.mendoza.PrometeoMuni.dao.IBaseDao;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;
import ar.gov.mendoza.PrometeoMuni.utils.Utilities;

import java.lang.reflect.Type;

public class SearchableActivity<T extends ISeleccionable<String>> extends ActionBarListActivity implements SearchView.OnQueryTextListener {
	protected List<T> lstItems;
	protected ProgressDialog progressDialog;
	public HandlerSearchableListViewInterface handlerSelectItemClick;
	
	public IBaseDao<T> genericDao;
	public T instanceGenericClass;  
	public ContentValues contentValues;
	
	public String titleHeader="Persona"; 
	
	public	TextView textView_PatronBusqueda;
	public	String initValueSearch="";
	
	public int idLayoutItems =0;
	public int idLayoutActivity =0;
	
	public String OBJETO_BUSQUEDA_DEFAULT = "ACTA";
	public String LABEL_SEARCH_DEFAULT =  "Busqueda Generica";
	public int HINT_SEARCH_DEFAULT = string.searchable_hint_default;
	public String FIELD_SEARCH_DEFAULT =  "NUMERO_DOCUMENTO";
	public String FIELD_SEARCH2_DEFAULT =  "";
	public Boolean LIKEABLE_SEARCH_DEFAULT = false;
	public Boolean LIKEABLE_OR = false;
	public Boolean SHOW_ALL_ITEMS_DEFAULT = false;
	public int CHOICE_MODE_LIST_VIEW = ListView.CHOICE_MODE_SINGLE;
	public int SELECTOR_LIST_ITEM =0;
	
	public Boolean PERMITIR_BUSQUEDA_SIN_DATOS = false;
	
	public static String APP_DATA_CLASS_ITEM_BUSQUEDA= "CLASE_ITEM_BUSQUEDA" ;
	public static String APP_DATA_OBJETO_BUSQUEDA = "OBJETO_BUSQUEDA" ;
	public static String APP_DATA_LABEL_SEARCH = "LABEL_SEARCH" ;
	public static String APP_DATA_HINT_SEARCH = "HINT_SEARCH" ;
	public static String APP_DATA_HEADER_LIST = "HEADER_LIST" ;
	
	public SearchView searchView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		if (idLayoutActivity==0)
			setContentView(layout.activity_searchable);
			else
			setContentView(idLayoutActivity);
		
		textView_PatronBusqueda = this.findViewById(id.TextView_PatronBusqueda);
		
		handleIntent(getIntent());

	}
	
	public void initializeListView()
	{
		
		ListView listView1 = this.getListView();
		listView1.setChoiceMode(CHOICE_MODE_LIST_VIEW);
		listView1.setSelector(R.drawable.config_selector);
		View header = getLayoutInflater().inflate(layout.listview_header_row, null);
        TextView textView = header.findViewById(id.txtHeader);
        textView.setText(titleHeader);
        listView1.addHeaderView(header ,null,false);
		
		List<T> values = new ArrayList<T>() ;
		
		GenericListAdapter<T> myTAdapter = new GenericListAdapter<T>(this,getLayoutItems(),values);
        
        setListAdapter(myTAdapter);		
	}
	
	public void handleIntent(Intent intent) {
		 
		
		
		Bundle appData = intent.getBundleExtra(SearchManager.APP_DATA);//getIntent().getBundleExtra(SearchManager.APP_DATA);
		if (appData != null) 
		{
		    OBJETO_BUSQUEDA_DEFAULT = appData.getString(SearchableActivity.APP_DATA_OBJETO_BUSQUEDA,"ACTA");
		    LABEL_SEARCH_DEFAULT =  appData.getString(SearchableActivity.APP_DATA_LABEL_SEARCH,"Busqueda Generica");
		    HINT_SEARCH_DEFAULT =  appData.getInt(SearchableActivity.APP_DATA_HINT_SEARCH,HINT_SEARCH_DEFAULT);
		}
		
		initializeListView();
		
	   // Toast.makeText(this,"Buscando " + OBJETO_BUSQUEDA_DEFAULT, Toast.LENGTH_LONG).show();
		
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) 
	    {
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      initValueSearch = query;
	      doMySearch(query);
	    }
	    
	 }

	T createContents(Class<T> clazz) throws InstantiationException, IllegalAccessException
    {
        return clazz.newInstance();
    }
	
	public void doMySearch(String pQuery) {
		if (!TextUtils.isEmpty(pQuery) || PERMITIR_BUSQUEDA_SIN_DATOS==true )
		{
			if (pQuery==null) pQuery ="";
		 //Toast.makeText(this, "Iniciando la Consulta\n"  + pQuery, Toast.LENGTH_LONG).show();
	      textView_PatronBusqueda.setVisibility(View.VISIBLE);
	      textView_PatronBusqueda.setText("Buscando por el patron de busqueda : " + pQuery);
	      	
	      {
	    	  Type sooper = getClass().getGenericSuperclass();
	          Type t = ((ParameterizedType)sooper).getActualTypeArguments()[ 0 ];
	    	  
	    	 //IBaseDao<T> genericDao = (IBaseDao<T>) DaoFactory.getInstance().createDao("Persona");
      		
      		ContentValues pContentValues = new ContentValues();
      		
      		if (LIKEABLE_SEARCH_DEFAULT==true) // para uso del LIKE
      		{	
      			pContentValues.put(FIELD_SEARCH_DEFAULT, "%" + pQuery + "%");
      			if (LIKEABLE_OR==true && !FIELD_SEARCH2_DEFAULT.trim().equals(""))
      			{
      				pContentValues.put(FIELD_SEARCH2_DEFAULT, "%" + pQuery + "%");
      				lstItems= genericDao.getByFilterLikeOr(pContentValues);//getAll(aSort);//
      			}
      			else
      			{
      				lstItems= genericDao.getByFilterLike(pContentValues);//getAll(aSort);//
      			}
      		}
      		else
      		{
      			pContentValues.put(FIELD_SEARCH_DEFAULT,  pQuery );
      			lstItems= genericDao.getByFilter(pContentValues);

      		}
      		
      		
      		if (lstItems.size()>0)
      		{
      			Utilities.ShowToast(GlobalStateApp.mContext, "Items Encontrado encontrados " + lstItems.size());
      		}
      		else
      		{	
      			     T newObject = genericDao.getNewObject();
      			     newObject.setItemId("-1");
      			     newObject.setItemName("No se encontraron items ...");
      				 lstItems.add(newObject);
      		}
      		
      		GenericListAdapter<T> myTAdapter = new GenericListAdapter<T>(this,getLayoutItems(),lstItems);
            setListAdapter(myTAdapter);		
	    	  
	      }
	      
	      
		}
		else
		{
			textView_PatronBusqueda.setVisibility(View.GONE);
		}
			
	      
	}
	
	// implementaciones vacias
	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		//return false;
		 if (searchView.getQuery().length() == 0) {
			 doMySearch("");
		 }
	        return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		//Toast.makeText(this, "Buscando", Toast.LENGTH_SHORT).show();
		
		doMySearch(arg0);
		return false;
	}

	
	
	@Override
	protected void onNewIntent(Intent intent) {
	    setIntent(intent);
	    handleIntent(intent);
	}

	
	@Override
	protected int getListViewId() {
		return android.R.id.list;
	}

	@Override
	protected int getLayoutItems() {
		if(idLayoutItems==0)
			return layout.list_item_with_two_text;
		else
			return idLayoutItems;
	}
	 @Override
	 protected void onListItemClick(ListView l, View v, int position, long id) {
	        Log.d("click", "Position click " + position);
	        T item = (T) getListAdapter().getItem(position -1 );
	        //Toast.makeText(this, item.getItemName() + " selected", Toast.LENGTH_LONG).show();
	        
	        if (handlerSelectItemClick==null)
	        { 	
		        Intent intent = new Intent();
		        Bundle appData = new Bundle();
		        
		        //appData.putString("_id", item.);
		        
		        intent.putExtra(SearchManager.APP_DATA,appData);
		        setResult(RESULT_OK, intent);
		        finish();
	        }
	        else
	        {
	        	handlerSelectItemClick.onSelectedItem(item,v);
	        }
	        
	    }
	 
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {

	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.searchoptions, menu);
	    MenuItem searchItem = menu.findItem(id.action_search_execute);
	    
	    searchItem.expandActionView();
	    searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	    searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
	    searchView.setOnQueryTextListener(this);
	    
	 // traverse the view to the widget containing the hint text
	    LinearLayout ll = (LinearLayout)searchView.getChildAt(0);
	    LinearLayout ll2 = (LinearLayout)ll.getChildAt(2);
	    LinearLayout ll3 = (LinearLayout)ll2.getChildAt(1);
	    SearchView.SearchAutoComplete autoComplete = (SearchView.SearchAutoComplete) ll3.getChildAt(0);
	    // set the hint text color
	    autoComplete.setHint(HINT_SEARCH_DEFAULT);
	    //searchView.setIconifiedByDefault(false);
	    searchView.setIconified(false);
	    
	    
	    
	    //autoComplete.setHintTextColor(getResources().getColor(Color.WHITE));
	    // set the text color
	     
	    autoComplete.setTextColor(Color.BLUE);
	    
	    if (!TextUtils.isEmpty(initValueSearch))
	    	autoComplete.setText(initValueSearch);
	    
        return true;
    }
    
	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
		
	    switch (item.getItemId()) {
	    // action with ID action_settings was selected
	    case id.action_search_execute:
	      Toast.makeText(this, "Buscando", Toast.LENGTH_SHORT).show();
	      break;
	    default:
	      break;
	    }

	    return true;
	  }

	
}

