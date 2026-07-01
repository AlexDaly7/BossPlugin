package Slippy.bossPlugin.abilities;

import java.util.Map;

public enum AbilityType {
    SUMMON_SPIDERS {
        @Override
        public Ability create(Map<String, Object> data) {
            return new SummonSpiders(data);
        }
    },
    EVOKER_FANG_STRIKE {
        @Override
        public Ability create(Map<String, Object> data) {
            return new EvokerFangStrike(data);
        }
    },
    EXPLOSION {
        @Override
        public Ability create(Map<String, Object> data) {
            return new Explosion(data);
        }
    },
    BLIND_NEARBY {
        @Override
        public Ability create(Map<String, Object> data) {
            return new BlindNearby(data);
        }
    };

    public abstract Ability create(Map<String, Object> data);
}