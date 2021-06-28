package ar.gov.mendoza.PrometeoMuni;



import ar.gov.mendoza.PrometeoMuni.abstracts.ActionBarListActivity;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ItemSearchActivity  extends  ActionBarListActivity implements SearchView.OnQueryTextListener 
{ 
	@Override
 	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchable);
		
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.searchoptions, menu);
	    MenuItem searchItem = menu.findItem(R.id.action_search_execute);
	    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        return true;
    }
    
	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
		
	    switch (item.getItemId()) {
	    // action with ID action_settings was selected
	    case R.id.action_search_execute:
	      Toast.makeText(this, "Buscando", Toast.LENGTH_SHORT).show();
	      break;
	    default:
	      break;
	    }

	    return true;
	  }
	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected int getListViewId() {
		return android.R.id.list;
	}
	 @Override
	 protected void onListItemClick(ListView l, View v, int position, long id) {
	        Log.d("click", "Position click " + position);
	        String item = (String) getListAdapter().getItem(position);
	        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
	    }

	@Override
	protected int getLayoutItems() {
		// TODO Auto-generated method stub
		return 0;
	}
}