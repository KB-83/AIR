package GameObjects;

import java.awt.*;

public class Gift {
    //gift x and y check again
    public int x,y;
    public int radius;
    public boolean isFireBall = false;
    public boolean isBiggerGoal = false;
    public boolean isMirrorWall = false;
    public boolean isActive;
    public int numOfGettingBigger;
    public int gettingBiggerRate;
    // time handler
    public long startOfCreated;
    public long startOfEnd;
    public boolean isEndOfThisGift;
    public boolean isVisible;
    public Color color;
    public Player owner;
    public Gift(){
        double xRandom = Math.random();
        x = (int) (300 * (xRandom) + 60);
        double yRandom = Math.random();
        y = (int) (530 * (yRandom) + 80);
        double giftType = Math.random();
        if(giftType < 0.33){
            isFireBall= true;
            color=Color.red;
        }
        else if(giftType > 0.33 && giftType<= 0.66){
            isBiggerGoal = true;
            color = Color.orange;
        }
        else {
            isMirrorWall= true;
            color = Color.cyan;
        }
        radius=20;
        isActive=false;
        numOfGettingBigger=0;
        gettingBiggerRate=5;//pixels
        isEndOfThisGift = false;
        isVisible=true;
        startOfCreated=System.currentTimeMillis();
    }
}
