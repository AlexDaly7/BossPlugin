package Slippy.bossPlugin.abilities;

import Slippy.bossPlugin.abilities.*;

public enum AbilityType {
    SUMMON_SPIDERS {
        @Override
        public Ability create(int range) {
            return new SummonSpiders(range);
        }
    },
    EVOKER_FANG_STRIKE {
        @Override
        public Ability create(int range) {
            return new EvokerFangStrike(range);
        }
    },
    EXPLOSION {
        @Override
        public Ability create(int range) {
            return new Explosion(range);
        }
    };

    public abstract Ability create(int range);
}