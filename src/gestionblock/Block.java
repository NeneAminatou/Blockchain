package gestionblock;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




public class Block {
	private int index;
	private Timestamp timestamp;
    private String hash;
	private String previousHash; 
	private String merkleRoot;

	private int nonce;
	private ArrayList<Transaction>listTransaction;
	public Block(String previousHash,int index)
	{
		this.previousHash=previousHash;
		timestamp=new Timestamp(new Date().getTime());
	    hash=calculerHash();
	    this.index=index;
		listTransaction=new ArrayList<>();
	}
	
	public String calculerHash()
	{
		String hashBlock=HashUtil.applySha256(previousHash+Float.toString(timestamp.getTime())+merkleRoot+Integer.toString(nonce));
		
	    return	hashBlock;
		
	
	
	}
	public void ajouterTransation(Transaction transation)
	{
		listTransaction.add(transation);
	}
	
	public boolean ajouterTransaction(Transaction transaction,BlockChain blockchaine)
	{
		
			if(transaction == null)
				return false;		
			if((!"0".equals(getPreviousHash()))) {
				if((transaction.effectuerTransaction(blockchaine) != true)) {
					System.out.println("Transaction failed to process. Discarded.");
					return false;
				}
				
				
			}

		listTransaction.add(transaction);
			System.out.println("Transaction Successfully added to Block");
			return true;
		
	}
	
	public void mineBlock(int difficulte) {
		merkleRoot = getMerklRoot();
		String resultat = getDificultyString(difficulte); 
		while(!hash.substring( 0, difficulte).equals(resultat)) {
			nonce ++;
			hash = calculerHash();
			
		}
		System.out.println("Block Mined!");
	}
	
	public  String getDificultyString(int difficulty) {
		return new String(new char[difficulty]).replace('\0', '0');
	}
	
	
	public  String getMerklRoot() {
		int count = listTransaction.size();
		
		List<String> coucheprecedente = new ArrayList<String>();
		for(Transaction transaction : listTransaction) {
			coucheprecedente.add(transaction.getHash());
		}
		List<String> couchecourant = coucheprecedente;
		
		while(count > 1) {
			couchecourant = new ArrayList<String>();
			for(int i=1; i < coucheprecedente.size(); i+=2) {
				couchecourant.add(HashUtil.applySha256(coucheprecedente.get(i-1) + coucheprecedente.get(i)));
			}
			count = couchecourant.size();
			coucheprecedente = couchecourant;
		}
		
		String merkleRoot = (couchecourant.size() == 1) ? couchecourant.get(0) : "";
		return merkleRoot;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the timestamp
	 */
	
	/**
	 * @return the hash
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * @param hash the hash to set
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}

	/**
	 * @return the previousHash
	 */
	public String getPreviousHash() {
		return previousHash;
	}

	/**
	 * @param previousHash the previousHash to set
	 */
	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	/**
	 * @return the merkleRoot
	 */
	public String getMerkleRoot() {
		return merkleRoot;
	}

	/**
	 * @param merkleRoot the merkleRoot to set
	 */
	public void setMerkleRoot(String merkleRoot) {
		this.merkleRoot = merkleRoot;
	}

	/**
	 * @return the nonce
	 */
	public int getNonce() {
		return nonce;
	}

	/**
	 * @param nonce the nonce to set
	 */
	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

	/**
	 * @return the listTransaction
	 */
	public ArrayList<Transaction> getListTransaction() {
		return listTransaction;
	}

	/**
	 * @param listTransaction the listTransaction to set
	 */
	public void setListTransaction(ArrayList<Transaction> listTransaction) {
		this.listTransaction = listTransaction;
	}
	
}
