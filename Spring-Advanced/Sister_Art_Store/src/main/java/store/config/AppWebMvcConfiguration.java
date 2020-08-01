package store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import store.web.interceptors.BackGroundImgInterceptor;
import store.web.interceptors.FaviconInterceptor;
import store.web.interceptors.TitleInterceptor;

@Configuration
public class AppWebMvcConfiguration implements WebMvcConfigurer {

    private final TitleInterceptor titleInterceptor;
    private final FaviconInterceptor faviconInterceptor;
    private final BackGroundImgInterceptor backGroundImgInterceptor;

    @Autowired
    public AppWebMvcConfiguration(TitleInterceptor titleInterceptor, FaviconInterceptor faviconInterceptor, BackGroundImgInterceptor backGroundImgInterceptor) {
        this.titleInterceptor = titleInterceptor;
        this.faviconInterceptor = faviconInterceptor;
        this.backGroundImgInterceptor = backGroundImgInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.titleInterceptor);
        registry.addInterceptor(this.faviconInterceptor);
        registry.addInterceptor(this.backGroundImgInterceptor);
    }
}