import java.util.*;

/**
 * Your implementation of a Binary Search Tree.
 *
 * @author Hao Zhang
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST.
     * YOU DO NOT NEED TO IMPLEMENT THIS CONSTRUCTOR!
     */
    public BST() {
    }

    /**
     * Initializes the BST with the data in the Collection. The data in the BST
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        for(T datum : data) {
            add(datum);
        }
    }

    /**
     * Adds a new node with the given data by traversing the tree and finding
     * the appropriate location. If the data is already present in the tree do
     * nothing.
     *
     * Running Time: Worst: O(n)
     *               Average: O(log n)
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    @Override
    public void add(T data) {
        root = addPrivate(root, data);
    }

    private BSTNode<T> addPrivate(BSTNode<T> node, T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        } else if (node == null) {
            node = new BSTNode<>(data);
            size += 1;
            return node;
        } else if (node.getData().compareTo(data) > 0) {
            node.setLeft(addPrivate(node.getLeft(), data));
        } else if (node.getData().compareTo(data) < 0) {
            node.setRight(addPrivate(node.getRight(), data));
        }
        return node;
    }


    /**
     * Removes the data from the tree. There are 3 cases to consider:
     * 1: the node containing data is a leaf node.  In this case, simply
     * remove it.
     * 2: the node containing data has one child. In this case, simply replace
     * it with its child, then remove the child.
     * 3: the node containing data has 2 children. There are two approaches:
     *  - replacing the data with either the largest element that is smaller
     *    than the element being removed (commonly called the predecessor)
     *  - replacing it with the smallest element that is larger than the element
     *    being removed (commonly called the successor).
     * For this assignment, use the predecessor.
     *
     * Running Time: Worst: O(n)
     *               Average: O(log n)
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. DO NOT RETURN THE PARAMETER DATA.
     * RETURN THE DATA THAT WAS STORED IN THE TREE.
     */
    @Override
    public T remove(T data) {
        BSTNode<T> dummy = new BSTNode<>(null);
        root = removePrivate(root, dummy, data);
        return dummy.getData();
    }

    private BSTNode<T> removePrivate(BSTNode<T> current, BSTNode<T> dummy, T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        } else if (current == null) {
            throw new NoSuchElementException();
        } else if (current.getData().compareTo(data) > 0) {
            current.setLeft(removePrivate(current.getLeft(), dummy, data));
        } else if (current.getData().compareTo(data) < 0) {
            current.setRight(removePrivate(current.getRight(), dummy, data));
        } else {
            dummy.setData(current.getData());
            if (current.getRight() == null && current.getLeft() == null) {
                size -= 1;
                return null;
            } else if (current.getLeft() == null) {
                size -= 1;
                return current.getRight();
            } else if (current.getRight() == null) {
                size -= 1;
                return current.getLeft();
            } else {
                BSTNode<T> temp = new BSTNode<>(null);
                current.setLeft(predecessor(current.getLeft(), temp));
                current.setData(temp.getData());
                size -= 1;
            }
        }
        return current;
    }

    private BSTNode<T> predecessor(BSTNode<T> current, BSTNode<T> dummy) {
        if (current.getRight() != null) {
            current.setRight(predecessor(current.getRight(), dummy));
        } else {
            dummy.setData(current.getData());
            current = current.getLeft();
        }
        return current;
    }

    /**
     * Gets the data in the tree matching the parameter passed in (think
     * whether you should use .equals or == ?).
     *
     * Running Time: Worst: O(n)
     *               Average: O(log n)
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data removed from the tree. DO NOT RETURN THE PARAMETER DATA.
     * RETURN THE DATA THAT WAS STORED IN THE TREE.
     */
    @Override
    public T get(T data) {
        return getPrivate(root, data).getData();
    }

    private BSTNode<T> getPrivate(BSTNode<T> node, T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        } else if (node == null) {
            throw new NoSuchElementException();
        } else if (node.getData().compareTo(data) == 0) {
            return node;
        } else if (node.getData().compareTo(data) > 0) {
            return getPrivate(node.getLeft(), data);
        } else {
            return getPrivate(node.getRight(), data);
        }
    }

    /**
     * Checks whether or not the parameter is contained within the tree.
     *
     * Running Time: Worst: O(n)
     *               Average: O(log n)
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether the parameter is contained within the tree or not.
     */
    @Override
    public boolean contains(T data) {
        return containsPrivate(root, data);
    }

    private boolean containsPrivate(BSTNode<T> node, T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        } else if (node == null) {
            return false;
        } else if (node.getData().compareTo(data) == 0) {
            return true;
        } else {
            return containsPrivate(node.getLeft(), data) || containsPrivate(node.getRight(), data);
        }
    }

    /**
     * Finds the smallest element greater than data, or the successor,
     * from the tree. There are 2 cases to consider:
     * 1: The right sub-tree is non-empty, then successor is the leftmost node
     * the right sub-tree
     * 2: The right sub-tree is empty, then successor is the lowest ancestor of
     * the node containing data, whose left child is also an ancestor of given
     * data eg.
     *                    73
     *                   /  \
     *                  34   90
     *                 /  \
     *                32  40
     * If we need to find the nextLargest of 40, you would return 73.
     *
     * Running Time: Worst: O(n)
     *               Average: O(log n)
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not in the tree
     * @param data the data for which the nextLargest is to be found
     * @return the next largest data in the tree. If there is no larger data
     * than the one given, return {@code null}.
     */
    @Override
    public T nextLargest(T data) {
        return getSuccessor(root, root, data).getData();
    }

    private BSTNode<T> getSuccessor(BSTNode<T> current, BSTNode<T> parent, T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        } else if (current == null) {
            throw new NoSuchElementException();
        } else if (current.getData().compareTo(data) == 0) {
            if (current.getRight() != null) {
                return getMinPrivate(current.getRight());
            } else if (current.getData().compareTo(parent.getData()) > 0) {
                return new BSTNode<T>(null);
            } else {
                return parent;
            }
        } else if (current.getData().compareTo(data) > 0) {
            return getSuccessor(current.getLeft(), current, data);
        } else {
            return getSuccessor(current.getRight(), parent, data);
        }
    }

    /**
     * Returns a string representation of the tree. The string should be
     * formatted as follows:
     *        {currentData, leftSubtree, rightSubtree}
     *
     * For example, for a tree that looks like the following:
     *                    8
     *                   / \
     *                 6    10
     *                / \     \
     *              4     7    15
     *
     * The string should be:
     *      {8, {6, {4, {}, {}}, {7, {}, {}}}, {10, {}, {15, {}, {}}}}
     *
     * An empty tree should return an empty set of brackets, i.e. {}.
     *
     * Running Time: O(n)
     *
     * @return String representation of the tree
     */
    @Override
    public String toString() {
        return "{"+ ToString(root) + "}";
    }

    private String ToString(BSTNode<T> node) {
        if (node == null) {
            return "";
        }
        else {
            return  node.getData() + ", {" + ToString(node.getLeft()) + "}" + ", {" + ToString(node.getRight()) + "}";
        }
    }

    /**
     * Finds the largest item in the tree and returns it.
     *
     * Running Time: Worst: O(n)
     *               Average: O(log n)
     *
     *
     * @return largest item in the tree. If the tree is empty,
     * return {@code null}.
     */
    @Override
    public T getMax() {
        return getMaxPrivate(root).getData();
    }

    private BSTNode<T> getMaxPrivate(BSTNode<T> node) {
        if (node == null) {
            return new BSTNode<>(null);
        } else if (node.getRight() == null) {
            return node;
        } else {
            return getMaxPrivate(node.getRight());
        }
    }

    /**
     * Finds the smallest item in the tree and returns it.
     *
     * Running Time: Worst: O(n)
     *               Average: O(log n)
     *
     * @return smallest item in the tree. If the tree is empty,
     * return {@code null}.
     */
    @Override
    public T getMin() {
        return getMinPrivate(root).getData();
    }

    private BSTNode<T> getMinPrivate(BSTNode<T> node) {
        if (node == null) {
            return new BSTNode<>(null);
        } else if (node.getLeft() == null) {
            return node;
        } else {
            return getMinPrivate(node.getLeft());
        }
    }

    /**
     * Running Time: O(1)
     *
     * @return the number of elements in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Generates a preorder traversal of the tree.
     *
     * Running Time: O(n)
     *
     * @return a preorder traversal of the tree
     */
    @Override
    public List<T> preorder() {
        return Preorder(root);
    }

    private List<T> Preorder(BSTNode<T> node) {
        ArrayList<T> list = new ArrayList<T>();
        if (node == null) {
            return list;
        }
        else {
            list.add(node.getData());
            list.addAll(Preorder(node.getLeft()));
            list.addAll(Preorder(node.getRight()));
            return list;
        }

    }

    /**
     * Generates a postorder traversal of the tree.
     *
     * Running Time: O(n)
     *
     * @return a postorder traversal of the tree
     */
    @Override
    public List<T> postorder() {
        return Postorder(root);
    }

    private List<T> Postorder(BSTNode<T> node) {
        ArrayList<T> list = new ArrayList<>();
        if (node == null) {
            return list;
        }
        else {
            list.addAll(Postorder(node.getLeft()));
            list.addAll(Postorder(node.getRight()));
            list.add(node.getData());
            return list;
        }
    }

    /**
     * Generates an inorder traversal of the tree.
     *
     * Running Time: O(n)
     *
     * @return an inorder traversal of the tree
     */
    @Override
    public List<T> inorder() {
        return Inorder(root);
    }

    private List<T> Inorder(BSTNode<T> node) {
        ArrayList<T> list = new ArrayList<>();
        if (node == null) {
            return list;
        }
        else {
            list.addAll(Inorder(node.getLeft()));
            list.add(node.getData());
            list.addAll(Inorder(node.getRight()));
            return list;
        }

    }

    /**
     * Generates a level-order traversal of the tree.
     *
     * To do this, add the root node to a queue. Then, while the queue isn't
     * empty, dequeue one node, add its data to the list being returned, and
     * enqueue its left and right child nodes to the queue (left first).
     * If what you just removed is {@code null}, ignore it and continue with
     * the rest of the nodes.
     *
     * Running Time: O(n)
     *
     * @return a level order traversal of the tree
     */
    @Override
    public List<T> levelorder() {
        return Levelorder(root);
    }

    private List<T> Levelorder(BSTNode<T> node) {
        ArrayList<BSTNode<T>> queue = new ArrayList<>();
        ArrayList<T> list = new ArrayList<>();
        if (node == null) {
            return list;
        }
        queue.add(node);
        while (queue.size() != 0) {
            list.add(queue.get(0).getData());
            if (queue.get(0).getLeft() != null) {
                queue.add(queue.get(0).getLeft());
            }
            if (queue.get(0).getRight() != null) {
                queue.add(queue.get(0).getRight());
            }
            queue.remove(0);
        }
        return list;

    }

    /**
     * Clears the tree.
     *
     * Running Time: O(1)
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Calculates and returns the height of the root of the tree.
     * REMEMBER: A node's height is defined as
     * {@code max(left.height, right.height) + 1}.
     *
     * A leaf node has a height of 0.
     *
     * Running Time: O(n)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    @Override
    public int height() {
        return Height(root);
    }

    private int Height(BSTNode<T> node) {
        if (node == null) return -1;
        else {
            return Math.max(Height(node.getLeft()), Height(node.getRight())) + 1;
        }
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     * DO NOT USE IT IN YOUR CODE
     * DO NOT CHANGE THIS METHOD
     *
     * @return the root of the tree
     */
    @Override
    public BSTNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}
