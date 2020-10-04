package service;

import controllers.DTO.playlist.PlaylistDTO;
import controllers.DTO.playlist.PlaylistsDTO;

import java.util.ArrayList;

public class PlaylistService {

    public PlaylistsDTO generatePlaylists() {
        var playlistsDTO = new PlaylistsDTO();
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
}
