package util;

/**
 * Created by henry on 3/6/16.
 */
public class FastListTest {
    public static void main(String[] args) {
        FastList<Integer> list = new FastList<>();
        list.insertNode(1);
        list.insertNode(2);
        list.insertNode(3);
        for (Integer entry : list) {
            System.out.println(entry);
        }
        list.removeCurrent();
        list.removeCurrent();
        list.removeCurrent();
        list.removeCurrent();
    }
}
