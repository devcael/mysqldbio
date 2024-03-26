package common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Logger;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionBuilder {
  private String db_name;
  private String username;
  private String password;
  private String driverClass;
  private String ip;
  private String port;
  private Connection doConnect(String driver, String url, String username, String password) throws SQLException, ClassNotFoundException {
    Class.forName(driver);
    return  DriverManager.getConnection(url, username, password);
  }
  public Connection createConnection() throws Exception {
    StringBuilder builder = new StringBuilder();

    if(this.ip == null && this.port == null) {
      this.ip = "localhost";
      this.port = "3306";
    }

    String url = "jdbc:mysql://" + this.ip + ":" + this.port +"/";
    builder.append(url);
    if(this.db_name != null){
      builder.append(this.db_name);
    }
    builder.append("?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false");
    String driver = (Objects.isNull(this.driverClass) || this.driverClass.isEmpty()) ? "com.mysql.cj.jdbc.Driver" : this.driverClass;
    return doConnect(driver, builder.toString(), this.username, password);
  }
}
