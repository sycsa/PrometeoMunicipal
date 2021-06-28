package ar.gov.mendoza.PrometeoMuni.sync;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import ar.gov.mendoza.PrometeoMuni.core.exceptions.DeviceActasSynchronizationException;
import ar.gov.mendoza.PrometeoMuni.libraries.SoporteSistemas;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;


public class BaseRestSync {

	protected String serviceUrl;
	protected String serviceName;
	protected String serviceMethodName;
	protected String strUrl;
	protected String serviceNamespace;
	protected String serviceSoapAction;
	protected String serviceUrlPHP;
	protected String strUrlPHP;

	protected Context context;
	private SoporteSistemas soporte;

	private SoapObject soapRequest = null;
	private SoapSerializationEnvelope soapEnvelope = null;
	private SoapPrimitive  soapRequestResult = null;

	private final String user = "CALLER_PDA1";
	private final String password = "peterpan2112";

	private final String input = user + ":" + password;

	private final String basicAuth = "Basic " + Base64.encodeToString(input.getBytes(), Base64.NO_WRAP);



	public BaseRestSync(Context context, String serviceMethodName) {
		
		this.context = context;
		this.strUrl = GlobalVar.getInstance().getSuportTable().getWsLocation();
		this.serviceName = "pda";
		this.serviceUrl = this.strUrl + "/"+ this.serviceName ;
		this.serviceMethodName 	= serviceMethodName;
		this.serviceUrlPHP = this.strUrlPHP + this.serviceName;
		this.soporte = new SoporteSistemas();

	}
	public void setServiceMethodName(String serviceMethodName) {
		this.serviceMethodName 	= serviceMethodName;
	}


	//public String callServiceRestPostGenerico(HashMap<String, Object> values) throws DeviceActasSynchronizationException
	public String callServiceRestPostGenerico(String idsActualizaciones) throws DeviceActasSynchronizationException

	{
		
		HttpsTrustManager.allowAllSSL();

		String soloBody="";

		 String result = null;
		 String path = this.serviceUrl + "/" + this.serviceMethodName + "/" + GlobalVar.getInstance().getImei();
		 URL url;
		 HttpURLConnection conn;
		 
		 try {
			 url = new URL(path);
			 conn = (HttpURLConnection) url.openConnection();
			 conn.setRequestMethod("PUT");
			 conn.setConnectTimeout(10000);
			 conn.setReadTimeout(10000);
			 conn.setUseCaches(false);
			 conn.setDoInput(true);
			 conn.setDoOutput(true);
			 conn.setRequestProperty("actualizacion", idsActualizaciones);
			 conn.setRequestProperty("Authorization", basicAuth);
			 byte[] bypes = soloBody.getBytes();
			  conn.addRequestProperty("Content-Type", "text/plain");
			   DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			   out.write(bypes);
			   out.close();
	            
			 StringBuilder sb = new StringBuilder(); 
			 int HttpResult = conn.getResponseCode(); 
			 if (HttpResult == HttpURLConnection.HTTP_OK) 
			 {
				    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
				    String line = null;  
				    while ((line = br.readLine()) != null) 
				    {  
				        sb.append(line + "\n");  
				    }
				    br.close();
				    result = sb.toString();
			 }
			 else
			 {
				 //System.out.println();
				 String sError = conn.getResponseMessage();
				 Toast.makeText(context,sError,Toast.LENGTH_LONG).show();  
			 }
		 
		 } catch (ProtocolException e) {
			e.printStackTrace();
		 } catch (MalformedURLException e1) {
			e1.printStackTrace();
		 } catch (IOException e) {
			e.printStackTrace();
		 }

		    return result;
	}


