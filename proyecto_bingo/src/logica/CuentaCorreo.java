package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeMultipart; // para agregar múltiples partes al mensaje con estilo MIME
import javax.mail.internet.MimeBodyPart; // para agregar el cuerpo al mensaje con estilo MIME
import javax.activation.DataSource; // para recuperar el archivo que adjuntar
import javax.activation.FileDataSource; // para recuperar el archivo que adjuntar
import javax.activation.DataHandler; // para recuperar el archivo que adjuntar

/**
 * La clase CuentaCorreo se utiliza para enviar correos electrónicos a través de
 * una
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
   * @param cuerpo       El cuerpo del correo electrónico.
   */
  public void enviarCorreo(String destinatario, String tituloCorreo, String cuerpo) {
    Session sesion = abrirSession();

    try {
      Message message = new MimeMessage(sesion);
      message.setFrom(new InternetAddress(usuario));
      message.setRecipients(
          Message.RecipientType.TO,
          InternetAddress.parse(destinatario));
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
   * @param destinatario     La dirección de correo electrónico del destinatario.
   * @param tituloCorreo     El título del correo electrónico.
   * @param cuerpo           El cuerpo del correo electrónico.
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
          InternetAddress.parse(destinatario));
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

  public List<Message> obtenerMensajes() {
    List<Message> mensajes = new ArrayList<>();

    Properties prop = new Properties();

    // Deshabilitamos TLS
    prop.setProperty("mail.pop3.starttls.enable", "false");

    // Hay que usar SSL
    prop.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    prop.setProperty("mail.pop3.socketFactory.fallback", "false");

    // Puerto 995 para conectarse.
    prop.setProperty("mail.pop3.port", "995");
    prop.setProperty("mail.pop3.socketFactory.port", "995");

    Session sesion = Session.getInstance(prop);

    // Para obtener un log más extenso.
    sesion.setDebug(true);

    try {
      // Conéctate a la cuenta de correo
      Store store = sesion.getStore("pop3");
      store.connect("pop.gmail.com", usuario, clave);

      // Abre la carpeta de entrada
      Folder folder = store.getFolder("INBOX");
      folder.open(Folder.READ_ONLY);

      // Obtiene los mensajes y los agrega a la lista
      Message[] mensajesEnBandeja = folder.getMessages();

      for (Message mensaje : mensajesEnBandeja) {
        // Analizar el contenido del mensaje
        analizarContenidoMensaje(mensaje);
        mensajes.add(mensaje);
      }

      // Cierra la carpeta y la conexión
      folder.close(true); // Cierra la carpeta y expunge los mensajes eliminados
      store.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return mensajes;
  }

  public List<String> obtenerComentariosDeMensajes() {
    List<String> comentarios = new ArrayList<>();

    Properties prop = new Properties();

    // Deshabilitamos TLS
    prop.setProperty("mail.pop3.starttls.enable", "false");

    // Hay que usar SSL
    prop.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    prop.setProperty("mail.pop3.socketFactory.fallback", "false");

    // Puerto 995 para conectarse.
    prop.setProperty("mail.pop3.port", "995");
    prop.setProperty("mail.pop3.socketFactory.port", "995");

    Session sesion = Session.getInstance(prop);

    // Para obtener un log más extenso.
    sesion.setDebug(true);

    try {
      Store store = sesion.getStore("pop3");
      store.connect("pop.gmail.com", usuario, clave);

      Folder folder = store.getFolder("INBOX");
      folder.open(Folder.READ_ONLY);

      Message[] mensajesEnBandeja = folder.getMessages();

      for (Message mensaje : mensajesEnBandeja) {
        String comentario = analizarContenidoMensaje(mensaje);
        comentarios.add(comentario);
      }

      folder.close(true);
      store.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return comentarios;
  }

  private String analizarContenidoMensaje(Message mensaje) {
    try {
      StringBuilder contenidoMensaje = new StringBuilder();

      if (mensaje.isMimeType("text/plain")) {
        contenidoMensaje.append((String) mensaje.getContent());
      } else if (mensaje.isMimeType("multipart/*")) {
        Multipart multiPart = (Multipart) mensaje.getContent();

        for (int i = 0; i < multiPart.getCount(); i++) {
          BodyPart parte = multiPart.getBodyPart(i);

          if (parte.isMimeType("text/plain")) {
            contenidoMensaje.append((String) parte.getContent());
          }
        }
      }

      String comentario = contenidoMensaje.toString().trim();
      return comentario;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

}
