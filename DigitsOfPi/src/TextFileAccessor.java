import java.util.Scanner;
import java.io.IOException;
import java.io.FileReader;

public abstract class TextFileAccessor {
	protected String fileName;
	protected Scanner scan;

	// throws a FileNotFoundException if can't open file
	public void openFile(String fn) throws IOException {
		fileName = fn;
		scan = new Scanner(new FileReader(fileName));
	}

	public void processFile() {
		while (scan.hasNext()) {
			processLine(scan.nextLine());
		}
		scan.close();
	}

	protected abstract void processLine(String line);

	public abstract void printReport();

}
