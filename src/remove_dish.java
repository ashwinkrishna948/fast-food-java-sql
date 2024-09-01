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
import javax.swing.SwingConstants;

public class remove_dish extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	public static Admin_class admin_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					remove_dish frame = new remove_dish(admin_1);
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
	public remove_dish(Admin_class admin_1) {
		
		this.admin_1=admin_1;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblDishName = new JLabel("Dish ID");
		lblDishName.setBounds(131, 62, 75, 14);
		panel.add(lblDishName);
		
		textField = new JTextField();
		textField.setBounds(209, 60, 86, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(165, 108, 89, 23);
		panel.add(btnDelete);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(304, 217, 89, 23);
		panel.add(btnBack);
		
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(125, 164, 188, 14);
		panel.add(label);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setBounds(325, 2, 89, 23);
		panel.add(btnLogout);
		
		
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent args0) {
		try
		{
			
			int dish_id = Integer.parseInt(textField.getText());
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// step2 create the connection object
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM",
					"16181618");
			// create statement'
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String q = "delete from dish where d_id =" 	+dish_id+" and r_id = " + admin_1.ad_id;
			int rowsAffected= stmt.executeUpdate(q);
			if (rowsAffected == 0)
				throw new Invalid_Dish_Id ("Enter valid dish ID!");
			label.setText("Deletion Successful");
			con.commit ();
			con.close ();
		}
		catch (Invalid_Dish_Id e) {
			label.setText(e.toString());
		}
		catch(Exception e)
		{
			label.setText("Deletion Unsuccessful");
			e.printStackTrace();
		}
	}
});
		
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
