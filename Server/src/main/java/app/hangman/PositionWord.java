package app.hangman;

import java.util.ArrayList;

public class PositionWord {
    private ArrayList<Integer> positions;

    public PositionWord()
    {
        this.positions = new ArrayList<Integer>();
    }

    public void addPosition(Integer position) {
        this.positions.add(position);
    }

    public ArrayList<Integer> getPositions()
    {
        return positions;
    }
}
