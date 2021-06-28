package ar.gov.mendoza.PrometeoMuni.ui.base;

//import com.actionbarsherlock.app.SherlockActivity;

import android.app.Activity;
import android.os.Bundle;

import ar.gov.mendoza.PrometeoMuni.core.security.SecurePreferences;


public abstract class BaseListActivity extends Activity
{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
	
	
    protected SecurePreferences mSecurePrefs;
    public static final String XML_TAG_STRING = "string";
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

    public static final String DEBUG_TAG = "Been There, Done That! Activity Log";
    
    // Server URLs
    public static final String TRIVIA_SERVER_BASE = "http://tqs.mamlambo.com/";
    public static final String TRIVIA_SERVER_SCORES = TRIVIA_SERVER_BASE + "scores.jsp";
    public static final String TRIVIA_SERVER_QUESTIONS = TRIVIA_SERVER_BASE + "questions.jsp";
    public static final String TRIVIA_SERVER_ACCOUNT_EDIT = TRIVIA_SERVER_BASE + "receive";
    public static final String TRIVIA_SERVER_FRIEND_ADD = TRIVIA_SERVER_BASE + "friend";
   
}