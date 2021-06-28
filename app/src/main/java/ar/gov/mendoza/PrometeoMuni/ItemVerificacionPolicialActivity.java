package ar.gov.mendoza.PrometeoMuni;

import com.zebra.android.zebrautilitiesmza.screens.chooseprinter.ChoosePrinterController;

import android.content.Intent;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
/*
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
*/

import android.support.v7.app.AppCompatActivity;//.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class ItemVerificacionPolicialActivity  extends AppCompatActivity//ActionBarActivity //extends SherlockActivity
{// Activity 

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Used to put dark icons on light action bar
        boolean isLight =true;// SampleList.THEME == R.style.Theme_Sherlock_Light;
       /*
       MenuItem mi = menu.add(com.actionbarsherlock.view.Menu.NONE,R.id.action_acta_imprimir,com.actionbarsherlock.view.Menu.NONE,R.string.menu_item_imprimir_acta);
       	mi.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menu.add(com.actionbarsherlock.view.Menu.NONE,R.id.action_printer_settings,com.actionbarsherlock.view.Menu.NONE,R.string.menu_item_configurar_impresora)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
            .setIcon(isLight ? R.drawable.ic_refresh_inverse : R.drawable.ic_refresh)
        */

	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.crearactaoptions, menu);
	   /* MenuItem searchItem = menu.findItem(R.id.action_acta_imprimir);
	    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);*/
        return true;
    }
    
//	@Override
//	  public boolean onCreateOptionsMenu(Menu menu) {
//	    MenuInflater inflater = getMenuInflater();
//	    inflater.inflate(R.menu.crearactaoptions, menu);
//	    return true;
//	  } 
	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
		
	    switch (item.getItemId()) {
	    // action with ID action_refresh was selected
//	    case R.id.action_acta_imprimir://R.id.action_refresh:
//	      Toast.makeText(this, "Imprimir Acta", Toast.LENGTH_SHORT)
//	          .show();
	    //  break;
	    // action with ID action_settings was selected
	    case R.id.action_printer_settings:
	      Toast.makeText(this, "Configurar Impresora", Toast.LENGTH_SHORT)
	          .show();
	      
	      Intent localIntent = new Intent(this, ChoosePrinterController.class);
	      this.startActivity(localIntent);
	      
	      break;
	    default:
	      break;
	    }

	    return true;
	  }
	
	//CurrentActa preference values
    public static final String CURRENT_ACTA_PREFERENCES = "CurrentActaPrefs";
    
    public static final String CURRENT_ACTA_PAIS_LICENCIA = "PaisLicencia";	  
    public static final String CURRENT_ACTA_PROVINCIA_LICENCIA = "ProvinciaLicencia";
    public static final String CURRENT_ACTA_DEPARTAMENTO_LICENCIA = "DepartamentoLicencia";
    public static final String CURRENT_ACTA_LOCALIDAD_LICENCIA = "LocalidadLicencia";
    
    public static final String CURRENT_ACTA_PAIS_DOCUMENTO = "PaisDocumento";	  
    public static final String CURRENT_ACTA_PROVINCIA_DOCUMENTO= "ProvinciaDocumento";
    public static final String CURRENT_ACTA_DEPARTAMENTO_DOCUMENTO= "DepartamentoDocumento";
    public static final String CURRENT_ACTA_LOCALIDAD_DOCUMENTO = "LocalidadDocumento";
    
    
    public static final String CURRENT_ACTA_TIPO_RUTA = "TipoRuta";	  
    public static final String CURRENT_ACTA_RUTA = "Ruta";
    public static final String CURRENT_ACTA_ENTIDAD= "Entidad";
    
    public static final String CURRENT_ACTA_FECHA_VENCIMIENTO_LICENCIA = "FechaVencimientoLicencia";
    public static final String CURRENT_ACTA_MARCA = "Marca";	  
    public static final String CURRENT_ACTA_COLOR = "Color";	 
    
    public static final String CURRENT_ACTA_INFRACCION1 = "Infraccion1";
    public static final String CURRENT_ACTA_INFRACCION2 = "Infraccion2";
    
    public static final String CURRENT_ACTA_FOTO_LICENCIA = "FotoLicencia";
    public static final String CURRENT_ACTA_FOTO_DOCUMENTO= "FotoDocumento";
    public static final String CURRENT_ACTA_FOTO_OTRO = "FotoOtro";
    
    // Game preference values
    public static final String GAME_PREFERENCES = "GamePrefs";
    public static final String GAME_PREFERENCES_NICKNAME = "Nickname"; // String
    public static final String GAME_PREFERENCES_EMAIL = "Email"; // String
    public static final String GAME_PREFERENCES_PASSWORD = "Password"; // String
    public static final String GAME_PREFERENCES_DOB = "DOB"; // Long
    public static final String GAME_PREFERENCES_GENDER = "Gender";  // Integer, in array order: Male (1), Female (2), and Undisclosed (0)
    public static final String GAME_PREFERENCES_TIPO_RUTA = "TipoRuta";  // Integer, in array order: 
    public static final String GAME_PREFERENCES_RUTA = "Ruta";  // Integer, in array order:
    public static final String GAME_PREFERENCES_SCORE = "Score"; // Integer
    public static final String GAME_PREFERENCES_CURRENT_QUESTION = "CurQuestion"; // Integer
    public static final String GAME_PREFERENCES_AVATAR = "Avatar"; // String URL to image
    public static final String GAME_PREFERENCES_FAV_PLACE_NAME = "FavPlaceName"; // String
    public static final String GAME_PREFERENCES_FAV_PLACE_LONG = "FavPlaceLong"; // float
    public static final String GAME_PREFERENCES_FAV_PLACE_LAT = "FavPlaceLat"; // float
    public static final String GAME_PREFERENCES_PLAYER_ID = "ServerId"; // Integer


    // Question XML Tag Names
    public static final String XML_TAG_QUESTION_BLOCK = "questions";
    public static final String XML_TAG_QUESTION = "question";
    public static final String XML_TAG_QUESTION_ATTRIBUTE_NUMBER = "number";
    public static final String XML_TAG_QUESTION_ATTRIBUTE_TEXT = "text";
    public static final String XML_TAG_QUESTION_ATTRIBUTE_IMAGEURL = "imageUrl";
    public static final int QUESTION_BATCH_SIZE = 15;

    public static final String DEBUG_TAG = "Been There, Done That! Activity Log";
    
    // Server URLs
    public static final String TRIVIA_SERVER_BASE = "http://tqs.mamlambo.com/";
    public static final String TRIVIA_SERVER_SCORES = TRIVIA_SERVER_BASE + "scores.jsp";
    public static final String TRIVIA_SERVER_QUESTIONS = TRIVIA_SERVER_BASE + "questions.jsp";
    public static final String TRIVIA_SERVER_ACCOUNT_EDIT = TRIVIA_SERVER_BASE + "receive";
    public static final String TRIVIA_SERVER_FRIEND_ADD = TRIVIA_SERVER_BASE + "friend";
   
}