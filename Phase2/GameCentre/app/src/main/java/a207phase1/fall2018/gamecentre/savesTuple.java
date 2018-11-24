package a207phase1.fall2018.gamecentre;

import java.io.Serializable;

/*
 * Stores a move that has been made
 */
class SavesTuple implements Serializable {
    private int row1;
    private int col1;
    private int row2;
    private int col2;

    /**
     *
     * @param t the index of the row of the first tile stored
     * @param u the index of the column of the first tile stored
     * @param v the index of the row of the second tile stored
     * @param w the index of the column of the second tile stored
     */
    SavesTuple(int t, int u, int v, int w) {
        this.row1 = t;
        this.col1 = u;
        this.row2 = v;
        this.col2 = w;
    }

    /**
     *
     * @return the index of the row of the first tile stored
     */
    int getRow1() {
        return row1;
    }

    /**
     *
     * @return the index of the column of the first tile stored
     */
    int getCol1() { return col1;}


    /**
     *
     * @return the index of the row of the second tile stored
     */
    int getRow2() {
        return row2;
    }

    /**
     *
     * @return the index of the column of the second tile stored
     */
    int getCol2() {
        return col2;
    }


}

