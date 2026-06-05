import java.util.Arrays;

public class BarChartRacer {

    public static void main(String[] args) {

        StdDraw.setCanvasSize(1000, 700);
        StdDraw.enableDoubleBuffering();

        String filename = args[0];
        int k = Integer.parseInt(args[1]);

        In in = new In(filename);

        String title = in.readLine();
        String xAxis = in.readLine();
        String source = in.readLine();

        BarChart chart = new BarChart(title, xAxis, source);

        in.readLine(); // skip blank line after header

        while (in.hasNextLine()) {

            chart.reset();

            String line = in.readLine();

            if (line == null || line.trim().isEmpty()) {
                continue;
            }

            int n = Integer.parseInt(line);

            Bar[] bars = new Bar[n];
            String caption = "";

            for (int i = 0; i < n; i++) {

                String record = in.readLine();
                String[] fields = record.split(",");

                caption = fields[0];
                String name = fields[1];
                int value = Integer.parseInt(fields[3]);
                String category = fields[4];

                bars[i] = new Bar(name, value, category);
            }

            Arrays.sort(bars);

            for (int i = Math.max(0, n - k); i < n; i++) {
                chart.add(
                    bars[i].getName(),
                    bars[i].getValue(),
                    bars[i].getCategory()
                );
            }

            chart.setCaption(caption);

            StdDraw.clear();
            chart.draw();
            StdDraw.show();
            StdDraw.pause(50);

            if (in.hasNextLine()) {
                in.readLine(); // skip blank line between groups
            }
        }
    }
}