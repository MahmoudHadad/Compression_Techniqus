package com.yahoo.hooda_fci.dictionary_based_compression;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DictionaryMainWindow extends JFrame{
	
	public DictionaryMainWindow()
	{
		super("Welcome");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setSize(350, 130);
		setLocation(350, 250);
		this.getContentPane().setBackground(new Color(255, 255, 255));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setLayout(new FlowLayout());
		
		JLabel label = new JLabel("Welcome to Dictionary Based Compression.. Do You want to ");
		
		JButton compress = new JButton ("Compress");
		JButton deCompress = new JButton ("Decompress");
		
		add(label);
		add(compress);
		add(deCompress);
		
		
		compress.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new CompressWindow();
			}
		});
		
		deCompress.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new DecompressWindow();
			}
		});
		
	}
}
