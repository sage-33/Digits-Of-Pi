import java.util.Scanner;

/**
 * If given a file, run Pi Driver and calculate the distributions of numbers in
 * said file and how many non numbers there are.
 * 
 * @author sagesilberman
 *
 */
public class PiDriver {

	public static void main(String args[]) {
		try {
			PiCounter e = new PiCounter("corruptedDigitsOfPi.txt");
			e.processFile();
			e.printReport();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
