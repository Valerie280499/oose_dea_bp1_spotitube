package service;

import controllers.DTO.PlaylistDTO;
import controllers.DTO.PlaylistsDTO;

import java.util.ArrayList;

public class PlaylistService {

    public PlaylistsDTO generatePlaylists(){

        var playlistsDTO = new PlaylistsDTO();
        playlistsDTO.setLength(1000);

        ArrayList<PlaylistDTO> playlists = new ArrayList<>();
        playlists.add(new PlaylistDTO(1, "test lijst", true));

        playlistsDTO.setPlaylists(playlists);

        return playlistsDTO;

    }
}
