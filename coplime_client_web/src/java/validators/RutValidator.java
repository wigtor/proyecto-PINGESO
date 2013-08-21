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
import sessionBeans.CrudUsuariosComunLocal;

/**
 *
 * @author Carlos
 */
@FacesValidator("RutValidator")
public class RutValidator implements Validator {
    CrudUsuariosComunLocal crudUsuariosComun = lookupCrudUsuariosComunLocal();
    private static final String RUT_PATTERN = "^[0-9]+$";
 
	private Pattern pattern;
	private Matcher matcher;
 
	public RutValidator(){
		  pattern = Pattern.compile(RUT_PATTERN);
	}
 
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {
            FacesMessage msg =
                    new FacesMessage("El rut ingresado no es válido",
                    "Deben ser sólo números");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        if (crudUsuariosComun.existeRut((Integer) value)) {
            String mensaje = "El rut del usuario \"".concat(value.toString()).concat("\" ya existe");
            FacesMessage msg = new FacesMessage(mensaje, mensaje);

            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

    private CrudUsuariosComunLocal lookupCrudUsuariosComunLocal() {
        try {
            Context c = new InitialContext();
            return (CrudUsuariosComunLocal) c.lookup("java:global/coplime/coplime-ejb/CrudUsuariosComun!sessionBeans.CrudUsuariosComunLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
