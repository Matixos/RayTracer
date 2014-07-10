package pl.com.mat.raytracer.rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IFarmer<
        TInput extends Serializable,
        TOutput extends Serializable,
        TTask extends ITask<TInput, ?, ?, TOutput>
        > extends Remote {
    public TOutput compute(TTask task, TInput input) throws RemoteException;
}
