package services;

import dto.PlaylistDTO;

public class PlaylistService {

    public int generateLengthPlaylist(PlaylistDTO playlistDTO){
        var tracks = playlistDTO.getTracks();
        var lenghtPlaylist = 0;
        for (var track : tracks) {
            lenghtPlaylist += track.getDuration();
        }
        return lenghtPlaylist;
    }
}
