package org.apache.hadoop.examples;

import java.util.*;

public class BinaryTree {

	private Scanner scanner = null;
	private TreeNode treenode = null;
	private Queue queue = null;

	public TreeNode getTreenode() {
		return treenode;
	}

	public void setTreenode(TreeNode treenode) {
		this.treenode = treenode;
	}

	public Scanner getScanner() {
		return scanner;
	}

	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}

	public BinaryTree() {
		scanner = new Scanner(System.in);
		this.queue = new Queue();
	}

	public void createTree() {
		this.treenode = createTree(treenode);
	}

	public void preTraverse() {
		preTraverse(this.treenode);
	}

	public void midTraverse() {
		midTraverse(this.treenode);
	}

	public void postTraverse() {
		postTraverse(this.treenode);
	}

	public void levelTraverse() {
		levelTraverse(this.treenode);
	}

	public TreeNode createTree(TreeNode root) {
		String data = scanner.next();
		if (data.equals("/")) {
			root = null;
			return null;
		}
		root = new TreeNode(data);
		root.setLchild(createTree(root.getLchild()));
		root.setRchild(createTree(root.getRchild()));
		return root;
	}

	// 先序遍历
	public void preTraverse(TreeNode root) {
		if (root != null) {
			System.out.print(root.getData() + " ");
			preTraverse(root.getLchild());
			preTraverse(root.getRchild());
		}
	}

	// 中序遍历
	public void midTraverse(TreeNode root) {
		if (root != null) {
			midTraverse(root.getLchild());
			System.out.print(root.getData() + " ");
			midTraverse(root.getRchild());
		}
	}

	// 后序遍历
	public void postTraverse(TreeNode root) {
		if (root != null) {
			postTraverse(root.getLchild());
			postTraverse(root.getRchild());
			System.out.print(root.getData() + " ");
		}
	}

	// 层次遍历
	public void levelTraverse(TreeNode root) {
		System.out.print(root.getData() + " ");
		queue.enQueue(root);
		int level = 1;
		while (!queue.isEmpty()) {
			List<TreeNode> list = new ArrayList<TreeNode>();
			while (!queue.isEmpty()) {
				list.add(queue.deQueue());
			}
			boolean tag = false;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getLchild() != null) {
					System.out.print(list.get(i).getLchild().getData() + " ");
					queue.enQueue(list.get(i).getLchild());
					tag = true;
				}
				if (list.get(i).getRchild() != null) {
					System.out.print(list.get(i).getRchild().getData() + " ");
					queue.enQueue(list.get(i).getRchild());
					tag = true;
				}
			}
			if (tag) {
				level++;
			}
		}
		System.out.println("二叉树共" + level + "层");
	}

	public static void main(String[] args) {
		BinaryTree tree = new BinaryTree();
		tree.createTree();
		System.out.println("二叉树建立完成");
		System.out.print("先序遍历结果为：");
		tree.preTraverse();
		System.out.println();
		System.out.print("中序遍历结果为：");
		tree.midTraverse();
		System.out.println();
		System.out.print("后序遍历结果为：");
		tree.postTraverse();
		System.out.println();
		System.out.print("层次遍历结果为：");
		tree.levelTraverse();
		System.out.println();
	}
	/*运行结果如下所示：
	0 1 2 / / 3 / / 4 / /
	二叉树建立完成
	先序遍历结果为：0 1 2 3 4 
	中序遍历结果为：2 1 3 0 4 
	后序遍历结果为：2 3 1 4 0 
	层次遍历结果为：0 1 4 2 3 二叉树共3层*/
}
