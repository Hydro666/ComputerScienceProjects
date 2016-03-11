package textObjects;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import renderer.Renderer;

import java.util.Map;

/**
 * Created by henry on 3/3/16.
 */
public class Line extends Group {
    // Text Info
//    private datInfo dat;

    // Cursor Info
    private int cursorPosition;
    private Rectangle cursor;
    private Map<Integer, Integer> posToLoc;
    private Map<Integer, Integer> locToPos;

    // Graph Info
    private Group parent;
    private Group renderNode = new Group();
    private Group textNode;
    private Line nextLine;
    private Renderer rend;

    /*public Line(datInfo dat, Group textNodes, Group parent, Renderer rend) {
        this.textNode = textNodes;
        this.parent = parent;
        this.cursor = new Rectangle(rend.getStartX(), rend.getStartY(), 1, rend.ghost.getLayoutBounds().getHeight());
        this.renderNode.getChildren().add(this.textNode);
        this.renderNode.getChildren().add(this.cursor);
        this.getChildren().add(this.renderNode);
        this.parent.getChildren().add(this);
        this.dat = dat;
        this.cursorPosition = dat.getBuffer().getBuffer().size();
        this.rend = rend;
    }

    public Line(datInfo dat, Container cont, Group parent, Renderer rend) {
        this(dat, cont.getNode(), parent, rend);
        this.posToLoc = cont.getPosToLoc();
        this.locToPos = cont.getLocToPos();
    }
    public Line(datInfo dat, Group parent, Renderer rend) {
        this(dat, rend.renderLine(dat), parent, rend);
    }

    public Line() {
        this.nextLine = null;
    }

    public void insertText(String letter, Renderer rend) {
        this.dat.insertChar(this.cursorPosition, letter);
        Container newCont = rend.renderLine(dat);
        replaceText(newCont.getNode());
        locToPos = newCont.getLocToPos();
        posToLoc = newCont.getPosToLoc();
        cursorPosition += 1;

    }*/

    public void insertLine(Line insert) {
        Group hold = this.getParentC();
    }

    public void replaceText(Group replacer) {
        this.renderNode.getChildren().remove(this.textNode);
        this.textNode = replacer;
        this.renderNode.getChildren().add(this.textNode);
    }

    public boolean hasNextLine() {
        return this.nextLine != null;
    }

    public void setNextLine(Line ln) {
        this.nextLine = ln;
    }

    public void setCursorPosition(int pos) {
        this.cursorPosition = pos;
        this.cursor.setX(posToLoc.get(pos));
    }

    public void drawCursor() {
        cursor.setX(posToLoc.get(cursorPosition));
        this.renderNode.getChildren().add(cursor);
        setCursorPosition(cursorPosition + 1);
    }

    public Group getParentC() {
        return this.parent;
    }

    public Line getNextLine() {
        return nextLine;
    }

    public Group getTextNode() {
        return textNode;
    }

    public int getCursorPosition() {
        return cursorPosition;
    }
}
