package app.hangman;

public class PositionWord {
    int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public int[] getPosition() {
        return new int[]{position};
    }
}
