import java.util.Scanner;
import java.io.IOException;
import java.io.FileReader;

/**
 * Provides a template for a file to be accessed
 * 
 * @author sagesilberman
 *
 */
public abstract class TextFileAccessor {
	protected String fileName;
	protected Scanner scan;

	/**
	 * Opens a file and throws an IOException if a file can't open
	 * 
	 * @param fn the file name
	 * @throws IOException if a file can't open
	 */
	public void openFile(String fn) throws IOException {
		fileName = fn;
		scan = new Scanner(new FileReader(fileName));
	}

	/**
	 * Processes a file by scanning each line
	 */
	public void processFile() {
		while (scan.hasNext()) {
			processLine(scan.nextLine());
		}
		scan.close();
	}

	/**
	 * Processes a Line with the string line from the file
	 * 
	 * @param line the string line from a file
	 */
	protected abstract void processLine(String line);

	/**
	 * Prints the report for a given file
	 */
	public abstract void printReport();

}
