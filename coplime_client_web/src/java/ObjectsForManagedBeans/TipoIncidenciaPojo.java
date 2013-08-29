/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectsForManagedBeans;

/**
 *
 * @author Armando
 */
public class TipoIncidenciaPojo {
    private String id;
    private String nombreTipoIncidencia;
    private boolean visibleAlUsuario;

    public TipoIncidenciaPojo() {
    }

    public TipoIncidenciaPojo(String nombreTipoIncidencia, boolean visibleAlUsuario) {
        this.nombreTipoIncidencia = nombreTipoIncidencia;
        this.visibleAlUsuario = visibleAlUsuario;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreTipoIncidencia() {
        return nombreTipoIncidencia;
    }

    public void setNombreTipoIncidencia(String nombreTipoIncidencia) {
        this.nombreTipoIncidencia = nombreTipoIncidencia;
    }

    public boolean isVisibleAlUsuario() {
        return visibleAlUsuario;
    }

    public void setVisibleAlUsuario(boolean visibleAlUsuario) {
        this.visibleAlUsuario = visibleAlUsuario;
    }
    
    
    
}
