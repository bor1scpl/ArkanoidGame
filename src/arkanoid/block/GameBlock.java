/*
BORIS DAVID BELLO DEL RIO: 0222220046
JUAN DAVID NARVAEZ TORRES: 0222220040
DIOGO RODRIGUEZ ACEVEDO : 0222220025


*/


package arkanoid.block;


import interfaces.Paintable;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;

public class GameBlock implements Paintable {
    public int x;
    public int y;
    public int width;
    public int height;
    public Color color;
    public Container parent;
    private boolean visible;

    public GameBlock(int x, int y, int width, int height, Color color, Container parent) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.visible = true;
        this.parent = parent;
    }

    @Override
    public void paint(Graphics2D g) {
        if (visible) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
            g.setColor(Color.RED);
            g.drawRect(x, y, width, height);
        }
    }

    public void remove(GameBlock block) {
        visible = false;
    }
        
}
