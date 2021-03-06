package view;

import model.*;
import controller.*;
import javax.swing.*;

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
                if (bAct.isEnabled()) {
                    showActMenu();
                }
            } else if (e.getSource() == bRehearse) {
                if (bRehearse.isEnabled()) {
                    showRehearseMenu();
                }
            } else if (e.getSource() == bMove) {
                if (bMove.isEnabled()) {
                    // call method in the controller to get the neighboring rooms
                    BoardLayersListener.getInstance().getMoveMenu();
                }
            } else if (e.getSource() == bUpgrade) {
                if (bUpgrade.isEnabled()) {
                    BoardLayersListener.getInstance().getUpgradesMenu();
                }
            } else if (e.getSource() == bEndTurn) {
                BoardLayersListener.getInstance().endTurn();
            } else if (e.getSource() == bTakeRole) {
                if (bTakeRole.isEnabled()) {
                    BoardLayersListener.getInstance().getPossibleRolesMenu();
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
    JLabel activeplayerlabel; // says whose turn it is in upper right corner
    JLabel player1label;
    JLabel player1Decorator;
    JLabel player2label;
    JLabel player2Decorator;
    JLabel player3label;
    JLabel player3Decorator;
    JLabel player4label;
    JLabel player4Decorator;
    JLabel player5label;
    JLabel player5Decorator;
    JLabel player6label;
    JLabel player6Decorator;
    JLabel player7label;
    JLabel player7Decorator;
    JLabel player8label;
    JLabel player8Decorator;
    JLabel mLabel;
    JLabel shotLabel;
    
    // JButtons
    JButton bAct;
    JButton bRehearse;
    JButton bMove;
    JButton bUpgrade;
    JButton bEndTurn;
    JButton bTakeRole;
    JButton bMoveOk = new JButton("Ok"); // ok button in the move menu
    JButton bMoveCancel = new JButton("Cancel"); // cancel button in the move menu
    JButton bTakeRoleOk = new JButton("Ok"); // ok button in the take role menu
    JButton bTakeRoleCancel = new JButton("Cancel"); // cancel button in the take role menu
    JButton bActOk = new JButton("Ok"); // ok button in the act menu
    JButton bActCancel = new JButton("Cancel"); // cancel button in the act menu
    JButton bRollDice = new JButton("Roll Dice");
    JButton bRehearseOk = new JButton("Ok"); // ok button in the rehearse menu
    JButton bRehearseCancel = new JButton("Cancel"); // cancel button in the rehearse menu
    JButton bDoRehearse = new JButton("Rehearse");    
    JButton bUpgradeOk = new JButton("Ok"); // ok button in the upgrade menu
    JButton bUpgradeCancel = new JButton("Cancel"); // cancel button in the upgrade menu    
    JButton bDoUpgrade = new JButton("Submit");

    // JLayered Pane
    public JLayeredPane bPane;
    public JPanel activePlayerPanel = new JPanel();
    public JPanel actionsPanel = new JPanel();
    public JPanel movePanel = new JPanel();
    public JPanel takeRolePanel = new JPanel();
    public JPanel actPanel = new JPanel();
    public JPanel rehearsePanel = new JPanel();
    public JPanel upgradePanel = new JPanel();     

    private ImageIcon boardIcon;

    JComboBox roomList;
    JComboBox roleList;
    JComboBox upgradeList;

    private JLabel[] cardLabels = { cardlabel0, cardlabel1, cardlabel2, cardlabel3, cardlabel4, cardlabel5, cardlabel6,
            cardlabel7, cardlabel8, cardlabel9 };
    private JLabel[] shotLabels = new JLabel[22];
    // Private Constructor (singleton pattern)
    private BoardView() {
        // Set the title of the JFrame
        super("Deadwood");

        initBoard();
        
        activePlayerPanel.setVisible(true);
        activePlayerPanel.setBounds((boardIcon.getIconWidth() + 10), 0, 175, 50);
        activePlayerPanel.setAlignmentX(0.0F);          
        bPane.add(activePlayerPanel, new Integer(2));

        activeplayerlabel = new JLabel("<html>:</html>");
        activeplayerlabel.setBounds(boardIcon.getIconWidth() + 40, 0, 170, 20);
        activeplayerlabel.setAlignmentX(0.0F);
        activePlayerPanel.add(activeplayerlabel, new Integer(2));

        actionsPanel.setVisible(true);
        actionsPanel.setBounds((boardIcon.getIconWidth() + 10), 70, 150, 300);
        actionsPanel.setAlignmentX(0.0F);  
        bPane.add(actionsPanel, new Integer(2));

        movePanel.setVisible(false);
        movePanel.setBounds((boardIcon.getIconWidth() + 10), 70, 150, 300);
        movePanel.setAlignmentX(0.0F);  
        bPane.add(movePanel, new Integer(2));

        takeRolePanel.setVisible(false);
        takeRolePanel.setBounds((boardIcon.getIconWidth() + 5), 70, 200, 300);
        takeRolePanel.setAlignmentX(0.0F);  
        bPane.add(takeRolePanel, new Integer(2));

        actPanel.setVisible(false);
        actPanel.setBounds((boardIcon.getIconWidth() + 10), 70, 170, 150);
        actPanel.setAlignmentX(0.0F);   
        bPane.add(actPanel, new Integer(2));        

        rehearsePanel.setVisible(false);
        rehearsePanel.setBounds((boardIcon.getIconWidth() + 10), 70, 150, 300);
        rehearsePanel.setAlignmentX(0.0F); 
        bPane.add(rehearsePanel, new Integer(2));

        upgradePanel.setVisible(false);
        upgradePanel.setBounds((boardIcon.getIconWidth() + 10), 70, 180, 300);
        upgradePanel.setAlignmentX(0.0F); 
        bPane.add(upgradePanel, new Integer(2));
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
        setSize(boardIcon.getIconWidth() + 230, boardIcon.getIconHeight()+40);

        // Setup the main menu
        initActionsMenu();
        // Setup the Move menu
        initMoveMenu();
        // Setup the Take Role menu
        initTakeRoleMenu();
        // Setup the Act menu
        initActMenu();
        // Setup the Rehearse menu
        initRehearseMenu();
        // Setup the Upgrade menu
        initUpgradeMenu();
    }

    public void initGameState() {
        // fetch the game state
        BoardLayersListener.getInstance().stateChanged();
    }

    public static BoardView getInstance() {
        return instance;
    }
    public void addShot(int x, int y, int z){
      shotLabel = new JLabel();
      ImageIcon sIcon = new ImageIcon("images/shot.png");
      shotLabel.setIcon(sIcon);
      shotLabel.setBounds(x, y, sIcon.getIconWidth(), sIcon.getIconHeight());
      shotLabel.setOpaque(false);
      bPane.add(shotLabel, new Integer(1));
      shotLabels[z] = shotLabel;
    }
    public void completedShot(int z){
      bPane.add(shotLabels[z], new Integer(-1));

    }
    public void resetShots(){
      for(int i = 0; i < shotLabels.length; i++){
         bPane.add(shotLabels[i], new Integer(2));
      }
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

    public void flipCard(String image, int[] point, int roomNum) {
        Container parent = cardLabels[roomNum].getParent();
        parent.remove(cardLabels[roomNum]);
        parent.validate();
        parent.repaint();
        templabel = new JLabel();
        ImageIcon cIcon = new ImageIcon("images/cards/" + image);
        templabel.setIcon(cIcon);
        templabel.setBounds(point[0], point[1], cIcon.getIconWidth() + 2, cIcon.getIconHeight());
        templabel.setOpaque(true);
        cardLabels[roomNum] = templabel;
        bPane.add(cardLabels[roomNum], new Integer(2));
    }

    public void removeCard(String image, int[] point, int roomNum) {
        Container parent = cardLabels[roomNum].getParent();
        parent.remove(cardLabels[roomNum]);
        parent.validate();
        parent.repaint();
    }
      
    public void initPlayerDice(Player[] playerArray) {
        // Add a dice to represent a player.                
        player1label = new JLabel();
        player1Decorator = new JLabel();
        player1label.setLayout(new OverlayLayout(player1label));
        int p1rank = playerArray[0].getRank();
        ImageIcon pIcon = new ImageIcon("images/dice/"+getPlayerColor(1)+p1rank+".png");        
        player1label.setIcon(pIcon);        
        player1label.setBounds(boardIcon.getIconWidth() - 220, (200 + (1 * 50)), pIcon.getIconWidth(),
                pIcon.getIconHeight());               
        player1label.add(player1Decorator);
        player1Decorator.setVisible(false);
        player1Decorator.setAlignmentX(-2);
        player1Decorator.setAlignmentY(-2);
        bPane.add(player1label, new Integer(2));          

        player2label = new JLabel();
        player2Decorator = new JLabel();
        player2label.setLayout(new OverlayLayout(player2label));
        int p2rank = playerArray[1].getRank();
        pIcon = new ImageIcon("images/dice/"+getPlayerColor(2)+p2rank+".png");        
        player2label.setIcon(pIcon);
        player2label.setBounds(boardIcon.getIconWidth() - 220, (200 + (2 * 50)), pIcon.getIconWidth(),
                pIcon.getIconHeight());
        player2label.add(player2Decorator);
        player2Decorator.setVisible(false);
        player2Decorator.setAlignmentX(-2);
        player2Decorator.setAlignmentY(-2);
        bPane.add(player2label, new Integer(2));

        player3label = new JLabel();
        player3Decorator = new JLabel();
        player3label.setLayout(new OverlayLayout(player3label));
        if (playerArray[2] != null){
           int p3rank = playerArray[2].getRank();
           pIcon = new ImageIcon("images/dice/"+getPlayerColor(3)+p3rank+".png");
           player3label.setIcon(pIcon);
           player3label.setBounds(boardIcon.getIconWidth() - 220, (200 + (3 * 50)), pIcon.getIconWidth(),
                   pIcon.getIconHeight());
        } else {
            player3label.setVisible(false);
        }
        player3label.add(player3Decorator);
        player3Decorator.setVisible(false);
        player3Decorator.setAlignmentX(-2);
        player3Decorator.setAlignmentY(-2);
        bPane.add(player3label, new Integer(2));

        player4label = new JLabel();
        player4Decorator = new JLabel();
        player4label.setLayout(new OverlayLayout(player4label));
        if (playerArray[3] != null){
           int p4rank = playerArray[3].getRank();
           pIcon = new ImageIcon("images/dice/"+getPlayerColor(4)+p4rank+".png");
           player4label.setIcon(pIcon);
           player4label.setBounds(boardIcon.getIconWidth() - 220, (200 + (4 * 50)), pIcon.getIconWidth(),
                   pIcon.getIconHeight());
        } else {
            player4label.setVisible(false);
        }
        player4label.add(player4Decorator);
        player4Decorator.setVisible(false);
        player4Decorator.setAlignmentX(-2);
        player4Decorator.setAlignmentY(-2);
        bPane.add(player4label, new Integer(2));

        player5label = new JLabel();
        player5Decorator = new JLabel();
        player5label.setLayout(new OverlayLayout(player5label));
        if (playerArray[4] != null){
           int p5rank = playerArray[4].getRank();
           pIcon = new ImageIcon("images/dice/"+getPlayerColor(5)+p5rank+".png");
           player5label.setIcon(pIcon);
           player5label.setBounds(boardIcon.getIconWidth() - 50, (200 + (1 * 50)), pIcon.getIconWidth(),
                   pIcon.getIconHeight());
        } else {
            player5label.setVisible(false);
        }
        player5label.add(player5Decorator);
        player5Decorator.setVisible(false);
        player5Decorator.setAlignmentX(-2);
        player5Decorator.setAlignmentY(-2);
        bPane.add(player5label, new Integer(2));

        player6label = new JLabel();
        player6Decorator = new JLabel();
        player6label.setLayout(new OverlayLayout(player6label));
        if (playerArray[5] != null){
        int p6rank = playerArray[5].getRank();
           pIcon = new ImageIcon("images/dice/"+getPlayerColor(6)+p6rank+".png");
           player6label.setIcon(pIcon);
           player6label.setBounds(boardIcon.getIconWidth() - 50, (200 + (2 * 50)), pIcon.getIconWidth(),
                   pIcon.getIconHeight());
        } else {
            player6label.setVisible(false);
        }
        player6label.add(player6Decorator);
        player6Decorator.setVisible(false);
        player6Decorator.setAlignmentX(-2);
        player6Decorator.setAlignmentY(-2);
        bPane.add(player6label, new Integer(2));

        player7label = new JLabel();
        player7Decorator = new JLabel();
        player7label.setLayout(new OverlayLayout(player7label));
        if (playerArray[6] != null){
           int p7rank = playerArray[6].getRank();
           pIcon = new ImageIcon("images/dice/"+getPlayerColor(7)+p7rank+".png");
           player7label.setIcon(pIcon);
           player7label.setBounds(boardIcon.getIconWidth() - 50, (200 + (3 * 50)), pIcon.getIconWidth(),
                   pIcon.getIconHeight());
        } else {
            player7label.setVisible(false);
        }
        player7label.add(player7Decorator);
        player7Decorator.setVisible(false);
        player7Decorator.setAlignmentX(-2);
        player7Decorator.setAlignmentY(-2);
        bPane.add(player7label, new Integer(2));

        player8label = new JLabel();
        player8Decorator = new JLabel();
        player8label.setLayout(new OverlayLayout(player8label));
        if (playerArray[7] != null){
           int p8rank = playerArray[7].getRank();
           pIcon = new ImageIcon("images/dice/"+getPlayerColor(8)+p8rank+".png");
           player8label.setIcon(pIcon);
           player8label.setBounds(boardIcon.getIconWidth() - 50, (200 + (4 * 50)), pIcon.getIconWidth(),
                   pIcon.getIconHeight());
        } else {
            player8label.setVisible(false);
        }
        player8label.add(player8Decorator);
        player8Decorator.setVisible(false);
        player8Decorator.setAlignmentX(-2);
        player8Decorator.setAlignmentY(-2);
        bPane.add(player8label, new Integer(2));
    }

    public String getPlayerColor(int playerNum){
        String c = "";
        switch(playerNum){
            case 1:
                c="r";
                break;
            case 2:
                c="o";
                break;
            case 3:
                c="y";
                break;
            case 4:
                c="g";
                break;
            case 5:
                c="b";
                break;
            case 6:
                c="v";
                break;
            case 7:
                c="p";
                break;
            case 8:
                c="w";
                break;
        }
        return c;
    }


    // set the rehearsal icon to show
    public void updateActivePlayerRehearsalChips(Player activeP) {
        int p = activeP.getID() + 1;        
        int chips = activeP.getRehearseCounter();
        ImageIcon pDecorator = new ImageIcon("images/rehearse/rc1.png");
        if(chips > 0 && chips < 6) {
            pDecorator = new ImageIcon("images/rehearse/rc"+chips+".png");
        }
        switch(p){
        case 1:        
            if(chips==0) {player1Decorator.setVisible(false);} else{player1Decorator.setVisible(true);player1Decorator.setIcon(pDecorator);}           
            break;
        case 2:
            if(chips==0) {player2Decorator.setVisible(false);} else{player2Decorator.setVisible(true);player2Decorator.setIcon(pDecorator);}
            break;
        case 3:
            if(chips==0) {player3Decorator.setVisible(false);} else{player3Decorator.setVisible(true);player3Decorator.setIcon(pDecorator);}
            break;
        case 4:
            if(chips==0) {player4Decorator.setVisible(false);} else{player4Decorator.setVisible(true);player4Decorator.setIcon(pDecorator);}
            break;            
        case 5:
            if(chips==0) {player5Decorator.setVisible(false);} else{player5Decorator.setVisible(true);player5Decorator.setIcon(pDecorator);}
            break;     
        case 6:
            if(chips==0) {player6Decorator.setVisible(false);} else{player6Decorator.setVisible(true);player6Decorator.setIcon(pDecorator);}
            break;     
        case 7:
            if(chips==0) {player7Decorator.setVisible(false);} else{player7Decorator.setVisible(true);player7Decorator.setIcon(pDecorator);}
            break;     
        case 8:
            if(chips==0) {player8Decorator.setVisible(false);} else{player8Decorator.setVisible(true);player8Decorator.setIcon(pDecorator);}
            break;     
        }
    }

    // sets the Z index of the current player to display on top of (overlap) the others in the same room
    public void updateActivePlayerDice(Player activeP) {
        int p = activeP.getID() + 1;        
        if(p==1) {bPane.setComponentZOrder(player1label, 2);} else{bPane.setComponentZOrder(player1label, 3);}
        if(p==2) {bPane.setComponentZOrder(player2label,2);} else{bPane.setComponentZOrder(player2label, 3);}
        if(p==3) {bPane.setComponentZOrder(player3label, 2);} else{bPane.setComponentZOrder(player3label, 3);}
        if(p==4) {bPane.setComponentZOrder(player4label, 2);} else{bPane.setComponentZOrder(player4label, 3);}
        if(p==5) {bPane.setComponentZOrder(player5label, 2);} else{bPane.setComponentZOrder(player5label, 3);}
        if(p==6) {bPane.setComponentZOrder(player6label, 2);} else{bPane.setComponentZOrder(player6label, 3);}
        if(p==7) {bPane.setComponentZOrder(player7label, 2);} else{bPane.setComponentZOrder(player7label, 3);}
        if(p==8) {bPane.setComponentZOrder(player8label, 2);} else{bPane.setComponentZOrder(player8label, 3);}               
    }

    // update the dice image to show the player's current rank in the game
    public void updatePlayerDice(int rank, int player) {
      switch(player){
         case 0: ImageIcon pIcon = new ImageIcon("images/dice/r" + rank + ".png");
                  player1label.setIcon(pIcon);
                  break;
         case 1:  pIcon = new ImageIcon("images/dice/o" + rank + ".png");
                  player2label.setIcon(pIcon);
                  break;
         case 2:  pIcon = new ImageIcon("images/dice/y" + rank + ".png");
                  player3label.setIcon(pIcon);
                  break;
         case 3:  pIcon = new ImageIcon("images/dice/g" + rank + ".png");
                  player4label.setIcon(pIcon);
                  break;
         case 4:  pIcon = new ImageIcon("images/dice/b" + rank + ".png");
                  player5label.setIcon(pIcon);
                  break;
         case 5:  pIcon = new ImageIcon("images/dice/v" + rank + ".png");
                  player6label.setIcon(pIcon);
                  break;
         case 6:  pIcon = new ImageIcon("images/dice/p" + rank + ".png");
                  player7label.setIcon(pIcon);
                  break;
         case 7:  pIcon = new ImageIcon("images/dice/w" + rank + ".png");
                  player8label.setIcon(pIcon);
                  break;
      }
    }
    public void initActionsMenu() {
        // Create the Menu for action buttons
        mLabel = new JLabel("           MENU            ");
        mLabel.setBounds(boardIcon.getIconWidth() + 40, 50, 50, 20);
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

    public void initMoveMenu() {
        Point p = movePanel.getLocation();

        JLabel moveMenuLabel = new JLabel("Choose a room:", SwingConstants.CENTER);
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

    public void initTakeRoleMenu() {
        Point p = takeRolePanel.getLocation();

        JLabel roleMenuLabel = new JLabel("Choose a role:", SwingConstants.CENTER);
        roleMenuLabel.setBounds((int) p.getX(), (int) (p.getY() + 10), 150, 40);
        takeRolePanel.add(roleMenuLabel, new Integer(2));

        // display error messages from taking a role
        JLabel resultMsgLabel = new JLabel();
        resultMsgLabel.setBounds((int) p.getX(), (int) (p.getY()), 150, 40);
        takeRolePanel.add(resultMsgLabel, new Integer(2));

        roleList = new JComboBox();
        roleList.setBounds((int) p.getX(), (int) (p.getY() + 20), 150, 40);
        takeRolePanel.add(roleList, new Integer(2));

        bTakeRoleOk.setBackground(Color.green);
        bTakeRoleOk.setBounds(boardIcon.getIconWidth() + 10, 190, 40, 20);
        bTakeRoleOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // submit the move to controller
                String resultMsg = BoardLayersListener.getInstance().submitPlayerTakeRole(roleList.getSelectedIndex());
                if (resultMsg.equals("Success!")) {
                    // reset the error message box
                    resultMsgLabel.setText("");
                    // hide the take role menu
                    actionsPanel.setVisible(true);
                    // show the actions menu
                    takeRolePanel.setVisible(false);
                } else {
                    resultMsgLabel.setText(resultMsg);
                }
            }
        });

        bTakeRoleCancel.setBackground(Color.white);
        bTakeRoleCancel.setBounds(boardIcon.getIconWidth() + 30, 190, 40, 20);
        bTakeRoleCancel.addMouseListener(new BoardMouseListener());
        bTakeRoleCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // reset the error message box
                resultMsgLabel.setText("");
                // hide the takerole menu
                actionsPanel.setVisible(true);
                // show the actions menu
                takeRolePanel.setVisible(false);
            }
        });
        takeRolePanel.add(bTakeRoleOk, new Integer(2));
        takeRolePanel.add(bTakeRoleCancel, new Integer(2));
        takeRolePanel.repaint();
    }

    public void initActMenu() {
        Point p = actPanel.getLocation();

        JLabel actMenuLabel = new JLabel("<html>Roll dice to act:<br></html>");
        actMenuLabel.setBounds((int) p.getX(), (int) (p.getY()), 20, 20);
        actPanel.add(actMenuLabel, new Integer(2));

        // display result of rolling the dice
        JLabel resultMsgLabel = new JLabel();
        resultMsgLabel.setBounds((int) p.getX(), (int) (p.getY())+30, 200, 300);
        actPanel.add(resultMsgLabel, new Integer(2));

        bRollDice.setBackground(Color.white);
        bRollDice.setBounds(boardIcon.getIconWidth() + 10, (int) (p.getY())+30, 40, 20);
        bRollDice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = BoardLayersListener.getInstance().act();                
                // show result in the message box
                resultMsgLabel.setText(result);
                bActOk.setVisible(true);
                bActCancel.setVisible(false);
                bRollDice.setVisible(false);
            }
        });

        bActOk.setBackground(Color.green);
        bActOk.setVisible(false);
        bActOk.setBounds(boardIcon.getIconWidth() + 10, (int) (p.getY())+30, 40, 20);
        bActOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // reset the error message box
                resultMsgLabel.setText("");
                // hide the act menu
                actPanel.setVisible(false);
                // show the actions menu
                actionsPanel.setVisible(true);
                bActOk.setVisible(false);                
                bActCancel.setVisible(true);
                bRollDice.setVisible(true);
            }
        });

        bActCancel.setBackground(Color.white);
        bActCancel.setBounds(boardIcon.getIconWidth() + 30, (int) (p.getY())+30, 40, 20);
        bActCancel.addMouseListener(new BoardMouseListener());
        bActCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // reset the error message box
                resultMsgLabel.setText("");
                // hide the act menu
                actPanel.setVisible(false);                
                // show the actions menu
                actionsPanel.setVisible(true);                             
            }
        });
        actPanel.add(bRollDice, new Integer(2));
        actPanel.add(bActOk, new Integer(2));
        actPanel.add(bActCancel, new Integer(2));
        actPanel.repaint();
    }

    public void initRehearseMenu() {
        Point p = rehearsePanel.getLocation();

        JLabel rehearseMenuLabel = new JLabel("<html>Take a rehearsal:<br></html>");
        rehearseMenuLabel.setBounds((int) p.getX(), (int) (p.getY()), 20, 20);
        rehearsePanel.add(rehearseMenuLabel, new Integer(2));

        // display result of rolling the dice
        JLabel resultMsgLabel = new JLabel();
        resultMsgLabel.setBounds((int) p.getX(), (int) (p.getY())+30, 200, 300);
        rehearsePanel.add(resultMsgLabel, new Integer(2));

        bDoRehearse.setBackground(Color.white);
        bDoRehearse.setBounds(boardIcon.getIconWidth() + 10, (int) (p.getY())+30, 40, 20);
        bDoRehearse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = BoardLayersListener.getInstance().rehearse();                
                // show result in the message box
                resultMsgLabel.setText(result);
                bRehearseOk.setVisible(true);
                bRehearseCancel.setVisible(false);
                bDoRehearse.setVisible(false);
            }
        });

        bRehearseOk.setBackground(Color.green);
        bRehearseOk.setVisible(false);
        bRehearseOk.setBounds(boardIcon.getIconWidth() + 10, (int) (p.getY())+30, 40, 20);
        bRehearseOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // reset the error message box
                resultMsgLabel.setText("");
                // hide the act menu
                rehearsePanel.setVisible(false);
                // show the actions menu
                actionsPanel.setVisible(true);
                bRehearseOk.setVisible(false);                
                bRehearseCancel.setVisible(true);
                bDoRehearse.setVisible(true);
            }
        });

        bRehearseCancel.setBackground(Color.white);
        bRehearseCancel.setBounds(boardIcon.getIconWidth() + 30, (int) (p.getY())+30, 40, 20);
        bRehearseCancel.addMouseListener(new BoardMouseListener());
        bRehearseCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // reset the error message box
                resultMsgLabel.setText("");
                // hide the act menu
                rehearsePanel.setVisible(false);                
                // show the actions menu
                actionsPanel.setVisible(true);                             
            }
        });
        rehearsePanel.add(bDoRehearse, new Integer(2));
        rehearsePanel.add(bRehearseOk, new Integer(2));
        rehearsePanel.add(bRehearseCancel, new Integer(2));
        rehearsePanel.repaint();
    }

    public void initUpgradeMenu() {
        Point p = upgradePanel.getLocation();

        JLabel upgradeMenuLabel = new JLabel("<html>Choose an Upgrade:<br></html>");
        upgradeMenuLabel.setBounds((int) p.getX(), (int) (p.getY()), 20, 20);
        upgradePanel.add(upgradeMenuLabel, new Integer(2));

        // display result of upgrading attempt
        JLabel resultMsgLabel = new JLabel();
        resultMsgLabel.setBounds((int) p.getX(), (int) (p.getY())+30, 200, 300);
        upgradePanel.add(resultMsgLabel, new Integer(2));

        upgradeList = new JComboBox();
        upgradeList.setBounds((int) p.getX(), (int) (p.getY() + 20), 150, 40);
        upgradePanel.add(upgradeList, new Integer(2)); 
       
        bDoUpgrade.setBackground(Color.green);
        bDoUpgrade.setVisible(true);
        bDoUpgrade.setBounds(boardIcon.getIconWidth() + 10, (int) (p.getY())+30, 40, 20);
        bDoUpgrade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = BoardLayersListener.getInstance().submitUpgrade(upgradeList.getSelectedIndex());
                // set the message box
                resultMsgLabel.setText(result);
                if(result.equals("<html>Cannot upgrade <br> Insufficient Funds<br></html>")) {
                    bUpgradeOk.setVisible(false);                
                    bUpgradeCancel.setVisible(true);
                    bDoUpgrade.setVisible(true);
                } else {
                    bUpgradeOk.setVisible(true);                
                    bUpgradeCancel.setVisible(false);
                    bDoUpgrade.setVisible(false);
                }
            }
        });

        bUpgradeOk.setBackground(Color.green);
        bUpgradeOk.setVisible(false);
        bUpgradeOk.setBounds(boardIcon.getIconWidth() + 10, (int) (p.getY())+30, 40, 20);
        bUpgradeOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // reset the error message box
                resultMsgLabel.setText("");
                // hide the upgrade menu
                upgradePanel.setVisible(false);
                // show the actions menu
                actionsPanel.setVisible(true);
                bUpgradeOk.setVisible(false);                
                bUpgradeCancel.setVisible(true);
                bDoUpgrade.setVisible(true);
            }
        });

        bUpgradeCancel.setBackground(Color.white);
        bUpgradeCancel.setBounds(boardIcon.getIconWidth() + 30, (int) (p.getY())+30, 40, 20);
        bUpgradeCancel.addMouseListener(new BoardMouseListener());
        bUpgradeCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // reset the error message box
                resultMsgLabel.setText("");
                // hide the upgrade menu
                upgradePanel.setVisible(false);                
                // show the actions menu
                actionsPanel.setVisible(true);                             
            }
        });
        upgradePanel.add(bDoUpgrade, new Integer(2));
        upgradePanel.add(bUpgradeOk, new Integer(2));        
        upgradePanel.add(bUpgradeCancel, new Integer(2));
        upgradePanel.repaint();
    }

    public void showActionMenu(ArrayList<String> actions) {
        if (actions.contains(new String("act"))) {
            bAct.setEnabled(true);
        } else {
            bAct.setEnabled(false);
        }

        if (actions.contains(new String("rehearse"))) {
            bRehearse.setEnabled(true);
        } else {
            bRehearse.setEnabled(false);
        }

        if (actions.contains(new String("move"))) {
            bMove.setEnabled(true);
        } else {
            bMove.setEnabled(false);
        }

        if (actions.contains(new String("upgrade"))) {
            bUpgrade.setEnabled(true);
        } else {
            bUpgrade.setEnabled(false);
        }

        if (actions.contains(new String("takerole"))) {
            bTakeRole.setEnabled(true);
        } else {
            bTakeRole.setEnabled(false);
        }
    }

    // update the active player label
    public void updateActivePlayerLabel(Player activeP) {
        activeplayerlabel.setText("<html>" + "Player " + (activeP.getID() + 1) + ": " + activeP.getName() + "<br>" +
         "Money: $" + activeP.getMoney() + "<br>" + "Credits: " + activeP.getCredits() + "<br></html>");
         int rank = activeP.getRank();         
         String color = getPlayerColor(activeP.getID() + 1);        
         ImageIcon pIcon = new ImageIcon("images/dice/"+color+rank+".png");         
         activeplayerlabel.setIcon(pIcon);         
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

    public void showTakeRoleMenu(String[] roles) {
        // hide the actions menu
        actionsPanel.setVisible(false);
        // show the roles menu
        takeRolePanel.setVisible(true);
        roleList.removeAllItems();
        for (String role : roles) {
            roleList.addItem(role);
        }
        roleList.setSelectedIndex(0);
    }

    public void showActMenu() {
        // hide the actions menu
        actionsPanel.setVisible(false);
        // show the act menu
        actPanel.setVisible(true);
    }

    public void showRehearseMenu() {
        // hide the actions menu
        actionsPanel.setVisible(false);
        // show the rehearse menu
        rehearsePanel.setVisible(true);
    }

    public void showUpgradeMenu(String[] upgradesArray) {
        // hide the actions menu
        actionsPanel.setVisible(false);
        // show the roles menu
        upgradePanel.setVisible(true);
        upgradeList.removeAllItems();
        for (int i = 0; i < upgradesArray.length; i++) {
            upgradeList.addItem(upgradesArray[i]);
        }
        upgradeList.setSelectedIndex(0);
    }

}
