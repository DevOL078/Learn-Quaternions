package controller;

import animation.Action;
import animation.QRotate;
import animation.QTranslate;
import gui.AnimationBox;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AnimationInfoController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label shapeLabel;

    @FXML
    private Label priorityLabel;

    @FXML
    private Label tXLab;

    @FXML
    private Label tYLab;

    @FXML
    private Label tZLab;

    @FXML
    private Label vaXLab;

    @FXML
    private Label vaYLab;

    @FXML
    private Label vaZLab;

    @FXML
    private Label vaAngleLab;

    @FXML
    private Label qSLab;

    @FXML
    private Label qXLab;

    @FXML
    private Label qYLab;

    @FXML
    private Label qZLab;

    @FXML
    private GridPane tPane;

    @FXML
    private GridPane rPane;

    @FXML
    private Label fullRotationLab;

    @FXML
    private Button okButton;

    private static AnimationInfoController instance;
    private Stage stage;

    @FXML
    private void initialize(){
        instance = this;
    }

    public static AnimationInfoController getInstance(){
        return instance;
    }

    public void buildScene(AnimationBox box){
        Action action = box.getAction();
        nameLabel.setText(box.getName());
        shapeLabel.setText(action.getShape().getName());
        priorityLabel.setText(String.valueOf(action.getPriority()));
        if(action instanceof QTranslate){
            typeLabel.setText("Transition");
            tPane.setVisible(true);
            rPane.setVisible(false);

            QTranslate translate = (QTranslate)action;
            tXLab.setText(String.format("%.3f", translate.getVector().X()));
            tYLab.setText(String.format("%.3f", translate.getVector().Y()));
            tZLab.setText(String.format("%.3f", translate.getVector().Z()));
        }
        else if(action instanceof QRotate){
            typeLabel.setText("Rotation");
            rPane.setVisible(true);
            tPane.setVisible(false);

            QRotate rotate = (QRotate)action;
            vaXLab.setText(String.format("%.3f", rotate.getQuaternion().getVector().X()));
            vaYLab.setText(String.format("%.3f", rotate.getQuaternion().getVector().Y()));
            vaZLab.setText(String.format("%.3f", rotate.getQuaternion().getVector().Z()));
            vaAngleLab.setText(String.format("%.3f", rotate.getQuaternion().getAngle()));

            qSLab.setText(String.format("%.3f", rotate.getQuaternion().S()));
            qXLab.setText(String.format("%.3f", rotate.getQuaternion().X()));
            qYLab.setText(String.format("%.3f", rotate.getQuaternion().Y()));
            qZLab.setText(String.format("%.3f", rotate.getQuaternion().Z()));

            if(rotate.getFullRotation()){
                fullRotationLab.setText("Yes");
            }
            else{
                fullRotationLab.setText("No");
            }
        }
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void onOkButtonClick(){
        stage.close();
    }

}
