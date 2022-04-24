package mx.edu.utez.SIRIACI_servicio.controller.NotificacionesYMensajes;

import io.github.jav.exposerversdk.*;
import mx.edu.utez.SIRIACI_servicio.model.aspecto.Aspecto;
import mx.edu.utez.SIRIACI_servicio.model.aspecto.AspectoRepository;
import mx.edu.utez.SIRIACI_servicio.model.dispositivoMovil.DispositivoMovil;
import mx.edu.utez.SIRIACI_servicio.model.dispositivoMovil.DispositivoMovilRepository;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.IncidenciaRepository;
import mx.edu.utez.SIRIACI_servicio.model.responsable.Responsable;
import mx.edu.utez.SIRIACI_servicio.model.responsable.ResponsableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotificacionesService {
    private final static Logger logger = LoggerFactory.getLogger(NotificacionesService.class);
    @Autowired
    IncidenciaRepository incidenciaRepository;
    @Autowired
    ResponsableRepository responsableRepository;
    @Autowired
    AspectoRepository aspectoRepository;
    @Autowired
    DispositivoMovilRepository dispositivoMovilRepository;
    @Value("${jwt.expiration}")
    private int duracionSesion;

    @Transactional(rollbackFor = {SQLException.class})
    public void suscribirse(DispositivoMovil dispositivoMovil) {
        try {
            Optional<DispositivoMovil> resultado = dispositivoMovilRepository.findByToken(dispositivoMovil.getToken());
            if (resultado.isPresent()) resultado.get().reemplazar(dispositivoMovil);
            else dispositivoMovilRepository.save(dispositivoMovil);
        } catch (Exception e) {
            logger.error("Error en método suscribirse: " + e.getMessage());
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public void desuscribirse(String token) {
        Optional<DispositivoMovil> resultado = dispositivoMovilRepository.findByToken(token);
        if (resultado.isPresent()) dispositivoMovilRepository.delete(resultado.get());
        else logger.error("Suscribirse: Token inexistente");
    }

    @Transactional(readOnly = true)
    public void enviarNotificacionPorNuevaIncidencia(Incidencia incidencia) {
        try {
            incidencia = incidenciaRepository.getById(incidencia.getId());

            List<String> tokensDispositivosResponsables = new ArrayList<>();
            for (Responsable responsable : responsableRepository.findAllByAspecto_IdAndUsuario_ActivoIsTrue(incidencia.getAspecto().getId())) {
                for (DispositivoMovil dispositivoMovil : responsable.getUsuario().getDispositivosMoviles()) {
                    if (LocalDateTime.now().until(dispositivoMovil.getTiempoSuscripcion().plus(duracionSesion, ChronoUnit.SECONDS), ChronoUnit.SECONDS) < 0)
                        desuscribirse(dispositivoMovil.getToken());
                    else tokensDispositivosResponsables.add(dispositivoMovil.getToken());
                }
            }

            List<ExpoPushMessage> mensajes = new ArrayList<>();

            ExpoPushMessage expoPushMessage = new ExpoPushMessage();
            for (String token : tokensDispositivosResponsables) {
                if (!PushClient.isExponentPushToken(token))
                    logger.error("El token: " + token + " no es un token válido.");
                else expoPushMessage.getTo().add(token);
            }
            expoPushMessage.setTitle("Nueva incidencia ambiental");
            expoPushMessage.setBody(incidencia.getDescripcion() + ".\nMás detalles en la aplicación web.");
            mensajes.add(expoPushMessage);

            new PushClient().sendPushNotificationsAsync(mensajes);
        } catch (Exception e) {
            logger.error("Error en método enviarNotificacionPorNuevaIncidencia: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public void enviarNotificacionPorReasignacion(Incidencia incidencia, Byte idAspectoAnterior, Byte idAspectoNuevo) {
        try {
            incidencia = incidenciaRepository.getById(incidencia.getId());
            Aspecto aspectoAnterior = aspectoRepository.getById(idAspectoAnterior);
            Aspecto aspectoNuevo = aspectoRepository.getById(idAspectoNuevo);

            List<String> tokensDispositivosResponsablesAnteriores = new ArrayList<>();
            for (Responsable responsable : responsableRepository.findAllByAspecto_IdAndUsuario_ActivoIsTrue(aspectoAnterior.getId())) {
                for (DispositivoMovil dispositivoMovil : responsable.getUsuario().getDispositivosMoviles()) {
                    System.out.println(responsable.getUsuario().getCorreo());
                    System.out.println(dispositivoMovil.getToken());
                    if (LocalDateTime.now().until(dispositivoMovil.getTiempoSuscripcion().plus(duracionSesion, ChronoUnit.SECONDS), ChronoUnit.SECONDS) < 0)
                        desuscribirse(dispositivoMovil.getToken());
                    else tokensDispositivosResponsablesAnteriores.add(dispositivoMovil.getToken());
                }
            }

            List<String> tokensDispositivosResponsablesNuevos = new ArrayList<>();
            for (Responsable responsable : responsableRepository.findAllByAspecto_IdAndUsuario_ActivoIsTrue(aspectoNuevo.getId())) {
                for (DispositivoMovil dispositivoMovil : responsable.getUsuario().getDispositivosMoviles()) {
                    if (LocalDateTime.now().until(dispositivoMovil.getTiempoSuscripcion().plus(duracionSesion, ChronoUnit.SECONDS), ChronoUnit.SECONDS) < 0)
                        desuscribirse(dispositivoMovil.getToken());
                    else tokensDispositivosResponsablesNuevos.add(dispositivoMovil.getToken());
                }
            }

            List<ExpoPushMessage> mensajes = new ArrayList<>();

            ExpoPushMessage expoPushMessage = new ExpoPushMessage();
            for (String token : tokensDispositivosResponsablesAnteriores) {
                if (!PushClient.isExponentPushToken(token))
                    logger.error("El token: " + token + " no es un token válido.");
                else expoPushMessage.getTo().add(token);
            }
            expoPushMessage.setTitle("Incidencia ambiental removida de tu aspecto");
            expoPushMessage.setBody("\"" + incidencia.getDescripcion() + "\"\nConsulta las incidencias de tu aspecto en la aplicación web.");
            mensajes.add(expoPushMessage);

            new PushClient().sendPushNotificationsAsync(mensajes);
            mensajes = new ArrayList<>();

            expoPushMessage = new ExpoPushMessage();
            for (String token : tokensDispositivosResponsablesNuevos) {
                if (!PushClient.isExponentPushToken(token))
                    logger.error("El token: " + token + " no es un token válido.");
                else expoPushMessage.getTo().add(token);
            }
            expoPushMessage.setTitle("Incidencia ambiental reasignada a tu aspecto");
            expoPushMessage.setBody("\"" + incidencia.getDescripcion() + "\"\nMás detalles en la aplicación web.");
            mensajes.add(expoPushMessage);

            new PushClient().sendPushNotificationsAsync(mensajes);
        } catch (Exception e) {
            logger.error("Error en método enviarNotificacionPorReasignacion: " + e.getMessage());
        }
    }
}