import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    // pixel[x][y] stores the packed RGB color
    private int[][] pixel;   // pixel[col][row]
    private int width;
    private int height;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("picture is null");

        width = picture.width();
        height = picture.height();
        pixel = new int[width][height];
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                pixel[x][y] = picture.getRGB(x, y);
    }

    // current picture
    public Picture picture() {
        Picture pic = new Picture(width, height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                pic.setRGB(x, y, pixel[x][y]);
        return pic;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    private void validateX(int x) {
        if (x < 0 || x >= width)
            throw new IllegalArgumentException("x out of range: " + x);
    }

    private void validateY(int y) {
        if (y < 0 || y >= height)
            throw new IllegalArgumentException("y out of range: " + y);
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validateX(x);
        validateY(y);

        if (x == 0 || x == width - 1 || y == 0 || y == height - 1)
            return 1000.0;

        double dx2 = gradientSquared(pixel[x + 1][y], pixel[x - 1][y]);
        double dy2 = gradientSquared(pixel[x][y + 1], pixel[x][y - 1]);
        return Math.sqrt(dx2 + dy2);
    }

    private double gradientSquared(int rgb1, int rgb2) {
        int r1 = (rgb1 >> 16) & 0xFF, g1 = (rgb1 >> 8) & 0xFF, b1 = rgb1 & 0xFF;
        int r2 = (rgb2 >> 16) & 0xFF, g2 = (rgb2 >> 8) & 0xFF, b2 = rgb2 & 0xFF;
        double dr = r1 - r2, dg = g1 - g2, db = b1 - b2;
        return dr * dr + dg * dg + db * db;
    }

    // ---------- core DP used by both seam-finding methods ----------
    // operates on a "virtual" width x height grid using the energy(x,y) function
    // finds a min-energy top-to-bottom path; returns array seam[] of length height
    // giving the column index chosen in each row
    private int[] findVerticalSeamCore() {
        double[][] distTo = new double[width][height];
        int[][] edgeTo = new int[width][height]; // stores column of predecessor

        for (int x = 0; x < width; x++)
            distTo[x][0] = energy(x, 0);

        for (int y = 1; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double best = Double.POSITIVE_INFINITY;
                int bestPrev = -1;
                for (int dx = -1; dx <= 1; dx++) {
                    int px = x + dx;
                    if (px < 0 || px >= width) continue;
                    double cand = distTo[px][y - 1];
                    if (cand < best) {
                        best = cand;
                        bestPrev = px;
                    }
                }
                distTo[x][y] = best + energy(x, y);
                edgeTo[x][y] = bestPrev;
            }
        }

        // find best ending column in bottom row
        double best = Double.POSITIVE_INFINITY;
        int bestX = -1;
        for (int x = 0; x < width; x++) {
            if (distTo[x][height - 1] < best) {
                best = distTo[x][height - 1];
                bestX = x;
            }
        }

        int[] seam = new int[height];
        int cur = bestX;
        for (int y = height - 1; y >= 0; y--) {
            seam[y] = cur;
            if (y > 0) cur = edgeTo[cur][y];
        }
        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return findVerticalSeamCore();
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        // transpose pixel array, reuse vertical-seam logic, transpose result back
        int[][] originalPixel = pixel;
        int originalWidth = width, originalHeight = height;

        int[][] transposed = new int[height][width];
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                transposed[y][x] = pixel[x][y];

        pixel = transposed;
        width = originalHeight;
        height = originalWidth;

        int[] seam = findVerticalSeamCore();

        // restore state
        pixel = originalPixel;
        width = originalWidth;
        height = originalHeight;

        return seam;
    }

    // ---------- validation helper for seam removal ----------
    private void validateSeam(int[] seam, int expectedLength, int otherDimBound) {
        if (seam == null)
            throw new IllegalArgumentException("seam is null");
        if (seam.length != expectedLength)
            throw new IllegalArgumentException("seam has wrong length");
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= otherDimBound)
                throw new IllegalArgumentException("seam entry out of range");
            if (i > 0 && Math.abs(seam[i] - seam[i - 1]) > 1)
                throw new IllegalArgumentException("invalid seam: adjacent entries differ by more than 1");
        }
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (width <= 1)
            throw new IllegalArgumentException("width must be greater than 1");
        validateSeam(seam, height, width);

        int[][] newPixel = new int[width - 1][height];
        for (int y = 0; y < height; y++) {
            int skip = seam[y];
            int nx = 0;
            for (int x = 0; x < width; x++) {
                if (x == skip) continue;
                newPixel[nx][y] = pixel[x][y];
                nx++;
            }
        }
        pixel = newPixel;
        width--;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (height <= 1)
            throw new IllegalArgumentException("height must be greater than 1");
        validateSeam(seam, width, height);

        int[][] newPixel = new int[width][height - 1];
        for (int x = 0; x < width; x++) {
            int skip = seam[x];
            int ny = 0;
            for (int y = 0; y < height; y++) {
                if (y == skip) continue;
                newPixel[x][ny] = pixel[x][y];
                ny++;
            }
        }
        pixel = newPixel;
        height--;
    }

    // unit testing (optional)
    public static void main(String[] args) {
        Picture pic = new Picture(args[0]);
        SeamCarver sc = new SeamCarver(pic);
        System.out.println("Width:  " + sc.width());
        System.out.println("Height: " + sc.height());

        int[] vseam = sc.findVerticalSeam();
        System.out.print("Vertical seam: ");
        for (int v : vseam) System.out.print(v + " ");
        System.out.println();

        int[] hseam = sc.findHorizontalSeam();
        System.out.print("Horizontal seam: ");
        for (int h : hseam) System.out.print(h + " ");
        System.out.println();

        double totalEnergy = 0;
        for (int x = 0; x < sc.width(); x++)
            for (int y = 0; y < sc.height(); y++)
                totalEnergy += sc.energy(x, y);
        System.out.println("Total energy: " + totalEnergy);
    }
}