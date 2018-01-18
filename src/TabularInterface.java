import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 */

/**
 * @author Personal
 *
 */
public class TabularInterface {

	private JFrame frmTabularMethod;
	private JTextField textFieldMin;
	private JTextField textFieldDC;
	private JTextField textFieldoutput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					TabularInterface window = new TabularInterface();
					window.frmTabularMethod.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TabularInterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		TabularMethod start = new TabularMethod();
		frmTabularMethod = new JFrame();
		frmTabularMethod.setTitle("Tabular Method");
		frmTabularMethod.setBounds(100, 100, 450, 300);
		frmTabularMethod.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTabularMethod.getContentPane().setLayout(null);

		textFieldMin = new JTextField();
		textFieldMin.setBounds(150, 11, 235, 20);
		frmTabularMethod.getContentPane().add(textFieldMin);
		textFieldMin.setColumns(10);

		JMenuBar menuBar = new JMenuBar();

	    // File Menu, F - Mnemonic
	    JMenu fileMenu = new JMenu("File");
	    fileMenu.setMnemonic(KeyEvent.VK_F);
	    menuBar.add(fileMenu);

	    // File->New, N - Mnemonic
	    JMenuItem openMenuItem = new JMenuItem("Open File");
	    fileMenu.add(openMenuItem);

	    frmTabularMethod.setJMenuBar(menuBar);

	    JMenuItem exitItem = new JMenuItem("Exit");
	    fileMenu.add(exitItem);

	    exitItem.addActionListener(new ActionListener() {
	        @Override
			public void actionPerformed(ActionEvent ev) {
	                System.exit(0);
	        }
	    });

	    openMenuItem.addActionListener(new ActionListener() {
	        @Override
			public void actionPerformed(ActionEvent e2) {
	        	JFileChooser fileChooser = new JFileChooser();
		        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		        int result = fileChooser.showOpenDialog(frmTabularMethod);
		        if (result == JFileChooser.APPROVE_OPTION) {
		            File selectedFile = fileChooser.getSelectedFile();
		            String[] terms = start.readfile(selectedFile.getPath());
		            textFieldMin.setText(terms[0]);
		            textFieldDC.setText(terms[1]);
		        }

	        }
	    });

		JLabel lblMinterms = new JLabel("Minterms");
		lblMinterms.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblMinterms.setBounds(22, 4, 89, 32);
		frmTabularMethod.getContentPane().add(lblMinterms);



		JButton btnFindMin = new JButton("Find PI");
		btnFindMin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					start.readMinterms(textFieldMin.getText());
					start.readDC(textFieldDC.getText());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Invalid Input");
				}

				start.tabularTable();
				String output = start.content;
				textFieldoutput.setText(output);

			}
		});
		btnFindMin.setBounds(152, 191, 89, 23);
		frmTabularMethod.getContentPane().add(btnFindMin);

		JLabel lblNewLabel = new JLabel("Don't cares");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel.setBounds(22, 47, 89, 22);
		frmTabularMethod.getContentPane().add(lblNewLabel);

		textFieldDC = new JTextField();
		textFieldDC.setBounds(150, 53, 235, 20);
		frmTabularMethod.getContentPane().add(textFieldDC);
		textFieldDC.setColumns(10);

		textFieldoutput = new JTextField();
		textFieldoutput.setBounds(22, 80, 363, 101);
		frmTabularMethod.getContentPane().add(textFieldoutput);
		textFieldoutput.setColumns(10);
	}
}
