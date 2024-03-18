package com.infra;

import com.infra.interfaces.ReadLine;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileReader {
  private String path;
  private boolean isPaused;
  private int line = 0;
  private ReadLine event;
  public FileReader(String path) {
    this.path = path;
  }
  public boolean fileExists() {
    return FileReader.class.getResourceAsStream(path) != null;
  }
  public void addReadeLineEvent(ReadLine callback) {
    this.event = callback;
  }
  private void startReader(int i_line) {
    try {
      InputStream inputStream = FileReader.class.getResourceAsStream(path);
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      String strLine;
      int count = 0;
      while (((strLine = reader.readLine()) != null)) {
        if (isPaused) {
          break;
        }
        if (count >= this.line) {
          count++;
          this.line = count;
          if (event != null) {
            event.readLine(strLine);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public void start() {
    isPaused = false;
    startReader(0);
  }
  public void continueReader() {
    isPaused = false;
    startReader(this.line);
  }
  public void pause() {
    isPaused = true;
  }

  public int getLine() {
    return this.line;
  }
}
