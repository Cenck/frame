package com.cengel.starbucks.util.tree;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Tree<T> {

	public static void main(String[] args) {
		// todo: url= https://blog.csdn.net/u011240877/article/details/53193877
		Tree<Integer> root = new Tree<>(8);
		root.addNode(new Tree<>(3)
				.addNode(new Tree<>(1))
				.addNode(new Tree<>(6).addNode(new Tree<>(4)).addNode(new Tree<>(7))))
			.addNode(new Tree<>(10).addNode(new Tree<>(14).addNode(new Tree<>(13))));
		System.out.println(root.size());
		System.out.println(root.dept());
	}

	@Getter
	private T             data;
	@Getter
	private List<Tree<T>> children;

	public Tree() {
		data = null;
		children = new ArrayList<>();
		children.clear();
	}

	public Tree(T data) {
		this.data = data;
		children = new ArrayList<>();
		children.clear();
	}

	/**
	 * 添加子树
	 *
	 * @param tree 子树
	 */
	private Tree<T> addNode(Tree tree) {
		children.add(tree);
		return this;
	}

	/**
	 * 添加子树
	 *
	 * @param t 子树
	 * @return 返回root
	 */
	private Tree<T> addNode(T t) {
		return addNode(new Tree<>(t));
	}

	/**
	 *
	 * @param t
	 * @return 返回子对象
	 */
//	public Tree<T> addChild(T t) {
//		Tree<T> child = new Tree<>(t);
//		addNode(child);
//		return child;
//	}
	/**
	 * 置空树
	 */
	public void clearTree() {
		data = null;
		children.clear();
	}

	/**
	 * 求树的深度
	 * 这方法还有点问题，有待完善
	 *
	 * @return 树的深度
	 */
	public int dept() {
		return dept(this);
	}

	/**
	 * 求树的深度
	 * 这方法还有点问题，有待完善
	 *
	 * @param tree
	 * @return
	 */
	private int dept(Tree tree) {
		if (tree.isEmpty()) {
			return 0;
		} else if (tree.isLeaf()) {
			return 1;
		} else {
			int depth = 1;
			if (tree.getChildren() == null || tree.getChildren().size() == 0) return depth;
			List<Tree> childs = tree.getChildren();
			int[] childDepths = new int[childs.size()];
			for (int i = 0; i < childs.size(); i++) {
				Tree ctree = childs.get(i);
				childDepths[i] = dept(ctree);
			}
			int max = 0;
			for (int childDepth : childDepths) {
				if (childDepth > max) max = childDepth;
			}
			depth += max;
			return depth;
		}
	}

	/**
	 * 返回递i个子树
	 *
	 * @param i
	 * @return
	 */
	public Tree getChild(int i) {
		return children.get(i);
	}

	/**
	 * 求第一个孩子 结点
	 *
	 * @return
	 */
	public Tree getFirstChild() {
		return children.get(0);

	}

	/**
	 * 求最后 一个孩子结点
	 *
	 * @return
	 */
	public Tree getLastChild() {
		return children.get(children.size() - 1);
	}

	/**
	 * 判断是否为空树
	 *
	 * @return 如果为空，返回true,否则返回false
	 */
	public boolean isEmpty() {
		if (children.isEmpty() && data == null) return true;
		return false;
	}

	/**
	 * 判断是否为叶子结点
	 *
	 * @return
	 */
	public boolean isLeaf() {
		if (children.isEmpty()) return true;
		return false;
	}

	/**
	 * 获得树根
	 *
	 * @return 树的根
	 */
	public Tree root() {
		return this;
	}

	/**
	 * 设置根结点的数据
	 */
	public void setRootData(T data) {
		this.data = data;
	}

	/**
	 * 求结点数
	 *
	 * @return 结点的个数
	 */
	public int size() {
		return size(this);
	}

	/**
	 * 求结点数
	 * 这方法还有点问题，有待完善
	 *
	 * @param tree
	 * @return
	 */
	private int size(Tree tree) {
		if (tree.isEmpty()) {
			return 0;
		} else if (tree.isLeaf()) {
			return 1;
		} else {
			int size = 1;
			if (tree.getChildren() == null || tree.getChildren().size() == 0) return size;
			List<Tree> childs = tree.getChildren();
			for (Tree child : childs) {
				size += child.size();
			}
			return size;
		}
	}

}