package fr.aureliancnx.betterbungee.rabbit;

import com.google.common.collect.Queues;

import java.util.Queue;

/**
 * @author aureliancnx
 */
public class RabbitThread extends Thread {

    private Queue<RabbitPacket> queue = Queues.newLinkedBlockingDeque();

    private RabbitService service;

    public RabbitThread(RabbitService service, int id) {
        super("ArgusRabbit/" + id);
        this.service = service;
        this.start();
    }

    public void run() {
        while (true) {
            while (!this.queue.isEmpty()) {
                RabbitPacket packet = this.queue.poll();
                this.service.sendBlockingPacket(packet);
            }
            synchronized (this) {
                try {
                    this.wait();
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void sendUpsignal() {
        synchronized (this) {
            this.notify();
        }
    }

    public boolean isAvailable() {
        return this.getState() == State.WAITING;
    }

    public int getQueueSize() {
        return this.queue.size();
    }

    public void addQueuedPacket(RabbitPacket packet) {
        this.queue.add(packet);
    }

}
