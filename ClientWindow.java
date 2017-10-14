package com.InternetRelayChat.chat;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class ClientWindow extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea history;
	private JTextField txtMessage;
	private Thread run, listen;
	private Client client;
	private boolean running = false;
    
    
	
	public ClientWindow(String name, String address, int port) {
		client = new Client(name, address, port);
		setTitle("Internet Relay Chat");

		boolean connect = client.openConnection(address, port);
		if (!connect) {
			System.err.println("connection failed");
			connectionMessage("connection failed");
			}
		createWindow();
		// connectionMessage("Successfully connected !!");
		// connectionMessage("Successfully disconnected !!");
		connectionMessage("Attempting a connection to " + address + "::" + port + "::user->" + name);
		String connection = "/c/" + name + "/e/";
		client.send(connection.getBytes());
	
		run = new Thread(this, "Running");

		running = true;
		run.start();
	}

	private void createWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e){
		}

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(880, 550);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 15, 820, 30, 15 };// Sum=880,
																	// and to
																	// setlle
																	// send
																	// button to
																	// the
																	// corner of
																	// the
																	// window
		gridBagLayout.rowHeights = new int[] { 30, 480, 40 };// Sum=550
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0 };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gridBagLayout);

		history = new JTextArea();
		history.setEditable(false);
		// history.setText("history");
		JScrollPane scroll = new JScrollPane(history);
		GridBagConstraints scrollConstraints = new GridBagConstraints();
		scrollConstraints.insets = new Insets(0, 0, 5, 5);
		scrollConstraints.fill = GridBagConstraints.BOTH;
		scrollConstraints.gridx = 0;
		scrollConstraints.gridy = 0;
		scrollConstraints.gridwidth = 3;
		scrollConstraints.gridheight = 2;
		scrollConstraints.weightx = 1;
		scrollConstraints.weighty = 1;
		scrollConstraints.insets = new Insets(0, 5, 0, 0);
		
		contentPane.add(scroll, scrollConstraints);

		txtMessage = new JTextField();
		txtMessage.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					send(txtMessage.getText(),true);

			}
		});
		GridBagConstraints gbc_txtMessage = new GridBagConstraints();
		gbc_txtMessage.insets = new Insets(0, 0, 0, 5);
		gbc_txtMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMessage.gridx = 0;
		gbc_txtMessage.gridy = 2;
		gbc_txtMessage.gridwidth = 2;
		
		
		contentPane.add(txtMessage, gbc_txtMessage);
		txtMessage.setColumns(10);

		JButton btnSend = new JButton("send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send(txtMessage.getText(),true);
			}
		});
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 0, 5);
		gbc_btnSend.gridx = 2;
		gbc_btnSend.gridy = 2;
		
		contentPane.add(btnSend, gbc_btnSend);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				String disconnect = "/d/" + client.getID() + "/e/";
				send(disconnect,false);
				running= false;
				client.close();

			}
		});

		setVisible(true);
		txtMessage.requestFocusInWindow();
	}

	public void run() {
		listen();
	}
	
	private void send(String message, boolean text) {
		if (message.equals(" "))
			return;
		if(text){
		 message = client.getName() + ":" + message;
		// connectionMessage(message);
		  message = "/m/" + message;
		}		client.send(message.getBytes());
		txtMessage.setText("");
	}
	
	

	public void listen() {
		listen = new Thread("Listen") {
			public void run() {
				while (running) {
					
						String message = client.receive();
						if (message.startsWith("/c/")) {
							// "/c/8125/e/ 8125/e/ ->[1]
							client.setID(Integer.parseInt(message.split("/c/|/e/")[1]));
							connectionMessage("Succesflluy connected to server ! ID :" + client.getID());

						} else if (message.startsWith("/m/")) {
							String text = message.substring(3);
							text=text.split("/e/")[0];
							connectionMessage(text);
						}else if(message.startsWith("/i/")){
							String text="/i/" +client.getID()+"/e/";
							send(text,false);
						}
					
				}
			}

		};
		listen.start();
	}

	private void connectionMessage(String message) {
		history.append(message + "\n\r");
		
	}

	
}
