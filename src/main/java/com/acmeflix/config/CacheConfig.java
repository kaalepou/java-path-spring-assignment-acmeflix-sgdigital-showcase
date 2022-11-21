package com.acmeflix.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kalepou
 */
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

  @Bean("customCacheKeyGenerator")
  public KeyGenerator getCacheKeyGenerator() {
    return new CustomCacheKeyGenerator();
  }
}