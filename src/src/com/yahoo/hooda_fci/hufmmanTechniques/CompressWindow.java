package com.yahoo.hooda_fci.hufmmanTechniques;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;


public class CompressWindow extends JFrame{
	
	private JButton compressB ;
	private JButton browseB ;
	private JRadioButton standardRB;
	private JRadioButton modifiedRB;
	private JRadioButton advancedRB;
	private JFileChooser chooseInputFile;
	private JFileChooser chooseOutputFile;
	private ButtonGroup bg;
	private File sourceFile;
	private File destinationFile;
	
	private JLabel label;
	String tech="Standard Huffman";
	CompressWindow()
	{
		super("Compression window");
		
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		//setResizable(false);
		setBounds(100, 100, 455, 180);
		this.getContentPane().setBackground(new Color(255, 255, 255));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		
		// Select Files
		
		
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
		compressB = new JButton("Compress");
		compressB.setFont(new Font("Tahoma", Font.BOLD, 12));
		compressB.setBounds(158, 66, 101, 23);
		
		//browse
		browseB = new JButton("Browse");
		browseB.setFont(new Font("Tahoma", Font.BOLD, 12));
		browseB.setBounds(305, 66, 101, 23);
		
		//label
		label = new JLabel("Standard Huffman");
		label.setFont(new Font("Tahoma", Font.BOLD, 18));
		label.setBounds(135, 66+23+10, 181, 23);
		
		
		
		// add to group button
		bg.add(standardRB);
		bg.add(modifiedRB);
		bg.add(advancedRB);
		
		// add to panel
		add(standardRB);
		add(modifiedRB);
		add(advancedRB);
		add(compressB);
		add(label);
		add(browseB);
		// Actions
		standardRB.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				label.setText("Standard Huffman");
				tech="Standard Huffman";
			}
		});
		
		modifiedRB.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				label.setText("Modified Huffman");
				tech="Modified Huffman";
			}
		});
		
		advancedRB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				label.setText("Advanced Huffman");
				tech="Advanced Huffman";
			}
		});
		
		compressB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(standardRB.isSelected())
				{
					StandardHuffman.compress(DealWithFiles.readFile(sourceFile).toString(), destinationFile);
				}
				
				else if (modifiedRB.isSelected())
				{
					ModifiedHuffman.compress(DealWithFiles.readFile(sourceFile).toString(), destinationFile);
				}
				
				else
				{
					
				}
				
				JOptionPane.showMessageDialog(null, "The compressed data created in \n" + destinationFile.getPath());
			}
			
			
			
		});
		
		browseB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				chooseInputFile = new JFileChooser("D:");
				chooseInputFile.setDialogTitle("Choose file to compress");
				
				
				while(! (chooseInputFile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION))
					JOptionPane.showMessageDialog(null,"Error" , "No file is selected", JOptionPane.ERROR_MESSAGE);
				
				chooseOutputFile = new JFileChooser("D:");
				chooseOutputFile.setDialogTitle("Choose Destination path");
				chooseOutputFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				while(! (chooseOutputFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION))
					JOptionPane.showMessageDialog(null,"Error" , "No file is selected", JOptionPane.ERROR_MESSAGE);
				
				sourceFile = chooseInputFile.getSelectedFile();
				
				try {
					File dest = chooseOutputFile.getSelectedFile();
					destinationFile = new File(dest.getPath()+"\\compressed_"+sourceFile.getName());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "this");
				}
			}
		});
		
		chooseInputFile = new JFileChooser("D:");
		chooseInputFile.setDialogTitle("Choose file to compress");
		
		
		while(! (chooseInputFile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION))
			JOptionPane.showMessageDialog(this,"Error" , "No file is selected", JOptionPane.ERROR_MESSAGE);
		
		chooseOutputFile = new JFileChooser("D:");
		chooseOutputFile.setDialogTitle("Choose Destination path");
		chooseOutputFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		while(! (chooseOutputFile.showSaveDialog(this) == JFileChooser.APPROVE_OPTION))
			JOptionPane.showMessageDialog(this,"Error" , "No file is selected", JOptionPane.ERROR_MESSAGE);
		
		sourceFile = chooseInputFile.getSelectedFile();
		
		try {
			File dest = chooseOutputFile.getSelectedFile();
			destinationFile = new File(dest.getPath()+"\\compressed_"+sourceFile.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "this");
		}
		
		
	}
}
