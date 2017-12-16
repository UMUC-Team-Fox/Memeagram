package memeagram.ui;

import memeagram.Context;
import memeagram.data.objects.Meme;
import memeagram.data.objects.User;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class BrowsePanel extends JPanel {

    public User user;
    Meme meme;
    ArrayList<Meme> memeList;
    Integer currentMemeNum = 0;
    Integer totalNumOfMemes = 0;
    Context context;
    JLabel imageLabel;

    JLabel lblMemePopularity = new JLabel();
    JLabel lblMemeCount = new JLabel();

    BrowsePanel(Context context){
        setPreferredSize(new Dimension(400,400));
        setBackground(Color.WHITE);

        this.context = context;

        Box topBox1 = Box.createHorizontalBox();
        topBox1.add(new JLabel("Tags Search : "));
        JTextField tagsTF = new JTextField(15);
        tagsTF.setMaximumSize(tagsTF.getPreferredSize());
        topBox1.add(tagsTF);
        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> {
            try {
                memeList = Meme.getMemesByTag(context,tagsTF.getText());
                if(!memeList.isEmpty()) {
                    totalNumOfMemes = memeList.size();
                    updateMemeImage(0);
                }
            }catch(Exception ex) {
                ex.printStackTrace();
            }
        });
        topBox1.add(btnSearch);
        this.add(topBox1);

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
        btnSearchRating.addActionListener(e -> {
            try{
                memeList = Meme.getAllMemes(context);
                if (rBtnHighest.isSelected()) {
                    memeList.sort(Meme.SORT_RATING_DESCENDING);
                }
                else if (rBtnLowest.isSelected()) {
                    memeList.sort(Meme.SORT_RATING_ASCENDING);
                }
                updateMemeImage(0);
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }

            if(memeList.isEmpty()) {
                System.out.println("Search was unsuccessful");
            }else {
                System.out.println("Search was successful");
            }
        });
        topBox2.add(btnSearchRating);
        this.add(Box.createVerticalStrut(10));
        this.add(topBox2);

        imageLabel = new JLabel();
        imageLabel.setSize(new Dimension(400, 400));
        imageLabel.setBackground(Color.white);
        imageLabel.setPreferredSize(new Dimension (400,400));

        Box imageBox = Box.createVerticalBox();
        imageBox.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        imageBox.add(imageLabel);
        this.add(imageBox);

        Box bottomBox1 = Box.createHorizontalBox();
        JButton btnPrev = new JButton("Previous");
        btnPrev.addActionListener(e -> {
            currentMemeNum -= 1;
            try{updateMemeImage(currentMemeNum);}catch (Exception ex) {}

        });
        bottomBox1.add(btnPrev);
        bottomBox1.add(Box.createHorizontalStrut(30));
        bottomBox1.add(lblMemeCount);
        bottomBox1.add(Box.createHorizontalStrut(30));
        JButton btnNext = new JButton("Next");
        btnNext.addActionListener(e -> {
            currentMemeNum += 1;
            try{updateMemeImage(currentMemeNum);}catch (Exception ex) {}

        });
        bottomBox1.add(btnNext);
        this.add(bottomBox1);

        Box bottomBox2 = Box.createHorizontalBox();
        JButton btnLike = new JButton("Like");
        btnLike.addActionListener(e -> {
            try{
                Meme.likeMeme(context, user.id, meme.id, true);
                updateMemeImage(currentMemeNum);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        bottomBox2.add(btnLike);
        bottomBox2.add(Box.createHorizontalStrut(30));
        bottomBox2.add(lblMemePopularity);
        JButton btnDislike = new JButton("Dislike");
        btnDislike.addActionListener(e -> {
            try{
                Meme.likeMeme(context, user.id, meme.id, false);
                updateMemeImage(currentMemeNum);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        bottomBox2.add(Box.createHorizontalStrut(30));
        bottomBox2.add(btnDislike);
        this.add(Box.createVerticalStrut(30));
        this.add(bottomBox2);
    }

    public void updateMemeImage(Integer i) {
        meme = memeList.get(i);
        meme.getImage();
        imageLabel.setIcon(new ImageIcon(meme.memeImage));
        imageLabel.validate();
        imageLabel.repaint();

        meme.getLikes();
        meme.getDislikes();

        lblMemePopularity.setText("Likes : " +Integer.toString(meme.numLikes) + "  Dislikes : " + Integer.toString(meme.numDislikes));
        lblMemeCount.setText(Integer.toString(currentMemeNum + 1) + " of " + Integer.toString(memeList.size()));
    }




}
