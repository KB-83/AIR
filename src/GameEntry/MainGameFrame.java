package GameEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class MainGameFrame extends JPanel implements ActionListener {
    Color color;
    // real width=400
    //real height=700
    //sony
//    int width=415,height=700+39;
    //mack
    int width=400,height=700+29;
    MainGameFrame(Color color){
        super();
        this.setFocusable(true);
        this.color=color;
        this.setBackground(color);
        this.setSize(new Dimension(this.width,this.height));
    }
}
