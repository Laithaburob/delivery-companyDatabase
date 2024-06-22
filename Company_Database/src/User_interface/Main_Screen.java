package User_interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import DataBase.*;
public class Main_Screen extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void Show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_Screen frame = new Main_Screen();
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
	public Main_Screen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 347, 309);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton BranchButton = new JButton("Branch");
		BranchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Branch_Screen.Show();
				dispose();
			}
		});
		BranchButton.setBounds(47, 59, 119, 23);
		BranchButton.setBorder(new LineBorder(Color.BLACK));
		contentPane.add(BranchButton);
		
		JButton DepartmentButton = new JButton("Department");
		DepartmentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Department_Screen.Show();
				dispose();
			}
		});
		DepartmentButton.setBorder(new LineBorder(Color.BLACK));
		DepartmentButton.setBounds(47, 93, 119, 23);
		contentPane.add(DepartmentButton);
		
		JButton StoragesButton = new JButton("Storages");
		StoragesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Storages_Screen.Show();
				dispose();
			}
		});
		StoragesButton.setBorder(new LineBorder(Color.BLACK));
		StoragesButton.setBounds(47, 127, 119, 23);
		contentPane.add(StoragesButton);
		
		JButton EmployeeButton = new JButton("Employee");
		EmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Employee_Screen.Show();
				dispose();
			}
		});
		EmployeeButton.setBorder(new LineBorder(Color.BLACK));
		EmployeeButton.setBounds(47, 161, 119, 23);
		contentPane.add(EmployeeButton);
		
		JButton CarsButton = new JButton("Cars");
		CarsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Delivery_Cars_Screen.Show();
				dispose();
			}
		});
		CarsButton.setBorder(new LineBorder(Color.BLACK));
		CarsButton.setBounds(176, 59, 119, 23);
		contentPane.add(CarsButton);
		
		JButton CustomerButton = new JButton("Customer");
		CustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Customer_Screen.Show();
				dispose();
			}
		});
		CustomerButton.setBorder(new LineBorder(Color.BLACK));
		CustomerButton.setBounds(176, 93, 119, 23);
		contentPane.add(CustomerButton);
		
		JButton ProductButton = new JButton("Products");
		ProductButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Product_Screen.Show();
				dispose();
			}
		});
		ProductButton.setBorder(new LineBorder(Color.BLACK));
		ProductButton.setBounds(176, 127, 119, 23);
		contentPane.add(ProductButton);
		
		JButton OrdersButton = new JButton("Orders");
		OrdersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Orders_Screen.Show();
				dispose();
			}
		});
		OrdersButton.setBorder(new LineBorder(Color.BLACK));
		OrdersButton.setBounds(176, 161, 119, 23);
		contentPane.add(OrdersButton);
		
		JButton btnCarsOwners = new JButton("Cars Owners");
		btnCarsOwners.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Employee2Cars_Screen.Show();
				dispose();
			}
		});
		btnCarsOwners.setBorder(new LineBorder(Color.BLACK));
		btnCarsOwners.setBounds(47, 195, 119, 23);
		contentPane.add(btnCarsOwners);
		
		JButton btnDeliversInformation = new JButton("Delivers information");
		btnDeliversInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Delivers_information_Screen.Show();
				dispose();
			}
		});
		btnDeliversInformation.setBorder(new LineBorder(Color.BLACK));
		btnDeliversInformation.setBounds(176, 195, 119, 23);
		contentPane.add(btnDeliversInformation);
		
		JLabel UserNameLabel = new JLabel(DataBase_Tools.getUsername());
		UserNameLabel.setBounds(10, 11, 140, 23);
		contentPane.add(UserNameLabel);
		
		JButton Sing_Out = new JButton("Sing Out");
		Sing_Out.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection conn = DataBase_Tools.getConnection();
					conn.close();
					LoginScreen.Show();
					dispose();
				} catch (Exception e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		Sing_Out.setBorder(new LineBorder(Color.BLACK));
		Sing_Out.setBounds(47, 229, 231, 33);
		contentPane.add(Sing_Out);
	}
}
