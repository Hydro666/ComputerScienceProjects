package renderer;

import javafx.scene.Group;
import textObjects.TextBlock;
import textObjects.rawLists.textBuffer;
/**
 * Created by henry on 3/8/16.
 */
public class Render {
    public static Group renderLine(textBuffer list) {
        Group node = new Group();
        int startx = 0;
        for (TextBlock block : list.getBuffer()) {
            block.setPos(startx, 0);
            startx += block.getWidth();
            node.getChildren().add(block);
        }
        return node;
    }
    public void insertNext(String letter) {

    }
}
