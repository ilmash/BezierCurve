package com.ilmash.bezier;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class MainController {
    private static AnimOptions animationOptions;
    @FXML
    private Label label;
    @FXML
    private Canvas canv;
    @FXML
    private Circle circle_HLeft, circle_HRight, circle_M1, circle_M2, circle_M3, circle_D1, circle_D2, circle_D3;
    @FXML
    private Line pilot_left, pilot_middle, pilot_right, left_arm, right_arm, pen_track;
    private Point2D Ref, M1, M2, M3, D1, D2, D3;
    private Timeline tl;
    private AnimationTimer tm;
    private GraphicsContext gc;

    public static AnimOptions getAnimationOptions() {
        return animationOptions;
    }

    @FXML
    protected void buttonAction() {
        if (tl != null && tl.getStatus() == Animation.Status.RUNNING) {
            tl.stop();
            tm.stop();
            sceneReset();
            return;
        }
        label.setText("hello");

        final DoubleProperty M1x = new SimpleDoubleProperty();
        final DoubleProperty M1y = new SimpleDoubleProperty();
        final DoubleProperty M2x = new SimpleDoubleProperty();
        final DoubleProperty M2y = new SimpleDoubleProperty();
        final DoubleProperty M3x = new SimpleDoubleProperty();
        final DoubleProperty M3y = new SimpleDoubleProperty();

        final double baseDistance = Point2D.distance(new Point2D(circle_M1.getCenterX(), circle_M1.getCenterY()),
                new Point2D(circle_M2.getCenterX(), circle_M2.getCenterY()));
        Ref = new Point2D(circle_M1.getCenterX(), circle_M1.getCenterY());

        tl = new Timeline(
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
                        Duration.seconds(animationOptions.getDuration()),
                        new KeyValue(M1x, pilot_left.getEndX()),
                        new KeyValue(M1y, pilot_left.getEndY()),
                        new KeyValue(M2x, pilot_middle.getEndX()),
                        new KeyValue(M2y, pilot_middle.getEndY()),
                        new KeyValue(M3x, pilot_right.getEndX()),
                        new KeyValue(M3y, pilot_right.getEndY())
                )
        );
        tl.setAutoReverse(true);
        tl.setCycleCount(animationOptions.getRepeat() * 2);

        tm = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateInterpolatedElements(M1x, M1y, M2x, M2y, M3x, M3y);
                calculatePenTrackPosition(M1x, M1y, M2x, M2y, M3x, M3y, baseDistance);
                drawPenTrackAndCurve(gc);
            }
        };

        tm.start();
        tl.play();

    }

    private void calculatePenTrackPosition(DoubleProperty m1x, DoubleProperty m1y, DoubleProperty m2x, DoubleProperty m2y, DoubleProperty m3x, DoubleProperty m3y, double baseDistance) {
        M1 = new Point2D(m1x.doubleValue(), m1y.doubleValue());
        M2 = new Point2D(m2x.doubleValue(), m2y.doubleValue());
        M3 = new Point2D(m3x.doubleValue(), m3y.doubleValue());
        D1 = new Point2D();
        D2 = new Point2D();
        D3 = new Point2D();
        double percent = Point2D.distance(M1, Ref) / baseDistance;
        D1 = Point2D.pointBetween(M1, M2, percent);
        D2 = Point2D.pointBetween(M2, M3, percent);
        D3 = Point2D.pointBetween(D1, D2, percent);
    }

    private void drawPenTrackAndCurve(GraphicsContext gc) {
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

        gc.fillOval(D3.getX() - 2, D3.getY() - 2, 4, 4);
    }

    private void updateInterpolatedElements(DoubleProperty m1x, DoubleProperty m1y, DoubleProperty m2x, DoubleProperty m2y, DoubleProperty m3x, DoubleProperty m3y) {
        circle_M1.setCenterX(m1x.doubleValue());
        circle_M1.setCenterY(m1y.doubleValue());
        circle_M2.setCenterX(m2x.doubleValue());
        circle_M2.setCenterY(m2y.doubleValue());
        circle_M3.setCenterX(m3x.doubleValue());
        circle_M3.setCenterY(m3y.doubleValue());

        left_arm.setStartX(circle_M1.getCenterX());
        left_arm.setStartY(circle_M1.getCenterY());
        left_arm.setEndX(circle_M2.getCenterX());
        left_arm.setEndY(circle_M2.getCenterY());

        right_arm.setStartX(circle_M2.getCenterX());
        right_arm.setStartY(circle_M2.getCenterY());
        right_arm.setEndX(circle_M3.getCenterX());
        right_arm.setEndY(circle_M3.getCenterY());
    }

    @FXML
    protected void closeapp() {
        Stage stage = (Stage) label.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void initialize() {
        System.out.println("hello, init method is working");
        gc = canv.getGraphicsContext2D();
        animationOptions = new AnimOptions();
        sceneReset();
    }

    public void openDialog() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("DialogWindow.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            label.getScene().getWindow().hide();
            stage.setTitle("Animation options");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sceneReset() {
        circle_HLeft.setCenterX(animationOptions.getLeftHandle().getX());
        circle_HLeft.setCenterY(animationOptions.getLeftHandle().getY());
        circle_HRight.setCenterX(animationOptions.getRightHandle().getX());
        circle_HRight.setCenterY(animationOptions.getRightHandle().getY());

        circle_M1.setCenterX(75.0);
        circle_M1.setCenterY(250.0);
        circle_D1.setCenterX(75.0);
        circle_D1.setCenterY(250.0);

        circle_M2.setCenterX(animationOptions.getLeftHandle().getX());
        circle_M2.setCenterY(animationOptions.getLeftHandle().getY());
        circle_D2.setCenterX(animationOptions.getLeftHandle().getX());
        circle_D2.setCenterY(animationOptions.getLeftHandle().getY());
        circle_D3.setCenterX(animationOptions.getLeftHandle().getX());
        circle_D3.setCenterY(animationOptions.getLeftHandle().getY());
        circle_M3.setCenterX(animationOptions.getRightHandle().getX());
        circle_M3.setCenterY(animationOptions.getRightHandle().getY());

        setLineProperty(pilot_left, 75.0, 250.0, circle_HLeft.getCenterX(), circle_HLeft.getCenterY());
        setLineProperty(pilot_middle, circle_HLeft.getCenterX(), circle_HLeft.getCenterY(), circle_HRight.getCenterX(), circle_HRight.getCenterY());
        setLineProperty(pilot_right, circle_HRight.getCenterX(), circle_HRight.getCenterY(), 425.0, 250.0);
        setLineProperty(left_arm, 75.0, 250.0, circle_HLeft.getCenterX(), circle_HLeft.getCenterY());
        setLineProperty(right_arm, circle_HLeft.getCenterX(), circle_HLeft.getCenterY(), circle_HRight.getCenterX(), circle_HRight.getCenterY());
        setLineProperty(pen_track, 75.0, 250.0, circle_HLeft.getCenterX(), circle_HLeft.getCenterY());

        gc.clearRect(0, 0, canv.getWidth(), canv.getHeight());
    }

    private void setLineProperty(Line line, double sx, double sy, double ex, double ey) {
        line.setStartX(sx);
        line.setStartY(sy);
        line.setEndX(ex);
        line.setEndY(ey);
    }
}
