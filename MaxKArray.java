package org.apache.hadoop.examples;

//给定两个已经排好序的数组，数组a  [1,4,7,13,18,23,44,56]，
//数组b [3,5,6,8,19,34,55,62,78];求数组a和b中输出第k大的数
public class MaxKArray {

	/**
	 * 解法一：
	 * 
	 * 假如第k大的数在a中，设置a[mid],那么肯定有b[k-mid-1]<=a[mid]<=b[k-mid]，这是由于a中已经有mid个元素<a[
	 * mid]了，
	 * 
	 * 则b中肯定还有k-mid - 1个元素小于a[mid],所以我们判断
	 * 
	 * 若b[k-mid-2]<=a[mid]<=b[k-mid-1], 返回a[mid]
	 * 
	 * 若a[mid] < b[k-mid-2] 说明a[mid]小于第k个元素值，a.low = a.mid + 1
	 * 
	 * 若a[mid] > b[k-mid-1],说明a[mid]大于第k个元素值，a.high = a.mid - 1
	 * 
	 * 结束条件为a.low > a.high 如果未找到，则假设存在于b中，再判断一次
	 */
	int get_k_from_sorted_array(int[] a, int[] b, int low, int high, int k,
			int len) {
		if (low > high)
			return -1;
		int mid = low + (high - low) / 2;
		// a中元素个数小于k个，那么k - 2 - mid就可能超出b的下标范围
		if (k - 2 - mid >= len)
			return get_k_from_sorted_array(a, b, mid + 1, high, k, len);
		if (k - 1 - mid < len) {
			// 判断b数据中是否存在b[k-1-mid]
			if (a[mid] >= b[k - mid - 2] && a[mid] <= b[k - mid - 1])
				return a[mid];
		} else {
			if (a[mid] >= b[k - mid - 2])
				return a[mid];
		}
		if (a[mid] < b[k - mid - 2])
			return get_k_from_sorted_array(a, b, mid + 1, high, k, len);
		return get_k_from_sorted_array(a, b, low, mid - 1, k, len);
	}

	int _func(int[] a, int a_len, int[] b, int b_len, int k) {
		int rst = 0;
		if (a_len + b_len + 2 < k)
			return -1;
		int p1 = a_len > k - 1 ? k - 1 : a_len;
		int p2 = b_len > k - 1 ? k - 1 : b_len;
		rst = get_k_from_sorted_array(a, b, 0, p1, k, 4);
		if (-1 == rst) {
			rst = get_k_from_sorted_array(b, a, 0, p2, k, 5);
		}
		return rst;
	}

	/**
	 * 解法二：
	 * 
	 * 判断a[mid_a] 与 b[mid_b]的关系 如果a[mida] < b[mid_b]
	 * 
	 * 1）k小于等于mida + midb + 1,那么b数组从mid_b开始就没有用了，缩小b的搜索范围 2）k大于mida + midb + 1,
	 * 那么a数组从low到mid_a开始就没用了，缩小a的搜索范围 3）终止条件是 a搜索完 返回b中元素或者相反
	 */
	int get_k_from_sorted_array2(int[] a, int[] b, int la, int ha, int lb,
			int hb, int k) {
		if (la > ha)
			return b[lb + k - 1];
		if (lb > hb)
			return a[la + k - 1];
		int mida = (la + ha) >> 1;
		int midb = (lb + hb) >> 1;
		int num = mida - la + midb - lb + 1;

		// cout<<la<<" "<<ha<<" "<<lb<<" "<<hb<<" "<<num<<" "<<a[mida]<<" "<<b[midb]<<endl;

		if (a[mida] <= b[midb]) {
			if (k <= num)
				return get_k_from_sorted_array2(a, b, la, ha, lb, midb - 1, k);
			else
				return get_k_from_sorted_array2(a, b, mida + 1, ha, lb, hb, k
						- (mida - la + 1));
		} else {
			if (k <= num)
				return get_k_from_sorted_array2(a, b, la, mida - 1, lb, hb, k);
			else
				return get_k_from_sorted_array2(a, b, la, ha, midb + 1, hb, k
						- (midb - lb + 1));
		}
	}

	int _func2(int[] a, int a_len, int[] b, int b_len, int k) {
		int rst = 0;
		if (a_len + b_len < k)
			return -1;
		int p1 = a_len > k ? k : a_len;
		int p2 = b_len > k ? k : b_len;
		// cout<<p1<<" "<<p2<<endl;
		rst = get_k_from_sorted_array2(a, b, 0, p1 - 1, 0, p2 - 1, k);
		return rst;
	}

}
