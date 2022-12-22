import java.util.*;
import java.lang.Math;

public class OrderTest{
   public static void main(String[] args){
      String[] one = new String[] {"a1,","a2,","a3,"};
      String[] two = new String[] {"b1,","b2,","b3,"};
      String[] three = new String[] {"c1,","c2,"};
      String[] four = new String[] {"d1,","d2,"};
      int count = 0;
      int count2 = 0;
      int count3 = 0;
      String[] possible = new String[25201];
      boolean ab = false;
      boolean ca = false;
      boolean db = false;
      
      for(int i = 0; i < 2000000; i++){
         int counter = 0;
         int[] arrayManager = new int[] {0,0,0,0};
         String temp = "";
         boolean exists = false;
         while(counter < 10){
            int rand = (int)Math.floor(Math.random()*4);
            if(rand == 0 && arrayManager[rand] < 3){
               temp += one[arrayManager[rand]];
               arrayManager[rand]++;
               counter++;
            } else if(rand == 1 && arrayManager[rand] < 3){
               temp += two[arrayManager[rand]];
               arrayManager[rand]++;
               counter++;
            } else if(rand == 2 && arrayManager[rand] < 2){
               temp += three[arrayManager[rand]];
               arrayManager[rand]++;
               counter++;
            } else if(rand == 3 && arrayManager[rand] < 2){
               temp += four[arrayManager[rand]];
               arrayManager[rand]++;
               counter++;
            } else {
               //noop
            }
         }
         
         int j = 0;
         for(; j <= count; j++){
            if(temp.equals(possible[j])){
               exists = true;
            }
            
         }
         if(!exists){
            possible[j] = temp;
            count++;
         }
         count2++;
         //this was added after total histories was determined to exit the loop faster
         if(count == 25200){
            i = 2000001;
         }
      }
      for(int i =0; i < possible.length; i++){
         if(possible[i] != null){
            String[] temp = possible[i].split(",");
            for (int j = 0; j < temp.length; j++){
               if(temp[j].equals("a1")){
                  ab = false;
               } else if(temp[j].equals("b3")){
                  ab = true;
               } else if(temp[j].equals("c2")){
                  ca = false;
               } else if(temp[j].equals("a3")){
                  ca = true;
               } else if(temp[j].equals("d1")){
                  db = false;
               } else if(temp[j].equals("b2")){
                  db = true;
               }
            }
            if(ab && ca && db){
               count3++;
            }
         }
      }
      System.out.println("Total possible histories " + count);
      System.out.println("Histories that pass constraints " +count3);
      double failPercent = (double) Math.round((1.0-(double)count3/count) * 100000)/1000;
      System.out.println("Failure percentage " + failPercent + "%");
      System.out.println("Loops needed to make total histories " +count2);
   }
}