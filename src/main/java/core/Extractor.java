package core;

import data.TableData;

import java.sql.SQLException;
import java.util.List;

public interface Extractor {
  public TableData getTableData(String tableName) throws SQLException, ClassNotFoundException;
  public List<TableData> getAllTableData();
}
