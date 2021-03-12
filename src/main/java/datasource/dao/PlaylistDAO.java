package datasource.dao;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import datasource.connection.JDBCConnection;
import datasource.errors.PlayListNotFoundError;
import datasource.errors.someSQLError;
import datasource.resultsetMappers.MapResultsetToPlaylistDTO;
import dto.PlaylistDTO;
import dto.PlaylistsDTO;
import dto.TrackDTO;
import dto.TracksDTO;
import services.PlaylistService;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistDAO {
    private static final String SELECT_FROM_PLAYLIST = "SELECT * FROM playlist";
    private static final String SELECT_TRACKS_FROM_PLAYLIST = "SELECT T.* FROM track T INNER JOIN playlistTracks P ON T.id = P.idTrack WHERE P.idPlaylist = ?";
    private static final String DELETE_PLAYLIST = "DELETE FROM playlist WHERE id = ?";
    private static final String DELETE_TRACK_FROM_PLAYLIST = "DELETE FROM playlistTracks WHERE idTrack = ?";
    private static final String INSERT_INTO_PLAYLIST = "INSERT INTO playlist (id, name, owner, length) VALUES (?, ?, ?, NULL)";
    private static final String INSERT_NEW_TRACK_INTO_PLAYLIST = "INSERT INTO playlistTracks (idPlaylist, idTrack, offlineAvailable) VALUES (?, ?, ?)";
    private static final String UPDATE_PLAYLIST_NAME = "UPDATE playlist SET name = ? WHERE id = ?";

    private JDBCConnection JDBCConnection;
    private UserDAO userDAO;
    private PlaylistService playlistService;
    private MapResultsetToPlaylistDTO mapResultsetToPlaylistDTO;
    private TrackDAOService trackDAOService;

    @Inject public void setJDBCConnection(JDBCConnection JDBCConnection){this.JDBCConnection = JDBCConnection;}

    @Inject public void setPlaylistService(PlaylistService playlistService){this.playlistService = playlistService;}

    @Inject public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Inject public void setMapResultsetToPlaylistDTO(MapResultsetToPlaylistDTO mapResultsetToPlaylistDTO){ this.mapResultsetToPlaylistDTO = mapResultsetToPlaylistDTO;}

    @Inject public void setTrackService(TrackDAOService trackDAOService) { this.trackDAOService = trackDAOService;}

    public PlaylistsDTO getAllPlaylists(String token){
        userDAO.getUserByToken(token);

        ArrayList<PlaylistDTO> playlists = new ArrayList<>();
        var length = 0;
        try {
            var conn = JDBCConnection.createConnection();
            var statement = conn.prepareStatement(SELECT_FROM_PLAYLIST);
            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                var playlistDTO = mapResultsetToPlaylistDTO.map(resultSet, getTracksInPlaylist(token, resultSet.getInt("id")));

                playlists.add(playlistDTO);
                length += playlistService.lengthPlaylist(playlistDTO);
            }
        } catch (SQLException error) {
            throw new someSQLError(error);
        }

        if (playlists.isEmpty()){
            throw new PlayListNotFoundError();
        } else {
            var playlistsDTO = new PlaylistsDTO();
            playlistsDTO.setPlaylists(playlists);
            playlistsDTO.setLength(length);
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
            statement.setBoolean(3, true);
            statement.execute();
        } catch (MySQLIntegrityConstraintViolationException error){
            var id = newPlaylist.getId();
            newPlaylist.setId(++id);
            addPlaylist(token, newPlaylist);
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

    public TracksDTO getTracksInPlaylist(String token, int playlist_id) {
        userDAO.getUserByToken(token);

        TracksDTO tracksDTO = null;
        try {
            tracksDTO = trackDAOService.getTracks(playlist_id, SELECT_TRACKS_FROM_PLAYLIST);
        } catch (SQLException error){
            throw new someSQLError(error);
        }
        return tracksDTO;
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

    public void deleteTrackFromPlaylist(int track_id) {
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