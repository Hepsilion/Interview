package org.interview.algorithm.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 插入排序
 * 希尔排序
 * 冒泡排序、改进的冒泡排序
 * 快速排序
 * 选择排序
 * 堆排序
 * 归并排序
 * 基数排序
 * @author tingming
 */
public class Sort {
	public static void main(String[] args) {
		List<Integer> numbers=new ArrayList<Integer>();
		//49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 35, 14, 62, 99, 98, 54, 56, 17, 18, 23, 34, 15, 35, 25, 53, 51
		//12, 13, 14, 15, 17, 18, 23, 25, 27, 34, 34, 35, 35, 38, 49, 49, 51, 53, 54, 56, 62, 64, 65, 76, 78, 97, 98, 99 
		numbers.add(49);	numbers.add(38);	numbers.add(65);	numbers.add(97);
		numbers.add(76);	numbers.add(13);	numbers.add(27);	numbers.add(49);
		numbers.add(78);	numbers.add(34);	numbers.add(12);	numbers.add(64);
		numbers.add(35);	numbers.add(14);	numbers.add(62);	numbers.add(99);
		numbers.add(98);	numbers.add(54);	numbers.add(56);	numbers.add(17);
		numbers.add(18);	numbers.add(23);	numbers.add(34);	numbers.add(15);
		numbers.add(35);	numbers.add(25);	numbers.add(53);	numbers.add(51);
		
		HeapSort selectionSort=new HeapSort();
		selectionSort.sort(numbers);
		Sort.print(numbers);
	}
	
	public static void print(List<Integer> numbers) {
		int length=numbers.size();
		for(int i=0; i<length; i++) {
			System.out.print(numbers.get(i)+" ");
		}
	}
}

// 插入排序
class InsertionSort {
	public void sort(List<Integer> numbers) {
		int length=numbers.size();
		for(int i=1; i<length; i++) {
			int value=numbers.get(i);
			int j;
			for(j=i-1; j>=0 && numbers.get(j)>value; j--) {
				numbers.set(j+1, numbers.get(j));
			}
			numbers.set(j+1, value);
		}
	}
}

// 希尔排序
class ShellSort {
	public void sort(List<Integer> numbers) {
		int length=numbers.size();
		int increament=length/2;
		while(increament>=1) {
			for(int x=0; x<increament; x++) {
				for(int i=x+increament; i<length; i+=increament) {
					int value=numbers.get(i);
					int j;
					for(j=i-increament; j>=0 && numbers.get(j)>value; j-=increament) {
						numbers.set(j+increament, numbers.get(j));
					}
					numbers.set(j+increament, value);
				}
			}
			increament=increament/2;
		}
	}
}

// 冒泡排序
class BubbleSort{
	public void sort(List<Integer> numbers) {
		int length=numbers.size();
		for(int i=0; i<length; i++) {
			for(int j=1; j<length-i; j++) {
				if(numbers.get(j-1)>numbers.get(j)) {
					int temp=numbers.get(j);
					numbers.set(j, numbers.get(j-1));
					numbers.set(j-1, temp);
				}
			}
		}
	}
}

// 改进的冒泡排序
class OptBubbleSort{
	public void sort(List<Integer> numbers) {
		int length=numbers.size();
		for(int i=0; i<length; i++) {
			boolean flag=false;
			for(int j=1; j<length-i; j++) {
				if(numbers.get(j-1)>numbers.get(j)) {
					int temp=numbers.get(j);
					numbers.set(j, numbers.get(j-1));
					numbers.set(j-1, temp);
					flag=true;
				}
			}
			if(!flag)
				break;
		}
	}
}

// 快速排序
class QuickSort{
	public void sort(List<Integer> numbers) {
		sort(numbers, 0, numbers.size()-1);
	}
	
	private void sort(List<Integer> numbers, int start, int end) {
		if(start<end) {
			int middle=partition(numbers, start, end);
			sort(numbers, start, middle-1);
			sort(numbers, middle+1, end);
		}
	}
	
