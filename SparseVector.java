package Lab2Final;
import java.io.Serializable;
import java.util.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SparseVector implements Comparable<SparseVector>, Serializable {
    String word;
    Map<String, SetElement> map;
    float sim;

    SparseVector(String word) {
        this.word = word;
        map = new HashMap<>();
    }

    public String getWord() { // getter for keyword
        return word;
    }

    void update(String context) {
        if (context.equals(word)) return; // do nothing

        SetElement x = map.get(context);
        if (x==null) {
            x = new SetElement(context);
            map.put(x.key, x); // push only once
        }
        x.update(); // increment freq
    }

    List<SetElement> top(int k) {
        List<SetElement> values = new ArrayList<>(map.values());
        Collections.sort(values);
        return values.subList(0, k);
    }

    @Override
    public String toString() {
        String result = "";
        result += map.entrySet();
        return result;//+ ": " + top(10).toString(); //*******
    }

    float norm() {
        float norm = 0;
        for (SetElement x: map.values()) {
            norm += x.freq*x.freq;
        }
        return (float)Math.sqrt(norm);
    }

    float sim(SparseVector that) {
        float sim = 0;
        for (String w: this.map.keySet()) {
            // check if w matches with the other sparsevec
            SetElement that_w = that.map.get(w);
            if (that_w == null)
                continue;
            sim += this.map.get(w).freq * that_w.freq;
        }
        return sim/(this.norm() * that.norm());
    }

    @Override
    public int compareTo(SparseVector that) {
        return -1* Float.compare(sim, that.sim); // descending
    }
}
