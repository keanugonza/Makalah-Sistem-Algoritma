import java.util.Comparator;

public class JalurComparator implements Comparator<Jalur> {
    @Override
    public int compare(Jalur j1, Jalur j2) {
        int bobotComparison = Integer.compare(j2.getBobot(), j1.getBobot());
        if (bobotComparison == 0) {
            return Integer.compare(j1.lastSelected, j2.lastSelected);
        }
        return bobotComparison;
    }
}
