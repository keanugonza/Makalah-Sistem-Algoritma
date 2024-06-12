public class Jalur {
    int id;
    int belok_kanan;
    int lurus;
    int lastSelected;

    public Jalur(int id, int belok_kanan, int lurus) {
        this.id = id;
        this.belok_kanan = belok_kanan;
        this.lurus = lurus;
        this.lastSelected = 0;
    }

    public Jalur(int id, int belok_kanan, int lurus, int lastSelected) {
        this.id = id;
        this.belok_kanan = belok_kanan;
        this.lurus = lurus;
        this.lastSelected = lastSelected;
    }

    public int getBobot() {
        return lurus + (belok_kanan*2);
    }
}
