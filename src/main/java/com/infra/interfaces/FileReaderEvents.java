package com.infra.interfaces;

public interface FileReaderEvents {
  public void readLine(String line);
  public void completed();
  public void error(Exception e);
}
