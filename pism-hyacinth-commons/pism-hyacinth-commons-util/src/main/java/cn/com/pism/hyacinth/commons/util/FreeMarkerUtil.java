package cn.com.pism.hyacinth.commons.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @author PerccyKing
 * @since 2023/3/5 18:03
 */
public class FreeMarkerUtil {
    private FreeMarkerUtil() {
    }

    public static Configuration createConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
        configuration.setDefaultEncoding("utf-8");
        return configuration;
    }

    public static String processTemplateIntoString(Template template, Object model)
            throws IOException, TemplateException {

        StringWriter result = new StringWriter(1024);
        template.process(model, result);
        return result.toString();
    }
}
