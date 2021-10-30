/*
	Nick Miller
	Connect4Board Class
	Last updated:  1/7/20
*/

///////////////////////////////////////////////////////////////

import java.awt.*;


public class Connect4Board {

    private Connect4Square[][] board;

    private int selectedRow;
    private int selectedColumn;

    public Connect4Board() {
        board = new Connect4Square[8][9];

        for(int r = 0; r < board.length; r++) {
            for(int c = 0; c < board[r].length; c++){
                board[r][c] = new Connect4Square(Connect4Square.EMPTY, r, c);
            }
        }
    } // end of Constructor

    public void setValue(int r, int c, int value) {
        if (0 <= r && r <= 7 && 0 <= c && c <= 8 && 0 <= value && value <= 2) {
            board[r][c].setValue(value);
        }
    } // end of setValue()

    public void setSelected( int value ) {
        this.setValue(this.getSelectedRow(), this.getSelectedColumn(), value);
    } // end of setSelected()

    public void setSelectedRow(int r) {
        if (0 <= r && r <= 7){
            selectedRow = r;
        }
    } // end of setSelectedRow()

    public void setSelectedColumn(int c) {
        if (0 <= c && c <= 8){
            selectedColumn = c;
        }
    } // end of setSelectedColumn()

    public int getSelectedRow() {
        return this.selectedRow;
    }

    public int getSelectedColumn() {
        return this.selectedColumn;
    }

    public int getValue(int r, int c) {
        if (0<= r && r <= 7 && 0 <= c && c <= 8){
            return board[r][c].getValue();
        }
        return -1;
    } // end of getValue

    public int getRowOfLowestEmptySquareInSelectedColumn() {
        int r = 7;

        while (r >= 0 && getValue(r, selectedColumn) != Connect4Square.EMPTY){
            r--;
        }
        return r;
    }

    public Connect4Square getPlay(int r, int c) {
        return board[r][c];
    }

    public int checkForAWinner() {

        for(int i = 0; i < 8; i++) {
            if(checkRowForRedWin(i) != Connect4Square.EMPTY) {
                return checkRowForRedWin(i);
            }
            if(checkRowForGreenWin(i) != Connect4Square.EMPTY) {
                return checkRowForGreenWin(i);
            }
        }

        for(int i = 0; i < 9; i++) {
            if(checkColumnForRedWin(i) != Connect4Square.EMPTY) {
                return checkColumnForRedWin(i);
            }
            if(checkColumnForGreenWin(i) != Connect4Square.EMPTY) {
                return checkColumnForGreenWin(i);
            }
        }

        for(int r = 0; r < 8; r++) {
            for( int c = 0; c < 9; c++) {
                if (checkDownToRightDiagonalForRedWin(r, c) != Connect4Square.EMPTY) {
                    return checkDownToRightDiagonalForRedWin(r, c);
                }
                if (checkDownToRightDiagonalForGreenWin(r, c) != Connect4Square.EMPTY) {
                    return checkDownToRightDiagonalForGreenWin(r, c);
                }
                if (checkDownToLeftDiagonalForRedWin(r, c) != Connect4Square.EMPTY) {
                    return checkDownToLeftDiagonalForRedWin(r, c);
                }
                if (checkDownToLeftDiagonalForGreenWin(r, c) != Connect4Square.EMPTY) {
                    return checkDownToLeftDiagonalForGreenWin(r, c);
                }
            }
        }
        return Connect4Square.EMPTY;
    }

    public int checkRowForRedWin(int rowNum) {

        int consecutiveRedPieces = 0;

        int columnNum = 0;
        while (columnNum < 9) {
            if (board[rowNum][columnNum].getValue() == Connect4Square.RED) {
                consecutiveRedPieces++;   // just saw another RED piece
                if (consecutiveRedPieces >= 4) {
                    return Connect4Square.RED;  // this indicates a RED win
                }
            }
            else {
                consecutiveRedPieces = 0; // just saw a non-RED piece, reset count
            }
            columnNum++;
        } // end while

        return Connect4Square.EMPTY;
    } // end of checkRowForRedWin()

    public int checkRowForGreenWin(int rowNum) {

        int consecutiveGreenPieces = 0;
        int columnNum = 0;

        while (columnNum < 9) {
            if (board[rowNum][columnNum].getValue() == Connect4Square.GREEN) {
                consecutiveGreenPieces++;   // just saw another GREEN piece
                if (consecutiveGreenPieces >= 4) {
                    return Connect4Square.GREEN;  // this indicates a GREEN win
                }
            }
            else {
                consecutiveGreenPieces = 0; // just saw a non-GREEN piece, reset count
            }
            columnNum++;
        } // end while

        return Connect4Square.EMPTY;
    } // end of checkRowForGreenWin()

    public int checkColumnForRedWin(int columnNum) {

        int consecutiveRedPieces = 0;

        int rowNum = 0;
        while (rowNum < 8) {
            if (board[rowNum][columnNum].getValue() == Connect4Square.RED) {
                consecutiveRedPieces++;   // just saw another RED piece
                if (consecutiveRedPieces >= 4) {
                    return Connect4Square.RED;  // this indicates a RED win
                }
            }
            else {
                consecutiveRedPieces = 0; // just saw a non-RED piece, reset count
            }
            rowNum++;
        } // end while

        return Connect4Square.EMPTY;
    } // end of checkColumnForRedWin()

