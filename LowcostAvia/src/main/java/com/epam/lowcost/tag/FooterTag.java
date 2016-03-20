package com.epam.lowcost.tag;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Виктория on 19.03.2016.
 */
@SuppressWarnings("serial")
public class FooterTag extends TagSupport {
    private static Logger logger = Logger.getLogger(FooterTag.class);

    private String getFilename() {
        String finalPath = "";
        try {
            String path = getClass().getClassLoader().getResource("").getPath();
            String fullPath = URLDecoder.decode(path, "UTF-8");
            String pathArr[] = fullPath.split("/WEB-INF/classes/");
            fullPath = pathArr[0];
            finalPath = new File(fullPath).getPath() + File.separatorChar + "jsp" + File.separatorChar + "footer.jsp";
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        }
        return finalPath;
    }

    @Override
    public int doStartTag() throws JspTagException {
        try {
            JspWriter out = pageContext.getOut();
            List<String> lines = Files.readAllLines(Paths.get(getFilename()), StandardCharsets.UTF_8);
            for (String line : lines) {
                out.write(line);
            }
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}
