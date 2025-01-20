package com.example;

public class TransactionInput {
  public String transactionOutputId;
  public TransactionOutput UTXO;

  public TransactionInput(String transactationOutputId) {
    this.transactionOutputId = transactationOutputId;
  }
}
