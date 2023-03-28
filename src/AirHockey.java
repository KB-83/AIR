import GameEntry.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AirHockey {
    JFrame jFrame=new JFrame("AIR HOCKEY");
    GameEntry gameEntry=new GameEntry( );
    GameHistory gameHistory=new GameHistory();
    GamePanel gamePanel=new GamePanel(Color.BLACK);
    GamePanel gamePanel1 = new GamePanel(Color.BLACK);
    GamePause gamePause = new GamePause();
    JFrame endOfGameMassage=new JFrame("");
    GameOverPanel gameOverPanel=new GameOverPanel();
    GameModePanel gameModePanel=new GameModePanel();
    long startOfPause;

    public AirHockey() throws IOException {
        setListeners();
        endOfGameMassage.setSize(gameOverPanel.getSize());
        endOfGameMassage.add(gameOverPanel);
        endOfGameMassage.setLocationRelativeTo(null);
        jFrame.setSize(gameEntry.getSize());
        jFrame.add(gameEntry);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public void setListeners(){
        gameHistory.setListener(new Listener() {
            @Override
            public void listen(String s) {
                switch (s){
                    case "GO BACK":
                        jFrame.setVisible(false);
                        jFrame.remove(gameHistory);
                        jFrame.add(gameEntry);
                        jFrame.setVisible(true);
                }
            }
        });
        gamePause.setListener(new Listener() {
            @Override
            public void listen(String s) {
                switch (s){
                    case "EXIT":
                        jFrame.setVisible(false);
                        jFrame.remove(gamePause);
                        jFrame.add(gameEntry);
                        jFrame.setVisible(true);
                        gameHistory.addHistory(gamePanel1.playerOne.name +" : " +gamePanel1.playerOne.numOfGoals +" --- " +gamePanel1.playerTwo.name +" : " +gamePanel1.playerTwo.numOfGoals+" --- WINNER :"
                                +gamePanel1.winner +" --- IS TIME LIMITED :"+ gamePanel1.isGameTimeLimited+" --- IS GOAL LIMITED :"+
                                gamePanel1.isGameGoalLimited +" --- IS TWO MARGIN :" + gamePanel1.isTwoMargin);
                        break;
                    case "RESUME" :
                        jFrame.setVisible(false);
                        jFrame.remove(gamePause);
                        gamePanel1.transferInfo(gamePanel);
                        jFrame.add(gamePanel);
                        gamePanel.startGameThread();
                        gamePanel1.transferInfo(gamePanel);
                        gamePanel.setDelay((int) ((System.currentTimeMillis() - startOfPause)/1000));
                        jFrame.setVisible(true);
                }
            }
        });
        gameOverPanel.setListener(new Listener() {
            @Override
            public void listen(String s) {
                switch (s){
                    case "OK":
                        endOfGameMassage.setVisible(false);
                        jFrame.setVisible(false);
                        jFrame.remove(gamePanel);
                        jFrame.add(gameEntry);
                        jFrame.setVisible(true);
                }
            }
        });
        gamePanel.setListener(new Listener() {
            @Override
            public void listen(String s) {
                switch (s) {
                    case "STOP":
                        startOfPause = System.currentTimeMillis();
                        jFrame.setVisible(false);
                        gamePanel.transferInfo(gamePanel1);
                        jFrame.remove(gamePanel);
                        jFrame.add(gamePause);
                        jFrame.setVisible(true);
                        break;
                    case "END":
                        gameOverPanel.setWinner(gamePanel.winner);
                        gameHistory.addHistory(gamePanel.playerOne.name +" : " +gamePanel.playerOne.numOfGoals+"--- " +gamePanel.playerTwo.name +" : " +gamePanel.playerTwo.numOfGoals+" --- WINNER :"
                                +gamePanel.winner +" --- IS TIME LIMITED :"+ gamePanel.isGameTimeLimited+" --- IS GOAL LIMITED :"+
                                gamePanel.isGameGoalLimited +" --- IS TWO MARGIN :" + gamePanel.isTwoMargin);
                        endOfGameMassage.setVisible(true);
                        break;

                }
            }
        });
        gameModePanel.setListener(new Listener() {
            @Override
            public void listen(String s) {
                switch (s) {
                    case "GOAL LIMITED":
                        gameModePanel.gameIsGoalLimited(true);
                        gameModePanel.isGameMoodSelected();
                        break;
                    case "TIME LIMITED":
                        gameModePanel.gameIsTimeLimited(true);
                        gameModePanel.isGameMoodSelected();
                        break;
                    case "NOT GOAL LIMITED":
                        gameModePanel.gameIsGoalLimited(false);
                        gameModePanel.isGameMoodSelected();
                        break;
                    case "NOT TIME LIMITED":
                        gameModePanel.gameIsTimeLimited(false);
                        gameModePanel.isGameMoodSelected();
                        break;
                    case "OK":
                        jFrame.setVisible(false);
                        jFrame.remove(gameModePanel);
                        gamePanel.init();
                        gamePanel.getInfoOfGame(gameModePanel.playerOneName, gameModePanel.playerTwoName,
                                gameModePanel.isGameTimeLimited, gameModePanel.isGameGoalLimited,
                                gameModePanel.isGameTwoMargin, gameModePanel.goalsOfGame, gameModePanel.minutesOfGame,
                                gameModePanel.colorOfPlayerOne, gameModePanel.colorOfPlayerTwo);
                        gamePanel.setPureDelay(0);
                        jFrame.add(gamePanel);
                        gamePanel.startGameThread();
                        jFrame.setVisible(true);
                        break;
                }
            }
        });
        gameEntry.setListener(new Listener(){
            @Override
            public void listen(String s) {
                switch (s){
                    case "GAME HISTORY":
                        jFrame.setVisible(false);
                        jFrame.remove(gameEntry);
                        jFrame.add(gameHistory);
                        jFrame.setVisible(true);
                        break;
                    case "CLOSE":
                        System.exit(0);
                        break;
                    case "START GAME":
                        jFrame.setVisible(false);
                        jFrame.remove(gameEntry);
                        jFrame.add(gameModePanel);
                        jFrame.setVisible(true);
                        break;
                }
            }
        });
    }
}
