import java.util.Random;

public class Merge {

	public static void main(String[] args) throws Exception {

		Random gen = new Random();
		int length = 1+gen.nextInt(200);
		int k = gen.nextInt(1000);
		
		int[] a = new int[length];
		int[] b = new int[2*length];
		
		for (int i=0; i<length; i++){
			a[i] = k;
			b[i] = k+gen.nextInt(3);
			k+=gen.nextInt(100);
		}
		b[length]=k;
		for (int i=length+1; i<2*length; i++){
			b[i] = 0;
		}
		
		new Merge().merge(b,a);
	}
	
	void merge(int[] a, int[] b) throws Exception{
		int len_a = a.length;
		int len_b = b.length;
		
		if(len_a == 2*len_b){
			in_place_merge(b, a);
		}
		else if (len_b == 2* len_a) {
			in_place_merge(a, b);
		}
		else
			throw new Exception("Lengths are invalid. They have to x, 2x.");
	}
	
	private int[] in_place_merge(int[] a, int[]b){
		int i=0,j = 0;
		int len_a = a.length;
		int len_b = b.length;

		while(i<len_a && j<len_b){
			
			if(a[i] < b[j]){
				int k = len_b-1;
				while(k > j){
					b[k] = b[k-1];
					k--;
				}
				b[j] = a[i];
				i++;j++;
			}
			else if(a[i] >= b[j]){
				j++;
			}
		}

		for(int k=0; k<len_b; k++)
			System.out.print(b[k]+", ");
		
		return b;
	}
}