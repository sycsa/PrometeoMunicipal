package ar.gov.mendoza.PrometeoMuni.core.security;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import ar.gov.mendoza.PrometeoMuni.core.util.Tools;

//import android.widget.Toolbar;

public class HelperEncriptador {
 
    public static final String TAG = "DeviceActas";
 
    private static String TRANSFORMATION = "AES/CBC/PKCS7Padding";
    private static String ALGORITHM = "AES";
    private static String DIGEST = "MD5";
     
    private static Cipher _cipher;
    private static SecretKey _password ;
    private static IvParameterSpec _IVParamSpec;
     
    //16-byte private key
    private static byte[] IV =new byte[] { 57, 95, 19, 79, (byte) 252, 50, 90, (byte) 150, 116, 70, 15, 97, 10, 86, (byte) 192, 97 };//ABCDEFGH //"vectorDelTipoDir".getBytes();
    //aSecreta

    
    
    
    
    /**
     Constructor
     @password Public key
      
    */
    public HelperEncriptador(String password) {
 
        try {
            //Encode digest
           // MessageDigest digest;           
           // digest = MessageDigest.getInstance(ALGORITHM);            
            //_password = new SecretKeySpec(digest.digest(password.getBytes()), ALGORITHM);
        	
        	//byte[] passwordBytes = Tools.convertExtendedAscii(password);
            _password = new SecretKeySpec(password.getBytes(), ALGORITHM);
        	//_password = new SecretKeySpec(passwordBytes, ALGORITHM);
            
            //Initialize objects
            _cipher = Cipher.getInstance(TRANSFORMATION);//Cipher.getInstance(TRANSFORMATION);
            _IVParamSpec = new IvParameterSpec(IV);
            
             
        } catch (NoSuchAlgorithmException e) {
           // Log.e(TAG, "No such algorithm " + ALGORITHM, e);
        } catch (NoSuchPaddingException e) {
           // Log.e(TAG, "No such padding PKCS7", e);
        }              
    }

    public HelperEncriptador(byte[] password) {
    	 
        try {
            //Encode digest
           // MessageDigest digest;           
           // digest = MessageDigest.getInstance(ALGORITHM);            
            //_password = new SecretKeySpec(digest.digest(password.getBytes()), ALGORITHM);
        	
        	//byte[] passwordBytes = Tools.convertExtendedAscii(password);
            _password = new SecretKeySpec(password, ALGORITHM);
        	//_password = new SecretKeySpec(passwordBytes, ALGORITHM);
            
            //Initialize objects
            _cipher = Cipher.getInstance(TRANSFORMATION);//Cipher.getInstance(TRANSFORMATION);
            _IVParamSpec = new IvParameterSpec(IV);
            
             
        } catch (NoSuchAlgorithmException e) {
           // Log.e(TAG, "No such algorithm " + ALGORITHM, e);
        } catch (NoSuchPaddingException e) {
           // Log.e(TAG, "No such padding PKCS7", e);
        }              
    }
    /**
    Encryptor.
 
    @text String to be encrypted
    @return Base64 encrypted text
     * @throws UnsupportedEncodingException
 
    */
    public String Encripta(String text) throws UnsupportedEncodingException
    {
    	//byte[] textBytes = Tools.convertExtendedAscii(text);
    	//return encrypt(textBytes);
    	return encrypt(Tools.getBytesForCipher(text));//.getBytes());
    }
    
    public String crypt(String text, String ppp) throws UnsupportedEncodingException {
        
    	
        byte[] encryptedData;
         
        try {
        	_IVParamSpec = new IvParameterSpec(ppp.getBytes());
            _cipher.init(Cipher.ENCRYPT_MODE, _password, _IVParamSpec);
            //byte[] textBytes = Tools.convertExtendedAscii(text);
            //encryptedData = _cipher.doFinal(textBytes);
            
            //encryptedData = _cipher.doFinal(text.getBytes());
            encryptedData = _cipher.doFinal(Tools.getBytesForCipher(text));
             
        } catch (InvalidKeyException e) {
           // Log.e(TAG, "Invalid key  (invalid encoding, wrong length, uninitialized, etc).", e);
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            //Log.e(TAG, "Invalid or inappropriate algorithm parameters for " + ALGORITHM, e);
            return null;
        } catch (IllegalBlockSizeException e) {
          //  Log.e(TAG, "The length of data provided to a block cipher is incorrect", e);
            return null;
        } catch (BadPaddingException e) {
          //  Log.e(TAG, "The input data but the data is not padded properly.", e);
            return null;
        }               
        int flags = Base64.NO_WRAP ;//| Base64.URL_SAFE;//Base64.DEFAULT
        return Base64.encodeToString(encryptedData,flags);
         
    }
    
