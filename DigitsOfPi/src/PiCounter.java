import java.io.IOException;

/**
 * Represents a file consisting of all digits of pi where each character is
 * scanned. Based on the character, the score for a digit or bad symbol is
 * increased until there are no more characters, resulting with the total number
 * of each digit from 0 to 9 and the total number of non digit (bad) symbols
 * being printed out
 *
 * @author sagesilberman
 *
 */
public class PiCounter extends TextFileAccessor {
	private int[] scores = new int[10]; // scores array
	private final int ASCII_CODE_0 = 48; // a number that represents the number "0"
	private int badScore; // holds the number of non-numbers present

	/**
	 * Initializes the code by setting score as an array with ten values and
	 * badScore with a starting value of 0. Opens the file but throws IOException if
	 * it can't open the file
	 * 
	 * @param filename
	 * @throws IOException if file can't be opened
	 */
	public PiCounter(String filename) throws IOException {
		openFile(filename);

	}

	@Override
	protected void processLine(String curLine) {
		char[] ch = curLine.toCharArray();
		for (int i = 0; i < curLine.length(); i++) {
			if (Character.isDigit(ch[i])) {
				scores[ch[i] - ASCII_CODE_0]++;
			}

			else {
				badScore++;
			}
		}
	}

	@Override
	public void printReport() {
		for (int i = 0; i < scores.length; i++) {
			System.out.println(i + " " + scores[i]);
		}
		System.out.println("bad symbols " + badScore);

	}

}
