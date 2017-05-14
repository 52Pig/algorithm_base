package org.apache.hadoop.examples;

/**
 * 给定一棵二叉树，编程写出一个二叉树的最大深度
 */
public class MaxDepthForBinaryTree {
	public class TreeNode{
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x){
			val = x;
		}
	}
	
	
	public static void main(String[] args){
		
	}
	
	public int maxDepth(TreeNode root){
		if(root == null){
			return 0;
		}
		if(root.left==null){
			return maxDepth(root.right) + 1;
		}
		if(root.right==null){
			return maxDepth(root.left) + 1;
		}
		
		return max(maxDepth(root.left), maxDepth(root.right)) + 1;
	}

	public int max(int left, int right){
		if(left>right){
			return left;
		}else{
			return right; 
		}
	}
	
	
	
	
}

