
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import com.yahoo.hooda_fci.dictionary_based_compression.DictionaryMainWindow;
import com.yahoo.hooda_fci.hufmmanTechniques.HuffmanMainWindow;
public class MainWindow extends JFrame{
	
	MainWindow()
	{
		super("Welcome");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(null);
		setResizable(false);
		//setSize(350, 130);
		setBounds(100, 100, 412, 274);
		this.getContentPane().setBackground(new Color(255, 255, 255));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		// label
		JLabel label = new JLabel("Which technique do you want to try ?\r\n ");
		label.setForeground(Color.BLUE);
		label.setFont(new Font("Rockwell Extra Bold", Font.BOLD | Font.ITALIC, 14));
		label.setBounds(10, 11, 376, 56);
		
		//dictionaryButton 
		JButton dictionaryButton = new JButton ("Dictionary Techniques");
		dictionaryButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		dictionaryButton.setBounds(75, 70, 230, 35);
		
		//huffmanButton
		JButton huffmanButton = new JButton ("Huffman Techniques");
		huffmanButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		huffmanButton.setBounds(75, 130, 230, 35);
		
		// add to panel
		add(label);
		add(dictionaryButton);
		add(huffmanButton);
		
		
		dictionaryButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
				new DictionaryMainWindow();
			}
		});
		
		huffmanButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
				new HuffmanMainWindow();
			}
		});
		
	}
}
