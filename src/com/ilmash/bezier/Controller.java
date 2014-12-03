package com.ilmash.bezier;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Controller {
    @FXML
    private Label label;
    @FXML
    private Canvas canv;
    @FXML
    private Circle circle_M1, circle_M2, circle_M3, circle_D1, circle_D2, circle_D3;
    @FXML
    private Line pilot_left, pilot_middle, pilot_right, left_arm, right_arm, pen_track;
    private Point2D Ref, M1, M2, M3, D1, D2, D3;

    @FXML
    protected void buttonAction() {
        label.setText("hello");
        final GraphicsContext gc = canv.getGraphicsContext2D();

        final DoubleProperty M1x = new SimpleDoubleProperty();
        final DoubleProperty M1y = new SimpleDoubleProperty();
        final DoubleProperty M2x = new SimpleDoubleProperty();
        final DoubleProperty M2y = new SimpleDoubleProperty();
        final DoubleProperty M3x = new SimpleDoubleProperty();
        final DoubleProperty M3y = new SimpleDoubleProperty();

        final double baseDistance = Point2D.distance(new Point2D(circle_M1.getCenterX(), circle_M1.getCenterY()),
                new Point2D(circle_M2.getCenterX(), circle_M2.getCenterY()));
        Ref = new Point2D(circle_M1.getCenterX(), circle_M1.getCenterY());



        final Timeline tl = new Timeline(
                new KeyFrame(
                        Duration.seconds(0),
                        new KeyValue(M1x, circle_M1.getCenterX()),
                        new KeyValue(M1y, circle_M1.getCenterY()),
                        new KeyValue(M2x, circle_M2.getCenterX()),
                        new KeyValue(M2y, circle_M2.getCenterY()),
                        new KeyValue(M3x, circle_M3.getCenterX()),
                        new KeyValue(M3y, circle_M3.getCenterY())
                ),
                new KeyFrame(
                        Duration.seconds(10),
                        new KeyValue(M1x, pilot_left.getEndX()),
                        new KeyValue(M1y, pilot_left.getEndY()),
                        new KeyValue(M2x, pilot_middle.getEndX()),
                        new KeyValue(M2y, pilot_middle.getEndY()),
                        new KeyValue(M3x, pilot_right.getEndX()),
                        new KeyValue(M3y, pilot_right.getEndY())
                )
        );
        tl.setAutoReverse(true);
        tl.setCycleCount(2);

        AnimationTimer tm = new AnimationTimer() {
            @Override
            public void handle(long now) {
                circle_M1.setCenterX(M1x.doubleValue());
                circle_M1.setCenterY(M1y.doubleValue());
                circle_M2.setCenterX(M2x.doubleValue());
                circle_M2.setCenterY(M2y.doubleValue());
                circle_M3.setCenterX(M3x.doubleValue());
                circle_M3.setCenterY(M3y.doubleValue());

                left_arm.setStartX(circle_M1.getCenterX());
                left_arm.setStartY(circle_M1.getCenterY());
                left_arm.setEndX(circle_M2.getCenterX());
                left_arm.setEndY(circle_M2.getCenterY());

                right_arm.setStartX(circle_M2.getCenterX());
                right_arm.setStartY(circle_M2.getCenterY());
                right_arm.setEndX(circle_M3.getCenterX());
                right_arm.setEndY(circle_M3.getCenterY());

                M1 = new Point2D(M1x.doubleValue(),M1y.doubleValue());
                M2 = new Point2D(M2x.doubleValue(), M2y.doubleValue());
                M3 = new Point2D(M3x.doubleValue(), M3y.doubleValue());
                D1 = new Point2D();
                D2 = new Point2D();
                D3 = new Point2D();
                double percent=Point2D.distance(M1,Ref)/baseDistance;
                D1 = Point2D.pointBetween(M1, M2, percent);
                D2 = Point2D.pointBetween(M2, M3, percent);
                D3 = Point2D.pointBetween(D1, D2, percent);

                circle_D1.setCenterX(D1.getX());
                circle_D1.setCenterY(D1.getY());
                circle_D2.setCenterX(D2.getX());
                circle_D2.setCenterY(D2.getY());
                circle_D3.setCenterX(D3.getX());
                circle_D3.setCenterY(D3.getY());

                pen_track.setStartX(circle_D1.getCenterX());
                pen_track.setStartY(circle_D1.getCenterY());
                pen_track.setEndX(circle_D2.getCenterX());
                pen_track.setEndY(circle_D2.getCenterY());

                gc.fillOval(D3.getX()-2,D3.getY()-2,4,4);

            }
        };

        tm.start();
        tl.play();

    }

    @FXML
    protected void closeapp() {
        Stage stage = (Stage) label.getScene().getWindow();
        stage.close();
    }
}
