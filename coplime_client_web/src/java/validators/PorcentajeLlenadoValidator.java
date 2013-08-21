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
@FacesValidator("PorcentajeLlenadoValidator")
public class PorcentajeLlenadoValidator implements Validator {
    CrudPuntoLimpioLocal crudPuntoLimpio = lookupCrudPuntoLimpioLocal();
    private static final String NUM_PATTERN = "^[0-9]+$";
 
	private Pattern pattern;
	private Matcher matcher;
 
	public PorcentajeLlenadoValidator(){
		  pattern = Pattern.compile(NUM_PATTERN);
	}
 
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {
            FacesMessage msg =
                    new FacesMessage("El número ingresado no es válido",
                    "Deben entre 0 y 100");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        Integer valor = (Integer) value;
        if (valor < 0 || valor > 100) {
            String mensaje = "El porcentaje de llenado ingresado no se encuentra en un rango válido";
            FacesMessage msg = new FacesMessage(mensaje, "Deben entre 0 y 100");

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
