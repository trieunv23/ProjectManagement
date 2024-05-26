package com.gui.projectmanagement.network;

import com.gui.projectmanagement.cache.Interactors;
import com.gui.projectmanagement.cache.Messages;
import com.gui.projectmanagement.controller.InterfaceClientController;
import com.gui.projectmanagement.controller.ProjectViewController;
import com.gui.projectmanagement.entity.*;
import com.gui.projectmanagement.network.DataService;
import com.gui.projectmanagement.network.StreamFunction;
import com.gui.projectmanagement.network.StreamObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Processing {

    StreamObject so = null;

    // Window

    InterfaceClientController icc = new InterfaceClientController() ;

    ProjectViewController pvc = new ProjectViewController();


    // Service
    DataService ds = new DataService() ;
    StreamFunction sf = new StreamFunction() ;

    // Interact
    ContactObject interactor = null ;
    ProjectPreview project_interact = null;

    public Processing(StreamObject so) {
        this.so = so ;
    }

    public void loadInterfaceClient(InterfaceClientController icc) {
        this.icc = icc ;
    }

    public void loadInterfaceProject(ProjectViewController pvc) {
        this.pvc = pvc ;
    }

    public void loadStream(StreamObject so) {
        this.so = so ;
    }

    public void openThread() {
        new Thread(() -> {
            boolean should_exit = false;
            while (!should_exit) {
                String request = ds.receive(so.getDis(), TypeData.STRING);
                switch (request) {
                    case "@interactors":
                        // List<ContactObject> contacts = sf.receiveInteractor(so) ;
                        // icc.updateInteractor(contacts);
                        break ;
                    case "@project_control":
                        String project_id = ds.receive(so.getDis(), TypeData.STRING) ;
                        ProjectControl pc = sf.receiveProjectControl(so) ;
                        icc.openProjectView(pc.getMembers(), pc.getTasks(), project_id);
                        break ;
                    case "@task":
                        TaskObject task = sf.receiveTask(so) ;
                        ProductPreview pp = sf.receiveProduct(so) ;
                        pvc.changeTask(task, pp);
                        break ;
                    case "@reset_task":
                        TaskObject new_task = sf.receiveTask(so) ;
                        ProductPreview new_pp = sf.receiveProduct(so) ;
                        pvc.updateTask(new_task, new_pp);
                        break ;
                    case "@task_just_create":
                        TaskObject task_object = sf.receiveTaskJustCreate(so) ;
                        pvc.buildTreeItem(task_object);
                        break ;
                    case "@product_just_create":
                        ProductObject po = ds.receive(so.getDis(), TypeData.PRODUCT_OBJECT) ;
                        // ?
                        break ;
                    case "@product" :
                        ProductObject po_1 = sf.receveProductJustCreate(so) ;
                        break ;
                        // ?
                    case "@project_just_create":
                        ProjectObject project_object = sf.receiveProjectJustCreate(so) ;
                        icc.updateListProject(project_object);
                        break ;
                    case "@messages":
                        List<Message> messages = sf.receiveMessages(so) ;
                        icc.displayMessage(messages);
                        break ;
                    case "@message_return" :
                        Message message = sf.returnMessage(so) ;
                        icc.updateMessage(message);
                        break ;
                    case "@new_message":
                        Message m = sf.receiveMessage(so) ;
                        icc.receiveMessage(m);
                        break ;
                    case "@interactor_just_sendMs" :
                        ContactObject co = sf.receiveInteractorJustSendMs(so) ;
                        icc.newInteractor(co);
                        break ;
                    case "@contacts":
                        List<ContactObject> contacts_obj = sf.receiveContacts(so) ;
                        icc.displayContacts(contacts_obj);
                        break ;
                    case "@result_client":
                        ContactObject client = sf.receiveClient(so) ;
                        icc.displayProfileClient(client);
                        break ;
                    case "@new_member":
                        String new_member_id = sf.receiveIdNewMember(so) ;
                        pvc.updateMembers(new_member_id) ;
                        break ;
                    case "@reset_project":
                        ProjectControl new_pc = sf.receiveNewProject(so) ;
                        pvc.updateProject(new_pc) ;
                        break ;
                    case "@requests":
                        List<RequestAddContact> requests = sf.receiveRequest(so) ;
                        icc.contructorRequest(requests);
                        break ;
                    case "@feedbacks":
                        List<FeedBackObject> feebacks = sf.receiveFeedbacks(so) ;
                        icc.constructorFeedBack(feebacks) ;
                        break;
                    case "@accept_success":
                        String contact_id = sf.receiveNewContactId(so) ;
                        icc.updateContact(contact_id) ;
                        break ;
                    case "@new_request" :
                        RequestAddContact rac = sf.receiveRequestFromSender(so) ;
                        icc.updateRequest(rac) ;
                        break ;
                    case "@new_feedback" :
                        FeedBackObject fbo = sf.receveFeedBackFromSender(so) ;
                        icc.updateFeedBack(fbo);
                        break ;
                    case "@new_contact":
                        ContactObject contact = sf.receiveNewContact(so) ;
                        icc.updateNewContact(contact) ;
                        break ;
                    case "@result_change_infor":
                        boolean rs = sf.receiveResultChangeBsIf(so) ;
                        if (rs) {
                            icc.alert("Successful change!");
                            icc.updateBasicInformation();
                        } else {
                            icc.error("Change failed!");
                        }
                        break ;
                    case "@file_return":
                        FileObject fo = sf.getFile(so) ;
                        icc.downloadFile(fo);
                        break ;
                    case "@product_object":
                        FileObject fo2 = sf.getProductObject(so) ;
                        pvc.dowloadProduct(fo2);
                        break ;
                    case "@disconnect":
                        boolean result = sf.disconnectLast(so);
                        if (result) {
                            should_exit = true ;
                            try {
                                so.getDis().close();
                                so.getDos().close();
                                so.getSocket().close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        System.out.println("Has disconnect!");
                        break ;
                }
            }
        }).start();
    }
}
