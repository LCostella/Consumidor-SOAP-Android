package com.example.leonardo.clientesoapcorreios;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



public class MainActivity extends ActionBarActivity {

    Encomenda encomenda = new Encomenda();
    String TAG ="Response";
    Button bt;
    EditText nCdServico;
    EditText sCepOrigem;
    EditText sCepDestino;
    String getServico;
    String getOrigem;
    String getDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt = (Button) findViewById(R.id.enviar);
        sCepOrigem = (EditText) findViewById(R.id.sCepOrigem);
        sCepDestino = (EditText) findViewById(R.id.sCepDestino);
        nCdServico = (EditText) findViewById(R.id.CdServico);
        if(nCdServico.length()== 0 ) {

        }



        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDestino = sCepDestino.getText().toString();
                getOrigem = sCepOrigem.getText().toString();
                getServico = nCdServico.getText().toString();
                AsyncCallWS task = new AsyncCallWS();
                task.execute();

            }
        });

    }
    private class AsyncCallWS extends AsyncTask<Void,Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            CalcPrazo();
            return null;
        }
        protected  void onPostExecute(Void result){
            Log.i(TAG,"onPostExecute");

            AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);

            dialogo.setTitle("Resultado"); // setando título

            // setando mensagem
            dialogo.setMessage(
                    "Código da encomenda: "+ encomenda.getCodigo()    +"\n " +
                            "Prazo de Entrega: " + encomenda.getPrazoEntrega()+ " \n" +
                            "Entrega Domiciliar: " + encomenda.getEntregaDomiciliar()+ " \n" +
                            "Entrega Sábado: " + encomenda.getEntregaSabado());


            dialogo.setNeutralButton("OK", null); // setando botão

            dialogo.show();   // chamando o AlertDialog

        }
    }

    private void CalcPrazo() {
        String SOAP_ACTION = "http://tempuri.org/CalcPrazo"; //nome da açao
        String METHOD_NAME = "CalcPrazo";// nome do método a ser envocado
        String NAMESPACE =  "http://tempuri.org/";//NOME DO WEBSERVICE
        String URL = "http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx"; /// URL DO METODO

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("nCdServico", getServico);
            Request.addProperty("sCepOrigem", getOrigem);
            Request.addProperty("sCepDestino", getDestino);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);

            transport.call(SOAP_ACTION, soapEnvelope);

            SoapObject response = (SoapObject) soapEnvelope.getResponse();

            response = (SoapObject) response.getProperty("Servicos");
            response = (SoapObject) response.getProperty("cServico");
            encomenda = new Encomenda();
            encomenda.setCodigo(response.getPropertyAsString("Codigo"));
            encomenda.setPrazoEntrega(response.getPropertyAsString("PrazoEntrega"));
            encomenda.setEntregaDomiciliar(response.getPropertyAsString("EntregaDomiciliar"));
            encomenda.setEntregaSabado(response.getPropertyAsString("EntregaSabado"));

        }catch (Exception ex){
            Log.e(TAG, "ERROR: " + ex.getMessage());
        }

    }




}