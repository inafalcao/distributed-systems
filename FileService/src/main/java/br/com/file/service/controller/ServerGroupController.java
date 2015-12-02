package br.com.file.service.controller;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import br.com.file.service.component.ServerGroup;
import br.com.file.service.component.SlaveGroup;
import br.com.file.service.enumeration.Operation;
import br.com.file.service.group.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.file.service.model.RemoteFile;

import java.util.List;

@Controller
public class ServerGroupController  {

    @Autowired
    ServerGroup serverGroup;

	@PostConstruct
    public void init() throws Exception {
        System.out.println("Waiting for MASTER ELECTION ");

        Thread.sleep(5000);
        while(serverGroup.getMaster() == null) {}

        System.out.println("SERVER GROUP: " + serverGroup.showGroup());
        System.out.println("MASTER IS: " + serverGroup.getMaster().toString());
    }

	@RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
	public String create(@RequestBody RemoteFile file) {
        serverGroup.getMaster().addWaitingOperation(Operation.CREATE, file);
        while (!file.isBack()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "Pronto";
	}

    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RemoteFile read(@PathVariable int id) {
        RemoteFile file = new RemoteFile(id);
        serverGroup.getMaster().addWaitingOperation(Operation.READ, file);
        while (!file.isBack()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String remove(@PathVariable int id) {
        RemoteFile file = new RemoteFile(id);
        serverGroup.getMaster().addWaitingOperation(Operation.REMOVE, file);
        while (!file.isBack()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "Pronto";
    }

    @RequestMapping(value = "/write", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String update(@RequestBody RemoteFile file) {
        serverGroup.getMaster().addWaitingOperation(Operation.WRITE, file);
        while (!file.isBack()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "Pronto";
    }

	@RequestMapping(value="/shutdown/server/{index}", method=RequestMethod.PUT)
    @ResponseBody
	public String shutdownServer(@PathVariable int index) throws Exception {
		int size = serverGroup.getServers().size();

		if(index > size-1 || index < 0)
			return "Index out of bound of the servers list";

		serverGroup.getServers().remove(index).leave();

		// Trigger another ELECTION, if there's any Server to attend.
		if(size-1 > 0) {

            for(Server s : serverGroup.getServers())
                s.setIsMaster(false);

			serverGroup.getServers().get(0).sendElection();
			init();
            return "SERVER GROUP: " + serverGroup.showGroup() +
                   "MASTER IS: " + serverGroup.getMaster().toString();
		}
		else
			return "We have no servers.";

	}

    @RequestMapping(value="/shutdown/slave/{index}", method=RequestMethod.PUT)
    @ResponseBody
    public String shutdownSlave(@PathVariable int index) throws Exception {
        int size = SlaveGroup.slaves.size();

        if(index > size-1 || index < 0)
            return "Index out of bound of the slaves list";

        SlaveGroup.slaves.remove(index);

        if(size-1 > 0)
            return "Slave removed. We still have slaves.";
        else
            return "Slave removed. We have no slaves.";
    }

}
