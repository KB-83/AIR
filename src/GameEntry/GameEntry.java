package GameEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameEntry extends MainGameFrame implements ActionListener {

    JButton startGame=new JButton("start new game");
    JButton seeHistory=new JButton("see games history");
    JButton close=new JButton("close the program");
    Listener listener;
    public GameEntry() {
        super(Color.BLACK);
        this.setFocusable(true);
        startGame.setBackground(Color.MAGENTA);
        seeHistory.setBackground(Color.MAGENTA);
        close.setBackground(Color.MAGENTA);
        startGame.setForeground(Color.BLACK);
        seeHistory.setForeground(Color.BLACK);
        close.setForeground(Color.BLACK);
        startGame.setFocusable(false);
        seeHistory.setFocusable(false);
        close.setFocusable(false);
        startGame.addActionListener(this);
        seeHistory.addActionListener(this);
        close.addActionListener(this);
        this.setLayout(null);
        this.add(startGame);
        this.add(seeHistory);
        this.add(close);
        Insets insets = this.getInsets();
        startGame.setBounds(((this.getSize().width-140)/2) + insets.left, (this.getSize().height/2) + insets.top,
                140, 30);
        seeHistory.setBounds(((this.getSize().width-140)/2) + insets.left, (this.getSize().height/2)+35 + insets.top,
                140, 30);
        close.setBounds(((this.getSize().width-140)/2) + insets.left,(this.getSize().height/2)+70  + insets.top,
                140, 30 );

    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.MAGENTA);
        g.setFont(new Font("monospaced", Font.BOLD, 36));
        FontMetrics fm = g.getFontMetrics();
        int textX = (this.getSize().width / 2) - (fm.stringWidth("AIR HOCKEY") / 2);
        g.drawString("AIR HOCKEY", textX,140);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(this.seeHistory)){
            listener.listen("GAME HISTORY");
        }
        else if(e.getSource().equals(this.startGame)){
            listener.listen("START GAME");
        }
        else if(e.getSource().equals(this.close)){
            listener.listen("CLOSE");
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
