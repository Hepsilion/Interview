package org.interview.algorithm.dp;

import java.util.ArrayList;
import java.util.List;

public class Backpack {
	public static void main(String[] args) {
		List<Integer> C=new ArrayList<Integer>();
		List<Integer> V=new ArrayList<Integer>();
		//01背包问题测试数据集1
//		int N=5, W=100;
//		C.add(77); V.add(92);
//		C.add(22); V.add(22);
//		C.add(29); V.add(87);
//		C.add(50); V.add(46);
//		C.add(99); V.add(90);//133
		//01背包问题测试数据集2
//		int N=8, W=200;
//		C.add(79); V.add(83);
//		C.add(58); V.add(14);
//		C.add(86); V.add(54);
//		C.add(11); V.add(79);
//		C.add(28); V.add(72);
//		C.add(62); V.add(52);
//		C.add(15); V.add(48);
//		C.add(68); V.add(62);//334
		
		//new ZeroOnePack().fun1(N, C, V, W);
		//new ZeroOnePack().fun11(N, C, V, W);
		//new ZeroOnePack().fun2(N, C, V, W);
		//new ZeroOnePack().fun21(N, C, V, W);
		
		//完全背包问题测试数据集1
//		int N=3, W=10;
//		C.add(3); V.add(4);
//		C.add(4); V.add(5);
//		C.add(5); V.add(6);//13
		//完全背包问题测试数据集2
		int N=6, W=10;
		C.add(3); V.add(6);
		C.add(2); V.add(5);
		C.add(5); V.add(10);
		C.add(1); V.add(2);
		C.add(6); V.add(16);
		C.add(4); V.add(8);//26
		new FullBackPack().fun1(N, C, V, W);
		new FullBackPack().fun11(N, C, V, W);
		new FullBackPack().fun12(N, C, V, W);
		new FullBackPack().fun2(N, C, V, W);
		new FullBackPack().fun21(N, C, V, W);
	}
}

/**
 * 01背包问题
 * 
 * 一个背包总容量为W，现在有N个物品，第i个物品体积为C[i]，价值为V[i]。现在往背包里面装东西，怎么装能使背包内的物品价值最大？
 * @author tingming
 *
 */
