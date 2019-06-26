package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/****************************************************
 *** Created by Fady Fouad on 6/25/2019 at 22:47.***
 ***************************************************/
public class DataSource {
    public static final String DATABASE_NAME = "music.db";
    public static final String CONNECTION = "jdbc:sqlite:" + DATABASE_NAME;

    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUM_ID = "_id";
    public static final String COLUMN_ALBUM_NAME = "name";
    public static final String COLUMN_ALBUM_ARTIST = "artist";

    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTIST_ID = "_id";
    public static final String COLUMN_ARTIST_NAME = "name";

    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONGS_ID = "_id";
    public static final String COLUMN_SONGS_TRACK = "track";
    public static final String COLUMN_SONGS_TITLE = "title";
    public static final String COLUMN_SONGS_ALBUM = "album";

    private Connection connection;

    public boolean open() {
        try {
            connection = DriverManager.getConnection(CONNECTION);
            return true;
        } catch (SQLException e) {
            System.out.println("Faild to connect to database");
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        try {
            if (connection!=null)
            connection.close();
        } catch (SQLException e) {
            System.out.println("Couldnt close connection");
            e.printStackTrace();
           }
    }
    public List<Artist>getArtists(){
        Statement statement = null;
        ResultSet resultSet = null ;

        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM "+ TABLE_ARTISTS);
            List<Artist>artists = new ArrayList<>();
            while (resultSet.next()){
                Artist artist = new Artist();
                artist.setId(resultSet.getInt(COLUMN_ARTIST_ID));
                artist.setName(resultSet.getString(COLUMN_ARTIST_NAME));
                artists.add(artist);
            }
            return artists;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            try{
                if (statement!=null){
                    statement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}