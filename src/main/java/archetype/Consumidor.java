package archetype;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Consumidor {

    private static final String NOME_EXCHANGE = "exchange_direct";
    private static final String NOME_FILA = "fila_info";

    public static void main(String[] args) throws Exception {
        System.out.println("Consumidor");

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection conexao = connectionFactory.newConnection();
        Channel canal = conexao.createChannel();

        canal.exchangeDeclare(NOME_EXCHANGE, "direct");

        canal.queueDeclare(NOME_FILA, false, false, false, null);
        canal.queueBind(NOME_FILA, NOME_EXCHANGE, "info");

        // Define o callback para processar as mensagens recebidas
        DeliverCallback callback = (consumerTag, delivery) -> {
            String mensagem = new String(delivery.getBody());
            System.out.println("Recebi a mensagem: " + mensagem);
        };

        // Consome as mensagens da fila
        canal.basicConsume(NOME_FILA, true, callback, consumerTag -> {});

        System.out.println("Aguardando mensagens...");
    }
}