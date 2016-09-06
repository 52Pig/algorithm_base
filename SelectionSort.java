package org.apache.hadoop.examples;
/**
 * 对于一个int数组，请编写一个选择排序算法，对数组元素排序。
给定一个int数组A及数组的大小n，请返回排序后的数组。
测试样例：
[1,2,3,5,2,3],6
[1,2,2,3,3,5]
 * @author Administrator
 *
 */
public class SelectionSort {
	public static void main(String[] args) {
		int[] A = {1,2,3,5,2,3};
		int [] As = selectionSort(A, 6);
		for(int i=0;i<As.length;i++){
			System.out.println(As[i]);
		}
	}

	public static int[] selectionSort(int[] A, int n){
		int max ;
		for(int i=0;i<n-1;i++){
			for(int j=0;j<n-i-1;j++){
				if(A[j] > A[j+1]){
					max = A[j];
					A[j] = A[j+1];
					A[j+1] = max;
				}
			}
		}
		return A;
	}
	
}
