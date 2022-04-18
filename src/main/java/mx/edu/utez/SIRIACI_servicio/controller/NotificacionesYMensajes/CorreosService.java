package mx.edu.utez.SIRIACI_servicio.controller.NotificacionesYMensajes;

import com.devskiller.friendly_id.FriendlyId;
import mx.edu.utez.SIRIACI_servicio.model.aspecto.Aspecto;
import mx.edu.utez.SIRIACI_servicio.model.aspecto.AspectoRepository;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.IncidenciaRepository;
import mx.edu.utez.SIRIACI_servicio.model.noVerificado.NoVerificado;
import mx.edu.utez.SIRIACI_servicio.model.responsable.Responsable;
import mx.edu.utez.SIRIACI_servicio.model.responsable.ResponsableRepository;
import mx.edu.utez.SIRIACI_servicio.model.solicitudRestablecimiento.SolicitudRestablecimiento;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import mx.edu.utez.SIRIACI_servicio.util.Formateador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CorreosService {
    private final static String plantillaCorreo = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\"><html lang=\"es\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><title></title><style type=\"text/css\"></style></head><body style=\"margin:0; padding:0;\"><center><table width=\"100%%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td align=\"center\" valign=\"top\"><table width=\"640\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"wrapper\" bgcolor=\"#F2F2F2\" style=\"padding-top: 15px;\"><tr><td height=\"10\" style=\"font-size:10px; line-height:10px;\">&nbsp;</td></tr><tr><td align=\"center\" valign=\"top\"><table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"container\"><tr><td align=\"center\" valign=\"top\"><table width=\"100%%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"left\" valign=\"middle\"><img src=\"https://lh3.googleusercontent.com/SkhZbp4P20caPVpEPtvlqCwAI_EmTrxaGDYC2__K0AdqrC-KqgDQ4Ff3XBeevjSNXYCyf56y_iblmrqfkCWezwk-apQGOF619PHS4dlOgRnC9QSeS0Q7YgcThvQvSA29fx5ytlDIEY0AqLxGc9PiR50X_7bVOPIWEt459cf6F4YENWnfP3Iu2Zt9mcCDbGqTMNt3jHT1Hl-9S4mbcdzyPBRrbI70U4er5N-PxdP_x6bQ0QBiaLz3f2lvjGvjt8HYbiJ0Nh1qftd-eP-qhKSMSoRfVR1XtYRPsz52OobiVr5zu_1Bs-8-eE0l16ljBbEQVKSHmVMC0e60qSt-K4x-SxyGTNsp86Ba30l6RyFneTYqhvQuM_FwSVdGsbfS-IP49EuvkKC9AvcP1N2TImdFOQMzJkuZso_PwrO2IlBTKjX5PwnbGAspjdb3v3is_F8rnPRYWsiA6b5zkyqzvDG3DCqSa6qE1CpO0PDr425057FKORjHIZmcylDcrGKYpj3PoXCHetNeSE1UvubooOnEGO5wk9hpUwfmgs_PvwuHHHqKy01HAh7aELTJzpjXepyN_7NTOowmJ7q_tGsfvNUW4HP4hjs5J2H2-KDJ0UeooStiEu18AF7AD7fHR3np8EmCSRvuKKbdxR0CfsTB8KlfZhTNRnp93eQEP1-lx935BRd_xR2Lo67ERyMi7qpTL3rCA0Gi8dW3UrNcu_n5gqIsJDLigQXxjomOUyjbJwyJWVOiOIdTx45ELUnPxx_uS7DeFIsZdyRq_WsiYMiAHEiXFa5fPrpXF2dQ2K2G9TNOtB_BlR1UFA6p30bVv5NuR6Z7iXqcfhbXe00I5PFo2xSC5w5QrsxeCbr9aLiHlQ=w151-h35-no\" width=\"151\" height=\"35\" style=\"margin:0; padding:0; border:none; display:block;\" border=\"0\" alt=\"\" /></td><td align=\"right\" valign=\"middle\"><h1 style=\"margin:0; padding:0;font-family: Arial, sans-serif; color: #5D5D5D; font-size: 30px;\">SIRIACI</h1></td></tr><tr><td height=\"30\" style=\"font-size:10px; line-height:10px;\">&nbsp;</td></tr></table><table width=\"100%%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color: #fff; background-clip: border-box; border: 1px solid rgba(0, 0, 0, 0.125); border-radius: 1rem; box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15); padding-top: 25px; padding-bottom: 25px; padding-left: 30px; padding-right: 30px;\"><tr><td align=\"left\" valign=\"top\"><h3 style=\"margin:0; padding:0; margin-bottom:15px;font-family: Arial, sans-serif; color: #353E4A; font-size: 18px;\">%1$s</h3><p style=\"margin:0; padding:0; margin-bottom:15px;font-family: Arial, sans-serif; color: #353E4A; font-size: 16px;\">%2$s</p></td></tr><tr><td align=\"center\" valign=\"top\">%3$s</td></tr></table><table width=\"100%%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td height=\"25\" style=\"font-size:10px; line-height:10px;\">&nbsp;</td></tr><tr><td align=\"center\" valign=\"top\"><p style=\"margin:0; padding:0; margin-bottom:15px;font-family: Arial, sans-serif; color: #5D5D5D; font-size: 16px;\"><b>SIRIACI | </b>Sistema de Reporte de Indicencias Ambientales y Cápsulas Informativas</p></td></tr></table></td></tr></table></td></tr><tr><td height=\"10\" style=\"font-size:10px; line-height:10px;\">&nbsp;</td></tr></table></td></tr></table></center></body></html>";
    private final static String plantillaBoton = "<table width=\"200\" height=\"44\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#00B185\" style=\"border-radius:10px; margin-top: 5px;\"><tr><td align=\"center\" valign=\"middle\" height=\"44\" style=\"font-family: Arial, sans-serif; font-size:18px; font-weight:bold;\"><a href=\"%2$s\" target=\"_blank\" style=\"font-family: Arial, sans-serif; color:#ffffff; display: inline-block; text-decoration: none; line-height:44px; width:200px; font-weight:bold;\">%1$s</a></td></tr></table>";
    private final static String plantillaCodigo = "<table width=\"150px\" height=\"44\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F5F5F5\" style=\"margin-top: 5px;\"><tr><td align=\"center\" valign=\"middle\" height=\"44\" style=\"border:1px solid #D6D6D6;\"><h2 style=\"margin:0; padding:0;font-family: Arial, sans-serif; font-weight: normal; color: #5D5D5D; font-size: 24px;\">%1$s</h2></td></tr></table>";
    private final static Logger logger = LoggerFactory.getLogger(CorreosService.class);
    @Value("${conf.base_url}")
    private String baseUrl;
    @Value("${conf.duracion_solicitud_restablecimiento}")
    private int duracionRestablecimiento;
    @Value("${conf.correo_recursos_materiales}")
    private String correoRecursosMateriales;
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private IncidenciaRepository incidenciaRepository;
    @Autowired
    private AspectoRepository aspectoRepository;
    @Autowired
    private ResponsableRepository responsableRepository;

    // 5.1 Enviar correo por nuevo reporte de incidencia
    @Transactional(readOnly = true)
    public void enviarCorreoPorNuevaIncidencia(Incidencia incidencia) {
        try {
            incidencia = incidenciaRepository.getById(incidencia.getId());

            List<String> correosResponsables = new ArrayList<>();
            for (Responsable responsable : responsableRepository.findAllByAspecto_IdAndUsuario_ActivoIsTrue(incidencia.getAspecto().getId())) {
                correosResponsables.add(responsable.getUsuario().getCorreo());
            }

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("siriaci.utez@gmail.com", "SIRIACI UTEZ");
            helper.setTo(correosResponsables.toArray(new String[correosResponsables.size()]));
            helper.setCc(correoRecursosMateriales);
            helper.setSubject("¡Nueva incidencia ambiental!");
            String boton = String.format(plantillaBoton, "Ir a la incidencia", baseUrl + "/incidencias/" + FriendlyId.toFriendlyId(incidencia.getCodigo()));
            String texto = String.format(plantillaCorreo, "Hola, se ha reportado una nueva incidencia ambiental en tu aspecto:", "La incidencia <b>" + incidencia.getDescripcion() + "</b> fue reportada por <b>" + incidencia.getUsuario().getNombre() + " " + incidencia.getUsuario().getApellido1() + (incidencia.getUsuario().getApellido2() != null ? (" " + incidencia.getUsuario().getApellido2()) : "") + "</b> el " + Formateador.getFecha(incidencia.getTiempoIncidencia()) + " y asignada al aspecto ambiental <b>" + incidencia.getAspecto().getNombre() + "</b>. Puedes consultar más detalles en la aplicación web.", boton);
            helper.setText(texto, true);
            emailSender.send(message);
        } catch (Exception e) {
            logger.error("Error en método enviarCorreoPorNuevaIncidencia: " + e.getMessage());
        }
    }

    // 5.2 Enviar correo por reasignación de reporte de incidencia
    @Transactional(readOnly = true)
    public void enviarCorreoPorReasignacion(Incidencia incidencia, Byte idAspectoAnterior, Byte idAspectoNuevo) {
        try {
            incidencia = incidenciaRepository.getById(incidencia.getId());
            Aspecto aspectoAnterior = aspectoRepository.getById(idAspectoAnterior);
            Aspecto aspectoNuevo = aspectoRepository.getById(idAspectoNuevo);

            List<String> correosResponsablesAnteriores = new ArrayList<>();
            for (Responsable responsable : responsableRepository.findAllByAspecto_IdAndUsuario_ActivoIsTrue(aspectoAnterior.getId())) {
                correosResponsablesAnteriores.add(responsable.getUsuario().getCorreo());
            }

            List<String> correosResponsablesNuevos = new ArrayList<>();
            for (Responsable responsable : responsableRepository.findAllByAspecto_IdAndUsuario_ActivoIsTrue(aspectoNuevo.getId())) {
                correosResponsablesNuevos.add(responsable.getUsuario().getCorreo());
            }

            // Correo para los responsables del aspecto anterior
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("siriaci.utez@gmail.com", "SIRIACI UTEZ");
            helper.setTo(correosResponsablesAnteriores.toArray(new String[correosResponsablesAnteriores.size()]));
            helper.setCc(correoRecursosMateriales);
            helper.setSubject("Reasignación de incidencia ambiental");
            String boton = String.format(plantillaBoton, "Ver incidencias", baseUrl + "incidencias");
            String texto = String.format(plantillaCorreo, "Hola, se ha removido una incidencia ambiental de tu aspecto:", "La incidencia <b>" + incidencia.getDescripcion() + "</b> fue reportada por <b>" + incidencia.getUsuario().getNombre() + " " + incidencia.getUsuario().getApellido1() + (incidencia.getUsuario().getApellido2() != null ? (" " + incidencia.getUsuario().getApellido2()) : "") + "</b> el " + Formateador.getFecha(incidencia.getTiempoIncidencia()) + " y asignada anteriormente al aspecto ambiental <b>" + aspectoAnterior.getNombre() + "</b>, el " + Formateador.getFecha(LocalDateTime.now()) + " fue modificada y reasignada a <b>" + aspectoNuevo.getNombre() + "</b>, por lo que dejará de ser visible para los responsables del aspecto original.", boton);
            helper.setText(texto, true);
            emailSender.send(message);

            // Correo para los responsables del nuevo aspecto
            message = emailSender.createMimeMessage();
            helper = new MimeMessageHelper(message);
            helper.setFrom("siriaci.utez@gmail.com", "SIRIACI UTEZ");
            helper.setTo(correosResponsablesNuevos.toArray(new String[correosResponsablesNuevos.size()]));
            helper.setCc(correoRecursosMateriales);
            helper.setSubject("Reasignación de incidencia ambiental");
            boton = String.format(plantillaBoton, "Ir a la incidencia", baseUrl + "/incidencias/" + incidencia.getCodigo());
            texto = String.format(plantillaCorreo, "Hola, se ha reasignado una incidencia ambiental a tu aspecto:", "La incidencia <b>" + incidencia.getDescripcion() + "</b> fue reportada por <b>" + incidencia.getUsuario().getNombre() + " " + incidencia.getUsuario().getApellido1() + (incidencia.getUsuario().getApellido2() != null ? (" " + incidencia.getUsuario().getApellido2()) : "") + "</b> el " + Formateador.getFecha(incidencia.getTiempoIncidencia()) + " y asignada anteriormente al aspecto ambiental <b>" + aspectoAnterior.getNombre() + "</b>, el " + Formateador.getFecha(LocalDateTime.now()) + " fue modificada y reasignada a <b>" + aspectoNuevo.getNombre() + "</b>, por lo que deberá ser atendida por los responsables de este aspecto. Puedes consultar más detalles en la aplicación web.", boton);
            helper.setText(texto, true);
            emailSender.send(message);
        } catch (Exception e) {
            logger.error("Error en método enviarCorreoPorReasignacion: " + e.getMessage());
        }
    }

    // 5.3 Enviar correo de verificación de cuenta
    @Transactional(readOnly = true)
    public void enviarCorreoPorRegistro(Usuario usuario, NoVerificado noVerificado) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("siriaci.utez@gmail.com", "SIRIACI UTEZ");
            helper.setTo(usuario.getCorreo());
            helper.setSubject("Completa tu registro");
            String boton = String.format(plantillaBoton, "Verificar cuenta", baseUrl + "/verificar/" + usuario.getCorreo().replace("@", "%40") + "/" + FriendlyId.toFriendlyId(noVerificado.getCodigo()));
            String texto = String.format(plantillaCorreo, "Hola, " + usuario.getNombre() + ":", "Bienvenid@ a SIRIACI, se registró esta dirección de correo en nuestro sistema a nombre de <b>" + usuario.getNombre() + " " + usuario.getApellido1() + (usuario.getApellido2() != null ? (" " + usuario.getApellido2()) : "") + "</b>. Utiliza el botón de abajo para confirmar la dirección y activar la cuenta.<br><br>Si no reconoces esta actividad, ignora este correo.", boton);
            helper.setText(texto, true);
            emailSender.send(message);
        } catch (Exception e) {
            logger.error("Error en método enviarCorreoPorRegistro: " + e.getMessage());
        }
    }

    // 5.3 Enviar correo de verificación de cuenta
    @Transactional(readOnly = true)
    public void enviarCorreoPorAutorregistro(Usuario usuario, NoVerificado noVerificado) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("siriaci.utez@gmail.com", "SIRIACI UTEZ");
            helper.setTo(usuario.getCorreo());
            helper.setSubject("Completa tu registro");
            String boton = String.format(plantillaBoton, "Verificar cuenta", baseUrl + "hola?id=" + FriendlyId.toFriendlyId(noVerificado.getCodigo()));
            String texto = String.format(plantillaCorreo, "Hola, " + usuario.getNombre() + ":", "Bienvenid@ a SIRIACI, registraste esta dirección de correo en nuestro sistema a nombre de <b>" + usuario.getNombre() + " " + usuario.getApellido1() + (usuario.getApellido2() != null ? (" " + usuario.getApellido2()) : "") + "</b>. Utiliza el botón de abajo para confirmar la dirección y activar la cuenta.<br><br>Si no has sido tú, ignora este correo.", boton);
            helper.setText(texto, true);
            emailSender.send(message);
        } catch (Exception e) {
            logger.error("Error en método enviarCorreoPorAutorregistro: " + e.getMessage());
        }
    }

    // 5.4Enviar correo de restablecimiento de contraseña
    @Transactional(readOnly = true)
    public void enviarCorreoPorRestablecimiento(Usuario usuario, SolicitudRestablecimiento solicitudRestablecimiento) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("siriaci.utez@gmail.com", "SIRIACI UTEZ");
            helper.setTo(usuario.getCorreo());
            helper.setSubject("Restablecimiento de contraseña");
            String codigo = String.format(plantillaCodigo, solicitudRestablecimiento.getCodigo());
            String texto = String.format(plantillaCorreo, "Hola, " + usuario.getNombre() + ":", "Hemos recibido una solicitud para restablecer tu contraseña. Utiliza el código al final de este correo para continuar con el proceso, solo será válido durante los siguientes <b>" + duracionRestablecimiento + " minutos</b>.<br><br>Si no has sido tú, ignora este correo." , codigo);
            helper.setText(texto, true);
            emailSender.send(message);
        } catch (Exception e) {
            logger.error("Error en método enviarCorreoPorRestablecimiento: " + e.getMessage());
        }
    }
}
