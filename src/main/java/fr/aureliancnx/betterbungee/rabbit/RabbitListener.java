package fr.aureliancnx.betterbungee.rabbit;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.rabbitmq.client.DeliverCallback;
import fr.aureliancnx.betterbungee.BetterBungeePlugin;
import fr.aureliancnx.betterbungee.packet.Packet;
import fr.aureliancnx.betterbungee.rabbit.packet.RabbitPacketType;
import fr.aureliancnx.betterbungee.rabbit.service.RabbitService;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author aureliancnx
 */
public abstract class RabbitListener<T extends Packet> {

    private final RabbitService service;
    private final RabbitPacketType type;
    private final String                    queueName;
    private final Class<T>                  clazz;

    public RabbitListener(RabbitService service, Class<T> clazz) {
        String queueName;
        RabbitPacketType type;
        this.service = service;
        this.clazz = clazz;

        try {
            Constructor<?> emptyConstructor = clazz.getConstructor();
            Packet packet = (Packet) emptyConstructor.newInstance();
            type = packet.getType();
            queueName = packet.getQueueName();
        }catch(Exception error) {
            error.printStackTrace();
            type = null;
            queueName = null;
        }
        this.queueName = queueName;
        this.type = type;

        if (this.queueName != null) {
            try {
                this.startQueue();
            } catch (Exception error) {
                error.printStackTrace();
            }
        }
    }

    private void startQueue() throws Exception {
        if (type == RabbitPacketType.PUBLISH) {
            service.getChannel().exchangeDeclare(queueName, "fanout");
            String queueName = service.getChannel().queueDeclare().getQueue();
            service.getChannel().queueBind(queueName, this.queueName, "");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                try {
                    byte[] data = delivery.getBody();
                    ByteArrayDataInput input = ByteStreams.newDataInput(data);
                    Constructor<?> emptyConstructor = clazz.getConstructor();
                    T packet = (T) emptyConstructor.newInstance();
                    packet.fromBytes(input);
                    if (BetterBungeePlugin.getInstance().isDebugMode()) {
                        BetterBungeePlugin.getInstance().getLogger().info("[DEBUG] Received packet: " + queueName
                                + " (" + type + ") -> size " + data.length);
                    }
                    onReceive(packet);
                }catch (Exception err) {
                    err.printStackTrace();
                }
            };
            service.getChannel().basicConsume(queueName, true, deliverCallback, consumerTag -> { });
            return;
        }
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-message-ttl", 60000);
        service.getChannel().queueDeclare(queueName, false, false, false, args);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            try {
                byte[] data = delivery.getBody();
                ByteArrayDataInput input = ByteStreams.newDataInput(data);
                Constructor<?> emptyConstructor = clazz.getConstructor();
                T packet = (T) emptyConstructor.newInstance();
                if (BetterBungeePlugin.getInstance().isDebugMode()) {
                    BetterBungeePlugin.getInstance().getLogger().info("[DEBUG] Received packet: " + queueName
                            + " (" + type + ") -> size " + data.length);
                }
                packet.fromBytes(input);
                onReceive(packet);
            }catch (Exception err) {
                err.printStackTrace();
            }
        };
        service.getChannel().basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }

    public abstract void onReceive(T packet);

}
