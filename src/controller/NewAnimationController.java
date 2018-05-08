package controller;

import animation.QRotate;
import animation.QTranslate;
import gui.AnimationBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Point;
import model.Quaternion;
import model.Shape;
import util.SceneManager;

public class NewAnimationController {

    @FXML
    private ChoiceBox<String> animationChoiceBox;

    @FXML
    private TextField shapeNameText;

    @FXML
    private GridPane transitionGrid;

    @FXML
    private TextField priorityTText;

    @FXML
    private TextField vectorXTText;

    @FXML
    private TextField vectorYTText;

    @FXML
    private TextField vectorZTText;

    @FXML
    private Button addTransitionButton;

    @FXML
    private Button cancelTransitionButton;

    @FXML
    private TabPane rotationPane;

    @FXML
    private TextField priorityVAText;

    @FXML
    private TextField vectorXVAText;

    @FXML
    private TextField vectorYVAText;

    @FXML
    private TextField vectorZVAText;

    @FXML
    private Button addVAButton;

    @FXML
    private Button cancelVAButton;

    @FXML
    private TextField angleVAText;

    @FXML
    private ChoiceBox<String> fullVABox;

    @FXML
    private TextField priorityQText;

    @FXML
    private TextField quatSText;

    @FXML
    private TextField quatXText;

    @FXML
    private TextField quatYText;

    @FXML
    private TextField quatZText;

    @FXML
    private Button addQButton;

    @FXML
    private Button cancelQButton;

    @FXML
    private ChoiceBox<String> fullQBox;

    private static NewAnimationController instance;
    private Stage stage;

    @FXML
    public void initialize(){
        instance = this;
        ObservableList<String> animationsTypes = FXCollections.observableArrayList();
        animationsTypes.addAll("Transition", "Rotation");
        animationChoiceBox.getItems().addAll(animationsTypes);
        animationChoiceBox.getSelectionModel().select(0);
        animationChoiceBox.setOnAction(e->{
            if(animationChoiceBox.getSelectionModel().getSelectedItem().equals("Rotation")){
                transitionGrid.setVisible(false);
                rotationPane.setVisible(true);
            }
            else{
                rotationPane.setVisible(false);
                transitionGrid.setVisible(true);
            }
        });

        ObservableList<String> fullTypes = FXCollections.observableArrayList();
        fullTypes.addAll("Yes", "No");
        fullVABox.getItems().addAll(fullTypes);
        fullVABox.getSelectionModel().select(0);

        fullQBox.getItems().addAll(fullTypes);
        fullQBox.getSelectionModel().select(0);

        rotationPane.setVisible(false);
        transitionGrid.setVisible(true);
    }

    public void onAddTransitionButtonClick(){
        boolean check1 = !shapeNameText.getText().equals("");
        boolean check2 = !priorityTText.getText().equals("");
        boolean check3 = !vectorXTText.getText().equals("") && !vectorYTText.getText().equals("") && !vectorZTText.getText().equals("");
        if(check1 && check2 && check3){
            String name = shapeNameText.getText();
            Shape shape = SceneManager.getInstance().getShape(name);
            if(shape != null) {
                int priority = Integer.valueOf(priorityTText.getText());
                double x = Double.valueOf(vectorXTText.getText());
                double y = Double.valueOf(vectorYTText.getText());
                double z = Double.valueOf(vectorZTText.getText());
                QTranslate translate = new QTranslate(priority, shape, new Point(x,y,z));
                SceneManager.getInstance().getScenario().addAction(translate);
                SceneManager.getInstance().addTransition();

                AnimationBox animationBox = new AnimationBox(translate, "Transition" + SceneManager.getInstance().getTransitionNumber());
                SceneController.getInstance().addAnimationBox(animationBox);
                stage.close();
            }
        }
    }

    public void onAddVARotationButtonClick(){
        boolean check1 = !shapeNameText.getText().equals("");
        boolean check2 = !priorityVAText.getText().equals("");
        boolean check3 = !vectorXVAText.getText().equals("") && !vectorYVAText.getText().equals("") && !vectorZVAText.getText().equals("");
        boolean check4 = !angleVAText.getText().equals("");
        if(check1 && check2 && check3 && check4){
            String name = shapeNameText.getText();
            Shape shape = SceneManager.getInstance().getShape(name);
            if(shape != null) {
                int priority = Integer.valueOf(priorityVAText.getText());
                double x = Double.valueOf(vectorXVAText.getText());
                double y = Double.valueOf(vectorYVAText.getText());
                double z = Double.valueOf(vectorZVAText.getText());
                double angle = Double.valueOf(angleVAText.getText());
                QRotate rotation;
                AnimationBox animationBox;
                if(fullVABox.getSelectionModel().getSelectedItem().equals("Yes")){
                    rotation = new QRotate(priority, shape, new Point(x,y,z),angle,true);
                    SceneManager.getInstance().addFullRotation();
                    animationBox = new AnimationBox(rotation, "FullRotation" + SceneManager.getInstance().getFullRotationNumber());
                }
                else{
                    rotation = new QRotate(priority, shape, new Point(x,y,z),angle,false);
                    SceneManager.getInstance().addRotation();
                    animationBox = new AnimationBox(rotation, "Rotation" + SceneManager.getInstance().getRotationNumber());
                }
                SceneManager.getInstance().getScenario().addAction(rotation);

                SceneController.getInstance().addAnimationBox(animationBox);
                stage.close();
            }
        }
    }

    public void onAddQRotationButtonClick(){
        boolean check1 = !shapeNameText.getText().equals("");
        boolean check2 = !priorityQText.getText().equals("");
        boolean check3 = !quatSText.getText().equals("") && !quatXText.getText().equals("") && !quatYText.getText().equals("") && !quatZText.getText().equals("");
        if(check1 && check2 && check3){
            String name = shapeNameText.getText();
            Shape shape = SceneManager.getInstance().getShape(name);
            if(shape != null) {
                int priority = Integer.valueOf(priorityQText.getText());
                double s = Double.valueOf(quatSText.getText());
                double x = Double.valueOf(quatXText.getText());
                double y = Double.valueOf(quatYText.getText());
                double z = Double.valueOf(quatZText.getText());
                QRotate rotation;
                AnimationBox animationBox;
                if(fullQBox.getSelectionModel().getSelectedItem().equals("Yes")){
                    rotation = new QRotate(priority, shape, new Quaternion(s,x,y,z), true);
                    SceneManager.getInstance().addFullRotation();
                    animationBox = new AnimationBox(rotation, "FullRotation" + SceneManager.getInstance().getFullRotationNumber());
                }
                else{
                    rotation = new QRotate(priority, shape, new Quaternion(s,x,y,z), false);
                    SceneManager.getInstance().addRotation();
                    animationBox = new AnimationBox(rotation, "FullRotation" + SceneManager.getInstance().getRotationNumber());
                }
                SceneManager.getInstance().getScenario().addAction(rotation);

                SceneController.getInstance().addAnimationBox(animationBox);
                stage.close();
            }
        }
    }

    public void onCancelButtonClick(){
        stage.close();
    }

    public static NewAnimationController getInstance(){
        return instance;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

}
