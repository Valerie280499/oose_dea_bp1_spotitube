package datasource.DAO;

import datasource.connection.JDBCConnection;
import datasource.errors.EmptyPlaylistError;
import datasource.errors.someSQLError;
import dto.PlaylistDTO;
import dto.PlaylistsDTO;
import dto.TrackDTO;
import dto.TracksDTO;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class PlaylistsDAO {
    public static final String SELECT_FROM_PLAYLIST = "SELECT * FROM playlist";
    public static final String SELECT_TRACKS_FROM_PLAYLIST = "SELECT T.* FROM track T where id not in (select P.idTrack FROM playlistTracks P WHERE P.idPlaylist = ?)";
    public static final String DELETE_PLAYLIST = "DELETE FROM playlist WHERE id = ?";
    public static final String INSERT_INTO_PLAYLIST = "INSERT INTO playlist (id, name, owner, length) VALUES (?, ?, ?, NULL)";
    public static final String UPDATE_PLAYLIST_NAME = "UPDATE playlist SET name = ? WHERE id = ?";
    private JDBCConnection JDBCConnection;

    @Inject
    public void setJDBCConnection(JDBCConnection JDBCConnection){this.JDBCConnection = JDBCConnection;}

    public Optional<PlaylistsDTO> getAllPlaylists(){
        ArrayList<PlaylistDTO> playlists = new ArrayList<>();

        try {
            var conn = JDBCConnection.createConnection();
            var statement = conn.prepareStatement(SELECT_FROM_PLAYLIST);
            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                playlists.add(new PlaylistDTO(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("owner")));
            }
        } catch (SQLException error) {
            throw new someSQLError();
        }

        if (playlists.isEmpty()){
            return Optional.empty();
        } else {
            var playlistsDTO = new PlaylistsDTO();
            playlistsDTO.setPlaylists(playlists);
            return Optional.of(playlistsDTO);
        }

    }

    public Optional<PlaylistsDTO> deletePlaylist(int playlist_id) {

        try {
            var conn = JDBCConnection.createConnection();
            var statement = conn.prepareStatement(DELETE_PLAYLIST);
            statement.setInt(1, playlist_id);
            statement.execute();
        } catch (SQLException error) {
            throw new someSQLError();
        }

        return getAllPlaylists();
    }

    public Optional<PlaylistsDTO> addPlaylist(PlaylistDTO newPlaylist) {

        try {
            var conn = JDBCConnection.createConnection();
            var statement = conn.prepareStatement(INSERT_INTO_PLAYLIST);
            statement.setInt(1, newPlaylist.getId());
            statement.setString(2, newPlaylist.getName());
            statement.setString(3, newPlaylist.getOwner());
            statement.execute();
        } catch (SQLException error) {
            throw new someSQLError();
        }

        return getAllPlaylists();
    }

    public Optional<PlaylistsDTO> editPlaylist(PlaylistDTO newPlaylist) {
        try {
            var conn = JDBCConnection.createConnection();
            var statement = conn.prepareStatement(UPDATE_PLAYLIST_NAME);
            statement.setString(1, newPlaylist.getName());
            statement.setInt(2, newPlaylist.getId());
            statement.execute();
        } catch (SQLException error) {
            throw new someSQLError();
        }

        return getAllPlaylists();
    }

    public TracksDTO getTracksFromPlaylist(int playlist_id) {
        var foundTracks = new ArrayList<TrackDTO>();

        try {
            var conn = JDBCConnection.createConnection();
            var statement = conn.prepareStatement(SELECT_TRACKS_FROM_PLAYLIST);
            statement.setInt(1, playlist_id);
            var resultSet = statement.executeQuery();

            while (resultSet.next()){
                foundTracks.add(new TrackDTO(resultSet.getInt("id"),
                        resultSet.getString("titel"),
                        resultSet.getString("performer"),
                        resultSet.getInt("duration"),
                        resultSet.getString("album"),
                        resultSet.getInt("playcount"),
                        resultSet.getString("publicationDate"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("OfflineAvailable")));
            }
        } catch (SQLException e){
            throw new someSQLError();
        }

        if (foundTracks.isEmpty()){
            throw new EmptyPlaylistError();
        } else {
            var tracksDTO = new TracksDTO();
            tracksDTO.setTracks(foundTracks);
            return tracksDTO;
        }
    }
}
