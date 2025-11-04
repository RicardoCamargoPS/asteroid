import java.applet.Applet;
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
    }


    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
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
