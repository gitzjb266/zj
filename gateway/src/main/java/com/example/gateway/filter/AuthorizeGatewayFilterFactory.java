//package com.example.gateway.filter;
//
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.cloud.gateway.filter.factory.PrefixPathGatewayFilterFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
///**
// * @author
// * @ClassName AuthorizeGatewayFilterFactory
// * @Description TODO
// * @Date 2019-06-06 10:10
// * @Version 1.0
// */
//@Component
//public class AuthorizeGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizeGatewayFilterFactory.Config> {
//    private static final Log logger = LogFactory.getLog(AbstractGatewayFilterFactory.class);
//    public AuthorizeGatewayFilterFactory(){
//        super(Config.class);
//    }
//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            ServerHttpRequest request =exchange.getRequest();
//            String token = request.getHeaders().getFirst("token");
//            logger.info("token:"+token);
//            if (null == token){
//                token = request.getQueryParams().getFirst("token");
//            }
//            ServerHttpResponse response = exchange.getResponse();
//            if (StringUtils.isEmpty(token)){
//                response.setStatusCode(HttpStatus.UNAUTHORIZED);
//                return response.setComplete();
//            }
//            return chain.filter(exchange);
//        };
//    }
//    public static class Config{
//        static String s="123456";
//    }
//}
