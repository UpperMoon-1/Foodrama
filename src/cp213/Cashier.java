package cp213;

import java.util.Scanner;

/**
 * Wraps around an Order object to ask for MenuItems and quantities.
 *
 * @author Giuseppe Akbari
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2024-10-15
 */
public class Cashier {

	// private static final char[] "" = null;
	private Menu menu = null;

	/**
	 * Constructor.
	 *
	 * @param menu A Menu.
	 */
	public Cashier(Menu menu) {
		this.menu = menu;
	}

	/**
	 * Reads keyboard input. Returns a positive quantity, or 0 if the input is not a
	 * valid positive integer.
	 *
	 * @param scan A keyboard Scanner.
	 * @return
	 */
	private int askForQuantity(Scanner scan) {
		int quantity = 0;
		System.out.print("How many do you want? ");

		try {
			String line = scan.nextLine();
			quantity = Integer.parseInt(line);
		} catch (NumberFormatException nfex) {
			System.out.println("Not a valid number");
		}
		return quantity;
	}

	/**
	 * Prints the menu.
	 */
	private void printCommands() {
		System.out.println("\nMenu:");
		System.out.println(menu.toString());
		System.out.println("Press 0 when done.");
		System.out.println("Press any other key to see the menu again.\n");
	}

	/**
	 * Asks for commands and quantities. Prints a receipt when all orders have been
	 * placed.
	 *
	 * @return the completed Order.
	 */
	public Order takeOrder() {
		System.out.println("Welcome to WLU Foodorama!");

		// your code here
		// make scanner
		Scanner scanner = new Scanner(System.in);

		printCommands();

		Order order = new Order();

		while (true) {
			System.out.println("Command: ");
			String input = scanner.nextLine();
			try {
				int i = Integer.parseInt(input);
				if (i == 0) {// when it equals 0 it is fine
					System.out.println("----------------------------------------");
					System.out.println("Receipt");
					System.out.println(order.toString());
					break;
				} else if (i > 0 && i < 8) {// which means it is fine and we can continue
					// i is the item order
					MenuItem item = this.menu.getItem(i - 1);
					int quantity = askForQuantity(scanner);
					order.add(item, quantity);
				}

			} catch (NumberFormatException nfex) {
				System.out.println("Not a valid number");
				printCommands();
			}

		}

		return null;
	}
}