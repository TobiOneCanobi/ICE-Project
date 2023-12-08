package ICE;

public class Building extends Field {

    public boolean canPass = false;

    public Building(int xPosition, int yPosition, boolean canPass) {
        super(xPosition, yPosition, canPass);
        this.canPass = canPass;
    }
}
