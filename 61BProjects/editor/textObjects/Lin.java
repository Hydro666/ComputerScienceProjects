package textObjects;

import javafx.scene.Group;
import javafx.scene.transform.Translate;
import renderer.Render;
import textObjects.rawLists.textBuffer;

/**
 * Created by henry on 3/8/16.
 */
public class Lin extends Group {
    private textBuffer buff;
    private Group textNode;
    private Group sublines;
    private Lin nextLin;
    private int lineNumber;
    private int sublineNumber;

    public textBuffer getBuff() {
        return buff;
    }

    public Group getTextNode() {
        return textNode;
    }

    public Lin(textBuffer list) {
        this.buff = list;
        buff.drawCursor();
        textNode = Render.renderLine(list);
        this.getChildren().add(textNode);
    }

    public void updateSize(int newSize) {

    }

    private void badUpdateTextNode() {
        this.getChildren().remove(textNode);
        textNode = Render.renderLine(buff);
        this.getChildren().add(textNode);
    }

    public void badAddChar(String letter) {
        buff.insertCharacter(letter);
        badUpdateTextNode();
    }

    public void badRemChar() {
        buff.deleteChatacter();
        badUpdateTextNode();
    }

    public Lin newLin() {
        buff.removeCursor();
        textBuffer newBuff = buff.fastNewBuff();
        nextLin = new Lin(newBuff);
        nextLin.getTransforms().add(new Translate(0, (1 + sublineNumber) * buff.getHeight()));
        this.getChildren().add(nextLin);
        return  nextLin;
    }
}
