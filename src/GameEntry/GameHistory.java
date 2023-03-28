package GameEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameHistory extends MainGameFrame{

    JTextArea display = new JTextArea ( 30,51);
    JScrollPane scroll = new JScrollPane ( display );
    Listener listener;
    JButton airHockey = new JButton();
    public GameHistory(){
        super(Color.BLACK);
        this.setFocusable(true);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.MAGENTA);
        display.setFont(new Font("monospaced", Font.BOLD, 12));
        display.setSize(this.getSize());
        display.setEditable ( false ); // set textArea non-editable
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
        scroll.setHorizontalScrollBarPolicy ( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        scroll.setBorder(null);
        scroll.setBounds(0,0,400,400);
        this.add ( scroll );
        airHockey.setFocusable(false);
        airHockey.setBackground(Color.black);
        airHockey.setFont(new Font("monospaced", Font.BOLD, 36));
        airHockey.setForeground(Color.MAGENTA);
        airHockey.setText("AIR HOCKEY");
        airHockey.setBounds(50,600,300,50);
        airHockey.addActionListener(this);
        this.add(airHockey);
        this.setLayout(null);
    }
    public void setListener (Listener listener) {
        this.listener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == airHockey){
            listener.listen("GO BACK");
        }
    }
    public void addHistory(String s){
        display.append(" "+s+"\n");
    }

}
