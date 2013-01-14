package fi.lolcatz.profiler.analysis;

public final class DoublePair implements Comparable<Object> {
    public final double x;
    public final double y;

    public DoublePair() {
        this.x = 0.0;
        this.y = 0.0;
    }

    public DoublePair(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Object t) {
        return Double.compare(x, y);
    }
}
