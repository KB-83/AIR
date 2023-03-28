package GameObjects;

import java.awt.*;
import java.util.ArrayList;

public class Player {
    //these x and y are top left of circle
    public int numOfGoals;
    public int x;
    public int y;
    public int radius;
    public int v;
    public int maxX,maxY,minX,minY;
    public String name;
    public Color color;
    public ArrayList<Gift> activeGifts = new ArrayList<>();
    public Player(int x,int y,int maxX,int maxY,int minX,int minY){
        v=3;
        radius=20;
        numOfGoals=0;
        this.x=x;
        this.y=y;
        this.maxX=maxX;
        this.maxY=maxY;
        this.minX=minX;
        this.minY=minY;
    }
}
