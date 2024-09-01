import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
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
import javax.swing.SwingConstants;
import java.awt.Font;

public class Admin_login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	protected Admin_class admin_1;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin_login frame = new Admin_login();
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
	public Admin_login() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(120, 73, 72, 15);
		panel.add(lblUsername);
		
		textField = new JTextField();
		textField.setBounds(207, 71, 86, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(120, 128, 70, 15);
		panel.add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(157, 188, 89, 23);
		panel.add(btnLogin);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(110, 223, 183, 14);
		panel.add(lblNewLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(207, 126, 86, 20);
		panel.add(passwordField);
		
		JLabel lblAdminAccess = new JLabel("Admin Access");
		lblAdminAccess.setFont(new Font("Dialog", Font.BOLD, 16));
		lblAdminAccess.setVerticalAlignment(SwingConstants.TOP);
		lblAdminAccess.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdminAccess.setBounds(102, 12, 181, 28);
		panel.add(lblAdminAccess);
		
		
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent args0) {
				try
				{
					
					String admin_id1 = textField.getText();
					//int admin_id = Integer.parseInt(admin_id1);
					String password = String.valueOf(passwordField.getPassword());
					Class.forName("oracle.jdbc.driver.OracleDriver");
					// step2 create the connection object
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM",
							"16181618");
					// create statement'
					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					
					String q = "select ad_id from admin where ad_id = " + admin_id1 + " and password = '"+password+"'";
					
					ResultSet rs= stmt.executeQuery(q);
					//if(rs.next())
					//	System.out.println("hello");
					if(!rs.next())
					{
						lblNewLabel.setText("Incorrect credentials");
					}
					else
					{
						rs.beforeFirst();
						while(rs.next())
						{
							admin_1 = new Admin_class(rs.getInt("ad_id"));
						}
					Admin_landing new_frame=new Admin_landing(admin_1);
					new_frame.setVisible(true);
					dispose();
					
					con.commit ();
					con.close ();
					}
				}
			
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});

	}
}
