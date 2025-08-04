package testClasses;

import java.util.Objects;

public class MyColor implements Comparable<MyColor> {
    private final int r;
    private final int g;
    private final int b;
    private final int sum;

    public MyColor(int b, int g, int r) {
        this.b = b;
        this.g = g;
        this.r = r;
        this.sum = r + g + b;
    }

    @Override
    public int compareTo(MyColor o) {
        int compSum = sum - o.sum;
        int compR = r - o.r;
        int compG = g - o.g;
        return compSum != 0 ? compSum : compR != 0 ? compR : compG != 0 ? compG : b - o.b;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MyColor myColor = (MyColor) o;
        return r == myColor.r && g == myColor.g && b == myColor.b && sum == myColor.sum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b, sum);
    }
}