package Tables_Screen;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import DataBase.DataBase_Tools;
import net.proteanit.sql.DbUtils;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Orders_Table extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField OrdersID;
	private JLabel CustomerIDLabel;
	private JTextField CustomerID;
	private JLabel ProductIDLabel;
	private JTextField ProductID;
	private JLabel numofOrderLabel;
	private JLabel numofDeliverLabel;
	private JLabel numofDeliver;
	private JLabel numoforders;

	/**
	 * Launch the application.
	 */
	public static void Show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Orders_Table frame = new Orders_Table();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Orders_Table() {
		setBounds(100, 100, 603, 337);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setEnabled(false);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel find_OrdersIDLabel = new JLabel("Find by Orders ID");
		find_OrdersIDLabel.setBorder(new LineBorder(Color.BLACK));
		find_OrdersIDLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(find_OrdersIDLabel);
		
		OrdersID = new JTextField();
		OrdersID.setBorder(new LineBorder(Color.BLACK));
		OrdersID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				DefaultTableModel t = (DefaultTableModel) table.getModel();
				String search = OrdersID.getText();
				TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(t);
				table.setRowSorter(tr);
				tr.setRowFilter(RowFilter.regexFilter(search, 0));
			}
		});
		panel.add(OrdersID);
		OrdersID.setColumns(10);
		
		CustomerIDLabel = new JLabel("Find by Customer ID");
		CustomerIDLabel.setBorder(new LineBorder(Color.BLACK));
		CustomerIDLabel.setAlignmentX(1.0f);
		panel.add(CustomerIDLabel);
		
		CustomerID = new JTextField();
		CustomerID.setBorder(new LineBorder(Color.BLACK));
		CustomerID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				DefaultTableModel t = (DefaultTableModel) table.getModel();
				String search = CustomerID.getText();
				TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(t);
				table.setRowSorter(tr);
				tr.setRowFilter(RowFilter.regexFilter(search, 1));
			}
		});
		CustomerID.setColumns(10);
		panel.add(CustomerID);
		
		ProductIDLabel = new JLabel("Find by Product ID");
		ProductIDLabel.setBorder(new LineBorder(Color.BLACK));
		ProductIDLabel.setAlignmentX(1.0f);
		panel.add(ProductIDLabel);
		
		ProductID = new JTextField();
		ProductID.setBorder(new LineBorder(Color.BLACK));
		ProductID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				DefaultTableModel t = (DefaultTableModel) table.getModel();
				String search = ProductID.getText();
				TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(t);
				table.setRowSorter(tr);
				tr.setRowFilter(RowFilter.regexFilter(search, 3));
			}
		});
		ProductID.setColumns(10);
		panel.add(ProductID);
		
		numofOrderLabel = new JLabel("Number of Orders");
		numofOrderLabel.setBorder(new LineBorder(Color.BLACK));
		panel.add(numofOrderLabel);
		
		numoforders = new JLabel(getNumoforders());
		numoforders.setBorder(new LineBorder(Color.BLACK));
		panel.add(numoforders);
		
		numofDeliverLabel = new JLabel("Number of Deliver");
		numofDeliverLabel.setBorder(new LineBorder(Color.BLACK));
		panel.add(numofDeliverLabel);
		
		numofDeliver = new JLabel(getNumofDeliver());
		numofDeliver.setBorder(new LineBorder(Color.BLACK));
		panel.add(numofDeliver);
		load_Table();
	}

	private void load_Table() {
		try {
			table.setModel(DbUtils.resultSetToTableModel(Desplay_Table()));
			table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
			TableColumnModel tcm = table.getColumnModel();
			for (int i = 0; i < tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setPreferredWidth(10 * (table.getColumnName(i).length()));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
	}

	private ResultSet Desplay_Table() throws Exception{
		Connection conn = DataBase_Tools.getConnection();
		String Statement = 
				"select O.Orders_ID,O.Customer_ID,C.Customer_Name,O.Product_ID,P.Product_Name,O.Address,O.Date_of_Order,O.Date_of_Deliver	" + 
				"from Orders O 	" + 
				"join Customer C on O.Customer_ID = C.Customer_ID	" + 
				"join Product P on O.Product_ID = P.Product_ID	" + 
				";";
				
		PreparedStatement ps = conn.prepareStatement(Statement);
		
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	private String getNumoforders() {
		try {
			Connection conn = DataBase_Tools.getConnection();
			String Statement = "select count(Date_of_Order) from Orders ;";
			PreparedStatement ps = conn.prepareStatement(Statement);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return "0";
	}
	
	private String getNumofDeliver() {
		try {
			Connection conn = DataBase_Tools.getConnection();
			String Statement = "select count(Date_of_Deliver) from Orders ;";
			PreparedStatement ps = conn.prepareStatement(Statement);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return "0";
	}
}
