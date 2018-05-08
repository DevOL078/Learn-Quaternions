package animation;

import javafx.scene.Group;
import javafx.scene.transform.Translate;
import model.Point;
import model.Shape;
import util.SceneManager;

public class QTranslate implements Action {

    private Shape shape;
    private Point vector;
    private int ticks;
    private int priority;
    private Translate translate;

    public QTranslate(int priority, Shape shape, Point vector) {
        this.priority = priority;
        this.shape = shape;
        this.vector = vector;
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

    @Override
    public void playTick(double tick) {
        Point interpolateV = vector.interpolatePoint(tick);
        translate(interpolateV);
        ticks++;
    }

    private void translate(Point vector){
        translate = new Translate(vector.X(), vector.Y(), vector.Z());
        shape.getTranslationGroup().getTransforms().add(translate);
    }

    public static void translateShape(Shape shape, Point vector){
        Translate translate = new Translate(vector.X(), vector.Y(), vector.Z());
        shape.getTranslationGroup().getTransforms().add(translate);
        SceneManager.getInstance().getScenario().addMemberTransform(shape, translate);
    }

    public static void translateGroup(Group group, Point vector){

    }

}
