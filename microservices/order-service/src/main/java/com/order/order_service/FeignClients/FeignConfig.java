package com.order.order_service.FeignClients;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig implements RequestInterceptor {

    @Autowired
    private Tracer tracer;

    public void apply(RequestTemplate template) {
        Span span = tracer.currentSpan(); //mevcut trace icin
        if (span != null) {

            template.header("X-B3-TraceId", span.context().traceId());
            template.header("X-B3-SpanId", span.context().spanId());
            template.header("X-B3-Sampled", "1");
        }
    }
}