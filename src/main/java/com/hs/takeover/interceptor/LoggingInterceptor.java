package com.hs.takeover.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {
  private ObjectMapper objectMapper = new ObjectMapper();
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // 요청 전처리 로직 구현
    log.info("request: {} {}", request.getMethod(), request.getRequestURI());
    // 파라미터 로깅
    Map<String, String> paramMap = new HashMap<>();
    Enumeration<String> params = request.getParameterNames();
    while (params.hasMoreElements()) {
      String param = params.nextElement();
      String value = request.getParameter(param);
      paramMap.put(param, value);
    }
    log.info("request parameters: {}", objectMapper.writeValueAsString(paramMap));
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request
                                       , HttpServletResponse response
                                         , Object handler, ModelAndView modelAndView)
                                            throws Exception {
    // 요청 후처리 로직 구현
    log.info("response: {}", response.getStatus());
  }

  @Override
  public void afterCompletion(HttpServletRequest request
                  , HttpServletResponse response, Object handler, Exception ex) throws Exception {
    // 예외 처리 로직 구현
  }
}