	public String callServiceRestGetGenerico(HashMap<String, Object> values) throws DeviceActasSynchronizationException
	{
		
		HttpsTrustManager.allowAllSSL();

		String params = "";
		//String soloBody="";
		if(values != null) 
		{
			Set<String> keys = values.keySet();
			for(Iterator<String> it = keys.iterator(); it.hasNext();) {
				
				String key = it.next();
				Object value = values.get(key);
				params = params + (params.equals("")?"/":"/") + key + "/" + value;
				
			}
		}

		 String result = null;
		 String imei = GlobalVar.getInstance().getImei();
		 String path = this.serviceUrl + "/" +  this.serviceMethodName + "/" + imei;//"356939100756724";// + params;
		 URL url;


		 HttpURLConnection conn; //
		 
		 try {
			 url = new URL(path);

			 conn = (HttpURLConnection) url.openConnection();//conn = (HttpsURLConnection) url.openConnection();
			 conn.setRequestMethod("GET");
			 conn.setConnectTimeout(10000);
			 conn.setReadTimeout(10000);
			 conn.setUseCaches(false);
			 conn.setDoInput(true);

			 switch (this.serviceMethodName){
                 case "login":
                     conn.setRequestProperty("username", values.get("username").toString());
                     conn.setRequestProperty("password", values.get("password").toString());
                     break;
                 case "sync":
                     conn.setRequestProperty("params", values.get("params").toString());
                     conn.setRequestProperty("acta", values.get("acta").toString());
                     conn.setRequestProperty("user", values.get("user").toString());
                     break;
				 case "getacta":
					 conn.setRequestProperty("acta", values.get("datoBusqueda").toString());
					 break;
				 case "getactaverificacion":
					 conn.setRequestProperty("acta", values.get("acta").toString());
					 break;
				 default:
             }

             conn.setRequestProperty ("Authorization", basicAuth);

				 StringBuilder sb = new StringBuilder();
				 int HttpResult = conn.getResponseCode();
				 if (HttpResult == HttpURLConnection.HTTP_OK)
				 {
					 BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
					 String line = null;
					 while ((line = br.readLine()) != null)
					 {
						 sb.append(line + "\n");
					 }
					 br.close();
					 result = sb.toString();
				 }
				 else
				 {
					 String sError = conn.getResponseMessage();
 					 Toast.makeText(context,sError,Toast.LENGTH_LONG).show();
				 }

		 } catch (ProtocolException e) {
			e.printStackTrace();
		 } catch (MalformedURLException e1) {
			e1.printStackTrace();
		 } catch (IOException e) {
			e.printStackTrace();
		 }

	    return result;
	}




	public static boolean checkConnectivity(Context context) {
		
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		
		return activeNetwork.isConnectedOrConnecting();
	}

	protected String encodeValue(String pValue){
	       String _value = "";
	       try
	       {
	       _value  = android.text.Html.escapeHtml(pValue);
	       } catch(Exception e)
	       { _value = pValue; }
	       
	       return _value;
	}

	protected String Proteger(String valor)
	{
		String svalor = "";
		try
		{
			svalor = soporte.Proteger(valor);

		}
		catch(Exception exception)
		{

		}
		return svalor;
	}

	protected String ProcesarRetorno(String valor)
	{
		String svalor = "";
		try
		{
			svalor = soporte.DesProteger(valor);//

		}
		catch(Exception exception)
		{

		}
		return svalor;
	}


	// para cambiar el servicio y el metodo del webservice
	public void setServiceNameMethodName(String serviceName) {
		String serviceMethodName = "Execute";
		this.serviceName = serviceName;
		this.serviceUrl = this.strUrl + this.serviceName;

		setServiceMethodName(serviceMethodName);
	}

