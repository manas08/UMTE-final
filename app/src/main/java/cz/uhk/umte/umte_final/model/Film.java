package cz.uhk.umte.umte_final.model;

public class Film {

    public int id;
    public String nazev;
    public String anglickyNazev;
    public int rok;
    public float hodnoceni;
    public Director reziser;
    public String zemeNataceni;
    public String adresar;
    public String genre;

    public Film(){};

    public Film(int id, String nazev, String anglickyNazev, int rok, String zemeNataceni, String genre, String adresar) {
        this.id = id;
        this.nazev = nazev;
        this.anglickyNazev = anglickyNazev;
        this.rok = rok;
        this.genre = genre;
        this.zemeNataceni = zemeNataceni;
        this.adresar = adresar;
    }

    public int getId(){
        return id;
    }

    public Director getReziser() {
        return reziser;
    }

    public void setReziser(Director reziser) {
        this.reziser = reziser;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public String getAnglickyNazev() {
        return anglickyNazev;
    }

    public void setAnglickyNazev(String anglickyNazev) {
        this.anglickyNazev = anglickyNazev;
    }

    public int getRok() {
        return rok;
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

    public float getHodnoceni() {
        return hodnoceni;
    }

    public void setHodnoceni(float hodnoceni) {
        this.hodnoceni = hodnoceni;
    }

    public String getZemeNataceni() {
        return zemeNataceni;
    }

    public void setZemeNataceni(String zemeNataceni) {
        this.zemeNataceni = zemeNataceni;
    }

    public String getAdresar() {
        return adresar;
    }

    public void setAdresar(String adresar) {
        this.adresar = adresar;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
