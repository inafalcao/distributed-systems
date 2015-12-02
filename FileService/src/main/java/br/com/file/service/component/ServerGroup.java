package br.com.file.service.component;

import br.com.file.service.group.Server;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inafalcao on 10/20/15.
 */
@Component("serverGroup")
public class ServerGroup {

    String groupName = "serverGroup";

    private List<Server> servers;

    public ServerGroup() {
        // Adding servers

        servers = new ArrayList<>();

        Server s1 = new Server();
        s1.start();
        s1.connectToGroup(groupName);

        Server s2 = new Server();
        s2.start();
        s2.connectToGroup(groupName);

        Server s3 = new Server();
        s3.start();
        s3.connectToGroup(groupName);

        servers.add(s1);
        servers.add(s2);
        servers.add(s3);

        servers.get(0).sendElection();

    }

    public Server getMaster() {
        for (Server s : servers) {
            if(s.isMaster()) {
                return s;
            }
        }
        return null;
    }

    public String showGroup() {
        String g = "\n";
        for (Server s : servers) {
            g += "    " + s.toString() + "\n";
        }
        return g;
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }
}
