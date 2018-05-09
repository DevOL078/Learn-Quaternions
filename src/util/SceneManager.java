package util;

import animation.Action;
import animation.QRotate;
import animation.QTranslate;
import animation.Scenario;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;
import model.Shape;

import java.util.HashMap;

public class SceneManager{

    private static SceneManager instance = new SceneManager();

    private int transitionNumber;
    private int rotationNumber;
    private int fullRotationNumber;
    private int shapeNumber;
    private int transitionActualNumber;
    private int rotationActualNumber;
    private int fullRotationActualNumber;
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

        this.transitionNumber = 0;
        this.rotationNumber = 0;
        this.fullRotationNumber = 0;
        this.transitionActualNumber = 0;
        this.rotationActualNumber = 0;
        this.fullRotationActualNumber = 0;
        this.shapeNumber = 0;

        camera = new PerspectiveCamera(true);
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(-200);
        camera.setFarClip(1000);

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

    public HashMap<String, Shape> getShapes(){
        return shapes;
    }

    public void addTransition(){
        transitionNumber++;
        transitionActualNumber++;
    }

    public void addRotation(){
        rotationNumber++;
        rotationActualNumber++;
    }

    public void addFullRotation(){
        fullRotationNumber++;
        fullRotationActualNumber++;
    }

    public void deleteAction(Action action){
        if(action instanceof QTranslate){
            transitionActualNumber--;
        }
        else if(action instanceof QRotate){
            if(((QRotate) action).getFullRotation()){
                fullRotationActualNumber--;
            }
            else{
                rotationActualNumber--;
            }
        }
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
        return transitionActualNumber + rotationActualNumber + fullRotationActualNumber;
    }

    public void resetManager(){
        scenario = new Scenario();
        shapes = new HashMap<>();
        mainScene = new Group();

        this.transitionNumber = 0;
        this.rotationNumber = 0;
        this.fullRotationNumber = 0;
        this.shapeNumber = 0;

        camera = new PerspectiveCamera(true);
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(-200);
        camera.setFarClip(1000);

        cameraGroup1 = new Group();
        cameraGroup1.getChildren().add(camera);

        cameraGroup2 = new Group();
        cameraGroup2.getChildren().add(cameraGroup1);

        mainScene.getChildren().add(cameraGroup2);
    }

}
