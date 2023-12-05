/*
BORIS DAVID BELLO DEL RIO: 0222220046
JUAN DAVID NARVAEZ TORRES: 0222220040
DIOGO RODRIGUEZ ACEVEDO : 0222220025


*/
package arkanoid.thread;

import arkanoid.ball.GameBall;
import arkanoid.ball.Shape;
import java.util.List;

public class GameThread extends Thread{
    Shape shape;
    private List<GameBall> balls;
    int speed;
    private boolean isPaused = false;
 
    public GameThread(Shape shape, List<GameBall> balls, int speed, String name){
        super(name);
        this.shape = shape;
        this.balls= balls;
        this.speed = speed;
    }

    public GameThread(Shape shape, int speed, String string) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
 
    public void setSpeed(int speed){
        this.speed = speed;
    }
    
    public void pauseThread(){
        isPaused = true;
    }
    
    public void resumeThread(){
        isPaused = false;
        notify();
    }
     
    @Override
    public void run(){
        try{
            while(!isInterrupted()){
                if(balls!=null){
                //
                System.out.println("ANTES DEL MOV");
                shape.parent.repaint();
                shape.move();}
                
                System.out.println(this.getName());
                try{
                    Thread.sleep(speed);
                }
                catch(InterruptedException e){
                    System.out.println(e);
                    interrupt();
                }
            }
        }
        catch(IllegalStateException excp){            
        }
    }
} 