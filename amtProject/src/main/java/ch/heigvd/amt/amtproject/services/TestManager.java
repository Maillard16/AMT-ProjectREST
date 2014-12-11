/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.amtproject.services;

import ch.heigvd.amt.amtproject.model.Organisation;
import ch.heigvd.amt.amtproject.model.Sensor;
import ch.heigvd.amt.amtproject.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Calixte
 */
@Stateless
public class TestManager implements TestManagerLocal {
    
    @EJB
    private OrganisationManagerLocal organisationManager;
    
    @EJB
    private UserManagerLocal userManager;
    
    @EJB
    private SensorManagerLocal sensorManager;
    
   
    @Override
    public void generateData() {
        // Let's create a first test organization
        Organisation org = new Organisation();
        org.setName("SuperOrg");
        long orgId = organisationManager.createOrganisation(org);
        
        Sensor sen = new Sensor();
        sen.setOrganisation(org);
        long sensorId = sensorManager.createSensor(sen);
        
        //now let's create some users for the organizations:
        List<Long> orgUsers= new ArrayList();
        for (int i = 1; i <= 5; i++) {
            orgUsers.add(userManager.createUser(new User(i, "user"+i,"pass" ,"user"+i+"@org1.com", org)));
            
        }
        org.setContactUser(userManager.findUserById(4));
        
        //organisationManager.updateOrganisation(org);
    }
}