    public int checkColumnForGreenWin(int columnNum) {

        int consecutiveGreenPieces = 0;

        int rowNum = 0;
        while (rowNum < 8) {
            if (board[rowNum][columnNum].getValue() == Connect4Square.GREEN) {
                consecutiveGreenPieces++;   // just saw another GREEN piece
                if (consecutiveGreenPieces >= 4) {
                    return Connect4Square.GREEN;  // this indicates a GREEN win
                }
            }
            else {
                consecutiveGreenPieces = 0; // just saw a non-GREEN piece, reset count
            }
            rowNum++;
        } // end while

        return Connect4Square.EMPTY;
    } // end of checkColumnForGreenWin()

    public int checkDownToRightDiagonalForRedWin(int rowNum, int columnNum) {

        int consecutiveRedPieces = 0;

        while (rowNum < 8 && columnNum < 9) {
            if (board[rowNum][columnNum].getValue() == Connect4Square.RED) {
                consecutiveRedPieces++;   // just saw another RED piece
                if (consecutiveRedPieces >= 4) {
                    return Connect4Square.RED;  // this indicates a RED win
                }
            }
            else {
                consecutiveRedPieces = 0; // just saw a non-RED piece, reset count
            }
            columnNum++;
            rowNum++;
        } // end while

        return Connect4Square.EMPTY;
    } // end of checkDownToRightDiagonalForRedWin()

    public int checkDownToRightDiagonalForGreenWin(int rowNum, int columnNum) {

        int consecutiveGreenPieces = 0;

        while (rowNum < 8 && columnNum < 9) {
            if (board[rowNum][columnNum].getValue() == Connect4Square.GREEN) {
                consecutiveGreenPieces++;   // just saw another GREEN piece
                if (consecutiveGreenPieces >= 4) {
                    return Connect4Square.GREEN;  // this indicates a GREEN win
                }
            }
            else {
                consecutiveGreenPieces = 0; // just saw a non-GREEN piece, reset count
            }
            columnNum++;
            rowNum++;
        } // end while

        return Connect4Square.EMPTY;
    } // end of checkDownToRightDiagonalForGreenWin()

    public int checkDownToLeftDiagonalForRedWin(int rowNum, int columnNum) {

        int consecutiveRedPieces = 0;

        while (rowNum < 8 && columnNum >= 0) {
            if (board[rowNum][columnNum].getValue() == Connect4Square.RED) {
                consecutiveRedPieces++;   // just saw another RED piece
                if (consecutiveRedPieces >= 4) {
                    return Connect4Square.RED;  // this indicates a RED win
                }
            }
            else {
                consecutiveRedPieces = 0; // just saw a non-RED piece, reset count
            }
            columnNum--;
            rowNum++;
        } // end while

        return Connect4Square.EMPTY;
    } // end of checkDownToLeftDiagonalForRedWin()

    public int checkDownToLeftDiagonalForGreenWin(int rowNum, int columnNum) {

        int consecutiveGreenPieces = 0;

        while (rowNum < 8 && columnNum >= 0) {
            if (board[rowNum][columnNum].getValue() == Connect4Square.GREEN) {
                consecutiveGreenPieces++;   // just saw another GREEN piece
                if (consecutiveGreenPieces >= 4) {
                    return Connect4Square.GREEN;  // this indicates a GREEN win
                }
            }
            else {
                consecutiveGreenPieces = 0; // just saw a non-GREEN piece, reset count
            }
            columnNum--;
            rowNum++;
        } // end while

        return Connect4Square.EMPTY;
    } // end of checkDownToLeftDiagonalForGreenWin()

    public int initializeBoard(String input) {

        int returnValue = 0;

        String[] inputLine = input.split("\n");
        String firstMoveColor = inputLine[0].substring(0, inputLine[0].indexOf(" "));

        if (firstMoveColor.equals("GREEN"))
            returnValue = Connect4Square.GREEN;
        else if (firstMoveColor.equals("RED"))
            returnValue = Connect4Square.RED;

        for (int i = 1; i < inputLine.length; i++) {
            int r = i-1;
            String[] pieceColor = inputLine[i].split(",");
            for (int c = 0; c < pieceColor.length; c++) {
                if (pieceColor[c].trim().equals("0"))
                    board[r][c].setValue(Connect4Square.EMPTY);
                else if (pieceColor[c].trim().equals("1"))
                    board[r][c].setValue(Connect4Square.RED);
                else if (pieceColor[c].trim().equals("2"))
                    board[r][c].setValue(Connect4Square.GREEN);
            }
        }
        selectedRow = selectedColumn = -1;
        return returnValue;
    } // end of initializeBoard()

    public void drawBoard(Graphics g) {

        g.setColor(Color.lightGray);

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 9; c++) {
                g.drawRect(c*50 + 130, r*50 + 30, 50, 50);
            }
        }

        g.setColor(Color.black);
        g.drawRect(130, 30, 450, 400);
        g.drawRect(129, 29, 452, 402);

        g.setFont(new Font("Helvetica", Font.BOLD, 18));
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 9; c++) {
                switch (board[r][c].getValue()) {
                    case Connect4Square.EMPTY: g.setColor(Color.WHITE); break;
                    case Connect4Square.RED:   g.setColor(Color.RED);   break;
                    case Connect4Square.GREEN: g.setColor(Color.GREEN); break;
                } // end if
                g.fillOval(c*50 + 131, r*50 + 31, 48, 48);
                if (selectedRow == r && selectedColumn == c) {
                    g.setColor(Color.red.darker());
                    g.drawRect(c*50 + 130, r*50 + 30, 50, 50);
                    g.drawRect(c*50 + 131, r*50 + 31, 48, 48);
                }
            }
        }
    } // end of drawBoard()

} // end of class Connect4Board