package animation;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import model.Point;
import model.Quaternion;
import model.Shape;
import util.SceneManager;

public class QRotate implements Action {

    private Shape shape;
    private Quaternion q;
    private int ticks;
    private int priority;
    private boolean fullRotation;
    private Rotate rotate;

    public QRotate(int priority, Shape shape, Point vector, double angle, boolean fullRotation){
        this.priority = priority;
        this.shape = shape;
        this.q = new Quaternion(vector, angle);
        this.ticks = 0;
        this.fullRotation = fullRotation;
    }

    public QRotate(int priority, Shape shape, Quaternion q, boolean fullRotation){
        this.priority = priority;
        this.shape = shape;
        this.q = q;
        this.fullRotation = fullRotation;
    }

    @Override
    public int getTicks(){
        return ticks;
    }

    @Override
    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String getName() {
        return shape.getName();
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    public boolean getFullRotation() {return fullRotation;}

    public Quaternion getQuaternion(){
        return q;
    }

    @Override
    public void playTick(double tick){
        Quaternion startQ = new Quaternion(1, 0, 0, 0);
        Quaternion interpolatedQ = Quaternion.interQuaternion(startQ, q, tick);
        rotate(interpolatedQ);
        ticks++;
    }

    private void rotate(Quaternion q){
        double mag = q.X()*q.X() + q.Y()*q.Y() + q.Z()*q.Z();

        final double EPS = 1.0e-12;

        if(mag > EPS){
            mag = Math.sqrt(mag);
            double invMag = 1.0/mag;

            double pX = q.X()*invMag;
            double pY = q.Y()*invMag;
            double pZ = q.Z()*invMag;
            double angle = 2.0*Math.atan2(mag, q.S()) * 180 / Math.PI;
            rotate = new Rotate(angle, new Point3D(pX, pY, pZ));
            if(fullRotation)
                shape.getTranslationGroup().getTransforms().add(rotate);
            else
                shape.getRotationGroup().getTransforms().add(rotate);
        }
    }

    public static void rotateShape(Shape shape, Quaternion q){
        double mag = q.X()*q.X() + q.Y()*q.Y() + q.Z()*q.Z();

        final double EPS = 1.0e-12;

        if(mag > EPS){
            mag = Math.sqrt(mag);
            double invMag = 1.0/mag;

            double pX = q.X()*invMag;
            double pY = q.Y()*invMag;
            double pZ = q.Z()*invMag;
            double angle = 2.0*Math.atan2(mag, q.S()) * 180 / Math.PI;
            Rotate rotate = new Rotate(angle, new Point3D(pX, pY, pZ));
            shape.getTranslationGroup().getTransforms().add(rotate);
            SceneManager.getInstance().getScenario().addMemberTransform(shape, rotate);
        }
    }

    public static void rotateGroup(Group group, Quaternion q){
        double mag = q.X()*q.X() + q.Y()*q.Y() + q.Z()*q.Z();

        final double EPS = 1.0e-12;

        if(mag > EPS){
            mag = Math.sqrt(mag);
            double invMag = 1.0/mag;

            double pX = q.X()*invMag;
            double pY = q.Y()*invMag;
            double pZ = q.Z()*invMag;
            double angle = 2.0*Math.atan2(mag, q.S()) * 180 / Math.PI;
            Rotate rotate = new Rotate(angle, new Point3D(pX, pY, pZ));
            group.getTransforms().add(rotate);
        }
    }

}
