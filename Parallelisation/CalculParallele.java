public class CalculParallele {
	
	private static final int[] DONNEES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final int MULTIPLICATEUR = 1000;

    public static void main(String[] args) {
        System.out.println("=== Calcul Parallèle ===");
	
	Calcule[] = new Calcule[DONNEES.length];
	int somme = 0;
	
	for (int i; i < c.lengh; i++) {
		c[i] = Calcule(DONNEES[i], MULTIPLICATEUR, i)
		c[i].start();
	}
	
	for (int i=0, c.length; i++) {
		c[i].join();
		somme+c[i].get();
	}
}