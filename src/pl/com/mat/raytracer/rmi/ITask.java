package pl.com.mat.raytracer.rmi;

import java.io.Serializable;
import java.util.ArrayList;

public interface ITask<
        TInput extends Serializable,
        TPartialInput extends Serializable,
        TPartialOutput extends Serializable,
        TOutput extends Serializable
        > extends Serializable {
    public ArrayList<TPartialInput> split(TInput input);

    public TPartialOutput compute(TPartialInput subInputs, int workerIndex);

    public TOutput combine(ArrayList<TPartialOutput> subOutputs);

    public boolean isComplete(ArrayList<TPartialOutput> subOutputs);
}
