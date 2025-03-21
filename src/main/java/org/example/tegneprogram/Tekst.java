package org.example.tegneprogram;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;

public class Tekst extends Figur {
    private final Text textShape;

    public Tekst(double x, double y, String content, int size, Color strokeColor) {
        super(strokeColor, strokeColor);
        textShape = new Text(x, y, content);
        textShape.setFont(javafx.scene.text.Font.font(size));
        textShape.setFill(strokeColor);
        this.shape = textShape;
        setMouseHandlers();
    }

    @Override
    public void tegn(Pane tegnePane) {
        this.tegnePane = tegnePane;
        tegnePane.getChildren().add(textShape);
    }

    @Override
    public String getInfo() {
        return "Tekst: " + textShape.getText() + " \nPå (" + textShape.getX() + ", " + textShape.getY() + ")";
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }

    public void setContent(String content) {
        textShape.setText(content);
    }

    public void setSize(int size) {
        textShape.setFont(javafx.scene.text.Font.font(size));
    }

    public void move(double x, double y) {
        textShape.setX(x);
        textShape.setY(y);
    }

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

    // Alltid false fordi tekst må oppdateres ved å skrive inn ny skriftstørrelse
    @Override
    protected boolean isNearEdge(double mouseX, double mouseY) {
        return false;
    }

    // Tom metode fordi tekst ikke kan oppdateres via muse event, må oppgi skriftstørrelse
    @Override
    protected void resize(MouseEvent event) {
    }

    // Metode for å flytte teksten rundt i panelet utfra muse event
    @Override
    protected void move(MouseEvent event) {
        double deltaX = event.getX() - startX;
        double deltaY = event.getY() - startY;
        textShape.setX(textShape.getX() + deltaX);
        textShape.setY(textShape.getY() + deltaY);
        startX = event.getX();
        startY = event.getY();
    }
}