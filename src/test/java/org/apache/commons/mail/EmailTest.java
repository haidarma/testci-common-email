package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmailTest {
	
	private static final String[] TEST_EMAILS = {"ab@a.com", "hi@hi.org", "abc@cd.com"};
	
	private static final String TEST_EMAIL = "a@b.com";
	
	private static final String TEST_HEADER = "1";
	
	private static final String TEST_NAME = "Jeff";
	
	private EmailConcrete email;
	
	@Before 
	public void setUpEmailTest() throws Exception {
		
		email = new EmailConcrete();
		
	}
	
	@After
	public void tearDownEmailTest() throws Exception{
		
	}

	@Test
	public void testAddBcc() throws Exception{
		
		email.addBcc(TEST_EMAILS);
		
		assertEquals(3, email.getBccAddresses().size());
	}
	
	@Test
	public void testAddCc() throws Exception{
		
		email.addCc(TEST_EMAIL);
		
		assertFalse(email.ccList.isEmpty());
	}
	
	@Test
	public void testAddReplyTo() throws Exception{
		email.addReplyTo(TEST_EMAIL, TEST_NAME);
		
		assertNotSame(TEST_EMAIL, TEST_NAME);
		
	}

	@Test
	public void testAddHeader() throws Exception{
		email.addHeader(TEST_NAME, TEST_HEADER);
		
		assertFalse(email.headers.isEmpty());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddHeaderNoName() throws Exception{
		String name = null;
		email.addHeader(name, TEST_HEADER);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddHeaderNoValue() {
		String value = null;
		email.addHeader(TEST_NAME, value);
	}

	@Test
	public void testbuildMimeMessage() throws EmailException {
		email.setHostName("localhost");
		email.setSmtpPort(1234);
		email.addTo(TEST_EMAILS);
		email.addBcc(TEST_EMAILS);
		email.addCc(TEST_EMAIL);
		email.addReplyTo("hi@cc.com");
		email.setFrom("d@d.com");
		email.setSubject("test mail");
		email.addHeader("some", "thing");
		email.setContent("test", "text/plain");
		email.setCharset("ISO-8859-1");
		email.setMsg("Hello");
		email.buildMimeMessage();
		
	}
	
	@Test
	public void testbuildMimeMessageNull() throws EmailException {
		email.setHostName("localhost");
		email.setSubject("test mail");
		email.addTo(TEST_EMAILS);
		email.addBcc(TEST_EMAILS);
		email.addCc(TEST_EMAIL);
		email.addReplyTo("hi@cc.com");
		email.setFrom("d@d.com");
		email.addHeader("some", "thing");
		email.setContent("test", "text/plain");
		email.charset = null;
		email.setMsg("Hello");
		email.buildMimeMessage();
	}
	
	@Test
	public void testgetHostNameWithNull()  {
		email.setHostName(null);
		assertEquals(null, email.getHostName());
	}
	
	@Test
	public void testGetSetHostNameWithSession()  {
		Properties properties = new Properties();
		
		Session session = Session.getDefaultInstance(properties, null);
		properties.put(EmailConstants.MAIL_HOST, "smtp.gmail.com");
		email.setMailSession(session);
		assertEquals("smtp.gmail.com", email.getHostName());
	}
	
	@Test
	public void testgetHostName()  {
		email.setHostName("localhost");
		String hostname = email.getHostName();
		assertEquals("localhost", hostname);
		
	}
	
	@Test
	public void testgetMailSession() throws EmailException {
		Properties properties = new Properties(System.getProperties());
		   
        Session session = Session.getInstance(properties, null);
        properties.getProperty(EmailConstants.MAIL_SMTP_SOCKET_FACTORY_FALLBACK, "false");
        properties.getProperty(EmailConstants.MAIL_SMTP_FROM, "H@c.com");
        email.setMailSession(session);
        assertEquals(session, this.email.getMailSession());        

	}
	
	@Test(expected = EmailException.class)
	public void testgetMailSessionNull() throws EmailException {
		
		email.setHostName(null);
		email.buildMimeMessage();
		
	}
	
	@Test
	public void testgetSentDate() {
		
		int year = 1;
		int month = 1;
		int min = 60;
		int hrs = 3;
		int date =2 ;
		Date d = new Date(year, month, date, hrs, min);
		
		email.setSentDate(d);
		email.getSentDate();
	}
	
	@Test
	public void testgetSentDateNull() {
		Date date = null;
		this.email.setSentDate(date);
		
	}
	
	@Test
	public void testgetSocketConnectionTimeout() {
		
		email.setSocketConnectionTimeout(60000);
		email.getSocketConnectionTimeout();
	}
	
	@Test
	public void testsetFrom() throws EmailException {
		/* 
		 * This already has 100% coverage from the other junit tests
		 * Specifically the buildMimeMessage tests covers this
		 * But if they didnt this is how you would test this method
		 */
		
		email.setFrom("d@d.com");
	}

}
