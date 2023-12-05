
package arkanoid.board;


import arkanoid.ball.GameBall;
import arkanoid.pad.GamePad;
import arkanoid.panel.GamePanel;
import arkanoid.thread.GameThread;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import arkanoid.block.GameBlock;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Board extends javax.swing.JFrame implements ActionListener {

    
    GamePanel panel;
    GameBall ball1;
    GameBall ball2;
    GameBall ball3;
    String nombre;
    String velocidad;
    GameThread gameThread;
    GameThread gameThread2;
    GameThread gameThread3;
    
    private List<GameBall> balls;
    final static int NORMAL = 60;
    final static int SLOW = 15;
    final static int FAST = 5;
    final static int VERY_SLOW = 20; 
    final static int VERY_FAST = 2; 
    private int score = 0;
    private long finalScore;
    
    int nbolas;
    
        
    int speed;
    JLabel timeLabel;
    JLabel scoreLabel;
    
    private boolean isGamePaused = false;
    private long gameTime;  
    private boolean isGameRunning;  
    private long lastUpdateTime ;
    
    public Board() {
        
        this.speed = NORMAL;
        this.panel = new GamePanel();
        panel.setBackground(Color.BLACK);
        //Bloques de juego
        
                                //ALT  IZQ  ALT+PRO ANC  
        GamePad pad = new GamePad(580, 100, 600,    200, Color.WHITE, this);  

        
        //MENU
        JMenuBar menuBar = new JMenuBar();  
        //Menu game
        JMenu gameMenu = new JMenu("Game");
        
        JMenuItem playItem = new JMenuItem("Play");
        gameMenu.add(playItem);
        playItem.setActionCommand("PLAY");
        playItem.addActionListener(this);
        
        JMenuItem pauseItem = new JMenuItem("Pause");
        gameMenu.add(pauseItem);
        pauseItem.setActionCommand("PAUSE");
        pauseItem.addActionListener(this);
        
        JMenuItem restartItem = new JMenuItem("Restart");
        gameMenu.add(restartItem);
        restartItem.setActionCommand("RESTART");
        restartItem.addActionListener(this);
        
        JMenuItem closeItem = new JMenuItem("Close");
        gameMenu.add(closeItem);
        closeItem.setActionCommand("CLOSE");
        closeItem.addActionListener(this);
        
        gameMenu.addSeparator();
        
        menuBar.add(gameMenu);
        
        //Menu options
        
        JMenu optionsMenu = new JMenu("Options");
        //Submenu speed
        JMenu speedMenu = new JMenu("Speed");
        //BOTONES
        ButtonGroup speedBtnGroup = new ButtonGroup();
        
        JRadioButtonMenuItem verySlowItem = new JRadioButtonMenuItem("Very Slow");
        verySlowItem.setActionCommand("VERY_SLOW");
        verySlowItem.addActionListener(this);
        speedBtnGroup.add(verySlowItem);
        speedMenu.add(verySlowItem);
        
        JRadioButtonMenuItem slowItem = new JRadioButtonMenuItem("Slow");
        slowItem.setActionCommand("SLOW");
        slowItem.addActionListener(this);
        speedBtnGroup.add(slowItem);
        speedMenu.add(slowItem);
        
        JRadioButtonMenuItem fastItem = new JRadioButtonMenuItem("Fast");
        fastItem.setActionCommand("FAST");
        fastItem.addActionListener(this);
        speedBtnGroup.add(fastItem);
        speedMenu.add(fastItem);
        
        JRadioButtonMenuItem veryFastItem = new JRadioButtonMenuItem("Very Fast");
        veryFastItem.setActionCommand("VERY_FAST");
        veryFastItem.addActionListener(this);
        speedBtnGroup.add(veryFastItem);
        speedMenu.add(veryFastItem);
        
        optionsMenu.add(speedMenu);
        
        //Submenu balls
        
        JMenu ballsMenu = new JMenu("Balls");
        //BOTONES
        ButtonGroup ballsBtnGroup = new ButtonGroup();
        
        JRadioButtonMenuItem oneBallItem = new JRadioButtonMenuItem("One");
        oneBallItem.setActionCommand("ONE_BALL");
        oneBallItem.addActionListener(this);
        ballsBtnGroup.add(oneBallItem);
        ballsMenu.add(oneBallItem);
        
        JRadioButtonMenuItem twoBallItem = new JRadioButtonMenuItem("Two");
        twoBallItem.setActionCommand("TWO_BALL");
        twoBallItem.addActionListener(this);
        ballsBtnGroup.add(twoBallItem);
        ballsMenu.add(twoBallItem);
        
        JRadioButtonMenuItem threeBallItem = new JRadioButtonMenuItem("Three");
        threeBallItem.setActionCommand("THREE_BALL");
        threeBallItem.addActionListener(this);
        ballsBtnGroup.add(threeBallItem);
        ballsMenu.add(threeBallItem);
        
        optionsMenu.add(ballsMenu);
        
        menuBar.add(optionsMenu);
        
        //Menu records
        JMenu recordsMenu = new JMenu("Records");
        JMenuItem showTimesItem = new JMenuItem("Show Best Times");
        recordsMenu.add(showTimesItem);
        showTimesItem.setActionCommand("SHOW_TIMES");
        showTimesItem.addActionListener(this);
        
        menuBar.add(recordsMenu);
        
        timeLabel = new JLabel(" Time: 0 seconds    "); //Etiqueta para mostrar el tiempo de juego
        scoreLabel = new JLabel (" Score: 0  "); 
        menuBar.add(Box.createHorizontalGlue()); 
        menuBar.add(timeLabel);
        menuBar.add(scoreLabel);
        
        // GAME
        
        
        int rows = 5;
        int columns = 12;
        int blockWidth = 50;
        int blockHeight = 20;   
        int startX = 20;
        int startY = 10;
        int gap = 5;
        
        GameBlock[] block;
        block = new GameBlock[60];
        int i = 0;
        
        
        
        for(int row = 0; row< rows; row++){
            for(int col = 0; col< columns; col++){
                int x = startX + col * (blockWidth + gap);
                int y = startY + row * (blockHeight + gap);

                block[i] = new GameBlock(x, y, blockWidth, blockHeight, Color.RED, this);
                
                panel.add(block[i]);
                i++;
            }
        }
        
        //Cambiar direccion de bolas y aparicion. 
        
        panel.remove(ball1);
        panel.remove(ball2);
        panel.remove(ball3);
        balls = new ArrayList<>();
        ball1 = new GameBall(new Point((550)+20, 150), Color.WHITE, 1, 1, 20, panel, pad, block, this, balls);
        
        balls.add(ball1);
        ball2 = new GameBall(new Point((150)+10, 250), Color.WHITE, 1, 1, 20, panel, pad, block, this, balls);
        
        balls.add(ball2);
        ball3 = new GameBall(new Point((200)+10, 180), Color.WHITE, 1, 1, 20, panel, pad, block, this, balls);
        
        balls.add(ball3);
        
        
        
        
  
        gameThread = new GameThread(ball1, balls, speed, "GameThread");
        gameThread2 = new GameThread(ball2, balls ,speed, "GameThread");
        gameThread3 = new GameThread(ball3, balls ,speed, "GameThread");
        
        
         
        panel.add(pad);
        this.add(panel, BorderLayout.CENTER);
        this.add(menuBar, BorderLayout.NORTH);
        
        this.addKeyListener(new KeyAdapter() {
             @Override
             public void keyPressed(KeyEvent e) 
             {                
                 int keyCode = e.getKeyCode();
                 
                 switch(keyCode)
                 {
                 case 39:
                     pad.moveRight(panel.getGraphics());
                     repaint();
                     break;
                 case 37:
                    pad.moveLeft(panel.getGraphics());
                    repaint();
                     break;
                 case 68:
                     pad.moveRight(panel.getGraphics());
                     repaint();
                     break;
                 case 65: 
                     pad.moveLeft(panel.getGraphics());
                     repaint();
                     break;
                 }                    
             }
         });

    this.setTitle("ARKANOID GAME");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(700, 650);
    this.setResizable(false); 
    
    }
    
    
  @Override
   public void actionPerformed(ActionEvent e) {
    if (this.gameThread != null) {
        System.out.println("Action " + e.getActionCommand());

        switch (e.getActionCommand()) {
            case "PLAY":
                startGame();
                break;
            case "PAUSE":
                pauseGame();
                break;
            case "RESTART":
                restartGame();
                break;
            case "CLOSE":
                closeGame();
                dispose();
                break;
            case "VERY_SLOW":  
                this.speed = VERY_SLOW;
                this.gameThread.setSpeed(VERY_SLOW);
                this.gameThread2.setSpeed(VERY_SLOW);
                this.gameThread3.setSpeed(VERY_SLOW);  
                velocidad="VerySlow";
                break;
            case "SLOW":
                this.speed = SLOW;
                this.gameThread.setSpeed(SLOW);
                this.gameThread2.setSpeed(SLOW);
                this.gameThread3.setSpeed(SLOW);
                velocidad="Slow";
                break;
            case "NORMAL":
                this.speed = NORMAL;
                this.gameThread.setSpeed(NORMAL);
                this.gameThread2.setSpeed(NORMAL);
                this.gameThread3.setSpeed(NORMAL);
                velocidad="Normal";
                break;
            case "FAST":
                this.speed = FAST;
                this.gameThread.setSpeed(FAST);
                this.gameThread2.setSpeed(FAST);
                this.gameThread3.setSpeed(FAST);
                velocidad="Fast";
                break;
            case "VERY_FAST":
                this.speed = VERY_FAST;
                this.gameThread.setSpeed(VERY_FAST);
                this.gameThread2.setSpeed(VERY_FAST);
                this.gameThread3.setSpeed(VERY_FAST);
                velocidad="VeryFast";
                break;
            case "ONE_BALL":
                panel.add(ball1);
                nbolas=1;
                break;
            case "TWO_BALL":
                panel.add(ball1);
                panel.add(ball2);
                nbolas=2;
                break;
            case "THREE_BALL":
                panel.add(ball1);
                panel.add(ball2);
                panel.add(ball3);
                nbolas=3;
                break;
            case "SHOW_TIMES":
                
                break;
                
            
        }
    }
}
   
    private long calculateScore() {
        long currentTime = System.currentTimeMillis();
        gameTime += currentTime - lastUpdateTime;
        lastUpdateTime = currentTime;
        long totalSeconds = gameTime / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        long milliseconds = gameTime % 1000;

        return (long) (6000 * (minutes * 60 + seconds + (double) milliseconds / 1000));
    }
    
    public void registroarchivo() {
        try {
            File file = new File("data\\Records.txt");

            // Leer el archivo y guardar los datos existentes en una lista
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder content = new StringBuilder();

            while ((line = br.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
            br.close();

            // Escribir en el archivo agregando el nuevo registro
            FileWriter escribir = new FileWriter(file, true);
            PrintWriter linea = new PrintWriter(escribir);
            String concatenacion = nombre + ";" + calculateScore() + ";" + nbolas + ";" + velocidad;
            linea.println(concatenacion);
            linea.close();
            escribir.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
        
        
        
    private void initGame(GameThread gt){
        gt.start();
    }
    
    
    private void startGame(){
        updateScoreLabel();
       
        gameTime = 0;
        isGameRunning = true;
        lastUpdateTime = System.currentTimeMillis();
        //Inicializar hilo del cronometro
        Thread timerThread = new Thread(() ->{
            while(isGameRunning){
                updateGameTime(); 
                try{
                    Thread.sleep(100); // Actualizar cada 100 milisegundos
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        timerThread.start();
        //Iniciar los otros hilos del juego
        initGame(gameThread);
        initGame(gameThread2);
        initGame(gameThread3);
    }
    
    public void increaseScore(int points) {
        score += points;
        updateScoreLabel();
        if(score == 6000){
           pauseGameAndShowResult(true);
           finalScore = calculateScore();
           nombre= JOptionPane.showInputDialog(null, "Has ganado! Ingresa tu nombre.");
           //JOptionPane.showMessageDialog(this, "WINNER!!\nYour Score: " + finalScore    , "Update", JOptionPane.INFORMATION_MESSAGE);
           System.exit(0);
           
        }     
    }
    
    private void updateScoreLabel() {
        scoreLabel.setText(" Score: " + score + "  ");
    }
    
    public void pauseGameAndShowResult(boolean win) {
        isGameRunning = false;
        isGamePaused = true;
        
        if(isGameRunning && !isGamePaused){
            long currentTime = System.currentTimeMillis();
            gameTime += currentTime - lastUpdateTime;
            lastUpdateTime = currentTime;
            //Actualizar la visualizacion del tiempo en el frame
            long totalSeconds = gameTime / 1000;
            //long hours = totalSeconds / 3600;
            long minutes = (totalSeconds % 3600) / 60;
            long seconds = totalSeconds % 60;
            long milliseconds = gameTime % 1000;            
            //Cadena en la notacion deseada
            String formattedTime = String.format("%02d:%02d:%02d    ",minutes,seconds,milliseconds);
        }
    }
    
    public void pauseLose(boolean lose){
        gameThread.interrupt();
        gameThread2.interrupt();
        gameThread3.interrupt();
        pauseGameAndShowResult(true);
        JOptionPane.showMessageDialog(this, "¡¡GAME OVER!!", "Update", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
        
    }
    
    public void pauseGame(){
        isGameRunning = false;
        gameThread.interrupt();
        gameThread2.interrupt();
        gameThread3.interrupt();
        
    } 
    private void resume(){
        isGameRunning = true;
    }
    
    private void restartGame(){
        
        gameTime = 0;
    }
    
    private void closeGame(){
        isGameRunning = false;
        System.exit(0);
        // operaciones de cierre  
    }
    

    
    private void updateGameTime(){
        if(isGameRunning && !isGamePaused){
            long currentTime = System.currentTimeMillis();
            gameTime += currentTime - lastUpdateTime;
            lastUpdateTime = currentTime;
            
            //Actualizar la visualizacion del tiempo en el frame
            long totalSeconds = gameTime / 1000;
            //long hours = totalSeconds / 3600;
            long minutes = (totalSeconds % 3600) / 60;
            long seconds = totalSeconds % 60;
            long milliseconds = gameTime % 1000;            
            //Cadena en la notacion deseada
            String formattedTime = String.format("%02d:%02d:%02d    ",minutes,seconds,milliseconds);
            timeLabel.setText(" Time: " + formattedTime);
            
            
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Board.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Board.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Board.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Board.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Board().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}