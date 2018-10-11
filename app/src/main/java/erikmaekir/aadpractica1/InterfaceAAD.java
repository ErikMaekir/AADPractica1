package erikmaekir.aadpractica1;

import android.util.Log;
import android.webkit.JavascriptInterface;

import static erikmaekir.aadpractica1.ActividadBase.TAG;

public class InterfaceAAD {
    private String usuario, clave;

    public InterfaceAAD() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @JavascriptInterface
    public void sendData(String usuario, String clave) {
        setUsuario(usuario);
        setClave(clave);
        Log.v(TAG, usuario + " " + clave);
    }

    @Override
    public String toString() {
        return "InterfaceAAD{" +
                "usuario='" + usuario + '\'' +
                ", clave='" + clave + '\'' +
                '}';
    }
}
