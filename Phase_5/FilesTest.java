import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
public class FilesTest {
	@Test
	public void mergeTransFilesTest() {
		Files1 file = new Files1();
		assertEquals("output.txt", mergeTransFiles("csci3060u_project", "output.txt"));
	}
}