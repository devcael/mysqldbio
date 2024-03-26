package utils;

import common.ConnectionBuilder;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionBuilderTest {
  @Test
  void createConnection() throws Exception {
    String username = "asnweb";
    String password = "webasnsoft@";
    Connection connection = ConnectionBuilder.builder().username(username).password(password).build().createConnection();
    assertFalse(connection.isClosed());
    connection.close();
    assertTrue(connection.isClosed());
  }
}