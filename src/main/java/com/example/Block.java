package com.example;

import java.util.ArrayList;
import java.util.Date;

public class Block {
    public String hash;
    public String previousHash;
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //our data will be a simple message.    
    private long timeStamp;
    private int nonce;

    //the cosntructor of Block
    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculatedHash();
    }
    
    public String calculatedHash() {
        String calculatedHash = StringUtil.applySha256(previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + merkleRoot);
        return calculatedHash;
    }

    public void mineBlock(int difficulty) { //creating a way to mineblock and their difficulty
        merkleRoot = StringUtil.getMerkleRoot(transactions);
        String target = new String(new char[difficulty]); //basically creating string with difficulty * '0'
        while(!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculatedHash();
        }
        System.out.println("Setoran sudah diterima (block mined): " + hash);
    }

    public boolean addTransaction(Transaction transaction) {
        //process transaction and check if valid, unless block is genesis block then ignore.
		if(transaction == null) return false;
        if ((previousHash != "0")) {
            if(transaction.processTransaction() !=true) {
                System.out.println("Transaction failed to process. Cancelled");
                return false;
            }
        }
        transactions.add(transaction);
		System.out.println("Transaction Successfully added to Block");
		return true;
    }
}