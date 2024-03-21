package com.infra.sqlsplitter;

import java.util.ArrayList;
import java.util.List;

public class SqlSplitter {
  public SqlSplitter() {
  }

  public String[] splitMySQL(String sql) {
    return split(SplitterType.MYSQL, sql, ';').toArray(new String[]{});
  }

  private List<String> split(SplitterType type, String sql, char delimiter) {
    List<String> querys = new ArrayList<>();
    boolean flag = true;
    String restOfQuery = null;
    while (flag) {
      if (restOfQuery == null) {
        restOfQuery = sql;
      }
      Tuple<String, String> statementAndRest = getStatements(restOfQuery, type, delimiter);
      String statement = statementAndRest.firstValue;
      if (statement != null && !statement.trim().isEmpty()) {
        querys.add(statement);
      }
      restOfQuery = statementAndRest.secondValue;
      if (restOfQuery == null || restOfQuery.trim().isEmpty()) {
        break;
      }
    }
    return querys;
  }

  private char nullChar(){
    return '\0';
  }

  public Tuple<String, String> getStatements(String query, SplitterType dbType, char delimiter) {
    char[] q_arr = query.toCharArray();
    char previousChar = nullChar();
    char nextChar = nullChar();
    char stringChar = nullChar();
    char commentChar = nullChar();
    char tagChar = nullChar();
    boolean isInComment = false;
    boolean isInString = false;
    boolean isInTag = false;

    for (int cursor = 0; cursor < q_arr.length; cursor++) {
      char ch = q_arr[cursor];
      previousChar = cursor > 0 ? q_arr[cursor - 1] : '\0';
      nextChar = cursor < (q_arr.length - 1) ? q_arr[cursor + 1] : '\0';

      if (previousChar != '\\' && (ch == '\'' || ch == '"') && !isInString && !isInComment) {
        isInString = true;
        stringChar = ch;
        continue;
      }

      if (((ch == '#' && nextChar == ' ') || (ch == '-' && nextChar == '-') || (ch == '/' && nextChar == '*')) && !isInString) {
        isInComment = true;
        commentChar = ch;
        continue;
      }

      if (isInComment && (((commentChar == '#' || commentChar == '-') && ch == '\n') || (commentChar == '/' && (ch == '*' && nextChar == '/')))) {
        isInComment = false;
        commentChar = nullChar();
        continue;
      }

      if (previousChar != '\\' && ch == stringChar && isInString) {
        isInString = false;
        stringChar = nullChar();
        continue;
      }

      if(Character.toLowerCase(ch) == Character.toLowerCase(delimiter) && !isInString && !isInComment){
        return getQuery(query, cursor, delimiter);
      }

    }
    if (query != null) {
      query = query.trim();
    }
    return new Tuple<>(query, null);
  }

  public Tuple<String, String> getQuery(String query, int splittingIndex , char delimiter){
    String statement = query.substring(0, splittingIndex);
    String restOfQuery = query.substring(splittingIndex + 1);
    if (statement != null) {
      statement = statement.trim();
    }
    return new Tuple<>(statement, restOfQuery);
  }
}
