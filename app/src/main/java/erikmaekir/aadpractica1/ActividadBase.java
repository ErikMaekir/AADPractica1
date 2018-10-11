package erikmaekir.aadpractica1;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ActividadBase extends AppCompatActivity {

    static final String TAG = "MITAG";
    private WebView webView;
    private String url = "http://www.juntadeandalucia.es/averroes/centros-tic/18700098/moodle2/login/index.php";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_base);

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        final InterfaceAAD interf = new InterfaceAAD();
        webView.addJavascriptInterface(interf, "puente");
        webView.loadUrl(url);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        final SharedPreferences.Editor editor = pref.edit();


        if (!pref.getString("pass", null).equals(null)){
            final String JSguardar = "javascript:(function(){\n" +
                    "  document.getElementById('username').value='" + pref.getString("usuario",null) + "';\n" +
                    "  document.getElementById('password').value='" + pref.getString("pass", null) + "';\n" +
                    "  document.forms.loginbtn.submit.click();\n" +
                    "})();";
            webView.loadUrl(JSguardar);

            String urlResult = webView.getUrl();

            if (urlResult == url){
                editor.putString("usuario", null);
                editor.putString("pass", null);
                editor.apply();
            }
        }

        webView.setWebViewClient(new WebViewClient() {
            int contador = 0;
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.v(TAG, url);
                if(contador == 0) {
                    Log.v(TAG, "on page finished");
                    final String javaScript = "" +
                            "var boton = document.getElementById('loginbtn');" +
                            "boton.addEventListener('click', function() {" +
                            "    var nombre = document.getElementById('username').value;" +
                            "    var clave  = document.getElementById('password').value;" +
                            "    puente.sendData(nombre, clave);" +
                            "});";
                    webView.loadUrl("javascript: " + javaScript);
                    Log.v(TAG, interf.getUsuario());
                    Log.v(TAG, interf.getClave());

                    editor.putString("usuario", interf.getUsuario() );
                    editor.putString("pass", interf.getClave());
                    editor.apply();

                }
                contador++;
            }
        });
    }
}
