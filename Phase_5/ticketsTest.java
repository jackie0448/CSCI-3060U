import org.junit.Test;
import java.io.*;
import java.util.*;
import static org.junit.Assert.*;

public class ticketsTest {

  @Parameter
  public String ticketsDirectory;
  @Parameter
  public ArrayList<String> updatedParseTicketsList;

  @Test
  public void noTicketsFound(){
    testFileName = "noTickets.txt";
    List<String> testArray = new ArrayList<String>();
    try {
        File testFile = new File(testFileName);
        Scanner scanner = new Scanner(testFile);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            testArray.add(line);
        }
        scanner.close();
    } catch (IOException e) {
        System.out.println("No tickets found");
    }
    assertTrue(success);
  }

  @Test
  public void parseManyTickets(){
    testFileName = "manyTickets.txt";
    List<String> testArray = new ArrayList<String>();
    try {
        File testFile = new File(testFileName);
        Scanner scanner = new Scanner(testFile);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            testArray.add(line);
        }
        scanner.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    assertTrue(success);
  }

  @Test
  public void notEnoughTickets(){

  }

  @Test
  public void buyOneTicket(){
    Tickets tester = new Tickets();
    int tixQtyInFile = 1;
    assertEquals("True", true, tester.checkBuyTixValid("oneTicket.txt"));
  }

  @Test
  public void buyZeroTickets(){
    Tickets tester = new Tickets();
    int tixQtyInFile = 0;
    assertEquals("No tickets", false, tester.checkBuyTixValid("noTickets.txt"));
  }

  @Test
  public void addNewTicket(){
    Tickets tester = new Tickets();
    assertEquals("Added ticket", true, tester.editTixTextFile("manyTickets.txt"));
  }

  @Test
  public void removeExistingTicket(){
    Tickets tester = new Tickets();
    assertEquals("Removed ticket", true, tester.editTixTextFile("manyTickets.txt"));
  }

  @Test
  public void negativeTickets(){
    Tickets tester = new Tickets();
    assertEquals("Added ticket", true, tester.checkNegTix("manyTickets.txt"));
  }

  @Test
  public void negativeQuantity(){
    Tickets tester = new Tickets();
    assertEquals("Added ticket", true, tester.getQty("manyTickets.txt"));
  }

  @Test
  public void negativePrice(){
    Tickets tester = new Tickets();
    assertEquals("Added ticket", true, tester.getTixPrice("manyTickets.txt"));
  }
}
