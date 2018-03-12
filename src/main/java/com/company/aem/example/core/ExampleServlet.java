package com.company.aem.example.core;

import com.day.cq.wcm.api.NameConstants;
import com.google.common.collect.ImmutableMap;
import com.google.common.net.MediaType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.Map;
import javax.servlet.Servlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

/**
 * @link http://[host]:[port]/content/we-retail/us/en.example.json
 */
@Component(
  service = Servlet.class,
  property = {
    "sling.servlet.extensions=json",
    "sling.servlet.selectors=example",
    "sling.servlet.resourceTypes=" + NameConstants.NT_PAGE
  }
)
public class ExampleServlet extends SlingAllMethodsServlet {

  private static final Gson GSON = new GsonBuilder()
    .disableHtmlEscaping().serializeNulls().setPrettyPrinting()
    .create();

  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
    throws IOException {
    Map<String, String> result = ImmutableMap.of("message", "Hello World!");

    response.setContentType(MediaType.JSON_UTF_8.toString());
    response.getWriter().write(GSON.toJson(result));
  }

}
