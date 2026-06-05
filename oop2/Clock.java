public class Clock {
    private int hours;
    private int minutes;

    // Creates a clock whose initial time is h hours and m minutes.
    public Clock(int h, int m) {
        if (h < 0 || h > 23 || m < 0 || m > 59) {
            throw new IllegalArgumentException("invalid time");
        }

        hours = h;
        minutes = m;
    }

    // Creates a clock whose initial time is specified as a string HH:MM.
    public Clock(String s) {
        if (s == null || s.length() != 5 || s.charAt(2) != ':') {
            throw new IllegalArgumentException("invalid time");
        }

        int h;
        int m;

        try {
            h = Integer.parseInt(s.substring(0, 2));
            m = Integer.parseInt(s.substring(3, 5));
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("invalid time");
        }

        if (h < 0 || h > 23 || m < 0 || m > 59) {
            throw new IllegalArgumentException("invalid time");
        }

        hours = h;
        minutes = m;
    }

    // Returns a string representation of this clock.
    public String toString() {
        String h = "";
        String m = "";

        if (hours < 10) {
            h = "0" + hours;
        }
        else {
            h = "" + hours;
        }

        if (minutes < 10) {
            m = "0" + minutes;
        }
        else {
            m = "" + minutes;
        }

        return h + ":" + m;
    }

    // Is the time on this clock earlier than the time on that one?
    public boolean isEarlierThan(Clock that) {
        if (this.hours < that.hours) {
            return true;
        }

        if (this.hours > that.hours) {
            return false;
        }

        return this.minutes < that.minutes;
    }

    // Adds 1 minute to the time on this clock.
    public void tic() {
        minutes++;

        if (minutes == 60) {
            minutes = 0;
            hours++;

            if (hours == 24) {
                hours = 0;
            }
        }
    }

    // Adds delta minutes to the time on this clock.
    public void toc(int delta) {
        if (delta < 0) {
            throw new IllegalArgumentException("delta must be nonnegative");
        }

        int totalMinutes = hours * 60 + minutes;
        totalMinutes = (totalMinutes + delta) % (24 * 60);

        hours = totalMinutes / 60;
        minutes = totalMinutes % 60;
    }

    // Test client.
    public static void main(String[] args) {

        Clock c1 = new Clock(23, 59);
        System.out.println(c1);

        c1.tic();
        System.out.println(c1);

        Clock c2 = new Clock("06:30");
        System.out.println(c2);

        c2.toc(90);
        System.out.println(c2);

        Clock c3 = new Clock("08:00");
        Clock c4 = new Clock("09:00");

        System.out.println(c3.isEarlierThan(c4));
        System.out.println(c4.isEarlierThan(c3));
    }
}