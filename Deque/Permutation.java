/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> randQueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            randQueue.enqueue(StdIn.readString());
        }
        int numberOfPrint = 0;
        int goal = Integer.parseInt(args[0]);
        for (String i : randQueue) {
            if (numberOfPrint == goal)
                break;
            StdOut.println(i);
            numberOfPrint++;
        }
    }
}
