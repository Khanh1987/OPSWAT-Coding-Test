package filescanApp;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileSystemView;

import hashCalc.hashing;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class FileScanUI {

	
	private JFrame frame;
	
	final String apikey = "52cf5b11a6ba9999adc615158a724f8a";
	String path;
	static JLabel lblFilename;
	void fileChooser(){
		
	}
	JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	private JTextField textField_1;
	private JTextPane textPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileScanUI window = new FileScanUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FileScanUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		lblFilename = new JLabel("File Path");
		
		
		textPane = new JTextPane();
		
		
		JButton btnScanMD5Hash = new JButton("Scan File MD5 Hash");
		btnScanMD5Hash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				hashing hash = new hashing();
				String click = e.getActionCommand();
				String hashValue = "";
				
				if (click.equals("Scan File MD5 Hash")) {
			 
			            // invoke the showsOpenDialog function to show the save dialog
			            int r = j.showOpenDialog(null);
			 
			            // if the user selects a file
			            if (r == JFileChooser.APPROVE_OPTION)
			 
			            {
			            	path = j.getSelectedFile().getAbsolutePath();
			                lblFilename.setText(j.getSelectedFile().getAbsolutePath());
			            }
			            // if the user cancelled the operation
			            else
			                lblFilename.setText("No File Selected");
			        	}
				
				
				try {
					hashValue = hash.getMD5HashString(path);
				} 
				catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				} 
				catch (IOException e1) {
					e1.printStackTrace();
				}
				
				String url = "https://api.metadefender.com/v4/hash/" + hashValue;
				HttpClient client = HttpClient.newHttpClient();
			    HttpRequest request = HttpRequest.newBuilder()
			          .uri(URI.create(url))
			          .header("apikey", apikey)
			         // .header("accept", "application/json")
			          .build();
			    
			    client.sendAsync(request, BodyHandlers.ofString())
		          .thenApply(HttpResponse::body)
		          .thenAccept(textPane::setText)
		          .join();
			}

		});
		btnScanMD5Hash.setActionCommand("Scan File MD5 Hash");
		
		JButton btnUploadFile = new JButton("Upload File");
		btnUploadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int status;
				
				String click = e.getActionCommand();
				
				if (click.equals("Upload File")) {
			 
			            // invoke the showsOpenDialog function to show the save dialog
			            int r = j.showOpenDialog(null);
			 
			            // if the user selects a file
			            if (r == JFileChooser.APPROVE_OPTION)
			 
			            {
			            	path = j.getSelectedFile().getAbsolutePath();
			                lblFilename.setText(j.getSelectedFile().getAbsolutePath());
			            }
			            // if the user cancelled the operation
			            else
			                lblFilename.setText("No File Selected");
			        	}
				
				String url = "https://api.metadefender.com/v4/file";
				
				 HttpClient client = HttpClient.newBuilder().build();
				 HttpRequest request;
				try {
					request = HttpRequest.newBuilder()
					            .uri(URI.create(url))            
					            .header("apikey", apikey)
					            .header("content-type", "application/octet-stream")
					            .POST(BodyPublishers.ofFile(Paths.get(path)))
					            .build();
					
					HttpResponse<?> response = client.send(request, BodyHandlers.ofString());
				    status = response.statusCode();
				    textPane.setText(Integer.toString(status) + " " + response.body());
				    System.out.println(status);
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				
			}

			
		});
		
		textField_1 = new JTextField();
		
		JButton btnScanData_Id = new JButton("Scan Data_Id");
		btnScanData_Id.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String data_Id;
				String url;
				data_Id = textField_1.getText();
				url = "https://api.metadefender.com/v4/file/" + data_Id;
				
				HttpClient client = HttpClient.newHttpClient();
			    HttpRequest request = HttpRequest.newBuilder()
			          .uri(URI.create(url))
			          .header("apikey", apikey)
			          .build();
			    
			    client.sendAsync(request, BodyHandlers.ofString())
		          .thenApply(HttpResponse::body)
		          .thenAccept(textPane::setText)
		          .join();
			}
		});
		btnScanData_Id.setActionCommand("Scan Data_Id");
		textField_1.setColumns(10);
		
		JButton btnScanSHA256Hash = new JButton("Scan File SHA_256 Hash");
		btnScanSHA256Hash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				hashing hash = new hashing();
				String click = e.getActionCommand();
				String hashValue = "";
				
				if (click.equals("Scan File SHA_256 Hash")) {
			 
			            // invoke the showsOpenDialog function to show the save dialog
			            int r = j.showOpenDialog(null);
			 
			            // if the user selects a file
			            if (r == JFileChooser.APPROVE_OPTION)
			 
			            {
			            	path = j.getSelectedFile().getAbsolutePath();
			                lblFilename.setText(j.getSelectedFile().getAbsolutePath());
			            }
			            // if the user cancelled the operation
			            else
			                lblFilename.setText("No File Selected");
			        	}
				
				
				try {
					hashValue = hash.getSHA256HashString(path);
				} 
				catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				} 
				catch (IOException e1) {
					e1.printStackTrace();
				}
				
				String url = "https://api.metadefender.com/v4/hash/" + hashValue;
				HttpClient client = HttpClient.newHttpClient();
			    HttpRequest request = HttpRequest.newBuilder()
			          .uri(URI.create(url))
			          .header("apikey", apikey)
			          .build();
			    
			    client.sendAsync(request, BodyHandlers.ofString())
		          .thenApply(HttpResponse::body)
		          .thenAccept(textPane::setText)
		          .join();
			}
		});
		
		
		frame.getContentPane().add(textPane);
		JScrollPane scrollPane = new JScrollPane(textPane);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 464, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblFilename, GroupLayout.PREFERRED_SIZE, 361, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(btnUploadFile, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 361, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(btnScanData_Id))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnScanMD5Hash, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(btnScanSHA256Hash, GroupLayout.PREFERRED_SIZE, 242, GroupLayout.PREFERRED_SIZE))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 316, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFilename, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnUploadFile, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnScanData_Id, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnScanMD5Hash, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnScanSHA256Hash, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
		);
		frame.getContentPane().setLayout(groupLayout);
		
				
	}
}
