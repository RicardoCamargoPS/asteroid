import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

public class AsteroidGame extends Applet  implements Runnable, KeyListener{
    
    Thread gameLoop;
    BufferedImage backbuffer;
    Graphics2D g2d;
    boolean showBounds = false;
    int ASTEROIDS = 20;
    Asteroid[] ast = new Asteroid[ASTEROIDS];

    int BULLETS = 10;
    Bullet[] bullet = new Bullet[BULLETS];
    int currentBukke = 0;

    Ship ship = new Ship();

    AffineTransform identity = new AffineTransform();
    Random rand = new Random();

    public void init(){
        backbuffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        g2d = backbuffer.createGraphics();

        ship.setX(320);
        ship.setY(240);

        for(int n  =0; n < BULLETS; n++){
            bullet[n] = new Bullet();
        }

        for(int n  =0; n < ASTEROIDS; n++){
            ast[n] = new Asteroid();
            ast[n].setRotationVelocity(rand.nextInt(3)+1);
            ast[n].setX(rand.nextInt(600)+ 20);
            ast[n].setY(rand.nextInt(440)+ 20);
            ast[n].setMoveAngle(360);
            double ang =ast[n].getMoveAngle();
            ast[n].setVelX(calcAngleMoveX(ang));
            ast[n].setVelY(calAngleMoveY(ang));

        }

        addKeyListener(this);
    }

    public void updadte(Graphics g){
            g2d.setTransform(identity);
            g2d.setPaint(Color.BLACK);
            g2d.fillRect(0, 0, getSize().width, getSize().height);

            g2d.setColor(Color.WHITE);
            g2d.drawString("Ship: " + Math.round(ship.getX()) + "." + Math.round(ship.getY()), 5, 10);
            g2d.drawString("Move Angle: " + Math.round(ship.getMoveAngle() + 90) , 5, 25);
            g2d.drawString("Face Angle: " + Math.round(ship.getFaceAngle()), 5, 10);

            drawShip();
            drawBullets();
            drawAsteroids();

            paint(g);

    }

    private double calAngleMoveY(double ang) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calAngleMoveY'");
    }

    private double calcAngleMoveX(double ang) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calcAngleMoveX'");
    }


    private void drawAsteroids() {
        for(int n = 0; n<ASTEROIDS; n++){
            if(ast[n].isAlive()){
                g2d.setTransform(identity);
                g2d.translate(ast[n].getX(), ast[n].getY());
                g2d.rotate(Math.toRadians(ast[n].getMoveAngle()));
                g2d.setColor(Color.DARK_GRAY);
                g2d.fill(ast[n].getShape());
            }
        }
    }

    private void drawBullets() {

        for(int n = 0; n < BULLETS; n++){
            if(bullet[n].isAlive()){
                g2d.setTransform(identity);
                g2d.translate(bullet[n].getX(), bullet[n].getY());
                g2d.fillRect(0, 0, getSize().width, getSize().height);
                g2d.setColor(Color.MAGENTA);
                g2d.fill(bullet[n].getShape());
            }
        }
       
    }

    private void drawShip() {
        g2d.setTransform(identity);
        g2d.translate(ship.getX(), ship.getY());   
        g2d.rotate(Math.toRadians(ship.getFaceAngle())); 
        g2d.setColor(Color.ORANGE);
        g2d.fill(ship.getShape());
    }

    public void paint(Graphics g){
        g.drawImage(backbuffer, 0, 0, this);
    }

    public void start(){
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        while (t == gameLoop) {

            try {
                gameUpdate();
                Thread.sleep(20);
                
            } catch (Exception e) {
                e.printStackTrace();
            }

            repaint();       
            
        }
    }
    private void gameUpdate() {
        updateShip();
        updateBullets();
        updateAsteroids();
        checkCollisions();
        
    }

    public void stop(){
        gameLoop = null; 
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

}
