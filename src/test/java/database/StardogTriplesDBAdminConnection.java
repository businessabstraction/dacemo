package database;

import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.admin.AdminConnection;
import com.complexible.stardog.api.admin.AdminConnectionConfiguration;
import com.stardog.stark.io.RDFFormats;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StardogTriplesDBAdminConnection {
    private AdminConnection adminConnection;

    private String dbURL, user, pass;

    StardogTriplesDBAdminConnection(String dbURL, String user, String pass){
        AdminConnectionConfiguration adminConfig = AdminConnectionConfiguration.toServer(dbURL).credentials(user, pass);
        adminConnection = adminConfig.connect();

        this.dbURL = dbURL;
        this.user = user;
        this.pass = pass;
    }

    boolean existsDB(String dbName){
        return adminConnection.list().contains(dbName);
    }

    public void dropDB(String dbName){
        adminConnection.drop(dbName);
    }

    void createDB(String dbName, String pathToData){
        adminConnection.newDatabase(dbName).create();

        if (pathToData != null){
            ConnectionConfiguration config = ConnectionConfiguration.to(dbName).server(dbURL).credentials(user, pass);
            Connection connection = config.connect();

            connection.begin();
            try {
                connection.add().io().format(RDFFormats.TURTLE).stream(new FileInputStream(pathToData));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            connection.commit();
        }
    }
}
