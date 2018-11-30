package a207phase1.fall2018.gamecentre;

import java.util.ArrayList;

/**
 * The animation grid for 2048
 */
public class AnimationGrid {
    public final ArrayList<AnimationCell> globalAnimation = new ArrayList<>();
    private final ArrayList<AnimationCell>[][] field;
    private int activeAnimations = 0;
    private boolean oneMoreFrame = false;

    public AnimationGrid(int x, int y) {
        field = new ArrayList[x][y];

        for (int xx = 0; xx < x; xx++) {
            for (int yy = 0; yy < y; yy++) {
                field[xx][yy] = new ArrayList<>();
            }
        }
    }

    /**
     * Starts an animation
     * @param x x coordinate
     * @param y y coordinate
     * @param animationType the type of animation
     * @param length how long the animation should be shown
     * @param delay the delay after the animation has been shown
     * @param extras extras to be added
     */
    public void startAnimation(int x, int y, int animationType, long length, long delay, int[] extras) {
        AnimationCell animationToAdd = new AnimationCell(x, y, animationType, length, delay, extras);
        if (x == -1 && y == -1) {
            globalAnimation.add(animationToAdd);
        } else {
            field[x][y].add(animationToAdd);
        }
        activeAnimations = activeAnimations + 1;
    }

    /**
     * handles the passage of time for animations
     * @param timeElapsed since animation started
     */
    void tickAll(long timeElapsed) {
        ArrayList<AnimationCell> cancelledAnimations = new ArrayList<>();
        for (AnimationCell animation : globalAnimation) {
            animation.tick(timeElapsed);
            if (animation.animationDone()) {
                cancelledAnimations.add(animation);
                activeAnimations = activeAnimations - 1;
            }
        }

        for (ArrayList<AnimationCell>[] array : field) {
            for (ArrayList<AnimationCell> list : array) {
                for (AnimationCell animation : list) {
                    animation.tick(timeElapsed);
                    if (animation.animationDone()) {
                        cancelledAnimations.add(animation);
                        activeAnimations = activeAnimations - 1;
                    }
                }
            }
        }

        for (AnimationCell animation : cancelledAnimations) {
            cancelAnimation(animation);
        }
    }

    /**
     *
     * @return if the animation is active
     */
    boolean isAnimationActive() {
        if (activeAnimations != 0) {
            oneMoreFrame = true;
            return true;
        } else if (oneMoreFrame) {
            oneMoreFrame = false;
            return true;
        } else {
            return false;
        }
    }

    ArrayList<AnimationCell> getAnimationCell(int x, int y) {
        return field[x][y];
    }

    /**
     * Cancels all animations
     */
    public void cancelAnimations() {
        for (ArrayList<AnimationCell>[] array : field) {
            for (ArrayList<AnimationCell> list : array) {
                list.clear();
            }
        }
        globalAnimation.clear();
        activeAnimations = 0;
    }

    /**
     * Cancels the animation
     * @param animation the animation
     */
    private void cancelAnimation(AnimationCell animation) {
        if (animation.getX() == -1 && animation.getY() == -1) {
            globalAnimation.remove(animation);
        } else {
            field[animation.getX()][animation.getY()].remove(animation);
        }
    }

}
