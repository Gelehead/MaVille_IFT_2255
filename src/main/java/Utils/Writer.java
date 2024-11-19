package Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

import Instances.Intervenant;
import Instances.Project;
import Instances.Resident;
import Instances.User;

public class Writer {

    public static void add(User u, String targetFile){
        try 
        {
            ObjectMapper om = new ObjectMapper();
            om.writeValue(new File(targetFile), u);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static void add(Resident r, String targetFile){
        try 
        {
            ObjectMapper om = new ObjectMapper();
            om.writeValue(new File(targetFile), r);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static void add(Intervenant i, String targetFile){
        try 
        {
            ObjectMapper om = new ObjectMapper();
            om.writeValue(new File(targetFile), i);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static void add(Project p, String targetFile){
        try 
        {
            ObjectMapper om = new ObjectMapper();
            om.writeValue(new File(targetFile), p);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
