import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/*
 * Last Updated: August 13, 2024
 */
public class Main {
    public static void main(String[] args){
        // Reads salon data from file "data.bin"
        Salon salon = new Salon();
        try{  
            FileInputStream fi = new FileInputStream("data.bin");
            ObjectInputStream istream = new ObjectInputStream(fi);
            salon = (Salon)istream.readObject();
            istream.close();
        }
        catch (Exception e) {
            System.out.println();
            System.out.println("New salon.");
        }
        
        // Runs program for user
        ClientHandler ch = new ClientHandler(salon);
        OwnerHandler oh = new OwnerHandler(salon);
        Scanner s = new Scanner(System.in);
        int userChoice = 0;
        while (userChoice != 3) {
            System.out.println("(1) I am a client\n(2) I am the salon owner\n(3) I would like to close the app");
            System.out.print("Enter your choice: ");
            userChoice = s.nextInt();
            if(userChoice == 1) {
                System.out.print("\033[H\033[2J");
                ch.printAndGetChoices();
            }
            else if (userChoice == 2) {
                System.out.print("\033[H\033[2J");
                oh.signIn();
            }
            System.out.print("\033[H\033[2J");
            
        }
        s.close();

        // Saves updated salon data to the file "data.bin"
        try {
            FileOutputStream fo = new FileOutputStream("data.bin");
            ObjectOutputStream ostream = new ObjectOutputStream(fo);
            ostream.writeObject(salon);
            ostream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}