package presentationL;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import businessL.BaseProduct;
import businessL.CompositeProduct;
import businessL.MenuItem;
import dataL.FileWriter;
import ro.tuc.tp.assig4.App;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JCheckBox;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AdministratorGraphicalUserInterface extends JFrame {
	private JTextField nameTextField;
	private JTextField priceTextField;
	private JTable menuItemsTable;
	private final JList<BaseProduct> productList;
	private static DefaultListModel<BaseProduct> lModel;
	private JTextField newNameTextField;
	private JTextField newPriceTextField;
	private JLabel lblNewPrice;
	private JLabel lblNewName;
	private JButton btnEdit;
	private JButton btnEditMenuItem;

	public static void refreshModel(float price, String name) {
		lModel.addElement(new BaseProduct(price, name));
	}

	public void refreshTable() {
		DefaultTableModel model = (DefaultTableModel) menuItemsTable.getModel();
		int rows = model.getRowCount();
		for (int i = rows - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		for (MenuItem item : App.getRestaurant().getMenuItems()) {
			if (item instanceof BaseProduct) {
				model.addRow(new Object[] { item.getName(), item.getPrice(), "-" });
			} else if (item instanceof CompositeProduct) {
				model.addRow(
						new Object[] { item.getName(), item.getPrice(), ((CompositeProduct) item).listToString() });
			}
		}
	}

	public MenuItem getItemByName(String name) {
		MenuItem searchedItem = null;
		for (MenuItem item : App.getRestaurant().getMenuItems()) {
			if (item.getName().equals(name)) {
				searchedItem = item;
			}
		}
		return searchedItem;
	}

	public AdministratorGraphicalUserInterface() {
		setTitle("Administrator UI");
		setBounds(400, 50, 900, 500);
		getContentPane().setLayout(null);

		JLabel lblCreateNewMenu = new JLabel("NEW  MENU  ITEM");
		lblCreateNewMenu.setBounds(10, 11, 147, 14);
		getContentPane().add(lblCreateNewMenu);

		nameTextField = new JTextField();
		nameTextField.setBounds(10, 52, 147, 20);
		getContentPane().add(nameTextField);
		nameTextField.setColumns(10);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 36, 88, 14);
		getContentPane().add(lblName);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(10, 83, 88, 14);
		getContentPane().add(lblPrice);

		priceTextField = new JTextField();
		priceTextField.setBounds(10, 100, 147, 20);
		getContentPane().add(priceTextField);
		priceTextField.setColumns(10);

		final JCheckBox checkBoxComposite = new JCheckBox("Composite");
		checkBoxComposite.setBounds(10, 138, 147, 28);
		getContentPane().add(checkBoxComposite);

		JLabel lblProductsOf = new JLabel("*Products of which the");
		lblProductsOf.setBounds(10, 173, 147, 14);
		getContentPane().add(lblProductsOf);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 213, 147, 145);
		getContentPane().add(scrollPane);

		final ArrayList<BaseProduct> list = new ArrayList<BaseProduct>();

		for (MenuItem item : App.getRestaurant().getMenuItems()) {
			if (item instanceof BaseProduct) {
				list.add((BaseProduct) item);
			}
		}

		lModel = new DefaultListModel<BaseProduct>();
		for (BaseProduct p : list) {
			lModel.addElement(p);
		}

		productList = new JList<BaseProduct>();
		productList.setModel(lModel);
		scrollPane.setViewportView(productList);

		JButton btnDone = new JButton("Done");
		btnDone.setBounds(10, 369, 147, 38);
		btnDone.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub\
				if (checkBoxComposite.isSelected()) {
					if (!nameTextField.getText().isEmpty()) {
						ArrayList<BaseProduct> compositeProductList = new ArrayList<BaseProduct>();
						for (Object o : productList.getSelectedValues()) {
							BaseProduct product = (BaseProduct) o;
							compositeProductList.add(product);
						}
						App.getRestaurant().createMenuItem(1, nameTextField.getText().trim(),
								checkBoxComposite.isSelected(), compositeProductList);
						WaiterGraphicalUserInterface.updateProductList(getItemByName(nameTextField.getText().trim()));
						refreshTable();
					} else {
						JOptionPane.showMessageDialog(null, "Complete necessary fields");
					}
				} else if (!nameTextField.getText().isEmpty() && !priceTextField.getText().isEmpty()) {
					try {
						float price = Float.parseFloat(priceTextField.getText().trim());
						ArrayList<BaseProduct> compositeProductList = new ArrayList<BaseProduct>();
						for (Object o : productList.getSelectedValues()) {
							BaseProduct product = (BaseProduct) o;
							compositeProductList.add(product);
						}
						App.getRestaurant().createMenuItem(price, nameTextField.getText().trim(),
								checkBoxComposite.isSelected(), compositeProductList);
						WaiterGraphicalUserInterface.updateProductList(getItemByName(nameTextField.getText().trim()));
						refreshTable();
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "Error parsing the price.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Complete the necessary fields");
				}

			}
		});
		getContentPane().add(btnDone);

		JLabel lblItemIsMade = new JLabel("                  item is made");
		lblItemIsMade.setBounds(10, 194, 147, 14);
		getContentPane().add(lblItemIsMade);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(206, 11, 668, 396);
		getContentPane().add(scrollPane_1);

		menuItemsTable = new JTable();
		menuItemsTable
				.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Price", "*Products" }));

		DefaultTableModel model = (DefaultTableModel) menuItemsTable.getModel();
		for (MenuItem item : App.getRestaurant().getMenuItems()) {
			if (item instanceof BaseProduct) {
				model.addRow(new Object[] { item.getName(), item.getPrice(), "-" });
			} else if (item instanceof CompositeProduct) {
				model.addRow(
						new Object[] { item.getName(), item.getPrice(), ((CompositeProduct) item).listToString() });
			}
		}
		scrollPane_1.setViewportView(menuItemsTable);

		JButton btnDeleteMenuItem = new JButton("Delete menu item");
		btnDeleteMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int column = 0;
				int row = menuItemsTable.getSelectedRow();
				String value = menuItemsTable.getModel().getValueAt(row, column).toString();
				App.getRestaurant().deleteMenuItem(value);
				refreshTable();
				WaiterGraphicalUserInterface.removeItemFromProductList(getItemByName(value));
			}
		});
		btnDeleteMenuItem.setBounds(717, 418, 157, 23);
		getContentPane().add(btnDeleteMenuItem);

		btnEditMenuItem = new JButton("Edit menu item");
		btnEditMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewName.setVisible(true);
				lblNewPrice.setVisible(true);
				newNameTextField.setVisible(true);
				newPriceTextField.setVisible(true);
				btnEdit.setVisible(true);
				btnEditMenuItem.setVisible(false);
				JOptionPane.showMessageDialog(null, "Enter the details in the new text fields, then press <<Edit>>.");
			}
		});
		btnEditMenuItem.setBounds(550, 418, 157, 23);
		getContentPane().add(btnEditMenuItem);

		JButton btnExitApplication = new JButton("Exit application");
		btnExitApplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileWriter.writeToFile(App.getRestaurant());
				System.exit(0);
			}
		});
		btnExitApplication.setBounds(10, 418, 147, 23);
		getContentPane().add(btnExitApplication);

		newNameTextField = new JTextField();
		newNameTextField.setBounds(206, 418, 157, 20);
		getContentPane().add(newNameTextField);
		newNameTextField.setColumns(10);

		newPriceTextField = new JTextField();
		newPriceTextField.setBounds(373, 418, 147, 20);
		getContentPane().add(newPriceTextField);
		newPriceTextField.setColumns(10);

		lblNewName = new JLabel("New name");
		lblNewName.setBounds(206, 436, 157, 14);
		getContentPane().add(lblNewName);

		lblNewPrice = new JLabel("New price");
		lblNewPrice.setBounds(373, 436, 147, 14);
		getContentPane().add(lblNewPrice);

		btnEdit = new JButton("Edit");
		btnEdit.setBounds(550, 418, 157, 23);
		btnEdit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = menuItemsTable.getSelectedRow();
				String value = menuItemsTable.getModel().getValueAt(row, 0).toString();
				for (MenuItem i : App.getRestaurant().getMenuItems()) {
					System.out.println(i.getName());
					if (i.getName().equals(value)) {
						if (newNameTextField.getText().isEmpty()) {
							float price = i.getPrice();
							try {
								price = Float.parseFloat(newPriceTextField.getText().trim());
							} catch (NumberFormatException e1) {
								JOptionPane.showMessageDialog(null, "Introduce valid price number.");
							}
							App.getRestaurant().editMenuItem(i, i.getName(), price);
							refreshTable();
							WaiterGraphicalUserInterface.updateProductList(getItemByName(nameTextField.getText().trim()));
						} else {
							float price = i.getPrice();
							try {
								price = Float.parseFloat(newPriceTextField.getText().trim());
							} catch (NumberFormatException e1) {
								JOptionPane.showMessageDialog(null, "Introduce valid price number.");
							}
							App.getRestaurant().editMenuItem(i, newNameTextField.getText().trim(), price);
							refreshTable();
							WaiterGraphicalUserInterface.updateProductList(getItemByName(nameTextField.getText().trim()));
						}
						break;
					}
				}
				btnEditMenuItem.setVisible(true);
				btnEdit.setVisible(false);
				lblNewName.setVisible(false);
				lblNewPrice.setVisible(false);
				newNameTextField.setVisible(false);
				newPriceTextField.setVisible(false);
			}
		});
		getContentPane().add(btnEdit);

		lblNewName.setVisible(false);
		lblNewPrice.setVisible(false);
		newNameTextField.setVisible(false);
		newPriceTextField.setVisible(false);
	}
}
