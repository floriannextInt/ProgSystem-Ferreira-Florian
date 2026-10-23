import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] args) throws Exception {
        // 1. Connexion au serveur
		Socket socket = new Socket("localhost", 5000);
		System.out.println("Connecté au serveur");
	
        // 2. Flux réseau (vers/depuis le serveur)
		BufferedReader in = new BufferedReader(
			new InputStreamReader(socket.getInputStream())
		);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader clavier = new BufferedReader(
			new InputStreamReader(System.in)
		);
		
        // 3. Flux clavier (entrée utilisateur)
		
        // 4. Boucle d'envoi/réception
		
        // 5. Fermeture
		
		String texte;
		while ((texte = clavier.readLine()) != null) {
			out.println(texte);
			String reponse = in.readLine();
			System.out.println(reponse);
		}
		socket.close();
    }
}