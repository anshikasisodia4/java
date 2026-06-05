public class ColorHSB {
    private final int hue;
    private final int saturation;
    private final int brightness;

    // Creates a color with hue h, saturation s, and brightness b.
    public ColorHSB(int h, int s, int b) {
        if (h < 0 || h > 359) {
            throw new IllegalArgumentException("hue out of range");
        }
        if (s < 0 || s > 100) {
            throw new IllegalArgumentException("saturation out of range");
        }
        if (b < 0 || b > 100) {
            throw new IllegalArgumentException("brightness out of range");
        }

        hue = h;
        saturation = s;
        brightness = b;
    }

    // Returns a string representation of this color.
    public String toString() {
        return "(" + hue + ", " + saturation + ", " + brightness + ")";
    }

    // Is this color a shade of gray?
    public boolean isGrayscale() {
        return saturation == 0 || brightness == 0;
    }

    // Returns the squared distance between the two colors.
    public int distanceSquaredTo(ColorHSB that) {
        if (that == null) {
            throw new IllegalArgumentException("argument is null");
        }

        int hueDiff = Math.abs(this.hue - that.hue);
        hueDiff = Math.min(hueDiff, 360 - hueDiff);

        int satDiff = this.saturation - that.saturation;
        int brightDiff = this.brightness - that.brightness;

        return hueDiff * hueDiff
                + satDiff * satDiff
                + brightDiff * brightDiff;
    }

    // Sample client.
    public static void main(String[] args) {
        int h = Integer.parseInt(args[0]);
        int s = Integer.parseInt(args[1]);
        int b = Integer.parseInt(args[2]);

        ColorHSB target = new ColorHSB(h, s, b);

        ColorHSB closest = null;
        int minDistance = Integer.MAX_VALUE;

        while (!StdIn.isEmpty()) {
            String name = StdIn.readString();

            int hue = StdIn.readInt();
            int saturation = StdIn.readInt();
            int brightness = StdIn.readInt();

            ColorHSB color = new ColorHSB(hue, saturation, brightness);

            int distance = target.distanceSquaredTo(color);

            if (distance < minDistance) {
                minDistance = distance;
                closest = color;
            }
        }

        StdOut.println(closest);
    }
}