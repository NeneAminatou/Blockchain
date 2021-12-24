package gestionblock;

public class Mykey {
	
  private String keyPuHexa;
  private byte[] keyByte;
  /**
 * @return the keyPrivHexa
 */

  public Mykey(String keyPu,byte [] keybyte)
  {
	  
	  this.keyByte=keybyte;
	  this.keyPuHexa=keyPu;
  }
/**
 * @return the keyPuHexa
 */
public String getKeyPuHexa() {
	return keyPuHexa;
}
/**
 * @param keyPuHexa the keyPuHexa to set
 */
public void setKeyPuHexa(String keyPuHexa) {
	this.keyPuHexa = keyPuHexa;
}
/**
 * @return the keyByte
 */
public byte[] getKeyByte() {
	return keyByte;
}
/**
 * @param keyByte the keyByte to set
 */
public void setKeyByte(byte[] keyByte) {
	this.keyByte = keyByte;
}


}
