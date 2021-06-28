package ar.gov.mendoza.PrometeoMuni;

//import com.actionbarsherlock.app.SherlockActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import ar.gov.mendoza.PrometeoMuni.core.security.SecurePreferences;
import android.support.v7.app.AppCompatActivity;//.ActionBarActivity;
public class MendozaActivity extends AppCompatActivity {//ActionBarActivity{// Activity
	
	
	protected boolean backPressedToExitOnce = false;
	private Toast toast = null;
	
	@Override
	public void onBackPressed() {
	    if (backPressedToExitOnce) {
	        super.onBackPressed();
	    } else {
	        this.backPressedToExitOnce = true;
	        showToast("Presione de nuevo para Salir");
	        new Handler().postDelayed(new Runnable() {

	            @Override
	            public void run() {
	                backPressedToExitOnce = false;
	            }
	        }, 2000);
	    }
	}
	
	
	/**
	 * Created to make sure that you toast doesn't show miltiple times, if user pressed back
	 * button more than once. 
	 * @param message Message to show on toast.
	 */
	protected void showToast(String message) {
	    if (this.toast == null) {
	        // Create toast if found null, it would he the case of first call only
	        this.toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);

	    } else if (this.toast.getView() == null) {
	        // Toast not showing, so create new one
	        this.toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);

	    } else {
	        // Updating toast message is showing
	        this.toast.setText(message);
	    }

	    // Showing toast finally
	    this.toast.show();
	}
	
	/**
	 * Kill the toast if showing. Supposed to call from onPause() of activity.
	 * So that toast also get removed as activity goes to background, to improve
	 * better app experiance for user
	 */
	protected void killToast() {
	    if (this.toast != null) {
	        this.toast.cancel();
	    }
	}
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	    //    getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
	 }
	
   protected SecurePreferences mSecurePrefs;
   public static final String XML_TAG_STRING = "string";
	
    // Game preference values
    public static final String GAME_PREFERENCES = "GamePrefs";
    public static final String GAME_PREFERENCES_NICKNAME = "Nickname"; // String
    public static final String GAME_PREFERENCES_EMAIL = "Email"; // String
    public static final String GAME_PREFERENCES_PASSWORD = "Password"; // String
    public static final String GAME_PREFERENCES_DOB = "DOB"; // Long
    public static final String GAME_PREFERENCES_FVL = "FVL"; // Long // fecha vencimiento licencia
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

public static final String PREFERENCES_APP_ISCONFIG = "preference_app_isconfig";

public static final String PREFERENCES_SERVICES_LAST_UPDATE = "preference_services_lastUpdate";	
public static final String PREFERENCES_SERVICES_NAMESPACE = "preference_services_namespace";
public static final String PREFERENCES_SERVICES_NAME = "preference_services_name";
public static final String PREFERENCES_SERVICES_URL = "preference_services_url";

public static final String PREFERENCES_SESION_USUARIO = "preference_sesion_usuario";
public static final String PREFERENCES_SESION_USUARIO_ID = "preference_sesion_usuario_id";
public static final String PREFERENCES_SESION_INSPECCION_ID = "preference_sesion_inspeccion_id";
public static final String PREFERENCES_SESION_PERFIL = "preference_sesion_perfil";


public static final String PREFERENCES_SUPPORT_TABLE = "preference_support_table";

    // Question XML Tag Names
    public static final String XML_TAG_QUESTION_BLOCK = "questions";
    public static final String XML_TAG_QUESTION = "question";
    
    public static final String XML_TAG_QUESTION_ATTRIBUTE_NUMBER = "number";
    public static final String XML_TAG_QUESTION_ATTRIBUTE_TEXT = "text";
    public static final String XML_TAG_QUESTION_ATTRIBUTE_IMAGEURL = "imageUrl";
    public static final int QUESTION_BATCH_SIZE = 15;

    public static final String DEBUG_TAG = "Activity Log";
    
    // Server URLs
    public static final String TRIVIA_SERVER_BASE = "http://tqs.mamlambo.com/";
    public static final String TRIVIA_SERVER_SCORES = TRIVIA_SERVER_BASE + "scores.jsp";
    public static final String TRIVIA_SERVER_QUESTIONS = TRIVIA_SERVER_BASE + "questions.jsp";
    public static final String TRIVIA_SERVER_ACCOUNT_EDIT = TRIVIA_SERVER_BASE + "receive";
    public static final String TRIVIA_SERVER_FRIEND_ADD = TRIVIA_SERVER_BASE + "friend";
   
}