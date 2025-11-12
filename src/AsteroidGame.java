import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints.Key;
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
    int currentBullet = 0;

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
   
    public void stop(){
        gameLoop = null; 
    }

    private void gameUpdate() {
        updateShip();
        updateBullets();
        updateAsteroids();
        checkCollisions();       
        
    }
    public void updateShip(){
        ship.incX(ship.getVelX());

        if(ship.getX() < -10) 
            ship.setX(getSize().width + 10);
        else if (getX() > getSize().width + 10)
            ship.setX(-10);

        ship.incY(ship.getVelY());
        if(ship.getY() < -10) 
            ship.setY(getSize().height + 10);
        else if (getY() > getSize().height + 10)
            ship.setY(-10);

    }
    public void updateBullets(){
        for(int n = 0; n < BULLETS; n++){
            if(bullet[n].isAlive()){
                bullet[n].incX(bullet[n].getVelX());
                if(bullet[n].getX() < 0 || bullet[n].getX() > getSize().width){
                    bullet[n].setAlive(false);
                }

                bullet[n].incY(bullet[n].getVelY());
                if(bullet[n].getY() < 0 || bullet[n].getY() > getSize().height){
                    bullet[n].setAlive(false);
                }
            }
        }
        
    }

    public void updateAsteroids(){
        for(int n = 0; n < ASTEROIDS; n++){
            if(ast[n].isAlive()){
                ast[n].incX(ast[n].getVelX());
                if(ast[n].getX() < -20) 
                    ast[n].setX(getSize().width + 20);
                else if (ast[n].getX() > getSize().width + 20)
                    ast[n].setX(-20);

                ast[n].incY(ast[n].getVelY());
                if(ast[n].getY() < -20) 
                    ast[n].setY(getSize().height + 20);
                else if (ast[n].getY() > getSize().height + 20)
                    ast[n].setY(-20);

                ast[n].incMoveAngle(ast[n].getRotationVelocity());

                if(ast[n].getMoveAngle() < 0){
                    ast[n].setMoveAngle(360  - ast[n].getRotationVelocity());
                } else if (ast[n].getMoveAngle() > 360){
                    ast[n].setMoveAngle(ast[n].getRotationVelocity());                    
                }
            }
        }
        
    }
    public void checkCollisions(){

        for(int a = 0; a < ASTEROIDS; a++){
            if(ast[a].isAlive()){
                for(int b = 0; b < BULLETS; b++){
                    if(bullet[b].isAlive()){
                        if(ast[a].getBounds().contains(bullet[b].getBounds())){
                            ast[a].setAlive(false);
                            bullet[b].setAlive(false);
                            continue;
                        }
                    }
                }

                if(ast[a].getBounds().intersects(ship.getBounds())){
                    ast[a].setAlive(false);
                    ship.setX(320);
                    ship.setY(240);
                    ship.setFaceAngle(0);
                    ship.setVelX(0);
                    ship.setVelY(0);
                    continue;
                }
            }
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (Key) {
            
            case KeyEvent.VK_LEFT:
                ship.incFaceAngle(-5);
                if(ship.getFaceAngle() < 0){
                    ship.setFaceAngle(360 - 5);
                    
                }
                break;
            case KeyEvent.VK_RIGHT:
                ship.incFaceAngle(5);
                if(ship.getFaceAngle() > 360){
                    ship.setFaceAngle(5);
                    
                }
                break;
            case KeyEvent.VK_UP:
                ship.setMoveAngle(ship.getFaceAngle() - 90);
                ship.incVelX(calcAngleMoveX(ship.getFaceAngle()) * 0.10);
                ship.incVelY(calAngleMoveY(ship.getFaceAngle()) * 0.10);
                break;
            case KeyEvent.VK_CONTROL:
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_SPACE:
                currentBullet++;
                if(currentBullet > BULLETS - 1){
                    currentBullet = 0;
                }
                bullet[currentBullet].setAlive(true);
                bullet[currentBullet].setX(ship.getX());
                bullet[currentBullet].setY(ship.getY());
                bullet[currentBullet].setMoveAngle(ship.getFaceAngle() + 90);
                double ang = bullet[currentBullet].getMoveAngle();
                bullet[currentBullet].setVelX(calcAngleMoveX(ang) * 4);
                bullet[currentBullet].setVelY(calAngleMoveY(ang) * 4);

                currentBullet++;
                
                break;
            

        }

        if(key == KeyEvent.VK_LEFT){
            ship.incFaceAngle(-10);
            if(ship.getFaceAngle() < 0){
                ship.setFaceAngle(360);
            }
        } else if (key == KeyEvent.VK_RIGHT){
            ship.incFaceAngle(10);
            if(ship.getFaceAngle() > 360){
                ship.setFaceAngle(0);
            }
        } else if (key == KeyEvent.VK_UP){
            ship.incVelX(calcAngleMoveX(ship.getFaceAngle() + 90));
            ship.incVelY(calAngleMoveY(ship.getFaceAngle() + 90));
        } else if (key == KeyEvent.VK_SPACE){
            bullet[currentBukke].setAlive(true);
            bullet[currentBukke].setX(ship.getX());
            bullet[currentBukke].setY(ship.getY());
            bullet[currentBukke].setMoveAngle(ship.getFaceAngle() + 90);
            double ang = bullet[currentBukke].getMoveAngle();
            bullet[currentBukke].setVelX(calcAngleMoveX(ang) * 4);
            bullet[currentBukke].setVelY(calAngleMoveY(ang) * 4);

            currentBukke++;
            if(currentBukke >= BULLETS){
                currentBukke = 0;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

}
