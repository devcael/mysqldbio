package com.infra.sql_divider;

import java.util.ArrayList;
import java.util.List;

public class StrStore {
  private final List<String> data = new ArrayList<>();
  private StringBuilder currBuffer = new StringBuilder();;
  public StrStore(){
  }

  public void addStr(String value){
    currBuffer.append(value);
  }
  private boolean currBufferIsEmpty(){
    return currBuffer.toString().isEmpty();
  }

  public void cut(){
    if(!currBufferIsEmpty()){
      data.add(currBuffer.toString());
      currBuffer = new StringBuilder();
    }
  }

  public int getSizeOfAllStrings(){
    int size =0;
    for (String curr: data){
      size += curr.length();
    }
    return size;
  }

  List<String> getData(){
    if(!currBuffer.toString().isEmpty()){
      data.add(currBuffer.toString());
      currBuffer = new StringBuilder();
    }
    return data;
  }
}
