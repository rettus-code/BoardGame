package view;
import controller.*;
import javax.swing.*;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class BoardView extends JFrame {
    // Eager Initialization
    private static BoardView instance = new BoardView();

    // JLabels
    JLabel boardlabel;
    JLabel cardlabel;
    JLabel playerlabel;
    JLabel mLabel;

    // JButtons
    JButton bAct;
    JButton bRehearse;
    JButton bMove;

    // JLayered Pane
    JLayeredPane bPane;

    private ImageIcon boardIcon;

    // Private Constructor (singleton pattern)
    private BoardView() {
        // Set the title of the JFrame
        super("Deadwood");
        initBoard();
        initActionsMenu();
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

    public void addCard() {
        // Add a scene card to this room
        cardlabel = new JLabel();
        ImageIcon cIcon = new ImageIcon("images/cards/01.png");
        cardlabel.setIcon(cIcon);
        cardlabel.setBounds(20, 65, cIcon.getIconWidth() + 2, cIcon.getIconHeight());
        cardlabel.setOpaque(true);

        // Add the card to the lower layer
        bPane.add(cardlabel, new Integer(1));

    }

    public void initPlayerDice() {
        // Add a dice to represent a player.
        // Role for Crusty the prospector. The x and y co-ordiantes are taken from
        // Board.xml file
        playerlabel = new JLabel();
        ImageIcon pIcon = new ImageIcon("r2.png");
        playerlabel.setIcon(pIcon);
        // playerlabel.setBounds(114,227,pIcon.getIconWidth(),pIcon.getIconHeight());
        playerlabel.setBounds(114, 227, 46, 46);
        playerlabel.setVisible(false);
        bPane.add(playerlabel, new Integer(3));
    }

    public void initActionsMenu() {
        // Create the Menu for action buttons
        mLabel = new JLabel("MENU");
        mLabel.setBounds(boardIcon.getIconWidth() + 40, 0, 100, 20);
        bPane.add(mLabel, new Integer(2));

        // Create Action buttons
        bAct = new JButton("ACT");
        bAct.setBackground(Color.white);
        bAct.setBounds(boardIcon.getIconWidth() + 10, 30, 100, 20);
        bAct.addMouseListener(new BoardMouseListener());

        bRehearse = new JButton("REHEARSE");
        bRehearse.setBackground(Color.white);
        bRehearse.setBounds(boardIcon.getIconWidth() + 10, 60, 100, 20);
        bRehearse.addMouseListener(new BoardMouseListener());

        bMove = new JButton("MOVE");
        bMove.setBackground(Color.white);
        bMove.setBounds(boardIcon.getIconWidth() + 10, 90, 100, 20);
        bMove.addMouseListener(new BoardMouseListener());

        // Place the action buttons in the top layer
        bPane.add(bAct, new Integer(2));
        bPane.add(bRehearse, new Integer(2));
        bPane.add(bMove, new Integer(2));
    }
}
