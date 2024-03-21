package com.infra.sql_divider;

import static org.junit.jupiter.api.Assertions.*;

import com.infra.FileReader;
import com.infra.interfaces.FileReaderEvents;
import com.infra.sqlsplitter.SqlSplitter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

class SqlDividerTest {

  SqlLexer divider;

  @BeforeEach
  void setUp() {
    divider = new SqlLexer();
  }


  @Test
  public void ShouldStopTheWhileLoop(){
    boolean flag = true;
    int i = 0;
    while (flag){
      i++;
      System.out.println("Index: " + i);
      if(i > 10){
        break;
      }
    }
    System.out.println("Stopped");
  }

  @Test
  public void ShouldReturnTwoQuerysOnArray() {
    String mysqlSql = "CREATE TABLE users (id INT, name VARCHAR(255));\n" +
       "INSERT INTO users (id, name) VALUES (1, 'Jo" +
       "hn');\n" +
       "INSERT INTO users (id, name) VALUES (2, 'Doe');";

    String comments = "--\n" +
       "-- start table insert : cad_os\n" +
       "--";

    String cad_os_insert = "INSERT INTO `cad_os`(`id_os`, `id_cliente`, `id_tipo_os`, `data_abertura`, `hora_abertura`, `data_fechamento`, `hora_fechamento`, `status_os`, `info_cliente`, `id_tecnico1`, `id_tecnico2`, `nome_tecnico1`, `nome_tecnico2`, `nome_cliente`, `solucaoexecutada`, `Problema_constatado`, `nome_tipo`, `pessoa_contato`, `numeroparacontato`, `aberturavia`, `id_controle_de_qualidade`) VALUES\n" +
       "(7, 16, 2, '2019-02-03', '20:54:55', '2020-01-01', '21:45:33', 'Fechado', 'EERWRQWE', 4, null, 'ALAN', null, 'MERCADO ALMEIDA LTDA - ME', 'ggfsdgdfgfdgfdgfd', 'idjafihadsfi\t\t', 'CARTAO CREDITO', null, null, null, 0),\n" +
       "(9, 198, 1, '2020-01-01', '21:50:23', '2020-01-01', '16:51:36', 'Fechado', 'TRET', 4, null, 'ALAN', null, 'QUALIMAK FERRAMENTAS ELETRICAS LTDA', 'TESTE', 'TESTE', 'DIVERSOS', '3432', null, null, 0),\n" +
       "(10, 16, 1, '2020-01-01', '16:58:23', '2020-01-01', '09:13:31', 'Fechado', 'er', 1, null, 'UELBERTE', null, 'MERCADO ALMEIDA LTDA ', 'teste', 'teste\t', 'DIVERSOS', '', '', 'CELULAR', 0),\n" +
       "(11, 458, 1, '2020-01-01', '16:59:14', '2020-01-01', '17:06:26', 'Fechado', 'O LEITOR DO CERTIFICADO N�O ESTAVA FUNCIONANDO E TAMB�M FOI PRECISO FORMATAR O COMPUTADOR.\n" +
       "\n" +
       "', 0, null, 'ALAN', null, 'Z JIACHUAN PASTELARIA EIRELI ', 'REALIZEI A FORMATA��O DO COMPUTADOR E A TROCA DO LEITOR', 'REALIZEI A FORMATA��O DO COMPUTADOR E A TROCA DO LEITOR', 'DIVERSOS', '', '', 'CELULAR', 0),\n" +
       "(12, 557, 1, '2020-01-01', '16:33:39', '2020-01-01', '07:57:31', 'Fechado', 'ENVIAR UM ARQUIVO XML QUE A CONTABILIDADE SOLICITOU VIA E_MAIL.\n" +
       "\n" +
       "ACESSO ANYDESK: 450433103 ', 1, null, 'UELBERTE', null, 'ERLY SOUZA DOS SANTOS  ', 'Solicitei acesso remoto do cliente, fiz a exporta��o dos xml referente ao mes 01/2020 junto ao relatorio e enviei para a contabilidade via E-Mail.', 'ENVIAR UM ARQUIVO XML QUE A CONTABILIDADE SOLICITOU VIA E_MAIL.', 'DIVERSOS', 'Erike', '75 98833-1497', 'CELULAR', 0),\n" +
       "(13, 547, 1, '2020-01-01', '10:03:19', '2020-11-10', '10:45:25', 'Fechado', 'Duvida sobre o CFOP e XML da nota. ', 1, null, 'UELBERTE', null, 'VALDECI TEIXEIRA DA SILVA  ', 'teste', 'teste', 'DIVERSOS', 'VALDIRA', '75 3621-1839', 'CELULAR', 0),\n" +
       "(14, 534, 1, '2020-01-01', '10:19:33', '2020-01-01', '11:21:30', 'Fechado', 'Cliente falou que no cupom, quando ele alterou a quantidade do produto, o valor  inal do produto ficou incorreto. Ele quer saber como consertar o valor.\n" +
       "\n" +
       "Acesso remoto: 233240985. ', 1, null, 'UELBERTE', null, '3 M S MATERIAL DE CONSTRUCAO LTDA   ', 'solicitei para que o cliente clickasse no bot�o alterar pois assim o campo de lavor unitario n�o era mascarado. ', 'quando o cliente alterava um produto no pedido e digiava enter  o valor do produto era acrescido com zeros a sua esquerda fazendo assim que o valor ficasse diferente do atual ', 'DIVERSOS', 'Manoel ', '071 99332-1262', 'CELULAR', 0),\n" +
       "(15, 462, 1, '2020-01-01', '10:50:51', '2020-01-01', '11:32:41', 'Fechado', 'MOSTRAR O RELATORIO E SUA FUNCIONALIDADE, PARA A CLIENTE.\n" +
       "\n" +
       "ACESSO REMOTO: asnsoftware-pc-5@ad', 1, null, 'UELBERTE', null, 'DELMAR DUARTE DE MELO', 'Mostrei ao cliente como ta emitindo os 3 relatorios adicionados e tamb�m ensinei as suas funcionalidade campo a campo, ensinando tamb�m como alterar o valor de compra do produto para que ele fosse validado no relatorio de lucratividade.', 'MOSTRAR O RELATORIO E SUA FUNCIONALIDADE, PARA A CLIENTE.\n" +
       "', 'DIVERSOS', 'MONIQUE', '75 98301-1560', 'CELULAR', 0),\n" +
       "(16, 432, 1, '2020-01-01', '10:53:55', '2020-01-01', '11:43:17', 'Fechado', 'Cliente o vinculo da nota para a cliente, a nota j� est� minimizada na tela.\n" +
       "\n" +
       "Aceso remoto: 946726151', 1, null, 'UELBERTE', null, 'GILDELICE CONCEICAO DE JESUS', 'Fiz o vinculo de cfop 5401 para 1401 e do cst 202 para 500.', 'Fazer vinculo de cfop e cts no produto durante a importa��o de xml.', 'DIVERSOS', 'Gildelice', '71 98775-0807', 'CELULAR', 0);";
    String[] mysqlStatements = new SqlSplitter().splitMySQL(mysqlSql + comments + cad_os_insert);
    System.out.println("MySQL Statements:");
    for (String statement : mysqlStatements) {
      System.out.println(statement);
      System.out.println("-----------");
    }

  }

