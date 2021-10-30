/*
	Nick Miller
	Connect4Square Class
	Last updated:  1/7/20
*/

///////////////////////////////////////////////////////////////

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class Connect4Square {

    public static final int EMPTY = 0;
    public static final int RED = 1;
    public static final int GREEN = 2;


    private int value;
    private int row;
    private int column;
    private int left, right, top, bottom; // pixels

    public Connect4Square() {
        value = EMPTY;
        row = column = left = right = top = bottom = 0;
    } // end of generic Constructor

    public Connect4Square(int v, int r, int c) {
        value = v;
        row = r;
        column = c;
        left = r*50 + 130;
        right = (r+1)*50 + 130;
        top = c*50 + 30;
        bottom = (c+1)* 50 + 30;
    } // end of specific Constructor

    public int getValue() { return value; }

    public void setValue(int v) { value = v; }

} // end of class Connect4Square