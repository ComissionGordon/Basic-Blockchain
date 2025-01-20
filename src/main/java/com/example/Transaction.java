package com.example;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {
  public String transactionId; //a hash of the transaction
  public PublicKey sender;     //sender address/public key
  public PublicKey reciepient; //a reciept for address/public key
  public float value;
  public byte[] signature;     //genuine signature so that nobody spend our fund in our wallet

  public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
  public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

  public static int sequence = 0; //rough count how manu transaction had been generated

  public Transaction(PublicKey from, PublicKey to, float value,  ArrayList<TransactionInput> inputs) {
    this.sender = from;
    this.reciepient = to;
    this.value = value;
    this.inputs = inputs;
  }

  //Returns true if new transaction could be created
  public boolean processTransaction() {
    if(verifySignature() == false) {
      System.out.println("#Transaction Signature failed to verify");
      return false;
    }

    //gather transaction inputs (Make sure they are unspent):
    for (TransactionInput i : inputs) {
        i.UTXO = BasicChain.UTXOs.get(i.transactionOutputId);
    }

    //this will check if the transaction are valid
    if(getInputsValue() < BasicChain.minimumTransaction) {
      System.out.println("#Transaction Inputs to small: " + getInputsValue());
      System.out.println("Please enter the amount greater than " + BasicChain.minimumTransaction);
      return false;
    }

    //generate transaction outputs:
		float leftOver = getInputsValue() - value; //get value of inputs then the left over change:
		transactionId = calculateHash2();
    outputs.add(new TransactionOutput(this.reciepient, value, transactionId)); //send value to recipient
    outputs.add(new TransactionOutput(this.sender, leftOver, transactionId)); //send the left over 'change' back to sender

    //outputs to Unspent list
		for(TransactionOutput o : outputs) {
      BasicChain.UTXOs.put(o.id , o);
    }

    //remove transaction inputs from UTXO lists as spent:
		for(TransactionInput i : inputs) {
      if(i.UTXO == null) continue; //if Transaction can't be found skip it 
			BasicChain.UTXOs.remove(i.UTXO.id);
    }
      
    return true;
  }

  //returns sum of inputs(UTXOs) values
	public float getInputsValue() {
		float total = 0;
		for(TransactionInput i : inputs) {
			if(i.UTXO == null) continue; //if Transaction can't be found skip it 
			total += i.UTXO.value;
		}
		return total;
	}

  //returns sum of outputs:
	public float getOutputsValue() {
		float total = 0;
		for(TransactionOutput o : outputs) {
			total += o.value;
		}
		return total;
	}


  //Signs all the data we dont wish to be tampered with.
  public void generateSignature(PrivateKey privateKey) {
    String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient);
    signature = StringUtil.applyECDSASig(privateKey, data);
  }
  
  //Verifies the data we signed hasnt been tampered with
  public boolean verifySignature() {
    String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient);
    return StringUtil.verifyECDSASig(sender, data, signature);
  } //In reality, you may want to sign more information, like the outputs/inputs used and/or time-stamp ( for now we are just signing the bare minimum )

  // This Calculates the transaction hash (which will be used as its Id)
	private String calculateHash2() {
		sequence++; //increase the sequence to avoid 2 identical transactions having the same hash
		return StringUtil.applySha256(
      StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value) + sequence); 
    }
}
