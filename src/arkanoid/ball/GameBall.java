/*
BORIS DAVID BELLO DEL RIO: 0222220046
JUAN DAVID NARVAEZ TORRES: 0222220040
DIOGO RODRIGUEZ ACEVEDO : 0222220025

*/
package arkanoid.ball;

import arkanoid.pad.GamePad;
import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import arkanoid.block.GameBlock;
import arkanoid.board.Board;
import java.util.List;




public class GameBall extends Ball{
    
    public int y;
  
    Board board;
    GameBlock [] block;
    private List<GameBall>balls;
    
    
    GamePad padBottom;
    public GameBall(Point p, Color c, int dx, int dy, int diameter, Container parent, GamePad padBottom, GameBlock[] block, Board board,List<GameBall>balls){
        super(p, c, dx, dy, diameter, parent);
        this.padBottom = padBottom;
        this.block = block;
        this.board = board;
        this.balls = balls;
       
    }
    
    
     
    @Override
    public void move(){
        
        System.out.println("dirx    "+dx);
        System.out.println("diry    "+dy);
        System.out.println("px    "+p.x);
        System.out.println("py    "+p.y);
        System.out.println("padBotton.left      "+padBottom.left);
        System.out.println("padBotton.right     "+padBottom.right);
        
        
        
        if (dy < 0) { // ball moving up
            handleBallMovingUp();
        } else { // ball moving down
            handleBallMovingDown();
        }


        if (dx < 0) { // ball moving left
            handleBallMovingLeft();
        } else { // ball moving right
            handleBallMovingRight();
        }
        
        handleBallCollision();
        p.x += dx;
        p.y += dy;

    }
    
    private void handleBallMovingUp() {
        if (p.y + dy + height < 0) {
            dy = -dy;
        }

        for (int i = 0; i < block.length; i++) {
            handleBlockCollision(i);
        }
        
        
    }

    private void handleBallMovingDown() {
        
        if (p.y + height + dy > padBottom.top && p.x > padBottom.left && p.x < padBottom.right) {
            dy = -dy;
        }

        if (p.y + height + dy > 620) {
        System.out.println("ERROR");
        throw new IllegalStateException();
        }
        
        boolean allBallsBelowBottom = true;
    for (GameBall ball : balls) {
        if (ball.p.y + ball.height + ball.dy < 620) {
            allBallsBelowBottom = false;
            break;
        }
    }

        if (allBallsBelowBottom) {
        // Todas las bolas pasaron el borde inferior, mostrar "Game Over"
        board.pauseLose(false);
        }
    }

    private void handleBallMovingLeft() {
        if (p.x + dx < 0) {
            dx = -dx;
        }

        for (int i = 0; i < block.length; i++) {
            handleBlockCollision(i);
        }
    }

    private void handleBallMovingRight() {
         if (p.x + dx > parent.getWidth() - parent.getInsets().right - width) {
            dx = -dx;
        }

        for (int i = 0; i < block.length; i++) {
            handleBlockCollision(i);
        }
    }

    private void handleBlockCollision(int i) {
        if (block[i] != null && p.y + dy < block[i].y + block[i].height) {
            if (p.x + width + dx > block[i].x - block[i].width / 2 && p.x + dx < block[i].x + block[i].width / 2) {
                dy = -dy;
                block[i].remove(block[i]);
                block[i] = null;
                board.increaseScore(100);
                
                // Verificar si se eliminó el último bloque
                if (checkLastBlock()) {
                    board.pauseGameAndShowResult(true); // Pausar el juego y mostrar mensaje de victoria
                }
                
            }
        }
    }
    
    private boolean checkLastBlock() {
        for (GameBlock b : block) {
            if (b != null) {
            return false; 
            }
        }
        return true; 
    }
    
    public void handleBallCollision() {
        for (GameBall otherBall : balls) {
            if (otherBall != this) {
                double distance = Math.sqrt(Math.pow(this.p.x - otherBall.p.x, 2) + Math.pow(this.p.y - otherBall.p.y, 2));
                if (distance <= this.width / 2 + otherBall.width / 2) {
                    // Las bolas colisionan

                    // Intercambiar velocidades para simular el rebote
                    int tempDx = this.dx;
                    int tempDy = this.dy;
                    this.dx = otherBall.dx;
                    this.dy = otherBall.dy;
                    otherBall.dx = tempDx;
                    otherBall.dy = tempDy;
                }
            }
        }
    }
}