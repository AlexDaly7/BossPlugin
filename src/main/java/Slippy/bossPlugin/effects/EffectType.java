package Slippy.bossPlugin.effects;

import java.util.Map;

public enum EffectType {
    POTION {
        @Override
        public Effect create(Map<String, Object> data) {
            return new Effect(data);
        }
    },
    PARTICLE {
        @Override
        public Effect create(Map<String, Object> data) {
            return new Effect(data);
        }
    };

    public abstract Effect create(Map<String, Object> data);
}
