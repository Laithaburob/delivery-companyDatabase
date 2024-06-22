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

public class Product_Table extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField findtextField;
	private JLabel numofProductLabel;
	private JLabel numofProduct;
	private JLabel findLabelBYNAME;
	private JTextField textFieldbyname;

	/**
	 * Launch the application.
	 */
	public static void Show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Product_Table frame = new Product_Table();
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
	public Product_Table() {
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
		findLabel.setBorder(new LineBorder(Color.BLACK));
		findLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(findLabel);
		
		findtextField = new JTextField();
		findtextField.setBorder(new LineBorder(Color.BLACK));
		findtextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!findtextField.getText().isBlank()) {
					if (textFieldbyname.getText().isBlank() && findtextField.getText().isBlank()) {
						load_Table("");
					} else {
						if (textFieldbyname.getText().isBlank()) {
							load_Table("Where P.Product_ID = "+findtextField.getText()+" ");
						} else {
							load_Table("Where P.Product_ID = " + findtextField.getText()+" AND P.Product_Name Like \""+textFieldbyname.getText()+"%\" ");
						}
					}
				}
				if (textFieldbyname.getText().isBlank() && findtextField.getText().isBlank()) {
					load_Table("");
				}
			}
		});
		panel.add(findtextField);
		findtextField.setColumns(10);
		
		findLabelBYNAME = new JLabel("Find by name");
		findLabelBYNAME.setBorder(new LineBorder(Color.BLACK));
		findLabelBYNAME.setAlignmentX(1.0f);
		panel.add(findLabelBYNAME);
		
		textFieldbyname = new JTextField();
		textFieldbyname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!textFieldbyname.getText().isBlank()) {
					if (textFieldbyname.getText().isBlank() && findtextField.getText().isBlank()) {
						load_Table("");
					} else {
						if (findtextField.getText().isBlank()) {
							load_Table("Where P.Product_Name Like \""+textFieldbyname.getText()+"%\" ");
						} else {
							load_Table("Where P.Product_ID = " + findtextField.getText()+" AND P.Product_Name Like \""+textFieldbyname.getText()+"%\" ");
						}
					}
				}
				if (textFieldbyname.getText().isBlank() && findtextField.getText().isBlank()) {
					load_Table("");
				}
			}
		});
		textFieldbyname.setColumns(10);
		textFieldbyname.setBorder(new LineBorder(Color.BLACK));
		panel.add(textFieldbyname);
		
		numofProductLabel = new JLabel("Number of Product");
		numofProductLabel.setBorder(new LineBorder(Color.BLACK));
		panel.add(numofProductLabel);
		
		numofProduct = new JLabel(getNumofProduct());
		numofProduct.setBorder(new LineBorder(Color.BLACK));
		panel.add(numofProduct);
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
				"select P.* , count(O.Product_ID) AS \"Number of Orders\"	" + 
				"from Product P	" + 
				"left join Orders O  on P.Product_ID = O.Product_ID	" + 
				find+
				"group by P.Product_ID	" + 
				";";
				
		PreparedStatement ps = conn.prepareStatement(Statement);
		
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	private String getNumofProduct() {
		try {
			Connection conn = DataBase_Tools.getConnection();
			String Statement = "select count(*) from Product ;";
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
