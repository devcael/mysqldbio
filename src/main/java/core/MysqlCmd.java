package core;

import common.ConnectionBuilder;
import data.ColumnData;
import data.TableData;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class MysqlCmd implements Extractor {
  private Connection conn;

  private List<String> getAllTablesNames() throws SQLException {
    List<String>  tableNames = new ArrayList<>();
    String[] types = {"TABLE"};
    DatabaseMetaData md = conn.getMetaData();
    String currSchema = conn.getCatalog();
    ResultSet rs = md.getTables(currSchema, null, "%", types);
    while (rs.next()){
      String tableName = rs.getString("TABLE_NAME");
      if (tableName != null){
        tableNames.add(tableName);
      }
    }
    return tableNames;
  }

  private String removeAutoIncrementClause(String sql){
    String regex = "(?i)AUTO_INCREMENT=\\d+";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(sql);
    return matcher.replaceAll("");
  }

  private String getCreateTableStatement(String tableName) throws SQLException {
    String sql = "SHOW CREATE TABLE " + tableName;
    PreparedStatement statement = conn.prepareStatement(sql);
    ResultSet rs = statement.executeQuery();
    String createStatement = null;
    if(rs.next()){
      String createTable = rs.getString(2);
      createStatement = removeAutoIncrementClause(createTable);
    }
    return createStatement;
  }

  private double getTableSizeKb(String tableName) throws SQLException {
    PreparedStatement statement = conn.prepareStatement("SELECT \n" +
       "ROUND(SUM(data_length + index_length) / 1024, 2) AS 'kbs'\n" +
       "FROM information_schema.tables\n" +
       "WHERE table_schema = ? AND table_name = ?;");
    statement.setString(1, conn.getCatalog());
    statement.setString(2, tableName);
    ResultSet rs = statement.executeQuery();
    if(rs.next()){
      return rs.getDouble("kbs");
    }else{
      return 0.00;
    }
  }

  @Override
  public TableData getTableData(String tableName) throws SQLException, ClassNotFoundException {
    String getAllDataQuery = "SELECT * FROM " + tableName + " LIMIT 1";
    String countQuery = "SELECT COUNT(*) as count FROM " + tableName;
    ResultSet rsCount = conn.createStatement().executeQuery(countQuery);
    ResultSet rsFindData = conn.createStatement().executeQuery(getAllDataQuery);
    ResultSetMetaData metaData = rsFindData.getMetaData();
    int columnCount = metaData.getColumnCount();
    int rowsCount = 0;
    String createTable = getCreateTableStatement(tableName);
    if (rsCount.next()) {
      rowsCount = rsCount.getInt("count");
    }
    List<ColumnData> columnData = new ArrayList<>();
    for (int i = 0; i < columnCount; i++) {
      String name = metaData.getColumnName(i + 1);
      Class<?> type = Class.forName(metaData.getColumnClassName(i + 1));
      columnData.add(ColumnData.builder().name(name).type(type).build());
    }
    return TableData
       .builder()
       .name(tableName)
       .columnCount(columnCount)
       .size_kb(getTableSizeKb(tableName))
       .createTableStatement(createTable)
       .columns(columnData.toArray(new ColumnData[]{}))
       .rows(rowsCount)
       .build();
  }

  @Override
  public List<TableData> getAllTableData() {
    try {
      List<TableData> data = new ArrayList<>();
      List<String> tableNames = getAllTablesNames();
      for (String table : tableNames){
        TableData tableData = getTableData(table);
        data.add(tableData);
      }
      return data;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
