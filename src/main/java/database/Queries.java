package database;

import java.util.Properties;

public class Queries {
    private Properties properties;

    public Queries() {
        properties = new ReadPropertiesFile().getProperties();
    }

    public String selectAllFromPlaylistTable(){
        return String.format("select * from %s",
                properties.getProperty("db_table_playlist"));
    }

    public String selectAllFromTrackTable(){
        return String.format("select * from %s",
                properties.getProperty("db_table_track"));
    }

    public String selectAllFromTrackPlaylistTable(){
        return String.format("select * from %s",
                properties.getProperty("db_table_track_playlist"));
    }

    public String selectAllFromUserTable(){
        return String.format("select * from %s",
                properties.getProperty("db_table_user"));
    }

}
