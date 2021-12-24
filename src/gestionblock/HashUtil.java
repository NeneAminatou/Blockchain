package gestionblock;
import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.ECKey.ECDSASignature;
import org.bitcoinj.core.Sha256Hash;

import com.google.gson.GsonBuilder;

public class HashUtil {
	 
	 //Applies Sha256 to a string and returns the result. 
	
	
	 public  static String applySha256(String input){
	  
	  try {
	   MessageDigest digest = MessageDigest.getInstance("SHA-256");
	         
	   //Applies sha256 to our input, 
	   byte[] hash = digest.digest(input.getBytes("UTF-8"));
	         
	   StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
	   for (int i = 0; i < hash.length; i++) {
	    String hex = Integer.toHexString(0xff & hash[i]);
	    if(hex.length() == 1) hexString.append('0');
	    hexString.append(hex);
	   }
	   return hexString.toString();
	  }
	  catch(Exception e) {
	   throw new RuntimeException(e);
	  }
	 }
	 
	//Applies ECDSA Signature and returns the result ( as bytes ).
	
		public static String  applyECDSASig(byte[] keyprivate, String input) {
		ECKey key=new ECKey();
	  return 	key.fromPrivate(keyprivate).signMessage(input);
	
		}
		//Verifies a String signature 
		public static boolean verifyECDSASig(byte [] keypublic, String data,String signature) {
		
	           
	         try {
	        	 ECKey key=new ECKey();
	        	 key.fromPublicOnly(keypublic).verifyMessage(data, signature);
				
				return true;
			} catch (SignatureException e) {
				// TODO Auto-generated catch block
				
				System.out.println("Wrong key is used to verify");
				return false;
			}
	            		
		}

		//Short hand helper to turn Object into a json string
		public static String getJson(Object o) {
			return new GsonBuilder().setPrettyPrinting().create().toJson(o);
		}
		
		//Returns difficulty string target, to compare to hash. eg difficulty of 5 will return "00000"  
		public static String getDificultyString(int difficulty) {
			return new String(new char[difficulty]).replace('\0', '0');
		}
		
		public static String getStringFromKey(Key key) {
			return Base64.getEncoder().encodeToString(key.getEncoded());
			
		}
		
		
		
}
