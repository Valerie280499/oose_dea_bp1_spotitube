package service;

import controllers.DTO.playlist.PlaylistDTO;
import controllers.DTO.playlist.PlaylistsDTO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;

@Singleton
public class PlaylistService {

    @Inject
    // protected want kan anders niet testen @Jailbreak werkte niet
    protected PlaylistsDTO playlistsDTO = new PlaylistsDTO();

    public PlaylistsDTO generatePlaylists() {
        playlistsDTO.setLength(6);

        ArrayList<PlaylistDTO> playlists = new ArrayList<>();
        playlists.add(new PlaylistDTO(1, "sunday morning pop", true));
        playlists.add(new PlaylistDTO(2, "new pop", true));
        playlists.add(new PlaylistDTO(3, "love pop", true));
        playlists.add(new PlaylistDTO(4, "sport rock", true));
        playlists.add(new PlaylistDTO(5, "study rock", true));
        playlists.add(new PlaylistDTO(6, "fun rock", true));

        playlistsDTO.setPlaylists(playlists);
        return playlistsDTO;
    }

    public PlaylistsDTO deleteAPlaylist(int playlist_id) {
        var playlists = playlistsDTO.getPlaylists();

        playlists.removeIf(playlist -> playlist.getId() == playlist_id);

        playlistsDTO.setPlaylists(playlists);
        return playlistsDTO;
    }

    public PlaylistsDTO addPlaylist(PlaylistDTO newPlaylist){
        var playlists = playlistsDTO.getPlaylists();
        playlists.add(new PlaylistDTO(newPlaylist.getId(), newPlaylist.getName(), false));

        playlistsDTO.setPlaylists(playlists);
        return playlistsDTO;
    }


    public PlaylistsDTO editPlayist(int playlist_id, PlaylistDTO newPlaylist) {
        var playlists = playlistsDTO.getPlaylists();

        playlists.removeIf(playlist -> playlist.getId() == playlist_id);
        playlists.add(new PlaylistDTO(newPlaylist.getId(), newPlaylist.getName(), false));

        playlistsDTO.setPlaylists(playlists);
        return playlistsDTO;

    }
}




