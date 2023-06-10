package com.example.learnpython.mail;

import com.example.learnpython.auth.RegisterRequest;
import com.example.learnpython.user.model.dto.ResetPasswordRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender mailSender;
    private final MailTemplateRepository mailTemplateRepository;


    /***
     * Temporary method to initialize mail templates
     */
    @Bean
    public void init() {

        if (mailTemplateRepository.findByType(MailType.REGISTER) == null) {
            mailTemplateRepository.save(
                    MailTemplate
                            .builder()
                            .body(RegisterConfirmationEmail.MAIL_BODY)
                            .subject(RegisterConfirmationEmail.SUBJECT)
                            .type(MailType.REGISTER)
                            .build());
        }

        if (mailTemplateRepository.findByType(MailType.RESET_PASSWORD) == null) {
            mailTemplateRepository.save(
                    MailTemplate
                            .builder()
                            .body(ResetPasswordEmail.MAIL_BODY)
                            .subject(ResetPasswordEmail.SUBJECT)
                            .type(MailType.RESET_PASSWORD)
                            .build());
        }
    }

    @Async
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Override
    public void sendRegisterEmail(final RegisterRequest request) {
        final MailTemplate template = mailTemplateRepository.findByType(MailType.REGISTER);
        try {
            log.info("Sending email to {}", request.getEmail());
            template.setBody(template.getBody().replace("${USERNAME}", request.getNickname()));
            sendSimpleEmail(request.getEmail(), template.getBody(), template.getSubject());
        } catch (final MessagingException e) {
            log.error("Error while sending email", e);
        }
    }

    @Async
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Override
    public void sendResetPasswordEmail(final ResetPasswordRequest request) {
        final MailTemplate template = mailTemplateRepository.findByType(MailType.RESET_PASSWORD);
        try {
            log.info("Sending email to {}", request.email());
            template.setBody(template.getBody()
                    .replace("${USERNAME}", request.email())
                    .replace("${TOKEN}", request.token()));
            sendSimpleEmail(request.email(), template.getBody(), template.getSubject());
        } catch (final MessagingException e) {
            log.error("Error while sending email", e);
        }
    }

    private void sendSimpleEmail(final String toEmail,
                                 final String body,
                                 final String subject) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(System.getenv("EMAIL_ADDRESS"));
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body, true);
        mimeMessageHelper.setSubject(subject);

        mailSender.send(mimeMessage);
        log.info("Mail successfully send to {}", toEmail);
    }

    private void sendEmailWithAttachment(String toEmail,
                                        String body,
                                        String subject,
                                        String attachment) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(System.getenv("EMAIL_ADDRESS"));
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystem
                = new FileSystemResource(new File(attachment));

        mimeMessageHelper.addAttachment(Objects.requireNonNull(fileSystem.getFilename()), fileSystem);

        mailSender.send(mimeMessage);
        log.info("Mail Send to {}", toEmail);

    }
}