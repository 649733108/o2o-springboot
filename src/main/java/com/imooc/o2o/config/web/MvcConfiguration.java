package com.imooc.o2o.config.web;
/*
 * Created by wxn
 * 2018/11/26 22:42
 */


import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.imooc.o2o.Interceptor.shopadmin.ShopLoginInterceptor;
import com.imooc.o2o.Interceptor.shopadmin.ShopPermissionInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
//等价于<mvc:annotation-driver/>
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {

	private ApplicationContext applicationContext;


	@Value("${kaptcha.border}")
	private String border;
	@Value("${kaptcha.textproducer.font.color}")
	private String fcolor;
	@Value("${kaptcha.image.width}")
	private String width;
	@Value("${kaptcha.textproducer.char.string}")
	private String cString;
	@Value("${kaptcha.image.height}")
	private String height;
	@Value("${kaptcha.textproducer.font.size}")
	private String fsize;
	@Value("${kaptcha.noise.color}")
	private String nColor;
	@Value("${kaptcha.textproducer.char.length}")
	private String clength;
	@Value("${kaptcha.textproducer.font.names}")
	private String fnames;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * 静态资源配置
	 */
	public void addResourceHandlers(ResourceHandlerRegistry registry){
//		registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")){
			basePath = "C:\\Users\\64973\\Pictures\\o2o\\upload\\";
		}else {
			basePath = "/usr/o2o/upload/";
		}
		registry.addResourceHandler("/upload/**").addResourceLocations("file:"+basePath);
	}

	/**
	 * 定义默认的请求处理器
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * 创建viewResolver
	 */
	@Bean(name = "viewResolver")
	public ViewResolver createViewResolver(){
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		//设置Spring容器
		viewResolver.setApplicationContext(applicationContext);
		//取消缓存
		viewResolver.setCache(false);
		//设置解析的前缀
		viewResolver.setPrefix("/WEB-INF/html/");
		//设置解析的后缀
		viewResolver.setSuffix(".html");
		return viewResolver;
	}

	/**
	 * 文件上传解析器
	 */
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver createMultipartResolver(){
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("utf-8");
		multipartResolver.setMaxUploadSize(20971520);
		multipartResolver.setMaxInMemorySize(20971520);
		return multipartResolver;
	}

	/**
	 * 由于web.xml不生效 需要在这里配置Kaptcha验证码servlet
	 */
	@Bean
	public ServletRegistrationBean servletRegistrationBean(){
		ServletRegistrationBean servlet = new ServletRegistrationBean(new KaptchaServlet(),"/Kaptcha");
		servlet.addInitParameter("kaptcha.border",border);//无边框
		servlet.addInitParameter("kaptcha.textproducer.font.color",fcolor);//字体颜色
		servlet.addInitParameter("kaptcha.image.width",width);//图片宽度
		servlet.addInitParameter("kaptcha.textproducer.char.string",cString);//使用那些字符
		servlet.addInitParameter("kaptcha.image.height",height);//图片高度
		servlet.addInitParameter("kaptcha.textproducer.font.size",fsize);//字体大小
		servlet.addInitParameter("kaptcha.noise.color",nColor);//干扰线颜色
		servlet.addInitParameter("kaptcha.textproducer.char.length",clength);//字符个数
		servlet.addInitParameter("kaptcha.textproducer.font.names",fnames);//字体

		return servlet;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		String interceptPath = "/shopadmin/**";
		//注册拦截器
		InterceptorRegistration loginIR = registry.addInterceptor(new ShopLoginInterceptor());
		//配置拦截的路径
		loginIR.addPathPatterns(interceptPath);
		loginIR.excludePathPatterns("/shopadmin/addshopauthmap");
		//还可以注册其他的拦截器
		InterceptorRegistration permissionIR = registry.addInterceptor(new ShopPermissionInterceptor());
		//配置拦截的路径
		permissionIR.addPathPatterns(interceptPath);
		//配置不拦截的路径
		permissionIR.excludePathPatterns("/shopadmin/shoplist");
		permissionIR.excludePathPatterns("/shopadmin/getshoplist");
		permissionIR.excludePathPatterns("/shopadmin/getshopinitinfo");
		permissionIR.excludePathPatterns("/shopadmin/registershop");
		permissionIR.excludePathPatterns("/shopadmin/shopoperation");
		permissionIR.excludePathPatterns("/shopadmin/shopmanagement");
		permissionIR.excludePathPatterns("/shopadmin/getshopmanagementinfo");
		permissionIR.excludePathPatterns("/shopadmin/addshopauthmap");

	}
}
