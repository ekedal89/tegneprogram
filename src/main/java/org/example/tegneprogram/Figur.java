package org.example.tegneprogram;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;

public abstract class Figur {
    protected Color strokeColor;
    protected Color fillColor;
    protected Shape shape;
    protected Pane tegnePane;

    protected double startX, startY;
    protected boolean isResizing;

    public Figur(Color strokeColor, Color fillColor) {
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
    }

    public abstract void tegn(Pane tegnePane);
    public abstract String getInfo();
    public abstract double getWidth();
    public abstract double getHeight();

    protected abstract void setMouseHandlers();
    protected abstract boolean isNearEdge(double mouseX, double mouseY);
    protected abstract void resize(MouseEvent event);
    protected abstract void move(MouseEvent event);

    /**
     * Metode for å flytte figur helt foran i panelet ved å fjerne det først, så tegne det på nytt
      */
    public void bringToFront() {
        if (shape != null) {
            tegnePane.getChildren().remove(shape);
            tegnePane.getChildren().add(shape);
        }
    }

    public void setOnMouseClick(javafx.event.EventHandler<MouseEvent> handler) {
        shape.setOnMouseClicked(handler);
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
        if (shape != null) {
            shape.setStroke(strokeColor);
        }
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        if (shape != null) {
            shape.setFill(fillColor == null ? Color.TRANSPARENT : fillColor);
        }
    }

    protected String colorTilNavn(Color color) {
        if (color.equals(Color.RED)) return "Rød";
        if (color.equals(Color.BLUE)) return "Blå";
        if (color.equals(Color.GREEN)) return "Grønn";
        if (color.equals(Color.BLACK)) return "Svart";
        if (color.equals(Color.PINK)) return "Rosa";
        if (color.equals(Color.ORANGE)) return "Oransje";
        return "Ingen farge";
    }
}