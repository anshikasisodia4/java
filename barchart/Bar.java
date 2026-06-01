public class Bar {
    private String name;
    private int value;
    private String category;

    public Bar(String name, int value, String category) {
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
    public String toString() {
        return name + " " + value + " " + category;
    }

    public static void main(String[] args) {
        Bar bar = new Bar("Beijing", 672, "East Asia");
        System.out.println(bar);
    }
}