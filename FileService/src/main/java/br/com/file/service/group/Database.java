package br.com.file.service.group;

import br.com.file.service.model.RemoteFile;
import br.com.file.service.model.RemoteFileOperations;
import org.apache.commons.collections.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inafalcao on 9/14/15.
 */
public class Database implements RemoteFileOperations {

    private List<RemoteFile> files;

    public Database() {
        files = new ArrayList<>();
        files = ListUtils.synchronizedList(files);
    }

    @Override
    public RemoteFile viewFile(RemoteFile file) {
        for (RemoteFile f : files) {
            if (f.getId() == file.getId()) {
                return f;
            }
        }
        return null;
    }

    @Override
    public RemoteFile removeFile(Integer id) {
        for (RemoteFile f : files) {
            if (f.getId() == id) {
                return files.remove(files.indexOf(f));
            }
        }
        return null;
    }

    @Override
    public RemoteFile editFile(RemoteFile file) {
        for (RemoteFile f : files) {
            if (f.getId() == file.getId()) {
                files.set(files.indexOf(f), file);
                return file;
            }
        }
        return null;
    }

    @Override
    public RemoteFile createFile(RemoteFile file) {
        this.files.add(file);
        return file;
    }

    public void showFiles() {
        for (RemoteFile f : files) {
            System.out.printf("id: " + f.getId() + ", content: " + f.getContent() + " | ");
        }
        System.out.println("\n_________________________________________________________________");
    }

}
