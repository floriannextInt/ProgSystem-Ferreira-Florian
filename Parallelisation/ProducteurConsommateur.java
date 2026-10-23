import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.LinkedList;
import java.util.Queue;

public class ProducteurConsommateur {
    private static final int TAILLE_BUFFER = 5;

    private final Integer[] buffer = new Integer[TAILLE_BUFFER];
    private int indexEcriture = 0;
    private int indexLecture = 0;

    private final Semaphore placesLibres = new Semaphore(TAILLE_BUFFER);
    private final Semaphore elementsDisponibles = new Semaphore(0);
    private final Semaphore mutexBuffer = new Semaphore(1);

    // Compteurs de monitoring (atomiques : lisibles sans prendre le mutex)
    private final AtomicInteger totalProduit = new AtomicInteger();
    private final AtomicInteger totalConsomme = new AtomicInteger();

    class Producteur implements Runnable {
        private final int id;
        private final int nbAProduire;
        private final long delaiMs; // simule le temps de fabrication

        Producteur(int id, int nbAProduire, long delaiMs) {
            this.id = id;
            this.nbAProduire = nbAProduire;
            this.delaiMs = delaiMs;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < nbAProduire; i++) {
                    int produit = id * 100 + i;
                    if (delaiMs > 0) Thread.sleep(delaiMs);

                    placesLibres.acquire();
                    mutexBuffer.acquire();

                    buffer[indexEcriture] = produit;
                    indexEcriture = (indexEcriture + 1) % TAILLE_BUFFER;
                    totalProduit.incrementAndGet();

                    mutexBuffer.release();
                    elementsDisponibles.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    class Consommateur implements Runnable {
        private final int id;
        private final int nbAConsommer;
        private final long delaiMs;

        Consommateur(int id, int nbAConsommer, long delaiMs) {
            this.id = id;
            this.nbAConsommer = nbAConsommer;
            this.delaiMs = delaiMs;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < nbAConsommer; i++) {
                    elementsDisponibles.acquire();
                    mutexBuffer.acquire();

                    buffer[indexLecture] = null;
                    indexLecture = (indexLecture + 1) % TAILLE_BUFFER;
                    totalConsomme.incrementAndGet();

                    mutexBuffer.release();
                    placesLibres.release();

                    if (delaiMs > 0) Thread.sleep(delaiMs);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public int getTotalProduit()  { return totalProduit.get(); }
    public int getTotalConsomme() { return totalConsomme.get(); }

    public String etatBuffer() throws InterruptedException {
        mutexBuffer.acquire();
        try {
            StringBuilder sb = new StringBuilder();
            for (Integer v : buffer) sb.append(v == null ? "[ ]" : "[X]");
            return sb.toString();
        } finally {
            mutexBuffer.release();
        }
    }

    public int producteursEnAttente() { return placesLibres.getQueueLength(); }
    public int consommateursEnAttente() { return elementsDisponibles.getQueueLength(); }
    public int getPlacesLibres() { return placesLibres.availablePermits(); }
    public int getElementsDisponibles() { return elementsDisponibles.availablePermits(); }
}