	protected ArrayList<HashMap<String, Object>> CargarListaValores(String xmlAProcesar,String Patron)
	{
		ArrayList<HashMap<String, Object>> values = new ArrayList<HashMap<String, Object>>();

		if( xmlAProcesar !="")
		{
			String text =  xmlAProcesar;//"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
			try {

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setNamespaceAware(true);
				DocumentBuilder builder;
				Document doc = null;

				builder = factory.newDocumentBuilder();

				doc = builder.parse(new InputSource(new StringReader(text)));


				// Create a XPathFactory
				XPathFactory xFactory = XPathFactory.newInstance();

				// Create a XPath object
				XPath xpath = xFactory.newXPath();
				XPathExpression expr = null;
				String xpathPattern = null;
				xpathPattern = Patron;
				expr = xpath.compile(xpathPattern);

				try
				{
					Object result = expr.evaluate(doc, XPathConstants.NODESET);
					NodeList nodes = (NodeList) result;
					for (int i = 0; i < nodes.getLength(); i++) {
						//items.add(nodes.item(i).getNodeValue());
						Node nodo = nodes.item(i);  // fila

						if(nodo.hasChildNodes()  || nodo.getNodeType()!=3)
						{
							System.out.println(nodo.getNodeName() + " : " +nodo.getTextContent());
							Log.e("Elemento",nodo.getNodeName() + " : " +nodo.getTextContent());
							NodeList el=nodo.getChildNodes();
							if (el.getLength()>0)
							{
								HashMap<String, Object> childnodes = new HashMap<String, Object>();
								for(int j=0;j<el.getLength();j++)
								{
									Node nodohijo = el.item(j);
									System.out.println(nodohijo.getNodeName() + " : " +nodohijo.getTextContent());
									Log.e("ElementoHijo", nodohijo.getNodeName() + " : " +nodohijo.getTextContent());
									String valor = nodohijo.getTextContent(); //nodos.item(j).getTextContent();
									String nombre = nodohijo.getNodeName();//nodos.item(j).getNodeName();
									childnodes.put(nombre, valor);
								}
								values.add(childnodes);
							}
						}
					}
				}
				catch (XPathExpressionException xee) {
				}


			} catch (Exception e) {

			}

		}

		return values;


	}

	protected ArrayList<HashMap<String, Object>> CargarValores(String xmlAProcesar,String Patron)
	{
		ArrayList<HashMap<String, Object>> values = new ArrayList<HashMap<String, Object>>();

		if( xmlAProcesar !="")
		{
			String text =  xmlAProcesar;//"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
			try {

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setNamespaceAware(true);
				DocumentBuilder builder;
				Document doc = null;

				builder = factory.newDocumentBuilder();

				doc = builder.parse(new InputSource(new StringReader(text)));


				// Create a XPathFactory
				XPathFactory xFactory = XPathFactory.newInstance();

				// Create a XPath object
				XPath xpath = xFactory.newXPath();
				XPathExpression expr = null;
				String xpathPattern = null;
				xpathPattern = Patron;
				expr = xpath.compile(xpathPattern);

				try
				{
					Object result = expr.evaluate(doc, XPathConstants.NODESET);
					NodeList nodes = (NodeList) result;

					for (int i = 0; i < nodes.getLength(); i++) {
						//items.add(nodes.item(i).getNodeValue());
						NodeList nodos = nodes.item(i).getChildNodes();
						HashMap<String, Object> childnodes = new HashMap<String, Object>();
						for (int j = 0; j < nodos.getLength(); j++) {
							String valor = nodos.item(j).getTextContent();
							String nombre = nodos.item(j).getNodeName();
							childnodes.put(nombre, valor);
						}
						if (nodos.getLength()>0)
						{
							values.add(childnodes);
						}
					}
				}
				catch (XPathExpressionException xee) {
					//Log.d(TAG, "used xpathPattern: " + xpathPattern);
				}


			} catch (Exception e) {

				//e.printStackTrace();
			}

		}

		return values;


	}
	public String callService(HashMap<String, Object> values) throws DeviceActasSynchronizationException
	{
		Log.d("DeviceActas.Sync", this.serviceMethodName);

		String result = null;
		//SoapObject
		try
		{
			soapRequest = new SoapObject(serviceNamespace, serviceMethodName);
		}
		catch(Exception ex)
		{
			String valor = ex.getMessage();
			String buf = valor;
		}

		if(values != null)
		{
			Set<String> keys = values.keySet();
			for(Iterator<String> it = keys.iterator(); it.hasNext();) {

				String key = it.next();
				Object value = values.get(key);
				if(this.serviceMethodName.equals("GrabarFoto") && key.equals("param1"))
				{
					PropertyInfo imagePI = new PropertyInfo();
					imagePI.setName("param1");
					imagePI.setValue(value);
					imagePI.setType(byte[].class);
					soapRequest.addProperty(imagePI);
				}
				else
				{
					PropertyInfo paramMethod = new PropertyInfo();
					paramMethod.setName(key);
					paramMethod.setValue(value);
					paramMethod.setType(String.class);
					soapRequest.addProperty(paramMethod);
					//soapRequest.addProperty(key, value);
				}
			}
		}
		//SoapSerializationEnvelope

		soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		soapEnvelope.dotNet = true;

		//soapEnvelope.dotNet = true;
		soapEnvelope.setOutputSoapObject(soapRequest);
		//http://desa.serviciosyconsultoria.com/vialmza/servlet/com.ktksuitelr.ktkmod.awssoapregistralog
		//HttpTransportSE transporte = new HttpTransportSE(serviceUrl);
		//HttpTransportSE transporte = new HttpTransportSE("http://desa.serviciosyconsultoria.com/vialmza/servlet/com.ktksuitelr.ktkmod.awssoapregistralog");
		HttpTransportSE transporte = new HttpTransportSE(serviceUrl);

		try {
			transporte.call(this.serviceSoapAction, soapEnvelope);
			//SoapPrimitive
			soapRequestResult = (SoapPrimitive)soapEnvelope.getResponse();

			if(soapRequestResult != null) {
				result = soapRequestResult.toString();
				Log.v("DeviceActas.Sync", result);
			}
		}
		catch(Exception e) {
			throw new DeviceActasSynchronizationException(e.getMessage());
		}

		return result;

	}

