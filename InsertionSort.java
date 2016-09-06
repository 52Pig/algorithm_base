package org.apache.hadoop.examples;
/**
 * 对于一个int数组，请编写一个插入排序算法，对数组元素排序。
给定一个int数组A及数组的大小n，请返回排序后的数组。
测试样例：
[1,2,3,5,2,3],6
[1,2,2,3,3,5]
 *
 */
public class InsertionSort {

	public static void main(String[] args) {
		
	}

	public int[] insertionSort1(int[] A, int n) {
        int i, j, temp;
         
        for(i = 1; i < n; i++){
            temp = A[i];
            for(j = i; j > 0 && A[j - 1] > temp; j-- ){
                A[j] = A[j - 1];
            }
            A[j] = temp;
        }
         
        return A;
    }
	
	public int[] insertionSort2(int[] A, int n) {
        // write code here
        int exchange;
        int tmp;
        for(int i=1;i<n;i++){
            exchange=i;
            for(int j=i-1;j>=0;j--){
                if(A[j]<=A[exchange])
                    break;
                else{
                	tmp=A[exchange];
                    A[exchange]=A[j];
                    A[j]=tmp;
                    exchange=j;
                }
            }
        }
        return A;
    }
	
	public int[] insertionSort3(int[] A, int n) {
        for(int i=0; i< n; i++){
            int temp = A[i];
            int j=i-1;
            for(; j>=0;j--){
                if(temp > A[j]){
                    break;
                }
                A[j+1] = A[j];
            }
            
 			A[j+1]=temp;
        }
        
        return A;
    }
	
	
}
