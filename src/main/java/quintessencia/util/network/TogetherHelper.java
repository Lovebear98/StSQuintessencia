package quintessencia.util.network;

import quintessencia.reagents.AbstractReagent;
import spireTogether.SpireTogetherMod;
import spireTogether.networkcore.objects.NetworkObject;
import spireTogether.subscribers.TiSCustomSerializationSubscriber;

import java.util.ArrayList;
import java.util.HashMap;

public class TogetherHelper implements TiSCustomSerializationSubscriber {
    public static void TogetherRegister(){
        SpireTogetherMod.subscribe(new TogetherHelper());
    }

    @Override
    public HashMap<Class<?>, Class<? extends NetworkObject<?>>> getCustomSerializers() {
        return null;
    }

    @Override
    public ArrayList<Class<?>> getDefaultSerializableClasses() {
        ArrayList<Class<?>> classes = new ArrayList<>();
        classes.add(AbstractReagent.class);
        return classes;
    }
}
