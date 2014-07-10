package pl.com.mat.raytracer.rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IWorker<
        TPartialInput extends Serializable,
        TPartialOutput extends Serializable,
        TTask extends ITask<?, TPartialInput, TPartialOutput, ?>
        > extends Remote {
    public TPartialOutput compute(TTask task, TPartialInput input) throws RemoteException;
}
