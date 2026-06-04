public class AudioCollage {

    // Amplify every sample by alpha
    public static double[] amplify(double[] a, double alpha) {
        double[] b = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = alpha * a[i];
        }
        return b;
    }

    // Reverse the samples
    public static double[] reverse(double[] a) {
        double[] b = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = a[a.length - 1 - i];
        }
        return b;
    }

    // Merge two sounds end-to-end
    public static double[] merge(double[] a, double[] b) {
        double[] c = new double[a.length + b.length];

        for (int i = 0; i < a.length; i++) {
            c[i] = a[i];
        }

        for (int i = 0; i < b.length; i++) {
            c[a.length + i] = b[i];
        }

        return c;
    }

    // Mix two sounds together
    public static double[] mix(double[] a, double[] b) {
        int n = Math.max(a.length, b.length);
        double[] c = new double[n];

        for (int i = 0; i < n; i++) {
            if (i < a.length) c[i] += a[i];
            if (i < b.length) c[i] += b[i];
        }

        return c;
    }

    // Change playback speed
    public static double[] changeSpeed(double[] a, double alpha) {
        int n = (int) (a.length / alpha);
        double[] b = new double[n];

        for (int i = 0; i < n; i++) {
            b[i] = a[(int) (i * alpha)];
        }

        return b;
    }

    public static void main(String[] args) {

        double[] harp = StdAudio.read("harp.wav");
        double[] piano = StdAudio.read("piano.wav");
        double[] singer = StdAudio.read("singer.wav");
        double[] beat = StdAudio.read("beatbox.wav");

        double[] part1 = amplify(harp, 0.5);
        double[] part2 = reverse(piano);
        double[] part3 = mix(singer, beat);
        double[] part4 = changeSpeed(harp, 1.5);

        double[] collage = merge(
                merge(part1, part2),
                merge(part3, part4)
        );

        StdAudio.play(collage);
    }
}