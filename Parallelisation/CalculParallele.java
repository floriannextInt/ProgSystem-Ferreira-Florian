public class CalculParallele {
    private static final int[] DONNEES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final int MULTIPLICATEUR = 1000;

    public static void main(String[] args){
        
        Calcule[] threads = new Calcule[DONNEES.length];
        
        System.out.println("=== Calcul Parallèle ===");

        long debut = System.nanoTime();
        long sommeTotal = 0;

        // TODO: Ce code sera remplacé par une version parallèle
        for (int i = 0; i < DONNEES.length; i++) {
            
            threads[i] = new Calcule(DONNEES[i], MULTIPLICATEUR, i);
            
            threads[i].start();
            
        }
        
        for (int j = 0; j < DONNEES.length; j++) {
            
            try {
                threads[j].join();
            } catch (InterruptedException e) {
                System.err.println("Le thread a été interrompu : " + e.getMessage());
            }
            
            sommeTotal += threads[j].getResults();
            System.out.println("Traitement de " + threads[j].getResults() +
                               " terminé : " + threads[j].getResults());
        }

        long duree = (System.nanoTime() - debut) / 1_000_000;
        System.out.println("Résultat total : " + sommeTotal);
        System.out.println("Durée : " + duree + " ms");
    }
}