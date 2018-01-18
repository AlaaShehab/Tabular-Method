import java.util.Scanner;

/**
 *
 */

/**
 * @author Personal
 *
 */
public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {


		TabularMethod start = new TabularMethod();
		Scanner scan = new Scanner(System.in);
		while (true) {
			String input;
			boolean valid = true;
			do {
				valid = true;
				System.out.println("Enter 'F' to choose Minterms from file or 'M' to Enter Manual:");
				input = scan.nextLine();
				char ch = input.charAt(0);
				ch = Character.toUpperCase(ch);
				if (ch == 'F') {
					System.out.println("Enter File pathname:");
					String pathname = scan.nextLine();
					start.readfile(pathname);
				} else if (ch == 'M') {
					start.scan();
				} else {
					System.out.println("Invalid Input");
					valid = false;
				}
			} while (!valid);

			start.tabularTable();
			System.out.println();
			System.out.println("To continue playing press any key or 'E' to Exit");
			input = scan.nextLine();
			char ch = input.charAt(0);
			ch = Character.toUpperCase(ch);
			if (ch == 'E') {
				return;
			}
		}

	}
}