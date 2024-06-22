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
import javax.swing.table.TableColumnModel;

import com.mysql.cj.x.protobuf.MysqlxCrud.Find;

import DataBase.DataBase_Tools;
import net.proteanit.sql.DbUtils;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Customer_Table extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField findtextbyid;
	private JLabel numofcustomer;
	private JLabel numofcustomerLabel;
	private JLabel findLabelbyname;
	private JTextField findtextbyname;

	/**
	 * Launch the application.
	 */
	public static void Show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Customer_Table frame = new Customer_Table();
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
	public Customer_Table() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel findLabel = new JLabel("Find by ID");
		findLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		findLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(findLabel);
		
		findtextbyid = new JTextField();
		findtextbyid.setBorder(new LineBorder(new Color(0, 0, 0)));
		findtextbyid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					if (!findtextbyid.getText().isBlank()) {
						if (findtextbyid.getText().isBlank() && findtextbyname.getText().isBlank()) {
							load_Table("");
						} else {
							if (findtextbyname.getText().isBlank()) {
								load_Table("Where C.Customer_ID = " + findtextbyid.getText()+" ");
							} else {
								load_Table("Where C.Customer_ID = " + findtextbyid.getText()+" AND C.Customer_Name Like \""+findtextbyname.getText()+"%\" ");
							}
							
						}
					}
					if (findtextbyid.getText().isBlank() && findtextbyname.getText().isBlank()) {
						load_Table("");
					}
				} catch (Exception e1) {
					System.out.println(e1);
				}
			}
		});
		panel.add(findtextbyid);
		findtextbyid.setColumns(10);
		
		findLabelbyname = new JLabel("Find by Name");
		findLabelbyname.setBorder(new LineBorder(new Color(0, 0, 0)));
		findLabelbyname.setAlignmentX(1.0f);
		panel.add(findLabelbyname);
		
		findtextbyname = new JTextField();
		findtextbyname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!findtextbyname.getText().isBlank()) {
					if (findtextbyid.getText().isBlank() && findtextbyname.getText().isBlank()) {
						load_Table("");
					} else {
						if (findtextbyid.getText().isBlank()) {
							load_Table("Where C.Customer_Name Like \""+findtextbyname.getText()+"%\" ");
						} else {
							load_Table("Where C.Customer_ID = " + findtextbyid.getText()+" AND C.Customer_Name Like \""+findtextbyname.getText()+"%\" ");
						}
					}
				}
				if (findtextbyid.getText().isBlank() && findtextbyname.getText().isBlank()) {
					load_Table("");
				}
			}
		});
		findtextbyname.setColumns(10);
		findtextbyname.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(findtextbyname);
		
		numofcustomerLabel = new JLabel("number of Customer");
		numofcustomerLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(numofcustomerLabel);
		
		numofcustomer = new JLabel(getnumofcustomer());
		numofcustomer.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(numofcustomer);
		load_Table("");
	}

	

	private void load_Table(String find) {
		try {
			table.setModel(DbUtils.resultSetToTableModel(Desplay_Table(find)));
			table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
			TableColumnModel tcm = table.getColumnModel();
			for (int i = 0; i < tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setPreferredWidth(10 * (table.getColumnName(i).length()));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
	}

	private ResultSet Desplay_Table(String find) throws Exception{
		Connection conn = DataBase_Tools.getConnection();
		String Statement = 
				"select C.* ,count(O.Date_of_Order) AS \"Number of Orders\"	" + 
				"from Customer C	" + 
				"left join Orders O on C.Customer_ID = O.Customer_ID	" + 
				find+
				"group by C.Customer_ID	" + 
				";";
				
		PreparedStatement ps = conn.prepareStatement(Statement);
		
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	private String getnumofcustomer() {
		try {
			Connection conn = DataBase_Tools.getConnection();
			String Statement = "select count(*) from Customer ;";
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

