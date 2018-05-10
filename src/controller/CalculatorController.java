package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Quaternion;

public class CalculatorController {

    @FXML
    private TextField firstQSText;

    @FXML
    private TextField firstQXText;

    @FXML
    private TextField firstQYText;

    @FXML
    private TextField firstQZText;

    @FXML
    private TextField secondQSText;

    @FXML
    private TextField secondQXText;

    @FXML
    private TextField secondQYText;

    @FXML
    private TextField secondQZText;

    @FXML
    private Label resultQSText;

    @FXML
    private Label resultQXText;

    @FXML
    private Label resultQYText;

    @FXML
    private Label resultQZText;

    @FXML
    private Label resultVAAngleText;

    @FXML
    private Label resultVAXText;

    @FXML
    private Label resultVAYText;

    @FXML
    private Label resultVAZText;

    @FXML
    private TextField scalarText;

    @FXML
    private Label messageLab;

    @FXML
    public void initialize(){
        resultQSText.setText("...");
        resultQXText.setText("...");
        resultQYText.setText("...");
        resultQZText.setText("...");
        resultVAAngleText.setText("...");
        resultVAXText.setText("...");
        resultVAYText.setText("...");
        resultVAZText.setText("...");
        messageLab.setVisible(false);
    }

    public void onInverseButtonClick(){
        Quaternion q = getFirstQuat();
        if(q != null){
            writeResult(q.inverse());
        }
    }

    public void onNormalizeButtonClick(){
        Quaternion q = getFirstQuat();
        if(q != null){
            writeResult(q.normalize());
        }
    }

    public void onMultiplyByScalarButtonClick(){
        if(scalarText.getText() != null){
            try{
                double scalar = Double.parseDouble(scalarText.getText());
                Quaternion q = getFirstQuat();
                if(q != null){
                    writeResult(q.multiplyScalar(scalar));
                }
            }catch(NumberFormatException e){
                messageLab.setText("Illegal format of the parameters");
            }
        }
    }

    public void onSumButtonClick(){
        Quaternion q1 = getFirstQuat();
        Quaternion q2 = getSecondQuat();
        if(q1 != null && q2 != null){
            writeResult(q1.sum(q2));
        }
    }

    public void onMultiplyButtonClick(){
        Quaternion q1 = getFirstQuat();
        Quaternion q2 = getSecondQuat();
        if(q1 != null && q2 != null){
            writeResult(q1.multiply(q2));
        }
    }

    private Quaternion getFirstQuat(){
        boolean check1 = !firstQSText.getText().equals("");
        boolean check2 = !firstQXText.getText().equals("");
        boolean check3 = !firstQYText.getText().equals("");
        boolean check4 = !firstQZText.getText().equals("");
        try {
            if(check1 && check2 && check3 && check4) {
                double s = Double.parseDouble(firstQSText.getText());
                double x = Double.parseDouble(firstQXText.getText());
                double y = Double.parseDouble(firstQYText.getText());
                double z = Double.parseDouble(firstQZText.getText());
                return new Quaternion(s, x, y, z);
            }
            else{
                throw new NumberFormatException();
            }
        }catch (NumberFormatException e){
            messageLab.setVisible(true);
            messageLab.setText("Illegal format of the parameters");
        }
        return null;
    }

    private Quaternion getSecondQuat(){
        boolean check1 = !secondQSText.getText().equals("");
        boolean check2 = !secondQXText.getText().equals("");
        boolean check3 = !secondQYText.getText().equals("");
        boolean check4 = !secondQZText.getText().equals("");
        try {
            if(check1 && check2 && check3 && check4) {
                double s = Double.parseDouble(secondQSText.getText());
                double x = Double.parseDouble(secondQXText.getText());
                double y = Double.parseDouble(secondQYText.getText());
                double z = Double.parseDouble(secondQZText.getText());
                return new Quaternion(s, x, y, z);
            }
            else{
                throw new NumberFormatException();
            }
        }catch (NumberFormatException e){
            messageLab.setVisible(true);
            messageLab.setText("Illegal format of the parameters");
        }
        return null;
    }

    private void writeResult(Quaternion resq){
        resultQSText.setText(String.format("%.3f", resq.S()));
        resultQXText.setText(String.format("%.3f", resq.X()));
        resultQYText.setText(String.format("%.3f", resq.Y()));
        resultQZText.setText(String.format("%.3f", resq.Z()));
        resultVAAngleText.setText(String.format("%.3f", resq.getAngle()));
        resultVAXText.setText(String.format("%.3f", resq.getVector().X()));
        resultVAYText.setText(String.format("%.3f", resq.getVector().Y()));
        resultVAZText.setText(String.format("%.3f", resq.getVector().Z()));
        messageLab.setVisible(false);
    }

}

