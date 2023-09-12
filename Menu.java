import java.util.Scanner;

public class Menu {

    public void displayMenu(){
        System.out.println("0)EXIT");
        System.out.println("1)Converti FILE PDB");
        System.out.println("2)Converti FILE AAS");
    }

    public int scelta(){
        System.out.println("FAI UNA SCELTA : (0-2)");

        Scanner s= new Scanner(System.in);
        int n;
        do {
            n=  s.nextInt();
        }while (n<0 || n>2);

       return n;
    }


}
