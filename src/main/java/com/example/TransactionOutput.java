package com.example;

import java.security.PublicKey;

public class TransactionOutput {
  public String id;
  public PublicKey reciepient;        //showing reciept of new owner the chain/coin
  public float value;                 //the amount of chain/coin
  public String parentTransactionId;  //the id transaction this output was created in

  public TransactionOutput(PublicKey reciepient, float value, String id) {
    this.reciepient = reciepient;
    this.value = value;
    this.parentTransactionId = parentTransactionId;
    this.id = StringUtil.applySha256(StringUtil.getStringFromKey(reciepient) + Float.toString(value) + parentTransactionId);
  }

  //gonna check if the chain/coin is legit belong to you
  public boolean theCoinIsMine(PublicKey publicKey) {
    return (publicKey == reciepient);
  }
}


