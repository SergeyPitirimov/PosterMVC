/* This is the storage for logins, passwords and posts. */
package Poster;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Storage
{
  public static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/postgres";
  public static final String DATABASE_USER = "pitirimov";
  public static final String DATABASE_PASSWORD = "123456";
  public static final String SELECT_USERS_QUERY = "SELECT * FROM USERS;";
  public static final String SELECT_POSTS_QUERY = "SELECT * FROM POSTS;";
  public static final String PREPARED_INSERT_TO_USERS_QUERY = "INSERT INTO USERS (USERID, NAME, PASSWORD) VALUES (?,?,?);";
  public static final String PREPARED_INSERT_TO_POSTS_QUERY = "INSERT INTO POSTS (USERID, POSTID, POST) VALUES (?,?,?);";
  public static final String PREPARED_UPDATE_TO_POSTS_QUERY = "UPDATE POSTS SET POST = ? WHERE POSTID = ?;";
  public static final String PREPARED_DELETE_FROM_POSTS_QUERY = "DELETE FROM POSTS WHERE POSTID = ?;";
  public static final int ALL_USERS = -1;

  private static final String LOGGER_PROPERTIES_FILE = "/home/pitirimov/Javaworks/Poster/log4j.properties";
  private static Logger logger = LoggerFactory.getLogger(Storage.class);
  
  static
  {
    try
    {
      /* Set the database driver */
      Class.forName("org.postgresql.Driver");      
    }
    catch (ClassNotFoundException e)
    {
      logger.error("Can't load jdbc driver.");
    }
  }

  public Storage()
  {
    super();
    /* Logger configuration */
    PropertyConfigurator.configure(LOGGER_PROPERTIES_FILE);
  }
  
  /* This method returns user's name by his Id from the users table */
  public String getName(final int userId)
  {
    String userName = "";

    try
    {
      /* Connect to the database */
      Connection databaseConnection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
      
      /* Create the unique exemplar of connection */
      Statement statement = databaseConnection.createStatement();

      /* Get the posts */
      ResultSet resultSetOfNames = statement.executeQuery(SELECT_USERS_QUERY);
      
      /* Read the data from the database */
      while(resultSetOfNames.next())
      {
        /* Get user's name by his userId */
        if (userId == resultSetOfNames.getInt(1))
        {
          userName = resultSetOfNames.getString(2);
        }
      }

      /* Close the connection to database */
      databaseConnection.close();
    }
    catch (SQLException e)
    {
      logger.error("Can't communicate with the database.");
      e.printStackTrace();
    }

    /* Return the name of user with required Id */
    return (userName);
  }

  /* This method returns the user's ID by his user name from the database */
  public int getUserId(final String userName)
  {
    int userId = -1;

    try
    {
      /* Connect to the database */
      Connection databaseConnection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
      
      /* Create the unique exemplar of connection */
      Statement statement = databaseConnection.createStatement();

      /* Get the posts */
      ResultSet resultSetOfNames = statement.executeQuery(SELECT_USERS_QUERY);
      
      /* Read the data from the database */
      while(resultSetOfNames.next())
      {
        /* Get user's name by his userId */
        if (userName.equals(resultSetOfNames.getString(2)))
        {
          userId = resultSetOfNames.getInt(1);
        }
      }

      /* Close the connection to database */
      databaseConnection.close();
    }
    catch (SQLException e)
    {
      logger.error("Can't communicate with the database.");
      e.printStackTrace();
    }

    /* Return the name of user with required Id */
    return (userId);
  }

  /* This method returns post ID by the post content from the database */
  public int getPostId(final String userPost)
  {
    int postId = -1;

    try
    {
      /* Connect to the database */
      Connection databaseConnection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
      
      /* Create the unique exemplar of connection */
      Statement statement = databaseConnection.createStatement();

      /* Get the posts */
      ResultSet resultSetOfPosts = statement.executeQuery(SELECT_POSTS_QUERY);
      
      /* Read the data from the database */
      while(resultSetOfPosts.next())
      {
        /* Get post ID by post content */
        if (userPost.equals(resultSetOfPosts.getString(3)))
        {
          postId = resultSetOfPosts.getInt(2);
        }
      }

      /* Close the connection to database */
      databaseConnection.close();
    }
    catch (SQLException e)
    {
      logger.error("Can't communicate with the database.");
      e.printStackTrace();
    }
    
    return postId;
  }

  /* This method saves updated editPost into the database by its post ID and returns post ID */
  public int updatePost(final int postId, final String updatedPost)
  {
    try
    {
      /* Connect to the database */
      Connection databaseConnection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);

      /* Prepare to write values and save them */
      PreparedStatement preparedStatement = databaseConnection.prepareStatement(PREPARED_UPDATE_TO_POSTS_QUERY);

      /* Update the post in the database */
      preparedStatement.setString(1, updatedPost);
      preparedStatement.setInt(2, postId);
      preparedStatement.executeUpdate();

      /* Close the connection to database */
      databaseConnection.close();
    }
    catch (SQLException e)
    {
      logger.error("Can't communicate with the database.");
      e.printStackTrace();
    }

    return postId;
  }

  /* This method adds the post to the database */
  public int addPost(final int userId, String postToAdd)
  {
    int nextPostId = 0;

    try
    {
      /* Connect to the database */
      Connection databaseConnection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);

      /* Create the unique exemplar of connection */
      Statement addedPostsStatement = databaseConnection.createStatement();

      /* Get the user's ID */
      ResultSet resultSetOfPosts = addedPostsStatement.executeQuery(SELECT_POSTS_QUERY);
      
      /* Read the data from the database */
      while(resultSetOfPosts.next())
      {
        nextPostId = nextPostId + 1;
      }

      /* Prepare to write values and save them */
      PreparedStatement preparedStatement = databaseConnection.prepareStatement(PREPARED_INSERT_TO_POSTS_QUERY);
      
      /* Add the post to the database */
      preparedStatement.setInt(1, userId);
      preparedStatement.setInt(2, nextPostId);
      preparedStatement.setString(3, postToAdd);
      preparedStatement.executeUpdate();

      /* Close the connection to database */
      databaseConnection.close();
    }
    catch (SQLException e)
    {
      logger.error("Can't communicate with the database.");
      e.printStackTrace();
    }

    /* return the user's ID */
    return nextPostId;
  }
  
  /* This method returns the linked list of user's name and his post content */
  public LinkedList<String> getPosts(final int userId)
  {
    /* Create the linked list of names and posts */
    LinkedList<String> linkedListOfNamesAndPosts = new LinkedList<String>();

    try
    {
      /* Connect to the database */
      Connection databaseConnection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
      
      /* Create the unique exemplar of connection */
      Statement statement = databaseConnection.createStatement();

      /* Get the posts */
      ResultSet resultSetOfPosts = statement.executeQuery(SELECT_POSTS_QUERY);
      
      /* Read the data from the database */
      while(resultSetOfPosts.next())
      {
        if (userId == ALL_USERS)
        {
          /* Insert the user's name to the linked list */
          linkedListOfNamesAndPosts.addLast(getName(resultSetOfPosts.getInt(1)));
          /* Insert the post content to the linked list */
          linkedListOfNamesAndPosts.addLast(resultSetOfPosts.getString(3));
        }
        else
        {
          /* Insert the post of required user */
          if (userId == resultSetOfPosts.getInt(1))
          {
            linkedListOfNamesAndPosts.addLast(resultSetOfPosts.getString(3));
          }
        }
      }

      /* Close the connection to database */
      databaseConnection.close();
    }
    catch (SQLException e)
    {
      logger.error("Can't communicate with the database.");
      e.printStackTrace();
    }

    return linkedListOfNamesAndPosts;
  }

  /* This method deletes the post from the database */
  public int deletePost(final int postId)
  {
    try
    {
      /* Connect to the database */
      Connection databaseConnection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);

      /* Prepare to delete the post */
      PreparedStatement preparedStatement = databaseConnection.prepareStatement(PREPARED_DELETE_FROM_POSTS_QUERY);

      /* Delete the post from the database */
      preparedStatement.setInt(1, postId);
      preparedStatement.executeUpdate();

      /* Close the connection to database */
      databaseConnection.close();
    }
    catch (SQLException e)
    {
      logger.error("Can't communicate with the database.");
      e.printStackTrace();
    }

    /* Post has been deleted successfully */
    return (0);
  }
  
  /* This method adds the user by his name and password to the database and returns his ID */
  public int addUser(final String userName, final String userPassword)
  {
    int nextUserId = 0;

    try
    {
      /* Connect to the database */
      Connection databaseConnection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);

      /* Create the unique exemplar of connection */
      Statement registeredUsersStatement = databaseConnection.createStatement();

      /* Get the user's ID */
      ResultSet resultSetOfUsers = registeredUsersStatement.executeQuery(SELECT_USERS_QUERY);
      
      /* Read the data from the database */
      while(resultSetOfUsers.next())
      {
        nextUserId = nextUserId + 1;
      }

      /* Prepare to write values and save them */
      PreparedStatement preparedStatement = databaseConnection.prepareStatement(PREPARED_INSERT_TO_USERS_QUERY);
      
      /* Add new user to the database */
      preparedStatement.setInt(1, nextUserId);
      preparedStatement.setString(2, userName);
      preparedStatement.setString(3, userPassword);
      preparedStatement.executeUpdate();

      /* Close the connection to database */
      databaseConnection.close();
    }
    catch (SQLException e)
    {
      logger.error("Can't communicate with the database.");
      e.printStackTrace();
    }

    /* return the user's ID */
    return nextUserId;
  }
}