import java.util.*;
import java.io.*;

public class JosephusSim {
   private PersonNode circle;     // a PersonNode pointer that tracks first node
   private int size;              // the number of people in the circle
   private int eliminationCount;  // the number to count to for elimination       
   private PersonNode track;      // a PersonNode pointer to help with elimination

   public JosephusSim(String fileName) {
      try {
         // load names from the file in order, generating a singly linked list of PersonNodes
         Scanner file = new Scanner(new File(fileName));
         
         while (file.hasNextLine()) {
            String name = file.nextLine().trim();
            if (!name.isEmpty()) {
               add(name); // add each person to the list
            }
         }

         // Then the singly linked list should be turned into a circularly linked list 
         // by pointing the last node's next at the first node
         if (circle != null) {
            PersonNode current = circle;
            while (current.next != null) {
               current = current.next;
            }
            current.next = circle;

            // In order to aid in eliminating people, you will need to continuously track 
            // the person /before/ the next person to begin counting at. At the beginning 
            // of the simulation this is the last node
            track = current;
         }

         // Generate and print an elimination count
         // This count should not be larger than half the number of people in the list 
         // and it must be at least 1. (i.e. If there are 10 people in the list, this value 
         // should be a random number 1-5)
         eliminationCount = (int)(Math.random() * (size / 2)) + 1;
         System.out.println("Elimination Count: " + eliminationCount);

      } catch(FileNotFoundException e) {
         System.out.println("Something went wrong with " + fileName);
      }
   }
   
   // optional helper method for constructing the circle
   private void add(String val) {
      PersonNode newNode = new PersonNode(val);
      if (circle == null) {
         circle = newNode;
      } else {
         PersonNode current = circle;
         while (current.next != null) {
            current = current.next;
         }
         current.next = newNode;
      }
      size++;
   }
   
   public void eliminate() {
      // count to the elimination count
      for (int i = 0; i < eliminationCount; i++) {
         track = track.next;
      }

      // print who will be eliminated
      PersonNode toRemove = track.next;
      System.out.println("Eliminated: " + toRemove.name);

      // eliminate the person and update "front" of the circle and size
      track.next = toRemove.next;

      if (toRemove == circle) {
         circle = toRemove.next;
      }

      size--;
   }
   
   public boolean isOver() {
      // check if there's only one person left in the circle
      return size == 1;
   }
   
   public String toString() {
      // if there's only one person left, print them as the last survivor
      if (isOver()) {
         return "Survivor: " + circle.name;
      }

      // print the remaining survivors (watch out for infinite loop since list is circular)
      StringBuilder sb = new StringBuilder();
      PersonNode current = circle;
      for (int i = 0; i < size; i++) {
         sb.append(current.name);
         if (i != size - 1) {
            sb.append(", ");
         }
         current = current.next;
      }
      return sb.toString();
   }
}
