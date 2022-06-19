package GUI;


import Logic.Objects.ObjectManager;
import Logic.Scenario;
import Logic.Entities.EntityManager;
import Logic.Tiles.TileManager;




import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    public final int tileSize = 8;

    final int maxScreenCol = 120;
    final int maxScreenRow = 80;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;
    final int FPS = 60;
    public Scenario scenario;

    Thread gameThread;

    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int guardsWinState = 2;
    public final int intrudersWinState = 3;

    public TileManager tileM;
    public EntityManager entityM;
    public ObjectManager objectM;
    public UI ui;
    public KeyHandler keyHandler;

    public GamePanel(Scenario scenario){
        this.scenario = scenario;

        this.tileM = new TileManager(this);
        this.entityM = new EntityManager(this);
        this.objectM = new ObjectManager(this);
        this.ui = new UI(this);
        this.keyHandler = new KeyHandler(this);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        gameState = titleState;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void endGameThread(){
        gameThread.interrupt();
    }

    public void resetGamePanel(){
        this.entityM = new EntityManager(this);
        this.objectM = new ObjectManager(this);
        this.ui = new UI(this);
        this.keyHandler = new KeyHandler(this);
    }

    @Override
    public void run() {
        double interval = 1000000000/FPS;
        double nextLoop = System.nanoTime() + interval;

        while (gameThread != null){

            update();

            repaint();

            try{
                double remainTime = nextLoop - System.nanoTime();
                remainTime = remainTime/1000000;

                if(remainTime < 0){
                    remainTime = 0;
                }
                Thread.sleep((long) remainTime);

                nextLoop += interval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(){
        if(gameState == playState){
            entityM.update();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;


        if(gameState == playState){
            tileM.draw(g2);
            entityM.draw(g2);
            objectM.draw(g2);
        }

        ui.draw(g2);

        g2.dispose();
    }

    public int getScreenWidth() {
        return screenWidth;
    }
    public int getScreenHeight() {
        return screenHeight;
    }
    public int getTileSize() {
        return tileSize;
    }
    public int getFPS() {
        return FPS;
    }
}
