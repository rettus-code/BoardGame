package view;

import model.*;
import controller.*;
import javax.swing.*;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.*;

public class BoardView extends JFrame {   
    public interface cardsOnBoard{
      public void addCards();
    }
    // Eager Initialization
    private static BoardView instance = new BoardView();

    // JLabels
    JLabel boardlabel;
    JLabel cardlabel;
    JLabel activeplayerlabel;
    JLabel player1label;
    JLabel player2label;
    JLabel player3label;
    JLabel player4label;
    JLabel player5label;
    JLabel player6label;
    JLabel player7label;
    JLabel player8label;
    JLabel mLabel;

    // JButtons
    JButton bAct;
    JButton bRehearse;
    JButton bMove;
    JButton bUpgrade;

    // JLayered Pane
    public JLayeredPane bPane;

    private ImageIcon boardIcon;

    // Private Constructor (singleton pattern)
    private BoardView() {
        // Set the title of the JFrame
        super("Deadwood");
        initBoard();
        initActionsMenu();
        activeplayerlabel = new JLabel("Current Player: ");
        activeplayerlabel.setBounds(boardIcon.getIconWidth() + 40, 0, 150, 20);
        bPane.add(activeplayerlabel, new Integer(2));
    }

    public void initBoard() {
        // Set the exit option for the JFrame
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create the JLayeredPane to hold the display, cards, dice and buttons
        bPane = getLayeredPane();

        // Create the deadwood board
        boardlabel = new JLabel();
        this.boardIcon = new ImageIcon("images/board.jpg");
        boardlabel.setIcon(boardIcon);
        boardlabel.setBounds(0, 0, boardIcon.getIconWidth(), boardIcon.getIconHeight());

        // Add the board to the lowest layer
        bPane.add(boardlabel, new Integer(0));

        // Set the size of the GUI
        setSize(boardIcon.getIconWidth() + 200, boardIcon.getIconHeight());
    }

    public static BoardView getInstance() {
        return instance;
    }

    public void addCard(String image, int[] point) {
        // Add a scene card to this room
        cardlabel = new JLabel();
        System.out.println(image + " " + point[0] + " " + point[1]);
        ImageIcon cIcon = new ImageIcon("images/cards/" + image);
        cardlabel.setIcon(cIcon);
        cardlabel.setBounds(point[0], point[1], cIcon.getIconWidth() + 2, cIcon.getIconHeight());
        cardlabel.setOpaque(true);

        // Add the card to the lower layer
        bPane.add(cardlabel, new Integer(1));
    }

