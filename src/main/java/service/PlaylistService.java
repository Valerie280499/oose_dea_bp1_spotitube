package service;

import datasource.DAO.PlaylistsDAO;
import dto.PlaylistDTO;
import dto.PlaylistsDTO;
import dto.TracksDTO;
import service.interfaces.IPlaylistService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.NotAuthorizedException;

@Singleton
public class PlaylistService implements IPlaylistService {

    @Inject
    protected PlaylistsDTO playlistsDTO = new PlaylistsDTO();

    @Inject
    protected PlaylistsDAO playlistsDAO = new PlaylistsDAO();

    public PlaylistsDTO getAllPlaylists() {
        var playlistsDTO = playlistsDAO.getAllPlaylists();
        return playlistsDTO.orElseThrow(() -> new NotAuthorizedException(401));
    }

    public PlaylistsDTO deleteAPlaylist(int playlist_id) {
        var playlistsDTO = playlistsDAO.deletePlaylist(playlist_id);
        return playlistsDTO.orElseThrow(() -> new NotAuthorizedException(401));
    }

    public PlaylistsDTO addPlaylist(PlaylistDTO newPlaylist){
        var playlistsDTO = playlistsDAO.addPlaylist(newPlaylist);
        return playlistsDTO.orElseThrow(() -> new NotAuthorizedException(401));
    }


    public PlaylistsDTO editPlaylist(PlaylistDTO newPlaylist) {
        var playlistsDTO = playlistsDAO.editPlaylist(newPlaylist);
        return playlistsDTO.orElseThrow(() -> new NotAuthorizedException(401));
    }

    public TracksDTO getAllTracksInAPlaylist(int playlist_id) {
        var tracksDTO = playlistsDAO.getTracksFromPlaylist(playlist_id);
        return tracksDTO;
    }

}




