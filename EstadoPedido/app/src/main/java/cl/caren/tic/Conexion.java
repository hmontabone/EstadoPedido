package cl.caren.tic;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    String classs = "net.sourceforge.jtds.jdbc.Driver";

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {
            Class.forName(classs);
            String ip = "200.54.81.1";
            String db = "wms";
            String un = "wmsbesa1";
            String password = "besa";
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + ip + "/" + db+ ";user=" + un + ";password=" + password + ";");
        } catch (SQLException se) {
            Log.e("ERROR:1", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERROR:2", e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR:3", e.getMessage());
        }
        return conn;
    }
}