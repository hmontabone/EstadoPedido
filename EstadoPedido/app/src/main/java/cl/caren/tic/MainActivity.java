package cl.caren.tic;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {

    Conexion conexion;
    private EditText etNumero;
    private EditText etEstado;
    private ImageView est1, est2, est3, est4, est5, est6;
    MediaPlayer mp;
    int color_principal = Color.parseColor("#938D8C");
    int color_secundario = Color.parseColor("#AE6118");
    private Button btnConsultar, btnNuevaConsulta;

    Pedido pedido = new Pedido();
    DML dml = new DML();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp=MediaPlayer.create(this,R.raw.exito);

        est1=findViewById(R.id.img_porliberar);
        est2=findViewById(R.id.img_liberado);
        est3=findViewById(R.id.img_pickeado);
        est4=findViewById(R.id.img_embalado);
        est5=findViewById(R.id.img_anden);
        est6=findViewById(R.id.img_despachado);

        setColor_principal();

        etNumero= findViewById(R.id.etNumero);

        etNumero.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etEstado=findViewById(R.id.etEstado);
        etEstado.setEnabled(false);
        btnConsultar = findViewById(R.id.btnConnect);
        btnConsultar.setEnabled(false);
        btnNuevaConsulta = findViewById(R.id.btnNueva);


        btnNuevaConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etNumero.setText("");
                etEstado.setText("");
                est1.setColorFilter(color_principal);
                est2.setColorFilter(color_principal);
                est3.setColorFilter(color_principal);
                est4.setColorFilter(color_principal);
                est5.setColorFilter(color_principal);
                est6.setColorFilter(color_principal);

            }
        });


        etNumero.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.d("TAG_", "On start: " + start);
                Log.d("TAG_", "On count : " + count);

                if (count>=1 && start>=5){
                    btnConsultar.setEnabled(true);
                } else {
                    btnConsultar.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                est1.setColorFilter(color_principal);
                est2.setColorFilter(color_principal);
                est3.setColorFilter(color_principal);
                est4.setColorFilter(color_principal);
                est5.setColorFilter(color_principal);
                est6.setColorFilter(color_principal);

                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    Log.w("TAG", "getInstanceId failed", task.getException());
                                    return;
                                }

                                // Get new Instance ID token
                                String token = task.getResult().getToken();

                                // Log
                                Log.d("TAG", "token" + token);

                            }
                        });


                conexion = new Conexion();

                try {

                    Connection conn = conexion.CONN( );
                    if (conn == null) {

                        Toast.makeText(getBaseContext(), "No se pudo establecer la conexion", Toast.LENGTH_SHORT).show();
                    }else{


                        Log.d("TAG_", "A consultar : " + etNumero.getText());
                        pedido = dml.ConsultarEstadoPedido(conn, etNumero.getText().toString());
                        Log.d("TAG_", "Estado del Pedido  " +etNumero.getText()+ "es : " + pedido.getEstado());


                        if (pedido.getEstado().equals("Vacio")){

                            etEstado.setText("");

                        } else {


                            switch( pedido.getEstado()) {
                                case "POR LIBERAR":
                                    etEstado.setText(pedido.getEstado());
                                    est1.setColorFilter(color_secundario);

                                    break;
                                case "LIBERADO" :
                                    etEstado.setText(pedido.getEstado());

                                    est2.setColorFilter(color_secundario);
                                    break;
                                case "PICKEADO" :
                                    etEstado.setText(pedido.getEstado());

                                    est3.setColorFilter(color_secundario);
                                    break;
                                case "EMBALADO" :
                                    etEstado.setText(pedido.getEstado());

                                    est4.setColorFilter(color_secundario);
                                    break;
                                case "ANDEN" :
                                    etEstado.setText(pedido.getEstado());

                                    est5.setColorFilter(color_secundario);
                                    break;
                                case "DESPACHADO" :
                                    etEstado.setText(pedido.getEstado());

                                    est6.setColorFilter(color_secundario);
                                    //Play sonido exito
                                    mp.start();
                                    break;

                                default:
                                    etEstado.setText("");
                                    Toast.makeText(MainActivity.this, "No existe", Toast.LENGTH_SHORT).show();
                                    setColor_principal();



                            }
                        }



                    }
                }catch (Exception e){
                    Log.e("ERROR - ", e.getMessage());
                }



            }
        });


    }



    public void setColor_principal(){

        est1.setColorFilter(color_principal);
        est2.setColorFilter(color_principal);
        est3.setColorFilter(color_principal);
        est4.setColorFilter(color_principal);
        est5.setColorFilter(color_principal);
        est6.setColorFilter(color_principal);

    }


}



