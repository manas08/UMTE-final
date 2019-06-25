package cz.uhk.umte.umte_final.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Actor {

    public int id;
    public String jmeno;
    public String prijmeni;
    public int vek;
    public String datumNarozeni;
    public String mistoNarozeni;
    public String datumUmrti;
    public String mistoUmrti;
    public boolean jeOznacen;
    public String adresar;
    public float hodnoceni;

    public Actor(int id, String jmeno, String prijmeni, int vek, String datumNarozeni, String mistoNarozeni, String datumUmrti, String mistoUmrti, String adresar) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.vek = vek;
        this.datumNarozeni = datumNarozeni;
        this.mistoNarozeni = mistoNarozeni;
        this.datumUmrti = datumUmrti;
        this.mistoUmrti = mistoUmrti;
        this.adresar = adresar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public int getVek() {
        return vek;
    }

    public String getDatumNarozeni() {
        return datumNarozeni;
    }

    public void setDatumNarozeni(String datumNarozeni) {
        this.datumNarozeni = datumNarozeni;
    }

    public String getMistoNarozeni() {
        return mistoNarozeni;
    }

    public void setMistoNarozeni(String mistoNarozeni) {
        this.mistoNarozeni = mistoNarozeni;
    }

    public String getDatumUmrti() {
        return datumUmrti;
    }

    public void setDatumUmrti(String datumUmrti) {
        this.datumUmrti = datumUmrti;
    }

    public String getMistoUmrti() {
        return mistoUmrti;
    }

    public void setMistoUmrti(String mistoUmrti) {
        this.mistoUmrti = mistoUmrti;
    }

    public boolean isJeOznacen() {
        return jeOznacen;
    }

    public void setJeOznacen(boolean jeOznacen) {
        this.jeOznacen = jeOznacen;
    }

    public String getAdresar() {
        return adresar;
    }

    public void setAdresar(String adresar) {
        this.adresar = adresar;
    }

    public float getHodnoceni() {
        return hodnoceni;
    }

    public void setHodnoceni(float hodnoceni) {
        this.hodnoceni = hodnoceni;
    }

    public void setVek(int vek) {
        this.vek = vek;
    }
}
