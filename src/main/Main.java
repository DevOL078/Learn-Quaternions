package main;

import animation.QRotate;
import animation.QTranslate;
import animation.Scenario;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.TriangleMesh;
import javafx.stage.Stage;
import model.Point;
import model.Quaternion;
import model.Shape;
import util.Parser;

public class Main extends Application {

    double oldPosX = 0;
    double oldPosY = 0;
    double posX = 0;
    double posY = 0;

    final double SHIFT_ROTATION_COEFF = 10;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parser parser = new Parser("src/files/plane.smesh");
        TriangleMesh mesh = parser.parseTriangleMesh();

        Parser parser2 = new Parser("src/files/road.smesh");
        TriangleMesh mesh2 = parser2.parseTriangleMesh();

        Group root = new Group();

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateX(0);
        camera.setTranslateY(-60);
        camera.setTranslateZ(-200);
        camera.setFarClip(600);

        Group cameraGroup1 = new Group();
        cameraGroup1.getChildren().add(camera);

        Group cameraGroup2 = new Group();
        cameraGroup2.getChildren().add(cameraGroup1);


        Scenario scenario = new Scenario();
        Shape planeShape = new Shape("Cube", mesh);
        planeShape.getTranslationGroup().setTranslateY(-100);
        planeShape.getTranslationGroup().setRotationAxis(new Point3D(0,1,0));
        planeShape.getTranslationGroup().setRotate(180);
        planeShape.switchAxes();

        Shape roadShape = new Shape("Road", mesh2);
        roadShape.switchAxes();
        roadShape.getTranslationGroup().setRotationAxis(new Point3D(0,1,0));
        roadShape.getTranslationGroup().setRotate(90);

        Quaternion q2 = new Quaternion(new Point(0, 0 ,1), Math.PI / 180 * -20);
        Quaternion q3 = new Quaternion(new Point(0, 1, 0), Math.PI / 180 * 20);
        Quaternion q41 = new Quaternion(new Point(0, 0 ,1), Math.PI / 180 * 40);
        Quaternion q42 = new Quaternion(new Point(1, 0, 0), Math.PI / 180 * 10);
        Quaternion q4 = q41.multiply(q42);
        Quaternion q5 = new Quaternion(new Point(0, 1,0), Math.PI / 180 * -50);
        Quaternion q6 = new Quaternion(new Point(1, 0,0), Math.PI / 180 * -5);
        Quaternion q7 = new Quaternion(new Point(0, 1, 0), Math.PI / 180 * -100);
        Quaternion q81 = new Quaternion(new Point(0, 0, 1), Math.PI / 180 * -20);
        Quaternion q82 = new Quaternion(new Point(1, 0, 0), Math.PI / 180 * 3);
        Quaternion q8 = q81.multiply(q82);
        Quaternion q83 = new Quaternion(new Point(0, 1, 0), Math.PI /180 * -11);
        Quaternion q9 = new Quaternion(new Point(1, 0, 0), Math.PI / 180 * -8);

