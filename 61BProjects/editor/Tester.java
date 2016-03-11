import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.geometry.VPos;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import renderer.Render;
import textObjects.TextBlock;
import textObjects.rawLists.textBuffer;
import textObjects.Lin;

import java.util.ArrayList;

/**
 * Created by henry on 3/8/16.
 */
public class Tester extends Application {

    Group root = new Group();

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
        private Lin list;
        public KeyEventHandler(Lin lst) {
            this.list = lst;
        }

        public void handleCharacter(String letter) {

        }

        @Override
        public void handle(KeyEvent event) {
            if (event.getEventType() == KeyEvent.KEY_TYPED) {
                String character = event.getCharacter();
                /* Normal keystrokes */
                if (event.getCharacter().equals("\r")) {
                    list = list.newLin();
                } else if (character.length() > 0 && character.charAt(0) != 8) {
                    list.badAddChar(character);
//                    root.getChildren().remove(list);
//                    root.getChildren().add(Render.renderLine(list));
                    System.out.println(character);
                    event.consume();
                } else if (character.charAt(0) == 8) {
                    if (!list.getBuff().getBuffer().isEmpty()) {
                        list.badRemChar();
//                        root.getChildren().remove(list);
//                        root.getChildren().add(Render.renderLine(list));
                    }
                    event.consume();
                }
                /*( Handle enter key presses for new lines )*/
            } else if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                if (event.isShortcutDown()) {

                }
            }
        }
    }

    private class TestKeyEventHandler implements EventHandler<KeyEvent> {

        private TextBlock block;
        private Rectangle compareBlock;

        public TestKeyEventHandler(TextBlock block, Rectangle compareBlock) {
            this.block = block;
            this.compareBlock = compareBlock;
            compareBlock.setWidth(block.getWidth());
            compareBlock.setHeight(block.getHeight());
        }
        @Override
        public void handle(KeyEvent event) {
            String character = event.getCharacter();
            if (event.getEventType() == KeyEvent.KEY_TYPED) {
                root.getChildren().remove(block);
                block = new TextBlock(character);
                block.setParameters("Verdana", 20);
                updateCompare();
                block.drawCursor();
                block.setPos(250, 250);
                root.getChildren().add(block);
                block.toFront();
            }
        }
        private void updateCompare() {
            this.compareBlock.setHeight(block.getHeight());
            this.compareBlock.setWidth(block.getWidth());
        }
    }

    private void updateAll(int size, ArrayList<Lin> linHolder) {
        for (Lin ln : linHolder) {
            
        }
    }
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(root, 500, 500, Color.WHITE);
        textBuffer list = new textBuffer(20, "Verdana");
        ArrayList<Lin> linHolder = new ArrayList<>();
        Lin line = new Lin(list);
        linHolder.add(line);
        /*
        TextBlock test = new TextBlock("H");
        test.setParameters("Verdana", 20);
        Rectangle compareBlock = new Rectangle(250, 250, test.getWidth(), test.getHeight());
        compareBlock.setFill(Color.AQUA);
        test.drawCursor();
        test.setPos(250, 250);
        root.getChildren().addAll(test, compareBlock);
        test.toFront();
        */
        root.getChildren().add(line);
        KeyEventHandler handle = new KeyEventHandler(line);
        //TestKeyEventHandler testHandle = new TestKeyEventHandler(test, compareBlock);
        /*for (String i : new String[]{"H", "i", " ", "T", "h", "e"}) {
            list.insertCharacter(i);
        }
        int startx = 0;
        /*for (TextBlock block : list) {
            block.setPos(startx, 0);
            startx += block.getWidth();
            root.getChildren().add(block);
        }*/
        scene.setOnKeyTyped(handle);

        stage.setTitle("Test");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
