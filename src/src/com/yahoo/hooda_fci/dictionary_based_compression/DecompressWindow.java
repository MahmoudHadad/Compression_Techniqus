package com.yahoo.hooda_fci.dictionary_based_compression;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.io.File;

public class DecompressWindow extends JFrame{

	private JButton deCompressB ;
	private JRadioButton LZ77RB;
	private JRadioButton LZ78RB;
	private JRadioButton LZWRB;
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
		LZ77RB = new JRadioButton("LZ77");
		LZ77RB.setSelected(true);
		LZ77RB.setBounds(6, 17, 109, 23);

		LZ78RB = new JRadioButton("LZ78");
		LZ78RB.setBounds(158, 17, 109, 23);

		LZWRB = new JRadioButton("LZW");
		LZWRB.setBounds(305, 17, 109, 23);

		// Compress Button
		deCompressB = new JButton("Decompress");
		deCompressB.setFont(new Font("Tahoma", Font.BOLD, 12));
		deCompressB.setBounds(158, 66, 111, 23);
		

		label = new JLabel("LZ77");
		label.setFont(new Font("Tahoma", Font.BOLD, 18));
		label.setBounds(190, 66+23+10, 101, 23);
		
		add(label);
		
		// Actions
		LZ77RB.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				label.setText("LZ77");
			}
		});
		
		LZ78RB.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				label.setText("LZ78");
			}
		});
		
		LZWRB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				label.setText("LZW");
			}
		});
		
		// add to group button
		bg.add(LZ77RB);
		bg.add(LZ78RB);
		bg.add(LZWRB);

		// add to panel
		add(LZ77RB);
		add(LZ78RB);
		add(LZWRB);
		add(deCompressB);


		// Actions
		deCompressB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if(LZ77RB.isSelected())
				{
					StringBuilder result = DealWithFiles.readFile(sourceFile);

					DealWithFiles.printOnFile(destinationFile, LZ77.deCompress(result.toString() , new StringBuilder() ));

				}

				else if (LZ78RB.isSelected())
				{

				}

				else
				{
					StringBuilder result = DealWithFiles.readFile(sourceFile);

					DealWithFiles.printOnFile(destinationFile, LZW.deCompress(result.toString()));
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
