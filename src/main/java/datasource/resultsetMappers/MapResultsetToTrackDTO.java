package datasource.resultsetMappers;

import dto.TrackDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapResultsetToTrackDTO {

    public TrackDTO map(ResultSet resultSet) throws SQLException {
        var track = new TrackDTO();
        track.setId(resultSet.getInt("id"));
        track.setTitle(resultSet.getString("titel"));
        track.setPerformer(resultSet.getString("performer"));
        track.setDuration(resultSet.getInt("duration"));
        track.setAlbum(resultSet.getString("album"));
        track.setPlaycount(resultSet.getInt("playcount"));
        track.setPublicatationDate(resultSet.getString("publicationDate"));
        track.setDescription(resultSet.getString("description"));
        track.setOfflineAvailable(resultSet.getBoolean("offlineAvailable"));
        return track;
    }
}
