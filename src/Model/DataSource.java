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

    public static final String QUERY_ARTIST_4_SONG =
            "SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " +
                    TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + ", " +
                    TABLE_SONGS + "." + COLUMN_SONGS_TRACK + " FROM " + TABLE_SONGS +
                    " INNER JOIN " + TABLE_ALBUMS + " ON " +
                    TABLE_SONGS + "." + COLUMN_SONGS_ALBUM + " = " + TABLE_ALBUMS + "." +
                    COLUMN_ALBUM_ID + " INNER JOIN " + TABLE_ARTISTS + " ON " +
                    TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST + " = " + TABLE_ARTISTS + "." +
                    COLUMN_ARTIST_ID + " WHERE " + TABLE_SONGS + "." + COLUMN_SONGS_TITLE + " = \"";

    public static final String QUERY_ARTIST_4_SONG_SORT =
            " ORDER BY " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " +
                    TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " COLLATE NOCASE ";

    public static final String TABLE_ARTIST_SONG_VIEW = "artist_list";

    public static final String CREATE_ARTIST_SONG_VIEW = " CREATE VIEW IF NOT EXISTS " +
            TABLE_ARTIST_SONG_VIEW + " AS SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " +
            TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " AS " + COLUMN_SONGS_ALBUM + ", " +
            TABLE_SONGS + "." + COLUMN_SONGS_TRACK + ", " + TABLE_SONGS + "." + COLUMN_SONGS_TITLE +
            " FROM " + TABLE_SONGS +
            " INNER JOIN " + TABLE_ALBUMS + " ON " + TABLE_SONGS +
            "." + COLUMN_SONGS_ALBUM + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ID +
            " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST +
            " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID +
            " ORDER BY " +
            TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " +
            TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + ", " +
            TABLE_SONGS + "." + COLUMN_SONGS_TRACK;


    public static final String QUERY_VIEW_SONG = "SELECT " + COLUMN_ARTIST_NAME + ", " +
            COLUMN_SONGS_ALBUM + " , " + COLUMN_SONGS_TRACK + " FROM " + TABLE_ARTIST_SONG_VIEW +
            " WHERE " + COLUMN_SONGS_TITLE + " = \"";

    public static final String QUERY_VIEW_SONG_PREP = "SELECT " + COLUMN_ARTIST_NAME + ", " +
            COLUMN_SONGS_ALBUM + " , " + COLUMN_SONGS_TRACK + " FROM " + TABLE_ARTIST_SONG_VIEW +
            " WHERE " + COLUMN_SONGS_TITLE + " = ? ";

    public static final String INSERT_ARTIST = "INSERT INTO " + TABLE_ARTISTS +
            '(' + COLUMN_ARTIST_NAME + ')' + " VALUES (?)";

    public static final String INSERT_ALBUM = "INSERT INTO " + TABLE_ALBUMS +
            '(' + COLUMN_ALBUM_NAME + " , " + COLUMN_ALBUM_ARTIST + ')' + " VALUES (?),(?)";

    public static final String INSERT_SONG = "INSERT INTO " + TABLE_SONGS +
            '(' + COLUMN_SONGS_TRACK + " , " + COLUMN_SONGS_TITLE + " , " + COLUMN_SONGS_ALBUM + ')' + " VALUES (?),(?),(?)";

    public static final String GET_ARTIST_ID = "SELECT " + COLUMN_ARTIST_ID + " FROM " +
            TABLE_ARTISTS + " WHERE " + COLUMN_ARTIST_NAME + " = ?";

    public static final String GET_ALBUM_ID = "SELECT " + COLUMN_ALBUM_ID + " FROM " +
            TABLE_ALBUMS + " WHERE " + COLUMN_ALBUM_NAME + " = ?";

    private Connection connection;
    private PreparedStatement preparedStatement;
    private PreparedStatement insertIntoArtist;
    private PreparedStatement insertIntoAlbums;
    private PreparedStatement insertIntoSongs;

    private PreparedStatement queryArtist;
    private PreparedStatement queryAlbum;

    public boolean open() {
        try {
            connection = DriverManager.getConnection(CONNECTION);
            preparedStatement = connection.prepareStatement(QUERY_VIEW_SONG_PREP);

            insertIntoArtist = connection.prepareStatement(INSERT_ARTIST, Statement.RETURN_GENERATED_KEYS);
            insertIntoAlbums = connection.prepareStatement(INSERT_ALBUM, Statement.RETURN_GENERATED_KEYS);
            insertIntoSongs = connection.prepareStatement(INSERT_SONG);

            queryArtist = connection.prepareStatement(GET_ARTIST_ID);
            queryAlbum = connection.prepareStatement(GET_ALBUM_ID);
            return true;
        } catch (SQLException e) {
            System.out.println("Faild to connect to database");
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (insertIntoArtist != null) {
                insertIntoArtist.close();
            }
            if (insertIntoAlbums != null) {
                insertIntoAlbums.close();
            }
            if (insertIntoSongs != null) {
                insertIntoSongs.close();
            }
            if (queryArtist != null) {
                queryArtist.close();
            }
            if (queryAlbum != null) {
                queryAlbum.close();
            }

            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            System.out.println("Couldnt close connection");
            e.printStackTrace();
        }
    }

    private int insertArtist(String name) throws SQLException {
        queryArtist.setString(1, name);
        ResultSet resultSet = queryArtist.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        insertIntoArtist.setString(1, name);
        int affectedRaws = insertIntoArtist.executeUpdate();
        if (affectedRaws != 1) {
            throw new SQLException("Failed Insertion");
        }
        ResultSet generatedKeys = insertIntoArtist.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        } else throw new SQLException("Can't get id");
    }

    private int insertAlbum(String name, int id) throws SQLException {
        queryAlbum.setString(1, name);
        ResultSet resultSet = queryAlbum.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        insertIntoAlbums.setString(1, name);
        insertIntoAlbums.setInt(2, id);
        int affectedRaws = insertIntoAlbums.executeUpdate();
        if (affectedRaws != 1) {
            throw new SQLException("Failed insertion");
        }
        ResultSet generatedKeys = insertIntoAlbums.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        } else throw new SQLException("Can't get id");
    }

    private void insertSong(String title, String artist, String album, int track) {
        try {
            connection.setAutoCommit(false);
            int artistId = insertArtist(artist);
            int albumId = insertAlbum(album, artistId);
            insertIntoSongs.setInt(1, track);
            insertIntoSongs.setString(2, title);
            insertIntoSongs.setInt(3, albumId);

            int affectedRaws = insertIntoAlbums.executeUpdate();
            if (affectedRaws == 1) {
                connection.commit();
            } else {
                throw new SQLException("Faild");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
                System.out.println("Failed to insert --> roll back");
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public List<Artist> getArtists(int sortOrder) {

        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(TABLE_ARTISTS);
        if (sortOrder != ORDERBY_NONE) {
            sql.append(" ORDER BY ");
            sql.append(COLUMN_ARTIST_NAME);
            sql.append(" COLLATE NOCASE ");
            if (sortOrder == ORDERBY_ASC) {
                sql.append(" ASC ");
            } else
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

    public List<String> albums4Artists(String artistName, int order) {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(TABLE_ALBUMS);
        sql.append('.');
        sql.append(COLUMN_ALBUM_NAME);
        sql.append(" FROM ");
        sql.append(TABLE_ALBUMS);
        sql.append(" JOIN ");
        sql.append(TABLE_ARTISTS);
        sql.append(" ON ");
        sql.append(TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST + " = ");
        sql.append(TABLE_ARTISTS + "." + COLUMN_ARTIST_ID);
        sql.append(" WHERE ");
        sql.append(TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME);
        sql.append(" = \"");
        sql.append(artistName);
        sql.append("\"");

        if (order != ORDERBY_NONE) {
            sql.append(" ORDER BY ");
            sql.append(TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME);
            sql.append(" COLLATE NOCASE ");
            if (order == ORDERBY_ASC) {
                sql.append(" ASC ");
            } else
                sql.append(" DESC ");
        }
        return getData(sql);

    }

    public List<String> artist4Songs(String artistName, int order) {
        StringBuilder sql = new StringBuilder(QUERY_ARTIST_4_SONG);
        sql.append(artistName);
        sql.append("\"");
        if (order != ORDERBY_NONE) {
            sql.append(QUERY_ARTIST_4_SONG_SORT);
            if (order == ORDERBY_DES) {
                sql.append("DESC");
            } else {
                sql.append("ASC");
            }
        }
        return getData(sql);

    }

    public int getCount(String table) {
        String sql = " SELECT COUNT(*) , MIN(_id) AS min , SUM(_id)as sum FROM  " + table;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            int count = resultSet.getInt(1);
            int min = resultSet.getInt("min");
            int sum = resultSet.getInt("sum");
            System.out.format("Count = %d min = %d Sum = %d\n", count, min, sum);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }

    public boolean createSongArtistView() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(CREATE_ARTIST_SONG_VIEW);
            System.out.println("\n" + CREATE_ARTIST_SONG_VIEW + "\n");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SongArtist> songInfo(String title) {
//        StringBuilder sql = new StringBuilder(QUERY_VIEW_SONG);
//        sql.append(title);
//        sql.append("\"");
//        System.out.println(sql.toString());
        try {
            preparedStatement.setString(1, title);//Order is must
            ResultSet resultSet = preparedStatement.executeQuery();
            List<SongArtist> songArtists = new ArrayList<>();
            while (resultSet.next()) {
                SongArtist songArtist = new SongArtist();
                songArtist.setArtistName(resultSet.getString(1));
                songArtist.setAlbumName(resultSet.getString(2));
                songArtist.setTrack(resultSet.getInt(3));
                songArtists.add(songArtist);
            }
            return songArtists;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

//        try (Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(sql.toString())) {
//            List<SongArtist> songArtists = new ArrayList<>();
//            while (resultSet.next()) {
//                SongArtist songArtist = new SongArtist();
//                songArtist.setArtistName(resultSet.getString(1));
//                songArtist.setAlbumName(resultSet.getString(2));
//                songArtist.setTrack(resultSet.getInt(3));
//                songArtists.add(songArtist);
//            }
//            return songArtists;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    private List<String> getData(StringBuilder sql) {
        System.out.println(sql.toString());

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql.toString())) {
            List<String> artists = new ArrayList<>();
            while (resultSet.next()) {
                artists.add(resultSet.getString(1));
            }
            return artists;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}