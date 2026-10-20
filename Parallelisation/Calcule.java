public class Calcule extends Thread {
    
	private int valeur;
	
	private int multiplicateur;
	
	private int index;
	
	private int resultat;
	
	public Calcule (int valeur, int multiplicateur, int index) {
		this.valeur = valeur;
		this.multiplicateur = multiplicateur;
		this.index = index;
		this.resultat = 0;
	}
	
	@Override
	public void run() {
		for (int i = index; i < multiplicateur; i++) {
            this.resultat += this.valeur * this.valeur + this.valeur;
		}
	}
	
	// Réduction
	public getResultat() {
		return this.resultat;
	}
}