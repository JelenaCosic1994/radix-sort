package sample;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import javax.swing.*;

public class RectangleNumber extends Group {

    private Text t;

    /**
     * Kontruktor
     * @param text
     * @param width
     * @param height
     * @param colorBorder
     * @param colorInside
     * @param textColor
     * @param isCenterText = da li se text nalazi na sredini pravougaonika, ukoliko je false, onda stoji na samom dnu
     */
    public RectangleNumber(String text, Double width, Double height, Paint colorBorder, Paint colorInside,
                           Paint textColor, boolean isCenterText ) {
        Rectangle r = new Rectangle(width, height);
        r.setStroke(colorBorder);
        r.setFill(colorInside);

        t = new Text();
        t.setText(text);
        if(isCenterText) {
            t.setTranslateX(width / 5);
            t.setTranslateY(height / 2);
        } else{
            t.setTranslateX(width / 2);
            t.setTranslateY(height*1.1);
        }
        t.setFill(textColor);

        getChildren().addAll(r, t);
    }

    public String getString() {
        return t.getText();
    }

    public void setString(String t) {
        this.t.setText(t);
    }
}
