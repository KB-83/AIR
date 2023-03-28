 package GameObjects;

import java.awt.*;

public class Ball{
    //these x and y are top left of circle
    public int x;
    public int y;
    public int radius;
    public int xOfV;
    public int yOfV;
    public int measureOfV;
    public int maxX,maxY,minX,minY;
    public Color color;
    // new for gift
    public Player lastTouch;
    public Ball(){
        measureOfV=10;
        x=200-10;
        y=375;
        radius=10;
        xOfV=0;
        color=Color.WHITE;
        maxX=400-(10*2);
        maxY=700-(10*2);
        minX=0;
        minY=50;
        double start=Math.random();
        if(start<0.5){
            yOfV=measureOfV;
        }
        else {
            yOfV=-measureOfV;
        }
    }
}
