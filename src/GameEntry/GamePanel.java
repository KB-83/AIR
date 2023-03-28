package GameEntry;

import GameObjects.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends MainGameFrame implements Runnable {
    Ball ball;
    //player one is upper circle
    // min y and max y is a test
    public Player playerOne;
    public Player playerTwo;
    Gift gift ;
    Goal goalOfPlayerOne;
    Goal goalOfPlayerTwo;
    JButton stopGameAndClose;
    public String winner;
    public boolean isGameTimeLimited;
    public boolean isGameGoalLimited;
    public boolean isTwoMargin;
    int goalsOfGame;
    int fbs=1000000000/60;
    PlayersKeyHandler keyH=new PlayersKeyHandler();
    Listener listener;
    BufferedImage bgImage = ImageIO.read(new File("/Users/kajal/Documents/AP/AIR/pict.png"));
    Thread threadGame;
    //time handling in seconds
    public int remainingTimeOfTheGame;
    int timeOfEndOfGame;
    int timeOfGame;
    int timeOfDelay=0;
    // gifts
    long passesTimeAfterGiftCreated;
    long passesTimeAfterGiftEnded;

    public GamePanel(Color color) throws IOException {
        super(color);
        stopGameAndClose=new JButton();
        stopGameAndClose.setBounds(0,0,50,50);
        stopGameAndClose.setBackground(Color.MAGENTA);
        stopGameAndClose.setForeground(Color.BLACK);
        stopGameAndClose.setFocusable(false);
        stopGameAndClose.addActionListener(this);
        stopGameAndClose.setVisible(true);
        this.add(stopGameAndClose);
        init();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        System.out.println(e.getSource());
        if(e.getSource() == stopGameAndClose){
            //check theread of game
            listener.listen("STOP");
            threadGame=null;
            init();
        }
    }
    public void startGameThread(){
        threadGame=new Thread(this);
        if(isGameTimeLimited) {
            timeOfEndOfGame = (int) (System.currentTimeMillis() / 1000 + timeOfGame);
        }
        threadGame.start();
    }
    @Override
    public void run() {
        //threadGame != null
        while (threadGame != null){
            try {
                if (checkIfGameIsOver()){
                    threadGame=null;
//                    JOptionPane.showMessageDialog(this,winner+" Wins");
                    listener.listen("END");
                    init();
                    break;
                }
                long currentTime=System.nanoTime();
                update();
                repaint();
                long distanceTime=System.nanoTime()-currentTime;
//                long ThreadTime = fbs-(distanceTime)/1000000;
                if((fbs-(distanceTime))/1000000 >  0){
                Thread.sleep((fbs-(distanceTime))/1000000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void update() throws InterruptedException {
        updatePlayersState();
        updateBallState();
//        updateGoalsState();
        if(isGameTimeLimited){
            updateTimeState();
        }
//        updateNumOfGoalsState();
        updateGiftState();
    }
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2= (Graphics2D) g;
        g2.drawImage(bgImage, 0, 0, null);
        g2.setColor(Color.MAGENTA);
        //masmali stop button
        g2.fillRect(0,0,50,50);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("monospaced", Font.BOLD, 20));
        g2.drawString("||",15,30);
        drawGiftState(g2);
        drawPlayersState(g2);
        drawBallState(g2);
        drawGoalsState(g2);
        if(isGameTimeLimited){
            drawTimeState(g2);
        }
        drawNumOfGoalsState(g2);
        g2.dispose();
    }
    public void updatePlayersState(){
        if(keyH.UpPressed){
            if(playerOne.y<=playerOne.minY){
                playerOne.y= playerOne.minY;
            }
            else {
                playerOne.y-=playerOne.v;
            }
        }
        if(keyH.DownPressed){
            if(playerOne.y>=playerOne.maxY){
                playerOne.y= playerOne.maxY;
            }
            else {
                playerOne.y+=playerOne.v;
            }
        }
        if(keyH.RightPressed){
            if(playerOne.x>=playerOne.maxX){
                playerOne.x= playerOne.maxX;
            }
            else {
                playerOne.x += playerOne.v;
            }
        }
        if(keyH.LeftPressed){
            if(playerOne.x<=playerOne.minX){
                playerOne.x= playerOne.minX;
            }
            else {
                playerOne.x -= playerOne.v;
            }
        }
        if(keyH.UpPressed2){
            if(playerTwo.y<=playerTwo.minY){
                playerTwo.y= playerTwo.minY;
            }
            else {
                playerTwo.y-=playerTwo.v;
            }
        }
        if(keyH.DownPressed2) {
            if (playerTwo.y >= playerTwo.maxY) {
                playerTwo.y = playerTwo.maxY;
            } else {
                playerTwo.y += playerTwo.v;
            }
        }
        if (keyH.RightPressed2) {
            if (playerTwo.x >= playerTwo.maxX) {
                playerTwo.x = playerTwo.maxX;
            } else {
                playerTwo.x += playerTwo.v;
            }
        }
        if (keyH.LeftPressed2) {
            if (playerTwo.x <= playerTwo.minX) {
                playerTwo.x = playerTwo.minX;
            } else {
                playerTwo.x -= playerTwo.v;
            }
        }
    }
    public void drawPlayersState(Graphics2D g2){
        g2.setColor(playerOne.color);
        g2.fillOval(playerOne.x, playerOne.y, playerOne.radius*2,playerOne.radius*2);
        g2.setColor(playerTwo.color);
        g2.fillOval(playerTwo.x, playerTwo.y, playerTwo.radius*2, playerTwo.radius*2);
    }
    public void setListener(Listener listener) {
        this.listener = listener;
    }
    public void updateBallState() throws InterruptedException {
        checkIfGoalScored();
        int a=ball.x+ball.radius;
        int b=ball.y+ball.radius;
        int aPlayerOne=playerOne.x+playerOne.radius;
        int bPlayerOne=playerOne.y+playerOne.radius;
        int aPlayerTwo=playerTwo.x+playerTwo.radius;
        int bPlayerTwo=playerTwo.y+playerTwo.radius;
        boolean isMirrorWallActivated = false;
        boolean isPlayerOneHaveFireBall = false;
        boolean isPlayerTwoHaveFireBall = false;
        //checking gifts state
        for(int i =0;i<playerOne.activeGifts.size();i++){
            if(playerOne.activeGifts.get(i).isFireBall){
                isPlayerOneHaveFireBall = true;
            }
            if(playerOne.activeGifts.get(i).isMirrorWall){
                isMirrorWallActivated = true;
            }
        }
        for(int i =0;i<playerTwo.activeGifts.size();i++){
            if(playerTwo.activeGifts.get(i).isFireBall){
                isPlayerTwoHaveFireBall = true;
            }
            if(playerTwo.activeGifts.get(i).isMirrorWall){
                isMirrorWallActivated = true;
            }
        }
        //state of ball with player one
        // i added >=0 to all parameters maybe need to be just >0

        if(distance(a,b,aPlayerOne,bPlayerOne)<=ball.radius+playerOne.radius){
            //added for gifts
            ball.lastTouch = playerOne;
            //handling fireball
            if(isPlayerTwoHaveFireBall){}
            else{
            int deltaBDivideDeltaA=0;
            if(b-bPlayerOne == 0 || a-aPlayerOne == 0){
                if(aPlayerOne == a){
                    if (b>bPlayerOne) {
                        ball.yOfV=-ball.measureOfV;
                        ball.y = bPlayerOne + playerOne.radius;
                    }
                    if (b<bPlayerOne){
                        ball.yOfV=ball.measureOfV;
                        ball.y = bPlayerOne - playerOne.radius - 2*ball.radius;
                    }
                    ball.yOfV=-ball.yOfV;
                }
                if(bPlayerOne == b){
                    if(a>aPlayerOne){
                        ball.x=aPlayerOne + playerOne.radius;
                        ball.xOfV=+ball.xOfV;
                    }
                    if(a<aPlayerOne){
                        ball.xOfV=-ball.xOfV;
                        ball.x=aPlayerOne - playerOne.radius-2*ball.radius;
                    }
                    ball.xOfV=-ball.xOfV;
                }
            }
            else {
            deltaBDivideDeltaA=Math.abs((b-bPlayerOne)/(a-aPlayerOne));
            int measureOfV=ball.measureOfV;
            int measureOfXOfV= (int) (measureOfV/Math.sqrt(1+Math.pow(deltaBDivideDeltaA,2)));
            int measureOfYOfV= (int) Math.sqrt(Math.pow(measureOfV,2)-Math.pow(measureOfXOfV,2));
            if(a-aPlayerOne>=0){
                if(b-bPlayerOne>=0){
                    ball.xOfV=measureOfXOfV;
                    ball.yOfV=measureOfYOfV;
                }
                if(b-bPlayerOne<=0){
                    ball.xOfV=measureOfXOfV;
                    ball.yOfV=-measureOfYOfV;
                }
            }
            if(a-aPlayerOne<=0){
                if(b-bPlayerOne>=0){
                    ball.xOfV=-measureOfXOfV;
                    ball.yOfV=measureOfYOfV;
                }
                if(b-bPlayerOne<=0){
                    ball.xOfV=-measureOfXOfV;
                    ball.yOfV=-measureOfYOfV;
                }
            }
        }
            }
    }
        //state of ball with player two
        if(distance(a,b,aPlayerTwo,bPlayerTwo)<=ball.radius+playerTwo.radius){
            ball.lastTouch=playerTwo;
            if(isPlayerOneHaveFireBall){}
            else{
            int deltaBDivideDeltaA=0;
            if(b-bPlayerTwo == 0 || a-aPlayerTwo == 0){
                if(aPlayerTwo == a){
                    if (b>bPlayerTwo) {
                        ball.yOfV=-ball.measureOfV;
                        ball.y = bPlayerTwo + playerTwo.radius;
                    }
                    if (b<bPlayerTwo){
                        ball.yOfV=ball.measureOfV;
                        ball.y = bPlayerTwo - playerTwo.radius - 2*ball.radius;
                    }
                    ball.yOfV=-ball.yOfV;
                }
                if(bPlayerTwo == b){
                    if(a>aPlayerTwo){
                        ball.x=aPlayerTwo + playerTwo.radius;
                        ball.xOfV=+ball.xOfV;
                    }
                    if(a<aPlayerTwo){
                        ball.xOfV=-ball.xOfV;
                        ball.x=aPlayerTwo - playerTwo.radius-2*ball.radius;
                    }
                    ball.xOfV=-ball.xOfV;
                }
            }
            else {
                deltaBDivideDeltaA=Math.abs((b-bPlayerTwo)/(a-aPlayerTwo));
            int measureOfV=ball.measureOfV;
            int measureOfXOfV= (int) (measureOfV/Math.sqrt(1+Math.pow(deltaBDivideDeltaA,2)));
            int measureOfYOfV= (int) Math.sqrt(Math.pow(measureOfV,2)-Math.pow(measureOfXOfV,2));
            if(a-aPlayerTwo>=0){
                if(b-bPlayerTwo>=0){
                    ball.xOfV=measureOfXOfV;
                    ball.yOfV=measureOfYOfV;
                }
                if(b-bPlayerTwo<=0){
                    ball.xOfV=measureOfXOfV;
                    ball.yOfV=-measureOfYOfV;
                }
            }
            if(a-aPlayerTwo<=0){
                if(b-bPlayerTwo>=0){
                    ball.xOfV=-measureOfXOfV;
                    ball.yOfV=measureOfYOfV;
                }
                if(b-bPlayerTwo<=0){
                    ball.xOfV=-measureOfXOfV;
                    ball.yOfV=-measureOfYOfV;
                }
            }
        }
            }
        }
        //state of ball with wall
        // mirror wall handled
        if(isMirrorWallActivated){
            if(ball.x > ball.maxX){
                ball.x=ball.minX;
            }
            if(ball.x < ball.minX){
                ball.x=ball.maxX;
            }
            if(ball.y >= ball.maxY || ball.y <= ball.minY){
                ball.yOfV=-ball.yOfV;
            }
        }
        else {
        if(ball.x >= ball.maxX || ball.x <= ball.minX){
            ball.xOfV=-ball.xOfV;
        }
        if(ball.y >= ball.maxY || ball.y <= ball.minY){
            ball.yOfV=-ball.yOfV;
        }
        }
        ball.x+=ball.xOfV;
        ball.y+=ball.yOfV;
        //state of ball with gift
        if (distance(a,b,gift.x+gift.radius,gift.y+gift.radius)<=gift.radius+ball.radius){
            if(gift.isVisible && gift.isActive == false){
                activeGift();
            }
        }
    }
    public void checkIfGoalScored() throws InterruptedException {
        //player one scored a goal
        //change ball x and y
        if(ball.x > goalOfPlayerTwo.x && ball.x < goalOfPlayerTwo.x+goalOfPlayerTwo.width
        && ball.y>goalOfPlayerTwo.y){
            playerOne.numOfGoals++;
            disActiveGifts();
            Ball newBall=new Ball();
            newBall.xOfV=0;
            newBall.yOfV=0;
            newBall.x =200 - (newBall.radius);
            newBall.y=375+100;
            ball=newBall;
            ball.lastTouch = playerTwo;
//            playerOne=new Player(200-20,50+30+5,400-(20*2),375-(20*2),0,50+30);
//            playerTwo=new Player(200-20,700-(30+5+(2*20)),400-40,700-(20*2)-30,0,375);
            playerOne.x=200-20;
            playerOne.y=50+30+5;
            playerTwo.x=200-20;
            playerTwo.y = 700-(30+5+(2*20));
            //class kar;)
            threadGame.sleep(300);
        }
        // player two scored a goal
        else if(ball.x > goalOfPlayerOne.x && ball.x < goalOfPlayerOne.x+goalOfPlayerOne.width
                && ball.y<goalOfPlayerOne.y+goalOfPlayerOne.height){
            playerTwo.numOfGoals++;
            disActiveGifts();
            Ball newBall = new Ball();
            newBall.xOfV=0;
            newBall.yOfV=0;
            newBall.x= 200 -(newBall.radius);
            newBall.y= 50+225 -(newBall.radius * 2);
            ball=newBall;
            ball.lastTouch = playerOne;
            playerOne.x=200-20;
            playerOne.y=50+30+5;
            playerTwo.x=200-20;
            playerTwo.y = 700-(30+5+(2*20));
            threadGame.sleep(300);
        }
    }
    public void drawBallState(Graphics2D g2){
        g2.setColor(ball.color);
        g2.fillOval(ball.x,ball.y,ball.radius*2,ball.radius*2);
    }
    public void updateTimeState(){
        remainingTimeOfTheGame= (int) (timeOfEndOfGame-(System.currentTimeMillis()/1000)+(timeOfDelay));
    }
    public void drawTimeState(Graphics2D g2){
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("monospaced", Font.BOLD, 36));
        g2.drawString(String.valueOf(remainingTimeOfTheGame),50+30,30);
    }
    public void drawNumOfGoalsState(Graphics2D g2){
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("monospaced", Font.BOLD, 12));
        g2.drawString(playerOne.name+" : "+playerOne.numOfGoals+"   "+playerTwo.name+" : "+playerTwo.numOfGoals,170,30);
    }
    //you have to change it with gifts
    public void drawGoalsState(Graphics2D g2){
        g2.setColor(goalOfPlayerOne.color);
        g2.fillRect(goalOfPlayerOne.x,goalOfPlayerOne.y,goalOfPlayerOne.width,goalOfPlayerOne.height);
        g2.setColor(goalOfPlayerTwo.color);
        g2.fillRect(goalOfPlayerTwo.x,goalOfPlayerTwo.y,goalOfPlayerTwo.width,goalOfPlayerTwo.height);
    }
    public void updateGiftState(){
        if(gift.isEndOfThisGift){
        passesTimeAfterGiftEnded = System.currentTimeMillis()-gift.startOfEnd;
        if(passesTimeAfterGiftEnded>=10000){
            gift = new Gift();
//            gift.startOfCreated = System.currentTimeMillis();
        }
        }
        else {
        passesTimeAfterGiftCreated = System.currentTimeMillis()-gift.startOfCreated;
        if (passesTimeAfterGiftCreated>=5000 && gift.numOfGettingBigger==0){
            gift.radius+=gift.gettingBiggerRate;
            gift.numOfGettingBigger = 1;
        }
        else if(passesTimeAfterGiftCreated >= 10000 && gift.numOfGettingBigger==1){
            gift.radius+=gift.gettingBiggerRate;
            gift.numOfGettingBigger = 2;
        }
        else if(passesTimeAfterGiftCreated>=15000 && gift.numOfGettingBigger==2){
            gift.numOfGettingBigger=4;
            gift.isEndOfThisGift=true;
            gift.isVisible=false;
            gift.startOfEnd= System.currentTimeMillis();
        }
        }
    }
    public void drawGiftState(Graphics2D g2){
        if (gift != null && gift.isEndOfThisGift==false) {
            g2.setColor(gift.color);
            g2.fillOval(gift.x,gift.y, gift.radius*2, gift.radius*2);
        }
    }
    public boolean checkIfGameIsOver(){
        if(isGameGoalLimited) {
            if (isTwoMargin){
                if (playerOne.numOfGoals >= goalsOfGame && playerOne.numOfGoals-2>=playerTwo.numOfGoals) {
                    winner = playerOne.name;
                    return true;
                }
                if (playerTwo.numOfGoals >= goalsOfGame && playerTwo.numOfGoals -2>= playerOne.numOfGoals) {
                    winner = playerTwo.name;
                    return true;
                }
            }
            else {
                if (playerOne.numOfGoals >= goalsOfGame) {
                winner = playerOne.name;
                return true;
            }
            if (playerTwo.numOfGoals >= goalsOfGame) {
                winner = playerTwo.name;
                return true;
            }
        }
        }
        if(isGameTimeLimited){
            if(remainingTimeOfTheGame<=0){
                if(playerOne.numOfGoals>playerTwo.numOfGoals){
                    winner=playerOne.name;
                    return true;
                }
                else if(playerTwo.numOfGoals>playerOne.numOfGoals){
                    winner = playerTwo.name;
                    return true;
                }
            }
        }
        return false;
    }
    public void getInfoOfGame(String playerOneName,String playerTwoName,boolean isGameTimeLimited,
                              boolean isGameGoalLimited,boolean isGameTwoMargin,
                              int goalsOfGame,int minutesOfGame,
                              Color colorOfPlayerOne,Color colorOfPlayerTwo){
        playerOne.name = playerOneName;
        playerOne.color=colorOfPlayerOne;
        playerTwo.name = playerTwoName;
        playerTwo.color=colorOfPlayerTwo;
        this.isGameTimeLimited = isGameTimeLimited;
        this.isGameGoalLimited = isGameGoalLimited;
        if(isGameGoalLimited){
            this.isTwoMargin = isGameTwoMargin;
            this.goalsOfGame = goalsOfGame ;
        }
        if(isGameTimeLimited){
            this.timeOfGame = minutesOfGame * 60;

        }
    }
    public double distance(double a, double b , double A , double B){
        return Math.sqrt(Math.pow((a-A),2)+Math.pow((b-B),2));
    }
    public void activeGift(){
        gift.owner=ball.lastTouch;
        gift.owner.activeGifts.add(gift);
        //extra handel is in ballStateUpdate for two first type of gifts
        if(gift.isFireBall){
            System.out.println("Fire Ball");
            System.out.println(ball.measureOfV);
            ball.measureOfV = ball.measureOfV*2;
            ball.xOfV*=2;
            ball.yOfV*=2;
            ball.color = Color.red;
            System.out.println(ball.measureOfV);
            gift.isActive = true;
            gift.isVisible=false;
            gift.isEndOfThisGift=true;
            gift.startOfEnd = System.currentTimeMillis();
        }
        else if(gift.isMirrorWall){
            System.out.println("Mirror Wall");
            gift.isActive = true;
            gift.isVisible=false;
            gift.isEndOfThisGift=true;
            gift.startOfEnd = System.currentTimeMillis();
        }
        else if(gift.isBiggerGoal){
            System.out.println("Bigger Goal");
            if(gift.owner == playerOne){
                goalOfPlayerTwo.x=100;
                goalOfPlayerTwo.width =200;
            }
            else {
                goalOfPlayerOne.x=100;
                goalOfPlayerOne.width =200;
            }
            gift.isActive = true;
            gift.isVisible=false;
            gift.isEndOfThisGift=true;
            gift.startOfEnd = System.currentTimeMillis();
        }
    }
    public void disActiveGifts(){
        for (int i=0; i< playerOne.activeGifts.size();i++) {
            //extra handel is in ballStateUpdate for two first type of gifts
            if (playerOne.activeGifts.get(i).isFireBall) {
                playerOne.activeGifts.get(i).isActive = false;
            } else if (playerOne.activeGifts.get(i).isMirrorWall) {
                playerOne.activeGifts.get(i).isActive = false;
            } else if (playerOne.activeGifts.get(i).isBiggerGoal) {
                goalOfPlayerTwo = new Goal((400-100)/2,700-(30));
            }
                playerOne.activeGifts.get(i).isActive = false;
            }
//            playerOne.activeGifts.remove(gift);
        for (int i=0; i< playerTwo.activeGifts.size();i++) {
            //extra handel is in ballStateUpdate for two first type of gifts
            if (playerTwo.activeGifts.get(i).isFireBall) {
                playerTwo.activeGifts.get(i).isActive = false;
            } else if (playerTwo.activeGifts.get(i).isMirrorWall) {
                playerTwo.activeGifts.get(i).isActive = false;
            } else if (playerTwo.activeGifts.get(i).isBiggerGoal) {
                goalOfPlayerOne = new Goal((400-100)/2,50);
                }
                playerTwo.activeGifts.get(i).isActive = false;
            }
//            playerTwo.activeGifts.remove(gift);
        playerOne.activeGifts=new ArrayList<>();
        playerTwo.activeGifts=new ArrayList<>();
    }
    public void init(){
        ball=new Ball();
//        System.out.println(ball.measureOfV);
        playerOne=new Player(200-20,50+30+5,400-(20*2),375-(20*2),0,50+30);
        playerTwo=new Player(200-20,700-(30+5+(2*20)),400-40,700-(20*2)-30,0,375);
        goalOfPlayerOne = new Goal((400-100)/2,50);
        goalOfPlayerTwo = new Goal((400-100)/2,700-(30));
        winner="no one";
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.setLayout(null);
        passesTimeAfterGiftCreated = 0;
        passesTimeAfterGiftEnded = 0;
        gift=new Gift();
        gift.isEndOfThisGift=true;
        gift.isActive=false;
        gift.isVisible=false;
        gift.startOfEnd=System.currentTimeMillis();
        // preventing a bug
        ball.lastTouch=playerTwo;
    }
    // deap copy for pause button
    public void transferInfo(GamePanel gamePanel){
        gamePanel.ball = this.ball;
        gamePanel.playerOne = this.playerOne;
        gamePanel.playerTwo = this.playerTwo;
        gamePanel.gift = this.gift;
        gamePanel.goalOfPlayerOne = this.goalOfPlayerOne;
        gamePanel.goalOfPlayerTwo = this.goalOfPlayerTwo;
//        gamePanel.stopGameAndClose = this.stopGameAndClose;
        gamePanel.winner=this.winner;
        gamePanel.isGameTimeLimited = this.isGameTimeLimited;
        gamePanel.isGameGoalLimited = this.isGameGoalLimited;
        gamePanel.isTwoMargin = this.isTwoMargin;
        gamePanel.goalsOfGame = this.goalsOfGame;
//        PlayersKeyHandler keyH=new PlayersKeyHandler();
        gamePanel.listener = this.listener;
//        BufferedImage bgImage = ImageIO.read(new File("C:\\Users\\kajal\\Desktop\\AP\\2\\AirHokey\\pict.png"));
        //imppp
//        Thread threadGame;
        // time handling
        gamePanel.remainingTimeOfTheGame = this.remainingTimeOfTheGame;
        gamePanel.timeOfEndOfGame = this.timeOfEndOfGame;
        gamePanel.timeOfGame = this.timeOfGame;
        // gifts
        gamePanel.passesTimeAfterGiftCreated = this.passesTimeAfterGiftCreated;
        gamePanel.passesTimeAfterGiftEnded = this.passesTimeAfterGiftEnded;
        gamePanel.timeOfDelay=this.timeOfDelay;
    }
    public void setDelay(int delay){
        this.timeOfDelay+=delay;
    }

    public void setPureDelay(int i) {
        this.timeOfDelay=i;
    }
}