 public String crypt(String text, byte[] ppp) throws UnsupportedEncodingException {
        
    	
        byte[] encryptedData;
         
        try {
        	_IVParamSpec = new IvParameterSpec(ppp);
            _cipher.init(Cipher.ENCRYPT_MODE, _password, _IVParamSpec);
            //byte[] textBytes = Tools.convertExtendedAscii(text);
            //encryptedData = _cipher.doFinal(textBytes);
            
            //encryptedData = _cipher.doFinal(text.getBytes());
            encryptedData = _cipher.doFinal(Tools.getBytesForCipher(text));
             
        } catch (InvalidKeyException e) {
           // Log.e(TAG, "Invalid key  (invalid encoding, wrong length, uninitialized, etc).", e);
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            //Log.e(TAG, "Invalid or inappropriate algorithm parameters for " + ALGORITHM, e);
            return null;
        } catch (IllegalBlockSizeException e) {
          //  Log.e(TAG, "The length of data provided to a block cipher is incorrect", e);
            return null;
        } catch (BadPaddingException e) {
          //  Log.e(TAG, "The input data but the data is not padded properly.", e);
            return null;
        }               
        int flags = Base64.NO_WRAP ;//| Base64.URL_SAFE;//Base64.DEFAULT
        return Base64.encodeToString(encryptedData,flags);
         
    }
    public String encrypt(byte[] text) throws UnsupportedEncodingException {
         
        byte[] encryptedData;
         
        try {
             
            _cipher.init(Cipher.ENCRYPT_MODE, _password, _IVParamSpec);
            encryptedData = _cipher.doFinal(text);
             
        } catch (InvalidKeyException e) {
           // Log.e(TAG, "Invalid key  (invalid encoding, wrong length, uninitialized, etc).", e);
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            //Log.e(TAG, "Invalid or inappropriate algorithm parameters for " + ALGORITHM, e);
            return null;
        } catch (IllegalBlockSizeException e) {
          //  Log.e(TAG, "The length of data provided to a block cipher is incorrect", e);
            return null;
        } catch (BadPaddingException e) {
          //  Log.e(TAG, "The input data but the data is not padded properly.", e);
            return null;
        }               
        int flags = Base64.NO_WRAP ;//| Base64.URL_SAFE;//Base64.DEFAULT
        return Base64.encodeToString(encryptedData,flags);
         
    }
     
    /**
    Decryptor.
 
    @text Base64 string to be decrypted
    @return decrypted text
 
    */   
    public String decrypt(String text) {
 
        try {
            _cipher.init(Cipher.DECRYPT_MODE, _password, _IVParamSpec);
             
            //byte[] textBytes = Tools.convertExtendedAscii(text);
            //byte[] decodedValue = Base64.decode(textBytes, Base64.DEFAULT);
            
            //byte[] decodedValue = Base64.decode(text.getBytes(), Base64.DEFAULT);
            byte[] decodedValue = Base64.decode(Tools.getBytesForCipher(text), Base64.DEFAULT);
            byte[] decryptedVal = _cipher.doFinal(decodedValue);
            return new String(decryptedVal);
             
             
        } catch (InvalidKeyException e) {
            Log.e(TAG, "Invalid key  (invalid encoding, wrong length, uninitialized, etc).", e);
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            Log.e(TAG, "Invalid or inappropriate algorithm parameters for " + ALGORITHM, e);
            return null;
        } catch (IllegalBlockSizeException e) {
            Log.e(TAG, "The length of data provided to a block cipher is incorrect", e);
            return null;
        } catch (BadPaddingException e) {
            Log.e(TAG, "The input data but the data is not padded properly.", e);
            return null;
        }               
         
    }
    
    public String uncrypt(String text, String ppp) {
   	 
        try {
        	
        	//byte[] pppBytes = Tools.convertExtendedAscii(ppp);
        	//_IVParamSpec = new IvParameterSpec(pppBytes);
        	
        	//_IVParamSpec = new IvParameterSpec(ppp.getBytes());
        	_IVParamSpec = new IvParameterSpec(Tools.getBytesForCipher(ppp));
            _cipher.init(Cipher.DECRYPT_MODE, _password, _IVParamSpec);
             
            //byte[] textBytes = Tools.convertExtendedAscii(text);
            //byte[] decodedValue = Base64.decode(textBytes, Base64.DEFAULT);
            
            //byte[] decodedValue = Base64.decode(text.getBytes(), Base64.DEFAULT);
            byte[] decodedValue = Base64.decode(Tools.getBytesForCipher(text), Base64.DEFAULT);
            byte[] decryptedVal = _cipher.doFinal(decodedValue);
            return new String(decryptedVal);
             
             
        } catch (InvalidKeyException e) {
            Log.e(TAG, "Invalid key  (invalid encoding, wrong length, uninitialized, etc).", e);
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            Log.e(TAG, "Invalid or inappropriate algorithm parameters for " + ALGORITHM, e);
            return null;
        } catch (IllegalBlockSizeException e) {
            Log.e(TAG, "The length of data provided to a block cipher is incorrect", e);
            return null;
        } catch (BadPaddingException e) {
            Log.e(TAG, "The input data but the data is not padded properly.", e);
            return null;
        }               
         
    }
    
    public String uncrypt(String text, byte[] ppp) {
      	 
        try {
        	
        	//byte[] pppBytes = Tools.convertExtendedAscii(ppp);
        	//_IVParamSpec = new IvParameterSpec(pppBytes);
        	
        	//_IVParamSpec = new IvParameterSpec(ppp.getBytes());
        	_IVParamSpec = new IvParameterSpec(ppp);
            _cipher.init(Cipher.DECRYPT_MODE, _password, _IVParamSpec);
             
            //byte[] textBytes = Tools.convertExtendedAscii(text);
            //byte[] decodedValue = Base64.decode(textBytes, Base64.DEFAULT);
            
            //byte[] decodedValue = Base64.decode(text.getBytes(), Base64.DEFAULT);
            byte[] decodedValue = Base64.decode(Tools.getBytesForCipher(text), Base64.DEFAULT);
            byte[] decryptedVal = _cipher.doFinal(decodedValue);
            return new String(decryptedVal);
             
             
        } catch (InvalidKeyException e) {
            Log.e(TAG, "Invalid key  (invalid encoding, wrong length, uninitialized, etc).", e);
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            Log.e(TAG, "Invalid or inappropriate algorithm parameters for " + ALGORITHM, e);
            return null;
        } catch (IllegalBlockSizeException e) {
            Log.e(TAG, "The length of data provided to a block cipher is incorrect", e);
            return null;
        } catch (BadPaddingException e) {
            Log.e(TAG, "The input data but the data is not padded properly.", e);
            return null;
        }               
         
    }
    
}