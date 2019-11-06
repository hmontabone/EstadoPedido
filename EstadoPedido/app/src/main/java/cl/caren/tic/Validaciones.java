package cl.caren.tic;


import java.util.regex.Pattern;

public class Validaciones {



    public String esNumeroValido(String numero) {
        String mensaje;

        Pattern patron = Pattern.compile("^[0-9]+$");
        if (!patron.matcher(numero).matches() || numero.length() > 30) {
            mensaje = "Error";
        } else {
            mensaje = "Valido";
        }
        return mensaje;
    }




}
