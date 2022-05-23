package view;

import model.*;
import controller.*;
import javax.swing.*;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.*;
import java.util.*;

public class BoardView extends JFrame {
    public interface cardsOnBoard {
        public void addCards();
    }

    public class BoardMouseListener implements MouseListener {
        // Code for the different button clicks
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == bAct) {
                // playerlabel.setVisible(true);
                if(bAct.isEnabled()) {
                    System.out.println("Acting is Selected\n");
                } 
            } else if (e.getSource() == bRehearse) {
                if(bRehearse.isEnabled()) {
                    System.out.println("Rehearse is Selected\n");
                }
            } else if (e.getSource() == bMove) {
                if(bMove.isEnabled()) {
                    System.out.println("Move is Selected\n");
                    // call method in the controller to get the neighboring rooms
                    BoardLayersListener.getInstance().getMoveMenu();
                }
            } else if (e.getSource() == bUpgrade) {
                if(bUpgrade.isEnabled()) {
                    System.out.println("Upgrade is Selected\n");
                }
            } else if (e.getSource() == bEndTurn) {
                BoardLayersListener.getInstance().endTurn();
            } else if (e.getSource() == bTakeRole) {
                if(bTakeRole.isEnabled()) {
                    System.out.println("TakeRole is Selected\n");
                }
            }
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }



    // Eager Initialization
    private static BoardView instance = new BoardView();

    // JLabels
    JLabel boardlabel;
    JLabel templabel;
    JLabel cardlabel0;
    JLabel cardlabel1;
    JLabel cardlabel2;
    JLabel cardlabel3;
    JLabel cardlabel4;
    JLabel cardlabel5;
    JLabel cardlabel6;
    JLabel cardlabel7;
    JLabel cardlabel8;
    JLabel cardlabel9;
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
    JButton bEndTurn;
    JButton bTakeRole;
    JButton bMoveOk = new JButton("Ok");
    JButton bMoveCancel = new JButton("Cancel");

    // JLayered Pane
    public JLayeredPane bPane;
    public JPanel actionsPanel = new JPanel();
    public JPanel movePanel = new JPanel();

    private ImageIcon boardIcon;


    JComboBox roomList;


    private JLabel[] cardLabels = {cardlabel0, cardlabel1, cardlabel2, cardlabel3, cardlabel4, cardlabel5, cardlabel6, cardlabel7, cardlabel8, cardlabel9};

    // Private Constructor (singleton pattern)
    private BoardView() {
        // Set the title of the JFrame
        super("Deadwood");
        initBoard();
        initActionsMenu();
        activeplayerlabel = new JLabel("Current Player: ");
        activeplayerlabel.setBounds(boardIcon.getIconWidth() + 40, 0, 150, 20);
        bPane.add(activeplayerlabel, new Integer(2));
        bPane.add(actionsPanel, new Integer(2));
        bPane.add(movePanel, new Integer(2));
        movePanel.setVisible(false);
        actionsPanel.setVisible(true);
        movePanel.setBounds((boardIcon.getIconWidth() + 10), 50, 130, 130);
        actionsPanel.setBounds((boardIcon.getIconWidth() + 10), 50, 100, 300);
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

        // Setup the Move menu
        initMoveMenu();
    }

    public void initGameState() {
        // fetch the game state
        BoardLayersListener.getInstance().stateChanged();
    }

    public static BoardView getInstance() {
        return instance;
    }

    public void addCard(String image, int[] point, int roomNum) {
        // Add a scene card to this room
        templabel = new JLabel();
        ImageIcon cIcon = new ImageIcon("images/cards/" + image);
        templabel.setIcon(cIcon);
        templabel.setBounds(point[0], point[1], cIcon.getIconWidth() + 2, cIcon.getIconHeight());
        templabel.setOpaque(true);
        // Add the card to the lower layer
        cardLabels[roomNum] = templabel;
        bPane.add(cardLabels[roomNum], new Integer(1));
    }
    public void flipCard(String image, int[] point, int roomNum){
      System.out.println("flip " + image + " " + point[0] + " " + point[1]);
        Container parent = cardLabels[roomNum].getParent();
        parent.remove(cardLabels[roomNum]);
        parent.validate();
        parent.repaint();
        templabel = new JLabel();
        ImageIcon cIcon = new ImageIcon("images/cards/" + image);
        templabel.setIcon(cIcon);
        templabel.setBounds(point[0], point[1], cIcon.getIconWidth() + 2, cIcon.getIconHeight());
        templabel.setOpaque(true);
        // Add the card to the lower layer
        cardLabels[roomNum] = templabel;
        bPane.add(cardLabels[roomNum], new Integer(2));
       // // Add a scene card to this room
//         cardlabel = new JLabel();
//         ImageIcon cIcon = new ImageIcon("images/cards/" + image);
//         cardlabel.setIcon(cIcon);
//         cardlabel.setBounds(point[0], point[1], cIcon.getIconWidth() + 2, cIcon.getIconHeight());
//         cardlabel.setOpaque(true);
//         // Add the card to the lower layer
//         bPane.add(cardlabel, new Integer(2));
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

    public void initMoveMenu() {
        Point p = movePanel.getLocation();
        
        JLabel moveMenuLabel = new JLabel();
        moveMenuLabel.setText("Choose a room:");
        moveMenuLabel.setBounds((int) p.getX(), (int) (p.getY()), 150, 40);
        movePanel.add(moveMenuLabel, new Integer(2));

        roomList = new JComboBox();
        roomList.setBounds((int) p.getX(), (int) (p.getY() + 20), 150, 40);
        movePanel.add(roomList, new Integer(2));

        bMoveOk.setBackground(Color.green);
        bMoveOk.setBounds(boardIcon.getIconWidth() + 10, 190, 40, 20);
        bMoveOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // submit the move to controller
                BoardLayersListener.getInstance().submitPlayerMove(roomList.getSelectedItem().toString());
                // hide the move menu
                actionsPanel.setVisible(true);
                // show the actions menu
                movePanel.setVisible(false);
            }
        });

        bMoveCancel.setBackground(Color.white);
        bMoveCancel.setBounds(boardIcon.getIconWidth() + 30, 190, 40, 20);
        bMoveCancel.addMouseListener(new BoardMouseListener());
        bMoveCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // hide the move menu
                actionsPanel.setVisible(true);
                // show the actions menu
                movePanel.setVisible(false);

            }
        });
        movePanel.add(bMoveOk, new Integer(2));
        movePanel.add(bMoveCancel, new Integer(2));
    }

    public void initActionsMenu() {
        // Create the Menu for action buttons
        mLabel = new JLabel("MENU");
        mLabel.setBounds(boardIcon.getIconWidth() + 40, 50, 100, 20);
        actionsPanel.add(mLabel, new Integer(2));

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

        
        bTakeRole = new JButton("TAKE ROLE");
        bTakeRole.setBackground(Color.white);
        bTakeRole.setBounds(boardIcon.getIconWidth() + 10, 170, 100, 20);
        bTakeRole.addMouseListener(new BoardMouseListener());

        bUpgrade = new JButton("UPGRADE");
        bUpgrade.setBackground(Color.white);
        bUpgrade.setBounds(boardIcon.getIconWidth() + 10, 200, 100, 20);
        bUpgrade.addMouseListener(new BoardMouseListener());

        bEndTurn = new JButton("END TURN");
        bEndTurn.setBackground(Color.white);
        bEndTurn.setBounds(boardIcon.getIconWidth() + 10, 230, 100, 20);
        bEndTurn.addMouseListener(new BoardMouseListener());

        // Place the action buttons in the top layer
        actionsPanel.add(bAct, new Integer(2));
        actionsPanel.add(bRehearse, new Integer(2));
        actionsPanel.add(bMove, new Integer(2));
        actionsPanel.add(bTakeRole, new Integer(2));
        actionsPanel.add(bUpgrade, new Integer(2));
        actionsPanel.add(bEndTurn, new Integer(2));

    }


    public void showActionMenu(ArrayList<String> actions) {
        if(actions.contains(new String("act"))) {
            bAct.setEnabled(true);
        } else {
            bAct.setEnabled(false);
        }

        if(actions.contains(new String("rehearse"))) {
            bRehearse.setEnabled(true);
        } else {
            bRehearse.setEnabled(false);
        }

        if(actions.contains(new String("move"))) {
            bMove.setEnabled(true);
        } else {
            bMove.setEnabled(false);
        } 
        
        if(actions.contains(new String("upgrade"))) {
            bUpgrade.setEnabled(true);
        } else {
            bUpgrade.setEnabled(false);
        }

        if(actions.contains(new String("takerole"))) {
            bTakeRole.setEnabled(true);
        } else {
            bTakeRole.setEnabled(false);
        }
    }

    // update the active player label
    public void updateActivePlayerLabel(Player active) {
        activeplayerlabel.setText("Current Player " + (active.getID()+1) + ": " + active.getName());
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

    public void showMoveMenu(String[] neighbors) {
        // hide the actions menu
        actionsPanel.setVisible(false);
        // show the moves menu
        movePanel.setVisible(true);
        roomList.removeAllItems();
        for (String neighbor : neighbors) {
            roomList.addItem(neighbor);
        }
        roomList.setSelectedIndex(0);
    }
}
