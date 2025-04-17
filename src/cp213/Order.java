package cp213;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores a HashMap of MenuItem objects and the quantity of each MenuItem
 * ordered. Each MenuItem may appear only once in the HashMap.
 *
 * @author Giuseppe Akbari
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2024-10-15
 */
public class Order implements Printable {

	private static final String lineFormat = "%-14s%2d @ $%5.2f = $%6.2f\n";
	private static final String totalFormat = "%-9s                   $%6.2f\n";
	/**
	 * The current tax rate on menu items.
	 */
	public static final BigDecimal TAX_RATE = new BigDecimal(0.13);

	// define a Map of MenuItem objects
	// Note that this must be a *Map* of some flavour
	// @See
	// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Map.html

	// your code here
	HashMap<MenuItem, Integer> m = new HashMap<>();

	/**
	 * Increments the quantity of a particular MenuItem in an Order with a new
	 * quantity. If the MenuItem is not in the order, it is added.
	 *
	 * @param item     The MenuItem to purchase - the HashMap key.
	 * @param quantity The number of the MenuItem to purchase - the HashMap value.
	 */
	public void add(final MenuItem item, final int quantity) {

		// your code here
		if (m.containsKey(item)) {
			this.update(item, quantity);
		} else {

			// quantity is greater than 0
			if (quantity > 0) {
				m.put(item, quantity);

				// quantity is 0
			} else {
				m.remove(item);
			}
		}

	}

	/**
	 * Calculates the total value of all MenuItems and their quantities in the
	 * HashMap.
	 *
	 * @return the total cost for the MenuItems ordered.
	 */
	public BigDecimal getSubTotal() {

		// your code here
		// BigDecimal num = BigDecimal.ZERO;
		// BigDecimal tot = BigDecimal.ZERO;
		// BigDecimal quant = BigDecimal.ZERO;
		BigDecimal moo = BigDecimal.ZERO;
		for (Map.Entry<MenuItem, Integer> item : m.entrySet()) {
			BigDecimal quant = BigDecimal.valueOf(item.getValue());
			BigDecimal tot = item.getKey().getPrice();
			BigDecimal num = tot.multiply(quant);
			moo = moo.add(num);
		}
		return moo;
	}

	/**
	 * Calculates and returns the total taxes to apply to the subtotal of all
	 * MenuItems in the order. Tax rate is TAX_RATE.
	 *
	 * @return total taxes on all MenuItems
	 */
	public BigDecimal getTaxes() {

		// your code here

		// BigDecimal num = getSubTotal();

		MathContext m = new MathContext(2);
		BigDecimal num = this.getSubTotal().multiply(TAX_RATE).round(m);
		// System.out.println(num);

		return num;
	}

	/**
	 * Calculates and returns the total cost of all MenuItems order, including tax.
	 *
	 * @return total cost
	 */
	public BigDecimal getTotal() {

		// your code here

		return getTaxes().add(getSubTotal());
	}

	/*
	 * Implements the Printable interface print method. Prints lines to a Graphics2D
	 * object using the drawString method. Prints the current contents of the Order.
	 */
	@Override
	public int print(final Graphics graphics, final PageFormat pageFormat, final int pageIndex)
			throws PrinterException {
		int result = PAGE_EXISTS;

		if (pageIndex == 0) {
			final Graphics2D g2d = (Graphics2D) graphics;
			g2d.setFont(new Font("MONOSPACED", Font.PLAIN, 12));
			g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
			// Now we perform our rendering
			final String[] lines = this.toString().split("\n");
			int y = 100;
			final int inc = 12;

			for (final String line : lines) {
				g2d.drawString(line, 100, y);
				y += inc;
			}
		} else {
			result = NO_SUCH_PAGE;
		}
		return result;
	}

	/**
	 * Removes an item from an order entirely.
	 * 
	 * @param item The MenuItem to completely remove.
	 */
	public void removeAll(final MenuItem item) {

		m.remove(item);
	}

	/**
	 * Returns a String version of a receipt for all the MenuItems in the order.
	 */
	@Override
	public String toString() {

		// your code here
		String s = "";
		BigDecimal num = BigDecimal.ZERO;
		for (Map.Entry<MenuItem, Integer> item : m.entrySet()) {
			s += String.format(lineFormat, item.getKey().getListing(), item.getValue(), item.getKey().getPrice(),
					num.add(item.getKey().getPrice().multiply(BigDecimal.valueOf(item.getValue()))));
		}
		s += String.format(totalFormat, "Subtotal:", getSubTotal());
		s += String.format(totalFormat, "Taxes:", getTaxes());
		s += String.format(totalFormat, "Total:", getTotal());
		// System.out.println("HI");
		return s;
	}

	/**
	 * Replaces the quantity of a particular MenuItem in an Order with a new
	 * quantity. If the MenuItem is not in the order, it is added. If quantity is 0
	 * or negative, the MenuItem is removed from the Order.
	 *
	 * @param item     The MenuItem to update
	 * @param quantity The quantity to apply to item
	 */
	public void update(final MenuItem item, final int quantity) {

		// your code here
		if (quantity > 0) {
			int amount = m.get(item);
			// update quantity
			m.put(item, amount + quantity);
		} else {
			// remove item from hashmap
			m.remove(item);
		}
	}
}