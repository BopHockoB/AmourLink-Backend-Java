package ua.nure.subscriptionservice.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.UUID;


@Component
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * Determines whether the given method parameter is supported for resolution by this argument resolver.
     * The method parameter must be annotated with the UserId annotation and have a type of UUID.
     *
     * @param parameter the method parameter to check
     * @return true if the method parameter is supported, false otherwise
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(UserId.class) != null && parameter.getParameterType().equals(UUID.class);
    }

    /**
     * Resolves the argument value for a method parameter annotated with @UserId.
     * The method parameter must have a type of UUID.
     *
     * @param parameter the method parameter being resolved
     * @param mavContainer the ModelAndViewContainer for the current request
     * @param webRequest the current native web request
     * @param binderFactory the factory for creating WebDataBinder instances
     * @return the resolved argument value, which is the user ID retrieved from the HttpServletRequest attribute
     * @throws Exception if there is an error during the resolution process
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory){
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return request.getAttribute("userId");
    }


}
