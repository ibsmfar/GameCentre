package a207phase1.fall2018.gamecentre;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainGame2048 {

    public static final int SPAWN_ANIMATION = -1;
    public static final int MOVE_ANIMATION = 0;
    public static final int MERGE_ANIMATION = 1;

    public static final int FADE_GLOBAL_ANIMATION = 0;
    private static final long MOVE_ANIMATION_TIME = MainView2048.BASE_ANIMATION_TIME;
    private static final long SPAWN_ANIMATION_TIME = MainView2048.BASE_ANIMATION_TIME;
    private static final long NOTIFICATION_DELAY_TIME = MOVE_ANIMATION_TIME + SPAWN_ANIMATION_TIME;
    private static final long NOTIFICATION_ANIMATION_TIME = MainView2048.BASE_ANIMATION_TIME * 5;
    private static final int startingMaxValue = 2048;
    //Odd state = game is not active
    //Even state = game is active
    //Win state = active state + 1
    private static final int GAME_WIN = 1;
    private static final int GAME_LOST = -1;
    private static final int GAME_NORMAL = 0;
    public int gameState = GAME_NORMAL;
    public int lastGameState = GAME_NORMAL;
    private int bufferGameState = GAME_NORMAL;
    private static final int GAME_ENDLESS = 2;
    private static final int GAME_ENDLESS_WON = 3;
    private static final String HIGH_SCORE = "high score";
    private static final String FIRST_RUN = "first run";
    private static int endingMaxValue;
    final int numSquaresX = 4;
    final int numSquaresY = 4;
    private final Context mContext;
    private final MainView2048 mView;
    public Grid2048 grid = null;
    public AnimationGrid aGrid;
    public boolean canUndo;
    public long score = 0;
    public long highScore = 0;
    public long lastScore = 0;
    private long bufferScore = 0;
    String username;
    ArrayList<Game2048ScoreboardEntry> gameScores;
    private GameStatesContainer<saveTuple2048> movesMade = new GameStatesContainer<>(3);

    /**
     * Creates a new 2048 game
     * @param context the context in which the game is being created
     * @param view the view of the game
     * @param username the username of the current user
     */
    public MainGame2048(Context context, MainView2048 view, String username) {
        gameScores = SavingData.loadFromFile(SavingData.GAME_SCOREBOARD_2048, context);
        mContext = context;
        mView = view;
        this.username = username;
        endingMaxValue = (int) Math.pow(2, view.numCellTypes - 1);
    }

    /**
     * Creates a new game if the grid is empty and sets up a game
     */
    public void newGame() {
        if (grid == null) {
            grid = new Grid2048(numSquaresX, numSquaresY);
        } else {
            prepareUndoState();
            saveUndoState();
            grid.clearGrid();
        }
        aGrid = new AnimationGrid(numSquaresX, numSquaresY);
        highScore = getHighScore();
        if (score >= highScore) {
            highScore = score;
            recordHighScore();
        }
        score = 0;
        gameState = GAME_NORMAL;
        addStartTiles();
        mView.showHelp = firstRun();
        mView.refreshLastTime = true;
        mView.resyncTime();
        mView.invalidate();
    }

    private void addStartTiles() {
        int startTiles = 2;
        for (int xx = 0; xx < startTiles; xx++) {
            this.addRandomTile();
        }
    }

    /**
     * Adds a tile randomly to the grid
     */
    private void addRandomTile() {
        if (grid.isCellsAvailable()) {
            int value = Math.random() < 0.9 ? 2 : 4;
            Tile2048 tile = new Tile2048(grid.randomAvailableCell(), value);
            spawnTile(tile);
        }
    }

    /**
     * Displays the tile that has been created
     * @param tile the created tile
     */
    private void spawnTile(Tile2048 tile) {
        grid.insertTile(tile);
        aGrid.startAnimation(tile.getX(), tile.getY(), SPAWN_ANIMATION,
                SPAWN_ANIMATION_TIME, MOVE_ANIMATION_TIME, null); //Direction: -1 = EXPANDING
    }

    /**
     * Record the user's high score
     */
    private void recordHighScore() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(HIGH_SCORE, highScore);
        editor.commit();
    }

    /**
     *
     * @return the user's high score
     */
    private long getHighScore() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
        return settings.getLong(HIGH_SCORE, -1);
    }

    /**
     *
     * @return if it the first time the user is playing 2048
     */
    private boolean firstRun() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (settings.getBoolean(FIRST_RUN, true)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(FIRST_RUN, false);
            editor.commit();
            return true;
        }
        return false;
    }

    /**
     * Set up the tiles in grid
     */
    private void prepareTiles() {
        for (Tile2048[] array : grid.field) {
            for (Tile2048 tile : array) {
                if (grid.isCellOccupied(tile)) {
                    tile.setMergedFrom(null);
                }
            }
        }
    }

    /**
     * move the tile to cell
     * @param tile the tile being moved
     * @param cell the cell the tile is being moved to
     */
    private void moveTile(Tile2048 tile, Cell cell) {
        grid.field[tile.getX()][tile.getY()] = null;
        grid.field[cell.getX()][cell.getY()] = tile;
        tile.updatePosition(cell);
    }

    /**
     * save the current tiles so we can undo back to them
     */
    private void saveUndoState() {
        grid.saveTiles();
        canUndo = true;
        lastScore = bufferScore;
        lastGameState = bufferGameState;
    }

    /**
     * prepare the undo tiles to be saved
     */
    private void prepareUndoState() {
        grid.prepareSaveTiles();
        bufferScore = score;
        bufferGameState = gameState;
    }

    /**
     * Undo the last move
     */
    void revertUndoState() {
        if (canUndo) {
            canUndo = false;
            aGrid.cancelAnimations();
            grid.revertTiles();
            score = lastScore;
            gameState = lastGameState;
            mView.refreshLastTime = true;
            mView.invalidate();
        }
    }

    /**
     *
     * @return if the game has been won
     */
    boolean gameWon() {
        return (gameState > 0 && gameState % 2 != 0);
    }

    /**
     *
     * @return if a game has been lost
     */
    boolean gameLost() {
        return (gameState == GAME_LOST);
    }

    /**
     *
     * @return if a game is currently being played
     */
    public boolean isActive() {
        return !(gameWon() || gameLost());
    }

    /**
     * executes a move based on direction
     * @param direction the direction the user swiped in
     */
    public void move(int direction) {
        aGrid.cancelAnimations();
        // 0: up, 1: right, 2: down, 3: left
        if (!isActive()) {
            return;
        }
        prepareUndoState();
        Cell vector = getVector(direction);
        List<Integer> traversalsX = buildTraversalsX(vector);
        List<Integer> traversalsY = buildTraversalsY(vector);
        boolean moved = false;

        prepareTiles();

        for (int xx : traversalsX) {
            for (int yy : traversalsY) {
                Cell cell = new Cell(xx, yy);
                Tile2048 tile = grid.getCellContent(cell);

                if (tile != null) {
                    Cell[] positions = findFarthestPosition(cell, vector);
                    Tile2048 next = grid.getCellContent(positions[1]);

                    if (next != null && next.getValue() == tile.getValue() && next.getMergedFrom() == null) {
                        Tile2048 merged = new Tile2048(positions[1], tile.getValue() * 2);
                        Tile2048[] temp = {tile, next};
                        merged.setMergedFrom(temp);

                        grid.insertTile(merged);
                        grid.removeTile(tile);

                        // Converge the two tiles' positions
                        tile.updatePosition(positions[1]);

                        int[] extras = {xx, yy};
                        aGrid.startAnimation(merged.getX(), merged.getY(), MOVE_ANIMATION,
                                MOVE_ANIMATION_TIME, 0, extras); //Direction: 0 = MOVING MERGED
                        aGrid.startAnimation(merged.getX(), merged.getY(), MERGE_ANIMATION,
                                SPAWN_ANIMATION_TIME, MOVE_ANIMATION_TIME, null);

                        // Update the score
                        score = score + merged.getValue();

                        highScore = Math.max(score, highScore);

                        // The mighty 2048 tile
                        if (merged.getValue() >= winValue() && !gameWon()) {
                            gameState = gameState + GAME_WIN; // Set win state
                            endGame();
                        }
                    } else {
                        moveTile(tile, positions[0]);
                        int[] extras = {xx, yy, 0};
                        aGrid.startAnimation(positions[0].getX(), positions[0].getY(), MOVE_ANIMATION, MOVE_ANIMATION_TIME, 0, extras); //Direction: 1 = MOVING NO MERGE
                    }

                    if (!positionsEqual(cell, tile)) {
                        moved = true;
                    }
                }
            }
        }

        if (moved) {
            saveUndoState();
            addRandomTile();
            checkLose();
            save();
        }
        mView.resyncTime();
        mView.invalidate();
    }

    /**
     * check if a user has lost the game
     */
    private void checkLose() {
        if (!movesAvailable() && !gameWon()) {
            gameState = GAME_LOST;
            endGame();
        }
    }

    /**
     * End the game, record the user's score, and possibly update the 2048 Scoreboard
     */
    private void endGame() {
        aGrid.startAnimation(-1, -1, FADE_GLOBAL_ANIMATION, NOTIFICATION_ANIMATION_TIME, NOTIFICATION_DELAY_TIME, null);
        Game2048ScoreboardEntry g = new Game2048ScoreboardEntry(username, (int) score);
        updateScoreBoard(g);
        if (score >= highScore) {
            highScore = score;
            recordHighScore();
        }
    }

    /**
     *
     * @param direction the direction the user swiped in
     * @return the Cell that is in the direction the user swiped in
     */
    private Cell getVector(int direction) {
        Cell[] map = {
                new Cell(0, -1), // up
                new Cell(1, 0),  // right
                new Cell(0, 1),  // down
                new Cell(-1, 0)  // left
        };
        return map[direction];
    }

    /**
     *
     * @param vector a cell
     * @return the list of x values to be traversed
     */
    private List<Integer> buildTraversalsX(Cell vector) {
        List<Integer> traversals = new ArrayList<>();

        for (int xx = 0; xx < numSquaresX; xx++) {
            traversals.add(xx);
        }
        if (vector.getX() == 1) {
            Collections.reverse(traversals);
        }

        return traversals;
    }

    /**
     *
     * @param vector a Cell
     * @return the list of y values to be traversed
     */
    private List<Integer> buildTraversalsY(Cell vector) {
        List<Integer> traversals = new ArrayList<>();

        for (int xx = 0; xx < numSquaresY; xx++) {
            traversals.add(xx);
        }
        if (vector.getY() == 1) {
            Collections.reverse(traversals);
        }

        return traversals;
    }

    /**
     *
     * @param cell A cell
     * @param vector A cell
     * @return an array of cells furthest from cell
     */
    private Cell[] findFarthestPosition(Cell cell, Cell vector) {
        Cell previous;
        Cell nextCell = new Cell(cell.getX(), cell.getY());
        do {
            previous = nextCell;
            nextCell = new Cell(previous.getX() + vector.getX(),
                    previous.getY() + vector.getY());
        } while (grid.isCellWithinBounds(nextCell) && grid.isCellAvailable(nextCell));

        return new Cell[]{previous, nextCell};
    }

    /**
     *
     * @return if there are moves available
     */
    private boolean movesAvailable() {
        return grid.isCellsAvailable() || tileMatchesAvailable();
    }

    /**
     *
     * @return if there are tiles on the board that can be matched (a 4 and a 4, for examples)
     */
    private boolean tileMatchesAvailable() {
        Tile2048 tile;

        for (int xx = 0; xx < numSquaresX; xx++) {
            for (int yy = 0; yy < numSquaresY; yy++) {
                tile = grid.getCellContent(new Cell(xx, yy));

                if (tile != null) {
                    for (int direction = 0; direction < 4; direction++) {
                        Cell vector = getVector(direction);
                        Cell cell = new Cell(xx + vector.getX(), yy + vector.getY());

                        Tile2048 other = grid.getCellContent(cell);

                        if (other != null && other.getValue() == tile.getValue()) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     *
     * @param first a cell
     * @param second a cell
     * @return if the two cells are in the same position
     */
    private boolean positionsEqual(Cell first, Cell second) {
        return first.getX() == second.getX() && first.getY() == second.getY();
    }

    /**
     *
     * @return a value indicating whether or not the game has been won
     */
    private int winValue() {
        if (!canContinue()) {
            return endingMaxValue;
        } else {
            return startingMaxValue;
        }
    }

    /**
     * Put the game into endless mode
     */
    void setEndlessMode() {
        gameState = GAME_ENDLESS;
        mView.invalidate();
        mView.refreshLastTime = true;
    }

    /**
     *
     * @return if a user can continue
     */
    boolean canContinue() {
        return !(gameState == GAME_ENDLESS || gameState == GAME_ENDLESS_WON);
    }

    /**
     * save the current game
     */
    public void save() {
        int width = numSquaresX;
        int height = numSquaresY;
        long currScore = score;
        long highestScore = highScore;
        long undoScore = lastScore;
        boolean undoAvailable = canUndo;
        int currGameState = gameState;
        int undoGameState = lastGameState;
        Tile2048[][] field = grid.field;
        Tile2048[][] undoField = grid.undoField;
        saveTuple2048 st = new saveTuple2048(width, height, currScore,
                highestScore, undoScore,
                canUndo, currGameState, undoGameState);
        for (int xPosition = 0; xPosition < field.length; xPosition++) {
            for (int yPosition = 0; yPosition < field[0].length; yPosition++) {
                if (field[xPosition][yPosition] != null) {
                    TileContainer2048 tc = new TileContainer2048(xPosition, yPosition, field[xPosition][yPosition].getValue());
                    st.currentTiles.add(tc);
                }
            }
        }
        movesMade.contents.add(st);
        movesMade.currentMoveCounter++;
        if ((this.movesMade.currentMoveCounter - this.movesMade.undoMoveCounter) > this.movesMade.getNumUndos()) {
            this.movesMade.undoMoveCounter++;

        }

    }

//    public TileContainer2048 saveTile(int xPosition, int yPosition, int value) {
//        return new TileContainer2048(xPosition, yPosition, value);
//    }

    /**
     * Update the scoreboard if g is an entry with a score higher than the ones already present
     * @param g the entry to be entered
     */
    void updateScoreBoard(Game2048ScoreboardEntry g){
        gameScores.add(g);
        Collections.sort(gameScores, new SortByScore());
        if (gameScores.size() == 6){
            gameScores.remove(gameScores.size() - 1);
       }
        SavingData.saveToFile(SavingData.GAME_SCOREBOARD_2048, mContext, gameScores);
    }

}
