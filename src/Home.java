import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import java.awt.Font;
import java.awt.Image;

public class Home extends JFrame {

	private JPanel contentPane;
	private JTextField txtPhno;
	private JPasswordField txtPassword;
	protected User user;
	static Home frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Home();
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
	public Home() {
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 100, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		try {
			Image img = ImageIO.read(getClass().getResource("resources/bg.png"));
			Image resizedImage = img.getScaledInstance(getWidth(), getHeight(),
					java.awt.Image.SCALE_SMOOTH);
			JLabel bg = new JLabel (new ImageIcon(img));
			contentPane.add(bg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		JPanel panel = new JPanel();
		panel.setBounds(12, 24, 426, 464);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblMessage = new JLabel("");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setBounds(96, 303, 237, 15);
		panel.add(lblMessage);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(155, 200, 117, 19);
		panel.add(txtPassword);

		JLabel lblPhno = new JLabel("Phone Number");
		lblPhno.setHorizontalAlignment(SwingConstants.LEFT);
		lblPhno.setBounds(155, 107, 158, 25);
		panel.add(lblPhno);

		txtPhno = new JTextField();
		txtPhno.setBounds(155, 144, 114, 19);
		panel.add(txtPhno);
		txtPhno.setColumns(10);

		JButton btnSearch = new JButton("Login");

		btnSearch.setBounds(155, 266, 117, 25);
		panel.add(btnSearch);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(155, 171, 133, 17);
		panel.add(lblPassword);

		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUp s = new SignUp ();
				s.setVisible(true);
			}
		});
		btnSignUp.setBounds(155, 330, 117, 25);
		panel.add(btnSignUp);

		JButton btnAdmin = new JButton("Admin");
		btnAdmin.setBounds(155, 391, 117, 23);
		panel.add(btnAdmin);
		
		JLabel lblTitle = new JLabel("FoodFast");
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 26));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(12, 12, 402, 56);
		panel.add(lblTitle);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String phno = txtPhno.getText();
					String password = String.valueOf(txtPassword.getPassword());
					// Get driver class
					Class.forName("oracle.jdbc.driver.OracleDriver");
					// step2 create the connection object
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM",
							"16181618");
					// create statement
					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					try {
						ResultSet rs = stmt.executeQuery(
								"SELECT * FROM users where phone_num = " + phno + " and password = '" + password + "'");
						if (!rs.next()) {
							lblMessage.setText("Incorrect credentials");
							/*
							JDialog d = new JDialog(frame, "dialog Box");

							// create a label
							JLabel l = new JLabel("Incorrect Credentials!");
							d.getContentPane().add(l);
							d.setBounds(625, 150, 200, 200);
							d.setVisible(true);
							*/
						} else {
							// reset to first row
							rs.beforeFirst();
							while (rs.next()) {
								// set instance
								user = new User(rs.getString("phone_num"), rs.getString("name"),
										rs.getString("address"));
								Landing l = new Landing(user, "", "");
								l.setVisible(true);
								dispose();
								// setVisible (false);
							}
						}
					} catch (SQLException e) {
						lblMessage.setText("Incorrect format");
					}

					con.commit();
					con.close();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent args0) {
				try
				{
					Admin_login new_frame2=new Admin_login();
					new_frame2.setVisible(true);
					//dispose();
				}
			
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});

	}
}