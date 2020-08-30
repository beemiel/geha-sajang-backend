package com.incense.gehasajang.util;

import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.CannotSendMailException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MailHandler {
    private JavaMailSender sender;
    private MimeMessage message;
    private MimeMessageHelper messageHelper;

    /**
     * 생성자
     *
     * @param mailSender
     * @throws MessagingException
     */
    public MailHandler(JavaMailSender mailSender) throws MessagingException {
        this.sender = mailSender;
        message = mailSender.createMimeMessage();
        messageHelper = new MimeMessageHelper(message, true, "UTF-8");
    }

    /**
     * 보내는 사람 이메일
     *
     * @param fromAddress
     * @throws MessagingException
     */
    public void setFrom(String fromAddress) throws MessagingException {
        messageHelper.setFrom(fromAddress);
    }

    /**
     * 받는 사람 이메일
     *
     * @param email
     * @throws MessagingException
     */
    public void setTo(String email) throws MessagingException {
        messageHelper.setTo(email);
    }

    /**
     * 제목
     *
     * @param subject
     * @throws MessagingException
     */
    public void setSubject(String subject) throws MessagingException {
        messageHelper.setSubject(subject);
    }

    /**
     * 메일 내용
     *
     * @param text
     * @param useHtml
     * @throws MessagingException
     */
    public void setText(String text, boolean useHtml) throws MessagingException {
        messageHelper.setText(text, useHtml);
    }

    /**
     * 첨부 파일
     *
     * @param displayFileName
     * @param pathToAttachment
     * @throws MessagingException
     * @throws IOException
     */
    public void setAttach(String displayFileName, String pathToAttachment) throws MessagingException, IOException {
        File file = new ClassPathResource(pathToAttachment).getFile();
        FileSystemResource fsr = new FileSystemResource(file);

        messageHelper.addAttachment(displayFileName, fsr);
    }

    /**
     * 이미지 삽입
     *
     * @param contentId
     * @param pathToInline
     * @throws MessagingException
     * @throws IOException
     */
    public void setInline(String contentId, String pathToInline) throws MessagingException, IOException {
        InputStream inputStream = new ClassPathResource(pathToInline).getInputStream();
        File file = File.createTempFile("logoblackTemp", ".png");
        file.deleteOnExit();
        try {
            FileUtils.copyInputStreamToFile(inputStream, file);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

        FileSystemResource fsr = new FileSystemResource(file);

        messageHelper.addInline(contentId, fsr);
    }

    /**
     * 메일 전송
     */
    public void send() {
        try {
            sender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CannotSendMailException(ErrorCode.CANNOT_SEND_MAIL);
        }
    }

}
