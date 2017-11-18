package UI;
/*
 * Class : Main
 * Description : Driver class to instantiate and instance of memegrame applicaton
 * Revision Date : 11/11/2017
 * Revision Number: 1
 * Authors : Team Foxtrot 
 */

import ImageManipulation.ImageController;
import java.awt.Color;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;  

public class Memeagram{

    JFrame f;
    JPanel mainPanel,createPanel,browsePanel;
    JButton btnFileChooser, btnAddText;
    JTextField jtf;
    JTabbedPane tp;
    BufferedImage cImage;
    JLabel imageLabel;
    ImageController ic;
    JFileChooser jfc;
    File workingFile;

    public Memeagram() throws IOException{  //Constructor
        f = new JFrame();
        ic = new ImageController();
        jfc = new JFileChooser(FileSystemView.getFileSystemView());
        File wkDir = new File(System.getProperty("user.dir"));
        jfc.setCurrentDirectory(wkDir);
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        //Panels and layouts
        mainPanel = new JPanel();
        createPanel = new JPanel();
        browsePanel = new JPanel();
        
        //Objects and configurations
        imageLabel = new JLabel();
        imageLabel.setSize(600, 600);
        imageLabel.setBackground(Color.white);

        
        
        //Interactive objects and configurations
        btnFileChooser = new JButton("Browse");
        btnAddText = new JButton("Add Text");
        jtf = new JTextField(15);
        
        //Adding objects to panels
        createPanel.add(btnFileChooser);
        createPanel.add(jtf);
        createPanel.add(btnAddText);
        createPanel.add(imageLabel);
      
        //Listeners
        //Button listener for file chooser button
        btnFileChooser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int returnValue = jfc.showOpenDialog(null);
				if(returnValue == JFileChooser.APPROVE_OPTION) {
						
					    workingFile = jfc.getSelectedFile();
					    try{
					    cImage = ic.getImage(workingFile);
					    cImage = ic.resizeImage(cImage, 400, 400);
					    imageLabel.setIcon(new ImageIcon(cImage));
						createPanel.validate();
						createPanel.repaint(); // refresh the create panel
					    }catch(IOException ex) {}
				}
			}
        	
        });
        
        //Button Listener for Add Text button
        btnAddText.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				
				try{ic.addText(cImage, jtf.getText());} catch(IOException ex){}
				createPanel.validate();
				createPanel.repaint(); // refresh the create panel
			}
        	
        });
        
        
        //Tabbed Pane setup
        tp = new JTabbedPane();
        tp.setBounds(50, 50, 600, 600);
        tp.add(" Home ", mainPanel);
        tp.add(" Create ", createPanel);
        tp.add(" Browse ", browsePanel);
        f.add(tp);
        f.setSize(700,700);
        f.setLayout(null);
        f.setVisible(true);
        
    }

}