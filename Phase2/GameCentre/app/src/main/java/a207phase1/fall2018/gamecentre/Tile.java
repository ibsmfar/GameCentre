package a207phase1.fall2018.gamecentre;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;

import java.io.Serializable;

import a207phase1.fall2018.gamecentre.R;

/**
 * A Tile in a sliding tiles puzzle.
 */
public class Tile implements Comparable<Tile>, Serializable {

    /**
     * The background id to find the tile image.
     */
    private int background;

    /**
     * The unique id.
     */
    private int id;

    public static int dimension;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {

        return background;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {

        return id;
    }

    /**
     * A Tile with id and background. The background may not have a corresponding image.
     *
     * @param id         the id
     * @param background the background
     */
    public Tile(int id, int background) {
        this.id = id;
        this.background = background;
    }


    public void numberTile(int backgroundId, int numTiles) {
        id = backgroundId + 1;

        switch (backgroundId + 1) {
            case 1:
                background = R.drawable.tile_1;
                break;
            case 2:
                background = R.drawable.tile_2;
                break;
            case 3:
                background = R.drawable.tile_3;
                break;
            case 4:
                background = R.drawable.tile_4;
                break;
            case 5:
                background = R.drawable.tile_5;
                break;
            case 6:
                background = R.drawable.tile_6;
                break;
            case 7:
                background = R.drawable.tile_7;
                break;
            case 8:
                background = R.drawable.tile_8;
                break;
            case 9:
                if (numTiles != 9) {
                    background = R.drawable.tile_9;
                } else {
                    background = R.drawable.tile_blank;
                }
                break;
            case 10:
                background = R.drawable.tile_10;
                break;
            case 11:
                background = R.drawable.tile_11;
                break;
            case 12:
                background = R.drawable.tile_12;
                break;
            case 13:
                background = R.drawable.tile_13;
                break;
            case 14:
                background = R.drawable.tile_14;
                break;
            case 15:
                background = R.drawable.tile_15;
                break;
            case 16:
                if (numTiles != 16) {
                    background = R.drawable.tile_16;
                } else {
                    background = R.drawable.tile_blank;
                }
                break;
            case 17:
                background = R.drawable.tile_17;
                break;
            case 18:
                background = R.drawable.tile_18;
                break;
            case 19:
                background = R.drawable.tile_19;
                break;
            case 20:
                background = R.drawable.tile_20;
                break;
            case 21:
                background = R.drawable.tile_21;
                break;
            case 22:
                background = R.drawable.tile_22;
                break;
            case 23:
                background = R.drawable.tile_23;
                break;
            case 24:
                background = R.drawable.tile_24;
                break;
            case 25:
                background = R.drawable.tile_blank;
                break;
            default:
                background = R.drawable.tile_blank;
        }
    }

    public void uoftTile3x3(int backgroundId) {
        id = backgroundId + 1;

        switch (backgroundId + 1) {
            case 1:
                background = R.drawable.uoft_3_1;
                break;
            case 2:
                background = R.drawable.uoft_3_2;
                break;
            case 3:
                background = R.drawable.uoft_3_3;
                break;
            case 4:
                background = R.drawable.uoft_3_4;
                break;
            case 5:
                background = R.drawable.uoft_3_5;
                break;
            case 6:
                background = R.drawable.uoft_3_6;
                break;
            case 7:
                background = R.drawable.uoft_3_7;
                break;
            case 8:
                background = R.drawable.uoft_3_8;
                break;
            case 9:
                background = R.drawable.tile_blank;
                break;
            default:
                background = R.drawable.tile_blank;
        }
    }

    public void uoftTile4x4(int backgroundId) {
        id = backgroundId + 1;

        // This looks so ugly.
        switch (backgroundId + 1) {
            case 1:
                background = R.drawable.uoft_4_1;
                break;
            case 2:
                background = R.drawable.uoft_4_2;
                break;
            case 3:
                background = R.drawable.uoft_4_3;
                break;
            case 4:
                background = R.drawable.uoft_4_4;
                break;
            case 5:
                background = R.drawable.uoft_4_5;
                break;
            case 6:
                background = R.drawable.uoft_4_6;
                break;
            case 7:
                background = R.drawable.uoft_4_7;
                break;
            case 8:
                background = R.drawable.uoft_4_8;
                break;
            case 9:
                background = R.drawable.uoft_4_9;
                break;
            case 10:
                background = R.drawable.uoft_4_10;
                break;
            case 11:
                background = R.drawable.uoft_4_11;
                break;
            case 12:
                background = R.drawable.uoft_4_12;
                break;
            case 13:
                background = R.drawable.uoft_4_13;
                break;
            case 14:
                background = R.drawable.uoft_4_14;
                break;
            case 15:
                background = R.drawable.uoft_4_15;
                break;
            case 16:
                background = R.drawable.tile_blank;
                break;
            default:
                background = R.drawable.tile_blank;
        }
    }

    public void uoftTile5x5(int backgroundId) {
        id = backgroundId + 1;

        // This looks so ugly.
        switch (backgroundId + 1) {
            case 1:
                background = R.drawable.uoft_5_1;
                break;
            case 2:
                background = R.drawable.uoft_5_2;
                break;
            case 3:
                background = R.drawable.uoft_5_3;
                break;
            case 4:
                background = R.drawable.uoft_5_4;
                break;
            case 5:
                background = R.drawable.uoft_5_5;
                break;
            case 6:
                background = R.drawable.uoft_5_6;
                break;
            case 7:
                background = R.drawable.uoft_5_7;
                break;
            case 8:
                background = R.drawable.uoft_5_8;
                break;
            case 9:
                background = R.drawable.uoft_5_9;
                break;
            case 10:
                background = R.drawable.uoft_5_10;
                break;
            case 11:
                background = R.drawable.uoft_5_11;
                break;
            case 12:
                background = R.drawable.uoft_5_12;
                break;
            case 13:
                background = R.drawable.uoft_5_13;
                break;
            case 14:
                background = R.drawable.uoft_5_14;
                break;
            case 15:
                background = R.drawable.uoft_5_15;
                break;
            case 16:
                background = R.drawable.uoft_5_16;
                break;
            case 17:
                background = R.drawable.uoft_5_17;
                break;
            case 18:
                background = R.drawable.uoft_5_18;
                break;
            case 19:
                background = R.drawable.uoft_5_19;
                break;
            case 20:
                background = R.drawable.uoft_5_20;
                break;
            case 21:
                background = R.drawable.uoft_5_21;
                break;
            case 22:
                background = R.drawable.uoft_5_22;
                break;
            case 23:
                background = R.drawable.uoft_5_23;
                break;
            case 24:
                background = R.drawable.uoft_5_24;
                break;
            case 25:
                background = R.drawable.tile_blank;
                break;
            default:
                background = R.drawable.tile_blank;
        }
    }


    public void penguin5x5(int backgroundId){
        id = backgroundId + 1;
        // This looks so ugly.
        switch (backgroundId + 1) {
            case 1:
                background = R.drawable.penguin_5_1;
                break;
            case 2:
                background = R.drawable.penguin_5_2;
                break;
            case 3:
                background = R.drawable.penguin_5_3;
                break;
            case 4:
                background = R.drawable.penguin_5_4;
                break;
            case 5:
                background = R.drawable.penguin_5_5;
                break;
            case 6:
                background = R.drawable.penguin_5_6;
                break;
            case 7:
                background = R.drawable.penguin_5_7;
                break;
            case 8:
                background = R.drawable.penguin_5_8;
                break;
            case 9:
                background = R.drawable.penguin_5_9;
                break;
            case 10:
                background = R.drawable.penguin_5_10;
                break;
            case 11:
                background = R.drawable.penguin_5_11;
                break;
            case 12:
                background = R.drawable.penguin_5_12;
                break;
            case 13:
                background = R.drawable.penguin_5_13;
                break;
            case 14:
                background = R.drawable.penguin_5_14;
                break;
            case 15:
                background = R.drawable.penguin_5_15;
                break;
            case 16:
                background = R.drawable.penguin_5_16;
                break;
            case 17:
                background = R.drawable.penguin_5_17;
                break;
            case 18:
                background = R.drawable.penguin_5_18;
                break;
            case 19:
                background = R.drawable.penguin_5_19;
                break;
            case 20:
                background = R.drawable.penguin_5_20;
                break;
            case 21:
                background = R.drawable.penguin_5_21;
                break;
            case 22:
                background = R.drawable.penguin_5_22;
                break;
            case 23:
                background = R.drawable.penguin_5_23;
                break;
            case 24:
                background = R.drawable.penguin_5_24;
                break;
            case 25:
                background = R.drawable.tile_blank;
                break;
            default:
                background = R.drawable.tile_blank;
        }
    }

    public void penguin4x4(int backgroundId){
        id = backgroundId + 1;
        // This looks so ugly.
        switch (backgroundId + 1) {
            case 1:
                background = R.drawable.penguin_4_1;
                break;
            case 2:
                background = R.drawable.penguin_4_2;
                break;
            case 3:
                background = R.drawable.penguin_4_3;
                break;
            case 4:
                background = R.drawable.penguin_4_4;
                break;
            case 5:
                background = R.drawable.penguin_4_5;
                break;
            case 6:
                background = R.drawable.penguin_4_6;
                break;
            case 7:
                background = R.drawable.penguin_4_7;
                break;
            case 8:
                background = R.drawable.penguin_4_8;
                break;
            case 9:
                background = R.drawable.penguin_4_9;
                break;
            case 10:
                background = R.drawable.penguin_4_10;
                break;
            case 11:
                background = R.drawable.penguin_4_11;
                break;
            case 12:
                background = R.drawable.penguin_4_12;
                break;
            case 13:
                background = R.drawable.penguin_4_13;
                break;
            case 14:
                background = R.drawable.penguin_4_14;
                break;
            case 15:
                background = R.drawable.penguin_4_15;
                break;
            case 16:
                background = R.drawable.tile_blank;
                break;
            default:
                background = R.drawable.tile_blank;
        }
    }

    public void penguin3x3(int backgroundId){
        id = backgroundId + 1;
        // This looks so ugly.
        switch (backgroundId + 1) {
            case 1:
                background = R.drawable.penguin_3_1;
                break;
            case 2:
                background = R.drawable.penguin_3_2;
                break;
            case 3:
                background = R.drawable.penguin_3_3;
                break;
            case 4:
                background = R.drawable.penguin_3_4;
                break;
            case 5:
                background = R.drawable.penguin_3_5;
                break;
            case 6:
                background = R.drawable.penguin_3_6;
                break;
            case 7:
                background = R.drawable.penguin_3_7;
                break;
            case 8:
                background = R.drawable.penguin_3_8;
                break;
            case 9:
                background = R.drawable.tile_blank;
                break;
            default:
                background = R.drawable.tile_blank;
        }
    }

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId
     */
    public Tile(int picture, int backgroundId, int numTiles) {
        // id = backgroundId + 1;
        // This looks so ugly.
            if (SlidingTilesPreNewGameActivity.picture == 0) {
                numberTile(backgroundId, numTiles);
            }
            else if (SlidingTilesPreNewGameActivity.picture == 1) {
                if (numTiles == 9) {
                    penguin3x3(backgroundId);
                }
                else if (numTiles == 16) {
                    penguin4x4(backgroundId);
                }
                else {
                    penguin5x5(backgroundId);
                }
            }
            else if (SlidingTilesPreNewGameActivity.picture == 2) {
                if (numTiles == 9) {
                    uoftTile3x3(backgroundId);
                }
                else if (numTiles == 16) {
                    uoftTile4x4(backgroundId);
                }
                else {
                    uoftTile5x5(backgroundId);
                }
            }
    }

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.id - this.id;
    }
}
