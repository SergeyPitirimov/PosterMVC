package Poster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

public class EditorController extends ParameterizableViewController
{
  private static final String LOGGER_PROPERTIES_FILE = "/home/pitirimov/Javaworks/Poster/log4j.properties";
  private static Logger logger = LoggerFactory.getLogger(EditorController.class);
  
  public EditorController()
  {
    super();
    /* Logger configuration */
    PropertyConfigurator.configure(LOGGER_PROPERTIES_FILE);
  }
  
  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    ModelAndView mvc = new ModelAndView();

    if ((request != null) && (response != null))
    {
      /* Create the local storage for database access */
      Storage loginStorage = new Storage();

      /* Go to the user's page */
      if (!((request.getParameter("username")).equals("")))
      {
        if (loginStorage.getUserId(request.getParameter("username")) < 0)
        {
          /* Create new user */
          loginStorage.addUser(request.getParameter("username"), request.getParameter("userpassword"));
        }

        /* Go to the user's page */
        mvc.setViewName("Editor");
      }
      else
      {
        /* Go to the initial page */
        mvc.setViewName("Login");
      }
    }

    return mvc;
  }
}