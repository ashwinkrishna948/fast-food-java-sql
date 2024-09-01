import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class SignUp extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField txtPhno;
	private JTextField txtName;
	private JTextField txtAddr;
	private JPasswordField passwordField;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp();
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
	public SignUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 424, 10);
		contentPane.add(panel);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(118, 44, 104, 15);
		contentPane.add(lblPhoneNumber);
		
		txtPhno = new JTextField();
		txtPhno.setBounds(228, 43, 86, 20);
		contentPane.add(txtPhno);
		txtPhno.setColumns(10);
		
		JLabel lblEnterName = new JLabel("Enter Name");
		lblEnterName.setBounds(118, 88, 82, 15);
		contentPane.add(lblEnterName);
		
		txtName = new JTextField();
		txtName.setBounds(228, 85, 86, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(118, 136, 58, 15);
		contentPane.add(lblAddress);
		
		txtAddr = new JTextField();
		txtAddr.setBounds(228, 133, 86, 20);
		contentPane.add(txtAddr);
		txtAddr.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setBounds(118, 175, 70, 15);
		contentPane.add(lblNewLabel);
		
		JButton btnSignup = new JButton("SignUp");
		btnSignup.setBounds(168, 240, 89, 23);
		contentPane.add(btnSignup);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(228, 172, 86, 20);
		contentPane.add(txtPassword);
		
		JLabel check = new JLabel("");
		check.setBounds(158, 240, 116, 14);
		contentPane.add(check);
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent args0)
			{
				try {
					String phno = txtPhno.getText();
					String password = String.valueOf(txtPassword.getPassword());
					String name = txtName.getText();
					String address = txtAddr.getText();
					Class.forName("oracle.jdbc.driver.OracleDriver");
					// step2 create the connection object
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM",
							"16181618");
					// create statement
					Statement stmt = con.createStatement();
						   // ResultSet.TYPE_SCROLL_INSENSITIVE,
						   // ResultSet.CONCUR_READ_ONLY
						//);
					ResultSet rs = stmt.executeQuery("insert into users values('"+phno+"','"+name+"','"+address + "','" + password+"')");
					//home new_frame=new home();
					//new_frame.setVisible(true);
					Dialog d = new Dialog ("Sign Up successful!");
					d.setVisible(true);
					dispose();
				}
				catch(Exception e)
				{
					Dialog d = new Dialog ("User already exists");
					//e.printStackTrace();
				}
			}
		});
		}
	}

