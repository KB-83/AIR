package GameEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePause extends MainGameFrame implements ActionListener {
    JButton exit=new JButton("Exit");
    JButton resume=new JButton("Resume");
    Listener listener;
    public GamePause() {
        super(Color.BLACK);
        this.setFocusable(true);
        this.setLayout(null);
        exit.setBounds(150,500,100,30);
        resume.setBounds(150,460,100,30);
        exit.setBackground(Color.MAGENTA);
        resume.setBackground(Color.MAGENTA);
        exit.setForeground(Color.black);
        resume.setForeground(Color.BLACK);
        exit.setFocusable(false);
        resume.setFocusable(false);
        exit.addActionListener(this);
        resume.addActionListener(this);
        this.add(exit);
        this.add(resume);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exit){
            listener.listen("EXIT");
        }
        else if( e.getSource() == resume){
            listener.listen("RESUME");
        }
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

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}