package util;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import model.Point;
import animation.QRotate;
import model.Quaternion;

public class CameraController {

    private boolean isClcked = false;
    private double clickX;
    private double clickY;
    private Rotate rotX;
    private Rotate rotY;
    private Group camera;

    public CameraController(Group camera){
        clickX = 0;
        clickY = 0;
        rotX = new Rotate(0, Rotate.Y_AXIS);
        rotY = new Rotate(0, Rotate.X_AXIS);
        this.camera = camera;
        this.camera.getTransforms().addAll(rotX, rotY);
    }

    public void moveCamera(double x, double y){
        Quaternion qY = new Quaternion(new Point(0, 1, 0), Math.PI / 180 * x * 0.1);
        double angle = Math.atan(camera.getTranslateZ() / camera.getTranslateX());
        angle = Math.PI / 180 * 90 + angle;
        Point vector = new Point(Math.cos(angle), 0, Math.sin(angle));
        Quaternion qX = new Quaternion(vector, Math.PI / 180 * -y * 0.1);
        QRotate.rotateGroup(camera, qX.multiply(qY));
    }

}
