package com.example.tubesrafif;

public class DataModel {
    private String id;
    private String namaDeck;
    private String harga;
    private String urlFoto;

    public DataModel(String id, String namaDeck, String harga, String urlFoto) {
        this.id = id;
        this.namaDeck = namaDeck;
        this.harga = harga;
        this.urlFoto = urlFoto;
    }

    public String getId() {
        return id;
    }

    public String getNamaDeck() {
        return namaDeck;
    }

    public String getHarga() {
        return harga;
    }

    public String getUrlFoto() {
        return urlFoto;
    }
}