  @Test
  public void ShouldLoadSqlFileAndSepareteAlLStrings(){
    SqlSplitter splitter = new SqlSplitter();
    FileReader fileReader = new FileReader("/cad_os.sql");
    assertTrue(fileReader.fileExists());
    try{
      String file = fileReader.read();
      String[] statements = splitter.splitMySQL(file);
      for (String statement : statements) {
        System.out.println("--------------------------------");
        System.out.println(statement);
      }
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  @Test
  public void ShouldReturnOneQuery() {
    String[] lines = new String[]{
       "SELECT * FROM",
       "produto WHERE produto.codigo  \"M;icael\" = 1;",
       "SELECT * FROM",
       "produto WHERE produto.codigo  'Mi",
       "cael' = 1;"
    };
    for (String line : lines) {
      divider.addLine(line);
    }
    assertEquals(2, divider.getQuerys().size());
    for (String query : divider.getQuerys()) {
      System.out.println("--------------------------------");
      System.out.println(query);
    }
  }

  @Test
  public void ShouldLoadTestFileAndGetQueries() {
    FileReader fileReader = new FileReader("/cad_os.sql");
    assertTrue(fileReader.fileExists());
    fileReader.addReadeLineEvent(new FileReaderEvents() {
      @Override
      public void readLine(String line) {

      }

      @Override
      public void completed() {

      }

      @Override
      public void error(Exception e) {

      }
    });
    fileReader.start();
    for (String query : divider.getQuerys()) {
      System.out.println("--------------------------------");
      System.out.println(query);
    }
  }
}