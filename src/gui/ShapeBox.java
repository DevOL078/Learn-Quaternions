package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.Shape;

public class ShapeBox extends HBox {

    private Shape shape;
    private boolean clicked = false;

    public ShapeBox(Shape shape){
        this.shape = shape;
        buildBox();
    }

    private void buildBox(){
        Label label = new Label(shape.getName());
        HBox.setMargin(label, new Insets(0, 20, 0, 0));
        super.getChildren().add(label);
        super.setOnMouseClicked(e->{

        });
    }

    public void setClicked(boolean cond){
        clicked = cond;
    }

    public boolean getClicked(){
        return clicked;
    }

    public void switchDrawMode(){
        shape.switchDrawMode();
    }

    public Shape shape(){
        return shape;
    }
}