class ZeroOnePack {
	/**
	 * 问题：现在往背包里面装东西，求能使背包内的物品价值最大时的最大价值以及此时背包内的物品信息
	 * <pre>
	 * F[i][j]表示前i种物品中选取若干件物品放入空间为j的背包中所能得到的最大价值。
	 * 
	 * F[i][j]=max{
	 * 	F[i-1][j],          不放第i件物品
	 * 	F[i-1][j-C[i]]+V[i] 放第i件物品(j=>C[i])
	 * }
	 * </pre>
	 * @url http://blog.csdn.net/wumuzi520/article/details/7014559
	 * @param N 物品种类数
	 * @param C 每种物品的容量
	 * @param V 每种物品的价值
	 * @param W 背包的总容量
	 * @return
	 */
	public void fun1(int N, List<Integer> C, List<Integer> V, int W) {
		int[][] F=new int[N+1][W+1];
		// initialization (not necessary for java, demonstrate for completeness)
		for(int i=0; i<=N; i++) {
			F[i][0]=0;
		}
		for(int j=0; j<=W; j++) {
			F[0][j]=0;
		}
		
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=W; j++) {
				if(C.get(i-1)<=j) {
					F[i][j]=Math.max(F[i-1][j], F[i-1][j-C.get(i-1)]+V.get(i-1));
				}else {
					F[i][j]=F[i-1][j];
				}
			}
		}
		
		System.out.println("背包内物品最大价值="+F[N][W]);
		System.out.println("背包内物品信息如下：");
		int i=N, j=W;
		while(i>0 && j>0) {
			if(j>=C.get(i-1) && F[i][j]==F[i-1][j-C.get(i-1)]+V.get(i-1)) {
				int id=i-1;
				System.out.println("id=" + id + "\tC="+C.get(id)+"\tV="+V.get(id));
				j=j-C.get(id);
			}
			i--;
		}
	}
	
	/**
	 * 对fun1空间优化
	 * <pre>
	 * F[j]表示前i种物品中选取若干件物品放入空间为j的背包中所能得到的最大价值。
	 * 
	 * F[j]=max{
	 * 	F[j],          不放第i件物品
	 * 	F[j-C[i]]+V[i] 放第i件物品(j=>C[i])
	 * }
	 * </pre>
	 * @url http://blog.csdn.net/wumuzi520/article/details/7014559
	 * @param N
	 * @param C
	 * @param V
	 * @param W
	 * @return
	 */
	public void fun11(int N, List<Integer> C, List<Integer> V, int W) {
		int[] F=new int[W+1];
		// whether item i is in backpack
		boolean[][] Path=new boolean [N+1][W+1];
		// initialization (not necessary for java, demonstrate for completeness)
		for(int i=0; i<=W; i++) {
			F[i]=0;
		}
		for(int i=0; i<=N; i++) {
			for(int j=0; j<=W; j++) {
				Path[i][j]=false;
			}
		}
		
//		for(int i=1; i<=N; i++) {
//			for(int j=W; j>=1; j--) {
//				if(C.get(i-1)<=j) {
//					if(F[j] < F[j-C.get(i-1)]+V.get(i-1)) {
//						F[j]=F[j-C.get(i-1)]+V.get(i-1);
//						Path[i][j]=true;
//					}
//				}
//			}
//		}
		//将上面优化一下
		for(int i=1; i<=N; i++) {
			for(int j=W; j>=C.get(i-1); j--) {
				if(F[j] < F[j-C.get(i-1)]+V.get(i-1)) {
					F[j]=F[j-C.get(i-1)]+V.get(i-1);
					Path[i][j]=true;
				}
			}
		}
		
		System.out.println("背包内物品最大价值="+F[W]);
		System.out.println("背包内物品信息如下：");
		int i=N, j=W;
		while(i>0 && j>0) {
			if(Path[i][j]) {
				int id=i-1;
				System.out.println("id=" + id + "\tC="+C.get(id)+"\tV="+V.get(id));
				j=j-C.get(id);
			}
			i--;
		}
	}

	/**
	 * 问题：现在往背包里面装东西，求能使背包内的物品价值最大时的最优化方案总数
	 * <pre>
	 * G[i][j]表示F[i][j]对应的方案总数
	 * 
	 * G[i][j]={
	 * 	G[i-1][j],          			if F[i][j]=F[i-1][j] 且 F[i][j]!=F[i-1][j-C[i]]+W[i]
	 * 	G[i-1][j-C[i]]      			if F[i][j]=F[i-1][j-C[i]]+W[i] 且 F[i][j]!=F[i-1][j]
	 * 	G[i][j]=G[i-1][j-C[i]]+ G[i-1][j] 	if F[i][j]=F[i-1][j-C[i]]+W[i] 且 F[i][j]=F[i-1][j]
	 * }
	 * </pre>
	 * @url http://blog.csdn.net/wumuzi520/article/details/7019131
	 * @param N
	 * @param C
	 * @param V
	 * @param W
	 * @return
	 */
	public void fun2(int N, List<Integer> C, List<Integer> V, int W) {
		int[][] F=new int[N+1][W+1];
		int[][] G=new int[N+1][W+1];
		// initialization
		for(int i=0; i<=N; i++) {
			F[i][0]=0;
			for(int j=0; j<=W; j++) {
				G[i][j]=1;
			}
		}
		for(int j=0; j<=W; j++) {
			F[0][j]=0;
		}
		
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=W; j++) {
				if(C.get(i-1)<=j) {
					if(F[i-1][j] < F[i-1][j-C.get(i-1)]+V.get(i-1)) {
						F[i][j]=F[i-1][j-C.get(i-1)]+V.get(i-1);
						G[i][j]=G[i-1][j-C.get(i-1)];
					}else if(F[i-1][j] == F[i-1][j-C.get(i-1)]+V.get(i-1)){
						F[i][j]=F[i-1][j];
						G[i][j]=G[i-1][j]+G[i-1][j-C.get(i-1)];
					}else {
						F[i][j]=F[i-1][j];
						G[i][j]=G[i-1][j];
					}
				}else {
					F[i][j]=F[i-1][j];
					G[i][j]=G[i-1][j];
				}
			}
		}
		
		System.out.println("最优方案总数="+G[N][W]);
	}
	
	/**
	 * 对fun2空间优化
	 * <pre>
	 * G[j]表示F[j]对应的方案总数
	 * 
	 * G[j]={
	 * 	G[j],          			if F[j]=F[j] 且 F[j]!=F[j-C[i]]+W[i]
	 * 	G[j-C[i]]      			if F[j]=F[j-C[i]]+W[i] 且 F[j]!=F[j]
	 * 	G[j]=G[j-C[i]]+ G[j] 	if F[j]=F[j-C[i]]+W[i] 且 F[j]=F[j]
	 * }
	 * </pre>
	 * @param N
	 * @param C
	 * @param V
	 * @param W
	 */
	public void fun21(int N, List<Integer> C, List<Integer> V, int W) {
		int[] F=new int[W+1];
		int[] G=new int[W+1];
		for(int j=0; j<=W; j++) {
			G[j]=1;
		}
		for(int i=1; i<=N; i++) {
//			for(int j=W; j>=1; j--) {
//				if(C.get(i-1)<=j) {
//					if(F[j] < F[j-C.get(i-1)]+V.get(i-1)) {
//						F[j]=F[j-C.get(i-1)]+V.get(i-1);
//						G[j]=G[j-C.get(i-1)];
//					}else if(F[j] == F[j-C.get(i-1)]+V.get(i-1)){
//						F[j]=F[j];
//						G[j]=G[j]+G[j-C.get(i-1)];
//					}
//				}
//			}
			//将上面优化一下
			for(int j=W; j>=C.get(i-1); j--) {
				if(F[j] < F[j-C.get(i-1)]+V.get(i-1)) {
					F[j]=F[j-C.get(i-1)]+V.get(i-1);
					G[j]=G[j-C.get(i-1)];
				}else if(F[j] == F[j-C.get(i-1)]+V.get(i-1)){
					F[j]=F[j];
					G[j]=G[j]+G[j-C.get(i-1)];
				}
			}
		}
		System.out.println("最优方案总数="+G[W]);
	}
}

