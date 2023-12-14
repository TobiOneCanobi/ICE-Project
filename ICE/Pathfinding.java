package ICE;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Pathfinding extends JPanel {

    //Screen settings
    final int maxCol = 20;
    final int maxRow = 15;
    final int tileSize = 70;
    final int screenWidth = tileSize * maxCol;
    final int screenHeight = tileSize * maxRow;

    Tile[][] tile = new Tile[maxCol][maxRow];
    Tile startTile, goalTile, currentTile, normalTile;
    ArrayList<Tile> openList = new ArrayList<>();
    ArrayList<Tile> checkedList = new ArrayList<>();
    ArrayList<Tile> allTiles = new ArrayList<>();

    Boolean goalReached = false;
    int step = 0;

    public Pathfinding() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setLayout(new GridLayout(maxRow, maxCol));

        // place tile
        int col = 0;
        int row = 0;

        while (col < maxCol && row < maxRow) {
            tile[col][row] = new Tile(col, row);
            this.add(tile[col][row]);
            allTiles.add(tile[col][row]);

            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }
    }

    public void generateRandom() {
        Random rn = new Random();

        Tile tileClass = new Tile(0, 0);
        if (!tileClass.solid && !tileClass.checked && !tileClass.goal) {
            int randomCol = rn.nextInt(maxCol);
            int randomRow = rn.nextInt(maxRow);
            setStartTile(randomCol, randomRow);
        }
        if (!tileClass.solid && !tileClass.checked && !tileClass.start) {
            int randomCol = rn.nextInt(maxCol);
            int randomRow = rn.nextInt(maxRow);
            setGoalTile(randomCol, randomRow);
        }
        // generate blocks
        for (int i = 0; i < (maxCol*maxRow)/3; i++) {
            int randomColForLoop = rn.nextInt(maxCol);
            int randomRowForLoop = rn.nextInt(maxRow);
            if (!tileClass.goal && !tileClass.start) {
                setSolidTile(randomColForLoop, randomRowForLoop);
            }
        }
    }

    public void setStartTile(int col, int row) {
        tile[col][row].setAsStart();
        startTile = tile[col][row];
        currentTile = startTile;
    }

    public void checkStart() {
        for (Tile tile : allTiles) {
            if (tile == startTile) {
                tile.resetTile();
            }
        }
    }

    public void setGoalTile(int col, int row) {
        tile[col][row].setAsGoal();
        goalTile = tile[col][row];
    }

    public void checkGoal() {
        for (Tile tile : allTiles) {
            if (tile == goalTile) {
                tile.resetTile();
            }
        }
    }

    private void setSolidTile(int col, int row) {
        if (!tile[col][row].start && !tile[col][row].goal) {
            tile[col][row].setAsSolid();
        }
    }

    public void placeOrRemoveSolid(int col, int row) {
        if (tile[col][row].isSolid() == true) {
            tile[col][row].resetTile();
        } else if (tile[col][row].isSolid() == false) {
            tile[col][row].setAsSolid();
        }
    }

    public void reset() {
        for (Tile tile : allTiles) {
            tile.resetTile();
            goalReached = false;
            startTile = normalTile;
            goalTile = normalTile;
            currentTile = normalTile;
            openList.clear();
            checkedList.clear();
        }
    }

    public void setCostOnTiles() {

        int col = 0;
        int row = 0;

        while (col < maxCol && row < maxRow) {
            getCost(tile[col][row]);
            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }
    }

    public void updateTileCosts() {
        for (Tile[] tiles : tile) {
            for (Tile t : tiles) {
                getCost(t);
            }
        }
    }

    public void getCost(Tile tile) {
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
        if (ButtonHandler.costActive == true) {
            if (tile != startTile && tile != goalTile) {
                tile.setText("<html>F:" + tile.fCost + "<br>G:" + tile.gCost + "</html>");
            }
            // remove the cost on tile
        } else if (ButtonHandler.costActive == false) {
            if (tile != startTile && tile != goalTile) {
                tile.setText("");
            }
        }
    }

    public void autoSearch() {
        new Thread(() -> {
            while (goalReached == false && step < 300) {

                int col = currentTile.col;
                int row = currentTile.row;

                currentTile.setAsChecked();
                checkedList.add(currentTile);
                openList.remove(currentTile);
                //open the up tile
                if (row - 1 >= 0) {
                    openTile(tile[col][row - 1]);
                }
                //open the left tile
                if (col - 1 >= 0) {
                    openTile(tile[col - 1][row]);
                }
                //open the down tile
                if (row + 1 < maxRow) {
                    openTile(tile[col][row + 1]);
                }
                //open the right tile
                if (col + 1 < maxCol) {
                    openTile(tile[col + 1][row]);
                }
                //find the best tile
                int bestTileIndex = 0;
                int bestTileFCost = 999;

                for (int i = 0; i < openList.size(); i++) {

                    if (openList.get(i).fCost < bestTileFCost) {
                        bestTileIndex = i;
                        bestTileFCost = openList.get(i).fCost;
                    }
                    //if f cost is equal check the g cost
                    else if (openList.get(i).fCost == bestTileFCost) {
                        if (openList.get(i).gCost < openList.get(bestTileIndex).gCost) {
                            bestTileIndex = i;
                        }
                    }
                }
                // then the loop is done we will have the best tile for our next step
                currentTile = openList.get(bestTileIndex);
                if (currentTile == goalTile) {
                    goalReached = true;
                    trackThePath();
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            step++;
        }).start();
    }

    public void openTile(Tile tile) {
        if (tile.open == false && tile.checked == false && tile.solid == false) {

            tile.setAsOpen();
            tile.parent = currentTile;
            openList.add(tile);
        }
    }

    private void trackThePath() {
        new Thread(() -> {
            Tile current = goalTile;

            while (current != startTile) {
                current = current.parent;

                if (current != startTile) {
                    current.setAsPath();
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }
}
