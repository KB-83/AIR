package GameObjects;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayersKeyHandler implements KeyListener {
    public boolean DownPressed,UpPressed,RightPressed,LeftPressed;
    public boolean DownPressed2,UpPressed2,RightPressed2,LeftPressed2;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int input=e.getKeyCode();
        // this is a test for all f**king focusable (s)
//        System.out.println(input);
        if(input == KeyEvent.VK_W){
            this.UpPressed=true;
        }
        if(input == KeyEvent.VK_S){
            this.DownPressed=true;
        }
        if(input == KeyEvent.VK_D){
            this.RightPressed=true;
        }
        if(input == KeyEvent.VK_A){
            this.LeftPressed=true;
        }
        if(input == 38){
            this.UpPressed2=true;
        }
        if(input == 40){
            this.DownPressed2=true;
        }
        if(input == 39){
            this.RightPressed2=true;
        }
        if(input == 37){
            this.LeftPressed2=true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int input = e.getKeyCode();
        if(input == KeyEvent.VK_W){
            this.UpPressed=false;
        }
        if(input == KeyEvent.VK_S){
            this.DownPressed=false;
        }
        if(input == KeyEvent.VK_D){
            this.RightPressed=false;
        }
        if(input == KeyEvent.VK_A){
            this.LeftPressed=false;
        }
        if(input == 38){
            this.UpPressed2=false;
        }
        if(input == 40){
            this.DownPressed2=false;
        }
        if(input == 39){
            this.RightPressed2=false;
        }
        if(input == 37){
            this.LeftPressed2=false;
        }
    }
}
