package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

public class TesteConsumidor {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
	
		//Conexão com o ActiveMQ
		Context context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory)context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		//Abre a sessão com a nossa fila
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("financeiro"); // Precisa configurar no jndi.properties # register some queues in JNDI using the form
		MessageConsumer consumer = session.createConsumer(fila);
		
		//Obtém os dados
		Message message = consumer.receive();
		System.out.println("Recebendo mensagem: " + message);
		
		new Scanner(System.in).nextLine();
		
		connection.close();
		context.close();
	}	
}
