package store.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

  private AtomicInteger requestCount = new AtomicInteger(0);
  private LocalDateTime startedOn = LocalDateTime.now();

  public void incRequestCount() {
    requestCount.incrementAndGet();
  }

  public int getRequestCount() {
    return requestCount.get();
  }

  public LocalDateTime getStartedOn() {
    return startedOn;
  }

}
