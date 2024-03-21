package infraestructure;

import com.infra.FileReader;
import com.infra.interfaces.FileReaderEvents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {
  FileReader fileReader;

  @BeforeEach
  void setUp() {
    fileReader = new FileReader("/file_test.txt");
  }


  @Test
  public void ShouldReturnAllFileIntoString() throws Exception {
    long startMillis = System.currentTimeMillis();
    FileReader fileReader = new FileReader("/cad_os.sql");
    String file = fileReader.read();
    long endMillis = System.currentTimeMillis();
    System.out.println("Time in seconds: " + (endMillis - startMillis) / 1000);
    assertNotNull(file);
    assertTrue(file.length() > 0);
  }

  @Test
  public void ShouldReturnFourLines(){
    assertTrue(fileReader.fileExists());
    List<String> lines = new ArrayList<>();
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
    assertEquals(4, lines.size());
  }

  @Test
  public void ShouldPauseWithTwoLines(){
    assertTrue(fileReader.fileExists());
    List<String> lines = new ArrayList<>();
    final int[] count = {0};
//    fileReader.addReadeLineEvent(line -> {
//      lines.add(line);
//      count[0]++;
//      if(count[0] == 2){
//        fileReader.pause();
//      }
//    });
    fileReader.start();
    assertEquals(2, lines.size());
  }

  @Test
  public void ShouldReturnFourLineAfterRestart(){
    assertTrue(fileReader.fileExists());
    List<String> lines = new ArrayList<>();
    final int[] count = {0};
//    fileReader.addReadeLineEvent(line -> {
//      lines.add(line);
//      count[0]++;
//      if(count[0] == 2){
//        fileReader.pause();
//        fileReader.continueReader();
//      }
//    });
    fileReader.start();
    assertEquals(4, lines.size());
  }
}