package br.com.file.service.model;

import java.rmi.Remote;
import java.util.List;

/**
 * Created by inafalcao on 10/20/15.
 */
public interface RemoteFileOperations {

    RemoteFile viewFile(RemoteFile f);

    void removeFile(Integer id);

    void editFile(RemoteFile file);

    void createFile(RemoteFile file);

}
