package Slippy.bossPlugin.abilities;

import java.util.Map;

public enum AbilityType {
    SUMMON_MINIONS {
        @Override
        public Ability create(Map<String, Object> data) {
            return new SummonMinions(data);
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
    },
    HEAL {
        @Override
        public Ability create(Map<String, Object> data) {
            return new Heal(data);
        }
    },
    METEOR {
        @Override
        public Ability create(Map<String, Object> data) {
            return new Meteor(data);
        }
    };

    public abstract Ability create(Map<String, Object> data);
}