	private int partition(List<Integer> numbers, int start, int end) {
		int pivot=numbers.get(start);
		while(start<end) {
			while(start<end && numbers.get(end)>=pivot)
				end--;
			numbers.set(start, numbers.get(end));
			while(start<end && numbers.get(start)<=pivot)
				start++;
			numbers.set(end, numbers.get(start));
		}
		numbers.set(start, pivot);
		return start;
	}
}

// 选择排序
class SelectionSort {
	public void sort(List<Integer> numbers) {
		int length=numbers.size();
		for(int i=length-1; i>=0; i--) {
			int value=numbers.get(i);
			int max=Integer.MIN_VALUE, pos=-1;
			for(int j=0; j<=i; j++) {
				if(numbers.get(j)>max) {
					max=numbers.get(j);
					pos=j;
				}
			}
			numbers.set(i, max);
			numbers.set(pos, value);
		}
	}
}

// 堆排序
class HeapSort {
	public void sort(List<Integer> numbers) {
		int length=numbers.size();
		for(int i=0; i<length; i++) {
			buildMaxHeap(numbers, length-1-i);
			swap(numbers, 0, length-1-i);
		}
	}
	
	private void buildMaxHeap(List<Integer> numbers, int lastIndex) {
		for(int i=lastIndex/2; i>=0; i--) {
			int k=i;
			while(2*k+1<=lastIndex) {
				int largerChildIndex=2*k+1;
				if(largerChildIndex+1<=lastIndex && numbers.get(largerChildIndex+1)>numbers.get(largerChildIndex)) {
					largerChildIndex++;
				}
				if(numbers.get(k)<numbers.get(largerChildIndex)) {
					swap(numbers, k, largerChildIndex);
					k=largerChildIndex;
				}else {
					break;
				}
			}
		}
	}
	
	private void swap(List<Integer> numbers, int pos1, int pos2) {
		int temp=numbers.get(pos1);
		numbers.set(pos1, numbers.get(pos2));
		numbers.set(pos2, temp);
	}
}

// 归并排序
class MergeSort {
	public void sort(List<Integer> numbers) {
		sort(numbers, 0, numbers.size()-1);
	}
	
	private void sort(List<Integer> numbers, int start, int end) {
		if(start<end) {
			int middle=start+(end-start)/2;
			sort(numbers, start, middle);
			sort(numbers, middle+1, end);
			merge(numbers, start, middle, end);
		}
	}
	
	private void merge(List<Integer> numbers, int start, int middle, int end) {
		List<Integer> list=new ArrayList<Integer>();
		for(int i=start; i<=end; i++) {
			list.add(numbers.get(i));
		}
		int i=start, j=middle+1, k=start;
		while(i<=middle && j<=end) {
			if(list.get(i-start)<=list.get(j-start)) {
				numbers.set(k, list.get(i-start));
				i++;
			}else {
				numbers.set(k, list.get(j-start));
				j++;
			}
			k++;
		}
		while(i<=middle) {
			numbers.set(k, list.get(i-start));
			k++;
			i++;
		}
		while(j<=end) {
			numbers.set(k, list.get(j-start));
			k++;
			j++;
		}
	}
}

// 基数排序
class RadixSort {
	public void sort(List<Integer> numbers) {
		int length=numbers.size();
		
		int max=Integer.MIN_VALUE;
		for(int i=0; i<length; i++) {
			max=Math.max(max, numbers.get(i));
		}
		int times=String.valueOf(max).length();
		
		List<List<Integer>> queues=new ArrayList<List<Integer>>();
		for(int i=0; i<10; i++) {
			queues.add(new ArrayList<Integer>());
		}
		
		for(int i=0; i<times; i++) {
			// distribute
			for(int j=0; j<length; j++) {
				int number=numbers.get(j);
				int bit=number%(int)Math.pow(10, i+1)/(int)Math.pow(10, i);
				List<Integer> queue=queues.get(bit);
				queue.add(number);
			}
			
			// collect
			numbers.clear();
			for(int j=0; j<10; j++) {
				List<Integer> queue=queues.get(j);
				numbers.addAll(queue);
				queue.clear();
			}
		}
	}
}