package fr.aureliancnx.betterbungee.rabbit;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import fr.aureliancnx.betterbungee.BetterBungeePlugin;
import fr.aureliancnx.betterbungee.packet.ListenPacket;
import fr.aureliancnx.betterbungee.rabbit.packet.RabbitPacketType;
import fr.aureliancnx.betterbungee.rabbit.service.RabbitService;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author aureliancnx
 */
public class RabbitListener {

    private final RabbitService                    service;
    private final RabbitPacketType                 type;
    private final String                           queueName;
    private final Class<? extends ListenPacket>    clazz;

    public RabbitListener(RabbitService service, final ListenPacket listenPacket) {
        this.service = service;
        this.type = listenPacket.getType();
        this.queueName = listenPacket.getQueueName();
        if (this.queueName != null) {
            try {
                this.startQueue();
            } catch (Exception error) {
                error.printStackTrace();
            }
        }
        this.clazz = listenPacket.getClass();
    }

    private void startQueue() throws Exception {
        if (type == RabbitPacketType.PUBLISH) {
            service.getChannel().exchangeDeclare(queueName, "fanout");
            String queueName = service.getChannel().queueDeclare().getQueue();
            service.getChannel().queueBind(queueName, this.queueName, "");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                consume(consumerTag, delivery);
            };
            service.getChannel().basicConsume(queueName, true, deliverCallback, consumerTag -> { });
            return;
        }
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-message-ttl", 60000);
        service.getChannel().queueDeclare(queueName, false, false, false, args);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            consume(consumerTag, delivery);
        };
        service.getChannel().basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }

    private void consume(final String consumerTag, final Delivery delivery) {
        try {
            byte[] data = delivery.getBody();
            ByteArrayDataInput input = ByteStreams.newDataInput(data);
            Constructor<?> emptyConstructor = clazz.getConstructor();
            ListenPacket packetObj = (ListenPacket) emptyConstructor.newInstance();
            packetObj.fromBytes(input);
            if (BetterBungeePlugin.getInstance().isDebugMode()) {
                BetterBungeePlugin.getInstance().getLogger().info("[DEBUG] Received packet: " + queueName
                        + " (" + type + ") -> size " + data.length);
            }
            packetObj.onReceive();
        }catch(ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

}
