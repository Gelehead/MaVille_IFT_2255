import Instances.Project;
import backend.Database;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DataBaseTest {
@Test
    public void DataTestfiltrerTitreAANDReason(){

        Database datatest=new Database();
        ArrayList<Project> fo= datatest.addprojett();
        ArrayList<Project> ddd=datatest.getProjectsBy("Projet paysager");
        assertNotNull(ddd);
        assertEquals(1, ddd.size(), "erreur : ddd != 1 ");
        assertEquals("Projet paysager",ddd.get(0).getTitle());
        ArrayList<Project> ddd2=datatest.getProjectsBy(Project.Reason.Travaux_routiers);
        assertNotNull(ddd2);
        assertEquals(1, ddd2.size(), "erreur : ddd2 != 1 ");
        assertEquals(Project.Reason.Travaux_routiers,ddd2.get(0).getReason());







    // System.out.println(datatest.getResidentList().size());
    }
   /* @Test
    public void DataTestfiltrerreason(){
        Database datatest2=new Database();
        ArrayList<Project> fo= datatest2.addprojett();
        ArrayList<Project> ddd2=datatest2.getProjectsBy(Project.Reason.Travaux_routiers);
        assertNotNull(ddd2);
        assertEquals(1, ddd2.size(), "erreur : ddd != 1 ");
        assertEquals(Project.Reason.Travaux_routiers,ddd2.get(0).getReason());

    }*/

  //  public static void main(String[] args) {
       // Database datatest=new Database();
        //datatest.init(3,3,3);
        //System.out.println(datatest.getProjectList());
   // }
}
