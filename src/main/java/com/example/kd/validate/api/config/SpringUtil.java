package com.example.kd.validate.api.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class SpringUtil implements ApplicationContextAware {

  private static Logger logger = LoggerFactory.getLogger(SpringUtil.class);
  private static ApplicationContext applicationContext;

    public static void setApplication(ApplicationContext applicationContext) {
        SpringUtil.applicationContext = applicationContext;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.applicationContext == null)
        {
            SpringUtil.applicationContext = applicationContext;
        }
        logger.info("setApplicationContext");
    }
    public static  ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }
    public static  Object getBean(String name)
    {
        ApplicationContext applicationContext = getApplicationContext();
        if(applicationContext != null)
        {
            try {
                return applicationContext.getBean(name);
            }catch (Exception e)
            {

            }
        }
        return null;
    }
    public static  <T> T getBean(Class<T> clazz)
    {
        ApplicationContext applicationContext = getApplicationContext();
        if(applicationContext != null)
        {
            try {
                return applicationContext.getBean(clazz);
            }catch (Exception e)
            {

            }

        }
        return null;
    }
    public static  <T> T getBean(String name,Class<T> clazz)
    {
        ApplicationContext applicationContext = getApplicationContext();
        if(applicationContext != null)
        {
            try {
                return applicationContext.getBean(name,clazz);
            }catch (Exception e)
            {

            }
        }
        return null;
    }
}
