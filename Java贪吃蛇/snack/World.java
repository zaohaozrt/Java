package snack;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import snack.Snack.Node;


public class World extends JPanel implements KeyListener{

    public World(){
        this.setFocusable(true);
        this.requestFocus();
        this.setVisible(true);
        this.addKeyListener(this);
        Random random = new Random();
        int seed = random.nextInt(FOOD_WIDTH/10);
        foodX = 10 * seed;
        seed = random.nextInt(FOOD_HEIGHT/10);
        foodY = 10 * seed ;
        WordThread wt =  new WordThread();
        wt.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (flag == 1 && Snack.speed != 0) {
                        wt.toResume();  //恢复
                    }
                    try {
                        Thread.sleep(1); //不加这个，线程可能不执行
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(X, Y, WIDTH, HEIGHT);
        g.setColor(Color.red);
        g.drawRect(X, Y, WIDTH, HEIGHT);
        g.setColor(Color.green);
        g.fillRect(foodX, foodY, 10, 10);
        g.setColor(Color.white);
        g.fillRect(Snack.list.getFirst().getX(), Snack.list.getFirst().getY(), 10, 10);
        for(Node node : Snack.list){
            g.drawRect(node.getX(), node.getY(), 10, 10);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                if(snack.getSize() > 1 && snack.currentDirector == snack.down)
                    break;
                snack.currentDirector = snack.up;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if(snack.getSize() > 1 && snack.currentDirector == snack.up)
                    break;
                snack.currentDirector = snack.down;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                if(snack.getSize() > 1 && snack.currentDirector == snack.right)
                    break;
                snack.currentDirector = snack.left;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if(snack.getSize() > 1 && snack.currentDirector == snack.left)
                    break;
                snack.currentDirector = snack.right;
                break;
            case KeyEvent.VK_SPACE:
                flag = (++flag)%2;
                if (flag %2== 0){    //暂停
                    suspend = Snack.speed;
                    Snack.speed = 0;
                }else{          //开始
                    Snack.speed = suspend;

                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


    class WordThread extends Thread{
        int speed = 0;
        public synchronized void toResume (){
            notify();
        }
        @Override
        public void run() {
            synchronized(this){
                try {
                    while(true){
                        speed = 200-Snack.speed;
                        if(speed < 50){
                            speed = 50;
                        }
                        if (speed==200){ //暂停
                            wait();
                        }
                        Thread.sleep(speed);
                        if(snack.move()){
                            JOptionPane.showMessageDialog(World.this, "游戏结束");
                            System.exit(0);
                        }
                        repaint();
                    }
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
    private static int flag = -1;
    private static int suspend = 0;
    private static final int X = 0;
    private static final int Y = 0;
    public static final int WIDTH = 780;
    public static final int HEIGHT = 550;
    static final int FOOD_WIDTH = WIDTH-10;
    static final int FOOD_HEIGHT = HEIGHT-10;
    static int foodX;
    static int foodY;
    private static final Snack snack = new Snack();

}
