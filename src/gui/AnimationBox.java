package gui;

import animation.Action;
import animation.QRotate;
import animation.QTranslate;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.net.URL;

public class AnimationBox extends HBox {

    private Action animation;
    private String type;
    private String name;

    public AnimationBox(Action action, String name){
        this.name = name;
        this.animation = action;
        if(action instanceof QTranslate){
            type = "Transition";
        }
        else if(action instanceof QRotate){
            QRotate rot = (QRotate)animation;
            if(rot.getFullRotation()){
                type = "FullRotation";
            }
            else{
                type = "Rotation";
            }
        }
        buildBox();
    }

    private void buildBox(){
        Pane image = new Pane();
        image.setPrefWidth(20);
        image.setPrefHeight(20);
        switch(type){
            case "Transition": image.getStyleClass().add("transition"); break;
            case "Rotation" : image.getStyleClass().add("rotation"); break;
            case "FullRotation" : image.getStyleClass().add("fullRotation"); break;
        }
        URL url = this.getClass().getResource("../view/style/symbolsStyle.css");
        String css = url.toExternalForm();
        image.getStylesheets().add(css);
        Label label = new Label(name);
        HBox.setMargin(label, new Insets(0, 10, 0, 0));
        Label shapeName = new Label(animation.getName());
        HBox.setMargin(shapeName, new Insets(0, 0, 0, 10));
        Pane pane = new Pane();
        HBox.setHgrow(pane, Priority.ALWAYS);
        Label priority = new Label(String.valueOf(animation.getPriority()));
        super.getChildren().addAll(image, label, shapeName, pane, priority);
    }
}
