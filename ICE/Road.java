package ICE;

public class Road extends Field{

    public boolean canPass = true;
    public Road(int xPosition, int yPosition, boolean canPass) {
        super(xPosition, yPosition, canPass);
        this.canPass = canPass;
    }
}
