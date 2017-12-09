package memeagram.ui;

import memeagram.Context;
import memeagram.data.objects.Meme;
import memeagram.data.objects.User;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class BrowsePanel extends JPanel {
	
	Meme meme;
	
	BrowsePanel(Context context){
		setPreferredSize(new Dimension(400,400));
        setBackground(Color.WHITE);
        
        Box mainBox = Box.createVerticalBox();
        Box topBox1 = Box.createHorizontalBox();
        topBox1.add(new JLabel("Tags Search : "));
        JTextField tagsTF = new JTextField(15);
        topBox1.add(tagsTF);
        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// stuff
				
			}
        	
        });
        topBox1.add(btnSearch);
        mainBox.add(topBox1);
        
        Box topBox2 = Box.createHorizontalBox();
        topBox2.add(new JLabel("Likes Search : "));
        ButtonGroup bg = new ButtonGroup();
        JRadioButton rBtnHighest = new JRadioButton("Highest Rated");
        JRadioButton rBtnLowest = new JRadioButton("Lowest Rated");
        bg.add(rBtnHighest);
        bg.add(rBtnLowest);
        topBox2.add(rBtnHighest);
        topBox2.add(rBtnLowest);
        JButton btnSearchRating = new JButton("Search Rated");
        topBox2.add(btnSearchRating);
        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(topBox2);
        
        
        
        this.add(mainBox);
	}
	


}
