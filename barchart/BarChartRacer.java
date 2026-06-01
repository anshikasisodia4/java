
public class Bar implements Comparable<Bar> {

    private final String name;
    private final int value;
    private final String category;

    public Bar(String name, int value, String category) {
        if (name == null || category == null || value < 0) {
            throw new IllegalArgumentException();
        }

        this.name = name;
        this.value = value;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public int compareTo(Bar that) {
        if (that == null) {
            throw new NullPointerException();
        }
        return Integer.compare(this.value, that.value);
    }

    public static void main(String[] args) {

        Bar b1 = new Bar("Beijing", 22674, "East Asia");
        Bar b2 = new Bar("Delhi", 27890, "South Asia");

        System.out.println(b1.getName());
        System.out.println(b1.compareTo(b2));
    }
}