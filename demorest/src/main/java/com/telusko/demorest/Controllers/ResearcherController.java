package com.telusko.demorest.Controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONObject;

import com.telusko.demorest.model.CategoryModel;
import com.telusko.demorest.model.ProjectModel;
import com.telusko.demorest.model.UserModel;
import com.telusko.demorest.model.UserCardModel;
import com.telusko.demorest.services.CategoryRepository;
import com.telusko.demorest.services.ProjectRepository;
import com.telusko.demorest.services.UserRepository;

@Path("researcherlogin")
public class ResearcherController {

	ProjectRepository projectRepo=new ProjectRepository();
	UserRepository userRepo=new UserRepository();
	CategoryRepository categoryRepo=new CategoryRepository();
	
	//Project Controllers-------------------------------------------------------------------------------------------------------------------
	
	@Path("myProjects/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectModel> getProjects(@PathParam("id") int id) throws SQLException {
		return projectRepo.getProjectsByResearcher(id);
	}
	
	@Path("myProjects/request/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectModel> getProjectRequests(@PathParam("id") int id) throws SQLException {
		return projectRepo.getProjectRequests(id);
	}
	
	@Path("myProjects/project/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ProjectModel getProject(@PathParam("id") int id) throws SQLException {
		return projectRepo.getProject(id);
	}
	
	@Path("myProjects/resquest")
	@POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
    public String CreateProjectRequest(@FormDataParam("obj") String obj,
        @FormDataParam("file") InputStream uploadedInputStream,
        @FormDataParam("file") FormDataContentDisposition fileDetail) throws SQLException {
		ProjectModel project=new ProjectModel();
		 JSONObject obj1 = new JSONObject(obj);
		 project.setProject_name(obj1.getString("name"));
		 project.setDescription(obj1.getString("description"));
		 project.setCategoryId(obj1.getInt("category"));
		 project.setApproval(0);
		 project.setProject_type("document");
		 project.setFile("/FrontEnd/ProjectFiles/" + fileDetail.getFileName());
		 project.setStatus_id(0);
		 project.setResearcherId(obj1.getInt("researcherId"));
		 
		     String uploadedFileLocation = "C:\\wamp64\\www\\IT17131902_PAF_Practical\\FrontEnd\\WebContent\\ProjectFiles\\" + fileDetail.getFileName();
	       
	         writeToFile(uploadedInputStream, uploadedFileLocation);
	         
	         projectRepo.createProjectRequest(project);
				
	         return ("Success !");


    }
	
	@Path("myProjects/")
	@POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
    public String CreateProject(@FormDataParam("obj") String obj,
        @FormDataParam("file") InputStream uploadedInputStream,
        @FormDataParam("file") FormDataContentDisposition fileDetail) throws SQLException {
		ProjectModel project=new ProjectModel();
		 JSONObject obj1 = new JSONObject(obj);
		 project.setPrice(obj1.getString("price"));
		 project.setProject_type("project");
		 project.setProject_id(obj1.getInt("project_id"));
		 project.setFile("/FrontEnd/ProjectFiles/" + fileDetail.getFileName());
		 
		     String uploadedFileLocation = "C:\\wamp64\\www\\IT17131902_PAF_Practical\\FrontEnd\\WebContent\\ProjectFiles\\" + fileDetail.getFileName();
	       
	         writeToFile(uploadedInputStream, uploadedFileLocation);
	         
	         projectRepo.createProject(project);
				
	         return ("Success !");


    }
	
	@Path("myProjects/img")
	@POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
    public String addProjectImg(@FormDataParam("obj") String obj,
        @FormDataParam("file") InputStream uploadedInputStream,
        @FormDataParam("file") FormDataContentDisposition fileDetail) throws SQLException {
		ProjectModel project=new ProjectModel();
		 JSONObject obj1 = new JSONObject(obj);
		 project.setProject_id(obj1.getInt("project_id"));
		 project.setImgPath("/FrontEnd/ProjectImages/" + fileDetail.getFileName());
		 
		     String uploadedFileLocation = "C:\\wamp64\\www\\IT17131902_PAF_Practical\\FrontEnd\\WebContent\\ProjectImages\\" + fileDetail.getFileName();
	       
	         writeToFile(uploadedInputStream, uploadedFileLocation);
	         
	         projectRepo.addProjectImg(project);
				
	         return ("Success !");


    }
	
	//Category Controllers-------------------------------------------------------------------------------------------------------------------
	
	@Path("Categories/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CategoryModel> getCategories() throws SQLException {
		return categoryRepo.getCategories();
	}

	
	//Users Controllers-------------------------------------------------------------------------------------------------------------------
	
	@Path("users/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UserModel getUser(@PathParam("id") int id) throws SQLException {
		return userRepo.getUser(id);
	}
	
	@Path("users/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteUser(@PathParam("id") int id) throws SQLException {
		return userRepo.deleteUser(id);
	}
	
	@Path("users/card/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UserCardModel getUserCard(@PathParam("id") int id) throws SQLException {
		return userRepo.getUserCard(id);
	}
	
	@Path("users")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserModel UpdateUser(UserModel a) throws SQLException {

			userRepo.updateUser(a);
		
		return a;
	}
	
	@Path("users/updateCard")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserCardModel UpdateUserCard(UserCardModel a) throws SQLException {

			userRepo.updateUserCard(a);
		
		return a;
	}
	
	@Path("users/updateImg")
	@PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
    public String CreateUser(@FormDataParam("obj") String obj,
        @FormDataParam("file") InputStream uploadedInputStream,
        @FormDataParam("file") FormDataContentDisposition fileDetail) throws SQLException {
		 JSONObject obj1 = new JSONObject(obj);
		     
		     String uploadedFileLocation = "C:\\wamp64\\www\\IT17131902_PAF_Practical\\FrontEnd\\WebContent\\ProfileImages\\" + fileDetail.getFileName();
	       
	         writeToFile(uploadedInputStream, uploadedFileLocation);
	         
	         userRepo.updateUserImg(obj1.getInt("id"),"/FrontEnd/ProfileImages/" + fileDetail.getFileName());
				
	         return ("Success !");


    }
	
	
    private void writeToFile(InputStream uploadedInputStream,
            String uploadedFileLocation) {

            try {
                OutputStream out = new FileOutputStream(new File(
                        uploadedFileLocation));
                int read = 0;
                byte[] bytes = new byte[1024];

                out = new FileOutputStream(new File(uploadedFileLocation));
                while ((read = uploadedInputStream.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
                out.close();
            } catch (IOException e) {

                e.printStackTrace();
            }

        }
	
}
