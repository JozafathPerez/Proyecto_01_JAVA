package logica;

import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;   // para agregar múltiples partes al mensaje con estilo MIME
import javax.mail.internet.MimeBodyPart;    // para agregar el cuerpo al mensaje con estilo MIME
import javax.activation.DataSource;         // para recuperar el archivo que adjuntar
import javax.activation.FileDataSource;     // para recuperar el archivo que adjuntar
import javax.activation.DataHandler;        // para recuperar el archivo que adjuntar

/**
 * La clase CuentaCorreo se utiliza para enviar correos electrónicos a través de una
 * cuenta de correo electrónico configurada.
 */
public class CuentaCorreo {
  private String usuario;
  private String clave = "ibwnbsesoikhqgcq"; 
  private String servidor = "smtp.gmail.com";
  private String puerto = "587"; 
  private Properties propiedades;
  
  /**
   * Constructor de la clase CuentaCorreo.
   * 
   * @param pCorreo La dirección de correo electrónico del remitente.
   */
  public CuentaCorreo(String pCorreo) {
    propiedades = new Properties();
    propiedades.put("mail.smtp.host", servidor);
    propiedades.put("mail.smtp.port", puerto);
    propiedades.put("mail.smtp.auth", "true");
    propiedades.put("mail.smtp.starttls.enable", "true");
    usuario = pCorreo;
  }
  
  /**
   * Abre una sesión de correo electrónico autenticada.
   * 
   * @return Una instancia de la sesión de correo electrónico.
   */
  private Session abrirSession() {
    Session sesion = Session.getInstance(propiedades,
      new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(usuario, clave);
        }
    });
    return sesion;
  }
  
  /**
   * Envía un correo electrónico sin archivos adjuntos.
   * 
   * @param destinatario La dirección de correo electrónico del destinatario.
   * @param tituloCorreo El título del correo electrónico.
   * @param cuerpo El cuerpo del correo electrónico.
   */
  public void enviarCorreo(String destinatario, String tituloCorreo, String cuerpo) {
    Session sesion = abrirSession();
    
    try {
      Message message = new MimeMessage(sesion);
      message.setFrom(new InternetAddress(usuario));
      message.setRecipients(
        Message.RecipientType.TO,
        InternetAddress.parse(destinatario)
      );
      message.setSubject(tituloCorreo);
      message.setText(cuerpo);
      
      Transport.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Envía un correo electrónico con archivos adjuntos.
   * 
   * @param destinatario La dirección de correo electrónico del destinatario.
   * @param tituloCorreo El título del correo electrónico.
   * @param cuerpo El cuerpo del correo electrónico.
   * @param archivosAdjuntos Arreglo de rutas de archivos a adjuntar.
   */
  public void enviarCorreo(String destinatario, String tituloCorreo, String cuerpo, 
                           String[] archivosAdjuntos) {
    Session sesion = abrirSession();

    try {
      Message message = new MimeMessage(sesion);
      MimeMultipart multiPart = new MimeMultipart();
      
      message.setFrom(new InternetAddress(usuario));
      message.setRecipients(
        Message.RecipientType.TO, 
        InternetAddress.parse(destinatario)
      );
      message.setSubject(tituloCorreo);

      MimeBodyPart mensajePart = new MimeBodyPart();
      mensajePart.setText(cuerpo);
      multiPart.addBodyPart(mensajePart);

      // Ciclo para recuperar y adjuntar los archivos de las rutas
      for (String ruta : archivosAdjuntos) {
        MimeBodyPart archivo = new MimeBodyPart();
        DataSource fuente = new FileDataSource(ruta);
        archivo.setDataHandler(new DataHandler(fuente));
        archivo.setFileName(fuente.getName());
        multiPart.addBodyPart(archivo);
      }

      message.setContent(multiPart);

      Transport.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}

