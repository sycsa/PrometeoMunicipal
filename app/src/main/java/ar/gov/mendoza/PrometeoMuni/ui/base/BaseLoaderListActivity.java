package ar.gov.mendoza.PrometeoMuni.ui.base;

import android.app.LoaderManager.LoaderCallbacks;
import android.database.Cursor;
import android.os.Bundle;


public abstract class BaseLoaderListActivity 
extends BaseActivity
implements LoaderCallbacks<Cursor>
{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
   
}