import java.util.Scanner;

public class project{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int mynumber= (int)(Math.random()*100);
        int usernumber =0;
        do{
            System.out.println("guess number");
            usernumber= sc.nextInt();

            if (usernumber == mynumber){
                System.out.println("correct number");
                break;
            }else if (usernumber > mynumber){
                System.out.println("your number is too large");

            }else{
                System.out.println("your number is small");
            }

            
        }while(usernumber >= 0);
        System.out.println("my number was:");
        System.out.println(mynumber);
        sc.close();

        }
}