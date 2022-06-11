package pl.sda.library;

public class Main {

    public static void main(String[] args) {
        int[] t = {3,31,1,-28,5};
        int w = t[1] + t[3];
        System.out.println(t[w]);
        System.out.println(t[t[2] + 3]);
    }
}
