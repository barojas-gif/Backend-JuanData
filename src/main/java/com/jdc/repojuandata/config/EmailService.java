package com.jdc.repojuandata.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreo(String destinatario, String asunto, String cuerpoHtml) {
        try {
            System.out.println(" Enviando correo a: " + destinatario);

            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(cuerpoHtml, true); // true = HTML

            mailSender.send(mensaje);
            System.out.println(" Correo enviado correctamente.");
        } catch (MessagingException e) {
            System.out.println(" Error al enviar correo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
