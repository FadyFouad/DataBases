package Model;

/****************************************************
 *** Created by Fady Fouad on 6/26/2019 at 09:18.***
 ***************************************************/
public class Songs {
    private int id ;
    private int albumId ;
    private int track ;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}