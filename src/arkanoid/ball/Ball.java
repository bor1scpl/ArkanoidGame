/*
BORIS DAVID BELLO DEL RIO: 0222220046
JUAN DAVID NARVAEZ TORRES: 0222220040
DIOGO RODRIGUEZ ACEVEDO : 0222220025


*/
package arkanoid.ball;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;



public class Ball extends Shape{    
    Ball(Point p, Color c, int dx, int dy, int diameter, Container parent){
        super(p, c, dx, dy, diameter, diameter, parent);
    }
     
    @Override
    public void paint(Graphics2D g){      
        g.setColor(this.c);
        g.fill(new Ellipse2D.Double(p.x, p.y, width, width));
        g.setColor(Color.RED);
        g.drawOval(p.x, p.y, width, width);
    }
}