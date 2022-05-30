package fr.aureliancnx.betterbungee.util;

import com.google.gson.Gson;
import fr.aureliancnx.betterbungee.BetterBungeePlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.nio.file.Files;

public class ConfigUtils {

    private static final Gson gson = new Gson();

    public static Object load(final BetterBungeePlugin plugin, final String fileName, final Type clazz) throws Exception {
        assert plugin != null;
        assert fileName != null;
        final File pluginFolder = plugin.getDataFolder();
        if (!pluginFolder.exists()) {
            throw new FileNotFoundException("Plugin folder not found");
        }
        final File file = new File(pluginFolder, fileName);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
        }
        // I consider this as a bad practice in a way, because reading all file bytes
        // and storing this data in a variable is not memory-safe, as if the file
        // is larger than the available memory, the JVM will throw an OutOfMemoryError.
        // But still, here we are using this function to read a configuration file,
        // so a little kilobytes of data should be available in memory, or something is
        // really going wrong.
        final byte[] fileBytes = Files.readAllBytes(file.toPath());
        if (fileBytes.length == 0) {
            throw new FileNotFoundException("File is empty: " + file.getAbsolutePath());
        }
        final String content = new String(fileBytes);
        return gson.fromJson(content, clazz);
    }

}
