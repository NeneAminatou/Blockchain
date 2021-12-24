package gestionblock;

public class TransactionEntree {
	private String idTransEffectue;
	
	private TransactionDejaEffectue transactionEffectue;
	public TransactionEntree(String idTransEffectue)
	{
		this.idTransEffectue=idTransEffectue;
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
	 * @return the transactionEffectue
	 */
	public TransactionDejaEffectue getTransactionEffectue() {
		return transactionEffectue;
	}
	/**
	 * @param transactionEffectue the transactionEffectue to set
	 */
	public void setTransactionEffectue(TransactionDejaEffectue transactionEffectue) {
		this.transactionEffectue = transactionEffectue;
	}
}
