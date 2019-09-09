package Fangen;
import java.util.Scanner;

enum WingType {CLOCKWISE, ANTICLOCKWISE}

class CharMap{
    private String charMap;
    public CharMap() {this.charMap = "";}
    public CharMap(String string) {
        this.charMap = string;
    }
    /*this method print char c, n - times*/
    private static String printChar(int n, char c) {
        String chars = "";
        for (int i = 0; i < n; i++) {
            chars += c;
        }
        return chars;
    }
    /*this method is building text line*/
    public void addLineln(int n1, char c1, int n2, char c2, int n3, char c3, int n4, char c4) {
        this.charMap += printChar(n1, c1);
        this.charMap += printChar(n2, c2);
        this.charMap += printChar(n3, c3);
        this.charMap += printChar(n4, c4);
        this.charMap += '\n';
    }
    @Override
    public String toString() {
        return this.charMap;
    }
}

interface WingPainter{
    void fanPainter(int r);
}

interface WingPainterFactory{
    WingPainter GetWingPainter();
}

class WingPainterClockwise implements WingPainter {
    private WingPainterClockwise(){}
    public void fanPainter(int r) {
        /*this method is painting clockwise fan*/
        CharMap wingMap = new CharMap();
        int absr = (-r);
        for (int i = 0; i < absr; i++) {
            wingMap.addLineln(i, '.', absr-i, '*', absr - 1 - i, '.', i + 1, '*');
        }
        for (int i = 0; i < absr; i++) {
            wingMap.addLineln(absr - i, '*', i, '.', i + 1, '*', absr - 1 - i, '.');
        }
        System.out.println(wingMap.toString());
    }
    public static WingPainterFactory factory = () -> new WingPainterClockwise();
    /*public static WingPainterFactory factory = new WingPainterFactory() {
        public WingPainter GetWingPainter() {
            return new WingPainterClockwise();
        }
    };*/
}

class WingPainterAntiClockwise implements WingPainter {
    private WingPainterAntiClockwise() {}
    public void fanPainter(int r) {
        CharMap wingMap = new CharMap();
        for (int i = 0; i < r; i++) {
            wingMap.addLineln(i + 1, '*', r - 1 - i, '.', r - i, '*', i, '.');
        }
        for (int i = 0; i < r; i++) {
            wingMap.addLineln(r - 1 - i, '.', i + 1, '*', i, '.', r - i, '*');
        }
        System.out.println(wingMap.toString());
    }
    public static WingPainterFactory factory = WingPainterAntiClockwise :: new;
    /*public static WingPainterFactory factory = new WingPainterFactory() {
        public WingPainter GetWingPainter() {
            return new WingPainterAntiClockwise();
        }
    };*/
}

public class Main{
    public static void wingPainterConsumer(WingPainterFactory fact, int r) {
        WingPainter wp = fact.GetWingPainter();
        wp.fanPainter(r);
    }
    public static void runFan(){
        Scanner scan = new Scanner(System.in);
        WingType type;
        int r = scan.nextInt();
        do {
            if (r < 0) type = WingType.CLOCKWISE;
            else type = WingType.ANTICLOCKWISE;
            switch (type) {
                case CLOCKWISE : wingPainterConsumer(WingPainterClockwise.factory, r); break;
                case ANTICLOCKWISE : wingPainterConsumer(WingPainterAntiClockwise.factory, r); break;
                default : throw new IllegalArgumentException("Blad wielkosci");
            }
            r = scan.nextInt();
        } while (r != 0);
        System.exit(0);
    }
    public static void main(String[] args) {
        runFan();
    }
}