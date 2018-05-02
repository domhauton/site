package com.domhauton.site.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by dominic on 27/05/17.
 */
public abstract class ConfigManager {
  private final static Logger logger = LogManager.getLogger();
  private final static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

  public static void saveConfig(Path configPath, SiteConfig config) throws ConfigException {
    try {
      logger.info("Config - Saving config. \t\t[{}]", configPath);
      mapper.writeValue(configPath.toFile(), config);
    } catch (IOException e) {
      logger.error("Config - Failed to open file. \t[{}]", configPath);
      logger.debug(e);
      throw new ConfigException("Failed to open file. " + e.getMessage());
    }
  }

  public static SiteConfig loadConfig(Path filePath) throws ConfigException {
    try {
      logger.info("Config - Loading config. \t[{}]", filePath);
      return mapper.readValue(filePath.toFile(), SiteConfig.class);
    } catch (JsonParseException e) {
      logger.error("Config - Failed to parse YAML. \t[{}]", filePath);
      logger.debug(e);
      throw new ConfigException("Failed to parse YAML. " + e.getMessage());
    } catch (JsonMappingException e) {
      logger.error("Config - Config could not be parsed. \t[{}]", filePath);
      logger.debug(e);
      throw new ConfigException("Config has invalid fields. " + e.getMessage());
    } catch (IOException e) {
      logger.error("Config - Failed to open file. \t[{}]", filePath);
      logger.debug(e);
      throw new ConfigException("Failed to open file. " + e.getMessage());
    }
  }

  public static SiteConfig loadDefaultConfig() {
    return new SiteConfig();
  }
}
