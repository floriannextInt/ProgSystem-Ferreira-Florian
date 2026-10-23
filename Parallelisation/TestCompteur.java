public class TestCompteur {
    
    private static int nbThread = 9;
    private static int nbIncrement = 1111111;
    private static int nbTentative = 500;
    private static int nbJuste = 0;
    private static int nbMin = nbIncrement * nbThread;
    private static int nbMax = nbIncrement * nbThread;
    
    public static void main(String[] args) {
        for(int i = 1 ; i <= nbTentative ; i++) {
            CompteurDangereux.resetCompteur();
            
            CompteurDangereux.Incrementeur[] taches = new CompteurDangereux.Incrementeur[nbThread];
            Thread[] threads = new Thread[nbThread];
            for (int j = 0 ; j< taches.length ; j++) {
                taches[j] = new CompteurDangereux.Incrementeur("nom", nbIncrement);
                threads[j] = new Thread(taches[j]);
                threads[j].start();
            }      
            
            for (int k = 0 ; k < taches.length ; k++) {
                try {
                    threads[k].join();
                } catch (InterruptedException e) {
                    System.err.println("Interruption du threads : " + e.getMessage());
                }
            }  
            System.out.println("Compteur à la fin " + CompteurDangereux.getCompteur());
            if(CompteurDangereux.getCompteur() == nbIncrement * nbThread) {
                nbJuste++;
            }
            
            if(CompteurDangereux.getCompteur() < nbMin) {
                nbMin = CompteurDangereux.getCompteur();
            }
            
            if (CompteurDangereux.getCompteur() > nbMax) {
                nbMax = CompteurDangereux.getCompteur();
            }
        } 
        System.out.println("Nombre de réussite : " + nbJuste);
        System.out.println("Pourcentage de réussite : " + nbJuste / nbTentative);
        System.out.println("Valeur min obtenu : " + nbMin);
        System.out.println("Valeur max obtenu : " + nbMax);
        
    }
}