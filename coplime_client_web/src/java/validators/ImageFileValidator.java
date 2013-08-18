/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.primefaces.model.DefaultUploadedFile;

/**
 *
 * @author Carlos
 */
@FacesValidator("ImageFileValidator")
public class ImageFileValidator implements Validator{
	public ImageFileValidator(){
	}
 
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        List<FacesMessage> msgs = new ArrayList<>();
        DefaultUploadedFile file = (DefaultUploadedFile) value;
        if (file== null) {
            return; //En caso que no se haya optado por subir una imagen, no es obligatorio
        }
        if (file.getSize() > 16777216) {
            msgs.add(new FacesMessage("Imagen muy grande, máximo 16Mb, intente bajando la resolución"));
        }
        
        if (!file.getContentType().contains("image/")) {
            msgs.add(new FacesMessage("Archivo inválido, sólo están permitidas imágenes"));
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }
}
