package ar.gov.mendoza.PrometeoMuni.abstracts;

import android.support.v7.app.AppCompatActivity;//.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;



public abstract class ActionBarListActivity extends AppCompatActivity {// ActionBarActivity {

	 private final class ListOnItemClickListener implements OnItemClickListener 
	 {

	        public void onItemClick(AdapterView<?> lv, View v, int position, long id) {
	            onListItemClick((ListView) lv, v, position, id);
	        }
	 }

	    private ListView mListView;

	   
	    
	    protected ListView getListView() {

	        if (mListView == null) {
	            initListView();
	        }
	        return mListView;
	    }

	    private void initListView() {
	        mListView = findViewById(getListViewId());
	        if (mListView == null) {
	            throw new RuntimeException(
	                    "ListView cannot be null. Please set a valid ListViewId");
	        }
	      
	        mListView.setOnItemClickListener(new ListOnItemClickListener());
	    }

	    protected abstract int getListViewId();
	    protected abstract int getLayoutItems();
	    
	    protected void setListAdapter(ListAdapter adapter) {
	        getListView().setAdapter(adapter);
	    }

	    protected void onListItemClick(ListView lv, View v, int position, long id) {
	        // No default functionality. To override
	    }

	    protected ListAdapter getListAdapter() {
	        ListAdapter adapter = getListView().getAdapter();
	        if (adapter instanceof HeaderViewListAdapter) {
	            return ((HeaderViewListAdapter) adapter).getWrappedAdapter();
	        } else {
	            return adapter;
	        }
	    }
}
