import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Zadanie1 {
    static class Tools {

        public static void loadOcenaData(String filename) {
            File f = new File(filename);

            try {
                Scanner sc = new Scanner(f);

                sc.nextLine();
                while(sc.hasNextLine())
                new Ocena(sc.nextLine());

                sc.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public static void loadUczenData(String filename) {
            File f = new File(filename);

            try {
                Scanner sc = new Scanner(f);

                sc.nextLine();
                while(sc.hasNextLine())
                new Uczen(sc.nextLine());

                sc.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public static void loadPrzedmiotData(String filename) {
            File f = new File(filename);

            try {
                Scanner sc = new Scanner(f);

                sc.nextLine();
                while(sc.hasNextLine())
                new Przedmiot(sc.nextLine());

                sc.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public static void fillOutGrades() {
            for (Ocena o : Ocena.collection) {
                Uczen u = findUser(o.getiDucznia());
                Przedmiot p = findSubject(o.getiDprzedmiotu());

                if (!u.grades.containsKey(p)) {
                    u.grades.put(p, new ArrayList<Integer>());
                }
                u.grades.get(p).add(o.getOcena());
            }
        }
    }

    static class Ocena {
        private int iDucznia;
        private int ocena;
        private String date;
        private int iDprzedmiotu;

        public static ArrayList<Ocena> collection;

        static {
            collection = new ArrayList<Ocena>();
        }

        public Ocena(String line) {

            String[] data = line.split(";");
            iDucznia = Integer.valueOf(data[0]);
            ocena = Integer.valueOf(data[1]);
            date = data[2];
            iDprzedmiotu = Integer.valueOf(data[3]);

            Ocena.collection.add(this);
        }

        public int getiDucznia() {
            return iDucznia;
        }

        public void setiDucznia(int iDucznia) {
            this.iDucznia = iDucznia;
        }

        public int getOcena() {
            return ocena;
        }

        public void setOcena(int ocena) {
            this.ocena = ocena;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getiDprzedmiotu() {
            return iDprzedmiotu;
        }

        public void setiDprzedmiotu(int iDprzedmiotu) {
            this.iDprzedmiotu = iDprzedmiotu;
        }

        @Override
        public String toString() {
            return "Ocena [date=" + date + ", iDprzedmiotu=" + iDprzedmiotu + ", iDucznia=" + iDucznia + ", ocena="
            + ocena + "]";
        }

    }

    static class Uczen {
        private int iD;
        private String nazwisko;
        private String imie;

        public HashMap<Przedmiot, ArrayList<Integer>> grades = new HashMap<>();

        public static ArrayList<Uczen> collection;

        static {
            collection = new ArrayList<Uczen>();
        }

        public Uczen(String line) {

            String[] data = line.split(";");
            iD = Integer.valueOf(data[0]);
            nazwisko = data[1];
            imie = data[2];
            this.grades = new HashMap<Przedmiot, ArrayList<Integer>>();

            Uczen.collection.add(this);
        }

        public int getiD() {
            return iD;
        }

        public void setiD(int iD) {
            this.iD = iD;
        }

        public String getNazwisko() {
            return nazwisko;
        }

        public void setNazwisko(String nazwisko) {
            this.nazwisko = nazwisko;
        }

        public String getImie() {
            return imie;
        }

        public void setImie(String imie) {
            this.imie = imie;
        }

        public HashMap<Przedmiot, ArrayList<Integer>> getGrades() {
            return grades;
        }

        @Override
        public String toString() {
            return "Uczen [iD=" + iD + ", imie=" + imie + ", nazwisko=" + nazwisko + ", Hash" + grades.toString() + "]";
        }

    }

    static class Przedmiot {
        private int iD;
        private String nazwa;

        public static ArrayList<Przedmiot> collection;

        static {
            collection = new ArrayList<Przedmiot>();
        }

        public Przedmiot(String line) {

            String[] data = line.split(";");
            iD = Integer.valueOf(data[0]);
            nazwa = data[1];

            Przedmiot.collection.add(this);
        }

        public int getiD() {
            return iD;
        }

        public void setiD(int iD) {
            this.iD = iD;
        }

        public String getNazwa() {
            return nazwa;
        }

        public void setNazwa(String nazwa) {
            this.nazwa = nazwa;
        }

        @Override
        public String toString() {
            return "Przedmiot [iD=" + iD + ", nazwa=" + nazwa + "]";
        }

    }

    public static void main(String[] args) {
        Tools.loadOcenaData("oceny.txt");
        Tools.loadUczenData("uczniowie.txt");
        Tools.loadPrzedmiotData("przedmioty.txt");

        Tools.fillOutGrades();

        for (Uczen u : Uczen.collection) {
            float average = 0;
            for (Przedmiot p : u.getGrades().keySet()) {
                for (Integer i : u.grades.get(p)) {
                    average += i;
                }
                average = average / u.grades.get(p).size();
                System.out.println(u.getNazwisko() + " " + p.getNazwa() + " " + average);
            }
        }

    }

    private static Zadanie1.Przedmiot findSubject(int getiDprzedmiotu) {
        for (Przedmiot p : Przedmiot.collection) {
            if (p.getiD() == getiDprzedmiotu)
            return p;
        }
        return null;
    }

    private static Uczen findUser(int getiDucznia) {
        for (Uczen u : Uczen.collection) {
            if (u.getiD() == getiDucznia)
            return u;
        }
        return null;
    }
}
