package srmt.java.common;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class SendEmail {
	public void SentMail(String reMail) {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo("1149676636@qq.com");
		mailMessage.setFrom("864422716@qq.com");
		mailMessage.setSubject(" Hello world via spring mail sender ");
		mailMessage.setText(" Hello world via spring mail sender ");

		senderImpl.setUsername("864422716@qq.com"); // �����Լ������,����username
		senderImpl.setPassword(Constants.MAIL_PASSWORD); // �����Լ������, ����password

		Properties prop = System.getProperties();
		prop.put(" mail.smtp.auth", "true"); // �����������Ϊtrue���÷�����������֤,��֤�û����������Ƿ���ȷ
		prop.put(" mail.smtp.timeout", "25000");
		senderImpl.setPort(465);
		senderImpl.setHost("smtp.qq.com");
		senderImpl.setJavaMailProperties(prop);
		senderImpl.setProtocol("smtp");
		// �����ʼ�
		senderImpl.send(mailMessage);

		System.out.println(" �ʼ����ͳɹ�.. ");
	}

	public void SentMail2(String reMail) {
		// �ռ��˵�������
		String to = "1149676636@qq.com";

		// �����˵�������
		String from = "864422716@qq.com";

		// ָ�������ʼ�������Ϊ localhost
		String host = "smtp.qq.com"; // QQ �ʼ�������

		// ��ȡϵͳ����
		Properties properties = System.getProperties();

		// �����ʼ�������
		properties.setProperty("mail.smtp.host", host);

		properties.put("mail.smtp.auth", "true");

		properties.put("port", "465");
		// ��ȡĬ��session����
		Session session = Session.getInstance(properties, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("864422716@qq.com", Constants.MAIL_PASSWORD); // �������ʼ��û���������
			}
		});

		try {
			// ����Ĭ�ϵ� MimeMessage ����
			MimeMessage message = new MimeMessage(session);

			// Set From: ͷ��ͷ�ֶ�
			message.setFrom(new InternetAddress(from));

			// Set To: ͷ��ͷ�ֶ�
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: ͷ��ͷ�ֶ�
			message.setSubject("This is the Subject Line!");

			// ������Ϣ��
			message.setText("This is actual message");

			// ������Ϣ
			Transport.send(message);
			System.out.println("Sent message successfully....from w3cschool.cc");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

	}

	public static void main(String args[]) {
		SendEmail SendEmail = new SendEmail();
		SendEmail.SentMail2("");
		System.out.println(" �ʼ����ͳɹ�.. ");

	}

}