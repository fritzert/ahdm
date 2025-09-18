package pe.mef.sitfis.seguridad.adapter.config.util;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class TokenListaNegraService {

  private final ReactiveStringRedisTemplate redisTemplate;

  public TokenListaNegraService(ReactiveStringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public Mono<Void> guardarTokenlistaNegraRedis(String token, Duration ttl) {
    String key = TokenUtils.generarListaNegraKey(token);
    Map<String, String> hashData = new HashMap<>();
    hashData.put("estado", "true");
    hashData.put("timestamp", Instant.now().toString());

    return redisTemplate.opsForHash()
        .putAll(key, hashData)
        .then(redisTemplate.expire(key, ttl)) // establece el TTL para la entrada hash
        .doOnSuccess(r -> log.info("Token agregado a blacklist hash: {}", key))
        .then();
  }
}