package pt.isel.poo.connect4.model;

/**
 * Created by Palex on 27/05/2015.
 */
public enum Player {
    A('A'), B('B');

    private final char letter;

    private Player(char c) {
        letter = c;
    }

    public static Player valueOf(boolean player) {
        return player ? A : B ;
    }

    public Player other() {
        return this==A ? B : A;
    }

    public char toChar() {
        return letter;
    }

    public static Player valueOf(char c) {
        for(Player p : values())
            if (p.letter==c) return  p;
        return null;
    }
}
