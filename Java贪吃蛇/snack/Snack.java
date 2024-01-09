package snack;

import java.util.LinkedList;
import java.util.Random;


public class Snack {
    class Node{
        private int x;
        private int y;
        public Node(){}
        public Node(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
    }


    public Snack(){
        int initX = 200;
        int initY = 100;
        list.add(new Node(initX,initY));
        currentDirector = right;
    }

    public boolean move(){
        int tempX = list.getFirst().getX();
        int tempY = list.getFirst().getY();
        //新的位置
        int moveX = tempX + dir[currentDirector][0]*10;
        int moveY = tempY + dir[currentDirector][1]*10;

        //碰撞检测
        if(tempX == -10 || tempX== World.WIDTH || tempY == -10 || tempY == World.HEIGHT ){
            dead = true;
            return dead;
        }

        for(Node node: list){
            if(node == list.getFirst()) continue;
            if(node.getX() == tempX && node.getY() == tempY){
                dead = true;
                return dead;
            }
        }

        //碰撞到food 加一节 改变food的位置
        if(tempX ==World.foodX && tempY == World.foodY){
            Node newNode = list.getLast();
            grow(newNode);
        }

        //把最后面的删掉  最前面加一个节
        Node head = new Node(moveX,moveY);
        list.removeLast();
        list.addFirst(head);
        return dead;
    }

    private void grow(Node newNode){
        list.addLast(newNode);   //这个无所谓，反正后面得删
        speed += 5;
        Random random = new Random();
        int seed = random.nextInt(World.FOOD_WIDTH/10);
        World.foodX = 10 * seed;
        seed = random.nextInt(World.FOOD_HEIGHT/10);
        World.foodY = 10 * seed ;
    }

    public int getSize(){
        return list.size();
    }

    public static final LinkedList<Node> list = new LinkedList<>();
    public final int up = 0;
    public final int down = 1;
    public final int left = 2;
    public final int right = 3;
    private final int[][] dir = {{0, -1},{0, 1},{-1, 0},{1, 0}};
    public int currentDirector;
    public static int speed = 5;
    public static boolean dead = false;

}
