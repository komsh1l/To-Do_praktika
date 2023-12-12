package com.gamesnake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;




public class GameSnake {
  //game constants
  final String TITLE_OF_PROGRAM = "Classic Game Snake";
  final String GAME_OVER_MSG = "GAME OVER";
  final int POINT_RADIUS = 20; // in pix
  final int FILLED_WIDTH = 30; //in point
  final int FILLED_HEIGHT = 20; // in point
  final int FILLED_DX = 6;
  final int FILLED_DY = 28;
  final int START_LOCATION = 200;
  final int START_SNAKE_SIZE = 6;
  final int START_SNAKE_X = 10;
  final int START_SNAKE_Y = 10;
  final int SHOW_DEALAY = 150;
  final int LEFT = 37;
  final int UP = 38;
  final int RIGHT = 39;
  final int DOWN = 40;
  final int START_DIRECTION = RIGHT;
  final Color DEFAULT_COLOR = Color.blue;
  final Color FOOD_COLOR = Color.green;
  final Color POISON_COLOR = Color.red;
  Snake snake;
  Food food;
  //Poison poison;
  JFrame frame;
  Canvas canvasPanel;
  Random random = new Random();
  boolean gameOver = false;


  public static void main(String[] args) {
    new GameSnake().go();
  }


  class Snake {
    ArrayList<Point> snake = new ArrayList<Point>();
    int direction;

    public Snake(int x, int y, int length, int direction){
      for(int i = 0; i<length; i++){
        Point point = new Point(x-i, y);
        snake.add(point);
      }
      this.direction = direction;
    }
    boolean isInsideSnake(int x, int y){
      for(Point point : snake){
        if((point.getX() == x) && (point.getY() == y)) {
          return true;
        }
      }
      return false;
    }

    boolean isFood (Point food) {
      return ((snake.get(0).getX() == food.getX()) && (snake.get(0).getY() == food.getY()));
    }
    void move(){
      int x = snake.get(0).getX();
      int y = snake.get(0).getY();
      if(direction == LEFT){x--;}
      if(direction == RIGHT){x++;}
      if(direction == UP){y--;}
      if(direction == DOWN){y++;}
      if(x > FILLED_WIDTH - 1){x=0;}
      if(x < 0){x = FILLED_WIDTH-1;}
      if(y > FILLED_WIDTH - 1){y=0;}
      if(y < 0){y = FILLED_HEIGHT-1;}
      gameOver = isInsideSnake(x,y);//check for cross itselves
      snake.add(0, new Point(x,y));
      if(isFood(food)) {
        food.eat();
        score++;


frame.setTitle(TITLE_OF_PROGRAM + " : " + score);
      }else{
        snake.remove(snake.size()-1);
      }
    }

    void setDirection(int direction){
      if ((direction>=LEFT) && (direction<=DOWN)){
        if (Math.abs(this.direction - direction) != 2) {
          this.direction = direction;
        }
      }
    }

    void paint (Graphics g){
      for(Point point : snake) {
        point.paint(g);
      }
    }
  }

  class Food extends Point {

    public Food() {
      super(-1,-1);
      this.color = FOOD_COLOR;
      this.scoreValue = random.nextInt(3) + 1; // random score value
    }

    void eat(){
      this.setXY(-1,-1);
    }

    boolean isEaten() {
      return this.getX() == -1;
    }

    void next() {
      int x, y;
      do {
        x = random.nextInt(FILLED_WIDTH);
        y = random.nextInt(FILLED_HEIGHT);
      }while(snake.isInsideSnake(x,y));
      this.setXY(x,y);

    }

  }

  class Point {
    int x,y;
    Color color = DEFAULT_COLOR;
    int scoreValue = 1; // default score value

    public Point(int x, int y) {
      this.setXY(x, y);
    }
    void paint(Graphics g){
      g.setColor(color);
      g.fillOval(x * POINT_RADIUS, y*POINT_RADIUS, POINT_RADIUS, POINT_RADIUS);
    }

    int getX() {
      return x;
    }
    int getY() {
      return y;
    }

    void setXY(int x, int y){
      this.x = x;
      this.y = y;
    }
  }


  public class Canvas extends JPanel {

    @Override
    public void paint (Graphics q) {
      super.paint(q);
      snake.paint(q);
      food.paint(q);
      if (gameOver) {
        q.setColor(Color.red);
        q.setFont(new Font("Arial", Font.BOLD, 38));
        FontMetrics fm = q.getFontMetrics();
        q.drawString(GAME_OVER_MSG, (FILLED_WIDTH * POINT_RADIUS + FILLED_DX - fm.stringWidth(GAME_OVER_MSG))/2, (FILLED_HEIGHT * POINT_RADIUS + FILLED_DY)/2);
      }
    }

  }
}

   
