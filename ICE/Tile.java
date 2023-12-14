package ICE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tile extends JButton implements ActionListener {

    Tile parent;
    Pathfinding pf;
    ButtonHandler buttonHandler;
    int col;
    int row;
    int gCost;
    int hCost;
    int fCost;
    boolean start;
    boolean goal;
    boolean solid;
    boolean open;
    boolean checked;

    public Tile(int col, int row) {
        this.col = col;
        this.row = row;

        setBackground(Color.white);
        setForeground(Color.black);
        addActionListener(this);
        this.setFocusable(false);
    }

    public Tile(ButtonHandler buttonHandler) {
        this.buttonHandler = buttonHandler;
    }

    public void setAsStart() {
        setBackground(Color.BLUE);
        setForeground(Color.white);
        setText("Start");
        start = true;
    }


    public void setAsGoal() {
        setBackground(Color.yellow);
        setForeground(Color.black);
        setText("Goal");
        goal = true;
    }


    public void resetTile() {
        setBackground(Color.white);
        setForeground(Color.black);
        setText("");
        start = false;
        goal = false;
        solid = false;
        checked = false;
        open = false;
        fCost = 0;
        gCost = 0;
        hCost = 0;
    }

    public void setAsSolid() {
        setBackground(Color.black);
        setForeground(Color.black);
        solid = true;
    }

    public void setAsOpen() {
        open = true;
    }

    public void setAsChecked() {
        if (start == false && goal == false) {
            setBackground(Color.orange);
            setForeground(Color.black);
        }
        checked = true;
    }

    public void setAsPath() {
        setBackground(Color.GREEN);
        setForeground(Color.black);
    }

    public boolean isSolid() {
        return solid;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ButtonHandler.activeButton == ButtonHandler.placeStartTileButton) {
            ButtonHandler.pf.checkStart();
            ButtonHandler.pf.setStartTile(col, row);
        } else if (ButtonHandler.activeButton == ButtonHandler.placeGoalTileButton) {
            ButtonHandler.pf.checkGoal();
            ButtonHandler.pf.setGoalTile(col, row);

        } else if (ButtonHandler.activeButton == ButtonHandler.placeSolidTileButton) {
            ButtonHandler.pf.placeOrRemoveSolid(col, row);

        } else if (ButtonHandler.activeButton == ButtonHandler.runButton) {

        }
    }
}
