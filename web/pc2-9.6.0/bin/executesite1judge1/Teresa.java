import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class Teresa {
    public static void main(String [] args) throws IOException {
        Scanner sc = new Scanner(new File("teresa.dat"));
        int x = sc.nextInt();
        for(int i =0; i<x; i++){
            System.out.println(i+1 +":");
            int rows = sc.nextInt();
            int cols = sc.nextInt();
            int [][] m = new int[rows][cols];
            for(int j = 0; j<rows; j++)
                for(int k = 0;k<cols;k++){
                    m[j][k] = sc.nextInt();
                }
            int [] memes = new int[rows];
            for(int c = 0; c<cols; c++) {
                for (int b = 0; b < rows; b++) {
                    memes[b] = m[b][c];
                }

                Arrays.sort(memes);
                for (int b = 0; b < rows; b++) {
                    m[b][c] = memes[b];
                }
            }
                for(int z = 0;z<rows;z++)
                    Arrays.sort(m[z]);
                for(int[]fe:m) {
                    for (int fu : fe) {
                        System.out.print(fu + "    ");
                    }
                    System.out.println();
                }
                System.out.println("++++++++++++");
        }
    }
}
