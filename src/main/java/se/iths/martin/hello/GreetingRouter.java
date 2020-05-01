//package se.iths.martin.hello;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.web.reactive.HandlerResult;
//import org.springframework.web.reactive.function.server.RequestPredicates;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//@Configuration
//public class GreetingRouter {
//
//    @Bean
//    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {
//
//        return RouterFunctions
//                .route(RequestPredicates.GET("/hello/{name}").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), greetingHandler::hello)
//                .andRoute(RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), greetingHandler::hellos)
//                .andRoute(RequestPredicates.POST("/hello")
//                        .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)),greetingHandler::post);
//    }
//}
