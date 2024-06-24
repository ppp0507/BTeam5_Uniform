package util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author 朴姻禹
 */
public class SendMail {

	private Properties props = System.getProperties();
	private Session session;
	
	private MimeMessage mimeMessage;

	private String mailSubject;
	private String mailText;
	private String receiverAddress;
	
	public void sendMail() {
		
		//ここでメールのアドレスを設定してください。
		String address = "system.project.team34@kanda-it-school-system.com";
		String password = "6uTJ17VChH";

		// SMTPサーバのアドレスを指定（今回はxserverのSMTPサーバを利用）
		props.put("mail.smtp.host", "sv5215.xserver.jp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.debug", "true");
		
		try {
			
			session = Session.getInstance(
					props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							//メールサーバにログインするメールアドレスとパスワードを設定
							return new PasswordAuthentication(address,
									password);
						}
					});
			
			mimeMessage = new MimeMessage(session);

			// 送信元メールアドレスと送信者名を指定
			mimeMessage.setFrom(
					new InternetAddress(address, "神田ユニフォーム", "iso-2022-jp"));

			// 送信先メールアドレスを指定（ご自分のメールアドレスに変更）
			mimeMessage.setRecipients(Message.RecipientType.TO, receiverAddress);

			// メールのタイトルを指定(変数mailSubject)
			mimeMessage.setSubject(mailSubject, "iso-2022-jp");

			// メールの内容を指定(変数mailText)
			mimeMessage.setText(mailText, "iso-2022-jp");

			// メールの形式を指定
			mimeMessage.setHeader("Content-Type", "text/plain; charset=iso-2022-jp");

			// 送信日付を指定
			mimeMessage.setSentDate(new Date());

			// 送信します
			Transport.send(mimeMessage);

			// 送信成功
			System.out.println("送信に成功しました。");

		} catch (Exception e) {
			
			// e.printStackTrace();
			System.out.println("送信に失敗しました。\n" + e);
			
		}
	}
	
	//引数をメールのタイトル(mailSubject)にするメソッド
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}
	
	//引数をメールの本文(mailText)にするメソッド
	public void setMailText(String mailText) {
		this.mailText = mailText;
	}
	
	//引数をメールの宛先(receiverAddress)にするメソッド
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
}
