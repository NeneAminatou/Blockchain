package gestionblock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bitcoinj.core.ECKey;

public class Address  {
		
		
		private ECKey  key;
		private HashMap<String,TransactionDejaEffectue> transaction = new HashMap<>();
		private String name;
		private Mykey addr;
		/**
		 * @return the addr
		 */
		public Mykey getAddr() {
			return addr;
		}
		/**
		 * @param addr the addr to set
		 */
		public void setAddr(Mykey addr) {
			this.addr = addr;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		public Address(){
		
		key=new ECKey();
		addr=new Mykey(key.getPublicKeyAsHex(),key.getPubKey());
		}
		
	
		public float getBalance(BlockChain blochaine) {
			float total = 0;	
	        for (Entry<String, TransactionDejaEffectue> item: blochaine.getTransactionEffectue().entrySet())
	        {
	        	TransactionDejaEffectue trans = item.getValue();
	            if(trans.validerKey(getAddr().getKeyPuHexa())) {
	            	transaction.put(trans.getIdTransEffectue(),trans); 
	            	total += trans.getValue() ; 
	            }
	        }
	        if(total>=0)
			return total;
	        else
	        {
	        	return 0;
	        }
		}
		public Transaction sendCoins(Mykey recepteur,float value,BlockChain block) {
			
			if(getBalance(block) < value) {
				System.out.println("Pas assez d'argent");
				return null;
			}
			ArrayList<TransactionEntree> inputs = new ArrayList<>();
			
			float total = 0;
			for (Entry<String, TransactionDejaEffectue> item: transaction.entrySet()){
				TransactionDejaEffectue tro = item.getValue();
				total += tro.getValue();
				inputs.add(new TransactionEntree(tro.getIdTransEffectue()));
				if(total > value) break;
			}
			
			Transaction newTransaction = new Transaction(getAddr(), recepteur , value);
			newTransaction.setInputs(inputs);
			newTransaction.genererSignature(key.getPrivKeyBytes());
			
			for(TransactionEntree input: inputs){
				transaction.remove(input.getIdTransEffectue());
			}
			
			return newTransaction;
		}

		
	
		/**
		 * @return the publicKey
		 */
		public byte[] getPublicKey() {
			return key.getPubKey();
		}
		public byte[] getPrivatekey()
		{
			return key.getPrivKeyBytes();
		}

			
		
		
	}

