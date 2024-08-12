import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int i = 0;
        String champion = "";
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            if (StdRandom.bernoulli(1 / (i + 1.0))) {
                champion = word;
            }
            ++i;
        }
        // Print out the champion
        System.out.println(champion);
    }
}
