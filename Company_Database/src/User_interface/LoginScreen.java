package User_interface;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import DataBase.Create_Tables;
import DataBase.DataBase_Tools;

public class LoginScreen extends JFrame {

	private JPanel contentPane;
	private JLabel User_Name_Label;
	private JTextField User_Name_Field;
	private JLabel password_Label;
	private JTextField Password_Field;
	private JButton LoginButton;
	private JLabel Login_Label;

	/**
	 * Launch the application.
	 */
	public static void Show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					LoginScreen frame = new LoginScreen();
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
	public LoginScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		getContentPane().setLayout(null);
		contentPane.setLayout(null);
		User_Name_Label = new JLabel("User Name");
		User_Name_Label.setFont(new Font("Tahoma", Font.BOLD, 18));
		User_Name_Label.setHorizontalAlignment(SwingConstants.CENTER);
		User_Name_Label.setBounds(27, 94, 119, 22);
		User_Name_Label.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(User_Name_Label);

		User_Name_Field = new JTextField();
		User_Name_Field.setFont(new Font("Tahoma", Font.BOLD, 17));
		User_Name_Field.setColumns(10);
		User_Name_Field.setBounds(148, 94, 268, 22);
		User_Name_Field.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(User_Name_Field);

		password_Label = new JLabel("Password ");
		password_Label.setFont(new Font("Tahoma", Font.BOLD, 18));
		password_Label.setHorizontalAlignment(SwingConstants.CENTER);
		password_Label.setBounds(45, 161, 101, 22);
		password_Label.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(password_Label);

		Password_Field = new JTextField();
		Password_Field.setFont(new Font("Tahoma", Font.BOLD, 17));
		Password_Field.setColumns(10);
		Password_Field.setBounds(148, 161, 268, 22);
		Password_Field.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(Password_Field);

		LoginButton = new JButton("Login");
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					String UserName = User_Name_Field.getText();
					String PassWord = Password_Field.getText();
					DataBase_Tools.setConnection(UserName, PassWord);
					if (DataBase_Tools.getConnection() == null) {
						JOptionPane.showMessageDialog(null, "Username Or Password unincorrect");
						return;
					} else {

						JOptionPane.showMessageDialog(null, "Connected Successfully");
												
						if (DataBase_Tools.Database_is_exists()) {
							DataBase_Tools.setDataBase_Name("Company");
						} else {
							new Create_Tables();
						}

						if (UserName.equalsIgnoreCase("root")) {
							Main_Screen.Show();
						} else {
							Employee_interface.Show();
						}

						dispose();
					}

				} catch (Exception e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e1);
				}
			}

		});
		LoginButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		LoginButton.setBounds(65, 223, 327, 33);
		LoginButton.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(LoginButton);

		Login_Label = new JLabel("Login");
		Login_Label.setFont(new Font("Tahoma", Font.BOLD, 20));
		Login_Label.setHorizontalAlignment(SwingConstants.CENTER);
		Login_Label.setBounds(136, 50, 179, 33);
		Login_Label.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(Login_Label);

	}
}
