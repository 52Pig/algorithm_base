package org.apache.hadoop.examples;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 有一棵二叉树，请设计一个算法，按照层次打印这棵二叉树。
给定二叉树的根结点root，请返回打印结果，结果按照每一层一个数组进行储存，所有数组的顺序按照层数从上往下，且每一层的数组内元素按照从左往右排列。保证结点数小于等于500。
 * @param args
 */
public class TreePrinter {
	public static void main(String[] args) {
		TreeNode root = null ;
		printTree(root);
		
	}

	private static int[][] printTree(TreeNode root) {
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		
		TreeNode last = root;
		TreeNode nlast = null;	
		queue.add(root);
		TreeNode tmp = queue.poll();
		List<List<Integer>> value = new ArrayList<List<Integer>>();
		List<Integer> vv = new ArrayList<Integer>();
		while(tmp!=null){
			if(tmp.left != null){
				queue.add(tmp.left);
				nlast = tmp.left;
			}
			if(null!=tmp.right){
				queue.add(tmp.right);
				nlast = tmp.right;
			}
			vv.add(tmp.val);
			if(tmp == last){
				last = nlast;
				value.add(vv);
				vv = new ArrayList<Integer>();
			}
			tmp = queue.poll();
		}
		int level = value.size();
		int[][] res = new int[level][];
		for(int i=0;i<level;i++){
			int nodes = value.get(i).size();
			res[i] = new int[nodes];
			for(int j=0;j<nodes;j++){
				res[i][j] = value.get(i).get(j);
			}
		}
		return res;
	}

	public class TreeNode{
		int val = 0;
		TreeNode left = null;
		TreeNode right = null;
		public TreeNode(int val){
			this.val = val;
		}
	}
}


