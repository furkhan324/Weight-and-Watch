/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.furkhan324.weightandsee;

/**
 *
 * @author SrinathGoli
 */

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 * @author Crunchify.com
 * 
 */
 
public class Email1 extends AsyncTask<String, String, String> {
 
	Properties mailServerProperties;
	Session getMailSession;
	MimeMessage generateMailMessage;

	@Override
	protected String doInBackground(String... params) {
		String host = "smtp.gmail.com";
		String address = "weightandseestaff@gmail.com";
		String from = "weightandseestaff4@gmail.com";
		String pass = "EBDBBnB123";
		String to = "furkhan324@gmail.com";
		Multipart multiPart;
		String finalString = "";
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", address);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		Log.i("Check", "done pops");
		Session session = Session.getDefaultInstance(props, null);
		DataHandler handler = new DataHandler(new ByteArrayDataSource(finalString.getBytes(), "text/plain"));
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		try {
			message.setDataHandler(handler);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		Log.i("Check", "done sessions");
		multiPart = new MimeMultipart();
		MimeBodyPart imagePart = new MimeBodyPart();

		try {
			imagePart.attachFile( Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/rChart.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		try {
			multiPart.addBodyPart(imagePart);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		InternetAddress toAddress;
		try {
			toAddress = new InternetAddress(to);
			message.addRecipient(Message.RecipientType.TO, toAddress);
			Log.i("Check", "added recipient");
			message.setSubject("Send Auto-Mail");
			message.setContent(multiPart);
			message.setText("Demo For Sending Mail in Android Automatically");
			Log.i("check", "transport");
			Transport transport = session.getTransport("smtp");
			Log.i("check", "connecting");
			transport.connect(host, address, pass);
			Log.i("check", "wana send");
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			Log.i("check", "sent");
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}


		return "";
	}
/*
 
	public static void generateAndSendEmail(String recipients) throws AddressException, MessagingException, IOException {
 
		// Step1
		System.out.println("\n 1st ===> setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		System.out.println("Mail Server Properties have been setup successfully..");
 
		// Step2
		System.out.println("\n\n 2nd ===> get Mail Session..");
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipients));

		generateMailMessage.setSubject("Monthly Report - Weight and See");
		String emailBody = "Your monthly report from Weight and See: " + "<br><br> Regards, <br>Weight and See Staff";
		generateMailMessage.setText("something about sending email");
		System.out.println("Mail Session has been created successfully..");
 
		// Step3
		System.out.println("\n\n 3rd ===> Get Session and Send mail");
		Transport transport = getMailSession.getTransport("smtp");
                
                

		// Enter your correct gmail UserID and Password
		// if you have 2FA enabled then provide App Specific Password
		transport.connect("smtp.gmail.com", "weightandseestaff@gmail.com", "EBDBBnB123");
		transport.sendMessage(generateMailMessage   , generateMailMessage.getAllRecipients());
		transport.close();
	}*/


}
