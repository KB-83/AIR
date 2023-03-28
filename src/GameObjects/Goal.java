package GameObjects;

import java.awt.*;

public class Goal {
    public int width;
    public int height;
    public int x;
    public int y;
    public Color color;
    public Goal(int x, int y){
        color=Color.MAGENTA;
        width=100;
        height=30;
        this.x=x;
        this.y=y;
    }
}
