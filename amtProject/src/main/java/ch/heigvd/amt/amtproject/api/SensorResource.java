/**
 * @Authors : Melly Calixte And Saam Frederic
 * @Name : SensorResource.java
 * @Description : Specifiy the resources for the sensors
 * @Version 1.0
 **/

package ch.heigvd.amt.amtproject.api;

import ch.heigvd.amt.amtproject.dto.SensorDTO;
import ch.heigvd.amt.amtproject.model.Sensor;
import ch.heigvd.amt.amtproject.services.OrganisationManagerLocal;
import ch.heigvd.amt.amtproject.services.SensorManagerLocal;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;


@Path("sensors")
@Stateless
public class SensorResource {
    @EJB
    SensorManagerLocal sensorManager;
    
    @EJB
    OrganisationManagerLocal organisationManager;
    
    @Context
    private UriInfo context;
    
    public SensorResource (){}
    
    @GET
    @Produces("application/json")
    public List<SensorDTO> getAllSensors ()
    {
        List<Sensor> sensors = sensorManager.findAllSensors();
        List<SensorDTO> result = new LinkedList<>();
        
        for(Sensor sensor : sensors)
        {
            result.add(toDTO(sensor));
        }
        return result;
    }
    
    @Path("/{id}")
    @GET
    @Produces("application/json")
    public SensorDTO getSensorDetails (@PathParam("id") long id)
    {
        Sensor sensor = sensorManager.findSensorById(id);
        return toDTO(sensor);
    }
    
    @POST
    @Consumes("application/json")
    public long createSensor (SensorDTO sensorDTO)
    {
        Sensor newSensor = new Sensor();
        long id = sensorManager.createSensor(toSensor(sensorDTO,newSensor));
        return id;
    }  
    
    @Path("/{id}")
    @PUT
    @Produces("application/json")
    public void updateSensor (@PathParam("id") long id, SensorDTO dto)
    {
        Sensor existing = sensorManager.findSensorById(id);
        sensorManager.updateSensor(toSensor(dto, existing));
    }
    
    @Path("/{id}")
    @DELETE
    public void deleteSensor (@PathParam("id") long id)
    {
        sensorManager.deleteSensor(id);
    }

    private SensorDTO toDTO(Sensor sensor) {
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setId(sensor.getId());
        sensorDTO.setName(sensor.getName());
        sensorDTO.setDescription(sensor.getDescription());
        sensorDTO.setType(sensor.getType());
        sensorDTO.setOrganisationId(sensor.getOrganisation().getId());
        sensorDTO.setVisibility(sensor.getVisibility());

        return sensorDTO;
    }

    private Sensor toSensor(SensorDTO sensorDTO, Sensor original) {
        original.setName(sensorDTO.getName());
        original.setDescription(sensorDTO.getDescription());
        original.setType(sensorDTO.getType());
        original.setOrganisation(organisationManager.findOrganisationById(sensorDTO.getOrganisationId()));
        original.setVisibility(sensorDTO.getVisibility());
        
        return original;
    }
}
