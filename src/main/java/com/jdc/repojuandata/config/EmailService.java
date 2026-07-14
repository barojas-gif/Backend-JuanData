package com.jdc.repojuandata.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remitente;

    public void enviarCorreo(String destinatario, String asunto, String cuerpoHtml) {
        try {
            System.out.println(" Enviando correo a: " + destinatario);

            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setFrom(remitente);
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(cuerpoHtml, true);

            mailSender.send(mensaje);
            System.out.println(" Correo enviado correctamente.");
        } catch (MessagingException | MailException e) {
            System.out.println(" Error al enviar correo: " + e.getMessage());
            throw new EmailDeliveryException("No se pudo enviar el correo. Intenta más tarde.", e);
        }
    }
}
