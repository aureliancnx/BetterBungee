package fr.aureliancnx.betterbungee.config;

import fr.aureliancnx.betterbungee.rabbit.RabbitCredentials;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BetterBungeeConfig {

    private final String            name;
    private final int               localSlots;
    private final RabbitCredentials rabbit;

}
