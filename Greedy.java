import java.util.*;

public class Greedy {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Inisiasi 4 jalur
        int[][] jumlahKendaraanPerJalur = {
                {50, 100},  // Jalur 1: 50 belok_kanan, 100 lurus
                {40, 40},  // Jalur 2: 40 belok_kanan, 40 lurus
                {30, 0},  // Jalur 3: 30 belok_kanan, 0 lurus
                {0, 30}   // Jalur 4: 0 belok_kanan, 30 lurus
        };

        PriorityQueue<Jalur> queue = new PriorityQueue<>(new JalurComparator());
        Map<Integer, Jalur> jalurMap = new HashMap<>();
        ArrayList<Jalur> jalurList = new ArrayList<>();

        // Inisialisasi antrian jalur
        for (int i = 0; i < jumlahKendaraanPerJalur.length; i++) {
            Jalur jalur = new Jalur(i, jumlahKendaraanPerJalur[i][0], jumlahKendaraanPerJalur[i][1]);
            queue.add(jalur);
            jalurMap.put(i, jalur);
        }

        int iterasi = 0;
        while (!queue.isEmpty()) {
            iterasi++;
            Jalur selectedJalur = null;

            //jika 6 iterasi belum dipilih, maka pilih jalur itu
            for (Jalur jalur : queue) {
                if (iterasi - jalur.lastSelected - 1 >= 6) {
                    selectedJalur = jalur;
                    break;
                }
            }

            if (selectedJalur == null) {
                selectedJalur = queue.poll();
                System.out.println("Iterasi " + iterasi + ":");
                printJalur(queue, selectedJalur, false);
            } else {
                queue.remove(selectedJalur);
                System.out.println("Iterasi " + iterasi + ":");
                printJalur(queue, selectedJalur, true);
            }
            jalurList.add(selectedJalur);


            // Kurangi jumlah mobil di jalur tersebut
            selectedJalur.belok_kanan = Math.max(0, selectedJalur.belok_kanan - 10);
            selectedJalur.lurus = Math.max(0, selectedJalur.lurus - 20);
            selectedJalur.lastSelected = iterasi;

            // Jika masih ada mobil, masukkan kembali ke dalam antrian
            if (selectedJalur.belok_kanan > 0 || selectedJalur.lurus > 0) {
                queue.add(selectedJalur);
            }

            // Setiap 4 iterasi, meminta input pengguna
            if (iterasi % 4 == 0) {
                System.out.println(ANSI_RESET + "Ingin menambahkan kapasitas mobil?");
                int pilihan = scanner.nextInt();
                if(pilihan == 1){
                    System.out.println("Masukkan jumlah mobil dan motor baru untuk setiap jalur:");
                    for (int i = 0; i < jumlahKendaraanPerJalur.length; i++) {
                        System.out.print("Jalur " + (i+1) + " - Jumlah Mobil Belok Kanan: ");
                        int tambah_belokKanan = scanner.nextInt();
                        System.out.print("Jalur " + (i+1) + " - Jumlah Mobil Lurus: ");
                        int tambah_lurus = scanner.nextInt();
                        Jalur jalur = jalurMap.get(i);
                        if(tambah_lurus == 0 && tambah_belokKanan == 0){

                        }else{
                            if (queue.contains(jalur)) {
                                queue.remove(jalur);
                                jalur.belok_kanan += tambah_belokKanan;
                                jalur.lurus += tambah_lurus;
                                queue.add(jalur);
                            } else if (selectedJalur.id == i) {
                                selectedJalur.belok_kanan += tambah_belokKanan;
                                selectedJalur.lurus += tambah_lurus;
                            } else {
                                queue.add(new Jalur(i, tambah_belokKanan, tambah_lurus, iterasi));
                            }
                        }

                    }
                }

            }
        }
        scanner.close();
        System.out.print("Hasil = [");
        for (int i = 0; i < jalurList.size(); i++) {
            System.out.print(jalurList.get(i).id + 1);
            if (i != jalurList.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("]");
    }

    private static void printJalur(PriorityQueue<Jalur> queue, Jalur currentJalur, boolean flag) {
        int[][] jalurJumlah = new int[4][2];
        for (Jalur jalur : queue) {
            jalurJumlah[jalur.id][0] = jalur.belok_kanan;
            jalurJumlah[jalur.id][1] = jalur.lurus;
        }
        jalurJumlah[currentJalur.id][0] = currentJalur.belok_kanan;
        jalurJumlah[currentJalur.id][1] = currentJalur.lurus;

        for (int i = 0; i < 4; i++) {
            String color = (i == currentJalur.id) ? ANSI_GREEN : ANSI_RED;
            System.out.print(color + "Jalur " + (i+1) + ": " + jalurJumlah[i][0] + " belok_kanan, " + jalurJumlah[i][1] + " lurus" + ANSI_RESET);
            if(flag && i == currentJalur.id){
                System.out.println(ANSI_GREEN + "(sudah > 6 iterasi)");
            }else{
                System.out.println("");
            }
        }
        System.out.println();
    }
}
