package com.company;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

public class SendEmail
{
	
		//String message = "Happy Coding  ";
		String subject = "Receipt from the shop";
		//String to = "dertraurigkeitmorder@yandex.ru";
		String from = "javaisonelove@gmail.com";
	

public void sendAttachment(String message, String to) {
	System.out.println("preparing to send message ...");
	//Variable for gmail
	String host="smtp.gmail.com";
	
	//get the system properties
	Properties properties = System.getProperties();
	System.out.println("PROPERTIES "+properties);
	
	//setting important information to properties object
	
	//host set
	properties.put("mail.smtp.host", host);
	properties.put("mail.smtp.port","465");
	properties.put("mail.smtp.ssl.enable","true");
	properties.put("mail.smtp.auth","true");
	
	//Step 1: to get the session object..
	Session session=Session.getInstance(properties, new Authenticator() {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {				
			return new PasswordAuthentication("javaisonelove@gmail.com",                  "wtYfRDSaQ#");
		}	
	});
	
	session.setDebug(true);
	
	//Step 2 : compose the message [text,multi media]
	
	MimeMessage m = new MimeMessage(session);
	try {
	
	//from email
	m.setFrom(from);
	
	//adding recipient to message
	m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	
	//adding subject to message
	m.setSubject(subject);

	
	//attach
	
	//file path
	//String path="C:\\Users\\Оля\\Desktop\\s1200.jpg";
	
	
	MimeMultipart mimeMultipart = new MimeMultipart();
	MimeBodyPart textMime = new MimeBodyPart();//text
	//MimeBodyPart fileMime = new MimeBodyPart();//file
	
	try {
		
		textMime.setText(message); //add text in it
		
		//File file=new File(path);
		//fileMime.attachFile(file);  //add file path in it
		
		mimeMultipart.addBodyPart(textMime);  //add both in its parent
		//mimeMultipart.addBodyPart(fileMime);  //add both in its parent
	
		
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	
	
	m.setContent(mimeMultipart);
	
	
	//send 
	
	//Step 3 : send the message using Transport class
	Transport.send(m);
	
	
	
	}catch (Exception e) {
		e.printStackTrace();
	}
	
	System.out.println("Sent success...................");
	
	
	

}
}
