import java.io.IOException;

public class PiCounter extends TextFileAccessor {
	private final int[] NUMBERS = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	private final int ASCII_CODE_0 = 48;
	private int badScore;

	public PiCounter(String filename) throws IOException {
		openFile(filename);

	}

	@Override
	protected void processLine(String curLine) {
		for (int i = 0; i < curLine.length(); i++) {
			// char ch =
			// is this right?
			if (Character.isDigit(ASCII_CODE_0) && getLetterPos(ch) < 26) {
				int letterScore = (SCRABBLE_SCORES[getLetterPos(ch)]);

				if (i % 4 == 0) {
//					letterScore *= 2; Same thing as below but different format
					letterScore = letterScore * 2;

				} else if (i % 9 == 0) {

					letterScore = letterScore * 3;

				}

				curLineScore = curLineScore + letterScore;
			}

		}
		if (highestScore < curLineScore) {
			highestScore = curLineScore;
			winningString = curLine;
		}
	}

	private int getLetterPos(char ch) {
		return ch - ASCII_CODE_a;
	}

	@Override
	public void printReport() {
		System.out.println("winner: \"" + winningString + "\" score: " + highestScore);

	}

}
