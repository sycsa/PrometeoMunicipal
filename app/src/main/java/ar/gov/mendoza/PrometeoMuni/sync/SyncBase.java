package ar.gov.mendoza.PrometeoMuni.sync;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

import ar.gov.mendoza.PrometeoMuni.core.exceptions.DeviceActasSynchronizationException;
import ar.gov.mendoza.PrometeoMuni.libraries.SoporteSistemas;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;
/*
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
*/
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
//import org.apache.http.impl.client.DefaultHttpClient;


public class SyncBase {

	protected String serviceNamespace;
	protected String serviceUrl;
	protected String serviceUrlPHP;
	protected String serviceName;
	protected String serviceMethodName;
	protected String serviceSoapAction;
	
	protected String strUrl;
	protected String strUrlPHP;

	protected Context context;
	
	private SoapObject soapRequest = null;
	private SoapSerializationEnvelope soapEnvelope = null;
	private SoapPrimitive  soapRequestResult = null;
	
	private SoporteSistemas soporte;
	public SyncBase(Context context, String serviceMethodName) {		
		
		this.context = context;
		this.serviceNamespace	= "VialMza";// "http://tempuri.org/";
		
		this.serviceName 		= "";// "com.ktksuitelr.ktkmod.awssoapregistralog?wsdl";
		/*
		 try
		 {
			this.serviceName 		= GlobalVar.getInstance().getSuportTable().getServicioWeb();			
		 }
		 catch(Exception e)
		 {
		 }
		*/
		// este URL es el que se va a concatenar con  el nombre del servlet/servicio en cuestion
		this.strUrl = "http://desa.serviciosyconsultoria.com/vialmza/servlet/";
		this.serviceUrl = this.strUrl + this.serviceName;
	
		this.strUrlPHP = "http://stylenet.com.ar/servicios/";
		this.serviceUrlPHP = this.strUrlPHP + this.serviceName;
		
		//this.strUrl = "https://test.serviciosyconsultoria.com/PDAWebService/servicio/";
		
		this.strUrl = "http://desa.serviciosyconsultoria.com/PDAWebService/servicio/";
		
		
		this.strUrl = GlobalVar.getInstance().getSuportTable().getWsLocation();
		this.serviceUrl = this.strUrl + this.serviceMethodName ;

		//GlobalVar.getInstance().getSuportTable().getWsLocation() + "/" + this.serviceName ;
		
		this.serviceMethodName 	= serviceMethodName;
		this.serviceSoapAction	= this.serviceNamespace + this.serviceMethodName;
        
		this.soporte = new SoporteSistemas();
	
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
		this.serviceUrlPHP = this.strUrlPHP + this.serviceName;
		
		setServiceMethodName(serviceMethodName);
	}
	// para cambiar el metodo del webservice
	public void setServiceMethodName(String serviceMethodName) {
		this.serviceMethodName 	= serviceMethodName;
		this.serviceSoapAction	= this.serviceNamespace + this.serviceMethodName;		
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
				        	/*
				        	NodeList nodos = nodes.item(i).getChildNodes();
				        	HashMap<String, Object> childnodes = new HashMap<String, Object>();
				        	for (int j = 0; j < nodos.getLength(); j++) {
				        		Element elemento = (Element) nodos.item(j);
				        		
					        	String valor = elemento.getTextContent(); //nodos.item(j).getTextContent();
					        	String nombre = elemento.getNodeName();//nodos.item(j).getNodeName();
				        	    childnodes.put(nombre, valor);
				        	}
				        	if (nodos.getLength()>0)
				        	{
				        		values.add(childnodes);
				        	} */
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
		Log.d("DeviceActas.Synchronization", this.serviceMethodName);
		
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
	    
	    //esta linea se agrego 2015
	    //la quitamos momentaneamente new MarshalBase64().register(soapEnvelope);
	    
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

	
		/*Log.d("DeviceActas.Synchronization", this.serviceMethodName);
		String params = "Metodo=" + this.serviceMethodName;
		
		
		JSONObject jsonObject   = new JSONObject();
		
		if(values != null) {
			Set<String> keys = values.keySet();
			for(Iterator<String> it = keys.iterator(); it.hasNext();) {
				
				String key = (String)it.next();
				Object value = values.get(key);
				params += "&"+  key + "=" + value; 	
				try {
					jsonObject.put(key,value);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		 
		 String result = null;
		 String path="http://desa.serviciosyconsultoria.com/vialmza/servlet/com.ktksuitelr.ktkmod." + this.serviceMethodName ;
		 URL url;
		 HttpURLConnection conn;
		 try {
			 url = new URL(path);
			 conn = (HttpURLConnection) url.openConnection();
			 conn.setRequestMethod("POST");
			 conn.setConnectTimeout(10000);
			 conn.setReadTimeout(2000);
			 conn.setDoOutput(true);
			 //Agregdo
			 //conn.setDoInput(true);
			 //conn.setRequestProperty("Content-Type", "application/json");
			 //conn.setRequestProperty("Accept", "application/json");
			 //conn.setRequestMethod("POST");
			 
		 byte[] bypes = params.toString().getBytes();
		 //OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		 conn.getOutputStream().write(bypes);
		 //wr.write(jsonObject.toString());
		 //wr.flush();

		 StringBuilder sb = new StringBuilder(); 
		 int HttpResult = conn.getResponseCode(); 
		 if (HttpResult == HttpURLConnection.HTTP_OK) 
		 {
			  BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			    String line = null;  
			    while ((line = br.readLine()) != null) 
			    {  
			        sb.append(line + "\n");  
			    }
			    br.close();
			    result = sb.toString();
			 //
			 //InputStream inStream = conn.getInputStream();
			 //StringBuilder stringBuilder = new StringBuilder();
			 //if (inStream!=null)
			// {
				// byte[] buffer = new byte[1024];
	            // int length;
	             //try {
	            //     while ((length = inStream.read(buffer)) > 0) {
	           //          stringBuilder.append(new String(buffer, 0, length));
	          //       }
	          //   } catch (IOException e) {
	         //        e.printStackTrace();
	        //     }
				 
	          //   result= stringBuilder.toString();
			 //}
			 //
		 }
		 else
		 {
			 System.out.println(conn.getResponseMessage());
		 }
		 
		 } catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
*/
		 /*HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
		    "http://10.0.2.2:8080/Interactive_ICS_Web/datareciever");

		try {
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		    nameValuePairs.add(new BasicNameValuePair("username", "naveed"));
		    nameValuePairs.add(new BasicNameValuePair("password", "12345"));
		    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		    HttpResponse response = httpclient.execute(httppost);
		} catch (ClientProtocolException e) {
		    // TODO Auto-generated catch block
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		}
		*/
		/*
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
		
		if(values != null) {
			Set<String> keys = values.keySet();
			for(Iterator<String> it = keys.iterator(); it.hasNext();) {
				
				String key = (String)it.next();
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
				soapRequest.addProperty(key, value);			
			}
		}
		//SoapSerializationEnvelope
		
	    soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

	    //esta linea se agrego 2015
	    new MarshalBase64().register(soapEnvelope);
	    
	    soapEnvelope.dotNet = true; 
	    soapEnvelope.setOutputSoapObject(soapRequest);
	    
	    int MSG_TIMEOUT = 15000;
	    HttpTransportSE transporte = new HttpTransportSE(serviceUrl,MSG_TIMEOUT);
	 
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
	    */
	    //return result;
	}
	/**
	 * Metodo para enviar jsonObject a un rest
	 */
	public String callServiceRestStyleNet(String strXML) throws DeviceActasSynchronizationException 
	{
		Log.d("DeviceActas.Synchronization", this.serviceMethodName);
		String params = "vACTAXML=" + strXML;
		//params+="&param=" + strJSON; 
		InputStream inputStream = null;

		 String result = null;
		 String path ="http://geerconsultora.com.ar/servicios/wpregistraacta.php";
		 URL url;
		 HttpURLConnection conn;
		 try {
			 url = new URL(path);
			 conn = (HttpURLConnection) url.openConnection();
			 conn.setRequestMethod("POST");
			 conn.setConnectTimeout(10000);
			 conn.setReadTimeout(10000);
			 conn.setDoOutput(true);
			 
			 /*agregado*/
			 //conn.setDoInput(true);
			 //conn.setRequestProperty("Content-Type", "application/json");
			 //conn.setRequestProperty("Accept", "application/json");
			 //conn.setRequestMethod("POST");

		
	    
		 byte[] bypes = params.getBytes();
		 //OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		 conn.getOutputStream().write(bypes);
		 //wr.write(jsonObject.toString());
		 //wr.flush();

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
			 /*
			 InputStream inStream = conn.getInputStream();
			 StringBuilder stringBuilder = new StringBuilder();
			 if (inStream!=null)
			 {
				 byte[] buffer = new byte[1024];
	             int length;
	             try {
	                 while ((length = inStream.read(buffer)) > 0) {
	                     stringBuilder.append(new String(buffer, 0, length));
	                 }
	             } catch (IOException e) {
	                 e.printStackTrace();
	             }
				 
	             result= stringBuilder.toString();
			 }
			 */
			    result="<root><filas><fila>ok</fila></filas></root>";
		 }
		 else
		 {
			 System.out.println(conn.getResponseMessage());
		 }
		 
		 } catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		 /*HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
		    "http://10.0.2.2:8080/Interactive_ICS_Web/datareciever");

		try {
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		    nameValuePairs.add(new BasicNameValuePair("username", "naveed"));
		    nameValuePairs.add(new BasicNameValuePair("password", "12345"));
		    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		    HttpResponse response = httpclient.execute(httppost);
		} catch (ClientProtocolException e) {
		    // TODO Auto-generated catch block
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		}
		*/
		/*
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
		
		if(values != null) {
			Set<String> keys = values.keySet();
			for(Iterator<String> it = keys.iterator(); it.hasNext();) {
				
				String key = (String)it.next();
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
				soapRequest.addProperty(key, value);			
			}
		}
		//SoapSerializationEnvelope
		
	    soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

	    //esta linea se agrego 2015
	    new MarshalBase64().register(soapEnvelope);
	    
	    soapEnvelope.dotNet = true; 
	    soapEnvelope.setOutputSoapObject(soapRequest);
	    
	    int MSG_TIMEOUT = 15000;
	    HttpTransportSE transporte = new HttpTransportSE(serviceUrl,MSG_TIMEOUT);
	 
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
	    */
	    return result;
	}
