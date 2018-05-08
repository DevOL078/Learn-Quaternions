package util;

import animation.Action;
import animation.QRotate;
import animation.QTranslate;
import controller.SceneController;
import javafx.geometry.Point3D;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import main.Test;
import model.Point;
import model.Quaternion;
import model.Shape;

import java.io.*;

public class LoadController {

    String filePath;

    public LoadController(String filePath){
        this.filePath = filePath;
    }

    public void load(){
        SceneManager.getInstance().resetManager();
        loadNewScene();

        try(
                FileInputStream fstream = new FileInputStream(filePath);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fstream));
        ){
            String input;
            String work;

            //Shapes
            input = reader.readLine();
            work = input.substring(7);
            int shapesNumber = Integer.parseInt(work);
            for(int i = 0; i < shapesNumber; ++i){
                input = reader.readLine();
                String name = input.substring(5);
                input = reader.readLine();
                String path = input.substring(5);
                Shape shape = new Shape(name, new Parser(path).parseTriangleMesh(), filePath);
                addShape(shape);
            }

            //Animations
            input = reader.readLine();
            int animationsNumber = Integer.parseInt(input.substring(11));
            for(int i = 0; i < animationsNumber; ++i){
                input = reader.readLine();
                String shapeName = input.substring(6);
                input = reader.readLine();
                int priority = Integer.parseInt(input.substring(9));
                input = reader.readLine();
                String type = input.substring(5);
                if(type.equals("transition")){
                    input = reader.readLine();
                    work = input.substring(2);
                    double x = Double.parseDouble(work.replaceAll(",", "."));
                    input = reader.readLine();
                    work = input.substring(2);
                    double y = Double.parseDouble(work.replaceAll(",", "."));
                    input = reader.readLine();
                    work = input.substring(2);
                    double z = Double.parseDouble(work.replaceAll(",", "."));
                    QTranslate translate = new QTranslate(priority, SceneManager.getInstance().getShape(shapeName), new Point(x, y,z));
                    addAction(translate);

                }
                else{
                    input = reader.readLine();
                    work = input.substring(2);
                    double s = Double.parseDouble(work.replaceAll(",", "."));
                    input = reader.readLine();
                    work = input.substring(2);
                    double x = Double.parseDouble(work.replaceAll(",", "."));
                    input = reader.readLine();
                    work = input.substring(2);
                    double y = Double.parseDouble(work.replaceAll(",", "."));
                    input = reader.readLine();
                    work = input.substring(2);
                    double z = Double.parseDouble(work.replaceAll(",", "."));
                    QRotate rotate = null;
                    if(type.equals("fullRotation")){
                        rotate = new QRotate(priority,SceneManager.getInstance().getShape(shapeName),new Quaternion(s,x,y,z),true);
                    }
                    else if(type.equals("rotation")){
                        rotate = new QRotate(priority,SceneManager.getInstance().getShape(shapeName),new Quaternion(s,x,y,z),false);
                    }
                    addAction(rotate);
                }
            }

            //Transforms
            input = reader.readLine();
            int transformsNumber = Integer.parseInt(input.substring(11));
            for(int i = 0; i < transformsNumber; ++i){
                input = reader.readLine();
                String shapeName = input.substring(6);
                Shape shape = SceneManager.getInstance().getShape(shapeName);
                input = reader.readLine();
                String type = input.substring(5);
                if(type.equals("translate")){
                    input = reader.readLine();
                    work = input.substring(2);
                    double x = Double.parseDouble(work.replaceAll(",", "."));
                    input = reader.readLine();
                    work = input.substring(2);
                    double y = Double.parseDouble(work.replaceAll(",", "."));
                    input = reader.readLine();
                    work = input.substring(2);
                    double z = Double.parseDouble(work.replaceAll(",", "."));
                    Translate translate = new Translate(x,y,z);
                    addTransform(shape, translate);
                }
                else if(type.equals("rotate")){
                    input = reader.readLine();
                    work = input.substring(2);
                    double x = Double.parseDouble(work.replaceAll(",", "."));
                    input = reader.readLine();
                    work = input.substring(2);
                    double y = Double.parseDouble(work.replaceAll(",", "."));
                    input = reader.readLine();
                    work = input.substring(2);
                    double z = Double.parseDouble(work.replaceAll(",", "."));
                    input = reader.readLine();
                    work = input.substring(6);
                    double angle = Double.parseDouble(work.replaceAll(",", "."));
                    Rotate rotate = new Rotate(angle, new Point3D(x,y,z));
                    addTransform(shape, rotate);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadNewScene(){

        try {
            Test.getStage().close();
            new Test().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addShape(Shape shape){
        SceneController.getInstance().addShape(shape);
    }

    private void addAction(Action action){
        SceneController.getInstance().addAction(action);
    }

    private void addTransform(Shape shape, Transform transform){
        shape.getTranslationGroup().getTransforms().add(transform);
        SceneManager.getInstance().getScenario().addMemberTransform(shape, transform);
    }
}
