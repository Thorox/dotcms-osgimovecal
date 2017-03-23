package edu.pittstate.osgi.movetoevents;

import java.util.ArrayList;
import java.util.HashMap;
import com.dotmarketing.business.APILocator;
import com.dotmarketing.util.UtilMethods;
import com.dotmarketing.util.InodeUtils;
import com.dotmarketing.portlets.workflows.actionlet.WorkFlowActionlet;
import com.dotmarketing.portlets.workflows.model.WorkflowActionClassParameter;
import com.dotmarketing.portlets.workflows.model.WorkflowActionFailureException;
import com.dotmarketing.portlets.workflows.model.WorkflowActionletParameter;
import com.dotmarketing.portlets.workflows.model.WorkflowProcessor;
import com.dotmarketing.portlets.categories.model.Category;
import com.dotmarketing.portlets.categories.business.CategoryAPI;
import com.dotmarketing.portlets.contentlet.model.Contentlet;
import com.dotmarketing.portlets.contentlet.business.ContentletAPI;
import com.dotmarketing.portlets.structure.model.Field;
import com.dotmarketing.portlets.structure.model.Field.FieldType;
import com.dotmarketing.portlets.structure.model.Structure;
import com.dotmarketing.cache.FieldsCache;
import com.dotmarketing.cache.StructureCache;
import com.dotmarketing.util.Logger;
import java.util.List;
import java.util.Map;
import com.dotmarketing.business.UserAPI;

public class MyActionlet extends WorkFlowActionlet {

    private static final long serialVersionUID = 1L;

    @Override
    public List<WorkflowActionletParameter> getParameters () {
        return null;
    }

    @Override
    public String getName () {
        return "Move To Events";
    }

    @Override
    public String getHowTo () {
        return "For Use By CMS Administrators Only";
    }

    @Override
    public void executeAction ( WorkflowProcessor processor, Map<String, WorkflowActionClassParameter> params ) throws WorkflowActionFailureException {
        boolean clean = false;
        boolean live  = false;
	try {
		Structure s = StructureCache.getStructureByVelocityVarName("calendarEvent");
		if (s == null || s.getName() == null) {
			Logger.error(this,"Could Not Find calendarEvent");
		}
		Contentlet old = processor.getContentlet();
		Contentlet c = new Contentlet();
		c.setStructureInode(s.getInode());
		ContentletAPI conAPI = APILocator.getContentletAPI();
		CategoryAPI catAPI = APILocator.getCategoryAPI();
		UserAPI userAPI = APILocator.getUserAPI();
		List<Category> cats = new ArrayList<Category>();
	 	List<Category> cats2 = new ArrayList<Category>();
	
		//find all categories from old contentlet
		cats = catAPI.getParents(old, false, userAPI.getSystemUser(), false);
		Logger.error(this,cats.toString());
		for (Field field : FieldsCache.getFieldsByStructureInode(s.getInode())) {
			if (field.getVelocityVarName().equals("title")) {
				conAPI.setContentletProperty(c, field, old.getStringProperty("title"));
			}
			if (field.getVelocityVarName().equals("presenterSeries")) {
				conAPI.setContentletProperty(c, field, old.getStringProperty("presenterSeries"));
			}
                        if (field.getVelocityVarName().equals("startDate")) {
                                conAPI.setContentletProperty(c, field, old.getDateProperty("startDateTime"));
                        }
                        if (field.getVelocityVarName().equals("endDate")) {
                                conAPI.setContentletProperty(c, field, old.getDateProperty("endDateTime"));
                        }
                        if (field.getVelocityVarName().equals("location")) {   
                                conAPI.setContentletProperty(c, field, old.getStringProperty("location"));
                        }
                        if (field.getVelocityVarName().equals("addressforLocatingOnGoogleMap")) {
                                conAPI.setContentletProperty(c, field, old.getStringProperty("addressforShowingTheEventLocationOnTheMap"));
                        }
                        if (field.getVelocityVarName().equals("description")) {   
                                conAPI.setContentletProperty(c, field, old.getStringProperty("eventDesription"));
                        }
                        if (field.getVelocityVarName().equals("image")) {
                                conAPI.setContentletProperty(c, field, old.getStringProperty("eventImagerecommended"));
                        }
                        if (field.getVelocityVarName().equals("eventFile")) {
                                conAPI.setContentletProperty(c, field, old.getStringProperty("fileoptional"));
                        }
                        if (field.getVelocityVarName().equals("ticketCost")) {
                                conAPI.setContentletProperty(c, field, old.getStringProperty("ticketInformation"));
                        }
                        if (field.getVelocityVarName().equals("thisEventRequiresATicket")) {   
                                conAPI.setContentletProperty(c, field, old.getStringProperty("requiresTickets"));
                        }
                        if (field.getVelocityVarName().equals("onSaleFrom")) {  
                                conAPI.setContentletProperty(c, field, old.getDateProperty("ticketsOnSaleFromThisDate"));
                        }       
                        if (field.getVelocityVarName().equals("purchasingLink")) {   
                                conAPI.setContentletProperty(c, field, old.getStringProperty("purchaseTicketsLink"));
                        }
                        if (field.getVelocityVarName().equals("regRequired")) { 
                                conAPI.setContentletProperty(c, field, old.getStringProperty("registrationRequired"));
                        }
                        if (field.getVelocityVarName().equals("externalRegistrationLink"))
 {   
                                conAPI.setContentletProperty(c, field, old.getStringProperty("registrationLink"));
                        }
                        if (field.getVelocityVarName().equals("contactName")) {
                                conAPI.setContentletProperty(c, field, old.getStringProperty("contactFirstLastName"));
                        }
                        if (field.getVelocityVarName().equals("phoneNumber"))
 {
                                conAPI.setContentletProperty(c, field, old.getStringProperty("contactPhoneNumber"));
                        }
                        if (field.getVelocityVarName().equals("website")) { 
                                conAPI.setContentletProperty(c, field, old.getStringProperty("contactWebsitedepartmentOfficeOther"));
                        }
			if (field.getVelocityVarName().equals("calendarCategoryField")) {
				conAPI.setContentletProperty(c, field, old.getStringProperty("calendarCategories"));
			}
			if (field.getVelocityVarName().equals("colleges")) {
				conAPI.setContentletProperty(c, field, old.getStringProperty("showEventOnLandingPage"));
			}
			if (old.getStringProperty("showMyContactInformationOnEventPage") == "No") {
				//do something to remove/hide contact info?
			}
		}
	
		conAPI.checkin(c, userAPI.getSystemUser(), true, cats);
	} catch (Exception e) {
		throw new WorkflowActionFailureException(e.getMessage());
	}
}
}
 
