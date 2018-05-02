package com.domhauton.site;

import com.domhauton.site.config.ConfigException;
import com.domhauton.site.config.ConfigManager;
import com.domhauton.site.config.SiteConfig;
import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public final class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DEFAULT_CONFIG_LOCATION = System.getProperty("user.home") + File.separator
            + ".config" + File.separator +
            "domhauton-site" + File.separator +
            "config.yml";

    public static void main(String[] args) {
        LOGGER.info("Starting Site");
        Options options = new Options()
                .addOption("c", "config", true, "Specify the config directory. Default: [" + DEFAULT_CONFIG_LOCATION + "]");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            Path configLocation = Paths.get(cmd.getOptionValue("config", DEFAULT_CONFIG_LOCATION));
            SiteConfig siteConfig = getBotConfig(configLocation);
            startBot(siteConfig);
        } catch (ParseException e) {
            LOGGER.error("Argument parsing failed. Reason: {}", e.getMessage());
            System.exit(1);
        } catch (ConfigException e) {
            LOGGER.error("Config loading failed. Reason: {}", e.getMessage());
            System.exit(2);
        }
    }

    private static SiteConfig getBotConfig(Path configLocation) throws ConfigException {
        SiteConfig botConfig;
        try {
            return ConfigManager.loadConfig(configLocation);
        } catch (ConfigException e) {
            LOGGER.info("Attempting to use default config.");
            botConfig = ConfigManager.loadDefaultConfig();
            ConfigManager.saveConfig(configLocation, botConfig);
            return botConfig;
        }
    }

    private static void startBot(SiteConfig siteConfig) {
        LOGGER.info("Starting service!");
        Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);
    }
}
