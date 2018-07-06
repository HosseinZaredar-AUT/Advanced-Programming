package pack;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

    public GameFrame(String title) {
        super(title);
    }


    public void initBufferStrategy() {
        createBufferStrategy(3);
    }

}
