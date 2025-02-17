package archetype;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class Produtor {

    private static final String NOME_EXCHANGE = "exchange_direct";
    private static final String[] ROUTING_KEYS = {"info"};

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(NOME_EXCHANGE, "direct");

            String nomeCompleto = "Marcos Antonio Nascimento de Lima";
            String mensagem = "Olá, meu nome é " + nomeCompleto + "!";

            for (String routingKey : ROUTING_KEYS) {
                channel.basicPublish(NOME_EXCHANGE, routingKey, null, mensagem.getBytes());
                System.out.println("Enviei mensagem com routing key '" + routingKey + "': " + mensagem);
            }
        }
    }
}