class MultipleBackPack {
	
}

/**
 * 完全背包问题
 * 
 * 一个背包总容量为W，现在有N个物品，第i个物品体积为C[i]，价值为V[i]，每个物品都有无限多件。现在往背包里面装东西，怎么装能使背包内的物品价值最大？
 * @author tingming
 *
 */
class FullBackPack {

	/**
	 * 问题：现在往背包里面装东西，求能使背包内的物品价值最大时的最大价值以及此时背包内的物品信息
	 * <pre>
	 * F[i][j]=max(F[i-1][j-k*C[i]]+k*V[i]);
	 * 
	 * F[i-1][j-k*C[i]]+k*V[i]表示前i-1种物品中选取若干件物品放入空间为j-k*C[i]的背包中所能得到的最大价值加上k件第i种物品的价值之和
	 * </pre>
	 * @url http://blog.csdn.net/wumuzi520/article/details/7014830
	 * @param N 物品种类数
	 * @param C 每种物品的容量
	 * @param V 每种物品的价值
	 * @param W 背包的总容量
	 * @return
	 */
	public void fun1(int N, List<Integer> C, List<Integer> V, int W) {
		int[][] F=new int[N+1][W+1];
		// initialization (not necessary for java, demonstrate for completeness)
		for(int i=0; i<=N; i++) {
			F[i][0]=0;
		}
		for(int j=0; j<=W; j++) {
			F[0][j]=0;
		}
		
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=W; j++) {
				for(int k=0; k<=j/C.get(i-1); k++) {
					if(k*C.get(i-1)<=j) {
						F[i][j]=Math.max(F[i][j], F[i-1][j-k*C.get(i-1)]+k*V.get(i-1));
					}else {
						F[i][j]=F[i][j];
					}
				}
			}
		}
		
		System.out.println("背包内物品最大价值="+F[N][W]);
		System.out.println("背包内物品信息如下：");
		int i=N, j=W;
		while(i>0 && j>0) {
			if(j>=C.get(i-1) && F[i][j]==F[i][j-C.get(i-1)]+V.get(i-1)) {
				int id=i-1;
				System.out.println("id=" + id + "\tC="+C.get(id)+"\tV="+V.get(id));
				j=j-C.get(id);
			}else if(F[i][j]==F[i-1][j]) {
				i--;
			}
		}
	}
	
	/**
	 * 对fun1时间优化：转化为01背包问题求解
	 * <pre>
	 * F[i][j]=max{
	 * 	F[i-1][j]		if 不放第i种物品
	 * 	F[i][j-k*C[i]]+k*V[i])	if j>=C[i]并至少放一件第i种物品
	 * }
	 * </pre>
	 * @url http://blog.csdn.net/wumuzi520/article/details/7014830
	 * @param N
	 * @param C
	 * @param V
	 * @param W
	 */
	public void fun11(int N, List<Integer> C, List<Integer> V, int W) {
		int[][] F=new int[N+1][W+1];
		// initialization (not necessary for java, demonstrate for completeness)
		for(int i=0; i<=N; i++) {
			F[i][0]=0;
		}
		for(int j=0; j<=W; j++) {
			F[0][j]=0;
		}
		
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=W; j++) {
				if(C.get(i-1)<=j) {
					F[i][j]=Math.max(F[i-1][j], F[i][j-C.get(i-1)]+V.get(i-1));
				}else {
					F[i][j]=F[i-1][j];
				}
			}
		}
		
		System.out.println("背包内物品最大价值=" + F[N][W]);
		System.out.println("背包内物品信息如下：");
		int i=N, j=W;
		while(i>0 && j>0) {
			if(j>=C.get(i-1) && F[i][j]==F[i][j-C.get(i-1)]+V.get(i-1)) {
				int id=i-1;
				System.out.println("id=" + id + "\tC="+C.get(id)+"\tV="+V.get(id));
				j=j-C.get(id);
			}else if(F[i][j]==F[i-1][j]) {
				i--;
			}
		}
	}
	
	/**
	 * 对fun12空间优化
	 * <pre>
	 * F[j]=max{
	 * 	F[[j]		if 不放第i种物品
	 * 	F[j-k*C[i]]+k*V[i])	if j>=C[i]并至少放一件第i种物品
	 * }
	 * </pre>
	 * @url http://blog.csdn.net/wumuzi520/article/details/7014830
	 * @param N
	 * @param C
	 * @param V
	 * @param W
	 */
	public void fun12(int N, List<Integer> C, List<Integer> V, int W) {
		int[] F=new int[W+1];
		boolean[][] Path=new boolean[N+1][W+1];
		// initialization (not necessary for java, demonstrate for completeness)
		for(int j=0; j<=W; j++) {
			F[j]=0;
		}
		for(int i=0; i<=N; i++) {
			for(int j=0; j<=W; j++) {
				Path[i][j]=false;
			}
		}
		
		for(int i=1; i<=N; i++) {
//			for(int j=1; j<=W; j++) {
//				if(C.get(i-1)<=j) {
//					if(F[j] < F[j-C.get(i-1)]+V.get(i-1)) {
//						F[j]=F[j-C.get(i-1)]+V.get(i-1);
//						Path[i][j]=true;
//					} else {
//						F[j]=F[j];
//					}
//				}else {
//					F[j]=F[j];
//				}
//			}
			//将上面优化一下
			for(int j=C.get(i-1); j<=W; j++) {
				if(F[j] < F[j-C.get(i-1)]+V.get(i-1)) {
					F[j]=F[j-C.get(i-1)]+V.get(i-1);
					Path[i][j]=true;
				} else {
					F[j]=F[j];
				}
			}
		}
		
		System.out.println("背包内物品最大价值=" + F[W]);
		System.out.println("背包内物品信息如下：");
		int i=N, j=W;
		while(i>0 && j>0) {
			if(Path[i][j]) {
				int id=i-1;
				System.out.println("id=" + id + "\tC="+C.get(id)+"\tV="+V.get(id));
				j=j-C.get(id);
			}else {
				i--;
			}
		}
	}
	
	/**
	 * 问题：现在往背包里面装东西，求能使背包内的物品价值最大时的最优化方案总数
	 * <pre>
	 * G[i][j]表示F[i][j]对应的方案总数
	 * 
	 * G[i][j]={
	 * 	G[i-1][j],          			if F[i][j]=F[i-1][j] 且 F[i][j]!=F[i][j-C[i]]+W[i]
	 * 	G[i-1][j-C[i]]      			if F[i][j]=F[i][j-C[i]]+W[i] 且 F[i][j]!=F[i-1][j]
	 * 	G[i][j]=G[i-1][j-C[i]]+ G[i-1][j] 	if F[i][j]=F[i][j-C[i]]+W[i] 且 F[i][j]=F[i-1][j]
	 * }
	 * </pre>
	 * @url http://blog.csdn.net/wumuzi520/article/details/7019661
	 * @param N
	 * @param C
	 * @param V
	 * @param W
	 */
	public void fun2(int N, List<Integer> C, List<Integer> V, int W) {
		int[][] F=new int[N+1][W+1];
		int[][] G=new int[N+1][W+1];
		// initialization
		for(int i=0; i<=N; i++) {
			F[i][0]=0;
			for(int j=0; j<=W; j++) {
				G[i][j]=1;
			}
		}
		for(int j=0; j<=W; j++) {
			F[0][j]=0;
		}
		
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=W; j++) {
				if(j>=C.get(i-1)) {
					if(F[i][j]<F[i][j-C.get(i-1)]+V.get(i-1)) {
						F[i][j]=F[i][j-C.get(i-1)]+V.get(i-1);
						G[i][j]=G[i][j-C.get(i-1)];
					}else if(F[i][j]==F[i][j-C.get(i-1)]+V.get(i-1)) {
						F[i][j]=F[i-1][j];
						G[i][j]=G[i-1][j]+G[i][j-C.get(i-1)];
					}else {
						F[i][j]=F[i-1][j];
						G[i][j]=G[i-1][j];
					}
				}else {
					F[i][j]=F[i-1][j];
					G[i][j]=G[i-1][j];
				}
			}
		}
		System.out.println("最优方案总数="+G[N][W]);
	}
	
	/**
	 * 对fun2空间优化
	 * <pre>
	 * G[j]表示F[j]对应的方案总数
	 * 
	 * G[j]={
	 * 	G[j],          			if F[j]=F[j] 且 F[j]!=F[j-C[i]]+W[i]
	 * 	G[j-C[i]]      			if F[j]=F[j-C[i]]+W[i] 且 F[j]!=F[j]
	 * 	G[j]=G[j-C[i]]+ G[j] 	if F[j]=F[j-C[i]]+W[i] 且 F[j]=F[j]
	 * }
	 * </pre>
	 * @url http://blog.csdn.net/wumuzi520/article/details/7019661
	 * @param N
	 * @param C
	 * @param V
	 * @param W
	 */
	public void fun21(int N, List<Integer> C, List<Integer> V, int W) {
		int[] F=new int[W+1];
		int[] G=new int[W+1];
		for(int j=0; j<=W; j++) {
			G[j]=1;
		}
		for(int i=1; i<=N; i++) {
//			for(int j=1; j<=W; j++) {
//				if(j>=C.get(i-1)) {
//					if(F[j]<F[j-C.get(i-1)]+V.get(i-1)) {
//						F[j]=F[j-C.get(i-1)]+V.get(i-1);
//						G[j]=G[j-C.get(i-1)];
//					}else if(F[j]==F[j-C.get(i-1)]+V.get(i-1)) {
//						F[j]=F[j];
//						G[j]=G[j]+G[j-C.get(i-1)];
//					}else {
//						F[j]=F[j];
//						G[j]=G[j];
//					}
//				}else {
//					F[j]=F[j];
//					G[j]=G[j];
//				}
//			}
			//将上面优化一下
			for(int j=C.get(i-1); j<=W; j++) {
				if(F[j]<F[j-C.get(i-1)]+V.get(i-1)) {
					F[j]=F[j-C.get(i-1)]+V.get(i-1);
					G[j]=G[j-C.get(i-1)];
				}else if(F[j]==F[j-C.get(i-1)]+V.get(i-1)) {
					F[j]=F[j];
					G[j]=G[j]+G[j-C.get(i-1)];
				}else {
					F[j]=F[j];
					G[j]=G[j];
				}
			}
		}
		System.out.println("最优方案总数="+G[W]);
	}
}
