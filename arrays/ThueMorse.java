public class ThueMorse {
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);

        int[] sequence = new int[n];

        for (int i = 0; i < n; i++) {
            int x = i;
            int count = 0;

            while (x > 0) {
                count += x % 2;
                x /= 2;
            }

            sequence[i] = count % 2;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (sequence[i] == sequence[j]) {
                    System.out.print("+  ");
                } else {
                    System.out.print("-  ");
                }
            }
            System.out.println();
        }
    }
    
}
