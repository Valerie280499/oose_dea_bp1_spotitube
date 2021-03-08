package datasource.DAO;

import datasource.connection.JDBCConnection;
import datasource.errors.PlayListNotFoundError;
import datasource.errors.someSQLError;
import dto.PlaylistDTO;
import dto.PlaylistsDTO;
import dto.TrackDTO;
import dto.TracksDTO;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class PlaylistDAO {
    private static final String SELECT_FROM_PLAYLIST = "SELECT * FROM playlist";
    private static final String DELETE_PLAYLIST = "DELETE FROM playlist WHERE id = ?";
    private static final String DELETE_TRACK_FROM_PLAYLIST = "DELETE FROM playlistTracks WHERE idTrack = ?";
    private static final String INSERT_INTO_PLAYLIST = "INSERT INTO playlist (id, name, owner, length) VALUES (?, ?, ?, NULL)";
    private static final String INSERT_NEW_TRACK_INTO_PLAYLIST = "INSERT INTO playlistTracks (idPlaylist, idTrack, offlineAvailable) VALUES (?, ?, ?)";
    private static final String UPDATE_PLAYLIST_NAME = "UPDATE playlist SET name = ? WHERE id = ?";
    protected JDBCConnection JDBCConnection;
    protected UserDAO userDAO;

    @Inject
    public void setJDBCConnection(JDBCConnection JDBCConnection){this.JDBCConnection = JDBCConnection;}

    @Inject
    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public PlaylistsDTO getAllPlaylists(String token){
        userDAO.getUserByToken(token);

        ArrayList<PlaylistDTO> playlists = new ArrayList<>();
        try {
            var conn = JDBCConnection.createConnection();
            var statement = conn.prepareStatement(SELECT_FROM_PLAYLIST);
            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                playlists.add(new PlaylistDTO(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getBoolean("owner")));
            }
        } catch (SQLException error) {
            throw new someSQLError(error);
        }

        if (playlists.isEmpty()){
            throw new PlayListNotFoundError();
        } else {
            var playlistsDTO = new PlaylistsDTO();
            playlistsDTO.setPlaylists(playlists);
            return playlistsDTO;
        }

    }

    public PlaylistsDTO deletePlaylist(String token, int playlist_id) {
        try {
            var conn = JDBCConnection.createConnection();
            var statement = conn.prepareStatement(DELETE_PLAYLIST);
            statement.setInt(1, playlist_id);
            statement.execute();
        } catch (SQLException error) {
            throw new someSQLError(error);
        }

        return getAllPlaylists(token);
    }

    public PlaylistsDTO addPlaylist(String token, PlaylistDTO newPlaylist) {
        try {
            var conn = JDBCConnection.createConnection();
            var statement = conn.prepareStatement(INSERT_INTO_PLAYLIST);
            statement.setInt(1, newPlaylist.getId());
            statement.setString(2, newPlaylist.getName());
            statement.setBoolean(3, newPlaylist.getOwner());
            statement.execute();
        } catch (SQLException error) {
            throw new someSQLError(error);
        }

        return getAllPlaylists(token);
    }

    public PlaylistsDTO editPlaylist(String token, PlaylistDTO newPlaylist) {
        try {
            var conn = JDBCConnection.createConnection();
            var statement = conn.prepareStatement(UPDATE_PLAYLIST_NAME);
            statement.setString(1, newPlaylist.getName());
            statement.setInt(2, newPlaylist.getId());
            statement.execute();
        } catch (SQLException error) {
            throw new someSQLError(error);
        }

        return getAllPlaylists(token);
    }

    public Optional<TracksDTO> getTracks(String token, int playlist_id, String query) {
        userDAO.getUserByToken(token);

        var tracks = new ArrayList<TrackDTO>();
        try {
            var conn = JDBCConnection.createConnection();
            var statement = conn.prepareStatement(query);
            statement.setInt(1, playlist_id);
            var resultSet = statement.executeQuery();

            while (resultSet.next()){
                var track = new TrackDTO();
                track.setId(resultSet.getInt("id"));
                track.setTitle(resultSet.getString("titel"));
                track.setPerformer(resultSet.getString("performer"));
                track.setDuration(resultSet.getInt("duration"));
                track.setAlbum(resultSet.getString("album"));
                track.setPlaycount(resultSet.getInt("playcount"));
                track.setPublicatationDate(resultSet.getString("publicationDate"));
                track.setDescription(resultSet.getString("description"));
                track.setOfflineAvailable(resultSet.getBoolean("offlineAvailable"));
                tracks.add(track);
            }
        } catch (SQLException error){
            throw new someSQLError(error);
        }

        if (tracks.isEmpty()){
            return Optional.empty();
        } else {
            var tracksDTO = new TracksDTO();
            tracksDTO.setTracks(tracks);
            return Optional.of(tracksDTO);
        }
    }

    public void addTrackToPlaylist(int playlist_id, TrackDTO newTrack){
        try {
            var conn = JDBCConnection.createConnection();
            var statement = conn.prepareStatement(INSERT_NEW_TRACK_INTO_PLAYLIST);
            statement.setInt(1, playlist_id);
            statement.setInt(2, newTrack.getId());
            statement.setBoolean(3, newTrack.getOfflineAvailable());
            statement.execute();
        } catch (SQLException error) {
            throw new someSQLError(error);
        }

    }

    public void deleteTrackFromPlaylist(int playlist_id, int track_id) {
        try {
            var conn = JDBCConnection.createConnection();
            var statement = conn.prepareStatement(DELETE_TRACK_FROM_PLAYLIST);
            statement.setInt(1, track_id);
            statement.execute();
        } catch (SQLException error) {
            throw new someSQLError(error);
        }
    }
}