package gestionblock;


import java.nio.charset.Charset;

import org.bitcoinj.core.ECKey;

public class TransactionDejaEffectue {
	private String idTransEffectue;
	private Mykey recepteur;

	private float value;
	private String idPrecedentTransaction;
	public TransactionDejaEffectue(Mykey recepteur,float value,String idPrecedentTransaction)
	{
		this.idPrecedentTransaction=idPrecedentTransaction;
		this.value=value;
		this.recepteur=recepteur;
		
		
		this.idTransEffectue=HashUtil.applySha256(recepteur.getKeyPuHexa()+Float.toString(value)
		+idPrecedentTransaction);
	}
	public boolean validerKey(String publicKey)
	{
		
		return recepteur.getKeyPuHexa()==publicKey;
	}
	/**
	 * @return the idTransEffectue
	 */
	public String getIdTransEffectue() {
		return idTransEffectue;
	}
	/**
	 * @param idTransEffectue the idTransEffectue to set
	 */
	public void setIdTransEffectue(String idTransEffectue) {
		this.idTransEffectue = idTransEffectue;
	}
	/**
	 * @return the recepteur
	 */
	
	/**
	 * @return the value
	 */
	public float getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(float value) {
		this.value = value;
	}
	/**
	 * @return the idPrecedentTransaction
	 */
	public String getIdPrecedentTransaction() {
		return idPrecedentTransaction;
	}
	/**
	 * @param idPrecedentTransaction the idPrecedentTransaction to set
	 */
	public void setIdPrecedentTransaction(String idPrecedentTransaction) {
		this.idPrecedentTransaction = idPrecedentTransaction;
	}
	/**
	 * @return the recepteur
	 */

}
