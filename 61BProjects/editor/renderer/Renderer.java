package renderer;


import java.util.*;

import util.FastList;
import textObjects.rawLists.textBuffer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.Group;
import javafx.scene.transform.Translate;
import textObjects.TextBlock;
/**
 * Is going to take stringLine objects and transform them into container objects to then be handeled
 * in the editor class
 *
 * Instances take in the font style and size.
 *
 * TODO: Make wrap computation happen either here or in the editor.
 * Created by henry on 2/29/16.
 */
public class Renderer {

    private static int size = 20;
    private final String fontStyle;
    private final int START = 10;
    private static int startX = 10;
    private static int startY = 10;
    private static int windowWidth = 500;
    public Text ghost;

    public static void setSize(int newSize) {
        size = newSize;
    }

    public static void setStartX(int newX) {
        startX = newX;
    }

    public static void setStartY(int newY) {
        startY = newY;
    }

    public static void setWindowWidth(int newW) {
        windowWidth = newW;
    }


    public Renderer(int size, String style, int startX, int startY, int windowWidth) {
        this.fontStyle = style;
        setSize(size);
        setStartX(startX);
        setStartY(startY);
        setWindowWidth(windowWidth);
        this.ghost = new Text(startX, startY, "a");
    }

    /*
    private List<Text> prepareBuffer(textBuffer buffer) {
        List<Text> buffer = new LinkedList<>();
        for (String letter : raw.getList()) {
            Text buffed = new Text(letter);
            buffer.add(prepText(buffed));
        }
        return buffer;
    }
    */

    private Text prepText(Text letter) {
        letter.setFont(Font.font(fontStyle, size));
        letter.setFill(Color.BLACK);
        letter.setX(startX);
        letter.setY(startY);
        return letter;
    }
    public double getWrap(Container item) {
        return 0;
    }

    public Group assembleLines(List<Container> tree) {
        // TODO: Rewrite to handle stringLineHolder object instead. Is not useful as it stands
        Iterator<Container> iter = tree.iterator();
        try {
            if (!iter.hasNext()){
                throw new RuntimeException("Has no lines");
            }
            Container cont = iter.next();
            Container anchor = cont;
            while (iter.hasNext()) {
                Container attach = iter.next();
                cont.getNode().getChildren().add(attach.getNode());
                attach.getNode().getTransforms().add(new Translate(0, getWrap(attach)));
                cont = attach;
            }
        } catch(RuntimeException except) {
            return new Group();
        }
        return new Group();
    }



    public Container renderLine(textBuffer data) {
        /**
         * Takes in a stringLine and returns a renderContainer with the rendered node along with a
         * map from physical coordinates to cursor locations for editing the stringLine,
         */
        int place = startX;
        int position = 0;
        Group newLine = new Group();
        Container newCont = new Container(newLine);
        for (TextBlock letter : data.getBuffer()) {
            Text prepped = prepText(letter.getCharacter());
            int width = (int) Math.round(prepped.getLayoutBounds().getWidth());
            if (width + position > this.windowWidth) {
                /* ( Wrap the text) */
            }
            prepped.setX(position);
            position += 1;
            place += width;
            newCont.setPosition(width, position);
            newCont.getNode().getChildren().add(prepped);
        }
        return newCont;
    }


    public int getSize() {
        return size;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public class Container {
    /**
    * Will be a catch all for all data relating to a specific line such as the raw data, the node
    * created, and the array of character lengths and a map from character length to position (To support
    * clicking).
    *
    * SHOULD ONLY BE MADE FROM RENDERTEXT
    *
    */
        private Group node;
        private List<Integer> locations;
        private Map<Integer, Integer> locToPos;
        private Map<Integer, Integer> posToLoc;
        public Container(Group node) {
            this.node = node;
            this.locations = new ArrayList<>();
            this.locToPos = new HashMap<>();
            this.locToPos.put(startX, 0);
            this.posToLoc = new HashMap<>();
            this.posToLoc.put(0, startX);
        }

        public Container() {
        }

        public void setNode(Group replace) {
            this.node = replace;
        }

        public void setPosition(int location, int position) {
            locToPos.put(location, position);
            posToLoc.put(position, location);
            locations.add(location);
        }

        public List<Integer> getLetterLocations() {
            return locations;
        }

        public Group getNode() {
            return node;
        }

        public Map<Integer, Integer> getPosToLoc() {
            return this.posToLoc;
        }

        public Map<Integer, Integer> getLocToPos() {
            return this.locToPos;
        }

    }
}