        QTranslate tran1 = new QTranslate(1, planeShape, new Point(0, 0, -50));
        QRotate rot2 = new QRotate(2, planeShape, q2, false);
        QTranslate tran2 = new QTranslate(2, planeShape, new Point(0, 0, -50));
        QRotate rot3 = new QRotate(3, planeShape, q3, true);
        QTranslate tran3 = new QTranslate(3, planeShape, new Point(0, 0, -60));
        QRotate rot4 = new QRotate(4, planeShape, q4, false);
        QTranslate tran4 = new QTranslate(4, planeShape, new Point(0, 0, -50));
        QRotate rot5 = new QRotate(5, planeShape, q5, true);
        QTranslate tran51 = new QTranslate(5, planeShape, new Point(0, 0, -50));
        QTranslate tran52 = new QTranslate(5, planeShape, new Point(0, 10, 0));
        QRotate rot6 = new QRotate(6, planeShape, q5, true);
        QTranslate tran61 = new QTranslate(6, planeShape, new Point(0, 0, -50));
        QTranslate tran62 = new QTranslate(6, planeShape, new Point(0, 10, 0));
        QRotate rot7 = new QRotate(7, planeShape, q5, true);
        QTranslate tran71 = new QTranslate(7, planeShape, new Point(0, 0, -50));
        QTranslate tran72 = new QTranslate(7, planeShape, new Point(0, 10, 0));
        QRotate rot8 = new QRotate(8, planeShape, q5, true);
        QTranslate tran81 = new QTranslate(8, planeShape, new Point(0, 0, -50));
        QTranslate tran82 = new QTranslate(8, planeShape, new Point(0, 10, 0));
        QRotate rot9 = new QRotate(9, planeShape, q5, true);
        QTranslate tran91 = new QTranslate(9, planeShape, new Point(0, 0, -50));
        QTranslate tran92 = new QTranslate(9, planeShape, new Point(0, 10, 0));
        QRotate rot10 = new QRotate(10, planeShape, q5, true);
        QTranslate tran101 = new QTranslate(10, planeShape, new Point(0, 0, -50));
        QTranslate tran102 = new QTranslate(10, planeShape, new Point(0, 7, 0));
        QRotate rot111 = new QRotate(11, planeShape, q5, true);
        QRotate rot112 = new QRotate(11, planeShape, q6, false);
        QTranslate tran111 = new QTranslate(11, planeShape, new Point(0, 0, -50));
        QTranslate tran112 = new QTranslate(11, planeShape, new Point(0, 0, -25));
        QTranslate tran113 = new QTranslate(11, planeShape, new Point(0, 5, 0));
        QRotate rot12 = new QRotate(12, planeShape, q7, true);
        QTranslate tran121 = new QTranslate(12, planeShape, new Point(0, 0, -50));
        QTranslate tran122 = new QTranslate(12, planeShape, new Point(0, 5, 0));
        QRotate rot13 = new QRotate(13, planeShape, q7, true);
        QTranslate tran131 = new QTranslate(13, planeShape, new Point(0, 0, -30));
        QTranslate tran132 = new QTranslate(13, planeShape, new Point(0, 5, 0));
        QRotate rot141 = new QRotate(14, planeShape, q8, false);
        QRotate rot142 = new QRotate(14, planeShape, q83, true);
        QTranslate tran141 = new QTranslate(14, planeShape, new Point(0, 0, -50));
        QTranslate tran142 = new QTranslate(14, planeShape, new Point(0, 5, 0));
        QTranslate tran151 = new QTranslate(15, planeShape, new Point(0, 0, -80));
        QTranslate tran152 = new QTranslate( 15, planeShape, new Point(0, 12, 0));
        QRotate rot16 = new QRotate(16, planeShape, q9, false);
        QTranslate tran161 = new QTranslate(16, planeShape, new Point(0, 0, -100));
        QTranslate tran162 = new QTranslate( 16, planeShape, new Point(0, 10, 0));





        scenario.addAll(tran1,
                rot2, tran2,
                rot3, tran3,
                rot4, tran4,
                rot5, tran51, tran52,
                rot6, tran61, tran62,
                rot7, tran71, tran72,
                rot8, tran81, tran82,
                rot9, tran91, tran92,
                rot10, tran101, tran102,
                rot111, rot112, tran111, tran112, tran113,
                rot12, tran121, tran122,
                rot13, tran131, tran132,
                rot141, rot142, tran141, tran142,
                tran151, tran152,
                rot16, tran161, tran162);


        root.getChildren().addAll(planeShape.getTranslationGroup(), roadShape.getTranslationGroup());
        root.getChildren().add(cameraGroup2);
        Scene scene = new Scene(root, 600, 600, true);
        scene.setFill(Paint.valueOf("White"));
        scene.setCamera(camera);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case S: camera.setTranslateZ(camera.getTranslateZ() - 5); break;
                    case W: camera.setTranslateZ(camera.getTranslateZ() + 5); break;
                }
            }
        });

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                oldPosX = event.getSceneX();
                oldPosY = event.getSceneY();
                posX = oldPosX;
                posY = oldPosY;
            }
        });

        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                oldPosX = posX;
                oldPosY = posY;
                posX = event.getSceneX();
                posY = event.getSceneY();

                double deltaX = posX - oldPosX;
                double deltaY = posY - oldPosY;

                Quaternion qX;
                Quaternion qY;

                if (event.isShiftDown()) {
                    qX = new Quaternion(new Point(0, 1, 0), Math.PI / 180 * deltaX * -0.1 * SHIFT_ROTATION_COEFF);
                    qY = new Quaternion(new Point(1, 0, 0), Math.PI / 180 * deltaY * 0.1 * SHIFT_ROTATION_COEFF);
                } else {
                    qX = new Quaternion(new Point(0, 1, 0), Math.PI / 180 * deltaX * -0.1);
                    qY = new Quaternion(new Point(1, 0, 0), Math.PI / 180 * deltaY * 0.1);
                }

                QRotate.rotateGroup(cameraGroup2, qX);
                QRotate.rotateGroup(cameraGroup1, qY);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Test");
        primaryStage.setFullScreen(true);
        scenario.play(100);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
