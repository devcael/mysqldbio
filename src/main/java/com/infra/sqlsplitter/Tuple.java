package com.infra.sqlsplitter;

public class Tuple <X, Y>{
  public X firstValue;
  public Y secondValue;
  public Tuple(X firstValue, Y secondValue){
    this.firstValue = firstValue;
    this.secondValue = secondValue;
  }
}
