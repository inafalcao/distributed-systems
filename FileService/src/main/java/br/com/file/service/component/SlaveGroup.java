package br.com.file.service.component;

import br.com.file.service.group.Slave;
import br.com.file.service.model.RemoteFile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inafalcao on 10/20/15.
 */
@Component("slaveGroup")
public class SlaveGroup {

    String groupName = "slaveGroup";

    public static List<Slave> slaves;
    List<Slave> slavesWaiting;

    public SlaveGroup() {
        // Adding slaves

        slaves = new ArrayList<>();
        slavesWaiting = new ArrayList<>();

        Slave s1 = new Slave();
        s1.connectToGroup(groupName);

        Slave s2 = new Slave();
        s2.connectToGroup(groupName);

        Slave s3 = new Slave();
        s3.connectToGroup(groupName);

        slaves.add(s1);
        slaves.add(s2);
        slaves.add(s3);
    }

    public Slave getAvaliableSlave(RemoteFile file) {

        /* Vê se alguém da lista já pode escrever no arquivo */
        for (Slave s : slavesWaiting) {
            if(!s.isBusy()) {
                s.askAvailabilityMessage();
                while(s.isWaiting()) {} //
                if(!s.isBusy()) {
                    slavesWaiting.remove(s);
                    return s;
                }
            }
        }

        /* Vê se tem alguém que não tava esperando e que pode mexer no arquivo */
        for (Slave s : slaves) {
            /* Se não já está ocupado nem esperando */
            if(!s.isBusy() && !slavesWaiting.contains(s)) {
                s.setRemoteFile(file);
                s.askAvailabilityMessage(); /* Pergunta pra todo mundo se alguém tá mexendo no arquivo */
                while(s.isWaiting()) {} //
                if(!s.isFileAvaliable()) {
                    slavesWaiting.add(s); // se tem alguém mexendo, bota na fila
                } else {
                    return s;
                }
            }
        }

        return null;
    }

}
