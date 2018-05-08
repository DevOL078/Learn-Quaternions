package model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;


public class Shape {

    private String name;
    private TriangleMesh mesh;
    private Group group;
    private MeshView view;

    private Group rotationGroup;
    private Group translationGroup;

    private Box ox;
    private Box oy;
    private Box oz;

    public Shape(String name, TriangleMesh mesh){
        this.name = name;
        this.mesh = mesh;

        view = new MeshView(mesh);

        this.rotationGroup = new Group(view);
        this.translationGroup = new Group(rotationGroup);

        addAxes();
    }

    public Shape(String name, Group group){
        this.name = name;
        this.group = group;
        this.rotationGroup = new Group(group);
        this.translationGroup = new Group(rotationGroup);
    }

    public String getName(){
        return name;
    }

    public TriangleMesh getMesh() {
        return mesh;
    }

    public Group getRotationGroup(){
        return rotationGroup;
    }

    public Group getTranslationGroup() {return translationGroup;}

    public void switchDrawMode(){
        if(view.getDrawMode().equals(DrawMode.FILL)){
            view.setDrawMode(DrawMode.LINE);
        }
        else{
            view.setDrawMode(DrawMode.FILL);
        }
    }

    public void switchAxes(){
        if(ox.isVisible()){
            ox.setVisible(false);
            oy.setVisible(false);
            oz.setVisible(false);
        }
        else{
            ox.setVisible(true);
            oy.setVisible(true);
            oz.setVisible(true);
        }
    }

    private void addAxes(){
        ox = new Box(60, 0.5, 0.5);
        oy = new Box(0.5, 60, 0.5);
        oz = new Box(0.5, 0.5, 60);
        PhongMaterial materialX = new PhongMaterial();
        materialX.setDiffuseColor(Color.RED);
        materialX.setSpecularPower(10.0);
        PhongMaterial materialY = new PhongMaterial();
        materialY.setDiffuseColor(Color.GREEN);
        materialY.setSpecularPower(10.0);
        PhongMaterial materialZ = new PhongMaterial();
        materialZ.setDiffuseColor(Color.BLUE);
        materialZ.setSpecularPower(10.0);

        ox.setMaterial(materialX);
        oy.setMaterial(materialY);
        oz.setMaterial(materialZ);

        rotationGroup.getChildren().addAll(ox, oy, oz);
    }

}
