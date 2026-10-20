public class TestCompteur {
	
	private static final int NB_TESTS = 50
    private static final int NB_THREADS = 9;
    private static final int NB_INCREMENTS = 1111111;
    private static final int VALEUR_ATTENDUE = NB_THREADS * NB_INCREMENTS;

    public static void main(String[] args) {
	
	for (int test = 1; test <= NB_TESTS; test++) {

            CompteurDangereux.resetCompteur();

            Thread[] threads = new Thread[NB_THREADS];
            for (int i = 0; i < NB_THREADS; i++) {
                compteur = new CompteurDangereux.Incrementeur("Thread " + (i+1), NB_THREADS)
				
            } 
}