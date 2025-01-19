package com.example;

import java.util.ArrayList;

import com.google.gson.GsonBuilder;


public class BasicChain {

  public static ArrayList<Block> blockchain = new ArrayList<Block>();
  public static int difficulty = 10; 

  public static void main(String[] args) {
      blockchain.add(new Block("Block pertama lurrrr", "0"));
      System.out.println("Trying to Mine block 1... ");
		  blockchain.get(0).mineBlock(difficulty);

      blockchain.add(new Block("Block kedua", blockchain.get(blockchain.size()-1).hash));
      System.out.println("Trying to Mine block 2... ");
		  blockchain.get(1).mineBlock(difficulty);

      blockchain.add(new Block("block ketiga", blockchain.get(blockchain.size()-1).hash));
      System.out.println("Trying to Mine block 3... ");
		  blockchain.get(2).mineBlock(difficulty);

      blockchain.add(new Block("block keempat", blockchain.get(blockchain.size()-1).hash));
      System.out.println("Trying to Mine block 4... ");
		  blockchain.get(3).mineBlock(difficulty);

      blockchain.add(new Block("block kelima", blockchain.get(blockchain.size()-1).hash));
      System.out.println("Trying to Mine block 5... ");
		  blockchain.get(4).mineBlock(difficulty);

      blockchain.add(new Block("block keenam Muchas gracias afición, esto es para vosotros...", blockchain.get(blockchain.size()-1).hash));
      System.out.println("Trying to Mine block 6... ");
		  blockchain.get(5).mineBlock(difficulty);

      blockchain.add(new Block("block ketujuh ...SSIIUUUUUUUUUUUUUUUUUUUUUU", blockchain.get(blockchain.size()-1).hash));
      System.out.println("Trying to Mine block 7... ");
		  blockchain.get(6).mineBlock(difficulty);

      blockchain.add(new Block("block kedelapn", blockchain.get(blockchain.size()-1).hash));
      System.out.println("Trying to Mine block 8... ");
		  blockchain.get(7).mineBlock(difficulty);

      blockchain.add(new Block("block kesembilan wo men jin sheng zhu ding shi cang sang", blockchain.get(blockchain.size()-1).hash));
      System.out.println("Trying to Mine block 9... ");
		  blockchain.get(8).mineBlock(difficulty);

      blockchain.add(new Block("block kesepuluh I am Optimus Prime, and I send this message to any surviving Autobots jangan lupa sholat 5 waktu", blockchain.get(blockchain.size()-1).hash));
      System.out.println("Trying to Mine block 10... ");
		  blockchain.get(9).mineBlock(difficulty);

      System.out.println("\nBlockchain is Valid: " + isChainValid());
      
      String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
      System.out.println("\nThe block chain: ");
      System.out.println(blockchainJson);
  }

  public static Boolean isChainValid() {
    Block currentBlock;
    Block previousBlock;
    String hashTarget = new String(new char[difficulty]).replace('\0', '0');
      
      for (int i = 1; i < blockchain.size(); i++) {
        currentBlock = blockchain.get(i);
        previousBlock = blockchain.get(i - 1);
        
        if(!currentBlock.hash.equals(currentBlock.calculatedHash())) {
          System.out.println("Current hash are not equal");
          return false;
        }
        
        if(!previousBlock.hash.equals(currentBlock.previousHash)) {
          System.out.println("Previous hash are not equals");
          return false;
          
        }
        if(!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
          System.out.println("Setoran belum tidak diambil (block hasn't been mined)");
          return false;
        }
      }
      return true;
  }
}
