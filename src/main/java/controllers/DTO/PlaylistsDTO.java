package controllers.DTO;

import java.util.ArrayList;

public class PlaylistsDTO {

    private ArrayList<PlaylistDTO> playlists;
    private int length;


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public ArrayList<PlaylistDTO> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<PlaylistDTO> playlists) {
        this.playlists = playlists;
    }
}
