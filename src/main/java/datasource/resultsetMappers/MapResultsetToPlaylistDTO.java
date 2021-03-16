package datasource.resultsetMappers;

import dto.PlaylistDTO;
import dto.TrackDTO;
import dto.TracksDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MapResultsetToPlaylistDTO {

    public PlaylistDTO map(ResultSet resultSet, TracksDTO tracksDTO) throws SQLException {
        var playlistDTO = new PlaylistDTO();

        playlistDTO.setId(resultSet.getInt("id"));
        playlistDTO.setName(resultSet.getString("name"));
        playlistDTO.setOwner(resultSet.getBoolean("owner"));
        playlistDTO.setTracks(tracksDTO.getTracks());

        return playlistDTO;
    }
}
