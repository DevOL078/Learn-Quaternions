package util;

import animation.Action;
import animation.QRotate;
import animation.QTranslate;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import model.Shape;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveController {

    String dirPath;
    String fileName;

    public SaveController(String dirPath){
        this.dirPath = dirPath;

        Date dateNow = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy_hh-mm-ss");
        this.fileName = format.format(dateNow);
    }

    public void save(){
        String path = dirPath + "\\" + fileName + ".quat";
        System.out.println(path);
        try(
                FileWriter out = new FileWriter(dirPath + "\\" + fileName + ".quat");
        ){
            //Shapes
            out.write("Shapes:" + SceneManager.getInstance().getShapeNumber() + "\n");
            for (String name: SceneManager.getInstance().getShapes().keySet()) {
                Shape shape = SceneManager.getInstance().getShape(name);
                out.write("name:" + shape.getName() + "\n");
                out.write("path:" + shape.getFile() + "\n");
            }

            //Actions
            out.write("Animations:" + SceneManager.getInstance().getAnimationsNumber() + "\n");
            for(Action action: SceneManager.getInstance().getScenario().getActions()){
                out.write("shape:" + action.getShape().getName() + "\n");
                out.write("priority:" + action.getPriority() + "\n");
                if(action instanceof QTranslate){
                    out.write("type:transition" + "\n");
                    QTranslate translate = (QTranslate)action;
                    out.write("x:" + String.format("%.5f",translate.getVector().X()) + "\n");
                    out.write("y:" + String.format("%.5f",translate.getVector().Y()) + "\n");
                    out.write("z:" + String.format("%.5f",translate.getVector().Z()) + "\n");
                }else if(action instanceof QRotate){
                    QRotate rotate = (QRotate)action;
                    if(rotate.getFullRotation()){
                        out.write("type:fullRotation" + "\n");
                    }
                    else{
                        out.write("type:rotation" + "\n");
                    }
                    out.write("s:" + String.format("%.5f", rotate.getQuaternion().S()) + "\n");
                    out.write("x:" + String.format("%.5f", rotate.getQuaternion().X()) + "\n");
                    out.write("y:" + String.format("%.5f", rotate.getQuaternion().Y()) + "\n");
                    out.write("z:" + String.format("%.5f", rotate.getQuaternion().Z()) + "\n");
                }
            }

            //Transforms
            int sum = 0;
            for (String name: SceneManager.getInstance().getShapes().keySet()) {
                Shape shape = SceneManager.getInstance().getShape(name);
                sum += shape.getTranslationGroup().getTransforms().size();
            }
            out.write("Transforms:" + sum + "\n");
            for (String name: SceneManager.getInstance().getShapes().keySet()) {
                Shape shape = SceneManager.getInstance().getShape(name);
                for (Transform transform: shape.getTranslationGroup().getTransforms()) {
                    out.write("shape:" + shape.getName() + "\n");
                    if(transform instanceof Translate){
                        Translate translate = (Translate)transform;
                        out.write("type:translate" + "\n");
                        out.write("x:" + String.format("%.5f", translate.getX()) + "\n");
                        out.write("y:" + String.format("%.5f", translate.getY()) + "\n");
                        out.write("z:" + String.format("%.5f", translate.getZ()) + "\n");
                    }
                    else if(transform instanceof Rotate){
                        Rotate rotate = (Rotate)transform;
                        out.write("type:rotate" + "\n");
                        out.write("x:" + String.format("%.5f", rotate.getAxis().getX()) + "\n");
                        out.write("y:" + String.format("%.5f", rotate.getAxis().getY()) + "\n");
                        out.write("z:" + String.format("%.5f", rotate.getAxis().getZ()) + "\n");
                        out.write("angle:" + String.format("%.5f", rotate.getAngle()) + "\n");

                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
