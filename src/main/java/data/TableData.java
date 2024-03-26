package data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TableData {
  String name;
  String createTableStatement;
  int rows;
  int columnCount;
  double size_kb;
  ColumnData[] columns;

  public String getCreateIfNotExists() {
    if (createTableStatement == null) {
      return null;
    }
    if (createTableStatement.toUpperCase().contains("CREATE TABLE") && !createTableStatement.toUpperCase().contains("IF NOT EXISTS")) {
      createTableStatement = createTableStatement.replaceFirst("(?i)CREATE TABLE", "CREATE TABLE IF NOT EXISTS");
    }
    return createTableStatement;
  }

  public String getInsertHeader() {
    StringBuilder builder = new StringBuilder("INSERT INTO ");
    builder.append("`").append(name).append("` (");
    for (int i = 0; i < columns.length; i++) {
      builder.append("`").append(columns[i].name).append("`");
      if (i != (columns.length - 1)) {
        builder.append(",");
      }
    }
    for (ColumnData col : columns) {
    }
    builder.append(") VALUES ");
    return builder.toString();
  }
}
