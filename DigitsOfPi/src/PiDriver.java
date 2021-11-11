import java.util.Scanner;

public class PiDriver {

	public static void main(String args[]) {
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.println("enter file name");
			String filename = scanner.next();
			PiCounter scrabble = new PiCounter(filename);
			scrabble.processFile();
			scrabble.printReport();
			scanner.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
