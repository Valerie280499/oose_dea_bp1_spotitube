package datasource.dao;

import dto.TracksDTO;

import javax.inject.Inject;
import javax.ws.rs.ServerErrorException;
import java.sql.SQLException;

public class TrackDAO {
    private static final String SELECT_TRACKS_WHERE_ID_NOT_IN_PLAYLIST = "SELECT T.* FROM track T where id not in (select P.idTrack FROM playlistTracks P WHERE P.idPlaylist = ?)";
    private UserDAO userDAO;
    private TrackDAOService trackDAOService;

    @Inject public void setUserDAO(UserDAO userDAO){ this.userDAO = userDAO;}

    @Inject public void setTrackDAOService(TrackDAOService trackDAOService){ this.trackDAOService = trackDAOService;}

    public TracksDTO getTracksWhichAreNotInAPlaylist(String token, int playlist_id){
        userDAO.getUserByToken(token);

        TracksDTO tracksDTO;
        try{
            tracksDTO = trackDAOService.getTracks(playlist_id, SELECT_TRACKS_WHERE_ID_NOT_IN_PLAYLIST);
        } catch (SQLException error) {
            throw new ServerErrorException(500);
        }
        return tracksDTO;
    }
}

