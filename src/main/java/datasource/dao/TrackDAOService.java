package datasource.dao;

import com.mysql.jdbc.JDBC4Connection;
import datasource.connection.JDBCDatabaseConnection;
import datasource.resultsetMappers.MapResultsetToTrackDTO;
import dto.TrackDTO;
import dto.TracksDTO;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrackDAOService {
    private JDBC4Connection JDBCconnection;
    private MapResultsetToTrackDTO mapResultsetToTrackDTO;

    @Inject public void setJDBCConnection(JDBCDatabaseConnection JDBCDatabaseConnection){ this.JDBCconnection = JDBCDatabaseConnection.createConnection(); }

    @Inject public void setMapResultsetToTrackDTO(MapResultsetToTrackDTO mapResultsetToTrackDTO){ this.mapResultsetToTrackDTO = mapResultsetToTrackDTO;}

    public TracksDTO getTracks(int playlist_id, String query) throws SQLException {
        var tracks = new ArrayList<TrackDTO>();

        var statement = JDBCconnection.prepareStatement(query);
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
