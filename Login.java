package com.InternetRelayChat.chat;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
//created three textFields,five labels and one button named login using Java Frame
public class Login extends JFrame {
	private static final long serialVersionUID = 1l;

	private JPanel contentPane;
	private JTextField textName;
	private JLabel lblName;
	private JTextField textAddress;
	private JLabel lblIpAddress;
	private JLabel lbleg;

	private JTextField textPort;
	private JLabel lblPort;
	private JLabel lbleg_1;

	public Login() {  //constructor called
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e){
		}

		setResizable(false); //fixed size page
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 380);
		setLocationRelativeTo(null); //center
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textName = new JTextField();//textfield 1
		textName.setBounds(133, 52, 143, 20);
		contentPane.add(textName);
		textName.setColumns(10);

		lblName = new JLabel("Name:");//label 1
		lblName.setBounds(183, 40, 46, 14);
		contentPane.add(lblName);

		textAddress = new JTextField();//textfield 2
		textAddress.setBounds(133, 127, 143, 25);
		contentPane.add(textAddress);
		textAddress.setColumns(10);

		lblIpAddress = new JLabel("IP Address:");//label 2
		lblIpAddress.setBounds(173, 109, 100, 20);
		contentPane.add(lblIpAddress);

		lbleg = new JLabel("(eg. 192.168.0.2)");//label 3
		lbleg.setBounds(160, 155, 100, 14);
		contentPane.add(lbleg);

		textPort = new JTextField();//textfield 3
		textPort.setBounds(133, 227, 143, 20);
		contentPane.add(textPort);
		textPort.setColumns(10);

		lblPort = new JLabel("Port:");//label 4
		lblPort.setBounds(183, 212, 46, 14);
		contentPane.add(lblPort);

		lbleg_1 = new JLabel("(eg. 8192)");//label 5
		lbleg_1.setBounds(183, 248, 100, 14);
		contentPane.add(lbleg_1);

		JButton btnLogin = new JButton("Login");//button object btnLogin declared inside the constructor
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//client widow will open after pressing  the "login" button 
				String name = textName.getText();
				String address = textAddress.getText();
				int port = Integer.parseInt(textPort.getText());

				login(name, address, port);//login method called

			}
		});
		btnLogin.setBounds(158, 317, 89, 23);
		contentPane.add(btnLogin);
	}

	public void login(String name, String address, int port) {

		dispose();

		new ClientWindow(name, address, port);

	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {

				}
			}
		});
	}
}
