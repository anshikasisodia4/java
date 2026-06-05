import java.awt.Color;

public class KernelFilter {

    private static int clamp(double value) {
        if (value < 0) return 0;
        if (value > 255) return 255;
        return (int) Math.round(value);
    }

    private static Picture applyKernel(Picture picture, double[][] kernel) {
        int width = picture.width();
        int height = picture.height();

        Picture result = new Picture(width, height);

        int k = kernel.length;
        int offset = k / 2;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                double red = 0.0;
                double green = 0.0;
                double blue = 0.0;

                for (int i = 0; i < k; i++) {
                    for (int j = 0; j < k; j++) {

                        int nx = (x + i - offset + width) % width;
                        int ny = (y + j - offset + height) % height;

                        Color color = picture.get(nx, ny);

                        red += kernel[i][j] * color.getRed();
                        green += kernel[i][j] * color.getGreen();
                        blue += kernel[i][j] * color.getBlue();
                    }
                }

                Color newColor = new Color(
                        clamp(red),
                        clamp(green),
                        clamp(blue));

                result.set(x, y, newColor);
            }
        }

        return result;
    }

    // Identity filter
    public static Picture identity(Picture picture) {
        double[][] kernel = {
            { 0, 0, 0 },
            { 0, 1, 0 },
            { 0, 0, 0 }
        };

        return applyKernel(picture, kernel);
    }

    // Gaussian blur
    public static Picture gaussian(Picture picture) {
        double[][] kernel = {
            { 1.0 / 16, 2.0 / 16, 1.0 / 16 },
            { 2.0 / 16, 4.0 / 16, 2.0 / 16 },
            { 1.0 / 16, 2.0 / 16, 1.0 / 16 }
        };

        return applyKernel(picture, kernel);
    }

    // Sharpen
    public static Picture sharpen(Picture picture) {
        double[][] kernel = {
            { 0, -1, 0 },
            { -1, 5, -1 },
            { 0, -1, 0 }
        };

        return applyKernel(picture, kernel);
    }

    // Laplacian
    public static Picture laplacian(Picture picture) {
        double[][] kernel = {
            { -1, -1, -1 },
            { -1, 8, -1 },
            { -1, -1, -1 }
        };

        return applyKernel(picture, kernel);
    }

    // Emboss
    public static Picture emboss(Picture picture) {
        double[][] kernel = {
            { -2, -1, 0 },
            { -1, 1, 1 },
            { 0, 1, 2 }
        };

        return applyKernel(picture, kernel);
    }

    // Motion blur
    public static Picture motionBlur(Picture picture) {
        double[][] kernel = {
            { 1.0 / 9, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 1.0 / 9, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 1.0 / 9, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 1.0 / 9, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 1.0 / 9, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 1.0 / 9, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 1.0 / 9, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 1.0 / 9, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 1.0 / 9 }
        };

        return applyKernel(picture, kernel);
    }

    // Test client
    public static void main(String[] args) {

        Picture picture = new Picture(args[0]);

        identity(picture).show();
        gaussian(picture).show();
        sharpen(picture).show();
        laplacian(picture).show();
        emboss(picture).show();
        motionBlur(picture).show();
    }
}