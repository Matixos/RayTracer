package pl.com.mat.raytracer.rmi;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public final class GenericFarmer<
        TInput extends Serializable,
        TPartialInput extends Serializable,
        TPartialOutput extends Serializable,
        TOutput extends Serializable,
        TTask extends ITask<TInput, TPartialInput, TPartialOutput, TOutput>
        > extends UnicastRemoteObject implements IFarmer<TInput, TOutput, TTask> {
	private static final long serialVersionUID = 1L;
	
	private final ArrayList<IWorker<TPartialInput, TPartialOutput, TTask>> mWorkers;
    private final int mWorkerCount;

    @SuppressWarnings("unchecked")
    public GenericFarmer(String farmerHandle, String... workerHandles) throws RemoteException, MalformedURLException, NotBoundException {
        super();

        mWorkerCount = workerHandles.length;
        mWorkers = new ArrayList<IWorker<TPartialInput, TPartialOutput, TTask>>(mWorkerCount);

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        Naming.rebind(farmerHandle, this);

        for (String workerHandle : workerHandles) {
            IWorker<TPartialInput, TPartialOutput, TTask> worker;
            worker = (IWorker<TPartialInput, TPartialOutput, TTask>) Naming.lookup(workerHandle);
            mWorkers.add(worker);
        }

        System.out.println("+ farmer ready");
    }

    private ArrayList<ArrayList<TPartialInput>> distribute(TTask task, TInput input) {
        List<TPartialInput> subInputs = task.split(input);
        System.out.println("- distribute SubInputs");

        ArrayList<ArrayList<TPartialInput>> distribution;

        // distribute subinputs
        distribution = new ArrayList<ArrayList<TPartialInput>>(mWorkerCount);
        for (int idx = 0; idx < mWorkerCount; ++idx) {
            distribution.add(new ArrayList<TPartialInput>(subInputs.size() / mWorkerCount));
        }

        for (int idx = 0; idx < subInputs.size(); ++idx) {
            distribution.get(idx % mWorkerCount).add(subInputs.get(idx));
        }

        System.out.println("- distribute: distribution size = " + distribution.size());

        return distribution;
    }

    @Override
    public TOutput compute(TTask task, TInput input) throws RemoteException {
        System.out.println("- farmer compute called");

        ArrayList<ArrayList<TPartialInput>> subInputs = distribute(task, input);
        ArrayList<TPartialOutput> subOutputs = new ArrayList<TPartialOutput>();
        ArrayList<Thread> threads = new ArrayList<Thread>(mWorkerCount);

        for (int idx = 0; idx < mWorkerCount; ++idx) {
            WorkerThread<TPartialInput, TPartialOutput, TTask> thread;
            IWorker<TPartialInput, TPartialOutput, TTask> worker;
            worker = mWorkers.get(idx);

            System.out.println("- creating WorkerThread #" + idx);
            thread = WorkerThread.create(worker, task, subInputs.get(idx), subOutputs);
            thread.start();

            threads.add(thread);
        }

        synchronized (subOutputs) {
            while (!task.isComplete(subOutputs)) {
                try {
                    System.out.println("- waiting on outputs");
                    subOutputs.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("- combining into final output");
        return task.combine(subOutputs);
    }
}
