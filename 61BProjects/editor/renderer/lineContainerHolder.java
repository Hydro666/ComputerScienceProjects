package renderer;

import java.util.List;
import renderer.Renderer.Container;
import java.util.ArrayList;
/**
 * Created by henry on 3/2/16.
 */
public class lineContainerHolder {
    private List<Container> list;

    public lineContainerHolder() {
        this.list = new ArrayList<>();
    }

    public void addContainer(Container node) {
        list.add(node);
    }
    public void insertContainer(int position, Container node) {
        list.add(position, node);
    }

    public List getList() {
        return list;
    }
}
