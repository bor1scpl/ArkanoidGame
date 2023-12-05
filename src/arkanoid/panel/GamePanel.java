/*
BORIS DAVID BELLO DEL RIO: 0222220046
JUAN DAVID NARVAEZ TORRES: 0222220040
DIOGO RODRIGUEZ ACEVEDO : 0222220025


*/
package arkanoid.panel;

import arkanoid.block.GameBlock;
import interfaces.Paintable;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;



public class GamePanel extends JPanel{
    private ArrayList<Paintable> paintables;
    

    public GamePanel() {
        paintables = new ArrayList<>();
      
    }
    
    public void add(Paintable obj){
        this.paintables.add(obj);
    }
    
    public void remove(Paintable obj){
        if(paintables.contains(obj))
            paintables.remove(obj);
    }
    
    public void addBlock(GameBlock block) {
        paintables.add(block);
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Dibujar todos los elementos Paintable
        for (Paintable paintable : paintables) {
            paintable.paint(g2d);
        }
    }
    
    public int getCount(){
        this.paintables.size();
        return 0;
   }
    
}