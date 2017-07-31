package com.abelski.networking.chat;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.TextArea;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import java.awt.Font;

public class ClientGUI implements StringConsumer, StringProducer{

	private ConnectionProxy proxy;
	private Socket socket;
	private String nick;
	
	public String getNick() {
		return nick;
	}
	
	@Override
	public void addConsumer(StringConsumer sc) {
		
		
	}

	@Override
	public void removeConsumer(StringConsumer sc) {
		
		
	}

	@Override
	public void consume(String str) {
		
		Date dNow = new Date( );
		SimpleDateFormat ft = 
			      new SimpleDateFormat ("'at' hh:mm a");
		textArea.append(ft.format(dNow)+ " " +str +"\n");
	}
	
	
	private JFrame frmChatApplication;
	private TextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI();
					window.frmChatApplication.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientGUI() {
		initialize();
		try {
			socket = new Socket("127.0.0.1" , 1300);
			proxy = new ConnectionProxy(socket);
			proxy.addConsumer(this);
			proxy.start();
		} catch (UnknownHostException e) {	
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		while(true)
		{
			nick = JOptionPane.showInputDialog(null,"please enter your nick",null,1);
			if(nick == null)
			{
				System.exit(1);
			}
			else if( ! nick.equals("") )
			{
				break;
			}
		}
		frmChatApplication = new JFrame();
		frmChatApplication.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\amit\\Desktop\\amit\\programming\\Sec Year\\Sem B\\Java\\Networking\\src\\com\\abelski\\networking\\chat\\1464124326_GroupMe.png"));
		frmChatApplication.getContentPane().setBackground(Color.WHITE);
		frmChatApplication.setResizable(false);
		frmChatApplication.setTitle("Chat Application");
		frmChatApplication.setBounds(100, 100, 553, 424);
		frmChatApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatApplication.getContentPane().setLayout(null);
		
		JLabel lblEnterText = new JLabel("Enter text :");
		lblEnterText.setForeground(Color.BLACK);
		lblEnterText.setBounds(56, 311, 71, 14);
		frmChatApplication.getContentPane().add(lblEnterText);
		
		textArea = new TextArea();
		textArea.setFont(new Font("Arial", Font.BOLD, 13));
		textArea.setForeground(Color.BLACK);
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setEditable(false);
		textArea.setBounds(56, 45, 436, 251);
		frmChatApplication.getContentPane().add(textArea);
		
		JFormattedTextField textMessage = new JFormattedTextField();
		textMessage.setFont(new Font("Arial", Font.BOLD, 13));
		textMessage.setBackground(Color.LIGHT_GRAY);
		textMessage.setForeground(Color.BLACK);
		textMessage.setBounds(56, 324, 436, 26);
		frmChatApplication.getContentPane().add(textMessage);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				proxy.consume(nick+ " : " +textMessage.getText());
				textMessage.setText("");
			}
		});
		btnSend.setBounds(224, 361, 89, 23);
		frmChatApplication.getContentPane().add(btnSend);
		
		JLabel lblNick = new JLabel("Nick :");
		lblNick.setForeground(Color.BLACK);
		lblNick.setBounds(56, 25, 89, 14);
		lblNick.setText("Nick : " +nick);
		frmChatApplication.getContentPane().add(lblNick);
	}
}
