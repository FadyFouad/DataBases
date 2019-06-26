package Model;

/****************************************************
 *** Created by Fady Fouad on 6/26/2019 at 09:17.***
 ***************************************************/
public class Album {
    private int id ;
    private int artistId ;
    private String track ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }
}