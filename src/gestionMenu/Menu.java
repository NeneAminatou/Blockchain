package gestionMenu;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import org.bouncycastle.crypto.signers.RandomDSAKCalculator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import gestionblock.BCJsonUtils;
import gestionblock.Block;
import gestionblock.BlockChain;

import gestionblock.Transaction;
import gestionblock.Address;

public class Menu {
	
	
 private	int difficulty;
 private  	int nbblock;
 private   BlockChain blockchain;
 private 	String tab[] ={"BOB","ALICE","CHARLIE","EVE","BOBY","EVY","ALICY","EVA","CHARLY"};
 private ArrayList<Address>listAdres=new ArrayList<>();
 Transaction genesisTransaction;
private Scanner sc;
 

 	public Menu()
 	{
	  
 	}
 	public int randomValue(int check)
 	{
 		int nb;
 		if(check!=-1)
 		{
 			do 
 			{
 			nb=new Random().nextInt(9);	
 			}while(check==nb);
 		}
 		else
 		{
 			nb=new Random().nextInt(9);
 		}
 		return nb;
 	}
 	public int randomValue(int check,int nbAddress)
 	{
 		int nb;
 		if(check!=-1)
 		{
 			do 
 			{
 			nb=new Random().nextInt(nbAddress);	
 			}while(check==nb);
 		}
 		else
 		{
 			nb=new Random().nextInt(nbAddress);
 		}
 		return nb;
 	}
 	
 	public void generTransactionAleatoireLevel2()
 	{
 		Scanner sc=new Scanner(System.in);
 		int source,destination,first;
 		System.out.println("Enter the difficulty ");
		difficulty=sc.nextInt();
	
			 System.out.println("Enter le nombre d'addresses");
			    nbblock=sc.nextInt();
		
		
	   
	    blockchain=new BlockChain(nbblock, difficulty);
	    for(int i=0;i<nbblock;i++)
	    {
	    Address ad= new Address();
	    ad.setName(tab[i%9]);
	    	
	    	listAdres.add(ad);
	    	
	    }
	    
	    blockchain=new BlockChain(nbblock, difficulty);
	    
	   
	    destination=0;
	    
	    blockchain.genererGenesisBloc(listAdres.get(destination),-1);
	    
	
	    Block b=new Block("0", blockchain.position());
	   b.ajouterTransaction(blockchain.getGenesisTransaction(), blockchain);
	
	   blockchain.addBlock(b);
	 
	   first=destination;
	 
	 
		for(int i=1;i<nbblock;i++)
		{
			
			  source=first;
			  destination=randomValue(source,nbblock);
			  
			  System.out.println("Solde  "+listAdres.get(source).getName()+"  avant "+listAdres.get(source).getBalance(blockchain));
			  System.out.println("Solde   "+listAdres.get(destination).getName()+" avant "+listAdres.get(destination).getBalance(blockchain));
			  Block b1=new Block(blockchain.getPreviousBlock(), blockchain.position());
			  b1.ajouterTransaction(listAdres.get(source).sendCoins(listAdres.get(destination).getAddr()
			  ,new Random().nextInt(blockchain.getGenesisTransaction().getMax())
			  , blockchain), blockchain);
			  blockchain.addBlock(b1);
			  System.out.println("Solde "+listAdres.get(source).getName()+" après "+listAdres.get(source).getBalance(blockchain));
			  System.out.println("Solde "+listAdres.get(destination).getName()+" après "+listAdres.get(destination).getBalance(blockchain));
		      first=destination;	  
			   
			   
		}
		blockchain.verifierChaine(2);
		
	    
 	}
 	
 	public void generTransactionAleatioire()
 	{
 		Scanner sc=new Scanner(System.in);
 		int source,destination,nbt,temp;
 		System.out.println("Enter the difficulty ");
		difficulty=sc.nextInt();
	    System.out.println("Enter le nombre de block");
	    nbblock=sc.nextInt();
	    System.out.println("Entrer le nombre de transaction");
	    nbt=sc.nextInt();
	    blockchain=new BlockChain(nbblock, difficulty);
	   
	  
	    System.out.println("Generation alÃ©atoire de transaction");
	    source=randomValue(-1);
	   
	    destination=randomValue(source);
		genesisTransaction=new Transaction(tab[source]+"-"+tab[destination]);
		Block genesis=new Block("0",blockchain.position());
		genesis.ajouterTransation(genesisTransaction);
		blockchain.addBlock(genesis);
		blockchain.setNombreDeTransaction(nbt);
		temp=nbblock;
		int j=0;
		for(int i=1;i<nbt;i++)
		{
			  source=randomValue(-1);
			  destination=randomValue(source);
			  
			    Transaction t=new Transaction(tab[source]+"-"+tab[destination]);
			    
			   
			   if(nbblock>0)
			   {
				   Block b=new Block(blockchain.getPreviousBlock(), blockchain.position());
				    b.ajouterTransation(t);
				     blockchain.addBlock(b);
				     nbblock--;
			   }
			   else 
			   {
				
				    blockchain.getListBloc().get(j%blockchain.position()).ajouterTransation(t);
				    
			   }
		}
		 String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		   System.out.println(blockchainJson);
		   System.out.println("Verification of your blockchain is  loading ");
		   blockchain.verifierChaine(1);
 	}
 	public void manageSavedChain()
 	{
 		 sc=new Scanner(System.in);
 		String choix,name;
 		 do 
 		 {
 			
 	 		System.out.println("Press 4 to save");
 			System.out.println("Press 5 to read a saved Blocchain");
 			System.out.println("Press c to continue");
 			
 			choix=sc.nextLine();
 			switch(choix)
 			{
 			case "4":
 			{
 				System.out.println("Ente a name of your saved blockchain");
 				name=sc.nextLine();
 				BCJsonUtils.BCJsonWriter(blockchain,name+".json");
 					System.out.println("Blochaine successfully saved");
 				
 				break;
 			}
 			case "5":
 			{
 				System.out.println("Enter a name of blockchain to load ");
 				name=sc.nextLine();
 				blockchain=BCJsonUtils.BCJsonReader(name+".json");
 				
 				 String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
 			
 				
 				   System.out.println(blockchainJson);
 				   
 				   System.out.println("Blockchain load complete");
 				   System.out.println("Verification of the blockchain loading ");
 				   blockchain.verifierChaine(1);
 				   break;
 			}
 			default :
 			{
 				System.out.println("Good we need your feed back after");
 			}
 			
 			}	
 		 }while(!choix.equals("c"));
 	
 	}
	public void start()
	{
		sc = new Scanner(System.in);
		String nb;
		
		    do 
		    {
			System.out.println("Press 1 for Level 1");
			System.out.println("Press 2 for Level 2");
			System.out.println("Press 3 to quit");
			nb=sc.nextLine();
			switch(nb)
			{
			case "1":
			{
		     
		    	 generTransactionAleatioire();
			     manageSavedChain();
					
		    	break;
			
			}
			case "2":
			{
				generTransactionAleatoireLevel2();
				  manageSavedChain();
				break;
			}
			
			}
			
		    }while(!nb.equals("3"));
			
	}
	

	/**
	 * @return the difficulty
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * @return the nbblock
	 */
	public int getNbblock() {
		return nbblock;
	}

	/**
	 * @param nbblock the nbblock to set
	 */
	public void setNbblock(int nbblock) {
		this.nbblock = nbblock;
	}
	

}
