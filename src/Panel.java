/*
	Nick Miller
	Panel Class
	Last updated:  1/7/20
*/

///////////////////////////////////////////////////////////////

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Panel extends JPanel implements ActionListener, ItemListener, MouseListener, MouseMotionListener {

    private final int HUMAN = 0;
    private final int COMPUTER = 1;

    private Connect4Board theBoard;
    private TextArea    theArea, messageArea;
    private Button      createButton;
    private Choice      redPlayerChoice, greenPlayerChoice;
    private Label       redPlayerLabel,  greenPlayerLabel;
    private int         currentTurnColor = Connect4Square.RED;
    private int         redPlayer = HUMAN;
    private int         greenPlayer = HUMAN;
    private int         occupiedSpaces = 0;
    private boolean     gameOver = false;

    public Panel() {

        setLayout(null);
        setPreferredSize(new Dimension(800, 600));
        setName("Play Connect4!");
        setBackground(Color.WHITE);

        addMouseListener(this);
        addMouseMotionListener(this);

        theBoard = new Connect4Board();

        theArea = new TextArea();
        theArea.setBounds(600, 120, 190, 100);
        theArea.setText("GREEN moves first\n");
        theArea.append( "0,0,0,0,0,0,0,0,0\n");
        theArea.append( "0,0,0,0,0,0,0,0,0\n");
        theArea.append( "0,0,0,0,0,0,0,0,0\n");
        theArea.append( "0,0,1,0,0,0,0,0,0\n");
        theArea.append( "0,0,2,0,0,0,0,0,0\n");
        theArea.append( "0,1,2,0,0,0,1,0,0\n");
        theArea.append( "0,1,1,0,0,0,1,0,2\n");
        theArea.append( "0,2,2,1,0,0,1,2,2\n");
        add(theArea);

        messageArea = new TextArea();
        messageArea.setBounds(50, 480, 700, 110);
        add(messageArea);

        createButton = new Button("Initialize Game Board");
        createButton.addActionListener(this);
        createButton.setBounds(610, 90, 170, 20);
        add(createButton);

        redPlayerLabel = new Label("RED player:");
        redPlayerLabel.setBounds(600, 340, 100, 20);
        add(redPlayerLabel);

        redPlayerChoice = new Choice();
        redPlayerChoice.addItemListener(this);
        redPlayerChoice.setBounds(620, 365, 100, 25);
        redPlayerChoice.addItem("Human");
        redPlayerChoice.addItem("Computer");
        add(redPlayerChoice);

        greenPlayerLabel = new Label("GREEN player:");
        greenPlayerLabel.setBounds(600, 410, 100, 20);
        add(greenPlayerLabel);

        greenPlayerChoice = new Choice();
        greenPlayerChoice.addItemListener(this);
        greenPlayerChoice.setBounds(620, 435, 100, 25);
        greenPlayerChoice.addItem("Human");
        greenPlayerChoice.addItem("Computer");
        add(greenPlayerChoice);
    } // end of Panel constructor

    public void paintComponent( Graphics g ) {
        super.paintComponent( g );

        g.setFont(new Font("Helvetica", Font.BOLD, 18));
        if (!gameOver) {
            if (occupiedSpaces < 72) {
                if (currentTurnColor == Connect4Square.RED && redPlayer == COMPUTER) {
                    makeAMove(Connect4Square.RED);
                }

                else if (currentTurnColor == Connect4Square.GREEN && greenPlayer == COMPUTER) {
                    makeAMove(Connect4Square.GREEN);
                }

                int winner = theBoard.checkForAWinner();
                if (winner == Connect4Square.RED) {
                    messageArea.setText("RED player has won the game");
                    gameOver = true;
                }
                else if (winner == Connect4Square.GREEN) {
                    messageArea.setText("GREEN player has won the game");
                    gameOver = true;
                }
                else if (occupiedSpaces >= 72) {
                    messageArea.setText("Game ends in a draw");
                    gameOver = true;
                }

                if (currentTurnColor == Connect4Square.RED) {
                    g.setColor(Color.RED);
                    g.drawString("  RED to move", 625, 280);
                }
                else {
                    g.setColor(Color.GREEN);
                    g.drawString("GREEN to move", 625, 280);
                }

            }
        }

        theBoard.drawBoard(g);

        g.setColor(Color.MAGENTA.darker());
        g.drawString("Connect4!", 15, 75);
    } // end of paintComponent()

    public void makeAMove(int colorOfMove) {
        if (!gameOver) {
            // randomly choose a column
            int c;
            do {
                c = (int) (Math.random()*9);
            } while (theBoard.getValue(0,c) != Connect4Square.EMPTY);
            theBoard.setSelectedColumn(c);
            int r = theBoard.getRowOfLowestEmptySquareInSelectedColumn();
            if (r >= 0) {
                theBoard.setSelectedRow(r);
                if (currentTurnColor == Connect4Square.RED) {
                    theBoard.setSelected(Connect4Square.RED);
                    currentTurnColor = Connect4Square.GREEN;
                    messageArea.setText("Placed a RED piece in\n   row "
                            + r + ", column " + c);
                }
                else {
                    theBoard.setSelected(Connect4Square.GREEN);
                    currentTurnColor = Connect4Square.RED;
                    messageArea.setText("Placed a GREEN piece in\n   row "
                            + r + ", column " + c);
                }
            }
            occupiedSpaces++;
            repaint();
        }
    } // end of makeAMove()

    public void actionPerformed( ActionEvent event ) {
        Object source = event.getSource();

        if (source.equals(createButton)) {
            gameOver = false;
            currentTurnColor = theBoard.initializeBoard(theArea.getText());
            if (currentTurnColor == Connect4Square.RED) {
                redPlayer = HUMAN;
                redPlayerChoice.select(0);
            }
            else if (currentTurnColor == Connect4Square.GREEN) {
                greenPlayer = HUMAN;
                greenPlayerChoice.select(0);
            }
            messageArea.setText("Game Board reset");
            occupiedSpaces = 0;
            for (int r = 0; r < 8; r++)
                for (int c = 0; c < 8; c++)
                    if (theBoard.getValue(r, c) != Connect4Square.EMPTY)
                        occupiedSpaces++;
        } // end of create Button processing

        repaint();
    } // end of actionPerformed()

    public void itemStateChanged(ItemEvent event) {
        Object source = event.getSource();
        if (source.equals(redPlayerChoice)) {
            int usersSelection = redPlayerChoice.getSelectedIndex();
            if (usersSelection != redPlayer) {
                redPlayer = usersSelection;
                theBoard = new Connect4Board();
                currentTurnColor = Connect4Square.RED;
            }
        } // end redPlayerChoice

        if (source.equals(greenPlayerChoice)) {
            int usersSelection = greenPlayerChoice.getSelectedIndex();
            if (usersSelection != greenPlayer) {
                greenPlayer = usersSelection;
                theBoard = new Connect4Board();
                currentTurnColor = Connect4Square.RED;
            }
        } // end greenPlayerChoice

        occupiedSpaces = 0;
        gameOver = false;
        repaint();
    } // end of itemStateChanged()

    public  void mousePressed(MouseEvent e) {
        if (!gameOver && 130 < e.getX() && e.getX() < 580 && 30 < e.getY() && e.getY() < 480) {
            int c = (e.getX() - 130) / 50;

            theBoard.setSelectedColumn(c);
            int r = theBoard.getRowOfLowestEmptySquareInSelectedColumn();
            if (r >= 0) {
                theBoard.setSelectedRow(r);
                if (currentTurnColor == Connect4Square.RED) {
                    theBoard.setSelected(Connect4Square.RED);
                    currentTurnColor = Connect4Square.GREEN;
                }
                else {
                    theBoard.setSelected(Connect4Square.GREEN);
                    currentTurnColor = Connect4Square.RED;
                }
                occupiedSpaces++;
                repaint();
            }
        }
    } // end of mousePressed()

    public  void mouseClicked(MouseEvent e){ }

    public  void mouseReleased(MouseEvent e){ }

    public  void mouseEntered(MouseEvent e){ }

    public  void mouseExited(MouseEvent e){ }

    public void mouseMoved(MouseEvent e) {
        int r = -1;
        int c = -1;
        if (130 < e.getX() && e.getX() < 580 && 30 < e.getY() && e.getY() < 430) {
            r = (e.getY() - 30) / 50;
            c = (e.getX() - 130) / 50;
        }

        theBoard.setSelectedRow(r);
        theBoard.setSelectedColumn(c);
        repaint();
    } // end of mouseMoved

    public  void mouseDragged(MouseEvent e){ }


    public void frame() {

        for (Component c: getComponents())
            c.setSize(c.getPreferredSize());
        JFrame f = new JFrame(getName());
        f.setContentPane(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);

    } // end of frame()

    public static void main(String args[]){new Panel().frame();}

} // end of class Panel()