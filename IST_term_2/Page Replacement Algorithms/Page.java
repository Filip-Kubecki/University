import java.util.Comparator;
public class Page {
    public int nr;
    public boolean parityBit;
    public int ref;
    public Page(int nr, boolean parityBit, int ref)
    {
        this.nr = nr;
        this.parityBit = parityBit;
        this.ref = ref;
    }
    public Page(Page p )
    {
        this.nr = p.nr;
        this.parityBit = p.parityBit;
        this.ref = ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public void setParityBit(boolean parityBit) {
        this.parityBit = parityBit;
    }

    public static Comparator <Page> refComparator = new Comparator<Page>() {
        @Override
        public int compare(Page o1, Page o2) {
            return o1.ref - o2.ref;
        }
    };

    public String toString() {
        return nr + " ";
    }
}
