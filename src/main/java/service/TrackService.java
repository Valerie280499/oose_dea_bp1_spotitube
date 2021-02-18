package service;

import dto.PlaylistsDTO;
import dto.TrackDTO;
import dto.TracksDTO;
import service.interfaces.ITrackService;

import javax.inject.Inject;
import java.util.ArrayList;

public class TrackService implements ITrackService {

    @Inject
    protected PlaylistsDTO playlistsDTO = new PlaylistsDTO();
    private TracksDTO tracksDTO = new TracksDTO();

    public TracksDTO generateTracks() {
        tracksDTO.setLength(10);

        ArrayList<TrackDTO> tracks = new ArrayList<>();
        tracks.add(new TrackDTO(1, "song1", "singer1", 100, "red album", 5, "01-01-2001", "undefined", true));
        tracks.add(new TrackDTO(2, "song2", "singer2", 50, "blue album", 10, "03-03-2003", "undefined", false));
        tracks.add(new TrackDTO(3, "song3", "singer3", 25, "green album", 15, "06-06-2006", "undefined", false));
        tracks.add(new TrackDTO(4, "song4", "singer4", 125, "white album", 20, "09-09-2009", "best album ever", false));
        tracks.add(new TrackDTO(5, "song5", "singer5", 150, "yellow album", 25, "12-12-2012", "worst album ever", true));
        tracks.add(new TrackDTO(6, "song6", "singer6", 35, "pink album", 6, "12-07-2015", "undefined", false));

        tracksDTO.setTracks(tracks);
        return tracksDTO;
    }

    public TracksDTO addTrackToPlaylist(int playlist_id, TrackDTO newTrack){
        var playlistDTO = playlistsDTO.getPlaylists().get(playlist_id);
        var tracks = playlistDTO.getTracks();

        tracks.add(new TrackDTO(newTrack.getId(), newTrack.getTitle(),
                newTrack.getPerformer(), newTrack.getDuration(),
                newTrack.getAlbum(), newTrack.getPlaycount(),
                newTrack.getPublicatationDate(), newTrack.getDescription(),
                newTrack.getOfflineAvailable()));

        tracksDTO.setTracks(tracks);
        playlistDTO.setTracks(tracks);

        return tracksDTO;
    }
}
