import java.util.ArrayList;
import java.util.Collections;

public class Tri {
	public static void quickSort(ArrayList<Item> s,int debut,int fin) {
		int pivot;
		if (debut < fin) {
			pivot = (debut+fin) / 2;
			pivot = ranger(s, debut, fin, pivot);
			quickSort(s, debut, pivot-1);
			quickSort(s, pivot+1, fin);
		}
	}
	
	private static int ranger(ArrayList<Item> s, int debut, int fin, int pivot) {
		Collections.swap(s, pivot, fin);
		int i = fin - 1;
		int j = fin - 1;
		while (i >= debut) {
			if (s.get(i).inferiorTo(s.get(fin))) Collections.swap(s, i, j--);
			i--;
		}
		j++;
		Collections.swap(s, fin, j);
		return j;
	}
}
