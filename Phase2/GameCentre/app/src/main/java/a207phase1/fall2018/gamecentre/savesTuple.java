package a207phase1.fall2018.gamecentre;

import java.io.Serializable;

class savesTuple implements Serializable {
    private int row1;
    private int col1;
    private int row2;
    private int col2;
    private int minutes;
    private int seconds;
    private int milliseconds;


    /**
     *
     * @param t the index of the row of the first tile stored
     * @param u the index of the column of the first tile stored
     * @param v the index of the row of the second tile stored
     * @param w the index of the column of the second tile stored
     * @param x the number of minutes at the time that the savesTuple was created
     * @param y the number of seconds at the time that the savesTuple was created
     * @param z the number of milliseconds at the time that the savesTuple was created
     */
    savesTuple(int t, int u, int v, int w, int x, int y, int z) {
        this.row1 = t;
        this.col1 = u;
        this.row2 = v;
        this.col2 = w;
        this.minutes = x;
        this.seconds = y;
        this.milliseconds = z;
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


    /**
     *
     * @return the number of minutes passed in game when the savesTuple was created
     */
    int getMinutes() {
        return minutes;
    }


    /**
     *
     * @return the number of seconds passed in game when the savesTuple was created
     */
    int getSeconds() {
        return seconds;
    }


    /**
     *
     * @return the number of milliseconds passed in game when the savesTuple was created
     */
    int getMilliseconds() {
        return milliseconds;
    }
}

