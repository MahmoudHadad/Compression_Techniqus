package com.yahoo.hooda_fci.hufmmanTechniques;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.io.File;

public class DecompressWindow extends JFrame{

	private JButton deCompressB ;
	private JRadioButton standardRB;
	private JRadioButton modifiedRB;
	private JRadioButton advancedRB;
	private JFileChooser chooseInputFile;
	private JFileChooser chooseOutputFile;
	private ButtonGroup bg;
	private File sourceFile;
	private File destinationFile;
	private JLabel label;
	
	DecompressWindow()
	{
		super("Decompression window");

		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		//setResizable(false);
		setBounds(100, 100, 455, 180);
		this.getContentPane().setBackground(new Color(255, 255, 255));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);



		// Group button
		bg= new ButtonGroup();

		// Radio Buttons
		standardRB = new JRadioButton("Standard Huffman");
		standardRB.setSelected(true);
		standardRB.setBounds(6, 17, 129, 23);
		
		modifiedRB = new JRadioButton("Modified Huffman");
		modifiedRB.setBounds(158, 17, 129, 23);
		
		advancedRB = new JRadioButton("Advanced Huffman");
		advancedRB.setBounds(305, 17, 129, 23);
		

		// Compress Button
		deCompressB = new JButton("Decompress");
		deCompressB.setFont(new Font("Tahoma", Font.BOLD, 12));
		deCompressB.setBounds(158, 66, 111, 23);
		

		label = new JLabel("Standard Huffman");
		label.setFont(new Font("Tahoma", Font.BOLD, 18));
		label.setBounds(135, 66+23+10, 181, 23);
		
		add(label);
		
		// Actions
		standardRB.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				label.setText("Standard Huffman");
			}
		});
		
		modifiedRB.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				label.setText("Modified Huffman");
			}
		});
		
		advancedRB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				label.setText("Advanced Huffman");
			}
		});
		
		// add to group button
		bg.add(standardRB);
		bg.add(modifiedRB);
		bg.add(advancedRB);

		// add to panel
		add(standardRB);
		add(modifiedRB);
		add(advancedRB);
		add(deCompressB);


		// Actions
		deCompressB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if(standardRB.isSelected())
				{
					
					String x = StandardHuffman.deCompress(sourceFile);
					DealWithFiles.printOnFile(destinationFile, x);
				}

				else if (modifiedRB.isSelected())
				{

				}

				else
				{
					
					
				}

				JOptionPane.showMessageDialog(null, "The decompressed data created in \n" + destinationFile.getPath());
			}

		});
		
		// Select Files
		chooseInputFile = new JFileChooser("D:");
		chooseInputFile.setDialogTitle("Choose file to Decompress");

		while(! (chooseInputFile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION))
			JOptionPane.showMessageDialog(this,"Error" , "No file is selected", JOptionPane.ERROR_MESSAGE);

		chooseOutputFile = new JFileChooser("D:");
		chooseOutputFile.setDialogTitle("Choose Destination file");
		chooseOutputFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		while(! (chooseOutputFile.showSaveDialog(this) == JFileChooser.APPROVE_OPTION))
			JOptionPane.showMessageDialog(this,"Error" , "No file is selected", JOptionPane.ERROR_MESSAGE);
		
		sourceFile = chooseInputFile.getSelectedFile();
		try {
			File dest = chooseOutputFile.getSelectedFile();
			destinationFile = new File(dest.getPath()+"\\decompressed_"+sourceFile.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "this");
		}
		
		
	}
}
