import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class UpdateForm extends JFrame {

	private JPanel contentPane;
	User user;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateForm frame = new UpdateForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

*/
	/**
	 * Create the frame.
	 */
	public UpdateForm(User u) {
		user = u;
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblEnterName = new JLabel("Name");
		lblEnterName.setBounds(118, 88, 82, 15);
		contentPane.add(lblEnterName);
		
		JTextField txtName = new JTextField();
		txtName.setBounds(228, 85, 121, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(118, 136, 58, 15);
		contentPane.add(lblAddress);
		
		JTextField txtAddr = new JTextField();
		txtAddr.setBounds(228, 133, 121, 20);
		contentPane.add(txtAddr);
		txtAddr.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setBounds(118, 175, 70, 15);
		contentPane.add(lblNewLabel);
		
		JButton btnSignup = new JButton("Update");

		btnSignup.setBounds(172, 216, 86, 25);
		contentPane.add(btnSignup);
		
		JPasswordField txtPassword = new JPasswordField();
		txtPassword.setBounds(228, 172, 121, 20);
		contentPane.add(txtPassword);
		
		JLabel lblNewLabel_1 = new JLabel("Fill only changed fields");
		lblNewLabel_1.setBounds(118, 35, 162, 15);
		contentPane.add(lblNewLabel_1);
	
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String password = String.valueOf(txtPassword.getPassword());
					String name = txtName.getText();
					String address = txtAddr.getText();
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM",
							"16181618");
					// create statement
					Statement stmt = con.createStatement();
					String query;
					if (!name.equals ("")) {
						query = "update users set name = '" + name + "' where phone_num = '" + user.phone_num +"'";
						stmt.executeUpdate (query);
					}
					if (!password.equals(""))
					{
						query = "update users set password = '" + password + "' where phone_num = '" + user.phone_num +"'";
						stmt.executeUpdate (query);
					}
					if (!address.equals ("")) {
						query = "update users set address = '" + address + "' where phone_num = '" + user.phone_num +"'";
						stmt.executeUpdate (query);
					}
					Dialog d = new Dialog ("Update successful! Changes are reflected on logging in again.");
					d.setVisible(true);
					dispose ();
					con.commit ();
					con.close ();
					
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			}
		});
	}	
}
