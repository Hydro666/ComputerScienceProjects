package textObjects.rawLists;

import java.util.Iterator;

import javafx.scene.Group;
import javafx.scene.text.Font;
import textObjects.TextBlock;
import util.FastList;
/**
 * Created by henry on 3/5/16.
 */
public class textBuffer implements Iterable<TextBlock> {
    private FastList<TextBlock> buffer;
    private static int size;
    private static String style;
    private TextBlock sentinelLike = new TextBlock(" ");

    public textBuffer(int size, String style) {
        this.buffer = new FastList<>();
        sentinelLike.setParameters(style, size);
        this.buffer.insertNode(sentinelLike);
        this.size = size;
        this.style = style;
    }
    public static textBuffer fastNewBuff() {
        return new textBuffer(size, style);
    }
    public void removeCursor() {
        getLetterUnderCursor().removeCursor();
    }
    public void drawCursor() {
        getLetterUnderCursor().drawCursor();
    }
    public void insertCharacter(String textLetter) {
        insertBlock(new TextBlock(textLetter));
    }
    public void insertBlock(TextBlock block) {
        removeCursor();
        this.buffer.insertNode(block);
        block.setParameters(style, size);
        block.drawCursor();
    }
    public void deleteChatacter() {
        if (getLetterUnderCursor() == sentinelLike) {
            return;
        }
        removeCursor();
        this.buffer.removeCurrent();
        drawCursor();
    }
    public void rightArrow() {
        this.buffer.nextEntry();
    }
    public void leftArrow() {
        this.buffer.previousEntry();
    }
    public Iterator<TextBlock> iterator() {
        return this.buffer.iterator();
    }
    public FastList<TextBlock> getBuffer() {
        return this.buffer;
    }
    public TextBlock getLetterUnderCursor() {
        return this.buffer.getCurrent();
    }
    public TextBlock getLastLetter() {
        return this.buffer.getPrev();
    }
    public double getHeight() {
        return sentinelLike.getHeight();
    }
    public void mouseClick(int position) {

    }
}
