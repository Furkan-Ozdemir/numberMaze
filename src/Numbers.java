import enigma.console.TextAttributes;
import enigma.core.Enigma;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Numbers {
    static Queue inputList = new Queue(100);
    static Queue nextNumber = new Queue(10);
    static Queue tempQueue = new Queue(10); // bu placeNewNumbers() fonksiyonunda kullanmak için
    static enigma.console.Console cn = Enigma.getConsole("Number Maze",80, 24, 20, 0);
    static String[][] gameBoard = new String[23][55];
    static TextAttributes green = new TextAttributes(Color.GREEN,Color.BLACK);
    static TextAttributes red = new TextAttributes(Color.RED,Color.BLACK);
    static TextAttributes yellow = new TextAttributes(Color.YELLOW,Color.BLACK);
    static TextAttributes blue = new TextAttributes(Color.BLUE,Color.BLACK);
    static TextAttributes defaultColor = new TextAttributes(Color.white,Color.BLACK);
    static int[] high = {1,2,3};
    static int[] mid = {4,5,6};
    static int[] low = {7,8,9};

    public static void generateRandomNumbers(){
        // input listesi için random number generator.
//        1, 2, 3	75 %   (equal probabilities within themselves)
//        4, 5, 6	20 %   (equal probabilities within themselves)
//        7, 8, 9	5 %   (equal probabilities within themselves)
        Random random = new Random();


        for (int i = 0; i < 10; i++) {

            int possibility = random.nextInt(101);
            int index = random.nextInt(3);

            if(possibility <= 5 && !inputList.isFull()) // 0 ile 5 arasındaysa 7 8 9 seç.
            {
                inputList.enqueue(low[index]);
            }
            if (possibility >= 6 && possibility <= 25 && !inputList.isFull()){ // 4 5 6
                inputList.enqueue(mid[index]);
            }
            if(possibility >= 26 && !inputList.isFull()){// 1 2 3
                inputList.enqueue(high[index]);
            }
        }
    }
    public static void generateNextNumbers(){
        // input listesi için random number generator.
//        1, 2, 3	75 %   (equal probabilities within themselves)
//        4, 5, 6	20 %   (equal probabilities within themselves)
//        7, 8, 9	5 %   (equal probabilities within themselves)
        Random random = new Random();


        for (int i = 0; i < 10; i++) {

            int possibility = random.nextInt(101);
            int index = random.nextInt(3);

            if(possibility <= 5 && !nextNumber.isFull()) // 0 ile 5 arasındaysa 7 8 9 seç.
            {
                nextNumber.enqueue(low[index]);
            }
            if (possibility >= 6 && possibility <= 25 && !nextNumber.isFull()){ // 4 5 6
                nextNumber.enqueue(mid[index]);
            }
            if(possibility >= 26 && !nextNumber.isFull()){// 1 2 3
                nextNumber.enqueue(high[index]);
            }
        }
    }
    public static void fileRead(){
        try {
            File myObj = new File("maze.txt");
            Scanner myReader = new Scanner(myObj);
            int j =0;

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
//                cn.getTextWindow().output(data);
                System.out.println(data);

                for (int i = 0; i < data.length(); i++) { // her satırı arraye atıyor
                    gameBoard[j][i] = data.substring(i,i+1);
                }

                Random random = new Random();
                for (int i = 0; i < data.length(); i++) {
                    if(data.charAt(i) != '#' && random.nextInt(25) == 1){
                        cn.getTextWindow().setCursorPosition(i,j);

                        if(inputList.isEmpty()){
                            generateRandomNumbers();
                        }
                       while (!inputList.isEmpty()){
                           if(inputList.peek().equals(Integer.valueOf(1)) || inputList.peek().equals(Integer.valueOf(2)) ||inputList.peek().equals(Integer.valueOf(3))){
                               cn.setTextAttributes(green);
                               String a = String.valueOf(inputList.dequeue());
                               System.out.print(a);
                               gameBoard[j][i] = a;
//                               cn.getTextWindow().output(inputList.dequeue()+"");
                               cn.setTextAttributes(defaultColor);
                               break;
                           }
                           else if(inputList.peek().equals(Integer.valueOf(4)) || inputList.peek().equals(Integer.valueOf(5)) ||inputList.peek().equals(Integer.valueOf(6))){
                               cn.setTextAttributes(yellow);
                               String a = String.valueOf(inputList.dequeue());
                               System.out.print(a);
                               gameBoard[j][i] = a;
                               cn.setTextAttributes(defaultColor);
                               break;
                           }
                           else{
                               cn.setTextAttributes(red);
                               String a =String.valueOf(inputList.dequeue());
                               System.out.print(a);
                               gameBoard[j][i] = a;
                               cn.setTextAttributes(defaultColor);
                               break;
                           }

                       }


                    }
                }
                j++;
                cn.getTextWindow().setCursorPosition(0,j);
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public static void createMenu(){

        cn.getTextWindow().setCursorPosition(58,1);
        System.out.print("<<<<<<<<<<<<<<<<<<<");
        cn.getTextWindow().setCursorPosition(58,2);

        if(nextNumber.isEmpty()){ // sağ üstteki queue da sayı yoksa içini doldurup yazdır
            generateNextNumbers();
            while(!nextNumber.isEmpty()){
                int popped = (int) nextNumber.dequeue();
                System.out.print(popped+" ");
                tempQueue.enqueue(popped);
            }
        }
        else{
            while(!nextNumber.isEmpty()){
                int popped = (int) nextNumber.dequeue();
                System.out.print(popped+" ");
                tempQueue.enqueue(popped);
            }
        }
        cn.getTextWindow().setCursorPosition(58,3);
        System.out.print("<<<<<<<<<<<<<<<<<<<");
        cn.getTextWindow().setCursorPosition(58,6);
        System.out.print(" BackPacks");

        for (int i = 7; i < 15; i++) {
            cn.getTextWindow().setCursorPosition(58,i);
            System.out.print("|   |  |   |");
        }
        cn.getTextWindow().setCursorPosition(58,15);
        System.out.print("+---+  +---+");
        cn.getTextWindow().setCursorPosition(58,16);
        System.out.print("Left   Right");
        cn.getTextWindow().setCursorPosition(58,17);
        System.out.print("  Q      R");
        cn.getTextWindow().setCursorPosition(58,19);
        System.out.print("Score:");
        cn.getTextWindow().setCursorPosition(58,21);
        System.out.print("Time:");
        cn.getTextWindow().setCursorPosition(15,5); // bunu kaldır


    }

}
