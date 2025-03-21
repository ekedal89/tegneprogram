package org.example.tegneprogram;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;

public class Linje extends Figur {
    private final Line lineShape;

    public Linje(double startX, double startY, double endX, double endY, Color strokeColor) {
        super(strokeColor, null); // Linjer har ingen fyllfarge
        lineShape = new Line(startX, startY, endX, endY);
        lineShape.setStroke(strokeColor);
        this.shape = lineShape;
        setMouseHandlers();
    }

    @Override
    public void tegn(Pane tegnePane) {
        this.tegnePane = tegnePane;
        tegnePane.getChildren().add(lineShape);
    }

    @Override
    public String getInfo() {
        return "Linje fra (" + lineShape.getStartX() + ", "
                + lineShape.getStartY() + ") til ("
                + lineShape.getEndX()
                + ", " + lineShape.getEndY() + ")";
    }

    @Override
    public double getWidth() {
        return Math.abs(lineShape.getEndX() - lineShape.getStartX());
    }

    @Override
    public double getHeight() {
        return Math.abs(lineShape.getEndY() - lineShape.getStartY());
    }

    public void setEndPoint(double x, double y) {
        lineShape.setEndX(x);
        lineShape.setEndY(y);
    }

    /**
     * Metode for å flytte linjen dersom musen presses ned og dras en annen plass
     */
    @Override
    protected void setMouseHandlers() {
        shape.setOnMousePressed(ex -> {
            startX = ex.getX();
            startY = ex.getY();
        });
        shape.setOnMouseDragged(ex -> move(ex));
    }

    @Override
    protected boolean isNearEdge(double mouseX, double mouseY) {
        return false;
    }

    @Override
    protected void resize(MouseEvent event) {
    }

    @Override
    protected void move(MouseEvent event) {
        double deltaX = event.getX() - startX;
        double deltaY = event.getY() - startY;

        lineShape.setStartX(lineShape.getStartX() + deltaX);
        lineShape.setStartY(lineShape.getStartY() + deltaY);
        lineShape.setEndX(lineShape.getEndX() + deltaX);
        lineShape.setEndY(lineShape.getEndY() + deltaY);

        startX = event.getX();
        startY = event.getY();
    }
}