package ar.gov.mendoza.PrometeoMuni.core.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import ar.gov.mendoza.PrometeoMuni.core.security.Encriptacion;

public class HelperWebService {
	
	public static final String XML_TAG_STRING = "string";
	
	SharedPreferences mDeviceActasSettings;
	private final Context _ctx;
	private String _sCryptoKey ="";
	private String _sCryptoVector ="vvvvvvvvvvvvvvvv";
	private String _sWSLocation ="http://repatpda.cba.gov.ar";
	
	public HelperWebService(Context pCtx)
	{
		  _ctx = pCtx;
//		   mDeviceActasSettings = _ctx.getSharedPreferences(Preferencias.DEVICE_ACTAS_PREFERENCES ,Context.MODE_PRIVATE);
//		  _sCryptoKey = mDeviceActasSettings.getString(Preferencias.DEVICE_ACTAS_PREF_CRIPTOKEY, "LaLlaveDeLaPuertaSecreta");
//		  _sCryptoVector = mDeviceActasSettings.getString(Preferencias.DEVICE_ACTAS_PREF_CRIPTOVECTOR, "vectorDelTipoDir");
//		  _sWSLocation  = mDeviceActasSettings.getString(Preferencias.DEVICE_ACTAS_PREF_WSLOCATION, "http://repatpda.cba.gov.ar");
	}
	
	public Boolean errorConexion = false;
	private String strActaServiceName = "/ActasService.asmx";
	private int timeout = 30 * 1000;// 5 minutos por (60 segundos = 1 minutos) * (1000 milisegundos = 1 segundo) = 300000 ; no es ->50000 valor anterior;
	
