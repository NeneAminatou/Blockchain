package gestionblock;

import java.util.ArrayList;
import java.util.Random;

import org.bitcoinj.core.ECKey;

public class Transaction {
	
	private String infos;

	private Mykey keySource;
	
	private Mykey keyDestination;
	private float value;
	private String signature;
	private ArrayList<TransactionEntree>inputs=new ArrayList<>();
	private ArrayList<TransactionDejaEffectue> outputs = new ArrayList<>();
	private  int Max=100;
	private String idTransaction;
	
	public Transaction(String infos)
	{
		 this.value=new Random().nextInt(Max);
      	this.infos=" "+infos+" :"+this.value;
	}
	public Transaction(Mykey keysource,Mykey destination,float value)
	{
		this.keySource=keysource;
		this.keyDestination=destination;
		if(value!=-1)
		this.value=value;
		else
		this.value=getMax();
		
		this.infos="[Source]: "+keySource.getKeyPuHexa()+"[Destination] :"+this.keyDestination.getKeyPuHexa()+this.value;
		
	}
	public boolean effectuerTransaction(BlockChain blockchaine)
	{
	  if(!verifierSignature())
	  {
		  System.out.println("Echec verificiation signature");
		  return false;
	  }

			for(TransactionEntree i : inputs) {
		
				i.setTransactionEffectue(blockchaine.getTransactionEffectue().get(i.getIdTransEffectue()));
				
			}
			float reste = getValuesFromIput() - value;
		setIdTransaction(calculerHash());
		outputs.add(new TransactionDejaEffectue(keyDestination, value,getIdTransaction())); 
		
		outputs.add(new TransactionDejaEffectue(keySource, reste,getIdTransaction()));
		
	

	for(TransactionDejaEffectue o : outputs) {
		
		blockchaine.getTransactionEffectue().put(o.getIdTransEffectue(), o);
	}
	for(TransactionEntree i : inputs) {
		if(i.getTransactionEffectue() == null) 
			continue;
		
		blockchaine.getTransactionEffectue().remove(i.getTransactionEffectue().getIdTransEffectue());
	}

		return true;
	}
	public float getValuesFromIput()
	{
		
		float total=0;
		for(TransactionEntree i : inputs) {
			if(i.getIdTransEffectue() == null) continue;
			total += i.getTransactionEffectue().getValue();
		}
		return total;
		
	}
	public float getValuesFromOutput()
	{
		float total = 0;
		for(TransactionDejaEffectue o : outputs) {
			total += o.getValue();
		}
		return total;
	}

	public void addinputTransaction(ArrayList<TransactionEntree>inputs)
	{
		this.inputs=inputs;
	}
	
	public void genererSignature(byte[] privatekey)
	{
	   String data= keySource.getKeyPuHexa()+keyDestination.getKeyPuHexa()+Float.toString(value);
	 signature= HashUtil.applyECDSASig(privatekey, data);
	}
	public String getHash()
	{
		
	return	HashUtil.applySha256(this.infos);
		
	}
	public String calculerHash()
	{
		return HashUtil.applySha256(keySource.getKeyPuHexa()+keyDestination.getKeyPuHexa()+
				Float.toString(value));
	}
	
	public boolean verifierSignature()
	{
		 String data=keySource.getKeyPuHexa()+keyDestination.getKeyPuHexa()+Float.toString(value);
		
	 return 	 HashUtil.verifyECDSASig(keySource.getKeyByte(), data, signature);
	}
	/**
	 * @return the idTransaction
	 */
	public String getIdTransaction() {
		return idTransaction;
	}
	/**
	 * @param idTransaction the idTransaction to set
	 */
	public void setIdTransaction(String idTransaction) {
		this.idTransaction = idTransaction;
	}
	/**
	 * @return the source
	 */
	/*
	 * @return the keySource
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
	 * @return the signature
	 */
	public String getSignature() {
		return signature;
	}
	/**
	 * @param signature the signature to set
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}
	/**
	 * @return the inputs
	 */
	public ArrayList<TransactionEntree> getInputs() {
		return inputs;
	}
	/**
	 * @param inputs the inputs to set
	 */
	public void setInputs(ArrayList<TransactionEntree> inputs) {
		this.inputs = inputs;
	}
	/**
	 * @return the outputs
	 */
	public ArrayList<TransactionDejaEffectue> getOutputs() {
		return outputs;
	}
	/**
	 * @param outputs the outputs to set
	 */
	public void setOutputs(ArrayList<TransactionDejaEffectue> outputs) {
		this.outputs = outputs;
	}
	/**
	 * @return the dcc
	 */
	/**
	 * @return the max
	 */
	public int getMax() {
		return Max;
	}
	/**
	 * @param max the max to set
	 */
	public void setMax(int max) {
		Max = max;
	}
	/**
	 * @return the keyDestination
	 */
	public Mykey getKeyDestination() {
		return keyDestination;
	}
	/**
	 * @param keyDestination the keyDestination to set
	 */
	public void setKeyDestination(Mykey keyDestination) {
		this.keyDestination = keyDestination;
	}
	

}
