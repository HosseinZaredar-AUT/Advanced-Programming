package pack.sound;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExampleSounds {
   public static ExecutorService executorService;


    public ExampleSounds() {
        executorService = Executors.newFixedThreadPool(20);
    }

    public static void playagree() {
        executorService.execute(new Sound("res/Sounds/agree.wav"));
    }

    public static void playcannon() {
        executorService.execute(new Sound("res/Sounds/cannon.wav"));
    }

    public static void playemptyGun() {
        executorService.execute(new Sound("res/Sounds/emptyGun.wav"));
    }

    public static void playendOfGame() {
        executorService.execute(new Sound("res/Sounds/endOfGame.wav"));
    }

    public static void playEnemyBulletToMyTank() {
        executorService.execute(new Sound("res/Sounds/EnemyBulletToMyTank.wav"));
    }

    public static void playenemydestroyed() {
        executorService.execute(new Sound("res/Sounds/enemydestroyed.wav"));
    }

    public static void playenemyshot() {
        executorService.execute(new Sound("res/Sounds/enemyshot.wav"));
    }

    public static void playgameOver() {
        executorService.execute(new Sound("res/Sounds/gameOver.wav"));
    }

    public static void playheavygun() {
        executorService.execute(new Sound("res/Sounds/heavygun.wav"));
    }

    public static void playgameSound1() {
        executorService.execute(new Sound("res/Sounds/gameSound1.wav"));
    }

    public static void playgameSound2() {
        executorService.execute(new Sound("res/Sounds/gameSound2.wav"));
    }

    public static void playlightgun() {
        executorService.execute(new Sound("res/Sounds/lightgun.wav"));
    }

    public static void playmashingun() {
        executorService.execute(new Sound("res/Sounds/mashingun.wav"));
    }

    public static void playmine() {
        executorService.execute(new Sound("res/Sounds/mine.wav"));
    }

    public static void playMineBoom() {
        executorService.execute(new Sound("res/Sounds/MineBoom.wav"));
    }

    public static void playmotor1() {
        executorService.execute(new Sound("res/Sounds/motor1.wav"));
    }

    public static void playrecosh() {
        executorService.execute(new Sound("res/Sounds/recosh.wav"));
    }

    public static void playrepair() {
        executorService.execute(new Sound("res/Sounds/repair.wav"));
    }

    public static void playselect() {
        executorService.execute(new Sound("res/Sounds/select.wav"));
    }

    public static void playsoftwall() {
        executorService.execute(new Sound("res/Sounds/softwall.wav"));
    }

    public static void playstartup() {
        executorService.execute(new Sound("res/Sounds/startup.wav"));
    }


}
