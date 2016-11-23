package com.example.egi_fcb.pekepanukuik.Database;

/**
 * Created by Egi_FCB on 8/6/2016.
 */
public class PengingatPekerjaan {
    private long id;
    private String txt_hari;
    private String txt_taggal;
    private String txt_jam;
    private String txt_pekerjaan;

    //Mendefinisikan Objek PengingatPekerjaan beserta fungsi-fungsi dari atribut-atribuynya
    public PengingatPekerjaan(){

    }

    public long getId(){
        return id;
    }

    public void setId (long id){
        this.id = id;
    }

    public String getTxt_hari(){
        return txt_hari;
    }

    public void setTxt_hari(String txt_hari){
        this.txt_hari = txt_hari;
    }

    public String getTxt_taggal(){
        return txt_taggal;
    }

    public void setTxt_taggal(String txt_taggal){
        this.txt_taggal = txt_taggal;
    }

    public String getTxt_jam(){
        return txt_jam;
    }

    public void setTxt_jam(String txt_jam){
        this.txt_jam = txt_jam;
    }

    public String getTxt_pekerjaan(){
        return txt_pekerjaan;
    }

    public void setTxt_pekerjaan(String txt_pekerjaan){
        this.txt_pekerjaan = txt_pekerjaan;
    }

    public String toString(){
        return txt_hari+ "\n" +txt_taggal+ "\n" +txt_jam+ "\n" +txt_pekerjaan;
    }
}