    public void initPlayerDice(Player[] playerArray) {
        // Add a dice to represent a player.
        player1label = new JLabel();
        ImageIcon pIcon = new ImageIcon("images/dice/r2.png");
        player1label.setIcon(pIcon);
        player1label.setBounds(boardIcon.getIconWidth() - 220, (200 + (1 * 50)), pIcon.getIconWidth(),
                pIcon.getIconHeight());
        bPane.add(player1label, new Integer(2));

        player2label = new JLabel();
        pIcon = new ImageIcon("images/dice/o2.png");
        player2label.setIcon(pIcon);
        player2label.setBounds(boardIcon.getIconWidth() - 220, (200 + (2 * 50)), pIcon.getIconWidth(),
                pIcon.getIconHeight());
        bPane.add(player2label, new Integer(2));

        player3label = new JLabel();
        pIcon = new ImageIcon("images/dice/y2.png");
        player3label.setIcon(pIcon);
        player3label.setBounds(boardIcon.getIconWidth() - 220, (200 + (3 * 50)), pIcon.getIconWidth(),
                pIcon.getIconHeight());
        if (playerArray[2] == null) {
            player3label.setVisible(false);
        }
        bPane.add(player3label, new Integer(2));

        player4label = new JLabel();
        pIcon = new ImageIcon("images/dice/g2.png");
        player4label.setIcon(pIcon);
        player4label.setBounds(boardIcon.getIconWidth() - 220, (200 + (4 * 50)), pIcon.getIconWidth(),
                pIcon.getIconHeight());
        if (playerArray[3] == null) {
            player4label.setVisible(false);
        }
        bPane.add(player4label, new Integer(2));

        player5label = new JLabel();
        pIcon = new ImageIcon("images/dice/b2.png");
        player5label.setIcon(pIcon);
        player5label.setBounds(boardIcon.getIconWidth() - 50, (200 + (1 * 50)), pIcon.getIconWidth(),
                pIcon.getIconHeight());
        if (playerArray[4] == null) {
            player5label.setVisible(false);
        }
        bPane.add(player5label, new Integer(2));

        player6label = new JLabel();
        pIcon = new ImageIcon("images/dice/v2.png");
        player6label.setIcon(pIcon);
        player6label.setBounds(boardIcon.getIconWidth() - 50, (200 + (2 * 50)), pIcon.getIconWidth(),
                pIcon.getIconHeight());
        if (playerArray[5] == null) {
            player6label.setVisible(false);
        }
        bPane.add(player6label, new Integer(2));

        player7label = new JLabel();
        pIcon = new ImageIcon("images/dice/p2.png");
        player7label.setIcon(pIcon);
        player7label.setBounds(boardIcon.getIconWidth() - 50, (200 + (3 * 50)), pIcon.getIconWidth(),
                pIcon.getIconHeight());
        if (playerArray[6] == null) {
            player7label.setVisible(false);
        }
        bPane.add(player7label, new Integer(2));

        player8label = new JLabel();
        pIcon = new ImageIcon("images/dice/w2.png");
        player8label.setIcon(pIcon);
        player8label.setBounds(boardIcon.getIconWidth() - 50, (200 + (4 * 50)), pIcon.getIconWidth(),
                pIcon.getIconHeight());
        if (playerArray[7] == null) {
            player8label.setVisible(false);
        }
        bPane.add(player8label, new Integer(2));
    }

    public void initActionsMenu() {
        // Create the Menu for action buttons
        mLabel = new JLabel("MENU");
        mLabel.setBounds(boardIcon.getIconWidth() + 40, 50, 100, 20);
        bPane.add(mLabel, new Integer(2));

        // Create Action buttons
        bAct = new JButton("ACT");
        bAct.setBackground(Color.white);
        bAct.setBounds(boardIcon.getIconWidth() + 10, 80, 100, 20);
        bAct.addMouseListener(new BoardMouseListener());

        bRehearse = new JButton("REHEARSE");
        bRehearse.setBackground(Color.white);
        bRehearse.setBounds(boardIcon.getIconWidth() + 10, 110, 100, 20);
        bRehearse.addMouseListener(new BoardMouseListener());

        bMove = new JButton("MOVE");
        bMove.setBackground(Color.white);
        bMove.setBounds(boardIcon.getIconWidth() + 10, 140, 100, 20);
        bMove.addMouseListener(new BoardMouseListener());

        bUpgrade = new JButton("UPGRADE");
        bUpgrade.setBackground(Color.white);
        bUpgrade.setBounds(boardIcon.getIconWidth() + 10, 170, 100, 20);
        bUpgrade.addMouseListener(new BoardMouseListener());

        // Place the action buttons in the top layer
        bPane.add(bAct, new Integer(2));
        bPane.add(bRehearse, new Integer(2));
        bPane.add(bMove, new Integer(2));
        bPane.add(bUpgrade, new Integer(2));
    }
    
    // update the active player label
    public void updateActivePlayerLabel(Player active) {
        activeplayerlabel.setText("Current Player: " + active.getName());
    }

    public void movePlayerDie(Player active) {
        Point p = new Point(active.getLocationX(), active.getLocationY());
        int activePlayerNum = active.getID() + 1;
        switch (activePlayerNum) {
            case 1:
                player1label.setLocation(p);
                break;
            case 2:
                player2label.setLocation(p);
                break;
            case 3:
                player3label.setLocation(p);
                break;
            case 4:
                player4label.setLocation(p);
                break;
            case 5:
                player5label.setLocation(p);
                break;
            case 6:
                player6label.setLocation(p);
                break;
            case 7:
                player7label.setLocation(p);
                break;
            case 8:
                player8label.setLocation(p);
                break;
        }

    }
}
