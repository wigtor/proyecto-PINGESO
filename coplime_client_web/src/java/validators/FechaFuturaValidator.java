/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import java.util.Calendar;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Carlos
 */
@FacesValidator("FechaFuturaValidator")
public class FechaFuturaValidator implements Validator {
 
	private Calendar fechaActual;
 
	public FechaFuturaValidator(){
            fechaActual = Calendar.getInstance();
            fechaActual.set(Calendar.HOUR_OF_DAY, 23);
            fechaActual.set(Calendar.MINUTE, 50);
            fechaActual.set(Calendar.MILLISECOND, 999);
	}
 
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        System.out.println("wea puesta: "+ value);
        Calendar fechaEscrita = Calendar.getInstance();
        Date fechaDate;
        try {
            fechaDate = (Date)value;
            System.out.println("Fecha DATE: "+fechaDate);
            fechaEscrita.setTime(fechaDate);
            fechaEscrita.set(Calendar.HOUR_OF_DAY, 23);
            fechaEscrita.set(Calendar.MINUTE, 59);
            fechaEscrita.set(Calendar.MILLISECOND, 999);
        }
        catch (ClassCastException cce) {
            return;
        }
        if (fechaActual.after(fechaEscrita)) {
            //System.out.println("ERROR: la fecha escrita es menor a la actual");
            String mensaje = "La fecha para la primera revisión no puede ser pasada";
            FacesMessage msg = new FacesMessage(mensaje, "Debe ingresar una fecha futura");

            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        //System.out.println("TUDO BEM: la fecha escrita es mayor o igual a la actual");
    }
}
