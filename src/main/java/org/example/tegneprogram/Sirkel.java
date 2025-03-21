package org.example.tegneprogram;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;

public class Sirkel extends Figur {
    private final Circle circleShape;

    public Sirkel(double centerX, double centerY, double radius, Color strokeColor, Color fillColor) {
        super(strokeColor, fillColor);
        circleShape = new Circle(centerX, centerY, radius);
        circleShape.setStroke(strokeColor);
        circleShape.setFill(fillColor);
        this.shape = circleShape;
        setMouseHandlers();
    }

    @Override
    public void tegn(Pane tegnePane) {
        this.tegnePane = tegnePane;
        tegnePane.getChildren().add(circleShape);
    }

    @Override
    public String getInfo() {
        return "Sirkel på (" + circleShape.getCenterX()
                + ", " + circleShape.getCenterY()
                + ")\nRadius: " + circleShape.getRadius() +
                ". \nStroke: " + colorTilNavn(strokeColor)
                + ". \nFyll: " + colorTilNavn(fillColor);
    }

    @Override
    public double getWidth() {
        return circleShape.getRadius() * 2;
    }

    @Override
    public double getHeight() {
        return circleShape.getRadius() * 2;
    }

    /**
     * Metode for å endre størrelse dersom musen trykker inn på et spesifisert område på objektet, og resizer dersom denne er true
     */
    @Override
    protected void setMouseHandlers() {
        shape.setOnMousePressed(e -> {
            startX = e.getX();
            startY = e.getY();
            isResizing = isNearEdge(e.getX(), e.getY());
        });

        shape.setOnMouseDragged(e -> {
            if (isResizing) {
                resize(e);
            } else {
                move(e);
            }
        });
    }

    /**
     * Metode for å returne true eller false utfra hvor musen klikker på objektet. Matte formel hentet fra ChatGPT!
     * @param mouseX x koordinatet til musen
     * @param mouseY y koordinatet til musen
     * @return true eller false utfra hvor brukeren trykker musen
     */
    @Override
    protected boolean isNearEdge(double mouseX, double mouseY) {
        double distance = Math.hypot(mouseX - circleShape.getCenterX(), mouseY - circleShape.getCenterY());
        return distance >= circleShape.getRadius() - 10 && distance <= circleShape.getRadius();
    }

    /**
     * Metode for å endre størrelse utfra hvor langt ut/inn man drar musen. Matte funksjon hentet fra ChatGPT!
     * @param event
     */
    @Override
    protected void resize(MouseEvent event) {
        double newRadius = Math.hypot(event.getX() - circleShape.getCenterX(), event.getY() - circleShape.getCenterY());
        circleShape.setRadius(newRadius);
    }

    @Override
    protected void move(MouseEvent event) {
        double deltaX = event.getX() - startX;
        double deltaY = event.getY() - startY;
        circleShape.setCenterX(circleShape.getCenterX() + deltaX);
        circleShape.setCenterY(circleShape.getCenterY() + deltaY);
        startX = event.getX();
        startY = event.getY();
    }
}