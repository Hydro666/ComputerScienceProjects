
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import renderer.Renderer;
import renderer.lineContainerHolder;
import textObjects.Line;

/**
 * A JavaFX application that displays the letter the user has typed most recently in the center of
 * the window. Pressing the up and down arrows causes the font size to increase and decrease,
 * respectively.
 */
public class Editor extends Application {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    private static final int START_POS_X = 10;
    private static final int START_POS_Y = 10;

    public int size = 12;
    public String style = "Verdana";
    public Renderer rend = new Renderer(size, style, START_POS_X, START_POS_Y, WINDOW_WIDTH);
    public Text letterTest = new Text(0, 0, "H");


    public lineContainerHolder containers = new lineContainerHolder();

    public List<Line> lineNodes = new ArrayList<>();

//    public Map<Line, stringLine> maps = new HashMap<>();

    private class KeyEventHandler implements EventHandler<KeyEvent> {
        /**
         * The key event will be for that line only
         *
         * IE the set of raw data lines and the containers
         *
         * When the cursor is placed somewhere triggering a click event,
         * a new Handler will be instantiated at that position and line
         */
        private int position;
        private Line line;
        public KeyEventHandler(int position, Line ln) {
            this.line = ln;
            this.position = position;
            ln.setCursorPosition(position);
        }
        public void shiftFocus(Line ln) {
            // Will move the line to a new one below it
            this.line = ln;
            this.position = 0;
        }

        public void handelPlus() {
            rend.setSize(rend.getSize() + 4);
        }
        public void handelMinus() {
            rend.setSize(rend.getSize() - 4);
        }
        public void handleCharacter(String letter) {

        }

        @Override
        public void handle(KeyEvent event) {
            if (event.getEventType() == KeyEvent.KEY_TYPED) {
                String character = event.getCharacter();
                /* Normal keystrokes */
                System.out.println(event.getCharacter());
                if (event.getCharacter().equals("\r")) {
                    /*[ Make a new line ]*/
                    // TODO: All of this should really be put into a method
                    Line newLine = new Line();
                    lineNodes.add(newLine);
                    //Container newCont = new Container(new stringLine(), new Group());
                    //containers.addContainer(newCont);
                    //maps.put(newLine, newCont);
                    // TODO
//                    Translate newTrans = new Translate(0, height * 1);
//                    newLine.getTransforms().add(newTrans);
                    if (line.hasNextLine()) {
                        newLine.getChildren().add(line.getNextLine());
                        newLine.setNextLine(line.getNextLine());
                        line.getChildren().remove(line.getNextLine());
                    }
                    line = newLine;
                    position = 0;
                } else if (character.length() > 0 && character.charAt(0) != 8) {
//                    line.insertText(character, rend);
                    System.out.println(character);
                    position += 1;
                    event.consume();
                } else if (character.charAt(0) == 8) {
                    //backspaceAll(line, position);
                    position -= 1;
                    event.consume();
                }
                /*( Handle enter key presses for new lines )*/
            } else if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                if (event.isShortcutDown()) {

                }
            }
        }
    }
    /** An EventHandler to handle keys that get pressed. */

    @Override
    public void start(Stage primaryStage) {
        // Create a Node that will be the parent of all things displayed on the screen.
        Group root = new Group();
        letterTest.setFont(Font.font(style, size));
//        height = (int) rend.ghost.getLayoutBounds().getHeight();
//        datInfo firstDat = new datInfo();
//        firstDat.insertChar(0, "H");
//        Line firstLine = new Line(firstDat, root, rend);


        //maps.put(firstLine, firstCont);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.WHITE);

        KeyEventHandler handle = new KeyEventHandler(0, new Line());
        scene.setOnKeyTyped(handle);

        primaryStage.setTitle("Single Letter Display Simple");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
