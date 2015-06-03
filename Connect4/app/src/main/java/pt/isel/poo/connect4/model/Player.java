package pt.isel.poo.connect4.model;

/**
 * Created by Palex on 27/05/2015.
 */
public enum Player {
    A, B;

    public static Player valueOf(boolean player) {
        return player ? A : B ;
    }

    public Player other() {
        return this==A ? B : A;
    }
}
