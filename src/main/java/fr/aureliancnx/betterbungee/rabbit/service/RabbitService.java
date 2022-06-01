package fr.aureliancnx.betterbungee.rabbit.service;

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import fr.aureliancnx.betterbungee.BetterBungeePlugin;
import fr.aureliancnx.betterbungee.packet.IListenPacket;
import fr.aureliancnx.betterbungee.packet.Packet;
import fr.aureliancnx.betterbungee.rabbit.RabbitCredentials;
import fr.aureliancnx.betterbungee.rabbit.RabbitListener;
import fr.aureliancnx.betterbungee.rabbit.packet.RabbitPacket;
import fr.aureliancnx.betterbungee.rabbit.packet.RabbitPacketType;
import lombok.Getter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

/**
 * @author aureliancnx
 */
@Getter
public class RabbitService {

    private final BetterBungeePlugin    plugin;

    private final Gson                  gson;
    private final RabbitCredentials     credentials;
    private final List<RabbitThread>    workers;
    private final List<IListenPacket>   listeners;
    private Connection                  connection;
    private Channel                     channel;

    public RabbitService(final BetterBungeePlugin plugin, final RabbitCredentials credentials) {
        this.plugin = plugin;
        this.workers = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.credentials = credentials;
        this.gson = new Gson();
        this.initWorkers();
        this.connect();
    }

    private void initWorkers() {
        for (int i = 0; i < credentials.getWorkers(); ++i) {
            this.workers.add(new RabbitThread(this, i));
        }
    }

    private boolean connect() {
        ConnectionFactory factory = this.getCredentials().toConnectionFactory();

        try {
            this.connection = factory.newConnection();
            this.channel = connection.createChannel();
            plugin.getLogger().info("Connected to RabbitMQ!");
            return true;
        }catch (TimeoutException | IOException e) {
            e.printStackTrace();
            plugin.getLogger().severe("Unable to connect to RabbitMQ: " + e.getMessage());
            return false;
        }
    }

    public boolean isConnected() {
        if (this.connection == null || this.channel == null)
            return false;
        return this.connection.isOpen();
    }

    public void registerListener(final IListenPacket listenPacket) {
        this.listeners.add(listenPacket);
    }

    public RabbitPacket convertPacket(final Packet packet) {
        return new RabbitPacket(packet);
    }

    public boolean sendBlockingPacket(final RabbitPacket packet) {
        if (!this.isConnected()) {
            if (!this.connect()) {
                plugin.getLogger().severe("RabbitMQ: unable to send packet: not connected");
                return false;
            }
        }
        try {
            final String queueName = packet.getQueueName();
            final byte[] message = packet.getMessage();
            if (packet.getType() == RabbitPacketType.SIMPLE) {
                Map<String, Object> args = new HashMap<String, Object>();
                args.put("x-message-ttl", 60000);
                this.getChannel().queueDeclare(queueName, false, false, false, args);
                this.getChannel().basicPublish("", queueName, null, message);
                return true;
            }
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .expiration("60000")
                    .build();
            this.getChannel().exchangeDeclare(queueName, "fanout");
            this.getChannel().basicPublish(queueName, "", properties, message);
            return true;
        }catch(Exception error) {
            plugin.getLogger().severe("RabbitMQ: unable to send packet: " + error.toString());
            return false;
        }
    }

    public boolean sendPacket(final Packet packetBase) {
        final RabbitPacket packet = new RabbitPacket(packetBase);
        final Optional<RabbitThread> optThread = this.getWorkers().stream()
                .filter(RabbitThread::isAvailable)
                .findFirst();

        if (optThread.isPresent()) {
            final RabbitThread thread = optThread.get();
            thread.addQueuedPacket(packet);
            synchronized (thread) {
                thread.notify();
            }
            return true;
        }
        RabbitThread unusedThread = null;
        for (final RabbitThread thread : this.getWorkers()) {
            if (unusedThread == null || thread.getQueueSize() < unusedThread.getQueueSize())
                unusedThread = thread;
        }
        if (unusedThread == null) {
            return false;
        }
        unusedThread.addQueuedPacket(packet);
        synchronized (unusedThread) {
            unusedThread.notify();
        }
        return true;
    }

}
