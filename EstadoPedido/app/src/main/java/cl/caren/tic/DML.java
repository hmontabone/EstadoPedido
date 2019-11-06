package cl.caren.tic;

import android.util.Log;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DML {





    public Pedido ConsultarEstadoPedido(Connection miBD, String numero) throws SQLException {


        //Rellenar con 0 a la izquierda, debe ser cadena de 8
        numero = String.format("%08d", Integer.parseInt(numero));

        Pedido consultado = new Pedido();

        Log.d("TAG_", "Recibido : " + numero);

        String consulta = "{call intranet_statusPedido(?)} ";


        CallableStatement ps = miBD.prepareCall(consulta);
        ps.setString(1,numero);
        ResultSet rs = ps.executeQuery();
        rs.next();

        //Se extraen los datos

        if (!rs.equals(null)){

            Log.d("TAG_", "Verificando estado ... " );

            String respuesta = rs.getString(1) ;
            Log.d("TAG_", "1. Estado:  "+ respuesta);

            if (respuesta==null){
                consultado.setEstado("No existe NV/SF");
                Log.d("TAG_", "El estado es : " + consultado.getEstado());
            }

            else  {

            Log.d("TAG_", "No esta vacio " );
            consultado.setEstado(rs.getString(1));
            Log.d("TAG_", "RS String: " + rs.getString(1));
            Log.d("TAG_", "El estado es : " + consultado.getEstado());

            }
        }


        //Se cierra la conexión
        miBD.close();
        // Se envian los datos
        return  consultado;

    }




}
