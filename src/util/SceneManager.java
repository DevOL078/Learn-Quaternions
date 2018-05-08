package util;

import animation.Scenario;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Shape;

import java.util.HashMap;
import java.util.Vector;

public class SceneManager {

    private static SceneManager instance = new SceneManager();

    private int transitionNumber = 0;
    private int rotationNumber = 0;
    private int fullRotationNumber = 0;
    private int shapeNumber = 0;
    private Scenario scenario;
    private HashMap<String, Shape> shapes;
    private Group mainScene;
    private PerspectiveCamera camera;
    private Group cameraGroup1;
    private Group cameraGroup2;

    private SceneManager(){
        scenario = new Scenario();
        shapes = new HashMap<>();
        mainScene = new Group();

        camera = new PerspectiveCamera(true);
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(-200);
        camera.setFarClip(600);

        cameraGroup1 = new Group();
        cameraGroup1.getChildren().add(camera);

        cameraGroup2 = new Group();
        cameraGroup2.getChildren().add(cameraGroup1);

        mainScene.getChildren().add(cameraGroup2);
    }

    public int getTransitionNumber() {
        return transitionNumber;
    }

    public int getRotationNumber() {
        return rotationNumber;
    }

    public int getFullRotationNumber() {
        return fullRotationNumber;
    }

    public int getShapeNumber() {
        return shapeNumber;
    }

    public PerspectiveCamera getCamera() {
        return camera;
    }

    public Group getCameraGroup1() {
        return cameraGroup1;
    }

    public Group getCameraGroup2() {
        return cameraGroup2;
    }

    public static SceneManager getInstance(){
        return instance;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public Group getMainScene() {
        return mainScene;
    }

    public Shape getShape(String shapeName){
        return shapes.get(shapeName);
    }

    public void addTransition(){
        transitionNumber++;
    }

    public void addRotation(){
        rotationNumber++;
    }

    public void addFullRotation(){
        fullRotationNumber++;
    }

    public void addShape(Shape shape){
        shapeNumber++;
        shapes.put(shape.getName(), shape);
        mainScene.getChildren().add(shape.getTranslationGroup());
    }

    public void deleteShape(Shape shape){
        shapeNumber--;
        if(shape != null){
            mainScene.getChildren().remove(shape.getTranslationGroup());
            scenario.deleteShape(shape);
        }
    }

    public int getAnimationsNumber(){
        return transitionNumber + rotationNumber + fullRotationNumber;
    }

}
