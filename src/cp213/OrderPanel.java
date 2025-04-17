package cp213;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The GUI for the Order class.
 *
 * @author your name here
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2024-10-15
 */
@SuppressWarnings("serial")
public class OrderPanel extends JPanel {

	/**
	 * Implements a FocusListener on a JTextField. Accepts only positive integers,
	 * all other values are reset to 0. Adds a new MenuItem to the Order with the
	 * new quantity, updates an existing MenuItem in the Order with the new
	 * quantity, or removes the MenuItem from the Order if the resulting quantity is
	 * 0.
	 */
	private class QuantityListener implements FocusListener {
		private MenuItem item = null;

		QuantityListener(final MenuItem item) {
			this.item = item;
		}

		// your code here
		@Override
		public void focusLost(FocusEvent e) {

			JTextField source = (JTextField) e.getSource();

			// quantity entered into the JTextField
			String txt = source.getText();

			int quantity;

			try {

				// clear item from existing order
				order.removeAll(this.item);

				// ensures quantity is numeric
				quantity = Integer.parseInt(txt);
				// order.update(this.item, quantity);

				// valid quantity
				if (quantity > 0) {

					// add the item into the order with the quantity entered
					order.add(this.item, quantity);

					// invalid quantity
				} else {
					order.removeAll(item);
					source.setText("0");

				}

				// quantity entered is a string
			} catch (NumberFormatException e1) {
				source.setText("0");
			}
			updateTotals();
		}

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub

		}
	}

	// Attributes
	private Menu menu = null;
	private final Order order = new Order();
	private final DecimalFormat priceFormat = new DecimalFormat("$##0.00");
	private final JButton printButton = new JButton("Print");
	private final JLabel subtotalLabel = new JLabel("$0.00");
	private final JLabel taxLabel = new JLabel("$0.00");
	private final JLabel totalLabel = new JLabel("$0.00");
	private final JLabel tax = new JLabel("Tax:");
	private final JLabel subtotal = new JLabel("Subtotal:");
	private final JLabel total = new JLabel("Total:");

	// theheaders which will be at the top
	private final JLabel itemheader = new JLabel("Item", JLabel.CENTER);
	private final JLabel priceheader = new JLabel("Price", JLabel.CENTER);
	private final JLabel amountheader = new JLabel("Quantity", JLabel.CENTER);

	private JLabel nameLabels[] = null;
	private JLabel priceLabels[] = null;
	// TextFields for menu item quantities.
	private JTextField quantityFields[] = null;
	private JPanel totalsPanel = new JPanel();
	private JPanel headerPanel = new JPanel(new GridLayout(1, 3, 5, 5));

	/**
	 * Displays the menu in a GUI.
	 *
	 * @param menu The menu to display.
	 */
	public OrderPanel(final Menu menu) {
		this.menu = menu;
		this.nameLabels = new JLabel[this.menu.size()];
		this.priceLabels = new JLabel[this.menu.size()];
		this.quantityFields = new JTextField[this.menu.size()];
		this.layoutView();
		this.registerListeners();
	}

	/**
	 * Implements an ActionListener for the 'Print' button. Prints the current
	 * contents of the Order to a system printer or PDF.
	 */
	private class PrintListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {

			// your code here
			// create printer job
			PrinterJob job = PrinterJob.getPrinterJob();

			// order is to be printed
			job.setPrintable(order);

			// call the Order class print() abstract method to print contents
			try {
				job.print();
			} catch (PrinterException e1) {
				System.out.println("There was an error printing the receipt. Please try again.");
			}

		}
	}

	/**
	 * Uses the GridLayout to place the labels and buttons.
	 */
	private void layoutView() {

		// your code here
		// setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setLayout(new BorderLayout(10, 10));
		headerPanel.add(itemheader);
		headerPanel.add(priceheader);
		headerPanel.add(amountheader);

		// middle
		JPanel menuPanel = new JPanel(new GridLayout(menu.size(), 3, 5, 5));
		for (int i = 0; i < menu.size(); i++) {
			MenuItem f = menu.getItem(i);

			nameLabels[i] = new JLabel(f.getListing(), JLabel.CENTER);
			priceLabels[i] = new JLabel(priceFormat.format(f.getPrice()), JLabel.CENTER);

			quantityFields[i] = new JTextField("0");
			quantityFields[i].setHorizontalAlignment(JTextField.CENTER);
			quantityFields[i].addFocusListener(new QuantityListener(f));
			// them to the screen
			menuPanel.add(nameLabels[i]);
			menuPanel.add(priceLabels[i]);
			menuPanel.add(quantityFields[i]);
		}

		JPanel subtotalPanel = new JPanel(new BorderLayout());

		JPanel taxPanel = new JPanel(new BorderLayout());

		JPanel totalPanel = new JPanel(new BorderLayout());

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(printButton);
		subtotalPanel.add(subtotal, BorderLayout.WEST);
		subtotalPanel.add(subtotalLabel, BorderLayout.EAST);
		taxPanel.add(tax, BorderLayout.WEST);
		taxPanel.add(taxLabel, BorderLayout.EAST);
		totalPanel.add(total, BorderLayout.WEST);
		totalPanel.add(totalLabel, BorderLayout.EAST);

		totalsPanel.add(subtotalPanel);
		totalsPanel.add(taxPanel);
		totalsPanel.add(totalPanel);
		totalsPanel.add(buttonPanel);

		add(headerPanel, BorderLayout.NORTH);
		add(menuPanel, BorderLayout.CENTER);
		add(totalsPanel, BorderLayout.SOUTH);
	}

	/**
	 * Register the widget listeners with the widgets.
	 */
	private void registerListeners() {
		// Register the PrinterListener with the print button.
		this.printButton.addActionListener(new PrintListener());

	}

	/**
	 * Updates the subtotal, tax, and total labels.
	 */
	private void updateTotals() {
		subtotalLabel.setText(priceFormat.format(order.getSubTotal()));
		taxLabel.setText(priceFormat.format(order.getTaxes()));
		totalLabel.setText(priceFormat.format(order.getTotal()));
	}

}