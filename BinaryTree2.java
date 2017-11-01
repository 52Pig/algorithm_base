package org.apache.hadoop.examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree2<E> {

	private static class Node<E> {
		public E value; // 节点值
		public Node<E> left; // 左节点
		public Node<E> right;// 右节点

		public Node(E value) {
			this.value = value;
			this.left = null;
			this.right = null;
		}
	}

	public static List<Node<Integer>> nodeList = null;// 用于存储二叉树的节点

	/**
	 * 创建数组
	 * 
	 * @param length
	 * @return
	 */
	public int[] createArray(int length) {
		int[] array = new int[length];
		for (int i = 0; i < array.length; i++) {
			array[i] = (int) (Math.random() * 100);
		}
		return array;
	}

	/**
	 * 创建二叉树
	 */
	public void createBinaryTree(int nodeNum) {
		nodeList = new ArrayList<Node<Integer>>();
		int[] array = createArray(nodeNum);
		for (int i : array) {
			System.out.print(i + " ");
		}
		System.out.println();
		for (int i = 0; i < array.length; i++) {
			Node<Integer> temp = new Node<Integer>(array[i]);
			nodeList.add(temp);
		}
		for (int parentIndex = 0; parentIndex < array.length / 2 - 1; parentIndex++) {
			// 父节点
			Node<Integer> parentNode = nodeList.get(parentIndex);
			// 左节点
			parentNode.left = nodeList.get(parentIndex * 2 + 1);
			// 右节点
			parentNode.right = nodeList.get(parentIndex * 2 + 2);

			int lastParentIndex = array.length / 2 - 1;
			Node<Integer> lastParentNode = nodeList.get(lastParentIndex);
			lastParentNode.left = nodeList.get(lastParentIndex * 2 + 1);
			// 总节点数为基数，最后一个父节点才有右孩子
			if (array.length % 2 == 1) {
				lastParentNode.right = nodeList.get(lastParentIndex * 2 + 2);
			}
		}

	}

	/**
	 * 二叉树先序遍历（递归）
	 * 
	 * @param parentNode
	 */
	public void BinaryTreePreOrder(Node<E> parentNode) {
		if (parentNode == null) {
			return;
		}
		System.out.print(parentNode.value + " ");
		BinaryTreePreOrder(parentNode.left);
		BinaryTreePreOrder(parentNode.right);
	}

	/**
	 * 二叉树先序遍历（循环）
	 * 
	 * @param rootNode
	 */
	public void BinaryTreePreOrder_loop(Node<E> rootNode) {
		Stack<Node<E>> stack = new Stack<Node<E>>();
		Node<E> cur = rootNode;
		while (cur != null || !stack.isEmpty()) {
			while (cur != null) {
				System.out.print(cur.value + " ");
				stack.push(cur);
				cur = cur.left;
			}
			cur = stack.pop();
			cur = cur.right;
		}
	}

	/**
	 * 二叉树中序遍历（递归）
	 * 
	 * @param parentNode
	 */
	public void BinaryTreeMidOrder(Node<E> parentNode) {
		if (parentNode == null) {
			return;
		}
		BinaryTreeMidOrder(parentNode.left);
		System.out.print(parentNode.value + " ");
		BinaryTreeMidOrder(parentNode.right);
	}

	/**
	 * 二叉树中序遍历（循环）
	 * 
	 * @param rootNode
	 */
	public void BinaryTreeMidOrder_loop(Node<E> rootNode) {
		Stack<Node<E>> stack = new Stack<Node<E>>();
		Node<E> cur = rootNode;
		while (cur != null || !stack.isEmpty()) {
			while (cur != null) {
				stack.push(cur);
				cur = cur.left;
			}
			cur = stack.pop();
			System.out.print(cur.value + " ");
			cur = cur.right;
		}
	}

	/**
	 * 二叉树后序遍历（递归）
	 * 
	 * @param parentNode
	 */
	public void BinaryTreePostOrder(Node<E> parentNode) {
		if (parentNode == null) {
			return;
		}
		BinaryTreePostOrder(parentNode.left);
		BinaryTreePostOrder(parentNode.right);
		System.out.print(parentNode.value + " ");
	}

	/**
	 * 二叉树后序遍历（非递归） 先处理左右子树，再处理根
	 * 
	 * @param rootNode
	 */
	public void BinaryTreePostOrder_loop(Node<E> rootNode) {
		Stack<Node<E>> stack = new Stack<Node<E>>();
		// 使用map来标记已经访问过的节点
		Map<Node<E>, Boolean> nodeMap = new HashMap<Node<E>, Boolean>();
		stack.push(rootNode);
		while (!stack.isEmpty()) {
			Node<E> temp = stack.peek();
			// 获取左子树的左节点
			if (temp.left != null && !nodeMap.containsKey(temp.left)) {
				temp = temp.left;
				while (temp != null) {
					stack.push(temp);
					temp = temp.left;
				}
				continue;
			}
			// 获取右节点
			if (temp.right != null && !nodeMap.containsKey(temp.right)) {
				stack.push(temp.right);
				continue;
			}
			Node<E> cur = stack.pop();
			System.out.print(cur.value + " ");
			nodeMap.put(cur, true);
		}
	}

	/**
	 * 二叉树层次遍历
	 * 
	 * @param rootNode
	 */
	public void BinaryTreeLevelOrder(Node<E> rootNode) {
		// 使用队列来实现遍历
		Queue<Node<E>> queue = new LinkedList<Node<E>>();
		queue.add(rootNode);
		while (!queue.isEmpty()) {
			Node<E> parentNode = queue.poll();
			System.out.print(parentNode.value + " ");
			if (parentNode.left != null) {
				queue.add(parentNode.left);
			}
			if (parentNode.right != null) {
				queue.add(parentNode.right);
			}
		}
	}

	public static void main(String[] args) {
		BinaryTree2<Integer> tree = new BinaryTree2<Integer>();
		System.out.println("遍历前数组：");
		tree.createBinaryTree(11);
		Node<Integer> rootNode = nodeList.get(0);
		System.out.println("先序遍历（递归）：");
		tree.BinaryTreePreOrder(rootNode);
		System.out.println();
		System.out.println("先序遍历（非递归）：");
		tree.BinaryTreePreOrder_loop(rootNode);
		System.out.println();
		System.out.println("中序遍历（递归）：");
		tree.BinaryTreeMidOrder(rootNode);
		System.out.println();
		System.out.println("中序遍历（非递归）：");
		tree.BinaryTreeMidOrder_loop(rootNode);
		System.out.println();
		System.out.println("后序遍历（递归）：");
		tree.BinaryTreePostOrder(rootNode);
		System.out.println();
		System.out.println("后序遍历（非递归）：");
		tree.BinaryTreePostOrder_loop(rootNode);
		System.out.println();
		System.out.println("层次遍历：");
		tree.BinaryTreeLevelOrder(rootNode);
	}
}
