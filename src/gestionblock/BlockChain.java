package gestionblock;

import java.util.ArrayList;
import java.util.HashMap;

public class BlockChain {
	
	private int difficulte;
	private int nbBlock;
	private int nombreDeTransaction;
	/**
	 * @return the nombreDeTransaction
	 */
	public int getNombreDeTransaction() {
		return nombreDeTransaction;
	}

	/**
	 * @param nombreDeTransaction the nombreDeTransaction to set
	 */
	public void setNombreDeTransaction(int nombreDeTransaction) {
		this.nombreDeTransaction = nombreDeTransaction;
	}

	private ArrayList<Block>listBloc;
	private HashMap<String, TransactionDejaEffectue>transactionEffectue=new HashMap<>();
	private Transaction genesisTransaction;
	public  BlockChain(int nbblock,int difficulte) {
	  listBloc=new ArrayList<>();
	  this.nbBlock=nbblock;
	  this.difficulte=difficulte;
	  
	}
	
	public void genererGenesisBloc(Address destination,float value)
	{
		Address genererCoin=new Address();
		/*  transaction pour generer de l'argent dans un portefeuille*/
	  genesisTransaction=new Transaction(genererCoin.getAddr(),
			 destination.getAddr(),value);
	  
	  genesisTransaction.setIdTransaction("0");
	
	  
	
	  /**
	   * Ajouter la transaction dans la liste de transaction effectuée de la blockchaine
	   */
	  
	   genesisTransaction.getOutputs().add(new TransactionDejaEffectue(genesisTransaction.getKeyDestination(), 
			   genesisTransaction.getValue(), genesisTransaction.getIdTransaction()));
	  
	   
	   transactionEffectue.put(genesisTransaction.getOutputs().get(0).getIdTransEffectue(),
			   genesisTransaction.getOutputs().get(0));
	
	}
	public void addBlock(Block b)
	{
	  if(listBloc.size()!=nbBlock)
	  {
		  b.mineBlock(this.difficulte);
		  listBloc.add(b);
		 
	  }
	  else
	  {
		  System.out.println("Nombre de bloc atteint");
	  }
	}
	public String getPreviousBlock()
	{
		
		if(listBloc.size()!=0)
	  return	listBloc.get(listBloc.size()-1).getHash();
		else
		{
			System.out.println("Block Chain is empty");
			return null;
		}
		
		
		
	}
	
    public int position()
    {
    	return listBloc.size();
    }
    public boolean verifierChaine(int level)
    {
    	Block blockcourant = null;
    	Block blockprecedent;
    	String hashblock = new String(new char[difficulte]).replace('\0', '0');
    	int tr=0;
    	if(!listBloc.get(0).getPreviousHash().equals("0"))
    	{
    		System.out.println("Absence du block genesis");
    		return false;
    	}
    	for(int i=1;i<listBloc.size();i++)
    	{
    		blockcourant=listBloc.get(i);
    		blockprecedent=listBloc.get(i-1);
    		if(!blockcourant.getHash().equals(blockcourant.calculerHash()))
    		{
    			
    			System.out.println("Invalide chainage sur les blocks courant ");
    			return false;
    		}
    		if(!blockcourant.getPreviousHash().equals(blockprecedent.calculerHash()))
    		{
    			System.out.println("Invalide chainage sur les blocks precedents ");
    			return false;	
    		}
    		
    		if(!blockcourant.getHash().substring(0, difficulte).equals(hashblock))
			{
	            System.out.println("Minage invalide du block "+blockcourant.getIndex());
	            return false;
			} 
    		
    		if(level==2)
        	{
        		for( tr=0; tr <blockcourant.getListTransaction().size(); tr++) {

        			Transaction trans =blockcourant.getListTransaction().get(tr);

        			
                     
        			if(!trans.verifierSignature()) {

        				System.out.println("Signature on Transaction(" + tr + ") is Invalid");

        				return false; 

        			}
        		
          

            	}
            	
        	}
        	
    		
    	}
    
    	
    	
    	System.out.println("Blockchain is valide");
    	return true;
    	
    }

	/**
	 * @return the difficulte
	 */
	public int getDifficulte() {
		return difficulte;
	}

	/**
	 * @param difficulte the difficulte to set
	 */
	public void setDifficulte(int difficulte) {
		this.difficulte = difficulte;
	}

	/**
	 * @return the nbBlock
	 */
	public int getNbBlock() {
		return nbBlock;
	}

	/**
	 * @param nbBlock the nbBlock to set
	 */
	public void setNbBlock(int nbBlock) {
		this.nbBlock = nbBlock;
	}

	/**
	 * @return the listBloc
	 */
	public ArrayList<Block> getListBloc() {
		return listBloc;
	}

	/**
	 * @param listBloc the listBloc to set
	 */
	public void setListBloc(ArrayList<Block> listBloc) {
		this.listBloc = listBloc;
	}

	/**
	 * @return the transactionEffectue
	 */
	public HashMap<String, TransactionDejaEffectue> getTransactionEffectue() {
		return transactionEffectue;
	}

	/**
	 * @param transactionEffectue the transactionEffectue to set
	 */
	public void setTransactionEffectue(HashMap<String, TransactionDejaEffectue> transactionEffectue) {
		this.transactionEffectue = transactionEffectue;
	}

	/**
	 * @return the genesisTransaction
	 */
	public Transaction getGenesisTransaction() {
		return genesisTransaction;
	}

	/**
	 * @param genesisTransaction the genesisTransaction to set
	 */
	public void setGenesisTransaction(Transaction genesisTransaction) {
		this.genesisTransaction = genesisTransaction;
	}
	

}
 