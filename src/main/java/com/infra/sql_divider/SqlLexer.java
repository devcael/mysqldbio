package com.infra.sql_divider;

import java.util.List;

public class SqlLexer {
  StringBuilder source;
  StrStore strStore;
  int cursor = 0;
  int start = 0;

  private boolean isInsideString = false;
  private char stringType = '\"';
  private String lastLineInserted = "";
  private int currLine = 0;

  public SqlLexer(String source) {
    this.source = new StringBuilder(source);
    this.strStore = new StrStore();
  }

  public SqlLexer() {
    this.source = new StringBuilder();
    this.strStore = new StrStore();
  }

  public void addLine(String line) {
    boolean hasString = line.contains("\"") || line.contains("\'") || line.contains("`");
    boolean hasEndPointer = line.contains(";");
    lastLineInserted = line;
    currLine++;
    if(hasString || hasEndPointer){
      source.append(line).append("\n");
      scanner();
      return;
    }
    source.append(line).append("\n");
    addToStore(line + "\n");
    cursor = source.toString().length();
  }

  public boolean isEof() {
    return cursor >= source.toString().length();
  }

  private void scanner() {
    while (!isEof()) {
      start = cursor;
      scanChars();
    }
  }

  private void scanChars() {
    char c = advance();
    if(isInsideString){
      addToStore(c);
      if(c == stringType){
        isInsideString = false;
      }
      return;
    }
    switch (c) {
      case '\'':
      case '"':
      case '`':
        advance();
        string(c);
        break;
      case ';':
        addToStore(c);
        endQuery();
        break;
      default:
        addToStore(c);
        break;
    }
  }

  private char peek() {
    if (isEof()) return '\0';
    return source.charAt(cursor);
  }


  private void string(char type) {
    while (peek() != type && !isEof()) {
      advance();
    }
    if (isEof()) {
      this.isInsideString = true;
      this.stringType = type;
      String str = source.substring(start, cursor);
      addToStore(str);
      return;
    }
    advance();
    String str = source.substring(start, cursor);
    addToStore(str);
  }

  private void endQuery() {
    start = 0;
    cursor = 0;
    source.setLength(0);
    strStore.cut();
  }

  public List<String> getQuerys() {
    return strStore.getData();
  }

  private void addToStore(String str) {
    this.strStore.addStr(str);
  }

  private void addToStore(char c) {
    this.strStore.addStr(Character.toString(c));
  }

  private char advance() {
    return source.charAt(cursor++);
  }
}
