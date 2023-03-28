package GameEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverPanel extends JPanel implements ActionListener {
    JButton ok=new JButton("OK");
    JTextArea massage=new JTextArea();
    int width=200;
    int height=150;
    Listener listener;
    String winner;
    public GameOverPanel(){
        this.setLayout(null);
        this.setSize(width,height);
        this.setBackground(Color.BLACK);
        ok.setBounds(5,70,60,20);
        ok.setBackground(Color.MAGENTA);
        ok.setForeground(Color.BLACK);
        ok.setFocusable(false);
        ok.addActionListener(this);
        this.setFocusable(true);
        this.add(ok);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ok){
            listener.listen("OK");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.MAGENTA);
        g.setFont(new Font("monospaced", Font.BOLD, 12));
        g.drawString(winner+" is winner",10,50);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
    public void setWinner(String winner){
        this.winner = winner;
    }
}
