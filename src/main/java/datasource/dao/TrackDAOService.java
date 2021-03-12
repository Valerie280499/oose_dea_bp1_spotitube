package datasource.dao;

import datasource.connection.JDBCConnection;
import datasource.resultsetMappers.MapResultsetToTrackDTO;
import dto.TrackDTO;
import dto.TracksDTO;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrackDAOService {
    private datasource.connection.JDBCConnection JDBCConnection;
    private MapResultsetToTrackDTO mapResultsetToTrackDTO;

    @Inject public void setJDBCConnection(JDBCConnection JDBCConnection){ this.JDBCConnection = JDBCConnection; }

    @Inject public void setMapResultsetToTrackDTO(MapResultsetToTrackDTO mapResultsetToTrackDTO){ this.mapResultsetToTrackDTO = mapResultsetToTrackDTO;}

    public TracksDTO getTracks(int playlist_id, String query) throws SQLException {
        var tracks = new ArrayList<TrackDTO>();

        var conn = JDBCConnection.createConnection();
        var statement = conn.prepareStatement(query);
        statement.setInt(1, playlist_id);
        var resultSet = statement.executeQuery();

        while (resultSet.next()) {
            var trackDTO = mapResultsetToTrackDTO.map(resultSet);
            tracks.add(trackDTO);
        }
        var tracksDTO = new TracksDTO();
        tracksDTO.setTracks(tracks);
        return tracksDTO;
    }
}
