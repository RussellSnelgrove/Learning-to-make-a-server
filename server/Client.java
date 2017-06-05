import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame {

	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP;
	private Socket connection;

	// constructor
	public Client(String host) {
		super("Russell's Instant Messanger Client Side");
		serverIP = host;
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				sendMessage(event.getActionCommand());
				userText.setText("");
			}
		}

		);
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow), BorderLayout.CENTER);
		setSize(300, 150);
		setVisible(true);
	}

	// connect to server
	public void startRunning() {
		try {
			connectToServer();
			setupStreams();
			whileChatting();
		} catch (EOFException eoffException) {
			showMessage("\n Client terminated connection");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			closeCrap();
		}
	}

	// connect to server
	private void connectToServer() throws IOException {
		showMessage("Hold on while you connect ...\n");
		connection = new Socket(InetAddress.getByName(serverIP), 25665);
		showMessage("Connection Established! Connected to: " + connection.getInetAddress().getHostName());
	}

	// set up streams to send and reccieve messages
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Now you are connected to Russell Snelgrove's server");

	}

	// while chatting with server
	private void whileChatting() throws IOException {
		ableToType(true);
		do {
			try {
				message = (String) input.readObject();
				showMessage("\n" + message);
			} catch (ClassNotFoundException classNotfoundException) {
				showMessage("\n I dont know that object type");
			}

		} while (!message.equals("SERVER - END"));
	}

	// close the streams and sockets
	private void closeCrap() {
		showMessage("\n Closing Down...");
		ableToType(false);
		try {
			output.close();
			input.close();
			connection.close();

		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

	}

	// send messages to server
	private void sendMessage(String message){
		try{
			output.writeObject("Client - "+message);
			output.flush();
			showMessage("\n Client - "+ message);
		}catch(IOException ioException){
			chatWindow.append("\n Error: Can not sent the message");
		}
	}
	
	// UpDate the gui and stuff (change update chat window)

	private void showMessage(final String m) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				chatWindow.append(m);
			}
		});
	}

	// give user permission to type
	private void ableToType(final boolean tof) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				userText.setEditable(tof);
			}
		});

	}
}
