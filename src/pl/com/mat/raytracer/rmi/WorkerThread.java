package pl.com.mat.raytracer.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public final class WorkerThread<
        TPartialInput extends Serializable,
        TPartialOutput extends Serializable,
        TTask extends ITask<?, TPartialInput, TPartialOutput, ?>
        > extends Thread {
    private final IWorker<TPartialInput, TPartialOutput, TTask> mWorker;
    private final ArrayList<TPartialInput> mInputs;
    private final ArrayList<TPartialOutput> mOutputs;
    private final TTask mTask;

    public WorkerThread(
            IWorker<TPartialInput, TPartialOutput, TTask> worker,
            TTask task,
            ArrayList<TPartialInput> inputs,
            ArrayList<TPartialOutput> outputs
    ) {
        mWorker = worker;
        mOutputs = outputs;
        mInputs = inputs;
        mTask = task;
    }

    public static <
            TPartialInput extends Serializable,
            TPartialOutput extends Serializable,
            TTask extends ITask<?, TPartialInput, TPartialOutput, ?>
            > WorkerThread<TPartialInput, TPartialOutput, TTask> create(
            IWorker<TPartialInput, TPartialOutput, TTask> worker,
            TTask task,
            ArrayList<TPartialInput> inputs,
            ArrayList<TPartialOutput> outputs
    ) {
        return new WorkerThread<TPartialInput, TPartialOutput, TTask>(worker, task, inputs, outputs);
    }

    @Override
	public void run() {
        ArrayList<TPartialOutput> outputs = new ArrayList<TPartialOutput>(mInputs.size());

        for (TPartialInput input : mInputs) {
            try {
                TPartialOutput output = mWorker.compute(mTask, input);
                outputs.add(output);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        synchronized (mOutputs) {
            mOutputs.addAll(outputs);
            mOutputs.notifyAll();
        }
    }
}
