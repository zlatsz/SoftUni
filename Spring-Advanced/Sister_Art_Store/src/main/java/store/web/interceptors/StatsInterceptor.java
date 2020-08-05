package store.web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import store.service.StatsService;

@Component
public class StatsInterceptor extends HandlerInterceptorAdapter {

  private final StatsService statsService;

  public StatsInterceptor(StatsService statsService) {
    this.statsService = statsService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    this.statsService.incRequestCount();
    return true;
  }
}
