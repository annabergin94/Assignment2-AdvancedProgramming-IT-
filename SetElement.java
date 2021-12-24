package Lab2Final;
import java.io.Serializable;

class SetElement implements Comparable<SetElement>, Serializable {
    String key;
    int freq;

    SetElement(String key) {
        this.key = key;
        freq = 0;
    }
    void update() {
        freq++; }
    public int compareTo(SetElement that)  {
        return -1*Integer.compare(freq, that.freq);
    }
    public String toString() {
        return String.format("<%s,%d>", key, freq);
    }
}


