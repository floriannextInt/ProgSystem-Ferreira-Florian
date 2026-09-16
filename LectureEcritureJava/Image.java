import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Image {
    private int width;
    private int height;

    // pixels[y][x][0=R,1=G,2=B]
    private int[][][] pixels; // pixels[y][x][0=R,1=G,2=B]

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    /**
     * Constructeur : initialise une image vide.
     */
    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[height][width][3];
    }

    /**
     * Définit la couleur d'un pixel à la position (x, y)
     */
    public void setPixel(int x, int y, int r, int g, int b) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            pixels[y][x][0] = r;
            pixels[y][x][1] = g;
            pixels[y][x][2] = b;
        }
    }

    /**
     * Sauvegarde l'image au format texte PPM (P3)
     */
    public void save_txt(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);

        writer.write("P3\n");

        // Écriture des dimensions
        writer.write(width + " " + height + "\n");

        // Écriture de la valeur maximale
        writer.write("255\n");

        // Écriture des pixels
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                writer.write(
                    pixels[y][x][0] + " " +
                    pixels[y][x][1] + " " +
                    pixels[y][x][2] + " "
                );
            }
            writer.write("\n");
        }

        writer.close(); // Fermeture du fichier
    }
    public int getRed(int x, int y) {
        return pixels[y][x][0];
    }

    public int getGreen(int x, int y) {
        return pixels[y][x][1];
    }

    public int getBlue(int x, int y) {
        return pixels[y][x][2];
    }
    public static Image read_txt(String filename) throws IOException {
    Scanner scanner = new Scanner(new java.io.File(filename));

    String format = scanner.next();

    if (!format.equals("P3")) {
        scanner.close();
        throw new IOException("Format PPM non supporté");
    }

    int width = scanner.nextInt();
    int height = scanner.nextInt();

    int max = scanner.nextInt();

    Image img = new Image(width, height);

    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            int r = scanner.nextInt();
            int g = scanner.nextInt();
            int b = scanner.nextInt();

            img.setPixel(x, y, r, g, b);
        }
    }

    scanner.close();

    return img;
    }
}
