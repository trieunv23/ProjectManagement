package com.gui.projectmanagement.network;

import com.gui.projectmanagement.controller.InterfaceClientController;
import com.gui.projectmanagement.controller.MessageObject;
import com.gui.projectmanagement.entity.*;

import javax.security.auth.callback.Callback;
import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StreamFunction {

    DataService ds = new DataService();

    InterfaceClientController icc ;

    public StreamFunction(InterfaceClientController icc) {
        this.icc = icc ;
    }

    public StreamFunction() {
    }

    public ClientData login(StreamObject so, LoginObject lo) {
        ClientData client_data = null;
        ds.send(so.getDos(), "@login");
        ds.send(so.getDos(), lo);
        String result = ds.receive(so.getDis(), TypeData.STRING);
        if (result.equals("@accept")) {
            client_data = ds.receive(so.getDis(), TypeData.CLIENT_DATA);
        } else if (result.equals("@refuse")) {
            System.out.println("Login failed!");
        }
        return client_data;
    }

    public void regester_first(StreamObject so, RegesterObject ro) {
        ds.send(so.getDos(), "@regester");
        ds.send(so.getDos(), ro);
    }

    public String regester_last(StreamObject so, String code) {
        ds.send(so.getDos(), code);
        String result = ds.receive(so.getDis(), TypeData.STRING);
        return result ;
    }

    public boolean forgotPassword_frist(StreamObject so, String username) {
        ds.send(so.getDos(), "@forgot_password");
        ds.send(so.getDos(), username);
        return ds.receive(so.getDis(), TypeData.BOOLEAN) ;
    }

    public boolean forgotPassword_mid(StreamObject so, String code) {
        ds.send(so.getDos(), code);
        return ds.receive(so.getDis(), TypeData.BOOLEAN) ;
    }

    public boolean forgotPassword_last(StreamObject so, String new_password) {
        ds.send(so.getDos(), new_password);
        return ds.receive(so.getDis(), TypeData.BOOLEAN) ;
    }

    public void createProject(StreamObject so, CreateProjectObject cpo) {
        ds.send(so.getDos(), "@new_project");
        ds.send(so.getDos(), cpo);
    }

    public ProjectObject receiveProjectJustCreate(StreamObject so) {
        ProjectObject po = ds.receive(so.getDis(), TypeData.PROJECT_OBJECT) ;
        return po ;
    }

    public List<ProjectPreview> getProjects(StreamObject so, String client_id){
        ds.send(so.getDos(), "@projects");
        ds.send(so.getDos(), client_id);
        List<ProjectPreview> projects = ds.receive(so.getDis(), TypeData.PROJECTS) ;
        return projects ;
    }

    public void createTask(StreamObject so, CreateTaskProject ctp) {

    }

    public void sendRqProjectControl(StreamObject so, String project_id) {
        ds.send(so.getDos(), "@project_control");
        ds.send(so.getDos(), project_id);
    }

    public ProjectControl receiveProjectControl(StreamObject so) {
        ProjectControl project = ds.receive(so.getDis(), TypeData.PROJECT_CONTROL) ;
        return project ;
    }

    public List<ClientObject> receiveMembers(StreamObject so) {
        List<ClientObject> members = ds.receive(so.getDis(), TypeData.MEMBERS) ;
        return members ;
    }

    public void sendRqTask(StreamObject so, String task_id) {
        ds.send(so.getDos(), "@task");
        ds.send(so.getDos(), task_id);
    }

    public TaskObject receiveTask(StreamObject so) {
        TaskObject task = ds.receive(so.getDis(), TypeData.TASK) ;
        return task ;
    }

    public void sendRqProduct(StreamObject so, String product_id) {
        ds.send(so.getDos(), "@product");
        ds.send(so.getDos(), product_id);
    }

    public ProductPreview receiveProduct(StreamObject so) {
        ProductPreview pp = ds.receive(so.getDis(), TypeData.PRODUCT) ;
        return pp ;
    }

    public ClientObject1 getInforClient(StreamObject so, String client_id) {
        ds.send(so.getDos(), "@client");
        ds.send(so.getDos(), client_id);
        ClientObject1 client = ds.receive(so.getDis(), TypeData.TASK) ;
        return client;
    }

    public void createProduct(StreamObject so, CreateProductObject cpo) {
        ds.send(so.getDos(), "@upload_product");
        ds.send(so.getDos(), cpo);
    }

    public ProductObject receveProductJustCreate(StreamObject so) {
        ProductObject po = ds.receive(so.getDis(), TypeData.PRODUCT_OBJECT) ;
        return po ;
    }

    public TaskObject receiveTaskJustCreate(StreamObject so) {
        TaskObject task = ds.receive(so.getDis(), TypeData.TASK) ;
        return task ;
    }

    public void sendRqTask(StreamObject so, CreateTaskProject ctp) {
        ds.send(so.getDos(), "@new_task");
        ds.send(so.getDos(), ctp);
    }

    public List<ContactObject> receiveContacts(StreamObject so) {
        List<ContactObject> contacts = ds.receive(so.getDis(), TypeData.CONTACTS) ;
        return contacts ;
    }

    public void sendRqContacts(StreamObject so, String client_id) {
        ds.send(so.getDos(), "@contacts");
        ds.send(so.getDos(), client_id);
    }

    public void searchClient(StreamObject so, String client_email) {
        ds.send(so.getDos(), "@search_client");
        ds.send(so.getDos(), client_email);
    }

    public ContactObject receiveClient(StreamObject so) {
        ContactObject co = ds.receive(so.getDis(), TypeData.CLIENT) ;
        return co ;
    }

    public void sendRqGetInteractor(StreamObject so, String client_id) {
        ds.send(so.getDos(), "@interactor_just_sendMs");
        ds.send(so.getDos(), client_id);
    }

    public ContactObject receiveInteractorJustSendMs(StreamObject so) {
        return ds.receive(so.getDis(), TypeData.CLIENT);
    }

    public void sendMessage(StreamObject so, MessageSend ms) {
        ds.send(so.getDos(), "@send_message");
        ds.send(so.getDos(), ms);
    }

    public Message receiveMessage(StreamObject so) {
        Message message = ds.receive(so.getDis(), TypeData.MESSAGE) ;
        return message ;
    }

    public Message returnMessage(StreamObject so) {
        Message message = ds.receive(so.getDis(), TypeData.MESSAGE) ;
        return message ;
    }

    public ContactObject getClientById(StreamObject so, String client_id) {
        ds.send(so.getDos(), "@client");
        ds.send(so.getDos(), client_id);
        ContactObject client = ds.receive(so.getDis(), TypeData.CLIENT) ;
        return client ;
    }

    public void sendRqGetInteractors(StreamObject so, String client_id) {
        ds.send(so.getDos(), "@interactors");
        ds.send(so.getDos(), client_id);
    }

    public List<ContactObject> getInteractor(StreamObject so, String client_id) {
        ds.send(so.getDos(), "@interactors");
        ds.send(so.getDos(), client_id);
        List<ContactObject> interactors = ds.receive(so.getDis(), TypeData.CONTACTS) ;
        return interactors ;
    }

    public void sendRqMessages(StreamObject so, String client_id, String interactor_id) {
        ds.send(so.getDos(), "@messages");
        ds.send(so.getDos(), client_id);
        ds.send(so.getDos(), interactor_id);
    }

    public List<Message> receiveMessages(StreamObject so) {
        List<Message> messages = ds.receive(so.getDis(), TypeData.MESSAGES) ;
        return messages ;
    }

    public void sendRqAddMember(StreamObject so, String client_id, String project_id) {
        ds.send(so.getDos(), "@add_members");
        ds.send(so.getDos(), client_id);
        ds.send(so.getDos(), project_id);
    }

    public String receiveIdNewMember(StreamObject so) {
        return ds.receive(so.getDis(), TypeData.STRING) ;
    }

    public void sendRqAddContact(StreamObject so, String rq_sender, String rq_receive) {
        ds.send(so.getDos(), "@add_contact");
        ds.send(so.getDos(), rq_sender);
        ds.send(so.getDos(), rq_receive);
    }

    public boolean receiveResultSendRequest(StreamObject so) {
        boolean is_success = ds.receive(so.getDis(), TypeData.BOOLEAN) ;
        return is_success ;
    }

    public RequestAddContact receiveRequestFromSender(StreamObject so) {
        RequestAddContact rac = ds.receive(so.getDis(), TypeData.REQUEST) ;
        return rac ;
    }

    public void sendRqResetTask(StreamObject so, String task_id) {
        ds.send(so.getDos(), "@reset_task");
        ds.send(so.getDos(), task_id);
    }

    public void sendRqResetProject(StreamObject so, String project_id) {
        ds.send(so.getDos(), "@reset_project");
        ds.send(so.getDos(), project_id);
    }

    public ProjectControl receiveNewProject(StreamObject so) {
        String project_id = ds.receive(so.getDis(), TypeData.STRING);
        ProjectControl pc = ds.receive(so.getDis(), TypeData.PROJECT_CONTROL) ;
        return pc ;
    }

    public void sendRqRquestAddContact(StreamObject so, String client_id) {
        ds.send(so.getDos(), "@requests");
        ds.send(so.getDos(), client_id);
    }

    public List<RequestAddContact> receiveRequest(StreamObject so) {
        List<RequestAddContact> requests = ds.receive(so.getDis(), TypeData.REQUESTS) ;
        return requests ;
    }

    public void sendRequestFeedBacks(StreamObject so, String client_id) {
        ds.send(so.getDos(), "@feedbacks");
        ds.send(so.getDos(), client_id);
    }

    public List<FeedBackObject> receiveFeedbacks(StreamObject so) {
        List<FeedBackObject> feedbacks = ds.receive(so.getDis(), TypeData.FEEDBACKS) ;
        return feedbacks ;
    }

    public void acceptAddContact(StreamObject so, String request_id, String client_id, String contact_id) {
        ds.send(so.getDos(), "@accept");
        ds.send(so.getDos(), request_id);
        ds.send(so.getDos(), client_id);
        ds.send(so.getDos(), contact_id);
    }

    public String receiveNewContactId(StreamObject so) {
        String contact_id = ds.receive(so.getDis(), TypeData.STRING) ;
        return contact_id ;
    }

    public void sendRequestAddContact(StreamObject so, String client_id, String client_receive_id) {
        ds.send(so.getDos() ,"@request_add_contact");
        ds.send(so.getDos() ,client_id);
        ds.send(so.getDos() ,client_receive_id);
    }

    public List<ContactObject> getContacts(StreamObject so, String client_id) {
        ds.send(so.getDos() ,"@contacts") ;
        ds.send(so.getDos(), client_id);
        String req_tmp = ds.receive(so.getDis(), TypeData.STRING) ;
        List<ContactObject> contacts = ds.receive(so.getDis(), TypeData.CONTACTS) ;
        return contacts ;
    }

    public List<RequestAddContact> getRequest(StreamObject so, String client_id) {
        ds.send(so.getDos(), "@requests");
        ds.send(so.getDos(), client_id);
        String req_tmp = ds.receive(so.getDis(), TypeData.STRING) ;
        List<RequestAddContact> requests = ds.receive(so.getDis(), TypeData.REQUESTS) ;
        return requests ;
    }

    public void sendFeedBack(StreamObject so, CreateFeedBack cfb) {
        ds.send(so.getDos(), "@send_feedbacks");
        ds.send(so.getDos(), cfb);
    }

    public FeedBackObject receveFeedBackFromSender(StreamObject so) {
        FeedBackObject fo = ds.receive(so.getDis(), TypeData.FEEDBACK) ;
        return fo ;
    }

    public List<FeedBackObject> getFeedBacks(StreamObject so, String client_id) {
        ds.send(so.getDos(), "@feedbacks");
        ds.send(so.getDos(), client_id);
        String req_tmp = ds.receive(so.getDis(), TypeData.STRING) ;
        List<FeedBackObject> feebacks = ds.receive(so.getDis(), TypeData.FEEDBACKS) ;
        return feebacks ;
    }

    public ContactObject receiveNewContact(StreamObject so) {
        ContactObject contact = ds.receive(so.getDis(), TypeData.CLIENT) ;
        return contact ;
    }

    public void changeBasicInformation(StreamObject so, BasicInformation bi) {
        ds.send(so.getDos(), "@change_bsinf");
        ds.send(so.getDos(), bi);
    }

    public void sendRqFile(StreamObject so, String file_id) {
        ds.send(so.getDos(), "@file");
        ds.send(so.getDos(),file_id);
    }

    public FileObject getFile(StreamObject so) {
        FileObject fo = ds.receive(so.getDis(), TypeData.FILE) ;
        return fo ;
    }

    public void sendRqProductObject(StreamObject so, String product_id) {
        ds.send(so.getDos(), "@product_object");
        ds.send(so.getDos(), product_id);
    }

    public FileObject getProductObject(StreamObject so) {
        FileObject fo = ds.receive(so.getDis(), TypeData.FILE) ;
        return fo ;
    }

    public boolean receiveResultChangeBsIf(StreamObject so) {
        return ds.receive(so.getDis(), TypeData.BOOLEAN) ;
    }

    public void disconnectFirst(StreamObject so) {
        ds.send(so.getDos(), "@disconnect");
    }

    public boolean disconnectLast(StreamObject so) {
        return ds.receive(so.getDis(), TypeData.BOOLEAN) ;
    }

}
