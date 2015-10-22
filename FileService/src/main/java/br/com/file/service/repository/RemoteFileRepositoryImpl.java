package br.com.file.service.repository;

import br.com.file.service.model.RemoteFile;
import org.springframework.stereotype.Repository;

@Repository("remoteFileRepository")
public class RemoteFileRepositoryImpl extends RepositoryImpl<RemoteFile> implements RemoteFileRepository {

    private static final long serialVersionUID = -8841031766612946531L;

    @Override
    public String value() {
        return "remoteFileRepository";
    }
}
