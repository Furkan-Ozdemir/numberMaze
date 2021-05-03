import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import enigma.console.TextAttributes;
import java.awt.Color;
import java.util.Random;

public class Game {
   public enigma.console.Console cn = Enigma.getConsole("Number Maze");
   public TextMouseListener tmlis; 
   public KeyListener klis;
   static Stack leftStack=new Stack(10);
   static Stack rightStack=new Stack(10);
   //            60,13 -> left baccking orta koordinatları
   int leftStackX = 60 ,leftStackY=14, rightStackX = 67 ,rightStackY = 14, topRightX = 58, topRightY=2;

   // ------ Standard variables for mouse and keyboard ------
   public int mousepr;          // mouse pressed?
   public int mousex, mousey;   // mouse text coords.
   public int keypr;   // key pressed?
   public int rkey;    // key   (for press/release)
   // ----------------------------------------------------
   
   
   Game() throws Exception {   // --- Contructor
                 
      // ------ Standard code for mouse and keyboard ------ Do not change
      tmlis=new TextMouseListener() {
         public void mouseClicked(TextMouseEvent arg0) {}
         public void mousePressed(TextMouseEvent arg0) {
            if(mousepr==0) {
               mousepr=1;
               mousex=arg0.getX();
               mousey=arg0.getY();
            }
         }
         public void mouseReleased(TextMouseEvent arg0) {}
      };
      cn.getTextWindow().addTextMouseListener(tmlis);
    
      klis=new KeyListener() {
         public void keyTyped(KeyEvent e) {}
         public void keyPressed(KeyEvent e) {
            if(keypr==0) {
               keypr=1;
               rkey=e.getKeyCode();
            }
         }
         public void keyReleased(KeyEvent e) {}
      };
      cn.getTextWindow().addKeyListener(klis);
      // ----------------------------------------------------

      int randomInt = new Random().nextInt(9) + 1;

      int px=15,py=5; // p harfinin konulduğu koordinat = gameBoard[5][15]
      cn.setTextAttributes(new TextAttributes(Color.blue,Color.BLACK));
      cn.getTextWindow().setCursorPosition(px,py);
      System.out.print(randomInt);

      cn.setTextAttributes(new TextAttributes(Color.WHITE,Color.BLACK));
      while(true) {

         if(keypr==1) {    // if keyboard button pressed

            if(rkey==KeyEvent.VK_LEFT && isBlank(Numbers.gameBoard[py][px-1])){ // solda boşluk varsa
               cn.getTextWindow().output(px, py, ' ');
               Numbers.gameBoard[py][px]=" ";
               px--;
               Numbers.gameBoard[py][px]= String.valueOf(randomInt);
            }
            else if(rkey==KeyEvent.VK_LEFT && isNumber(Numbers.gameBoard[py][px-1]) && Integer.parseInt(Numbers.gameBoard[py][px-1]) <= randomInt ){ // benim sayım soldaki sayıdan büyükse
               cn.getTextWindow().output(px, py, ' '); // sayıyı yiyip stacke atıcak bunu her yön için yap
               cn.getTextWindow().output(leftStackX,leftStackY--,Numbers.gameBoard[py][px-1].charAt(0));
               leftStack.push((int) Numbers.gameBoard[py][px-1].charAt(0)); // inte çevir
               Numbers.gameBoard[py][px]=" ";
               px--;
               Numbers.gameBoard[py][px]= String.valueOf(randomInt);
               placeNewNumbers();

            }
            else if(rkey==KeyEvent.VK_LEFT && isNumber(Numbers.gameBoard[py][px-1]) && Integer.parseInt(Numbers.gameBoard[py][px-1]) > randomInt ){ // benim sayım soldaki sayıdan küçükse
             //öl
            }
             ////////////////////////////
            if(rkey==KeyEvent.VK_RIGHT && isBlank(Numbers.gameBoard[py][px+1])){
               cn.getTextWindow().output(px, py, ' ');
               Numbers.gameBoard[py][px]=" ";
               px++;
               Numbers.gameBoard[py][px]= String.valueOf(randomInt);
            }
            else if(rkey==KeyEvent.VK_RIGHT && isNumber(Numbers.gameBoard[py][px+1]) && Integer.parseInt(Numbers.gameBoard[py][px+1]) <= randomInt ){// benim sayım sağdaki sayıdan büyüks
               cn.getTextWindow().output(px, py, ' ');
               cn.getTextWindow().output(leftStackX,leftStackY--,Numbers.gameBoard[py][px+1].charAt(0));
               leftStack.push((int)Numbers.gameBoard[py][px+1].charAt(0));
               Numbers.gameBoard[py][px]=" ";
               px++;
               Numbers.gameBoard[py][px]= String.valueOf(randomInt);
               placeNewNumbers();
            }
            else if(rkey==KeyEvent.VK_RIGHT && isNumber(Numbers.gameBoard[py][px+1]) && Integer.parseInt(Numbers.gameBoard[py][px+1]) > randomInt ){// benim sayım sağdaki sayıdan büyüks
            //öl
            }
            //////////////////
            if(rkey==KeyEvent.VK_UP && isBlank(Numbers.gameBoard[py-1][px])){
               cn.getTextWindow().output(px, py, ' ');
               Numbers.gameBoard[py][px]=" ";
               py--;
               Numbers.gameBoard[py][px]= String.valueOf(randomInt);
            }
            else if(rkey==KeyEvent.VK_UP && isNumber(Numbers.gameBoard[py-1][px]) && Integer.parseInt(Numbers.gameBoard[py-1][px]) <= randomInt ){
               cn.getTextWindow().output(px, py, ' ');
               cn.getTextWindow().output(leftStackX,leftStackY--,Numbers.gameBoard[py-1][px].charAt(0));
               leftStack.push((int)Numbers.gameBoard[py-1][px].charAt(0));
               Numbers.gameBoard[py][px]=" ";
               py--;
               Numbers.gameBoard[py][px]= String.valueOf(randomInt);
               placeNewNumbers();
            }
            else if(rkey==KeyEvent.VK_UP && isNumber(Numbers.gameBoard[py-1][px]) && Integer.parseInt(Numbers.gameBoard[py-1][px]) > randomInt ){
               //öl,

            }
            ////////////////////
            if(rkey==KeyEvent.VK_DOWN && isBlank(Numbers.gameBoard[py+1][px])){
               cn.getTextWindow().output(px, py, ' ');
               Numbers.gameBoard[py][px]=" ";
               py++;
               Numbers.gameBoard[py][px]= String.valueOf(randomInt);
            }
            else if(rkey==KeyEvent.VK_DOWN && isNumber(Numbers.gameBoard[py+1][px]) && Integer.parseInt(Numbers.gameBoard[py+1][px]) <= randomInt ){
               cn.getTextWindow().output(px, py, ' ');
               cn.getTextWindow().output(leftStackX,leftStackY--,Numbers.gameBoard[py+1][px].charAt(0));
               leftStack.push((int)Numbers.gameBoard[py+1][px].charAt(0));
               Numbers.gameBoard[py][px]=" ";
               py++;
               Numbers.gameBoard[py][px]= String.valueOf(randomInt);
               placeNewNumbers();
            }
            else if (rkey==KeyEvent.VK_DOWN && isNumber(Numbers.gameBoard[py+1][px]) && Integer.parseInt(Numbers.gameBoard[py+1][px]) > randomInt){
               //öl
            }
            if(rkey == KeyEvent.VK_Q){
               if(!leftStack.isEmpty()) {// boş degilken popla boşsa poplama
                int popped = (int) leftStack.pop();
                  cn.getTextWindow().output(rightStackX, rightStackY--, (char) popped);
                  rightStack.push(popped);
                  cn.getTextWindow().output(leftStackX,++leftStackY,' ');
               }
            }
            if(rkey == KeyEvent.VK_R){
               if(!rightStack.isEmpty()) {// boş degilken popla boşsa poplama
                  int popped = (int) rightStack.pop();
                  cn.getTextWindow().output(leftStackX,leftStackY--, (char) popped);
                  leftStack.push(popped);
                  cn.getTextWindow().output(rightStackX,++rightStackY,' ');
               }
            }
            
            char rckey=(char)rkey;
            //        left          right          up            down
            if(rckey=='%' || rckey=='\'' || rckey=='&' || rckey=='(') {
               cn.getTextWindow().setCursorPosition(px,py);
               cn.setTextAttributes(new TextAttributes(Color.blue,Color.BLACK));
               System.out.print(Numbers.gameBoard[py][px]);
               cn.setTextAttributes(new TextAttributes(Color.WHITE,Color.BLACK));

            }

            keypr=0;    // last action  
         }
         Thread.sleep(20);
      }
   }
   public boolean isNumber(String str){
      try {
         int d = Integer.parseInt(str);
      } catch (NumberFormatException nfe) {
         return false;
      }
      return true;
   }
   public boolean isBlank(String str){
      return str.equals(" ");
   }
   public void placeNewNumbers() { // sağ üstten sil.

      int popped = (int) Numbers.tempQueue.dequeue();
      int randomX=new Random().ints(1, (21 + 1)).limit(1).findFirst().getAsInt();
      int randomY =new Random().ints(1, (53 + 1)).limit(1).findFirst().getAsInt();

      while(!isBlank(Numbers.gameBoard[randomX][randomY])){
          randomX=new Random().ints(1, (21 + 1)).limit(1).findFirst().getAsInt();
          randomY =new Random().ints(1, (53 + 1)).limit(1).findFirst().getAsInt();
      }
      cn.getTextWindow().setCursorPosition(randomY,randomX);
      if(popped <=3){
         cn.setTextAttributes(Numbers.green);
         System.out.print(popped);
         cn.setTextAttributes(Numbers.defaultColor);

      }
      else if (popped > 3 && popped <= 6){
         cn.setTextAttributes(Numbers.yellow);
         System.out.print(popped);
         cn.setTextAttributes(Numbers.defaultColor);

      }
      else{
         cn.setTextAttributes(Numbers.red);
         System.out.print(popped);
         cn.setTextAttributes(Numbers.defaultColor);
      }
      Numbers.gameBoard[randomX][randomY] = String.valueOf(popped);
      //58.2 sağ üstün koordinatları
      cn.getTextWindow().output(topRightX,topRightY,' ');
      topRightX+=2;
   }
}
