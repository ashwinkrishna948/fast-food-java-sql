import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

public class add_dish extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	public static Admin_class admin_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					add_dish frame = new add_dish(admin_1);
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
	public add_dish(Admin_class admin_1) {
		this.admin_1 = admin_1;
		String a[] ={"Non Veg","Veg"};
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(233, 79, 86, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblDishName = new JLabel("Dish Name");
		lblDishName.setBounds(132, 82, 91, 14);
		panel.add(lblDishName);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(132, 110, 46, 14);
		panel.add(lblPrice);
		
		textField_1 = new JTextField();
		textField_1.setBounds(233, 107, 86, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnAddDish = new JButton("Add Dish");
		btnAddDish.setBounds(174, 181, 96, 25);
		btnAddDish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel.add(btnAddDish);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(313, 228, 89, 23);
		panel.add(btnBack);
		
		JLabel lblType = new JLabel("Type");
		lblType.setBounds(133, 141, 46, 14);
		panel.add(lblType);
		
		JComboBox<String> comboBox = new JComboBox(a);
		comboBox.setBounds(246, 138, 73, 20);
		comboBox.setSelectedIndex(-1);
		panel.add(comboBox);
		
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(96, 53, 257, 14);
		panel.add(label);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setBounds(335, 0, 89, 23);
		panel.add(btnLogout);
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent args0) {
				try
				{
					Admin_landing new_frame2=new Admin_landing(admin_1);
					new_frame2.setVisible(true);
					dispose();
				}
			
				catch(Exception e)
				{
					
					e.printStackTrace();
				}
			}
		});
		
		btnAddDish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent args0) {
				try
				{
					int type = comboBox.getSelectedIndex();
					int id_1 = admin_1.ad_id;
					//String dish_id = textField_2.getText();
					String dish_name = textField.getText();
					int price = Integer.parseInt (textField_1.getText());
					
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM",
							"16181618");
					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					String q = "insert into dish values(null,"+ id_1 + " ,'"+dish_name + "'," + price + "," + type +" )";
					ResultSet rs = stmt.executeQuery(q);
					/*String x = "select * from dish where d_id = " + dish_id + "'";
					rs = stmt.executeQuery(x);
					if(rs.next())
						label.setText("Addition Successful");
					else
						label.setText("Addition unsuccessful");*/
					label.setText("Addition Successful");
					con.commit();
					con.close ();
					
					
					
				}
			
				catch(Exception e)
				{
					label.setText("Dish already exists in the table");
					e.printStackTrace();
				}
			}
		});

		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent args0) {
				try
				{
					admin_1.ad_id = 0;
					Home new_f1 =new Home();
					new_f1.setVisible(true);
					dispose();
				}
			
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
