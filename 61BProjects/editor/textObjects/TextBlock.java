package textObjects;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.Group;
import javafx.geometry.VPos;
import javafx.scene.transform.Translate;
/**
 * Created by henry on 3/8/16.
 */
public class TextBlock extends Group {
    public Text character;
    public Rectangle cursor;
    public Translate position;
    public boolean isCursorOn = false;

    public TextBlock(String letter) {
        this.character = new Text(letter);
        this.character.setTextOrigin(VPos.TOP);
        this.cursor = new Rectangle(getWidth(), 0, 1, getHeight());
        System.out.println(getWidth());
        System.out.println(cursor.getX());
        this.getChildren().addAll(this.character);
        this.position = new Translate(0, 0);
    }

    private void updateCursor() {
        this.cursor.setX(getWidth());
        this.cursor.setHeight(getHeight());
    }

    public void drawCursor() {
        if (isCursorOn) {
            return;
        }
        this.getChildren().add(cursor);
        isCursorOn = true;
    }

    public void removeCursor() {
        if (isCursorOn) {
            this.getChildren().remove(cursor);
            isCursorOn = false;
        }
    }

    public double getWidth() {
        return Math.round(character.getLayoutBounds().getWidth());
    }

    public double getHeight() {
        return Math.round(character.getLayoutBounds().getHeight());
    }

    public void setParameters(String font, double size) {
        character.setFont(Font.font(font, size));
        updateCursor();
    }

    public void setPos(double xpos, double ypos) {
        this.getTransforms().remove(position);
        position = new Translate(xpos, ypos);
        this.getTransforms().add(position);
    }
    public Text getCharacter() {
        return this.character;
    }
}
