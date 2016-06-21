package com.org.scube.dashboard.util;

import java.io.UnsupportedEncodingException;  
import java.util.Properties;  
import java.util.logging.Level;  
import java.util.logging.Logger;  
import javax.mail.Authenticator;  
import javax.mail.Message;  
import javax.mail.MessagingException;  
import javax.mail.PasswordAuthentication;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeMessage;  
  
public class MailUtil {  
  
/*    private String SMTP_HOST = "smtp.gmail.com";  
    private String FROM_ADDRESS = "yourname@gmail.com";  
    private String PASSWORD = "yourpassword";  
    private String FROM_NAME = "Sameera";*/
	
    private String SMTP_HOST = "smtp.gmail.com";  
    private String FROM_ADDRESS = "vandataconsulting@gmail.com";  
    private String PASSWORD = "nick!@#123";  
    private Integer HOST_PORT = 587;
    
/*    public static final String SMTP_HOST = "smtp.mail.yahoo.com";
    public static final String FROM_ADDRESS = "scubeteam@yahoo.com";
    public static final String PASSWORD = "scubeadmin";
    public static  final Integer HOST_PORT = 587;*/
  
    public boolean sendMail(String[] recipients, String[] bccRecipients, String subject, String message) throws UnsupportedEncodingException {  
        try {  
        	System.out.println("in sendMail try");
            Properties props = new Properties();  
            props.put("mail.smtp.host", SMTP_HOST);  
            props.put("mail.smtp.auth", "true");  
            props.put("mail.debug", "false");  
            props.put("mail.smtp.ssl.enable", "true");  
            
            System.out.println("create Session");
            Session session = Session.getInstance(props, new SocialAuth());  
            Message msg = new MimeMessage(session);  
            System.out.println("create msg");
            InternetAddress from = new InternetAddress(FROM_ADDRESS);  
            msg.setFrom(from);  
            System.out.println("set msg");
            InternetAddress[] toAddresses = new InternetAddress[recipients.length];  
            for (int i = 0; i < recipients.length; i++) {  
                toAddresses[i] = new InternetAddress(recipients[i]);  
            }  
            System.out.println("for loop");
            msg.setRecipients(Message.RecipientType.TO, toAddresses);  
  
            System.out.println("InternetAddress msg");
            InternetAddress[] bccAddresses = new InternetAddress[bccRecipients.length];  
            for (int j = 0; j < bccRecipients.length; j++) {  
                bccAddresses[j] = new InternetAddress(bccRecipients[j]);  
            }  
            msg.setRecipients(Message.RecipientType.BCC, bccAddresses);  
            System.out.println("setRecipients msg");
            msg.setSubject(subject);  
            System.out.println("subject msg");
            msg.setContent(message, "text/html");  
            System.out.println("message msg");
            Transport.send(msg);  
            System.out.println("End");
            return true;  
        } catch (MessagingException ex) {  
            Logger.getLogger(MailUtil.class.getName()).log(Level.SEVERE, null, ex);  
            return false;  
        }  
    }  
  
    class SocialAuth extends Authenticator {  
  
        @Override  
        protected PasswordAuthentication getPasswordAuthentication() {  
  
            return new PasswordAuthentication(FROM_ADDRESS, PASSWORD);  
  
        }  
    }  
}
