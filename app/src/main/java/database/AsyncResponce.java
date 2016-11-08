package database;

/**
 * Created by Stan Guldemond on 20/10/16.
 */

public interface AsyncResponce {
    void processFinish(String type, Object output);
}