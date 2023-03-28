package GameEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameModePanel extends MainGameFrame implements ActionListener {
    JCheckBox timeLimited = new JCheckBox("Time Limited");
    JCheckBox goalLimited = new JCheckBox("Goal Limited");
    JTextArea numOfGoalsTextArea=new JTextArea("type num of goals ...");
    JCheckBox isTwoMargin = new JCheckBox("Two Margin");
    JTextArea minutesOfGameTextArea = new JTextArea("minutes of game ...");
    JTextArea nameOfPlayerOne=new JTextArea("name of player one ...");
    public Color colorOfPlayerOne;
    JTextArea nameOfPlayerTwo=new JTextArea("name of player two ...");
    public Color colorOfPlayerTwo;
    JButton okButton=new JButton("Ok");
    Listener listener;
    public String playerOneName="";
    public String playerTwoName="";
    public boolean isGameTimeLimited;
    public boolean isGameGoalLimited;
    public boolean isGameTwoMargin;
    public int minutesOfGame;
    public int goalsOfGame;
    boolean isInfoOfGameTrue=false;
    public GameModePanel(){
        super(Color.BLACK);
        this.setPanelDesign();
        this.add(timeLimited);
        this.add(goalLimited);
        this.add(isTwoMargin);
        this.add(numOfGoalsTextArea);
        this.add(minutesOfGameTextArea);
        this.add(nameOfPlayerOne);
        this.add(nameOfPlayerTwo);
        this.add(okButton);
        timeLimited.addActionListener(this);
        goalLimited.addActionListener(this);
        isTwoMargin.addActionListener(this);
        okButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == okButton){
            setInfoOfGame();
            if(isInfoOfGameTrue){
                colorOfPlayerOne=JColorChooser.showDialog(null,"color of player one",Color.magenta);
                colorOfPlayerTwo=JColorChooser.showDialog(null,"color of player Two",Color.magenta);
                listener.listen("OK");
            }
        }
        if(timeLimited.isSelected()){
            listener.listen("TIME LIMITED");
        }
        if(goalLimited.isSelected()){
            listener.listen("GOAL LIMITED");
        }
        if(timeLimited.isSelected() == false){
            listener.listen("NOT TIME LIMITED");
        }
        if(goalLimited.isSelected() == false){
            listener.listen("NOT GOAL LIMITED");
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
    public void gameIsTimeLimited(boolean isIt){
        minutesOfGameTextArea.setVisible(isIt);
    }
    public void gameIsGoalLimited(boolean isIt){
        numOfGoalsTextArea.setVisible(isIt);
        isTwoMargin.setVisible(isIt);
    }
    public void isGameMoodSelected(){
        boolean answer=false;
        if(goalLimited.isSelected() || timeLimited.isSelected()){
            answer=true;
        }
        nameOfPlayerOne.setVisible(answer);
        nameOfPlayerTwo.setVisible(answer);
        okButton.setVisible(answer);
    }
    public void setPanelDesign(){
        this.setFocusable(true);
        timeLimited.setBounds((400-150)/2,100,150,30);
        timeLimited.setBackground(Color.BLACK);
        timeLimited.setForeground(Color.MAGENTA);
        timeLimited.setFocusable(false);
        goalLimited.setBounds(125,130+10,150,30);
        goalLimited.setBackground(Color.BLACK);
        goalLimited.setForeground(Color.MAGENTA);
        goalLimited.setFocusable(false);
        numOfGoalsTextArea.setBounds(125,170+10,150,30);
        numOfGoalsTextArea.setBackground(Color.MAGENTA);
        numOfGoalsTextArea.setForeground(Color.BLACK);
        numOfGoalsTextArea.setVisible(false);
        isTwoMargin.setBounds(125,210+10,150,30);
        isTwoMargin.setBackground(Color.BLACK);
        isTwoMargin.setVisible(false);
        isTwoMargin.setForeground(Color.MAGENTA);
        isTwoMargin.setFocusable(false);
        minutesOfGameTextArea.setBounds(125,250+10,150,30);
        minutesOfGameTextArea.setBackground(Color.MAGENTA);
        minutesOfGameTextArea.setForeground(Color.BLACK);
        minutesOfGameTextArea.setVisible(false);
        nameOfPlayerOne.setBounds(125,290+10,150,30);
        nameOfPlayerOne.setBackground(Color.MAGENTA);
        nameOfPlayerOne.setForeground(Color.BLACK);
        nameOfPlayerOne.setVisible(false);
        nameOfPlayerTwo.setBounds(125,330+10,150,30);
        nameOfPlayerTwo.setBackground(Color.MAGENTA);
        nameOfPlayerTwo.setForeground(Color.BLACK);
        nameOfPlayerTwo.setVisible(false);
        okButton.setBounds((400-50)/2,500,50,30);
        okButton.setBackground(Color.MAGENTA);
        okButton.setForeground(Color.BLACK);
        okButton.setFocusable(false);
        okButton.setVisible(false);
        this.setLayout(null);
    }
    public void setInfoOfGame(){
        try {
        playerOneName=nameOfPlayerOne.getText();
        playerTwoName=nameOfPlayerTwo.getText();
        isGameTimeLimited=timeLimited.isSelected();
        isGameGoalLimited=goalLimited.isSelected();
        if(isGameGoalLimited){
            isGameTwoMargin=isTwoMargin.isSelected();
            goalsOfGame = Integer.parseInt(numOfGoalsTextArea.getText());
        }
        if(isGameTimeLimited){
            minutesOfGame = Integer.parseInt(minutesOfGameTextArea.getText());

        }
        isInfoOfGameTrue=true;
        } catch (NumberFormatException e) {
            System.out.println("inter valid");
        }
        }
}
