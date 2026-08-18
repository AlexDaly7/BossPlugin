package Alex.bossPlugin.passiveEffects;

import java.util.Map;

public enum PassiveEffectType {
    POTION {
        @Override
        public PassiveEffect create(Map<String, Object> data) {
            return new Potion(data);
        }
    },
    PARTICLE {
        @Override
        public PassiveEffect create(Map<String, Object> data) {
            return new PassiveEffect(data);
        }
    };

    public abstract PassiveEffect create(Map<String, Object> data);
}
