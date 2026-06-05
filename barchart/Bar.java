public class Bar implements Comparable<Bar> {

    private final String name;
    private final int value;
    private final String category;

    // Creates a new bar.
    public Bar(String name, int value, String category) {
        this.name = name;
        this.value = value;
        this.category = category;
    }

    // Returns the name of this bar.
    public String getName() {
        return name;
    }

    // Returns the value of this bar.
    public int getValue() {
        return value;
    }

    // Returns the category of this bar.
    public String getCategory() {
        return category;
    }

    // Compare two bars by value.
    public int compareTo(Bar that) {
        if (this.value < that.value) {
            return -1;
        }
        if (this.value > that.value) {
            return 1;
        }
        return 0;
    }

    // Sample client.
    public static void main(String[] args) {

        Bar[] bars = new Bar[4];

        bars[0] = new Bar("Beijing", 672, "East Asia");
        bars[1] = new Bar("Delhi", 731, "South Asia");
        bars[2] = new Bar("Tokyo", 697, "East Asia");
        bars[3] = new Bar("Shanghai", 704, "East Asia");

        java.util.Arrays.sort(bars);

        for (int i = 0; i < bars.length; i++) {
            System.out.println(
                bars[i].getName() + " "
                + bars[i].getValue() + " "
                + bars[i].getCategory()
            );
        }
    }
}