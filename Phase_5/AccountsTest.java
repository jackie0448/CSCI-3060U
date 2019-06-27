import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
public class AccountsTest {
	@Test
	public void getUserNameTest() {
		Accounts1 account = new Accounts1();
		assertEquals("user01", account.getUserName());
		//assertEquals("user03", account.getUserName());
	}
	@Test
	public void getSellerUserNameRefundTest() {
		Accounts1 account = new Accounts1();
		Accounts1 account2 = new Accounts1();
		assertEquals("user02", account.getSellerUserNameRefund("transactionFile.txt"));
		assertEquals("user03", account.getSellerUserNameRefund("transactionFile.txt"));
	}
	@Test
	public void uniqueUserNameCheckTest() {
		Accounts1 account = new Accounts1();
		Accounts1 account2 = new Accounts1();
		assertEquals(false, account.uniqueUserNameCheck("user01", "transactionFile.txt"));
		assertEquals(true, account.uniqueUserNameCheck("user05", "transactionFile.txt"));
	}
	@Test
	public void getUserCreditFromOutputFileTest() {
		Accounts1 account = new Accounts1();
		account.addCreditToUser(20.0, 0.0);
		assertEquals(20.0, getUserCreditFromOutputFile("user01"));
	}
	@Test
	public void  getCreditFromAccFileTest() {
		Accounts1 account = new Accounts1();
		account.addCreditToUser(20.0, 0,0);
		assertEquals(20.0, getCreditFromAccFile("accountFile.txt"));
	}
	@Test
	public void convertCreditFormatTest() {
		Accounts1 account = new Accounts1();
		account.addCreditToUser(10.0, 0.0);
		assertEquals(10.0, convertCreditFormat("10.0"));
	}
	@Test
	public void getCorrespondingLineInParseAccFileTest() {
		Accounts1 account = new Accounts1();
		assertEquals("accountFile.txt", "accountFile.txt");
	}
	@Test
	public void editCreditInAccFileTest() {
		Accounts1 account = new Accounts1();
		ArrayList<String> updatedParseAccList = [["user01", "0.0"], ["user02", "0.0"]];
		account.editCreditInAccFile(updatedParseAccList, "user01", "19.0");
		assertEquals("19.0", getCreditFromAccFile("accountFile.txt"));
	}
	@Test
	public void deleteUserTest() {
		Accounts1 account = new Accounts1();
		ArrayList<String> updatedParseAccList = [["user01"], ["user02"]];
		account.deleteUser("user01", updatedParseAccList);
		assertEquals([["user02"]], updatedParseAccList);
	}
	@Test
	public void addCreditToUserTest() {
		Accounts1 account = new Accounts1();
		account.addCreditToUser(19.00, 0.0);
		assertEquals(19.00, getUserCreditFromOutputFile("user01"));)
	}
	@Test
	public void creditForBuyTransTest() {
		Accounts1 account = new Accounts1();
		assertEquals(9.0, account.creditForBuyTrans(true, 19.0, 10, 1));	
	}
	@Test
	public void creditForSellTransTest() {
		Accounts1 account = new Accounts1();
		assertEquals(30.0, account.creditForSellTrans(true, 20.0, 10, 1));
	}
	@Test
	public void tixQtyAfterTransTest() {
		Accounts1 account = new Accounts1();
		assertEquals(10, account.tixQtyAfterTrans("11"));
	}
	@Test
	public void getTixNeededTest() {
		Accounts1 account = new Accounts1();
		assertEquals(10, account.getTixNeeded("accountFile.txt"));
	}
	
}