package ICE;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Pathfinding extends JPanel {

    //Screen settings
    final int maxCol = 15;
    final int maxRow = 10;
    final int tileSize = 70;
    final int screenWidth = tileSize * maxCol;
    final int screenHeight = tileSize * maxRow;

    // tile
    Tile[][] tile = new Tile[maxCol][maxRow];
    Tile startTile, goalTile, currentTile;
    ArrayList<Tile>openList = new ArrayList<>();
    ArrayList<Tile>checkedList = new ArrayList<>();

    Boolean goalReached = false;
    int step = 0;

    public Pathfinding() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setLayout(new GridLayout(maxRow,maxCol));
        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);

        // place tile
        int col = 0;
        int row = 0;

        while (col < maxCol && row < maxRow){
            tile[col][row] = new Tile(col,row);
            this.add(tile[col][row]);

            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }
        // set start and goal tile
        setStartTile(6,6);
        setGoalTile(11,3);

        //
        setSolidTile(10,0);
        setSolidTile(10,1);
        setSolidTile(10,2);
        setSolidTile(10,3);
        setSolidTile(10,4);
        setSolidTile(10,5);
        setSolidTile(10,6);
        setSolidTile(11,6);
        setSolidTile(12,6);

        setCostOnTiles();
    }
    private void setStartTile(int col, int row){
        tile[col][row].setAsStart();
        startTile = tile[col][row];
        currentTile = startTile;
    }
    private void setGoalTile(int col, int row){
        tile[col][row].setAsGoal();
        goalTile = tile[col][row];
    }
    private void setSolidTile(int col, int row) {
        tile[col][row].setAsSolid();
    }

    private void setCostOnTiles(){

        int col = 0;
        int row = 0;

        while(col < maxCol && row < maxRow){
            getcost(tile[col][row]);
            col++;
            if(col == maxCol){
                col = 0;
                row++;
            }
        }
    }
    private void getcost(Tile tile) {
        // get g Cost (the distance from the start tile)
        int xDistance = Math.abs(tile.col - startTile.col);
        int yDistance = Math.abs(tile.row - startTile.row);
        tile.gCost = xDistance + yDistance;

        // get H cost (the distance from the goal tile)
        xDistance = Math.abs(tile.col - goalTile.col);
        yDistance = Math.abs(tile.row - goalTile.row);
        tile.hCost = xDistance + yDistance;

        // get F cost (total cost)
        tile.fCost = tile.gCost + tile.hCost;

        // display the cost on tile
        if (tile != startTile && tile != goalTile) {
            tile.setText("<html>F:" + tile.fCost + "<br>G:" + tile.gCost + "</html>");
        }
    }
    public void search(){
        if (goalReached == false && step <300) {
            int col = currentTile.col;
            int row = currentTile.row;

            currentTile.setAsChecked();
            checkedList.add(currentTile);
            openList.remove(currentTile);
            //open the up tile
            if (row - 1 >= 0) {
                openTile(tile[col][row-1]);
            }
            //open the left tile
            if (col -1 >=0){
                openTile(tile[col-1][row]);
            }
            //open the down tile
            if (row +1 < maxRow) {
                openTile(tile[col][row + 1]);
            }
            //open the right tile
            if (col +1 < maxCol) {
                openTile(tile[col + 1][row]);
            }
            //find the best tile
            int bestTileIndex = 0;
            int bestTileFCost = 999;

            for (int i = 0; i < openList.size(); i++){
                if(openList.get(i).fCost < bestTileFCost){
                    bestTileIndex = i;
                    bestTileFCost = openList.get(i).fCost;
                }
                //if f cost is equal check the g cost
                else if (openList.get(i).fCost == bestTileFCost) {
                    if (openList.get(i).gCost < openList.get(bestTileIndex).gCost){
                        bestTileIndex = i;
                    }
                }
            }
            // then the loop is done we will have the best tile for our next step
            currentTile = openList.get(bestTileIndex);
            if (currentTile == goalTile){
                goalReached = true;
            }
        }
        step++;
    }
    public void autoSearch() {

        while (goalReached == false && step < 300) {

            int col = currentTile.col;
            int row = currentTile.row;

            currentTile.setAsChecked();
            checkedList.add(currentTile);
            openList.remove(currentTile);
            //open the up tile
            if (row - 1 >= 0) {
                openTile(tile[col][row-1]);
            }
            //open the left tile
            if (col -1 >=0){
                openTile(tile[col-1][row]);
            }
            //open the down tile
            if (row +1 < maxRow) {
                openTile(tile[col][row + 1]);
            }
            //open the right tile
            if (col +1 < maxCol) {
                openTile(tile[col + 1][row]);
            }
            //find the best tile
            int bestTileIndex = 0;
            int bestTileFCost = 999;

            for (int i = 0; i < openList.size(); i++){

                if(openList.get(i).fCost < bestTileFCost){
                    bestTileIndex = i;
                    bestTileFCost = openList.get(i).fCost;
                }
                //if f cost is equal check the g cost
                else if (openList.get(i).fCost == bestTileFCost) {
                    if (openList.get(i).gCost < openList.get(bestTileIndex).gCost){
                        bestTileIndex = i;
                    }
                }
            }
            // then the loop is done we will have the best tile for our next step
            currentTile = openList.get(bestTileIndex);
            if (currentTile == goalTile){
                goalReached = true;
                trackThePath();
            }
        }
        step++;
    }
    private void openTile(Tile tile){
        if (tile.open == false && tile.checked == false && tile.solid == false){


            tile.setAsOpen();
            tile.parent = currentTile;
            openList.add(tile);
        }
    }
    private void trackThePath(){

        Tile current = goalTile;

        while (current != startTile) {
            current = current.parent;

            if (current != startTile) {
                current.setAsPath();
            }
        }
    }
}