	/**
	 * Parses the XML questions to {@see mQuestions}. They're preloaded into
	 * an XmlPullParser (questionBatch)
	 * 
	 * @param questionBatch
	 *            The incoming XmlPullParser
	 * @throws XmlPullParserException
	 *             Thrown if XML parsing errors
	 * @throws IOException
	 *             Thrown if IO exceptions
	 */
	private String parseXMLQuestionBatch(XmlPullParser questionBatch)
			throws XmlPullParserException, IOException {
		int eventType = -1;

		String sretorno ="";
		// Find Score records from XML
		while (eventType != XmlResourceParser.END_DOCUMENT )//&& !isCancelled())
		{
			if (eventType == XmlResourceParser.START_TAG) {

				// Get the name of the tag (eg questions or question)
				String strName = questionBatch.getName();

				if (strName.equals(XML_TAG_STRING)) {

					//String questionNumber = questionBatch.getAttributeValue(null,XML_TAG_QUESTION_ATTRIBUTE_NUMBER);
					//Integer questionNum = new Integer(questionNumber);
					//String questionText = questionBatch.getAttributeValue(null, XML_TAG_QUESTION_ATTRIBUTE_TEXT);
					//String questionImageUrl = questionBatch.getAttributeValue(null,XML_TAG_QUESTION_ATTRIBUTE_IMAGEURL);

					// Save data to our hashtable
					//mQuestions.put(questionNum, new Question(questionNum,questionText, questionImageUrl));
					
				}
			}
			else if (eventType == XmlResourceParser.TEXT)
			{
				sretorno = questionBatch.getText();
				break;
				
			}
			
			eventType = questionBatch.next();
		}
		
		return sretorno;
	}
	  public String validarNumeroActa(String numeroSerie) throws UnsupportedEncodingException
      {
		  	
		  //Suport_Table TablaConfiguracion = Aplicacion.GetSuportTable(false);
          
		  String numeroActa = "";
          //XmlDocument localxml = new XmlDocument();
          //XmlDocument mainXml = new XmlDocument();


          //preparar el xml
          StringBuilder strb = new StringBuilder();
          strb.append("<root>");
          strb.append("<values>");

          strb.append("<numeroSerie>");//numeroSerie
          strb.append(numeroSerie.trim());
          strb.append("</numeroSerie>");

          strb.append("</values>");
          strb.append("</root>");

          String sxml = strb.toString();
          Encriptacion encriptacion = new Encriptacion(_sCryptoKey);
          String xmlEncriptado = encriptacion.Encripta(sxml);


          //string strURL = supportTable.WSLocation + strActaServiceName + "/validarNumeroActa?param=" + xmlEncriptado;
          //string strURL = Global.GlobalVar.WSLocation + Global.GlobalVar.ActaServiceName + "/validarNumeroActa?param=" + xmlEncriptado;
          String strURL = _sWSLocation + strActaServiceName + "/validarNumeroActa?param=" + xmlEncriptado;
          String xmlSource  = strURL;
          
          String xmlEnc = "";
          
          try
          {
        	    XmlPullParser questionBatch;
	  			try 
	  			{
	  				URL xmlUrl = new URL(xmlSource);
	  				questionBatch = XmlPullParserFactory.newInstance().newPullParser();
	  				InputStream INP = xmlUrl.openStream();
	  				questionBatch.setInput(INP, null);
	  			} catch (XmlPullParserException e1) {
	  				questionBatch = null;
	  				//Log.e(DEBUG_TAG, "Failed to initialize pull parser", e1);
	  			} catch (IOException e) {
	  				questionBatch = null;
	  				//Log.e(DEBUG_TAG,"IO Failure during pull parser initialization", e);
	  			}

	  			// Parse the XML
	  			if (questionBatch != null) 
	  			{
	  				try {
	  					xmlEnc = parseXMLQuestionBatch(questionBatch);
	  				
	  					//result = true;
	  				} catch (XmlPullParserException e) {
	  					//Log.e(DEBUG_TAG, "Pull Parser failure", e);
	  				} catch (IOException e) {
	  					//Log.e(DEBUG_TAG, "IO Exception parsing XML", e);
	  				}
  			    }//end if questionBatch !=null 
        	  
	  			
//              HttpWebRequest webReq = (HttpWebRequest)WebRequest.Create(strURL);
//
//              ServicePointManager.CertificatePolicy = new AcceptAllCerts();
//
//              //if(Global.GlobalVar.Debug==true)
//              //   webReq.Proxy = this.getProxyConfigurado();
//              webReq.Proxy = ConfigurarProxy();
//
//              webReq.AllowWriteStreamBuffering = true;
//              webReq.Timeout = timeout;
//              using (HttpWebResponse webRes = (HttpWebResponse)webReq.GetResponse())
//              {
//                  localxml.Load(webRes.GetResponseStream());
//                  webRes.Close();
//              }
	  			
          }
          catch (Exception ex)
          {
              return numeroActa;
          }


          if (xmlEnc.equals(""))
            {
                return numeroActa;
            }
            else
            {
                xmlEnc = xmlEnc.replace(" ", "+");
            }

          String respuestadesencriptada = encriptacion.decrypt(xmlEnc);

          try
          {
        	  XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        	  XmlPullParser xpp = factory.newPullParser();
        	  factory.setNamespaceAware(true);
        	  
        	  xpp.setInput(new StringReader(respuestadesencriptada));
        	  
        	  xpp.next();
        	  int eventType = xpp.getEventType();
        	  
        	  String tagName ;
        	  
        	  try {       
        	        while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
        	            if (xpp.getEventType()== XmlPullParser.START_TAG)
        	            {
        	            	tagName = xpp.getName();
        	                  if (tagName.equalsIgnoreCase("NUMERO_ACTA")) {
        	                	  numeroActa = xpp.nextText();
        	                  }

        	              }
        	            xpp.next();
        	        }       
        	    } catch (Exception e) {
        	       // Toast.makeText(this, "error!", Toast.LENGTH_LONG).show();
        	    }
          
          } catch (XmlPullParserException e1) {
			numeroActa= "";
          } catch (Exception e) {
			numeroActa = "";
          }
//          string xmlEnc = "";
//          xmlEnc = localxml.InnerText;
//
//          if (xmlEnc.Equals(""))
//          {
//              return numeroActa;
//          }
//          else
//          {
//              xmlEnc = xmlEnc.Replace(" ", "+");
//          }
//
//          string respuestadesencriptada = encriptacion.Desencripta(xmlEnc);
//
//          XmlDocument finalxml = new XmlDocument();
//          finalxml.LoadXml(respuestadesencriptada);
//
//          DataSet LicDS = new DataSet();
//          DataTable licTable = LicDS.Tables.Add();
//
//          licTable.Columns.Add("NUMERO_ACTA", typeof(string));
//
//          foreach (XmlNode nFila in finalxml.SelectNodes("/r/fs/f"))
//          {
//              numeroActa = nFila.SelectSingleNode("nroacta").InnerText;//NUMERO_ACTA
//          }
          return numeroActa;
		  
      }
}
