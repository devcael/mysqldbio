package infraestructure;

import com.infra.FileReader;
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
  public void ShouldReturnFourLines(){
    assertTrue(fileReader.fileExists());
    List<String> lines = new ArrayList<>();
    fileReader.addReadeLineEvent(lines::add);
    fileReader.start();
    assertEquals(4, lines.size());
  }

  @Test
  public void ShouldPauseWithTwoLines(){
    assertTrue(fileReader.fileExists());
    List<String> lines = new ArrayList<>();
    final int[] count = {0};
    fileReader.addReadeLineEvent(line -> {
      lines.add(line);
      count[0]++;
      if(count[0] == 2){
        fileReader.pause();
      }
    });
    fileReader.start();
    assertEquals(2, lines.size());
  }

  @Test
  public void ShouldReturnFourLineAfterRestart(){
    assertTrue(fileReader.fileExists());
    List<String> lines = new ArrayList<>();
    final int[] count = {0};
    fileReader.addReadeLineEvent(line -> {
      lines.add(line);
      count[0]++;
      if(count[0] == 2){
        fileReader.pause();
        fileReader.continueReader();
      }
    });
    fileReader.start();
    assertEquals(4, lines.size());
  }
}