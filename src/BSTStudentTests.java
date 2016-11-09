import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.TIMEOUT;

import java.util.*;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

/**
 * Basic tests for the implementation of a BST.
 *
 * @author gbianco6
 * @author gkimble3
 * @author jlukose6
 * @version 2.0
 */
public class BSTStudentTests {

    private BST<Integer> bst;
    public static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        bst = new BST<>();
    }

    @Test(timeout = TIMEOUT)
    public void testBSTCollection() {
        System.out.println("Testing constructor with a collection...");
        ArrayList<Integer> toAdd = new ArrayList<>();
        assertEquals(0, toAdd.size());

        toAdd.add(10);
        toAdd.add(-1);
        toAdd.add(13);
        toAdd.add(15);
        toAdd.add(5);

        assertEquals(5, toAdd.size());

        bst = new BST<>(toAdd);
        assertEquals(5, bst.size());

        BSTNode<Integer> root = bst.getRoot();
        assertNotNull(root);
        assertEquals((Integer) 10, root.getData());

        BSTNode<Integer> traverse = root.getLeft();
        assertNotNull(traverse);
        assertEquals((Integer) (-1), traverse.getData());

        traverse = traverse.getRight();
        assertNotNull(traverse);
        assertEquals((Integer) 5, traverse.getData());

        traverse = traverse.getRight();
        assertNull(traverse);

        traverse = root.getRight();
        assertNotNull(traverse);
        assertEquals((Integer) 13, traverse.getData());

        traverse = traverse.getRight();
        assertNotNull(traverse);
        assertEquals((Integer) 15, traverse.getData());

        traverse = traverse.getLeft();
        assertNull(traverse);

        System.out.println("Testing edge case: collection has 1 element...");
        toAdd = new ArrayList<>();
        assertEquals(toAdd.size(), 0);
        toAdd.add(1);
        bst = new BST<>(toAdd);
        assertEquals(1, bst.size());

        System.out.println("Testing edge case: collection has 0 elements...");
        toAdd = new ArrayList<>();
        assertEquals(toAdd.size(), 0);
        bst = new BST<>(toAdd);
        assertEquals(0, bst.size());
        System.out.println("Finished constructor with collection!\n");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBSTCollectionIsNull() {
        bst = new BST<>(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBSTCollectionHasNullElement() {
        ArrayList<Integer> toAdd = new ArrayList<>();
        toAdd.add(3);
        toAdd.add(null);

        bst = new BST<>(toAdd);
    }

    @Test(timeout = TIMEOUT)
    public void testBSTAdd() {
        System.out.println("Testing add...");
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(10);
        bst.add(-1);
        bst.add(13);
        bst.add(15);
        bst.add(5);
        assertEquals(5, bst.size());

        BSTNode<Integer> root = bst.getRoot();
        assertNotNull(root);
        assertEquals((Integer) 10, root.getData());

        BSTNode<Integer> traverse = root.getLeft();
        assertNotNull(traverse);
        assertEquals((Integer) (-1), traverse.getData());

        traverse = traverse.getRight();
        assertNotNull(traverse);
        assertEquals((Integer) 5, traverse.getData());

        traverse = traverse.getRight();
        assertNull(traverse);

        traverse = root.getRight();
        assertNotNull(traverse);
        assertEquals((Integer) 13, traverse.getData());

        traverse = traverse.getRight();
        assertNotNull(traverse);
        assertEquals((Integer) 15, traverse.getData());

        traverse = traverse.getLeft();
        assertNull(traverse);

        System.out.println("Testing edge case: element"
                + " added is the only element...");
        bst = new BST<>();
        assertEquals(0, bst.size());
        bst.add(1);
        assertEquals((Integer) 1, bst.getRoot().getData());
        assertEquals(1, bst.size());
        System.out.println("Finished add!\n");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBSTAddNullData() {
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(null);
    }

    @Test(timeout = TIMEOUT)
    public void testBSTGet() throws Exception {
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(50);
        bst.add(25);
        bst.add(10);
        bst.add(75);
        bst.add(60);
        bst.add(70);

        assertEquals((Integer) 50, bst.get(50));
        assertEquals((Integer) 25, bst.get(25));
        assertEquals((Integer) 10, bst.get(10));
        assertEquals((Integer) 75, bst.get(75));
        assertEquals((Integer) 60, bst.get(60));
        assertEquals((Integer) 70, bst.get(70));
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testBSTGetDataNotFound() {
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(3);
        bst.add(0);
        bst.add(-2);
        bst.add(5);

        bst.get(4);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBSTGetDataNullData() {
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(3);
        bst.add(0);
        bst.add(-2);
        bst.add(5);

        bst.get(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBSTRemoveNullData() {
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(3);
        bst.add(0);
        bst.add(-2);
        bst.add(5);

        bst.remove(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testBSTRemoveNotFound() {
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(3);
        bst.add(0);
        bst.add(-2);
        bst.add(5);

        bst.remove(4);
    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(0);
        bst.add(-5);
        bst.add(-9);
        bst.add(5);
        assertEquals(4, bst.size());
        assertNotNull(bst.getRoot());
        assertEquals((Integer) 0, bst.getRoot().getData());

        assertEquals((Integer) (-5), bst.remove(-5));
        assertEquals(3, bst.size());
        assertNotNull(bst.getRoot());
        assertNotNull(bst.getRoot().getLeft());
        assertFalse(bst.getRoot().getLeft().getData().equals((Integer) (-5)));
        assertEquals((Integer) (-9), bst.getRoot().getLeft().getData());

        assertEquals((Integer) 0, bst.remove(0));
        assertEquals(2, bst.size());
        assertNotNull(bst.getRoot());
        assertNull(bst.getRoot().getLeft());
        assertEquals((Integer) (-9), bst.getRoot().getData());
        assertNotNull(bst.getRoot().getRight());
        assertEquals((Integer) 5, bst.getRoot().getRight().getData());

        assertEquals((Integer) 5, bst.remove(5));
        assertEquals(1, bst.size());
        assertNotNull(bst.getRoot());
        assertEquals((Integer) (-9), bst.getRoot().getData());
        assertNull(bst.getRoot().getRight());

        assertEquals((Integer) (-9), bst.remove(-9));
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testBSTGetMax() throws Exception {
        System.out.println("Testing getMax...");
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(50);
        assertEquals((Integer) 50, bst.getMax());

        bst.add(25);
        assertEquals((Integer) 50, bst.getMax());

        bst.add(40);
        assertEquals((Integer) 50, bst.getMax());

        bst.add(75);
        assertEquals((Integer) 75, bst.getMax());

        bst.add(60);
        assertEquals((Integer) 75, bst.getMax());

        bst.add(100);
        assertEquals((Integer) 100, bst.getMax());

        System.out.println("Testing edge case: tree is empty...");
        bst.clear();
        assertNull(bst.getMax());
        System.out.println("Finished getMax!\n");
    }

    @Test(timeout = TIMEOUT)
    public void testBSTNextLargest() throws Exception {
        System.out.println("Testing nextLargest...");
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(50);
        bst.add(60);
        bst.add(40);
        bst.add(75);
        bst.add(30);
        bst.add(1);
        assertEquals((Integer) 30, bst.nextLargest(1));
        System.out.println("Passed 1 -> 30");
        assertEquals((Integer) 75, bst.nextLargest(60));
        System.out.println("Passed 60 -> 75");
        assertEquals((Integer) 50, bst.nextLargest(40));
        System.out.println("Passed 40 -> 50");
        assertEquals((Integer) 40, bst.nextLargest(30));
        System.out.println("Passed 30 -> 40");
        assertNull(bst.nextLargest(75));
        System.out.println("Passed 75 -> No greater");
        assertEquals((Integer) 60, bst.nextLargest(50));
        System.out.println("Passed 50 -> 60");
        System.out.println("Finsished nextLargest!\n");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBSTNextLargestNullData() {
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.nextLargest(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testBSTNextLargestNotInTheTree() {
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(3);
        bst.add(4);
        bst.add(1);

        bst.nextLargest(2);
    }

    @Test(timeout = TIMEOUT)
    public void testBSTGetMin() throws Exception {
        System.out.println("Testing getMin...");
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(50);
        assertEquals((Integer) 50, bst.getMin());

        bst.add(60);
        assertEquals((Integer) 50, bst.getMin());

        bst.add(40);
        assertEquals((Integer) 40, bst.getMin());

        bst.add(75);
        assertEquals((Integer) 40, bst.getMin());

        bst.add(30);
        assertEquals((Integer) 30, bst.getMin());

        bst.add(1);
        assertEquals((Integer) 1, bst.getMin());

        System.out.println("Testing edge case: tree is empty...");
        bst.clear();
        assertNull(bst.getMin());

        System.out.println("Finished getMin!\n");
    }

    @Test(timeout = TIMEOUT)
    public void testToString() {
        System.out.println("Testing toString...");
        String expected1 = "{5, {0, {-5, {}, {}}, {3, {}, {}}}, {10, {7, {}, "
                + "{}}, {13, {}, {}}}}";
        String expected2 = "{7, {0, {-2, {}, {}}, {5, {}, {}}}, {12, {11, {},"
                + " {}}, {15, {}, {16, {}, {}}}}}";
        String expectedEmpty = "{}";

        assertNull(bst.getRoot());
        assertEquals(0, bst.size());

        bst.add(5);
        bst.add(0);
        bst.add(-5);
        bst.add(3);
        bst.add(10);
        bst.add(7);
        bst.add(13);

        String actual1 = bst.toString();

        assertEquals(expected1, actual1);

        bst.clear();
        String actualEmpty = bst.toString();
        assertEquals(actualEmpty, expectedEmpty);
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(7);
        bst.add(0);
        bst.add(12);
        bst.add(-2);
        bst.add(5);
        bst.add(11);
        bst.add(15);
        bst.add(16);

        BSTNode<Integer> traverse = bst.getRoot();
        assertEquals((Integer) 7, traverse.getData());
        assertEquals((Integer) 0, traverse.getLeft().getData());
        assertEquals((Integer) (-2), traverse.getLeft().getLeft().getData());
        assertEquals((Integer) 5, traverse.getLeft().getRight().getData());
        assertEquals((Integer) 12, traverse.getRight().getData());
        assertEquals((Integer) 11, traverse.getRight().getLeft().getData());
        assertEquals((Integer) 15, traverse.getRight().getRight().getData());
        assertEquals((Integer) 16, traverse.getRight().getRight().getRight()
                .getData());

        String actual2 = bst.toString();

        assertEquals(expected2, actual2);
        System.out.println("Finished toString!\n");
    }

    @Test(timeout = TIMEOUT)
    public void testPreorder() {
        System.out.println("Testing preorder...");
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(5);
        bst.add(0);
        bst.add(-5);
        bst.add(3);
        bst.add(10);
        bst.add(7);
        bst.add(13);

        List<Integer> correctList = new ArrayList<Integer>(
            Arrays.asList(new Integer[]{5, 0, -5, 3, 10, 7, 13}));
        assertEquals(correctList, bst.preorder());

        System.out.println("Testing edge case: tree is empty...");
        bst.clear();
        correctList = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{}));
        assertEquals(correctList, bst.preorder());

        System.out.println("Finished preorder!\n");
    }

    @Test(timeout = TIMEOUT)
    public void testInorder() {
        System.out.println("Testing inorder...");
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(5);
        bst.add(0);
        bst.add(-5);
        bst.add(3);
        bst.add(10);
        bst.add(7);
        bst.add(13);

        List<Integer> correctList = new ArrayList<Integer>(
            Arrays.asList(new Integer[]{-5, 0, 3, 5, 7, 10, 13}));
        assertEquals(correctList, bst.inorder());

        System.out.println("Testing edge case: tree is empty...");
        bst.clear();
        correctList = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{}));
        assertEquals(correctList, bst.inorder());

        System.out.println("Finished inorder!\n");
    }

    @Test(timeout = TIMEOUT)
    public void testPostorder() {
        System.out.println("Testing postorder...");
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(5);
        bst.add(0);
        bst.add(-5);
        bst.add(3);
        bst.add(10);
        bst.add(7);
        bst.add(13);

        List<Integer> correctList = new ArrayList<Integer>(
            Arrays.asList(new Integer[]{-5, 3, 0, 7, 13, 10, 5}));
        assertEquals(correctList, bst.postorder());

        System.out.println("Testing edge case: tree is empty...");
        bst.clear();
        correctList = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{}));
        assertEquals(correctList, bst.postorder());
        System.out.println("Finished postorder!\n");
    }

    @Test(timeout = TIMEOUT)
    public void testLevelorder() {
        System.out.println("Testing levelorder...");
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(5);
        bst.add(0);
        bst.add(-5);
        bst.add(3);
        bst.add(10);
        bst.add(7);
        bst.add(13);

        List<Integer> correctList = new ArrayList<>(
            Arrays.asList(new Integer[]{5, 0, 10, -5, 3, 7, 13}));
        assertEquals(correctList, bst.levelorder());

        System.out.println("Testing edge case: tree is empty...");
        bst.clear();
        correctList = new ArrayList<>(
                Arrays.asList(new Integer[]{}));
        assertEquals(correctList, bst.levelorder());
        System.out.println("Finished levelorder!\n");
    }

    @Test(timeout = TIMEOUT)
    public void testBSTClear() throws Exception {
        System.out.println("Testing clear...");
        bst = new BST<>();
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(50);
        bst.add(25);
        bst.add(75);
        bst.add(10);
        bst.add(5);
        bst.add(2);
        assertEquals(6, bst.size());

        bst.clear();
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());
        System.out.println("Finished clear!\n");
    }

    @Test(timeout = TIMEOUT)
    public void testHeight() {
        System.out.println("Testing height...");
        bst = new BST<>();
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(50);
        bst.add(25);
        bst.add(75);
        bst.add(10);
        bst.add(5);
        bst.add(2);
        assertEquals(6, bst.size());

        assertEquals(4, bst.height());

        bst.clear();
        System.out.println("Testing edge case: tree is empty...");
        assertEquals(-1, bst.height());

        bst.add(50);
        System.out.println("Testing edge case: tree has only root...");
        assertEquals(0, bst.height());
        bst.add(25);
        bst.add(75);
        bst.add(10);
        bst.add(35);
        bst.add(66);
        bst.add(55);
        bst.add(70);
        assertEquals(8, bst.size());
        assertEquals(3, bst.height());
        System.out.println("Finished height!\n");
    }

    @Test(timeout = TIMEOUT)
    public void testBSTContains() throws Exception {
        System.out.println("Testing contains...");
        bst = new BST<>();
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(50);
        bst.add(25);
        bst.add(10);
        bst.add(75);
        bst.add(60);
        bst.add(70);

        assertEquals((boolean) true, bst.contains(50));
        assertEquals((boolean) false, bst.contains(30));
        System.out.println("Finished contains!\n");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBSTContainsNullData() {
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());

        bst.add(3);
        bst.add(4);
        bst.add(1);

        bst.contains(null);
    }
}