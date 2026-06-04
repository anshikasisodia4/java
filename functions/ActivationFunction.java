public class ActivationFunction {

    // Returns the Heaviside function of x.
    public static double heaviside(double x) {
        if (x < 0.0) {
            return 0.0;
        }
        return 1.0;
    }

    // Returns the sigmoid function of x.
    public static double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    // Returns the hyperbolic tangent of x.
    public static double tanh(double x) {
        double ex = Math.exp(x);
        double enx = Math.exp(-x);
        return (ex - enx) / (ex + enx);
    }

    // Returns the softsign function of x.
    public static double softsign(double x) {
        return x / (1.0 + Math.abs(x));
    }

    // Returns the SQNL function of x.
    public static double sqnl(double x) {
        if (x <= -2.0) {
            return -1.0;
        }
        if (x < 0.0) {
            return x + (x * x) / 4.0;
        }
        if (x < 2.0) {
            return x - (x * x) / 4.0;
        }
        return 1.0;
    }

    public static void main(String[] args) {
        double x = Double.parseDouble(args[0]);

        System.out.println("heaviside(" + x + ") = " + heaviside(x));
        System.out.println("sigmoid(" + x + ") = " + sigmoid(x));
        System.out.println("tanh(" + x + ") = " + tanh(x));
        System.out.println("softsign(" + x + ") = " + softsign(x));
        System.out.println("sqnl(" + x + ") = " + sqnl(x));
    }
}