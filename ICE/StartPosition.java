package ICE;

public class StartPosition extends Field{

    public boolean canPass = true;
    public StartPosition(int xPosition, int yPosition, boolean canPass) {
        super(xPosition, yPosition, canPass);
        this.canPass = canPass;
    }
}
