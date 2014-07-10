package pl.com.mat.raytracer.rmi;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public final class GenericWorker<
        TPartialInput extends Serializable,
        TPartialOutput extends Serializable,
        TTask extends ITask<?, TPartialInput, TPartialOutput, ?>
        > extends UnicastRemoteObject implements IWorker<TPartialInput, TPartialOutput, TTask> {
	private static final long serialVersionUID = 1L;
	
	private int workerIndex;

	public GenericWorker(String workerHandle, int index) throws RemoteException, MalformedURLException {
        super();
        this.workerIndex = index;

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        Naming.rebind(workerHandle, this);

        System.out.println("+ worker ready");
    }

    @Override
    public TPartialOutput compute(TTask task, TPartialInput subInput) throws RemoteException {
        System.out.println("- receive subinput");

        TPartialOutput result = task.compute(subInput, workerIndex);

        System.out.println("- suboutput created");

        return result;
    }
}
