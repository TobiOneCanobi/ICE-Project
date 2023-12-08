package ICE;

public class EndPosition extends Field {

    public boolean canPass = true;

    public EndPosition(int xPosition, int yPosition, boolean canPass) {
        super(xPosition, yPosition, canPass);
        this.canPass = canPass;
    }
}
