package ar.gov.mendoza.PrometeoMuni;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

//import org.apache.http.NameValuePair;
/*import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
*/
/*
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
*/
//import org.simpleframework.xml.Serializer;
//import org.simpleframework.xml.core.Persister;

import com.zebra.android.zebrautilitiesmza.util.UIHelper;
import com.zebra.android.zebrautilitiesmza.util.ZebraPrinterTask;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.device.ZebraIllegalArgumentException;

import android.R.layout;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore.Images.Media;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;


import ar.gov.mendoza.PrometeoMuni.core.domain.ActaConstatacion;
import ar.gov.mendoza.PrometeoMuni.core.domain.AndroidWidgetControl;
import ar.gov.mendoza.PrometeoMuni.core.domain.Color;
import ar.gov.mendoza.PrometeoMuni.core.domain.Departamento;
import ar.gov.mendoza.PrometeoMuni.core.domain.DominioPadron;
import ar.gov.mendoza.PrometeoMuni.core.domain.ISeleccionable;
import ar.gov.mendoza.PrometeoMuni.core.domain.InfraccionNomenclada;
import ar.gov.mendoza.PrometeoMuni.core.domain.Marca;
import ar.gov.mendoza.PrometeoMuni.core.domain.Ruta;
import ar.gov.mendoza.PrometeoMuni.core.domain.Seccional;
import ar.gov.mendoza.PrometeoMuni.core.domain.TipoDocumento;
import ar.gov.mendoza.PrometeoMuni.core.domain.Entidad;
import ar.gov.mendoza.PrometeoMuni.core.domain.Localidad;
import ar.gov.mendoza.PrometeoMuni.core.domain.Pais;
import ar.gov.mendoza.PrometeoMuni.core.domain.PersonaPadron;
import ar.gov.mendoza.PrometeoMuni.core.domain.Provincia;
import ar.gov.mendoza.PrometeoMuni.core.domain.TipoRuta;
//import ar.gov.mendoza.deviceactas.core.rules.ActaConstatacionRules;
import ar.gov.mendoza.PrometeoMuni.core.domain.TipoVehiculo;
import ar.gov.mendoza.PrometeoMuni.core.util.Tools;
import ar.gov.mendoza.PrometeoMuni.adapters.GenericSpinAdapter;
import ar.gov.mendoza.PrometeoMuni.dao.EntidadDao;
import ar.gov.mendoza.PrometeoMuni.dao.InfraccionNomencladaDao;
import ar.gov.mendoza.PrometeoMuni.layouts.EditTextWithDeleteButton;
import ar.gov.mendoza.PrometeoMuni.layouts.HandlerClearTextInterface;
import ar.gov.mendoza.PrometeoMuni.objects.SomeExpensiveObject;
import ar.gov.mendoza.PrometeoMuni.rules.ActaConstatacionRules;
import ar.gov.mendoza.PrometeoMuni.rules.DepartamentoRules;
import ar.gov.mendoza.PrometeoMuni.rules.EntidadRules;
import ar.gov.mendoza.PrometeoMuni.rules.LocalidadRules;
import ar.gov.mendoza.PrometeoMuni.rules.PersonaPadronRules;
import ar.gov.mendoza.PrometeoMuni.rules.ProvinciaRules;
import ar.gov.mendoza.PrometeoMuni.rules.RutaRules;
import ar.gov.mendoza.PrometeoMuni.screens.actas.CreateActaHelper;
import ar.gov.mendoza.PrometeoMuni.screens.actas.PrintActaHelper;
import ar.gov.mendoza.PrometeoMuni.screens.qrcode.CodigoMancha;
import ar.gov.mendoza.PrometeoMuni.screens.qrcode.CodigoQR;
import ar.gov.mendoza.PrometeoMuni.screens.verificaciones.ResultadoVerificacionPolicialActivity;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;
import ar.gov.mendoza.PrometeoMuni.sync.DeviceActasSync;
import ar.gov.mendoza.PrometeoMuni.sync.SyncLicencia;
import ar.gov.mendoza.PrometeoMuni.sync.VerificacionPolicialSync;
import ar.gov.mendoza.PrometeoMuni.sync.dto.LicenciaDTO;
import ar.gov.mendoza.PrometeoMuni.sync.dto.LicenciaValidaSyncDTO;
import ar.gov.mendoza.PrometeoMuni.sync.dto.RestriccionDTO;
import ar.gov.mendoza.PrometeoMuni.utils.StringBuilderForPrint;
import ar.gov.mendoza.PrometeoMuni.utils.Utilities;

import com.google.zxing.client.android.CaptureActivity;
//import jim.h.common.android.zxingjar.demo.ZXingJarDemoActivity;
//import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
//import jim.h.common.android.zxinglib.integrator.IntentResult;
import com.microblink.activity.Pdf417ScanActivity;
import com.microblink.recognizers.BaseRecognitionResult;
import com.microblink.recognizers.RecognitionResults;
import com.microblink.recognizers.blinkbarcode.bardecoder.BarDecoderRecognizerSettings;
import com.microblink.recognizers.blinkbarcode.pdf417.Pdf417RecognizerSettings;
import com.microblink.recognizers.blinkbarcode.pdf417.Pdf417ScanResult;
import com.microblink.recognizers.blinkbarcode.simnumber.SimNumberRecognizerSettings;
import com.microblink.recognizers.blinkbarcode.zxing.ZXingRecognizerSettings;
import com.microblink.recognizers.settings.RecognitionSettings;
import com.microblink.recognizers.settings.RecognizerSettings;


public class CreateActaActivity extends ItemCreateActaActivity implements LocationListener {
	/*
    private TextView latitudeTextView,longitudeTextView;
    private Location mylocation;
    private GoogleApiClient googleApiClient;
    private final static int REQUEST_CHECK_SETTINGS_GPS=0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS=0x2;
	*/

	public static final int REQUEST_CODE_PDF417 = 1024;//195543262;
	//
	private static final String LICENSE_KEY = "GN3TJJEA-H2L3XLAC-ETFN25HU-R3DVJH6D-EAKRQLTP-PG2K3HVH-IEKRQLTP-PG2PZM5C";

	private static final int MY_REQUEST_CODE = 1337;

	private Handler handler = new Handler();
	private TextView txtScanResult;

	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
	private String GNumeroDocumentoCodigoQR = "";


	ProgressDialog pleaseWaitDialog;
	private Boolean WorkingInPrintAsyncTask = false;
	private Boolean WorkingInVerificacionAsyncTask = false;

	PrintActaRequestTask printActaRequest;

	private ProgressDialog progressDialog;
	/** Reference of SomeExpensiveObject.  This object is expensive to create. */
	private SomeExpensiveObject someExpensiveObject;

	private LoadViewTask loadViewTask;


	private static final String TAG = "CreateActaActivity";
	private static final String RESOURCE_SIN_FOTO = "android.resource://ar.gov.mendoza.deviceactas/drawable/sinfoto";

	SharedPreferences mGameSettings;
	SharedPreferences mCurrenActaSettings;
	GPSCoords mFavPlaceCoords;
	FriendRequestTask friendRequest;
	CleanFormActivityRequestTask cleanFormRequest;

	LicenciaRequestTask licenciaRequest;
	EnvioCodigoQRRequestTask envioCodigoQRRequestTask;

	VerificacionPolicialRequestTask verificacionPolicialRequest;


	private Boolean IsRecreating = false; // Indicador de que el Activity esta siendo recreado
	private Boolean IsRestoringLastData = false;

	static final String PAIS = "Pais";
	static final String PROVINCIA = "Provincia";
	static final String DEPARTAMENTO = "Departamento";
	static final String LOCALIDAD = "Localidad";

	static final String LICENCIA = "Licencia";
	static final String DOCUMENTO = "Documento";
	static final String DOCUMENTO_TITULAR = "DocumentoTitular";
	static final String DOCUMENTO_TESTIGO = "DocumentoTestigo";

	static final String MARCA = "Marca";
	static final String COLOR = "Color";

	// DIALOG
	static final int DATE_DIALOG_ID = 0;
	static final int PASSWORD_DIALOG_ID = 1;
	static final int PLACE_DIALOG_ID = 2;
	static final int FRIEND_EMAIL_DIALOG_ID = 3;
	static final int EXPIRE_DATE_DIALOG_ID = 4;
	static final int PRINT_ACTA_DIALOG_ID = 5;
	static final int SCAN_QR_CODE_DIALOG_ID = 6;
	static final int BORN_DATE_DIALOG_ID = 7;

	// INTENT RESULTS
	static final int TAKE_AVATAR_CAMERA_REQUEST_LICENCIA = 1;
	static final int TAKE_AVATAR_CAMERA_REQUEST_DOCUMENTO = 2;
	static final int TAKE_AVATAR_CAMERA_REQUEST_OTRO = 3;
	static final int TAKE_AVATAR_GALLERY_REQUEST = 4;
	static final int SEARCH_PERSONA = 5;

	static final int SEARCH_INFRACCION1 = 6;
	static final int SEARCH_INFRACCION2 = 7;
	static final int SEARCH_INFRACCION3 = 12;
	static final int SEARCH_INFRACCION4 = 13;
	static final int SEARCH_INFRACCION5 = 14;
	static final int SEARCH_INFRACCION6 = 15;
	static final int SCANNER_QR_CODE = 8;
	static final int SEARCH_PERSONA_TITULAR = 9;
	static final int SEARCH_PERSONA_TESTIGO = 10;
	static final int SEARCH_DOMINIO = 11;

	static final String SPINNER_PAIS_LICENCIA = "PAIS_LICENCIA";
	static final String SPINNER_PAIS_DOCUMENTO = "PAIS_DOCUMENTO";
	static final String SPINNER_PAIS_DOCUMENTO_TITULAR = "PAIS_DOCUMENTO_TITULAR";
	static final String SPINNER_PAIS_DOCUMENTO_TESTIGO = "PAIS_DOCUMENTO_TESTIGO";

	static final String SPINNER_PROVINCIA_LICENCIA = "PROVINCIA_LICENCIA";
	static final String SPINNER_PROVINCIA_DOCUMENTO = "PROVINCIA_DOCUMENTO";
	static final String SPINNER_PROVINCIA_DOCUMENTO_TITULAR = "PROVINCIA_DOCUMENTO_TITULAR";
	static final String SPINNER_PROVINCIA_DOCUMENTO_TESTIGO = "PROVINCIA_DOCUMENTO_TESTIGO";

	static final String SPINNER_DEPARTAMENTO_LICENCIA = "DEPARTAMENTO_LICENCIA";
	static final String SPINNER_DEPARTAMENTO_DOCUMENTO = "DEPARTAMENTO_DOCUMENTO";
	static final String SPINNER_DEPARTAMENTO_DOCUMENTO_TITULAR = "DEPARTAMENTO_DOCUMENTO_TITULAR";
	static final String SPINNER_DEPARTAMENTO_DOCUMENTO_TESTIGO = "DEPARTAMENTO_DOCUMENTO_TESTIGO";

	static final String SPINNER_LOCALIDAD_LICENCIA = "LOCALIDAD_LICENCIA";
	static final String SPINNER_LOCALIDAD_DOCUMENTO = "LOCALIDAD_DOCUMENTO";
	static final String SPINNER_LOCALIDAD_DOCUMENTO_TITULAR = "LOCALIDAD_DOCUMENTO_TITULAR";
	static final String SPINNER_LOCALIDAD_DOCUMENTO_TESTIGO = "LOCALIDAD_DOCUMENTO_TESTIGO";

	static final String SPINNER_TIPO_RUTA = "TIPO_RUTA";
	static final String SPINNER_RUTA = "RUTA";

	static final String SPINNER_TIPO_DOCUMENTO = "TIPO_DOCUMENTO";
	static final String SPINNER_TIPO_DOCUMENTO_TITULAR = "TIPO_DOCUMENTO_TITULAR";
	static final String SPINNER_TIPO_DOCUMENTO_TESTIGO = "TIPO_DOCUMENTO_TESTIGO";

	static final String SPINNER_TIPO_GENERO = "TIPO_GENERO";
	static final String SPINNER_TIPO_VEHICULO = "TIPO_VEHICULO";
	static final String SPINNER_TIPO_GENERO_TITULAR = "TIPO_GENERO_TITULAR";
	static final String SPINNER_TIPO_GENERO_TESTIGO = "TIPO_GENERO_TESTIGO";

	static final String SPINNER_MARCA_VEHICULO = "MARCA_VEHICULO";
	static final String SPINNER_COLOR_VEHICULO = "COLOR_VEHICULO";

	static final String FORM_PERSONA_TITULAR = "PERSONA_TITULAR";
	static final String FORM_PERSONA_INFRACTOR = "PERSONA_INFRACTOR";
	static final String FORM_PERSONA_TESTIGO = "PERSONA_TESTIGO";
	static final String FORM_DOMINIO = "DOMINIO";
	static final String FORM_LICENCIA = "LICENCIA";


	static final String EDIT_TEXT_KM = "KM";

	LinearLayout subPnlTipoVehiculo;
	TextView textView_NumeroActa;

	CheckBox checkBox_LicenciaUnicaProvincial;
	CheckBox checkBox_NoTieneLicencia;
	CheckBox checkBox_LicenciaRetenida;
	CheckBox checkBox_VehiculoRetenido;
	CheckBox checkBox_CallePublicaTitular;
	CheckBox checkBox_AlturaSinNumeroTitular;

	EditText editText_NumeroLicencia;
	EditText editText_ClaseLicencia;

	Button Button_CopiarDatosTitular;
	Button Button_VerificarCodigoQR;
	Button button_VerificarLicencia;
	Button button_VerificarDominio;

	CheckBox checkBox_SinConductor;
	EditText editText_NumeroDocumento;
	EditText editText_NumeroDocumentoTitular;
	EditText editText_NumeroDocumentoTestigo;

	EditText editText_Ubicacion;
	EditText editText_DescripcionUbicacion;
	EditText editText_Referencia;

	EditText editText_ModeloVehiculo;
	EditText editText_AnioModeloVehiculo;
	TextView textView_NroRuta;
	TextView textView_Km;


	TextView textView_LocalidadInfraccion;

	EditText editText_Km;

	EditText editText_PaisLicencia;
	EditText editText_ProvinciaLicencia;
	EditText editText_DepartamentoLicencia;
	EditText editText_LocalidadLicencia;

	EditText editText_PaisDocumento;
	EditText editText_ProvinciaDocumento;
	EditText editText_DepartamentoDocumento;
	EditText editText_LocalidadDocumento;

	EditText editText_PaisDocumentoTitular;
	EditText editText_ProvinciaDocumentoTitular;
	EditText editText_DepartamentoDocumentoTitular;
	EditText editText_LocalidadDocumentoTitular;

	EditText editText_PaisDocumentoTestigo;
	EditText editText_ProvinciaDocumentoTestigo;
	EditText editText_DepartamentoDocumentoTestigo;
	EditText editText_LocalidadDocumentoTestigo;

	EditTextWithDeleteButton editText_CODInfraccion_1;
	EditTextWithDeleteButton editText_CODInfraccion_2;
	EditTextWithDeleteButton editText_CODInfraccion_3;
	EditTextWithDeleteButton editText_CODInfraccion_4;
	EditTextWithDeleteButton editText_CODInfraccion_5;
	EditTextWithDeleteButton editText_CODInfraccion_6;

	TextView textView_DescripcionInfraccion1;
	TextView textView_DescripcionInfraccion2;
	TextView textView_DescripcionInfraccion3;
	TextView textView_DescripcionInfraccion4;
	TextView textView_DescripcionInfraccion5;
	TextView textView_DescripcionInfraccion6;


	EditText editText_Apellido;
	EditText editText_Nombre;
	EditText editText_Telefono;
	EditText editText_Email;


	EditText editText_Calle;
	EditText editText_Altura;
	EditText editText_Piso;
	EditText editText_Depto;
	EditText editText_Barrio;

	CheckBox checkBox_SinTestigo;
	EditText editText_CodigoPostal;

	CheckBox checkBox_SinTitular;
	EditText editText_ApellidoTitular;
	EditText editText_NombreTitular;

	EditText editText_CalleTitular;
	EditText editText_AlturaTitular;
	EditText editText_PisoTitular;
	EditText editText_DeptoTitular;
	EditText editText_BarrioTitular;

	EditText editText_CodigoPostalTitular;

	EditText editText_ApellidoTestigo;
	EditText editText_NombreTestigo;

	EditText editText_CalleTestigo;
	EditText editText_AlturaTestigo;
	EditText editText_PisoTestigo;
	EditText editText_DeptoTestigo;
	EditText editText_BarrioTestigo;

	EditText editText_CodigoPostalTestigo;
	EditText editText_ManifestacionTestigo;

	TextView textView_DescripcionEntidad;
	TextView editText_FVL_Info;
	TextView textView_FN_Info;


	EditText editText_Dominio;

	EditText editText_Marca;
	EditText editText_Color;

	CheckBox checkBox_ConduccionPeligrosa;
	CheckBox checkBox_DejaCopia;

	EditText editText_Observaciones;
	EditText editText_Observaciones2;

	static TextView textView_AlcoholEnSangre;
	static EditText editText_AlcoholEnSangre;

	ImageButton imageButton_Licencia;
	ImageButton imageButton_Documento;

	Button button_VerificarDocumento;
	Button button_VerificarDocumentoTitular;
	Button button_VerificarDocumentoTestigo;
	Button button_VerificarInfraccion1;
	Button button_VerificarInfraccion2;

	Button button_GrabarActa;
	Button button_CancelarActa;

	Boolean bGrabando = false;
	private Context ctx;

	public void turnGPSOn() {
		startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);

	}

	/*private void doVerificarPolicial(String pTipoObjetoBusqueda, String pTipoBusqueda, String pValor) {
		String usarWebService = "N";
		try {
			usarWebService = GlobalVar.getInstance().getSuportTable().getUsarWebServicePolicia();
		} catch (Exception ex) {

		}

		if (usarWebService != null && usarWebService.toUpperCase().equals("S")) {
			// make sure we don't collide with another pending update
			if (verificacionPolicialRequest == null || verificacionPolicialRequest.getStatus() == AsyncTask.Status.FINISHED || verificacionPolicialRequest.isCancelled()) {
				verificacionPolicialRequest = new VerificacionPolicialRequestTask(pTipoObjetoBusqueda, pTipoBusqueda);
				verificacionPolicialRequest.setPostExecuteHandler(receptorVerificacionPolicialTask);
				verificacionPolicialRequest.execute(pValor);
			} else {
				Log.w("AsyncTask", "Warning: verificacionPolicialRequestTask already going");
			}
		}
	}*/

	private boolean appInstalledOrNot(String uri) {
		PackageManager pm = getPackageManager();
		boolean app_installed;
		try {
			pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
			app_installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			app_installed = false;
		}
		return app_installed;
	}

	private Boolean doVerificarInstalacionAppScanner() {
		boolean installed = appInstalledOrNot("com.google.zxing.client.android");

		if (installed == true) {
			return true;
		} else {
			UIHelper.showAlertOnGuiThread(CreateActaActivity.this, "La Aplicacion externa para Scanner el Codigo QR no esta instalado", "Scanner de Codigo QR ", null);
			return false;
		}
	}

	private RecognizerSettings[] setupSettingsArray() {
		Pdf417RecognizerSettings sett = new Pdf417RecognizerSettings();
		// disable scanning of white barcodes on black background
		sett.setInverseScanning(false);
		// allow scanning of barcodes that have invalid checksum
		sett.setUncertainScanning(true);
		// disable scanning of barcodes that do not have quiet zone
		// as defined by the standard
		sett.setNullQuietZoneAllowed(false);

		// now add sett to recognizer settings array that is used to configure
		// recognition
		return new RecognizerSettings[]{sett};
	}
	/*
	 *
				if(doVerificarInstalacionAppScanner()==false) return;

	            Intent intent = new Intent(ACTION_SCAN);

	            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
	            //intent.putExtra("PARAM1", pNumeroDocumento);
	            GNumeroDocumentoCodigoQR = pNumeroDocumento;

	            startActivityForResult(intent,SCANNER_QR_CODE);

	 * */


	private void doVerificarCodigoQR(String pTipoLector) {
		try {

			if (pTipoLector.equals("zxing")) {
				Log.i(TAG, "scan will be performed with zxing");
				Intent intentZXing = new Intent(CreateActaActivity.this, CaptureActivity.class);
				CreateActaActivity.this.startActivityForResult(intentZXing, REQUEST_CODE_PDF417);//195543262);
			} else {
				Log.i(TAG, "scan will be performed with microblink");
				Intent intent = new Intent(this, Pdf417ScanActivity.class);

				//intent.putExtra(Pdf417ScanActivity.EXTRAS_BEEP_RESOURCE, R.raw.beep);
				String sLICENSE_KEY = GlobalVar.getInstance().getSuportTable().getLicenseKey();
				intent.putExtra(Pdf417ScanActivity.EXTRAS_LICENSE_KEY, sLICENSE_KEY);
				//intent.putExtra(Pdf417ScanActivity.EXTRAS_CAMERA_TYPE, (Parcelable) CameraType.CAMERA_FRONTFACE);

				// Pdf417RecognizerSettings define the settings for scanning plain PDF417 barcodes.
				Pdf417RecognizerSettings pdf417RecognizerSettings = new Pdf417RecognizerSettings();

				// Set this to true to scan barcodes which don't have quiet zone (white area) around it
				// Use only if necessary because it drastically slows down the recognition process
				pdf417RecognizerSettings.setNullQuietZoneAllowed(true);
				// Set this to true to scan even barcode not compliant with standards
				// For example, malformed PDF417 barcodes which were incorrectly encoded
				// Use only if necessary because it slows down the recognition process
				// pdf417RecognizerSettings.setUncertainScanning(true);

				// BarDecoderRecognizerSettings define settings for scanning 1D barcodes with algorithms
				// implemented by Microblink team.
				BarDecoderRecognizerSettings oneDimensionalRecognizerSettings = new BarDecoderRecognizerSettings();
				// set this to true to enable scanning of Code 39 1D barcodes
				oneDimensionalRecognizerSettings.setScanCode39(true);
				// set this to true to enable scanning of Code 128 1D barcodes
				oneDimensionalRecognizerSettings.setScanCode128(true);
				// set this to true to use heavier algorithms for scanning 1D barcodes
				// those algorithms are slower, but can scan lower resolution barcodes
				//oneDimensionalRecognizerSettings.setTryHarder(true);

				// ZXingRecognizerSettings define settings for scanning barcodes with ZXing library
				// We use modified version of ZXing library to support scanning of barcodes for which
				// we still haven't implemented our own algorithms.
				ZXingRecognizerSettings zXingRecognizerSettings = new ZXingRecognizerSettings();
				// set this to true to enable scanning of QR codes
				zXingRecognizerSettings.setScanQRCode(true);
				zXingRecognizerSettings.setScanITFCode(true);

				// SimNumberRecognizerSettings define settings for scanning barcode from SIM number barcode
				// located on boxes of prepaid SIMs
				SimNumberRecognizerSettings sm = new SimNumberRecognizerSettings();

				// finally, when you have defined settings for each recognizer you want to use,
				// you should put them into array held by global settings object

				RecognitionSettings recognitionSettings = new RecognitionSettings();
				// add settings objects to recognizer settings array
				// Pdf417Recognizer, BarDecoderRecognizer and ZXingRecognizer
				//  will be used in the recognition process
				recognitionSettings.setRecognizerSettingsArray(new RecognizerSettings[]{pdf417RecognizerSettings, oneDimensionalRecognizerSettings, zXingRecognizerSettings, sm});

				// additionally, there are generic settings that are used by all recognizers or the
				// whole recognition process

				// by default, this option is true, which means that it is possible to obtain multiple
				// recognition results from the same image.
				// if you want to obtain one result from the first successful recognizer
				// (when first barcode is found, no matter which type) set this option to false
				// recognitionSettings.setAllowMultipleScanResultsOnSingleImage(false);

				// finally send that settings object over intent to scan activity
				// use Pdf417ScanActivity.EXTRAS_RECOGNITION_SETTINGS to set recognizer settings
				intent.putExtra(Pdf417ScanActivity.EXTRAS_RECOGNITION_SETTINGS, recognitionSettings);

				// if you do not want the dialog to be shown when scanning completes, add following extra
				// to intent
				// intent.putExtra(Pdf417ScanActivity.EXTRAS_SHOW_DIALOG_AFTER_SCAN, false);

				// if you want to enable pinch to zoom gesture, add following extra to intent
				intent.putExtra(Pdf417ScanActivity.EXTRAS_ALLOW_PINCH_TO_ZOOM, true);

				// if you want Pdf417ScanActivity to display rectangle where camera is focusing,
				// add following extra to intent
				intent.putExtra(Pdf417ScanActivity.EXTRAS_SHOW_FOCUS_RECTANGLE, true);

				// if you want to use camera fit aspect mode to letterbox the camera preview inside
				// available activity space instead of cropping camera frame (default), add following
				// extra to intent.
				// Camera Fit mode does not look as nice as Camera Fill mode on all devices, especially on
				// devices that have very different aspect ratios of screens and cameras. However, it allows
				// all camera frame pixels to be processed - this is useful when reading very large barcodes.
				// intent.putExtra(Pdf417ScanActivity.EXTRAS_CAMERA_ASPECT_MODE, (Parcelable) CameraAspectMode.ASPECT_FIT);

				// Start Activity
				startActivityForResult(intent, MY_REQUEST_CODE);
			}

		} catch (ActivityNotFoundException anfe) {
			//on catch, show the download dialog
			//showDialog(QuizCreateActaActivity.this, "Scanner no encontrado", "Download a scanner code activity?", "Yes", "No").show();
			UIHelper.showAlertOnGuiThread(CreateActaActivity.this, "Scanner no encontrado", "Scanner de Codigo QR ", null);

		}


	}

	private void doVerificarLicencia(String numeroLicencia) {
		//EnvioCodigoQRRequestTask
		// make sure we don't collide with another pending update
		if (licenciaRequest == null || licenciaRequest.getStatus() == AsyncTask.Status.FINISHED || licenciaRequest.isCancelled()) {
			licenciaRequest = new LicenciaRequestTask();
			licenciaRequest.setPostExecuteHandler(receptorVerificacionPolicialyLicenciaTask);
			licenciaRequest.execute(numeroLicencia);
		} else {
			Log.w(DEBUG_TAG, "Warning: licenciaRequestTask already going");
		}

		/*
        // make sure we don't collide with another pending update
        if (licenciaRequest == null || licenciaRequest.getStatus() == AsyncTask.Status.FINISHED || licenciaRequest.isCancelled()) {
            licenciaRequest = new LicenciaRequestTask();
            licenciaRequest.setPostExecuteHandler(receptorVerificacionPolicialyLicenciaTask);
            licenciaRequest.execute(numeroLicencia);
        } else {
            Log.w(DEBUG_TAG, "Warning: licenciaRequestTask already going");
        }
        */
	}

	private void setupErrorFor(List<AndroidWidgetControl> lstControls) {
		for (AndroidWidgetControl objeto : lstControls) {
			String nombreControl = objeto.getNameOfControl();
			String mensajeAMostrar = objeto.getMessage();
			int id = getResources().getIdentifier(nombreControl, "id", this.getPackageName());

			if (id != 0) {
				View view;
				view = findViewById(id);
				setupErrorFor(view, mensajeAMostrar);
			}
		}
	}

	private void setupErrorFor(View view, String sMensaje) {
		if (view == null) return;

		if (view.getClass().getName().equalsIgnoreCase("android.widget.Spinner")) {
			View selectedView = ((Spinner) view).getSelectedView();
			if (selectedView != null && selectedView instanceof TextView) {
				TextView selectedTextView = (TextView) selectedView;
				String errorString = sMensaje;
				selectedTextView.setError(errorString);
			}
		} else {
			if (view.getClass().getName().equalsIgnoreCase("android.widget.EditText")) {
				String errorString = sMensaje;
				((EditText) view).setError(errorString);
			}
		}

	}

	private void ocultarTeclado(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

	}

	private void focusIn(String NombredelControl, String sMensaje) {
		try {
			int id = getResources().getIdentifier(NombredelControl, "id", this.getPackageName());

			if (id != 0) {
				View view;
				view = findViewById(id);
				if (view != null) {
					ScrollView scvw = (ScrollView) findViewById(R.id.ScrollView01);
					scvw.scrollTo(0, (int) view.getY());
					view.requestFocusFromTouch();
					setupErrorFor(view, sMensaje);
					ocultarTeclado(view);
				}

			}
		} catch (Exception ex) {

		}
	}

	private void doGrabarActa() {

		// get location manager to check gps availability
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 1.0f, CreateActaActivity.this);

		boolean isGPS = locationManager.isProviderEnabled (LocationManager.GPS_PROVIDER);



		final ActaConstatacion acta ;//= new ActaConstatacion();
		ActaConstatacionRules actaRules = new ActaConstatacionRules(this);

		acta = doCargarSeleccion();
		//View controlGenerico=null;

		//StringBuilder NombreControlGenerico = new StringBuilder();
		List<AndroidWidgetControl> lstControl;
		lstControl = actaRules.validarActa(acta);

		if(!isGPS){
			bGrabando = false;
			UIHelper.showAlertOnGuiThread(this, "Debe habilitar el servicio de GPS", "Generacion de Actas", new Runnable() {

			@Override
			public void run() {
				turnGPSOn();
			}
		});

   	  return;
	}


		if (lstControl.size()>0)
		{	// siempre mostraremos el primer erro (por ahora)
			    AndroidWidgetControl awctrl = lstControl.get(0);
				String sNameOfControl = awctrl.getNameOfControl();
				String sMensajeError = awctrl.getMessage();
				focusIn(sNameOfControl,sMensajeError);
				setupErrorFor(lstControl);
				bGrabando = false;
			return ;
		}



		if (acta.getTieneLicencia().equals("S") && acta.getSinConductor().equals("N") && !acta.getNumeroLicencia().equalsIgnoreCase(acta.getNumeroDocumento()))
		{

			UIHelper.showAlertCancelableExtended(CreateActaActivity.this, "El Numero de la Licencia No coincide con el Numero de Documento del Infractor, Desea Continuar?","Labrado de Actas",new Runnable() {

				@Override
				public void run() {
					IniciarGrabadoEImpresion(acta);
				}
			},new Runnable() {
					@Override
					public void run() {
						bGrabando= false;
					}
			});

		}

		else{

			IniciarGrabadoEImpresion(acta);
		}


	}

	private void IniciarGrabadoEImpresion(final ActaConstatacion acta)
	{
		final ActaConstatacionRules actaRules = new ActaConstatacionRules(this);

		List<ActaConstatacion> lstActasCreadas = actaRules.grabarActa(acta);
		//int cantidad = lstActasCreadas.size();

		new ZebraPrinterTask<Object, Object, Object>((Activity)CreateActaActivity.this, new Object[0])
         {
		   Boolean bActaGrabada =false;
           @Override
           public Object doWork(final Object[] params)
             throws ConnectionException, ZebraIllegalArgumentException
	           {
        	    //RESTAURAR 2016
        	      CreateActaHelper.printTestigo(this.printer);
        	    //aqui debemos grabar 2 actas si hay 2 infracciones
        	    //bActaGrabada

        	    List<ActaConstatacion> lstActasCreadas = actaRules.grabarActa(acta);
        	       int cantidad = lstActasCreadas.size();
        	     //if(bActaGrabada==true)
	        	   if(cantidad>0)
	        	   {
	        		   bActaGrabada = true;
	        	    	return lstActasCreadas;
	        	   }
	        	   else
	        	   {
	        		     bGrabando = false;
	        	    	 return null;
	        	   }
	           }
           @Override
           public void handleError(final Exception e)
           {
     		  bGrabando = false;
        	  if (e instanceof ConnectionException)
        		  UIHelper.showErrorOnGuiThread((Activity)CreateActaActivity.this, "No se pudo conectar con la Impresora\nVerifique que la Impresora este Encendida", null);
        	  else
        	  {
        		  UIHelper.showErrorOnGuiThread((Activity)CreateActaActivity.this, e.getLocalizedMessage(), new Runnable() {

					@Override
					public void run() {

			        	  if (bActaGrabada==true)
			        	  {
			        		  CreateActaActivity.this.finish();
			        	  }
					}
				});


        	  }


           }
	   		@Override
	   		public void onProgressUpdate(Object... values) {
	   			if(values != null && values[0] != null) {
	   				///context.getProgressDialog().setMessage(values[0]);
	   				pleaseWaitDialog.setMessage((String)values[0]);
	   				pleaseWaitDialog.incrementProgressBy(30);
	   				if(!pleaseWaitDialog.isShowing())
	   					pleaseWaitDialog.show();
	   				//if(!context.getProgressDialog().isShowing())
	   				//	context.getProgressDialog().show();
	   			}
	   			super.onProgressUpdate(values);

	   		}
           @Override
           public void onPreExecute()
           {
   			WorkingInPrintAsyncTask = true;
   			pleaseWaitDialog = ProgressDialog.show(CreateActaActivity.this,"Grabando Acta...", "Policia Caminera", true, false);
        	super.onPreExecute();
           }


           @SuppressWarnings("unchecked")
		   @Override
           public void onPostExecute(Object object)
           {
        		WorkingInPrintAsyncTask = false;
    			super.onPostExecute(object);

    			pleaseWaitDialog.dismiss();
    			pleaseWaitDialog = null;

        	    if(bActaGrabada)
       			{   // podriamos leer  lo que se grabo y eso mismo imprimir
	        	     if(object!=null)
	        	    {	//ActaConstatacion actaGrabada = (ActaConstatacion) object;
	        	    	List<ActaConstatacion> actasGrabadas = (List <ActaConstatacion>) object;
	        	    	List<ActaConstatacion> actasGrabadasCompletas = new ArrayList<ActaConstatacion>();
	        	    	for(ActaConstatacion actaGrabada: actasGrabadas)
	        	    	{
	        	    		actaGrabada = actaRules.getActaById(actaGrabada.getIdActaConstatacion());
	        	    		actaGrabada = Utilities.CargarInformacionAdicional(actaGrabada);
	        	    		actasGrabadasCompletas.add(actaGrabada);
	        	    	}
	        	    	//RESTAURAR 2017
        	    		doPrintActaRequest(actasGrabadasCompletas);


	        	    }

       			}
        	    else
        	    {
	        		bGrabando = false;
        	    }

           }
         };

	}
	private ActaConstatacion  doCargarSeleccion()
	{
		ActaConstatacion acta = new ActaConstatacion();
		/* carga de datos que estuvieron faltando */
		Date hoy = Tools.Today();
		acta.setFechaHoraCarga(hoy);
		acta.setFechaHoraLabrado(hoy);

    	ActaConstatacionRules actaRules = new ActaConstatacionRules(this);
    	String sNumeroActa = actaRules.getNextNumeroActa();
		acta.setNumeroActa(sNumeroActa);
		String digitoVerificador = sNumeroActa.substring(sNumeroActa.length()-1);
		acta.setDigitoVerificador(digitoVerificador);

		String sLetraSerie ="";
		try
		{
			sLetraSerie = GlobalVar.getInstance().getLetraSerie();
		}
		catch(Exception ex)
		{

		}
		acta.setLetraSerie(sLetraSerie);

		Date fechaVencimiento = Utilities.ConfigFechaVencimiento(this, 0);
		Date fechaVencimiento2do = Utilities.ConfigFechaVencimiento2do(this, 0);

		acta.setFechaVencimientoCupon(fechaVencimiento);
		acta.setFechaVencimiento2DoCupon(fechaVencimiento2do);
		Integer idMovil = GlobalVar.getInstance().getIdMovil();
		acta.setIdMovil(idMovil);


		//acta.setIdActaConstatacion()
		String idUsuario = GlobalVar.getInstance().getIdUsuario();
		acta.setIdUsuarioPDA(idUsuario);

		String sLatitud = "0.0";
		String sLongitud = "0.0";
		try
		{
				sLatitud = GlobalVar.getInstance().getLatitud().toString();
				sLongitud = GlobalVar.getInstance().getLongitud().toString();

				double dLatitud = GlobalVar.getInstance().getLatitud();
				double dLongitud = GlobalVar.getInstance().getLongitud();

				sLatitud = Tools.DecimalFormat(dLatitud,"0.0000000");
				sLongitud = Tools.DecimalFormat(dLongitud,"0.0000000");
				acta.setLatitud(sLatitud);
				acta.setLongitud(sLongitud);
		}
		catch(Exception e){}
		try
		{
			calculateCurrentCoordinates();
			sLatitud = Tools.DecimalFormat((double) mFavPlaceCoords.mLat,"0.0000000");
			sLongitud = Tools.DecimalFormat((double) mFavPlaceCoords.mLon,"0.0000000");
		} catch(Exception e){}

		try
		{		acta.setLatitud(sLatitud);
				acta.setLongitud(sLongitud);
		}
		catch(Exception e){}
		/**/
		acta.setoDepartamentoInfraccion((Departamento) myDepartamentoInfraccionSpinner.getSelectedItem());
		//if (acta.getIdDepartamentoInfraccion()!=null && acta.getIdDepartamentoInfraccion().equals(-1))  acta.setDepartamentoInfraccion(editText_DepartamentoInfraccion.getText().toString());

		acta.setoLocalidadInfraccion((Localidad) myLocalidadInfraccionSpinner.getSelectedItem());
		//if (acta.getIdLocalidadInfraccion() !=null && acta.getIdLocalidadInfraccion().equals(-1))  acta.setLocalidadInfraccion(editText_LocalidadInfraccion.getText().toString());


		/**/
		//
		acta.setoTipoRuta((TipoRuta) myTipoRutaSpinner.getSelectedItem());
		// por ahora este campo esta oculto y quizas no se use nunca
		acta.setUbicacion(editText_Ubicacion.getText().toString());

		acta.setoRuta((Ruta) myRutaSpinner.getSelectedItem());

		acta.setDescripcionUbicacion(editText_DescripcionUbicacion.getText().toString());

		acta.setReferencia(editText_Referencia.getText().toString());
		acta.setTelefonoConductor(editText_Telefono.getText().toString());
		acta.setEmailConductor(editText_Email.getText().toString());

		if(checkBox_ConduccionPeligrosa.isChecked()==true)
		{
			acta.setConduccionPeligrosa("S");
		}
		else
		{
			acta.setConduccionPeligrosa("N");
		}

		if(checkBox_DejaCopia.isChecked()==true)
		{
			acta.setDejaCopia("S");
		}
		else
		{
			acta.setDejaCopia("N");
		}

		acta.setKm(editText_Km.getText().toString());
		acta.setoEntidad((Entidad) myEntidadSpinner.getSelectedItem());
		acta.setoSeccional((Seccional) mySeccionalSpinner.getSelectedItem());

		CheckBox chkNoTieneLicencia = (CheckBox) findViewById(R.id.CheckBox_NoTieneLicencia);
		if(chkNoTieneLicencia.isChecked()==false)
		{
			acta.setTieneLicencia("S");
			CheckBox chkLicenciaRetenida = (CheckBox) findViewById(R.id.CheckBox_LicenciaRetenida);
			if(chkLicenciaRetenida.isChecked()==true) acta.setLicenciaRetendia("S"); else acta.setLicenciaRetendia("N");

			CheckBox chkLicenciaUnicaProvincial = (CheckBox) findViewById(R.id.CheckBox_LicenciaUnicaProvincial);
			if(chkLicenciaUnicaProvincial.isChecked()==true) acta.setLicenciaUnicaProvincial("S"); else acta.setLicenciaUnicaProvincial("N");

			acta.setNumeroLicencia(editText_NumeroLicencia.getText().toString());
			acta.setClaseLicencia(editText_ClaseLicencia.getText().toString());

			acta.setFechaVencimientoLicencia(Tools.ConvertStringToDate(editText_FVL_Info.getText().toString()));


			acta.setoPaisLicencia((Pais) myPaisSpinner.getSelectedItem());
			if (acta.getIdPaisLicencia().equals("-1"))  acta.setPaisLicencia(editText_PaisLicencia.getText().toString());

			acta.setoProvinciaLicencia((Provincia) myProvinciaSpinner.getSelectedItem());
			if (acta.getIdProvinciaLicencia().equals("-1"))  acta.setProvinciaLicencia(editText_ProvinciaLicencia.getText().toString());

			/*
			acta.setoDepartamentoLicencia((Departamento) myDepartamentoSpinner.getSelectedItem());
			if (acta.getIdDepartamentoLicencia()!=null && acta.getIdDepartamentoLicencia().equals(-1))  acta.setDepartamentoLicencia(editText_DepartamentoLicencia.getText().toString());

			acta.setoLocalidadLicencia((Localidad) myLocalidadSpinner.getSelectedItem());
			if (acta.getIdLocalidadLicencia()!=null && acta.getIdLocalidadLicencia().equals(-1))  acta.setLocalidadLicencia(editText_LocalidadLicencia.getText().toString());
			*/
		}
		else
			acta.setTieneLicencia("N");


		CheckBox chkSinConductor = (CheckBox) findViewById(R.id.CheckBox_SinConductor);
		if(chkSinConductor.isChecked()==false)
		{

			acta.setoTipoDocumento((TipoDocumento) myTipoDocumentoSpinner.getSelectedItem());
			//acta.setoGenero((Genero) myGeneroSpinner.getSelectedItem());
			acta.setNumeroDocumento(editText_NumeroDocumento.getText().toString());
			acta.setFechaNacimiento(Tools.ConvertStringToDate(textView_FN_Info.getText().toString()));

			acta.setApellido(editText_Apellido.getText().toString());
			acta.setNombre(editText_Nombre.getText().toString());

			CheckBox chkEsCallePublica = (CheckBox) findViewById(R.id.CheckBox_CallePublica);
			if(chkEsCallePublica.isChecked()==false)
			{
				acta.setCalle(editText_Calle.getText().toString());
				acta.setEsCallePublica(false);
			}
			else
			{
				acta.setEsCallePublica(true);
				acta.setCalle(GlobalVar.getInstance().getSuportTable().getCallePublica());
			}

			CheckBox chkEsAlturaSinNumero= (CheckBox) findViewById(R.id.CheckBox_AlturaSinNumero);
			if(chkEsAlturaSinNumero.isChecked()==false)
			{
				acta.setAltura(editText_Altura.getText().toString());
				acta.setEsAlturaSinNumero(false);
			}
			else
			{
				acta.setEsAlturaSinNumero(true);
				acta.setAltura(GlobalVar.getInstance().getSuportTable().getAlturaSinNumero());
			}


			acta.setPiso(editText_Piso.getText().toString());
			acta.setDepartamento(editText_Depto.getText().toString());
			acta.setBarrio(editText_Barrio.getText().toString());

			acta.setoPaisDomicilio((Pais) myPaisDSpinner.getSelectedItem());
			if (acta.getIdPaisDomicilio().equals("-1"))  acta.setPaisDomicilio(editText_PaisDocumento.getText().toString());

			acta.setoProvinciaDomicilio((Provincia) myProvinciaDSpinner.getSelectedItem());
			if (acta.getIdProvinciaDomicilio().equals("-1"))  acta.setProvinciaDomicilio(editText_ProvinciaDocumento.getText().toString());

			acta.setoDepartamentoDomicilio((Departamento) myDepartamentoDSpinner.getSelectedItem());
			if (acta.getIdDepartamentoDomicilio()!=null && acta.getIdDepartamentoDomicilio().equals(-1))  acta.setDepartamentoDomicilio(editText_DepartamentoDocumento.getText().toString());

			acta.setoLocalidadDomicilio((Localidad) myLocalidadDSpinner.getSelectedItem());
			if (acta.getIdLocalidadDomicilio() !=null && acta.getIdLocalidadDomicilio().equals(-1))  acta.setLocalidadDomicilio(editText_LocalidadDocumento.getText().toString());

			acta.setCodigoPostal(editText_CodigoPostal.getText().toString());
			acta.setSinConductor("N");
		}
		else
		{
			acta.setSinConductor("S");
		}// fin de If Sin Conductor = false

		/* Datos del Titular */
		//CheckBox chkConductorTitular = (CheckBox) findViewById(R.id.CheckBox_ConductorTitular);
		//if(chkConductorTitular.isChecked()==false)
		CheckBox chkSinTitular = (CheckBox) findViewById(R.id.CheckBox_SinTitular);
		if(chkSinTitular.isChecked()==true)
		{
			acta.setSinTitular("S");
		}
		else
		{
			acta.setSinTitular("N");
			acta.setoTipoDocumentoTitular((TipoDocumento) myTipoDocumentoTitularSpinner.getSelectedItem());
			//acta.setoGeneroTitular((Genero) myGeneroTitularSpinner.getSelectedItem());
			acta.setNumeroDocumentoTitular(editText_NumeroDocumentoTitular.getText().toString());


			acta.setApellidoTitular(editText_ApellidoTitular.getText().toString());
			acta.setNombreTitular(editText_NombreTitular.getText().toString());

			CheckBox chkEsCallePublicaTitular = (CheckBox) findViewById(R.id.CheckBox_CallePublicaTitular);
			if(chkEsCallePublicaTitular.isChecked()==false)
			{
				acta.setCalleTitular(editText_CalleTitular.getText().toString());
				acta.setEsCallePublicaTitular(false);
			}
			else
			{
				acta.setEsCallePublicaTitular(true);
				acta.setCalleTitular(GlobalVar.getInstance().getSuportTable().getCallePublica());
			}

			CheckBox chkEsAlturaSinNumeroTitular= (CheckBox) findViewById(R.id.CheckBox_AlturaSinNumeroTitular);
			if(chkEsAlturaSinNumeroTitular.isChecked()==false)
			{
				acta.setAlturaTitular(editText_AlturaTitular.getText().toString());
				acta.setEsAlturaSinNumeroTitular(false);
			}
			else
			{
				acta.setEsAlturaSinNumeroTitular(true);
				acta.setAlturaTitular(GlobalVar.getInstance().getSuportTable().getAlturaSinNumero());
			}


			acta.setPisoTitular(editText_PisoTitular.getText().toString());
			acta.setDepartamentoTitular(editText_DeptoTitular.getText().toString());
			acta.setBarrioTitular(editText_BarrioTitular.getText().toString());

			acta.setoPaisDomicilioTitular((Pais) myPaisDTitularSpinner.getSelectedItem());
			if (acta.getIdPaisDomicilioTitular().equals("-1"))  acta.setPaisDomicilioTitular(editText_PaisDocumentoTitular.getText().toString());

			acta.setoProvinciaDomicilioTitular((Provincia) myProvinciaDTitularSpinner.getSelectedItem());
			if (acta.getIdProvinciaDomicilioTitular().equals("-1"))  acta.setProvinciaDomicilioTitular(editText_ProvinciaDocumentoTitular.getText().toString());

			acta.setoDepartamentoDomicilioTitular((Departamento) myDepartamentoDTitularSpinner.getSelectedItem());
			if (acta.getIdDepartamentoDomicilioTitular()!=null && acta.getIdDepartamentoDomicilioTitular().equals(-1))  acta.setDepartamentoDomicilioTitular(editText_DepartamentoDocumentoTitular.getText().toString());

			acta.setoLocalidadDomicilioTitular((Localidad) myLocalidadDTitularSpinner.getSelectedItem());
			if (acta.getIdLocalidadDomicilioTitular() !=null && acta.getIdLocalidadDomicilioTitular().equals(-1))  acta.setLocalidadDomicilioTitular(editText_LocalidadDocumentoTitular.getText().toString());

			acta.setCodigoPostalTitular(editText_CodigoPostalTitular.getText().toString());
		}

		/*
		else
		{
			if(acta.getSinConductor().equals("S"))
			{
				acta.setSinTitular("S");
			}
			else
			{
				acta.setSinTitular("N");
			}
			acta.setConductorEsTitular("S");
			//copiamos los datos del conductor en el titular
			acta.setoTipoDocumentoTitular((TipoDocumento) myTipoDocumentoSpinner.getSelectedItem());
			//acta.setoGeneroTitular((Genero) myGeneroSpinner.getSelectedItem());
			acta.setNumeroDocumentoTitular(editText_NumeroDocumento.getText().toString());


			acta.setApellidoTitular(editText_Apellido.getText().toString());
			acta.setNombreTitular(editText_Nombre.getText().toString());

			CheckBox chkEsCallePublicaTitular = (CheckBox) findViewById(R.id.CheckBox_CallePublica);
			if(chkEsCallePublicaTitular.isChecked()==false)
			{
				acta.setCalleTitular(editText_Calle.getText().toString());
				acta.setEsCallePublicaTitular(false);
			}
			else
			{
				acta.setEsCallePublicaTitular(true);
				acta.setCalleTitular(GlobalVar.getInstance().getSuportTable().getCallePublica());
			}

			CheckBox chkEsAlturaSinNumeroTitular= (CheckBox) findViewById(R.id.CheckBox_AlturaSinNumero);
			if(chkEsAlturaSinNumeroTitular.isChecked()==false)
			{
				acta.setAlturaTitular(editText_Altura.getText().toString());
				acta.setEsAlturaSinNumeroTitular(false);
			}
			else
			{
				acta.setEsAlturaSinNumeroTitular(true);
				acta.setAlturaTitular(GlobalVar.getInstance().getSuportTable().getAlturaSinNumero());
			}


			acta.setPisoTitular(editText_Piso.getText().toString());
			acta.setDepartamentoTitular(editText_Depto.getText().toString());
			acta.setBarrioTitular(editText_Barrio.getText().toString());

			acta.setoPaisDomicilioTitular((Pais) myPaisDSpinner.getSelectedItem());
			if (acta.getIdPaisDomicilioTitular().equals("-1"))  acta.setPaisDomicilioTitular(editText_PaisDocumento.getText().toString());

			acta.setoProvinciaDomicilioTitular((Provincia) myProvinciaDSpinner.getSelectedItem());
			if (acta.getIdProvinciaDomicilioTitular().equals("-1"))  acta.setProvinciaDomicilioTitular(editText_ProvinciaDocumento.getText().toString());

			acta.setoDepartamentoDomicilioTitular((Departamento) myDepartamentoDSpinner.getSelectedItem());
			if (acta.getIdDepartamentoDomicilioTitular()!=null && acta.getIdDepartamentoDomicilioTitular().equals(-1))  acta.setDepartamentoDomicilioTitular(editText_DepartamentoDocumento.getText().toString());

			acta.setoLocalidadDomicilioTitular((Localidad) myLocalidadDSpinner.getSelectedItem());
			if (acta.getIdLocalidadDomicilioTitular() !=null && acta.getIdLocalidadDomicilioTitular().equals(-1))  acta.setLocalidadDomicilioTitular(editText_LocalidadDocumento.getText().toString());

			acta.setCodigoPostalTitular(editText_CodigoPostal.getText().toString());
			// se copiaron los datos del conductor

		}
		*/
		/* Fin de Datos del Titular */

		/* Datos del Testigo */
		CheckBox chkSinTestigo = (CheckBox) findViewById(R.id.CheckBox_SinTestigo);
		if(chkSinTestigo.isChecked()==true)
		{
			acta.setSinTestigo("S");

		}
		else
		{
			    acta.setSinTestigo("N");
				acta.setoTipoDocumentoTestigo((TipoDocumento) myTipoDocumentoTestigoSpinner.getSelectedItem());
				//acta.setoGeneroTestigo((Genero) myGeneroTestigoSpinner.getSelectedItem());
				acta.setNumeroDocumentoTestigo(editText_NumeroDocumentoTestigo.getText().toString());


				acta.setApellidoTestigo(editText_ApellidoTestigo.getText().toString());
				acta.setNombreTestigo(editText_NombreTestigo.getText().toString());

				CheckBox chkEsCallePublicaTestigo = (CheckBox) findViewById(R.id.CheckBox_CallePublicaTestigo);
				if(chkEsCallePublicaTestigo.isChecked()==false)
				{
					acta.setCalleTestigo(editText_CalleTestigo.getText().toString());
					acta.setEsCallePublicaTestigo(false);
				}
				else
				{
					acta.setEsCallePublicaTestigo(true);
					acta.setCalleTestigo(GlobalVar.getInstance().getSuportTable().getCallePublica());
				}

				CheckBox chkEsAlturaSinNumeroTestigo= (CheckBox) findViewById(R.id.CheckBox_AlturaSinNumeroTestigo);
				if(chkEsAlturaSinNumeroTestigo.isChecked()==false)
				{
					acta.setAlturaTestigo(editText_AlturaTestigo.getText().toString());
					acta.setEsAlturaSinNumeroTestigo(false);
				}
				else
				{
					acta.setEsAlturaSinNumeroTestigo(true);
					acta.setAlturaTestigo(GlobalVar.getInstance().getSuportTable().getAlturaSinNumero());
				}


				acta.setPisoTestigo(editText_PisoTestigo.getText().toString());
				acta.setDepartamentoTestigo(editText_DeptoTestigo.getText().toString());
				acta.setBarrioTestigo(editText_BarrioTestigo.getText().toString());

				acta.setoPaisDomicilioTestigo((Pais) myPaisDTestigoSpinner.getSelectedItem());
				if (acta.getIdPaisDomicilioTestigo().equals("-1"))  acta.setPaisDomicilioTestigo(editText_PaisDocumentoTestigo.getText().toString());

				acta.setoProvinciaDomicilioTestigo((Provincia) myProvinciaDTestigoSpinner.getSelectedItem());
				if (acta.getIdProvinciaDomicilioTestigo().equals("-1"))  acta.setProvinciaDomicilioTestigo(editText_ProvinciaDocumentoTestigo.getText().toString());

				acta.setoDepartamentoDomicilioTestigo((Departamento) myDepartamentoDTestigoSpinner.getSelectedItem());
				if (acta.getIdDepartamentoDomicilioTestigo()!=null && acta.getIdDepartamentoDomicilioTestigo().equals(-1))  acta.setDepartamentoDomicilioTestigo(editText_DepartamentoDocumentoTestigo.getText().toString());

				acta.setoLocalidadDomicilioTestigo((Localidad) myLocalidadDTestigoSpinner.getSelectedItem());
				if (acta.getIdLocalidadDomicilioTestigo() !=null && acta.getIdLocalidadDomicilioTestigo().equals(-1))  acta.setLocalidadDomicilioTestigo(editText_LocalidadDocumentoTestigo.getText().toString());

				acta.setCodigoPostalTestigo(editText_CodigoPostalTestigo.getText().toString());

				acta.setManifestacionTestigo(editText_ManifestacionTestigo.getText().toString());
		}
		/* Fin Datos del Testigo */


		TipoVehiculo idtipov = (TipoVehiculo) myTipoVehiculoSpinner.getSelectedItem();
		acta.setoTipoVehiculo(idtipov);
		acta.setIdTipoPatente((String) myTipoPatenteSpinner.getSelectedItem());
		acta.setDominio(editText_Dominio.getText().toString());

		acta.setoMarca((Marca) myMarcaSpinner.getSelectedItem());
		if (acta.getIdMarca()!=null && acta.getIdMarca().equals(-1))  acta.setMarca(editText_Marca.getText().toString());

		acta.setModeloVehiculo(editText_ModeloVehiculo.getText().toString());
		acta.setAnioModeloVehiculo(editText_AnioModeloVehiculo.getText().toString());

		if(checkBox_VehiculoRetenido.isChecked()==true) acta.setVehiculoRetenido("S"); else acta.setVehiculoRetenido("N");

		acta.setoColor((Color) myColorSpinner.getSelectedItem());
		if (acta.getIdColor()!=null && acta.getIdColor().equals(-1))  acta.setColor(editText_Color.getText().toString());

		if(editText_CODInfraccion_1.getIdItem()!=null && editText_CODInfraccion_1.getObjectItem()!=null)
		{
			//acta.setIdInfraccion1(Integer.parseInt(editText_CODInfraccion_1.getIdItem()));
			acta.setoInfraccionNomenclada1((InfraccionNomenclada)editText_CODInfraccion_1.getObjectItem());
		}

		if(editText_CODInfraccion_2.getIdItem()!=null && editText_CODInfraccion_2.getObjectItem()!=null)
		{
			//acta.setIdInfraccion2(Integer.parseInt(editText_CODInfraccion_2.getIdItem()));
			acta.setoInfraccionNomenclada2((InfraccionNomenclada)editText_CODInfraccion_2.getObjectItem());
		}
		if(editText_CODInfraccion_3.getIdItem()!=null && editText_CODInfraccion_3.getObjectItem()!=null)
		{
			//acta.setIdInfraccion2(Integer.parseInt(editText_CODInfraccion_2.getIdItem()));
			acta.setoInfraccionNomenclada3((InfraccionNomenclada)editText_CODInfraccion_3.getObjectItem());
		}
		if(editText_CODInfraccion_4.getIdItem()!=null && editText_CODInfraccion_4.getObjectItem()!=null)
		{
			//acta.setIdInfraccion2(Integer.parseInt(editText_CODInfraccion_2.getIdItem()));
			acta.setoInfraccionNomenclada4((InfraccionNomenclada)editText_CODInfraccion_4.getObjectItem());
		}
		if(editText_CODInfraccion_5.getIdItem()!=null && editText_CODInfraccion_5.getObjectItem()!=null)
		{
			//acta.setIdInfraccion2(Integer.parseInt(editText_CODInfraccion_2.getIdItem()));
			acta.setoInfraccionNomenclada5((InfraccionNomenclada)editText_CODInfraccion_5.getObjectItem());
		}
		if(editText_CODInfraccion_6.getIdItem()!=null && editText_CODInfraccion_6.getObjectItem()!=null)
		{
			//acta.setIdInfraccion2(Integer.parseInt(editText_CODInfraccion_2.getIdItem()));
			acta.setoInfraccionNomenclada6((InfraccionNomenclada)editText_CODInfraccion_6.getObjectItem());
		}


		/* al momento de grabar el acta como es particular para  este calculo cuando vamos a grabar el acta*/
		Double monto= 0.0;
		List<Integer> lstIdInfracciones = new ArrayList<Integer>();

		if (acta.getIdInfraccion1()!=null && acta.getIdInfraccion1()>0) lstIdInfracciones.add(acta.getIdInfraccion1());
		if (acta.getIdInfraccion2()!=null && acta.getIdInfraccion2()>0) lstIdInfracciones.add(acta.getIdInfraccion2());
		if (acta.getIdInfraccion3()!=null && acta.getIdInfraccion3()>0) lstIdInfracciones.add(acta.getIdInfraccion3());
		if (acta.getIdInfraccion4()!=null && acta.getIdInfraccion4()>0) lstIdInfracciones.add(acta.getIdInfraccion4());
		if (acta.getIdInfraccion5()!=null && acta.getIdInfraccion5()>0) lstIdInfracciones.add(acta.getIdInfraccion5());
		if (acta.getIdInfraccion6()!=null && acta.getIdInfraccion6()>0) lstIdInfracciones.add(acta.getIdInfraccion6());

		monto = Utilities.ConfigMontoTotalMendozaConDescuento(this,lstIdInfracciones);

		acta.setMontoCupon(monto);

		double montoSancion =0.0;
		montoSancion = Utilities.ConfigMontoTotalMendoza(this,lstIdInfracciones);

		acta.setImporteSancion(montoSancion);
		Double descuentoAplicadoSancion = montoSancion * GlobalVar.getInstance().getSuportTable().getPorcDescuento() /100;
		acta.setDescuentoAplicadoSancion(descuentoAplicadoSancion);
		monto = montoSancion - descuentoAplicadoSancion;

		acta.setMontoCupon(monto);

		//String sMonto = String.format("%10.2", monto) ;
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		String sFechaVencimiento = formatter.format(fechaVencimiento);

		//RECORDAR QUE LOS CAMPOS CALCULADOS LO DEBEMOS PONER LUEGO DE LAS VALDIACIONES PARA QUE NO PRODUZCAN ERROR
		//String codigoBarra = Tools.actaCodeGetKolektor(acta.getNumeroActa().toString(), acta.getNumeroDocumento().toString(), sFechaVencimiento, acta.getMontoCuponString());//(actaID, pNroDocumento, pFechaVencimiento_ddmmyyyy, pMontoMulta0_00);
		int largo = acta.getNumeroActa().length();
		String Letra = acta.getNumeroActa().substring(4,largo);
		String codigoBarra = "5133" + acta.getNumeroActa().substring(1,largo) + acta.getLetraSerie() + "02501";//(actaID, pNroDocumento, pFechaVencimiento_ddmmyyyy, pMontoMulta0_00);
		acta.setCodigoBarra(codigoBarra);

		String pMontoMulta0_00 = Tools.DecimalFormat(monto);
        pMontoMulta0_00 = pMontoMulta0_00.replace(",", "").replace(".", "");
        String sMontoMulta = Tools.formatearIzquierda(pMontoMulta0_00, 10,'0'); // 10 digitos

        /*String pMontoSancion0_00 = Tools.DecimalFormat(descuentoAplicadoSancion);
		pMontoSancion0_00 = pMontoSancion0_00.replace(",", "").replace(".", "");
        String sMontoSancion = Tools.formatearIzquierda(pMontoSancion0_00, 6,'0'); // 10 digitos  */

		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		String AYear = df.format(acta.getFechaHoraLabrado());

		String codigoBarra2 = AYear  +"01" +  sMontoMulta ;

		digitoVerificador = Tools.CalculaDV_Mendoza(codigoBarra + codigoBarra2);
		codigoBarra2 = codigoBarra2 + digitoVerificador;
		acta.setCodigoBarra2Do(codigoBarra2);
		/**/

		/*El siguiente codigo se aplicará cuando se implemente el cobro
		  a traves del sistema de PAGOS360.COM


		char letra = acta.getNumeroActa().substring(0,1).charAt(0);
		String letraAscii = Integer.toString((int)letra);

		String codigoBarra360 = "29589" + "123456" + letraAscii + acta.getNumeroActa().substring(2);
		acta.setCodigoBarra(codigoBarra360);

		int dias2doVencim = Utilities.diasHabilesACorridos(10);

		String segCodigoBarra360 = sMontoMulta + Tools.fechaJuliana(fechaVencimiento) + sMontoSancion + dias2doVencim;
		segCodigoBarra360 = segCodigoBarra360 + Tools.calculoDVPagos360(segCodigoBarra360);
		acta.setCodigoBarra2Do(segCodigoBarra360);
		*/

		acta.setObservaciones(editText_Observaciones.getText().toString());
		acta.setObservaciones2(editText_Observaciones2.getText().toString());
		acta.setGrad_alcohol(editText_AlcoholEnSangre.getText().toString());

		//String sImagenes ="";
		//String sImagen ="";

		String sImagen = RESOURCE_SIN_FOTO;
		String sImagenes = RESOURCE_SIN_FOTO;

		sImagen = mCurrenActaSettings.getString(CURRENT_ACTA_FOTO_LICENCIA,RESOURCE_SIN_FOTO);
		sImagenes = sImagen;
		if (!sImagen.equals("")) acta.setFotoLicencia(Utilities.ReadBytes(sImagen));

		sImagen = mCurrenActaSettings.getString(CURRENT_ACTA_FOTO_DOCUMENTO,RESOURCE_SIN_FOTO);
		sImagenes = sImagenes + (sImagen.isEmpty()==false?"|" + sImagen :"" );
		if (!sImagen.equals("")) acta.setFotoLicencia(Utilities.ReadBytes(sImagen));

		sImagen = mCurrenActaSettings.getString(CURRENT_ACTA_FOTO_OTRO,RESOURCE_SIN_FOTO);
		sImagenes = sImagenes + (sImagen.isEmpty()==false?"|" + sImagen:"" );
		if (!sImagen.equals(""))  acta.setFotoLicencia(Utilities.ReadBytes(sImagen));

		acta.setFotos(sImagenes);
		return acta;
	}
	private void doImpresion(ActaConstatacion pActa)
	{
		//TODO
		//showDialog();
		Bundle passData = new Bundle();
		passData.putSerializable("ActaConstatacion",pActa);
		showDialog(PRINT_ACTA_DIALOG_ID, passData);
	}
	private void doCancelarActa()
	{

		finish();
	}
	/*
	 * pLugarDemografico PAIS PROVINCIA DEPARTAMENTO LOCALIDAD
	 * pIdentificacion LICENCIA o DOCUMENTO
	 */
    private void ShowEditText(String pIdentificacion ,String pLugarDemografico,Boolean pMostrar)
    {
    	int iVisible = pMostrar==true? View.VISIBLE:View.GONE;

    	if (pIdentificacion == MARCA )
    	{
    		String sHint = "Escriba aqui el nombre la marca";
    		//editText_Marca.setText("");
 		    editText_Marca.setHint(sHint );
 		    editText_Marca.setVisibility(iVisible);
    	}

    	if (pIdentificacion == COLOR )
    	{
    		String sHint = "Escriba aqui el nombre del Color";
    		//editText_Color.setText("");
 		    editText_Color.setHint(sHint );
 		    editText_Color.setVisibility(iVisible);
    	}

    	if (pIdentificacion == LICENCIA )
    	{
    		String sHint = "Escriba aqui el nombre";
    		String sArticulo = " del ";
    		String sLastArticulo = " de la " + LICENCIA;
    		switch (pLugarDemografico)
    		{
			case PAIS:
				sArticulo = " del ";
				//editText_PaisLicencia.setText("");
     		    editText_PaisLicencia.setHint(sHint + sArticulo + pLugarDemografico + sLastArticulo);
     		    editText_PaisLicencia.setVisibility(iVisible);
				break;

			case PROVINCIA:
				sArticulo = " de la ";
     		   //editText_ProvinciaLicencia.setText("");
     		   editText_ProvinciaLicencia.setHint(sHint + sArticulo + pLugarDemografico + sLastArticulo); //"Hint to be displayed"
     		   editText_ProvinciaLicencia.setVisibility(iVisible);

				break;
			case DEPARTAMENTO:
				sArticulo = " del ";
     		   //editText_DepartamentoLicencia.setText("");
     		   editText_DepartamentoLicencia.setHint(sHint + sArticulo + pLugarDemografico + sLastArticulo); //"Hint to be displayed"
     		   editText_DepartamentoLicencia.setVisibility(iVisible);

				break;
			case LOCALIDAD:
			   sArticulo = " de la ";
			  // editText_LocalidadLicencia.setText("");
     		   editText_LocalidadLicencia.setHint(sHint + sArticulo + pLugarDemografico + sLastArticulo); //"Hint to be displayed"
     		   editText_LocalidadLicencia.setVisibility(iVisible);
				break;

			default:
				break;
			}

    	}
    	if (pIdentificacion== DOCUMENTO)
    	{
    		String sHint = "Escriba aqui el nombre";
    		String sArticulo = " del ";
    		String sLastArticulo = " del " + DOCUMENTO;
    		switch (pLugarDemografico)
    		{
			case PAIS:
				sArticulo = " del ";
				//editText_PaisDocumento.setText("");
     		    editText_PaisDocumento.setHint(sHint + sArticulo + pLugarDemografico + sLastArticulo);
     		    editText_PaisDocumento.setVisibility(iVisible);
				break;

			case PROVINCIA:
				sArticulo = " de la ";
     		  // editText_ProvinciaDocumento.setText("");
     		   editText_ProvinciaDocumento.setHint(sHint + sArticulo + pLugarDemografico + sLastArticulo); //"Hint to be displayed"
     		   editText_ProvinciaDocumento.setVisibility(iVisible);

				break;
			case DEPARTAMENTO:
				sArticulo = " del ";
     		   //editText_DepartamentoDocumento.setText("");
     		   editText_DepartamentoDocumento.setHint(sHint + sArticulo + pLugarDemografico + sLastArticulo); //"Hint to be displayed"
     		   editText_DepartamentoDocumento.setVisibility(iVisible);

				break;
			case LOCALIDAD:
			   sArticulo = " de la ";
			   //editText_LocalidadDocumento.setText("");
     		   editText_LocalidadDocumento.setHint(sHint + sArticulo + pLugarDemografico + sLastArticulo); //"Hint to be displayed"
     		   editText_LocalidadDocumento.setVisibility(iVisible);
				break;

			default:
				break;
			}
    	}

    	if (pIdentificacion== DOCUMENTO_TITULAR)
    	{
    		String sHint = "Escriba aqui el nombre";
    		String sArticulo = " del ";
    		String sLastArticulo = " del " + DOCUMENTO;
    		switch (pLugarDemografico)
    		{
			case PAIS:
				sArticulo = " del ";
				//editText_PaisDocumento.setText("");
     		    editText_PaisDocumentoTitular.setHint(sHint + sArticulo + pLugarDemografico + sLastArticulo);
     		    editText_PaisDocumentoTitular.setVisibility(iVisible);
				break;

			case PROVINCIA:
				sArticulo = " de la ";
     		  // editText_ProvinciaDocumento.setText("");
     		   editText_ProvinciaDocumentoTitular.setHint(sHint + sArticulo + pLugarDemografico + sLastArticulo); //"Hint to be displayed"
     		   editText_ProvinciaDocumentoTitular.setVisibility(iVisible);

				break;
			case DEPARTAMENTO:
				sArticulo = " del ";
     		   //editText_DepartamentoDocumento.setText("");
     		   editText_DepartamentoDocumentoTitular.setHint(sHint + sArticulo + pLugarDemografico + sLastArticulo); //"Hint to be displayed"
     		   editText_DepartamentoDocumentoTitular.setVisibility(iVisible);

				break;
			case LOCALIDAD:
			   sArticulo = " de la ";
			   //editText_LocalidadDocumento.setText("");
     		   editText_LocalidadDocumentoTitular.setHint(sHint + sArticulo + pLugarDemografico + sLastArticulo); //"Hint to be displayed"
     		   editText_LocalidadDocumentoTitular.setVisibility(iVisible);
				break;

			default:
				break;
			}
    	}
    	if (pIdentificacion== DOCUMENTO_TESTIGO)
    	{
    		String sHint = "Escriba aqui el nombre";
    		String sArticulo = " del ";
    		String sLastArticulo = " del " + DOCUMENTO;
    		switch (pLugarDemografico)
    		{
			case PAIS:
				sArticulo = " del ";
				//editText_PaisDocumento.setText("");
     		    editText_PaisDocumentoTestigo.setHint(sHint + sArticulo + pLugarDemografico + sLastArticulo);
     		    editText_PaisDocumentoTestigo.setVisibility(iVisible);
				break;

			case PROVINCIA:
				sArticulo = " de la ";
     		  // editText_ProvinciaDocumento.setText("");
     		   editText_ProvinciaDocumentoTestigo.setHint(sHint + sArticulo + pLugarDemografico + sLastArticulo); //"Hint to be displayed"
     		   editText_ProvinciaDocumentoTestigo.setVisibility(iVisible);

				break;
			case DEPARTAMENTO:
				sArticulo = " del ";
     		   //editText_DepartamentoDocumento.setText("");
     		   editText_DepartamentoDocumentoTestigo.setHint(sHint + sArticulo + pLugarDemografico + sLastArticulo); //"Hint to be displayed"
     		   editText_DepartamentoDocumentoTestigo.setVisibility(iVisible);

				break;
			case LOCALIDAD:
			   sArticulo = " de la ";
			   //editText_LocalidadDocumento.setText("");
     		   editText_LocalidadDocumentoTestigo.setHint(sHint + sArticulo + pLugarDemografico + sLastArticulo); //"Hint to be displayed"
     		   editText_LocalidadDocumentoTestigo.setVisibility(iVisible);
				break;

			default:
				break;
			}
    	}

    }
	private void HabilitarListeners(String... pSpinnerName )
	{

		for (int i = 0; i < pSpinnerName.length; i++) {
			switch (pSpinnerName[i])
			{
			case SPINNER_PAIS_LICENCIA:
				initPaisesListeners();
			break;
			case SPINNER_PAIS_DOCUMENTO:
				initPaisesDListeners();
			break;
			case SPINNER_PAIS_DOCUMENTO_TITULAR:
				initPaisesDTitularListeners();;
			break;
			case SPINNER_PAIS_DOCUMENTO_TESTIGO:
				initPaisesDTestigoListeners();;
			break;

			case SPINNER_PROVINCIA_LICENCIA:
				initProvinciasListeners();
				break;
			case SPINNER_PROVINCIA_DOCUMENTO:
				initProvinciasDListeners();
				break;
			case SPINNER_PROVINCIA_DOCUMENTO_TITULAR:
				initProvinciasDTitularListeners();
				break;
			case SPINNER_PROVINCIA_DOCUMENTO_TESTIGO:
				initProvinciasDTestigoListeners();
				break;

			case SPINNER_DEPARTAMENTO_LICENCIA:
				//initDepartamentosListeners();
				break;
			case SPINNER_DEPARTAMENTO_DOCUMENTO:
				initDepartamentosDListeners();
				break;
			case SPINNER_DEPARTAMENTO_DOCUMENTO_TITULAR:
				initDepartamentosDTitularListeners();
				break;
			case SPINNER_DEPARTAMENTO_DOCUMENTO_TESTIGO:
				initDepartamentosDTestigoListeners();
				break;

			case SPINNER_LOCALIDAD_LICENCIA:
				//initLocalidadesListeners();
				break;
			case SPINNER_LOCALIDAD_DOCUMENTO:
				initLocalidadesDListeners();
				break;
			case SPINNER_LOCALIDAD_DOCUMENTO_TITULAR:
				initLocalidadesDTitularListeners();
				break;
			case SPINNER_LOCALIDAD_DOCUMENTO_TESTIGO:
				initLocalidadesDTestigoListeners();
				break;

			case SPINNER_TIPO_RUTA:
				initTipoRutaListeners();
				break;

			case SPINNER_RUTA:
				initRutaListeners();
				break;

			case EDIT_TEXT_KM:
				initEditTextKM();
			default:
				break;
			}
		}
	}
	private <T extends ISeleccionable<String>> void SetSpinnerSelectionByValue2(GenericSpinAdapter<T> pMyGenericAdpater,String pSpinnerName,String pKeyValue)
	{
		int cantItems = pMyGenericAdpater.getCount();
		for (int i = 0; i<cantItems;i++)
		{   String idKey = pMyGenericAdpater.getItem(i).getItemName();
			if(pKeyValue !=null && idKey.toUpperCase().equals(pKeyValue.toUpperCase()))
			{
				SetFirstSelection(i, pSpinnerName);
				break;
			}
		}


	}

	private <T extends ISeleccionable<String>> int SetSpinnerSelectionByValue(GenericSpinAdapter<T> pMyGenericAdpater,String pSpinnerName,String pKeyValue)
	{
		int cantItems = pMyGenericAdpater.getCount();
		int retornar = cantItems;
		for (int i = 0; i<cantItems;i++)
		{   String idKey = pMyGenericAdpater.getItem(i).getItemId();
			if(pKeyValue !=null && idKey.toUpperCase().equals(pKeyValue.toUpperCase()))
			{
				retornar = i;
				SetFirstSelection(i, pSpinnerName);
				return i;
			}
		}
		return retornar;


	}
	private void SetFirstSelection(int pPosition,String... pSpinnerName )
	{

		for (int i = 0; i < pSpinnerName.length; i++) {
			switch (pSpinnerName[i]) {
			case SPINNER_PAIS_LICENCIA:
				myPaisSpinner.setSelection(pPosition);
				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PAIS_LICENCIA, pPosition).commit();
			break;
			case SPINNER_PAIS_DOCUMENTO:
				myPaisDSpinner.setSelection(pPosition,true);
				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PAIS_DOCUMENTO, pPosition).commit();
			break;
			case SPINNER_PAIS_DOCUMENTO_TITULAR:
				myPaisDTitularSpinner.setSelection(pPosition);
				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PAIS_DOCUMENTO_TITULAR, pPosition).commit();
			break;
			case SPINNER_PAIS_DOCUMENTO_TESTIGO:
				myPaisDTestigoSpinner.setSelection(pPosition);
				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PAIS_DOCUMENTO_TESTIGO, pPosition).commit();
			break;


			case SPINNER_PROVINCIA_LICENCIA:
				myProvinciaSpinner.setSelection(pPosition,true);
				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PROVINCIA_LICENCIA, pPosition).commit();
				break;
			case SPINNER_PROVINCIA_DOCUMENTO:
				myProvinciaDSpinner.setSelection(pPosition,true);
				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PROVINCIA_DOCUMENTO, pPosition).commit();
				break;
			case SPINNER_PROVINCIA_DOCUMENTO_TITULAR:
				myProvinciaDTitularSpinner.setSelection(pPosition,true);
				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PROVINCIA_DOCUMENTO_TITULAR, pPosition).commit();
				break;
			case SPINNER_PROVINCIA_DOCUMENTO_TESTIGO:
				myProvinciaDTestigoSpinner.setSelection(pPosition,true);
				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PROVINCIA_DOCUMENTO_TESTIGO, pPosition).commit();
				break;

			case SPINNER_DEPARTAMENTO_LICENCIA:
				myDepartamentoSpinner.setSelection(pPosition);
				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_DEPARTAMENTO_LICENCIA, pPosition).commit();
				break;
			case SPINNER_DEPARTAMENTO_DOCUMENTO:
				myDepartamentoDSpinner.setSelection(pPosition,true);
				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_DEPARTAMENTO_DOCUMENTO, pPosition).commit();
				break;
			case SPINNER_DEPARTAMENTO_DOCUMENTO_TITULAR:
				myDepartamentoDTitularSpinner.setSelection(pPosition);
				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_DEPARTAMENTO_DOCUMENTO_TITULAR, pPosition).commit();
				break;
			case SPINNER_DEPARTAMENTO_DOCUMENTO_TESTIGO:
				myDepartamentoDTestigoSpinner.setSelection(pPosition);
				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_DEPARTAMENTO_DOCUMENTO_TESTIGO, pPosition).commit();
				break;

			case SPINNER_LOCALIDAD_LICENCIA:
				myLocalidadSpinner.setSelection(pPosition);
				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_LOCALIDAD_LICENCIA, pPosition).commit();
				break;
			case SPINNER_LOCALIDAD_DOCUMENTO:
				myLocalidadDSpinner.setSelection(pPosition,true);
				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_LOCALIDAD_DOCUMENTO, pPosition).commit();
				break;
			case SPINNER_LOCALIDAD_DOCUMENTO_TITULAR:
				myLocalidadDTitularSpinner.setSelection(pPosition);
				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_LOCALIDAD_DOCUMENTO_TITULAR, pPosition).commit();
				break;
			case SPINNER_LOCALIDAD_DOCUMENTO_TESTIGO:
				myLocalidadDTestigoSpinner.setSelection(pPosition);
				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_LOCALIDAD_DOCUMENTO_TESTIGO, pPosition).commit();
				break;

			case SPINNER_TIPO_RUTA:
				myTipoRutaSpinner.setSelection(pPosition);
				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_TIPO_RUTA, pPosition).commit();
				break;
			case SPINNER_MARCA_VEHICULO:
				myMarcaSpinner.setSelection(pPosition);
				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_MARCA, pPosition).commit();
				break;
			case SPINNER_COLOR_VEHICULO:
				myColorSpinner.setSelection(pPosition);
				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_COLOR, pPosition).commit();
				break;

			case SPINNER_RUTA:
				myRutaSpinner.setSelection(pPosition);
				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_RUTA, pPosition).commit();
				break;

			case SPINNER_TIPO_DOCUMENTO:
				myTipoDocumentoSpinner.setSelection(pPosition);
				//mCurrenActaSettings.edit().putInt(CURRENT_ACTA_, pPosition).commit();
				break;
			case SPINNER_TIPO_DOCUMENTO_TITULAR:
				myTipoDocumentoTitularSpinner.setSelection(pPosition);
				//mCurrenActaSettings.edit().putInt(CURRENT_ACTA_, pPosition).commit();
				break;
			case SPINNER_TIPO_DOCUMENTO_TESTIGO:
				myTipoDocumentoTestigoSpinner.setSelection(pPosition);
				//mCurrenActaSettings.edit().putInt(CURRENT_ACTA_, pPosition).commit();
				break;
			case SPINNER_TIPO_GENERO:
				//myGeneroSpinner.setSelection(pPosition);
				break;
			case SPINNER_TIPO_GENERO_TITULAR:
				//myGeneroTitularSpinner.setSelection(pPosition);
				break;
			case SPINNER_TIPO_GENERO_TESTIGO:
				//myGeneroTestigoSpinner.setSelection(pPosition);
				break;
			default:
				break;
			}
		}


	}
	private void DeshabilitarListeners(String... pSpinnerName )
	{

		for (int i = 0; i < pSpinnerName.length; i++) {
			switch (pSpinnerName[i]) {
			/* CASOS DEMOGRAFICOS */
			case SPINNER_PAIS_LICENCIA:
				myPaisSpinner.setOnItemSelectedListener(null);
			break;
			case SPINNER_PAIS_DOCUMENTO:
				myPaisDSpinner.setOnItemSelectedListener(null);
			break;
			case SPINNER_PAIS_DOCUMENTO_TITULAR:
				myPaisDTitularSpinner.setOnItemSelectedListener(null);
			break;
			case SPINNER_PAIS_DOCUMENTO_TESTIGO:
				myPaisDTestigoSpinner.setOnItemSelectedListener(null);
			break;

			case SPINNER_PROVINCIA_LICENCIA:
				myProvinciaSpinner.setOnItemSelectedListener(null);
				break;
			case SPINNER_PROVINCIA_DOCUMENTO:
				myProvinciaDSpinner.setOnItemSelectedListener(null);
				break;
			case SPINNER_PROVINCIA_DOCUMENTO_TITULAR:
				myProvinciaDTitularSpinner.setOnItemSelectedListener(null);
				break;
			case SPINNER_PROVINCIA_DOCUMENTO_TESTIGO:
				myProvinciaDTestigoSpinner.setOnItemSelectedListener(null);
				break;

			case SPINNER_DEPARTAMENTO_LICENCIA:
				myDepartamentoSpinner.setOnItemSelectedListener(null);
				break;
			case SPINNER_DEPARTAMENTO_DOCUMENTO:
				myDepartamentoDSpinner.setOnItemSelectedListener(null);
				break;
			case SPINNER_DEPARTAMENTO_DOCUMENTO_TITULAR:
				myDepartamentoDTitularSpinner.setOnItemSelectedListener(null);
				break;
			case SPINNER_DEPARTAMENTO_DOCUMENTO_TESTIGO:
				myDepartamentoDTestigoSpinner.setOnItemSelectedListener(null);
				break;

			case SPINNER_LOCALIDAD_LICENCIA:
				myLocalidadSpinner.setOnItemSelectedListener(null);
				break;
			case SPINNER_LOCALIDAD_DOCUMENTO:
				myLocalidadDSpinner.setOnItemSelectedListener(null);
				break;
			case SPINNER_LOCALIDAD_DOCUMENTO_TITULAR:
				myLocalidadDTitularSpinner.setOnItemSelectedListener(null);
				break;
			case SPINNER_LOCALIDAD_DOCUMENTO_TESTIGO:
				myLocalidadDTestigoSpinner.setOnItemSelectedListener(null);
				break;

			/* CASOS VARIOS */
			case SPINNER_TIPO_RUTA:
				myTipoRutaSpinner.setOnItemSelectedListener(null);
				break;
			case SPINNER_RUTA:
				myRutaSpinner.setOnItemSelectedListener(null);
				break;
			case EDIT_TEXT_KM:
				editText_LocalidadDocumento.setOnFocusChangeListener(null);
			break;
			default:
				break;
			}
		}


	}
	@Override
	protected void onDestroy() {

		 //HomeKeyLocker no funciona
//        mHomeKeyLocker.unlock();
//        mHomeKeyLocker = null;


		Tools.Log(Log.ERROR, TAG, "onDestroy");

		Log.d(DEBUG_TAG, "SHARED PREFERENCES");
		Log.d(DEBUG_TAG,
				"Nickname is: "
						+ mGameSettings.getString(GAME_PREFERENCES_NICKNAME,
								"Not set"));
		Log.d(DEBUG_TAG,
				"Email is: "
						+ mGameSettings.getString(GAME_PREFERENCES_EMAIL,
								"Not set"));
		Log.d(DEBUG_TAG,
				"Gender (M=1, F=2, U=0) is: "
						+ mGameSettings.getInt(GAME_PREFERENCES_GENDER, 0));
		// We are not saving the password yet
		Log.d(DEBUG_TAG,
				"Password is: "
						+ mGameSettings.getString(GAME_PREFERENCES_PASSWORD,
								"Not set"));
		// We are not saving the date of birth yet
		Log.d(DEBUG_TAG,
				"DOB is: "
						+ DateFormat.format("MMMM dd, yyyy",
								mGameSettings.getLong(GAME_PREFERENCES_DOB, 0)));

		Log.d(DEBUG_TAG,
				"Avatar is: "
						+ mGameSettings.getString(GAME_PREFERENCES_AVATAR,
								"Not set"));

		Log.d(DEBUG_TAG,
				"Fav Place Name is: "
						+ mGameSettings.getString(
								GAME_PREFERENCES_FAV_PLACE_NAME, "Not set"));
		Log.d(DEBUG_TAG,
				"Fav Place GPS Lat is: "
						+ mGameSettings.getFloat(
								GAME_PREFERENCES_FAV_PLACE_LAT, 0));
		Log.d(DEBUG_TAG,
				"Fav Place GPS Lon is: "
						+ mGameSettings.getFloat(
								GAME_PREFERENCES_FAV_PLACE_LONG, 0));

		super.onDestroy();
	}

	@Override
	protected void onStop() {
		Tools.Log(Log.ERROR, TAG, "onStop");
		super.onStop();
	}

	@Override
	public void onTrimMemory(int level)
	{
		Tools.Log(Log.ERROR, TAG, "onTrimMemory Level " + level);
		super.onTrimMemory(level);
	}
	@Override
	public void onLowMemory()
	{
		Tools.Log(Log.ERROR, TAG, "onLowMemory");
		super.onLowMemory();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		Tools.Log(Log.ERROR, TAG, "onConfigurationChanged");
		super.onConfigurationChanged(newConfig);

	}

	@Override
	protected void onPause() {
		Tools.Log(Log.ERROR, TAG, "onPause");
		 killToast();
		super.onPause();
		this.IsRestoringLastData= false;

		if (loadViewTask!=null)
			loadViewTask.cancel(true);

		/*
		onPause
		Save your persistent data in onPause rather than in onSaveInstanceState.
		-> onPause is always called
		-> onPause is always called when the activity is placed in the background or is about to be destroyed.
		 */




       /* if (friendRequest != null) {
            friendRequest.cancel(true);
        }

		EditText nicknameText = (EditText) findViewById(R.id.EditText_Nickname);
		EditText emailText = (EditText) findViewById(R.id.EditText_Email);

		String strNickname = nicknameText.getText().toString();
		String strEmail = emailText.getText().toString();

		Editor editor = mGameSettings.edit();
		editor.putString(GAME_PREFERENCES_NICKNAME, strNickname);
		editor.putString(GAME_PREFERENCES_EMAIL, strEmail);

		editor.commit();

		Intent uploadService = new Intent(getApplicationContext(),
				UploaderService.class);
		startService(uploadService);
		*/
	}
    @Override
    public void onResume()
    {



    	Tools.Log(Log.ERROR, TAG, "onResume");
       super.onResume();

		String[] sListeners = {
				   SPINNER_PAIS_LICENCIA,SPINNER_PAIS_DOCUMENTO,SPINNER_PAIS_DOCUMENTO_TITULAR,SPINNER_PAIS_DOCUMENTO_TESTIGO
				  ,SPINNER_PROVINCIA_LICENCIA,SPINNER_PROVINCIA_DOCUMENTO,SPINNER_PROVINCIA_DOCUMENTO_TITULAR,SPINNER_PROVINCIA_DOCUMENTO_TESTIGO
				  ,SPINNER_DEPARTAMENTO_LICENCIA,SPINNER_DEPARTAMENTO_DOCUMENTO, SPINNER_DEPARTAMENTO_DOCUMENTO_TITULAR, SPINNER_DEPARTAMENTO_DOCUMENTO_TESTIGO
				  ,SPINNER_LOCALIDAD_LICENCIA,SPINNER_LOCALIDAD_DOCUMENTO,SPINNER_LOCALIDAD_DOCUMENTO_TITULAR,SPINNER_LOCALIDAD_DOCUMENTO_TESTIGO
				  ,SPINNER_TIPO_RUTA,SPINNER_RUTA
				  ,EDIT_TEXT_KM
				  };

		DeshabilitarListeners(sListeners);
    	/*
    	onResume
    	This is where you can get any persistent data out of the Shared Preference file and use it to restore the activity.
    	-> onResume is always called
    	-> onResume is called at the start of every activity�s active lifetime.
    	*/

    		this.IsRestoringLastData = true;
    		//this.IsRecreating = true;
    		String sfechaVencimientoLicencia = mCurrenActaSettings.getString(CURRENT_ACTA_FECHA_VENCIMIENTO_LICENCIA,"");
    		String sfechaNacimiento = mCurrenActaSettings.getString(CURRENT_ACTA_FECHA_NACIMIENTO,"");
    		int isharedPais = mCurrenActaSettings.getInt(CURRENT_ACTA_PAIS_LICENCIA, -2);
    		int isharedProvincia = mCurrenActaSettings.getInt(CURRENT_ACTA_PROVINCIA_LICENCIA, -2);
    		//int isharedDepartamento = mCurrenActaSettings.getInt(CURRENT_ACTA_DEPARTAMENTO_LICENCIA, -2);
    		//int isharedLocalidad= mCurrenActaSettings.getInt(CURRENT_ACTA_LOCALIDAD_LICENCIA, -2);

    		int isharedPaisD = mCurrenActaSettings.getInt(CURRENT_ACTA_PAIS_DOCUMENTO, -2);
    		int isharedProvinciaD = mCurrenActaSettings.getInt(CURRENT_ACTA_PROVINCIA_DOCUMENTO, -2);
    		int isharedDepartamentoD = mCurrenActaSettings.getInt(CURRENT_ACTA_DEPARTAMENTO_DOCUMENTO, -2);
    		int isharedLocalidadD= mCurrenActaSettings.getInt(CURRENT_ACTA_LOCALIDAD_DOCUMENTO, -2);

    		int isharedPaisDTitular = mCurrenActaSettings.getInt(CURRENT_ACTA_PAIS_DOCUMENTO_TITULAR, -2);
    		int isharedProvinciaDTitular = mCurrenActaSettings.getInt(CURRENT_ACTA_PROVINCIA_DOCUMENTO_TITULAR, -2);
    		int isharedDepartamentoDTitular = mCurrenActaSettings.getInt(CURRENT_ACTA_DEPARTAMENTO_DOCUMENTO_TITULAR, -2);
    		int isharedLocalidadDTitular = mCurrenActaSettings.getInt(CURRENT_ACTA_LOCALIDAD_DOCUMENTO_TITULAR, -2);

    		int isharedPaisDTestigo= mCurrenActaSettings.getInt(CURRENT_ACTA_PAIS_DOCUMENTO_TESTIGO, -2);
    		int isharedProvinciaDTestigo= mCurrenActaSettings.getInt(CURRENT_ACTA_PROVINCIA_DOCUMENTO_TESTIGO, -2);
    		int isharedDepartamentoDTestigo= mCurrenActaSettings.getInt(CURRENT_ACTA_DEPARTAMENTO_DOCUMENTO_TESTIGO, -2);
    		int isharedLocalidadDTestigo= mCurrenActaSettings.getInt(CURRENT_ACTA_LOCALIDAD_DOCUMENTO_TESTIGO, -2);

    		int isharedTipoRuta = mCurrenActaSettings.getInt(CURRENT_ACTA_TIPO_RUTA, -2);


    		int isharedRuta = mCurrenActaSettings.getInt(CURRENT_ACTA_RUTA, -2);

    		int isharedEntidad = mCurrenActaSettings.getInt(CURRENT_ACTA_ENTIDAD, -2);


    		Integer isharedInfraccion1 = mCurrenActaSettings.getInt(CURRENT_ACTA_INFRACCION1, -2);
    		Integer isharedInfraccion2 = mCurrenActaSettings.getInt(CURRENT_ACTA_INFRACCION2, -2);
    		Integer isharedInfraccion3 = mCurrenActaSettings.getInt(CURRENT_ACTA_INFRACCION3, -2);
    		Integer isharedInfraccion4 = mCurrenActaSettings.getInt(CURRENT_ACTA_INFRACCION4, -2);
    		Integer isharedInfraccion5 = mCurrenActaSettings.getInt(CURRENT_ACTA_INFRACCION5, -2);
    		Integer isharedInfraccion6 = mCurrenActaSettings.getInt(CURRENT_ACTA_INFRACCION6, -2);


    		int isharedMarca = mCurrenActaSettings.getInt(CURRENT_ACTA_MARCA, -2);
    		int isharedColor = mCurrenActaSettings.getInt(CURRENT_ACTA_COLOR, -2);
    		/*---------------------------------------------------------*/
    		if(!sfechaVencimientoLicencia.equals(""))
    		{

    			editText_FVL_Info.setText(sfechaVencimientoLicencia);

    		}
    		if(!sfechaNacimiento.equals(""))
    		{

    			textView_FN_Info.setText(sfechaNacimiento);

    		}
    		if (!isharedInfraccion1.equals(-2))
    		{
    			InfraccionNomencladaDao infDao = new InfraccionNomencladaDao();
    		 	InfraccionNomenclada item =  infDao.getItem(isharedInfraccion1.toString());
    			if (item !=null && !item.getId().equals(""))
    			{
    				setInfraccionValues(SEARCH_INFRACCION1,item);
    			}
    		}

    		/*---------------------------------------------------------*/

    		if (!isharedInfraccion2.equals(-2))
    		{
    			InfraccionNomencladaDao infDao = new InfraccionNomencladaDao();
    		 	InfraccionNomenclada item =  infDao.getItem(isharedInfraccion2.toString());
    			if (item !=null && !item.getId().equals(""))
    			{
    				setInfraccionValues(SEARCH_INFRACCION2,item);
    			}
    		}

       		if (!isharedInfraccion3.equals(-2))
    		{
    			InfraccionNomencladaDao infDao = new InfraccionNomencladaDao();
    		 	InfraccionNomenclada item =  infDao.getItem(isharedInfraccion3.toString());
    			if (item !=null && !item.getId().equals(""))
    			{
    				setInfraccionValues(SEARCH_INFRACCION3,item);
    			}
    		}

       		if (!isharedInfraccion4.equals(-2))
    		{
    			InfraccionNomencladaDao infDao = new InfraccionNomencladaDao();
    		 	InfraccionNomenclada item =  infDao.getItem(isharedInfraccion4.toString());
    			if (item !=null && !item.getId().equals(""))
    			{
    				setInfraccionValues(SEARCH_INFRACCION4,item);
    			}
    		}
       		if (!isharedInfraccion5.equals(-2))
    		{
    			InfraccionNomencladaDao infDao = new InfraccionNomencladaDao();
    		 	InfraccionNomenclada item =  infDao.getItem(isharedInfraccion5.toString());
    			if (item !=null && !item.getId().equals(""))
    			{
    				setInfraccionValues(SEARCH_INFRACCION5,item);
    			}
    		}

       		if (!isharedInfraccion6.equals(-2))
    		{
    			InfraccionNomencladaDao infDao = new InfraccionNomencladaDao();
    		 	InfraccionNomenclada item =  infDao.getItem(isharedInfraccion6.toString());
    			if (item !=null && !item.getId().equals(""))
    			{
    				setInfraccionValues(SEARCH_INFRACCION6,item);
    			}
    		}

       		/*---------------------------------------------------------*/

    		textView_DescripcionEntidad.setVisibility(View.GONE);
    		if (isharedEntidad>= 0)
    		{
    			myEntidadSpinner= (Spinner) findViewById(R.id.Spinner_Juzgado);
    			myEntidadSpinner.setSelection(isharedEntidad);

    			//if(isharedEntidad!=0) recordar que para las entidades se puede seleccionar la posicion 0
    			{
    				textView_DescripcionEntidad.setVisibility(View.VISIBLE);
    			    Entidad entidad	= (Entidad) myEntidadSpinner.getItemAtPosition(isharedEntidad);
    				fillDescripcionEntidadTextView(entidad.getId().toString());
    			}
    		}
    		else
    		{
    			myEntidadSpinner.setSelection(myEntidadSpinner.getCount());
    		}

    		/*---------------------------------------------------------*/

    		ShowEditText(LICENCIA, PAIS, false);
    		if (isharedPais >= 0)
    		{
    			myPaisSpinner = (Spinner) findViewById(R.id.Spinner_PaisLicencia);
    			myPaisSpinner.setSelection(isharedPais);

    			if(isharedPais==0) ShowEditText(LICENCIA, PAIS, true);
    		}
    		else
    		{  int iPosition = myPaisSpinner.getCount();
    		   if(iPosition>0 && this.IsRecreating ==true)
    			   {
    			   	myPaisSpinner.setSelection(0);
    			   	ShowEditText(LICENCIA, PAIS, true);
    			   }
    		   else
    			   myPaisSpinner.setSelection(iPosition);
    		}

    		/*---------------------------------------------------------*/

    		ShowEditText(DOCUMENTO, PAIS, false);
    		if (isharedPaisD >=0)
    		{
    			myPaisDSpinner = (Spinner) findViewById(R.id.Spinner_PaisDocumento);
    			myPaisDSpinner.setSelection(isharedPaisD);
    			if(isharedPaisD==0) ShowEditText(DOCUMENTO, PAIS, true);
    		}
   		 	else
    		{
   		 	   int iPosition = myPaisDSpinner.getCount();
    		   if(iPosition>0 && this.IsRecreating ==true)
    			   {
    			   	myPaisDSpinner.setSelection(0);
    			   	ShowEditText(DOCUMENTO, PAIS, true);
    			   }
    		   else
    			   myPaisDSpinner.setSelection(iPosition);
    		}

    		ShowEditText(DOCUMENTO_TITULAR, PAIS, false);
    		if (isharedPaisDTitular >=0)
    		{
    			myPaisDTitularSpinner = (Spinner) findViewById(R.id.Spinner_PaisDocumentoTitular);
    			myPaisDTitularSpinner.setSelection(isharedPaisDTitular);
    			if(isharedPaisDTitular==0) ShowEditText(DOCUMENTO_TITULAR, PAIS, true);
    		}
   		 	else
    		{
   		 	   int iPosition = myPaisDTitularSpinner.getCount();
    		   if(iPosition>0 && this.IsRecreating ==true)
    			   {
    			   	myPaisDTitularSpinner.setSelection(0);
    			   	ShowEditText(DOCUMENTO_TITULAR, PAIS, true);
    			   }
    		   else
    			   myPaisDTitularSpinner.setSelection(iPosition);
    		}

    		/* pais del testigo */
    		ShowEditText(DOCUMENTO_TESTIGO, PAIS, false);
    		if (isharedPaisDTestigo>=0)
    		{
    			myPaisDTestigoSpinner = (Spinner) findViewById(R.id.Spinner_PaisDocumentoTestigo);
    			myPaisDTestigoSpinner.setSelection(isharedPaisDTestigo);
    			if(isharedPaisDTestigo==0) ShowEditText(DOCUMENTO_TESTIGO, PAIS, true);
    		}
   		 	else
    		{
   		 	   int iPosition = myPaisDTestigoSpinner.getCount();
    		   if(iPosition>0 && this.IsRecreating ==true)
    			   {
    			   	myPaisDTestigoSpinner.setSelection(0);
    			   	ShowEditText(DOCUMENTO_TESTIGO, PAIS, true);
    			   }
    		   else
    			   myPaisDTestigoSpinner.setSelection(iPosition);
    		}


    		/*---------------------------------------------------------*/

    		ShowEditText(LICENCIA, PROVINCIA, false);
    		if (isharedProvincia >= 0)
    		{
    			myProvinciaSpinner = (Spinner) findViewById(R.id.Spinner_ProvinciaLicencia);
    			myProvinciaSpinner.setSelection(isharedProvincia);
    			if(isharedProvincia==0)	ShowEditText(LICENCIA, PROVINCIA, true);
    		}
    		 else
    		{
    			 int iPosition = myProvinciaSpinner.getCount();
      		   if(iPosition>0 && this.IsRecreating ==true)
      			   {
      			   	myProvinciaSpinner.setSelection(0);
      				ShowEditText(LICENCIA, PROVINCIA, true);
      			   }
      		   else
      			   myProvinciaSpinner.setSelection(iPosition);
    		}

    		/*---------------------------------------------------------*/
    		ShowEditText(DOCUMENTO, PROVINCIA, false);
    		if (isharedProvinciaD >=0)
    		{
    			myProvinciaDSpinner = (Spinner) findViewById(R.id.Spinner_ProvinciaDocumento);
    			myProvinciaDSpinner.setSelection(isharedProvinciaD);
    			if(isharedProvinciaD==0) ShowEditText(DOCUMENTO, PROVINCIA, true);
    		}
    		else
    		{
    		   int iPosition = myProvinciaDSpinner.getCount();
      		   if(iPosition>0 && this.IsRecreating ==true)
      			   {
      			   	myProvinciaDSpinner.setSelection(0);
      			    ShowEditText(DOCUMENTO, PROVINCIA, true);
      			   }
      		   else
      			   myProvinciaDSpinner.setSelection(iPosition);
    		}

    		/*---------------------------------------------------------*/
    		ShowEditText(DOCUMENTO_TITULAR, PROVINCIA, false);
    		if (isharedProvinciaDTitular >=0)
    		{
    			myProvinciaDTitularSpinner = (Spinner) findViewById(R.id.Spinner_ProvinciaDocumentoTitular);
    			myProvinciaDTitularSpinner.setSelection(isharedProvinciaDTitular);
    			if(isharedProvinciaDTitular==0) ShowEditText(DOCUMENTO_TITULAR, PROVINCIA, true);
    		}
    		else
    		{
    		   int iPosition = myProvinciaDTitularSpinner.getCount();
      		   if(iPosition>0 && this.IsRecreating ==true)
      			   {
      			   	myProvinciaDTitularSpinner.setSelection(0);
      			    ShowEditText(DOCUMENTO_TITULAR, PROVINCIA, true);
      			   }
      		   else
      			   myProvinciaDTitularSpinner.setSelection(iPosition);
    		}
    		/* provincia del testigo*/
    		ShowEditText(DOCUMENTO_TESTIGO, PROVINCIA, false);
    		if (isharedProvinciaDTestigo>=0)
    		{
    			myProvinciaDTestigoSpinner = (Spinner) findViewById(R.id.Spinner_ProvinciaDocumentoTestigo);
    			myProvinciaDTestigoSpinner.setSelection(isharedProvinciaDTestigo);
    			if(isharedProvinciaDTestigo==0) ShowEditText(DOCUMENTO_TESTIGO, PROVINCIA, true);
    		}
    		else
    		{
    		   int iPosition = myProvinciaDTestigoSpinner.getCount();
      		   if(iPosition>0 && this.IsRecreating ==true)
      			   {
      			   	myProvinciaDTestigoSpinner.setSelection(0);
      			    ShowEditText(DOCUMENTO_TESTIGO, PROVINCIA, true);
      			   }
      		   else
      			   myProvinciaDTestigoSpinner.setSelection(iPosition);
    		}

    		/*---------------------------------------------------------*/
    		/*
    		ShowEditText(LICENCIA, DEPARTAMENTO, false);
    		if (isharedDepartamento >=0)
    		{
    			myDepartamentoSpinner = (Spinner) findViewById(R.id.Spinner_DepartamentoLicencia);
    			myDepartamentoSpinner.setSelection(isharedDepartamento);
    			if(isharedDepartamento==0) ShowEditText(LICENCIA, DEPARTAMENTO, true);
    		}
    		else
    		{
     		   int iPosition = myDepartamentoSpinner.getCount();
      		   if(iPosition>0 && this.IsRecreating ==true)
      		   {
      			 myDepartamentoSpinner.setSelection(0);
      			 ShowEditText(LICENCIA, DEPARTAMENTO, true);
      		   }
      		   else
      			 myDepartamentoSpinner.setSelection(iPosition);
    		}
    		*/
    		/*---------------------------------------------------------*/

    		ShowEditText(DOCUMENTO, DEPARTAMENTO, false);
    		if (isharedDepartamentoD >= 0)
    		{	myDepartamentoDSpinner = (Spinner) findViewById(R.id.Spinner_DepartamentoDocumento);
    			myDepartamentoDSpinner.setSelection(isharedDepartamentoD);
    			if(isharedDepartamentoD==0) ShowEditText(DOCUMENTO, DEPARTAMENTO, true);
    		}
    		else
    		{
    			  int iPosition = myDepartamentoDSpinner.getCount();
         		   if(iPosition>0 && this.IsRecreating ==true)
         		   {
         			myDepartamentoDSpinner.setSelection(0);
             		ShowEditText(DOCUMENTO, DEPARTAMENTO, true);
         		   }
         		   else
         			 myDepartamentoDSpinner.setSelection(iPosition);

    		}

    		/*---------------------------------------------------------*/

    		ShowEditText(DOCUMENTO_TITULAR, DEPARTAMENTO, false);
    		if (isharedDepartamentoDTitular >= 0)
    		{	myDepartamentoDTitularSpinner = (Spinner) findViewById(R.id.Spinner_DepartamentoDocumentoTitular);
    			myDepartamentoDTitularSpinner.setSelection(isharedDepartamentoDTitular);
    			if(isharedDepartamentoDTitular==0) ShowEditText(DOCUMENTO_TITULAR, DEPARTAMENTO, true);
    		}
    		else
    		{
    			  int iPosition = myDepartamentoDTitularSpinner.getCount();
         		   if(iPosition>0 && this.IsRecreating ==true)
         		   {
         			myDepartamentoDTitularSpinner.setSelection(0);
             		ShowEditText(DOCUMENTO_TITULAR, DEPARTAMENTO, true);
         		   }
         		   else
         			 myDepartamentoDTitularSpinner.setSelection(iPosition);

    		}
    		/* DEPARTAMENTAL DEL TESTIGO */
    		ShowEditText(DOCUMENTO_TESTIGO, DEPARTAMENTO, false);
    		if (isharedDepartamentoDTestigo>= 0)
    		{	myDepartamentoDTestigoSpinner = (Spinner) findViewById(R.id.Spinner_DepartamentoDocumentoTestigo);
    			myDepartamentoDTestigoSpinner.setSelection(isharedDepartamentoDTestigo);
    			if(isharedDepartamentoDTestigo==0) ShowEditText(DOCUMENTO_TESTIGO, DEPARTAMENTO, true);
    		}
    		else
    		{
    			  int iPosition = myDepartamentoDTestigoSpinner.getCount();
         		   if(iPosition>0 && this.IsRecreating ==true)
         		   {
         			myDepartamentoDTestigoSpinner.setSelection(0);
             		ShowEditText(DOCUMENTO_TESTIGO, DEPARTAMENTO, true);
         		   }
         		   else
         			 myDepartamentoDTestigoSpinner.setSelection(iPosition);

    		}

    		/*---------------------------------------------------------*/
    		/*
    		ShowEditText(LICENCIA, LOCALIDAD, false);
    		if (isharedLocalidad >= 0)
    		{
    			myLocalidadSpinner = (Spinner) findViewById(R.id.Spinner_LocalidadLicencia);
    			myLocalidadSpinner.setSelection(isharedLocalidad);
    			if(isharedLocalidad==0) ShowEditText(LICENCIA, LOCALIDAD, true);
    		}
    		else
    		{
    			 int iPosition = myLocalidadSpinner.getCount();
       		   if(iPosition>0 && this.IsRecreating ==true)
       		   {
       			 myLocalidadSpinner.setSelection(0);
       			 ShowEditText(LICENCIA, LOCALIDAD, true);
       			}
       		   else
       			myLocalidadSpinner.setSelection(iPosition);
    		}
    		*/
    		/*---------------------------------------------------------*/
    		ShowEditText(DOCUMENTO, LOCALIDAD, false);
			editText_LocalidadDocumento.setVisibility(View.GONE);
    		if (isharedLocalidadD >= 0)
    		{
    			myLocalidadDSpinner = (Spinner) findViewById(R.id.Spinner_LocalidadDocumento);
    			myLocalidadDSpinner.setSelection(isharedLocalidadD);
    			if(isharedLocalidadD==0) ShowEditText(DOCUMENTO, LOCALIDAD, true);
    		}
    		else
    		{
   			 int iPosition = myLocalidadDSpinner.getCount();
     		   if(iPosition>0 && this.IsRecreating ==true)
     			{
     			  myLocalidadDSpinner.setSelection(0);
     			 ShowEditText(DOCUMENTO, LOCALIDAD, true);
     			}
     		   else
     			  myLocalidadDSpinner.setSelection(iPosition);
    		}
    		/*---------------------------------------------------------*/
    		ShowEditText(DOCUMENTO_TITULAR, LOCALIDAD, false);
			editText_LocalidadDocumentoTitular.setVisibility(View.GONE);
    		if (isharedLocalidadDTitular >= 0)
    		{
    			myLocalidadDTitularSpinner = (Spinner) findViewById(R.id.Spinner_LocalidadDocumentoTitular);
    			myLocalidadDTitularSpinner.setSelection(isharedLocalidadDTitular);
    			if(isharedLocalidadDTitular==0) ShowEditText(DOCUMENTO_TITULAR, LOCALIDAD, true);
    		}
    		else
    		{
   			 int iPosition = myLocalidadDTitularSpinner.getCount();
     		   if(iPosition>0 && this.IsRecreating ==true)
     			{
     			  myLocalidadDTitularSpinner.setSelection(0);
     			 ShowEditText(DOCUMENTO_TITULAR, LOCALIDAD, true);
     			}
     		   else
     			  myLocalidadDTitularSpinner.setSelection(iPosition);
    		}
    		/* localidad del testigo */
    		ShowEditText(DOCUMENTO_TESTIGO, LOCALIDAD, false);
			editText_LocalidadDocumentoTestigo.setVisibility(View.GONE);
    		if (isharedLocalidadDTestigo >= 0)
    		{
    			myLocalidadDTestigoSpinner = (Spinner) findViewById(R.id.Spinner_LocalidadDocumentoTestigo);
    			myLocalidadDTestigoSpinner.setSelection(isharedLocalidadDTestigo);
    			if(isharedLocalidadDTestigo==0) ShowEditText(DOCUMENTO_TESTIGO, LOCALIDAD, true);
    		}
    		else
    		{
   			 int iPosition = myLocalidadDTestigoSpinner.getCount();
     		   if(iPosition>0 && this.IsRecreating ==true)
     			{
     			  myLocalidadDTestigoSpinner.setSelection(0);
     			 ShowEditText(DOCUMENTO_TESTIGO, LOCALIDAD, true);
     			}
     		   else
     			  myLocalidadDTestigoSpinner.setSelection(iPosition);
    		}

    		/*---------------------------------------------------------*/
    		if (isharedTipoRuta >= 0)
    		{
    			myTipoRutaSpinner = (Spinner) findViewById(R.id.Spinner_TipoRuta);
    			myTipoRutaSpinner.setSelection(isharedTipoRuta);

    			//if(isharedTipoRuta==0) ShowEditText(LICENCIA, PAIS, true);
    		}
    		else
    		{  int iPosition = myTipoRutaSpinner.getCount();
    		   if(iPosition>0 && this.IsRecreating ==true)
    			   {
    			   myTipoRutaSpinner.setSelection(0);
    			  //ShowEditText(LICENCIA, PAIS, true);
    			   }
    		   else
    			   myTipoRutaSpinner.setSelection(iPosition);
    		}
    		/*---------------------------------------------------------*/

    		if (isharedRuta >= 0)
    		{
    			myRutaSpinner = (Spinner) findViewById(R.id.Spinner_Ruta);
    			myRutaSpinner.setSelection(isharedRuta);
    			//if(isharedProvinciaD==0) ShowEditText(DOCUMENTO, PROVINCIA, true);
    		}
    		else
    		{
    		   int iPosition = myRutaSpinner.getCount();
      		   if(iPosition>0 && this.IsRecreating ==true)
      		   {
      			    myRutaSpinner.setSelection(0);
      			    //ShowEditText(DOCUMENTO, PROVINCIA, true);
      		   }
      		   else
      			   myRutaSpinner.setSelection(iPosition);
    		}


    		/*---------------------------------------------------------*/

    		ShowEditText(MARCA,null, false);
    		if (isharedMarca>= 0)
    		{
    			myMarcaSpinner = (Spinner) findViewById(R.id.Spinner_Marca);
    			myMarcaSpinner.setSelection(isharedMarca);

    			if(isharedMarca==0) ShowEditText(MARCA, null, true);
    		}
    		else
    		{  int iPosition = myMarcaSpinner.getCount();
    		   if(iPosition>0 && this.IsRecreating ==true)
    			   {
    			   	myMarcaSpinner.setSelection(0);
    			   	ShowEditText(MARCA,null, true);
    			   }
    		   else
    			   myMarcaSpinner.setSelection(iPosition);
    		}

    		/*---------------------------------------------------------*/

    		ShowEditText(COLOR,null, false);
    		if (isharedColor>= 0)
    		{
    			myColorSpinner = (Spinner) findViewById(R.id.Spinner_Color);
    			myColorSpinner.setSelection(isharedColor);

    			if(isharedColor==0) ShowEditText(COLOR, null, true);
    		}
    		else
    		{  int iPosition = myColorSpinner.getCount();
    		   if(iPosition>0 && this.IsRecreating ==true)
    			   {
    			   	myColorSpinner.setSelection(0);
    			   	ShowEditText(COLOR,null, true);
    			   }
    		   else
    			   myColorSpinner.setSelection(iPosition);
    		}

    		/*---------------------------------------------------------*/
    		//editText_ClaseLicencia = (EditText) findViewById(R.id.EditText_ClaseLicencia) ;
    		/*---------------------------------------------------------*/
    		HabilitarListeners(sListeners);



    }

    @Override
    protected void onRestart() {
    	Tools.Log(Log.ERROR, TAG, "onRestart");
    	super.onRestart();
    }
    @Override
    public void onStart()
    {
    	Tools.Log(Log.ERROR, TAG, "onStart");
    	super.onStart();

    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
    	return someExpensiveObject;
    }
    /**
     *  The method that creates an instance of SomeExpensiveObject class.
     */
    public SomeExpensiveObject doSomeExpensiveOperation() {
     return new SomeExpensiveObject();
    }


    private void cleanActivity()
    {
    	bGrabando = false;
    try
    {

    	//Toast.makeText(CreateActaActivity.this, "Reiniciando Formulario de Carga ",Toast.LENGTH_SHORT).show();

    	ActaConstatacionRules actaRules = new ActaConstatacionRules(this);
    	String sNumeroActa = actaRules.getNextNumeroActa();
    	textView_NumeroActa.setText(sNumeroActa);

		myEntidadSpinner.setSelection(-1);
		mCurrenActaSettings.edit().putInt(CURRENT_ACTA_ENTIDAD,0).commit();

		//textView_DescripcionEntidad.setText("");


		myTipoVehiculoSpinner.setSelection(myTipoVehiculoSpinner.getCount());
		mCurrenActaSettings.edit().putInt(CURRENT_ACTA_TIPO_VEHICULO,0).commit();
		subPnlTipoVehiculo.setVisibility(View.GONE);




		checkBox_ConduccionPeligrosa.setChecked(false);
		//siempre lo dejaria copiado
		checkBox_DejaCopia.setChecked(true);

		String sContenido ="";
		editText_CODInfraccion_1.cleanData();
		editText_CODInfraccion_2.cleanData();
		editText_CODInfraccion_3.cleanData();
		editText_CODInfraccion_4.cleanData();
		editText_CODInfraccion_5.cleanData();
		editText_CODInfraccion_6.cleanData();

		editText_CODInfraccion_1.setVisibility(View.GONE);
		editText_CODInfraccion_2.setVisibility(View.GONE);
		editText_CODInfraccion_3.setVisibility(View.GONE);
		editText_CODInfraccion_4.setVisibility(View.GONE);
		editText_CODInfraccion_5.setVisibility(View.GONE);
		editText_CODInfraccion_6.setVisibility(View.GONE);

		mCurrenActaSettings.edit().putInt(CURRENT_ACTA_INFRACCION1, -2).commit();
		mCurrenActaSettings.edit().putInt(CURRENT_ACTA_INFRACCION2, -2).commit();
		mCurrenActaSettings.edit().putInt(CURRENT_ACTA_INFRACCION3, -2).commit();
		mCurrenActaSettings.edit().putInt(CURRENT_ACTA_INFRACCION4, -2).commit();
		mCurrenActaSettings.edit().putInt(CURRENT_ACTA_INFRACCION5, -2).commit();
		mCurrenActaSettings.edit().putInt(CURRENT_ACTA_INFRACCION6, -2).commit();

		editText_Observaciones.setText("");

		editText_AlcoholEnSangre.setText("");
		editText_AlcoholEnSangre.setVisibility(View.GONE);
		textView_AlcoholEnSangre.setVisibility(View.GONE);

		int iPosition =0;

		/* *************** CONDUCTOR ***********************/
		checkBox_SinConductor.setChecked(false);
		editText_Telefono.setText("");
		editText_Email.setText("");
		editText_FVL_Info.setText("");
		myTipoDocumentoSpinner.setSelection(myTipoDocumentoSpinner.getCount());
		editText_NumeroDocumento.setText("");
		editText_Apellido.setText("");
		editText_Nombre.setText("");
		editText_Calle.setText("");
		editText_Altura.setText("");
		editText_Piso.setText("");
		editText_Depto.setText("");
		editText_Barrio.setText("");

		iPosition = myLocalidadDSpinner.getCount();
		myLocalidadDSpinner.setSelection(iPosition);
		mCurrenActaSettings.edit().putInt(CURRENT_ACTA_LOCALIDAD_DOCUMENTO,iPosition).commit();
		editText_LocalidadDocumento.setText("");

		iPosition = myDepartamentoDSpinner.getCount();
		myDepartamentoDSpinner.setSelection(iPosition);
		mCurrenActaSettings.edit().putInt(CURRENT_ACTA_DEPARTAMENTO_DOCUMENTO,iPosition).commit();
		editText_DepartamentoDocumento.setText("");

		editText_PaisDocumento.setText("");
		editText_ProvinciaDocumento.setText("");

		editText_CodigoPostal.setText("");


		/* **********TESTIGO***********************/
		checkBox_SinTestigo.setChecked(true);
		myTipoDocumentoTestigoSpinner.setSelection(myTipoDocumentoTestigoSpinner.getCount());
		editText_NumeroDocumentoTestigo.setText("");
		editText_ApellidoTestigo.setText("");
		editText_NombreTestigo.setText("");
		editText_CalleTestigo.setText("");
		editText_AlturaTestigo.setText("");
		editText_PisoTestigo.setText("");
		editText_DeptoTestigo.setText("");
		editText_BarrioTestigo.setText("");

		iPosition = myLocalidadDTestigoSpinner.getCount();
		myLocalidadDTestigoSpinner.setSelection(iPosition);
		mCurrenActaSettings.edit().putInt(CURRENT_ACTA_LOCALIDAD_DOCUMENTO_TESTIGO,iPosition).commit();
		editText_LocalidadDocumentoTestigo.setText("");

		iPosition = myDepartamentoDTestigoSpinner.getCount();
		myDepartamentoDTestigoSpinner.setSelection(iPosition);
		mCurrenActaSettings.edit().putInt(CURRENT_ACTA_DEPARTAMENTO_DOCUMENTO_TESTIGO,iPosition).commit();
		editText_DepartamentoDocumentoTestigo.setText("");

		editText_PaisDocumentoTestigo.setText("");
		editText_ProvinciaDocumentoTestigo.setText("");


		editText_CodigoPostalTestigo.setText("");

		editText_ManifestacionTestigo.setText("");

		/* *********** TITULAR *****************************/
		checkBox_SinTitular.setChecked(false);
		myTipoDocumentoTitularSpinner.setSelection(myTipoDocumentoTitularSpinner.getCount());
		editText_NumeroDocumentoTitular.setText("");
		editText_ApellidoTitular.setText("");
		editText_NombreTitular.setText("");
		editText_CalleTitular.setText("");
		editText_AlturaTitular.setText("");
		editText_PisoTitular.setText("");
		editText_DeptoTitular.setText("");
		editText_BarrioTitular.setText("");

		iPosition = myLocalidadDTitularSpinner.getCount();
		myLocalidadDTitularSpinner.setSelection(iPosition);
		mCurrenActaSettings.edit().putInt(CURRENT_ACTA_LOCALIDAD_DOCUMENTO_TITULAR,iPosition).commit();
		editText_LocalidadDocumentoTitular.setText("");

		iPosition = myDepartamentoDTitularSpinner.getCount();
		myDepartamentoDTitularSpinner.setSelection(iPosition);
		mCurrenActaSettings.edit().putInt(CURRENT_ACTA_DEPARTAMENTO_DOCUMENTO_TITULAR,iPosition).commit();
		editText_DepartamentoDocumentoTitular.setText("");

		editText_PaisDocumentoTitular.setText("");
		editText_ProvinciaDocumentoTitular.setText("");

		editText_CodigoPostalTitular.setText("");

		/* ************ LICENCIA ****************************/
		checkBox_LicenciaRetenida.setChecked(false);
		checkBox_VehiculoRetenido.setChecked(false);
		checkBox_LicenciaUnicaProvincial.setChecked(false);
		checkBox_NoTieneLicencia.setChecked(false);
		checkBox_SinTestigo.setChecked(true);
		checkBox_SinTitular.setChecked(false);

		editText_CodigoPostal.setText("");
		editText_CodigoPostalTestigo.setText("");
		editText_CodigoPostalTitular.setText("");

		/* TESTIGO */
		myTipoDocumentoTestigoSpinner.setSelection(myTipoDocumentoTestigoSpinner.getCount());
		editText_NumeroDocumentoTestigo.setText("");
		editText_ApellidoTestigo.setText("");
		editText_NombreTestigo.setText("");
		editText_CalleTestigo.setText("");
		editText_AlturaTestigo.setText("");
		editText_PisoTestigo.setText("");
		editText_DeptoTestigo.setText("");
		editText_BarrioTestigo.setText("");


		/*TITULAR*/
		myTipoDocumentoTitularSpinner.setSelection(myTipoDocumentoTitularSpinner.getCount());
		editText_NumeroDocumentoTitular.setText("");
		editText_ApellidoTitular.setText("");
		editText_NombreTitular.setText("");
		editText_CalleTitular.setText("");
		editText_AlturaTitular.setText("");
		editText_PisoTitular.setText("");
		editText_DeptoTitular.setText("");
		editText_BarrioTitular.setText("");

		checkBox_LicenciaUnicaProvincial.setChecked(false);
		checkBox_NoTieneLicencia.setChecked(false);
		checkBox_LicenciaRetenida.setChecked(false);
		editText_NumeroLicencia.setText("");
		editText_ClaseLicencia.setText("");

		editText_FVL_Info.setText("");
		mCurrenActaSettings.edit().putString(CURRENT_ACTA_FECHA_VENCIMIENTO_LICENCIA,"").commit();

		String strAvatarUri = RESOURCE_SIN_FOTO;
		Uri imageUri = Uri.parse(strAvatarUri);
		imageButton_Licencia.setImageURI(null); // Workaround for refreshing an
		imageButton_Licencia.setImageURI(imageUri);
										// ImageButton, which tries to cache the
										// previous image Uri. Passing null
										// effectively resets it.
		imageButton_Documento.setImageURI(null); // Workaround for refreshing an
		imageButton_Documento.setImageURI(imageUri);

		mCurrenActaSettings.edit().putString(CURRENT_ACTA_FOTO_LICENCIA,RESOURCE_SIN_FOTO).commit();
		mCurrenActaSettings.edit().putString(CURRENT_ACTA_FOTO_DOCUMENTO,RESOURCE_SIN_FOTO).commit();
		mCurrenActaSettings.edit().putString(CURRENT_ACTA_FOTO_OTRO,RESOURCE_SIN_FOTO).commit();

		// ImageButton, which tries to cache the
		// previous image Uri. Passing null
		// effectively resets it.

		editText_Dominio.setText("");
		editText_ModeloVehiculo.setText("");
		editText_AnioModeloVehiculo.setText("");
		editText_Marca.setText("");
		editText_Color.setText("");
		myMarcaSpinner.setSelection(myMarcaSpinner.getCount());

		myColorSpinner.setSelection(myColorSpinner.getCount());

		//this.IsRecreating = true;
	    //initPaisesSpinner();

	    {
			  String[] sListeners = {
					   SPINNER_PAIS_LICENCIA
					  };
			  String ParametroPais = "1";
			  try
		      {
				  	DeshabilitarListeners(sListeners);
		      		SetSpinnerSelectionByValue(myPaisAdapter,SPINNER_PAIS_LICENCIA,ParametroPais);
		      		HabilitarListeners(sListeners);
			   }
			  catch(Exception ex)
			   {
				Utilities.ShowToast(CreateActaActivity.this, "Error :" + ex.getMessage());
			   }
  	  }

	    //initProvinciasSpinner();
		{
			  String[] sListeners = {
					  	SPINNER_PROVINCIA_LICENCIA
					  };

			  //String ParametroProvincia = "13";
			  try
		      {	DeshabilitarListeners(sListeners);
			    fillProvinciaSpinner("1");
			    //SetSpinnerSelectionByValue(myPaisAdapter,SPINNER_PAIS_LICENCIA,ParametroPais);
			    	// SetSpinnerSelectionByValue(myProvinciaAdapter,SPINNER_PROVINCIA_LICENCIA,ParametroProvincia);
			    HabilitarListeners(sListeners);
			   }
			  catch(Exception ex)
			   {
				//Utilities.ShowToast(CreateActaActivity.this, "Error :" + ex.getMessage());
			   }
  	    }

		myProvinciaSpinner.post(new Runnable() {
		    public void run() {
		    	try
		    	{
		    	//myProvinciaSpinner.setSelection(12,true);
		    	//mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PROVINCIA_LICENCIA, 12).commit();
		    	}catch(Exception e){}//String ParametroProvincia = "13";

		    	SetSpinnerSelectionByValue(myProvinciaAdapter,SPINNER_PROVINCIA_LICENCIA,"12");
		    	int iposicion = myProvinciaSpinner.getSelectedItemPosition();
		    	if(iposicion>0)
		    	{
		    		//Utilities.ShowToast(CreateActaActivity.this, "Provincia de la Licencia Seleccionada " + iposicion);
		    	}

		    }
		  });

		try
		{
			editText_PaisLicencia.setText("");
			editText_ProvinciaLicencia.setText("");

			editText_PaisLicencia.setVisibility(View.GONE);
			editText_ProvinciaLicencia.setVisibility(View.GONE);


			editText_PaisDocumento.setText("");
			editText_PaisDocumentoTitular.setText("");
			editText_PaisDocumentoTestigo.setText("");

			editText_ProvinciaDocumento.setText("");
			editText_ProvinciaDocumentoTitular.setText("");
			editText_ProvinciaDocumentoTestigo.setText("");

			editText_DepartamentoDocumento.setText("");
			editText_DepartamentoDocumentoTitular.setText("");
			editText_DepartamentoDocumentoTestigo.setText("");


			editText_LocalidadDocumento.setText("");
			editText_LocalidadDocumentoTitular.setText("");
			editText_LocalidadDocumentoTestigo.setText("");


			editText_PaisDocumento.setVisibility(View.GONE);
			editText_PaisDocumentoTitular.setVisibility(View.GONE);
			editText_PaisDocumentoTestigo.setVisibility(View.GONE);

			editText_ProvinciaDocumento.setVisibility(View.GONE);
			editText_ProvinciaDocumentoTitular.setVisibility(View.GONE);
			editText_ProvinciaDocumentoTestigo.setVisibility(View.GONE);

			editText_DepartamentoDocumento.setVisibility(View.GONE);
			editText_DepartamentoDocumentoTitular.setVisibility(View.GONE);
			editText_DepartamentoDocumentoTestigo.setVisibility(View.GONE);

			editText_LocalidadDocumento.setVisibility(View.GONE);
			editText_LocalidadDocumentoTitular.setVisibility(View.GONE);
			editText_LocalidadDocumentoTestigo.setVisibility(View.GONE);

		}catch(Exception es){}
		//this.IsRecreating = false;
		/*
		// DEJAMOS LA LICENCIA EN MENDOZA POR DEFECTO
		{
			  String[] sListeners = {
					   SPINNER_PAIS_LICENCIA
					  ,SPINNER_PROVINCIA_LICENCIA
					  };
			  String ParametroPais = "1";
			  String ParametroProvincia = "13";
			  try
		      {	DeshabilitarListeners(sListeners);
			    fillProvinciaSpinner(ParametroPais);
			    SetSpinnerSelectionByValue(myPaisAdapter,SPINNER_PAIS_LICENCIA,ParametroPais);
			    int iposicion = SetSpinnerSelectionByValue(myProvinciaAdapter,SPINNER_PROVINCIA_LICENCIA,ParametroProvincia);
			    mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PROVINCIA_LICENCIA,iposicion).commit();
			    HabilitarListeners(sListeners);
			   }
			  catch(Exception ex)
			   {
				Utilities.ShowToast(CreateActaActivity.this, "Error :" + ex.getMessage());
			   }
    	  }
    	  */
	// LIMPIAMOS TITULAR
		  {
			  String[]	  sListeners = {
					   SPINNER_PAIS_DOCUMENTO_TITULAR
					  ,SPINNER_PROVINCIA_DOCUMENTO_TITULAR
					  ,SPINNER_DEPARTAMENTO_DOCUMENTO_TITULAR
					  ,SPINNER_LOCALIDAD_DOCUMENTO_TITULAR
					  };
			  String ParametroPais = "1";
			  String ParametroProvincia = "12";
			  try
		      {	DeshabilitarListeners(sListeners);
			    fillProvinciaDTitularSpinner(ParametroPais);
			    fillDepartamentoDTitularSpinner(ParametroProvincia);
			    fillLocalidadDTitularSpinner("-1");
			    SetSpinnerSelectionByValue(myPaisDTitularAdapter,SPINNER_PAIS_DOCUMENTO_TITULAR,ParametroPais);
			    int iposicion = SetSpinnerSelectionByValue(myProvinciaDTitularAdapter,SPINNER_PROVINCIA_DOCUMENTO_TITULAR,ParametroProvincia);
			    mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PROVINCIA_DOCUMENTO_TITULAR,iposicion).commit();

			    myDepartamentoDTitularSpinner.setSelection(myDepartamentoDTitularSpinner.getCount());
			    myLocalidadDTitularSpinner.setSelection(myLocalidadDTitularSpinner.getCount());

			    HabilitarListeners(sListeners);
			   }
			  catch(Exception ex)
			   {

			   }

		  }

	//LIMPIAMPOS CONDUCTOR
		  {
			  String[]	  sListeners = {
					   SPINNER_PAIS_DOCUMENTO
					  ,SPINNER_PROVINCIA_DOCUMENTO
					  ,SPINNER_DEPARTAMENTO_DOCUMENTO
					  ,SPINNER_LOCALIDAD_DOCUMENTO
					  };
			  String ParametroPais = "1";
			  String ParametroProvincia = "12";
			  try
		      {	DeshabilitarListeners(sListeners);
			    fillProvinciaDSpinner(ParametroPais);
			    fillDepartamentoDSpinner(ParametroProvincia);
			    fillLocalidadDSpinner("-1");
			    SetSpinnerSelectionByValue(myPaisDAdapter,SPINNER_PAIS_DOCUMENTO,ParametroPais);
			    int iposicion = SetSpinnerSelectionByValue(myProvinciaDAdapter,SPINNER_PROVINCIA_DOCUMENTO,ParametroProvincia);
			    mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PROVINCIA_DOCUMENTO,iposicion).commit();

			    myDepartamentoDSpinner.setSelection(myDepartamentoDSpinner.getCount());
			    myLocalidadDSpinner.setSelection(myLocalidadDSpinner.getCount());

			    HabilitarListeners(sListeners);
			   }
			  catch(Exception ex)
			   {

			   }

		  }

			//LIMPIAMPOS TESTIGO
		  {
			  String[]	  sListeners = {
					   SPINNER_PAIS_DOCUMENTO_TESTIGO
					  ,SPINNER_PROVINCIA_DOCUMENTO_TESTIGO
					  ,SPINNER_DEPARTAMENTO_DOCUMENTO_TESTIGO
					  ,SPINNER_LOCALIDAD_DOCUMENTO_TESTIGO
					  };
			  String ParametroPais = "1";
			  String ParametroProvincia = "12";
			  try
		      {	DeshabilitarListeners(sListeners);
			    fillProvinciaDTestigoSpinner(ParametroPais);
			    fillDepartamentoDTestigoSpinner(ParametroProvincia);
			    fillLocalidadDTestigoSpinner("-1");
			    SetSpinnerSelectionByValue(myPaisDTestigoAdapter,SPINNER_PAIS_DOCUMENTO_TESTIGO,ParametroPais);
			    int iposicion = SetSpinnerSelectionByValue(myProvinciaDTestigoAdapter,SPINNER_PROVINCIA_DOCUMENTO_TESTIGO,ParametroProvincia);
			    mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PROVINCIA_DOCUMENTO_TESTIGO,iposicion).commit();

			    myDepartamentoDTestigoSpinner.setSelection(myDepartamentoDTestigoSpinner.getCount());
			    myLocalidadDTestigoSpinner.setSelection(myLocalidadDTestigoSpinner.getCount());

			    HabilitarListeners(sListeners);
			   }
			  catch(Exception ex)
			   {

			   }

		  }

		  /*
	  initPaisesDSpinner();
	  initPaisesDTitularSpinner();
	  initPaisesDTestigoSpinner();

	  initProvinciasDSpinner();
	  initProvinciasDTitularSpinner();
	  initProvinciasDTestigoSpinner();


	  initDepartamentosDSpinner();
	  initDepartamentosDTitularSpinner();
	  initDepartamentosDTestigoSpinner();


	  initLocalidadesDSpinner();
	  initLocalidadesDTitularSpinner();
	  initLocalidadesDTestigoSpinner();
	  */
    } catch(Exception e){


    }

  }
    private void InitializeActivity()
    {
    	// get location manager to check gps availability
    	/*LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L,1.0f, CreateActaActivity.this);

		boolean isGPS = locationManager.isProviderEnabled (LocationManager.GPS_PROVIDER);

		if(!isGPS){
       	  turnGPSOn();
		}
		*/
    	InputFilter[]  filterForAllText = new InputFilter[] {new InputFilter.AllCaps()};

    	txtScanResult = (TextView) findViewById(R.id.scan_result);

    	textView_NumeroActa = (TextView) findViewById(R.id.TextView_Numero_Acta);
    	ActaConstatacionRules actaRules = new ActaConstatacionRules(this);
    	String sNumeroActa = actaRules.getNextNumeroActa();
    	textView_NumeroActa.setText(sNumeroActa);

    	subPnlTipoVehiculo = (LinearLayout) findViewById(R.id.Layout_SubPnlTipoVehiculo);

    	myTipoRutaSpinner = (Spinner) findViewById(R.id.Spinner_TipoRuta);
		myRutaSpinner = (Spinner) findViewById(R.id.Spinner_Ruta);

    	myDepartamentoInfraccionSpinner = (Spinner) findViewById(R.id.Spinner_DepartamentoInfraccion);
		myLocalidadInfraccionSpinner = (Spinner) findViewById(R.id.Spinner_LocalidadInfraccion);

		editText_Ubicacion = (EditText) findViewById(R.id.EditText_Ubicacion);
		editText_Referencia = (EditText) findViewById(R.id.EditText_Referencia);
		editText_Email = (EditText) findViewById(R.id.EditText_EmailConductor);
		editText_Telefono = (EditText) findViewById(R.id.EditText_TelefonoConductor);

		editText_DescripcionUbicacion = (EditText) findViewById(R.id.EditText_DescripcionUbicacion);
		editText_DescripcionUbicacion.setFilters(filterForAllText);

		textView_NroRuta = (TextView) findViewById(R.id.TextView_NroRuta);

		textView_LocalidadInfraccion = (TextView) findViewById(R.id.TextView_LocalidadInfraccion);
		textView_Km = (TextView) findViewById(R.id.TextView_Km);

		editText_Km= (EditText) findViewById(R.id.EditText_Km) ;
		textView_DescripcionEntidad = (TextView) findViewById(R.id.TextView_DescripcionJuzgado);
		editText_FVL_Info = (EditText) findViewById(R.id.EditText_FVL_Info); // fecha de vencimiento de la licencia
		textView_FN_Info = (TextView) findViewById(R.id.TextView_FN_Info); // fecha de nacimiento conductor

		myEntidadSpinner = (Spinner) findViewById(R.id.Spinner_Juzgado);
		mySeccionalSpinner= (Spinner) findViewById(R.id.Spinner_Seccional);

		checkBox_LicenciaUnicaProvincial = (CheckBox) findViewById(R.id.CheckBox_LicenciaUnicaProvincial);
		checkBox_NoTieneLicencia = (CheckBox) findViewById(R.id.CheckBox_NoTieneLicencia);
		checkBox_LicenciaRetenida = (CheckBox) findViewById(R.id.CheckBox_LicenciaRetenida);
		checkBox_VehiculoRetenido = (CheckBox) findViewById(R.id.CheckBox_VehiculoRetenido);

		checkBox_CallePublicaTitular = (CheckBox) findViewById(R.id.CheckBox_CallePublicaTitular);
		checkBox_AlturaSinNumeroTitular = (CheckBox) findViewById(R.id.CheckBox_AlturaSinNumeroTitular);

		editText_NumeroLicencia= (EditText) findViewById(R.id.EditText_NumeroLicencia);
		editText_NumeroLicencia.setFilters(filterForAllText);

		editText_ClaseLicencia = (EditText) findViewById(R.id.EditText_ClaseLicencia) ;
		editText_ClaseLicencia.setFilters(filterForAllText);

		Button_CopiarDatosTitular = (Button) findViewById(R.id.Button_CopiarDatosTitular) ;
		Button_VerificarCodigoQR = (Button) findViewById(R.id.Button_VerificarCodigoQR) ;
		button_VerificarLicencia = (Button) findViewById(R.id.Button_VerificarLicencia) ;
		button_VerificarDominio = (Button) findViewById(R.id.Button_VerificarDominio) ;

		/*-----------------------------------------------------------------------------------*/
    	myPaisSpinner = (Spinner) findViewById(R.id.Spinner_PaisLicencia);
 	    myProvinciaSpinner = (Spinner) findViewById(R.id.Spinner_ProvinciaLicencia);
		myDepartamentoSpinner = (Spinner) findViewById(R.id.Spinner_DepartamentoLicencia);
		myLocalidadSpinner = (Spinner) findViewById(R.id.Spinner_LocalidadLicencia);


		editText_PaisLicencia = (EditText) findViewById(R.id.EditText_PaisLicencia) ;
		editText_ProvinciaLicencia = (EditText) findViewById(R.id.EditText_ProvinciaLicencia) ;
		editText_DepartamentoLicencia = (EditText) findViewById(R.id.EditText_DepartamentoLicencia) ;
		editText_LocalidadLicencia = (EditText) findViewById(R.id.EditText_LocalidadLicencia) ;
		/*-------------------------------------------------------------------------------------*/

		myTipoDocumentoSpinner = (Spinner) findViewById(R.id.Spinner_TipoDocumento);
		//myGeneroSpinner = (Spinner) findViewById(R.id.Spinner_Sexo);

		myTipoDocumentoTitularSpinner = (Spinner) findViewById(R.id.Spinner_TipoDocumentoTitular);
		//myGeneroTitularSpinner = (Spinner) findViewById(R.id.Spinner_SexoTitular);

		myTipoDocumentoTestigoSpinner = (Spinner) findViewById(R.id.Spinner_TipoDocumentoTestigo);
		//myGeneroTestigoSpinner = (Spinner) findViewById(R.id.Spinner_SexoTestigo);

		/*-------------------------------------------------------------------------------------*/
		editText_PaisDocumento = (EditText) findViewById(R.id.EditText_PaisDocumento) ;
		editText_ProvinciaDocumento = (EditText) findViewById(R.id.EditText_ProvinciaDocumento) ;
		editText_DepartamentoDocumento = (EditText) findViewById(R.id.EditText_DepartamentoDocumento) ;
		editText_LocalidadDocumento = (EditText) findViewById(R.id.EditText_LocalidadDocumento) ;

    	myPaisDSpinner = (Spinner) findViewById(R.id.Spinner_PaisDocumento);
 	    myProvinciaDSpinner = (Spinner) findViewById(R.id.Spinner_ProvinciaDocumento);
		myDepartamentoDSpinner = (Spinner) findViewById(R.id.Spinner_DepartamentoDocumento);
		myLocalidadDSpinner = (Spinner) findViewById(R.id.Spinner_LocalidadDocumento);
		/*--------------------------------------------------------------------------------------*/
		/*-------------------------------------------------------------------------------------*/
		editText_PaisDocumentoTitular = (EditText) findViewById(R.id.EditText_PaisDocumentoTitular) ;
		editText_ProvinciaDocumentoTitular = (EditText) findViewById(R.id.EditText_ProvinciaDocumentoTitular) ;
		editText_DepartamentoDocumentoTitular = (EditText) findViewById(R.id.EditText_DepartamentoDocumentoTitular) ;
		editText_LocalidadDocumentoTitular = (EditText) findViewById(R.id.EditText_LocalidadDocumentoTitular) ;

    	myPaisDTitularSpinner = (Spinner) findViewById(R.id.Spinner_PaisDocumentoTitular);
 	    myProvinciaDTitularSpinner = (Spinner) findViewById(R.id.Spinner_ProvinciaDocumentoTitular);
		myDepartamentoDTitularSpinner = (Spinner) findViewById(R.id.Spinner_DepartamentoDocumentoTitular);
		myLocalidadDTitularSpinner = (Spinner) findViewById(R.id.Spinner_LocalidadDocumentoTitular);
		/*--------------------------------------------------------------------------------------*/
		/*-------------------------------------------------------------------------------------*/
		editText_PaisDocumentoTestigo= (EditText) findViewById(R.id.EditText_PaisDocumentoTestigo) ;
		editText_ProvinciaDocumentoTestigo = (EditText) findViewById(R.id.EditText_ProvinciaDocumentoTestigo) ;
		editText_DepartamentoDocumentoTestigo = (EditText) findViewById(R.id.EditText_DepartamentoDocumentoTestigo) ;
		editText_LocalidadDocumentoTestigo= (EditText) findViewById(R.id.EditText_LocalidadDocumentoTestigo) ;

    	myPaisDTestigoSpinner = (Spinner) findViewById(R.id.Spinner_PaisDocumentoTestigo);
 	    myProvinciaDTestigoSpinner = (Spinner) findViewById(R.id.Spinner_ProvinciaDocumentoTestigo);
		myDepartamentoDTestigoSpinner = (Spinner) findViewById(R.id.Spinner_DepartamentoDocumentoTestigo);
		myLocalidadDTestigoSpinner = (Spinner) findViewById(R.id.Spinner_LocalidadDocumentoTestigo);
		/*--------------------------------------------------------------------------------------*/

		/*datos de la persona + domicilio TITULAR*/
		editText_NumeroDocumentoTitular= (EditText) findViewById(R.id.EditText_NumeroDocumentoTitular);
		editText_NumeroDocumentoTitular.setFilters(filterForAllText);


		editText_ApellidoTitular = (EditText) findViewById(R.id.EditText_ApellidoTitular) ;
		editText_ApellidoTitular.setFilters(filterForAllText);

		editText_NombreTitular = (EditText) findViewById(R.id.EditText_NombreTitular) ;
		editText_NombreTitular.setFilters(filterForAllText);

		editText_CalleTitular= (EditText) findViewById(R.id.EditText_CalleTitular) ;
		editText_CalleTitular.setFilters(filterForAllText);

		editText_AlturaTitular= (EditText) findViewById(R.id.EditText_AlturaTitular) ;
		//editText_Altura.setFilters(filterForAllText);

		editText_PisoTitular= (EditText) findViewById(R.id.EditText_PisoTitular) ;
		editText_PisoTitular.setFilters(filterForAllText);

		editText_DeptoTitular= (EditText) findViewById(R.id.EditText_DepartamentoTitular) ;
		editText_DeptoTitular.setFilters(filterForAllText);

		editText_BarrioTitular= (EditText) findViewById(R.id.EditText_BarrioTitular) ;
		editText_BarrioTitular.setFilters(filterForAllText);

		editText_CodigoPostalTitular= (EditText) findViewById(R.id.EditText_CodigoPostalTitular) ;
		editText_CodigoPostalTitular.setFilters(filterForAllText);
	    /* datos de la persnoa del testigo + domicilio*/
		editText_NumeroDocumentoTestigo= (EditText) findViewById(R.id.EditText_NumeroDocumentoTestigo);
		editText_NumeroDocumentoTestigo.setFilters(filterForAllText);


		editText_ApellidoTestigo= (EditText) findViewById(R.id.EditText_ApellidoTestigo) ;
		editText_ApellidoTestigo.setFilters(filterForAllText);

		editText_NombreTestigo = (EditText) findViewById(R.id.EditText_NombreTestigo) ;
		editText_NombreTestigo.setFilters(filterForAllText);

		editText_CalleTestigo= (EditText) findViewById(R.id.EditText_CalleTestigo) ;
		editText_CalleTestigo.setFilters(filterForAllText);

		editText_AlturaTestigo= (EditText) findViewById(R.id.EditText_AlturaTestigo) ;
		//editText_Altura.setFilters(filterForAllText);

		editText_PisoTestigo= (EditText) findViewById(R.id.EditText_PisoTestigo) ;
		editText_PisoTestigo.setFilters(filterForAllText);

		editText_DeptoTestigo= (EditText) findViewById(R.id.EditText_DepartamentoTestigo) ;
		editText_DeptoTestigo.setFilters(filterForAllText);

		editText_BarrioTestigo= (EditText) findViewById(R.id.EditText_BarrioTestigo) ;
		editText_BarrioTestigo.setFilters(filterForAllText);

		editText_CodigoPostalTestigo= (EditText) findViewById(R.id.EditText_CodigoPostalTestigo) ;
		editText_CodigoPostalTestigo.setFilters(filterForAllText);

		editText_ManifestacionTestigo= (EditText) findViewById(R.id.EditText_ManifestacionTestigo) ;
		editText_ManifestacionTestigo.setFilters(filterForAllText);

		/* fin datos de la persona + domicilio CONDUCTOR */
		checkBox_SinConductor = (CheckBox) findViewById(R.id.CheckBox_SinConductor);
		editText_NumeroDocumento= (EditText) findViewById(R.id.EditText_NumeroDocumento);
		editText_NumeroDocumento.setFilters(filterForAllText);


		editText_Apellido = (EditText) findViewById(R.id.EditText_Apellido) ;
		editText_Apellido.setFilters(filterForAllText);

		editText_Nombre = (EditText) findViewById(R.id.EditText_Nombre) ;
		editText_Nombre.setFilters(filterForAllText);

		editText_Calle= (EditText) findViewById(R.id.EditText_Calle) ;
		editText_Calle.setFilters(filterForAllText);

		editText_Altura= (EditText) findViewById(R.id.EditText_Altura) ;
		//editText_Altura.setFilters(filterForAllText);

		editText_Piso= (EditText) findViewById(R.id.EditText_Piso) ;
		editText_Piso.setFilters(filterForAllText);

		editText_Depto= (EditText) findViewById(R.id.EditText_Departamento) ;
		editText_Depto.setFilters(filterForAllText);

		editText_Barrio= (EditText) findViewById(R.id.EditText_Barrio) ;
		editText_Barrio.setFilters(filterForAllText);

		editText_CodigoPostal= (EditText) findViewById(R.id.EditText_CodigoPostal) ;
		editText_CodigoPostal.setFilters(filterForAllText);
		/* fin datos de la persona + domicilio CONDUCTOR */

		checkBox_SinTestigo = (CheckBox) findViewById(R.id.CheckBox_SinTestigo);
		initNoRegistraTestigoCheckBox();
		checkBox_SinTestigo.setChecked(true);
		checkBox_SinTitular =  (CheckBox) findViewById(R.id.CheckBox_SinTitular);

		myTipoVehiculoSpinner = (Spinner) findViewById(R.id.Spinner_TipoVehiculo);
		myTipoPatenteSpinner = (Spinner) findViewById(R.id.Spinner_TipoPatente);

		editText_Dominio= (EditText) findViewById(R.id.EditText_Dominio) ;
		editText_Dominio.setFilters(filterForAllText);

		imageButton_Licencia = (ImageButton) findViewById(R.id.ImageButton_Licencia);

		imageButton_Documento= (ImageButton) findViewById(R.id.ImageButton_Documento);

		checkBox_ConduccionPeligrosa = (CheckBox) findViewById(R.id.CheckBox_ConduccionPeligrosa);
		checkBox_DejaCopia = (CheckBox) findViewById(R.id.CheckBox_DejaCopia);
		checkBox_DejaCopia.setChecked(true);
		button_VerificarDocumento = (Button) findViewById(R.id.Button_VerificarDocumento);
		button_VerificarLicencia = (Button) findViewById(R.id.Button_VerificarLicencia);
		button_VerificarDominio = (Button) findViewById(R.id.Button_VerificarDominio);
		button_VerificarDocumentoTitular = (Button) findViewById(R.id.Button_VerificarDocumentoTitular);
		button_VerificarDocumentoTestigo = (Button) findViewById(R.id.Button_VerificarDocumentoTestigo);


		button_VerificarInfraccion1 = (Button) findViewById(R.id.Button_BuscarInfraccion1);
		button_VerificarInfraccion2 = (Button) findViewById(R.id.Button_BuscarInfraccion2);

		editText_CODInfraccion_1 = (EditTextWithDeleteButton) findViewById(R.id.EditText_CODInfraccion_1) ;
		editText_CODInfraccion_2 = (EditTextWithDeleteButton) findViewById(R.id.EditText_CODInfraccion_2) ;
		editText_CODInfraccion_3 = (EditTextWithDeleteButton) findViewById(R.id.EditText_CODInfraccion_3) ;
		editText_CODInfraccion_4 = (EditTextWithDeleteButton) findViewById(R.id.EditText_CODInfraccion_4) ;
		editText_CODInfraccion_5 = (EditTextWithDeleteButton) findViewById(R.id.EditText_CODInfraccion_5) ;
		editText_CODInfraccion_6 = (EditTextWithDeleteButton) findViewById(R.id.EditText_CODInfraccion_6) ;

		editText_CODInfraccion_1.handlerClearText = new HandlerClearTextInterface() {
			@Override
			public void onClearTextItem(View v) {

				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_INFRACCION1, -2).commit();
				fillJuzgadoSpinner();
			}
		};

		editText_CODInfraccion_2.handlerClearText = new HandlerClearTextInterface() {
			@Override
			public void onClearTextItem(View v) {

				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_INFRACCION2, -2).commit();
				fillJuzgadoSpinner();
			}
		};
		editText_CODInfraccion_3.handlerClearText = new HandlerClearTextInterface() {
			@Override
			public void onClearTextItem(View v) {

				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_INFRACCION3, -2).commit();
				fillJuzgadoSpinner();
			}
		};
		editText_CODInfraccion_4.handlerClearText = new HandlerClearTextInterface() {
			@Override
			public void onClearTextItem(View v) {

				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_INFRACCION4, -2).commit();
				fillJuzgadoSpinner();
			}
		};

		editText_CODInfraccion_5.handlerClearText = new HandlerClearTextInterface() {
			@Override
			public void onClearTextItem(View v) {

				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_INFRACCION5, -2).commit();
				fillJuzgadoSpinner();
			}
		};

		editText_CODInfraccion_6.handlerClearText = new HandlerClearTextInterface() {
			@Override
			public void onClearTextItem(View v) {

				mCurrenActaSettings.edit().putInt(CURRENT_ACTA_INFRACCION6, -2).commit();
				fillJuzgadoSpinner();
			}
		};

		myMarcaSpinner = (Spinner) findViewById(R.id.Spinner_Marca);
		myColorSpinner = (Spinner) findViewById(R.id.Spinner_Color);

		editText_Marca = (EditText) findViewById(R.id.EditText_Marca) ;
		editText_Marca.setFilters(filterForAllText);


		editText_Color = (EditText) findViewById(R.id.EditText_Color) ;
		editText_Color.setFilters(filterForAllText);

		editText_ModeloVehiculo = (EditText) findViewById(R.id.EditText_Modelo) ;
		editText_ModeloVehiculo.setFilters(filterForAllText);

		editText_AnioModeloVehiculo = (EditText) findViewById(R.id.EditText_AnioModeloVehiculo) ;

		textView_AlcoholEnSangre = findViewById(R.id.TextView_AlcoholEnSangre);
		editText_AlcoholEnSangre = findViewById(R.id.EditText_AlcoholEnSangre);

		editText_Observaciones = (EditText) findViewById(R.id.EditText_Observaciones) ;
		editText_Observaciones.setFilters(filterForAllText);

		editText_Observaciones2 = (EditText) findViewById(R.id.EditText_Observaciones2) ;
		editText_Observaciones2.setFilters(filterForAllText);
		/**/
		//customEdit.addTextChangedListener(editTextChanged());
		/**/

		textView_DescripcionInfraccion1 = (TextView) findViewById(R.id.TextView_DescripcionInfraccion1);
		textView_DescripcionInfraccion1.setVisibility(View.GONE);
		textView_DescripcionInfraccion2 = (TextView) findViewById(R.id.TextView_DescripcionInfraccion2);
		textView_DescripcionInfraccion2.setVisibility(View.GONE);

		button_GrabarActa = (Button) findViewById(R.id.Button_GrabarActa);
		button_CancelarActa = (Button) findViewById(R.id.Button_CancelarActa);

		button_GrabarActa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)	 {
				 //usarJuzgadoSegunConvenio();

				if (bGrabando==false)
				{
					bGrabando = true;

					doGrabarActa();
				}
				else
				{
					Utilities.ShowToast(CreateActaActivity.this,"procesando solicitud de Registro de Acta ");

				}

			}
		});
		button_CancelarActa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)	 {
			     doCancelarActa();
			}
		});

		button_VerificarInfraccion1.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View v)	 {

				doVerificacionInfraccion(1);

			}
		});
		button_VerificarInfraccion2.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View v) {
			     //doVerificacionInfraccion(2);
			}
		});


		Button_CopiarDatosTitular.setOnClickListener( new OnClickListener()
		{

			@Override
			public void onClick(View v) {


				TipoDocumento tipodoc = (TipoDocumento)  myTipoDocumentoTitularSpinner.getSelectedItem();
				SetSpinnerSelectionByValue(myTipoDocumentoAdapter,SPINNER_TIPO_DOCUMENTO,tipodoc.getId());

				String nrodoctitu = editText_NumeroDocumentoTitular.getText().toString();
				editText_NumeroDocumento.setText(nrodoctitu);


				editText_Apellido.setText(editText_ApellidoTitular.getText().toString());
				editText_Nombre.setText(editText_NombreTitular.getText().toString());


				CheckBox chkEsCallePublicaTitular = (CheckBox) findViewById(R.id.CheckBox_CallePublicaTitular);
				CheckBox chkEsCallePublica = (CheckBox) findViewById(R.id.CheckBox_CallePublica);
				Boolean esCallePublicaTitular = chkEsCallePublicaTitular.isChecked();

				editText_Calle.setText(editText_CalleTitular.getText().toString());
				chkEsCallePublica.setChecked(esCallePublicaTitular);

				CheckBox chkEsAlturaSinNumeroTitular= (CheckBox) findViewById(R.id.CheckBox_AlturaSinNumeroTitular);
				CheckBox chkEsAlturaSinNumero = (CheckBox) findViewById(R.id.CheckBox_AlturaSinNumero);
				Boolean  esAlturaSinNumeroTitular = chkEsAlturaSinNumeroTitular.isChecked();

				editText_Altura.setText(editText_AlturaTitular.getText().toString());
				chkEsAlturaSinNumero.setChecked(esAlturaSinNumeroTitular);


				editText_Piso.setText(editText_PisoTitular.getText().toString());
				editText_Depto.setText(editText_DeptoTitular.getText().toString());
				editText_Barrio.setText(editText_BarrioTitular.getText().toString());

				// ver si selecciono el pais no esta en la lista.. ver si se escribe el texto
				String[] sListeners = {
						   SPINNER_PAIS_DOCUMENTO
						  ,SPINNER_PROVINCIA_DOCUMENTO
						  ,SPINNER_DEPARTAMENTO_DOCUMENTO
						  ,SPINNER_LOCALIDAD_DOCUMENTO
						  };
				DeshabilitarListeners(sListeners);

				Pais paisTitular = (Pais) myPaisDTitularSpinner.getSelectedItem();
				final String pIdPais = paisTitular.getId();
				if(pIdPais!=null)
				{
				    fillProvinciaDSpinner(pIdPais);

				}

				Provincia provinciaTitular = (Provincia) myProvinciaDTitularSpinner.getSelectedItem();
				final String pIdProvincia = provinciaTitular.getId();
				if(pIdProvincia!=null)
				{
					fillDepartamentoDSpinner(pIdProvincia);
					if(pIdProvincia.equals("-1"))
					{
						String provincia  = editText_ProvinciaDocumentoTitular.getText().toString();
						editText_ProvinciaDocumento.setVisibility(View.VISIBLE);
						editText_ProvinciaDocumento.setText(provincia);
					}
				}


				Departamento departamentoTitular = (Departamento) myDepartamentoDTitularSpinner.getSelectedItem();
				final Integer pIdDepartamento = departamentoTitular.getId();
				if (pIdDepartamento!=null)
				{
					fillLocalidadDSpinner(pIdDepartamento.toString());
					if(pIdDepartamento ==-1)
					{
						String departamento  = editText_DepartamentoDocumentoTitular.getText().toString();
						editText_DepartamentoDocumento.setVisibility(View.VISIBLE);
						editText_DepartamentoDocumento.setText(departamento);
					}

				}


				Localidad localidadTitular = (Localidad) myLocalidadDTitularSpinner.getSelectedItem();
				final Integer pIdLocalidad = localidadTitular.getId();

				if(pIdLocalidad!=null)
				{
					if(pIdLocalidad ==-1)
					{
						String localidad  = editText_LocalidadDocumentoTitular.getText().toString();
						editText_LocalidadDocumento.setVisibility(View.VISIBLE);
						editText_LocalidadDocumento.setText(localidad);
					}
				}


				if (pIdPais!=null)
					SetSpinnerSelectionByValue(myPaisDAdapter,SPINNER_PAIS_DOCUMENTO,pIdPais);
				else
					myPaisDSpinner.setSelection(myPaisDSpinner.getCount());

				if (pIdProvincia!=null)
					SetSpinnerSelectionByValue(myProvinciaDAdapter,SPINNER_PROVINCIA_DOCUMENTO,pIdProvincia);
				else
					myProvinciaDSpinner.setSelection(myProvinciaDSpinner.getCount());

				if (pIdDepartamento!=null)
					SetSpinnerSelectionByValue(myDepartamentoDAdapter,SPINNER_DEPARTAMENTO_DOCUMENTO,pIdDepartamento.toString());
				else
					myDepartamentoDSpinner.setSelection(myDepartamentoDSpinner.getCount());


				if (pIdLocalidad!=null)
					SetSpinnerSelectionByValue(myLocalidadDAdapter,SPINNER_LOCALIDAD_DOCUMENTO,pIdLocalidad.toString());
				else
					myLocalidadDSpinner.setSelection(myLocalidadDSpinner.getCount());

				HabilitarListeners(sListeners);


				editText_CodigoPostal.setText(editText_CodigoPostalTitular.getText().toString());


				UIHelper.showAlertOnGuiThread(CreateActaActivity.this, "Se han copiado con exito los datos del Titular","Labrado de Actas",new Runnable() {

					@Override
					public void run() {

						if (pIdPais!=null)
							SetSpinnerSelectionByValue(myPaisDAdapter,SPINNER_PAIS_DOCUMENTO,pIdPais);
						else
							myPaisDSpinner.setSelection(myPaisDSpinner.getCount());

						if (pIdProvincia!=null)
							SetSpinnerSelectionByValue(myProvinciaDAdapter,SPINNER_PROVINCIA_DOCUMENTO,pIdProvincia);
						else
							myProvinciaDSpinner.setSelection(myProvinciaDSpinner.getCount());

						if (pIdDepartamento!=null)
							SetSpinnerSelectionByValue(myDepartamentoDAdapter,SPINNER_DEPARTAMENTO_DOCUMENTO,pIdDepartamento.toString());
						else
							myDepartamentoDSpinner.setSelection(myDepartamentoDSpinner.getCount());


						if (pIdLocalidad!=null)
							SetSpinnerSelectionByValue(myLocalidadDAdapter,SPINNER_LOCALIDAD_DOCUMENTO,pIdLocalidad.toString());
						else
							myLocalidadDSpinner.setSelection(myLocalidadDSpinner.getCount());

					}
				});
				 //UIHelper.showErrorOnGuiThread((Activity)PdaMenuActivity.this, e.getLocalizedMessage(), null);
				//ejemplo para Pais pais = (TipoDocumento)  myTipoDocumentoTitularSpinner.getSelectedItem();
				//SetSpinnerSelectionByValue(myTipoDocumentoAdapter,SPINNER_TIPO_DOCUMENTO,tipodoc.getId());

				/*acta.setoPaisDomicilioTitular((Pais) myPaisDSpinner.getSelectedItem());
				if (acta.getIdPaisDomicilioTitular().equals("-1"))  acta.setPaisDomicilioTitular(editText_PaisDocumento.getText().toString());

				acta.setoProvinciaDomicilioTitular((Provincia) myProvinciaDSpinner.getSelectedItem());
				if (acta.getIdProvinciaDomicilioTitular().equals("-1"))  acta.setProvinciaDomicilioTitular(editText_ProvinciaDocumento.getText().toString());

				acta.setoDepartamentoDomicilioTitular((Departamento) myDepartamentoDSpinner.getSelectedItem());
				if (acta.getIdDepartamentoDomicilioTitular()!=null && acta.getIdDepartamentoDomicilioTitular().equals(-1))  acta.setDepartamentoDomicilioTitular(editText_DepartamentoDocumento.getText().toString());

				acta.setoLocalidadDomicilioTitular((Localidad) myLocalidadDSpinner.getSelectedItem());
				if (acta.getIdLocalidadDomicilioTitular() !=null && acta.getIdLocalidadDomicilioTitular().equals(-1))  acta.setLocalidadDomicilioTitular(editText_LocalidadDocumento.getText().toString());

				acta.setCodigoPostalTitular(editText_CodigoPostal.getText().toString());
				// se copiaron los datos del conductor
				*/

			}
		});

		Button_VerificarCodigoQR.setOnClickListener( new OnClickListener()
		{
			//btn_LicanciaSearch_Click  in MC7596
			@Override
			public void onClick(View v) {
			   //doVerificarCodigoQR("");
			    /* dialgo para que seleccione el tipo de lector a utilizar zxing o microblink */
			 	showDialog(SCAN_QR_CODE_DIALOG_ID,null);
			}
		});

		button_VerificarLicencia.setOnClickListener( new OnClickListener()
		{
			//btn_LicanciaSearch_Click  in MC7596
			@Override
			public void onClick(View v) {
			  String licencia = editText_NumeroLicencia.getText().toString();
			  if (licencia==null || licencia.equals(""))
			  {
				  UIHelper.showAlert((Activity) CreateActaActivity.this,"Debe Ingresar un numero de licencia para realizar la verificacion", "Verificacion Licencia", null);
				  return;
			  }
			  else
			  { // contiene carateres  verificar la cantidad minima
				  Integer minCarLic = GlobalVar.getInstance().getSuportTable().getLicenciaMinCar();
				  Integer currCarLic = licencia.length();
				  if (currCarLic<minCarLic)
				  {
					  UIHelper.showAlert((Activity) CreateActaActivity.this,"La cadena de busqueda por licencia debe ser de por lo menos " +  minCarLic , "Verificacion Licencia", null);
					  return;
				  }
			  }

			  //doVerificarLicencia(licencia);
			}
		});

		button_VerificarLicencia.setVisibility(View.GONE);


	   button_VerificarDominio.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View v) {

				  String dominio = editText_Dominio.getText().toString();
				  if (dominio==null || dominio.equals(""))
				  {
					  UIHelper.showAlert((Activity) CreateActaActivity.this,"Debe Ingresar un Dominio para realizar la verificacion", "Verificacion Dominio", null);
					  return;
				  }
				//onSearchRequested();
				 Intent intent = new Intent(Intent.ACTION_SEARCH);
				 Bundle appSearchData = new Bundle();
				 appSearchData.putString(DominioSearchableActivity.APP_DATA_OBJETO_BUSQUEDA, "DOMINIO");
				 appSearchData.putString(DominioSearchableActivity.APP_DATA_LABEL_SEARCH, "Busqueda por Dominio");
				 appSearchData.putInt(DominioSearchableActivity.APP_DATA_HINT_SEARCH, R.string.searchable_hint_default);
				 appSearchData.putString(DominioSearchableActivity.APP_DATA_HEADER_LIST, "Dominios");
				 String initialQuery = editText_Dominio.getText().toString();

				 /*if(!initialQuery.isEmpty())
				 { // buscamos verificacion Policial
					doVerificarPolicial(VerificacionPolicialSync.AUTOMOTORES, "VE", initialQuery);
				 }*/

		    	 intent.putExtra(SearchManager.APP_DATA, appSearchData);

		         if (!TextUtils.isEmpty(initialQuery)) {
		             intent.putExtra(SearchManager.QUERY, initialQuery);
		         }
				 intent.setClass(CreateActaActivity.this,DominioSearchableActivity.class);
				 startActivityForResult(intent, CreateActaActivity.SEARCH_DOMINIO);
			}
		});
		/*
		button_VerificarDominio.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick(View v) {
			  String dominio = editText_Dominio.getText().toString();
			  if (dominio==null || dominio.equals(""))
			  {
				  UIHelper.showAlert((Activity) CreateActaActivity.this,"Debe Ingresar el Dominio o Patente para realizar la verificacion", "Verificacion Dominio", null);
				  return;
			  }

			  String pTipoObjetoBusqueda = VerificacionPolicialSync.AUTOMOTORES;

			RadioButton optTipoVehiculoA = (RadioButton) findViewById(R.id.RadioButton_Automotor);
			if(optTipoVehiculoA.isChecked())
				pTipoObjetoBusqueda = VerificacionPolicialSync.AUTOMOTORES;
			else
				pTipoObjetoBusqueda = VerificacionPolicialSync.MOTOVEHICULOS;

			  String pTipoBusqueda = VerificacionPolicialSync.DOMINIO;
			  String pValor = editText_Dominio.getText().toString();

			  doVerificarPolicial(pTipoObjetoBusqueda, pTipoBusqueda, pValor);
			}
		});*/

		button_VerificarDocumento.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View v) {
				//onSearchRequested();
				 Intent intent = new Intent(Intent.ACTION_SEARCH);
				 Bundle appSearchData = new Bundle();
				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_OBJETO_BUSQUEDA, "PERSONA");
				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_LABEL_SEARCH, "Busqueda por Documento");
				 appSearchData.putInt(PersonaSearchableActivity.APP_DATA_HINT_SEARCH, R.string.searchable_hint_default);
				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_HEADER_LIST, "Personas");
				 String initialQuery = editText_NumeroDocumento.getText().toString();

				 /*if(!initialQuery.isEmpty())
				 { // buscamos verificacion Policial
					doVerificarPolicial(VerificacionPolicialSync.PERSONAS, "PE", initialQuery);
				 }*/
		    	 intent.putExtra(SearchManager.APP_DATA, appSearchData);

		         if (!TextUtils.isEmpty(initialQuery)) {
		             intent.putExtra(SearchManager.QUERY, initialQuery);
		         }
				 intent.setClass(CreateActaActivity.this,PersonaSearchableActivity.class);
				 startActivityForResult(intent, CreateActaActivity.SEARCH_PERSONA);
			}
		});

		button_VerificarDocumentoTitular.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View v) {
				//onSearchRequested();
				 Intent intent = new Intent(Intent.ACTION_SEARCH);
				 Bundle appSearchData = new Bundle();
				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_OBJETO_BUSQUEDA, "PERSONA");
				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_LABEL_SEARCH, "Busqueda por Documento");
				 appSearchData.putInt(PersonaSearchableActivity.APP_DATA_HINT_SEARCH, R.string.searchable_hint_default);
				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_HEADER_LIST, "Personas");
				 String initialQuery = editText_NumeroDocumentoTitular.getText().toString();

				 /*if(!initialQuery.isEmpty())
				 { // buscamos verificacion Policial
					doVerificarPolicial(VerificacionPolicialSync.PERSONAS, "PE", initialQuery);
				 }*/

		    	 intent.putExtra(SearchManager.APP_DATA, appSearchData);

		         if (!TextUtils.isEmpty(initialQuery)) {
		             intent.putExtra(SearchManager.QUERY, initialQuery);
		         }
				 intent.setClass(CreateActaActivity.this,PersonaSearchableActivity.class);
				 startActivityForResult(intent, CreateActaActivity.SEARCH_PERSONA_TITULAR);
			}
		});

		/*verificar documento del testigo */
		button_VerificarDocumentoTestigo.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View v) {
				//onSearchRequested();
				 Intent intent = new Intent(Intent.ACTION_SEARCH);
				 Bundle appSearchData = new Bundle();
				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_OBJETO_BUSQUEDA, "PERSONA");
				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_LABEL_SEARCH, "Busqueda por Documento");
				 appSearchData.putInt(PersonaSearchableActivity.APP_DATA_HINT_SEARCH, R.string.searchable_hint_default);
				 appSearchData.putString(PersonaSearchableActivity.APP_DATA_HEADER_LIST, "Personas");
				 String initialQuery = editText_NumeroDocumentoTestigo.getText().toString();

				 /*if(!initialQuery.isEmpty())
				 { // buscamos verificacion Policial
					doVerificarPolicial(VerificacionPolicialSync.PERSONAS, "PE", initialQuery);
				 }*/
		    	 intent.putExtra(SearchManager.APP_DATA, appSearchData);

		         if (!TextUtils.isEmpty(initialQuery)) {
		             intent.putExtra(SearchManager.QUERY, initialQuery);
		         }
				 intent.setClass(CreateActaActivity.this,PersonaSearchableActivity.class);
				 startActivityForResult(intent, CreateActaActivity.SEARCH_PERSONA_TESTIGO);
			}
		});

		// Retrieve the shared preferences
		mGameSettings = getSharedPreferences(GAME_PREFERENCES,Context.MODE_PRIVATE);

		initEditTextKM();
		// Initialize the avatar button
		initTakePhotos();
		// Initialize the nickname entry
		//initNicknameEntry();
		// Initialize the email entry
		//initEmailEntry();
		// Initialize the Password chooser
		//initPasswordChooser();
		// Initialize the Date picker
		//initDatePicker();
		// Initialize the spinner
		 // initGenderSpinner();
		initTiposVehiculoSpinner();
		 // initGenerosSpinner();
		 // initGenerosTitularSpinner();
		 // initGenerosTestigoSpinner();
		// Initialize the favorite place picker
		//initFavoritePlacePicker();

		// Initialize the spinner Paises de Licencia
		initTipoDocumentoListenter(editText_NumeroDocumentoTitular, myTipoDocumentoTitularSpinner);
		initTipoDocumentoListenter(editText_NumeroDocumentoTestigo, myTipoDocumentoTestigoSpinner);
		initTipoDocumentoListenter(editText_NumeroDocumento, myTipoDocumentoSpinner);

		  initPaisesSpinner();
		  //initPaisesListeners();
		  initProvinciasSpinner();
		  //initProvinciasListeners();
		  //initDepartamentosSpinner();
		  //initDepartamentosListeners();
		  //initLocalidadesSpinner();
		  //initLocalidadesListeners();

		  initPaisesDSpinner();
		  initPaisesDTitularSpinner();
		  initPaisesDTestigoSpinner();

		  //initPaisesDListeners();

		  initProvinciasDSpinner();
		  initProvinciasDTitularSpinner();
		  initProvinciasDTestigoSpinner();
		  //initProvinciasDListeners();


		  initDepartamentosDSpinner();
		  initDepartamentosDTitularSpinner();
		  initDepartamentosDTestigoSpinner();
		  //initDepartamentosDListeners();


		  initLocalidadesDSpinner();
		  initLocalidadesDTitularSpinner();
		  initLocalidadesDTestigoSpinner();
		  //initLocalidadesDListeners();



		  initEntidadesSpinner();
		  initEntidadesListeners();

		  initSeccionalesSpinner();
		  initSeccionalesListeners();
		// Initialize the spinner Ruta
		  initTipoRutaSpinner();
		  initTipoRutaListeners();

		  //DepartamentoInfraccion
		  initDepartamentoInfraccionSpinner();
		  initDepartamentoInfraccionListeners();

		  //LocalidadInfraccion
		  initLocalidadInfraccionSpinner();
		  initLocalidadInfraccionListeners();


		  initTipoVehiculoListeners();
		// Initialize the spinner Ruta
		  initRutaSpinner();
		  initRutaListeners();

		  initMarcasSpinner();
		  initMarcasListeners();

		  initColoresSpinner();
		  initColoresListeners();

		// Initialize the spinner Documento
		  initDocumentosSpinner();
		  initDocumentosTitularSpinner();
		  initDocumentosTestigoSpinner();

		  initNoTieneLicenciaCheckBox();

		  initNoRegistraConductorCheckBox();
		  //initConductorTitularCheckBox();

		  initNoRegistraTitularCheckBox();

		  initCallePublicaCheckBox();
    	  initAlturaSinNumeroCheckBox();

    	  initCallePublicaTitularCheckBox();
    	  initAlturaSinNumeroTitularCheckBox();

    	  initCallePublicaTestigoCheckBox();
    	  initAlturaSinNumeroTestigoCheckBox();

    	  if(editText_Referencia.hasFocus())
    		  ocultarTeclado(editText_Referencia);

    	  //    	  mHomeKeyLocker = new HomeKeyLocker();
//    	  mHomeKeyLocker.lock(this);
    }
    /**
     *  The onCreate callback method.
     *  This method is part of the android activity lifecycle.
     *  In this method we create an instance of SomeExpensiveObject.
     */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		/*
		onCreate
		onCreate is where we initialize the activity and inflate the User Interface.
		It also receives a bundle containing the state of the activity as saved in onSaveInstanceState. You can use this bundle to restore the activity to its previous state in onCreate.
		The same bundle is also passed to onRestoreInstanceState where you can also restore the previous state.

		When is onCreate called?
		-> onCreate is called once at the start of the activity's full lifetime.
		*/



		Tools.Log(Log.ERROR, TAG, "onCreate");

		//requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		super.onCreate(savedInstanceState);


        setProgressBarVisibility(true);
        getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);


		mCurrenActaSettings = getSharedPreferences(CURRENT_ACTA_PREFERENCES,Context.MODE_PRIVATE);

		if (savedInstanceState==null)
		{
			mCurrenActaSettings.edit().clear().commit();
		}
		else //(savedInstanceState !=null)
		{
			this.IsRecreating = true;
			this.IsRestoringLastData = false;
		}
		setProgressBarIndeterminateVisibility(true);

		 //Initialize a LoadViewTask object and call the execute() method
	    //   loadViewTask = new LoadViewTask();
	    //   loadViewTask.execute();
		// con tiempo se puede hacer 2 task secuenciales con el objeto executor  (by ale)
		  /**
         * This is the turning point.
         * We first try o get an instance of SomeExpensiveObject using the
         * getLastNonConfigurationInstance method.  If the instance is null
         * we go ahead and create an instance.
         * Creating an instance of SomeExpensiveObject
         * is a time consuming operation.
         */
        someExpensiveObject = (SomeExpensiveObject) getLastCustomNonConfigurationInstance();
        if(someExpensiveObject == null) {
            someExpensiveObject = doSomeExpensiveOperation();
        }

	       setContentView(R.layout.create_acta);
	       InitializeActivity();

	       setProgressBarIndeterminateVisibility(false);
			setProgressBarVisibility(false);
		// Set up the action bar to show tabs.
//			final ActionBar actionBar = getActionBar();
//			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

			// For each of the sections in the app, add a tab to the action bar.
//			actionBar.addTab(actionBar.newTab().setText("2").setTabListener(this));
//			actionBar.addTab(actionBar.newTab().setText("3").setTabListener(this));//R.string.title_section2
//			actionBar.addTab(actionBar.newTab().setText("4").setTabListener(this));
	}

	 private Boolean EstaEnRangoConvenio(String pConvenio,Integer pIdRuta,String pKm)
     {
		 Boolean bRespuesta = false;

         try
         {
        	 RutaRules rutaRules = new RutaRules(this);
           bRespuesta = rutaRules.EstaEnRangoConvenio(pConvenio, pIdRuta, pKm);//GlobalArea.PluginManager.GetActivePlugin.EstaEnRangoConvenio(sConvenio, pIdRuta, pKm);
         }
         catch(Exception ex)
         { }
         //throw new Exception("Falta Definir la Consulta a la base de Datos");

         return bRespuesta;
     }
	private Boolean usarJuzgadoSegunConvenio()
	{
		/* falta definir el comportamiento de cuando se deba o no utilizar el convenio para seleccion de un juzgado */
        String sUsarParidadJuzgado = GlobalVar.getInstance().getSuportTable().getUsarParidadJuzgado();
        String sUsarRutaKMConvenio = GlobalVar.getInstance().getSuportTable().getUsarRutaKmConvenio();




        if (!sUsarParidadJuzgado.equals("S"))
            return false;
        /* Por Ahoras es para todas las Rutas el Convenio de Mendoza
        if (sUsarRutaKMConvenio.equals("S"))
        {
            int iIdRuta = 0;
            try
            {
                iIdRuta =Integer.parseInt( ((Ruta) myRutaSpinner.getSelectedItem()).getId()   ); // Convert.ToInt32(cmbNumRuta.SelectedValue);
            }
            catch(Exception ex)
            {

            }

            String sKm = "0";
            sKm = editText_Km.getText().toString().trim(); //this.txt_Km.Text.Trim();
            if (sKm.equals(""))
                sKm = "0";

            if (!EstaEnRangoConvenio("CBA", iIdRuta, sKm))
                return false;
        }
        */

        Boolean bResultado = false;
        Integer iCantLocalidades = myLocalidadDSpinner.getCount();//>1

        // DETERMINAR SEGUN EL NRO DE ACTA PROXIMO  PENULTIMO DIGITO   (NO EL DIGITO VERIFICADOR)
        //if (cmbLocalidadCond.Items.Count > 0)
        if (iCantLocalidades > 1) // contamos con el items seleccione una localidad
        {
            Integer idLocalidad ;
            try
            {
                idLocalidad = ((Localidad) myLocalidadDSpinner.getSelectedItem()).getId();//Convert.ToInt32(cmbLocalidadCond.SelectedValue);
                if (idLocalidad!=null && idLocalidad == 1) // Chaco capital
                {
                	ActaConstatacionRules actaRules = new ActaConstatacionRules(this);

                    String NrodeActaConDigitoVerificador = actaRules.getNextNumeroActa();//generateNroActa();
                    String sUltimoDigito = NrodeActaConDigitoVerificador.substring(10, 11);
                    try
                    {
                        int iUltimoDigito = Integer.parseInt(sUltimoDigito);//Convert.ToInt32(sUltimoDigito);
                        int resto = iUltimoDigito % 2;

                        //EntidadServices servicesEntidad = new EntidadServices();

                        //this.cmb_juzgados.ValueMember = "ID_ENTIDAD";
                        //this.cmb_juzgados.DisplayMember = "NOMBRE";
                        Entidad ent;
                        EntidadRules entidadRules = new EntidadRules(this);
                        if (resto == 0) // resto 0 es par
                        {
                            //seleccionar Juzgado Adm Municipal Chaco
                        	Integer idJuzgadoMunicipal = 4067;
                        	try{
                        		idJuzgadoMunicipal = GlobalVar.getInstance().getSuportTable().getIdEntidadJuzgadoMunicipalCba();
                        	}  catch(Exception ex){}
                            ent = entidadRules.getEntidadById(idJuzgadoMunicipal);//servicesEntidad.getEntidad(4067);
                        }
                        else
                        {
                            //seleccionar Juzgado Policial Chaco
                        	Integer idJuzgadoPolicial = 3061;
                        	try{
                        		idJuzgadoPolicial = GlobalVar.getInstance().getSuportTable().getIdEntidadJuzgadoPolicialCba();
                        	}  catch(Exception ex){}
                            ent = entidadRules.getEntidadById(idJuzgadoPolicial);//servicesEntidad.getEntidad(3061);
                        }
                        //setear lo seleccionado en preferences y cargar el dropdown
                        List<Entidad> list = new ArrayList<Entidad>();
                        list.add(ent);

                        Entidad pEntidad = new Entidad(-1,"Seleccione una Unidad de Resolucion Vial"); // Juzgado");
            			list.add(pEntidad);
            			itemsEntidad = new Entidad[list.size()];
            			list.toArray(itemsEntidad);

            			someExpensiveObject.lstEntidades = list;

        			    myEntidadAdapter = new GenericSpinAdapter<Entidad>(this, layout.simple_spinner_item,itemsEntidad);
        				myEntidadAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        				myEntidadSpinner.setAdapter(myEntidadAdapter);

        				int cantItems = myEntidadSpinner.getCount();
        				if (cantItems==1)
        				{
        					myEntidadSpinner.setSelection(0);
        					mCurrenActaSettings.edit().putInt(CURRENT_ACTA_ENTIDAD,0).commit();
        				}
        				else
        				{  int iposicion = myEntidadSpinner.getCount();
        					myEntidadSpinner.setSelection(iposicion);
        					mCurrenActaSettings.edit().putInt(CURRENT_ACTA_ENTIDAD,iposicion).commit();
        				}
                        /*
                        DataTable dtJuzSegunEntidad = new DataTable();
                        dtJuzSegunEntidad.Columns.Add("ID_ENTIDAD", typeof(int));
                        dtJuzSegunEntidad.Columns.Add("NOMBRE", typeof(string));
                        dtJuzSegunEntidad.Rows.Add(ent.Id_Entidad,ent.Nombre);
                        this.cmb_juzgados.DataSource = dtJuzSegunEntidad;
                        this.cmb_juzgados.SelectedIndex = 0;
                        */
                        bResultado = true;
                    }
                    catch(Exception ex)
                    {

                    }
                }
            }
            catch(Exception ex )
            {
            }

        }

        return bResultado;
	}

	private Spinner mySeccionalSpinner;
	private GenericSpinAdapter<Seccional> mySeccionalAdapter;
	private Seccional[] itemsSeccional;
	private boolean isSpinnerInitialSeccional = true;

	private void initSeccionalesListeners()
	{
		/*
		myEntidadSpinner.setSelection(myEntidadSpinner.getCount());
		textView_DescripcionEntidad.setVisibility(View.GONE);
		*/
		mySeccionalSpinner.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Tools.Log(Log.ERROR, "mySeccionalSpinner", "onTouch");
				IsRecreating = false;
				CreateActaActivity.this.IsRestoringLastData = false;
				isSpinnerInitialSeccional = false;
				return false;
			}
		 });
		mySeccionalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	            	  @Override
	                  public void onItemSelected(AdapterView<?> parent,
	                          View view,
	                          int position,
	                          long id) {

	            		  if(isSpinnerInitialSeccional) { isSpinnerInitialSeccional = false; return;}

	            		  if (CreateActaActivity.this.IsRestoringLastData== true) return;

	                	   //Entidad d = itemsEntidad[position];
	            		  Seccional d = mySeccionalAdapter.getItem(position);
	                	   /*
	                	   if (d.getId()==null || d.getId().equals(""))//.equals("")
	                	   {  // si viene vacio solo ocultamos la descripcion
	                		   textView_DescripcionEntidad.setVisibility(View.GONE);
	                		   return;
	                	   }*/

	            		   mCurrenActaSettings.edit().putInt(CURRENT_ACTA_SECCIONAL,position).commit();
	                	   //Toast.makeText(QuizCreateActaActivity.this, "IDEntidad: " + d.getId() + " Entidad: " + d.getItemName(),Toast.LENGTH_SHORT).show();
	            		   /*
	                	   if (d.getId().equals("-1"))
	                	   {
	                		   textView_DescripcionEntidad.setVisibility(View.GONE);
	                	   }
	                	   else
	                	   {
	                		   textView_DescripcionEntidad.setText("Buscando Datos del Juzgado...");
	                		   textView_DescripcionEntidad.setVisibility(View.VISIBLE);
	                	   }

	                	   String ParametroSeccional= d.getId().toString();
              		       //fillDescripcionSeccionalTextView(ParametroSeccional);
	                	   */

	                  }
	            	  @Override
	                  public void onNothingSelected(AdapterView<?> parent) {
	                  }
	              }
	          );
	}

	private void initSeccionalesSpinner()
	{


		/*EntidadRules entidadRules = new EntidadRules(this);
		List<Entidad> list = entidadRules.getAll();
 	    Entidad pEntidad = new Entidad("-1","Seleccione una Entidad");
		list.add(pEntidad);
		*/

		itemsSeccional = new Seccional[someExpensiveObject.lstSeccionales.size()];
		someExpensiveObject.lstSeccionales.toArray(itemsSeccional);


		mySeccionalAdapter = new GenericSpinAdapter<Seccional>(this, layout.simple_spinner_item,itemsSeccional);
		mySeccionalAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
		mySeccionalSpinner.setAdapter(mySeccionalAdapter);


	}

	/*  Juzgado */
	private Spinner myEntidadSpinner;
	private GenericSpinAdapter<Entidad> myEntidadAdapter;
	private Entidad[] itemsEntidad;
	private boolean isSpinnerInitialEntidad = true;
	private void initEntidadesListeners()
	{
		/*
		myEntidadSpinner.setSelection(myEntidadSpinner.getCount());
		textView_DescripcionEntidad.setVisibility(View.GONE);
		*/
		myEntidadSpinner.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Tools.Log(Log.ERROR, "myEntidadSpinner", "onTouch");
				IsRecreating = false;
				CreateActaActivity.this.IsRestoringLastData = false;
				isSpinnerInitialEntidad = false;
				return false;
			}
		 });
		myEntidadSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	            	  @Override
	                  public void onItemSelected(AdapterView<?> parent,
	                          View view,
	                          int position,
	                          long id) {

	            		  if(isSpinnerInitialEntidad) { isSpinnerInitialEntidad = false; return;}

	            		  if (CreateActaActivity.this.IsRestoringLastData== true) return;

	                	   //Entidad d = itemsEntidad[position];
	                	   Entidad d = myEntidadAdapter.getItem(position);

	                	   if (d.getId()==null || d.getId().equals(""))//.equals("")
	                	   {  // si viene vacio solo ocultamos la descripcion
	                		   textView_DescripcionEntidad.setVisibility(View.GONE);
	                		   return;
	                	   }

	            		   mCurrenActaSettings.edit().putInt(CURRENT_ACTA_ENTIDAD,position).commit();
	                	   //Toast.makeText(QuizCreateActaActivity.this, "IDEntidad: " + d.getId() + " Entidad: " + d.getItemName(),Toast.LENGTH_SHORT).show();

	                	   if (d.getId().equals("-1"))
	                	   {
	                		   textView_DescripcionEntidad.setVisibility(View.GONE);
	                	   }
	                	   else
	                	   {
	                		   textView_DescripcionEntidad.setText("Buscando Datos del Juzgado...");
	                		   textView_DescripcionEntidad.setVisibility(View.VISIBLE);
	                	   }
	                	   String ParametroEntidad = d.getId().toString();
              		       fillDescripcionEntidadTextView(ParametroEntidad);


	                  }
	            	  @Override
	                  public void onNothingSelected(AdapterView<?> parent) {
	                  }
	              }
	          );
	}

	private void initEntidadesSpinner()
	{


		/*EntidadRules entidadRules = new EntidadRules(this);
		List<Entidad> list = entidadRules.getAll();
 	    Entidad pEntidad = new Entidad("-1","Seleccione una Entidad");
		list.add(pEntidad);
		*/

		itemsEntidad = new Entidad[someExpensiveObject.lstEntidades.size()];
		someExpensiveObject.lstEntidades.toArray(itemsEntidad);


		myEntidadAdapter = new GenericSpinAdapter<Entidad>(this, layout.simple_spinner_item,itemsEntidad);
		myEntidadAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
		myEntidadSpinner.setAdapter(myEntidadAdapter);


	}

	private void fillDescripcionEntidadTextView(String pIdEntidad)
	{

		if (pIdEntidad==null || pIdEntidad.equals("") || pIdEntidad.equals("-1") )
		{
			return;
		}
		EntidadDao entidadDao = new EntidadDao();
		Entidad entidad = entidadDao.getItem(pIdEntidad);

		String sEntidad = entidad.getId().toString();
		String sDomicilio = "Calle : " + entidad.getDomicilioCalle() +  "\nLocalidad : " + entidad.getDomicilioLocalidad()  + " | CP. "+ entidad.getDomicilioCodigoPostal() ;

		String DetalleEntidad = "Entidad N° : " +  sEntidad + "\n" + sDomicilio;

		textView_DescripcionEntidad.setText(DetalleEntidad);
		textView_DescripcionEntidad.setVisibility(View.VISIBLE);
		// ver de mostrar la descripcion del Juzgado
	}

	private void usarJuzgadoPolicial()
	{
		Entidad ent;
		EntidadRules entidadRules = new EntidadRules(this);
		//seleccionar Juzgado Policial Chaco
		//usaremos el juzgado policial que este en la suporttaqble
		Integer idJuzgadoPolicial = 3061;
		try
		{
			idJuzgadoPolicial  = GlobalVar.getInstance().getSuportTable().getIdEntidadJuzgadoPolicialCba();
		}
		catch(Exception ex){}

		ent = entidadRules.getEntidadById(idJuzgadoPolicial);//servicesEntidad.getEntidad(3061);
		List<Entidad> list = new ArrayList<Entidad>();
		list.add(ent);
		Entidad pEntidad = new Entidad(null,"Seleccione una Unidad de Resolucion Vial ");//Juzgado");
		list.add(pEntidad);
		itemsEntidad = new Entidad[list.size()];
		list.toArray(itemsEntidad);
		someExpensiveObject.lstEntidades = list;
		myEntidadAdapter = new GenericSpinAdapter<Entidad>(this, layout.simple_spinner_item,itemsEntidad);
		myEntidadAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
		myEntidadSpinner.setAdapter(myEntidadAdapter);
		myEntidadSpinner.setSelection(0);
		mCurrenActaSettings.edit().putInt(CURRENT_ACTA_ENTIDAD,0).commit();

	}
	public void fillJuzgadoSpinner(){
	    // verificar los datos Ingresados en Ruta TipoRuta y Km y ver si lo  de la paridad y el convenio existente

		Integer pIdInfraccion01 = 0;
		Integer pIdInfraccion02 = 0;
		if(editText_CODInfraccion_1.getIdItem()!=null && editText_CODInfraccion_1.getObjectItem()!=null)
		{
			//acta.setIdInfraccion1(Integer.parseInt(editText_CODInfraccion_1.getIdItem()));
			pIdInfraccion01 = ((InfraccionNomenclada)editText_CODInfraccion_1.getObjectItem()).getId();
		}

		if(editText_CODInfraccion_2.getIdItem()!=null && editText_CODInfraccion_2.getObjectItem()!=null)
		{
			//acta.setIdInfraccion2(Integer.parseInt(editText_CODInfraccion_2.getIdItem()));
			pIdInfraccion02 = ((InfraccionNomenclada)editText_CODInfraccion_2.getObjectItem()).getId();
		}
		/*  esto no va para chaco porque no tiene la misma logica para valores pares e impares
		Boolean esParaJuzgadoPolicial = Utilities.EsParaJuzgadoPolicial(getApplicationContext(), pIdInfraccion01, pIdInfraccion02);

		if(esParaJuzgadoPolicial==true)
		{
			usarJuzgadoPolicial();
			return;
		}

		if (usarJuzgadoSegunConvenio()==true)
		{
			return;
		}
		*/

		if (itemsRuta==null || itemsRuta.length<=1)
			return;

		// voy a Anular la busqueda de una Unidad de resolucion vial basada en los KM Ruta etc.
		//Al dia de la fecha no hay un esquema definido para este comportamiento
		return;
		/*
		EntidadRules entidadRules = new EntidadRules(this);
		String sIdRuta =itemsRuta[myRutaSpinner.getSelectedItemPosition()].getId().toString();
		if (sIdRuta.equals(""))
			return;

		String sKm = editText_Km.getText().toString();

		if (sKm.trim().length()<=0)
			return;

		List<Entidad> list = entidadRules.getByRutaKM(sIdRuta, sKm);

		if (list.size()<=0)
		{
			list = entidadRules.getAll();
			Entidad pEntidad = new Entidad(null,"Seleccione una Unidad de Resolucion Vial ");// Juzgado");
			list.add(pEntidad);
			itemsEntidad = new Entidad[list.size()];
		}
		else
		{
			Entidad pEntidad = new Entidad(null,"Seleccione una Unidad de Resolucion Vial");// Juzgado");
			list.add(pEntidad);
			itemsEntidad = new Entidad[list.size()];
		}



		list.toArray(itemsEntidad);

		someExpensiveObject.lstEntidades = list;

	    myEntidadAdapter = new GenericSpinAdapter<Entidad>(this,android.R.layout.simple_spinner_item,itemsEntidad);
		myEntidadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		myEntidadSpinner.setAdapter(myEntidadAdapter);

		int cantItems = myEntidadSpinner.getCount();
		if (cantItems==1)
		{
			myEntidadSpinner.setSelection(0);
			mCurrenActaSettings.edit().putInt(CURRENT_ACTA_ENTIDAD,0).commit();
		}
		else
		    myEntidadSpinner.setSelection(myEntidadSpinner.getCount());
		*/
	}


	private void initEditTextKM()
	{  /*
		KmValidator kmvalidator = new KmValidator((EditText)editText_Km);
		kmvalidator.setHandlerListener(new HandlerValidatorInterface() {

			@Override
			public void onValidate (Boolean obj) {

				if (obj.equals(true))
				{
					fillJuzgadoSpinner();
				}
				else
				Toast.makeText(CreateActaActivity.this, "validado " + obj.toString(), Toast.LENGTH_LONG).show();
			}
		});
		editText_Km.setOnFocusChangeListener(kmvalidator);
		*/
	}

	private void initValidators()
	{


	}
	private String transformDataLicencia(String psContents)
	{
		String data;

		if (psContents==null)//si el parametro es null es porque vamos a usar unos datos de prueba
		{
			AssetManager manager = getApplicationContext().getAssets();//.getContext()
		    InputStream input = null;
		    InputStreamReader in = null;
		    try {
		        input = manager.open("pdf417.txt");
		        in = new InputStreamReader(input);
		    } catch (IOException e1) {
		        Log.d("ERROR DETECTED", "ERROR WHILE TRYING TO OPEN FILE");
		    }
		    StringBuilder sbResultado =new StringBuilder();
		    try {
		      char current;
		      while (in.ready()) {
		        current = (char) in.read();
		        sbResultado.append(current);
		        Log.d("caracter ", " " + current );
		        int ascii = (int) current ;
		        Log.d("ascii ", "(" + ascii + ")");
		      }
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		    data = 	sbResultado.toString().replace((char)13, '|').replace((char)10,' ').replace((char)65456 ,' ');
		    char current;
		    int largo = data.length();
			for (int i=0;i<largo;i++)
			{
				current = data.charAt(i);
			    Log.d("Nuevo caracter ", " " + current );
		        int ascii = (int) current ;
		        Log.d("Nuevo ascii ", "(" + ascii + ")");
			}

		}
		else
		{
							//replace((char)13, '|').replace((char)10,' ').replace((char)65456 ,' ');
			data = psContents.replace((char)13, '|').replace((char)10,' ').replace((char)65456 ,' ');
		}

		return data;
	}

	private void writeToFile(String data,Context context) {
	    try {
    		File sdCardDirectory = Environment.getExternalStorageDirectory();
    		File file = new File(sdCardDirectory, "licencia.txt");

    		FileOutputStream stream = new FileOutputStream(file);
    		try {
    		    stream.write(data.getBytes());
    		} finally {
    		    stream.close();
    		}
	        //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("CodigoLicencia.txt", Context.MODE_PRIVATE));
	        //outputStreamWriter.write(data);
	        //outputStreamWriter.close();
	    }
	    catch (IOException e) {
	        Log.e("Exception", "File write failed: " + e.toString());
	    }
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Tools.Log(Log.ERROR, TAG, "onActivityResult");
		String sTag ="";

		super.onActivityResult(requestCode, resultCode, data);
		/*
		 		case REQUEST_CODE:


		 * */
		switch (requestCode) {
		case MY_REQUEST_CODE:
		{
			if (resultCode == Pdf417ScanActivity.RESULT_OK && data != null) {
				// perform processing of the data here

				// for example, obtain parcelable recognition result
				Bundle extras = data.getExtras();
				RecognitionResults result = data.getParcelableExtra(Pdf417ScanActivity.EXTRAS_RECOGNITION_RESULTS);

				// get array of recognition results
				BaseRecognitionResult[] resultArray = result.getRecognitionResults();
				// Each element in resultArray inherits BaseRecognitionResult class and
				// represents the scan result of one of activated recognizers that have
				// been set up. More information about this can be found in
				// "Recognition settings and results" chapter


				// Or, you can pass the intent to another activity
				//data.setComponent(new ComponentName(this, ResultActivity.class));
				//startActivity(data);
				String contents2="" ;
				try
				{
					for(BaseRecognitionResult res : resultArray) {
						if(res instanceof Pdf417ScanResult) { // check if scan result is result of Pdf417 recognizer
							Pdf417ScanResult resultado = (Pdf417ScanResult) res;
							contents2 = resultado.getStringData();
						}
					}
				}
				catch(Exception ex){
				 Log.e(TAG, "Error " +  ex.getMessage());
				}
				contents2 = transformDataLicencia(contents2);
				final String resultScaneo = contents2;

				handler.post(new Runnable() {
                    @Override
                    public void run() {


                        txtScanResult.setText(resultScaneo);
                    	//EnvioCodigoQRRequestTask
                        // make sure we don't collide with another pending update
                        if (envioCodigoQRRequestTask == null || envioCodigoQRRequestTask.getStatus() == AsyncTask.Status.FINISHED || envioCodigoQRRequestTask.isCancelled()) {
                        	envioCodigoQRRequestTask  = new EnvioCodigoQRRequestTask ();
                        	//envioCodigoQRRequestTask .setPostExecuteHandler(receptorVerificacionPolicialyLicenciaTask);
                        	envioCodigoQRRequestTask.execute(resultScaneo);
                        } else {
                            Log.w(DEBUG_TAG, "Warning: EnvioCodigQRTask already going");
                        }

                        //lo enviamos a stylenet para ver los codigos leidos
                    }
                });

				try{


				writeToFile(contents2,getApplicationContext());
				}catch(Exception ex){
					Log.e(TAG, "Error " +  ex.getMessage());
				}


				CodigoMancha PDF417code ;

				try
            	{
            		PDF417code = new CodigoMancha(contents2);


                    editText_NumeroLicencia.setText(PDF417code.CodigoSeguridadLicencia);
                	editText_ClaseLicencia.setText(PDF417code.IdClaseLicencia);
                	Date dtFVL = PDF417code.FechaVencimiento;
					CharSequence DateFLV = DateFormat.format("dd/MM/yyyy",dtFVL);
					editText_FVL_Info.setText(DateFLV);
					mCurrenActaSettings.edit().putString(CURRENT_ACTA_FECHA_VENCIMIENTO_LICENCIA,DateFLV.toString()).commit();
					try
					{
						CheckBox checkBox_LicenciaUP = (CheckBox) findViewById(R.id.CheckBox_LicenciaUnicaProvincial);
						checkBox_LicenciaUP.setChecked(true);
					}
					catch(Exception ex)
					{}
					String[] sListeners = {
							   SPINNER_PAIS_LICENCIA,SPINNER_PAIS_DOCUMENTO
							  ,SPINNER_PROVINCIA_LICENCIA,SPINNER_PROVINCIA_DOCUMENTO
							  ,SPINNER_DEPARTAMENTO_LICENCIA,SPINNER_DEPARTAMENTO_DOCUMENTO
							  ,SPINNER_LOCALIDAD_LICENCIA,SPINNER_LOCALIDAD_DOCUMENTO
							  };

					try
					{
						DeshabilitarListeners(sListeners);
						/*
						SetSpinnerSelectionByValue(myPaisAdapter,SPINNER_PAIS_LICENCIA,qrcode.IdPaisLicencia);
						// FALTA CARGAR LAS PROVINCIA DEL PAIS DEL CODIGO QR POR ESO NO SE MUESTRAN
						SetSpinnerSelectionByValue(myProvinciaAdapter,SPINNER_PROVINCIA_LICENCIA,qrcode.IdProvinciaLicencia);
						SetSpinnerSelectionByValue(myDepartamentoAdapter,SPINNER_DEPARTAMENTO_LICENCIA,qrcode.IdDepartamentoLicencia);
						SetSpinnerSelectionByValue(myLocalidadAdapter,SPINNER_LOCALIDAD_LICENCIA,qrcode.IdLocalidadLicencia);

						SetSpinnerSelectionByValue(myPaisDAdapter,SPINNER_PAIS_DOCUMENTO,qrcode.IdPaisConductor);
						SetSpinnerSelectionByValue(myProvinciaDAdapter,SPINNER_PROVINCIA_DOCUMENTO,qrcode.IdProvinciaConductor);
						SetSpinnerSelectionByValue(myDepartamentoDAdapter,SPINNER_DEPARTAMENTO_DOCUMENTO,qrcode.IdDepartamentoConductor);
						SetSpinnerSelectionByValue(myLocalidadDAdapter,SPINNER_LOCALIDAD_DOCUMENTO,qrcode.IdLocalidadConductor);
						*/
						SetSpinnerSelectionByValue(myTipoDocumentoAdapter,SPINNER_TIPO_DOCUMENTO,PDF417code.TipoDocumento);
						//SetSpinnerSelectionByValue(myGeneroAdapter,SPINNER_TIPO_GENERO,qrcode.IdSexo);

					}
					catch(Exception ex)
					{}
					finally
					{
						HabilitarListeners(sListeners);
					}

					editText_NumeroDocumento.setText(PDF417code.NumeroDocumento);
					editText_Apellido.setText(PDF417code.Apellido);
					editText_Nombre.setText(PDF417code.Nombre);
					editText_Calle.setText(PDF417code.Calle);
					editText_Altura.setText(PDF417code.NumeroCalle);
					editText_Piso.setText(PDF417code.Piso);
					editText_Barrio.setText(PDF417code.Barrio);
					editText_CodigoPostal.setText(PDF417code.CodigoPostal);

					editText_NumeroLicencia.requestFocus();
					if(editText_NumeroLicencia.hasFocus())
			    		  ocultarTeclado(editText_NumeroLicencia);


            	}
            	catch(Exception ex)
            	{
            		 UIHelper.showAlert(CreateActaActivity.this, "El Formato del Codigo de Barra de la Licencia no es valida", "Codigo Mancha Licencia", null);
                     editText_NumeroLicencia.requestFocus();
             		if(editText_NumeroLicencia.hasFocus())
			    		  ocultarTeclado(editText_NumeroLicencia);
            		 return;
            	}



			}
		}
		break;
		case REQUEST_CODE_PDF417:

			if (resultCode == Activity.RESULT_CANCELED) {
				// Search person mode was canceled.
				//Utilities.ShowToast(QuizMenuActivity.this, "Busqueda de Infraccion Cancelada");
			} else if (resultCode == Activity.RESULT_OK) {
					String contents = data.getStringExtra("SCAN_RESULT");
					String formatName = data.getStringExtra("SCAN_RESULT_FORMAT");
					//transformamos los char(13) y char(10)
					contents = transformDataLicencia(contents);

					final String resultScaneo = contents;
	                handler.post(new Runnable() {
	                    @Override
	                    public void run() {


	                        txtScanResult.setText(resultScaneo);
	                    	//EnvioCodigoQRRequestTask
	                        // make sure we don't collide with another pending update
	                        if (envioCodigoQRRequestTask == null || envioCodigoQRRequestTask.getStatus() == AsyncTask.Status.FINISHED || envioCodigoQRRequestTask.isCancelled()) {
	                        	envioCodigoQRRequestTask  = new EnvioCodigoQRRequestTask ();
	                        	//envioCodigoQRRequestTask .setPostExecuteHandler(receptorVerificacionPolicialyLicenciaTask);
	                        	envioCodigoQRRequestTask.execute(resultScaneo);
	                        } else {
	                            Log.w(DEBUG_TAG, "Warning: EnvioCodigQRTask already going");
	                        }

	                        //lo enviamos a stylenet para ver los codigos leidos
	                    }
	                });

					writeToFile(contents,getApplicationContext());



					CodigoMancha PDF417code ;

					try
                	{
                		PDF417code = new CodigoMancha(contents);
						/*
    					try
                    	{

                    		Serializer serializer = new Persister();
                    		File sdCardDirectory = Environment.getExternalStorageDirectory();
                    		File result = new File(sdCardDirectory,"PDF417code.txt");
                    		serializer.write(PDF417code, result);
                    	}
                    	catch(Exception ex)
                    	{
                    	}
						*/
	                    editText_NumeroLicencia.setText(PDF417code.CodigoSeguridadLicencia);
	                	editText_ClaseLicencia.setText(PDF417code.IdClaseLicencia);
	                	Date dtFVL = PDF417code.FechaVencimiento;
						CharSequence DateFLV = DateFormat.format("dd/MM/yyyy",dtFVL);
						editText_FVL_Info.setText(DateFLV);
						mCurrenActaSettings.edit().putString(CURRENT_ACTA_FECHA_VENCIMIENTO_LICENCIA,DateFLV.toString()).commit();
						try
						{
							CheckBox checkBox_LicenciaUP = (CheckBox) findViewById(R.id.CheckBox_LicenciaUnicaProvincial);
							checkBox_LicenciaUP.setChecked(true);
						}
						catch(Exception ex)
						{}
						String[] sListeners = {
								   SPINNER_PAIS_LICENCIA,SPINNER_PAIS_DOCUMENTO
								  ,SPINNER_PROVINCIA_LICENCIA,SPINNER_PROVINCIA_DOCUMENTO
								  ,SPINNER_DEPARTAMENTO_LICENCIA,SPINNER_DEPARTAMENTO_DOCUMENTO
								  ,SPINNER_LOCALIDAD_LICENCIA,SPINNER_LOCALIDAD_DOCUMENTO
								  };

						try
						{
							DeshabilitarListeners(sListeners);
							/*
							SetSpinnerSelectionByValue(myPaisAdapter,SPINNER_PAIS_LICENCIA,qrcode.IdPaisLicencia);
							// FALTA CARGAR LAS PROVINCIA DEL PAIS DEL CODIGO QR POR ESO NO SE MUESTRAN
							SetSpinnerSelectionByValue(myProvinciaAdapter,SPINNER_PROVINCIA_LICENCIA,qrcode.IdProvinciaLicencia);
							SetSpinnerSelectionByValue(myDepartamentoAdapter,SPINNER_DEPARTAMENTO_LICENCIA,qrcode.IdDepartamentoLicencia);
							SetSpinnerSelectionByValue(myLocalidadAdapter,SPINNER_LOCALIDAD_LICENCIA,qrcode.IdLocalidadLicencia);

							SetSpinnerSelectionByValue(myPaisDAdapter,SPINNER_PAIS_DOCUMENTO,qrcode.IdPaisConductor);
							SetSpinnerSelectionByValue(myProvinciaDAdapter,SPINNER_PROVINCIA_DOCUMENTO,qrcode.IdProvinciaConductor);
							SetSpinnerSelectionByValue(myDepartamentoDAdapter,SPINNER_DEPARTAMENTO_DOCUMENTO,qrcode.IdDepartamentoConductor);
							SetSpinnerSelectionByValue(myLocalidadDAdapter,SPINNER_LOCALIDAD_DOCUMENTO,qrcode.IdLocalidadConductor);
							*/
							SetSpinnerSelectionByValue(myTipoDocumentoAdapter,SPINNER_TIPO_DOCUMENTO,PDF417code.TipoDocumento);
							//SetSpinnerSelectionByValue(myGeneroAdapter,SPINNER_TIPO_GENERO,qrcode.IdSexo);

						}
						catch(Exception ex)
						{}
						finally
						{
							HabilitarListeners(sListeners);
						}

						editText_NumeroDocumento.setText(PDF417code.NumeroDocumento);
						editText_Apellido.setText(PDF417code.Apellido);
						editText_Nombre.setText(PDF417code.Nombre);
						editText_Calle.setText(PDF417code.Calle);
						editText_Altura.setText(PDF417code.NumeroCalle);
						editText_Piso.setText(PDF417code.Piso);
						editText_Barrio.setText(PDF417code.Barrio);
						editText_CodigoPostal.setText(PDF417code.CodigoPostal);

						editText_NumeroLicencia.requestFocus();
						if(editText_Referencia.hasFocus())
				    		  ocultarTeclado(editText_NumeroLicencia);


                	}
                	catch(Exception ex)
                	{
                		 UIHelper.showAlert(CreateActaActivity.this, "El Formato del Codigo de Barra de la Licencia no es valida", "Codigo Mancha Licencia", null);
	 		    		 return;
                	}


            }
            break;
		case SCANNER_QR_CODE:
				            if (resultCode == RESULT_OK) {
				                //get the extras that are returned from the intent
				                String contents = data.getStringExtra("SCAN_RESULT");
				                String format = data.getStringExtra("SCAN_RESULT_FORMAT");
				                try
				                {	CodigoQR qrcode = null;
				                	try
				                	{
				                		 qrcode = new CodigoQR(contents);
				                	}
				                	catch(Exception ex)
				                	{
				                		 UIHelper.showAlert(CreateActaActivity.this, "El Formato del Codigo QR de la Licencia no es valida", "Codigo QR", null);
					 		    		 return;
				                	}
				                	/*
				                	try
				                	{
				                		Serializer serializer = new Persister();
				                		File sdCardDirectory = Environment.getExternalStorageDirectory();
				                		File result = new File(sdCardDirectory,"qrcode.dat");
				                		serializer.write(qrcode, result);
				                	}
				                	catch(Exception ex)
				                	{


				                	} */
				                	String lblMensajeAyudaText="";
				                	try
				                	{
				                		String sNumeroDocumento = qrcode.NumeroDocumento.trim();
				                		if(!GNumeroDocumentoCodigoQR.equals(sNumeroDocumento))
				                		{
				                			lblMensajeAyudaText = "El Numero de Documento Ingresado no se Corresponde con el que se encuentra en el Codigo QR de la Licencia\n";
				                			lblMensajeAyudaText += "Nro Documento del Codigo QR => " + qrcode.NumeroDocumento + "\n";
				                			lblMensajeAyudaText += "Nro Documento Ingresado => " + GNumeroDocumentoCodigoQR + "\n";
				                		}
				                		else
				                		{
						                    lblMensajeAyudaText = "Nro de Licencia " +  qrcode.CodigoSeguridadLicencia + "\n";
						                    lblMensajeAyudaText += "Fecha Vencimiento " + Tools.DateValueOf(qrcode.FechaEmision ,"dd/MM/yyyy")+ "\n";
						                    lblMensajeAyudaText += "Id Clase de la Licencia " + qrcode.IdClaseLicencia + "\n";
						                    lblMensajeAyudaText += "Nro Documento " + qrcode.NumeroDocumento + "\n";
						                    lblMensajeAyudaText += "Tipo Documento " + qrcode.TipoDocumento + "\n";
						                    lblMensajeAyudaText += "Persona " + qrcode.Apellido + ", " + qrcode.Nombre  + "\n";
						                    lblMensajeAyudaText += "Domicilio " + qrcode.Calle + " Nro " + qrcode.NumeroCalle + "\n";
						                    lblMensajeAyudaText += "Piso " + qrcode.Piso + " Departamento " + qrcode.DepartamentoEdificio + "\n";
						                    lblMensajeAyudaText += "Codigo Postal " + qrcode.CodigoPostal + "\n";
						                    lblMensajeAyudaText += "Barrio " + qrcode.Barrio + "\n";

						                    editText_NumeroLicencia.setText(qrcode.CodigoSeguridadLicencia);
						                	editText_ClaseLicencia.setText(qrcode.IdClaseLicencia);
						                	Date dtFVL = qrcode.FechaVencimiento;
											CharSequence DateFLV = DateFormat.format("dd/MM/yyyy",dtFVL);
											editText_FVL_Info.setText(DateFLV);

											mCurrenActaSettings.edit().putString(CURRENT_ACTA_FECHA_VENCIMIENTO_LICENCIA,DateFLV.toString()).commit();
											try
											{
												CheckBox checkBox_LicenciaUP = (CheckBox) findViewById(R.id.CheckBox_LicenciaUnicaProvincial);
												checkBox_LicenciaUP.setChecked(true);
											}
											catch(Exception ex)
											{}
											String[] sListeners = {
													   SPINNER_PAIS_LICENCIA,SPINNER_PAIS_DOCUMENTO
													  ,SPINNER_PROVINCIA_LICENCIA,SPINNER_PROVINCIA_DOCUMENTO
													  ,SPINNER_DEPARTAMENTO_LICENCIA,SPINNER_DEPARTAMENTO_DOCUMENTO
													  ,SPINNER_LOCALIDAD_LICENCIA,SPINNER_LOCALIDAD_DOCUMENTO
													  };

											try
											{
												DeshabilitarListeners(sListeners);
												SetSpinnerSelectionByValue(myPaisAdapter,SPINNER_PAIS_LICENCIA,qrcode.IdPaisLicencia);
												// FALTA CARGAR LAS PROVINCIA DEL PAIS DEL CODIGO QR POR ESO NO SE MUESTRAN
												SetSpinnerSelectionByValue(myProvinciaAdapter,SPINNER_PROVINCIA_LICENCIA,qrcode.IdProvinciaLicencia);
												SetSpinnerSelectionByValue(myDepartamentoAdapter,SPINNER_DEPARTAMENTO_LICENCIA,qrcode.IdDepartamentoLicencia);
												SetSpinnerSelectionByValue(myLocalidadAdapter,SPINNER_LOCALIDAD_LICENCIA,qrcode.IdLocalidadLicencia);

												SetSpinnerSelectionByValue(myPaisDAdapter,SPINNER_PAIS_DOCUMENTO,qrcode.IdPaisConductor);
												SetSpinnerSelectionByValue(myProvinciaDAdapter,SPINNER_PROVINCIA_DOCUMENTO,qrcode.IdProvinciaConductor);
												SetSpinnerSelectionByValue(myDepartamentoDAdapter,SPINNER_DEPARTAMENTO_DOCUMENTO,qrcode.IdDepartamentoConductor);
												SetSpinnerSelectionByValue(myLocalidadDAdapter,SPINNER_LOCALIDAD_DOCUMENTO,qrcode.IdLocalidadConductor);

												SetSpinnerSelectionByValue(myTipoDocumentoAdapter,SPINNER_TIPO_DOCUMENTO,qrcode.TipoDocumento);
												//SetSpinnerSelectionByValue(myGeneroAdapter,SPINNER_TIPO_GENERO,qrcode.IdSexo);
											}
											catch(Exception ex)
											{}
											finally
											{
												HabilitarListeners(sListeners);
											}

											editText_NumeroDocumento.setText(qrcode.NumeroDocumento);
											editText_Apellido.setText(qrcode.Apellido);
											editText_Nombre.setText(qrcode.Nombre);
											editText_Calle.setText(qrcode.Calle);
											editText_Altura.setText(qrcode.NumeroCalle);
											editText_Piso.setText(qrcode.Piso);
											editText_Barrio.setText(qrcode.Barrio);
											editText_CodigoPostal.setText(qrcode.CodigoPostal);

				                		}

				                		GNumeroDocumentoCodigoQR ="";
				                	}
				                	catch(Exception ex)
				                	{
				                		 UIHelper.showAlert(CreateActaActivity.this, "El Formato del Codigo QR de la Licencia no es validad", "Codigo QR", null);
					 		    		 return;
				                	}

									// faltarian lo de los datos demograficos de la licencia

					       	    	 Intent intent = new Intent();
					 			     Bundle appResultData = new Bundle();

					 			     String sResultado = lblMensajeAyudaText;//qrcode.CodigoSeguridadLicencia;
					 			     if(sResultado.equals(""))
					 			     {
					 			    	 UIHelper.showAlert(CreateActaActivity.this, "No se obtener la informacion solicitada", "Verificacion Policial", null);
					 		    		 return;
					 			     }
					 			     appResultData.putString(ResultadoVerificacionPolicialActivity.APP_DATA_RESULTADO,sResultado);

					 			     intent.putExtra(ResultadoVerificacionPolicialActivity.APP_DATA,appResultData);
					 				 intent.setClass(CreateActaActivity.this,ResultadoVerificacionPolicialActivity.class);

					 				 startActivity(intent);


				                } catch(Exception ex)
				                {


				                }
//				                Toast toast = Toast.makeText(this, "Contenido:" + contents + " Format:" + format, Toast.LENGTH_LONG);
//				                toast.show();
				            }

			break;
		case SEARCH_INFRACCION1:
		case SEARCH_INFRACCION2:
			if (resultCode == Activity.RESULT_CANCELED) {
				// Search person mode was canceled.
				//Utilities.ShowToast(QuizCreateActaActivity.this, "Busqueda de Infraccion Cancelada");
			} else if (resultCode == Activity.RESULT_OK) {

				Bundle appDataReturned = data.getBundleExtra(SearchManager.APP_DATA);

				InfraccionNomenclada infraccionNomenclada = (InfraccionNomenclada) appDataReturned.getSerializable("ItemObject");
				//Utilities.ShowToast(QuizCreateActaActivity.this, "QuizCreateActa Infraccion Seleccionada" + infraccionNomenclada.getItemName());
				String sContenido = "Codigo : " + infraccionNomenclada.getCodigo() + " Descripcion : " + infraccionNomenclada.getDescripcionCorta();

				if (requestCode == SEARCH_INFRACCION1)
				{
					// buscamos todos los codigos y lo ponemos en un array para despues validar si ya estuvo cargada
					List<Integer> listadoInf = new  ArrayList<Integer>();
					try
					{
					if (editText_CODInfraccion_1.getVisibility()== View.VISIBLE)
						listadoInf.add(editText_CODInfraccion_1.getIdItem());
					}catch(Exception ex){}
					try
					{
					if (editText_CODInfraccion_2.getVisibility()== View.VISIBLE)
						listadoInf.add(editText_CODInfraccion_2.getIdItem());
					}catch(Exception ex){}
					try
					{
					if (editText_CODInfraccion_3.getVisibility()== View.VISIBLE)
						listadoInf.add(editText_CODInfraccion_3.getIdItem());
					}catch(Exception ex){}

					try
					{
					if (editText_CODInfraccion_4.getVisibility()== View.VISIBLE)
						listadoInf.add(editText_CODInfraccion_4.getIdItem());
					}catch(Exception ex){}

					try
					{
					if (editText_CODInfraccion_5.getVisibility()== View.VISIBLE)
						listadoInf.add(editText_CODInfraccion_5.getIdItem());
					}catch(Exception ex){}
					try
					{
					if (editText_CODInfraccion_6.getVisibility()== View.VISIBLE)
						listadoInf.add(editText_CODInfraccion_6.getIdItem());
					}catch(Exception ex){}

					if(listadoInf.contains(infraccionNomenclada.getId()))
					{
						UIHelper.showErrorOnGuiThread((Activity)CreateActaActivity.this, "La Infraccion Seleccionada ya esta incluida en la presente acta", null);
						return;
					}

					Boolean InfCargada= false;
					if (InfCargada == false && editText_CODInfraccion_1.getVisibility()==View.GONE)
					{	mCurrenActaSettings.edit().putInt(CURRENT_ACTA_INFRACCION1, infraccionNomenclada.getId()).commit();
						editText_CODInfraccion_1.setVisibility(View.VISIBLE);
						InfCargada = true;
						editText_CODInfraccion_1.setText(sContenido);
						editText_CODInfraccion_1.setIdItem(infraccionNomenclada.getId());
						editText_CODInfraccion_1.setObjectItem(infraccionNomenclada);
						setInfraccionValues(SEARCH_INFRACCION1, infraccionNomenclada);
					}
					if (InfCargada == false && editText_CODInfraccion_2.getVisibility()==View.GONE)
					{	mCurrenActaSettings.edit().putInt(CURRENT_ACTA_INFRACCION2, infraccionNomenclada.getId()).commit();
						editText_CODInfraccion_2.setVisibility(View.VISIBLE);
						InfCargada = true;
						editText_CODInfraccion_2.setText(sContenido);
						editText_CODInfraccion_2.setIdItem(infraccionNomenclada.getId());
						editText_CODInfraccion_2.setObjectItem(infraccionNomenclada);
						setInfraccionValues(SEARCH_INFRACCION2, infraccionNomenclada);
					}
					if (InfCargada == false && editText_CODInfraccion_3.getVisibility()==View.GONE)
					{	mCurrenActaSettings.edit().putInt(CURRENT_ACTA_INFRACCION3, infraccionNomenclada.getId()).commit();
						editText_CODInfraccion_3.setVisibility(View.VISIBLE);
						InfCargada = true;
						editText_CODInfraccion_3.setText(sContenido);
						editText_CODInfraccion_3.setIdItem(infraccionNomenclada.getId());
						editText_CODInfraccion_3.setObjectItem(infraccionNomenclada);
						setInfraccionValues(SEARCH_INFRACCION3, infraccionNomenclada);
					}
					if (InfCargada == false && editText_CODInfraccion_4.getVisibility()==View.GONE)
					{	mCurrenActaSettings.edit().putInt(CURRENT_ACTA_INFRACCION4, infraccionNomenclada.getId()).commit();
						editText_CODInfraccion_4.setVisibility(View.VISIBLE);
						InfCargada = true;
						editText_CODInfraccion_4.setText(sContenido);
						editText_CODInfraccion_4.setIdItem(infraccionNomenclada.getId());
						editText_CODInfraccion_4.setObjectItem(infraccionNomenclada);
						setInfraccionValues(SEARCH_INFRACCION4, infraccionNomenclada);
					}
					if (InfCargada == false && editText_CODInfraccion_5.getVisibility()==View.GONE)
					{	mCurrenActaSettings.edit().putInt(CURRENT_ACTA_INFRACCION5, infraccionNomenclada.getId()).commit();
						editText_CODInfraccion_5.setVisibility(View.VISIBLE);
						InfCargada = true;
						editText_CODInfraccion_5.setText(sContenido);
						editText_CODInfraccion_5.setIdItem(infraccionNomenclada.getId());
						editText_CODInfraccion_5.setObjectItem(infraccionNomenclada);
						setInfraccionValues(SEARCH_INFRACCION5, infraccionNomenclada);
					}
					if (InfCargada == false && editText_CODInfraccion_6.getVisibility()==View.GONE)
					{	mCurrenActaSettings.edit().putInt(CURRENT_ACTA_INFRACCION6, infraccionNomenclada.getId()).commit();
						editText_CODInfraccion_6.setVisibility(View.VISIBLE);
						InfCargada = true;
						editText_CODInfraccion_6.setText(sContenido);
						editText_CODInfraccion_6.setIdItem(infraccionNomenclada.getId());
						editText_CODInfraccion_6.setObjectItem(infraccionNomenclada);
						setInfraccionValues(SEARCH_INFRACCION6, infraccionNomenclada);
					}

					if (infraccionNomenclada.getCodigo().equals("158")){
						toggleAlcoholemiaVisibility();
					}

					//setInfraccionValues(SEARCH_INFRACCION1, infraccionNomenclada);
					//editText_CODInfraccion_1.setText(sContenido);
				}
				else
				{
				//	mCurrenActaSettings.edit().putInt(CURRENT_ACTA_INFRACCION2, infraccionNomenclada.getId()).commit();
				//	setInfraccionValues(SEARCH_INFRACCION2, infraccionNomenclada);
					//editText_CODInfraccion_2.setText(sContenido);
				}
				// ponemos el foco en el campo de observaciones
				editText_Observaciones.requestFocus();
				ocultarTeclado(editText_Observaciones);
			}


			break;
		case SEARCH_DOMINIO:
			String dominio = editText_Dominio.getText().toString();
			TipoVehiculo tipoVehiculo = (TipoVehiculo) myTipoVehiculoSpinner.getSelectedItem();
			String idTipoVehiculo = tipoVehiculo.getId();
			if (resultCode == Activity.RESULT_CANCELED) {
				//Utilities.ShowToast(QuizCreateActaActivity.this, "Busqueda de Dominio Cancelada");
				/*if(dominio!=null && !dominio.equals("") && idTipoVehiculo!=null && !idTipoVehiculo.equals(""))
				{
				  if (idTipoVehiculo.equals("1") || idTipoVehiculo.equals("2")) //Antes A o M
				  {
					   String pTipoObjetoBusqueda = VerificacionPolicialSync.AUTOMOTORES;

						if(idTipoVehiculo.equals("1"))
							pTipoObjetoBusqueda = VerificacionPolicialSync.AUTOMOTORES;
						else if (idTipoVehiculo.equals("2"))
							pTipoObjetoBusqueda = VerificacionPolicialSync.MOTOVEHICULOS;

					   String pTipoBusqueda = VerificacionPolicialSync.DOMINIO;
					   String pValor = editText_Dominio.getText().toString();

					   doVerificarPolicial(pTipoObjetoBusqueda, pTipoBusqueda, pValor);
				  }
				}*/

			} else if (resultCode == Activity.RESULT_OK) {

				Bundle appDataReturned = data.getBundleExtra(SearchManager.APP_DATA);

				DominioPadron dominioPadron = (DominioPadron) appDataReturned.getSerializable("ItemObject");
				//Utilities.ShowToast(QuizCreateActaActivity.this, "QuizCreateActa Dominio Seleccionada" + per.getItemName());
				if (dominioPadron.getIdTipoVehiculo()!=null && !dominioPadron.getIdTipoVehiculo().equals(""))//.getDominio().equals("-1"))
				{
					String pTipoObjetoBusqueda = VerificacionPolicialSync.AUTOMOTORES;
					try
					{
						CargarDatosFormulario(FORM_DOMINIO,dominioPadron);


						String numeroDocumento = dominioPadron.getNumeroDocumento();
						if(numeroDocumento!=null)
						{ // buscar  persona
							PersonaPadronRules padronRules = new PersonaPadronRules(this);
							PersonaPadron personaPadron = padronRules.getByNumeroDocumento(numeroDocumento);

							if (personaPadron!=null)
							{
								CargarDatosFormulario(FORM_PERSONA_TITULAR,personaPadron);
							}
						}


						//doVerificarPolicial(VerificacionPolicialSync.DOMINIO, pTipoObjetoBusqueda, per.getDominio());
					}
					catch(Exception ex)
					{

					}

				}
			}


		break;
		case SEARCH_PERSONA:
			String numeroDocumento = editText_NumeroDocumento.getText().toString();
			if (resultCode == Activity.RESULT_CANCELED) {
				// Search person mode was canceled.
				//Utilities.ShowToast(QuizCreateActaActivity.this, "Busqueda de Persona Cancelada");
				/*if(numeroDocumento!=null && !numeroDocumento.equals(""))
				{
					doVerificarPolicial(VerificacionPolicialSync.PERSONAS, VerificacionPolicialSync.NUMERO_DOCUMENTO, numeroDocumento);
				}
				*/

			} else if (resultCode == Activity.RESULT_OK) {

				Bundle appDataReturned = data.getBundleExtra(SearchManager.APP_DATA);

				PersonaPadron per = (PersonaPadron) appDataReturned.getSerializable("ItemObject");
				//Utilities.ShowToast(QuizCreateActaActivity.this, "QuizCreateActa Persona Seleccionada" + per.getItemName());
				if (per.getNumeroDocumento() !=null && !per.getNumeroDocumento().equals("") && !per.getNumeroDocumento().equals("-1"))
				{
					CargarDatosFormulario(FORM_PERSONA_INFRACTOR, per);
				}
					//doVerificarPolicial(VerificacionPolicialSync.PERSONAS, VerificacionPolicialSync.NUMERO_DOCUMENTO, per.getNumeroDocumento());

		   }


		break;
		case SEARCH_PERSONA_TITULAR:
			String numeroDocumentoTitular = editText_NumeroDocumentoTitular.getText().toString();
			if (resultCode == Activity.RESULT_CANCELED) {
				// Search person mode was canceled.
				//Utilities.ShowToast(QuizCreateActaActivity.this, "Busqueda de Persona Cancelada");
				/*if(numeroDocumentoTitular!=null && !numeroDocumentoTitular.equals(""))
				{
					doVerificarPolicial(VerificacionPolicialSync.PERSONAS, VerificacionPolicialSync.NUMERO_DOCUMENTO, numeroDocumentoTitular);
				}*/

			} else if (resultCode == Activity.RESULT_OK) {

				Bundle appDataReturned = data.getBundleExtra(SearchManager.APP_DATA);

				PersonaPadron per = (PersonaPadron) appDataReturned.getSerializable("ItemObject");
				//Utilities.ShowToast(QuizCreateActaActivity.this, "QuizCreateActa Persona Seleccionada" + per.getItemName());
				if (per.getNumeroDocumento() !=null && !per.getNumeroDocumento().equals("") && !per.getNumeroDocumento().equals("-1"))
				{
					CargarDatosFormulario(FORM_PERSONA_TITULAR, per);
					//editText_a
					//doVerificarPolicial(VerificacionPolicialSync.PERSONAS, VerificacionPolicialSync.NUMERO_DOCUMENTO, per.getNumeroDocumento());
				}
			}


		break;
/*testigo*/
		case SEARCH_PERSONA_TESTIGO:
			String numeroDocumentoTestigo= editText_NumeroDocumentoTestigo.getText().toString();
			if (resultCode == Activity.RESULT_CANCELED) {
				// Search person mode was canceled.
				//Utilities.ShowToast(QuizCreateActaActivity.this, "Busqueda de Persona Cancelada");
				/*if(numeroDocumentoTestigo!=null && !numeroDocumentoTestigo.equals(""))
				{
					doVerificarPolicial(VerificacionPolicialSync.PERSONAS, VerificacionPolicialSync.NUMERO_DOCUMENTO, numeroDocumentoTestigo);
				}
				*/

			} else if (resultCode == Activity.RESULT_OK) {

				Bundle appDataReturned = data.getBundleExtra(SearchManager.APP_DATA);

				PersonaPadron per = (PersonaPadron) appDataReturned.getSerializable("ItemObject");
				//Utilities.ShowToast(QuizCreateActaActivity.this, "QuizCreateActa Persona Seleccionada" + per.getItemName());
				if (per.getNumeroDocumento() !=null && !per.getNumeroDocumento().equals("") && !per.getNumeroDocumento().equals("-1"))
				{
					CargarDatosFormulario(FORM_PERSONA_TESTIGO, per);
					//doVerificarPolicial(VerificacionPolicialSync.PERSONAS, VerificacionPolicialSync.NUMERO_DOCUMENTO, per.getMatricula());
				}
			}


		break;

		case TAKE_AVATAR_CAMERA_REQUEST_LICENCIA:
		case TAKE_AVATAR_CAMERA_REQUEST_DOCUMENTO:
		case TAKE_AVATAR_CAMERA_REQUEST_OTRO:


			if (resultCode == Activity.RESULT_CANCELED) {
				// Avatar camera mode was canceled.
			} else if (resultCode == Activity.RESULT_OK) {

				// Took a picture, use the downsized camera image provided by
				// default
				Bitmap cameraPic = (Bitmap) data.getExtras().get("data");
				if (cameraPic != null) {
					try {
						saveAvatar(cameraPic,requestCode);
					} catch (Exception e) {
						Log.e(DEBUG_TAG,
								"saveAvatar() with camera image failed.", e);
					}
				}
			}
			break;
		case TAKE_AVATAR_GALLERY_REQUEST:

			if (resultCode == Activity.RESULT_CANCELED) {
				// Avatar gallery request mode was canceled.
			} else if (resultCode == Activity.RESULT_OK) {

				// Get image picked
				Uri photoUri = data.getData();
				if (photoUri != null) {
					try {
						int maxLength = 75;
						// Full size image likely will be large. Let's scale the
						// graphic to a more appropriate size for an avatar
						Bitmap galleryPic = Media.getBitmap(
								getContentResolver(), photoUri);
						Bitmap scaledGalleryPic = createScaledBitmapKeepingAspectRatio(
								galleryPic, maxLength);
						saveAvatar(scaledGalleryPic,TAKE_AVATAR_GALLERY_REQUEST);
					} catch (Exception e) {
						Log.e(DEBUG_TAG,
								"saveAvatar() with gallery picker failed.", e);
					}
				}
			}
			break;
		}
	}

    private void CargarDatosFormulario(String pTipo,Object objeto)
    {
    	switch (pTipo)
    	{
	    	case FORM_PERSONA_TITULAR :
	    	case FORM_PERSONA_INFRACTOR:
	    	case FORM_PERSONA_TESTIGO:
	    		try
	    		{
		    		PersonaPadron personaPadron = (PersonaPadron) objeto;

		    		switch (pTipo)
		        	{
				    	case FORM_PERSONA_TITULAR :
				    		String[] sListenersTitular = {SPINNER_TIPO_DOCUMENTO_TITULAR};
				    		DeshabilitarListeners(sListenersTitular);
				    		SetSpinnerSelectionByValue(myTipoDocumentoTitularAdapter,SPINNER_TIPO_DOCUMENTO_TITULAR,personaPadron.getIdTipoDocumento());
				    		HabilitarListeners(sListenersTitular);

				    		String[] sListenersDemograficos = {
									   SPINNER_PAIS_DOCUMENTO_TITULAR,SPINNER_PROVINCIA_DOCUMENTO_TITULAR
									   ,SPINNER_DEPARTAMENTO_DOCUMENTO_TITULAR,SPINNER_LOCALIDAD_DOCUMENTO_TITULAR
									  };
				    		try
				    		{
				    			DeshabilitarListeners(sListenersDemograficos);
				    		//buscamos Localidad
				    		LocalidadRules localidadRules = new LocalidadRules(this);
				    		List<Localidad> lstLocalidades =  localidadRules.getByNombre(personaPadron.getLocalidad());
				    		if(lstLocalidades.isEmpty())
				    		{
								myLocalidadDTitularSpinner.post(new Runnable() {
								    public void run() {
								    	editText_LocalidadDocumentoTitular.setVisibility(View.VISIBLE);
								    	myLocalidadDTitularSpinner.setSelection(0,false);
								    	mCurrenActaSettings.edit().putInt(CURRENT_ACTA_LOCALIDAD_DOCUMENTO_TITULAR, 0).commit();
								    }
								  });

				    			//SetSpinnerSelectionByValue(myLocalidadAdapter,SPINNER_LOCALIDAD_DOCUMENTO_TITULAR,"0");

				    		}

				    		}catch(Exception e){

				    		}
				    		finally
				    		{
				    			HabilitarListeners(sListenersDemograficos);
				    		}
				    		editText_NumeroDocumentoTitular.setText(personaPadron.getNumeroDocumento());
							editText_ApellidoTitular.setText(personaPadron.getApellido());
							editText_NombreTitular.setText(personaPadron.getNombre());
							editText_CalleTitular.setText(personaPadron.getCalle());
							editText_AlturaTitular.setText(personaPadron.getAltura());
							editText_PisoTitular.setText(personaPadron.getPiso());
							editText_DeptoTitular.setText(personaPadron.getDepto());
							editText_BarrioTitular.setText(personaPadron.getBarrio());
				    		break;
				    	case FORM_PERSONA_INFRACTOR:
				    		String[] sListeners = {SPINNER_TIPO_DOCUMENTO};
				    		DeshabilitarListeners(sListeners);
				    		SetSpinnerSelectionByValue(myTipoDocumentoAdapter,SPINNER_TIPO_DOCUMENTO,personaPadron.getIdTipoDocumento());
				    		HabilitarListeners(sListeners);

				    		editText_NumeroDocumento.setText(personaPadron.getNumeroDocumento());
							editText_Apellido.setText(personaPadron.getApellido());
							editText_Nombre.setText(personaPadron.getNombre());
							editText_Calle.setText(personaPadron.getCalle());
							editText_Altura.setText(personaPadron.getAltura());
							editText_Piso.setText(personaPadron.getPiso());
							editText_Depto.setText(personaPadron.getDepto());
							editText_Barrio.setText(personaPadron.getBarrio());

							break;
				    	case FORM_PERSONA_TESTIGO:
				    		String[] sListenersTestigo = {SPINNER_TIPO_DOCUMENTO_TESTIGO};
				    		DeshabilitarListeners(sListenersTestigo);
				    		SetSpinnerSelectionByValue(myTipoDocumentoTestigoAdapter,SPINNER_TIPO_DOCUMENTO_TESTIGO,personaPadron.getIdTipoDocumento());
				    		HabilitarListeners(sListenersTestigo);

				    		editText_NumeroDocumentoTestigo.setText(personaPadron.getNumeroDocumento());
							editText_ApellidoTestigo.setText(personaPadron.getApellido());
							editText_NombreTestigo.setText(personaPadron.getNombre());
							editText_CalleTestigo.setText(personaPadron.getCalle());
							editText_AlturaTestigo.setText(personaPadron.getAltura());
							editText_PisoTestigo.setText(personaPadron.getPiso());
							editText_DeptoTestigo.setText(personaPadron.getDepto());
							editText_BarrioTestigo.setText(personaPadron.getBarrio());

				    		break;
				    	default:
							break;
		        	}
	    		}catch(Exception e){

	    		}
	    		break;
	    	case FORM_LICENCIA:
	    		try{
	    			throw new Exception("Form para Licencia aun no Implementado");
	    		}catch(Exception ex){

	    		}
	    		break;
	    	case FORM_DOMINIO:
	    		try {
		    		DominioPadron dominioPadron = (DominioPadron) objeto;
					String tipoV = dominioPadron.getIdTipoVehiculo();
					final String marcaV = dominioPadron.getMarca();
					final String colorV = dominioPadron.getColor();
					String[] sListeners = {SPINNER_TIPO_VEHICULO};
					DeshabilitarListeners(sListeners);
					// como agregaron mas tipos de vehiculo
		    		if (tipoV!=null && (tipoV.equals("1") || tipoV.equals("A")))
					{
		    			SetSpinnerSelectionByValue(myTipoVehiculoAdapter,SPINNER_TIPO_VEHICULO,"1");
					}
					if (tipoV!=null && (tipoV.equals("2") || tipoV.equals("M")))
					{
						 //SetSpinnerSelectionByValue(myTipoVehiculoAdapter,SPINNER_TIPO_VEHICULO,"2");//antes M
						SetSpinnerSelectionByValue(myTipoVehiculoAdapter,SPINNER_TIPO_VEHICULO,"2");
					}
					HabilitarListeners(sListeners);

					editText_Dominio.setText(dominioPadron.getDominio());
					editText_ModeloVehiculo.setText(dominioPadron.getModelo());
					editText_AnioModeloVehiculo.setText(dominioPadron.getAnio());
					myMarcaSpinner.post(new Runnable() {
					    public void run() {
					    	editText_Marca.setVisibility(View.VISIBLE);
					    	myMarcaSpinner.setSelection(0,false);
					    	mCurrenActaSettings.edit().putInt(CURRENT_ACTA_MARCA, 0).commit();
					    	SetSpinnerSelectionByValue2(myMarcaAdapter,SPINNER_MARCA_VEHICULO,marcaV);
					    	if(myMarcaSpinner.getSelectedItemPosition()>0)
					    	{
					    		editText_Marca.setVisibility(View.GONE);
					    	}

					    }
					  });
					editText_Marca.setText(dominioPadron.getMarca());

					myColorSpinner.post(new Runnable() {
					    public void run() {
					    	editText_Color.setVisibility(View.VISIBLE);
					    	myColorSpinner.setSelection(0,false);
					    	mCurrenActaSettings.edit().putInt(CURRENT_ACTA_COLOR, 0).commit();
					    	SetSpinnerSelectionByValue2(myColorAdapter,SPINNER_COLOR_VEHICULO,colorV);
					    	if(myColorSpinner.getSelectedItemPosition()>0)
					    	{
					    		editText_Color.setVisibility(View.GONE);
					    	}

					    }
					  });
					editText_Color.setText(dominioPadron.getColor());

	    		} catch(Exception ex){
				}

	    	default:
				break;
    	}

    }

	public void onLaunchCamera(View v) {

		int _idButton = v.getId();
		int _request_code = 0;
		Intent pictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		String strAvatarPrompt = "Obtener una Foto";

		switch (_idButton) {
		case R.id.ImageButton_Licencia:
			strAvatarPrompt = "Obtener una Foto de la Licencia!";
			_request_code  = TAKE_AVATAR_CAMERA_REQUEST_LICENCIA;
			break;
		case R.id.ImageButton_Documento:
			strAvatarPrompt = "Obtener una Foto del Documento";
			_request_code  = TAKE_AVATAR_CAMERA_REQUEST_DOCUMENTO;
			break;
//		case R.id.ImageButton_Otro:
//			strAvatarPrompt = "Obtener una Foto";
//			_request_code  = TAKE_AVATAR_CAMERA_REQUEST_OTRO;
//			break;
		default:
			Utilities.ShowToast(this,"Seleccion Invalida");
			return;
		}


		startActivityForResult(Intent.createChooser(pictureIntent, strAvatarPrompt),_request_code);
	}

	/**
	 * Scale a Bitmap, keeping its aspect ratio
	 *
	 * @param bitmap
	 *            Bitmap to scale
	 * @param maxSide
	 *            Maximum length of either side
	 * @return a new, scaled Bitmap
	 */
	private Bitmap createScaledBitmapKeepingAspectRatio(Bitmap bitmap,
			int maxSide) {
		int orgHeight = bitmap.getHeight();
		int orgWidth = bitmap.getWidth();

		// scale to no longer any either side than 75px
		int scaledWidth = (orgWidth >= orgHeight) ? maxSide
				: (int) ((float) maxSide * ((float) orgWidth / (float) orgHeight));
		int scaledHeight = (orgHeight >= orgWidth) ? maxSide
				: (int) ((float) maxSide * ((float) orgHeight / (float) orgWidth));

		// create the scaled bitmap
		Bitmap scaledGalleryPic = Bitmap.createScaledBitmap(bitmap,
				scaledWidth, scaledHeight, true);
		return scaledGalleryPic;
	}

	private void saveAvatar(Bitmap avatar,int pRequestCode) {
		String strAvatarFilename = "avatar.jpg";
		String sPreferenceFoto = GAME_PREFERENCES_AVATAR;
		String sNumeroActa = (new ActaConstatacionRules(this)).getNextNumeroActa();
		int _idButton = 0;
		switch (pRequestCode) {
		case TAKE_AVATAR_CAMERA_REQUEST_LICENCIA:
			strAvatarFilename =  sNumeroActa + "_licencia.jpg";
			sPreferenceFoto = CURRENT_ACTA_FOTO_LICENCIA;
			_idButton = R.id.ImageButton_Licencia;
			break;
		case TAKE_AVATAR_CAMERA_REQUEST_DOCUMENTO:
			strAvatarFilename = sNumeroActa + "_documento.jpg";
			sPreferenceFoto = CURRENT_ACTA_FOTO_DOCUMENTO;
			_idButton = R.id.ImageButton_Documento;
			break;
//		case TAKE_AVATAR_CAMERA_REQUEST_OTRO:
//			strAvatarFilename = sNumeroActa + "_otro.jpg";
//			sPreferenceFoto = CURRENT_ACTA_FOTO_OTRO;
//			_idButton = R.id.ImageButton_Otro;
//			break;
		default:
			Utilities.ShowToast(this, "Seleccion de Imagen Invalida");
			return;
		}
		File image = null;
		try {

			File sdCardDirectory = Environment.getExternalStorageDirectory();
			image = new File(sdCardDirectory, strAvatarFilename);
			FileOutputStream outStream;
			int iCalidadFoto = 100;
			try{
				iCalidadFoto = GlobalVar.getInstance().getSuportTable().getCalidadFoto();
			}catch(Exception ex)
			{

			}
			   try {

			        outStream = new FileOutputStream(image);
			        avatar.compress(CompressFormat.JPEG, iCalidadFoto, outStream);
			        /* 100 to keep full quality of the image */

			        outStream.flush();
			        outStream.close();

			    } catch (FileNotFoundException e) {
			        e.printStackTrace();
			    } catch (IOException e) {
			        e.printStackTrace();
			    }

			//avatar.compress(CompressFormat.JPEG, 100,openFileOutput(strAvatarFilename,MODE_WORLD_READABLE));

		} catch (Exception e) {
			Log.e(DEBUG_TAG, "Avatar compression and save failed.", e);
		}

		//Uri imageUriToSaveCameraImageTo = Uri.fromFile(new File(QuizCreateActaActivity.this.getFilesDir(), strAvatarFilename));
		if (image==null) return;
		Uri imageUriToSaveCameraImageTo = Uri.fromFile(image);

		Editor editor = mCurrenActaSettings.edit();
		editor.putString(sPreferenceFoto,imageUriToSaveCameraImageTo.getPath());
		editor.commit();

		// Update the settings screen
		ImageButton avatarButton = (ImageButton) findViewById(_idButton);
		String strAvatarUri = mCurrenActaSettings.getString(sPreferenceFoto,RESOURCE_SIN_FOTO);
		Uri imageUri = Uri.parse(strAvatarUri);
		avatarButton.setImageURI(null); // Workaround for refreshing an
										// ImageButton, which tries to cache the
										// previous image Uri. Passing null
										// effectively resets it.
		avatarButton.setImageURI(imageUri);
	}

	/**
	 * Initialize the Buttons TakePhotos
	 */
	private void initTakePhotos() {
		// Handle password setting dialog
		ImageButton avatarButton = (ImageButton) findViewById(R.id.ImageButton_Licencia);
		if (mCurrenActaSettings.contains(CURRENT_ACTA_FOTO_LICENCIA)) {
			String strAvatarUri = mCurrenActaSettings.getString(CURRENT_ACTA_FOTO_LICENCIA,RESOURCE_SIN_FOTO);
			Uri imageUri = Uri.parse(strAvatarUri);
			avatarButton.setImageURI(imageUri);
		} else {
			avatarButton.setImageResource(R.drawable.sinfoto);
		}

		 avatarButton = (ImageButton) findViewById(R.id.ImageButton_Documento);
		if (mCurrenActaSettings.contains(CURRENT_ACTA_FOTO_DOCUMENTO)) {
			String strAvatarUri = mCurrenActaSettings.getString(CURRENT_ACTA_FOTO_DOCUMENTO,RESOURCE_SIN_FOTO);
			Uri imageUri = Uri.parse(strAvatarUri);
			avatarButton.setImageURI(imageUri);
		} else {
			avatarButton.setImageResource(R.drawable.sinfoto);
		}

/*		avatarButton = (ImageButton) findViewById(R.id.ImageButton_Otro);
		if (mCurrenActaSettings.contains(CURRENT_ACTA_FOTO_OTRO)) {
			String strAvatarUri = mCurrenActaSettings.getString(CURRENT_ACTA_FOTO_OTRO,RESOURCE_SIN_FOTO);
			Uri imageUri = Uri.parse(strAvatarUri);
			avatarButton.setImageURI(imageUri);
		} else {
			avatarButton.setImageResource(R.drawable.sinfoto);
		}*/


//		avatarButton.setOnLongClickListener(new View.OnLongClickListener() {
//
//			public boolean onLongClick(View v) {
//				String strAvatarPrompt = "Choose a picture to use as your avatar!";
//				Intent pickPhoto = new Intent(Intent.ACTION_PICK);
//				pickPhoto.setType("image/*");
//				startActivityForResult(
//						Intent.createChooser(pickPhoto, strAvatarPrompt),
//						TAKE_AVATAR_GALLERY_REQUEST);
//				return true;
//			}
//		});

	}

	/**
	 * Initialize the nickname entry
	 */
	private void initNicknameEntry() {
		EditText nicknameText = (EditText) findViewById(R.id.EditText_Nickname);
		if (mGameSettings.contains(GAME_PREFERENCES_NICKNAME)) {
			nicknameText.setText(mGameSettings.getString(
					GAME_PREFERENCES_NICKNAME, ""));
		}
	}

	/**
	 * Initialize the email entry
	 */
	private void initEmailEntry() {
		EditText emailText = (EditText) findViewById(R.id.EditText_Email);
		if (mGameSettings.contains(GAME_PREFERENCES_EMAIL)) {
			emailText.setText(mGameSettings.getString(GAME_PREFERENCES_EMAIL,
					""));
		}
	}

	/**
	 * Initialize the Password chooser
	 */
	private void initPasswordChooser() {
		// Set password info
		TextView passwordInfo = (TextView) findViewById(R.id.TextView_Password_Info);
		if (mGameSettings.contains(GAME_PREFERENCES_PASSWORD)) {
			passwordInfo.setText(R.string.settings_pwd_set);
		} else {
			passwordInfo.setText(R.string.settings_pwd_not_set);
		}
	}

	/**
	 * Called when the user presses the Set Password button
	 *
	 * @param view
	 *            the button
	 */
	public void onSetPasswordButtonClick(View view) {
		showDialog(PASSWORD_DIALOG_ID);
	}

	/**
	 * Called when the user presses the Add Friend button
	 *
	 * @param view
	 *            the button
	 */
	public void onAddFriendButtonClick(View view) {
		showDialog(FRIEND_EMAIL_DIALOG_ID);
	}

	/**
	 * Initialize the Date picker
	 */
	private void initDatePicker() {
		// Set password info
		TextView dobInfo = (TextView) findViewById(R.id.TextView_DOB_Info);
		if (mGameSettings.contains(GAME_PREFERENCES_DOB)) {
			dobInfo.setText(DateFormat.format("MMMM dd, yyyy",
					mGameSettings.getLong(GAME_PREFERENCES_DOB, 0)));
		} else {
			dobInfo.setText(R.string.settings_dob_not_set);
		}
	}

	/**
	 * Called when the user presses the Pick Date button
	 *
	 * @param view
	 *            The button
	 */
	public void onPickDateButtonClick(View view) {
		showDialog(DATE_DIALOG_ID);
	}
	/**
	 * Called when the user presses the Pick Date button
	 *
	 * @param view
	 *            The button
	 */
	public void onPickExpireDateButtonClick(View view) {
		showDialog(EXPIRE_DATE_DIALOG_ID);
	}

	/**
	 * Called when the user presses the Pick Date button
	 *
	 * @param view
	 *            The button
	 */
	public void onPickBornDateButtonClick(View view) {
		showDialog(BORN_DATE_DIALOG_ID);
	}
	/* CHECK SIN CONDUCTOR */
	/**
	 * Initialize the checkbox ConductorTitular
	 */
	private void initNoRegistraConductorCheckBox() {
		// Populate Spinner control with genders
		final CheckBox _checkboxCC= (CheckBox) findViewById(R.id.CheckBox_SinConductor);
		//final CheckBox _checkboxCT= (CheckBox) findViewById(R.id.CheckBox_ConductorTitular);
		final LinearLayout _layout_Conductor = (LinearLayout) findViewById(R.id.Layout_Conductor);
		//final LinearLayout _layout_Titular = (LinearLayout) findViewById(R.id.Layout_Titular);
		_checkboxCC.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			  @Override
			  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			  {
				if (buttonView.isChecked()) {
				//checked
					_layout_Conductor.setVisibility(View.GONE);
					//_layout_Titular.setVisibility(View.GONE);
					//_checkboxCT.setEnabled(false);
					//_checkboxCT.setChecked(true);
					//copiar datos del conductor en Titular
				}
				else
				{
					_layout_Conductor.setVisibility(View.VISIBLE);
					//_checkboxCT.setEnabled(true);
					//_checkboxCT.setChecked(false);
				//not checked
				}

			 }

		   });
		}

	/* FIN  NO REGISTRA CONDUCTOR */
	/* CHECKBOX NOrEGISTRA TESTIGO*/
	/**
	 * Initialize the checkbox ConductorTitular
	 */
	private void initNoRegistraTestigoCheckBox() {
		// Populate Spinner control with genders
		final CheckBox _checkboxCT= (CheckBox) findViewById(R.id.CheckBox_SinTestigo);
		final LinearLayout _layout_Testigo = (LinearLayout) findViewById(R.id.Layout_Testigo);
		_checkboxCT.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			  @Override
			  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			  {
				if (buttonView.isChecked()) {
				//checked
					_layout_Testigo.setVisibility(View.GONE);
					//copiar datos del conductor en Titular
				}
				else
				{
					_layout_Testigo.setVisibility(View.VISIBLE);
				//not checked
				}

			 }

		   });
		}
	/* FIN NO REGISTRA TESTIGO*/

	/*NO REGISTRA TITULAR */
	/**
	 * Initialize the checkbox ConductorTitular
	 */
	private void initNoRegistraTitularCheckBox() {
		// Populate Spinner control with genders
		final CheckBox _checkboxT= (CheckBox) findViewById(R.id.CheckBox_SinTitular);
		final LinearLayout _layout_Titular= (LinearLayout) findViewById(R.id.Layout_Titular);
		_checkboxT.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			  @Override
			  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			  {
				if (buttonView.isChecked()) {
				//checked
					_layout_Titular.setVisibility(View.GONE);
					Button_CopiarDatosTitular.setVisibility(View.GONE);
					//copiar datos del conductor en Titular
				}
				else
				{
					_layout_Titular.setVisibility(View.VISIBLE);
					Button_CopiarDatosTitular.setVisibility(View.VISIBLE);
				//not checked
				}

			 }

		   });
		}
	/* FIN NO REGISTRA TITULAR*/

	/**
	 * Initialize the checkbox ConductorTitular
	 */
	private void initConductorTitularCheckBox() {
		// Populate Spinner control with genders
		//final CheckBox _checkboxCT= (CheckBox) findViewById(R.id.CheckBox_ConductorTitular);
		final LinearLayout _layout_Titular = (LinearLayout) findViewById(R.id.Layout_Titular);
		/*_checkboxCT.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			  @Override
			  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			  {
				if (buttonView.isChecked()) {
				//checked
					_layout_Titular.setVisibility(View.GONE);
					//copiar datos del conductor en Titular
				}
				else
				{
					_layout_Titular.setVisibility(View.VISIBLE);
				//not checked
				}

			 }

		   });*/
	/*

		if (mGameSettings.contains(GAME_PREFERENCES_TIPO_RUTA)) {
			spinner.setSelection(mGameSettings.getInt(GAME_PREFERENCES_TIPO_RUTA,0));
		}

		// Handle spinner selections
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent,
					View itemSelected, int selectedItemPosition, long selectedId) {
				Editor editor = mGameSettings.edit();
				editor.putInt(GAME_PREFERENCES_TIPO_RUTA, selectedItemPosition);
				editor.commit();

				fillRutaSpinner(String.valueOf(selectedItemPosition+1));
				// CARGAR LAS RUTAS
			}

			public void onNothingSelected(AdapterView<?> parent){
			}
		});*/
	}

	/**
	 * Initialize the checkbox NoTieneLicencia
	 */
	private void initNoTieneLicenciaCheckBox() {
		//agregamos tambien lo de Licencia Unica Provincial
		// CHK LICENCIA UNICA PROVINCIAL
		final CheckBox _checkboxLUP= (CheckBox) findViewById(R.id.CheckBox_LicenciaUnicaProvincial);
		final Button _BotonVerificacionLicencia = (Button) findViewById(R.id.Button_VerificarLicencia);
		   _checkboxLUP.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			  @Override
			  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			  {
				if (buttonView.isChecked()) {
				//checked
					_BotonVerificacionLicencia.setVisibility(View.VISIBLE);
				}
				else
				{
					_BotonVerificacionLicencia.setVisibility(View.GONE);
				//not checked
				}

			 }

		   });


		//CHK NO TIENE LICENCIA
		// Populate Spinner control with genders
		final CheckBox _checkbox= (CheckBox) findViewById(R.id.CheckBox_NoTieneLicencia);
		final LinearLayout _layout_Licencia = (LinearLayout) findViewById(R.id.Layout_Licencia);
		   _checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			  @Override
			  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			  {
				if (buttonView.isChecked()) {
				//checked
					_layout_Licencia.setVisibility(View.GONE);
				}
				else
				{
					_layout_Licencia.setVisibility(View.VISIBLE);
				//not checked
				}

			 }

		   });
	/*

		if (mGameSettings.contains(GAME_PREFERENCES_TIPO_RUTA)) {
			spinner.setSelection(mGameSettings.getInt(GAME_PREFERENCES_TIPO_RUTA,0));
		}

		// Handle spinner selections
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent,
					View itemSelected, int selectedItemPosition, long selectedId) {
				Editor editor = mGameSettings.edit();
				editor.putInt(GAME_PREFERENCES_TIPO_RUTA, selectedItemPosition);
				editor.commit();

				fillRutaSpinner(String.valueOf(selectedItemPosition+1));
				// CARGAR LAS RUTAS
			}

			public void onNothingSelected(AdapterView<?> parent){
			}
		});*/
	}

	/**
	 * Initialize the checkbox CallePublica
	 */
	private void initCallePublicaCheckBox() {
		// Populate Spinner control with genders
		final CheckBox _checkbox= (CheckBox) findViewById(R.id.CheckBox_CallePublica);
		final LinearLayout _layout_CallePublica = (LinearLayout) findViewById(R.id.Layout_CallePublica);
		   _checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			  @Override
			  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			  {
				if (buttonView.isChecked()) {
				//checked
					_layout_CallePublica.setVisibility(View.GONE);
				}
				else
				{
					_layout_CallePublica.setVisibility(View.VISIBLE);
				//not checked
				}

			 }

		   });
	}
	/**
	 * Initialize the checkbox CallePublica
	 */
	private void initCallePublicaTitularCheckBox() {
		// Populate Spinner control with genders
		final CheckBox _checkbox= (CheckBox) findViewById(R.id.CheckBox_CallePublicaTitular);
		final LinearLayout _layout_CallePublica = (LinearLayout) findViewById(R.id.Layout_CallePublicaTitular);
		   _checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			  @Override
			  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			  {
				if (buttonView.isChecked()) {
				//checked
					_layout_CallePublica.setVisibility(View.GONE);
				}
				else
				{
					_layout_CallePublica.setVisibility(View.VISIBLE);
				//not checked
				}

			 }

		   });
	}
/*testigo*/
	/**
	 * Initialize the checkbox CallePublica
	 */
	private void initCallePublicaTestigoCheckBox() {
		// Populate Spinner control with genders
		final CheckBox _checkbox= (CheckBox) findViewById(R.id.CheckBox_CallePublicaTestigo);
		final LinearLayout _layout_CallePublica = (LinearLayout) findViewById(R.id.Layout_CallePublicaTestigo);
		   _checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			  @Override
			  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			  {
				if (buttonView.isChecked()) {
				//checked
					_layout_CallePublica.setVisibility(View.GONE);
				}
				else
				{
					_layout_CallePublica.setVisibility(View.VISIBLE);
				//not checked
				}

			 }

		   });
	}

	/**
	 * Initialize the checkbox AlturaSinNumero
	 */
	private void initAlturaSinNumeroCheckBox() {
		// Populate Spinner control with genders
		final CheckBox _checkbox= (CheckBox) findViewById(R.id.CheckBox_AlturaSinNumero);
		final LinearLayout _layout_AlturaSinNumero = (LinearLayout) findViewById(R.id.Layout_AlturaSinNumero);
		   _checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			  @Override
			  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			  {
				if (buttonView.isChecked()) {
				//checked
					_layout_AlturaSinNumero.setVisibility(View.GONE);
				}
				else
				{
					_layout_AlturaSinNumero.setVisibility(View.VISIBLE);
				//not checked
				}

			 }

		   });
	}
	/**
	 * Initialize the checkbox AlturaSinNumero
	 */
	private void initAlturaSinNumeroTitularCheckBox() {
		// Populate Spinner control with genders
		final CheckBox _checkbox= (CheckBox) findViewById(R.id.CheckBox_AlturaSinNumeroTitular);
		final LinearLayout _layout_AlturaSinNumero = (LinearLayout) findViewById(R.id.Layout_AlturaSinNumeroTitular);
		   _checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			  @Override
			  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			  {
				if (buttonView.isChecked()) {
				//checked
					_layout_AlturaSinNumero.setVisibility(View.GONE);
				}
				else
				{
					_layout_AlturaSinNumero.setVisibility(View.VISIBLE);
				//not checked
				}

			 }

		   });
	}
/*testigo*/
	/**
	 * Initialize the checkbox AlturaSinNumero
	 */
	private void initAlturaSinNumeroTestigoCheckBox() {
		// Populate Spinner control with genders
		final CheckBox _checkbox= (CheckBox) findViewById(R.id.CheckBox_AlturaSinNumeroTestigo);
		final LinearLayout _layout_AlturaSinNumero = (LinearLayout) findViewById(R.id.Layout_AlturaSinNumeroTestigo);
		   _checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			  @Override
			  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			  {
				if (buttonView.isChecked()) {
				//checked
					_layout_AlturaSinNumero.setVisibility(View.GONE);
				}
				else
				{
					_layout_AlturaSinNumero.setVisibility(View.VISIBLE);
				//not checked
				}

			 }

		   });
	}

	/* Initialize Documentos*/
	 private Spinner myTipoDocumentoSpinner;
	 private GenericSpinAdapter<TipoDocumento> myTipoDocumentoAdapter;

	 private void initDocumentosSpinner() {


		   List<TipoDocumento> lstTiposDocumento = someExpensiveObject.lstTiposDocumento;
			myTipoDocumentoAdapter= new GenericSpinAdapter<TipoDocumento>(this, layout.simple_spinner_item,lstTiposDocumento);
			myTipoDocumentoAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
			myTipoDocumentoSpinner.setAdapter(myTipoDocumentoAdapter);
			myTipoDocumentoSpinner.setSelection(myTipoDocumentoSpinner.getCount());

	 }

	 private Spinner myTipoDocumentoTitularSpinner;
	 private GenericSpinAdapter<TipoDocumento> myTipoDocumentoTitularAdapter;

	 private void initDocumentosTitularSpinner() {


		   List<TipoDocumento> lstTiposDocumento = someExpensiveObject.lstTiposDocumento;
			myTipoDocumentoTitularAdapter= new GenericSpinAdapter<TipoDocumento>(this, layout.simple_spinner_item,lstTiposDocumento);
			myTipoDocumentoTitularAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
			myTipoDocumentoTitularSpinner.setAdapter(myTipoDocumentoTitularAdapter);
			myTipoDocumentoTitularSpinner.setSelection(myTipoDocumentoTitularSpinner.getCount());

	 }
/*testigo*/
	 private Spinner myTipoDocumentoTestigoSpinner;
	 private GenericSpinAdapter<TipoDocumento> myTipoDocumentoTestigoAdapter;

	 private void initDocumentosTestigoSpinner() {


		   List<TipoDocumento> lstTiposDocumento = someExpensiveObject.lstTiposDocumento;
			myTipoDocumentoTestigoAdapter= new GenericSpinAdapter<TipoDocumento>(this, layout.simple_spinner_item,lstTiposDocumento);
			myTipoDocumentoTestigoAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
			myTipoDocumentoTestigoSpinner.setAdapter(myTipoDocumentoTestigoAdapter);
			myTipoDocumentoTestigoSpinner.setSelection(myTipoDocumentoTestigoSpinner.getCount());

	 }

	 /* Initialize TiposPatentes*/
	 private Spinner myTipoPatenteSpinner;


	/* Initialize TiposVehiculos*/
	 private Spinner myTipoVehiculoSpinner;
	 private GenericSpinAdapter<TipoVehiculo> myTipoVehiculoAdapter;


	 private boolean isSpinnerInitialTipoVehiculo = true;
	 private void initTipoVehiculoListeners() {
		 myTipoVehiculoSpinner.setOnTouchListener(new View.OnTouchListener() {
			 @Override
			 public boolean onTouch(View v, MotionEvent event) {
				 Tools.Log(Log.ERROR, "myTipoVehiculoSpinner", "onTouch");
				 IsRecreating = false;
				 CreateActaActivity.this.IsRestoringLastData = false;
				 isSpinnerInitialTipoVehiculo = false;
				 return false;
			 }
		 });
		 // Handle spinner selections
		 myTipoVehiculoSpinner.setOnItemSelectedListener(
				 new AdapterView.OnItemSelectedListener() {


					 @Override
					 public void onItemSelected(
							 AdapterView<?> parent,
							 View view,
							 int position,
							 long id) {

						 if (isSpinnerInitialTipoVehiculo) {
							 isSpinnerInitialTipoVehiculo = false;
							 return;
						 }

						 if (CreateActaActivity.this.IsRestoringLastData == true) return;

						 TipoVehiculo d = myTipoVehiculoAdapter.getItem(position);

						 if (d.getId() == null || d.getId().equals("")) return;

						 mCurrenActaSettings.edit().putInt(CURRENT_ACTA_TIPO_VEHICULO, position).commit();
						 String requiereDominio = d.getRequiereDominio();

						 if (requiereDominio == null || requiereDominio.equals("S")) {
							 subPnlTipoVehiculo.setVisibility(View.VISIBLE);
						 } else {
							 subPnlTipoVehiculo.setVisibility(View.GONE);
							 editText_Dominio.setText("");
							 editText_ModeloVehiculo.setText("");
							 editText_AnioModeloVehiculo.setText("");
							 editText_Marca.setText("");
							 editText_Color.setText("");
							 myMarcaSpinner.setSelection(myMarcaSpinner.getCount(), false);
							 mCurrenActaSettings.edit().putInt(CURRENT_ACTA_MARCA, 0).commit();
							 myColorSpinner.setSelection(myColorSpinner.getCount(), false);
							 mCurrenActaSettings.edit().putInt(CURRENT_ACTA_COLOR, 0).commit();


						 }


					 }

					 public void onNothingSelected(AdapterView<?> parent) {
					 }
				 });

	 }

	 private void initTipoDocumentoListenter(final EditText numero_Documento, final Spinner spinner){


		 spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			 @Override
			 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				 if (spinner.getSelectedItem().toString().equals("NROS.ESPEC.") ||
						 spinner.getSelectedItem().toString().equals("PASAPORTE")) {
					 numero_Documento.setInputType(InputType.TYPE_CLASS_TEXT);
				 } else {
					 numero_Documento.setInputType(InputType.TYPE_CLASS_NUMBER);
				 }
				 numero_Documento.setText(numero_Documento.getText().toString().replaceAll("\\D", ""));
			 }


			 @Override
			 public void onNothingSelected(AdapterView<?> parent) {

			 }
		 });
	 }


	 private void initTiposVehiculoSpinner() {


		List<TipoVehiculo> lstTiposVehiculo = someExpensiveObject.lstTiposVehiculo;
		myTipoVehiculoAdapter = new GenericSpinAdapter<TipoVehiculo>(this, layout.simple_spinner_item,lstTiposVehiculo);
		myTipoVehiculoAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
		myTipoVehiculoSpinner.setAdapter(myTipoVehiculoAdapter);
		myTipoVehiculoSpinner.setSelection(myTipoVehiculoSpinner.getCount());

	 }

	/* Initialize Generos*/
	 /*private Spinner myGeneroSpinner;
	 private GenericSpinAdapter<Genero> myGeneroAdapter;
	 private void initGenerosSpinner() {


		List<Genero> lstGeneros = someExpensiveObject.lstGeneros;
		myGeneroAdapter = new GenericSpinAdapter<Genero>(this,android.R.layout.simple_spinner_item,lstGeneros);
		myGeneroAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		myGeneroSpinner.setAdapter(myGeneroAdapter);
		myGeneroSpinner.setSelection(myGeneroSpinner.getCount());

	 }

	 private Spinner myGeneroTitularSpinner;
	 private GenericSpinAdapter<Genero> myGeneroTitularAdapter;
	 private void initGenerosTitularSpinner() {


		List<Genero> lstGeneros = someExpensiveObject.lstGeneros;
		myGeneroTitularAdapter = new GenericSpinAdapter<Genero>(this,android.R.layout.simple_spinner_item,lstGeneros);
		myGeneroTitularAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		myGeneroTitularSpinner.setAdapter(myGeneroTitularAdapter);
		myGeneroTitularSpinner.setSelection(myGeneroTitularSpinner.getCount());

	 }*/
/* testigo */
	/* private Spinner myGeneroTestigoSpinner;
	 private GenericSpinAdapter<Genero> myGeneroTestigoAdapter;
	 private void initGenerosTestigoSpinner() {


		List<Genero> lstGeneros = someExpensiveObject.lstGeneros;
		myGeneroTestigoAdapter = new GenericSpinAdapter<Genero>(this,android.R.layout.simple_spinner_item,lstGeneros);
		myGeneroTestigoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		myGeneroTestigoSpinner.setAdapter(myGeneroTestigoAdapter);
		myGeneroTestigoSpinner.setSelection(myGeneroTestigoSpinner.getCount());

	 }
    */
	 /* Initialize Marcas */
	 private Spinner myMarcaSpinner;
	 private GenericSpinAdapter<Marca> myMarcaAdapter;
	 private boolean isSpinnerInitialMarcas = true;
	 private void initMarcasListeners()
	 {

		 myMarcaSpinner.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Tools.Log(Log.ERROR, "myMarcaSpinner", "onTouch");
					IsRecreating = false;
					CreateActaActivity.this.IsRestoringLastData = false;
					isSpinnerInitialMarcas = false;
					return false;
				}
			 });
		 myMarcaSpinner.setOnItemSelectedListener(
		              new AdapterView.OnItemSelectedListener() {


		            	  @Override
		                  public void onItemSelected(
		                          AdapterView<?> parent,
		                          View view,
		                          int position,
		                          long id) {

		            		  if(isSpinnerInitialMarcas) { isSpinnerInitialMarcas = false; return;}


		            		  if (CreateActaActivity.this.IsRestoringLastData== true) return;

		                	   Marca d = myMarcaAdapter.getItem(position);

		                	   if (d.getId()==null || d.getId().equals("")) return;

		                	   mCurrenActaSettings.edit().putInt(CURRENT_ACTA_MARCA, position).commit();

		                	   if (d.getId().equals(-1))
		                	   {

		                		   ShowEditText(MARCA,null,true);
		                		   editText_Marca.requestFocus();
		                	   }
		                	   else
		                	   {
		                		   ShowEditText(MARCA,null,false);
		                	   }


		                  }
		            	  @Override
		                  public void onNothingSelected(AdapterView<?> parent) {
		                  }
		              }
		          );
	 }

	 private void initMarcasSpinner() {

		    final Marca[] items = new Marca[someExpensiveObject.lstMarcas.size()];
		    someExpensiveObject.lstMarcas.toArray(items);
			myMarcaAdapter = new GenericSpinAdapter<Marca>(this, layout.simple_spinner_item,items);
			myMarcaAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
			myMarcaSpinner.setAdapter(myMarcaAdapter);

	 }


	 /* Initialize Colores */
	 private Spinner myColorSpinner;
	 private GenericSpinAdapter<Color> myColorAdapter;
	 private boolean isSpinnerInitialColores = true;
	 private void initColoresListeners()
	 {

		 myColorSpinner.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Tools.Log(Log.ERROR, "myColorSpinner", "onTouch");
					IsRecreating = false;
					CreateActaActivity.this.IsRestoringLastData = false;
					isSpinnerInitialColores = false;
					return false;
				}
			 });
		 myColorSpinner.setOnItemSelectedListener(
		              new AdapterView.OnItemSelectedListener() {


		            	  @Override
		                  public void onItemSelected(
		                          AdapterView<?> parent,
		                          View view,
		                          int position,
		                          long id) {

		            		  if(isSpinnerInitialColores) { isSpinnerInitialColores = false; return;}


		            		  if (CreateActaActivity.this.IsRestoringLastData== true) return;

		                	   Color d = myColorAdapter.getItem(position);

		                	   if (d.getId()==null || d.getId().equals("")) return;

		                	   mCurrenActaSettings.edit().putInt(CURRENT_ACTA_COLOR, position).commit();

		                	   if (d.getId().equals(-1))
		                	   {
		                		   ShowEditText(COLOR,null,true);
		                		   editText_Color.requestFocus();
		                	   }
		                	   else
		                	   {
		                		   ShowEditText(COLOR,null,false);
		                	   }

		                  }
		            	  @Override
		                  public void onNothingSelected(AdapterView<?> parent) {
		                  }
		              }
		          );
	 }

	 private void initColoresSpinner() {

		    final Color[] items = new Color[someExpensiveObject.lstColores.size()];
		    someExpensiveObject.lstColores.toArray(items);
			myColorAdapter = new GenericSpinAdapter<Color>(this, layout.simple_spinner_item,items);
			myColorAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
			myColorSpinner.setAdapter(myColorAdapter);

	 }

	 /* Initialize Paises Documento*/
	 private Spinner myPaisDSpinner;
	 private GenericSpinAdapter<Pais> myPaisDAdapter;

	 private boolean isSpinnerInitialPaisDocumento = true;
	 private void initPaisesDListeners()
	 {
		 myPaisDSpinner.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Pais objeto = (Pais) myPaisDSpinner.getSelectedItem();
					if(objeto!=null && (objeto.getId()==null || objeto.getId().equals("") ))
					{
						//myPaisDSpinner.setSelection(3);
						SetSpinnerSelectionByValue(myPaisDAdapter,SPINNER_PAIS_DOCUMENTO,"1");
					}

					Tools.Log(Log.ERROR, "myPaisDSpinner", "onTouch");
					IsRecreating = false;
					CreateActaActivity.this.IsRestoringLastData = false;
					isSpinnerInitialPaisDocumento = false;
					return false;
				}
			 });
			myPaisDSpinner.setOnItemSelectedListener(
		              new AdapterView.OnItemSelectedListener() {


		            	  @Override
		                  public void onItemSelected(
		                          AdapterView<?> parent,
		                          View view,
		                          int position,
		                          long id) {

		            		  if(isSpinnerInitialPaisDocumento)
		            		  {
		            			  isSpinnerInitialPaisDocumento = false;
		            			  String[] sListeners = {
										   SPINNER_PAIS_LICENCIA,SPINNER_PAIS_DOCUMENTO
										  ,SPINNER_PROVINCIA_LICENCIA,SPINNER_PROVINCIA_DOCUMENTO,SPINNER_DEPARTAMENTO_DOCUMENTO
										  };
		            			  String ParametroPais = "1";
		            			  String ParametroProvincia= "12";
		            			  try
							      {	DeshabilitarListeners(sListeners);
		            			    fillProvinciaDSpinner(ParametroPais);
								    SetSpinnerSelectionByValue(myProvinciaDAdapter,SPINNER_PROVINCIA_DOCUMENTO,ParametroProvincia);
								    fillDepartamentoDSpinner(ParametroProvincia);
								    HabilitarListeners(sListeners);
								   }catch(Exception ex)
								   {

								   }
		            			  return;
		            		   }


		            		  if (CreateActaActivity.this.IsRestoringLastData== true) return;

		                	   Pais d = myPaisDAdapter.getItem(position);

		                	   if (d.getId()==null || d.getId().equals("")) return;

		                	   mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PAIS_DOCUMENTO, position).commit();

		                	   String ParametroPais = d.getId();
	                		   fillProvinciaDSpinner(ParametroPais);

		                	   if (d.getId().equals("-1"))
		                	   {

		                		   ShowEditText(DOCUMENTO,PAIS,true);
		                		   editText_PaisDocumento.requestFocus();

		                		   DeshabilitarListeners(SPINNER_PROVINCIA_DOCUMENTO, SPINNER_DEPARTAMENTO_DOCUMENTO,SPINNER_LOCALIDAD_DOCUMENTO);
		                		   SetFirstSelection(0,SPINNER_PROVINCIA_DOCUMENTO, SPINNER_DEPARTAMENTO_DOCUMENTO,SPINNER_LOCALIDAD_DOCUMENTO);

		                		   ShowEditText(DOCUMENTO,PROVINCIA,true);
		                		   ShowEditText(DOCUMENTO,DEPARTAMENTO,true);
		                		   ShowEditText(DOCUMENTO,LOCALIDAD,true);

		                		   HabilitarListeners(SPINNER_PROVINCIA_DOCUMENTO, SPINNER_DEPARTAMENTO_DOCUMENTO,SPINNER_LOCALIDAD_DOCUMENTO);


		                	   }
		                	   else
		                	   {
		                		   ShowEditText(DOCUMENTO,PAIS,false);
		                		   ShowEditText(DOCUMENTO,PROVINCIA,false);
		                		   ShowEditText(DOCUMENTO,DEPARTAMENTO,false);
		                		   ShowEditText(DOCUMENTO,LOCALIDAD,false);
		                	   }

		                  }
		            	  @Override
		                  public void onNothingSelected(AdapterView<?> parent) {
		                  }
		              }
		          );

	 }
	 private void initPaisesDSpinner() {

		    final Pais[] items = new Pais[someExpensiveObject.lstPaisesDocumento.size()];
		    someExpensiveObject.lstPaisesDocumento.toArray(items);
			myPaisDAdapter = new GenericSpinAdapter<Pais>(this, layout.simple_spinner_item,items);
			myPaisDAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
			myPaisDSpinner.setAdapter(myPaisDAdapter);
			//myPaisDSpinner.setSelection(myPaisDSpinner.getCount());
			/*
			if (this.IsRecreating == false )
			{
				myPaisDSpinner.setSelection(myPaisDSpinner.getCount());
				editText_PaisLicencia.setVisibility(View.GONE);
			}
			*/
	 }

	 /* Initialize Paises Documento Titular*/
	 private Spinner myPaisDTitularSpinner;
	 private GenericSpinAdapter<Pais> myPaisDTitularAdapter;

	 private boolean isSpinnerInitialPaisDocumentoTitular = true;
	 private void initPaisesDTitularListeners()
	 {
		 myPaisDTitularSpinner.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Pais objeto = (Pais) myPaisDTitularSpinner.getSelectedItem();
					if(objeto!=null && (objeto.getId()==null || objeto.getId().equals("") ))
					{
						//myPaisDTitularSpinner.setSelection(3);
						SetSpinnerSelectionByValue(myPaisDTitularAdapter,SPINNER_PAIS_DOCUMENTO_TITULAR,"1");
					}

					Tools.Log(Log.ERROR, "myPaisDTitularSpinner", "onTouch");
					IsRecreating = false;
					CreateActaActivity.this.IsRestoringLastData = false;
					isSpinnerInitialPaisDocumentoTitular = false;
					return false;
				}
			 });
			myPaisDTitularSpinner.setOnItemSelectedListener(
		              new AdapterView.OnItemSelectedListener() {


		            	  @Override
		                  public void onItemSelected(
		                          AdapterView<?> parent,
		                          View view,
		                          int position,
		                          long id) {

		            		  if(isSpinnerInitialPaisDocumentoTitular)
		            		  {
		            			  	isSpinnerInitialPaisDocumentoTitular = false;

			                		  String[] sListeners = {
											   SPINNER_PAIS_DOCUMENTO_TITULAR
											  ,SPINNER_PROVINCIA_DOCUMENTO_TITULAR
											  ,SPINNER_DEPARTAMENTO_DOCUMENTO_TITULAR
											  };
			            			  String ParametroPais = "1";
			            			  String ParametroProvincia = "12";
			            			  try
								      {	DeshabilitarListeners(sListeners);
			            			    fillProvinciaDTitularSpinner(ParametroPais);
									    SetSpinnerSelectionByValue(myProvinciaDTitularAdapter,SPINNER_PROVINCIA_DOCUMENTO_TITULAR,ParametroProvincia);
									    fillDepartamentoDTitularSpinner(ParametroProvincia);
									    HabilitarListeners(sListeners);
									   }catch(Exception ex)
									   {

									   }

		            		  		return;
		            		  }


		            		  if (CreateActaActivity.this.IsRestoringLastData== true) return;

		                	   Pais d = myPaisDTitularAdapter.getItem(position);

		                	   if (d.getId()==null || d.getId().equals("")) return;

		                	   mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PAIS_DOCUMENTO_TITULAR, position).commit();

		                	   String ParametroPais = d.getId();
	                		   fillProvinciaDTitularSpinner(ParametroPais);

		                	   if (d.getId().equals("-1"))
		                	   {

		                		   ShowEditText(DOCUMENTO_TITULAR,PAIS,true);
		                		   editText_PaisDocumentoTitular.requestFocus();

		                		   DeshabilitarListeners(SPINNER_PROVINCIA_DOCUMENTO_TITULAR, SPINNER_DEPARTAMENTO_DOCUMENTO_TITULAR,SPINNER_LOCALIDAD_DOCUMENTO_TITULAR);
		                		   SetFirstSelection(0,SPINNER_PROVINCIA_DOCUMENTO_TITULAR, SPINNER_DEPARTAMENTO_DOCUMENTO_TITULAR,SPINNER_LOCALIDAD_DOCUMENTO_TITULAR);

		                		   ShowEditText(DOCUMENTO_TITULAR,PROVINCIA,true);
		                		   ShowEditText(DOCUMENTO_TITULAR,DEPARTAMENTO,true);
		                		   ShowEditText(DOCUMENTO_TITULAR,LOCALIDAD,true);

		                		   HabilitarListeners(SPINNER_PROVINCIA_DOCUMENTO_TITULAR, SPINNER_DEPARTAMENTO_DOCUMENTO_TITULAR,SPINNER_LOCALIDAD_DOCUMENTO_TITULAR);


		                	   }
		                	   else
		                	   {
		                		   ShowEditText(DOCUMENTO_TITULAR,PAIS,false);
		                		   ShowEditText(DOCUMENTO_TITULAR,PROVINCIA,false);
		                		   ShowEditText(DOCUMENTO_TITULAR,DEPARTAMENTO,false);
		                		   ShowEditText(DOCUMENTO_TITULAR,LOCALIDAD,false);
		                	   }

		                  }
		            	  @Override
		                  public void onNothingSelected(AdapterView<?> parent) {
		                  }
		              }
		          );

	 }
	 private void initPaisesDTitularSpinner() {

		    final Pais[] items = new Pais[someExpensiveObject.lstPaisesDocumentoTitular.size()];
		    someExpensiveObject.lstPaisesDocumentoTitular.toArray(items);
			myPaisDTitularAdapter = new GenericSpinAdapter<Pais>(this, layout.simple_spinner_item,items);
			myPaisDTitularAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
			myPaisDTitularSpinner.setAdapter(myPaisDTitularAdapter);
			//myPaisDSpinner.setSelection(myPaisDSpinner.getCount());
			/*
			if (this.IsRecreating == false )
			{
				myPaisDSpinner.setSelection(myPaisDSpinner.getCount());
				editText_PaisLicencia.setVisibility(View.GONE);
			}
			*/
	 }

	 /* Initialize Paises Documento Testigo*/
	 private Spinner myPaisDTestigoSpinner;
	 private GenericSpinAdapter<Pais> myPaisDTestigoAdapter;

	 private boolean isSpinnerInitialPaisDocumentoTestigo = true;
	 private void initPaisesDTestigoListeners()
	 {
		 myPaisDTestigoSpinner.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Pais objeto = (Pais) myPaisDTestigoSpinner.getSelectedItem();
					if(objeto!=null && (objeto.getId()==null || objeto.getId().equals("") ))
					{
						//myPaisDTestigoSpinner.setSelection(3);
					    SetSpinnerSelectionByValue(myPaisDTestigoAdapter,SPINNER_PAIS_DOCUMENTO_TESTIGO,"1");
					}

					Tools.Log(Log.ERROR, "myPaisDTestigoSpinner", "onTouch");
					IsRecreating = false;
					CreateActaActivity.this.IsRestoringLastData = false;
					isSpinnerInitialPaisDocumentoTestigo = false;
					return false;
				}
			 });
			myPaisDTestigoSpinner.setOnItemSelectedListener(
		              new AdapterView.OnItemSelectedListener() {


		            	  @Override
		                  public void onItemSelected(
		                          AdapterView<?> parent,
		                          View view,
		                          int position,
		                          long id) {

		            		  if(isSpinnerInitialPaisDocumentoTestigo)
		            		  {
		            			  isSpinnerInitialPaisDocumentoTestigo= false;
		                		  String[] sListeners = {
										   SPINNER_PAIS_DOCUMENTO_TESTIGO
										  ,SPINNER_PROVINCIA_DOCUMENTO_TESTIGO
										  ,SPINNER_DEPARTAMENTO_DOCUMENTO_TESTIGO
										  };
		            			  String ParametroPais = "1";
		            			  String ParametroProvincia = "12";
		            			  try
							      {	DeshabilitarListeners(sListeners);
		            			    fillProvinciaDTestigoSpinner(ParametroPais);
								    SetSpinnerSelectionByValue(myProvinciaDTestigoAdapter,SPINNER_PROVINCIA_DOCUMENTO_TESTIGO,ParametroProvincia);
								    fillDepartamentoDTestigoSpinner(ParametroProvincia);
								    HabilitarListeners(sListeners);
								   }catch(Exception ex)
								   {

								   }

		            			  return;
		            		  }


		            		  if (CreateActaActivity.this.IsRestoringLastData== true) return;

		                	   Pais d = myPaisDTestigoAdapter.getItem(position);

		                	   if (d.getId()==null || d.getId().equals("")) return;

		                	   mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PAIS_DOCUMENTO_TESTIGO, position).commit();

		                	   String ParametroPais = d.getId();
	                		   fillProvinciaDTestigoSpinner(ParametroPais);

		                	   if (d.getId().equals("-1"))
		                	   {

		                		   ShowEditText(DOCUMENTO_TESTIGO,PAIS,true);
		                		   editText_PaisDocumentoTestigo.requestFocus();

		                		   DeshabilitarListeners(SPINNER_PROVINCIA_DOCUMENTO_TESTIGO, SPINNER_DEPARTAMENTO_DOCUMENTO_TESTIGO,SPINNER_LOCALIDAD_DOCUMENTO_TESTIGO);
		                		   SetFirstSelection(0,SPINNER_PROVINCIA_DOCUMENTO_TESTIGO, SPINNER_DEPARTAMENTO_DOCUMENTO_TESTIGO,SPINNER_LOCALIDAD_DOCUMENTO_TESTIGO);

		                		   ShowEditText(DOCUMENTO_TESTIGO,PROVINCIA,true);
		                		   ShowEditText(DOCUMENTO_TESTIGO,DEPARTAMENTO,true);
		                		   ShowEditText(DOCUMENTO_TESTIGO,LOCALIDAD,true);

		                		   HabilitarListeners(SPINNER_PROVINCIA_DOCUMENTO_TESTIGO, SPINNER_DEPARTAMENTO_DOCUMENTO_TESTIGO,SPINNER_LOCALIDAD_DOCUMENTO_TESTIGO);


		                	   }
		                	   else
		                	   {
		                		   ShowEditText(DOCUMENTO_TESTIGO,PAIS,false);
		                		   ShowEditText(DOCUMENTO_TESTIGO,PROVINCIA,false);
		                		   ShowEditText(DOCUMENTO_TESTIGO,DEPARTAMENTO,false);
		                		   ShowEditText(DOCUMENTO_TESTIGO,LOCALIDAD,false);
		                	   }

		                  }
		            	  @Override
		                  public void onNothingSelected(AdapterView<?> parent) {
		                  }
		              }
		          );

	 }
	 private void initPaisesDTestigoSpinner() {

		    final Pais[] items = new Pais[someExpensiveObject.lstPaisesDocumentoTestigo.size()];
		    someExpensiveObject.lstPaisesDocumentoTestigo.toArray(items);
			myPaisDTestigoAdapter = new GenericSpinAdapter<Pais>(this, layout.simple_spinner_item,items);
			myPaisDTestigoAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
			myPaisDTestigoSpinner.setAdapter(myPaisDTestigoAdapter);
			//myPaisDSpinner.setSelection(myPaisDSpinner.getCount());
			/*
			if (this.IsRecreating == false )
			{
				myPaisDSpinner.setSelection(myPaisDSpinner.getCount());
				editText_PaisLicencia.setVisibility(View.GONE);
			}
			*/
	 }


	 private Spinner myProvinciaDSpinner;
	 private GenericSpinAdapter<Provincia> myProvinciaDAdapter;
	 private Provincia[] itemsProvinciaD;
	 private boolean isSpinnerInitialProvinciaDocumento = true;
	 private void initProvinciasDListeners()
	 {

		 myProvinciaDSpinner.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Tools.Log(Log.ERROR, "myProvinciaDSpinner", "onTouch");
					IsRecreating = false;
					CreateActaActivity.this.IsRestoringLastData = false;
					isSpinnerInitialProvinciaDocumento = false;
					return false;
				}
			 });


			myProvinciaDSpinner.setOnItemSelectedListener(
		              new AdapterView.OnItemSelectedListener() {

		            	  @Override
		                  public void onItemSelected(
		                          AdapterView<?> parent,
		                          View view,
		                          int position,
		                          long id) {

		            		  if(isSpinnerInitialProvinciaDocumento) { isSpinnerInitialProvinciaDocumento = false; return;}


		            		  if (CreateActaActivity.this.IsRestoringLastData== true)  return;

		                	   Provincia d = myProvinciaDAdapter.getItem(position);

		                	   if (d.getId()==null || d.getId().equals("")) return;

		                	   mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PROVINCIA_DOCUMENTO,position).commit();

		                	   String ParametroProvincia = d.getId();
		                	   fillDepartamentoDSpinner(ParametroProvincia);

		                	   if (d.getId().equals("-1"))
		                	   {
		                		   ShowEditText(DOCUMENTO, PROVINCIA, true);
		                		   editText_ProvinciaDocumento.requestFocus();

		                		   DeshabilitarListeners(SPINNER_DEPARTAMENTO_DOCUMENTO,SPINNER_LOCALIDAD_DOCUMENTO);
		                		   SetFirstSelection(0, SPINNER_DEPARTAMENTO_DOCUMENTO,SPINNER_LOCALIDAD_DOCUMENTO);

		                		   ShowEditText(DOCUMENTO, DEPARTAMENTO, true);
		                		   ShowEditText(DOCUMENTO, LOCALIDAD, true);

		                		   HabilitarListeners( SPINNER_DEPARTAMENTO_DOCUMENTO,SPINNER_LOCALIDAD_DOCUMENTO);
		                	   }
		                	   else
		                	   {
		                		   //ShowEditText(DOCUMENTO, PAIS, false);
		                		   ShowEditText(DOCUMENTO, PROVINCIA, false);
		                		   ShowEditText(DOCUMENTO, DEPARTAMENTO, false);
		                		   ShowEditText(DOCUMENTO, LOCALIDAD, false);
		                	   }



		                  }
		            	  @Override
		                  public void onNothingSelected(AdapterView<?> parent) {
		                  }
		              }
		          );
	 }
	 private void initProvinciasDSpinner()
	 {
		    itemsProvinciaD = new Provincia[someExpensiveObject.lstProvinciasDocumento.size()];
			someExpensiveObject.lstProvinciasDocumento.toArray(itemsProvinciaD); // fill the array

			myProvinciaDAdapter = new GenericSpinAdapter<Provincia>(this, layout.simple_spinner_item,itemsProvinciaD);
			myProvinciaDAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
			myProvinciaDSpinner.setAdapter(myProvinciaDAdapter);

		/*
		if (this.IsRecreating == false)
		{
			myProvinciaDSpinner.setSelection(myProvinciaDSpinner.getCount());
			editText_ProvinciaDocumento.setVisibility(View.GONE);
		}*/
	 }

	 private void fillProvinciaDSpinner(String pIdPais)
	 {
			if (pIdPais==null || pIdPais.equals("") )//|| pIdPais.equals("-1") )
			{
				return;
			}
				int cantidadProvinciasEncontradas = 0;

				ProvinciaRules provinciaRules = new ProvinciaRules(this);


				List<Provincia> list = provinciaRules.getByPaisForSpinner(pIdPais);//Provincia.GetByPais(pIdPais);
				cantidadProvinciasEncontradas = list.size(); // cuantas provincias se encontraron

				itemsProvinciaD= new Provincia[list.size()];
				list.toArray(itemsProvinciaD); // fill the array

				myProvinciaDAdapter = new GenericSpinAdapter<Provincia>(this, layout.simple_spinner_item,itemsProvinciaD);
				myProvinciaDAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
				myProvinciaDSpinner.setAdapter(myProvinciaDAdapter);

				if (this.IsRecreating==false)
				{
					myProvinciaDSpinner.setSelection(myProvinciaDSpinner.getCount());
					editText_ProvinciaDocumento.setVisibility(View.GONE);
					someExpensiveObject.lstProvinciasDocumento= list;
					if (cantidadProvinciasEncontradas==2)
					{
						someExpensiveObject.reInitializeList("DOCUMENTO","DEPARTAMENTO");
						initDepartamentosDSpinner();
						myDepartamentoDSpinner.setSelection(myDepartamentoDSpinner.getCount());
						editText_DepartamentoDocumento.setVisibility(View.GONE);

						someExpensiveObject.reInitializeList("DOCUMENTO","LOCALIDAD");
						initLocalidadesDSpinner();
						myLocalidadDSpinner.setSelection(myLocalidadDSpinner.getCount());
						editText_LocalidadDocumento.setVisibility(View.GONE);
					}

				}


		}

	 /*Provincias Titular*/
	 private Spinner myProvinciaDTitularSpinner;
	 private GenericSpinAdapter<Provincia> myProvinciaDTitularAdapter;
	 private Provincia[] itemsProvinciaDTitular;
	 private boolean isSpinnerInitialProvinciaDocumentoTitular = true;
	 private void initProvinciasDTitularListeners()
	 {

		 myProvinciaDTitularSpinner.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Tools.Log(Log.ERROR, "myProvinciaDTitularSpinner", "onTouch");
					IsRecreating = false;
					CreateActaActivity.this.IsRestoringLastData = false;
					isSpinnerInitialProvinciaDocumentoTitular = false;
					return false;
				}
			 });


			myProvinciaDTitularSpinner.setOnItemSelectedListener(
		              new AdapterView.OnItemSelectedListener() {

		            	  @Override
		                  public void onItemSelected(
		                          AdapterView<?> parent,
		                          View view,
		                          int position,
		                          long id) {

		            		  if(isSpinnerInitialProvinciaDocumentoTitular) { isSpinnerInitialProvinciaDocumentoTitular = false; return;}


		            		  if (CreateActaActivity.this.IsRestoringLastData== true)  return;

		                	   Provincia d = myProvinciaDTitularAdapter.getItem(position);

		                	   if (d.getId()==null || d.getId().equals("")) return;

		                	   mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PROVINCIA_DOCUMENTO_TITULAR,position).commit();

		                	   String ParametroProvincia = d.getId();
		                	   fillDepartamentoDTitularSpinner(ParametroProvincia);

		                	   if (d.getId().equals("-1"))
		                	   {
		                		   ShowEditText(DOCUMENTO_TITULAR, PROVINCIA, true);
		                		   editText_ProvinciaDocumentoTitular.requestFocus();

		                		   DeshabilitarListeners(SPINNER_DEPARTAMENTO_DOCUMENTO_TITULAR,SPINNER_LOCALIDAD_DOCUMENTO_TITULAR);
		                		   SetFirstSelection(0, SPINNER_DEPARTAMENTO_DOCUMENTO_TITULAR,SPINNER_LOCALIDAD_DOCUMENTO_TITULAR);

		                		   ShowEditText(DOCUMENTO_TITULAR, DEPARTAMENTO, true);
		                		   ShowEditText(DOCUMENTO_TITULAR, LOCALIDAD, true);

		                		   HabilitarListeners( SPINNER_DEPARTAMENTO_DOCUMENTO_TITULAR,SPINNER_LOCALIDAD_DOCUMENTO_TITULAR);
		                	   }
		                	   else
		                	   {
		                		   //ShowEditText(DOCUMENTO, PAIS, false);
		                		   ShowEditText(DOCUMENTO_TITULAR, PROVINCIA, false);
		                		   ShowEditText(DOCUMENTO_TITULAR, DEPARTAMENTO, false);
		                		   ShowEditText(DOCUMENTO_TITULAR, LOCALIDAD, false);
		                	   }


		                  }
		            	  @Override
		                  public void onNothingSelected(AdapterView<?> parent) {
		                  }
		              }
		          );
	 }
	 private void initProvinciasDTitularSpinner()
	 {
		    itemsProvinciaDTitular = new Provincia[someExpensiveObject.lstProvinciasDocumentoTitular.size()];
			someExpensiveObject.lstProvinciasDocumentoTitular.toArray(itemsProvinciaDTitular); // fill the array

			myProvinciaDTitularAdapter = new GenericSpinAdapter<Provincia>(this, layout.simple_spinner_item,itemsProvinciaDTitular);
			myProvinciaDTitularAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
			myProvinciaDTitularSpinner.setAdapter(myProvinciaDTitularAdapter);

		/*
		if (this.IsRecreating == false)
		{
			myProvinciaDSpinner.setSelection(myProvinciaDSpinner.getCount());
			editText_ProvinciaDocumento.setVisibility(View.GONE);
		}*/
	 }
	 private void fillProvinciaDTitularSpinner(String pIdPais)
		{
			if (pIdPais==null || pIdPais.equals("") )//|| pIdPais.equals("-1") )
			{
				return;
			}
				int cantidadProvinciasEncontradas = 0;

				ProvinciaRules provinciaRules = new ProvinciaRules(this);


				List<Provincia> list = provinciaRules.getByPaisForSpinner(pIdPais);//Provincia.GetByPais(pIdPais);
				cantidadProvinciasEncontradas = list.size(); // cuantas provincias se encontraron

				itemsProvinciaDTitular= new Provincia[list.size()];
				list.toArray(itemsProvinciaDTitular); // fill the array

				myProvinciaDTitularAdapter = new GenericSpinAdapter<Provincia>(this, layout.simple_spinner_item,itemsProvinciaDTitular);
				myProvinciaDTitularAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
				myProvinciaDTitularSpinner.setAdapter(myProvinciaDTitularAdapter);

				if (this.IsRecreating==false)
				{
					myProvinciaDTitularSpinner.setSelection(myProvinciaDTitularSpinner.getCount());
					editText_ProvinciaDocumentoTitular.setVisibility(View.GONE);
					someExpensiveObject.lstProvinciasDocumentoTitular= list;
					if (cantidadProvinciasEncontradas==2)
					{
						someExpensiveObject.reInitializeList("DOCUMENTO_TITULAR","DEPARTAMENTO");
						initDepartamentosDTitularSpinner();
						myDepartamentoDTitularSpinner.setSelection(myDepartamentoDTitularSpinner.getCount());
						editText_DepartamentoDocumentoTitular.setVisibility(View.GONE);

						someExpensiveObject.reInitializeList("DOCUMENTO_TITULAR","LOCALIDAD");
						initLocalidadesDTitularSpinner();
						myLocalidadDTitularSpinner.setSelection(myLocalidadDTitularSpinner.getCount());
						editText_LocalidadDocumentoTitular.setVisibility(View.GONE);
					}

				}


		}

	  /*End Provincias Titular*/
/*testigo provincias */
	 /*Provincias Testigo*/
	 private Spinner myProvinciaDTestigoSpinner;
	 private GenericSpinAdapter<Provincia> myProvinciaDTestigoAdapter;
	 private Provincia[] itemsProvinciaDTestigo;
	 private boolean isSpinnerInitialProvinciaDocumentoTestigo= true;
	 private void initProvinciasDTestigoListeners()
	 {

		 myProvinciaDTestigoSpinner.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Tools.Log(Log.ERROR, "myProvinciaDTestigoSpinner", "onTouch");
					IsRecreating = false;
					CreateActaActivity.this.IsRestoringLastData = false;
					isSpinnerInitialProvinciaDocumentoTestigo = false;
					return false;
				}
			 });


			myProvinciaDTestigoSpinner.setOnItemSelectedListener(
		              new AdapterView.OnItemSelectedListener() {

		            	  @Override
		                  public void onItemSelected(
		                          AdapterView<?> parent,
		                          View view,
		                          int position,
		                          long id) {

		            		  if(isSpinnerInitialProvinciaDocumentoTestigo) { isSpinnerInitialProvinciaDocumentoTestigo = false; return;}


		            		  if (CreateActaActivity.this.IsRestoringLastData== true)  return;

		                	   Provincia d = myProvinciaDTestigoAdapter.getItem(position);

		                	   if (d.getId()==null || d.getId().equals("")) return;

		                	   mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PROVINCIA_DOCUMENTO_TESTIGO,position).commit();

		                	   String ParametroProvincia = d.getId();
		                	   fillDepartamentoDTestigoSpinner(ParametroProvincia);

		                	   if (d.getId().equals("-1"))
		                	   {
		                		   ShowEditText(DOCUMENTO_TESTIGO, PROVINCIA, true);
		                		   editText_ProvinciaDocumentoTestigo.requestFocus();

		                		   DeshabilitarListeners(SPINNER_DEPARTAMENTO_DOCUMENTO_TESTIGO,SPINNER_LOCALIDAD_DOCUMENTO_TESTIGO);
		                		   SetFirstSelection(0, SPINNER_DEPARTAMENTO_DOCUMENTO_TESTIGO,SPINNER_LOCALIDAD_DOCUMENTO_TESTIGO);

		                		   ShowEditText(DOCUMENTO_TESTIGO, DEPARTAMENTO, true);
		                		   ShowEditText(DOCUMENTO_TESTIGO, LOCALIDAD, true);

		                		   HabilitarListeners( SPINNER_DEPARTAMENTO_DOCUMENTO_TESTIGO,SPINNER_LOCALIDAD_DOCUMENTO_TESTIGO);
		                	   }
		                	   else
		                	   {
		                		   //ShowEditText(DOCUMENTO, PAIS, false);
		                		   ShowEditText(DOCUMENTO_TESTIGO, PROVINCIA, false);
		                		   ShowEditText(DOCUMENTO_TESTIGO, DEPARTAMENTO, false);
		                		   ShowEditText(DOCUMENTO_TESTIGO, LOCALIDAD, false);
		                	   }


		                  }
		            	  @Override
		                  public void onNothingSelected(AdapterView<?> parent) {
		                  }
		              }
		          );
	 }
	 private void initProvinciasDTestigoSpinner()
	 {
		    itemsProvinciaDTestigo = new Provincia[someExpensiveObject.lstProvinciasDocumentoTestigo.size()];
			someExpensiveObject.lstProvinciasDocumentoTestigo.toArray(itemsProvinciaDTestigo); // fill the array

			myProvinciaDTestigoAdapter = new GenericSpinAdapter<Provincia>(this, layout.simple_spinner_item,itemsProvinciaDTestigo);
			myProvinciaDTestigoAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
			myProvinciaDTestigoSpinner.setAdapter(myProvinciaDTestigoAdapter);

		/*
		if (this.IsRecreating == false)
		{
			myProvinciaDSpinner.setSelection(myProvinciaDSpinner.getCount());
			editText_ProvinciaDocumento.setVisibility(View.GONE);
		}*/
	 }
	 private void fillProvinciaDTestigoSpinner(String pIdPais)
		{
			if (pIdPais==null || pIdPais.equals(""))// || pIdPais.equals("-1") )
			{
				return;
			}
				int cantidadProvinciasEncontradas = 0;

				ProvinciaRules provinciaRules = new ProvinciaRules(this);


				List<Provincia> list = provinciaRules.getByPaisForSpinner(pIdPais);//Provincia.GetByPais(pIdPais);
				cantidadProvinciasEncontradas = list.size(); // cuantas provincias se encontraron

				itemsProvinciaDTestigo= new Provincia[list.size()];
				list.toArray(itemsProvinciaDTestigo); // fill the array

				myProvinciaDTestigoAdapter = new GenericSpinAdapter<Provincia>(this, layout.simple_spinner_item,itemsProvinciaDTestigo);
				myProvinciaDTestigoAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
				myProvinciaDTestigoSpinner.setAdapter(myProvinciaDTestigoAdapter);

				if (this.IsRecreating==false)
				{
					myProvinciaDTestigoSpinner.setSelection(myProvinciaDTestigoSpinner.getCount());
					editText_ProvinciaDocumentoTestigo.setVisibility(View.GONE);
					someExpensiveObject.lstProvinciasDocumentoTestigo= list;
					if (cantidadProvinciasEncontradas==2)
					{
						someExpensiveObject.reInitializeList("DOCUMENTO_TESTIGO","DEPARTAMENTO");
						initDepartamentosDTestigoSpinner();
						myDepartamentoDTestigoSpinner.setSelection(myDepartamentoDTestigoSpinner.getCount());
						editText_DepartamentoDocumentoTestigo.setVisibility(View.GONE);

						someExpensiveObject.reInitializeList("DOCUMENTO_TESTIGO","LOCALIDAD");
						initLocalidadesDTestigoSpinner();
						myLocalidadDTestigoSpinner.setSelection(myLocalidadDTestigoSpinner.getCount());
						editText_LocalidadDocumentoTestigo.setVisibility(View.GONE);
					}

				}


		}

	  /*End Provincias testigo*/

	 /*   Fill Departamentos  segun pais seleccionado */
	 private Spinner myDepartamentoDSpinner;
	 private GenericSpinAdapter<Departamento> myDepartamentoDAdapter;
	 private Departamento[] itemsDepartamentoD;
	 private boolean isSpinnerInitialDepartamentoDocumento = true;
	 private void initDepartamentosDListeners()
	 {
		 myDepartamentoDSpinner.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Tools.Log(Log.ERROR, "myDepartamentoDSpinner", "onTouch");
					IsRecreating = false;
					CreateActaActivity.this.IsRestoringLastData = false;
					isSpinnerInitialDepartamentoDocumento = false;
					return false;
				}
			 });


			myDepartamentoDSpinner.setOnItemSelectedListener(
		              new AdapterView.OnItemSelectedListener() {


		            	  @Override
		                  public void onItemSelected(
		                          AdapterView<?> parent,
		                          View view,
		                          int position,
		                          long id) {

		            		  if(isSpinnerInitialDepartamentoDocumento) { isSpinnerInitialDepartamentoDocumento = false; return;}

		            		  if (CreateActaActivity.this.IsRestoringLastData== true) return;

		            		  Departamento d = myDepartamentoDAdapter.getItem(position);

		            		  if (d.getId()==null || d.getId().equals("")) return;

		            		  mCurrenActaSettings.edit().putInt(CURRENT_ACTA_DEPARTAMENTO_DOCUMENTO, position).commit();

	                		   String ParametroDepartamento= d.getId().toString();
	                		   fillLocalidadDSpinner(ParametroDepartamento);

		                	   if (d.getId().equals(-1))
		                	   {

		                		   ShowEditText(DOCUMENTO, DEPARTAMENTO, true);
		                		   //editText_DepartamentoDocumento.requestFocus();

		                		   DeshabilitarListeners(SPINNER_LOCALIDAD_DOCUMENTO);
		                		   SetFirstSelection(0,SPINNER_LOCALIDAD_DOCUMENTO);
		                		   ShowEditText(DOCUMENTO, LOCALIDAD, true);

		                		   HabilitarListeners(SPINNER_LOCALIDAD_DOCUMENTO);
		                	   }
		                	   else
		                	   {
		                		   //ShowEditText(DOCUMENTO, PAIS, false);
		                		   //ShowEditText(DOCUMENTO, PROVINCIA, false);
		                		   ShowEditText(DOCUMENTO, DEPARTAMENTO, false);
		                		   ShowEditText(DOCUMENTO, LOCALIDAD, false);
		                	   }

		            	  }
		            	  @Override
		                  public void onNothingSelected(AdapterView<?> parent) {
		                  }
		              }
		          );
	 }

	 private void initDepartamentosDSpinner()
	 {

		    itemsDepartamentoD = new Departamento[someExpensiveObject.lstDepartamentosDocumento.size()];
		    someExpensiveObject.lstDepartamentosDocumento.toArray(itemsDepartamentoD);
			myDepartamentoDAdapter = new GenericSpinAdapter<Departamento>(this, layout.simple_spinner_item,itemsDepartamentoD);
			myDepartamentoDAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
			myDepartamentoDSpinner.setAdapter(myDepartamentoDAdapter);
			/*
			if (this.IsRecreating == false)
			{
				myDepartamentoDSpinner.setSelection(myDepartamentoDSpinner.getCount());
				editText_DepartamentoDocumento.setVisibility(View.GONE);
			}
			*/



	 }
	 private void fillDepartamentoDSpinner(String pIdProvincia)
		{
			if (pIdProvincia==null || pIdProvincia.equals(""))// || pIdProvincia.equals("-1"))
			{
				//initDepartamentosSpinner();
				return;
			}
				int cantidadDepartamentosEncontrados=0;

				DepartamentoRules departamentoRules = new DepartamentoRules(this);
				List<Departamento> list = departamentoRules.getByProvinciaForSpinner(pIdProvincia);
				cantidadDepartamentosEncontrados = list.size();
				itemsDepartamentoD = new Departamento[list.size()];
				list.toArray(itemsDepartamentoD);
				myDepartamentoDAdapter = new GenericSpinAdapter<Departamento>(this, layout.simple_spinner_item,itemsDepartamentoD);

				myDepartamentoDAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
				myDepartamentoDSpinner.setAdapter(myDepartamentoDAdapter);

				if (this.IsRecreating==false)
				{
					myDepartamentoDSpinner.setSelection(myDepartamentoDSpinner.getCount());
					editText_LocalidadDocumento.setVisibility(View.GONE);
					someExpensiveObject.lstDepartamentosDocumento= list;
					if (cantidadDepartamentosEncontrados==2)
					{
						someExpensiveObject.reInitializeList("DOCUMENTO","LOCALIDAD");
						initLocalidadesDSpinner();
						myLocalidadDSpinner.setSelection(myLocalidadDSpinner.getCount());
						editText_LocalidadDocumento.setVisibility(View.GONE);
					}
				}





		}


	 /* Departamentos Titular*/
	 private Spinner myDepartamentoDTitularSpinner;
	 private GenericSpinAdapter<Departamento> myDepartamentoDTitularAdapter;
	 private Departamento[] itemsDepartamentoDTitular;
	 private boolean isSpinnerInitialDepartamentoDocumentoTitular = true;
	 private void initDepartamentosDTitularListeners()
	 {
		 myDepartamentoDTitularSpinner.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Tools.Log(Log.ERROR, "myDepartamentoDTitularSpinner", "onTouch");
					IsRecreating = false;
					CreateActaActivity.this.IsRestoringLastData = false;
					isSpinnerInitialDepartamentoDocumentoTitular = false;
					return false;
				}
			 });


			myDepartamentoDTitularSpinner.setOnItemSelectedListener(
		              new AdapterView.OnItemSelectedListener() {


		            	  @Override
		                  public void onItemSelected(
		                          AdapterView<?> parent,
		                          View view,
		                          int position,
		                          long id) {

		            		  if(isSpinnerInitialDepartamentoDocumentoTitular) { isSpinnerInitialDepartamentoDocumentoTitular = false; return;}

		            		  if (CreateActaActivity.this.IsRestoringLastData== true) return;

		            		  Departamento d = myDepartamentoDTitularAdapter.getItem(position);

		            		  if (d.getId()==null || d.getId().equals("")) return;

		            		  mCurrenActaSettings.edit().putInt(CURRENT_ACTA_DEPARTAMENTO_DOCUMENTO_TITULAR, position).commit();

	                		   String ParametroDepartamento= d.getId().toString();
	                		   fillLocalidadDTitularSpinner(ParametroDepartamento);

		                	   if (d.getId().equals(-1))
		                	   {

		                		   ShowEditText(DOCUMENTO_TITULAR, DEPARTAMENTO, true);
		                		   //editText_DepartamentoDocumento.requestFocus();

		                		   DeshabilitarListeners(SPINNER_LOCALIDAD_DOCUMENTO_TITULAR);
		                		   SetFirstSelection(0,SPINNER_LOCALIDAD_DOCUMENTO_TITULAR);
		                		   ShowEditText(DOCUMENTO_TITULAR, LOCALIDAD, true);

		                		   HabilitarListeners(SPINNER_LOCALIDAD_DOCUMENTO_TITULAR);
		                	   }
		                	   else
		                	   {
		                		   //ShowEditText(DOCUMENTO, PAIS, false);
		                		   //ShowEditText(DOCUMENTO, PROVINCIA, false);
		                		   ShowEditText(DOCUMENTO_TITULAR, DEPARTAMENTO, false);
		                		   ShowEditText(DOCUMENTO_TITULAR, LOCALIDAD, false);
		                	   }

		                  }
		            	  @Override
		                  public void onNothingSelected(AdapterView<?> parent) {
		                  }
		              }
		          );
	 }

	 private void initDepartamentosDTitularSpinner()
	 {

		    itemsDepartamentoDTitular = new Departamento[someExpensiveObject.lstDepartamentosDocumentoTitular.size()];
		    someExpensiveObject.lstDepartamentosDocumentoTitular.toArray(itemsDepartamentoDTitular);
			myDepartamentoDTitularAdapter = new GenericSpinAdapter<Departamento>(this, layout.simple_spinner_item,itemsDepartamentoDTitular);
			myDepartamentoDTitularAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
			myDepartamentoDTitularSpinner.setAdapter(myDepartamentoDTitularAdapter);
			/*
			if (this.IsRecreating == false)
			{
				myDepartamentoDSpinner.setSelection(myDepartamentoDSpinner.getCount());
				editText_DepartamentoDocumento.setVisibility(View.GONE);
			}
			*/



	 }
	 private void fillDepartamentoDTitularSpinner(String pIdProvincia)
		{
			if (pIdProvincia==null || pIdProvincia.equals("") )//|| pIdProvincia.equals("-1"))
			{
				//initDepartamentosSpinner();
				return;
			}
				int cantidadDepartamentosEncontrados=0;

				DepartamentoRules departamentoRules = new DepartamentoRules(this);
				List<Departamento> list = departamentoRules.getByProvinciaForSpinner(pIdProvincia);
				cantidadDepartamentosEncontrados = list.size();
				itemsDepartamentoDTitular = new Departamento[list.size()];
				list.toArray(itemsDepartamentoDTitular);
				myDepartamentoDTitularAdapter = new GenericSpinAdapter<Departamento>(this, layout.simple_spinner_item,itemsDepartamentoDTitular);

				myDepartamentoDTitularAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
				myDepartamentoDTitularSpinner.setAdapter(myDepartamentoDTitularAdapter);

				if (this.IsRecreating==false)
				{
					myDepartamentoDTitularSpinner.setSelection(myDepartamentoDTitularSpinner.getCount());
					editText_LocalidadDocumentoTitular.setVisibility(View.GONE);
					someExpensiveObject.lstDepartamentosDocumentoTitular= list;
					if (cantidadDepartamentosEncontrados==2)
					{
						someExpensiveObject.reInitializeList("DOCUMENTO_TITULAR","LOCALIDAD");
						initLocalidadesDTitularSpinner();
						myLocalidadDTitularSpinner.setSelection(myLocalidadDTitularSpinner.getCount());
						editText_LocalidadDocumentoTitular.setVisibility(View.GONE);
					}
				}





		}

	 /* End Departamentos Titular*/

	 /* Departamentos Testigo*/
	 private Spinner myDepartamentoDTestigoSpinner;
	 private GenericSpinAdapter<Departamento> myDepartamentoDTestigoAdapter;
	 private Departamento[] itemsDepartamentoDTestigo;
	 private boolean isSpinnerInitialDepartamentoDocumentoTestigo= true;
	 private void initDepartamentosDTestigoListeners()
	 {
		 myDepartamentoDTestigoSpinner.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Tools.Log(Log.ERROR, "myDepartamentoDTestigoSpinner", "onTouch");
					IsRecreating = false;
					CreateActaActivity.this.IsRestoringLastData = false;
					isSpinnerInitialDepartamentoDocumentoTestigo = false;
					return false;
				}
			 });


			myDepartamentoDTestigoSpinner.setOnItemSelectedListener(
		              new AdapterView.OnItemSelectedListener() {


		            	  @Override
		                  public void onItemSelected(
		                          AdapterView<?> parent,
		                          View view,
		                          int position,
		                          long id) {

		            		  if(isSpinnerInitialDepartamentoDocumentoTestigo) { isSpinnerInitialDepartamentoDocumentoTestigo = false; return;}

		            		  if (CreateActaActivity.this.IsRestoringLastData== true) return;

		            		  Departamento d = myDepartamentoDTestigoAdapter.getItem(position);

		            		  if (d.getId()==null || d.getId().equals("")) return;

		            		  mCurrenActaSettings.edit().putInt(CURRENT_ACTA_DEPARTAMENTO_DOCUMENTO_TESTIGO, position).commit();

	                		   String ParametroDepartamento= d.getId().toString();
	                		   fillLocalidadDTestigoSpinner(ParametroDepartamento);

		            		  if (d.getId().equals(-1))
		                	   {

		                		   ShowEditText(DOCUMENTO_TESTIGO, DEPARTAMENTO, true);
		                		   //editText_DepartamentoDocumento.requestFocus();

		                		   DeshabilitarListeners(SPINNER_LOCALIDAD_DOCUMENTO_TESTIGO);
		                		   SetFirstSelection(0,SPINNER_LOCALIDAD_DOCUMENTO_TESTIGO);
		                		   ShowEditText(DOCUMENTO_TESTIGO, LOCALIDAD, true);

		                		   HabilitarListeners(SPINNER_LOCALIDAD_DOCUMENTO_TESTIGO);
		                	   }
		                	   else
		                	   {
		                		   //ShowEditText(DOCUMENTO, PAIS, false);
		                		   //ShowEditText(DOCUMENTO, PROVINCIA, false);
		                		   ShowEditText(DOCUMENTO_TESTIGO, DEPARTAMENTO, false);
		                		   ShowEditText(DOCUMENTO_TESTIGO, LOCALIDAD, false);
		                	   }
		                  }
		            	  @Override
		                  public void onNothingSelected(AdapterView<?> parent) {
		                  }
		              }
		          );
	 }

	 private void initDepartamentosDTestigoSpinner()
	 {

		    itemsDepartamentoDTestigo = new Departamento[someExpensiveObject.lstDepartamentosDocumentoTestigo.size()];
		    someExpensiveObject.lstDepartamentosDocumentoTestigo.toArray(itemsDepartamentoDTestigo);
			myDepartamentoDTestigoAdapter = new GenericSpinAdapter<Departamento>(this, layout.simple_spinner_item,itemsDepartamentoDTestigo);
			myDepartamentoDTestigoAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
			myDepartamentoDTestigoSpinner.setAdapter(myDepartamentoDTestigoAdapter);
			/*
			if (this.IsRecreating == false)
			{
				myDepartamentoDSpinner.setSelection(myDepartamentoDSpinner.getCount());
				editText_DepartamentoDocumento.setVisibility(View.GONE);
			}
			*/



	 }
	 private void fillDepartamentoDTestigoSpinner(String pIdProvincia)
		{
			if (pIdProvincia==null || pIdProvincia.equals(""))// || pIdProvincia.equals("-1"))
			{
				//initDepartamentosSpinner();
				return;
			}
				int cantidadDepartamentosEncontrados=0;

				DepartamentoRules departamentoRules = new DepartamentoRules(this);
				List<Departamento> list = departamentoRules.getByProvinciaForSpinner(pIdProvincia);
				cantidadDepartamentosEncontrados = list.size();
				itemsDepartamentoDTestigo = new Departamento[list.size()];
				list.toArray(itemsDepartamentoDTestigo);
				myDepartamentoDTestigoAdapter = new GenericSpinAdapter<Departamento>(this, layout.simple_spinner_item,itemsDepartamentoDTestigo);

				myDepartamentoDTestigoAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
				myDepartamentoDTestigoSpinner.setAdapter(myDepartamentoDTestigoAdapter);

				if (this.IsRecreating==false)
				{
					myDepartamentoDTestigoSpinner.setSelection(myDepartamentoDTestigoSpinner.getCount());
					editText_LocalidadDocumentoTestigo.setVisibility(View.GONE);
					someExpensiveObject.lstDepartamentosDocumentoTestigo= list;
					if (cantidadDepartamentosEncontrados==2)
					{
						someExpensiveObject.reInitializeList("DOCUMENTO_TESTIGO","LOCALIDAD");
						initLocalidadesDTestigoSpinner();
						myLocalidadDTestigoSpinner.setSelection(myLocalidadDTestigoSpinner.getCount());
						editText_LocalidadDocumentoTestigo.setVisibility(View.GONE);
					}
				}





		}

	 /* End Departamentos Testigo*/

	 /*   Fill Localidades  segun pais seleccionado */
	 private Spinner myLocalidadDSpinner;
	 private GenericSpinAdapter<Localidad> myLocalidadDAdapter;
     private Localidad[] itemsLocalidadesD;
     private boolean isSpinnerInitialLocalidadDocumento = true;
     private void initLocalidadesDListeners()
     {

    	 myLocalidadDSpinner.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Tools.Log(Log.ERROR, "myLocalidadDSpinner", "onTouch");
					IsRecreating = false;
					CreateActaActivity.this.IsRestoringLastData = false;
					isSpinnerInitialLocalidadDocumento = false;
					return false;
				}
			 });
			myLocalidadDSpinner.setOnItemSelectedListener(
		              new AdapterView.OnItemSelectedListener() {


		            	  @Override
		                  public void onItemSelected(
		                          AdapterView<?> parent,
		                          View view,
		                          int position,
		                          long id) {

		            		  if(isSpinnerInitialLocalidadDocumento) { isSpinnerInitialLocalidadDocumento = false; return;}



		            		  if (CreateActaActivity.this.IsRestoringLastData== true) return;

		            		  Localidad d = myLocalidadDAdapter.getItem(position);

		            		  if (d.getId()==null || d.getId().equals("")) return;

		            		  mCurrenActaSettings.edit().putInt(CURRENT_ACTA_LOCALIDAD_DOCUMENTO,position).commit();

		                	   if (d.getId().equals(-1))
		                	   {
		                		   ShowEditText(DOCUMENTO, LOCALIDAD, true);
		                		   //editText_LocalidadLicencia.requestFocus();

		                	   }
		                	   else
		                	   {
		                		   //editText_LocalidadDocumento.setVisibility(View.GONE);
		                		   ShowEditText(DOCUMENTO, LOCALIDAD, false);
		                		   //usarJuzgadoSegunConvenio();
			                	   // revisamo si cambio de localidad para volver a revisar las entidades
			                	   fillJuzgadoSpinner();
		                		   //String ParametroLocalidad= d.getId();
		                		   fillCodigoPostal(d);

		                	   }


		                  }
		            	  @Override
		                  public void onNothingSelected(AdapterView<?> parent) {
		                  }
		              }
		          );
     }
	 private void initLocalidadesDSpinner()
	 {

			itemsLocalidadesD = new Localidad[someExpensiveObject.lstLocalidadesDocumento.size()];
			someExpensiveObject.lstLocalidadesDocumento.toArray(itemsLocalidadesD); // fill the array
			myLocalidadDAdapter = new GenericSpinAdapter<Localidad>(this, layout.simple_spinner_item,itemsLocalidadesD);

			myLocalidadDAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
			myLocalidadDSpinner.setAdapter(myLocalidadDAdapter);
			/*
			if (this.IsRecreating == false)
			{
				myLocalidadDSpinner.setSelection(myLocalidadDSpinner.getCount());
				editText_LocalidadDocumento.setVisibility(View.GONE);
			}
			*/

	 }
	 private void fillLocalidadDSpinner(String pIdDepartamento)
	{
			if (pIdDepartamento==null || pIdDepartamento.equals(""))// || pIdDepartamento.equals("-1"))
			{
				return;
			}

				LocalidadRules localidadRules = new LocalidadRules(this);
				List<Localidad> list = localidadRules.getByDepartamentoForSpinner(pIdDepartamento);

				//int cantidadLocalidadesEncontradas = list.size();

				itemsLocalidadesD = new Localidad[list.size()];
				list.toArray(itemsLocalidadesD); // fill the array
				myLocalidadDAdapter = new GenericSpinAdapter<Localidad>(this, layout.simple_spinner_item,itemsLocalidadesD);

				myLocalidadDAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
				myLocalidadDSpinner.setAdapter(myLocalidadDAdapter);


				//int isharedLocalidad = mCurrenActaSettings.getInt(CURRENT_ACTA_LOCALIDAD_LICENCIA, -1);
				if (this.IsRecreating==false)
				{
					myLocalidadDSpinner.setSelection(myLocalidadDSpinner.getCount());
					someExpensiveObject.lstLocalidadesDocumento= list;
					editText_LocalidadDocumento.setVisibility(View.GONE);
				}
				/*
				else// if (this.IsRestoringLastData==true)
				{
					myLocalidadSpinner.setSelection(isharedLocalidad);
				}
				*/


		}

	 /*Localidades Titular*/
	 private Spinner myLocalidadDTitularSpinner;
	 private GenericSpinAdapter<Localidad> myLocalidadDTitularAdapter;
     private Localidad[] itemsLocalidadesDTitular;
     private boolean isSpinnerInitialLocalidadDocumentoTitular = true;
     private void initLocalidadesDTitularListeners()
     {

    	 myLocalidadDTitularSpinner.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Tools.Log(Log.ERROR, "myLocalidadDTitularSpinner", "onTouch");
					IsRecreating = false;
					CreateActaActivity.this.IsRestoringLastData = false;
					isSpinnerInitialLocalidadDocumentoTitular = false;
					return false;
				}
			 });
			myLocalidadDTitularSpinner.setOnItemSelectedListener(
		              new AdapterView.OnItemSelectedListener() {


		            	  @Override
		                  public void onItemSelected(
		                          AdapterView<?> parent,
		                          View view,
		                          int position,
		                          long id) {

		            		  if(isSpinnerInitialLocalidadDocumentoTitular) { isSpinnerInitialLocalidadDocumentoTitular = false; return;}



		            		  if (CreateActaActivity.this.IsRestoringLastData== true) return;

		            		  Localidad d = myLocalidadDTitularAdapter.getItem(position);

		            		  if (d.getId()==null || d.getId().equals("")) return;

		            		  mCurrenActaSettings.edit().putInt(CURRENT_ACTA_LOCALIDAD_DOCUMENTO_TITULAR,position).commit();

		                	   if (d.getId().equals(-1))
		                	   {
		                		   ShowEditText(DOCUMENTO_TITULAR, LOCALIDAD, true);
		                		   //editText_LocalidadLicencia.requestFocus();

		                	   }
		                	   else
		                	   {
		                		   //editText_LocalidadDocumento.setVisibility(View.GONE);
		                		   ShowEditText(DOCUMENTO_TITULAR, LOCALIDAD, false);
		                		   //usarJuzgadoSegunConvenio();
			                	   // revisamo si cambio de localidad para volver a revisar las entidades
			                	   //fillJuzgadoSpinner();
		                		   //String ParametroLocalidad= d.getId();
		                		   fillCodigoPostalTitular(d);

		                	   }


		                  }
		            	  @Override
		                  public void onNothingSelected(AdapterView<?> parent) {
		                  }
		              }
		          );
     }
	 private void initLocalidadesDTitularSpinner()
	 {

			itemsLocalidadesDTitular = new Localidad[someExpensiveObject.lstLocalidadesDocumentoTitular.size()];
			someExpensiveObject.lstLocalidadesDocumentoTitular.toArray(itemsLocalidadesDTitular); // fill the array
			myLocalidadDTitularAdapter = new GenericSpinAdapter<Localidad>(this, layout.simple_spinner_item,itemsLocalidadesDTitular);

			myLocalidadDTitularAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
			myLocalidadDTitularSpinner.setAdapter(myLocalidadDTitularAdapter);
			/*
			if (this.IsRecreating == false)
			{
				myLocalidadDSpinner.setSelection(myLocalidadDSpinner.getCount());
				editText_LocalidadDocumento.setVisibility(View.GONE);
			}
			*/

	 }
	 private void fillLocalidadDTitularSpinner(String pIdDepartamento)
	{
			if (pIdDepartamento==null || pIdDepartamento.equals("")) //|| pIdDepartamento.equals("-1"))
			{
				return;
			}

				LocalidadRules localidadRules = new LocalidadRules(this);
				List<Localidad> list = localidadRules.getByDepartamentoForSpinner(pIdDepartamento);

				//int cantidadLocalidadesEncontradas = list.size();

				itemsLocalidadesDTitular = new Localidad[list.size()];
				list.toArray(itemsLocalidadesDTitular); // fill the array
				myLocalidadDTitularAdapter = new GenericSpinAdapter<Localidad>(this, layout.simple_spinner_item,itemsLocalidadesDTitular);

				myLocalidadDTitularAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
				myLocalidadDTitularSpinner.setAdapter(myLocalidadDTitularAdapter);


				//int isharedLocalidad = mCurrenActaSettings.getInt(CURRENT_ACTA_LOCALIDAD_LICENCIA, -1);
				if (this.IsRecreating==false)
				{
					myLocalidadDTitularSpinner.setSelection(myLocalidadDTitularSpinner.getCount());
					someExpensiveObject.lstLocalidadesDocumentoTitular= list;
					editText_LocalidadDocumentoTitular.setVisibility(View.GONE);
				}
				/*
				else// if (this.IsRestoringLastData==true)
				{
					myLocalidadSpinner.setSelection(isharedLocalidad);
				}
				*/


		}



	 /*End Localidades Titular*/

	 /*Localidades Testigo*/
	 private Spinner myLocalidadDTestigoSpinner;
	 private GenericSpinAdapter<Localidad> myLocalidadDTestigoAdapter;
     private Localidad[] itemsLocalidadesDTestigo;
     private boolean isSpinnerInitialLocalidadDocumentoTestigo= true;
     private void initLocalidadesDTestigoListeners()
     {

    	 myLocalidadDTestigoSpinner.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Tools.Log(Log.ERROR, "myLocalidadDTestigoSpinner", "onTouch");
					IsRecreating = false;
					CreateActaActivity.this.IsRestoringLastData = false;
					isSpinnerInitialLocalidadDocumentoTestigo = false;
					return false;
				}
			 });
			myLocalidadDTestigoSpinner.setOnItemSelectedListener(
		              new AdapterView.OnItemSelectedListener() {


		            	  @Override
		                  public void onItemSelected(
		                          AdapterView<?> parent,
		                          View view,
		                          int position,
		                          long id) {

		            		  if(isSpinnerInitialLocalidadDocumentoTestigo) { isSpinnerInitialLocalidadDocumentoTestigo = false; return;}



		            		  if (CreateActaActivity.this.IsRestoringLastData== true) return;

		            		  Localidad d = myLocalidadDTestigoAdapter.getItem(position);

		            		  if (d.getId()==null || d.getId().equals("")) return;

		            		  mCurrenActaSettings.edit().putInt(CURRENT_ACTA_LOCALIDAD_DOCUMENTO_TESTIGO,position).commit();

		                	   if (d.getId().equals(-1))
		                	   {
		                		   ShowEditText(DOCUMENTO_TESTIGO, LOCALIDAD, true);
		                		   //editText_LocalidadLicencia.requestFocus();

		                	   }
		                	   else
		                	   {
		                		   //editText_LocalidadDocumento.setVisibility(View.GONE);
		                		   ShowEditText(DOCUMENTO_TESTIGO, LOCALIDAD, false);
		                		   //usarJuzgadoSegunConvenio();
			                	   // revisamo si cambio de localidad para volver a revisar las entidades
			                	   //fillJuzgadoSpinner();
		                		   //String ParametroLocalidad= d.getId();
		                		   fillCodigoPostalTestigo(d);

		                	   }


		                  }
		            	  @Override
		                  public void onNothingSelected(AdapterView<?> parent) {
		                  }
		              }
		          );
     }
	 private void initLocalidadesDTestigoSpinner()
	 {

			itemsLocalidadesDTestigo = new Localidad[someExpensiveObject.lstLocalidadesDocumentoTestigo.size()];
			someExpensiveObject.lstLocalidadesDocumentoTestigo.toArray(itemsLocalidadesDTestigo); // fill the array
			myLocalidadDTestigoAdapter = new GenericSpinAdapter<Localidad>(this, layout.simple_spinner_item,itemsLocalidadesDTestigo);

			myLocalidadDTestigoAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
			myLocalidadDTestigoSpinner.setAdapter(myLocalidadDTestigoAdapter);
			/*
			if (this.IsRecreating == false)
			{
				myLocalidadDSpinner.setSelection(myLocalidadDSpinner.getCount());
				editText_LocalidadDocumento.setVisibility(View.GONE);
			}
			*/

	 }
	 private void fillLocalidadDTestigoSpinner(String pIdDepartamento)
	{
			if (pIdDepartamento==null || pIdDepartamento.equals("") )//|| pIdDepartamento.equals("-1"))
			{
				return;
			}

				LocalidadRules localidadRules = new LocalidadRules(this);
				List<Localidad> list = localidadRules.getByDepartamentoForSpinner(pIdDepartamento);

				//int cantidadLocalidadesEncontradas = list.size();

				itemsLocalidadesDTestigo = new Localidad[list.size()];
				list.toArray(itemsLocalidadesDTestigo); // fill the array
				myLocalidadDTestigoAdapter = new GenericSpinAdapter<Localidad>(this, layout.simple_spinner_item,itemsLocalidadesDTestigo);

				myLocalidadDTestigoAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
				myLocalidadDTestigoSpinner.setAdapter(myLocalidadDTestigoAdapter);


				//int isharedLocalidad = mCurrenActaSettings.getInt(CURRENT_ACTA_LOCALIDAD_LICENCIA, -1);
				if (this.IsRecreating==false)
				{
					myLocalidadDTestigoSpinner.setSelection(myLocalidadDTestigoSpinner.getCount());
					someExpensiveObject.lstLocalidadesDocumentoTestigo= list;
					editText_LocalidadDocumentoTestigo.setVisibility(View.GONE);
				}
				/*
				else// if (this.IsRestoringLastData==true)
				{
					myLocalidadSpinner.setSelection(isharedLocalidad);
				}
				*/


		}



	 /*End Localidades Testigo */


	/* Initialize Paises*/
	 private Spinner myPaisSpinner;
	 private GenericSpinAdapter<Pais> myPaisAdapter;

	 private boolean isSpinnerInitialPaisLicencia = true;
	 private void initPaisesListeners()
	 {

			myPaisSpinner.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {

					Pais objeto = (Pais) myPaisSpinner.getSelectedItem();
					if(objeto!=null && (objeto.getId()==null || objeto.getId().equals("") ))
					{
						//myPaisSpinner.setSelection(3);
						SetSpinnerSelectionByValue(myPaisAdapter,SPINNER_PAIS_LICENCIA,"1");
					}
					Tools.Log(Log.ERROR, "myPaisSpinner", "onTouch");
					IsRecreating = false;
					CreateActaActivity.this.IsRestoringLastData = false;
					isSpinnerInitialPaisLicencia= false;
					return false;
				}
			 });
			myPaisSpinner.setOnItemSelectedListener(
		              new AdapterView.OnItemSelectedListener() {


		            	  @Override
		                  public void onItemSelected(
		                          AdapterView<?> parent,
		                          View view,
		                          int position,
		                          long id) {

		            		  if(isSpinnerInitialPaisLicencia)
		            		  {
		            			  isSpinnerInitialPaisLicencia = false;
		                		  String[] sListeners = {
										   SPINNER_PAIS_LICENCIA,SPINNER_PAIS_DOCUMENTO
										  ,SPINNER_PROVINCIA_LICENCIA,SPINNER_PROVINCIA_DOCUMENTO
										  };
		            			  String ParametroPais = "1";
		            			  String ParametroProvincia = "12";
		            			  try
							      {	DeshabilitarListeners(sListeners);
		            			    fillProvinciaSpinner(ParametroPais);
								    SetSpinnerSelectionByValue(myProvinciaAdapter,SPINNER_PROVINCIA_LICENCIA,ParametroProvincia);
								    HabilitarListeners(sListeners);
								   }catch(Exception ex)
								   {

								   }
		                		  return;
		            		  }


		            		  if (CreateActaActivity.this.IsRestoringLastData== true) return;

		                	   Pais d = myPaisAdapter.getItem(position);

		                	   if (d.getId()==null || d.getId().equals("")) return;

		            		   mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PAIS_LICENCIA, position).commit();

		                	   String ParametroPais = d.getId();
	                		   fillProvinciaSpinner(ParametroPais);

		                	   if (d.getId().equals("-1"))
		                	   {
		                		   ShowEditText(LICENCIA, PAIS, true);
		                		   editText_PaisLicencia.requestFocus();


		                		   DeshabilitarListeners(SPINNER_PROVINCIA_LICENCIA, SPINNER_DEPARTAMENTO_LICENCIA,SPINNER_LOCALIDAD_LICENCIA);
		                		   SetFirstSelection(0,SPINNER_PROVINCIA_LICENCIA, SPINNER_DEPARTAMENTO_LICENCIA,SPINNER_LOCALIDAD_LICENCIA);

		                		   ShowEditText(LICENCIA, PROVINCIA, true);
		                		   //ShowEditText(LICENCIA, DEPARTAMENTO, true);
		                		   //ShowEditText(LICENCIA, LOCALIDAD, true);

		                		   HabilitarListeners(SPINNER_PROVINCIA_LICENCIA, SPINNER_DEPARTAMENTO_LICENCIA,SPINNER_LOCALIDAD_LICENCIA);

		                	   }
		                	   else
		                	   {
		                		   ShowEditText(LICENCIA, PAIS, false);
		                		   ShowEditText(LICENCIA, PROVINCIA, false);
		                		   ShowEditText(LICENCIA, DEPARTAMENTO, false);
		                		   ShowEditText(LICENCIA, LOCALIDAD, false);

		                	   }


		                  }
		            	  @Override
		                  public void onNothingSelected(AdapterView<?> parent) {
		                  }
		              }
		          );

	 }
	 private void initPaisesSpinner() {

	    final Pais[] items = new Pais[someExpensiveObject.lstPaisesLicencia.size()];
	    someExpensiveObject.lstPaisesLicencia.toArray(items);
		myPaisAdapter = new GenericSpinAdapter<Pais>(this, layout.simple_spinner_item,items);
		myPaisAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
		myPaisSpinner.setAdapter(myPaisAdapter);

		//if (this.IsRecreating == false )
		//{
		//	int iPosition = myPaisSpinner.getCount();
		//	myPaisSpinner.setSelection(iPosition);
		//}
		/*
		{
			myPaisSpinner.setSelection(myPaisSpinner.getCount());
			editText_PaisLicencia.setVisibility(View.GONE);
		}*/

	}


	 /*   Fill Provincias  segun pais seleccionado */
	 private Spinner myProvinciaSpinner;
	 private GenericSpinAdapter<Provincia> myProvinciaAdapter;
	 private Provincia[] itemsProvincia;
	  private boolean isSpinnerInitialProvinciaLicencia = true;
	 private void initProvinciasListeners()
	 {

		 myProvinciaSpinner.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Tools.Log(Log.ERROR, "myProvinciaSpinner", "onTouch");
					IsRecreating = false;
					CreateActaActivity.this.IsRestoringLastData = false;
					isSpinnerInitialProvinciaLicencia = false;
					return false;
				}
			 });


			myProvinciaSpinner.setOnItemSelectedListener(
		              new AdapterView.OnItemSelectedListener() {


		            	  @Override
		                  public void onItemSelected(
		                          AdapterView<?> parent,
		                          View view,
		                          int position,
		                          long id) {

		            		  if(isSpinnerInitialProvinciaLicencia) { isSpinnerInitialProvinciaLicencia = false; return;}



		            		  if (CreateActaActivity.this.IsRestoringLastData== true) return;

		                	   Provincia d = myProvinciaAdapter.getItem(position);

		                	   if (d.getId()==null || d.getId().equals(""))  return;

		                	   mCurrenActaSettings.edit().putInt(CURRENT_ACTA_PROVINCIA_LICENCIA,position).commit();

		                	   String ParametroProvincia = d.getId();
	              		       fillDepartamentoSpinner(ParametroProvincia);

		                	   if (d.getId().equals("-1"))
		                	   {
		                		   ShowEditText(LICENCIA, PROVINCIA, true);
		                		   editText_ProvinciaLicencia.requestFocus();

		                		   DeshabilitarListeners(SPINNER_DEPARTAMENTO_LICENCIA,SPINNER_LOCALIDAD_LICENCIA);
		                		   SetFirstSelection(0,SPINNER_DEPARTAMENTO_LICENCIA,SPINNER_LOCALIDAD_LICENCIA);


		                		   //ShowEditText(LICENCIA, DEPARTAMENTO, true);
		                		   //ShowEditText(LICENCIA, LOCALIDAD, true);

		                		   HabilitarListeners(SPINNER_DEPARTAMENTO_LICENCIA,SPINNER_LOCALIDAD_LICENCIA);

		                	   }
		                	   else
		                	   {
		                		  // ShowEditText(LICENCIA, PAIS, false);
		                		   ShowEditText(LICENCIA, PROVINCIA, false);
		                		   ShowEditText(LICENCIA, DEPARTAMENTO, false);
		                		   ShowEditText(LICENCIA, LOCALIDAD, false);
		                	   }



		                  }
		            	  @Override
		                  public void onNothingSelected(AdapterView<?> parent) {
		                  }
		              }
		          );
	 }
	 private void initProvinciasSpinner()
	 {

			itemsProvincia = new Provincia[someExpensiveObject.lstProvinciasLicencia.size()];
			someExpensiveObject.lstProvinciasLicencia.toArray(itemsProvincia); // fill the array

			myProvinciaAdapter = new GenericSpinAdapter<Provincia>(this, layout.simple_spinner_item,itemsProvincia);
			myProvinciaAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
			myProvinciaSpinner.setAdapter(myProvinciaAdapter);

		/*
		if (this.IsRecreating == false)
		{
			myProvinciaSpinner.setSelection(myProvinciaSpinner.getCount());
			editText_ProvinciaLicencia.setVisibility(View.GONE);
		}*/

	 }
	 private void fillProvinciaSpinner(String pIdPais)
		{
			if (pIdPais==null || pIdPais.equals("") )//|| pIdPais.equals("-1") )
			{
				return;
			}
				int cantidadProvinciasEncontradas = 0;

				ProvinciaRules provinciaRules = new ProvinciaRules(this);


				List<Provincia> list = provinciaRules.getByPaisForSpinner(pIdPais);//Provincia.GetByPais(pIdPais);
				cantidadProvinciasEncontradas = list.size(); // cuantas provincias se encontraron

				itemsProvincia= new Provincia[list.size()];
				list.toArray(itemsProvincia); // fill the array

				myProvinciaAdapter = new GenericSpinAdapter<Provincia>(this, layout.simple_spinner_item,itemsProvincia);
				myProvinciaAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
				myProvinciaSpinner.setAdapter(myProvinciaAdapter);

				if (this.IsRecreating==false)
				{
					myProvinciaSpinner.setSelection(myProvinciaSpinner.getCount());//
					editText_ProvinciaLicencia.setVisibility(View.GONE);
					someExpensiveObject.lstProvinciasLicencia = list;
					if (cantidadProvinciasEncontradas==2)
					{
						//someExpensiveObject.reInitializeList("LICENCIA","DEPARTAMENTO");
						//initDepartamentosSpinner();
						//myDepartamentoSpinner.setSelection(myDepartamentoSpinner.getCount());
						//editText_DepartamentoLicencia.setVisibility(View.GONE);

						//someExpensiveObject.reInitializeList("LICENCIA","LOCALIDAD");
						//initLocalidadesSpinner();
						//myLocalidadSpinner.setSelection(myLocalidadSpinner.getCount());
						//editText_LocalidadLicencia.setVisibility(View.GONE);
					}

				}


		}

	 /*   Fill Departamentos  segun pais seleccionado */
	 private Spinner myDepartamentoSpinner;
	 private GenericSpinAdapter<Departamento> myDepartamentoAdapter;
	 private Departamento[] itemsDepartamento;
	 private boolean isSpinnerInitialDepartamentoLicencia = true;
	 private void initDepartamentosListeners()
	 {
		 myDepartamentoSpinner.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Tools.Log(Log.ERROR, "myDepartamentoSpinner", "onTouch");
					IsRecreating = false;
					CreateActaActivity.this.IsRestoringLastData = false;
					isSpinnerInitialDepartamentoLicencia = false;
					return false;
				}
			 });


			myDepartamentoSpinner.setOnItemSelectedListener(
		              new AdapterView.OnItemSelectedListener() {


		            	  @Override
		                  public void onItemSelected(
		                          AdapterView<?> parent,
		                          View view,
		                          int position,
		                          long id) {

		            		  if(isSpinnerInitialDepartamentoLicencia) { isSpinnerInitialDepartamentoLicencia = false; return;}



		            		  if (CreateActaActivity.this.IsRestoringLastData== true)  return;

		            		  Departamento d = myDepartamentoAdapter.getItem(position);

		            		  if (d.getId()==null || d.getId().equals(""))  return;

		            		  mCurrenActaSettings.edit().putInt(CURRENT_ACTA_DEPARTAMENTO_LICENCIA, position).commit();

	                		   String ParametroDepartamento= d.getId().toString();
	                		   fillLocalidadSpinner(ParametroDepartamento);

		                	   if (d.getId().equals(-1))
		                	   {

		                		   ShowEditText(LICENCIA, DEPARTAMENTO, true);
		                		   //editText_DepartamentoLicencia.requestFocus();

		                		   DeshabilitarListeners(SPINNER_LOCALIDAD_LICENCIA);
		                		   SetFirstSelection(0,SPINNER_LOCALIDAD_LICENCIA);

		                		   ShowEditText(LICENCIA, LOCALIDAD, true);

		                		   HabilitarListeners(SPINNER_LOCALIDAD_LICENCIA);
		                	   }
		                	   else
		                	   {   // se supone que encontramos la provincia
		                		   //ShowEditText(LICENCIA, PAIS, false);
		                		   //ShowEditText(LICENCIA, PROVINCIA, false);
		                		   ShowEditText(LICENCIA, DEPARTAMENTO, false);
		                		   ShowEditText(LICENCIA, LOCALIDAD, false);
		                	   }
		                  }
		            	  @Override
		                  public void onNothingSelected(AdapterView<?> parent) {
		                  }
		              }
		          );
	 }

	 private void initDepartamentosSpinner()
	 {

		    itemsDepartamento = new Departamento[someExpensiveObject.lstDepartamentosLicencia.size()];
		    someExpensiveObject.lstDepartamentosLicencia.toArray(itemsDepartamento);
			myDepartamentoAdapter = new GenericSpinAdapter<Departamento>(this, layout.simple_spinner_item,itemsDepartamento);
			myDepartamentoAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
			myDepartamentoSpinner.setAdapter(myDepartamentoAdapter);
			/*
			if (this.IsRecreating == false)
			{
				myDepartamentoSpinner.setSelection(myDepartamentoSpinner.getCount());
				editText_DepartamentoLicencia.setVisibility(View.GONE);
			}
			*/



	 }
	 private void fillDepartamentoSpinner(String pIdProvincia)
		{
			if (pIdProvincia==null || pIdProvincia.equals("") )//|| pIdProvincia.equals("-1"))
			{
				//initDepartamentosSpinner();
				return;
			}
				int cantidadDepartamentosEncontrados=0;

				DepartamentoRules departamentoRules = new DepartamentoRules(this);
				List<Departamento> list = departamentoRules.getByProvinciaForSpinner(pIdProvincia);
				cantidadDepartamentosEncontrados = list.size();
				itemsDepartamento = new Departamento[list.size()];
				list.toArray(itemsDepartamento);
				myDepartamentoAdapter = new GenericSpinAdapter<Departamento>(this, layout.simple_spinner_item,itemsDepartamento);

				myDepartamentoAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
				myDepartamentoSpinner.setAdapter(myDepartamentoAdapter);

				if (this.IsRecreating==false)
				{
					myDepartamentoSpinner.setSelection(myDepartamentoSpinner.getCount());
					editText_LocalidadLicencia.setVisibility(View.GONE);
					someExpensiveObject.lstDepartamentosLicencia = list;
					if (cantidadDepartamentosEncontrados==2)
					{
						someExpensiveObject.reInitializeList("LICENCIA","LOCALIDAD");
						initLocalidadesSpinner();
						myLocalidadSpinner.setSelection(myLocalidadSpinner.getCount());
						editText_LocalidadLicencia.setVisibility(View.GONE);
					}
				}





		}

	 /*   Fill Localidades  segun pais seleccionado */
	 private Spinner myLocalidadSpinner;
	 private GenericSpinAdapter<Localidad> myLocalidadAdapter;
     private Localidad[] itemsLocalidades;
	 private boolean isSpinnerInitialLocalidadLicencia = true;
     private void initLocalidadesListeners()
     {

    	 myLocalidadSpinner.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Tools.Log(Log.ERROR, "myLocalidadSpinner", "onTouch");
					IsRecreating = false;
					CreateActaActivity.this.IsRestoringLastData = false;
					isSpinnerInitialLocalidadLicencia = false;
					return false;
				}
			 });
			myLocalidadSpinner.setOnItemSelectedListener(
		              new AdapterView.OnItemSelectedListener() {


		            	  @Override
		                  public void onItemSelected(
		                          AdapterView<?> parent,
		                          View view,
		                          int position,
		                          long id) {

		            		  if(isSpinnerInitialLocalidadLicencia) { isSpinnerInitialLocalidadLicencia = false; return;}


		            		  if (CreateActaActivity.this.IsRestoringLastData== true) return;

		            		  Localidad d = myLocalidadAdapter.getItem(position);

		            		  if (d.getId()==null || d.getId().equals("")) return;

	            			  mCurrenActaSettings.edit().putInt(CURRENT_ACTA_LOCALIDAD_LICENCIA,position).commit();

		                	   if (d.getId().equals(-1))
		                	   {
		                		   ShowEditText(LICENCIA, LOCALIDAD, true);
		                		   //editText_LocalidadLicencia.requestFocus();

		                	   }
		                	   else
		                	   {
		                		   //ShowEditText(LICENCIA, PAIS, false);
		                		   //ShowEditText(LICENCIA, PROVINCIA, false);
		                		   //ShowEditText(LICENCIA, DEPARTAMENTO, false);
		                		   ShowEditText(LICENCIA, LOCALIDAD, false);
		                		   //String ParametroLocalidad= d.getId();


		                	   }


		                  }
		            	  @Override
		                  public void onNothingSelected(AdapterView<?> parent) {
		                  }
		              }
		          );
     }

     private void fillCodigoPostal(Localidad pLocalidad)
     {
    	 if (pLocalidad == null) return;

    	 String sCodigoPostal = pLocalidad.getCpa();
    	 editText_CodigoPostal.setText(sCodigoPostal);
    	 //Utilities.ShowToast(this, sCodigoPostal);

     }
     private void fillCodigoPostalTitular(Localidad pLocalidad)
     {
    	 if (pLocalidad == null) return;

    	 String sCodigoPostal = pLocalidad.getCpa();
    	 editText_CodigoPostalTitular.setText(sCodigoPostal);
    	 //Utilities.ShowToast(this, sCodigoPostal);

     }
     private void fillCodigoPostalTestigo(Localidad pLocalidad)
     {
    	 if (pLocalidad == null) return;

    	 String sCodigoPostal = pLocalidad.getCpa();
    	 editText_CodigoPostalTestigo.setText(sCodigoPostal);
    	 //Utilities.ShowToast(this, sCodigoPostal);

     }
     private void initLocalidadesSpinner()
	 {

			itemsLocalidades = new Localidad[someExpensiveObject.lstLocalidadesLicencia.size()];
			someExpensiveObject.lstLocalidadesLicencia.toArray(itemsLocalidades); // fill the array
			myLocalidadAdapter = new GenericSpinAdapter<Localidad>(this, layout.simple_spinner_item,itemsLocalidades);

			myLocalidadAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
			myLocalidadSpinner.setAdapter(myLocalidadAdapter);
			/*
			if (this.IsRecreating == false)
			{
				myLocalidadSpinner.setSelection(myLocalidadSpinner.getCount());
				editText_LocalidadLicencia.setVisibility(View.GONE);
			}
			*/

	 }
	 private void fillLocalidadSpinner(String pIdDepartamento)
	{
			if (pIdDepartamento==null || pIdDepartamento.equals("") )//|| pIdDepartamento.equals("-1"))
			{
				return;
			}

				LocalidadRules localidadRules = new LocalidadRules(this);
				List<Localidad> list = localidadRules.getByDepartamentoForSpinner(pIdDepartamento);

				int cantidadLocalidadesEncontradas = list.size();
				itemsLocalidades = new Localidad[list.size()];
				list.toArray(itemsLocalidades); // fill the array
				myLocalidadAdapter = new GenericSpinAdapter<Localidad>(this, layout.simple_spinner_item,itemsLocalidades);

				myLocalidadAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
				myLocalidadSpinner.setAdapter(myLocalidadAdapter);


				//int isharedLocalidad = mCurrenActaSettings.getInt(CURRENT_ACTA_LOCALIDAD_LICENCIA, -1);
				if (this.IsRecreating==false)
				{
					myLocalidadSpinner.setSelection(myLocalidadSpinner.getCount());
					someExpensiveObject.lstLocalidadesLicencia = list;
					editText_LocalidadDocumento.setVisibility(View.GONE);
				}
				/*
				else// if (this.IsRestoringLastData==true)
				{
					myLocalidadSpinner.setSelection(isharedLocalidad);
				}
				*/


		}


	/**
	 * Initialize the spinner Tipo Rutas
	 */
	private Spinner myDepartamentoInfraccionSpinner;
	//private ArrayAdapter<?> myDepartamentoInfraccionAdapter;
	private GenericSpinAdapter<Departamento> myDepartamentoInfraccionAdapter;

	private boolean isSpinnerInitialDepartamentoInfraccion= true;

	EntidadRules rules = new EntidadRules(this);

	private void initDepartamentoInfraccionListeners()
	{
		myDepartamentoInfraccionSpinner.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Tools.Log(Log.ERROR, "myDepartamentoInfraccionSpinner", "onTouch");
				IsRecreating = false;
				CreateActaActivity.this.IsRestoringLastData = false;
				isSpinnerInitialDepartamentoInfraccion = false;
				return false;
			}
		 });
		// Handle spinner selections
		myDepartamentoInfraccionSpinner.setOnItemSelectedListener(
			new AdapterView.OnItemSelectedListener() {


			@Override
			public void onItemSelected(
					AdapterView<?> parent,
					View view,
					int position,
					long id) {

			  if(isSpinnerInitialDepartamentoInfraccion)
			  {
				  isSpinnerInitialDepartamentoInfraccion = false;
				  return;
			  }

			  if (CreateActaActivity.this.IsRestoringLastData== true) return;

			   Departamento d = myDepartamentoInfraccionAdapter.getItem(position);

	     	   if (d.getId()==null || d.getId().equals("")) return;

	     	   mCurrenActaSettings.edit().putInt(CURRENT_ACTA_TIPO_RUTA, position).commit();

	     	   Integer ParametroDepartamentoInfraccion = d.getId();

			   myLocalidadInfraccionSpinner.setVisibility(View.VISIBLE);

			   fillLocalidadInfraccionSpinner(ParametroDepartamentoInfraccion.toString());

			   int juzgado = d.getJuzgado();

			   if (juzgado > 0){
				   Integer posicion = rules.getPosicionById(d.getJuzgado());
				   myEntidadSpinner.setSelection(posicion);
			   }

			}

			public void onNothingSelected(AdapterView<?> parent){
			}
		});

	}
	private void initDepartamentoInfraccionSpinner() {


		//ArrayAdapter<?> adapter ;
		//myTipoRutaAdapter = ArrayAdapter.createFromResource(this,R.array.tipos_ruta, android.R.layout.simple_spinner_item);
		final Departamento[] items;
		/*
		items = new TipoRuta[list.size()];
		list.toArray(items); */
		items = new Departamento[someExpensiveObject.lstDepartamentosInfraccion.size()];
		someExpensiveObject.lstDepartamentosInfraccion.toArray(items);

		myDepartamentoInfraccionAdapter = new GenericSpinAdapter<Departamento>(this, layout.simple_spinner_item,items);
		myDepartamentoInfraccionAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
		myDepartamentoInfraccionSpinner.setAdapter(myDepartamentoInfraccionAdapter);
		myDepartamentoInfraccionSpinner.setSelection(myDepartamentoInfraccionSpinner.getCount());

		if (this.IsRecreating == false )
		{

		}


	}

	/****************/
	private void fillLocalidadInfraccionSpinner(String pDepartamentoInfraccion)
	{

		if (pDepartamentoInfraccion==null || pDepartamentoInfraccion.equals("") || pDepartamentoInfraccion.equals("-1") )
		{
			return;
		}

		int cantidadLocalidadesInfraccionEncontradas = 0;

		LocalidadRules localidadRules = new LocalidadRules(this);

		List<Localidad> list = localidadRules.getByDepartamentoForSpinner(pDepartamentoInfraccion);
		cantidadLocalidadesInfraccionEncontradas = list.size();

		itemsLocalidadInfraccion = new Localidad[list.size()];
		list.toArray(itemsLocalidadInfraccion); // fill the array

		myLocalidadInfraccionAdapter = new GenericSpinAdapter<Localidad>(this, layout.simple_spinner_item, itemsLocalidadInfraccion);
		myLocalidadInfraccionAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
		myLocalidadInfraccionSpinner.setAdapter(myLocalidadInfraccionAdapter);


		if (this.IsRecreating==false)
		{
			myLocalidadInfraccionSpinner.setSelection(myLocalidadInfraccionSpinner.getCount());
			someExpensiveObject.lstLocalidadesInfraccion = list;
		}

	}
	/**
	 * Initialize the spinner Localidades Infraccion
	 */
	private Spinner myLocalidadInfraccionSpinner;
	private GenericSpinAdapter<Localidad> myLocalidadInfraccionAdapter;
	private Localidad[] itemsLocalidadInfraccion;
	private void initLocalidadInfraccionListeners()
	{
		myLocalidadInfraccionSpinner.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Tools.Log(Log.ERROR, "myLocalidadInfraccionSpinner", "onTouch");
				IsRecreating = false;
				CreateActaActivity.this.IsRestoringLastData = false;
				return false;
			}
		 });


		myLocalidadInfraccionSpinner.setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener() {

					private boolean isSpinnerInitial = true;
					@Override
					public void onItemSelected(
							AdapterView<?> parent,
							View view,
							int position,
							long id) {

			  if(isSpinnerInitial) { isSpinnerInitial = false; return;}

			  if (CreateActaActivity.this.IsRestoringLastData== true) return;

			   Localidad d = myLocalidadInfraccionAdapter.getItem(position);

			   if (d.getId()==null || d.getId().equals("")) return;


			   mCurrenActaSettings.edit().putInt(CURRENT_ACTA_LOCALIDAD_INFRACCION,position).commit();

			   textView_LocalidadInfraccion.setVisibility(View.VISIBLE);
			   myLocalidadInfraccionSpinner.setVisibility(View.VISIBLE);

        	   //sfillJuzgadoSpinner();
			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
	private void initLocalidadInfraccionSpinner() {



		itemsLocalidadInfraccion = new Localidad[someExpensiveObject.lstLocalidadesInfraccion.size()];
		someExpensiveObject.lstLocalidadesInfraccion.toArray(itemsLocalidadInfraccion); // fill the array

		myLocalidadInfraccionAdapter = new GenericSpinAdapter<Localidad>(this, layout.simple_spinner_item, itemsLocalidadInfraccion);
		myLocalidadInfraccionAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
		myLocalidadInfraccionSpinner.setAdapter(myLocalidadInfraccionAdapter);
		/*
		if (this.IsRecreating == false)
		{
			myRutaSpinner.setSelection(myRutaSpinner.getCount());
		}
		*/

	}

	/*********localidades infraccion ********/
	/**
	 * Initialize the spinner Tipo Rutas
	 */
	private Spinner myTipoRutaSpinner;
	//private ArrayAdapter<?> myTipoRutaAdapter;
	private GenericSpinAdapter<TipoRuta> myTipoRutaAdapter;

	private boolean isSpinnerInitialTipoRuta = true;
	private void initTipoRutaListeners()
	{
		myTipoRutaSpinner.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Tools.Log(Log.ERROR, "myTipoRutaSpinner", "onTouch");
				IsRecreating = false;
				CreateActaActivity.this.IsRestoringLastData = false;
				isSpinnerInitialTipoRuta = false;
				return false;
			}
		 });
		// Handle spinner selections
		myTipoRutaSpinner.setOnItemSelectedListener(
			new AdapterView.OnItemSelectedListener() {


			@Override
			public void onItemSelected(
					AdapterView<?> parent,
					View view,
					int position,
					long id) {

			  if(isSpinnerInitialTipoRuta)
			  {
				  isSpinnerInitialTipoRuta = false;
				  return;
			  }

			  if (CreateActaActivity.this.IsRestoringLastData== true) return;

			   TipoRuta d = myTipoRutaAdapter.getItem(position);

	     	   if (d.getId()==null || d.getId().equals("")) return;

	     	   mCurrenActaSettings.edit().putInt(CURRENT_ACTA_TIPO_RUTA, position).commit();
			   String EsCiudad = d.getEsCiudad();
			   String ParametroTipoRuta = d.getId();
			   if(EsCiudad!=null && EsCiudad.equals("S"))
			   {

				   //myDepartamentoLugarInfraccionSpinner
				   textView_NroRuta.setText("En:");
				   //editText_Ubicacion.setVisibility(View.VISIBLE);
				   editText_DescripcionUbicacion.setVisibility(View.VISIBLE);
				   textView_Km.setText("Altura :");
			   }
			   else
			   {
				   textView_NroRuta.setText("En Ruta:");
				   //editText_Ubicacion.setVisibility(View.GONE);
				   editText_DescripcionUbicacion.setVisibility(View.GONE);
				   editText_DescripcionUbicacion.setText("");
				   textView_Km.setText("Km :");
			   }

				  editText_Km.setVisibility(View.VISIBLE);
				  textView_NroRuta.setVisibility(View.VISIBLE);
				  myRutaSpinner.setVisibility(View.VISIBLE);

			   fillRutaSpinner(ParametroTipoRuta);

			}

			public void onNothingSelected(AdapterView<?> parent){
			}
		});

	}
	private void initTipoRutaSpinner() {


		//ArrayAdapter<?> adapter ;
		//myTipoRutaAdapter = ArrayAdapter.createFromResource(this,R.array.tipos_ruta, android.R.layout.simple_spinner_item);
		final TipoRuta[] items;
		/*
		items = new TipoRuta[list.size()];
		list.toArray(items); */
		items = new TipoRuta[someExpensiveObject.lstTiposRuta.size()];
		someExpensiveObject.lstTiposRuta.toArray(items);

		myTipoRutaAdapter = new GenericSpinAdapter<TipoRuta>(this, layout.simple_spinner_item,items);
		myTipoRutaAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
		myTipoRutaSpinner.setAdapter(myTipoRutaAdapter);
		myTipoRutaSpinner.setSelection(myTipoRutaSpinner.getCount());

		if (this.IsRecreating == false )
		{

		}




		/*
		if (mGameSettings.contains(GAME_PREFERENCES_TIPO_RUTA))
		{
			myTipoRutaSpinner.setSelection(mGameSettings.getInt(GAME_PREFERENCES_TIPO_RUTA,0));
		}
		*/

	}

	/* */


	/**
	 * Fill Rutas segun el tipo de Ruta seleccionado
	 */
    // You spinner view
    //private Spinner mySpinner;
    // Custom Spinner adapter (ArrayAdapter<User>)
    // You can define as a private to use it in the all class
    // This is the object that is going to do the "magic"
    //private SpinAdapter myAdapter;




	private void fillRutaSpinner(String pTipoRuta)
	{

		if (pTipoRuta==null || pTipoRuta.equals("") || pTipoRuta.equals("-1") )
		{
			return;
		}

		int cantidadRutasEncontradas = 0;

		RutaRules rutaRules = new RutaRules(this);

		List<Ruta> list = rutaRules.getByTipoRutaForSpinner(pTipoRuta);
		cantidadRutasEncontradas = list.size();

		itemsRuta = new Ruta[list.size()];
		list.toArray(itemsRuta); // fill the array

		myRutaAdapter = new GenericSpinAdapter<Ruta>(this, layout.simple_spinner_item, itemsRuta);
		myRutaAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
		myRutaSpinner.setAdapter(myRutaAdapter);


		if (this.IsRecreating==false)
		{
			myRutaSpinner.setSelection(myRutaSpinner.getCount());
			someExpensiveObject.lstRutas = list;
		}

	}
	/**
	 * Initialize the spinner Rutas
	 */
	private Spinner myRutaSpinner;
	private GenericSpinAdapter<Ruta> myRutaAdapter;
	private Ruta[] itemsRuta;
	private void initRutaListeners()
	{
		myRutaSpinner.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Tools.Log(Log.ERROR, "myRutaSpinner", "onTouch");
				IsRecreating = false;
				CreateActaActivity.this.IsRestoringLastData = false;
				return false;
			}
		 });


		myRutaSpinner.setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener() {

					private boolean isSpinnerInitial = true;
					@Override
					public void onItemSelected(
							AdapterView<?> parent,
							View view,
							int position,
							long id) {

			  if(isSpinnerInitial) { isSpinnerInitial = false; return;}

			  if (CreateActaActivity.this.IsRestoringLastData== true) return;

			   Ruta d = myRutaAdapter.getItem(position);

			   if (d.getId()==null || d.getId().equals("")) return;


			   mCurrenActaSettings.edit().putInt(CURRENT_ACTA_RUTA,position).commit();

        	   fillJuzgadoSpinner();

        	   //Ruta d = itemsRuta[position];

				/*if (d.getId().equals("-1"))
          	   {
          		   myEntidadSpinner.setVisibility(View.GONE);
          	   }
			   else
			   {
				   myEntidadSpinner.setVisibility(View.VISIBLE);

			   }
			   */

			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
	private void initRutaSpinner() {



		itemsRuta = new Ruta[someExpensiveObject.lstRutas.size()];
		someExpensiveObject.lstRutas.toArray(itemsRuta); // fill the array

		myRutaAdapter = new GenericSpinAdapter<Ruta>(this, layout.simple_spinner_item, itemsRuta);
		myRutaAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
		myRutaSpinner.setAdapter(myRutaAdapter);
		/*
		if (this.IsRecreating == false)
		{
			myRutaSpinner.setSelection(myRutaSpinner.getCount());
		}
		*/

	}

	/**
	 * Initialize the spinner
	 */
	private void initGenderSpinner() {
		// Populate Spinner control with genders
		final Spinner spinner = (Spinner) findViewById(R.id.Spinner_Gender);
		ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this,
				R.array.genders, layout.simple_spinner_item);
		adapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		if (mGameSettings.contains(GAME_PREFERENCES_GENDER)) {
			spinner.setSelection(mGameSettings.getInt(GAME_PREFERENCES_GENDER,
					0));
		}
		// Handle spinner selections
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent,
					View itemSelected, int selectedItemPosition, long selectedId) {
				Editor editor = mGameSettings.edit();
				editor.putInt(GAME_PREFERENCES_GENDER, selectedItemPosition);
				editor.commit();
			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

	/**
	 * Called when the user presses the Favorite Location button
	 *
	 * @param view
	 *            The button
	 */
	public void onPickPlaceButtonClick(View view) {
		showDialog(PLACE_DIALOG_ID);
	}

	/**
	 * Initialize the Favorite Place picker
	 */
	private void initFavoritePlacePicker() {
		// Set place info
		TextView placeInfo = (TextView) findViewById(R.id.TextView_FavoritePlace_Info);

		if (mGameSettings.contains(GAME_PREFERENCES_FAV_PLACE_NAME)) {
			placeInfo.setText(mGameSettings.getString(
					GAME_PREFERENCES_FAV_PLACE_NAME, ""));
		} else {
			placeInfo.setText(R.string.settings_favoriteplace_not_set);
		}
	}


	@Override
	protected Dialog onCreateDialog (int id)
	{

		switch (id) {


		default:
			return null;
		}


	}

	@Override
	protected Dialog onCreateDialog(int id,final Bundle arg) {
		switch (id) {
		case PRINT_ACTA_DIALOG_ID:

		    LayoutInflater inflActa = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View printActaDialogLayout = inflActa.inflate(R.layout.print_acta_dialog, (ViewGroup) findViewById(R.id.root));



            AlertDialog.Builder printActaDialogBuilder = new AlertDialog.Builder(this);
            printActaDialogBuilder.setView(printActaDialogLayout);
            final TextView actaText = (TextView) printActaDialogLayout.findViewById(R.id.TextView_Acta);

            UIHelper.showAlertOnGuiThread(this, "El Acta se grabo existosamente", "Generacion de Actas", new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
            		ActaConstatacion acta  = (ActaConstatacion) arg.getSerializable("ActaConstatacion");
                    List<ActaConstatacion> actas = new ArrayList<ActaConstatacion>();
                    actas.add(acta);
            		doPrintActaRequest(actas);// ver como le pasamo el paraemtro
				}
			});

            /*
            actaText.setText("El Acta se grabo existosamente!");
            printActaDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                		ActaConstatacion acta  = (ActaConstatacion) arg.getSerializable("ActaConstatacion");
                        doPrintActaRequest(acta);// ver como le pasamo el paraemtro

                }
            }); */
            return printActaDialogBuilder.create();
		case PLACE_DIALOG_ID:

			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View dialogLayout = layoutInflater.inflate(
					R.layout.fav_place_dialog,
					(ViewGroup) findViewById(R.id.root));

			final TextView placeCoordinates = (TextView) dialogLayout
					.findViewById(R.id.TextView_FavPlaceCoords_Info);
			final EditText placeName = (EditText) dialogLayout
					.findViewById(R.id.EditText_FavPlaceName);
			placeName.setOnKeyListener(new View.OnKeyListener() {

				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if ((event.getAction() == KeyEvent.ACTION_DOWN)
							&& (keyCode == KeyEvent.KEYCODE_ENTER)) {

						String strPlaceName = placeName.getText().toString();
						if ((strPlaceName != null)
								&& (strPlaceName.length() > 0)) {
							// Try to resolve string into GPS coords
							resolveLocation(strPlaceName);

							Editor editor = mGameSettings.edit();
							editor.putString(GAME_PREFERENCES_FAV_PLACE_NAME,
									placeName.getText().toString());
							editor.putFloat(GAME_PREFERENCES_FAV_PLACE_LONG,
									mFavPlaceCoords.mLon);
							editor.putFloat(GAME_PREFERENCES_FAV_PLACE_LAT,
									mFavPlaceCoords.mLat);
							editor.commit();

							placeCoordinates
									.setText(formatCoordinates(
											mFavPlaceCoords.mLat,
											mFavPlaceCoords.mLon));
							return true;
						}
					}
					return false;
				}
			});

			final Button mapButton = (Button) dialogLayout
					.findViewById(R.id.Button_MapIt);
			mapButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {

					// Try to resolve string into GPS coords
					String placeToFind = placeName.getText().toString();
					resolveLocation(placeToFind);

					Editor editor = mGameSettings.edit();
					editor.putString(GAME_PREFERENCES_FAV_PLACE_NAME,
							placeToFind);
					editor.putFloat(GAME_PREFERENCES_FAV_PLACE_LONG,
							mFavPlaceCoords.mLon);
					editor.putFloat(GAME_PREFERENCES_FAV_PLACE_LAT,
							mFavPlaceCoords.mLat);
					editor.commit();

					placeCoordinates.setText(formatCoordinates(
							mFavPlaceCoords.mLat, mFavPlaceCoords.mLon));

					// Launch map with gps coords
					String geoURI = String.format("geo:%f,%f?z=10",
							mFavPlaceCoords.mLat, mFavPlaceCoords.mLon);
					Uri geo = Uri.parse(geoURI);
					Intent geoMap = new Intent(Intent.ACTION_VIEW, geo);
					startActivity(geoMap);
				}
			});

			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
			dialogBuilder.setView(dialogLayout);

			// Now configure the AlertDialog
			dialogBuilder.setTitle(R.string.settings_button_favoriteplace);

			dialogBuilder.setNegativeButton(string.cancel,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// We forcefully dismiss and remove the Dialog, so
							// it cannot be used again (no cached info)
							CreateActaActivity.this
									.removeDialog(PLACE_DIALOG_ID);
						}
					});

			dialogBuilder.setPositiveButton(string.ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

							TextView placeInfo = (TextView) findViewById(R.id.TextView_FavoritePlace_Info);
							String strPlaceName = placeName.getText()
									.toString();

							if ((strPlaceName != null)
									&& (strPlaceName.length() > 0)) {
								Editor editor = mGameSettings.edit();
								editor.putString(
										GAME_PREFERENCES_FAV_PLACE_NAME,
										strPlaceName);
								editor.putFloat(
										GAME_PREFERENCES_FAV_PLACE_LONG,
										mFavPlaceCoords.mLon);
								editor.putFloat(GAME_PREFERENCES_FAV_PLACE_LAT,
										mFavPlaceCoords.mLat);
								editor.commit();

								placeInfo.setText(strPlaceName);
							}

							// We forcefully dismiss and remove the Dialog, so
							// it cannot be used again
							CreateActaActivity.this
									.removeDialog(PLACE_DIALOG_ID);
						}
					});

			// Create the AlertDialog and return it
			AlertDialog placeDialog = dialogBuilder.create();
			return placeDialog;

		case EXPIRE_DATE_DIALOG_ID: // Fecha de vencimiento de la licencia
			Calendar calendario = Calendar.getInstance();
			DatePickerDialog dateFVLDialog = new DatePickerDialog(this,AlertDialog.THEME_HOLO_LIGHT,
					new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth)
						{
							Time dateOfExpired = new Time();
							dateOfExpired.set(dayOfMonth, monthOfYear, year);
							long dtDob = dateOfExpired.toMillis(true);
							CharSequence DateFLV = DateFormat.format("dd/MM/yyyy",dtDob);
							editText_FVL_Info.setText(DateFLV);
							editText_FVL_Info.setError("",null);

							mCurrenActaSettings.edit().putString(CURRENT_ACTA_FECHA_VENCIMIENTO_LICENCIA,DateFLV.toString()).commit();
						}
					}, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH),calendario.get(Calendar.DAY_OF_MONTH));
			dateFVLDialog.setTitle("Fecha Vencimiento Licencia");
			return dateFVLDialog;

		case BORN_DATE_DIALOG_ID: // Fecha de vencimiento de la licencia
			Calendar bornCalendar = Calendar.getInstance();
			DatePickerDialog dateFNDialog = new DatePickerDialog(this,AlertDialog.THEME_HOLO_LIGHT,
					new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth)
						{
							Time dateOfExpired = new Time();
							dateOfExpired.set(dayOfMonth, monthOfYear, year);
							long dtDob = dateOfExpired.toMillis(true);
							CharSequence DateFLV = DateFormat.format("dd/MM/yyyy",dtDob);
							textView_FN_Info.setText(DateFLV);
							mCurrenActaSettings.edit().putString(CURRENT_ACTA_FECHA_NACIMIENTO,DateFLV.toString()).commit();
						}
					}, bornCalendar.get(Calendar.YEAR), bornCalendar.get(Calendar.MONTH),bornCalendar.get(Calendar.DAY_OF_MONTH));
			return dateFNDialog;
		case DATE_DIALOG_ID:
			final TextView dob = (TextView) findViewById(R.id.TextView_DOB_Info);
			Calendar now = Calendar.getInstance();

			DatePickerDialog dateDialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {

							Time dateOfBirth = new Time();
							dateOfBirth.set(dayOfMonth, monthOfYear, year);
							long dtDob = dateOfBirth.toMillis(true);
							dob.setText(DateFormat.format("MMMM dd, yyyy",
									dtDob));

							Editor editor = mGameSettings.edit();
							editor.putLong(GAME_PREFERENCES_DOB, dtDob);
							editor.commit();
						}
					}, now.get(Calendar.YEAR), now.get(Calendar.MONTH),
					now.get(Calendar.DAY_OF_MONTH));
			return dateDialog;

		case SCAN_QR_CODE_DIALOG_ID:
			/*Sin dialogo para Mendoza */

			LayoutInflater inflaterQR = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View layoutQR = inflaterQR.inflate(R.layout.qrcode_dni_dialog,(ViewGroup) findViewById(R.id.root));
			final EditText p1DOC = (EditText) layoutQR.findViewById(R.id.EditText_Pwd1);
			p1DOC.setVisibility(View.GONE);
			AlertDialog.Builder builderQR = new AlertDialog.Builder(this);
			builderQR.setView(layoutQR);
			// Now configure the AlertDialog
			builderQR.setTitle(R.string.dialog_qrcode_titulo);
			builderQR.setNegativeButton("ZXing",
					//android.R.string.cancel,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// We forcefully dismiss and remove the Dialog, so
							// it
							// cannot be used again (no cached info)
							doVerificarCodigoQR("zxing");
							CreateActaActivity.this.removeDialog(SCAN_QR_CODE_DIALOG_ID);
						}
					});
			builderQR.setPositiveButton("MicroBlink",//android.R.string.ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							/*String strPassword1 = p1DOC.getText().toString();
								if(!strPassword1.trim().equals(""))
								{
									doVerificarCodigoQR(strPassword1);
									CreateActaActivity.this.removeDialog(SCAN_QR_CODE_DIALOG_ID);

								}
							 else {
								Log.d(DEBUG_TAG,"Passwords do not match. Not saving. Keeping old password (if set).");
							 } */
							doVerificarCodigoQR("microblink");
							CreateActaActivity.this.removeDialog(SCAN_QR_CODE_DIALOG_ID);
						}
					});

//			builderQR.seto
//
//			dialog.setOnShowListener(new OnShowListener() {
//
//			    @Override
//			    public void onShow(DialogInterface dialog) {
//			        if(condition)
//			        ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
//			    }
//			});

			// Create the AlertDialog and return it
			final AlertDialog QRDocDialog = builderQR.create();

			QRDocDialog.setOnShowListener(new OnShowListener() {

			    @Override
			    public void onShow(DialogInterface dialog) {
			        //((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
			    	Button zxingButton  = ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
			    	Button microblinkButton  = ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE);
			    	if (GlobalVar.getInstance().getSuportTable().getLectorPDF417().equals("zxing")){
			    		zxingButton.setFocusable(true);
			    		zxingButton.setFocusableInTouchMode(true);
			    		zxingButton.requestFocus();
			    	}
			    	else
			    	{
			    		microblinkButton.setFocusable(true);
			    		microblinkButton.setFocusableInTouchMode(true);
			    		microblinkButton.requestFocus();
			    	}



			    }
			});
			p1DOC.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {


				}

				@Override
				public void afterTextChanged(Editable s) {
					String strPass1 = p1DOC.getText().toString();
					if (strPass1.trim().equals("")) {
						//
						(QRDocDialog.getButton(AlertDialog.BUTTON_POSITIVE)).setEnabled(false);
					} else {
						(QRDocDialog.getButton(AlertDialog.BUTTON_POSITIVE)).setEnabled(true);
					}

				}
			}
			);
			return QRDocDialog;

		case PASSWORD_DIALOG_ID:
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View layout = inflater.inflate(R.layout.password_dialog,
					(ViewGroup) findViewById(R.id.root));
			final EditText p1 = (EditText) layout
					.findViewById(R.id.EditText_Pwd1);
			final EditText p2 = (EditText) layout
					.findViewById(R.id.EditText_Pwd2);
			final TextView error = (TextView) layout
					.findViewById(R.id.TextView_PwdProblem);
			p2.addTextChangedListener(new TextWatcher() {
				@Override
				public void afterTextChanged(Editable s) {
					String strPass1 = p1.getText().toString();
					String strPass2 = p2.getText().toString();
					if (strPass1.equals(strPass2)) {
						error.setText(R.string.settings_pwd_equal);
					} else {
						error.setText(R.string.settings_pwd_not_equal);
					}
				}

				// ... other required overrides need not be implemented
				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
				}
			});
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setView(layout);
			// Now configure the AlertDialog
			builder.setTitle(R.string.settings_button_pwd);
			builder.setNegativeButton(string.cancel,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// We forcefully dismiss and remove the Dialog, so
							// it
							// cannot be used again (no cached info)
							CreateActaActivity.this
									.removeDialog(PASSWORD_DIALOG_ID);
						}
					});
			builder.setPositiveButton(string.ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							TextView passwordInfo = (TextView) findViewById(R.id.TextView_Password_Info);
							String strPassword1 = p1.getText().toString();
							String strPassword2 = p2.getText().toString();
							if (strPassword1.equals(strPassword2)) {
								Editor editor = mGameSettings.edit();
								editor.putString(GAME_PREFERENCES_PASSWORD,
										strPassword1);
								editor.commit();
								passwordInfo.setText(R.string.settings_pwd_set);
							} else {
								Log.d(DEBUG_TAG,
										"Passwords do not match. Not saving. Keeping old password (if set).");
							}
							// We forcefully dismiss and remove the Dialog, so
							// it
							// cannot be used again
							CreateActaActivity.this
									.removeDialog(PASSWORD_DIALOG_ID);
						}
					});
			// Create the AlertDialog and return it
			AlertDialog passwordDialog = builder.create();
			return passwordDialog;



		 case FRIEND_EMAIL_DIALOG_ID:
			    LayoutInflater infl = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            final View friendDialogLayout = infl.inflate(R.layout.friend_dialog, (ViewGroup) findViewById(R.id.root));

	            AlertDialog.Builder friendDialogBuilder = new AlertDialog.Builder(this);
	            friendDialogBuilder.setView(friendDialogLayout);
	            final TextView emailText = (TextView) friendDialogLayout.findViewById(R.id.EditText_Friend_Email);

	            friendDialogBuilder.setPositiveButton(string.ok, new DialogInterface.OnClickListener() {

	                public void onClick(DialogInterface dialog, int which) {

	                    String friendEmail = emailText.getText().toString();
	                    if (friendEmail != null && friendEmail.length() > 0) {
	                        doFriendRequest(friendEmail);
	                    }
	                }
	            });
	            return friendDialogBuilder.create();
		}

		return null;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		super.onPrepareDialog(id, dialog);
		switch (id) {
		case PLACE_DIALOG_ID:

			// Handle any Favorite Place Dialog initialization here
			AlertDialog placeDialog = (AlertDialog) dialog;

			String strFavPlaceName;

			// Check for favorite place preference
			if (mGameSettings.contains(GAME_PREFERENCES_FAV_PLACE_NAME)) {

				// Retrieve favorite place from preferences
				strFavPlaceName = mGameSettings.getString(
						GAME_PREFERENCES_FAV_PLACE_NAME, "");
				mFavPlaceCoords = new GPSCoords(mGameSettings.getFloat(
						GAME_PREFERENCES_FAV_PLACE_LAT, 0),
						mGameSettings.getFloat(GAME_PREFERENCES_FAV_PLACE_LONG,
								0));

			} else {

				// No favorite place set, set coords to current location
				strFavPlaceName = getResources().getString(
						R.string.settings_favplace_currentlocation); // We do
																		// not
																		// name
																		// this
																		// place
																		// ("here"),
																		// but
																		// use
																		// it as
																		// a map
																		// point.
																		// User
																		// can
																		// supply
																		// the
																		// name
																		// they
																		// like
				calculateCurrentCoordinates();

			}

			// Set the placename text and coordinates either to the saved
			// values, or just set the GPS coords to the current location
			final EditText placeName = (EditText) placeDialog
					.findViewById(R.id.EditText_FavPlaceName);
			placeName.setText(strFavPlaceName);

			final TextView placeCoordinates = (TextView) placeDialog
					.findViewById(R.id.TextView_FavPlaceCoords_Info);
			placeCoordinates.setText(formatCoordinates(mFavPlaceCoords.mLat,
					mFavPlaceCoords.mLon));

			return;

		case DATE_DIALOG_ID:
			// Handle any DatePickerDialog initialization here
			DatePickerDialog dateDialog = (DatePickerDialog) dialog;
			int iDay,
			iMonth,
			iYear;
			// Check for date of birth preference
			if (mGameSettings.contains(GAME_PREFERENCES_DOB)) {
				// Retrieve Birth date setting from preferences
				long msBirthDate = mGameSettings.getLong(GAME_PREFERENCES_DOB,
						0);
				Time dateOfBirth = new Time();
				dateOfBirth.set(msBirthDate);

				iDay = dateOfBirth.monthDay;
				iMonth = dateOfBirth.month;
				iYear = dateOfBirth.year;
			} else {
				Calendar cal = Calendar.getInstance();
				// Today's date fields
				iDay = cal.get(Calendar.DAY_OF_MONTH);
				iMonth = cal.get(Calendar.MONTH);
				iYear = cal.get(Calendar.YEAR);
			}
			// Set the date in the DatePicker to the date of birth OR to the
			// current date
			dateDialog.updateDate(iYear, iMonth, iDay);
			return;
		case PASSWORD_DIALOG_ID:
			// Handle any Password Dialog initialization here
			// Since we don't want to show old password dialogs, just set new
			// ones, we need not do anything here
			// Because we are not "reusing" password dialogs once they have
			// finished, but removing them from
			// the Activity Dialog pool explicitly with removeDialog() and
			// recreating them as needed.
			return;
		 case FRIEND_EMAIL_DIALOG_ID:
			 return;
		}
	}

	/**
	 * Helper to format coordinates for screen display
	 *
	 * @param lat
	 * @param lon
	 * @return A string formatted accordingly
	 */
	private String formatCoordinates(float lat, float lon) {
		StringBuilder strCoords = new StringBuilder();
		strCoords.append(lat).append(",").append(lon);
		return strCoords.toString();
	}

	/**
	 *
	 * If location name can't be determined, try to determine location based on
	 * current coords
	 *
	 * @param strLocation
	 *            Location or place name to try
	 */
	private void resolveLocation(String strLocation) {
		boolean bResolvedAddress = false;

		if (strLocation.equalsIgnoreCase(getResources().getString(
				R.string.settings_favplace_currentlocation)) == false) {
			bResolvedAddress = lookupLocationByName(strLocation);
		}

		if (bResolvedAddress == false) {
			// If String place name could not be determined (or matches the
			// string for "current location", assume this is a custom name of
			// the current location
			calculateCurrentCoordinates();
		}
	}

	/**
	 * Attempt to get the last known location of the device. Usually this is the
	 * last value that a location provider set
	 */
	private void calculateCurrentCoordinates() {
		float lat = 0, lon = 0;

		try {
			LocationManager locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
			Location recentLoc = locMgr
					.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
			lat = (float) recentLoc.getLatitude();
			lon = (float) recentLoc.getLongitude();
		} catch (Exception e) {
			Log.e(DEBUG_TAG, "Location failed", e);
		}
		try
		{
			mFavPlaceCoords = new GPSCoords(lat, lon);
		}catch(Exception ex)
		{
			Log.e(DEBUG_TAG, "GPSCoords", ex);
		}
	}

	/**
	 * 
	 * Take a description of a location, store the coordinates in
	 * mFavPlaceCoords
	 * 
	 * @param strLocation
	 *            The location or placename to look up
	 * @return true if the address or place was recognized, otherwise false
	 */
	private boolean lookupLocationByName(String strLocation) {
		final Geocoder coder = new Geocoder(getApplicationContext());
		boolean bResolvedAddress = false;

		try {

			List<Address> geocodeResults = coder.getFromLocationName(
					strLocation, 1);
			Iterator<Address> locations = geocodeResults.iterator();

			while (locations.hasNext()) {
				Address loc = locations.next();
				mFavPlaceCoords = new GPSCoords((float) loc.getLatitude(),
						(float) loc.getLongitude());
				bResolvedAddress = true;
			}
		} catch (Exception e) {
			Log.e(DEBUG_TAG, "Failed to geocode location", e);
		}
		return bResolvedAddress;
	}

	private class GPSCoords {
		float mLat, mLon;

		GPSCoords(float lat, float lon) {
			mLat = lat;
			mLon = lon;

		}
	}

	public static class UploaderService extends Service {
		private static final String DEBUG_TAG = "SettingsActivity$UploaderService";

		private UploadTask uploader;

		@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
			uploader = new UploadTask();
			uploader.execute();
			Log.d(DEBUG_TAG, "Settings and image upload requested");
			return START_REDELIVER_INTENT;
		}

		@Override
		public IBinder onBind(Intent intent) {
			// no binding
			return null;
		}

		private class UploadTask extends AsyncTask<Object, String, Boolean> {
			SharedPreferences mGameSettings;

			@Override
			protected void onPostExecute(Boolean result) {
				// perhaps notify the user?
			}

			@Override
			protected void onPreExecute() {
				mGameSettings = getSharedPreferences(GAME_PREFERENCES,
						Context.MODE_PRIVATE);
			}

			@Override
			protected Boolean doInBackground(Object... params) {
				boolean result = postSettingsToServer();
				if (result && !isCancelled()) {
					result = postAvatarToServer();
				}
				Log.d(DEBUG_TAG, "Done uploading settings and image");
				return result;
			}

			private boolean postSettingsToServer() {
				boolean succeeded = false;

				// an example of using HttpClient with HTTP GET and form
				// variables

				Integer playerId = mGameSettings.getInt(
						GAME_PREFERENCES_PLAYER_ID, -1);
				String nickname = mGameSettings.getString(
						GAME_PREFERENCES_NICKNAME, "");
				String email = mGameSettings.getString(GAME_PREFERENCES_EMAIL,
						"");
				String password = mGameSettings.getString(
						GAME_PREFERENCES_PASSWORD, "");
				Integer score = mGameSettings
						.getInt(GAME_PREFERENCES_SCORE, -1);
				Integer gender = mGameSettings.getInt(GAME_PREFERENCES_GENDER,
						-1);
				Long birthdate = mGameSettings.getLong(GAME_PREFERENCES_DOB, 0);
				String favePlaceName = mGameSettings.getString(
						GAME_PREFERENCES_FAV_PLACE_NAME, "");

				/*Vector<NameValuePair> vars = new Vector<NameValuePair>();

				if (playerId == -1) {
					// if we don't have a playerId yet, we must pass up a
					// uniqueId
					// a good enough way to generate one is using the UUID
					// class:
					String uniqueId = UUID.randomUUID().toString();
					Log.d(DEBUG_TAG, "Unique ID: " + uniqueId);

					// if you want the user to be able to restore data by using
					// this, you could email it to them

					// why not use getDeviceId from TelephonyManager?
					// See: http://goo.gl/sAbV2
					// In short, it only works on phones. Got a wifi only
					// tablet? A TV? forget it.

					vars.add(new BasicNameValuePair("uniqueId", uniqueId));

				} else {
					// otherwise, we use the playerId to update data
					vars.add(new BasicNameValuePair("updateId", playerId
							.toString()));
					// and we go ahead and push up the latest score
					vars.add(new BasicNameValuePair("score", score.toString()));
				}
				vars.add(new BasicNameValuePair("nickname", nickname));
				vars.add(new BasicNameValuePair("email", email));
				vars.add(new BasicNameValuePair("password", password));
				vars.add(new BasicNameValuePair("gender", gender.toString()));
				vars.add(new BasicNameValuePair("faveplace", favePlaceName));
				vars.add(new BasicNameValuePair("dob", birthdate.toString()));

				String url = TRIVIA_SERVER_ACCOUNT_EDIT + "?"
						+ URLEncodedUtils.format(vars, null);

				HttpGet request = new HttpGet(url);

				try {

					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					HttpClient client = new DefaultHttpClient();
					String responseBody = client.execute(request,
							responseHandler);

					if (responseBody != null && responseBody.length() > 0) {
						Integer resultId = Integer.parseInt(responseBody);
						Editor editor = mGameSettings.edit();
						editor.putInt(GAME_PREFERENCES_PLAYER_ID, resultId);
						editor.commit();
					}
					succeeded = true;

				} catch (ClientProtocolException e) {
					Log.e(DEBUG_TAG, "Failed to get playerId (protocol): ", e);
				} catch (IOException e) {
					Log.e(DEBUG_TAG, "Failed to get playerId (io): ", e);
				}*/
				return succeeded;
			}

			private boolean postAvatarToServer() {
				boolean succeeded = false;
				// an example using HttpClient and HttpMime to upload a file via
				// HTTP POST in the same
				// way a web browser might, using multipart MIME encoding
			/*	String avatar = mGameSettings.getString(
						GAME_PREFERENCES_AVATAR, "");
				Integer playerId = mGameSettings.getInt(
						GAME_PREFERENCES_PLAYER_ID, -1);

				MultipartEntity entity = new MultipartEntity(
						HttpMultipartMode.BROWSER_COMPATIBLE);
				File file = new File(avatar);
				if (file.exists()) {
					FileBody encFile = new FileBody(file);

					entity.addPart("avatar", encFile);
					try {
						entity.addPart("updateId",
								new StringBody(playerId.toString()));
					} catch (UnsupportedEncodingException e) {
						Log.e(DEBUG_TAG, "Failed to add form field.", e);
					}

					HttpPost request = new HttpPost(TRIVIA_SERVER_ACCOUNT_EDIT);
					request.setEntity(entity);

					HttpClient client = new DefaultHttpClient();

					try {
						ResponseHandler<String> responseHandler = new BasicResponseHandler();
						String responseBody = client.execute(request,
								responseHandler);

						if (responseBody != null && responseBody.length() > 0) {
							Log.w(DEBUG_TAG,
									"Unexpected response from avatar upload: "
											+ responseBody);
						}
						succeeded = true;

					} catch (ClientProtocolException e) {
						Log.e(DEBUG_TAG, "Unexpected ClientProtocolException",
								e);
					} catch (IOException e) {
						Log.e(DEBUG_TAG, "Unexpected IOException", e);
					}
				} else {
					Log.d(DEBUG_TAG, "No avatar to upload");
					succeeded = true;
				}*/

				return succeeded;

			}

		}

	}


	private void doVerificacionInfraccion(Integer ItemInfraccion)
	{
		 
		 
		 Intent intent = new Intent(Intent.ACTION_SEARCH);
		 Bundle appSearchData = new Bundle();
		 appSearchData.putString(InfraccionNomencladaSearchableActivity.APP_DATA_OBJETO_BUSQUEDA, "INFRACCION");
		 appSearchData.putString(InfraccionNomencladaSearchableActivity.APP_DATA_LABEL_SEARCH, "Busqueda de Infraccion");
		 appSearchData.putInt(InfraccionNomencladaSearchableActivity.APP_DATA_HINT_SEARCH, R.string.searchable_hint_default_infraccion);
		 appSearchData.putString(InfraccionNomencladaSearchableActivity.APP_DATA_HEADER_LIST, "Infracciones Nomencladas");
		 
		 String initialQuery = "";// MAS ADELANTE VEREMOS editText_NumeroDocumento.getText().toString();
		 
    	 intent.putExtra(SearchManager.APP_DATA, appSearchData);
    	 
         if (!TextUtils.isEmpty(initialQuery)) {
             intent.putExtra(SearchManager.QUERY, initialQuery);
         }
		 intent.setClass(CreateActaActivity.this,InfraccionNomencladaSearchableActivity.class);
		 
			switch (ItemInfraccion) {
			case 1:
				//editText_CODInfraccion_1.setText("valor" + ItemInfraccion);
				 startActivityForResult(intent, CreateActaActivity.SEARCH_INFRACCION1);
				break;
			case 2: 
				//editText_CODInfraccion_2.setText("valor" + ItemInfraccion);
				 startActivityForResult(intent, CreateActaActivity.SEARCH_INFRACCION2);
				break;
			default:
				break;
			}


		
	}


    protected void doPrintActaRequest(final List<ActaConstatacion> pActa) {

        final ActaConstatacionRules rules = new ActaConstatacionRules(this);

    	new ZebraPrinterTask<Object, Object, Object>((Activity)CreateActaActivity.this, new Object[0])
         {

    	   Boolean Terminar = false;


             @Override
           public Object doWork(final Object[] params)
             throws ConnectionException, ZebraIllegalArgumentException
	           {
        	   try
		          {
        		   final PrintActaHelper printActaHelper =  new PrintActaHelper(this.printerConnection, this.printer);
   					try
   		            {
		        		   for(ActaConstatacion acta : pActa ) {
								   printActaHelper.printCreateActaCPCL((ActaConstatacion) acta, true, rules.omitirCodigoBarra(acta));
								   Thread.sleep(7000);
						   }
		        		   
   		            }
   	        	    catch(Exception ex)
   	        	    {
   	        	       Terminar= true;
   	        		   handleError(ex);
   	        	    }
   					//Terminar = true;// estga linea hay que desahbilitarla
   					/* 07/10/2017  habilitar para Mendoza
   					 * esta parte es para la segundo impresion Hay que habilitarla para Mendoza  por ahora lo dejo en pruebas comentado
   					*/
   					UIHelper.showAlertOnGuiThread(CreateActaActivity.this, "Presione aceptar para continuar con la impresion...", "Impresion del Acta", new Runnable() {
   	   					@Override
   	   					public void run() {
   	   				   
   	   					try
   	   		             {
   	   	        		   
   			        		   for(ActaConstatacion acta : pActa )
   			        		   {
   			        			   printActaHelper.printCreateActaCPCL((ActaConstatacion)acta,false, false);
   			        			   Thread.sleep(7000);
   			        		   }
   			        		Terminar= true;
   	   		            }
   	   	        	    catch(Exception ex)
   	   	        	    {
   	   	        	    	Terminar= true;
   	   	        		   handleError(ex);
   	   	        	    }	 
   	   					}
   	   					});
   				/*	*/
   					
        		   
        		   while(Terminar==false){
        			   Thread.sleep(4000);
        			}

        		   //UIHelper.showErrorOnGuiThread((Activity)CreateActaActivity.this, "Terminado la impresion", null);
        		   
        		   // CreateActaHelper.printCreateActaCPCL((ActaConstatacion)pActa,this.printer);
	             }
        	    catch(Exception ex)
        	   {
        		   handleError(ex);
        	   }
        	     bGrabando = false;
	             return null;
	           }
           @Override
           public void handleError(final Exception e)
           {
     		 bGrabando = false;
             UIHelper.showErrorOnGuiThread((Activity)CreateActaActivity.this, e.getLocalizedMessage(), null);
             CreateActaActivity.this.finish();
           }
           
           @Override
           public void onPreExecute()
           {
   			WorkingInPrintAsyncTask = true;
   			pleaseWaitDialog = ProgressDialog.show(CreateActaActivity.this,"Impresion de Acta", "Iniciando conexion con Impresora", true, false);
        	super.onPreExecute();
           }
           @Override
           public void onPostExecute(Object object)
           {
        		WorkingInPrintAsyncTask = false; 
    			super.onPostExecute(object);
    			try
    			{
    				pleaseWaitDialog.dismiss();
    				pleaseWaitDialog = null;
    			} catch(Exception ex){}
    			
//    			UIHelper.showAlertOnGuiThread(CreateActaActivity.this, "Presione aceptar para continuar con la impresion...", "Impresion del Acta", new Runnable() {
//					
//					@Override
//					public void run() {
//						
//						new ZebraPrinterTask<Object, Object, Object>((Activity)CreateActaActivity.this, new Object[0])
//				         {
//				    	    
//				           @Override
//				           public Object doWork(final Object[] params)
//				             throws ConnectionException, ZebraIllegalArgumentException
//					           {
//				        	   try
//					             {
//				        		   PrintActaHelper printActaHelper =  new PrintActaHelper(this.printerConnection, this.printer);
//				        		   for(ActaConstatacion acta : pActa )
//				        		   {
//				        			   printActaHelper.printCreateActaCPCL((ActaConstatacion)acta);
//				        		   }
//					             }
//				        	    catch(Exception ex)
//				        	   {
//				        		   handleError(ex);
//				        	   }
//					             return null;
//					           }
//				           @Override
//				           public void handleError(final Exception e)
//				           {
//				             UIHelper.showErrorOnGuiThread((Activity)CreateActaActivity.this, e.getLocalizedMessage(), null);
//				             CreateActaActivity.this.finish();
//				           }
//				           
//				           @Override
//				           public void onPreExecute()
//				           {
//				   			WorkingInPrintAsyncTask = true;
//				   			pleaseWaitDialog = ProgressDialog.show(CreateActaActivity.this,"Impresion de Acta", "Iniciando conexion con Impresora", true, false);
//				        	super.onPreExecute();
//				           }
//				           @Override
//				           public void onPostExecute(Object object)
//				           {
//				        		WorkingInPrintAsyncTask = false; 
//				    			super.onPostExecute(object);
//				    			
//				    			pleaseWaitDialog.dismiss();
//				    			pleaseWaitDialog = null;
//				    			CreateActaActivity.this.finish();
//				           }
//				         };
//					}
//				});
    			
    			Utilities.ShowToast(CreateActaActivity.this, "Impresion Finalizada");
    			//CreateActaActivity.this.finish();
    			CreateActaActivity.this.cleanActivity();
           }
         };
        // make sure we don't collide with another pending update
    	/*
        if (printActaRequest == null || printActaRequest.getStatus() == AsyncTask.Status.FINISHED || printActaRequest.isCancelled()) {
        	printActaRequest = new PrintActaRequestTask(this);
        	printActaRequest.execute(pActa);
        } else {
            Log.w(DEBUG_TAG, "Advertencia: La Solicitud de Impresion del Acta : " + pActa.getNumeroActa() + " ya esta siendo procesada");
        }
        */
    }

    private void doFriendRequest(String friendEmail) {
        // make sure we don't collide with another pending update
        if (friendRequest == null || friendRequest.getStatus() == AsyncTask.Status.FINISHED || friendRequest.isCancelled()) {
            friendRequest = new FriendRequestTask();
            friendRequest.execute(friendEmail);
        } else {
            Log.w(DEBUG_TAG, "Warning: friendRequestTask already going");
        }
    }
    
    private void doCleanRequest(String stuffString) {
        // make sure we don't collide with another pending update
        if (cleanFormRequest == null || cleanFormRequest.getStatus() == AsyncTask.Status.FINISHED || cleanFormRequest.isCancelled()) {
        	cleanFormRequest = new CleanFormActivityRequestTask();
        	cleanFormRequest.execute("");
        } else {
            Log.w(DEBUG_TAG, "Warning: cleanFormRequestTask already going");
        }
    }

    private class PrintActaRequestTask extends AsyncTask<ActaConstatacion, Object, Boolean> {
    	private Context mContext; 
    	//

    	
    	public PrintActaRequestTask(Context context)
    	{
    		mContext = context;
    		
    	}
        @Override
        protected void onPostExecute(Boolean result) {
            CreateActaActivity.this.setProgressBarIndeterminateVisibility(false);
        	if (result)
        		((Activity)mContext).finish();
        }

        @Override
        protected void onPreExecute() {
        	
            CreateActaActivity.this.setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Boolean doInBackground(ActaConstatacion... params) {
            Boolean succeeded = false;
            try {
                ActaConstatacion acta = params[0];
                
                
                /*
                SharedPreferences prefs = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
                Integer playerId = prefs.getInt(GAME_PREFERENCES_PLAYER_ID, -1);

                Vector<NameValuePair> vars = new Vector<NameValuePair>();
                vars.add(new BasicNameValuePair("command", "add"));
                vars.add(new BasicNameValuePair("playerId", playerId.toString()));
                vars.add(new BasicNameValuePair("friend", friendEmail));*/

                //HttpClient client = new DefaultHttpClient();

                // an example of using HttpClient with HTTP POST and form variables
                /*
                HttpPost request = new HttpPost(TRIVIA_SERVER_FRIEND_ADD);
                request.setEntity(new UrlEncodedFormEntity(vars));
                */
                /*
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String responseBody = client.execute(request, responseHandler);

                Log.d(DEBUG_TAG, "Add friend result: " + responseBody);
                if (responseBody != null) {
                    succeeded = true;
                }
                */
                succeeded = true;

            } catch(Exception ex)
            {
            	
            } 
            
            /*catch (MalformedURLException e) {
                Log.e(DEBUG_TAG, "Failed to add friend", e);
            } catch (IOException e) {
                Log.e(DEBUG_TAG, "Failed to add friend", e);
            } */

            return succeeded;
        }

    }
    public Handler receptorVerificacionPolicialTask = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	      
	    	 RestriccionDTO restricion = (RestriccionDTO) msg.obj;
	    	 String sResultado ="";
	    	 if (restricion==null)
	    	 {
	    		 UIHelper.showAlert(CreateActaActivity.this, "No se pudo obtener la informacion solicitada", "Verificacion Policial", null);
	    		 return;
	    	 }
	    	 Intent intent = new Intent();
			    Bundle appResultData = new Bundle();
			     sResultado = restricion.getResultado();
			    if(sResultado==null || sResultado.equals(""))
			    {
			    	 UIHelper.showAlert(CreateActaActivity.this, "Sin Antecedentes", "Verificacion Policial", null);
		    		 return;
			    }
			    else
			    {
			    	UIHelper.showAlert(CreateActaActivity.this, sResultado, "Verificacion Policial", null);
			    }
			    /*
			    appResultData.putString(ResultadoVerificacionPolicialActivity.APP_DATA_RESULTADO,sResultado); 
			        
			    intent.putExtra(ResultadoVerificacionPolicialActivity.APP_DATA,appResultData);
				intent.setClass(CreateActaActivity.this,ResultadoVerificacionPolicialActivity.class);
	             
				startActivity(intent);
				*/
	    }
	  };
	  //Licencia
    	public Handler receptorVerificacionPolicialyLicenciaTask = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	      
	    	 LicenciaValidaSyncDTO licenciaValida = (LicenciaValidaSyncDTO) msg.obj;
	    	 String sResultado ="";
	    	 String sDatosLicencia ="";
	    	 
	    	 if (licenciaValida==null)
	    	 {
	    		 UIHelper.showAlert(CreateActaActivity.this, "No se obtener la informacion solicitada", "Verificacion de Licencia", null);
	    		 return;
	    	 }
	    	 StringBuilderForPrint strb = new StringBuilderForPrint();
	    	 LicenciaDTO licencia = licenciaValida.licencia;
	    	 if(licencia!=null)
	    	 {
		    	 strb.append("Licencia:" + licencia.getNumeroLicencia())
		    	 .append("Vencida:" + licencia.getLicenciaVencida())
		    	 .append("Fecha Vencimiento:" + licencia.getFechaVencimiento())
		    	 .append("Titular" + licencia.getApellido() + ", " + licencia.getNombre())
		    	 .append("Numero Documento" +  licencia.getNumeroDocumento());
		    	 sDatosLicencia = strb.toString();
	    	 }
	    	 
	    	 String nroLicencia = editText_NumeroLicencia.getText().toString();
	    	 if(licencia.getNumeroLicencia()!=null && licencia.getNumeroLicencia().equals(nroLicencia) )
	    	 {
	    		// agregando aqui trabajando
	    		editText_ClaseLicencia.setText(licencia.getClaseLicencia());
	    		editText_NumeroDocumento.setText(licencia.getNumeroDocumento());
	    		
	    		//int posicionSexo = licencia.getIdSexo().trim().equals("M")?1:2;
	    		//myGeneroSpinner.setSelection(posicionSexo);
	    		int cantidadItems=0;
	    		/*
	    		cantidadItems  = myGeneroAdapter.getCount();
					    		
	    		String sexo = licencia.getIdSexo();
				int posicionSexo=0;
				if(cantidadItems>0)
				{	
					for(int i=0; i<=cantidadItems;i++)
					{
						
					    Genero gener =  (Genero) myGeneroSpinner.getItemAtPosition(i);	
						if(gener.equals(gener.getId().toString()))
						{
							myGeneroSpinner.setSelection(i);
							break;
						}
					}
				}
				*/
	    		
	    		
				try
				{
	    		 	String sFechaVencimiento = licencia.getFechaVencimiento();
	    		 	Time dateOfExpired = new Time();
					int dayOfMonth = Integer.parseInt(sFechaVencimiento.substring(1, 2));
					int monthOfYear = Integer.parseInt(sFechaVencimiento.substring(3, 5));
					int year = Integer.parseInt(sFechaVencimiento.substring(6, 10));
					dateOfExpired.set(dayOfMonth, monthOfYear-1, year);
					long dtDob = dateOfExpired.toMillis(true);

					CharSequence DateFLV = DateFormat.format("dd/MM/yyyy",dtDob);
					editText_FVL_Info.setText(DateFLV);
					mCurrenActaSettings.edit().putString(CURRENT_ACTA_FECHA_VENCIMIENTO_LICENCIA,DateFLV.toString()).commit();
				} catch(Exception ex)
				{
					
				}
				
				//buscamos el Tipo de Documento
				cantidadItems  = myTipoDocumentoTitularAdapter.getCount();
				String tipo = licencia.getIdTipoDocumento();
				int posicionDocumento =0;
				if(cantidadItems>0)
				{	
					for(int i=0; i<=cantidadItems;i++)
					{
						posicionDocumento++;
					    TipoDocumento tipoDocumento =  (TipoDocumento) myTipoDocumentoSpinner.getItemAtPosition(i);	
						if(tipo.equals(tipoDocumento.getId().toString()))
						{
							myTipoDocumentoSpinner.setSelection(i);
							break;
						}
					}
				}
				

				
			//fin
			   //DeshabilitarListeners(SPINNER_PAIS_LICENCIA,SPINNER_PROVINCIA_LICENCIA, SPINNER_DEPARTAMENTO_LICENCIA,SPINNER_LOCALIDAD_LICENCIA);
			   		//myPaisSpinner.setSelection(3);   
				   //SetFirstSelection(3,SPINNER_PAIS_LICENCIA);
				   //fillProvinciaSpinner("3");
				cantidadItems  = myPaisAdapter.getCount();
				String pais = licencia.getPaisLicencia();
				int posicionPais =0;
				if(cantidadItems>0)
				{	
					for(int i=0; i<=cantidadItems;i++)
					{
						
					    Pais pai =  (Pais) myPaisSpinner.getItemAtPosition(i);	
						if(pais.equals(pai.getId()))
						{
							posicionPais = i;
							//DeshabilitarListeners(SPINNER_PAIS_LICENCIA);
								//fillProvinciaSpinner(pais);
								//myPaisSpinner.setSelection(posicionPais);
							DeshabilitarListeners(SPINNER_PAIS_LICENCIA);
								SetFirstSelection(posicionPais,SPINNER_PAIS_LICENCIA);
								fillProvinciaSpinner(pais);
							HabilitarListeners(SPINNER_PAIS_LICENCIA);
							
							break;
						}
					}
					
				}
				
				// busqueda de provincia	
					cantidadItems  = myProvinciaAdapter.getCount();
					String provincia = licencia.getProvinciaLicencia();
					int posicionProvincia =0;
					if(cantidadItems>0)
					{	
						for(int i=0; i<=cantidadItems;i++)
						{
							
						    Provincia prov =  (Provincia) myProvinciaSpinner.getItemAtPosition(i);	
							if(provincia.equals(prov.getId()))
							{
								posicionProvincia = i;
								DeshabilitarListeners(SPINNER_PROVINCIA_LICENCIA);
									//myProvinciaSpinner.setSelection(posicionProvincia);
									SetFirstSelection(posicionProvincia,SPINNER_PROVINCIA_LICENCIA);
									fillDepartamentoSpinner(provincia);
								HabilitarListeners(SPINNER_PROVINCIA_LICENCIA);	
								//myProvinciaSpinner.setSelection(posicionProvincia);
								break;
							}
						}
						
					}
				   
					
					
				   
					  //departamento
					cantidadItems  = myDepartamentoAdapter.getCount();
					String departamento = licencia.getDepartamentoLicencia();
					int posicionDepartamento =0;
					if(cantidadItems>0)
					{	
						for(int i=0; i<=cantidadItems;i++)
						{
							
						    Departamento depto =  (Departamento) myDepartamentoSpinner.getItemAtPosition(i);	
							if(departamento.equals(depto.getId().toString()))
							{
								posicionDepartamento =i;
								DeshabilitarListeners(SPINNER_DEPARTAMENTO_LICENCIA);
									
									//myDepartamentoSpinner.setSelection(posicionDepartamento);
									SetFirstSelection(posicionDepartamento,SPINNER_DEPARTAMENTO_LICENCIA);
									fillLocalidadSpinner(departamento);
								HabilitarListeners(SPINNER_DEPARTAMENTO_LICENCIA);	

								break;
							}
						}
						
					}
				   
				   
				   

					  //localidad
					cantidadItems  = myLocalidadAdapter.getCount();
					String localidad = licencia.getLocalidadLicencia();
					int posicionLocalidad =0;
					if(cantidadItems>0)
					{	
						for(int i=0; i<=cantidadItems;i++)
						{
							
						    Localidad locali =  (Localidad) myLocalidadSpinner.getItemAtPosition(i);	
							if(localidad.equals(locali.getId().toString()))
							{
								posicionLocalidad=i;
								//myLocalidadSpinner.setSelection(posicionLocalidad);
								//myLocalidadSpinner.setSelection(posicionLocalidad);
								SetFirstSelection(posicionLocalidad,SPINNER_LOCALIDAD_LICENCIA);
								break;
							}
						}
						
					}				

				   
				   //SetFirstSelection(posicionLocalidad,SPINNER_LOCALIDAD_LICENCIA);
			   
     		   //HabilitarListeners(SPINNER_PAIS_LICENCIA,SPINNER_PROVINCIA_LICENCIA, SPINNER_DEPARTAMENTO_LICENCIA,SPINNER_LOCALIDAD_LICENCIA);
				
				
	    		 
	    	 }
	    		 List<RestriccionDTO> restriccion = licenciaValida.restriciones;
	    	 if (restriccion !=null && restriccion.size()>0)
	    	 {
	    		 sResultado = licenciaValida.restriciones.get(0).getResultado();
	    	 }
	    	 
			Intent intent = new Intent();
		    Bundle appResultData = new Bundle();
		     
		    if(sDatosLicencia.equals("") && sResultado.equals(""))
		    {
		    	sDatosLicencia = "No se encontraron datos de la Licencia";
		    }
		    appResultData.putString(ResultadoVerificacionPolicialActivity.APP_DATA_LICENCIA,sDatosLicencia);   
		    appResultData.putString(ResultadoVerificacionPolicialActivity.APP_DATA_RESULTADO,sResultado); 
		        
		    intent.putExtra(ResultadoVerificacionPolicialActivity.APP_DATA,appResultData);
			intent.setClass(CreateActaActivity.this,ResultadoVerificacionPolicialActivity.class);

			startActivity(intent);

	    }
	  };

	  /* Tareas para Enviar codigoLeido */
	  private class EnvioCodigoQRRequestTask extends AsyncTask<String, Object, String> {

	    	private Handler postExecuteHandler;

	        public void setPostExecuteHandler(Handler postExecuteHandler) {
	            this.postExecuteHandler = postExecuteHandler;
	        }

	    	@Override
	        protected void onPostExecute(String result) {
	            CreateActaActivity.this.setProgressBarIndeterminateVisibility(false);
	            WorkingInVerificacionAsyncTask = false; 
				super.onPostExecute(result);
				
				//pleaseWaitDialog.dismiss();
				//pleaseWaitDialog = null;
				
				if (postExecuteHandler != null) {
		            Message msg = Message.obtain();
		            msg.obj = result;
		            postExecuteHandler.sendMessage(msg);
		        }
				
	        }

	        @Override
	        protected void onPreExecute() {
	            CreateActaActivity.this.setProgressBarIndeterminateVisibility(true);
	        	WorkingInVerificacionAsyncTask = true;
	   			pleaseWaitDialog = ProgressDialog.show(CreateActaActivity.this,"Lectura de Codigo de Mancha", "Codigo Mancha Licencia Nacional", true, false);
	        	super.onPreExecute();
	            
	        }

	        @Override
	        protected String doInBackground(String... params) {
	            Boolean succeeded = false;
	            try {
	                String datosLicencia = params[0];

	                Context context = getApplicationContext();
	                
	                DeviceActasSync syncDeviceActas= new DeviceActasSync(context);
					pleaseWaitDialog.dismiss();
					pleaseWaitDialog = null;
	                Boolean resultado = syncDeviceActas.EnviarCodigoQR(datosLicencia);
	                
	                return resultado.toString();
	            } 
	            catch(Exception ex)
	            {
	            	String message = ex.getMessage();
	            	//UIHelper.showAlert((Activity)CreateActaActivity.this, message, "Error Obteniendo datos de Mancha de la Licencia", null);
	            	succeeded = false;
	            }
	            /*
	            catch (MalformedURLException e) {
	                Log.e(DEBUG_TAG, "Failed to add friend", e);
	            } catch (IOException e) {
	                Log.e(DEBUG_TAG, "Failed to add friend", e);
	            }*/

	            return null;
	        }

	    }

	  /* fin tareas*/
    private class LicenciaRequestTask extends AsyncTask<String, Object, LicenciaValidaSyncDTO> {
    	
    	private Handler postExecuteHandler;
    	
        public void setPostExecuteHandler(Handler postExecuteHandler) {
            this.postExecuteHandler = postExecuteHandler;
        }  
    	
    	@Override
        protected void onPostExecute(LicenciaValidaSyncDTO result) {
            CreateActaActivity.this.setProgressBarIndeterminateVisibility(false);
            WorkingInVerificacionAsyncTask = false; 
			super.onPostExecute(result);
			
			pleaseWaitDialog.dismiss();
			pleaseWaitDialog = null;
			
			if (postExecuteHandler != null) {
	            Message msg = Message.obtain();
	            msg.obj = result;
	            postExecuteHandler.sendMessage(msg);
	        }
			
        }

        @Override
        protected void onPreExecute() {
            CreateActaActivity.this.setProgressBarIndeterminateVisibility(true);
        	WorkingInVerificacionAsyncTask = true;
   			pleaseWaitDialog = ProgressDialog.show(CreateActaActivity.this,"Verificacion de Licencia", "Iniciando conexion con Sistema", true, false);
        	super.onPreExecute();
            
        }

        @Override
        protected LicenciaValidaSyncDTO doInBackground(String... params) {
            Boolean succeeded = false;
            try {
                String numeroLicencia = params[0];

                /*
                SharedPreferences prefs = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
                Integer playerId = prefs.getInt(GAME_PREFERENCES_PLAYER_ID, -1);

                Vector<NameValuePair> vars = new Vector<NameValuePair>();
                vars.add(new BasicNameValuePair("command", "add"));
                vars.add(new BasicNameValuePair("playerId", playerId.toString()));
                vars.add(new BasicNameValuePair("friend", friendEmail));

                HttpClient client = new DefaultHttpClient();

                // an example of using HttpClient with HTTP POST and form variables
                HttpPost request = new HttpPost(TRIVIA_SERVER_FRIEND_ADD);
                request.setEntity(new UrlEncodedFormEntity(vars));

                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String responseBody = client.execute(request, responseHandler);

                Log.d(DEBUG_TAG, "Add friend result: " + responseBody);
                if (responseBody != null) {
                    succeeded = true;
                }
              */
                Context context = getApplicationContext();
                SyncLicencia syncLicencia = new SyncLicencia(context);
                LicenciaValidaSyncDTO resultado = syncLicencia.validarNumeroLicencia(numeroLicencia);
                
                return resultado;
            } 
            catch(Exception ex)
            {
            	String message = ex.getMessage();
            	UIHelper.showAlert((Activity)CreateActaActivity.this, message, "Error Obteniendo datos de la Licencia", null);
            	succeeded = false;
            }
            /*
            catch (MalformedURLException e) {
                Log.e(DEBUG_TAG, "Failed to add friend", e);
            } catch (IOException e) {
                Log.e(DEBUG_TAG, "Failed to add friend", e);
            }*/

            return null;
        }

    }
    
    private class VerificacionPolicialRequestTask extends AsyncTask<String, Object, RestriccionDTO> 
    {
    	private Handler postExecuteHandler;
        final private String tipoObjetoBusqueda ;
        final private String tipoBusqueda ;
    	
        public void setPostExecuteHandler(Handler postExecuteHandler) {
            this.postExecuteHandler = postExecuteHandler;
        }
        
    	public VerificacionPolicialRequestTask (String pTipoObjetoBusqueda,String pTipoBusqueda)
        {
    		this.tipoObjetoBusqueda= pTipoObjetoBusqueda;
    		this.tipoBusqueda = pTipoBusqueda;
        }
    	
    	@Override
        protected void onPostExecute(RestriccionDTO result) {
            //VerificacionGenerica.this.setProgressBarIndeterminateVisibility(false);
            WorkingInVerificacionAsyncTask = false; 
			super.onPostExecute(result);
			
			pleaseWaitDialog.dismiss();
			pleaseWaitDialog = null;
			
			if (postExecuteHandler != null) {
	            Message msg = Message.obtain();
	            msg.obj = result;
	            postExecuteHandler.sendMessage(msg);
	        }
	  	
        }

        @Override
        protected void onPreExecute() {
            //QuizCreateActaActivity.this.setProgressBarIndeterminateVisibility(true);
        	WorkingInVerificacionAsyncTask = true;
   			pleaseWaitDialog = ProgressDialog.show(CreateActaActivity.this,"Verificacion Policial", "Iniciando conexion con Sistema", true, false);
        	super.onPreExecute();
            
        		
        }

        @Override
        protected RestriccionDTO doInBackground(String... params) {
        	RestriccionDTO resultado= null;
            try {
                String pDatoBusqueda = params[0];

                
                Context context = GlobalStateApp.getInstance().getApplicationContext();
                VerificacionPolicialSync  verificacionPolicialSync = new VerificacionPolicialSync(context);
                
                resultado = verificacionPolicialSync.BuscarRestricciones(tipoObjetoBusqueda, tipoBusqueda, pDatoBusqueda);
                //resultado = syncLicencia.validarNumeroLicencia(numeroLicencia);
                
                return resultado;
            } 
            catch(Exception ex)
            {
            	String message = ex.getMessage();
            	//UIHelper.showAlert(VerificacionGenerica.this, message, "Error Obteniendo datos de la Licencia", null);
            	resultado = null;
            }
            /*
            catch (MalformedURLException e) {
                Log.e(DEBUG_TAG, "Failed to add friend", e);
            } catch (IOException e) {
                Log.e(DEBUG_TAG, "Failed to add friend", e);
            }*/

            return resultado;
        }

    }
  
    
    private class FriendRequestTask extends AsyncTask<String, Object, Boolean> {
        @Override
        protected void onPostExecute(Boolean result) {
            CreateActaActivity.this.setProgressBarIndeterminateVisibility(false);
        }

        @Override
        protected void onPreExecute() {
            CreateActaActivity.this.setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            Boolean succeeded = false;
            /*try {
                String friendEmail = params[0];

                SharedPreferences prefs = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
                Integer playerId = prefs.getInt(GAME_PREFERENCES_PLAYER_ID, -1);

                Vector<NameValuePair> vars = new Vector<NameValuePair>();
                vars.add(new BasicNameValuePair("command", "add"));
                vars.add(new BasicNameValuePair("playerId", playerId.toString()));
                vars.add(new BasicNameValuePair("friend", friendEmail));

                HttpClient client = new DefaultHttpClient();

                // an example of using HttpClient with HTTP POST and form variables
                HttpPost request = new HttpPost(TRIVIA_SERVER_FRIEND_ADD);
                request.setEntity(new UrlEncodedFormEntity(vars));

                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String responseBody = client.execute(request, responseHandler);

                Log.d(DEBUG_TAG, "Add friend result: " + responseBody);
                if (responseBody != null) {
                    succeeded = true;
                }

            } catch (MalformedURLException e) {
                Log.e(DEBUG_TAG, "Failed to add friend", e);
            } catch (IOException e) {
                Log.e(DEBUG_TAG, "Failed to add friend", e);
            }
*/
            return succeeded;
        }

    }
  
    
    
    @Override
    public boolean onSearchRequested() {

    	
    	Bundle appData = new Bundle();
        appData.putString(PersonaSearchableActivity.APP_DATA_OBJETO_BUSQUEDA, "PERSONA");
        String sData = editText_NumeroDocumento.getText().toString();
        startSearch(sData, false, appData, false);
        
    	return super.onSearchRequested();
    }
    
    
    
    @Override
    protected void onSaveInstanceState(Bundle icicle) {
    	Tools.Log(Log.ERROR, TAG, "onSaveInstanceState");
    	  super.onSaveInstanceState(icicle);
    	  
    	  
    	/*icicle.putInt("myPaisSpinnerItemPosition", myPaisSpinner.getSelectedItemPosition());
    	  icicle.putInt("myProvinciaSpinnerItemPosition", myProvinciaSpinner.getSelectedItemPosition());
    	  icicle.putInt("myDepartamentoSpinnerItemPosition", myDepartamentoSpinner.getSelectedItemPosition());
    	  icicle.putInt("myLocalidadSpinnerItemPosition", myLocalidadSpinner.getSelectedItemPosition());
    	  */
    	  /*
    	   onSaveInstanceState
		   onSaveInstanceState is implemented by default.
		   onSaveInstanceState is designed to save the state of the activity if the activity is killed by the Android run time. The state can then be restored in either the onCreate or onRestoreInstanceState methods.
		   You can call onSaveInstanceState to save additional information about the state of the activity.
		   Only use onSaveInstanceState to save the transient state of the activity as it is not always called.
		   Save more persistent data in onPause as it is always called.
		   
		   When is onSaveInstanceState called?
		   -> onSaveInstanceState will always be called when the activity completes its active lifecycle, before it is killed.

		   When is onSaveInstanceState not called?
		   -> onSaveInstanceState is not called if the activity is closed by the user pressing the Back button or programmatically by calling finish().
    	   */
    }
    
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
    	Tools.Log(Log.ERROR, TAG, "onRestoreInstanceState");
    	//Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
    	
    	/*
    	 if (savedInstanceState != null) {
        
    		if (savedInstanceState.containsKey("myPaisSpinnerItemPosition")) {
                this.ItemPositionPais = savedInstanceState.getInt("myPaisSpinnerItemPosition",0);
            }
    		if (savedInstanceState.containsKey("myProvinciaSpinnerItemPosition")) {
                this.ItemPositionProvincia = savedInstanceState.getInt("myProvinciaSpinnerItemPosition",0);
            }
    		if (savedInstanceState.containsKey("myDepartamentoSpinnerItemPosition")) {
                this.ItemPositionDepartamento = savedInstanceState.getInt("myDepartamentoSpinnerItemPosition",0);
            }
    		if (savedInstanceState.containsKey("myLocalidadSpinnerItemPosition")) {
                this.ItemPositionLocalidad= savedInstanceState.getInt("myLocalidadSpinnerItemPosition",0);
            }    		
        }*/
    	

    	
        // 
        /*
        onRestoreInstanceState
        Usually the activity�s state is restored in onCreate but you can also do it in onRestoreInstanceState if you wish.
        
        When is onRestoreInstanceState called?
        -> onRestoreInstanceState is called after onStart() when the activity is being re-initialized from the saved state bundle passed by onSaveInstanceState.
        
        When is onRestoreInstanceState not called?
        -> onRestoreInstanceState is not called if the activity was not killed by the run time (as when the user presses the Back button or the activity closes programmatically using finish()).
        -> onRestoreInstanceState is also not called if there is no bundle to restore a state from.
        */
    }
    
    private void setInfraccionValues(int iRequestCode,InfraccionNomenclada infraccionNomenclada)
    { 	
    	if (infraccionNomenclada==null) return;
    	
    	String sContenido ="(" + infraccionNomenclada.getCodigo() + ") - " + infraccionNomenclada.getDescripcionCorta();
    	
    	
    	switch (iRequestCode) {
		case SEARCH_INFRACCION1:
				editText_CODInfraccion_1.setVisibility(View.VISIBLE);
				
				editText_CODInfraccion_1.setText(sContenido);	
				editText_CODInfraccion_1.setIdItem(infraccionNomenclada.getId());
				editText_CODInfraccion_1.setObjectItem(infraccionNomenclada);
		
			break;
		case SEARCH_INFRACCION2:
			editText_CODInfraccion_2.setText(sContenido);
			editText_CODInfraccion_2.setIdItem(infraccionNomenclada.getId());
			editText_CODInfraccion_2.setObjectItem(infraccionNomenclada);
			break;
		case SEARCH_INFRACCION3:
			editText_CODInfraccion_3.setText(sContenido);
			editText_CODInfraccion_3.setIdItem(infraccionNomenclada.getId());
			editText_CODInfraccion_3.setObjectItem(infraccionNomenclada);
			break;
		case SEARCH_INFRACCION4:
			editText_CODInfraccion_4.setText(sContenido);
			editText_CODInfraccion_4.setIdItem(infraccionNomenclada.getId());
			editText_CODInfraccion_4.setObjectItem(infraccionNomenclada);
			break;			
		case SEARCH_INFRACCION5:
			editText_CODInfraccion_5.setText(sContenido);
			editText_CODInfraccion_5.setIdItem(infraccionNomenclada.getId());
			editText_CODInfraccion_5.setObjectItem(infraccionNomenclada);
			break;			
		case SEARCH_INFRACCION6:
			editText_CODInfraccion_6.setText(sContenido);
			editText_CODInfraccion_6.setIdItem(infraccionNomenclada.getId());
			editText_CODInfraccion_6.setObjectItem(infraccionNomenclada);
			break;			
		default:
			break;
		}
    	//fillJuzgadoSpinner();
    }
    
    
    //To use the AsyncTask, it must be subclassed  
    private class LoadViewTask extends AsyncTask<Void, Integer, Void>  
    {  
        //Before running code in separate thread  
        @Override  
        protected void onPreExecute()  
        {  
            //Create a new progress dialog  
            progressDialog = new ProgressDialog(CreateActaActivity.this);  
            //Set the progress dialog to display a horizontal progress bar  
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
            //Set the dialog title to 'Loading...'  
            progressDialog.setTitle("Inicializando...");  
            //Set the dialog message to 'Loading application View, please wait...'  
            progressDialog.setMessage("Cargando los Datos del Formulario, Espere un momento por favor...");  
            //This dialog can't be canceled by pressing the back key  
            progressDialog.setCancelable(false);  
            //This dialog isn't indeterminate  
            progressDialog.setIndeterminate(false);  
            //The maximum number of items is 100  
            progressDialog.setMax(100);  
            //Set the current progress to zero  
            progressDialog.setProgress(0);  
            //Display the progress dialog  
            progressDialog.show();  
        }  
  
        //The code to be executed in a background thread.  
        @Override  
        protected Void doInBackground(Void... params)  
        {  
            /* This is just a code that delays the thread execution 4 times, 
             * during 850 milliseconds and updates the current progress. This 
             * is where the code that is going to be executed on a background 
             * thread must be placed. 
             */  
            try  
            {  
                //Get the current thread's token  
                synchronized (this)  
                {  
                    //Initialize an integer (that will act as a counter) to zero  
                    int counter = 0;  
                    //While the counter is smaller than four
                    
                  
        	        
                    while(counter <= 4)  
                    {  
                        //Wait 850 milliseconds  
                        this.wait(850);  
                        //Increment the counter  
                        counter++;  
                        //Set the current progress.  
                        //This value is going to be passed to the onProgressUpdate() method.  
                        publishProgress(counter*25);  
                    }  
                }  
            }  
            catch (InterruptedException e)  
            {  
                e.printStackTrace();  
            }  
            return null;  
        }  
  
        //Update the progress  
        @Override  
        protected void onProgressUpdate(Integer... values)  
        {  
            //set the current progress of the progress dialog  
            progressDialog.setProgress(values[0]);  
        }  
  
        //after executing the code in the thread  
        @Override  
        protected void onPostExecute(Void result)  
        {  
            //close the progress dialog  
            progressDialog.dismiss();  
            //initialize the View  
            //setContentView(R.layout.main);  
            setContentView(R.layout.create_acta);
            cleanActivity();//InitializeActivity();
        }  
    }  

    
    
    
    private class CleanFormActivityRequestTask extends AsyncTask<String, Object, Boolean> {
        
        @Override
        protected void onPreExecute() {
            //CreateActaActivity.this.setProgressBarIndeterminateVisibility(true);
        	pleaseWaitDialog = ProgressDialog.show(CreateActaActivity.this,"Actas Constatacion", "Espere un momento..!", true, false);
    		if(!pleaseWaitDialog.isShowing())
    				pleaseWaitDialog.show();

        }


    	@Override
        protected void onPostExecute(Boolean result) {
            //CreateActaActivity.this.setProgressBarIndeterminateVisibility(false);
    		pleaseWaitDialog.dismiss(); 
        }


        @Override
        protected Boolean doInBackground(String... params) {
            Boolean succeeded = false;
            try {

            	cleanActivity();
            	succeeded = true;
            } catch (Exception e) {
                Log.e(DEBUG_TAG, "Failed to add friend", e);
            }

            return succeeded;
        }

    }


	@Override
	public void onLocationChanged(Location loc) {
		// TODO Auto-generated method stub
        //intent.putExtra("Latitud", loc.getLatitude());
        //intent.putExtra("Longitud", loc.getLongitude());     
        //intent.putExtra("Provider", loc.getProvider());
        //previousBestLocation = loc;
        GlobalVar.getInstance().setLatitud(loc.getLatitude());
        GlobalVar.getInstance().setLongitud(loc.getLongitude());
        GlobalVar.getInstance().setProvider(loc.getProvider());
        //sendBroadcast(intent);      
        //Toast.makeText( CreateActaActivity.this, "Ubicacion GPS actualizada (lat " + loc.getLatitude() + " - lon " + loc.getLongitude() + ")", Toast.LENGTH_SHORT ).show();

	}
	@Override
	public void onProviderDisabled(String paramString) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String paramString) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle) {
		// TODO Auto-generated method stub
		
	}

	public static void toggleAlcoholemiaVisibility(){
    	if (editText_AlcoholEnSangre.getVisibility() == View.VISIBLE){
			editText_AlcoholEnSangre.setVisibility(View.GONE);
		}else{
			editText_AlcoholEnSangre.setVisibility(View.VISIBLE);
		}

		if (textView_AlcoholEnSangre.getVisibility() == View.VISIBLE){
			textView_AlcoholEnSangre.setVisibility(View.GONE);
		}else{
			textView_AlcoholEnSangre.setVisibility(View.VISIBLE);
		}
	}

}