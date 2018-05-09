package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Point;
import model.Quaternion;

public class ConverterController {

    @FXML
    private TextField sQuat;

    @FXML
    private TextField xQuat;

    @FXML
    private TextField yQuat;

    @FXML
    private TextField zQuat;

    @FXML
    private TextField angleVec;

    @FXML
    private TextField xVec;

    @FXML
    private TextField yVec;

    @FXML
    private TextField zVec;

    @FXML
    private Button toQuatButton;

    @FXML
    private Button toVecButton;

    @FXML
    public void initialize(){

    }

    public void onToVecButtonClick(){
        Quaternion q = getQuatFormQ();
        if(q != null){
            angleVec.setText(String.format("%.3f", q.getAngle()).replaceAll(",", "."));
            xVec.setText(String.format("%.3f", q.getVector().X()).replaceAll(",", "."));
            yVec.setText(String.format("%.3f", q.getVector().Y()).replaceAll(",", "."));
            zVec.setText(String.format("%.3f", q.getVector().Z()).replaceAll(",", "."));
        }
    }

    public void onToQuatButtonClick(){
        Quaternion q = getQuatFromVec();
        if(q != null){
            sQuat.setText(String.format("%.3f", q.S()).replaceAll(",", "."));
            xQuat.setText(String.format("%.3f", q.X()).replaceAll(",", "."));
            yQuat.setText(String.format("%.3f", q.Y()).replaceAll(",", "."));
            zQuat.setText(String.format("%.3f", q.Z()).replaceAll(",", "."));
        }
    }

    private Quaternion getQuatFormQ(){
        boolean check1 = !sQuat.getText().equals("");
        boolean check2 = !xQuat.getText().equals("");
        boolean check3 = !yQuat.getText().equals("");
        boolean check4 = !zQuat.getText().equals("");
        try {
            if(check1 && check2 && check3 && check4) {
                double s = Double.parseDouble(sQuat.getText());
                double x = Double.parseDouble(xQuat.getText());
                double y = Double.parseDouble(yQuat.getText());
                double z = Double.parseDouble(zQuat.getText());
                return new Quaternion(s, x, y, z);
            }
            else{
                throw new NumberFormatException();
            }
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Illegal format of the parameters");
            alert.showAndWait();
        }
        return null;
    }

    private Quaternion getQuatFromVec(){
        boolean check1 = !angleVec.getText().equals("");
        boolean check2 = !xVec.getText().equals("");
        boolean check3 = !yVec.getText().equals("");
        boolean check4 = !zVec.getText().equals("");
        try {
            if(check1 && check2 && check3 && check4) {
                double angle = Double.parseDouble(angleVec.getText());
                double x = Double.parseDouble(xVec.getText());
                double y = Double.parseDouble(yVec.getText());
                double z = Double.parseDouble(zVec.getText());
                return new Quaternion(new Point(x,y,z), angle);
            }
            else{
                throw new NumberFormatException();
            }
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Illegal format of the parameters");
            alert.showAndWait();
        }
        return null;
    }

}

