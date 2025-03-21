package org.example.tegneprogram;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;

public class Rektangel extends Figur {
    private final Rectangle rectShape;

    public Rektangel(double x, double y, double width, double height, Color strokeColor, Color fillColor) {
        super(strokeColor, fillColor);
        rectShape = new Rectangle(x, y, width, height);
        rectShape.setStroke(strokeColor);
        rectShape.setFill(fillColor);
        this.shape = rectShape;
        setMouseHandlers();
    }

    @Override
    public void tegn(Pane tegnePane) {
        this.tegnePane = tegnePane;
        tegnePane.getChildren().add(rectShape);
    }

    @Override
    public String getInfo() {
        return "Rektangel på (" + rectShape.getX() + ", " + rectShape.getY()
                + ")\nBredde: " + rectShape.getWidth() +
                "\nHhøyde: " + rectShape.getHeight()
                + ". \nStroke: " + colorTilNavn(strokeColor)
                + ". \nFyll: "
                + colorTilNavn(fillColor);
    }

    @Override
    public double getWidth() {
        return rectShape.getWidth();
    }

    @Override
    public double getHeight() {
        return rectShape.getHeight();
    }

    // Metode for å håndtere endring av størrelse på objektet dersom musen plasseres innenfor gitt område i objektet
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
        double rectX = rectShape.getX();
        double rectY = rectShape.getY();
        double rectWidth = rectShape.getWidth();
        double rectHeight = rectShape.getHeight();
        return (mouseX >= rectX + rectWidth - 10 && mouseX <= rectX + rectWidth)
                || (mouseY >= rectY + rectHeight - 10 && mouseY <= rectY + rectHeight);
    }

    /**
     * Metode for å endre størrelse utfra hvor langt ut/inn man drar musen. Matte funksjon hentet fra ChatGPT!
     * @param event
     */
    @Override
    protected void resize(MouseEvent event) {
        rectShape.setWidth(event.getX() - rectShape.getX());
        rectShape.setHeight(event.getY() - rectShape.getY());
    }

    @Override
    protected void move(MouseEvent event) {
        double deltaX = event.getX() - startX;
        double deltaY = event.getY() - startY;
        rectShape.setX(rectShape.getX() + deltaX);
        rectShape.setY(rectShape.getY() + deltaY);
        startX = event.getX();
        startY = event.getY();
    }
}