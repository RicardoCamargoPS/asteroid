import java.awt.Shape;

public class BaseVectorShape {

    private Shape shape;
    private boolean alive;
    private double x, y;
    private double velX, velY;
    private double moveAngle, faceAngle;

    
    public Shape getShape() {return shape;}
    public boolean isAlive(){return alive;}
    public double getX() {return x;}
    public double getY() {return y;}
    public double getVelX() {return velX;}
    public double getVelY() {return velY;}

    

    public void setShape(Shape shape) {this.shape = shape;}
    public void setAlive(boolean alive) {this.alive = alive;}
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
    public void setVelX(double velX) {this.velX = velX;}
    public void setVelY(double velY) {this.velY = velY;}
    public void setMoveAngle(double moveAngle) {this.moveAngle = moveAngle;}
    public void setFaceAngle(double faceAngle) {this.faceAngle = faceAngle;}
    public void incX(double i){this.x += i;}
    public void incY(double i){this.y += i;}
    public void incVelX(double i){this.velX += i;}
    public void incVelY(double i){this.velY += i;}
    public void incFaceAngle(double i){this.faceAngle += i;}
    public void incmoveAngle(double i){this.moveAngle += i;}
    

    BaseVectorShape(){
        setShape(null);
        setAlive(false);    
        setX(0.0);
        setY(0.0);    
        setVelX(0.0);
        setVelY(0.0);
        setMoveAngle(0.0);
        setFaceAngle(0.0);
    }

}