/*
 * Servicio para conectarse a stylenet.com.ar
 * */

	public String callServiceRestGrabarFoto(String sFoto, Bitmap btmFoto)
	{

		HttpsTrustManager.allowAllSSL();
		String result = null;
		String path = this.strUrl + "GrabarFoto";
		URL url;
		HttpURLConnection conn;

		try{

			url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(15000);
			conn.setReadTimeout(10000);
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			/*
			* Faltan algunos configuraciones
			* */
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			btmFoto.compress(CompressFormat.JPEG, 100, bos);
			byte[] data = bos.toByteArray();
			ByteArrayBody bab = new ByteArrayBody(data, sFoto );
			MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			reqEntity.addPart("file", bab);

			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.addRequestProperty("Content-length", reqEntity.getContentLength()+"");
			conn.addRequestProperty(reqEntity.getContentType().getName(), reqEntity.getContentType().getValue());
			//os
			OutputStream out = conn.getOutputStream();
			reqEntity.writeTo(conn.getOutputStream());
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
		/*
		try {
			HttpClient httpClient = new DefaultHttpClient();
	        HttpPost postRequest = new HttpPost(this.strUrl + "GrabarFoto");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            btmFoto.compress(CompressFormat.JPEG, 100, bos);
            byte[] data = bos.toByteArray();
            ByteArrayBody bab = new ByteArrayBody(data, sFoto );
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("file", bab);
            postRequest.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(postRequest);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent(), "UTF-8"));
            String sResponse;
            StringBuilder s = new StringBuilder();
 
            while ((sResponse = reader.readLine()) != null) {
                s = s.append(sResponse);
            }
             
             return s.toString();
            
            
        } catch (Exception e) {
            Log.e(e.getClass().getName(), e.getMessage());
            return null;
        }
        */

		
	}
/*
* contenido de sincronizarFotos
*
		try {

			HttpClient httpClient = new DefaultHttpClient();
	        HttpPost postRequest = new HttpPost(this.strUrl + "GrabarFoto");
	        //http://192.168.1.104/PDAWebService/servicio/PruebaImagen");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            btmFoto.compress(CompressFormat.JPEG, 100, bos);
            byte[] data = bos.toByteArray();
            ByteArrayBody bab = new ByteArrayBody(data, sFoto );
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("file", bab);
            //reqEntity.addPart("photoCaption", new StringBody("sfsdfsdf"));
            postRequest.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(postRequest);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent(), "UTF-8"));
            String sResponse;
            StringBuilder s = new StringBuilder();

            while ((sResponse = reader.readLine()) != null) {
                s = s.append(sResponse);
            }

             return s.toString();


        } catch (Exception e) {
            Log.e(e.getClass().getName(), e.getMessage());
            return null;
        }

* */

	/**
	 * Metodo para enviar un XML a un rest
	 */
	public String callServiceRestGenerico(HashMap<String, Object> values) throws DeviceActasSynchronizationException 
	{
		
		HttpsTrustManager.allowAllSSL();

		Log.d("DeviceActas.Synchronization", this.serviceMethodName);
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
		 String path = "http://2112soft.com.ar/desa/sav/pda/setuppda/356939100756724";//this.serviceUrl; //"http://desa.serviciosyconsultoria.com/vialmza/servlet/com.ktksuitelr.ktkmod.wpregistraacta";
		 URL url;
		 HttpURLConnection conn; //
		 
		 try {
			 url = new URL(path);
			 conn = (HttpURLConnection) url.openConnection();//conn = (HttpsURLConnection) url.openConnection();
			 conn.setRequestMethod("POST");
			 conn.setConnectTimeout(10000);
			 conn.setReadTimeout(10000);
			 conn.setUseCaches(false);
			 conn.setDoInput(true);
			 conn.setDoOutput(true);
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
/*
 * Metodo para enviar un para de variables GET y obtener un dato
 * */
	/**
	 * Metodo para enviar un XML a un rest
	 */
	public String callServiceRestGetGenerico(HashMap<String, Object> values) throws DeviceActasSynchronizationException 
	{
		
		HttpsTrustManager.allowAllSSL();

		
		Log.d("DeviceActas.Synchronization", this.serviceMethodName);
		String params = "";
		String soloBody="";
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
		 String path = this.serviceUrl;// + params; //"http://desa.serviciosyconsultoria.com/vialmza/servlet/com.ktksuitelr.ktkmod.wpregistraacta";
		 URL url;


		 HttpURLConnection conn; //
		 
		 try {
			 url = new URL(path);
			 /*SSLSocketFactory NoSSLv3Factory = null;
				try {
					NoSSLv3Factory = new NoSSLv3SocketFactory(url);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			 HttpsURLConnection.setDefaultSSLSocketFactory(NoSSLv3Factory);*/

			 
			 conn = (HttpURLConnection) url.openConnection();//conn = (HttpsURLConnection) url.openConnection();
			 conn.setRequestMethod("GET");
			 conn.setConnectTimeout(10000);
			 conn.setReadTimeout(10000);
			 conn.setUseCaches(false);
			 conn.setDoInput(true);
			 //conn.setDoOutput(true);
			 
			 /*byte[] bypes = soloBody.getBytes();
			  conn.addRequestProperty("Content-Type", "text/plain");
			   DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			   out.write(bypes);
			   out.close();
	           */ 
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


/**/	
	public String callServiceRestGenericoPHP(HashMap<String, Object> values) throws DeviceActasSynchronizationException 
	{
		
		HttpsTrustManager.allowAllSSL();
		Log.d("DeviceActas.Synchronization", this.serviceMethodName);
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

	
	/**
	 * OBSOLETO Este metodo no se usa, Antes Andres lo necesitaba para su servlet de Genexus
	 * 
	 */
	@Deprecated
	public String callServiceRest(String strXML) throws DeviceActasSynchronizationException 
	{   
		Log.d("DeviceActas.Synchronization", this.serviceMethodName);
		String params = "vACTAXML=" + strXML;
 
		InputStream inputStream = null;

		 String result = null;
		 String path;
		 		path ="http://desa.serviciosyconsultoria.com/vialmza/servlet/com.ktksuitelr.ktkmod.wpregistraacta";
		 URL url;
		 HttpURLConnection conn;
		 try {
			 url = new URL(path);
			 conn = (HttpURLConnection) url.openConnection();
			 conn.setRequestMethod("POST");
			 conn.setConnectTimeout(10000);
			 conn.setReadTimeout(2000);
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
		}
			 catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}

	    return result;
	}

	public static boolean checkConnectivity(Context context) {
		
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		
		return activeNetwork.isConnectedOrConnecting();
	}

	protected String encodeValue(String pValue)
	{
	       String _value = "";
	       try
	       {
	       _value  = android.text.Html.escapeHtml(pValue);
	       } catch(Exception e)
	       { _value = pValue; }
	       
	       return _value;
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


}