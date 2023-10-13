package com.example.app_pelis;

public class Peli {
    String name;
    String descrip;
    String category;
    String idioma;
    String Subtitulos;

    public Peli() {
    }

    public Peli(String name, String descrip, String category, String idioma, String subtitulos) {
        this.name = name;
        this.descrip = descrip;
        this.category = category;
        this.idioma = idioma;
        Subtitulos = subtitulos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getSubtitulos() {
        return Subtitulos;
    }

    public void setSubtitulos(String subtitulos) {
        Subtitulos = subtitulos;
    }
}
