/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import sessionBeans.CrudPuntoLimpioLocal;

/**
 *
 * @author Carlos
 */
@FacesValidator("NombrePtoLimpioValidator")
public class NombrePtoLimpioValidator implements Validator{
    CrudPuntoLimpioLocal crudPuntoLimpio = lookupCrudPuntoLimpioLocal();
    private static final String NOMBRE_PATTERN = "^[_A-Za-z0-9- ]+$";
 
	private Pattern pattern;
	private Matcher matcher;
 
	public NombrePtoLimpioValidator(){
		  pattern = Pattern.compile(NOMBRE_PATTERN);
	}
 
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {
            FacesMessage msg =
                    new FacesMessage("El nombre ingresado no es válido",
                    "Deben ser sólo letras y/o números");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        if (crudPuntoLimpio.existeNombrePuntoLimpio((String) value)) {
            String mensaje = "El nombre de punto limpio \"".concat(value.toString()).concat("\" ya existe");
            FacesMessage msg = new FacesMessage(mensaje, mensaje);

            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

    private CrudPuntoLimpioLocal lookupCrudPuntoLimpioLocal() {
        try {
            Context c = new InitialContext();
            return (CrudPuntoLimpioLocal) c.lookup("java:global/coplime/coplime-ejb/CrudPuntoLimpio!sessionBeans.CrudPuntoLimpioLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
