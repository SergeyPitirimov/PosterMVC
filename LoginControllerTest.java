package Poster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

public class LoginControllerTest
{
  
  public LoginControllerTest()
  {
  }
  
  @BeforeClass
  public static void setUpClass()
  {
  }
  
  @AfterClass
  public static void tearDownClass()
  {
  }
  
  @Before
  public void setUp()
  {
  }
  
  @After
  public void tearDown()
  {
  }

  /**
   * Test of handleRequestInternal method, of class LoginController.
   */
  @Test
  public void testHandleRequestInternal() throws Exception
  {
    System.out.println("handleRequestInternal");
    HttpServletRequest request = null;
    HttpServletResponse response = null;
    LoginController instance = new LoginController();
    ModelAndView expResult = null;
    ModelAndView result = instance.handleRequestInternal(request, response);
//    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
//    fail("The test case is a prototype.");
  }
  
}
