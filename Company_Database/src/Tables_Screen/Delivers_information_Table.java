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
import javax.swing.JDesktopPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Delivers_information_Table extends JFrame {

	private JPanel contentPane;
	public JTable table;
	private JTextField EmployeeField;
	private JTextField OrderField;
	private JLabel find_OIDLabel;

	/**
	 * Launch the application.
	 */
	public static void Show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Delivers_information_Table frame = new Delivers_information_Table();
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
	public Delivers_information_Table() {
		setBounds(100, 100, 450, 300);
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
		
		JLabel find_EIDLabel = new JLabel("Find by Employee ID");
		find_EIDLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		find_EIDLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(find_EIDLabel);
		
		EmployeeField = new JTextField();
		EmployeeField.setBorder(new LineBorder(new Color(0, 0, 0)));
		EmployeeField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!EmployeeField.getText().isBlank()) {
					if (EmployeeField.getText().isBlank() && OrderField.getText().isBlank()) {
						load_Table("");
					} else {
						if (OrderField.getText().isBlank()) {
							load_Table("Where DI.ID_Employee = " + EmployeeField.getText()+" ");
						} else {
							load_Table("Where DI.ID_Employee = " + EmployeeField.getText()+" AND DI.Orders_ID = "+OrderField.getText());
						}
						
					}
				}
				if (EmployeeField.getText().isBlank() && OrderField.getText().isBlank()) {
					load_Table("");
				}
			}
		});
		panel.add(EmployeeField);
		EmployeeField.setColumns(10);
		
		find_OIDLabel = new JLabel("Find by Order ID");
		find_OIDLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		find_OIDLabel.setAlignmentX(1.0f);
		panel.add(find_OIDLabel);
		
		OrderField = new JTextField();
		OrderField.setBorder(new LineBorder(new Color(0, 0, 0)));
		OrderField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!OrderField.getText().isBlank()) {
					if (EmployeeField.getText().isBlank() && OrderField.getText().isBlank()) {
						load_Table("");
					} else {
						if (!OrderField.getText().isBlank()) {
							load_Table("Where DI.Orders_ID = "+OrderField.getText());
						} else {
							load_Table("Where DI.ID_Employee = " + EmployeeField.getText()+" AND DI.Orders_ID = "+OrderField.getText());
						}
						
					}
				}
				if (EmployeeField.getText().isBlank() && OrderField.getText().isBlank()) {
					load_Table("");
				}
			}
		});
		OrderField.setColumns(10);
		panel.add(OrderField);
		load_Table("");
	}

	private void load_Table(String op) {
		try {
			table.setModel(DbUtils.resultSetToTableModel(Desplay_Table(op)));
			table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
			TableColumnModel tcm = table.getColumnModel();
			for (int i = 0; i < tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setPreferredWidth(10 * (table.getColumnName(i).length()));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
	}

	private ResultSet Desplay_Table(String op) throws Exception{
		Connection conn = DataBase_Tools.getConnection();
		String Statement = 
				"select  E.ID_Number as \"Employee ID\" , E.Employee_Name , O.Orders_ID ,C.Customer_ID,C.Customer_Name , P.Product_ID ,P.Product_Name,O.Address ,O.Date_of_Order,O.Date_of_Deliver	" + 
				"from Delivers_information DI 	" + 
				"join Employee E on DI.ID_Employee = E.ID_Number	" + 
				"join Orders O on DI.Orders_ID = O.Orders_ID 	" + 
				"join Customer C on O.Customer_ID = C.Customer_ID	" + 
				"join Product P on O.Product_ID = P.Product_ID	" + 
				op+
				" ;";
				
		PreparedStatement ps = conn.prepareStatement(Statement);
		
		ResultSet rs = ps.executeQuery();
		return rs;
	}

}