	protected String buscarCadenaKLK(String encriptHTML)
	{   String retorno= encriptHTML;
		int pos = encriptHTML.indexOf("<html");
		if (pos>0)
		{
			retorno = encriptHTML.substring(0, pos);
		}

		return retorno;
	}
	public String callServiceRestGenericoPHP(HashMap<String, Object> values) throws DeviceActasSynchronizationException
	{

		HttpsTrustManager.allowAllSSL();
		Log.d("DeviceActas.Sync", this.serviceMethodName);
		String params = "";
		String soloBody="";
		if(values != null)
		{
			Set<String> keys = values.keySet();
			for(Iterator<String> it = keys.iterator(); it.hasNext();) {

				String key = it.next();
				Object value = values.get(key);
				if (key.equals("param"))
				{
					soloBody = value.toString();
				}

				params = params + (params.equals("")?"":"&") + key + "=" + value;
			}
		}

		String result = null;
		String path = this.serviceUrlPHP;

		URL url;
		HttpURLConnection conn;
		try {
			url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			byte[] bypes = params.getBytes();
			conn.getOutputStream().write(bypes);

			StringBuilder sb = new StringBuilder();
			int HttpResult = conn.getResponseCode();
			if (HttpResult == HttpURLConnection.HTTP_OK)
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
				String line = null;
				while ((line = br.readLine()) != null)
				{
					sb.append(line + "\n");
				}
				br.close();
				result = sb.toString();

			}
			else
			{
				System.out.println(conn.getResponseMessage());
			}

		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	//public String callServiceRestGrabarFoto(String sFoto, Bitmap btmFoto)
    public String callServiceRestGrabarFoto(HashMap<String, Object> values)
	{

		HttpsTrustManager.allowAllSSL();
		String result = null;
        String path = this.serviceUrl + "/" + this.serviceMethodName + "/" + GlobalVar.getInstance().getImei();

        //String path = this.strUrl + "GrabarFoto";
		URL url;
		HttpURLConnection conn;

		try{

			url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("PUT");
			conn.setConnectTimeout(15000);
			conn.setReadTimeout(10000);
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty ("Authorization", basicAuth);

            JSONObject fotoJSON = new JSONObject();
            fotoJSON.put("image", values.get("foto").toString());
            fotoJSON.put("nro_acta", values.get("acta").toString());


			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.writeBytes(fotoJSON.toString());
			out.flush();
			out.close();

			// esta linea no la conocia
			conn.connect();

			StringBuilder sb = new StringBuilder();
			int HttpResult = conn.getResponseCode();
			if (HttpResult == HttpURLConnection.HTTP_OK)
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
				String line = null;
				while ((line = br.readLine()) != null)
				{
					sb.append(line + "\n");
				}
				br.close();
				result = sb.toString();
			}
			else
			{
				String sError = conn.getResponseMessage();
				Toast.makeText(context,sError,Toast.LENGTH_LONG).show();
			}
		}catch (ProtocolException e) {
			e.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e){
			//e.printStackTrace();
			Log.e(e.getClass().getName(), e.getMessage());
		}
		return result;
	}




}