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
    public static final int INDEX_ALBUM_ID = 1;
    public static final int INDEX_ALBUM_NAME = 2;
    public static final int INDEX_ALBUM_ARTIST = 3;

    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTIST_ID = "_id";
    public static final String COLUMN_ARTIST_NAME = "name";
    public static final int INDEX_ARTIST_ID = 1;
    public static final int INDEX_ARTIST_NAME = 2;

    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONGS_ID = "_id";
    public static final String COLUMN_SONGS_TRACK = "track";
    public static final String COLUMN_SONGS_TITLE = "title";
    public static final String COLUMN_SONGS_ALBUM = "album";
    public static final int INDEX_SONG_ID = 1;
    public static final int INDEX_SONG_TRACK = 2;
    public static final int INDEX_SONG_TITLE = 3;
    public static final int INDEX_SONG_ALBUM = 4;

    public static final int ORDERBY_NONE = 1;
    public static final int ORDERBY_ASC = 2;
    public static final int ORDERBY_DES = 3;


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
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            System.out.println("Couldnt close connection");
            e.printStackTrace();
        }
    }

    public List<Artist> getArtists(int sortOrder) {

        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(TABLE_ARTISTS);
        if (sortOrder!=ORDERBY_NONE){
            sql.append(" ORDER BY ");
            sql.append(COLUMN_ARTIST_NAME);
            sql.append(" COLLATE NOCASE ");
            if (sortOrder == ORDERBY_ASC){
                sql.append(" ASC ");
            }else
                sql.append(" DESC ");
        }

        try (Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_ARTISTS)) {
             ResultSet resultSet = statement.executeQuery(sql.toString())) {
            List<Artist> artists = new ArrayList<>();
            while (resultSet.next()) {
                Artist artist = new Artist();
                artist.setId(resultSet.getInt(INDEX_ARTIST_ID));//Column Indices
                artist.setName(resultSet.getString(INDEX_ARTIST_NAME));
                artists.add(artist);
            }
            return artists;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> albums4Artists(String artistName,int order){
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(TABLE_ALBUMS);
        sql.append('.');
        sql.append(COLUMN_ALBUM_NAME);
        sql.append(" FROM ");
        sql.append(TABLE_ALBUMS);
        sql.append(" JOIN ");
        sql.append(TABLE_ARTISTS);
        sql.append(" ON ");
        sql.append(TABLE_ALBUMS+"."+COLUMN_ALBUM_ARTIST+" = ");
        sql.append(TABLE_ARTISTS+"."+COLUMN_ARTIST_ID);
        sql.append(" WHERE ");
        sql.append(TABLE_ARTISTS+"."+COLUMN_ARTIST_NAME);
        sql.append(" = \"");
        sql.append(artistName);
        sql.append("\"");

        if (order!=ORDERBY_NONE){
            sql.append(" ORDER BY ");
            sql.append(TABLE_ALBUMS+"."+COLUMN_ALBUM_NAME);
            sql.append(" COLLATE NOCASE ");
            if (order == ORDERBY_ASC){
                sql.append(" ASC ");
            }else
                sql.append(" DESC ");
        }
        System.out.println(sql.toString());

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql.toString())) {
            List<String> albums = new ArrayList<>();
            while (resultSet.next()) {
                albums.add(resultSet.getString(1));
            }
            return albums;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}