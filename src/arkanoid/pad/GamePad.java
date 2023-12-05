/*
BORIS DAVID BELLO DEL RIO: 0222220046
JUAN DAVID NARVAEZ TORRES: 0222220040
DIOGO RODRIGUEZ ACEVEDO : 0222220025


*/
package arkanoid.pad;

import interfaces.Paintable;
import java.awt.Color;
import java.awt.Container;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePad implements Paintable{
    public int top;
    public int left;
    public int bottom;
    public int right;
    Color clr;
    Container parent;    
     
    final int dx = 12;
     
    public GamePad(int top, int left, int bottom, int right, Color clr, Container parent){
        super();
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.clr = clr;
        this.parent = parent;
    }
 
    public void moveLeft(Graphics g1){
        if(left - dx < 0)
            return;
        left -= dx;
        right -= dx;
        
        System.out.println(left + " " + right);
        Graphics2D g = (Graphics2D) g1;
        paint(g);
    }
     
    public void moveRight(Graphics g1){
        if(right + dx > parent.getWidth())
            return;
        left += dx;
        right += dx;
        System.out.println(left + " " + right);
        Graphics2D g = (Graphics2D) g1;
        paint(g);
    }

    @Override
    public void paint(Graphics2D g){
        //Dibujar la paleta
        GradientPaint gradient = new GradientPaint(left, top, Color.RED, right, bottom, clr);
        g.setPaint(gradient);
        //Rellenar la paleta con el gradiente
        g.fillRoundRect(left, top, right-left, bottom-top, 40, 8);
        g.setColor(this.clr);
        g.setColor(Color.WHITE);
        g.drawRoundRect(left, top, right-left, bottom-top, 40, 8);         
    }
}