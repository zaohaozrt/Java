package snack;

import javax.swing.JFrame;


public class Main extends JFrame{

    public Main(){
        super("贪吃蛇   (空格键暂停游戏)");
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocation(FRAME_X, FRAME_Y);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(world);
    }
    public static void main(String[] args) {
        // TODO code application logic here
        new Main();
    }

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int FRAME_X = 200;
    private static final int FRAME_Y = 80;
    private static final World world = new World();
}
